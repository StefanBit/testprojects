package controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import application.MyProperties;
import gui.CandleChart.ChartStage;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import model.*;
import model.metrics.ArithmeticMean;
import model.metrics.FloatingMean;
import model.metrics.FloatingMean2;
import model.metrics.IMetric;
import model.metrics.LowerConvexHull;
import model.metrics.ProMean;
import model.metrics.TotalMean;
import util.database.DAOHsqlImpl;
import util.loader.SymbolLoaderTask;
import util.loader.HistoricalDataLoader.HistStockDataLoaderTask;
import util.loader.HistoricalDataLoader.HistStockDataLoaderWorker;
import util.loader.Metric.MetricMapLoaderWorker;
import util.log.Log;

public class TradeFXBusinessController {

	public static TradeFXBusinessController tfxc = null;
	static Boolean DEBUG = true;
	public SymbolLoaderTask symbolsLoaderTask;
	public HistStockDataLoaderWorker histDataLoaderWorker;
	public MetricMapLoaderWorker metricLoaderWorker;
	ArrayList<Thread> threads;
	TradeFXModel model;
	public MyProperties myProperties;
	public TradeFXApplicationController tradeFXApplicationController; 


	private TradeFXBusinessController() {
		this.model = new TradeFXModel();
		this.myProperties = new MyProperties();
		BufferedInputStream stream=null;
	}

	
	static synchronized public TradeFXBusinessController getInstance() {
		if (tfxc == null) {
			tfxc = new TradeFXBusinessController();
		}
		return tfxc;
	}

	public void init() {
		registerMetricClasses( new FloatingMean(6), new ArithmeticMean(),new FloatingMean2(100),new TotalMean());
		//,new LowerConvexHull()
		loadSymbols();
	}

	public TradeFXModel getModel() {
		return this.model;
	}

	public void registerMetricClasses(IMetric... aMetricClass) {
		for (IMetric iMetric : aMetricClass) {
			model.aMetrics.add(iMetric);
		}
	}

	public void loadSymbols() {
		Log.info("Start loadSymbols()");
		startThread(new SymbolLoaderTask(), new SymbolLoaderTaskListener());
	}

	public void loadHistData() {
		Log.info("start loadHistData()");
		startThread(new HistStockDataLoaderWorker(),new HistStockDataLoaderWorkerListener());
	}
	
	public void loadSymbolMetrics(){
		if (DEBUG) System.out.println("Start loadSymbolMetrics()");
		startThread(new MetricMapLoaderWorker(),new MetricLoaderWorkerListener());
	}
	public void finish(){
		tradeFXApplicationController.Status.textProperty().unbind();
		tradeFXApplicationController.Status.textProperty().set("Ready");
		tradeFXApplicationController.OverallProgress.progressProperty().unbind();
		if (DEBUG) {
			System.out.println("--------------------- Finished ------------------");
			System.out.println("-------------------------------------------------");
		}
	}
	
	private void startThread(Task task, EventHandler e){
		Thread thread;
		tradeFXApplicationController = TradeFXApplicationController.getInstance();
		tradeFXApplicationController.bindProgressfrom(task);
		task.setOnSucceeded(e);
		if (DEBUG) System.out.println(task.getClass().getSimpleName()+ " started.");
		thread = new Thread(task);
		thread.start();
	}

	
	class SymbolLoaderTaskListener implements EventHandler{
		@Override
		public void handle(Event event) {
			tradeFXApplicationController.symbolTablePane.setData(model.getStockSymbols());
			loadHistData();
		}
	}
	
	class HistStockDataLoaderWorkerListener implements EventHandler{
		@Override
		public void handle(Event event) {
			//tradeFXApplicationController.barchartview.load();
			System.out.println("Finished Task HistStockDataLoaderWorker ");
			tradeFXApplicationController.metricTableView.addColumn();
			//tradeFXApplicationController.AvaiableMetricsPane.setContent(tradeFXApplicationController.metricTableView);
			loadSymbolMetrics();
		}
	}
	
	class MetricLoaderWorkerListener implements EventHandler{
		@Override
		public void handle(Event event) {
			//System.out.println("Finished Task MetricLoaderWorker "+metricLoaderWorker.getProgress());
			
			model.selectedSymbol= model.StockSymbols.get(0);
			tradeFXApplicationController.changePanes();
			finish();
		}
	}
	
	
}



package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import controller.TradeFXApplicationController.MetricLoaderWorkerListener;
import database.DAOHsqlImpl;
import gui.stage.ChartStage;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import loader.HistStockDataLoaderTask;
import loader.HistStockDataLoaderWorker;
import loader.MetricMapLoaderWorker;
import loader.SymbolLoaderTask;
import model.*;
import model.metrics.ArithmeticMean;
import model.metrics.FloatingMean;
import model.metrics.FloatingMean2;
import model.metrics.IMetric;
import model.metrics.ProMean;

public class TradeFXBusinessController {

	public static TradeFXBusinessController tfxc = null;
	static Boolean DEBUG = true;
	public SymbolLoaderTask symbolsLoaderTask;
	public HistStockDataLoaderWorker histDataLoaderWorker;
	public MetricMapLoaderWorker metricLoaderWorker;
	ArrayList<Thread> threads;
	TradeFXModel model;

	private TradeFXBusinessController() {
		model = new TradeFXModel();
	}

	static synchronized public TradeFXBusinessController getInstance() {
		if (tfxc == null) {
			tfxc = new TradeFXBusinessController();
		}
		return tfxc;
	}

	public void init() {
		registerMetricClasses( new FloatingMean(6), new ArithmeticMean(),new FloatingMean2(100));
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
		symbolsLoaderTask = new SymbolLoaderTask();
		startThread(symbolsLoaderTask);
	}

	public void loadHistData() {
		histDataLoaderWorker= new HistStockDataLoaderWorker();
		startThread(histDataLoaderWorker);
	}
	
	public void loadSymbolMetrics(MetricLoaderWorkerListener metricLoaderWorkerListener){
		metricLoaderWorker=new MetricMapLoaderWorker();
		metricLoaderWorker.setOnSucceeded(metricLoaderWorkerListener);
		startThread(metricLoaderWorker);
	}
	
	private void startThread(Task task){
		if (DEBUG) System.out.println(task.getClass().getSimpleName()+ " started.");
		Thread thread;
		thread = new Thread(task);
		thread.start();
	}
}
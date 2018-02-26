package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
import loader.SymbolLoaderTask;
import model.*;
import model.metrics.ArithmeticMean;
import model.metrics.FloatingMean;

public class TradeFXBusinessController {

	public static TradeFXBusinessController tfxc = null;
	static Boolean DEBUG = true;
	static Boolean USE_SYNCRONIZE = true;
	public SymbolLoaderTask symbolsLoaderTask;
	public HistStockDataLoaderWorker worker;
	ArrayList<Thread> threads;
	TradeFXModel model;

	public TradeFXBusinessController() {
		model = new TradeFXModel();
		
		model.aMetrics.add(new FloatingMean(100));
		model.aMetrics.add(new ArithmeticMean());
	}

	static public TradeFXBusinessController getInstance() {
		if (tfxc == null) {
			tfxc = new TradeFXBusinessController();
		}
		return tfxc;
	}

	public void init() {
		loadSymbols();
	}

	public TradeFXModel getModel() {
		return this.model;
	}

	public void loadSymbols() {
		if (DEBUG) System.out.println("Loading Symbols");
		symbolsLoaderTask = new SymbolLoaderTask();
		Thread thread = new Thread(symbolsLoaderTask);
		thread.start();
	}

	public void loadHistData() {
		if (DEBUG) System.out.println("Loading HistData");
		Thread thread;
		worker= new HistStockDataLoaderWorker();
		thread = new Thread(worker);
		thread.start();
	}
	
	public void loadSymbolMetrics(){
		model.symbolMetric.registerMetricClasses( new FloatingMean(6), new ArithmeticMean());
		model.symbolMetric.build();
	}


}
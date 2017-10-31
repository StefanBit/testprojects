package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import database.DAOHsqlImpl;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import loader.HistStockDataLoaderTask;
import loader.SymbolLoaderTask;
import model.*;
import stage.ChartStage;

public class TradeFXController {

	public SymbolLoaderTask symbolsLoaderTask;

	ArrayList<Thread> threads;
	
	public TradeFXController() {
		// TODO Auto-generated constructor stub
	}
	
	public void init() {

		TradeFXModel.trades = new ArrayList<Transaction>();
		TradeFXModel.trades.add(new Transaction("MSFT",0.5d));
		TradeFXModel.trades.add(new Transaction("MSFT",0.5d));
		
		TradeFXModel.tasks = new HashMap<Symbol,HistStockDataLoaderTask>();
		threads = new ArrayList<Thread>();

		symbolsLoaderTask = new SymbolLoaderTask();
		
		Thread thread = new Thread(symbolsLoaderTask);
		thread.start();
		
	}
}
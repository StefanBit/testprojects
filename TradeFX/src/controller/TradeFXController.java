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
import model.FloatingMean;
import model.HistData;
import model.Symbol;
import model.TradeFXModel;
import view.ChartStage;

public class TradeFXController {

	public static Task<ArrayList<Symbol>> symbolsLoaderTask;

	public static void init() {

		TradeFXModel.tasks = new HashMap<Symbol,HistStockDataLoaderTask>();
		ArrayList<Thread> threads = new ArrayList<Thread>();

		symbolsLoaderTask = new Task<ArrayList<Symbol>>() {
			@Override
			protected ArrayList<Symbol> call() throws Exception {
				System.out.println("Start Task");
				// Load Symbols
				DAOHsqlImpl<Symbol> SymbolsLoader = new DAOHsqlImpl(Symbol.class);
				ArrayList<Symbol> alSymbols = SymbolsLoader.getAll();
				// Create SymbolMap
				TradeFXModel.StockHistData = new HashMap<Symbol, ArrayList<HistData>>();
				for (Symbol symbol : alSymbols) {
					TradeFXModel.StockHistData.put(symbol, null);
				}
				System.out.println("Stop Task");
				updateProgress(1, 1);
				return alSymbols;
			}

		};

		symbolsLoaderTask.setOnSucceeded((WorkerStateEvent event) -> {
			System.out.println(event);
			ArrayList<Symbol> alSymbols = null;
			alSymbols = symbolsLoaderTask.getValue();
			TradeFXModel.StockSymbols = symbolsLoaderTask.getValue();
			// new StocksStage( alSymbols);
			// Load Hist Data
			for (Map.Entry<Symbol, ArrayList<HistData>> entry : TradeFXModel.StockHistData.entrySet()) {
				Symbol currentSymbol = entry.getKey();
				
				HistStockDataLoaderTask currenttask = new HistStockDataLoaderTask();
				Thread currentThread;
				TradeFXModel.tasks.put(currentSymbol, currenttask);
				currenttask.alSymbol = currentSymbol;
				currentThread = new Thread(currenttask);
				currentThread.start();
				
			}

		});
		Thread thread = new Thread(symbolsLoaderTask);
		thread.start();
		
	}
}
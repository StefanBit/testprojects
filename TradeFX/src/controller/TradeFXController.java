package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import database.DAOHsqlImpl;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import model.HistData;
import model.Symbol;
import model.TradeFXModel;

public class TradeFXController {
	
	public static Task<ArrayList<Symbol>> symbolsLoaderTask;
	
	
	public static void init() {
		
		
		symbolsLoaderTask = new Task<ArrayList<Symbol>>() {
			@Override
			protected ArrayList<Symbol> call() throws Exception {
				System.out.println("Start Task");
				// Load Symbols
				DAOHsqlImpl<Symbol> SymbolsLoader = new DAOHsqlImpl(Symbol.class);
				ArrayList<Symbol> alSymbols = SymbolsLoader.getAll();
				// Create Map
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

		});
		Thread thread = new Thread(symbolsLoaderTask);
		thread.start();
	}

}

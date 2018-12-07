package util.loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.concurrent.Task;
import model.HistData;
import model.Symbol;
import model.TradeFXModel;
import util.database.DAOHsqlImpl;
import util.log.Log;

public class SymbolLoaderTask extends Task{
	public SymbolLoaderTask() {
	Log.fine("SymbolLoaderTask Constructed");
	}
	
	@Override
	protected ArrayList<Symbol> call() throws Exception {
		Log.fine("Started SymbolLoaderTask in Thread");
		this.updateMessage("Loading Symbols");
		// Load Symbols
		DAOHsqlImpl<Symbol> SymbolsLoader = new DAOHsqlImpl(Symbol.class);
		ArrayList<Symbol> alSymbols = SymbolsLoader.getAll();
		Log.fine("Found "+alSymbols.size()+" Symbols.");
		// Create SymbolMap
		TradeFXModel.StockHistData = new HashMap<Symbol, ArrayList<HistData>>();
		
		for (Symbol symbol : alSymbols) {
			TradeFXModel.StockHistData.put(symbol, null);
			Log.fine("Put "+ symbol + "in StockHIstData");
		}
		Log.fine("Stop SymbolLoaderTask");
		TradeFXModel.StockSymbols = alSymbols;
		updateProgress(1, 1);
		return alSymbols;
	}
	@Override
	protected void succeeded() {
		Log.fine("Task Succeded");
		ArrayList<Symbol> alSymbols = null;
		alSymbols = (ArrayList<Symbol>) this.getValue();
		TradeFXModel.StockSymbols = alSymbols;
	}
}


// Depricated
// new StocksStage( alSymbols);
// Load Hist Data
//for (Map.Entry<Symbol, ArrayList<HistData>> entry : TradeFXModel.StockHistData.entrySet()) {
//	Symbol currentSymbol = entry.getKey();
//	HistStockDataLoaderTask currenttask = new HistStockDataLoaderTask();
//	Thread currentThread;
//	TradeFXModel.tasks.put(currentSymbol, currenttask);
//	currenttask.alSymbol = currentSymbol;
//	currentThread = new Thread(currenttask);
//	currentThread.start();
//}


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
	Log.info("new SymbolLoaderTask");
	}
	@Override
	protected ArrayList<Symbol> call() throws Exception {
		Log.info("Start SymbolLoaderTask");
		this.updateMessage("Loading Symbols");
		// Load Symbols
		DAOHsqlImpl<Symbol> SymbolsLoader = new DAOHsqlImpl(Symbol.class);
		ArrayList<Symbol> alSymbols = SymbolsLoader.getAll();
		Log.info("Found "+alSymbols.size()+" Symbols.");
		// Create SymbolMap
		TradeFXModel.StockHistData = new HashMap<Symbol, ArrayList<HistData>>();
		
		for (Symbol symbol : alSymbols) {
			System.out.println(".");
			TradeFXModel.StockHistData.put(symbol, null);
			Log.info("Put "+ symbol + "in StockHIstData");
		}
		Log.info("Stop SymbolLoaderTask");
		TradeFXModel.StockSymbols = alSymbols;
		updateProgress(1, 1);
		return alSymbols;
	}
	@Override
	protected void succeeded() {
		System.out.println("Succeded");
		ArrayList<Symbol> alSymbols = null;
		alSymbols = (ArrayList<Symbol>) this.getValue();
		TradeFXModel.StockSymbols = alSymbols;
		// new StocksStage( alSymbols);
		// Load Hist Data
//		for (Map.Entry<Symbol, ArrayList<HistData>> entry : TradeFXModel.StockHistData.entrySet()) {
//			Symbol currentSymbol = entry.getKey();
//			HistStockDataLoaderTask currenttask = new HistStockDataLoaderTask();
//			Thread currentThread;
//			TradeFXModel.tasks.put(currentSymbol, currenttask);
//			currenttask.alSymbol = currentSymbol;
//			currentThread = new Thread(currenttask);
//			currentThread.start();
//		}
	}
}

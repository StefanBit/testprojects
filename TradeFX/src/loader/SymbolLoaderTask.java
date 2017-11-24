package loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import database.DAOHsqlImpl;
import javafx.concurrent.Task;
import model.HistData;
import model.Symbol;
import model.TradeFXModel;

public class SymbolLoaderTask extends Task{
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
	@Override
	protected void succeeded() {
		//System.out.println(event);
		ArrayList<Symbol> alSymbols = null;
		alSymbols = (ArrayList<Symbol>) this.getValue();
		TradeFXModel.StockSymbols = alSymbols;
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
	}
}
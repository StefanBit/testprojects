package loader;

import java.util.ArrayList;
import java.util.Map;

import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import model.HistData;
import model.Symbol;
import model.TradeFXModel;

public class HistStockDataLoaderWorker extends Task{

	@Override
	protected ArrayList<HistData>  call() throws Exception {
		System.out.println("Starting Task to Load HistData for all Symbols");
		Thread currentThread = null;
		int i=0;
		int max= TradeFXModel.StockHistData.entrySet().size();
		for (Map.Entry<Symbol, ArrayList<HistData>> entry : TradeFXModel.StockHistData.entrySet()) {
			i++;
			Symbol currentSymbol = entry.getKey();
			HistStockDataLoaderTask currenttask = new HistStockDataLoaderTask();
			TradeFXModel.tasks.put(currentSymbol, currenttask);
			currenttask.alSymbol = currentSymbol;
			currentThread = new Thread(currenttask);
			currentThread.start();
			updateMessage("Loading Hist Data for "+i);
			while (currentThread.isAlive()) {
				// System.out.print("-");
			}
			updateProgress(i, max);
			System.out.println(currentSymbol.toString() + "loaded");
		}
		this.updateMessage("Loading Hist Data finished");
		//Thread.sleep(3000);
		updateProgress(1, 1);
//		if (USE_SYNCRONIZE) {
//			while (currentThread.isAlive()) {
//				System.out.print("-");
//			}
//			TradeFXModel.histDataLoaded = true;
//		}
		return null;
	}
	

}

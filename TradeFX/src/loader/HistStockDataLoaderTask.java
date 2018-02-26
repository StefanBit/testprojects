package loader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import database.DAOHsqlImpl;
import gui.stage.ChartStage;
import gui.stage.StocksStage;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import model.HistData;
import model.Symbol;
import model.TradeFXModel;
import model.metrics.ArithmeticMean;

public class HistStockDataLoaderTask<T> extends Task{

	public Symbol alSymbol;
	

	@Override
	protected ArrayList<HistData> call() throws Exception {
		System.out.println("Start Task HistLoader");
		HistStockDataLoader l = new HistStockDataLoader();
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		cal.add(Calendar.YEAR, -1); // to get previous year add -1
		Date lastYear = cal.getTime();
		ArrayList<HistData> alHistData;
		alHistData = l.load(alSymbol, lastYear, new Date());

		System.out.println("Stop Task");
		DAOHsqlImpl<HistData> sHistData = new DAOHsqlImpl(HistData.class);
		ArrayList<HistData> al2;
		sHistData.deleteAll();
		sHistData.insertAll(alHistData);
		al2 = sHistData.getAllWhere(alSymbol.getPk().toString());
		updateProgress(1, 1);
		TradeFXModel.StockHistData.put(alSymbol, (ArrayList<HistData>) al2);
		return al2;
	}
	
	@Override
	protected void succeeded() {
		System.out.println("Fertig");
		TradeFXModel.StockHistData.put(alSymbol, (ArrayList<HistData>) this.getValue());
		super.succeeded();
	}
}

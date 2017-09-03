package application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import database.DAOHsqlImpl;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import model.HistData;
import model.Symbol;
import view.StocksStage;

public class HistStockDataLoaderTask<T> extends Task{

	public Symbol alSymbol;
	

	@Override
	protected ArrayList<HistData> call() throws Exception {
		HistStockDataLoader l = new HistStockDataLoader();
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		cal.add(Calendar.MONTH, -1); // to get previous year add -1
		Date lastYear = cal.getTime();
		ArrayList<HistData> alHistData;
		alHistData = l.load(alSymbol, lastYear, new Date());

		System.out.println("Stop Task");
		DAOHsqlImpl<HistData> sHistData = new DAOHsqlImpl(HistData.class);
		ArrayList<HistData> al2;
		sHistData.deleteAll();
		sHistData.insertAll(alHistData);
		al2 = sHistData.getAllWhere("0");
		updateProgress(1, 1);
		return al2;
	}
	
}

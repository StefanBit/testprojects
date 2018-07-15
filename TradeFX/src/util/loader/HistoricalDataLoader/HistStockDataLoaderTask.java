package util.loader.HistoricalDataLoader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import application.MyProperties;
import controller.TradeFXBusinessController;
import gui.CandleChart.ChartStage;
import gui.Table.StocksStage;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import model.HistData;
import model.MyArrayList;
import model.Symbol;
import model.TradeFXModel;
import model.metrics.ArithmeticMean;
import util.Log;
import util.database.DAOHsqlImpl;
import util.loader.Metric.MetricMapLoaderWorker;

public class HistStockDataLoaderTask<T> extends Task{

	Boolean RELOAD;
	Boolean DEBUG;
	
	public Symbol alSymbol;
	HistoricalDataFromAlphavantage histStockDataLoader;
	ArrayList<HistData> alHistData;
	ArrayList<HistData> al2;
	DAOHsqlImpl<HistData> sHistData;
	Calendar cal;
	Date today;
	
	public HistStockDataLoaderTask() {
		Log.info("Create Task HistStockDataLoaderTask");
		al2= new MyArrayList();
	}
	
	@Override
	protected ArrayList<HistData> call() throws Exception {
		DEBUG=true;
		//RELOAD=Boolean.valueOf(TradeFXBusinessController.getInstance().myProperties.getProperty("reload").toString());
		RELOAD=true;
		Log.info("Start Task HistStockDataLoaderTask");
		
		histStockDataLoader = new HistoricalDataFromAlphavantage();
		
		sHistData = new DAOHsqlImpl(HistData.class);
		cal = Calendar.getInstance();
		today = cal.getTime();
		//cal.add(Calendar.YEAR, -1); // to get previous year add -1
		cal.setTime(alSymbol.getFromDate());
		
		Log.info("Update dbfrom Web? "+RELOAD);
		
		if (RELOAD) updateDBFromWeb();
		getFromDB();
		 
		if (DEBUG) System.out.println("Stop Task");
		TradeFXModel.StockHistData.put(alSymbol, (ArrayList<HistData>) al2);
		updateProgress(1, 1);
		return al2;
	}
	
	void loadFromWeb(){
		Date lastYear;
		lastYear = cal.getTime();
		Log.info("Loading"+ alSymbol + "from Web between"+  lastYear+ " and "+ new Date());
		alHistData = histStockDataLoader.load(alSymbol, lastYear, new Date());
	}
	
	void updateDB(){
		//update Data
		Log.info("Clear Database");
		sHistData.deleteAllWhere(alSymbol);
		Log.info("Insert "+alHistData.size() +" values in Database") ;
		sHistData.insertAll(alHistData);
	}
	
	void getFromDB(){
	     // Get from DB
		al2 = sHistData.getAllWhere(alSymbol.getPk().toString());
	}
	
	
	void updateDBFromWeb(){
		if (DEBUG) System.out.println("UpdateDB from Web");	
			loadFromWeb();
			updateDB();
	}
	
	
	
	@Override
	protected void succeeded() {
		System.out.println("Fertig");
		TradeFXModel.StockHistData.put(alSymbol, (ArrayList<HistData>) this.getValue());
		super.succeeded();
	}
}

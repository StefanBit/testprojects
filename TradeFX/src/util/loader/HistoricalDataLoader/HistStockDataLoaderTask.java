package util.loader.HistoricalDataLoader;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import util.database.DAOHsqlImpl;
import util.loader.Metric.MetricMapLoaderWorker;
import util.log.Log;

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
		RELOAD=Boolean.valueOf(TradeFXBusinessController.getInstance().myProperties.getProperty("reload").toString());
		Log.info("Start Task HistStockDataLoaderTask");	
		Log.config("Update dbfrom Web? "+RELOAD);
		if (!isDbUpToDate()|| RELOAD) updateDBFromWeb();
		getFromDB();	 

		TradeFXModel.StockHistData.put(alSymbol, (ArrayList<HistData>) al2);
		updateProgress(1, 1);
		return al2;
	}
	
	void loadFromWeb(){
		histStockDataLoader = new HistoricalDataFromAlphavantage();
		Date lastYear;
		cal = Calendar.getInstance();
		today = cal.getTime();
		//cal.add(Calendar.YEAR, -1); // to get previous year add -1
		cal.setTime(alSymbol.getFromDate());
		lastYear = cal.getTime();
		Log.info("Loading "+ alSymbol + "from Web between"+  lastYear+ " and "+ new Date());
		alHistData = histStockDataLoader.load(alSymbol, lastYear, new Date());
		Log.info("loadFrormWeb Finished");
	}
	
	void updateDB(){
		Log.info("Update Database");
		sHistData = new DAOHsqlImpl(HistData.class);
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
	
	Boolean isDbUpToDate() {
		Boolean dbNotUpToDate;
		LocalDate dDbLastUpdate=null;
		String date=TradeFXBusinessController.getInstance().myProperties.getProperty("DbLastUpdate");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		dDbLastUpdate = LocalDate.parse(date, formatter);
		dbNotUpToDate = dDbLastUpdate.isBefore(LocalDate.now());
		Log.info("Last Database Update was "+dDbLastUpdate+", today is "+LocalDate.now()+" Outdatet? "+dbNotUpToDate);
		return !dbNotUpToDate;
	}
	
	void updateDBFromWeb(){
		
		Log.info("UpdateDB from Web");	
			loadFromWeb();
			updateDB();
			TradeFXBusinessController.getInstance().myProperties.setProperty("DbLastUpdate",LocalDate.now().toString());
			TradeFXBusinessController.getInstance().myProperties.safeProperties();
	}
	
	
	
	@Override
	protected void succeeded() {
		System.out.println("Fertig");
		TradeFXModel.StockHistData.put(alSymbol, (ArrayList<HistData>) this.getValue());
		super.succeeded();
	}
}

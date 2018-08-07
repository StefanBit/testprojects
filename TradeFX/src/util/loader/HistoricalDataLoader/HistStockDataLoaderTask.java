package util.loader.HistoricalDataLoader;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import controller.TradeFXBusinessController;
import javafx.concurrent.Task;
import model.HistData;
import model.MyArrayList;
import model.Symbol;
import model.TradeFXModel;
import util.database.DAOHsqlImpl;
import util.log.Log;

public class HistStockDataLoaderTask extends Task<ArrayList<HistData>> {

	Boolean RELOAD;

	public Symbol alSymbol;
	HistoricalDataFromAlphavantage histStockDataLoader;
	ArrayList<HistData> data;
	DAOHsqlImpl<HistData> sHistData;

	public HistStockDataLoaderTask() {
		Log.info("Create HistStockDataLoaderTask");
		RELOAD = Boolean.valueOf(TradeFXBusinessController.getInstance().myProperties.getProperty("reload").toString());
		data= new MyArrayList();
	}

	@Override
	protected ArrayList<HistData> call() throws Exception {
		Log.info("Start Task HistStockDataLoaderTask");
		sHistData = new DAOHsqlImpl(HistData.class);
		Log.config("Update dbfrom Web? " + RELOAD);
		if (!isDbUpToDate() || RELOAD) {
			Log.config("Update db from Web! ");
			updateDBFromWeb();
		}
		getFromDB();

		TradeFXModel.StockHistData.put(alSymbol, (ArrayList<HistData>) data);
		updateProgress(1, 1);
		Log.info("End Task HistStockDataLoaderTask");
		return data;
	}

	void loadFromWeb() {
		histStockDataLoader = new HistoricalDataFromAlphavantage();
		Date lastYear;
		Calendar cal;
		Date today;
		cal = Calendar.getInstance();
		today = cal.getTime();
		// cal.add(Calendar.YEAR, -1); // to get previous year add -1
		cal.setTime(alSymbol.getFromDate());
		lastYear = cal.getTime();
		Log.info("Loading " + alSymbol + "from Web between" + lastYear + " and " + new Date());
		data = histStockDataLoader.load(alSymbol, lastYear, new Date());
		// alHistData = histStockDataLoader.load(alSymbol);
		Log.info("loadFrormWeb Finished");
	}

	void updateDB() {
		Log.info("Update Database");
		Log.fine("Clear Database");
		sHistData.deleteAllWhere(alSymbol);
		sHistData.insertAll(data);
		Log.info("Insert " + data.size() + " values in Database");
	}

	// Get from DB
	void getFromDB() {
		data = sHistData.getAllWhere(alSymbol.getPk().toString());
	}

	Boolean isDbUpToDate() {
		Boolean dbNotUpToDate;
		LocalDate dDbLastUpdate = null;
		Log.info("Checking if DB ist Up to Date");
		String date = TradeFXBusinessController.getInstance().myProperties.getProperty("DbLastUpdate");
		Log.info("Last Database Update was " + date );
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		dDbLastUpdate = LocalDate.parse(date, formatter);
		dbNotUpToDate = dDbLastUpdate.isBefore(LocalDate.now());
		Log.info("Last Database Update was " + dDbLastUpdate  +" Today is "+ LocalDate.now()+" Outdatet? "	+ dbNotUpToDate);
		return !dbNotUpToDate;
	}

	void updateDBFromWeb() {

		Log.info("UpdateDB from Web");
		loadFromWeb();
		updateDB();
		TradeFXBusinessController.getInstance().myProperties.setProperty("DbLastUpdate", LocalDate.now().toString());
		TradeFXBusinessController.getInstance().myProperties.safeProperties();
	}

	@Override
	protected void succeeded() {
		System.out.println("Fertig");
		TradeFXModel.StockHistData.put(alSymbol, (ArrayList<HistData>) this.getValue());
		super.succeeded();
	}

}

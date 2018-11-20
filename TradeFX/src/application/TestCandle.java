package application;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import database.DAOHsqlImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import loader.HistStockDataLoader;
import model.HistData;
import model.MyArrayList;
import model.Symbol;
import stage.BarChartStage;
import stage.CandleStickChartStage;
import stage.MyTableStage;


public class TestCandle extends Application {
	
	
	BorderPane root;
	Scene scene;
	Stage stage;
	@Override
	public void start(Stage primaryStage) {

		Symbol symbol = new Symbol(0, "NASDAQ:MSFT");
			ArrayList<HistData> alHIstoricalData;
	    	DAOHsqlImpl<HistData> historicalDataLoader = new DAOHsqlImpl(HistData.class);
	    	//alHIstoricalData = historicalDataLoader.getAllWhere(symbol.getPk().toString());
	    	alHIstoricalData = historicalDataLoader.getAllWhere(symbol.getPk().toString()+" AND Date >= '2017-01-09' AND Date <= '2017-12-25'");
	    	MyArrayList alHIstoricalData2= new MyArrayList();
	    	for (HistData histData : alHIstoricalData) {
	    		alHIstoricalData2.add(histData);
			}
	    	
	    	alHIstoricalData2.update();
	    	// alHIstoricalData2.getAsSingleItem()
	    	//new MyTableStage(alHIstoricalData);
	    	new CandleStickChartStage( alHIstoricalData2).setTitle("HistData");
	    	//new BarChartStage( alHIstoricalData2);
	}

	public static void main(String[] args) {
		launch(args);
	}
}

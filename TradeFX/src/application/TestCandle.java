package application;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import controller.TradeFXController;
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
	
	

	CandleStickChartStage candleStickChartStage;
	@Override
	public void start(Stage primaryStage) {

		
			TradeFXController tfxc = new TradeFXController();
			Symbol symbol = new Symbol(0, "NASDAQ:MSFT");
			candleStickChartStage = new CandleStickChartStage();

			tfxc.init();
		
			candleStickChartStage.show(symbol);
			candleStickChartStage.setTitle("Graph");
			
			//ArrayList<HistData> alHIstoricalData;
	    	//DAOHsqlImpl<HistData> historicalDataLoader = new DAOHsqlImpl(HistData.class);
	    	
	    	//alHIstoricalData = historicalDataLoader.getAllWhere(symbol.getPk().toString());
	    	//alHIstoricalData = historicalDataLoader.getAllWhere(symbol.getPk().toString()+" AND Date >= '2017-01-09' AND Date <= '2017-12-25'");
	    	
//	    	alHIstoricalData = tfxc.getModel().getHistDataFor(symbol);
//	    	
//	    	System.out.println(alHIstoricalData.size());
//	    	
//	    	MyArrayList alHIstoricalData2= new MyArrayList();
//	    	for (HistData histData : alHIstoricalData) {
//	    		alHIstoricalData2.add(histData);
//			}
	    	
	    	//alHIstoricalData2.update();
	    	// alHIstoricalData2.getAsSingleItem()
	    	//  	new MyTableStage(alHIstoricalData,HistData.class );
	    	//new CandleStickChartStage(alHIstoricalData2).setTitle("HistData");
	    	
	    	
	    	//new BarChartStage( alHIstoricalData2);
	}

	public static void main(String[] args) {
		launch(args);
	}
}

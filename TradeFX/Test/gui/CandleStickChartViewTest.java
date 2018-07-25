package gui;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

import application.util.loader.HistoricalData.HistoricalDataLoaderTest;
import gui.CandleChart.CandleStickChartView;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.HistData;
import model.Symbol;
import model.TradeFXModel;
import util.loader.HistoricalDataLoader.HistStockDataLoaderTask;
import util.loader.HistoricalDataLoader.HistoricalDataFromAlphavantage;
import util.loader.HistoricalDataLoader.HistoricalDataFromRandom;
import util.loader.HistoricalDataLoader.IHistoricalDataLoader;
import util.log.Log;

public class CandleStickChartViewTest extends Application {

	
	Symbol s = new Symbol();
	
	LocalDate startLocalDate,endLocalDate;
	Date startDate,endDate;
	int diff;
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		System.setProperty( "java.util.logging.config.file", "C:\\Users\\sbili\\git\\testprojects\\TradeFX\\src\\util\\log\\logging.properties" );

		CandleStickChartView node;
		node = new CandleStickChartView();
		
		LocalDate myDate=LocalDate.now();
		LocalDate fromDate = myDate.minusDays(365);
		Date from= Date.from(fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Log.info(this.getClass().getSimpleName()+": from"+from);
		
		s = new Symbol(
				1,
				"MSFT",
				from
				);
		
		
		HistStockDataLoaderTask currenttask = new HistStockDataLoaderTask();
		currenttask.alSymbol = s;
		
		ExecutorService executorService1 = Executors.newSingleThreadExecutor();
		
		executorService1.submit(currenttask);
		
		node.setDataSeries(currenttask.get());
		executorService1.shutdown();
		
		Scene scene = new Scene(node, 500, 500, Color.BLACK);
	       stage.setTitle("JavaFX Scene Graph Demo");
	       stage.setScene(scene);
	       stage.show();
	       
	       
	       
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}
}

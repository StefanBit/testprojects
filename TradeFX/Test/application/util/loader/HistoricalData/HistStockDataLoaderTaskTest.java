package application.util.loader.HistoricalData;



import java.lang.Thread.UncaughtExceptionHandler;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.LogManager;

import org.junit.Rule;
import org.junit.runner.RunWith;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.MyArrayList;
import model.Symbol;
import util.loader.HistoricalDataLoader.HistStockDataLoaderTask;
import util.log.Log;






public class HistStockDataLoaderTaskTest extends Application {

	Symbol currentSymbol;
	LocalDate fromDate;
	Thread currentThread;
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	
	public void start(Stage stage) throws Exception {
		new HistStockDataLoaderTaskTest();    
    }
	
	public HistStockDataLoaderTaskTest() {
		System.setProperty( "java.util.logging.config.file", "C:\\Users\\sbili\\git\\testprojects\\TradeFX\\src\\util\\log\\logging.properties" );

		try { LogManager.getLogManager().readConfiguration(); }
		catch ( Exception e ) { e.printStackTrace(); }
		LocalDate myDate=LocalDate.now();
		fromDate= myDate.minusDays(100);
		System.out.println(fromDate);
		Log.fine("test");
		Date from= Date.from(fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		System.out.println(from);
		
		currentSymbol = new Symbol(
				1,
				"MSFT",
				from
				);
		
		Log.config(currentSymbol.toString());
		MyArrayList al2 = new MyArrayList();
		HistStockDataLoaderTask currenttask;
		currenttask = new HistStockDataLoaderTask();
		currenttask.alSymbol = currentSymbol;
		currentThread = new Thread(currenttask);
		currentThread.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println(",kmlökj"+e);
			}
		});
		currentThread.start();
		
		while (currentThread.isAlive()) {
			
		}
		
		Log.info("Finished"+currenttask.getValue());
	}



}

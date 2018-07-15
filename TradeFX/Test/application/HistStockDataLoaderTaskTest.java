package application;



import java.lang.Thread.UncaughtExceptionHandler;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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
		
		LocalDate myDate=LocalDate.now();
		fromDate= myDate.minusDays(100);
		System.out.println(fromDate);
		Date from= Date.from(fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		System.out.println(from);
		currentSymbol = new Symbol(
				1,
				"MSFT",
				from
				);
		
		System.out.println(currentSymbol);
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
		
		System.out.print("Finished"+currenttask.getValue());
	}



}

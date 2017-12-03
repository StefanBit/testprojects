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


public class TestTable extends Application {
	
	
	BorderPane root;
	Scene scene;
	Stage stage;
	@Override
	public void start(Stage primaryStage) {
			
			ArrayList alData;
	    	DAOHsqlImpl<Symbol> historicalDataLoader = new DAOHsqlImpl(Symbol.class);
	    	alData = new ArrayList();
	    	alData = historicalDataLoader.getAll();
	    	new MyTableStage(alData, Symbol.class);
			
	    	ArrayList alData2;
	    	DAOHsqlImpl<HistData> historicalDataLoader2 = new DAOHsqlImpl(HistData.class);
	    	alData2 = new ArrayList();
	    	alData2 = historicalDataLoader2.getAll();
	    	System.out.println(alData2.size());
	    	
	    		    	new MyTableStage(alData2, HistData.class);
	    	
	}

	public static void main(String[] args) {
		launch(args);
	}
}

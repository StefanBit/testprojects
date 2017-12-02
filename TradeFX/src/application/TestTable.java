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

			ArrayList<Symbol> alHIstoricalData;
	    	DAOHsqlImpl<Symbol> historicalDataLoader = new DAOHsqlImpl(Symbol.class);
	    	alHIstoricalData = new ArrayList();
	    	alHIstoricalData = historicalDataLoader.getAll();
	    	new MyTableStage(alHIstoricalData, Symbol.class);
	    	
	}

	public static void main(String[] args) {
		launch(args);
	}
}

package application;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import controller.TradeFXBusinessController;
import gui.BarChart.BarChartStage;
import gui.CandleChart.CandleStickChartStage;
import gui.Console.ConsoleStage;
import gui.Table.MyTableStage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.HistData;
import model.MyArrayList;
import model.Symbol;
import util.database.DAOHsqlImpl;
import util.loader.HistoricalData.HistStockDataLoader;


public class TestTable extends Application {
	
	
	BorderPane root;
	Scene scene;
	Stage stage;
	@Override
	public void start(Stage primaryStage) {
															// Variable definition
			ArrayList alSymbolData, alHistData;
			DAOHsqlImpl<Symbol> dataLoader;
			ConsoleStage console;
															// Variable initialisation
			alSymbolData = new ArrayList();
			alHistData = new ArrayList();
		//	console = new ConsoleStage();
															//
//			
//			TradeFXController tfxc = new TradeFXController();
//			Symbol symbol = new Symbol(0, "NASDAQ:MSFT");
//
//			tfxc.init();
			
			
			
			dataLoader = new DAOHsqlImpl(Symbol.class);
	    	alSymbolData = dataLoader.getAll();
	    	dataLoader = new DAOHsqlImpl(HistData.class);
	    	alHistData = dataLoader.getAll();
//	    	//System.out.println("alHi"+alHistData.get(0));
	    	
	    	
	    	// Open TableStages and show 
	    	new MyTableStage(alSymbolData, Symbol.class);
	    	new MyTableStage(alHistData, HistData.class);
	}

	public static void main(String[] args) {
		launch(args);
	}
}

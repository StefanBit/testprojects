package application;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import database.DAOHsqlImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import loader.HistStockDataLoader;
import model.HistData;
import model.MyArrayList;
import model.Symbol;
import model.TradeFXModel;
import stage.BarChartStage;
import stage.CandleStickChartStage;
import stage.MyTableStage;
import view.MyTablePane;


public class TestStocksStage extends Application {
	
	
	BorderPane root;
	Scene scene;
	Stage stage;
	@Override
	public void start(Stage primaryStage) {
		
		ArrayList<Symbol> alSymbols;
		DAOHsqlImpl<Symbol> loader = new DAOHsqlImpl(Symbol.class);
		alSymbols = loader.getAll();
		new MyTableStage(alSymbols,new Symbol());
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}

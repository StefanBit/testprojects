package view;
import java.util.ArrayList;

import database.DAOHsqlImpl;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.HistData;
import model.Symbol;
import model.TradeFXModel;
import stage.CandleStickChartStage;
import stage.MainStage;

public class Test extends Application {
	
	
	BorderPane root;
	Scene scene;
	Stage stage;
	@Override
	public void start(Stage primaryStage) {

			ArrayList<HistData> alHIstoricalData;
	    	Symbol symbol = new Symbol(0, "NASDAQ:MSFT");
	    	DAOHsqlImpl<HistData> historicalDataLoader = new DAOHsqlImpl(HistData.class);
	    	alHIstoricalData= historicalDataLoader.getAllWhere(symbol.getPk().toString());	    	
	    	new CandleStickChartStage(alHIstoricalData).setTitle("HistData");
	}

	public static void main(String[] args) {
		launch(args);
	}
}

package application;

import java.util.ArrayList;
import java.util.Map;

import controller.TradeFXController;
import database.DAOHsqlImpl;
import javafx.application.Application;
import javafx.stage.Stage;
import model.HistData;
import model.MyArrayList;
import model.Symbol;
import model.TradeFXModel;
import stage.BarChartStage;

public class TestBarChart extends Application {

	
	
	ArrayList<HistData> alHIstoricalData;

	public void start(Stage primaryStage) {

		TradeFXController tfxc= new TradeFXController();
		tfxc.init();

		while (!(TradeFXModel.histDataLoaded))
		{
			
		}
		System.out.println("ready");

		new BarChartStage();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}

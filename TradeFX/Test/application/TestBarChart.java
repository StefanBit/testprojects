package application;

import java.util.ArrayList;
import java.util.Map;

import controller.TradeFXBusinessController;
import database.DAOHsqlImpl;
import gui.stage.BarChartStage;
import javafx.application.Application;
import javafx.stage.Stage;
import model.HistData;
import model.MyArrayList;
import model.Symbol;
import model.TradeFXModel;

public class TestBarChart extends Application {

	
	
	ArrayList<HistData> alHIstoricalData;

	public void start(Stage primaryStage) {

		TradeFXBusinessController tfxc= new TradeFXBusinessController();
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

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
		
		ArrayList<HistData> alHIstoricalData;
		alHIstoricalData = TradeFXModel.getHistDataFor(new Symbol(0,"MSFT"));
		MyArrayList alHIstoricalData2 = new MyArrayList();
		for (HistData histData : alHIstoricalData) {
			alHIstoricalData2.add(histData);
		}
		alHIstoricalData2.update();

		new BarChartStage(alHIstoricalData2);
		
		
		//System.exit(0);
	}

	public static void main(String[] args) {
		launch(args);
	}
}

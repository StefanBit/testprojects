package application;

import java.util.ArrayList;

import database.DAOHsqlImpl;
import javafx.application.Application;
import javafx.stage.Stage;
import model.HistData;
import model.MyArrayList;
import model.Symbol;
import stage.BarChartStage;

public class TestBarChart extends Application {

	public void start(Stage primaryStage) {

		Symbol symbol = new Symbol(0, "NASDAQ:MSFT");
		ArrayList<HistData> alHIstoricalData;
		DAOHsqlImpl<HistData> historicalDataLoader = new DAOHsqlImpl(HistData.class);

		alHIstoricalData = historicalDataLoader.getAllWhere(symbol.getPk().toString() + " AND Date >= '2017-01-09' AND Date <= '2017-12-25'");
		System.out.println("ll"+alHIstoricalData.size());

		MyArrayList alHIstoricalData2 = new MyArrayList();
		for (HistData histData : alHIstoricalData) {
			alHIstoricalData2.add(histData);
		}
		alHIstoricalData2.update();
		System.out.println(alHIstoricalData2.getAsSingleItem());
		new BarChartStage(alHIstoricalData2);

	}

	public static void main(String[] args) {
		launch(args);
	}
}

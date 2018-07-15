package application;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import gui.CandleChart.CandleStickChartView;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Symbol;
import util.loader.HistoricalDataLoader.HistoricalDataFromAlphavantage;
import util.loader.HistoricalDataLoader.HistoricalDataFromRandom;
import util.loader.HistoricalDataLoader.IHistoricalDataLoader;

public class CandleStickChartViewTest extends Application {

	
	Symbol s = new Symbol();
	
	LocalDate startLocalDate,endLocalDate;
	Date startDate,endDate;
	int diff;
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		CandleStickChartView node;
		node = new CandleStickChartView();
		IHistoricalDataLoader loader =  new HistoricalDataFromAlphavantage();
		//IHistoricalDataLoader loader =  new HistoricalDataFromRandom();
		//Symbol
		Calendar cal = Calendar.getInstance();
		endDate=cal.getTime();
		cal.add(Calendar.YEAR, -1);
		startDate =cal.getTime();
		s.setName("MSFT");
		s.setPk(1);
		s.setFromDate(endDate);

	    node.setDataSeries(loader.load(s, startDate, endDate));
	
		Scene scene = new Scene(node, 500, 500, Color.BLACK);
	       stage.setTitle("JavaFX Scene Graph Demo");
	       stage.setScene(scene);
	       stage.show();
	       
	       
	       
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}
}

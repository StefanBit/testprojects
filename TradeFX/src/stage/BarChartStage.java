package stage;

import java.util.ArrayList;
import java.util.Map;

import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.HistData;
import model.MyArrayList;
import model.Symbol;
import model.TradeFXModel;
import view.CandleStickChartView;

public class BarChartStage extends Stage{
	
	

    /*
     * Shows a BarChart with a number of Stock Measures
     * @param ArrayList  
     */
	
	public BarChartStage(MyArrayList data) {
		    setTitle("Bar Chart Sample");
		    final String candleStickChartCss =  getClass().getResource("CandleStickChart.css").toExternalForm();
		     
	        final NumberAxis xAxis = new NumberAxis();
	        final CategoryAxis yAxis = new CategoryAxis();
	        final BarChart<Number,String> bc = new BarChart<>(xAxis,yAxis);
	        bc.setTitle("Performance");
	        bc.getStylesheets().add(candleStickChartCss);
	        xAxis.setLabel("Percentage");  
	        xAxis.setTickLabelRotation(90);
	        yAxis.setLabel("Name");        
        

	        XYChart.Series series1 = new XYChart.Series();
	        series1.setName("Year");       
	        XYChart.Series series2 = new XYChart.Series();
	        series2.setName("Month");
	        
	        MyArrayList alHIstoricalData2;
	        HistData d=null;
	        for (Symbol s : TradeFXModel.StockSymbols) {
	        	alHIstoricalData2 = new MyArrayList();
				for (HistData histData :TradeFXModel.getHistDataFor(s)) {
					alHIstoricalData2.add(histData);
				}
				alHIstoricalData2.update();
				d= alHIstoricalData2.getAsSingleItem().get(0);
				series1.getData().add(new XYChart.Data(d.getClose()/d.getOpen()*100, s.getName()));
				series2.getData().add(new XYChart.Data(d.getClose()/d.getOpen()*100, s.getName()));
			}
	        

	        Scene scene  = new Scene(bc,300,400);
	        bc.getData().addAll(series1, series2);
	        setScene(scene);
	        show();
	}
	
	
	
}

package gui.stage;

import java.util.ArrayList;
import java.util.Map;

import gui.view.CandleStickChartView;
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
import model.metrics.FloatingMean;

public class BarChartStage extends Stage{
	
	

    /*
     * Shows a BarChart with a number of Stock Measures
     * @param ArrayList  
     */
	
	public BarChartStage() {
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
	        series1.setName("Week");       
	        XYChart.Series series2 = new XYChart.Series();
	        series2.setName("Month");
	        XYChart.Series series3 = new XYChart.Series();
	        series3.setName("100Day");
	        XYChart.Series series4 = new XYChart.Series();
	        series4.setName("270Day");
	        
	        MyArrayList alHIstoricalData2, p365;
	        HistData d=null;
	        
	        FloatingMean fm=new FloatingMean(50);
	        int len;
	        
	        for (Symbol s : TradeFXModel.StockSymbols) {
	        	alHIstoricalData2 = new MyArrayList();
				for (HistData histData :TradeFXModel.getHistDataFor(s)) {
					alHIstoricalData2.add(histData);
				}
				alHIstoricalData2.update();
				len=alHIstoricalData2.size();
				series1.getData().add(new XYChart.Data(alHIstoricalData2.get(len-1).getOpen()/alHIstoricalData2.get(len-7).getOpen()-1, s.getName()));
				series2.getData().add(new XYChart.Data(alHIstoricalData2.get(len-1).getOpen()/alHIstoricalData2.get(len-30).getOpen()-1, s.getName()));
				series3.getData().add(new XYChart.Data(alHIstoricalData2.get(len-1).getOpen()/alHIstoricalData2.get(len-100).getOpen()-1, s.getName()));
				series4.getData().add(new XYChart.Data(alHIstoricalData2.get(len-1).getOpen()/alHIstoricalData2.get(len-170).getOpen()-1, s.getName()));
				System.out.println(alHIstoricalData2.get(len-1).getDate()+"/"+alHIstoricalData2.get(len-30).getDate());
			}
	        

	        Scene scene  = new Scene(bc,300,400);
	        bc.getData().addAll(series1, series2,series3,series4);
	        setScene(scene);
	        show();
	}
	
	
	
}

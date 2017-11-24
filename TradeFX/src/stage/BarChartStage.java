package stage;

import java.util.ArrayList;

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
import view.CandleStickChartView;

public class BarChartStage extends Stage{
	
	
	
    final static String austria = "Austria";
    final static String brazil = "Brazil";
    final static String france = "France";
    final static String italy = "Italy";
    final static String usa = "USA";
    
	public BarChartStage(MyArrayList data) {
		    setTitle("Bar Chart Sample");
		    final String candleStickChartCss =  getClass().getResource("CandleStickChart.css").toExternalForm();
		     
	        final NumberAxis xAxis = new NumberAxis();
	        final CategoryAxis yAxis = new CategoryAxis();
	        final BarChart<Number,String> bc = 
	            new BarChart<>(xAxis,yAxis);
	        bc.setTitle("Performance");
	        bc.getStylesheets().add(candleStickChartCss);
	        xAxis.setLabel("Percentage");  
	        xAxis.setTickLabelRotation(90);
	        yAxis.setLabel("Name");        
	        HistData d= data.getAsSingleItem().get(0);
	        
	        XYChart.Series series1 = new XYChart.Series();
	        series1.setName("Rate");       
	        series1.getData().add(new XYChart.Data(d.getClose()/d.getOpen()*100, String.valueOf(d.getPk())));
	        series1.getData().add(new XYChart.Data(-d.getClose()/d.getOpen()*100, String.valueOf(d.getPk()+1)));
//	        
//	        XYChart.Series series2 = new XYChart.Series();
//	        series2.setName("2004");
//	        series2.getData().add(new XYChart.Data(57401.85, austria));
//	        series2.getData().add(new XYChart.Data(41941.19, brazil));
//	        series2.getData().add(new XYChart.Data(45263.37, france));
//	        series2.getData().add(new XYChart.Data(117320.16, italy));
//	        series2.getData().add(new XYChart.Data(14845.27, usa));  
//	        
//	        XYChart.Series series3 = new XYChart.Series();
//	        series3.setName("2005");
//	        series3.getData().add(new XYChart.Data(45000.65, austria));
//	        series3.getData().add(new XYChart.Data(44835.76, brazil));
//	        series3.getData().add(new XYChart.Data(18722.18, france));
//	        series3.getData().add(new XYChart.Data(17557.31, italy));
//	        series3.getData().add(new XYChart.Data(92633.68, usa));  
//	        
	        Scene scene  = new Scene(bc,300,400);
	        //, series2, series3
	        bc.getData().addAll(series1);
	        setScene(scene);
	        show();

	}
}

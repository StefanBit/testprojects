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
	        HistData d= data.getAsSingleItem().get(0);
	        System.out.println();
	        XYChart.Series series1 = new XYChart.Series();
	        series1.setName("Rate");       
	        series1.getData().add(new XYChart.Data(d.getClose()/d.getOpen()*100, String.valueOf("MSFT"+d.getPk())));
	        series1.getData().add(new XYChart.Data(-d.getClose()/d.getOpen()*100, String.valueOf("-MSFT"+d.getPk()+1)));
	        
	        XYChart.Series series2 = new XYChart.Series();
	        series2.setName("2004");
	        series2.getData().add(new XYChart.Data(d.getClose()/d.getOpen()*100, String.valueOf("MSFT"+d.getPk())));
	        series2.getData().add(new XYChart.Data(-d.getClose()/d.getOpen()*100, String.valueOf("-MSFT"+d.getPk()+1)));


	        Scene scene  = new Scene(bc,300,400);
	        //, series2, series3
	        bc.getData().addAll(series1,series2);
	        setScene(scene);
	        show();

	}
}

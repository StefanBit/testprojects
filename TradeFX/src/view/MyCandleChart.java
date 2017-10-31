package view;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

public class MyCandleChart<String, Number>  extends LineChart{

	public MyCandleChart(Axis xAxis, Axis yAxis) {
		super(xAxis, yAxis);
	}
	
	
}

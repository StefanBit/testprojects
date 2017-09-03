package view;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import model.HistData;

public class ChartView implements ChangeListener{
	final CandleChart<String, Number> lineChart;
	int x=0;

	public ChartView() {
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Month");
		
		yAxis.setForceZeroInRange(false);
		lineChart = new CandleChart<String, Number>(xAxis, yAxis);
		
	}

	public void setData(ArrayList<HistData> data) {
		lineChart.getData().add(getDataSeriesClose(data));
		
	}
	
	public XYChart.Series getDataSeriesClose(ArrayList<HistData> data){
		double mid;
		
		XYChart.Series series = new XYChart.Series();
		XYChart.Data dat;
		series.setName(String.valueOf(data.get(0).getPk()));
		int scale=data.size()/50;
		for (int i = 0; i < data.size(); i=i+scale) {
			//mid=(data.get(i).open+data.get(i).close)/2;
			
			dat = new XYChart.Data(data.get(i).getDate().toLocaleString(), data.get(i).getClose());
			//System.out.println(series.chartProperty());
			//CandleNode r= new CandleNode(data.get(i,i+1));
			
			//dat.setNode(r);
			series.getData().add(dat);
		}
		
		return series;
	}

	@Override
	public void changed(ObservableValue arg0, Object arg1, Object arg2) {
		
		
		
	}
	
}

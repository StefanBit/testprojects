package view;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import model.HistData;

public class CandleStickChartView {
	
	public CandleStickChart lineChart;
	int x=0;

	public CandleStickChartView() {
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Date");
		yAxis.setLabel("Value");
		yAxis.setForceZeroInRange(false);
		lineChart = new CandleStickChart(xAxis, yAxis);

	}

	public void setData(ArrayList<HistData> data) {
		setDataSeries(data);
		lineChart.setTitle("MSFT");
	}
	
	public XYChart.Series setDataSeries(ArrayList<HistData> data){
		double mid;
		
		XYChart.Series series = new XYChart.Series();
		XYChart.Data dat;
		series.setName(String.valueOf(data.get(0).getPk()));

		 for (int i=0; i< data.size(); i++) {
	            HistData day = data.get(i);
	            final CandleStickExtraValues extras =new CandleStickExtraValues(day.getOpen(),day.getHight(),day.getLow(),day.getClose());
	            series.getData().add(new XYChart.Data<String,Number>((String) day.getDate().toString(),(double) day.getOpen(),extras));
	        }
		 ObservableList<XYChart.Series<String, Number>> data1 = lineChart.getData();
		  if (data1 == null) {
	            data1 = FXCollections.observableArrayList(series);
	            lineChart.setData(data1);
	        } else {
	        	lineChart.getData().add(series);
	        }
		
		return series;
	}
}

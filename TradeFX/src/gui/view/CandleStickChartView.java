package gui.view;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import model.ArithmeticMean;
import model.FloatingMean;
import model.HistData;
import model.Symbol;
import model.TradeFXModel;

public class CandleStickChartView {

	public CandleStickChart lineChart;
	private int dataSetsToShow;
	
	
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
		dataSetsToShow=data.size();
		lineChart.setTitle("MSFT");
	}
	
	public void setDataForSymbol(Symbol symbol) {
		ArrayList<HistData> data;
		data= TradeFXModel.getHistDataFor(symbol);
		dataSetsToShow=data.size();
		setDataSeries(data);
		lineChart.setTitle(symbol.getName());
	}

	public XYChart.Series setDataSeries(ArrayList<HistData> data) {

		XYChart.Series series = new XYChart.Series();
		XYChart.Series series2 = new XYChart.Series();
		XYChart.Data dat;
		//series.setName(String.valueOf(data.get(0).getPk()));
		series.setName("Chart");

		for (int i = 0; i < dataSetsToShow; i++) {
			HistData day = data.get(i);
			final CandleStickExtraValues extras = new CandleStickExtraValues(day.getClose(), day.getHight(),
					day.getLow(), (day.getHight() + day.getLow()) / 2);
			series.getData().add(new XYChart.Data<String, Number>((String) day.getDate().toString(),(double) day.getOpen(), extras));
		}
		ObservableList<XYChart.Series<String, Number>> data1 = lineChart.getData();
		if (data1 == null) {
			data1 = FXCollections.observableArrayList(series);
			lineChart.setData(data1);
		} else {
			lineChart.getData().add(series);
		}

		series2.setName("Floating Mean");
		//series2.setName(String.valueOf(data.get(0).getPk()));
		FloatingMean fm = new FloatingMean();
		data=fm.calc(data,300);
		for (int i = 0; i < dataSetsToShow; i++) {
			HistData day = data.get(i);
			final CandleStickExtraValues extras2 = null; //new CandleStickExtraValues(day.getClose() - 10, day.getHight() - 10,day.getLow() - 10, (day.getHight() + day.getLow()) / 2 - 10);
			series2.getData().add(new XYChart.Data<String, Number>((String) day.getDate().toString(),(double) day.getOpen(),null));
		}
		ObservableList<XYChart.Series<String, Number>> data2 = lineChart.getData();
		if (data2 == null) {
			data2 = FXCollections.observableArrayList(series2);
			lineChart.setData(data2);
		} else {
			lineChart.getData().add(series2);
		}
		return series;
	}
}

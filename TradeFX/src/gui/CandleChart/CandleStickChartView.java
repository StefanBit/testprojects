package gui.CandleChart;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import controller.TradeFXBusinessController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.HistData;
import model.Symbol;
import model.TradeFXModel;
import model.metrics.ArithmeticMean;
import model.metrics.FloatingMean;
import model.metrics.IMetric;
import util.loader.Metric.MetricLoader;

public class CandleStickChartView {

	public CandleStickChart lineChart;
	private int dataSetsToShow;
	int offsetToShow=400;
	int offsetFromShow=100;
	TradeFXModel model;
	Symbol symbol;
	Boolean DEBUG=true;

	public CandleStickChartView() {
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Date");
		yAxis.setLabel("Value");
		yAxis.setForceZeroInRange(false);
		lineChart = new CandleStickChart(xAxis, yAxis);
		model = TradeFXBusinessController.getInstance().getModel();
	}

//	public void setData(ArrayList<HistData> data) {
//		setDataSeries(data);
//		dataSetsToShow = data.size();
//	}

	public void setDataForSymbol(Symbol symbol) {
		ArrayList<HistData> data;
		this.symbol=symbol;
		data = TradeFXModel.getHistDataFor(symbol);
		dataSetsToShow = data.size();
		setDataSeries(data);
		IMetric iml3;
		MetricLoader metricLoader;
		if (DEBUG) System.out.println("Model.items :" + model.MetricLoaderMaps);

		// Legede
		for (Map<String, Object> item : model.MetricLoaderMaps) {
			if (item.get("Symbol").equals(symbol.getName())) {
				for (IMetric metric : model.aMetrics) {
					metricLoader = (MetricLoader) item.get(metric.getClass().getSimpleName());
//					iml3 = metricLoader.metric.getInstance();
					iml3 = metricLoader.metric;
					//Random r = new Random();
					lineChart.paintingColor = iml3.getColor();
					setAdditonalDataSeries(data, iml3);
				}
			}
		}

		lineChart.setTitle(symbol.getName());
	}
	
	
	int getPositionOfDate(ArrayList<HistData> data, Date date){
		int pos=0;
		for (int i=0 ; i<data.size();i++) {
			if (data.get(i).getDate().before(date)){	
				pos=i;
			}
		}
		return pos;
	}

	// Candlestick Line
	public XYChart.Series setDataSeries(ArrayList<HistData> data) {
		XYChart.Series series = new XYChart.Series();
		XYChart.Data dat;
		series.setName("Chart");
		
		if (DEBUG) System.out.println("Days to Show from "+this.symbol.getFromDate()+" to "+data.get(data.size()-1).getDate()+ " diff: "+getPositionOfDate(data,this.symbol.getFromDate()));
		offsetFromShow=getPositionOfDate(data,this.symbol.getFromDate());
		for (int i = offsetFromShow; i < dataSetsToShow; i++) {
			HistData day = data.get(i);
			final CandleStickExtraValues extras = new CandleStickExtraValues(day.getClose(), day.getHight(),
					day.getLow(), (day.getHight() + day.getLow()) / 2);
			series.getData().add(new XYChart.Data<String, Number>((String) day.getDate().toString(),
					(double) day.getOpen(), extras));
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

	// Some other Line
	public void setAdditonalDataSeries(ArrayList<HistData> data, IMetric metric) {
		XYChart.Series additionalSeries = new XYChart.Series();
		additionalSeries.setName(metric.getClass().getSimpleName());

		//data = metric.calc(data);
		data = metric.getData();
		
		dataSetsToShow = data.size();
		for (int i = offsetFromShow; i < dataSetsToShow; i++) {
			HistData day = data.get(i);
			final CandleStickExtraValues extraValues = null;
			additionalSeries.getData().add(
					new XYChart.Data<String, Number>((String) day.getDate().toString(), (double) day.getOpen(), null));
		}
		ObservableList<XYChart.Series<String, Number>> lineChartData = lineChart.getData();
		if (lineChartData == null) {
			lineChartData = FXCollections.observableArrayList(additionalSeries);
			lineChart.setData(lineChartData);
		} else {
			lineChart.getData().add(additionalSeries);
		}
	}

}

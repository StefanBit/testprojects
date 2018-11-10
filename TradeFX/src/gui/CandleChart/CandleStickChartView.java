package gui.CandleChart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import controller.TradeFXBusinessController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.HistData;
import model.Symbol;
import model.TradeFXModel;
import model.metrics.ArithmeticMean;
import model.metrics.DatalineContainer;
import model.metrics.FloatingMean;
import model.metrics.IDataline;
import model.metrics.IMetric;
import util.loader.Metric.MetricLoader;
import util.log.Log;

public class CandleStickChartView extends BorderPane {

	public CandleStickChart chart;
	private int dataSetsToShow;
	int offsetToShow = 0;
	int offsetFromShow = 0;
	TradeFXModel model;
	Symbol symbol;
	Boolean DEBUG = true;
	BorderPane bp;
	Slider startSlider;
	ArrayList<HistData> data;
	Symbol currentSymbol;

	public CandleStickChartView() {
		model = TradeFXBusinessController.getInstance().getModel();
		bp = new BorderPane();
		initChart();
		initStartSlider();
		this.setCenter(chart);
		this.setBottom(startSlider);
	}

	private void initStartSlider() {
		this.startSlider = new Slider();
		this.startSlider.valueProperty().addListener(new ItemsToShowChangeListener());
		startSlider.setValue(0);
	}

	private void initChart() {
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Date");
		yAxis.setLabel("Value");
		yAxis.setForceZeroInRange(false);
		this.chart =  new CandleStickChart(xAxis, yAxis);
	}

	public void setDataForSymbol(Symbol symbol) {
		ArrayList<HistData> data;
		DatalineContainer datalinecontainer;
		MetricLoader metricLoader;
		chart.setTitle(symbol.getName());
		this.symbol = symbol;
		data = TradeFXModel.getHistDataFor(symbol);
		dataSetsToShow = data.size();
		startSlider.setMax(dataSetsToShow);

		datalinecontainer = new DatalineContainer();
		datalinecontainer.setData(data);
		datalinecontainer.setName("Chart");
		datalinecontainer.setType("Candlestick");
		populateSeries(datalinecontainer);
		// Legede
		for (Map<String, Object> mMetricLoader : model.MetricLoaderMaps) {
			if (mMetricLoader.get("Symbol").equals(symbol.getName())) {
				for (IMetric metric : model.aMetrics) {
					metricLoader = (MetricLoader) mMetricLoader.get(metric.getClass().getSimpleName());
					datalinecontainer = new DatalineContainer();
					datalinecontainer.setData(metricLoader.metric.getData());
					datalinecontainer.setName(metric.getClass().getSimpleName());
					datalinecontainer.setType("Flat");
					chart.paintingColor = metric.getColor();
					populateSeries(datalinecontainer);
				}
			}
		}
		//System.out.println("!"+chart.getData());
	}

	public void populateSeries(DatalineContainer dataline) {
		HistData day;
		String name;
		ArrayList<HistData> data = dataline.getData();
		name = dataline.getName();
		CandleStickExtraValues extraValues;
		XYChart.Series series = new XYChart.Series();
		dataSetsToShow = data.size();
		series.setName(name);
		for (int i = offsetFromShow; i < dataSetsToShow; i++) {
			day = data.get(i);
			extraValues = null;
			if (dataline.getType().equals("Candlestick")) {
				extraValues = new CandleStickExtraValues(day);
			}
			if (dataline.getType().equals("Flat")) {
				extraValues = null;
			}
			addDayToDataSerie(data.get(i), series, extraValues);
		}
		addSeries(series);
	}


	private void addSeries(Series series) {
		String name = series.getName();
		ObservableList<XYChart.Series<String, Number>> chartDatalines = chart.getData();
		if (chartDatalines == null) {
			chartDatalines = FXCollections.observableArrayList(series);
			chart.setData(chartDatalines);
		} else {
			// Serie Austauschen
			for (int i = 0; i < chartDatalines.size(); i++) {
				if (chartDatalines.get(i).getName().equals(name)) {
					//System.out.println("Add Serie " + chartDatalines.get(i).getData());
					chartDatalines.remove(i);
				}
			}
			chartDatalines.add(series);
		}
	}

	void addDayToDataSerie(HistData day, XYChart.Series series, CandleStickExtraValues extras) {
		series.getData().add(new XYChart.Data<String, Number>(new SimpleDateFormat("yy-MM-dd").format(day.getDate()),
				(double) day.getOpen(), extras));
	}

	class ItemsToShowChangeListener implements ChangeListener {
		@Override
		public void changed(ObservableValue observable, Object oldValue, Object newValue) {
			offsetFromShow = ((Double) newValue).intValue();
			// setDataSeries(data);
			setDataForSymbol(symbol);
		}
	}
}

//int getPositionOfDate(ArrayList<HistData> data, Date date) {
//int pos = 0;
//for (int i = 0; i < data.size(); i++) {
//	if (data.get(i).getDate().before(date)) {
//		pos = i;
//	}
//}
//return pos;
//}

//// Candlestick Line
//public XYChart.Series setDataSeries(ArrayList<HistData> data) {
//	HistData day;
//	CandleStickExtraValues extraValues;
//	XYChart.Series series;
//	series = new XYChart.Series();
//	dataSetsToShow = data.size();
//	series.setName("Chart");
//	for (int i = offsetFromShow; i < dataSetsToShow; i++) {
//		day = data.get(i);
//		extraValues = new CandleStickExtraValues(day);
//		addDayToDataSerie(data.get(i), series, extraValues);
//	}
//	addSeries(series);
//	return series;
//}
//
//// Some other Line
//public void setAdditonalDataSeries(ArrayList<HistData> data, IMetric metric) {
//	// data = metric.getData();
//	HistData day;
//	CandleStickExtraValues extraValues;
//	XYChart.Series series = new XYChart.Series();
//	dataSetsToShow = data.size();
//	series.setName(metric.getClass().getSimpleName());
//	for (int i = offsetFromShow; i < dataSetsToShow; i++) {
//		day = data.get(i);
//		extraValues = null;
//		addDayToDataSerie(data.get(i), series, extraValues);
//	}
//	addSeries(series);
//}

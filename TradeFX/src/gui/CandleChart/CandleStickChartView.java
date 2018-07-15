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
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.HistData;
import model.Symbol;
import model.TradeFXModel;
import model.metrics.ArithmeticMean;
import model.metrics.FloatingMean;
import model.metrics.IMetric;
import util.loader.Metric.MetricLoader;

public class CandleStickChartView extends BorderPane {

	public CandleStickChart lineChart;
	private int dataSetsToShow;
	int offsetToShow=0;
	int offsetFromShow=0;
	TradeFXModel model;
	Symbol symbol;
	Boolean DEBUG=true;
	BorderPane bp;
	Label dataLabel;
	
	public CandleStickChartView() {
		bp=new BorderPane();
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Date");
		yAxis.setLabel("Value");
		yAxis.setForceZeroInRange(false);
		lineChart = new CandleStickChart(xAxis, yAxis);
		model = TradeFXBusinessController.getInstance().getModel();
		dataLabel= new Label();
		this.setCenter(lineChart);
		this.setBottom(dataLabel);
		dataLabel.setText("new");

	}


	public void setDataForSymbol(Symbol symbol) {
		ArrayList<HistData> data;
		MetricLoader metricLoader;
		IMetric iml3;
		this.symbol=symbol;
		data = TradeFXModel.getHistDataFor(symbol);
		dataSetsToShow = data.size();
		setDataSeries(data);
		// Legede
		for (Map<String, Object> mMetricLoader : model.MetricLoaderMaps) {
			if (mMetricLoader.get("Symbol").equals(symbol.getName())) {
				for (IMetric metric : model.aMetrics) {
					metricLoader = (MetricLoader) mMetricLoader.get(metric.getClass().getSimpleName());
					iml3 = metricLoader.metric;
					lineChart.paintingColor = iml3.getColor();
					setAdditonalDataSeries(data, iml3);
				}
			}
		}
		dataLabel.setText("From:"+data.get(offsetFromShow).getDate()+" To:"+data.get(offsetToShow).getDate());
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
		series.setName("Chart");
		XYChart.Data dat;
		dataSetsToShow = data.size();
		
		for (int i = offsetFromShow; i < dataSetsToShow; i++) {
			HistData day = data.get(i);
			final CandleStickExtraValues extraValues = new CandleStickExtraValues(day.getClose(), day.getHight(),
					day.getLow(), (day.getHight() + day.getLow()) / 2);
			addDayToDataSerie(data.get(i),series,extraValues);
		}
		addSeries(series);
		return series;
	}

	// Some other Line
	public void setAdditonalDataSeries(ArrayList<HistData> data, IMetric metric) {
		XYChart.Series series = new XYChart.Series();
		series.setName(metric.getClass().getSimpleName());
		data = metric.getData();
		dataSetsToShow = data.size();
		
		for (int i = offsetFromShow; i < dataSetsToShow; i++) {
			HistData day = data.get(i);
			final CandleStickExtraValues extraValues = null;
			addDayToDataSerie(data.get(i),series,extraValues);
		}
		addSeries(series);
	}
	
	void addDayToDataSerie(HistData day,XYChart.Series series, CandleStickExtraValues extras){
		series.getData().add(new XYChart.Data<String, Number>((String) day.getDate().toString(),(double) day.getOpen(), extras));
	}
	
	
	private void addSeries(Series Series) {
		ObservableList<XYChart.Series<String, Number>> lineChartData = lineChart.getData();
		if (lineChartData == null) {
			lineChartData = FXCollections.observableArrayList(Series);
			lineChart.setData(lineChartData);
		} else {
			lineChart.getData().add(Series);
		}
	}

}

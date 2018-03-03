package gui.view;

import java.util.ArrayList;
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
import loader.MetricLoader;
import model.HistData;
import model.Symbol;
import model.TradeFXModel;
import model.metrics.ArithmeticMean;
import model.metrics.FloatingMean;
import model.metrics.IMetric;

public class CandleStickChartView {

	public CandleStickChart lineChart;
	private int dataSetsToShow;
	TradeFXModel model;
	
	public CandleStickChartView() {
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Date");
		yAxis.setLabel("Value");
		yAxis.setForceZeroInRange(false);
		lineChart = new CandleStickChart(xAxis, yAxis);
		model=TradeFXBusinessController.getInstance().getModel();
	}

	public void setData(ArrayList<HistData> data) {
		setDataSeries(data);
		dataSetsToShow=data.size();
	}

	
	public void setDataForSymbol(Symbol symbol) {
		ArrayList<HistData> data;
		data= TradeFXModel.getHistDataFor(symbol);
		dataSetsToShow=data.size();
		setDataSeries(data);
		IMetric iml3;
		MetricLoader metricLoader;
		System.out.println("Model.items-----------------"+model.items);
		
		
		for (Map<String, Object> item : model.items) {
			System.out.println("oooooooooooooooooooooooooooooooooooooooooooooooooooooooooo"+item.toString());
			System.out.println(symbol.getClass().getSimpleName()+"->"+item.get("Symbol").equals(symbol.getName()));
			System.out.println("oo"+item.get("Symbol")+"?"+symbol.getName());
			if (item.get("Symbol").equals(symbol.getName())){
				for (IMetric metric : model.aMetrics) {
					System.out.println("pülplülüü"+item.get(metric.getClass().getSimpleName()));
					metricLoader=( MetricLoader ) item.get(metric.getClass().getSimpleName());
					iml3=metricLoader.metric.getInstance();
					Random r=new Random();
					lineChart.paintingColor=Color.hsb(r.nextInt(), 1, 1);
					setAdditonalDataSeries(data, iml3);
				}
			}
		}
//		IMetric iml=new FloatingMean(50);
//		IMetric iml2= new ArithmeticMean();
//		lineChart.paintingColor=Paint.valueOf("yellow");
//		setAdditonalDataSeries(data, iml);
//		lineChart.paintingColor=Paint.valueOf("red");
//		setAdditonalDataSeries(data, iml2);
		lineChart.setTitle(symbol.getName());
	}

	// Candlestick Line
	public XYChart.Series setDataSeries(ArrayList<HistData> data) {
		XYChart.Series series = new XYChart.Series();
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
		return series;
	}
	
	// Some other Line
	public void setAdditonalDataSeries(ArrayList<HistData> data, IMetric fm){
		XYChart.Series additionalSeries = new XYChart.Series();
		additionalSeries.setName(fm.getClass().getSimpleName());
				
		data=fm.calc(data);
		for (int i = 0; i < dataSetsToShow; i++) {
			HistData day = data.get(i);
			final CandleStickExtraValues extraValues = null; //new CandleStickExtraValues(day.getClose() - 10, day.getHight() - 10,day.getLow() - 10, (day.getHight() + day.getLow()) / 2 - 10);
			additionalSeries.getData().add(new XYChart.Data<String, Number>((String) day.getDate().toString(),(double) day.getOpen(),null));
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

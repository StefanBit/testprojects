package view;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import model.HistData;
import model.Symbol;
import model.TradeFXModel;

public class PortfolioView implements ChangeListener {
	final StackedAreaChart<String, Number> lineChart;
	int x = 0;

	TradeFXModel m = new TradeFXModel();

	public PortfolioView() {
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Month");
		yAxis.setForceZeroInRange(false);
		lineChart = new StackedAreaChart<String, Number>(xAxis, yAxis);
	}

	public void setData() {
		getDataSeriesClose();

	}

	public void getDataSeriesClose() {
		double mid;
		ArrayList<HistData> data;
		XYChart.Data dat;
		XYChart.Series series = new XYChart.Series();;
		Symbol iterable_element;		
		// series.setName(String.valueOf(data.get(0).getPk()));
		//for (Symbol iterable_element : m.StockSymbols) {
		for (int k = 0;k<2;k++){
		iterable_element = m.StockSymbols.get(k);
			
		series = new XYChart.Series();
			data = m.StockHistData.get(iterable_element);
			int scale = data.size() / 50;
			System.out.println(iterable_element.getName() + "llll"+data.size());
			for (int i = 0; i < data.size(); i = i + scale) {
				dat = new XYChart.Data(data.get(i).getDate().toString(), data.get(i).getClose());
				series.getData().add(dat);
				
			}
			lineChart.getData().add(series);

		}
	}

	@Override
	public void changed(ObservableValue arg0, Object arg1, Object arg2) {

	}

}

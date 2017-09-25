package view;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

public class PortfolioView implements ChangeListener{
	final StackedAreaChart<String, Number> lineChart;
	int x=0;

	public PortfolioView() {
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Month");
		yAxis.setForceZeroInRange(false);
		lineChart = new StackedAreaChart<String, Number>(xAxis, yAxis);
	}

	public void setData() {
		TradeFXModel m=new TradeFXModel();
		for (Symbol iterable_element : m.StockSymbols) {
			System.out.println(iterable_element.getName()+"llll");
			lineChart.getData().add(getDataSeriesClose(m.StockHistData.get(iterable_element)));
		}
		
		
	}
	
	public XYChart.Series getDataSeriesClose(ArrayList<HistData> data){
		double mid;
		
		XYChart.Series series = new XYChart.Series();
		XYChart.Data dat;
		series.setName(String.valueOf(data.get(0).getPk()));
		int scale=data.size()/50;
		for (int i = 0; i < data.size(); i=i+scale) {
			dat = new StackedAreaChart.Data(data.get(i).getDate().toString(), data.get(i).getClose());

			series.getData().add(dat);
		}
		
		return series;
	}

	@Override
	public void changed(ObservableValue arg0, Object arg1, Object arg2) {
		
		
		
	}
	
}

package model;

import java.util.Map;

import controller.TradeFXBusinessController;
import javafx.collections.ObservableList;
import model.metrics.IMetric;
import util.loader.Metric.MetricLoader;

public class StockDetails {

	String name;
	TradeFXBusinessController tfxc;
	TradeFXModel model;
	Symbol symbol = null;
	//Map<Object, IMetric> mMetrics;
	Map<String, Object> mMetrics;
	String out, sMetrics;
	
	public StockDetails(String name) {
		this.name = name;
		bindModel();
		bindSymbol();
		bindMetrics();
		buildMetricsString();
	}

	private void buildMetricsString() {
		sMetrics="";
		System.out.println(mMetrics);
		MetricLoader ml;
		
		for (Map.Entry<String, Object> entry : mMetrics.entrySet()) {
		    String key = entry.getKey();
		    
		    if (!(entry.getValue() instanceof String)){
		    	ml = (MetricLoader)  entry.getValue();
		    	sMetrics+= ml.metric.getName()+": "+ml.metric.getData().get(0).getClose()+"$"+ System.lineSeparator();
		    }
		}
	}

	private void bindMetrics() {
		System.out.println("->" +model.MetricLoaderMaps);
		for (Map<String, Object> mMetrics : model.MetricLoaderMaps) {
			if (mMetrics.get("Symbol").equals(name)){
				this.mMetrics = mMetrics;
		
			}
		}
	}

	private void bindSymbol() {
		for (Symbol s : model.StockSymbols) {
			if (s.getName().equals(name)) {
				symbol = s;
			}
		}
	}

	private void bindModel() {
		tfxc = TradeFXBusinessController.getInstance();
		model = tfxc.getModel();
	}

	@Override
	public String toString() {

		HistData valuesToday;
		valuesToday = model.getHistDataFor(symbol).get(model.getHistDataFor(symbol).size() - 1);
		out = "";
		out += "Stock Details: " + System.lineSeparator();
		out += "Stock Name :" + name + System.lineSeparator();
		//out += "Stock Obj :" + symbol + System.lineSeparator();
		out += "Stock Values in $:" + valuesToday + System.lineSeparator();
		out += sMetrics;
		return out;
	}
}

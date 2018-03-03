package loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import controller.TradeFXBusinessController;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import model.HistData;
import model.Symbol;
import model.TradeFXModel;
import model.metrics.IMetric;

public class MetricLoaderWorker extends Task implements InvalidationListener {
	TradeFXModel model;
	private ArrayList<IMetric> aMetric;
	ArrayList<Symbol> StockSymbols;
	public  Map<String,Object> mCell;
	IMetric[] metrics;
	private ObservableList<Map<String, Object>> items;
	int metricLoaderCount;
	
	public MetricLoaderWorker() {
		model = TradeFXBusinessController.getInstance().getModel();
		StockSymbols = model.getStockSymbols();
		items = FXCollections.<Map<String,Object>>observableArrayList() ;
		aMetric = model.aMetrics;
		
	}

	@Override
	protected Object call() throws Exception {
		Thread currentThread = null;
		this.updateMessage("Loading MetricLoader start for "+ StockSymbols.size()*aMetric.size());
		// Fill with MetricLoader's 
		StockSymbols = model.getStockSymbols();
		
		MetricLoader metricLoader;
		System.out.println("start");
		for (Symbol symbol : StockSymbols) {
			mCell=new HashMap<String,Object>(); 
			mCell.put(symbol.getClass().getSimpleName(), symbol.getName().toString());
			System.out.println(".");
			for (IMetric metric : aMetric) {
				System.out.println("--");
				metricLoader=new MetricLoader(symbol, metric.getInstance());
				metricLoader.start();
				mCell.put(metric.getClass().getSimpleName(), metricLoader);
				metricLoader.ready.addListener(this);
			}
			items.add(mCell);
		}
		model.items=items;
		updateProgress(metricLoaderCount, StockSymbols.size()*aMetric.size());
		this.updateMessage("Loading MetricLoader finished");
		return null;
	}
	
	public void registerMetricClasses(IMetric... aMetricClass) {
		metrics = aMetricClass;
		for (IMetric iMetric : aMetricClass) {
			model.aMetrics.add(iMetric);
		}
		
	}

	@Override
	public void invalidated(Observable arg0) {
		if (((BooleanProperty) arg0).getValue().booleanValue()) metricLoaderCount++;
		this.updateMessage("Loading MetricLoader:"+metricLoaderCount+"/"+StockSymbols.size()*aMetric.size());
	}

}

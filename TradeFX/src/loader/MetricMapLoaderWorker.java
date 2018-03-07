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

public class MetricMapLoaderWorker extends Task implements InvalidationListener {
	TradeFXModel model;
	private ArrayList<IMetric> aMetric;
	ArrayList<Symbol> StockSymbols;
	public  Map<String,Object> mCell;
	IMetric[] metrics;
	private ObservableList<Map<String, Object>> items;
	int metricLoaderCount;
	int elementsTODO;
	
	public MetricMapLoaderWorker() {
		model = TradeFXBusinessController.getInstance().getModel();
		StockSymbols = model.getStockSymbols();
		aMetric = model.aMetrics;	
		items = FXCollections.<Map<String,Object>>observableArrayList() ;
		metricLoaderCount=0;
		}

	@Override
	protected Object call() throws Exception {
		MetricLoader metricLoader;
		elementsTODO=StockSymbols.size()*aMetric.size();
		this.updateMessage("Loading MetricLoader start for "+ elementsTODO);
		// Fill with MetricLoader's 

		for (Symbol symbol : StockSymbols) {
			mCell=new HashMap<String,Object>(); 
			mCell.put(symbol.getClass().getSimpleName(), symbol.getName().toString());
			for (IMetric metric : aMetric) {
				metricLoader=new MetricLoader(symbol, metric.getInstance());
				metricLoader.ready.addListener(this);
				metricLoader.start();
				mCell.put(metric.getClass().getSimpleName(), metricLoader);
			}
			items.add(mCell);
		}
		model.items=items;
//		while (metricLoaderCount!=(StockSymbols.size()*aMetric.size())){
//			System.out.print("kkklklklk:"+metricLoaderCount+"/"+StockSymbols.size()*aMetric.size());
//		}
		//System.out.print("kkklklklk:"+metricLoaderCount+"/"+StockSymbols.size()*aMetric.size());
		return null;
	}

	@Override
	public void invalidated(Observable arg0) {
		if (((BooleanProperty) arg0).getValue().booleanValue()) metricLoaderCount++;
		this.updateMessage("Loading MetricLoader finished");
		updateProgress(metricLoaderCount, elementsTODO);
		this.updateMessage("Loading MetricLoader:"+metricLoaderCount+"/"+StockSymbols.size()*aMetric.size()+" finished");
		System.out.println("Loading MetricLoader:"+metricLoaderCount+"/"+StockSymbols.size()*aMetric.size()+" finished");
	}
@Override
protected void succeeded() {
	// TODO Auto-generated method stub
	super.succeeded();
	
	System.out.println("Fertig "+this.progressProperty());
}
}

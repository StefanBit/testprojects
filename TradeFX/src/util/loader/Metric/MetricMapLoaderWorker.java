package util.loader.Metric;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import util.properties.MyProperties;


/**
 * 
 * @author Stefan
 *
 */



public class MetricMapLoaderWorker extends Task implements InvalidationListener {
	TradeFXModel model;
	private ArrayList<IMetric> aMetrics;
	ArrayList<Symbol> aSymbols;
	public Map<String, Object> mMetricLoaderRow;
	private ObservableList<Map<String, Object>> items;
	int metricLoaderCount;
	int elementsTODO;
	Boolean DEBUG;
	private static final Logger log = Logger.getLogger(MetricMapLoaderWorker.class.getName());

	public MetricMapLoaderWorker() {
		model = TradeFXBusinessController.getInstance().getModel();
		aSymbols = model.getStockSymbols();
		
		log.warning("Accessing "+ model.toString());
		
		aMetrics = model.aMetrics;
		log.warning("Avaiable Metrics "+aSymbols.toString());	
//		while (	aSymbols.size()==0) {
//			aSymbols = model.getStockSymbols();
//		}
		
		items = model.MetricLoaderMaps;
		items.clear();
		metricLoaderCount = 0;
		DEBUG = MyProperties.getDebugSettingFor("MetricMapLoaderWorkerDbg");
		// Build Metric Structure

		log.setLevel(Level.INFO);
	
		for (Symbol symbol : aSymbols) {
			mMetricLoaderRow = new HashMap<String, Object>();
			mMetricLoaderRow.put(symbol.getClass().getSimpleName(), symbol.getName().toString());
			items.add(mMetricLoaderRow);
			log.warning("Added ");	
			for (IMetric metric : aMetrics) {
				putMetricLoaderFor(symbol, metric);
			}
		}
		log.warning("MetricMap constructed");	
	}

	void putMetricLoaderFor(Symbol symbol, IMetric metric) {
		MetricLoader metricLoader;
		metricLoader = new MetricLoader(symbol, metric.getInstance());
		metricLoader.ready.addListener(this);
		mMetricLoaderRow.put(metric.getClass().getSimpleName(), metricLoader);
	}

	@Override
	protected Object call() throws Exception {
		System.out.println("call Worker");
		elementsTODO = aSymbols.size() * aMetrics.size();
		this.updateMessage("Loading MetricLoader start for " + elementsTODO);
		// Start Metric Loaders
		MetricLoader metricLoader;
		for (Symbol symbol : aSymbols) {
			mMetricLoaderRow = getHashMapFor(symbol);
			System.out.println(mMetricLoaderRow);
			for (IMetric metric : aMetrics) {
				metricLoader = (MetricLoader) mMetricLoaderRow.get(metric.getClass().getSimpleName());
				metricLoader.start();
				while (metricLoader.ready.getValue() != true) {
				}
			}
		}
		System.out.println("call Worker ended");
		return null;
	}

	private Map<String, Object> getHashMapFor(Symbol symbol) {
		Map<String, Object> map = null;
		for (Map<String, Object> map2 : items) {
			if (map2.get("Symbol").equals(symbol.getName())) {
				map = map2;
			}
		}
		return map;
	}

	@Override
	public void invalidated(Observable arg0) {
		if (((BooleanProperty) arg0).getValue().booleanValue())
			metricLoaderCount++;
		this.updateMessage(metricLoaderCount + "/" + aSymbols.size() * aMetrics.size() + " finished");
		updateProgress(metricLoaderCount, elementsTODO);
		if (DEBUG)
			System.out.println("Loading MetricLoader:" + metricLoaderCount + "/" + aSymbols.size() * aMetrics.size()
					+ " finished");
	}

	@Override
	protected void succeeded() {
		super.succeeded();
		if (DEBUG)
			System.out.println("Fertig " + this.progressProperty());
		metricLoaderCount = 0;
	}
}

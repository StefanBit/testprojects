package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.metrics.IMetric;
import util.loader.HistoricalDataLoader.HistStockDataLoaderTask;
import util.loader.Metric.MetricLoaderTask;
import util.log.Log;
import util.properties.MyProperties;

public class TradeFXModel {

	public static TradeFXModel model;
	public static ArrayList<Symbol> StockSymbols;
	public static Map<Symbol, ArrayList<HistData>> StockHistData;
	public static Map<Symbol, HistStockDataLoaderTask> tasks;
	// public static Map<Symbol, Map<Object, IMetric>> mSymbolMetric;
	public static Map<Symbol, StockDetails> mStockDetails;
	public static ArrayList<Transaction> trades;
	public static Boolean symbolsLoaded = false;
	public static Boolean histDataLoaded = false;
	public ArrayList<IMetric> aMetrics;
	public static Map<Symbol, ArrayList<MetricLoaderTask>> mSymbolMetricsTask;
	public ObservableList<Map<String, Object>> MetricLoaderMaps;
	public Symbol selectedSymbol;
	public MyProperties myProperies;
	
	
	public static TradeFXModel getInstance() {
		if (model==null) {
			model = new TradeFXModel();
		}
		return model;
	}
	
	public MyProperties getMyProperies() {
		return myProperies;
	}

	public void setMyProperies(MyProperties myProperies) {
		this.myProperies = myProperies;
	}

	public TradeFXModel() {
		
			StockSymbols = new ArrayList<>();
			StockHistData = new HashMap<Symbol, ArrayList<HistData>>();
			mSymbolMetricsTask = new HashMap<Symbol, ArrayList<MetricLoaderTask>>();
			tasks = new HashMap<Symbol, HistStockDataLoaderTask>();
			trades = new ArrayList<Transaction>();
			aMetrics = new ArrayList<IMetric>();
			MetricLoaderMaps = FXCollections.<Map<String, Object>>observableArrayList();
			// mSymbolMetric= new HashMap<Symbol,Map<Object,IMetric>>();			
		
	}
	

	public ArrayList<Symbol> getStockSymbols() {
		return StockSymbols;
	}

	public static void showState() {
		Log.config("-------------Showing Model Konfiguration--------------");
		Log.config("StockSymbols:" + StockSymbols);
		Log.config("StockHistData:" + StockHistData.size());
		
		Symbol currentSymbol;
		for (Map.Entry<Symbol, ArrayList<HistData>> entry : TradeFXModel.StockHistData.entrySet()) {
			Log.config("StockHistData Key:" + entry.getKey());
			Log.config("StockHistData Value:" + entry.getValue().size());
			Log.config("StockHistData:" + TradeFXModel.StockHistData);
		}
		Log.config(tasks.toString());
		Log.config("------------------------------------------------------");
	}

	public static ArrayList<HistData> getHistDataFor(Symbol s) {
		ArrayList<HistData> result;
		result = null;
		for (Map.Entry<Symbol, ArrayList<HistData>> entry : TradeFXModel.StockHistData.entrySet()) {
			if (entry.getKey().pk.equals(s.pk)) {
				// System.out.println("StockHistData Key:"+entry.getKey());
				// System.out.println("StockHistData
				// Value:"+entry.getValue().size());
				result = entry.getValue();
				// System.out.println("StockHistData:"+TradeFXModel.StockHistData);
			}
		}

		return result;
	}

}

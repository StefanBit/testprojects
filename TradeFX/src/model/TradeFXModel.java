package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import loader.HistStockDataLoaderTask;
import loader.MetricLoaderTask;

import model.metrics.IMetric;

public class TradeFXModel {

	public static ArrayList<Symbol> StockSymbols;
	public static Map<Symbol, ArrayList<HistData>> StockHistData;
	public static Map<Symbol, HistStockDataLoaderTask> tasks;
	public static Map<Symbol, Map<Object, IMetric>> mSymbolMetric;
	public static ArrayList<Transaction> trades;
	public static Boolean symbolsLoaded = false;
	public static Boolean histDataLoaded = false;
	public ArrayList<IMetric> aMetrics;
	public static Map<Symbol, ArrayList<MetricLoaderTask>> mSymbolMetricsTask;
//	public SymbolMetricLoader symbolMetricLoader;
	public ObservableList<Map<String, Object>> items;
	
	public TradeFXModel() {
		StockSymbols = new ArrayList<>();
		StockHistData = new HashMap<Symbol, ArrayList<HistData>>();
		mSymbolMetricsTask = new HashMap<Symbol, ArrayList<MetricLoaderTask>>();
		tasks = new HashMap<Symbol, HistStockDataLoaderTask>();
		trades = new ArrayList<Transaction>();
		aMetrics = new ArrayList<IMetric>();
	//	symbolMetricLoader = new SymbolMetricLoader();
		items=FXCollections.<Map<String,Object>>observableArrayList();
		mSymbolMetric= new HashMap<Symbol,Map<Object,IMetric>>();
	}

	public ArrayList<Symbol> getStockSymbols() {
		return StockSymbols;
	}

	public static void showState() {
		System.out.println("-------------State--------------");
		System.out.println("StockSymbols:" + StockSymbols);
		System.out.println("StockHistData:" + StockHistData.size());
		Symbol currentSymbol;
		for (Map.Entry<Symbol, ArrayList<HistData>> entry : TradeFXModel.StockHistData.entrySet()) {
			System.out.println("StockHistData Key:" + entry.getKey());
			System.out.println("StockHistData Value:" + entry.getValue().size());
			System.out.println("StockHistData:" + TradeFXModel.StockHistData);
		}
		System.out.println(tasks);
		System.out.println("-------------State--------------");
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

package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import loader.HistStockDataLoaderTask;


public class TradeFXModel {

	public static ArrayList<Symbol> StockSymbols;
	public static Map<Symbol, ArrayList<HistData>> StockHistData;
	public static Map<Symbol, HistStockDataLoaderTask> tasks;
	public static ArrayList<Transaction> trades;
	
	public TradeFXModel() {
		StockSymbols=new ArrayList<>();
		StockHistData=new HashMap<Symbol, ArrayList<HistData>>();
		tasks=new HashMap<Symbol, HistStockDataLoaderTask>();
		trades=new ArrayList<Transaction>();
	}

}

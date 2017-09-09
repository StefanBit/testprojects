package model;

import java.util.ArrayList;
import java.util.Map;

import loader.HistStockDataLoaderTask;


public class TradeFXModel {

	public static ArrayList<Symbol> StockSymbols;
	public static Map<Symbol, ArrayList<HistData>> StockHistData;
	public static Map<Symbol, HistStockDataLoaderTask> tasks;


}

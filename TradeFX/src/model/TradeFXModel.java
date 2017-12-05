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
	public static Boolean symbolsLoaded = false;
	public static Boolean histDataLoaded = false;
	
	public TradeFXModel() {
		StockSymbols=new ArrayList<>();
		StockHistData=new HashMap<Symbol, ArrayList<HistData>>();
		tasks=new HashMap<Symbol, HistStockDataLoaderTask>();
		trades=new ArrayList<Transaction>();
	}
	
	public static void showState(){
		System.out.println("-------------State--------------");
		System.out.println("StockSymbols:"+StockSymbols);
		System.out.println("StockHistData:"+StockHistData.size());
		Symbol currentSymbol;
		for (Map.Entry<Symbol, ArrayList<HistData>> entry : TradeFXModel.StockHistData.entrySet()) {
			System.out.println("StockHistData Key:"+entry.getKey());
			System.out.println("StockHistData Value:"+entry.getValue().size());
			System.out.println("StockHistData:"+TradeFXModel.StockHistData);
			}
		System.out.println(tasks);
		System.out.println("-------------State--------------");
	}
	
	
	public static ArrayList<HistData> getHistDataFor(Symbol s){
		ArrayList<HistData> result;
		result = null;
		for (Map.Entry<Symbol, ArrayList<HistData>> entry : TradeFXModel.StockHistData.entrySet()) {
			if (entry.getKey().pk.equals(s.pk)) {
				System.out.println("StockHistData Key:"+entry.getKey());
				System.out.println("StockHistData Value:"+entry.getValue().size());
				result = entry.getValue();
				System.out.println("StockHistData:"+TradeFXModel.StockHistData);
			}
			}
		
		
		return result;
	}

}

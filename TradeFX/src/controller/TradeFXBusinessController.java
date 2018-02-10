package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import database.DAOHsqlImpl;
import gui.stage.ChartStage;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import loader.HistStockDataLoaderTask;
import loader.SymbolLoaderTask;
import model.*;

public class TradeFXBusinessController {

	public static TradeFXBusinessController tfxc=null;
	
	public SymbolLoaderTask symbolsLoaderTask;
	ArrayList<Thread> threads;
	TradeFXModel model;
	
	public TradeFXBusinessController() {
		model = new TradeFXModel();
		tfxc=this;
		//TradeFXModel.tasks = new HashMap<Symbol,HistStockDataLoaderTask>();
		//threads = new ArrayList<Thread>();
	}
	
	public void init() {
		loadSymbols();
		loadHistData();
	}
	
	public TradeFXModel getModel(){
		return this.model;
	}
	
	public void loadSymbols(){
		System.out.println("Loading Symbols"); 
		symbolsLoaderTask = new SymbolLoaderTask();
		Thread thread = new Thread(symbolsLoaderTask);
		thread.start();
		while (thread.isAlive()){
		}
		System.out.println("Ok."+TradeFXModel.StockSymbols);
		//System.out.println("Ok."+TradeFXModel.StockHistData);
		model.symbolsLoaded=true;
	}
	
	public void loadHistData(){
		Thread currentThread=null;
		for (Map.Entry<Symbol, ArrayList<HistData>> entry : TradeFXModel.StockHistData.entrySet()) {
			Symbol currentSymbol = entry.getKey();
			HistStockDataLoaderTask currenttask = new HistStockDataLoaderTask();
			TradeFXModel.tasks.put(currentSymbol, currenttask);
			currenttask.alSymbol = currentSymbol;
			currentThread = new Thread(currenttask);
			currentThread.start();
			while (currentThread.isAlive()){
				//System.out.print("-");
			}
			System.out.println(currentSymbol.toString()+"loaded");
		}
		while (currentThread.isAlive()){
			//System.out.print("-");
		}
		TradeFXModel.histDataLoaded=true;
	}
	
	

	
}
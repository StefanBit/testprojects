package controller.state;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javafx.event.Event;
import javafx.event.EventHandler;
import model.Symbol;
import model.TradeFXModel;
import util.loader.SymbolLoaderTask;
import util.log.Log;

public class PropertiesLoadedState extends State implements EventHandler<Event>{

	
	public PropertiesLoadedState(StateMachine statemachine) {
		this.statemachine = statemachine;
	}

	
	public void loadSymbols() {
		Log.info("Loading Symbols ...");
		SymbolLoaderTask task = new SymbolLoaderTask();
		//Callable<ArrayList<Symbol>> task  = new SymbolLoaderTask();
//		Thread thread = new Thread(task);
//		thread.start();
//		task.setOnSucceeded(this);
		ExecutorService executorService1 = Executors.newSingleThreadExecutor();
		Future future = executorService1.submit(task);
		try {
			System.out.println("future.get() = " + future.get());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		executorService1.shutdown();
		
		statemachine.setState(statemachine.symbolsLoadedState);
	}
	
	@Override
	public void nextState() {
		loadSymbols();
	}


	@Override
	public void handle(Event event) {
		if (event.getEventType().toString().equals("WORKER_STATE_SUCCEEDED")) {
			
			System.out.println(event.getEventType());		
		}
	}


	@Override
	public void registerObserver(Object o) {
		// TODO Auto-generated method stub
		
	}
}

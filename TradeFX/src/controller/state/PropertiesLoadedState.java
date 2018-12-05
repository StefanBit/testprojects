package controller.state;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javafx.event.Event;
import javafx.event.EventHandler;
import util.loader.SymbolLoaderTask;
import util.log.Log;

public class PropertiesLoadedState extends State implements EventHandler<Event>{

	
	public PropertiesLoadedState(StateMachine stateMachine) {
	
	}

	
	public void loadSymbols() {
		Log.info("Loading Symbols ...");
		SymbolLoaderTask task = new SymbolLoaderTask();
		Thread thread = new Thread(task);
		thread.start();
		while (task.isRunning()) {
		}
		
	}
	
	@Override
	public void nextState() {
		loadSymbols();
	}


	@Override
	public void handle(Event event) {
		System.out.println("kkk");
	}

}

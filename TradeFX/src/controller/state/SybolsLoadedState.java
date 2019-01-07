package controller.state;

import gui.SymbolsUIController;
import util.loader.HistoricalDataLoader.HistStockDataLoaderWorker;
import util.log.Log;

public class SybolsLoadedState extends State {
	Object o;
	
	public SybolsLoadedState(StateMachine statemachine) {
		this.statemachine=statemachine;
	}
	

	public void loadHistoricalData() {
		super.loadHistoricalData();
		new HistStockDataLoaderWorker();
		//((SymbolsUIController) o).set();
		statemachine.setState(statemachine.historicalDataLoadedState);
	}
	
	@Override
	public void nextState() {
		loadHistoricalData();
	}
	
	
	@Override
	public void registerObserver(Object o) {
		this.o=o;
		
	}

}

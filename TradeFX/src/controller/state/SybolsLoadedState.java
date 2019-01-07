package controller.state;

import gui.SymbolsUIController;
import util.log.Log;

public class SybolsLoadedState extends State {
	Object o;
	
	public SybolsLoadedState(StateMachine statemachine) {
		this.statemachine=statemachine;
	}
	

	public void finished() {
		Log.info("Finished");
		((SymbolsUIController) o).set();
	}
	
	@Override
	public void nextState() {
		finished();
	}
	
	
	@Override
	public void registerObserver(Object o) {
		this.o=o;
		
	}

}

package controller.state;

import gui.SymbolsUIController;
import util.log.Log;

public class HistoricalDataLoadedState extends State {
	public HistoricalDataLoadedState(StateMachine statemachine) {
		this.statemachine = statemachine;
	}

	public void finished() {
		Log.info("Finished");

	}

	@Override
	public void nextState() {
		finished();
	}

	@Override
	public void registerObserver(Object o) {
		// TODO Auto-generated method stub

	}
}

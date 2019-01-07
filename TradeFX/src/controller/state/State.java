package controller.state;

import util.log.Log;
import util.properties.MyProperties;

public abstract class State implements IState{

	StateMachine statemachine;
	
	public void loadModel() {
		Log.info("Loading Model...");
	}
	public void loadProperties() {
		Log.info("Loading Properties ...");
	}

	public void loadSymbols() {
		Log.info("Loading Symbols ...");
	}
	public void finish() {
		Log.info("Finish ...");
	}
	
	public void nextState() {
		// TODO Auto-generated method stub
	}
	public void registerObserver() {};
}

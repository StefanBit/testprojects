package controller.state;

import util.log.Log;

public class InitialState implements IState{
	
	StateMachine statemachine;
	
	public InitialState(StateMachine statemachine) {
		this.statemachine	= statemachine;
	}
	@Override
	public void loadSymbols() {
		Log.info(this.getClass().getSimpleName() + " loading Symbols. ");
	}
	@Override
	public void nextState() {
		loadSymbols();
		
	}
}

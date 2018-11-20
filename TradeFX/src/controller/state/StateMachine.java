package controller.state;

import model.TradeFXModel;
import util.log.Log;
import util.properties.MyProperties;

public class StateMachine {

	IState state;
	IState initialState;
	IState propertiesLoadedState;
	IState symbolsLoadedState;
	IState modelLoadedState;
	
	MyProperties properties;
	TradeFXModel model;
	
	public StateMachine() {
		Log.info("Start Construction of "+this.getClass().getSimpleName());
		initialState = new InitialState(this);
		
		
		modelLoadedState = new ModelLoadedState(this);
		propertiesLoadedState = new PropertiesLoadedState(this);
		symbolsLoadedState = new SymbolsLoadedState(this);
		setState(initialState);
	}
	
	public void nextState() {
		state.nextState();
	}
	
	public void setState(IState state) {
		Log.info("... Reached "+state.getClass().getSimpleName());
		this.state=state;
	}
}

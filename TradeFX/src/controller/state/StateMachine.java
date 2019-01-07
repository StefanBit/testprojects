package controller.state;

import model.TradeFXModel;
import util.log.Log;
import util.properties.MyProperties;

public class StateMachine {

	IState state;
	IState initialState;
	IState propertiesLoadedState;
	IState historicalDataLoadedState;
	public IState modelLoadedState;
	public  IState symbolsLoadedState;
	static StateMachine stateMachine; 
	public TradeFXModel model;
	
	MyProperties properties;
	public MyProperties getProperties() {
		return properties;
	}

	public void setProperties(MyProperties properties) {
		this.properties = properties;
	}

	
	public static StateMachine getInstance() {
		if (stateMachine==null) {
			stateMachine = new StateMachine();
		}
		return stateMachine;
	}
	
	public StateMachine() {
		Log.info("Start Construction of "+this.getClass().getSimpleName());
		initialState = new InitialState(this);
		modelLoadedState = new ModelLoadedState(this);
		propertiesLoadedState = new PropertiesLoadedState(this);
		symbolsLoadedState = new SybolsLoadedState(this);
		historicalDataLoadedState = new HistoricalDataLoadedState(this);
		setState(initialState);
	}
	
	public void nextState() {
		state.nextState();
	}
	public State getState() {
		return null;
	}
	
	
	
	public void setState(IState state) {
		Log.info("... Reached "+state.getClass().getSimpleName());
		this.state=state;
	}
	
	public void init() {
		this.nextState();
		this.nextState();
		this.nextState();
		this.nextState();
	}
	
}

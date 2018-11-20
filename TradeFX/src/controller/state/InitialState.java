package controller.state;

import model.TradeFXModel;
import util.log.Log;
import util.properties.MyProperties;

public class InitialState extends State{
	
	public InitialState(StateMachine statemachine) {
		this.statemachine	= statemachine;
	}
	
	@Override
	public void loadModel() {
		super.loadModel();
		statemachine.model= new TradeFXModel();
		statemachine.model.showState();
		statemachine.setState(statemachine.modelLoadedState);
	}
	
	@Override
	public void nextState() {
		loadModel();
	}
}

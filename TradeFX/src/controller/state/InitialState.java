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
		statemachine.model= TradeFXModel.getInstance();
		statemachine.model.showState();
		statemachine.setState(statemachine.modelLoadedState);
	}
	
	@Override
	public void nextState() {
		loadModel();
	}

	@Override
	public void registerObserver(Object o) {
		// TODO Auto-generated method stub
		
	}
}

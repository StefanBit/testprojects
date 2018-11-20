package controller.state;

import util.properties.MyProperties;

public class ModelLoadedState extends State {
	public ModelLoadedState(StateMachine statemachine) {
		this.statemachine = statemachine;
	}
	
	
	@Override
	public void loadProperties() {
		super.loadProperties();
		statemachine.properties = new MyProperties();
		statemachine.setState(statemachine.propertiesLoadedState);
	}
	

	@Override
	public void nextState() {
		loadProperties();
	}

}

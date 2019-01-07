package controller.state;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventListener;

import gui.SymbolsUIController;
import javafx.event.EventHandler;
import util.properties.MyProperties;

public class ModelLoadedState extends State{
	
	public ModelLoadedState(StateMachine statemachine) {
		this.statemachine = statemachine;
	}
	
	
	@Override
	public void loadProperties() {
		super.loadProperties();
		statemachine.model.setMyProperies(new MyProperties());
		statemachine.setState(statemachine.propertiesLoadedState);
	}
	

	@Override
	public void nextState() {
		loadProperties();
	}
	
	@Override
	public void registerObserver(Object o) {
	}

}

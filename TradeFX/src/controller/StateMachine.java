package controller;

public class StateMachine {

	State InitialState;
	
	State state;
	
	public StateMachine() {
		InitialState= new InitialState(this);
		state =InitialState;
	}
	
	
}

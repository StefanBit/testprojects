package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class StateMachine {

	IState InitialState;
	IState SymbolsLoadedState;
	
	IState state;
	
	public StateMachine() {
		InitialState= new InitialState(this);
		SymbolsLoadedState = new SybolsLoadedState(this);
		state=InitialState;
		state.nextState();
	}
	
	@Test
	public void CreateClass(){
		//assertNotNull(new StateMachine());
	}
	@Test
	public void Test2(){
		//assertNotNull(new StateMachine());
	}
}

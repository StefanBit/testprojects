package controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.omg.CORBA.Environment;

import controller.state.StateMachine;
import util.log.Log;

public class StatemachineTest {
	public StatemachineTest() {
		Log.init();
		StateMachine stateMachine = StateMachine.getInstance();
		stateMachine.nextState();
		stateMachine.nextState();
	}

	@Test
	public void CreateClass() {
		System.out.println();
	}
}

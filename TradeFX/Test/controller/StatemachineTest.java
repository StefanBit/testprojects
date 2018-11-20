package controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import controller.state.StateMachine;
import util.log.Log;

public class StatemachineTest {
	public StatemachineTest() {
		Log.setLoggingProperties("C:\\Users\\Stefan\\git\\testprojects\\TradeFX\\src\\util\\log\\logging.properties");
		StateMachine stateMachine = new StateMachine();
		stateMachine.nextState();
		stateMachine.nextState();
	}

	@Test
	public void CreateClass() {
	}
}

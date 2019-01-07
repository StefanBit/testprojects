package controller;

import java.net.URL;

import controller.state.StateMachine;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.log.Log;

public class StatemachineTest extends Application{

	public static void main(String[] args) {
		
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Log.init();
		StateMachine stateMachine = StateMachine.getInstance();
		stateMachine.init();
	}
}
package gui;

import controller.state.StateMachine;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PropertiesUIController {
	@FXML
	TextField databasePath;

	public PropertiesUIController() {
	}
	public void initialize() {
		StateMachine stateMachine =StateMachine.getInstance();
		System.out.println("Const");
		
		databasePath.setText(stateMachine.getProperties().getProperty("dbfile"));
	}

}

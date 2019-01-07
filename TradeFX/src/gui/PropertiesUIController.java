package gui;

import controller.state.StateMachine;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PropertiesUIController {
	@FXML
	TextField databasePath;
	@FXML
	TextField propertiesFile;

	public PropertiesUIController() {
	}
	public void initialize() {
		StateMachine stateMachine =StateMachine.getInstance();
		System.out.println("Const");
		databasePath.setText(stateMachine.model.getMyProperies().getProperty("dbfile"));
		propertiesFile.setText(stateMachine.model.getMyProperies().getPropertiesFilename());
		
	}

}
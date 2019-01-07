package application.gui;

import java.net.URL;

import controller.state.StateMachine;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.log.Log;

public class TestSymbolsUI extends Application{

	public static void main(String[] args) {
		Log.init();
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		StateMachine stateMachine = StateMachine.getInstance();
		stateMachine.nextState();
		
		String path=System.getProperty("user.dir").toString();
		System.out.println(path);
		FXMLLoader loader = new FXMLLoader();
		URL url=new URL("file:///"+path+"/src/gui/SymbolsUI.fxml");
		loader.setLocation(url);
		System.out.println(loader.getLocation());
    	Parent root = loader.load(); 
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stateMachine.nextState();
        stateMachine.nextState();
        stateMachine.nextState();
	}
}

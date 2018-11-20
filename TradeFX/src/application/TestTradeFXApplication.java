package application;

import java.net.URL;

import controller.TradeFXBusinessController;
import gui.Table.MyTablePane;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TestTradeFXApplication extends Application{

	public static void main(String[] args) {
		Application.launch(args);
	}
	
	

    public void start(Stage stage) throws Exception {


    	FXMLLoader loader = new FXMLLoader(getClass().getResource("TradeFXApplication.fxml"));
    	Parent root = loader.load(); 
        Scene scene = new Scene(root, 1224, 1224);
        System.out.println(System.getProperties());
        stage.setTitle("TradeFXApplication");
        stage.initStyle(StageStyle.UNIFIED);
        stage.setScene(scene);
        
        stage.show();
        
    }
}

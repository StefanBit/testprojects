package application;



import gui.stage.MainStage;
import javafx.application.Application;
import javafx.stage.Stage;


public class TestMain extends Application {
	@Override
	public void start(Stage primaryStage) {
			new MainStage();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

package application;



import javafx.application.Application;
import javafx.stage.Stage;
import stage.MainStage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
			new MainStage();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

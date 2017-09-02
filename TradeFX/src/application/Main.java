package application;

import java.util.Calendar;
import java.util.Date;

import database.DAOHsqlImpl;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.Symbol;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
	
			StackPane root = new StackPane();
			
			new MainStage();
			
			
			
			
			
	}

	public static void main(String[] args) {
		launch(args);
	}
}

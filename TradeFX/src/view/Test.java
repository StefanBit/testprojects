package view;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import stage.MainStage;

public class Test extends Application {
	
	
	BorderPane root;
	Scene scene;
	Stage stage;
	private static Object[][] data = new Object[][]{
		{"1",  25.0, 20.0, 32.0, 16.0, 20.0},
		{"2",  26.0, 30.0, 33.0, 22.0, 25.0},
		{"3",  30.0, 38.0, 40.0, 20.0, 32.0},
		{"4",  24.0, 30.0, 34.0, 22.0, 30.0},
	};
	
	@Override
	public void start(Stage primaryStage) {
	
		  // DAY, OPEN, CLOSE, HIGH, LOW, AVERAGE

	    
			stage=new Stage();
			 stage.setTitle("Line Chart Sample");

			final CategoryAxis xAxis = new CategoryAxis();
			final NumberAxis yAxis = new NumberAxis();
			xAxis.setLabel("Month");
			//yAxis.setForceZeroInRange(false);		
			CandleStickChart lineChart = new CandleStickChart(xAxis,yAxis);
			lineChart.setTitle("Stock Monitoring, 2010");
			XYChart.Series series = new XYChart.Series();
			series.setName("My portfolio");
			 for (int i=0; i< data.length; i++) {
		            Object[] day = data[i];
		            final CandleStickExtraValues extras =new CandleStickExtraValues(day[2],day[3],day[4],day[5]);
		            series.getData().add(new XYChart.Data<String,Number>((String) day[0],(double) day[1],extras));
		        }
			 ObservableList<XYChart.Series<String, Number>> data1 = lineChart.getData();
			  if (data1 == null) {
		            data1 = FXCollections.observableArrayList(series);
		            lineChart.setData(data1);
		        } else {
		            lineChart.getData().add(series);
		        }
	        
	        scene = new Scene(lineChart, 800, 400);
	        //lineChart.getData().add(series);
			
			stage.setScene(scene);
			stage.show();
	
	}

	public static void main(String[] args) {
		launch(args);
	}
}

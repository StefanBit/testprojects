package loader;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Region;
import model.Symbol;
import model.metrics.IMetric;

public class MetricLoader extends Region implements EventHandler {
	ProgressIndicator currentProgress;
	MetricLoaderTask metricLoaderTask;
	Thread currentThread;
	
	public MetricLoader(Symbol symbol, IMetric iMetric) {
		currentProgress = new ProgressIndicator();	
		this.getChildren().add(currentProgress);
		metricLoaderTask=new MetricLoaderTask(symbol,iMetric);
		currentProgress.progressProperty().bind(metricLoaderTask.progressProperty());
		currentThread = new Thread(metricLoaderTask);
		currentThread.start();
		metricLoaderTask.setOnSucceeded(this);
	}

	@Override
	public void handle(Event arg0) {
		this.getChildren().remove(0);
		this.getChildren().add(new Label(Double.toString((double) metricLoaderTask.getValue())));
		
	}

}

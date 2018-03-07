package loader;

import javafx.application.Platform;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
	public IMetric metric;
	Thread currentThread;
	BooleanPropertyBase ready;
	Symbol symbol;

	public MetricLoader(Symbol symbol, IMetric iMetric) {
		ready = new SimpleBooleanProperty(false);
		this.symbol = symbol;
		metric = iMetric;
		currentProgress = new ProgressIndicator();
		metricLoaderTask = new MetricLoaderTask(symbol, metric);
		currentProgress.progressProperty().bind(metricLoaderTask.progressProperty());
		currentThread = new Thread(metricLoaderTask);
		this.getChildren().add(currentProgress);
	}

	public void start() {
		// this.getChildren().add(currentProgress);
		currentThread.start();
		metricLoaderTask.setOnSucceeded(this);
	}

	@Override
	public void handle(Event arg0) {
		this.metric = metricLoaderTask.iMetric;
		ready.set(true);
		this.getChildren().remove(0);
		this.getChildren().add(new Label(Double.toString((double) metricLoaderTask.getValue())));
		System.out.println("MetricLoader finished "+ symbol.getName()+":"+metric.getClass().getSimpleName());
	}
}

package util.loader.Metric;

import application.MyProperties;
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
	public Symbol symbol;
	Boolean DEBUG;
	Label label;
	
	public MetricLoader(Symbol symbol, IMetric iMetric) {
		DEBUG=MyProperties.getDebugSettingFor("MetricLoaderDbg");
		ready = new SimpleBooleanProperty(false);
		this.symbol = symbol;
		metric = iMetric;
		currentProgress = new ProgressIndicator();
		metricLoaderTask = new MetricLoaderTask(symbol, metric);
		currentProgress.progressProperty().bind(metricLoaderTask.progressProperty());
		this.getChildren().add(currentProgress);
	}

	public void start() {
		// this.getChildren().add(currentProgress);
		currentThread = new Thread(metricLoaderTask);
		metricLoaderTask.setOnSucceeded(this);
		currentThread.start();
	}

	@Override
	public void handle(Event arg0) {
		//this.metric = metricLoaderTask.iMetric;
		System.out.println("MetricLoader finished "+ symbol.getName()+":"+metric.getClass().getSimpleName());
		this.getChildren().remove(0);
		//label=new Label(Double.toString((double) metricLoaderTask.getValue()));
		label=new Label(String.format ("%.2f", (double) metricLoaderTask.getValue()));
		this.getChildren().add(label);
		ready.set(true);
	}
}

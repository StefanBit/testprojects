package controller;

import gui.stage.BarChartStage;
import gui.view.BarChartView;
import gui.view.CandleStickChartView;
import gui.view.ExpandableTableView;
import gui.view.MyTablePane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import model.Symbol;

public class TradeFXApplicationController implements EventHandler, ChangeListener {
	
	@FXML
	TitledPane BarChartPane;
	@FXML
	TitledPane SymbolPane;
	@FXML
	TitledPane DataPane;
	@FXML
	TitledPane ChartPane;
	@FXML
	Pane ConsolePane;
	@FXML
	ProgressBar OverallProgress;
	@FXML
	Label Status;
	@FXML
	TitledPane AvaiableMetricsPane;
	
	MyTablePane symbolTablePane;
	MyTablePane dataTablePane;
	TradeFXBusinessController tfxc;
	BarChartView v;
	CandleStickChartView chartview;
	ExpandableTableView metricTableView;
	public TradeFXApplicationController() {
		System.out.println("Constructor");
	}

	@FXML
	public void initialize() {
		 System.out.println("Initialize");
		 tfxc = new TradeFXBusinessController();
		 tfxc = TradeFXBusinessController.getInstance();
		 tfxc.init();
		 OverallProgress.progressProperty().bind(tfxc.symbolsLoaderTask.progressProperty());
		 Status.textProperty().bind(tfxc.symbolsLoaderTask.messageProperty());
		 tfxc.symbolsLoaderTask.setOnSucceeded(this);
		 chartview = new CandleStickChartView();
		 symbolTablePane = new MyTablePane(model.Symbol.class);
		 dataTablePane = new MyTablePane(model.HistData.class);
		 SymbolPane.setContent(symbolTablePane);
		 ChartPane.setContent(chartview.lineChart);
		 DataPane.setContent(dataTablePane);
		// MetricListViev.setItems(FXCollections.observableList(tfxc.getModel().aMetrics));
		 metricTableView= new ExpandableTableView();
		 AvaiableMetricsPane.setContent(metricTableView);
		 v=new BarChartView();
		 BarChartPane.setContent(v);
		 symbolTablePane.datatable.getSelectionModel().selectedItemProperty().addListener(this);
		
		 //ConsolePane.getChildren().add(new Console());
	}

	@Override
	public void handle(Event arg0) {
		System.out.println("Event:"+arg0);
		System.out.println("Finished Task--- "+arg0.getSource().getClass().toString());
		
		if (arg0.getSource().getClass().toString().equals("class loader.SymbolLoaderTask")){
			System.out.println("Finished Task********** "+arg0.getSource().getClass().toString());
			symbolTablePane.setData(tfxc.getModel().getStockSymbols());
			OverallProgress.progressProperty().unbind();
			tfxc.loadHistData();
			OverallProgress.progressProperty().bind(tfxc.histDataLoaderWorker.progressProperty());
			Status.textProperty().bind(tfxc.histDataLoaderWorker.messageProperty());
			tfxc.histDataLoaderWorker.setOnSucceeded(this);
			
		}
		if (arg0.getSource().getClass().toString().equals("class loader.HistStockDataLoaderWorker")){
			v.load();
			System.out.println("Finished Task HistStockDataLoaderWorker ");
			OverallProgress.progressProperty().unbind();
			// Load Metrics
			System.out.println("load metrics");
			tfxc.loadSymbolMetrics();
			OverallProgress.progressProperty().bind(tfxc.metricLoaderWorker.progressProperty());
			Status.textProperty().bind(tfxc.metricLoaderWorker.messageProperty());
			tfxc.metricLoaderWorker.setOnSucceeded(this);
			//metricTableView.addColumn();
		}
		if (arg0.getSource().getClass().toString().equals("class loader.MetricLoaderWorker")){
			System.out.println("Finished Task MetricLoaderWorker");
			metricTableView.addColumn();
			Symbol s= tfxc.getModel().StockSymbols.get(0);
			dataTablePane.setData(tfxc.getModel().getHistDataFor(s));
			chartview.setDataForSymbol(s);
		}
	}

	@Override
	public void changed(ObservableValue arg0, Object arg1, Object arg2) {
		System.out.println("Datatablechanged "+arg0+" ö"+arg1+" ll "+arg2);
		chartview = new CandleStickChartView();
		chartview.setDataForSymbol((Symbol) arg2);
		dataTablePane=new MyTablePane(model.HistData.class);
		dataTablePane.setData(tfxc.getModel().getHistDataFor((Symbol) arg2));
		DataPane.setContent(dataTablePane);
		ChartPane.setContent(chartview.lineChart);
	}
}

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
	ExpandableTableView etv;
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
		 etv= new ExpandableTableView();
		 AvaiableMetricsPane.setContent(etv);
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
			OverallProgress.progressProperty().bind(tfxc.worker.progressProperty());
			Status.textProperty().bind(tfxc.worker.messageProperty());
			tfxc.worker.setOnSucceeded(this);
			
		}
		if (arg0.getSource().getClass().toString().equals("class loader.HistStockDataLoaderWorker")){
			v.load();
			System.out.println("Finished Task******---**** ");
			OverallProgress.progressProperty().unbind();
			Symbol s= tfxc.getModel().StockSymbols.get(0);
			chartview.setDataForSymbol(s);
			dataTablePane.setData(tfxc.getModel().getHistDataFor(s));
			tfxc.loadSymbolMetrics();
			etv.addColumn();
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

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
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import model.HistData;
import model.Symbol;
import model.TradeFXModel;

public class TradeFXApplicationController {
	
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
	
	MyTablePane<Symbol> symbolTablePane;
	MyTablePane<HistData> dataTablePane;
	TradeFXBusinessController tradeFXBuissnesController;
	TradeFXModel model;
	BarChartView barchartview;
	CandleStickChartView chartview;
	ExpandableTableView metricTableView;
	public TradeFXApplicationController() {
		System.out.println("Constructor");
	}

	@FXML
	public void initialize() {
		 System.out.println("Initialize");
		 tradeFXBuissnesController = TradeFXBusinessController.getInstance();
		 tradeFXBuissnesController.init();		 
		 chartview = new CandleStickChartView();
		 symbolTablePane = new MyTablePane(model.Symbol.class);
		 dataTablePane = new MyTablePane(model.HistData.class);
		 metricTableView= new ExpandableTableView();
		 barchartview=new BarChartView();
		 model=tradeFXBuissnesController.getModel();
		 SymbolPane.setContent(symbolTablePane);
		 DataPane.setContent(dataTablePane);
		 BarChartPane.setContent(barchartview);
		 AvaiableMetricsPane.setContent(metricTableView);
		 
		 bindProgressfrom(tradeFXBuissnesController.symbolsLoaderTask);
		 tradeFXBuissnesController.symbolsLoaderTask.setOnSucceeded(new SymbolLoaderTaskListener() );
		 //ConsolePane.getChildren().add(new Console());
	}
	
	private void addChartPane(){
		ChartPane.setContent(chartview.lineChart);
		symbolTablePane.datatable.getSelectionModel().selectedItemProperty().addListener(new SymbolPaneChangedListener());
	}
	
	private void bindProgressfrom(Task o) {
		Status.textProperty().unbind();
		OverallProgress.progressProperty().unbind();
		Status.textProperty().bind(((Task) o).messageProperty());
		OverallProgress.progressProperty().bind(((Task) o).progressProperty());
	}

		
	class SymbolPaneChangedListener implements ChangeListener{

		@Override
		public void changed(ObservableValue arg0, Object arg1, Object arg2) {
			System.out.println("Datatablechanged "+arg0+" ö"+arg1+" ll "+arg2);
			chartview = new CandleStickChartView();
			chartview.setDataForSymbol((Symbol) arg2);
			dataTablePane=new MyTablePane(model.HistData.class);
			dataTablePane.setData(model.getHistDataFor((Symbol) arg2));
			DataPane.setContent(dataTablePane);
			ChartPane.setContent(chartview.lineChart);
		}
		
	}

	class SymbolLoaderTaskListener implements EventHandler{
		@Override
		public void handle(Event event) {
			symbolTablePane.setData(model.getStockSymbols());
			tradeFXBuissnesController.loadHistData();
			bindProgressfrom(tradeFXBuissnesController.histDataLoaderWorker); 
			tradeFXBuissnesController.histDataLoaderWorker.setOnSucceeded(new HistStockDataLoaderWorkerListener());
		}
	}
	class HistStockDataLoaderWorkerListener implements EventHandler{
		@Override
		public void handle(Event event) {
			barchartview.load();
			System.out.println("Finished Task HistStockDataLoaderWorker ");
			tradeFXBuissnesController.loadSymbolMetrics(new MetricLoaderWorkerListener());
			//tradeFXBuissnesController.metricLoaderWorker.setOnSucceeded(new MetricLoaderWorkerListener());
			bindProgressfrom(tradeFXBuissnesController.metricLoaderWorker);
		}
	}
	class MetricLoaderWorkerListener implements EventHandler{
		@Override
		public void handle(Event event) {
			System.out.println("Finished Task MetricLoaderWorker "+tradeFXBuissnesController.metricLoaderWorker.getProgress());
			//Status.textProperty().unbind();
			metricTableView.addColumn();
			Symbol s= model.StockSymbols.get(0);
			dataTablePane.setData(model.getHistDataFor(s));
			addChartPane();
			chartview.setDataForSymbol(s);
			//ChartPane.setContent(chartview.lineChart);
			//symbolTablePane.datatable.getSelectionModel().selectedItemProperty().addListener(new SymbolPaneChangedListener());
		}
	}
}







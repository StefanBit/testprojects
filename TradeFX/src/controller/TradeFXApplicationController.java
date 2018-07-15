package controller;

import java.util.ArrayList;

import gui.BarChart.BarChartStage;
import gui.BarChart.BarChartView;
import gui.CandleChart.CandleStickChartView;
import gui.Console.Console;
import gui.DetailPage.DetailPage;
import gui.Table.ExpandableTableView;
import gui.Table.MyTablePane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
//	@FXML
//	Pane ConsolePane;
	@FXML
	ProgressBar OverallProgress;
	@FXML
	Label Status;
	@FXML
	TitledPane AvaiableMetricsPane;
	@FXML
	TitledPane DetailPane;
	@FXML
	Button ReloadButton;

	MyTablePane<Symbol> symbolTablePane;
	MyTablePane<HistData> dataTablePane;
	TradeFXBusinessController tradeFXBuissnesController;
	static TradeFXApplicationController tradeFXApplicationController;
	TradeFXModel model;
	BarChartView barchartview;
	CandleStickChartView chartview;
	ExpandableTableView metricTableView;
	SymbolPaneChangedListener symbolPaneChangeListener;
	DetailPage detailPage; 

	public static TradeFXApplicationController getInstance() {
		return tradeFXApplicationController;
	}

	public TradeFXApplicationController() {
		System.out.println("Constructor");
		tradeFXApplicationController = this;
	}

	@FXML
	public void initialize() {
		System.out.println("Initialize GUI");
		tradeFXBuissnesController = TradeFXBusinessController.getInstance();
		model = tradeFXBuissnesController.getModel();
		// Create empty Panes
		chartview = new CandleStickChartView();
		symbolTablePane = new MyTablePane(model.Symbol.class);
		dataTablePane = new MyTablePane(model.HistData.class);
		metricTableView = new ExpandableTableView();
		barchartview = new BarChartView();
		detailPage = new DetailPage();
		DetailPane.setContent(detailPage);
		// Set Content to Window Panes
		SymbolPane.setContent(symbolTablePane);
		symbolPaneChangeListener = new SymbolPaneChangedListener();
		DataPane.setContent(dataTablePane);
		BarChartPane.setContent(barchartview);
		AvaiableMetricsPane.setContent(metricTableView);
		tradeFXBuissnesController.init();
		ReloadButton.setOnAction(new ReloadButtonActionListener());
	}
	
	
	public void changePanes(){
		changeChartPane(model.selectedSymbol);			
		changeDatatable(model.getHistDataFor(model.selectedSymbol));
		changeDetailPage(model.selectedSymbol);
	}
	
	public void changeDatatable(ArrayList<HistData> data){
		dataTablePane = new MyTablePane(model.HistData.class);
		dataTablePane.setData(data);
		DataPane.setContent(dataTablePane);
	}
	
	public void changeDetailPage(Symbol s) {
		detailPage.changeData(s);
	}
	
	public void changeChartPane(Symbol s){
		chartview = new CandleStickChartView();
		ChartPane.setContent(chartview);
		chartview.setDataForSymbol((Symbol) s);
		symbolTablePane.datatable.getSelectionModel().selectedItemProperty().removeListener(symbolPaneChangeListener);
		symbolTablePane.datatable.getSelectionModel().selectedItemProperty().addListener(symbolPaneChangeListener);
	}

	//////////// Listeners

	public void bindProgressfrom(Task o) {
		Status.textProperty().unbind();
		Status.textProperty().bind(((Task) o).messageProperty());
		OverallProgress.progressProperty().unbind();
		OverallProgress.progressProperty().bind(((Task) o).progressProperty());
	}

	class ReloadButtonActionListener implements EventHandler {
		@Override
		public void handle(Event arg0) {
			System.out.println("reload");
			tradeFXBuissnesController.loadHistData();
		}
	}

	class SymbolPaneChangedListener implements ChangeListener {
		@Override
		public void changed(ObservableValue arg0, Object arg1, Object arg2) {
			System.out.println("Datatablechanged for " + arg0 + " from " + arg1 + " to " + arg2);
			model.selectedSymbol=(Symbol) arg2;
			changePanes();
		}
	}

}

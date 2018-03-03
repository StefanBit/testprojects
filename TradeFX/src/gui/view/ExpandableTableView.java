package gui.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import controller.TradeFXBusinessController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import loader.MetricLoader;
import loader.MetricLoaderTask;
import model.Symbol;
import model.TradeFXModel;
import model.metrics.IMetric;


public class ExpandableTableView extends TableView {

	private ObservableList<Map<String, Object>> items;
	private TradeFXModel model;
	private ArrayList<IMetric> aMetric;
	
	public ExpandableTableView() {
		items = FXCollections.<Map<String,Object>>observableArrayList() ;
		model=TradeFXBusinessController.getInstance().getModel();
	}

	public void addColumn() {
		ArrayList<Symbol> StockSymbols = model.getStockSymbols();
		String symbolColumnKey= "Symbol";

		// Set Columnnames and CellValueFactorys
		// first Col
		TableColumn<Map,String> SymbolCol= new TableColumn<>(symbolColumnKey);
		SymbolCol.setCellValueFactory(new MapValueFactory<>(symbolColumnKey));
		this.getColumns().add(SymbolCol);
		// Metrics Col
		for (IMetric iMetric : model.aMetrics) {
			TableColumn<Map,String> metricCol= new TableColumn<>(iMetric.getClass().getSimpleName());
			metricCol.setCellValueFactory(new MapValueFactory<>(iMetric.getClass().getSimpleName()));
			this.getColumns().add(metricCol);
		}

		this.setItems(model.items);
	}
}

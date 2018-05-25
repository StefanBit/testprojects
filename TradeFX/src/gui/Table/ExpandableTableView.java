package gui.Table;

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
import model.Symbol;
import model.TradeFXModel;
import model.metrics.IMetric;
import util.loader.Metric.MetricLoader;
import util.loader.Metric.MetricLoaderTask;

public class ExpandableTableView extends TableView {

	private ObservableList<Map<String, Object>> items;
	private TradeFXModel model;
	private ArrayList<IMetric> aMetric;
	Boolean loaded = false;

	public ExpandableTableView() {
		items = FXCollections.<Map<String, Object>>observableArrayList();
		model = TradeFXBusinessController.getInstance().getModel();
	}

	public void addColumn() {
		if (loaded == false) {
			ArrayList<Symbol> StockSymbols = model.getStockSymbols();
			String symbolColumnKey = "Symbol";

			// Set Columnnames and CellValueFactorys
			// first Col
			TableColumn<Map, String> SymbolCol = new TableColumn<>(symbolColumnKey);
			SymbolCol.setCellValueFactory(new MapValueFactory<>(symbolColumnKey));
			this.getColumns().add(SymbolCol);
			// Metrics Col
			for (IMetric metric : model.aMetrics) {
				TableColumn<Map, String> metricCol = new TableColumn<>(metric.getClass().getSimpleName());
				metricCol.setCellValueFactory(new MapValueFactory<>(metric.getClass().getSimpleName()));
				this.getColumns().add(metricCol);
			}

			this.setItems(model.MetricLoaderMaps);
			loaded = true;
		}
	}
}

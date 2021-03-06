package gui.Table;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Map;

import application.CandleStickChartStage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import model.HistData;
import model.Symbol;
import model.TradeFXModel;
import model.metrics.ArithmeticMean;
import model.metrics.ProMean;
import util.loader.HistoricalDataLoader.HistStockDataLoaderTask;
import util.loader.HistoricalDataLoader.HistoricalDataFromAlphavantage;

public class StocksStage<T> extends Stage {

	ArrayList<ProgressIndicator> pbs;
	ArrayList<Button> buttons;
	ArrayList<HistStockDataLoaderTask> tasks;
	ArrayList<Thread> threads;

	int currentItem;

	public StocksStage() {

		build();
		show();
	}

	public void build() {
		tasks = new ArrayList();
		pbs = new ArrayList();
		threads = new ArrayList();
		buttons = new ArrayList();
		HBox hBox = new HBox();
		Scene SymbolScene = new Scene(hBox, 800, 600);
		StackPane table = new MyTablePane(TradeFXModel.StockSymbols,Symbol.class);
		hBox.getChildren().add(table);
		
		setScene(SymbolScene);
		setTitle("StockStage");

		for (Map.Entry<Symbol, ArrayList<HistData>> entry : TradeFXModel.StockHistData.entrySet()) {
			Symbol currentSymbol = entry.getKey();
			ProgressIndicator currentProgressindicator = new ProgressIndicator();
			currentProgressindicator.progressProperty().bind(TradeFXModel.tasks.get(currentSymbol).progressProperty());
			Button currentButton = new Button(currentSymbol.getName());
			buttons.add(currentButton);
			currentButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					CandleStickChartStage candleStickChartStage;
					
					candleStickChartStage = new CandleStickChartStage();
					candleStickChartStage.setTitle("HistData");
					candleStickChartStage.show(currentSymbol);
				}
			});
			hBox.getChildren().addAll(currentButton, currentProgressindicator);
		}
	}

}

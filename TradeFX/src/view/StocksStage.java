package view;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Map;

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
import loader.HistStockDataLoader;
import loader.HistStockDataLoaderTask;
import model.FloatingMean;
import model.HistData;
import model.Symbol;
import model.TradeFXModel;

public class StocksStage<T> extends Stage {

	ArrayList<HistStockDataLoaderTask> tasks;
	ArrayList<ProgressIndicator> pbs;
	ArrayList<Button> buttons;
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
		Scene SymbolScene = new Scene(hBox, 800, 400);
		TableView table = new MyTableView(TradeFXModel.StockSymbols);

		hBox.getChildren().add(table);
		setScene(SymbolScene);
		setTitle("StockStage");

		for (Map.Entry<Symbol, ArrayList<HistData>> entry : TradeFXModel.StockHistData.entrySet()) {
			Symbol currentSymbol = entry.getKey();
			HistStockDataLoaderTask currenttask = new HistStockDataLoaderTask();
			ProgressIndicator currentProgressindicator = new ProgressIndicator();
			Button currentButton = new Button(currentSymbol.getName());
			Thread currentThread;
			tasks.add(currenttask);
			currenttask.alSymbol = currentSymbol;
			currentProgressindicator.progressProperty().bind(currenttask.progressProperty());
			buttons.add(currentButton);
			currentThread = new Thread(currenttask);
			currentThread.start();
			currentButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					
					new ChartStage(TradeFXModel.StockHistData.get(currentSymbol));
					FloatingMean fm = new FloatingMean();
					new ChartStage(fm.calc(TradeFXModel.StockHistData.get(currentSymbol)));
				}
			});
			hBox.getChildren().addAll(currentButton, currentProgressindicator);
		}
	}

}

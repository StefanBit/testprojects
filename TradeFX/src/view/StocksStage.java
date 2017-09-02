package view;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import application.HistStockDataLoader;
import application.HistStockDataLoaderTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Scene;
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
import model.Symbol;

public class StocksStage<T> extends Stage {
	
	Class<T> c;
	ArrayList<TableColumn> colNames;
	ArrayList<HistStockDataLoaderTask> tasks;
	ArrayList<ProgressIndicator> pbs;
	ArrayList<Thread> threads;
	
	public StocksStage(ArrayList<T> ol) {
		T t;
		tasks=new ArrayList();
		pbs=new ArrayList();
		threads=new ArrayList();
		HBox hBox = new HBox();
		Scene SymbolScene = new Scene(hBox, 800, 400);
		TableView table = new MyTableView(ol);
		t = ol.get(1);
		
		hBox.getChildren().add(table);
		setScene(SymbolScene);
		setTitle("StockStage");
		
		for (int i = 0; i < 1; i++) {

			tasks.add(new HistStockDataLoaderTask());
			tasks.get(i).alSymbol=(Symbol) ol.get(i);
			pbs.add(new ProgressIndicator());
			pbs.get(i).progressProperty().bind(tasks.get(i).progressProperty());
			threads.add(new Thread(tasks.get(i)));
			threads.get(i).start();
			hBox.getChildren().add(pbs.get(i));
		}
		
		
		show();
	}


}

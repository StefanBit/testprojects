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
import model.FloatingMean;
import model.HistData;
import model.Symbol;

public class StocksStage<T> extends Stage implements EventHandler{
	
	Class<T> c;
	ArrayList<TableColumn> colNames;
	ArrayList<HistStockDataLoaderTask> tasks;
	ArrayList<ProgressIndicator> pbs;
	ArrayList<Button> buttons;
	ArrayList<Thread> threads;
	int currentItem;
	
	public StocksStage(ArrayList<T> ol) {
		T t;
		tasks=new ArrayList();
		pbs=new ArrayList();
		threads=new ArrayList();
		buttons=new ArrayList();
		HBox hBox = new HBox();
		Scene SymbolScene = new Scene(hBox, 800, 400);
		TableView table = new MyTableView(ol);
		t = ol.get(1);
		
		hBox.getChildren().add(table);
		setScene(SymbolScene);
		setTitle("StockStage");
		
		for (int i = 0; i < ol.size(); i++) {
			currentItem=i;
			tasks.add(new HistStockDataLoaderTask());
			tasks.get(i).alSymbol=(Symbol) ol.get(i);
			pbs.add(new ProgressIndicator());
			pbs.get(i).progressProperty().bind(tasks.get(i).progressProperty());
			buttons.add(new Button(String.valueOf(i)));
			buttons.get(i).setShape(pbs.get(i).getShape());
			threads.add(new Thread(tasks.get(i)));
			threads.get(i).start();
			hBox.getChildren().add(pbs.get(i));
			buttons.get(i).setOnAction(this);
//			tasks.get(i).setOnSucceeded((WorkerStateEvent event) -> {
//				System.out.println(event);
//				ArrayList<Symbol> alSymbols=null;
//				alSymbols=task.getValue();
//				new StocksStage( alSymbols);
//
//			});
			hBox.getChildren().add(buttons.get(i));
		}
		
		
		show();
	}

	@Override
	public void handle(Event event) {
		ArrayList<HistData> hd;
		int i=0;
		while (!event.getSource().equals(buttons.get(i))) {
			i++;
		}
			
			hd=(ArrayList<HistData>) tasks.get(i).getValue();
			System.out.println(i);
		new ChartStage(hd);
		FloatingMean fm= new FloatingMean();
		new ChartStage(fm.calc(hd));
	}
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
	}

}

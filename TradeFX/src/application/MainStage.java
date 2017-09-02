package application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import database.DAOHsqlImpl;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.HistData;
import model.Symbol;
import view.ChartView;
import view.MyTableView;
import view.StocksStage;

public class MainStage extends Stage {
	public MainStage() {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 800, 400);

		// root.setRight(new Console());
		ChartView view = new ChartView();

		final Task<ArrayList<Symbol>> task = new Task<ArrayList<Symbol>>() {

			@Override
			protected ArrayList<Symbol> call() throws Exception {
				System.out.println("Start Task");
				// Load Symbols
				DAOHsqlImpl<Symbol> SymbolsLoader = new DAOHsqlImpl(Symbol.class);
				ArrayList<Symbol> alSymbols = SymbolsLoader.getAll();

				System.out.println("Stop Task");
				// Load HistData

//				HistStockDataLoader l = new HistStockDataLoader();
//				Calendar cal = Calendar.getInstance();
//				Date today = cal.getTime();
//				cal.add(Calendar.MONTH, -1); // to get previous year add -1
//				Date lastYear = cal.getTime();
//				ArrayList<HistData> alHistData;
//				alHistData = l.load(alSymbols.get(0), lastYear, new Date());
//
//				System.out.println("Stop Task");
//				DAOHsqlImpl<HistData> sHistData = new DAOHsqlImpl(HistData.class);
//				ArrayList<HistData> al2;
//				sHistData.deleteAll();
//				sHistData.insertAll(alHistData);
//				al2 = sHistData.getAll();
				
				updateProgress(1, 1);
				return alSymbols;
			}

		};
		
		task.setOnSucceeded((WorkerStateEvent event) -> {
			System.out.println(event);
			ArrayList<Symbol> alSymbols=null;
			alSymbols=task.getValue();
			new StocksStage( alSymbols);

		});

		ProgressBar pb = new ProgressBar();
		pb.progressProperty().bind(task.progressProperty());
		root.setBottom(pb);

		Thread thread = new Thread(task);
		thread.start();

		// Load HistData from DB and show Table / Graph
		//
		// new TableStage( al2);
		// view.setData(al2);
		// root.setCenter(view.lineChart);
		// TODO Auto-generated method stub

		// Text
		// Stage secondStage = new Stage();

		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		setTitle("MyTradeFX");
		// secondStage.setTitle("2");

		scene.heightProperty().addListener(view);
		// root.getChildren().add(view.lineChart);

		setScene(scene);
		show();

	}

}

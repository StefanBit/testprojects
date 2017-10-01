package view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import controller.TradeFXController;
import database.DAOHsqlImpl;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.HistData;
import model.Symbol;
import model.TradeFXModel;

public class MainStage extends Stage {
	public MainStage() {

		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 800, 400);

		TradeFXController.init();

		// Menu
		MenuBar menuBar = new MenuBar();
		Menu menuStocks = new Menu("Stocks");
		menuBar.getMenus().add(menuStocks);
		MenuItem stocks = new MenuItem("stocks");
		stocks.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				new StocksStage();
			}
		});
		MenuItem portfolio = new MenuItem("Portfolio");
		portfolio.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				new PortfolioStage();
			}
		});

		menuStocks.getItems().addAll(portfolio, stocks);


		//Progressbar
		ProgressBar pb = new ProgressBar();
		pb.progressProperty().bind(TradeFXController.symbolsLoaderTask.progressProperty());
		
		
		//Layout
		setTitle("MyTradeFX");
		root.setBottom(pb);
		root.setTop(menuBar);
		//root.setRight(new Console());
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		setScene(scene);
		show();

	}

}

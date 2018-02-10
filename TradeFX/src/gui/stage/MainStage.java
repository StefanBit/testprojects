package gui.stage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import application.Console;
import controller.TradeFXBusinessController;
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
	
	BorderPane root;
	Scene scene;
	MenuBar menuBar;
	Menu menuStocks;
	MenuItem stocks;
	MenuItem portfolio;
	ProgressBar pb;
	TradeFXBusinessController tfxc;
	public MainStage() {

		//Init
		root = new BorderPane();
		scene = new Scene(root, 800, 400);
		menuBar = new MenuBar();
		menuStocks = new Menu("Stocks");
		stocks = new MenuItem("stocks");
		portfolio = new MenuItem("Portfolio");
		pb = new ProgressBar();

		TradeFXBusinessController tfxc = new TradeFXBusinessController();
		tfxc.init();

		//Layout
		setTitle("MyTradeFX");
		root.setBottom(pb);
		root.setTop(menuBar);
		//root.setRight(new Console());
		
		menuBar.getMenus().add(menuStocks);
		menuStocks.getItems().addAll(stocks,portfolio);
		//Progressbar
		pb.progressProperty().bind(tfxc.symbolsLoaderTask.progressProperty());
		

		// Menu Actions
		stocks.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				new StocksStage();
			}
		});
		portfolio.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				new PortfolioStage();
			}
		});

		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		setScene(scene);
		show();
	}

}

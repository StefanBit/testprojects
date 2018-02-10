package application;

import controller.TradeFXBusinessController;
import gui.stage.BarChartStage;
import gui.view.BarChartView;
import gui.view.MyTablePane;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;

public class TradeFXApplicationController {
	
	@FXML
	TitledPane BarChartPane;
	@FXML
	TitledPane SymbolPane;
	@FXML
	Pane ConsolePane;
	
	public TradeFXApplicationController() {
		System.out.println("Constructor");
	}

	@FXML
	public void initialize() {
		 System.out.println("initialize");
		 TradeFXBusinessController tfxc;
		 tfxc = new TradeFXBusinessController();
		 tfxc.init();
		 tfxc = TradeFXBusinessController.tfxc;
		 //ConsolePane.getChildren().add(new Console());
		 BarChartPane.setContent(new BarChartView());
		 SymbolPane.setContent(new MyTablePane(tfxc.getModel().getStockSymbols(), model.Symbol.class));
	}
}

package view;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.HistData;

public class ChartStage extends Stage {
	public ChartStage(ArrayList<HistData> data) {
		HBox hBox = new HBox();
		ChartView chartview = new ChartView();
		chartview.setData(data);
		Scene SymbolScene = new Scene(hBox, 800, 400);
		setScene(SymbolScene);
		setTitle("StockStage");
		hBox.getChildren().add(chartview.lineChart);
		show();
	}
}

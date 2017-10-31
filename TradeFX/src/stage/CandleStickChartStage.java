package stage;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.HistData;
import view.CandleStickChartView;
import view.ChartView;

public class CandleStickChartStage extends Stage{
	public CandleStickChartStage(ArrayList<HistData> data) {
		HBox hBox = new HBox();
		CandleStickChartView chartview = new CandleStickChartView();
		chartview.setData(data);
		Scene SymbolScene = new Scene(hBox, 800, 800);
		setScene(SymbolScene);
		hBox.getChildren().add(chartview.lineChart);
		show();
	}
}

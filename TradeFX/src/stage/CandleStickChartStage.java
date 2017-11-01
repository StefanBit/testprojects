package stage;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.HistData;
import view.CandleStickChartView;
import view.ChartView;

public class CandleStickChartStage extends Stage{
	public CandleStickChartStage(ArrayList<HistData> data) {
		HBox hBox = new HBox();
		BorderPane bp = new BorderPane();
		
		CandleStickChartView chartview = new CandleStickChartView();
		chartview.setData(data);
		Scene SymbolScene = new Scene(bp);
		setScene(SymbolScene);
		bp.setCenter(chartview.lineChart);
		//hBox.getChildren().add(chartview.lineChart);
		show();
	}
}

package gui.CandleChart;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.HistData;
import model.Symbol;
import model.TradeFXModel;

public class CandleStickChartStage extends Stage{
	CandleStickChartView chartview;
	
	public CandleStickChartStage() {
		BorderPane bp = new BorderPane();
		chartview = new CandleStickChartView();
		Scene SymbolScene = new Scene(bp);
		setScene(SymbolScene);
		bp.setCenter(chartview.lineChart);
		show();
	}
	
	public void show(Symbol symbol){
		chartview.setDataForSymbol(symbol);
		
	}
}

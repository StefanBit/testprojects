package stage;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.HistData;
import model.Symbol;
import model.TradeFXModel;
import view.CandleStickChartView;

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


//	public CandleStickChartStage(ArrayList<HistData> data) {
//		HBox hBox = new HBox();
//		BorderPane bp = new BorderPane();
//		chartview = new CandleStickChartView();
//		chartview.setData(data);
//		Scene SymbolScene = new Scene(bp);
//		setScene(SymbolScene);
//		bp.setCenter(chartview.lineChart);
//		//hBox.getChildren().add(chartview.lineChart);
//		show();
//	}
//	
//	
//	public CandleStickChartStage(Symbol symbol) {
//		ArrayList<HistData> data;
//		HBox hBox = new HBox();
//		BorderPane bp = new BorderPane();
//		chartview = new CandleStickChartView();
//		data= TradeFXModel.getHistDataFor(symbol);
//		chartview.setData(data);
//		Scene SymbolScene = new Scene(bp);
//		setScene(SymbolScene);
//		bp.setCenter(chartview.lineChart);
//		//hBox.getChildren().add(chartview.lineChart);
//		show();
//	}
//	
//	
//	
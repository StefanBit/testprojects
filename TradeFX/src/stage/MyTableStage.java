package stage;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.HistData;
import view.CandleStickChartView;
import view.MyTablePane;
import view.MyTableView;

public class MyTableStage extends Stage{
	public MyTableStage(ArrayList<HistData> data) {
		
		HBox hBox = new HBox();
		BorderPane bp = new BorderPane();
		MyTablePane mtp = new MyTablePane(data, HistData.class);
		this.setTitle(this.getClass().getName()+" for "+ HistData.class );
		Scene SymbolScene = new Scene(bp);
		setScene(SymbolScene);
		bp.setCenter(mtp);
		show();
	}
}

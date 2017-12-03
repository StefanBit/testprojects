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
	
	public MyTableStage(ArrayList data, Class c) {
		
		this.setTitle(this.getClass().getName()+" for "+ c );
		BorderPane bp = new BorderPane();
		MyTablePane mtp = new MyTablePane(data, c);
		Scene SymbolScene = new Scene(bp);
		setScene(SymbolScene);
		bp.setCenter(mtp);
		show();
	}
}

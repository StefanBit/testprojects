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
	public MyTableStage(ArrayList data,Object c) {
		
		HBox hBox = new HBox();
		BorderPane bp = new BorderPane();
		Class ArgClass;
		ArgClass=c.getClass();	
		if (data.size()==0){
			data.add(c);
		}
			
		System.out.println(ArgClass);
		MyTablePane mtp = new MyTablePane(data, ArgClass);
		this.setTitle(this.getClass().getName()+" for "+ HistData.class );
		Scene SymbolScene = new Scene(bp);
		setScene(SymbolScene);
		bp.setCenter(mtp);
		show();
	}
}

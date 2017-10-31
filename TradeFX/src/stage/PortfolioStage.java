package stage;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.HistData;
import model.TradeFXModel;
import view.MyTableView;
import view.PortfolioView;

public class PortfolioStage extends Stage {
	public PortfolioStage() {
		HBox hBox = new HBox();
		Scene SymbolScene = new Scene(hBox, 800, 400);
		setScene(SymbolScene);
		setTitle("Portfolios");
		PortfolioView pv=new PortfolioView();
		pv.setData();
		TableView table = new MyTableView(TradeFXModel.trades);
		hBox.getChildren().add(table);
		hBox.getChildren().add(pv.lineChart);
		show();
	}

}

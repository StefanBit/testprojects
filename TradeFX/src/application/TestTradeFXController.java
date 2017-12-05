package application;

import controller.TradeFXController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.TradeFXModel;

public class TestTradeFXController extends Application{


	@Override
	public void start(Stage arg0) throws Exception {

		TradeFXController t = new TradeFXController();
		t.init();
		while (!(TradeFXModel.symbolsLoaded))
		{
			System.out.println((TradeFXModel.histDataLoaded&TradeFXModel.symbolsLoaded));
			
		}
		TradeFXModel.showState();
		System.exit(0);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

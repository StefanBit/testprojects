package stage;

import java.util.ArrayList;

import application.Console;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

	public class ConsoleStage extends Stage{
		
		public ConsoleStage() {
			
		
			BorderPane bp = new BorderPane();
			Console con = new Console();
			Scene SymbolScene = new Scene(bp);
			setScene(SymbolScene);
			bp.setCenter(con);
			System.out.println("Test");
			show();
		}
}

package gui;



import controller.state.StateMachine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import model.Symbol;


public class SymbolsUIController{
	
	@FXML
	Pane pane;
//	@FXML
//	ListView symbolsList;
	@FXML 
	TextField textField;
	
	public SymbolsUIController() {
		// TODO Auto-generated constructor stub
	}

	public void initialize() {
		//StateMachine stateMachine = StateMachine.getInstance();
		//System.out.println(TradeFXModel.StockSymbols);
		// An entsprechendem State als Observer registrieren.
		StateMachine.getInstance().symbolsLoadedState.registerObserver(this);
//		this.symbolsList =new ListView<String>();
//		System.out.println("initialize FX"+symbolsList);
		
		//SymbolsList.setItems(FXCollections.observableList(TradeFXModel.StockSymbols));

	}

	public void set() {
		System.out.println("hallO");
	//	System.out.println(StateMachine.getInstance().model.StockSymbols);
		ObservableList<Symbol> list =FXCollections.observableArrayList(StateMachine.getInstance().model.StockSymbols);
		System.out.println(":"+list);
	//	symbolsList.setItems(FXCollections.observableArrayList("kljl","kpkplist"));
		textField.setText(list.toString());
		System.out.println("hallO");
	}
}

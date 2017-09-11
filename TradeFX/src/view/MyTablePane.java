package view;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Symbol;
import model.TradeFXModel;

public class MyTablePane<T> extends StackPane implements EventHandler<ActionEvent> {
	TableView table;
	ArrayList<T> ol;
	Class c;

	public MyTablePane(ArrayList<T> ol,Class c) {
		super();
		this.c=c;
		this.ol = ol;
		VBox vBox = new VBox();
		Button b = new Button("neu");
		table = new MyTableView(ol);
		this.getChildren().addAll(vBox);
		vBox.getChildren().addAll(table, b);
		b.setOnAction(this);
	}

	@Override
	public void handle(ActionEvent arg0) {
		T object=null;
		try {
			object = (T) c.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		table.getItems().add(object);

	}
}

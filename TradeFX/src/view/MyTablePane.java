package view;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;

import database.DAOHsqlImpl;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.HistData;
import model.Symbol;
import model.TradeFXModel;

public class MyTablePane<T> extends StackPane implements EventHandler<ActionEvent> {
	TableView table;
	ArrayList<T> ol;
	TableView inserttable;
	Class c;

	public MyTablePane(ArrayList<T> ol, Class c) {
		super();
		this.c = c;
		this.ol = ol;
		BeanInfo info;
		VBox vBox = new VBox();
		HBox hBox = new HBox();
		Button b = new Button("insert");
		Button b2 = new Button("save");
		Button b3 = new Button("drop");

		
		TextField tf = new TextField();
		table = new MyTableView(ol);
		ArrayList<T> ol2;
		ol2 = new ArrayList<T>();
		
		T newentiti = null;
		try {
			newentiti = (T) c.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		System.out.println(newentiti.getClass());
		
		ol2.add(newentiti);
		inserttable = new MyTableView(ol2);
		inserttable.setPrefHeight(100);
		this.getChildren().addAll(vBox);
		vBox.getChildren().addAll(table);
		vBox.getChildren().addAll(inserttable);
		hBox.getChildren().addAll(b,b2,b3);
		vBox.getChildren().addAll(hBox);
		b.setOnAction(this);
		b2.setOnAction(this);
		b3.setOnAction(this);
	}

	@Override
	public void handle(ActionEvent arg0) {
		T object = null;
		try {
			object = (T) c.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		// table.getItems().add(object);
		
		String command = ((Button) arg0.getSource()).getText();

		if (command.equals("insert")) {

			table.getItems().add(inserttable.getItems().get(0));
		}
		if (command.equals("save")) {
			DAOHsqlImpl<T> sSymbol = new DAOHsqlImpl(getArrayListsClass().getClass());
			sSymbol.deleteAll();
			ArrayList alt = new ArrayList<>(Arrays.asList(table.getItems().toArray()));
			sSymbol.insertAll(alt);
		}
		if (command.equals("drop")) {
			DAOHsqlImpl<T> sSymbol = new DAOHsqlImpl(getArrayListsClass().getClass());
			sSymbol.deleteAll();
			ArrayList alt = new ArrayList<>(Arrays.asList(table.getItems().toArray()));
			sSymbol.dropTable();
		}

	}
	
	private T getArrayListsClass(){
		T newentiti = null;
		try {
			newentiti = (T) c.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return newentiti;
	}

}

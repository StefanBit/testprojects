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
	TableView datatable,inserttable;
	ArrayList<T> dataTableObjectList,insertTableObjectList;
	Class c;
	Button buttonInsert,buttonSave; 
	VBox vBox;
	HBox hBox;
	
	public MyTablePane(ArrayList<T> dataTableObjectList, Class c) {
		super();
		this.c = c;
		this.dataTableObjectList = dataTableObjectList;
		vBox = new VBox();
		hBox = new HBox();
		buttonInsert = new Button("insert");
		buttonInsert.setOnAction(this);
		buttonSave = new Button("save");
		buttonSave.setOnAction(this);

		datatable = new MyTableView(dataTableObjectList, c);
		
		insertTableObjectList = new ArrayList<T>();
		T newentiti = null;
		try {
			newentiti = (T) c.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		insertTableObjectList.add(newentiti);
		inserttable = new MyTableView(insertTableObjectList,c);
		inserttable.setPrefHeight(100);
		this.getChildren().addAll(vBox);
		vBox.getChildren().add(datatable);
		vBox.getChildren().add(inserttable);
		vBox.getChildren().add(hBox);
		hBox.getChildren().addAll(buttonInsert,buttonSave);
	}
	
	public void addNewInsertable(){
		insertTableObjectList = new ArrayList<T>();
		T newentiti = null;
		try {
			newentiti = (T) c.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		insertTableObjectList.add(newentiti);
		inserttable = new MyTableView(insertTableObjectList,c);
		inserttable.setPrefHeight(100);
		vBox.getChildren().remove(1);
		vBox.getChildren().add(1,inserttable);
	}
	

	@Override
	public void handle(ActionEvent arg0) {
		T object = null;
		String command = ((Button) arg0.getSource()).getText();

		if (command.equals("insert")) {
			try {
				object = (T) c.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
			System.out.println("llll:"+(inserttable.getItems().get(0)));
			datatable.getItems().add(inserttable.getItems().get(0));
			addNewInsertable();
		}
		if (command.equals("save")) {
			DAOHsqlImpl<T> sSymbol = new DAOHsqlImpl(Symbol.class);
			sSymbol.deleteAll();
			ArrayList alt = new ArrayList<>(Arrays.asList(datatable.getItems().toArray()));
			sSymbol.insertAll(alt);
		}
	}
}

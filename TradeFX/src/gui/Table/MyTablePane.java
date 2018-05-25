package gui.Table;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import util.database.DAOHsqlImpl;

/**
 * 
 * Stellt eine Tabelle dar.
 * Option zum einfügen, löschen.
 * 
 */

public class MyTablePane<T> extends StackPane implements EventHandler<ActionEvent>, ChangeListener {

	static Boolean DEBUG=true; 
	public MyTableView datatable,inserttable;
	ArrayList<T> dataTableObjectList,insertTableObjectList;
	Class c;
	Button buttonInsert,buttonSave; 
	VBox vBox;
	HBox hBox;
	
	
	public MyTablePane(Class c) {
		super();
		this.c = c;
		vBox = new VBox();
		hBox = new HBox();
		buttonInsert = new Button("insert");
		buttonInsert.setOnAction(this);
		buttonSave = new Button("save");
		buttonSave.setOnAction(this);
		datatable = new MyTableView(c);
		this.getChildren().addAll(vBox);
		addNewInsertable();
		vBox.getChildren().add(datatable);
		vBox.getChildren().add(inserttable);
		vBox.getChildren().add(hBox);
		hBox.getChildren().addAll(buttonInsert,buttonSave);
		
	}
	
	public MyTablePane(ArrayList<T> dataTableObjectList, Class c) {
		this(c);
		//setData(dataTableObjectList);
	}
	
	public void setData(ArrayList<T> dataTableObjectList) {
		this.dataTableObjectList = dataTableObjectList;
		
		datatable.setData(dataTableObjectList);
		datatable.getSelectionModel().selectedItemProperty().addListener(this);
		datatable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
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
		inserttable = new MyTableView(c);
		inserttable.setData(insertTableObjectList);
		inserttable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		inserttable.setPrefHeight(100);
	}
	public void changeInserttable(){
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
			
			datatable.getItems().add(inserttable.getItems().get(0));
			addNewInsertable();
			changeInserttable();
		}
		if (command.equals("save")) {
			if (DEBUG) System.out.println("Saving to Database Table:"+this.c.getSimpleName());
			DAOHsqlImpl<T> sSymbol = new DAOHsqlImpl(this.c);
			sSymbol.deleteAll();
			
			ArrayList alt = new ArrayList<>(Arrays.asList(datatable.getItems().toArray()));
			sSymbol.insertAll(alt);
		}
	}
	@Override
	public void changed(ObservableValue arg0, Object arg1, Object arg2) {
		System.out.println("selection changed");  
		
	}
}

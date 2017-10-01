package view;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import model.Symbol;

public class MyTableView<T> extends TableView implements EventHandler<KeyEvent> {

	ArrayList<TableColumn> colNames;

	public MyTableView(ArrayList<T> ol) {
		super();
		ObservableList<T> items = FXCollections.observableList(ol);
		BeanInfo info;
		colNames = new ArrayList();
		setEditable(true);
		try {
			info = Introspector.getBeanInfo(ol.get(0).getClass());
			PropertyDescriptor[] props = info.getPropertyDescriptors();
			TableColumn tc;
			for (PropertyDescriptor pd : props) {
				tc = new TableColumn(pd.getName());
				System.out.println(":" + pd.getPropertyType().toString());
				if (pd.getPropertyType().toString().equals("class java.lang.String")) {
					tc.setCellFactory(TextFieldTableCell.forTableColumn());
					tc.setOnEditCommit(new EventHandler<CellEditEvent<T,String>>() {
						@Override
						public void handle(CellEditEvent<T,String> event) {
							Method m = pd.getWriteMethod();;
							try {
								m.invoke(event.getTableView().getItems().get(event.getTablePosition().getRow()), event.getNewValue());
							} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
								e.printStackTrace();
							}
						}
					});
				}
				if (pd.getPropertyType().toString().equals("class java.lang.Integer")) {
					tc.setCellFactory(TextFieldTableCell.forTableColumn(new MyConverter()));
					tc.setOnEditCommit(new EventHandler<CellEditEvent<T,String>>() {
						@Override
						public void handle(CellEditEvent<T,String> event) {
							Method m = pd.getWriteMethod();;
							try {
								m.invoke(event.getTableView().getItems().get(event.getTablePosition().getRow()), event.getNewValue());
							} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
								e.printStackTrace();
							}
						}
					});
				}
				tc.setCellValueFactory(new PropertyValueFactory<T, Integer>(pd.getName()));
				System.out.println(pd.getName().toString());
				colNames.add(tc);
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}


		getColumns().addAll(colNames);
		setItems(items);

		this.setOnKeyPressed(this);
	}

	public void handle(final KeyEvent keyEvent) {
		T selectedItem;
		selectedItem = (T) this.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			if (keyEvent.getCode().equals(KeyCode.DELETE)) {
				this.getItems().remove(selectedItem);
			}
			if (keyEvent.getCode().equals(KeyCode.SPACE)) {
				System.out.println("hehe" + ((Symbol) selectedItem).getName());

			}
		}
	}
}

class MyConverter extends StringConverter {

	@Override
	public Integer fromString(String arg0) {
		return Integer.parseInt(arg0);
	}

	@Override
	public String toString(Object arg0) {
		return String.valueOf(arg0);

	}

}
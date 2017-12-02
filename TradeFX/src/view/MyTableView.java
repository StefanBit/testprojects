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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import model.Symbol;

/**
 * A scrollable Table with Edit functionality.
 * 
 * @param ArrayList
 *            with Objects of Type T
 */

public class MyTableView<T> extends TableView implements EventHandler<KeyEvent> {

	ArrayList<TableColumn> aColumns;
	TableColumn tableColumn;
	ObservableList<T> items;
	BeanInfo info;
	PropertyDescriptor[] aPropertys;
	Tooltip tooltip;
	Label label;

	public MyTableView(ArrayList<T> lObjects, Class c) {
		super();
		items = FXCollections.observableList(lObjects);
		aColumns = new ArrayList();
		setEditable(true);

		try {
			info = Introspector.getBeanInfo(c);
			aPropertys = info.getPropertyDescriptors();
			// New Column for each Property of Object
			for (PropertyDescriptor property : aPropertys) {
				// Column with name
				tooltip = new Tooltip();
				label = new Label(property.getName());
				tableColumn = new TableColumn();
				label.setTooltip(tooltip);
				tableColumn.setGraphic(label);
				tableColumn.setCellValueFactory(new PropertyValueFactory<T, String>(property.getName()));
				tableColumn.setCellFactory(mytfCell.forTableColumn(new MyConverter(property.getPropertyType())));
				tableColumn.setOnEditCommit(new EventHandler<CellEditEvent<T, String>>() {
					@Override
					public void handle(CellEditEvent<T, String> event) {
						Method method = property.getWriteMethod();
						try {
							method.invoke(event.getTableView().getItems().get(event.getTablePosition().getRow()),event.getNewValue());
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				});
				tooltip.setText(property.getName() + " (" + property.getPropertyType() + ")");
				System.out.println(property.getName().toString());
				aColumns.add(tableColumn);
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}

		getColumns().addAll(aColumns);
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

class mytfCell<T, S> extends TextFieldTableCell<T, S> {
	MyTextField mtf = new MyTextField();

	public mytfCell() {
		mtf.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				// commitEdit( Integer.parseInt(mtf.getText().toString()));
				commitEdit(null);
			};
		});
	}

	@Override
	public void updateItem(S item, boolean empty) {
		super.updateItem(item, empty);
		if (item != null & !empty) {
			setTextFill(Color.BLUE);
			this.setText(item.toString() + "kkK");
		}
	}

}

class MyTextField extends TextField {
}

class MyConverter extends StringConverter {
	Class c;

	public MyConverter(Class c) {
		this.c = c;
	}

	// Translates Cells String Value to desired Field Type
	@Override
	public Object fromString(String arg0) {
		Object o = null;
		System.out.println(c);
		if (c.equals("class java.lang.String")) {
			o = arg0;
		}
		if (c.equals("class java.lang.Integer")) {
			o = Integer.parseInt(arg0);
		}
		if (c.equals(String.class)) {
			o = arg0;
		}
		if (c.equals(int.class)) {
			o = Integer.parseInt(arg0);
		}
		if (c.equals(Integer.class)) {
			o = Integer.parseInt(arg0);
		}
		if (c.equals(double.class)) {
			o = Double.parseDouble(arg0);
		}

		return o;
	}

	@Override
	public String toString(Object arg0) {
		return String.valueOf(arg0);

	}
}

package view;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import model.HistData;
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
	String ColumnObjectType="";
	public static final boolean DEBUG = true; 
			
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
				if (DEBUG) ColumnObjectType = " "+property.getPropertyType(); 
				// Column with name
				tooltip = new Tooltip();
				label = new Label(property.getName()+ColumnObjectType);
				tableColumn = new TableColumn();
				label.setTooltip(tooltip);
				tableColumn.setGraphic(label);

				// Verknüpft mit den Properties der Bean
				tableColumn.setCellValueFactory(new PropertyValueFactory<T, String>(property.getName()));

				// Zellenfabrik wird nach Typ definiert
				switch (property.getPropertyType().getName()) {
				case "java.util.Date":
//					tableColumn.setCellFactory(new Callback<TableColumn, TableCell>() {
//						@Override
//						public TableCell call(TableColumn arg0) {
//							System.out.println("kjkjjlkjlioii");
//							return new MyDpCell();
//						}
//					});
					//System.out.println("Building "+( (HistData) items.get(0)).getDate().getClass());
					//System.out.println("klklk-------");
					tableColumn.setCellFactory(DatePickerTableCell.forTableColumn());
					break;
				default:
					tableColumn.setCellFactory(MytfCell.forTableColumn(new MyConverter(property.getPropertyType())));
				}
				
				// ??
				tableColumn.setOnEditCommit(new EventHandler<CellEditEvent<T, String>>() {
					@Override
					public void handle(CellEditEvent<T, String> event) {
						Method method = property.getWriteMethod();
						try {
							method.invoke(event.getTableView().getItems().get(event.getTablePosition().getRow()),
									event.getNewValue());
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							e.printStackTrace();
						}
						System.out.println("sGetonedircommit");
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

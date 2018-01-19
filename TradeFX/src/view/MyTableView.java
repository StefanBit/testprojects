package view;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Callback;
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

				// Verknüpft mit den Properties der Bean
				tableColumn.setCellValueFactory(new PropertyValueFactory<T, String>(property.getName()));

				// Zellenfabrik wird nach Typ definiert
				switch (property.getPropertyType().getName()) {
				case "java.util.Date":
					tableColumn.setCellFactory(new Callback<TableColumn, TableCell>() {
						@Override
						public TableCell call(TableColumn arg0) {
							return new myDpCell();
						}
					});
					break;
				default:
					tableColumn.setCellFactory(mytfCell.forTableColumn(new MyConverter(property.getPropertyType())));
				}

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

class myDpCell<T, S> extends TableCell<T, S> {
	private DatePicker datePicker;

	public myDpCell() {
		super();

		if (datePicker == null) {
			createDatePicker();
		}
		setGraphic(datePicker);
		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				datePicker.requestFocus();
			}
		});
	}

	@Override
	public void startEdit() {
		datePicker = new DatePicker();
	}
	@Override
	public void updateItem(S item, boolean empty) {
		 super.updateItem(item, empty);

         SimpleDateFormat smp = new SimpleDateFormat("dd/MM/yyyy");

         if (null == this.datePicker) {
             System.out.println("datePicker is NULL");
         }

         if (empty) {
             setText(null);
             setGraphic(null);
         } else {

             if (isEditing()) {
                 setContentDisplay(ContentDisplay.TEXT_ONLY);

             } else {
            	 //System.out.println("dsfsdfsdfs"+item);
            	 if (item != null){
                 setDatepikerDate(smp.format(item));
                 setText(smp.format(item));
            	 }
                 setGraphic(this.datePicker);
                 setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
             }
         }
	}
	private void setDatepikerDate(String dateAsStr) {

        LocalDate ld = null;
        int jour, mois, annee;

        jour = mois = annee = 0;
        try {
            jour = Integer.parseInt(dateAsStr.substring(0, 2));
            mois = Integer.parseInt(dateAsStr.substring(3, 5));
            annee = Integer.parseInt(dateAsStr.substring(6, dateAsStr.length()));
        } catch (NumberFormatException e) {
            System.out.println("setDatepikerDate / unexpected error " + e);
        }

        ld = LocalDate.of(annee, mois, jour);
        datePicker.setValue(ld);
    }

	private void createDatePicker() {
		this.datePicker = new DatePicker();
		datePicker.setPromptText(datePicker.getValue()+"jj/mm/aaaa");
		datePicker.setEditable(true);

		datePicker.setOnAction(new EventHandler() {
			public void handle(Event t) {
				LocalDate date = datePicker.getValue();
				int index = getIndex();

				SimpleDateFormat smp = new SimpleDateFormat("dd/MM/yyyy");
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.DAY_OF_MONTH, date.getDayOfMonth());
				cal.set(Calendar.MONTH, date.getMonthValue() - 1);
				cal.set(Calendar.YEAR, date.getYear());

				setText(smp.format(cal.getTime()));
				commitEdit((S) cal.getTime());

			}
		});

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

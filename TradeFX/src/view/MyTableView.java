package view;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import model.Symbol;

public class MyTableView<T> extends TableView {

	Class<T> c;
	ArrayList<TableColumn> colNames;

	public MyTableView(ArrayList<T> ol) {
		super();
		T t;
		BeanInfo info;
		colNames = new ArrayList();
		StackPane SymbolPane = new StackPane();
		t = ol.get(1);
		setEditable(true);
		try {
			info = Introspector.getBeanInfo(t.getClass());
			PropertyDescriptor[] props = info.getPropertyDescriptors();
			TableColumn tc;
			for (PropertyDescriptor pd : props) {
				tc = new TableColumn(pd.getName());
				System.out.println(":"+pd.getPropertyType().toString());
				if (pd.getPropertyType().toString().equals("class java.lang.String")){
					System.out.println(pd.getPropertyType());
					tc.setCellFactory(TextFieldTableCell.forTableColumn());
					
				}
				if (pd.getPropertyType().toString().equals("class java.lang.Integer")){
					System.out.println(pd.getPropertyType());
					tc.setCellFactory(TextFieldTableCell.forTableColumn(new MyConverter()));
					
				}
				tc.setCellValueFactory(new PropertyValueFactory<T, Integer>(pd.getName()));
				System.out.println(pd.getName().toString());
				colNames.add(tc);
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}

		ObservableList<T> items = FXCollections.observableList(ol);

		getColumns().addAll(colNames);
		setItems(items);

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
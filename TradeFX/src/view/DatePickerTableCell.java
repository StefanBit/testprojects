package view;

import java.util.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;

import java.sql.Date;
import java.time.*;

public class DatePickerTableCell<S, T> extends TableCell<S, java.sql.Date> {
	private DatePicker datePicker;
	private StringConverter converter = null;
	private boolean datePickerEditable = true;

	public DatePickerTableCell() {
		this.converter = new LocalDateStringConverter();
	}

	public DatePickerTableCell(boolean datePickerEditable) {
		this.converter = new LocalDateStringConverter();
		this.datePickerEditable = datePickerEditable;
	}

	public DatePickerTableCell(StringConverter<java.sql.Date> converter) {
		this.converter = converter;
	}

	public DatePickerTableCell(StringConverter<java.sql.Date> converter, boolean datePickerEditable) {
		this.converter = converter;
		this.datePickerEditable = datePickerEditable;
	}

	@Override
	public void startEdit() {
		if (!isEditable() || !getTableView().isEditable() || !getTableColumn().isEditable()) {
			return;
		}
		super.startEdit();
		if (datePicker == null) {
			this.createDatePicker();
		}
		this.setGraphic(datePicker);
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();
		this.setText(converter.toString(this.getItem()));
		this.setGraphic(null);
	}

	@Override
	public void updateItem(java.sql.Date item, boolean empty) {
		super.updateItem(item, empty);
		if (empty) {
			this.setText(null);
			this.setGraphic(null);
		} else {
			if (this.isEditing()) {
				if (datePicker != null) {
					datePicker.setValue(((java.sql.Date) item).toLocalDate());
				}
				this.setText(null);
				this.setGraphic(datePicker);
			} else {
				try {
					if (item!=null){
						
						System.out.println("ü"+item.toLocalDate());
						this.setText(converter.toString(item.toLocalDate()));
					}
				} catch (Exception e) {
					System.out.println(e);
				}
				this.setGraphic(null);
			}
		}
	}

	private void createDatePicker() {
		datePicker = new DatePicker();
		datePicker.setConverter(converter);
		if (this.getItem()!=null){
			
			datePicker.setValue(((java.sql.Date) this.getItem()).toLocalDate());
		} else {
			datePicker.setValue(LocalDate.now());
		}
		datePicker.setPrefWidth(this.getWidth() - this.getGraphicTextGap() * 2);
		datePicker.setEditable(this.datePickerEditable);
		datePicker.valueProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue prop, Object oldValue, Object newValue) {
				if (DatePickerTableCell.this.isEditing()) {
					DatePickerTableCell.this.commitEdit((java.sql.Date) Date.valueOf(newValue.toString()));
				}
			}
		});
	}

	public static <S> Callback<TableColumn<S, java.sql.Date>, TableCell<S, java.sql.Date>> forTableColumn() {
		return forTableColumn(true);
	}

	public static <S> Callback<TableColumn<S, java.sql.Date>, TableCell<S, java.sql.Date>> forTableColumn(
			boolean datePickerEditable) {
		return (col -> new DatePickerTableCell<>(datePickerEditable));

	}

	public static <S> Callback<TableColumn<S,java.sql.Date>,TableCell<S,java.sql.Date>> forTableColumn(StringConverter<java.sql.Date> converter){
		return forTableColumn(converter,true);		
	}

	public static <S> Callback<TableColumn<S,java.sql.Date>,TableCell<S,java.sql.Date>> forTableColumn(StringConverter<java.sql.Date> converter,boolean datePickerEditable){
		return (col -> new DatePickerTableCell<>(converter, datePickerEditable));		
	}
}
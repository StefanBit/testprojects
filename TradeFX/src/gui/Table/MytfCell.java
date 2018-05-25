package gui.Table;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Color;

class MytfCell<T, S> extends TextFieldTableCell<T, S> {
	TextField mtf = new TextField();

	public MytfCell() {
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
package gui.DetailPage;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.StockDetails;
import model.Symbol;

public class DetailPage extends TextArea {
	StockDetails stockDetail;
	
	public DetailPage() {
		// TODO Auto-generated constructor stub
	}

	public void changeData(Symbol s) {
		stockDetail= new StockDetails(s.getName());
		this.setText(stockDetail.toString());
		this.setPrefHeight(500);
	}
}

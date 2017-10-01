package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import model.HistData;

public class CandleNode extends Rectangle {
	public CandleNode(HistData d) {
		StrokeType st;
		System.out.println(this.getX());
		this.setX(0);
		
		this.setY(20);
		
		if (d.getClose()<d.getOpen()){
			this.setFill(Color.RED);
			this.setHeight(d.getOpen()-d.getClose());
			
		} else {
			this.setFill(Color.GREEN);
			this.setHeight(d.getClose()-d.getOpen());
		}
		this.setHeight(this.getHeight()*200);
		this.setWidth(5);
		this.setStrokeWidth(0);
	}
}

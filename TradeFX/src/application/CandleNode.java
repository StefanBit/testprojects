package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class CandleNode extends Rectangle {
	public CandleNode(DaySet d) {
		StrokeType st;
		System.out.println(this.getX());
		this.setX(0);
		
		this.setY(20);
		
		if (d.close<d.open){
			this.setFill(Color.RED);
			this.setHeight(d.open-d.close);
			
		} else {
			this.setFill(Color.GREEN);
			this.setHeight(d.close-d.open);
		}
		this.setHeight(this.getHeight()*200);
		this.setWidth(5);
		this.setStrokeWidth(0);
	}
}

package model.metrics;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.paint.Color;
import model.HistData;

public class ArithmeticMean implements IMetric{
	
	ArrayList<HistData> data;
	
	public ArithmeticMean() {
		// TODO Auto-generated constructor stub
		data= new ArrayList<HistData>();
	}
	public ArrayList<HistData> calc(ArrayList<HistData> data) {
		ArrayList<HistData> erg = new ArrayList<HistData>(); 
		HistData set;
		double nc;
		for (int i =0;i<data.size();i++) {
			set=data.get(i);
			nc=0;
			if (i==0){
				nc=data.get(i).getClose();
			} else {
				nc=((erg.get(i-1).getClose()*(i))+data.get(i).getClose())/(i+1);
			}
			erg.add(new HistData(set.pk, set.date, nc, nc, nc, nc, set.volume));
		}
		this.data=erg;
		return erg;
	}
	public ArithmeticMean getInstance(){
		return new ArithmeticMean();
	}
	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}
	public ArrayList<HistData> getData(){
		return this.data;
	}
	@Override
	public Color getColor() {
		return Color.DARKSLATEBLUE;
	}
	
}

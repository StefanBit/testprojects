package model.metrics;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javafx.scene.paint.Color;
import model.HistData;

public class TotalMean implements IMetric{
	
	ArrayList<HistData> data;
	
	public TotalMean() {
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
		double pitch = (erg.get(erg.size()-1).getClose()-erg.get(0).getClose())/(erg.size()-1);
		erg = new ArrayList<HistData>();
		for (int i =0;i<data.size();i++) {
			set=data.get(i);
			nc=0;
			if (i==0){
				nc=pitch*(i)+data.get(0).getClose();
			} else {
				nc=pitch*(i)+data.get(0).getClose();
			}
			erg.add(new HistData(set.pk, set.date, nc, nc, nc, nc, set.volume));
		}
		
		Date date=new Date();
		Calendar c;
		for (int i =data.size();i<100+data.size();i++) {
			set=data.get(0);
			nc=0;
			if (i==0){
				nc=pitch*(i)+data.get(0).getClose();
			} else {
				nc=pitch*(i)+data.get(0).getClose();
			}
			
			
			c= Calendar.getInstance(); 
			c.setTime(date); 
			c.add(Calendar.DATE, 1);
			date = c.getTime();
			
			erg.add(new HistData(set.pk, date, nc, nc, nc, nc, set.volume));
		}
		this.data=erg;
		
		return erg;
	}
	public TotalMean getInstance(){
		return new TotalMean();
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
		return Color.DARKGOLDENROD;
	}

}

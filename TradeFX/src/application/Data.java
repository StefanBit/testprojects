package application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.*;

public class Data {
	String name;
	private Date firstDate, lastDate;
	
	ArrayList<DaySet> lDaySet;
	

	public Data() {
		lDaySet = new ArrayList();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getFirstDate() {
		return firstDate;
	}

	public void setFirstDate(Date firstDate) {
		this.firstDate = firstDate;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}
	
	public void addSet(DaySet ds){
		lDaySet.add(ds);
	}
	
	
	public DaySet get(int i,int j){
		double open=lDaySet.get(i).open;
		double close=lDaySet.get(i).close;
		
		double hight=lDaySet.get(i).hight;
		int x=0;
		for (;x<(j-i);x++){
			hight=Math.max(hight,lDaySet.get(i+x).hight);
		}
		
		double low=lDaySet.get(i).low;
		x=0;
		for (;x<(j-i);x++){
			low=Math.max(low,lDaySet.get(i+x).low);
		}
		
		return new DaySet(lDaySet.get(i).date,open,hight,low,close,low);
		
	}
	
	
	public ArrayList<DaySet> get(Date from, Date to){
		ArrayList<DaySet> result;
		int start=0;
		
		result = new ArrayList();
		while (!this.lDaySet.get(start).date.after(from)){
			start++;
		}
		int end=start;
		to.setTime(to.getTime() -  24*60*60);
		System.out.println(from+","+to);
		while (!this.lDaySet.get(end).date.after(to)){
			end++;
		}

		System.out.println(start+","+end);
		for (int i=start;i<end-1;i++){
			result.add(this.lDaySet.get(i));
		}
		return result;
	}
	

	
	@Override
	public String toString() {
		String s = "Name: " + name;
		s += " from " + firstDate;
		s += " to " + lastDate + "\n";
//		for (int i =0; i<lDaySet.size();i++){
//			s += lDaySet.get(i)+"\n";
//		}
		return s;
	}
}

class DaySet {
	Date date;
	double open, hight, low, close, volume;

	public DaySet(Date date, double open, double hight, double low, double close,double  volume) {
		this.date=date;
		this.open = open;
		this.hight = hight;
		this.low = low;
		this.close = close;
		this.volume = volume;
	}

	@Override
	public String toString() {
		return date+"["+open + "," + hight + "," + low + "," + close + "," + volume+"]";
	}
}

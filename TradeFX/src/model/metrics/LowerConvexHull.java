package model.metrics;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import application.MyProperties;
import javafx.scene.paint.Color;
import model.HistData;

public class LowerConvexHull implements IMetric{
	
	ArrayList<HistData> data;
	Boolean DEBUG;
	public LowerConvexHull() {
		// TODO Auto-generated constructor stub
		data= new ArrayList<HistData>();
		DEBUG=MyProperties.getDebugSettingFor("MetricDbg");
	}
	
	
	public ArrayList<HistData> calc(ArrayList<HistData> data) {
		ArrayList<HistData> erg = new ArrayList<HistData>(); 
		HistData set;
		double nc;
		ArrayList<Integer> hullpoints= new ArrayList();
		this.data=data;
		
		// Find Points
		int startx=0;
		hullpoints.add(startx);
		while (startx < data.size()-1){
			startx=getNextHullpoint(startx);
			hullpoints.add(startx);
		}
		
		int firstx=0;
		double angle=0;
		int x0,x1;
		int dx;
		double y0,y1;
		for (int i =0;i<data.size();i++) {
			if (i>hullpoints.get(firstx+1)){
				firstx++;
			}
			x0=hullpoints.get(firstx);
			x1=hullpoints.get(firstx+1);
			dx=x1-x0;
			y0=data.get(x0).getLow();
			y1=data.get(x1).getLow();
			angle = (y0-y1) / dx;
			nc= -angle * (i-x0) + y0;
			set=data.get(i);
			erg.add(new HistData(set.pk, set.date, nc, nc, nc, nc, set.volume));
		}
		
		
		
		// Forecast
		Date date=new Date();
		Calendar c;
		
		x0=hullpoints.get(hullpoints.size()-7);
		x1=hullpoints.get(hullpoints.size()-6);
		System.out.println(x0);
		System.out.println(x1);
		dx=x1-x0;
		y0=data.get(x0).getLow();
		y1=data.get(x1).getLow();
		angle = (y0-y1) / dx;
		System.out.println(hullpoints);
		
		
		for (int i =0;i<100;i++) {
			set=data.get(x1);
			nc= -angle*i +set.getLow();
			c= Calendar.getInstance(); 
			c.setTime(date); 
			c.add(Calendar.DATE, 1);
			date = c.getTime();
			erg.add(new HistData(set.pk, date, nc, nc, nc, nc, set.volume));
		}
		
		
		this.data=erg;
		return erg;
	}
	
	private int getNextHullpoint(int x0){
		int x1,dx;
		double y0,y1,dy;
		double angle,minangle,lastangle;
		x1=x0+1;
		y0=data.get(x0).getLow();
		y1=data.get(x1).getLow();
		dx=x1-x0;
		dy=y1-y0;
		lastangle=dy/dx;
		int minangleindex=0;
		for (x1=x0+1;x1<=data.size()-1;x1++){
			y1= data.get(x1).getLow();
			dx=x1-x0;
			dy=y1-y0;
			angle=dy/dx;
			if ( lastangle>=angle){
				lastangle=angle;
				minangleindex=x1;
			}
		}
		if (DEBUG) System.out.println("Found minagleindex "+minangleindex+ " date:"+data.get(minangleindex));
		return minangleindex;
	}
	
	
	
	
	public LowerConvexHull getInstance(){
		return new LowerConvexHull();
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

		return Color.AQUAMARINE;
	}


}

package application;

import java.util.ArrayList;
import java.util.Date;

public class Heuristik {
	Data data;
	
	
	public Heuristik(Data data) {
		this.data = data;
	}
	
	public void calc(Date from, Date to){
		
		ArrayList<DaySet> ads;
		double mid;
		double sum;
		mid=0;
		sum=0;
		ads=data.get(from, to);
		int count= ads.size();
		
		for (int i=0;i<count;i++) {
		sum+=ads.get(i).open;
		}
		System.out.println(sum/count);
		
		
	}
	
	public double getMid(){
		double mid;
		mid=0;
		
		return mid;
	}	
	
}

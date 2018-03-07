package model.metrics;

import java.util.ArrayList;

import model.HistData;

public class ProMean {
	
	
	ArrayList<HistData> data;
	
	public ProMean() {
		data= new ArrayList<HistData>();
	}
	public ArrayList<HistData> calc(ArrayList<HistData> data,ArrayList<HistData> data2)  {
		ArrayList<HistData> erg = new ArrayList<HistData>(); 
		HistData set;
		double nc;
		for (int i =0;i<data.size();i++) {
			set=data.get(i);
			nc=0;
			nc=(data.get(i).getClose()+1)/(data2.get(i).getClose()+1);
			erg.add(new HistData(set.pk, set.date, set.open, set.hight, set.low, nc, set.volume));
		}
		this.data=erg;
		return erg;
	}
	public ArrayList<HistData> getData(){
		return data;
	}
}

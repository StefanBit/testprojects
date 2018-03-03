package model.metrics;

import java.util.ArrayList;

import model.HistData;

public class ProMean {
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
		
		return erg;
	}
}

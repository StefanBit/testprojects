package model;

import java.util.ArrayList;

public class FloatingMean {
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
			erg.add(new HistData(set.pk, set.date, set.open, set.hight, set.low, nc, set.volume));
		}
		
		return erg;
	}
}

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
			int j=0;
				while (j<=i) {					
					nc+=data.get(j).getClose();
					j++;
				}
			nc=nc/(j);
			erg.add(new HistData(set.pk, set.date, set.open, set.hight, set.low, nc, set.volume));
		}
		
		return erg;
	}
}

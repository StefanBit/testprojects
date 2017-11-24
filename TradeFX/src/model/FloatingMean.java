package model;

import java.util.ArrayList;

public class FloatingMean {
	public ArrayList<HistData> calc(ArrayList<HistData> data, int n) {
		ArrayList<HistData> erg = new ArrayList<HistData>();
		HistData set;
		double nc;
		int k;
		for (int i = 0; i < data.size(); i++) {
			set=data.get(i);
			nc = 0;
			k=0;
			for (int j = -n; j <= 0; j++) {
				if (i+j >= 0) {
					k++;
					nc += data.get(i + j).getClose();
				}
			}
			nc=nc/k;
			erg.add(new HistData(set.pk, set.date, nc, nc, nc, nc, set.volume));
		}

		return erg;
	}
}

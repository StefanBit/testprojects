package model;

import java.util.ArrayList;

public class FloatingMean {
	public ArrayList<HistData> calc(ArrayList<HistData> data, int n) {
		ArrayList<HistData> erg = new ArrayList<HistData>();
		HistData set;
		double nc;
		Double open, hight, low, close, volume;
		int k;
		for (int i = 0; i < data.size(); i++) {
			set=data.get(i);
			open = 0.0;
			hight = 0.0;
			low=0.0;
			close=0.0;
			volume=0.0;
			k=0;
			for (int j = -n; j <= 0; j++) {
				if (i+j >= 0) {
					k++;
					open += data.get(i + j).getOpen();
					hight += data.get(i + j).getHight();
					low +=  data.get(i + j).getLow();
					close += data.get(i + j).getClose();
				}
			}
			open=open/k;
			hight=hight/k;
			low=low/k;
			close=close/k;
			
			erg.add(new HistData(set.pk, set.date, open, hight, low, close, set.volume));
		}

		return erg;
	}
}

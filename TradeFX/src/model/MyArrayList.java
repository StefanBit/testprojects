package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class MyArrayList extends ArrayList<HistData> {

	Date from, to;
	double open, close, hight, low, volume;

	public void update() {
		from=this.get(0).getDate();
		open=this.get(0).getOpen();
		hight=this.get(0).getHight();
		low=this.get(0).getLow();
		close=this.get(this.size()-1).getClose();
		for (HistData data : this) {
			hight=Math.max(hight, data.getHight());
			low=Math.min(low, data.getLow());
		}
	}
	public ArrayList<HistData> getAsSingleItem(){
		ArrayList<HistData> a;
		a= new ArrayList<HistData>();
		HistData h = new HistData(this.get(0).getPk(), from, open, hight, low, close, volume);
		a.add(h);
		return a;
	}

}

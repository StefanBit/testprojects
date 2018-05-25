package model.metrics;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.paint.Color;
import model.HistData;

public class FloatingMean implements IMetric{
	ArrayList<HistData> data;
	
	
	int n=0;
	public FloatingMean (int n){
		data= new ArrayList<HistData>();
		this.n=n;
	}
	
	public void setTimeSpan(int n){
		this.n=n;
	}
	
	public ArrayList<HistData> calc(ArrayList<HistData> data) {
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
		this.data=erg;
		return erg;
	}
	
	public FloatingMean getInstance(){
		return new FloatingMean(6);
	}

	@Override
	public String getName() {
		
		return "floating mean";
	}
	public ArrayList<HistData> getData(){
		return this.data;
	}
	
	@Override
	public Color getColor() {
		Random r = new Random();
		return Color.DARKSEAGREEN;
	}

}

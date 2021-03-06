package model;

import java.time.ZoneId;
import java.util.Date;

public class HistData {
	public Integer pk;
	public Date date;
	public Double open, hight, low, close;
	public Double volume;
	
	public HistData() {
		this.pk=0;
		this.date= null ;
		this.open = 0.0;
		this.hight = 0.0;
		this.low = 0.0;
		this.close = 0.0;
		this.volume = 0.0;
	}
	public HistData(int pk, Date date, double open, double hight, double low, double close,double  volume) {
		this.pk=pk;
		this.date=date;
		this.open = open;
		this.hight = hight;
		this.low = low;
		this.close = close;
		this.volume = volume;
	}
	
	public int getPk() {
		return pk;
	}
	public void setPk(int pk) {
		this.pk = pk;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getOpen() {
		return open;
	}
	public void setOpen(double open) {
		this.open = open;
	}
	public double getHight() {
		return hight;
	}
	public void setHight(double hight) {
		this.hight = hight;
	}
	public double getLow() {
		return low;
	}
	public void setLow(double low) {
		this.low = low;
	}
	public double getClose() {
		return close;
	}
	public void setClose(double close) {
		this.close = close;
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	
	@Override
	public String toString() {
		return super.toString()
				+":\n"+pk+"\n"
				+"[\n"
				+"Date: "+getDate()+",\n"
				+"open"+getOpen()+",\n"
				+"hight: "+getHight()+",\n"
				+ "low: "+getLow()+",\n"
				+"close: "+getClose()+",\n"
				+"volume: "+getVolume()+"\n"
				+"]\n";
	}
	
	public HistData clone() {
		return new HistData(this.getPk(),
				this.getDate(),
				this.getOpen(),
				this.getHight(),
				this.getLow(),
				this.getClose(),
				this.getVolume());
	}
}

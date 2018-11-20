package model;

import java.util.Date;

public class SymbolsPeriod {
	Integer pk;
	Date fromD;
	Date toD;

	public SymbolsPeriod() {
		pk=0;
		fromD=new Date();
		toD=new Date();
	}

	public SymbolsPeriod newInstance() {
		return new SymbolsPeriod();
	}

	public SymbolsPeriod(Integer pk, Date fromD, Date toD) {
		this.pk = pk;
		this.fromD = fromD;
		this.toD= toD;
	}

	public Integer getPk() {
		return pk;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}

	public Date getFromD() {
		return this.fromD;
	}

	public void setFromD(Date From) {
		this.fromD = fromD;
	}
	public Date getToD() {
		return this.fromD;
	}

	public void setToD(Date From) {
		this.fromD = fromD;
	}

	@Override
	public String toString() {
		return super.toString() + "["+ getPk();
	}
}

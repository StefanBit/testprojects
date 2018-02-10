package model;

import java.time.LocalDateTime;
import java.util.Date;

public class Symbol {
	Integer pk;
	String name;
	Date fromDate;
	public Symbol() {
		// TODO Auto-generated constructor stub
	}

	public Symbol newInstance() {
		return new Symbol();
	}

	public Symbol(Integer pk, String name,Date from) {
		this.pk = pk;
		this.name = name;
		this.fromDate = new Date();
	}

	public Integer getPk() {
		return pk;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Date getFromDate(){
		return fromDate;
	}
	
	public void setFromDate(Date fromDate){
		this.fromDate=fromDate;
	}
	
	@Override
	public String toString() {
		return super.toString() + "[" + getName() + "," + getPk()+ "," + getFromDate();
	}
}

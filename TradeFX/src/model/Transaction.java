package model;

import java.util.Date;

public class Transaction {
	String name;
	Double price;
	Double ammount;
	Date transactiondate;
	Integer transactiontype;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Transaction(String name, double price) {
		this.name = name;
		this.price = price;
	}

}

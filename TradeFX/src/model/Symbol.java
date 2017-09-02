package model;

public class Symbol {
	Integer pk;
	String name;
public Symbol() {
	// TODO Auto-generated constructor stub
}
	public Symbol(Integer pk, String name) {
		this.pk = pk;
		this.name = name;
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


}

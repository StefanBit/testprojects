package model.metrics;

import java.util.ArrayList;

import model.HistData;

public class DatalineContainer {
	ArrayList<HistData> data;
	String Name;
	String Type;

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public ArrayList<HistData> getData() {
		return data;
	}

	public void setData(ArrayList<HistData> data) {
		this.data = data;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}
}

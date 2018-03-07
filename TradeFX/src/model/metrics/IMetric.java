package model.metrics;

import java.util.ArrayList;

import model.HistData;

public interface IMetric {
	public IMetric getInstance();
	public String getName();
	public ArrayList<HistData> calc(ArrayList<HistData> data);
	public ArrayList<HistData> getData();
}

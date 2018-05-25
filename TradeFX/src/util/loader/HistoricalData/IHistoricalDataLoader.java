package util.loader.HistoricalData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import model.HistData;
import model.Symbol;

public interface IHistoricalDataLoader {
	public ArrayList<HistData> load(Symbol s, Date firstDate, Date lastDate);
	public ArrayList<HistData> load(Symbol s, LocalDate firstDate, LocalDate lastDate);
}

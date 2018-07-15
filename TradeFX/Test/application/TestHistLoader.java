package application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import model.HistData;
import model.Symbol;
import util.database.DAOHsqlImpl;
import util.loader.HistoricalDataLoader.HistoricalDataFromAlphavantage;

public class TestHistLoader {
public TestHistLoader() {
	Symbol alSymbol;
	alSymbol=new Symbol(1,"NASDAQ:MSFT",new Date());
	HistoricalDataFromAlphavantage l = new HistoricalDataFromAlphavantage();
	Calendar cal = Calendar.getInstance();
	Date today = cal.getTime();
	cal.add(Calendar.YEAR, -1); // to get previous year add -1
	Date lastYear = cal.getTime();
	ArrayList<HistData> alHistData;
	System.out.println("load");
	alHistData = l.load(alSymbol, lastYear, new Date());
	System.out.println("save");
	DAOHsqlImpl<HistData> sHistData = new DAOHsqlImpl(HistData.class);
	ArrayList<HistData> al2;
	System.out.println("delete");
	sHistData.deleteAll();
	System.out.println("insert");
	sHistData.insertAll(alHistData);
	System.out.println("get");
	al2 = sHistData.getAllWhere(alSymbol.getPk().toString());
}
public static void main(String[] args) {
	new TestHistLoader();
}
}

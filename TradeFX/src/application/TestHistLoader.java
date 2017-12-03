package application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import database.DAOHsqlImpl;
import loader.HistStockDataLoader;
import model.HistData;
import model.Symbol;

public class TestHistLoader {
public TestHistLoader() {
	Symbol alSymbol;
	alSymbol=new Symbol(0,"NASDAQ:MSFT");
	HistStockDataLoader l = new HistStockDataLoader();
	Calendar cal = Calendar.getInstance();
	Date today = cal.getTime();
	cal.add(Calendar.YEAR, -1); // to get previous year add -1
	Date lastYear = cal.getTime();
	ArrayList<HistData> alHistData;
	alHistData = l.load(alSymbol, lastYear, new Date());
	
	DAOHsqlImpl<HistData> sHistData = new DAOHsqlImpl(HistData.class);
	ArrayList<HistData> al2;
	sHistData.deleteAll();
	sHistData.insertAll(alHistData);
	al2 = sHistData.getAllWhere(alSymbol.getPk().toString());
}
public static void main(String[] args) {
	new TestHistLoader();
}
}

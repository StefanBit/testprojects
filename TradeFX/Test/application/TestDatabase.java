package application;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import gui.Console.ConsoleStage;
import model.HistData;
import model.Symbol;
import util.database.DAOHsqlImpl;
import util.database.HSQLQuery;

public class TestDatabase {

	public static void main(String[] args) {
//		HSQLQuery q = new HSQLQuery();
//		for (Object o : q.query("SELECT * FROM INFORMATION_SCHEMA.TABLES")) {
//			System.out.println(o);
//		};
//		for (Object o : q.query("SELECT * FROM SYMBOL")) {
//			System.out.println(o);
//		};
		
		
		DAOHsqlImpl<Symbol> dataLoader;
		
														// Variable initialisation
		ArrayList alSymbolData = new ArrayList();
			
		dataLoader = new DAOHsqlImpl(Symbol.class);
		Symbol t = new Symbol(1,"jkjk",new Date());
		dataLoader.insert(t);
		
		
		
	}
}

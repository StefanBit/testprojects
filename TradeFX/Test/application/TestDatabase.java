package application;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import database.DAOHsqlImpl;
import database.HSQLQuery;
import gui.stage.ConsoleStage;
import model.HistData;
import model.Symbol;

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

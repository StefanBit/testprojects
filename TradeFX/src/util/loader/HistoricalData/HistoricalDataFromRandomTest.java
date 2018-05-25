package util.loader.HistoricalData;

import static org.junit.Assert.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import model.Symbol;

public class HistoricalDataFromRandomTest {

	IHistoricalDataLoader histDataLoader = new HistoricalDataFromRandom();
	Symbol s = new Symbol();
	Date fromDate;
	
	public HistoricalDataFromRandomTest() {
		//Symbol
		s.setName("Test");
		s.setPk(1);
		Calendar cal = Calendar.getInstance();
		LocalDate endDate= LocalDate.now();
	}
	
	@Test
	public void ReturnValueIsNotNull() {
		assertTrue(histDataLoader.load(s, s.getFromDate(), new Date()) != null);
	}
	@Test
	public void ReturnedArrayHasRightSize(){
		assertTrue(histDataLoader.load(s, s.getFromDate(), new Date()).size()==1);
	}

}

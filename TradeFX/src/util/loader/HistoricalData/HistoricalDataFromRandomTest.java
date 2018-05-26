package util.loader.HistoricalData;

import static org.junit.Assert.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import model.Symbol;

public class HistoricalDataFromRandomTest {

	IHistoricalDataLoader histDataLoader = new HistoricalDataFromAlphavantage();
	Symbol s = new Symbol();
	
	LocalDate startLocalDate,endLocalDate;
	Date startDate,endDate;
	int diff;
	 
	public HistoricalDataFromRandomTest() {
		//Symbol
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -1);
		endDate=cal.getTime();
		startDate =cal.getTime();
		s.setName("MSFT");
		s.setPk(1);
		s.setFromDate(startDate);
		calcDays(0);
	}
	
	
	@Test
	public void ReturnValueIsNotNull() {
		assertTrue(histDataLoader.load(s, startDate, endDate) != null);
	}
	@Test
	public void ReturnedArrayHasRightSizeBYSameDay(){
		assertTrue(histDataLoader.load(s, startDate, endDate).size()==diff+1);
	}
	@Test
	public void ReturnedArrayHasRightSizeWithMoreDays(){
		calcDays(8);
		assertTrue(histDataLoader.load(s, startDate, endDate).size()==diff+1);
	}
	@Test
	public void ReturnedArrayHasRightSizeWithNegativeDays(){
		calcDays(-8);
		assertTrue(histDataLoader.load(s, startDate, endDate).size()==0);
	}
	
	
	
	
	void calcDays(int diff){
		this.diff=diff;
		startLocalDate= startLocalDate.now().minusDays(diff);
		endLocalDate=endLocalDate.now();
		startDate = Date.from(startLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		endDate = Date.from(endLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
	
}

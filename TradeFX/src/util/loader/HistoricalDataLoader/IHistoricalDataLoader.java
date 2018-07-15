package util.loader.HistoricalDataLoader;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import model.HistData;
import model.Symbol;

/**
 * 
 * @author Stefan
 *	@param firstDate
 *  @param lastDate
 *  @param  symbol
 *  
 *  
 *  
 */

public interface IHistoricalDataLoader {
	public ArrayList<HistData> load(Symbol symbol, Date firstDate, Date lastDate);
	public ArrayList<HistData> load(Symbol symbol, LocalDate firstDate, LocalDate lastDate);
}

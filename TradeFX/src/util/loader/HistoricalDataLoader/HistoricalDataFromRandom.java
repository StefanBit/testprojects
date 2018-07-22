package util.loader.HistoricalDataLoader;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Logger;

import model.HistData;
import model.Symbol;
import util.loader.Metric.MetricMapLoaderWorker;
import util.log.Log;

/**
 * Liefert Historische Daten aus zufälligen Werten.
 * 
 * @author Stefan
 *
 */

public class HistoricalDataFromRandom implements IHistoricalDataLoader {

	// private static final Logger log =
	// Logger.getLogger(HistoricalDataFromRandom.class.getName());

	ArrayList<HistData> aHistData;
	HistData histData;
	Random rnd;
	double value, range;

	public HistoricalDataFromRandom() {
		histData = new HistData();
		aHistData = new ArrayList<HistData>();
		rnd = new Random();
		value = rnd.nextDouble();

	}

	@Override
	public ArrayList<HistData> load(Symbol s, Date startDate, Date endDate) {
		return load(s, getLocalDate(startDate), getLocalDate(endDate));
	}

	@Override
	public ArrayList<HistData> load(Symbol s, LocalDate startDate, LocalDate endDate) {
		if (!startDate.isAfter(endDate)) {
			Log.info("Loading Data from " + startDate + " to " + endDate);
			do {
				aHistData.add(getHistDataFor(startDate));
				startDate = startDate.plusDays(1);
			} while (!startDate.isAfter(endDate));
			Log.info(aHistData.size() + " Days loaded.");
		} else {
			Log.warning("Loading Data fails cause " + startDate + " is after " + endDate);

		}
		return aHistData;
	}

	LocalDate getLocalDate(Date date) {
		Instant instant = date.toInstant();
		ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
		return zdt.toLocalDate();
	}

	private HistData getHistDataFor(LocalDate localDate) {
		HistData histData;
		histData = new HistData();
		histData.setDate(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		histData.setOpen(value += rnd.nextDouble());
		histData.setHight(value += rnd.nextDouble());
		histData.setLow(value += rnd.nextDouble());
		histData.setClose(value += rnd.nextDouble());
		histData.setVolume(value += rnd.nextDouble());
		return histData;
	}

	@Override
	public String toString() {
		return super.toString();
	}

}

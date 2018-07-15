package util.loader.HistoricalDataLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import application.MyProperties;
import controller.TradeFXBusinessController;
import model.HistData;
import model.MyArrayList;
import model.Symbol;
import util.Log;
import util.database.DAOHsqlImpl;
import util.loader.Metric.MetricMapLoaderWorker;

/* Loads Historical Data from URL and returns Array*/

public class HistoricalDataFromAlphavantage implements IHistoricalDataLoader {

	HistData histData;
	MyArrayList alHistData, alHistData2;
	Boolean DEBUG;

	public HistoricalDataFromAlphavantage() {
	}


	
	public ArrayList<HistData> load(Symbol symbol) {
		return alHistData2;
	}
	
	
	public ArrayList<HistData> load(Symbol symbol, Date firstDate, Date lastDate) {

		URL url = null;
		LocalDate startDate, endDate;
		startDate = getLocalDate(firstDate);
		endDate = getLocalDate(lastDate);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		String inputLine = null;
		BufferedReader br = null;
		StringTokenizer st = null;
		URLConnection conn = null;
		MyProperties.getDebugSettingFor("DbgHistStockDataLoader");
		alHistData = new MyArrayList();
		alHistData2 = new MyArrayList();

		Calendar cal = Calendar.getInstance();
		cal.setTime(firstDate);
		long millisStart = Calendar.getInstance().getTimeInMillis();

		String request = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + symbol.getName()
				+ "&outputsize=full&apikey="
				+ TradeFXBusinessController.getInstance().myProperties.getProperty("AlphavantageKey") + "&datatype=csv";
		Log.info("Fetch: " + request);

		try {
			url = new URL(request);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			if ((conn = url.openConnection()) != null) {
				Log.info("Connection to " + url + " established.");
			} else {
				Log.warning("Connection to " + url + " rejected.");
			}
		} catch (IOException e) {
			if (DEBUG)
				System.out.println("a");
			e.printStackTrace();
		}

		try {

			br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			inputLine = br.readLine();

			// Handle first CSV Line
			st = new StringTokenizer(inputLine, ",");
			while (st.hasMoreTokens()) {
				// System.out.println(inputLine);
				st.nextToken();
				// hst.colnames.addElement(st.nextToken());
			}

			Log.info("Loading Data from " + startDate + " to " + endDate);

			Integer pk;
			Date date;

			while (((inputLine = br.readLine()) != null)) {
				st = new StringTokenizer(inputLine, ",");
				pk = symbol.getPk();
				try {
					date = (Date) format.parse(st.nextToken());
					histData = new HistData();
					histData.setPk(pk);
					histData.setDate(date);
					histData.setOpen(convertValue(st.nextToken()));
					histData.setHight(convertValue(st.nextToken()));
					histData.setLow(convertValue(st.nextToken()));
					histData.setClose(convertValue(st.nextToken()));
					histData.setVolume(convertValue(st.nextToken()));
				} catch (ParseException e) {
					Log.warning(e.toString());
					e.printStackTrace();
				}
				alHistData.add(0, histData);
			}
			br.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.info("Collected " + alHistData.size() + " Days. List is from: " + alHistData.get(0).getDate() + " to:"
				+ alHistData.get(alHistData.size() - 1).getDate());

		// Fill with Days where no Data is present;
		HistData histData2, histData3;

		int iPosOfDay = findPositionOfDay(startDate, alHistData);

		Log.info("Starting with " + alHistData.get(iPosOfDay) + " " + iPosOfDay);

		LocalDate currentDate, nextDate;
		currentDate = startDate;
		if (!currentDate.isAfter(endDate)) {
			do {
				histData2 = alHistData.get(iPosOfDay);
				nextDate = getLocalDate(histData2.getDate());
				if (currentDate.isBefore(nextDate)) {
					histData3 = histData2.clone();
					histData3.setDate(Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
					alHistData2.add(histData3);
				} else {
					alHistData2.add(histData2);
					iPosOfDay++;
				}
				currentDate = currentDate.plusDays(1);
			} while (!currentDate.isAfter(endDate));
			long millisStop = Calendar.getInstance().getTimeInMillis();
			Log.info(alHistData2.size() + " Days loaded in " + (millisStop - millisStart) + "ms");
		} else {
			Log.warning("Loading Data fails cause " + startDate + " is after " + endDate);
		}

		Log.info("Collected " + alHistData2.size() + " Days. List is from: " + alHistData2.get(0).getDate() + " to:"
				+ alHistData2.get(alHistData2.size() - 1).getDate());

		// System.out.println(alHistData2);
		return alHistData2;
	}

	private int findPositionOfDay(LocalDate startDate, MyArrayList alHistData3) {
		int iCurrenDay = 0;
		while (startDate.isAfter(getLocalDate(alHistData.get(iCurrenDay).getDate()))) {
			iCurrenDay++;
		}
		return iCurrenDay;
	}

	LocalDate getLocalDate(Date date) {
		Instant instant = date.toInstant();
		ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
		return zdt.toLocalDate();
	}

	double convertValue(String s) {
		return Double.parseDouble(s);
	}

	@Override
	public ArrayList<HistData> load(Symbol s, LocalDate firstDate, LocalDate lastDate) {
		// TODO Auto-generated method stub
		return null;
	}

}

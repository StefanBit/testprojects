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
import util.database.DAOHsqlImpl;
import util.loader.Metric.MetricMapLoaderWorker;
import util.log.Log;

/* Loads Historical Data from URL and returns Array*/

public class HistoricalDataFromAlphavantage implements IHistoricalDataLoader {

	HistData histData;
	MyArrayList alHistData, alHistData2;
	Boolean DEBUG;
	Symbol symbol;
	DateFormat format;
	String inputLine;
	BufferedReader br;
	StringTokenizer st;
	URLConnection conn;
	URL url;

	public HistoricalDataFromAlphavantage() {
		inputLine = null;
		br = null;
		st = null;
		conn = null;
		url = null;
		alHistData = new MyArrayList();
		alHistData2 = new MyArrayList();
	}

	public ArrayList<HistData> load(Symbol symbol) {
		this.symbol = symbol;
		setUrlForRequest();
		openApiConnection();
		getDataFromApi();
		extrapolateData();
		return alHistData2;
	}

	public ArrayList<HistData> load(Symbol symbol, Date firstDate, Date lastDate) {

		LocalDate startDate, endDate;
		startDate = getLocalDate(firstDate);
		endDate = getLocalDate(lastDate);
		Calendar cal = Calendar.getInstance();
		this.format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		cal.setTime(firstDate);
		long millisStart = Calendar.getInstance().getTimeInMillis();

		load(symbol);

		Log.info("Loading Data from " + startDate + " to " + endDate);

		// Fill with Days where no Data is present;
		
		LocalDate currentDate, nextDate;
		HistData histData2, histData3;

		int iPosOfStartDay = findPositionOfDay(startDate, alHistData);
		int iPosOfEndDay = findPositionOfDay(endDate, alHistData);
		
		//Log.info("Starting with " + alHistData.get(iPosOfDay) + " " + iPosOfDay);

		for (int iPosOfCurrentDay= iPosOfStartDay; iPosOfCurrentDay<=iPosOfEndDay;iPosOfCurrentDay++) {
			alHistData2.add(alHistData.get(iPosOfCurrentDay));
		}
		

		Log.info("Will Return " + alHistData2.size() + " Collected Days. List is from: " + alHistData2.get(0).getDate()
				+ " to:" + alHistData2.get(alHistData2.size() - 1).getDate());

		// System.out.println(alHistData2);
		return alHistData2;
	}

	MyArrayList extrapolateData() {
		MyArrayList returnData;
		HistData histData2, histData3;
		LocalDate currentDate, nextDate, endDate;
		returnData = new MyArrayList();
		int iPosOfDay = 0;
		currentDate = getLocalDate(alHistData.get(iPosOfDay).getDate());
		endDate = getLocalDate(alHistData.get(alHistData.size() - 1).getDate());
		if (!currentDate.isAfter(endDate)) {
			do {
				histData2 = alHistData.get(iPosOfDay);
				nextDate = getLocalDate(histData2.getDate());
				if (currentDate.isBefore(nextDate)) {
					histData3 = histData2.clone();
					histData3.setDate(Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
					returnData.add(histData3);
				} else {
					returnData.add(histData2);
					iPosOfDay++;
				}
				currentDate = currentDate.plusDays(1);
			} while (!currentDate.isAfter(endDate));
		}
		this.alHistData =returnData ;
		return returnData;
	}

	private void getDataFromApi() {
		try {
			br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			inputLine = br.readLine();
			// Handle first CSV Line
			st = new StringTokenizer(inputLine, ",");
			while (st.hasMoreTokens()) {
				st.nextToken();
			}
			// Get the Rest of Data
			HistData histData;
			while (((inputLine = br.readLine()) != null)) {
				histData = getHistDataFromString(inputLine);
				alHistData.add(0, histData);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.info("Collected " + alHistData.size() + " Days. List is from: " + alHistData.get(0).getDate() + " to:"
				+ alHistData.get(alHistData.size() - 1).getDate());
	}

	private void openApiConnection() {
		try {
			if ((conn = url.openConnection()) != null) {
				Log.info("Connection to API established.");
			} else {
				Log.warning("Connection to API rejected.");
			}
		} catch (IOException e) {
			if (DEBUG)
				System.out.println("a");
			e.printStackTrace();
		}
	}

	private void setUrlForRequest() {
		String request;
		String sApiKey = TradeFXBusinessController.getInstance().myProperties.getProperty("AlphavantageKey");
		request = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + this.symbol.getName()
				+ "&outputsize=full&apikey=" + sApiKey + "&datatype=csv";
		try {
			this.url = new URL(request);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		Log.info("Request URL will be: " + request);
	}

	private int findPositionOfDay(LocalDate startDate, MyArrayList alHistData) {
		
		Log.info("Looking for Poition of " + startDate + " in Data with Size " + alHistData.size());
		int iCurrenDay=0;
		while (!startDate.equals(getLocalDate(alHistData.get(iCurrenDay).getDate()))) {
			iCurrenDay++;
		}
		Log.info("Pos is"+iCurrenDay);
		return iCurrenDay;
	}

	HistData getHistDataFromString(String inputLine) {
		HistData histData;
		histData = new HistData();
		StringTokenizer st = new StringTokenizer(inputLine, ",");
		int pk = this.symbol.getPk();
		try {
			Date date;
			date = (Date) this.format.parse(st.nextToken());
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
		return histData;
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

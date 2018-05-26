package util.loader.HistoricalData;

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
import model.Symbol;
import util.Log;
import util.database.DAOHsqlImpl;
import util.loader.Metric.MetricMapLoaderWorker;

/* Loads Historical Data from URL and returns Array*/

public class HistoricalDataFromAlphavantage implements IHistoricalDataLoader {

	HistData histData;
	ArrayList<HistData> alHistData;
	Boolean DEBUG;

	public HistoricalDataFromAlphavantage() {
	}

	public ArrayList<HistData> load(Symbol s, Date firstDate, Date lastDate) {

		URL url;
		LocalDate firstLocalDate,lastLocalDate;
		firstLocalDate=getLocalDate(firstDate);
		lastLocalDate=getLocalDate(lastDate);
		
		MyProperties.getDebugSettingFor("DbgHistStockDataLoader");
		alHistData = new ArrayList<HistData>();

		SimpleDateFormat ft = new SimpleDateFormat("YYYY-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(firstDate);
		int fromDay = cal.get(Calendar.DAY_OF_MONTH);
		int fromYear = cal.get(Calendar.YEAR);
		int fromMonth = cal.get(Calendar.MONTH);
		long millisStart = Calendar.getInstance().getTimeInMillis();
		// String request = "http://finance.google.com/finance/historical?q=" +
		// s.getName() + "&startdate=" + fromMonth + "+"+ fromDay + "+" +
		// fromYear + "&output=csv";
		String request = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + s.getName()
				+ "&outputsize=full&apikey="
				+ TradeFXBusinessController.getInstance().myProperties.getProperty("AlphavantageKey") + "&datatype=csv";
		Log.info("Fetch: " + request);

		try {
			StringTokenizer st;
			// DateFormat format = new SimpleDateFormat("dd-MMM-yy",
			// Locale.ENGLISH);
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			url = new URL(request);
			URLConnection conn;

			if ((conn = url.openConnection()) != null) {
				Log.info("Connection to " + url + " established.");
			} else {
				Log.warning("Connection to " + url + " rejected.");
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine = br.readLine();

			// Handle first CSV Line
			st = new StringTokenizer(inputLine, ",");
			while (st.hasMoreTokens()) {
				// System.out.println(inputLine);
				st.nextToken();
				// hst.colnames.addElement(st.nextToken());
			}

			// Data rows
			Double open, hight, low, close, volume;
			open = 0.0;
			hight = 0.0;
			low = 0.0;
			close = 0.0;
			volume = 0.0;

			long diff = lastDate.getTime() - firstDate.getTime();
			int maxLines = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			maxLines = maxLines - (2 * (maxLines % 7));
			System.out.println(maxLines - (2 * (maxLines % 7)));
			
			Log.info("From:" + firstLocalDate + " to " + lastLocalDate);

			// int maxLines= 400;
			int i = 0;
			while (((inputLine = br.readLine()) != null)) {
				st = new StringTokenizer(inputLine, ",");
				i++;
				Integer pk;
				Date date;
				pk = s.getPk();
				date = (Date) format.parse(st.nextToken());
				histData = new HistData();
				histData.setPk(pk);
				histData.setDate(date);
				histData.setOpen(convertValue(st.nextToken()));
				histData.setHight(convertValue(st.nextToken()));
				histData.setLow(convertValue(st.nextToken()));
				histData.setClose(convertValue(st.nextToken()));
				histData.setVolume(convertValue(st.nextToken()));
				alHistData.add(histData);

			}
			br.close();
			

			
			
			Log.info("Collected " + alHistData.size() + " Days");
			// if (DEBUG) System.out.println("Close: " + request);
		} catch (MalformedURLException e) {
			if (DEBUG)
				System.out.println("a");
			e.printStackTrace();
		} catch (IOException e) {
			if (DEBUG)
				System.out.println("a");
			e.printStackTrace();
		} catch (ParseException e) {
			Log.warning(e.toString());
			e.printStackTrace();
		}
		long millisStop = Calendar.getInstance().getTimeInMillis();
		Log.info("request took " + (millisStop - millisStart) + "ms");

		return alHistData;
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

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

public class HistStockDataLoader {

	HistData histData;
	ArrayList<HistData> alHistData;
	Boolean DEBUG;

	public HistStockDataLoader() {
	}

	public ArrayList<HistData> load(Symbol s, Date firstDate, Date lastDate) {

		URL url;
		MyProperties.getDebugSettingFor("DbgHistStockDataLoader");
		alHistData = new ArrayList<HistData>();

		SimpleDateFormat ft = new SimpleDateFormat("YYYY-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(firstDate);
		int fromDay = cal.get(Calendar.DAY_OF_MONTH);
		int fromYear = cal.get(Calendar.YEAR);
		int fromMonth = cal.get(Calendar.MONTH);
		long millisStart = Calendar.getInstance().getTimeInMillis();
		//String request = "http://finance.google.com/finance/historical?q=" + s.getName() + "&startdate=" + fromMonth + "+"+ fromDay + "+" + fromYear + "&output=csv";
		String request = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol="+s.getName()+"&outputsize=full&apikey="+TradeFXBusinessController.getInstance().myProperties.getProperty("AlphavantageKey")+"&datatype=csv";
		Log.info("Fetch: " + request);
		
		try {
			StringTokenizer st;
			//DateFormat format = new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH);
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			url = new URL(request);
			URLConnection conn;
			
			if ((conn = url.openConnection())!=null){
				Log.info("Connection to " + url +"established.");
			} else {
				Log.warning("Connection to " + url +"rejected.");
			}
				
			
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine = br.readLine();
			
			// Handle first CSV Line
			st = new StringTokenizer(inputLine, ",");
			while (st.hasMoreTokens() ) {
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
			    int maxLines= (int)   TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			    maxLines=maxLines-(2*(maxLines%7));
			    System.out.println(maxLines-(2*(maxLines%7)));
			    Log.info("From:"+firstDate+" to "+lastDate);
			
			//int maxLines= 400;
			int i =0;
			while (((inputLine = br.readLine()) != null) & (i<maxLines)) {
				st = new StringTokenizer(inputLine, ",");
				i++;
				Integer pk;
				Date date;
				histData = new HistData();
				pk = s.getPk();
				date = (Date) format.parse(st.nextToken());

				
				try {
					open = Double.parseDouble(st.nextToken());
				} catch (Exception e) {
					System.out.println(e);
				}
				histData.setOpen(open);

				try {
					hight = Double.parseDouble(st.nextToken());
				} catch (Exception e) {
					System.out.println(e);
				}
				histData.setHight(hight);

				try {
					low = Double.parseDouble(st.nextToken());
				} catch (Exception e) {
					System.out.println(e);
				}
				histData.setLow(low);
				
				try {
					close = Double.parseDouble(st.nextToken());
				} catch (Exception e) {
					System.out.println(e);
				}
				histData.setHight(close);
				
				try {
					volume = Double.parseDouble(st.nextToken());
				} catch (Exception e) {
					System.out.println(e);
				}
				histData.setHight(volume);
				
				
				histData.setPk(pk);
				histData.setDate(date);
				histData.setHight(hight);
				histData.setLow(low);
				histData.setClose(close);
				histData.setVolume(volume);
				if (s.getPk() == 2) {
					if (DEBUG) System.out.println(histData);
				}
				alHistData.add(histData);
				if (s.getPk() == 2) {
					if (DEBUG) System.out.println("Rows:" + alHistData.size());
				}
			}
			br.close();
			//if (DEBUG) System.out.println("Close: " + request);
		} catch (

		MalformedURLException e) {
			if (DEBUG) System.out.println("a");
			e.printStackTrace();
		} catch (IOException e) {
			if (DEBUG) System.out.println("a");
			e.printStackTrace();
		} catch (ParseException e) {
			if (DEBUG) System.out.println("c");
			e.printStackTrace();
		}
		long millisStop = Calendar.getInstance().getTimeInMillis();
		Log.info("request took " + (millisStop - millisStart) + "ms");

		return alHistData;
	}

}

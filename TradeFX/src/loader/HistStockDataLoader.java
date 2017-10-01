package loader;

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

import database.DAOHsqlImpl;
import model.HistData;
import model.Symbol;

public class HistStockDataLoader {

	HistData histData;
	ArrayList<HistData> alHistData;

	public HistStockDataLoader() {
	}

	public ArrayList<HistData> load(Symbol s, Date firstDate, Date lastDate) {

		URL url;

		alHistData = new ArrayList<HistData>();

		SimpleDateFormat ft = new SimpleDateFormat("YYYY-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(firstDate);
		int fromDay = cal.get(Calendar.DAY_OF_MONTH);
		int fromYear = cal.get(Calendar.YEAR);
		int fromMonth = cal.get(Calendar.MONTH);
		long millisStart = Calendar.getInstance().getTimeInMillis();
		String request = "http://www.google.com/finance/historical?q=" + s.getName() + "&startdate=" + fromMonth + "+"
				+ fromDay + "%2C+" + fromYear + "&output=csv";
		System.out.println("Fetch: " + request);
		try {
			StringTokenizer st;
			DateFormat format = new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH);
			url = new URL(request);
			URLConnection conn = url.openConnection();
			System.out.println("Connection:" + conn);
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine = br.readLine();
			// Col Names
			st = new StringTokenizer(inputLine, ",");
			while (st.hasMoreTokens()) {
				// System.out.println(inputLine);
				st.nextToken();
				// hst.colnames.addElement(st.nextToken());
			}

			// Data rows
			while ((inputLine = br.readLine()) != null) {
				st = new StringTokenizer(inputLine, ",");

				Integer pk;
				Date date;
				Double open, hight, low, close, volume;
				histData = new HistData();
				pk = s.getPk();
				date = (Date) format.parse(st.nextToken());

				open = 0.0;
				try {
					open = Double.parseDouble(st.nextToken());
				} catch (Exception e) {
					System.out.println(e);
				}
				histData.setOpen(open);

				hight = 0.0;
				try {
					hight = Double.parseDouble(st.nextToken());
				} catch (Exception e) {
					System.out.println(e);
				}
				histData.setHight(hight);

				low = 0.0;
				try {
					low = Double.parseDouble(st.nextToken());
				} catch (Exception e) {
					System.out.println(e);
				}
				histData.setLow(low);
				
				close = 0.0;
				try {
					close = Double.parseDouble(st.nextToken());
				} catch (Exception e) {
					System.out.println(e);
				}
				histData.setHight(close);
				
				volume = 0.0;
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
					System.out.println(histData);
				}
				alHistData.add(histData);
				if (s.getPk() == 2) {
					System.out.println("Rows:" + alHistData.size());
				}
			}
			br.close();
			System.out.println("Close: " + request);
		} catch (

		MalformedURLException e) {
			System.out.println("a");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("a");
			e.printStackTrace();
		} catch (ParseException e) {
			System.out.println("c");
			e.printStackTrace();
		}
		long millisStop = Calendar.getInstance().getTimeInMillis();
		System.out.println(request + " in " + (millisStop - millisStart) + "ms");

		return alHistData;
	}

}

package application;


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
		DaySet ds;

		alHistData=new ArrayList<HistData>();

		SimpleDateFormat ft = new SimpleDateFormat("YYYY-MM-dd");
		Calendar cal=Calendar.getInstance();
		long millisStart = Calendar.getInstance().getTimeInMillis();
		String request = "http://www.google.com/finance/historical?q=NASDAQ:"+s.getName()+"&startdate=Jun+01%2C+2017&output=csv";

		try {
			StringTokenizer st;
			DateFormat format = new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH);
			url = new URL(request);
			URLConnection conn = url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine = br.readLine();
			// Col Names
			st = new StringTokenizer(inputLine, ",");
			while (st.hasMoreTokens()) {
				//System.out.println(inputLine);
				st.nextToken();
				//hst.colnames.addElement(st.nextToken());
			}
			// Data rows
			while ((inputLine = br.readLine()) != null) {
				st = new StringTokenizer(inputLine, ",");
				histData=new HistData(s.getPk(),(Date) format.parse(st.nextToken()), Float.parseFloat(st.nextToken()), Float.parseFloat(st.nextToken()), Float.parseFloat(st.nextToken()),Float.parseFloat(st.nextToken()), Float.parseFloat(st.nextToken()));
				alHistData.add(histData);
			}
			br.close();

		} catch (

		MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long millisStop = Calendar.getInstance().getTimeInMillis();
		System.out.println("in "+ (millisStop - millisStart) + "ms");

		return alHistData;
	}
	

}

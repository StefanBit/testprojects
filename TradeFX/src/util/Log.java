package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import util.loader.HistoricalDataLoader.HistoricalDataFromRandom;

public class Log {
	
	private static final Logger log = Logger.getLogger(HistoricalDataFromRandom.class.getName());
	
	public Log() {
		Handler  handler = new ConsoleHandler();
		handler.setFormatter(new Formatter() {
		      public String format(LogRecord record) {
		        return record.getLevel() + "  :  "
		            + record.getSourceClassName() + " -:- "
		            + record.getSourceMethodName() + " -:- "
		            + record.getMessage() + "\n";
		      }
		    });
		    log.addHandler(handler);

	}
	
	public static void info(String s){
		log.info(s);
	}
	public static void warning(String s){
		log.warning(s);
	}




}

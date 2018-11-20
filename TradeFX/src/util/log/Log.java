package util.log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import util.loader.HistoricalDataLoader.HistoricalDataFromRandom;

public class Log {
	
	private static final Logger log = Logger.getLogger(HistoricalDataFromRandom.class.getName());
	
	public Log() {
	}
	
	public static void setLoggingProperties(String f){
		System.setProperty( "java.util.logging.config.file", f );
		try { LogManager.getLogManager().readConfiguration(); }
		catch ( Exception e ) { e.printStackTrace(); }
	}
	
	public static void info(String s){
		log.info("\t"+s);
	}
	public static void warning(String s){
		log.warning(s);
	}
	public static void fine(String s){
		log.fine("\t\t\t"+s);
	}
	public static void config(String s){
		log.config("\t\t"+s);
	}





}

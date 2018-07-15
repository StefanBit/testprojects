package util;

import java.util.logging.Logger;

import util.loader.HistoricalDataLoader.HistoricalDataFromRandom;

public class Log {
	
	private static final Logger log = Logger.getLogger(HistoricalDataFromRandom.class.getName());
	
	public Log() {

	}
	
	public static void info(String s){
		log.info(s);
	}
	public static void warning(String s){
		log.warning(s);
	}


}

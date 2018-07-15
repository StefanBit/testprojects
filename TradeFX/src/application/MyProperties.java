package application;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import controller.TradeFXBusinessController;
import util.Log;
import util.loader.HistoricalDataLoader.HistoricalDataFromRandom;

public class MyProperties extends Properties {
//	private static final Logger log = Logger.getLogger(MyProperties.class.getName());
	String sPropertiesFile;

	public MyProperties() {
		super();
		establishPropertiesFileToUse();
		loadProperties();
	}

	public String establishPropertiesFileToUse() {
		String fileSeperator = System.getProperty("file.separator");
		String fileName = "TradeFX.properties";
		sPropertiesFile = System.getProperty("user.home") + fileSeperator + fileName;
		File file = new File(sPropertiesFile);
		if (!file.exists()) {
			sPropertiesFile = System.getProperty("user.dir") + fileSeperator + fileName;
		}
		Log.info("Established "+sPropertiesFile+" as Properties File");
		return sPropertiesFile;
	}

	public void loadProperties() {
		BufferedInputStream stream = null;
		try {
			stream = new BufferedInputStream(new FileInputStream(sPropertiesFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			this.load(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

	static public Boolean getDebugSettingFor(String propertieName) {
		Boolean debug;
		String propertie = TradeFXBusinessController.getInstance().myProperties.getProperty(propertieName);
		if (propertie != null) {
			debug = false;
		} else {
			debug = Boolean.valueOf(propertie);
		}
		return debug;
	}

}

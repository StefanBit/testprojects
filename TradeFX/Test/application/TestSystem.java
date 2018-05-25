package application;

import java.io.File;

public class TestSystem {

	public static void main(String[] args) {
		
		
		File file = new File(
				System.getProperty("user.home") + System.getProperty("file.separator") + "TradeFX.properties");
		if (file.exists()) {
			System.out.println(System.getProperty("user.home"));
			// Use File
		} else {
			// Use default or Create File
			System.out.println(System.getProperty("user.dir"));
		}
		;

	}
}

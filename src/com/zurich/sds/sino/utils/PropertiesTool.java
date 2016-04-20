package com.zurich.sds.sino.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesTool {
	public static Properties getProperties(String propertiesFile) throws FileNotFoundException, IOException{

		Properties properties = new Properties();

		// ?¥properties?¾jarè£¡ç?propertiesè®???¹æ?
//	    properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesFile));

		// ?¥properties?¾jarå¤??propertiesè®???¹æ?
		properties.load(new FileInputStream(System.getProperty("user.dir") + "/properties/" + propertiesFile));

		return properties;
	}
}

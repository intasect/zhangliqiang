package com.laiyifen.common.usersync;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.eibus.util.system.EIBProperties;

public class PropertiesClass {
	private final static String PropertiesFilePath = EIBProperties
			.getInstallDir()
			+ File.separator
			+ "laiyifen"
			+ File.separator
			+ "usersynchronization" + File.separator + "usersync.properties";

	private static Properties prop = new Properties();

	static {
		try {
			LogHandler.debug(PropertiesFilePath);
			File file = new File(PropertiesFilePath);
			if (!file.exists()) {
				LogHandler.error("Can not find usersync.properties"
						+ file.getAbsolutePath());
			}
			InputStream in = new FileInputStream(file);
			prop.load(in);
			in.close();
		} catch (FileNotFoundException e) {
			LogHandler.error("Can not find usersync.properties");
		} catch (IOException e) {
			LogHandler.error("Invalid usersync.properties");
		}
	}

	public static String getKeyValue(String key) {
		String result = prop.getProperty(key);
		if (result != null && result.equals(""))
			result = null;
		try {
			result = new String(result.getBytes("ISO8859-1"), "UTF-8");
		} catch (Exception e) {
		}
		return result;
	}

}

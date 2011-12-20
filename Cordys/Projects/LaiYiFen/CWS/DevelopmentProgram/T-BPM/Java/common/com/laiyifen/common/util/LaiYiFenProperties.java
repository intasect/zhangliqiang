/**
 * @Project:LaiYiFen Bop4
 * @FileName:DBDownloadManager.java  2011-7-13
 * Copyright 2011 Cordys Info BV. All rights reserved.
 */
package com.laiyifen.common.util;

import com.eibus.util.system.EIBProperties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LaiYiFenProperties {
	public static final String AppToolKitDefaultPath = EIBProperties.getInstallDir()+ "/laiyifen/";

	public static final String AppToolKitWebPath = EIBProperties.getInstallDir()+ "/web/laiyifen/";

	public static final String AppToolKitInstallPath = EIBProperties.getInstallDir();

	public static final String PropertiesFilePath = AppToolKitDefaultPath+ "LYF_Environment.properties";

	public static final String TempDownloadFilePath = AppToolKitWebPath+ "download/";

	private static Properties prop = new Properties();

	public static String getUploadImplementation() {
		return prop.getProperty("Upload.implementation","Upload.implementation");
	}

	public static String getUploadFileStorage() {
		return AppToolKitDefaultPath
				+ prop.getProperty("Upload.file.storageFolder",
						"upload/storage");
	}

	public static String getDownloadFile() {
		return AppToolKitInstallPath
				+ prop.getProperty("Download.file.downloadFolder");
	}

	public static String getDownloadPath() {
		return prop.getProperty("Download.file.downloadPath");
	}

	public static String getViewFileURL() {
		return prop.getProperty("Download.file.viewFileUrl");
	}

	public static int getRemovedDays() {
		return Integer.parseInt(prop.getProperty("Removed.download.file.Days",
				"1"));
	}

	public static String getUploadDSO() {
		return AppToolKitDefaultPath
				+ prop.getProperty("Upload.db.dsoFile", "upload\\storage");
	}

	public static int getDownloadTempFileKeepTime() {
		return Integer.parseInt(prop
				.getProperty("Upload.DownloadTempFileKeepTime"));
	}

	public static int getLoggingThreadCheckInterval() {
		return Integer.parseInt(prop
				.getProperty("Logging.Thread.CheckInterval"));
	}

	public static int getLoggingConfigNum(){
		return Integer.parseInt(prop.getProperty("Logging.Config.LoggingNum"));
	}

	static {
		try {
			File file = new File(PropertiesFilePath);
			if (!file.exists()) {
				System.out.println("Can not find LYF_Environment.properties");
			}
			InputStream in = new FileInputStream(file);
			prop.load(in);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

package com.laiyifen.common.logging;

import java.util.HashMap;
import java.util.Map;

import com.laiyifen.common.core.COMMON_LOGGER;
import com.laiyifen.common.core.COMMON_LOGGER_CONF;

public class AppLogger
{
	private COMMON_LOGGER_CONF myconf;
	public String CATEGORY;
	private static final Map<String, AppLogger> s_loggerMap = new HashMap<String, AppLogger>();

	private AppLogger(String category,COMMON_LOGGER_CONF logconf)
	{
		this.myconf = logconf;
		this.CATEGORY = category;
	}

	private static AppLogger getAppLogger(String category)
	{
		AppLogger cLogger = (AppLogger) s_loggerMap.get(category);
		if (cLogger == null)
		{
			//init a logger
			COMMON_LOGGER_CONF logconf = COMMON_LOGGER_CONF.getLogConf(category);
			cLogger = new AppLogger(category, logconf);
			s_loggerMap.put(category, cLogger);
		}
		return cLogger;
	}

	public static AppLogger getAppLogger(Class clazz)
	{
		String category = clazz.getName();
		return getAppLogger(category);
	}

	public boolean isEnabled(String severity){
		if(this.myconf != null){
			if(this.myconf.getStringProperty(severity).equals("1")){
				return true;
			}else{
				return false;
			}
		}else{
			if(severity.equals("ERROR") || severity.equals("FATAL")){
				//default severity is ERROR and FATAL
				return true;
			}else{
				return false;
			}
		}
	}
	public void log(String LOG_LEVEL, String CATEGORY, String PATTERN_CATEGORY, String APPLICATION_USER, String SENDER_APPLICATION, String SENDER_COMPONENT, String REQUEST, String RESPONSE, String KEY_NO, String MESSAGE){
		COMMON_LOGGER.log(LOG_LEVEL, this.CATEGORY, PATTERN_CATEGORY, APPLICATION_USER, SENDER_APPLICATION, SENDER_COMPONENT, REQUEST, RESPONSE, KEY_NO, MESSAGE);
	}
}

package com.laiyifen.common.usersync;

import java.io.File;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import com.eibus.util.system.EIBProperties;


class LogHandler
{
    static
    {
        File logConfigueFile = new File(EIBProperties.getInstallDir() + File.separator + 
                "oas" + File.separator + "usersynchronization" +  File.separator + "log4j.xml");
        System.out.println("log configue file path:" + logConfigueFile.getAbsolutePath());
        DOMConfigurator.configure("log4j.xml");
    }
    private static final Logger logger = Logger.getLogger(LogHandler.class);
    
    public static void debug(String msg)
    {
        logger.debug(msg);
    }
    
    public static void info(String info)
    {
        logger.info(info);
    }    
    
    public static void info(String info, Throwable t)
    {
        logger.info(info,t);
    }  
    
    public static void error(String error)
    {
        logger.error(error);
    }  
    
    public static void error(String error, Throwable t)
    {
        logger.error(error,t);
    }      
}
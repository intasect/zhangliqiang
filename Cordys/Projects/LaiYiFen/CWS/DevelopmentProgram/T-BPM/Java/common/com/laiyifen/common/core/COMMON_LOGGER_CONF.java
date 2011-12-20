/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.common.core;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.QueryObject;


public class COMMON_LOGGER_CONF extends COMMON_LOGGER_CONFBase
{
    public COMMON_LOGGER_CONF()
    {
        this((BusObjectConfig)null);
    }

    public COMMON_LOGGER_CONF(BusObjectConfig config)
    {
        super(config);
    }
    public static COMMON_LOGGER_CONF getLogConf(String category){
    	try{
    	String queryText = "select * from \"COMMON_LOGGER_CONF\" where \"CATEGORY\" =:CATEGORY ";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("CATEGORY", "COMMON_LOGGER_CONF.CATEGORY", QueryObject.PARAM_STRING, category);
        query.setResultClass(COMMON_LOGGER_CONF.class);
        return (COMMON_LOGGER_CONF)query.getObject();
    	}catch (Exception e){
    		return null;
    	}
    }

}
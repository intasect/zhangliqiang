/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.common.core;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.QueryObject;


public class AUTO_NUMBER extends AUTO_NUMBERBase
{
    public AUTO_NUMBER()
    {
        this((BusObjectConfig)null);
    }

    public AUTO_NUMBER(BusObjectConfig config)
    {
        super(config);
    }

    public static com.laiyifen.common.core.AUTO_NUMBER getSeqAutoNumberObject(String seqType)
    {
    	String queryText = "select * from \"AUTO_NUMBER\" where \"AUTO_TYPE\" = :seqType";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("seqType", "AUTO_NUMBER.AUTO_TYPE", QueryObject.PARAM_STRING, seqType);
        query.setResultClass(AUTO_NUMBER.class);
        return (AUTO_NUMBER)query.getObject();
    }

}

/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.common.processconfig;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.busobject.QueryObject;


public class ORG_COMPANY_INFO extends ORG_COMPANY_INFOBase
{
    public ORG_COMPANY_INFO()
    {
        this((BusObjectConfig)null);
    }

    public ORG_COMPANY_INFO(BusObjectConfig config)
    {
        super(config);
    }

    public static BusObjectIterator<com.laiyifen.common.processconfig.ORG_COMPANY_INFO> getAllORG_COMPANY_INFO()
    {
    	String queryText = "select * from \"ORG_COMPANY_INFO\"";
        QueryObject query = new QueryObject(queryText);
        query.setResultClass(ORG_COMPANY_INFO.class);
        return query.getObjects();
    }
}

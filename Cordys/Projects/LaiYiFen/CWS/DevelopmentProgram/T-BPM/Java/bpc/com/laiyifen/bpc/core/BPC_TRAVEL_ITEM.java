/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.bpc.core;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.busobject.QueryObject;


public class BPC_TRAVEL_ITEM extends BPC_TRAVEL_ITEMBase
{
    public BPC_TRAVEL_ITEM()
    {
        this((BusObjectConfig)null);
    }

    public BPC_TRAVEL_ITEM(BusObjectConfig config)
    {
        super(config);
    }

    public static BusObjectIterator<com.laiyifen.bpc.core.BPC_TRAVEL_ITEM> getBpcTravelItemObjectsByFormID(String formID)
    {
        // TODO implement body
    	String queryText = "select * from \"BPC_TRAVEL_ITEM\" where \"FORM_ID\" = :Form_ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("Form_ID", "BPC_TRAVEL_ITEM.FORM_ID", QueryObject.PARAM_STRING,formID);
        query.setResultClass(BPC_TRAVEL_ITEM.class);
        return query.getObjects();
    }

}
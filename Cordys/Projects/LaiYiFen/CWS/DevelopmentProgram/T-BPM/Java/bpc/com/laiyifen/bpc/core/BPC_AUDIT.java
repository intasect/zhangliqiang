/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.bpc.core;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.QueryObject;

public class BPC_AUDIT extends BPC_AUDITBase
{
    public BPC_AUDIT()
    {
        this((BusObjectConfig)null);
    }

    public BPC_AUDIT(BusObjectConfig config)
    {
        super(config);
    }

    public static com.laiyifen.bpc.core.BPC_AUDIT getBpcAuditObjectByFormID(String formID)
    {
    	// TODO implement body
    	String queryText = "select * from \"BPC_AUDIT\" where \"FORM_ID\" = :FORM_ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("FORM_ID", "BPC_AUDIT.FORM_ID", QueryObject.PARAM_STRING, formID);//NOPMD
        query.setResultClass(BPC_AUDIT.class);
        return (BPC_AUDIT)query.getObject();
    }

}

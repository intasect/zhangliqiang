/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.bpc.core;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.busobject.QueryObject;


public class BPC_LOAN_ITEM extends BPC_LOAN_ITEMBase
{
    public BPC_LOAN_ITEM()
    {
        this((BusObjectConfig)null);
    }

    public BPC_LOAN_ITEM(BusObjectConfig config)
    {
        super(config);
    }

    public static BusObjectIterator<com.laiyifen.bpc.core.BPC_LOAN_ITEM> getBpcLoanItemObjectsByFormID(String formID)
    {
    	// TODO implement body
   	 	String queryText = "select * from \"BPC_LOAN_ITEM\" where \"FORM_ID\" = :Form_ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("Form_ID", "BPC_LOAN_ITEM.FORM_ID", QueryObject.PARAM_STRING,formID);
        query.setResultClass(BPC_LOAN_ITEM.class);
        return query.getObjects();
    }

}

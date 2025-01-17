/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.goods.core;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.busobject.QueryObject;


public class GROUPBUY_ORDER_DETAIL extends GROUPBUY_ORDER_DETAILBase
{
    public GROUPBUY_ORDER_DETAIL()
    {
        this((BusObjectConfig)null);
    }

    public GROUPBUY_ORDER_DETAIL(BusObjectConfig config)
    {
        super(config);
    }

    public static BusObjectIterator<com.laiyifen.goods.core.GROUPBUY_ORDER_DETAIL> getGroupbuyOrderDetailByOrderId(String orderId)
    {
    	StringBuffer queryText = new StringBuffer();
    	QueryObject query = null;	
    	queryText.append("SELECT * FROM GROUPBUY_ORDER_DETAIL WHERE ORDER_ID ='").append(orderId).append("'"); 	
    	query = new QueryObject(queryText.toString());
        query.setResultClass(GROUPBUY_ORDER_DETAIL.class);
        return query.getObjects();
    }

}

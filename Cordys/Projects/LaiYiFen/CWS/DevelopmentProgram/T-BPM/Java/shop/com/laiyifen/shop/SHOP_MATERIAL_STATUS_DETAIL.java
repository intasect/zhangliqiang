/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.shop;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.busobject.DMLStatement;
import com.cordys.cpc.bsf.busobject.QueryObject;
import com.cordys.cpc.bsf.util.ObjectHelper;
import com.eibus.util.logger.CordysLogger;
import com.eibus.util.logger.Severity;
import com.laiyifen.shop.SHOP_MATERIAL_STATUS_DETAIL;


public class SHOP_MATERIAL_STATUS_DETAIL extends SHOP_MATERIAL_STATUS_DETAILBase
{
	private static CordysLogger logger = CordysLogger.getCordysLogger(SHOP_MATERIAL_STATUS_DETAIL.class);
	
    public SHOP_MATERIAL_STATUS_DETAIL()
    {
        this((BusObjectConfig)null);
    }

    public SHOP_MATERIAL_STATUS_DETAIL(BusObjectConfig config)
    {
        super(config);
    }

    public static BusObjectIterator<com.laiyifen.shop.SHOP_MATERIAL_STATUS_DETAIL> getShopMaterialStatusDetailObjectsForFormId(String FORM_ID)
    {
        String queryText = "select * from \"SHOP_MATERIAL_STATUS_DETAIL\" where \"FORM_ID\" = :FORM_ID and SALES_STATUS = 'X'";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("FORM_ID", "SHOP_MATERIAL_STATUS_DETAIL.FORM_ID", QueryObject.PARAM_STRING, FORM_ID);//NOPMD
        query.setResultClass(SHOP_MATERIAL_STATUS_DETAIL.class);
        return query.getObjects();
    }
    
    public static BusObjectIterator<com.laiyifen.shop.SHOP_MATERIAL_STATUS_DETAIL> getShopMaterialStatusDetailObjectFormIdStatus(String FORM_ID)
    {
        String queryText = "select * from \"SHOP_MATERIAL_STATUS_DETAIL\" where \"FORM_ID\" = :FORM_ID and SALES_STATUS is null";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("FORM_ID", "SHOP_MATERIAL_STATUS_DETAIL.FORM_ID", QueryObject.PARAM_STRING, FORM_ID);//NOPMD
        query.setResultClass(SHOP_MATERIAL_STATUS_DETAIL.class);
        return query.getObjects();
    }

    public static void setAllShopMatenal(String formId, String shopId)
    {
    	
    	String dmlQueryText = "insert into SHOP_MATERIAL_STATUS_DETAIL(FORM_ID,SHOP_ID,PRODUCT_CODE,PRODUCT_NAME) select '"+formId+"','"+shopId+"',PRODUCT_CODE,PRODUCT_NAME from MATERIAL_AVER_PRICE where COMPANYCODE ='2000'"; 
    	logger.log(Severity.ERROR, "getSelectMaterialAverPriceObjects--查询移动平均价--开始"+dmlQueryText, null);
    	DMLStatement dml = new DMLStatement(dmlQueryText);
    	 // dml.addParameter("", "", );
    	 try 
    	 { 
    		 dml.execute(); // This creates the XQY request and passes it to the XQY layer } 
    	 }
    	catch (Exception e) 
    	{
    		e.printStackTrace(); 
    	} 
    }

}

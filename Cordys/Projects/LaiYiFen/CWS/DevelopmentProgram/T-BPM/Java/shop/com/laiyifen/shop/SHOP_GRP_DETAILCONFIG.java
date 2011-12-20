/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.shop;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.busobject.QueryObject;
import com.laiyifen.common.util.GUIDUtil;


public class SHOP_GRP_DETAILCONFIG extends SHOP_GRP_DETAILCONFIGBase
{
    public SHOP_GRP_DETAILCONFIG()
    {
        this((BusObjectConfig)null);
    }

    public SHOP_GRP_DETAILCONFIG(BusObjectConfig config)
    {
        super(config);
    }

    public static BusObjectIterator<com.laiyifen.shop.SHOP_GRP_DETAILCONFIG> getShopGrpDetailconfigObjectsByGrpId(String GRP_ID)
    {
    	String queryText = "select * from \"SHOP_GRP_DETAILCONFIG\" where \"GRP_ID\" = :GRP_ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("GRP_ID", "SHOP_GRP_DETAILCONFIG.GRP_ID", QueryObject.PARAM_STRING, GRP_ID);
        query.setResultClass(SHOP_GRP_DETAILCONFIG.class);
        return query.getObjects();
    }

    /**
     * 表SHOP_GRP_DETAILCONFIG增加记录时执行
     */
    @Override
	public void onInsert() {
		GUIDUtil util = new GUIDUtil();
		util.setGUID(null);
		String guid = util.getGUID();
		this.setID(guid);
	}
}
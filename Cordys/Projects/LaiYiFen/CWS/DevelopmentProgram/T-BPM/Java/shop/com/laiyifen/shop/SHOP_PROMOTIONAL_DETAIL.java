/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.shop;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.busobject.QueryObject;
import com.eibus.util.logger.CordysLogger;
import com.eibus.util.logger.Severity;
import com.laiyifen.common.util.GUIDUtil;


public class SHOP_PROMOTIONAL_DETAIL extends SHOP_PROMOTIONAL_DETAILBase
{
	private static CordysLogger logger = CordysLogger.getCordysLogger(SHOP_PROMOTIONAL_DETAIL.class);
	
    public SHOP_PROMOTIONAL_DETAIL()
    {
        this((BusObjectConfig)null);
    }

    public SHOP_PROMOTIONAL_DETAIL(BusObjectConfig config)
    {
        super(config);
    }

    /**
     * 根据条件(COMPANY_CODE, SHOP_NO, START_DATE, END_DATE, USER_CODE, HeadquarterFlag)对SHOP_PROMOTIONAL_DETAIL记录进行查询
     * @param COMPANY_CODE	公司编码
     * @param SHOP_NO	门店编码
     * @param START_DATE	起始时间
     * @param END_DATE		终止时间
     * @param USER_CODE		用户编码
     * @param HeadquarterFlag	总部标志 true：总部人员, 其他：分公司人员
     * @return
     */
    public static BusObjectIterator<com.laiyifen.shop.SHOP_PROMOTIONAL_DETAIL> getShopPromotionalDetailByFilter(String COMPANY_CODE, String SHOP_NO, String START_DATE, String END_DATE, String USER_CODE, String HeadquarterFlag)
    {
    	// 拼装动态查询语句
    	StringBuffer queryText = new StringBuffer();
    	queryText.append("select * from \"SHOP_PROMOTIONAL_DETAIL\" where 1=1 ");
    	QueryObject query = null;
    	
    	String querySHOP_PROMOTION = SHOP_PROMOTION.getShopPromotionByFilterSQL(COMPANY_CODE, SHOP_NO, USER_CODE, HeadquarterFlag);
    	if(!"".equals(querySHOP_PROMOTION)){
    		queryText.append(" and \"FORM_ID\" in (select FORM_ID from (" + querySHOP_PROMOTION + ")) ");
    	}
    	if(!"".equals(START_DATE) && !"".equals(END_DATE)){
    		queryText.append(" and \"START_DATE\" >= :START_DATE ");
    		queryText.append(" and \"END_DATE\" <= :END_DATE ");
    		logger.log(Severity.ERROR, "SQL: " + queryText.toString(), null);
    		query = new QueryObject(queryText.toString());
    		query.addParameter("START_DATE", "SHOP_PROMOTIONAL_DETAIL.START_DATE", QueryObject.PARAM_DATE, START_DATE);
    		query.addParameter("END_DATE", "SHOP_PROMOTIONAL_DETAIL.END_DATE", QueryObject.PARAM_DATE, END_DATE);
    	}
    	
    	if(!"".equals(START_DATE) && "".equals(END_DATE)){
    		queryText.append(" and \"START_DATE\" >= :START_DATE ");
    		logger.log(Severity.ERROR, "SQL: " + queryText.toString(), null);
    		query = new QueryObject(queryText.toString());
    		query.addParameter("START_DATE", "SHOP_PROMOTIONAL_DETAIL.START_DATE", QueryObject.PARAM_DATE, START_DATE);
    	}
    	
    	if("".equals(START_DATE) && !"".equals(END_DATE)){
    		queryText.append(" and \"END_DATE\" <= :END_DATE ");
    		query = new QueryObject(queryText.toString());
    		logger.log(Severity.ERROR, "SQL: " + queryText.toString(), null);
    		query.addParameter("END_DATE", "SHOP_PROMOTIONAL_DETAIL.END_DATE", QueryObject.PARAM_DATE, END_DATE);
    	}
    	if(null == query){
    		logger.log(Severity.DEBUG, "SQL: " + queryText.toString(), null);
    		query = new QueryObject(queryText.toString());
    	}
        query.setResultClass(SHOP_PROMOTIONAL_DETAIL.class);
        return query.getObjects();
    }
    
    /**
     * 根据条件(SHOP_ID, USER_CODE, STAUTS)对SHOP_PROMOTIONAL_DETAIL记录进行查询
     * @param SHOP_ID	门店ID
     * @param USER_CODE	用户编码
     * @param STAUTS	业务表单状态
     * @return
     */
    public static BusObjectIterator<com.laiyifen.shop.SHOP_PROMOTIONAL_DETAIL> getShopPromotionalDetailByStatus(String SHOP_ID, String USER_CODE, String STATUS)
    {
    	// 拼装动态查询语句
    	StringBuffer queryText = new StringBuffer();
    	queryText.append("select * from \"SHOP_PROMOTIONAL_DETAIL\" where 1=1 ");
    	QueryObject query = null;
    	
    	queryText.append(" and \"FORM_ID\" in " +
    			"(select FORM_ID from SHOP_PROMOTION where \"SHOP_ID\" = :SHOP_ID " +
    			" and \"USER_CODE\" =:USER_CODE " +
    			" and \"STATUS\" =:STATUS)");
    	
    	query = new QueryObject(queryText.toString());
    	query.addParameter("SHOP_ID", "SHOP_PROMOTION.SHOP_ID", QueryObject.PARAM_STRING, SHOP_ID);
    	query.addParameter("USER_CODE", "SHOP_PROMOTION.USER_CODE", QueryObject.PARAM_STRING, USER_CODE);
    	query.addParameter("STATUS", "SHOP_PROMOTION.STATUS", QueryObject.PARAM_STRING, STATUS);
        query.setResultClass(SHOP_PROMOTIONAL_DETAIL.class);
        return query.getObjects();
    }

	@Override
	public void onInsert() {
		// 设置主键 ID
		GUIDUtil util = new GUIDUtil();
		util.setGUID(null);
		String id = util.getGUID();
		this.setID(id);
	}

	@Override
	public void onDelete() {
		// TODO Auto-generated method stub
		super.onDelete();
	}

	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub
		super.onUpdate();
	}
}
/*
  This class has been generated by the Code Generator
 */

package com.laiyifen.shop;

import java.util.Date;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.busobject.QueryObject;
import com.laiyifen.common.util.GUIDUtil;

import com.eibus.util.logger.CordysLogger;
import com.eibus.util.logger.Severity;

public class SHOP_PROMOTION extends SHOP_PROMOTIONBase {
	
	private static CordysLogger logger = CordysLogger.getCordysLogger(SHOP_PROMOTION.class);
	
	public SHOP_PROMOTION() {
		this((BusObjectConfig) null);
	}

	public SHOP_PROMOTION(BusObjectConfig config) {
		super(config);
	}

	/**
	 * 根据条件(COMPANY_CODE, SHOP_NO, USER_CODE,
	 * HeadquarterFlag)进行SHOP_PROMOTION记录的查询
	 * 
	 * @param COMPANY_CODE
	 *            公司编码
	 * @param SHOP_NO
	 *            门店编码
	 * @param USER_CODE
	 *            用户编码
	 * @param HeadquarterFlag
	 *            总部标志 true：总部人员, 其他：分公司人员
	 * @return
	 */
	public static BusObjectIterator<com.laiyifen.shop.SHOP_PROMOTION> getShopPromotionByFilter(
			String COMPANY_CODE, String SHOP_NO, String USER_CODE,
			String HeadquarterFlag) {
		// 拼装动态查询语句
		StringBuffer queryText = new StringBuffer();
		queryText.append("select * from \"SHOP_PROMOTION\" where 1=1 ");

		QueryObject query = null;

		if (!"".equals(COMPANY_CODE.trim())) {
			queryText.append(" and \"COMPANY_CODE\" = '" + COMPANY_CODE.trim()
					+ "'");
		}

		if (!"".equals(SHOP_NO.trim())) {
			queryText
					.append(" and \"SHOP_ID\" = ( select SHOP_ID form SHOP_MASTER where \"SHOP_NO\" = '"
							+ SHOP_NO + "') ");
		}

		if (!"true".equals(HeadquarterFlag.toLowerCase().trim())) { // 子公司人员，根据用户编号进行查询
			queryText.append(" and \"USER_CODE\" ='" + USER_CODE.trim() + "'");
		}

		logger.log(Severity.ERROR, "SQL: " + queryText.toString(), null);
		
		query = new QueryObject(queryText.toString());
		query.setResultClass(SHOP_PROMOTION.class);
		return query.getObjects();
	}

	/**
	 * 根据条件(COMPANY_CODE, SHOP_NO, USER_CODE,
	 * HeadquarterFlag)获取对SHOP_PROMOTION记录的查询SQL
	 * 
	 * @param COMPANY_CODE
	 *            公司编码
	 * @param SHOP_NO
	 *            门店编码
	 * @param USER_CODE
	 *            用户编码
	 * @param HeadquarterFlag
	 *            总部标志 true：总部人员, 其他：分公司人员
	 * @return
	 */
	public static String getShopPromotionByFilterSQL(String COMPANY_CODE,
			String SHOP_NO, String USER_CODE, String HeadquarterFlag) {
		// 拼装动态查询语句
		StringBuffer queryText = new StringBuffer();
		queryText.append("select * from \"SHOP_PROMOTION\" where 1=1 ");

		QueryObject query = null;

		if (!"".equals(COMPANY_CODE.trim())) {
			queryText.append(" and \"COMPANY_CODE\" = '" + COMPANY_CODE.trim()
					+ "'");
		}

		if (!"".equals(SHOP_NO.trim())) {
			queryText
					.append(" and \"SHOP_ID\" = ( select SHOP_ID from SHOP_MASTER where \"SHOP_NO\" = '"
							+ SHOP_NO + "') ");
		}

		if (!"true".equals(HeadquarterFlag.toLowerCase().trim())) { // 子公司人员，根据用户编号进行查询
			queryText.append(" and \"USER_CODE\" ='" + USER_CODE.trim() + "'");
		}

		return queryText.toString();
	}

	/**
     * 根据条件(SHOP_ID)对SHOP_PROMOTIO记录进行查询
     * @param SHOP_ID	门店ID
     * @return
     */
    public static com.laiyifen.shop.SHOP_PROMOTION queryShopPromotionBySHOP_ID(String SHOP_ID)
    {
    	StringBuffer queryText = new StringBuffer();
    	QueryObject query = null;
    	
    	queryText.append("select * from SHOP_PROMOTION where \"SHOP_ID\" = :SHOP_ID ");
    	
    	query = new QueryObject(queryText.toString());
    	query.addParameter("SHOP_ID", "SHOP_PROMOTION.SHOP_ID", QueryObject.PARAM_STRING, SHOP_ID);
        query.setResultClass(SHOP_PROMOTION.class);
        return (SHOP_PROMOTION)query.getObject();
    }
    
	/**
	 * 表SHOP_PROMOTION增加记录时执行
	 */
	@Override
	public void onInsert() {
		// 设置主键 FORM_ID
		GUIDUtil util = new GUIDUtil();
		util.setGUID(null);
		String formId = util.getGUID();
		this.setFORM_ID(formId);
		// 设置字段CREATE_TIME
		Date date = new Date();
		this.setAPPLICATION_DATE(date);
	}
}

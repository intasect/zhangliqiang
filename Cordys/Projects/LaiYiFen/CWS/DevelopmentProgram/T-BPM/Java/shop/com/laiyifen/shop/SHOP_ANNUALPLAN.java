/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.shop;

import java.util.Date;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.busobject.QueryObject;
import com.eibus.util.logger.CordysLogger;
import com.laiyifen.common.util.GUIDUtil;


public class SHOP_ANNUALPLAN extends SHOP_ANNUALPLANBase
{
	private static CordysLogger logger = CordysLogger.getCordysLogger(SHOP_ANNUALPLAN.class);
	
    public SHOP_ANNUALPLAN()
    {
        this((BusObjectConfig)null);
    }

    public SHOP_ANNUALPLAN(BusObjectConfig config)
    {
        super(config);
    }

    /**
     * 根据条件(YEAR, AREA, USER_DN, HeadquarterFlag)进行SHOP_ANNUALPLAN记录的查询
     * @param YEAR 年度
     * @param AREA 区域
     * @param USER_DN 用户DN
     * @param HeadquarterFlag 总部标志 true：总部人员, 其他：分公司人员
     * @return
     */
    public static BusObjectIterator<com.laiyifen.shop.SHOP_ANNUALPLAN> getShopAnnualPlanByFilter(String YEAR, String AREA, String USER_CODE, String HeadquarterFlag)
    {
    	// 拼装动态查询语句
    	StringBuffer queryText = new StringBuffer();
    	queryText.append("select * from \"SHOP_ANNUALPLAN\" where 1=1 ");
    	
    	QueryObject query = null;
    	
    	if(!"".equals(YEAR.trim())){
    		queryText.append(" and \"YEAR\" = '" + YEAR.trim() + "'");
    	} 
    	
    	if(!"".equals(AREA.trim())){
    		queryText.append(" and \"AREA\" ='" + AREA.trim() + "'");
    	}
    	
    	if(!"true".equals(HeadquarterFlag.toLowerCase().trim())){ // 子公司人员，根据用户DN进行查询
    		queryText.append(" and \"USER_CODE\" ='" + USER_CODE.trim() + "'");
    	}
    	
    	query = new QueryObject(queryText.toString());
        query.setResultClass(SHOP_ANNUALPLAN.class);
        return query.getObjects();
    }

    /**
     * 根据条件(YEAR, AREA, USER_DN)查询出一条SHOP_ANNUALPLAN记录
     * @param YEAR 年度
     * @param AREA 区域
     * @param USER_DN 用户DN
     * @return
     */
    public static com.laiyifen.shop.SHOP_ANNUALPLAN queryShopAnnualplanByFilter(String YEAR, String AREA, String USER_CODE)
    {
    	String queryText = "select * from \"SHOP_ANNUALPLAN\" where \"YEAR\" = :YEAR and \"AREA\" = :AREA and \"USER_CODE\" = :USER_CODE ";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("YEAR", "SHOP_ANNUALPLAN.YEAR", QueryObject.PARAM_STRING, YEAR);
        query.addParameter("AREA", "SHOP_ANNUALPLAN.AREA", QueryObject.PARAM_STRING, AREA);
        query.addParameter("USER_CODE", "SHOP_ANNUALPLAN.USER_CODE", QueryObject.PARAM_STRING, USER_CODE);
        query.setResultClass(SHOP_ANNUALPLAN.class);
        return (SHOP_ANNUALPLAN)query.getObject();
    }

    /**
     * 表SHOP_ANNUALPLAN增加记录时执行
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
		this.setCREATE_TIME(date);
	}
}

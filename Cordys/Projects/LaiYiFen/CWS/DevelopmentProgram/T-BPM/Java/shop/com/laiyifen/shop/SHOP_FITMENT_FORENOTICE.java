/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.shop;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.busobject.BusObjectManager;
import com.cordys.cpc.bsf.busobject.QueryObject;
import com.cordys.cpc.bsf.event.AccessMode;
import com.cordys.cpc.bsf.event.AfterAttributeChangeEvent;
import com.cordys.cpc.bsf.event.AttributeAccessEvent;
import com.cordys.cpc.bsf.event.AttributeInitializeEvent;
import com.cordys.cpc.bsf.event.BeforeCommitObjectEvent;
import com.eibus.util.logger.CordysLogger;
import com.eibus.util.logger.Severity;
import com.laiyifen.common.core.APPROVAL_HISTORY;
import com.laiyifen.common.core.ATTACHMENT;
import com.laiyifen.common.core.User;


public class SHOP_FITMENT_FORENOTICE extends SHOP_FITMENT_FORENOTICEBase
{
	private static CordysLogger logger = CordysLogger.getCordysLogger(SHOP_DRAWINGS.class);
	
	private User userObj = null;
	
    public SHOP_FITMENT_FORENOTICE()
    {
        this((BusObjectConfig)null);
    }

    public SHOP_FITMENT_FORENOTICE(BusObjectConfig config)
    {
        super(config);
    }
    
    public User getCurrUserInfo()
    {
    	if( this.userObj==null)
    	{
    		this.userObj= User.getUserInfo();
    	}
    	
    	return this.userObj;

    }
    
    @SuppressWarnings({ "unchecked", "static-access" })
	@Override
    public void onBeforeCommit(BeforeCommitObjectEvent event) {

    	super.onBeforeCommit(event);
    	
    	BusObjectIterator<APPROVAL_HISTORY> approvalHistoryObjects = this.getObjectManager().getObjects(BusObjectManager.SCOPE_TRANSACTION).getObjectsByType(APPROVAL_HISTORY.class);
    	while(approvalHistoryObjects.hasMoreElements())
    	{
    		APPROVAL_HISTORY approvalHistoryObject = (APPROVAL_HISTORY)approvalHistoryObjects.nextElement();
    		approvalHistoryObject.setAttributeValueIntegrity(this, this.ATTR_FORM_ID, APPROVAL_HISTORY.ATTR_FORM_ID);
    	}
    	
    	BusObjectIterator<ATTACHMENT> attachmentObjects = this.getObjectManager().getObjects(BusObjectManager.SCOPE_TRANSACTION).getObjectsByType(ATTACHMENT.class);
    	while(attachmentObjects.hasMoreElements())
    	{
    		ATTACHMENT attachmentObject = (ATTACHMENT)attachmentObjects.nextElement();
    		attachmentObject.setAttributeValueIntegrity(this, this.ATTR_FORM_ID, ATTACHMENT.ATTR_FORM_ID);
    	}
    }

    /**
     * 通过查询条件获取进场装修预告相关信息
     * @param shopNo	门店编号
     * @param shopName	门店名称
     * @param layoutType	布局类型
     * @param useArea	销售厅面积
     * @param storageArea	后仓面积
     * @return SHOP_DRAWINGS对象
     */
    public static BusObjectIterator<com.laiyifen.shop.SHOP_FITMENT_FORENOTICE> getSelectFitmentObjects(String shopNo, String shopName, java.util.Date actualInTimeBegin, java.util.Date actualInTimeEnd, java.util.Date estimateTimeBegin, java.util.Date estimateTimeEnd, com.cordys.cpc.bsf.query.Cursor cursor)
    {
    	logger.log(Severity.INFO, "getSelectFitmentObjects--查询进场装修预告--开始", null);
    	
    	StringBuffer strBuf = new StringBuffer();
    	strBuf.append("select s.SHOP_ID,s.FORM_ID,s.USER_NAME,s.USER_CODE,s.DEPARTMENT_CODE,s.DEPARTMENT_NAME,s.APPLICATION_DATE,s.SUBCOMPANY_CODE," +
    			"s.SUBCOMPANY_NAME,s.CONTRACT_DATE,s.ACTUAL_DATE,s.ACTUAL_IN_TIME,s.ESTIMATE_TIME,s.DIRECTOR,s.DIRECTOR_CONTACT,s.CONSTRUCTION_TEAM,s.CONSTRUCTION_CONTACT,s.STATUS,m.SHOP_NO EXT1,m.SHOP_NAME EXT2 " +
    			"from \"SHOP_FITMENT_FORENOTICE\" s,\"SHOP_MASTER\" m where s.SHOP_ID = m.SHOP_ID ");
        if (null != com.laiyifen.common.core.User.getUserInfo().getCompanyCode() && "1000".equals(com.laiyifen.common.core.User.getUserInfo().getCompanyCode()))
        {
        	strBuf.append("and s.\"STATUS\" <> 0 ");
        }
        else
        {
        	strBuf.append("and s.\"USER_CODE\" = :user_code ");
        }
        
        if (null != shopNo && !("".equals(shopNo)) && "" != shopNo)
        {
        	strBuf.append("and m.\"SHOP_NO\" like :shopNo ");
        }
        if (null != shopName && !("".equals(shopName)) && "" != shopName)
        {
        	strBuf.append("and m.\"SHOP_NAME\" like :shopName "); 
        }
        if (null != actualInTimeBegin) 
		{
        	strBuf.append("and  to_char(s.ACTUAL_IN_TIME ,'yyyy-MM-dd') >= :actualInTimeBegin ");
		}
		if (null != actualInTimeEnd) 
		{
			strBuf.append("and  to_char(s.ACTUAL_IN_TIME ,'yyyy-MM-dd') <= :actualInTimeEnd ");
		}
		if (null != estimateTimeBegin) 
		{
        	strBuf.append("and  to_char(s.ESTIMATE_TIME ,'yyyy-MM-dd') >= :estimateTimeBegin ");
		}
		if (null != estimateTimeEnd) 
		{
			strBuf.append("and  to_char(s.ESTIMATE_TIME ,'yyyy-MM-dd') <= :estimateTimeEnd ");
		}
        
        QueryObject query = new QueryObject(strBuf.toString());
        
        if (null != shopNo && !("".equals(shopNo)) && "" != shopNo)
        {
        	 query.addParameter("shopNo", "SHOP_MASTER.SHOP_NO", QueryObject.PARAM_STRING, "%"+shopNo+"%");
        }
        if (null != shopName && !("".equals(shopName)) && "" != shopName)
        {
        	 query.addParameter("shopName", "SHOP_MASTER.SHOP_NAME", QueryObject.PARAM_STRING, "%"+shopName+"%");
        }
        if (null != actualInTimeBegin) 
		{
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String datebegin = sDateFormat.format(actualInTimeBegin);
			query.addParameter("actualInTimeBegin", "SHOP_FITMENT_FORENOTICE.ACTUAL_IN_TIME", QueryObject.PARAM_STRING, datebegin);
		}
		if (null != actualInTimeEnd) 
		{
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String dateend = sDateFormat.format(actualInTimeEnd);
			query.addParameter("actualInTimeEnd", "SHOP_FITMENT_FORENOTICE.ACTUAL_IN_TIME", QueryObject.PARAM_STRING, dateend);
		}
		if (null != estimateTimeBegin) 
		{
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String datebegin = sDateFormat.format(estimateTimeBegin);
			query.addParameter("estimateTimeBegin", "SHOP_FITMENT_FORENOTICE.ESTIMATE_TIME", QueryObject.PARAM_STRING, datebegin);
		}
		if (null != estimateTimeEnd) 
		{
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String dateend = sDateFormat.format(estimateTimeEnd);
			query.addParameter("estimateTimeEnd", "SHOP_FITMENT_FORENOTICE.ESTIMATE_TIME", QueryObject.PARAM_STRING, dateend);
		}
        
        
        if (null != com.laiyifen.common.core.User.getUserInfo().getCompanyCode() && !("1000".equals(com.laiyifen.common.core.User.getUserInfo().getCompanyCode())))
        {
        	query.addParameter("user_code", "SHOP_FITMENT_FORENOTICE.USER_CODE", QueryObject.PARAM_STRING, com.laiyifen.common.core.User.getUserInfo().getUserCode());
        }
        
        
        query.setResultClass(SHOP_FITMENT_FORENOTICE.class);
        query.setCursor(cursor);
        
        
    	logger.log(Severity.INFO, "getSelectFitmentObjects--查询进场装修预告--结束", null);
    	return query.getObjects();
    }

    public static com.laiyifen.shop.SHOP_FITMENT_FORENOTICE getShopIdFitmentObject(String shopId)
    {
    	StringBuffer queryText = new StringBuffer();
    	QueryObject query = null;
    	
    	queryText.append("select * from SHOP_FITMENT_FORENOTICE where \"SHOP_ID\" = :SHOP_ID ");
    	queryText.append(" AND APPLICATION_DATE =(SELECT MAX(APPLICATION_DATE) FROM SHOP_FITMENT_FORENOTICE WHERE SHOP_ID="+"'"+shopId+"')");
    	
    	query = new QueryObject(queryText.toString());
    	query.addParameter("SHOP_ID", "SHOP_FITMENT_FORENOTICE.SHOP_ID", QueryObject.PARAM_STRING, shopId);
        query.setResultClass(SHOP_FITMENT_FORENOTICE.class);
        return (SHOP_FITMENT_FORENOTICE)query.getObject();
    }

    /*
     * 控制是否修改.
     */
    public void onDisplay_USER_NAME(AttributeAccessEvent context){
    	context.setAccess(AccessMode.READONLY);
    }
    /*
     * 输入框改变时，给提示信息.
     */
    public void onAfterChange_CITY(AfterAttributeChangeEvent event)
    {
    	//this.setCITY("This is shanghai city");
    }
    /*
     * 初始化用户信息
     */
    // 用户姓名
    public void onInitialize_USER_NAME(AttributeInitializeEvent context){
    	context.setInitialValue(this.getCurrUserInfo().getUserName());
    }
    //用户代码
    public void onInitialize_USER_CODE(AttributeInitializeEvent context){
    	context.setInitialValue(this.getCurrUserInfo().getUserCode());
    }
    //部门名称
    public void onInitialize_DEPARTMENT_NAME(AttributeInitializeEvent context){
    	context.setInitialValue(this.getCurrUserInfo().getDeptName());
    }
    //部门代码
    public void onInitialize_DEPARTMENT_CODE(AttributeInitializeEvent context){
    	context.setInitialValue(this.getCurrUserInfo().getDeptCode());
    }
    
    //子公司代码
    public void onInitialize_SUBCOMPANY_CODE(AttributeInitializeEvent context){
    	context.setInitialValue(this.getCurrUserInfo().getCompanyCode());
    }
	//子公司名称
    public void onInitialize_SUBCOMPANY_NAME(AttributeInitializeEvent context){
    	context.setInitialValue(this.getCurrUserInfo().getCompanyName());
    }
    
    
    //创建时间
    public void onInitialize_APPLICATION_DATE(AttributeInitializeEvent context){
    	SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");      
    	String date = sDateFormat.format(new Date());
    	context.setInitialValue(date);
    }

}

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


public class SHOP_DRAWINGS extends SHOP_DRAWINGSBase
{
	
	private static CordysLogger logger = CordysLogger.getCordysLogger(SHOP_DRAWINGS.class);
	
	private User userObj = null;
	
    public SHOP_DRAWINGS()
    {
        this((BusObjectConfig)null);
    }

    public SHOP_DRAWINGS(BusObjectConfig config)
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
     * 通过查询条件获取装修设计审核相关信息
     * @param shopNo	门店编号
     * @param shopName	门店名称
     * @param layoutType	布局类型
     * @param useArea	销售厅面积
     * @param storageArea	后仓面积
     * @return SHOP_DRAWINGS对象
     */
    public static BusObjectIterator<com.laiyifen.shop.SHOP_DRAWINGS> getSelectDrawingsObjects(String shopNo, String shopName, String layoutType, double useArea, double storageArea, com.cordys.cpc.bsf.query.Cursor cursor)
    {
    	
    	logger.log(Severity.INFO, "getSelectDrawingsObjects--查询装修设计审核--开始", null);
    	
    	StringBuffer strBuf = new StringBuffer();
    	strBuf.append("select s.SHOP_ID,s.FORM_ID,s.USER_NAME,s.USER_CODE,s.DEPARTMENT_CODE,s.DEPARTMENT_NAME,s.APPLICATION_DATE,s.SUBCOMPANY_CODE," +
    			"s.SUBCOMPANY_NAME,s.FREE_RENT,s.USE_AREA,s.STORAGE_AREA,s.LAYOUT_TYPE,s.LAYOUT_DESC,s.MEMO,s.STATUS,m.SHOP_NO EXT1,m.SHOP_NAME EXT2 " +
    			"from \"SHOP_DRAWINGS\" s,\"SHOP_MASTER\" m where s.SHOP_ID = m.SHOP_ID ");
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
        if (null != layoutType && !("".equals(layoutType)) && "" != layoutType)
        {
        	strBuf.append("and s.\"LAYOUT_TYPE\" = :layoutType ");
        }
        
		strBuf.append("and s.\"USE_AREA\" > :useArea ");
		strBuf.append("and s.\"STORAGE_AREA\" > :storageArea ");
		

        
        QueryObject query = new QueryObject(strBuf.toString());
        
        if (null != shopNo && !("".equals(shopNo)) && "" != shopNo)
        {
        	 query.addParameter("shopNo", "SHOP_MASTER.SHOP_NO", QueryObject.PARAM_STRING, "%"+shopNo+"%");
        }
        if (null != shopName && !("".equals(shopName)) && "" != shopName)
        {
        	 query.addParameter("shopName", "SHOP_MASTER.SHOP_NAME", QueryObject.PARAM_STRING, "%"+shopName+"%");
        }
        if (null != layoutType && !("".equals(layoutType)) && "" != layoutType)
        {
        	 query.addParameter("layoutType", "SHOP_DRAWINGS.LAYOUT_TYPE", QueryObject.PARAM_STRING, layoutType);
        }
        query.addParameter("useArea", "SHOP_DRAWINGS.USE_AREA", QueryObject.PARAM_STRING, useArea);
		query.addParameter("storageArea", "SHOP_DRAWINGS.STORAGE_AREA", QueryObject.PARAM_STRING, storageArea);
        
        if (null != com.laiyifen.common.core.User.getUserInfo().getCompanyCode() && !("1000".equals(com.laiyifen.common.core.User.getUserInfo().getCompanyCode())))
        {
        	query.addParameter("user_code", "SHOP_DRAWINGS.USER_CODE", QueryObject.PARAM_STRING, com.laiyifen.common.core.User.getUserInfo().getUserCode());
        }
        
        
        query.setResultClass(SHOP_DRAWINGS.class);
        query.setCursor(cursor);
        
        
    	logger.log(Severity.INFO, "getSelectDrawingsObjects--查询装修设计审核--结束", null);
    	return query.getObjects();
    }

    public static com.laiyifen.shop.SHOP_DRAWINGS getShopIdDrawingsObject(String shopId)
    {
    	StringBuffer queryText = new StringBuffer();
    	QueryObject query = null;
    	
    	queryText.append("select * from SHOP_DRAWINGS where \"SHOP_ID\" = :SHOP_ID ");
    	queryText.append(" AND APPLICATION_DATE =(SELECT MAX(APPLICATION_DATE) FROM SHOP_DRAWINGS WHERE SHOP_ID="+"'"+shopId+"')");
    	
    	query = new QueryObject(queryText.toString());
    	query.addParameter("SHOP_ID", "SHOP_DRAWINGS.SHOP_ID", QueryObject.PARAM_STRING, shopId);
        query.setResultClass(SHOP_DRAWINGS.class);
        return (SHOP_DRAWINGS)query.getObject();
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

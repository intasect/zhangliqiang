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
import com.laiyifen.common.core.APPROVAL_HISTORY;
import com.laiyifen.common.core.ATTACHMENT;
import com.laiyifen.common.core.User;


public class SHOP_MATERIAL_STATUS extends SHOP_MATERIAL_STATUSBase
{
	private static CordysLogger logger = CordysLogger.getCordysLogger(SHOP_DRAWINGS.class);
	
	private User userObj = null;
	
    public SHOP_MATERIAL_STATUS()
    {
        this((BusObjectConfig)null);
    }

    public SHOP_MATERIAL_STATUS(BusObjectConfig config)
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
    	
    	BusObjectIterator<SHOP_MATERIAL_STATUS_DETAIL> shopMaterialStatusDetailObj = this.getObjectManager().getObjects(BusObjectManager.SCOPE_TRANSACTION).getObjectsByType(APPROVAL_HISTORY.class);
    	while(shopMaterialStatusDetailObj.hasMoreElements())
    	{
    		SHOP_MATERIAL_STATUS_DETAIL shopMaterialStatusDetail = (SHOP_MATERIAL_STATUS_DETAIL)shopMaterialStatusDetailObj.nextElement();
    		shopMaterialStatusDetail.setAttributeValueIntegrity(this, this.ATTR_FORM_ID, SHOP_MATERIAL_STATUS_DETAIL.ATTR_FORM_ID);
    	}
    }
    
    
    public static com.laiyifen.shop.SHOP_MATERIAL_STATUS getShopIdMaterialStatusObject(String shopId)
    {
    	StringBuffer queryText = new StringBuffer();
    	QueryObject query = null;
    	
    	queryText.append("select * from SHOP_MATERIAL_STATUS where \"SHOP_ID\" = :SHOP_ID ");
    	queryText.append(" AND APPLICATION_DATE =(SELECT MAX(APPLICATION_DATE) FROM SHOP_MATERIAL_STATUS WHERE SHOP_ID="+"'"+shopId+"')");
    	
    	query = new QueryObject(queryText.toString());
    	query.addParameter("SHOP_ID", "SHOP_MATERIAL_STATUS.SHOP_ID", QueryObject.PARAM_STRING, shopId);
        query.setResultClass(SHOP_MATERIAL_STATUS.class);
        return (SHOP_MATERIAL_STATUS)query.getObject();
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

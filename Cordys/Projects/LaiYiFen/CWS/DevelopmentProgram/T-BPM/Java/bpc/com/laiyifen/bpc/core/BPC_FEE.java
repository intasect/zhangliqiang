/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.bpc.core;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.busobject.BusObjectManager;
import com.cordys.cpc.bsf.busobject.QueryObject;
import com.cordys.cpc.bsf.event.AttributeInitializeEvent;
import com.cordys.cpc.bsf.event.BeforeCommitObjectEvent;
import com.laiyifen.bpc.util.BpcCommonUtil;
import com.laiyifen.common.core.APPROVAL_HISTORY;
import com.laiyifen.common.core.ATTACHMENT;
import com.laiyifen.common.core.User;


public class BPC_FEE extends BPC_FEEBase
{
	private User userObj = null;
	
    public BPC_FEE()
    {
        this((BusObjectConfig)null);
    }

    public BPC_FEE(BusObjectConfig config)
    {
        super(config);
    }

    private User getCurrUserInfo()
	{
	  if(this.userObj==null)
		  this.userObj= com.laiyifen.common.core.User.getUserInfo();
	  return this.userObj;
	}
    
    public static com.laiyifen.bpc.core.BPC_FEE getBpcFeeObjectByFormID(String formID)
    {
    	// TODO implement body
   	 	String queryText = "select * from \"BPC_FEE\" where \"FORM_ID\" = :FORM_ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("FORM_ID", "BPC_FEE.FORM_ID", QueryObject.PARAM_STRING, formID);//NOPMD
        query.setResultClass(BPC_FEE.class);
        return (BPC_FEE)query.getObject();
    }

    public static BusObjectIterator<com.laiyifen.bpc.core.BPC_FEE> getBpcFeeObjectsByLoanCode(String loanBillCode){
    	String queryText = "select * from \"BPC_FEE\" where \"LOANBILL_CODE\" = :LOANBILL_CODE";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("LOANBILL_CODE", "BPC_FEE.LOANBILL_CODE", QueryObject.PARAM_STRING, loanBillCode);//NOPMD
        query.setResultClass(BPC_FEE.class);
        return query.getObjects();
    }
    
    public static BusObjectIterator<com.laiyifen.bpc.core.BPC_FEE> getBpcFeeObjectsByParams(String user_id, String department_id, java.util.Date petitiondatebegin, java.util.Date petitiondateend)
    {
       // 拼装动态查询语句
    	StringBuffer queryText = new StringBuffer();
    	queryText.append("select * from \"BPC_FEE\" where 1=1 ");
    	   if (null != user_id && !("".equals(user_id)) && "" != user_id)
           {
    		   queryText.append("and \"USER_CODE\" = :user_id ");
           }
           if (null != department_id && !("".equals(department_id)) && "" != department_id)
           {
        	   queryText.append("and \"DEPARTMENT_CODE\" = :department_id "); 
           }
          if (null != petitiondatebegin )
           {
        	   queryText.append("and  to_char(PETITION_DATE ,'yyyy-MM-dd') >= :petitiondatebegin ");
           }
            if (null != petitiondateend )
           {
        	   queryText.append("and  to_char(PETITION_DATE ,'yyyy-MM-dd') <= :petitiondateend ");
           }  
  
           QueryObject query = new QueryObject(queryText.toString());
           
           if (null != user_id && !("".equals(user_id)) && "" != user_id)
           {
           	 query.addParameter("user_id", "BPC_FEE.USER_CODE", QueryObject.PARAM_STRING, user_id);
           }
           if (null != department_id && !("".equals(department_id)) && "" != department_id)
           {
           	 query.addParameter("department_id", "BPC_FEE.DEPARTMENT", QueryObject.PARAM_STRING, department_id);
           }
           if (null != petitiondatebegin )
           {
        	   SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");      
           	   String datebegin = sDateFormat.format(petitiondatebegin);
           	 query.addParameter("petitiondatebegin", "BPC_FEE.PETITION_DATE", QueryObject.PARAM_STRING, datebegin);
           }
           if (null != petitiondateend )
           {
        	   SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");      
           	   String dateend = sDateFormat.format(petitiondateend);
           	   query.addParameter("petitiondateend", "BPC_FEE.PETITION_DATE", QueryObject.PARAM_STRING, dateend);
           }
           
        query.setResultClass(BPC_FEE.class);
        return query.getObjects();
    }
    
    
    public static void deleteBpcFeeObjectByFormID(String formID)
    {
    	BusObjectIterator<ATTACHMENT> attachmentObj = ATTACHMENT.getAttachment(formID);
		if (attachmentObj != null) {
			while (attachmentObj.hasMoreElements()) {
				ATTACHMENT attachmentInfo = (ATTACHMENT) attachmentObj.nextElement();
				attachmentInfo.delete();
			}
		}
		
		BusObjectIterator<APPROVAL_HISTORY> approvalHistoryObj = APPROVAL_HISTORY.getApprovalHistory(formID);
		if (approvalHistoryObj != null) {
			while (approvalHistoryObj.hasMoreElements()) {
				APPROVAL_HISTORY approvalHistoryInfo = (APPROVAL_HISTORY) approvalHistoryObj.nextElement();
				approvalHistoryInfo.delete();
			}
		}
		
		BusObjectIterator<BPC_FEEDEP> bpcFeedepObj = BPC_FEEDEP.getBpcFeedepObjectsByFormID(formID);
		if (bpcFeedepObj != null) {
			while (bpcFeedepObj.hasMoreElements()) {
				BPC_FEEDEP bpcFeedepInfo = (BPC_FEEDEP) bpcFeedepObj.nextElement();
				bpcFeedepInfo.delete();
			}
		}
		
		BusObjectIterator<BPC_FEE_ITEM> bpcFeeItemObj = BPC_FEE_ITEM.getBpcFeeItemObjectsByFormID(formID);
		if (bpcFeeItemObj != null) {
			while (bpcFeeItemObj.hasMoreElements()) {
				BPC_FEE_ITEM bpcFeeItemInfo = (BPC_FEE_ITEM) bpcFeeItemObj.nextElement();
				bpcFeeItemInfo.delete();
			}
		}
		
		BPC_AUDIT bpcAuditObj = BPC_AUDIT.getBpcAuditObjectByFormID(formID);
		if (bpcAuditObj != null) {
			bpcAuditObj.delete();
		}
		
		BPC_FEE bpcFeeObj = BPC_FEE.getBpcFeeObjectByFormID(formID);
		if (bpcFeeObj != null) {
			bpcFeeObj.delete();
		}
    }
    
    /*
     * 初始化用户信息
     */
    public void onInitialize_PETITION_DATE(AttributeInitializeEvent context){
    	SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");      
    	String date = sDateFormat.format(new Date());
    	context.setInitialValue(date);
    }
    public void onInitialize_USER_CODE(AttributeInitializeEvent context){
    	context.setInitialValue(this.getCurrUserInfo().getUserCode());
    }
    public void onInitialize_USER_NAME(AttributeInitializeEvent context){
    	context.setInitialValue(this.getCurrUserInfo().getUserName());
    }
    public void onInitialize_DEPARTMENT_CODE(AttributeInitializeEvent context){
    	context.setInitialValue(this.getCurrUserInfo().getDeptCode());
    }
    public void onInitialize_DEPARTMENT_NAME(AttributeInitializeEvent context){
    	context.setInitialValue(this.getCurrUserInfo().getDeptName());
    }
    public void onInitialize_COSTCENTER_CODE(AttributeInitializeEvent context){
    	context.setInitialValue(BpcCommonUtil.getCostcenterFormatZero(this.getCurrUserInfo().getCostCenter()));
    }
    public void onInitialize_COMPANY_CODE(AttributeInitializeEvent context){
    	context.setInitialValue(this.getCurrUserInfo().getCompanyCode());
    }
    public void onInitialize_USER_PHONE(AttributeInitializeEvent context){
    	context.setInitialValue(this.getCurrUserInfo().getMobile());
    }
    public void onInitialize_USER_RANK(AttributeInitializeEvent context){
    	context.setInitialValue(this.getCurrUserInfo().getLyfLevel());
    }
    
    @SuppressWarnings({ "unchecked", "static-access" })
	@Override
    public void onBeforeCommit(BeforeCommitObjectEvent event) {

    	super.onBeforeCommit(event);
    	
    	BusObjectIterator<BPC_AUDIT> bpcAuditObjects = this.getObjectManager().getObjects(BusObjectManager.SCOPE_TRANSACTION).getObjectsByType(BPC_AUDIT.class);
    	while(bpcAuditObjects.hasMoreElements())
    	{
    		BPC_AUDIT bpcAuditObject = (BPC_AUDIT)bpcAuditObjects.nextElement();
    		bpcAuditObject.setAttributeValueIntegrity(this, this.ATTR_FORM_ID, BPC_AUDIT.ATTR_FORM_ID);
    	}
    	
    	BusObjectIterator<BPC_FEEDEP> bpcFeedepObjects = this.getObjectManager().getObjects(BusObjectManager.SCOPE_TRANSACTION).getObjectsByType(BPC_FEEDEP.class);
    	while(bpcFeedepObjects.hasMoreElements())
    	{
    		BPC_FEEDEP bpcFeedepObject = (BPC_FEEDEP)bpcFeedepObjects.nextElement();
    		bpcFeedepObject.setAttributeValueIntegrity(this, this.ATTR_FORM_ID, BPC_FEEDEP.ATTR_FORM_ID);
    	}
    	
    	BusObjectIterator<BPC_FEE_ITEM> bpcFeeItemObjects = this.getObjectManager().getObjects(BusObjectManager.SCOPE_TRANSACTION).getObjectsByType(BPC_FEE_ITEM.class);
    	while(bpcFeeItemObjects.hasMoreElements())
    	{
    		BPC_FEE_ITEM bpcFeeItemObject = (BPC_FEE_ITEM)bpcFeeItemObjects.nextElement();
    		bpcFeeItemObject.setAttributeValueIntegrity(this, this.ATTR_FORM_ID, BPC_FEE_ITEM.ATTR_FORM_ID);
    	}
    	
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
    
    //更新bpcFee ,记录sap返回信息
    //formID，sap凭证号，年度号
    public static boolean updateBpcFeeObjectBySAPReturn(String formID ,String E_BELNR,String E_GJAHR,String E_MESSAGE,String E_MSGTY){
    	BPC_FEE bpcFee = getBpcFeeObjectByFormID(formID);
    	bpcFee.setVOUCHER_CODE(E_BELNR);
    	bpcFee.setFINANCIAL_YEAR(E_GJAHR);
    	bpcFee.setVOUCHER_STATUS(E_MSGTY);
    	bpcFee.setVOUCHER_MSG(E_MESSAGE);
		return true;
    }
    
}

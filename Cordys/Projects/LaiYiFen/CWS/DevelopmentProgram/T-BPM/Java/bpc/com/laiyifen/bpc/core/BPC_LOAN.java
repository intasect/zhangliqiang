/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.bpc.core;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.busobject.BusObjectManager;
import com.cordys.cpc.bsf.busobject.QueryObject;
import com.cordys.cpc.bsf.event.AttributeInitializeEvent;
import com.cordys.cpc.bsf.event.BeforeCommitObjectEvent;
import com.cordys.cpc.bsf.query.Cursor;
import com.laiyifen.bpc.util.BpcCommonUtil;
import com.laiyifen.common.core.APPROVAL_HISTORY;
import com.laiyifen.common.core.ATTACHMENT;
import com.laiyifen.common.core.User;


public class BPC_LOAN extends BPC_LOANBase
{
	private User userObj = null;
	
    public BPC_LOAN()
    {
        this((BusObjectConfig)null);
    }

    public BPC_LOAN(BusObjectConfig config)
    {
        super(config);
    }

    private User getCurrUserInfo()
	{
	  if(this.userObj==null)
		  this.userObj= com.laiyifen.common.core.User.getUserInfo();
	  return this.userObj;
	}

    
    public static void deleteBpcLoanObjectByFormID(String formID)
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

		BusObjectIterator<BPC_LOAN_ITEM> bpcLoanItemObj = BPC_LOAN_ITEM.getBpcLoanItemObjectsByFormID(formID);
		if (bpcLoanItemObj != null) {
			while (bpcLoanItemObj.hasMoreElements()) {
				BPC_LOAN_ITEM bpcLoanItemInfo = (BPC_LOAN_ITEM) bpcLoanItemObj.nextElement();
				bpcLoanItemInfo.delete();
			}
		}
		
		BPC_AUDIT bpcAuditObj = BPC_AUDIT.getBpcAuditObjectByFormID(formID);
		if (bpcAuditObj != null) {
			bpcAuditObj.delete();
		}
		
		BPC_LOAN bpcLoanObj = BPC_LOAN.getBpcLoanObjectByFormID(formID);
		if (bpcLoanObj != null) {
			bpcLoanObj.delete();
		}
    }

    public static com.laiyifen.bpc.core.BPC_LOAN getBpcLoanObjectByFormID(String formID)
    {
    	// TODO implement body
   	 	String queryText = "select * from \"BPC_LOAN\" where \"FORM_ID\" = :FORM_ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("FORM_ID", "BPC_LOAN.FORM_ID", QueryObject.PARAM_STRING, formID);//NOPMD
        query.setResultClass(BPC_LOAN.class);
        return (BPC_LOAN)query.getObject();
    }
    
    public static com.laiyifen.bpc.core.BPC_LOAN getBpcLoanObjectByBillCode(String BillCode)
    {
    	// TODO implement body
   	 	String queryText = "select * from \"BPC_LOAN\" where \"RECODE_CODE\" = :BILL_CODE";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("BILL_CODE", "BPC_LOAN.RECODE_CODE", QueryObject.PARAM_STRING, BillCode);//NOPMD
        query.setResultClass(BPC_LOAN.class);
        return (BPC_LOAN)query.getObject();
    }

    public static BusObjectIterator<com.laiyifen.bpc.core.BPC_LOAN> getBpcLoanObjectsByParams(String user_id, String department_id, java.util.Date petitiondatebegin, java.util.Date petitiondateend)
    {
    	 // 拼装动态查询语句
	    	StringBuffer queryText = new StringBuffer();
	    	queryText.append("select * from \"BPC_LOAN\" where 1=1 ");
    	   if (null != user_id && !("".equals(user_id)) && "" != user_id)
           {
    		   queryText.append("and \"USER_CODE\" = :user_id ");
           }
           if (null != department_id && !("".equals(department_id)) && "" != department_id)
           {
        	   queryText.append("and \"DEPARTMENT\" = :department_id "); 
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
           	 query.addParameter("user_id", "BPC_LOAN.USER_CODE", QueryObject.PARAM_STRING, user_id);
           }
           if (null != department_id && !("".equals(department_id)) && "" != department_id)
           {
           	 query.addParameter("department_id", "BPC_LOAN.DEPARTMENT", QueryObject.PARAM_STRING, department_id);
           }
           if (null != petitiondatebegin )
           {
        	   SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");      
           	   String datebegin = sDateFormat.format(petitiondatebegin);
           	 query.addParameter("petitiondatebegin", "BPC_LOAN.PETITION_DATE", QueryObject.PARAM_STRING, datebegin);
           }
           if (null != petitiondateend )
           {
        	   SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");      
           	   String dateend = sDateFormat.format(petitiondateend);
           	   query.addParameter("petitiondateend", "BPC_LOAN.PETITION_DATE", QueryObject.PARAM_STRING, dateend);
           }
           
        query.setResultClass(BPC_LOAN.class);
        return query.getObjects();
    }
    
    
    public static BusObjectIterator<com.laiyifen.bpc.core.BPC_LOAN> getBpcLoanEndObjectsByParams(String user_id, String department_id, java.util.Date petitiondatebegin, java.util.Date petitiondateend)
    {
    	 // 拼装动态查询语句
	    	StringBuffer queryText = new StringBuffer();
	    	queryText.append("select * from \"BPC_LOAN\" where STATUS='2' ");
    	   if (null != user_id && !("".equals(user_id)) && "" != user_id)
           {
    		   queryText.append("and \"USER_CODE\" = :user_id ");
           }
           if (null != department_id && !("".equals(department_id)) && "" != department_id)
           {
        	   queryText.append("and \"DEPARTMENT\" = :department_id "); 
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
           	 query.addParameter("user_id", "BPC_LOAN.USER_CODE", QueryObject.PARAM_STRING, user_id);
           }
           if (null != department_id && !("".equals(department_id)) && "" != department_id)
           {
           	 query.addParameter("department_id", "BPC_LOAN.DEPARTMENT", QueryObject.PARAM_STRING, department_id);
           }
           if (null != petitiondatebegin )
           {
        	   SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");      
           	   String datebegin = sDateFormat.format(petitiondatebegin);
           	 query.addParameter("petitiondatebegin", "BPC_LOAN.PETITION_DATE", QueryObject.PARAM_STRING, datebegin);
           }
           if (null != petitiondateend )
           {
        	   SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");      
           	   String dateend = sDateFormat.format(petitiondateend);
           	   query.addParameter("petitiondateend", "BPC_LOAN.PETITION_DATE", QueryObject.PARAM_STRING, dateend);
           }
           
        query.setResultClass(BPC_LOAN.class);
        return query.getObjects();
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
    	
    	BusObjectIterator<BPC_LOAN_ITEM> bpcLoanItemObjects = this.getObjectManager().getObjects(BusObjectManager.SCOPE_TRANSACTION).getObjectsByType(BPC_LOAN_ITEM.class);
    	while(bpcLoanItemObjects.hasMoreElements())
    	{
    		BPC_LOAN_ITEM bpcLoanItemObject = (BPC_LOAN_ITEM)bpcLoanItemObjects.nextElement();
    		bpcLoanItemObject.setAttributeValueIntegrity(this, this.ATTR_FORM_ID, BPC_LOAN_ITEM.ATTR_FORM_ID);
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
    
    public static void updateBpcLoanSurplusByRepay(String formType,String formID ,String operateType){
    	String loanID = "";
    	Double sum = 0.0 ;
    	String YEAR = "" ,VOUCHER_CODE = "" ;
    	if("BPC_FEE".equals(formType)){
    		BPC_FEE bpcFee = BPC_FEE.getBpcFeeObjectByFormID(formID);
    		loanID = bpcFee.getLOANBILL_CODE();
    		sum = bpcFee.getPAY_SUM();
    		YEAR = bpcFee.getFINANCIAL_YEAR();
    		VOUCHER_CODE = bpcFee.getVOUCHER_CODE();
    	}else if("BPC_TRAVEL".equals(formType)){
    		BPC_TRAVEL bpcTravel = BPC_TRAVEL.getBpcTravelObjectByFormID(formID);
    		loanID = bpcTravel.getLOANBILL_CODE();
    		sum = bpcTravel.getPAY_SUM();
    		YEAR = bpcTravel.getFINANCIAL_YEAR();
    		VOUCHER_CODE = bpcTravel.getVOUCHER_CODE();
    	}else if("BPC_VERIFICATION".equals(formType)){
    		BPC_VERIFICATION bpcVerification = BPC_VERIFICATION.getBpcVerificationObjectByFormID(formID);
    		loanID = bpcVerification.getLOANBILL_CODE();
    		sum = bpcVerification.getPAY_SUM();
    		YEAR = bpcVerification.getFINANCIAL_YEAR();
    		VOUCHER_CODE = bpcVerification.getVOUCHER_CODE();
    	}
    	if(loanID!=null && !"".equals(loanID)){
	    	BPC_LOAN bpcLoanObj = BPC_LOAN.getBpcLoanObjectByBillCode(loanID);
	    	Double surplus = bpcLoanObj.getLOAN_SURPLUS() ;
	    	if("1".equals(operateType)){
		    	if( surplus == null ){
		    		surplus = bpcLoanObj.getTOTAL_AMOUNT();
		    	}
		    	surplus =  surplus - sum ;
		    	bpcLoanObj.setLOAN_SURPLUS(surplus);
	    	}
			BPC_LOG_LOAN.newBpcLogLoanObject(formID, formType, YEAR, VOUCHER_CODE, bpcLoanObj.getFORM_ID(),surplus, sum,  loanID, operateType, "");
    	}
    }
    
    //更新bpcLoan 
    //formID，sap凭证号，年度号
    public static boolean updateBpcLoanObjectBySAPReturn(String formID ,String E_BELNR,String E_GJAHR,String E_MESSAGE,String E_MSGTY){
    	BPC_LOAN bpcLoan = getBpcLoanObjectByFormID(formID);
    	bpcLoan.setVOUCHER_CODE(E_BELNR);
    	bpcLoan.setFINANCIAL_YEAR(E_GJAHR);
    	bpcLoan.setVOUCHER_STATUS(E_MSGTY);
    	bpcLoan.setVOUCHER_MSG(E_MESSAGE);
		return true;
    }
    
    //借款清帐，查询相关借款单，冲销单凭证，年度号
    //formID，sap凭证号，年度号
    public static List getBpcClearAccountListByLoanID(String loanBillCode){
    	if(loanBillCode==null) 
    		return null;
    	List<String> list = new ArrayList();
    	BPC_LOAN bpcLoan = getBpcLoanObjectByBillCode(loanBillCode);
    	Double loanSurplus = bpcLoan.getLOAN_SURPLUS();
    	String loanID = bpcLoan.getFORM_ID();
    	if(loanSurplus == 0.0){
    		list.add(bpcLoan.getVOUCHER_CODE() + "," + bpcLoan.getFINANCIAL_YEAR());
	    	BusObjectIterator<BPC_FEE> BpcFeeObjects = BPC_FEE.getBpcFeeObjectsByLoanCode(loanBillCode);
	    	if(BpcFeeObjects!=null){
	    		while(BpcFeeObjects.hasMoreElements()){
	    			BPC_FEE bpcFee = BpcFeeObjects.nextElement();
	    			if(bpcFee.getVOUCHER_CODE()!=null){
		    			String stmp = bpcFee.getVOUCHER_CODE() + "," + bpcFee.getFINANCIAL_YEAR();
		    			list.add(stmp);
	    			}
	    		}
	    	}
	    	BusObjectIterator<BPC_TRAVEL> BpcTravelObjects = BPC_TRAVEL.getBpcTravelObjectsByLoanCode(loanBillCode);
	    	if(BpcTravelObjects!=null){
	    		while(BpcTravelObjects.hasMoreElements()){
	    			BPC_TRAVEL bpcTravel = BpcTravelObjects.nextElement();
	    			if(bpcTravel.getVOUCHER_CODE()!=null){
	    			String stmp = bpcTravel.getVOUCHER_CODE() + "," + bpcTravel.getFINANCIAL_YEAR();
	    			list.add(stmp);
	    			}
	    		}
	    	}
	    	BusObjectIterator<BPC_VERIFICATION> BpcVerificationObjects = BPC_VERIFICATION.getBpcVerificationObjectsByLoanCode(loanBillCode);
	    	if(BpcVerificationObjects!=null){
	    		while(BpcVerificationObjects.hasMoreElements()){
	    			BPC_VERIFICATION bpcVerification = BpcVerificationObjects.nextElement();
	    			if(bpcVerification.getVOUCHER_CODE()!=null){
	    			String stmp = bpcVerification.getVOUCHER_CODE() + "," + bpcVerification.getFINANCIAL_YEAR();
	    			list.add(stmp);
	    			}
	    		}
	    	}
    	}
		return list;
    }
}

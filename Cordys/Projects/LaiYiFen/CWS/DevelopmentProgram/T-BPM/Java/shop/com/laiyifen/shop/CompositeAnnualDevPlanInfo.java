/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.shop;

import java.util.Vector;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.event.AfterCommitObjectEvent;
import com.cordys.cpc.bsf.util.ObjectHelper;
import com.eibus.util.logger.CordysLogger;
import com.eibus.util.logger.Severity;
import com.laiyifen.common.core.APPROVAL_HISTORY;
import com.laiyifen.common.core.ATTACHMENT;
import com.laiyifen.common.util.BusObjectHelper;
import com.laiyifen.shop.util.ShopUtil;


public class CompositeAnnualDevPlanInfo extends CompositeAnnualDevPlanInfoBase
{
	
	private SHOP_MONTHLY_PLAN shopMonthlyPlanObject = null;
	private APPROVAL_HISTORY approvalHistoryObject = null;
	private Vector<APPROVAL_HISTORY> approvalHistorys = new Vector<APPROVAL_HISTORY>();
	private ATTACHMENT attachment = null;
	private Vector<ATTACHMENT> attachments = new Vector<ATTACHMENT>();
	
	private static CordysLogger logger = CordysLogger.getCordysLogger(CompositeAnnualDevPlanInfo.class);
	
    public CompositeAnnualDevPlanInfo()
    {
        this((BusObjectConfig)null);
    }

    public CompositeAnnualDevPlanInfo(BusObjectConfig config)
    {
        super(config);
    }

    public static com.laiyifen.shop.CompositeAnnualDevPlanInfo getCompositeAnnualDevPlanInfoObject(String formId)
    {
    	
    	logger.log(Severity.INFO, "getCompositeAnnualDevPlanInfoObject--开始", null);
    	
    	// 新建网点年度规划复合对象实例
    	CompositeAnnualDevPlanInfo compositeAnnualDevPlanObject = new CompositeAnnualDevPlanInfo();

    	// 根据formID取得SHOP_MONTHLY_PLAN表中记录并设置到复合对象
    	SHOP_MONTHLY_PLAN shopMonthlyPlan = SHOP_MONTHLY_PLAN.getShopMonthlyPlanObject(formId);
    	compositeAnnualDevPlanObject.setSHOP_MONTHLY_PLANObject(shopMonthlyPlan);

    	//取得上传附件数据并设置到复合对象
		BusObjectIterator<ATTACHMENT> attachmentObj = ATTACHMENT
				.getAttachment(formId);
		if (attachmentObj != null) {
			while (attachmentObj.hasMoreElements()) {
				ATTACHMENT attachmentInfo = (ATTACHMENT) attachmentObj
						.nextElement();
				compositeAnnualDevPlanObject.addATTACHMENTObject(attachmentInfo);
			}
		}
		
		//取得审批记录数据并设置到复合对象
		BusObjectIterator<APPROVAL_HISTORY> approvalHistoryObj = APPROVAL_HISTORY
				.getApprovalHistory(formId);
		if (approvalHistoryObj != null) {
			while (approvalHistoryObj.hasMoreElements()) {
				APPROVAL_HISTORY approvalHistoryInfo = (APPROVAL_HISTORY) approvalHistoryObj
						.nextElement();
				compositeAnnualDevPlanObject
						.addAPPROVAL_HISTORYObject(approvalHistoryInfo);
			}
		}
		compositeAnnualDevPlanObject.makeTransient();
		
		logger.log(Severity.INFO, "getCompositeAnnualDevPlanInfoObject--结束", null);
		
		return compositeAnnualDevPlanObject;
    }

    public static BusObjectIterator<com.laiyifen.shop.CompositeAnnualDevPlanInfo> getCompositeAnnualDevPlanInfoObjects(com.cordys.cpc.bsf.query.Cursor cursor)
    {
        // TODO implement body
        return null;
    }

    public void onInsert()
    {
    	
    	logger.log(Severity.INFO, "CompositeAnnualDevPlanInfo--onInsert--开始", null);
    	
    	// Removing the unnecessary objects from the parent object.
		BusObjectHelper.removeEmptyChildren(this);
		
		SHOP_MONTHLY_PLAN shopMonthlyPlan = this.getSHOP_MONTHLY_PLANObject();
		if (shopMonthlyPlan != null) {
			ShopUtil _formIDGenerator = new ShopUtil();
			String formID = _formIDGenerator.setFormID(null);
                        /*
                       ShopUtil _shopIDGenerator = new ShopUtil();
			String shopID = _shopIDGenerator.setShopID("");
                       */
			shopMonthlyPlan.setFORM_ID(formID);
			shopMonthlyPlanObject = (SHOP_MONTHLY_PLAN) ObjectHelper.createObjectForInsert(shopMonthlyPlan, 
					com.laiyifen.shop.SHOP_MONTHLY_PLAN.class, false);
			shopMonthlyPlanObject.insert();
		}

		BusObjectIterator<APPROVAL_HISTORY> approvalHistoryObj = this
				.getAPPROVAL_HISTORYObjects();
		if (approvalHistoryObj != null) {
			while (approvalHistoryObj.hasMoreElements()) {
				APPROVAL_HISTORY approvalHistoryInfo = (APPROVAL_HISTORY) approvalHistoryObj
						.nextElement();
				approvalHistoryObject = (APPROVAL_HISTORY) ObjectHelper
						.createObjectForInsert(
								approvalHistoryInfo,
								com.laiyifen.common.core.APPROVAL_HISTORY.class,
								false);
				approvalHistoryObject.insert();
				approvalHistorys.add(approvalHistoryObject);
			}
		}

		BusObjectHelper.unlinkChildren(this, APPROVAL_HISTORY.class);

		BusObjectIterator<ATTACHMENT> attachmentObj = this
				.getATTACHMENTObjects();
		if (attachmentObj != null) {
			while (attachmentObj.hasMoreElements()) {
				ATTACHMENT attachmentInfo = (ATTACHMENT) attachmentObj
						.nextElement();
				attachment = (ATTACHMENT) ObjectHelper.createObjectForInsert(
						attachmentInfo,
						com.laiyifen.common.core.ATTACHMENT.class, false);
				attachment.insert();
				attachments.add(attachment);
			}
		}
		BusObjectHelper.unlinkChildren(this, ATTACHMENT.class);
		
		logger.log(Severity.INFO, "CompositeAnnualDevPlanInfo--onInsert--结束", null);
    }

    public void onUpdate()
    {
    	logger.log(Severity.INFO, "CompositeAnnualDevPlanInfo--onUpdate--开始", null);
    	
    	BusObjectHelper.removeEmptyChildren(this);
    	
    	SHOP_MONTHLY_PLAN shopMonthlyPlan = this.getSHOP_MONTHLY_PLANObject();
		if (shopMonthlyPlan != null) {
			shopMonthlyPlanObject = (SHOP_MONTHLY_PLAN) ObjectHelper
					.createObjectForUpdate(shopMonthlyPlan,
							com.laiyifen.shop.SHOP_MONTHLY_PLAN.class, false);
			shopMonthlyPlanObject.update();
		}

		BusObjectIterator<APPROVAL_HISTORY> approvalHistoryObj = this
				.getAPPROVAL_HISTORYObjects();
		if (approvalHistoryObj != null) {
			while (approvalHistoryObj.hasMoreElements()) {
				APPROVAL_HISTORY approvalHistoryInfo = (APPROVAL_HISTORY) approvalHistoryObj
						.nextElement();
				approvalHistoryObject = (APPROVAL_HISTORY) ObjectHelper
						.createObjectForUpdate(
								approvalHistoryInfo,
								com.laiyifen.common.core.APPROVAL_HISTORY.class,
								false);
				approvalHistoryObject.update();
				approvalHistorys.add(approvalHistoryObject);
			}
		}
		
		BusObjectHelper.unlinkChildren(this, APPROVAL_HISTORY.class);

		BusObjectIterator<ATTACHMENT> attachmentObj = this
				.getATTACHMENTObjects();
		if (attachmentObj != null) {
			while (attachmentObj.hasMoreElements()) {
				ATTACHMENT attachmentInfo = (ATTACHMENT) attachmentObj
						.nextElement();
				attachment = (ATTACHMENT) ObjectHelper.createObjectForUpdate(
						attachmentInfo,
						com.laiyifen.common.core.ATTACHMENT.class, false);
				attachment.update();
				attachments.add(attachment);
			}
		}
		
		BusObjectHelper.unlinkChildren(this, ATTACHMENT.class);
		
		logger.log(Severity.INFO, "CompositeAnnualDevPlanInfo--onUpdate--结束", null);
    }

    public void onDelete()
    {
    	logger.log(Severity.INFO, "CompositeAnnualDevPlanInfo--onDelete--开始", null);
    	
    	BusObjectHelper.removeEmptyChildren(this);
    	
    	SHOP_MONTHLY_PLAN shopMonthlyPlan = this.getSHOP_MONTHLY_PLANObject();
		if (shopMonthlyPlan != null) {
			ObjectHelper.createObjectForDelete(shopMonthlyPlan,
					com.laiyifen.shop.SHOP_MONTHLY_PLAN.class, true);
		}

		BusObjectIterator<APPROVAL_HISTORY> approvalHistoryObj = this
				.getAPPROVAL_HISTORYObjects();
		if (approvalHistoryObj != null) {
			while (approvalHistoryObj.hasMoreElements()) {
				APPROVAL_HISTORY approvalHistoryInfo = (APPROVAL_HISTORY) approvalHistoryObj
						.nextElement();
				ObjectHelper.createObjectForDelete(approvalHistoryInfo,
						com.laiyifen.common.core.APPROVAL_HISTORY.class, true);
			}
		}

		BusObjectIterator<ATTACHMENT> attachmentObj = this
				.getATTACHMENTObjects();
		if (attachmentObj != null) {
			while (attachmentObj.hasMoreElements()) {
				ATTACHMENT attachmentInfo = (ATTACHMENT) attachmentObj
						.nextElement();
				ObjectHelper.createObjectForDelete(attachmentInfo,
						com.laiyifen.common.core.ATTACHMENT.class, true);
			}
		}
		
		logger.log(Severity.INFO, "CompositeAnnualDevPlanInfo--onDelete--结束", null);
    }

	@Override
	public void onAfterCommit(AfterCommitObjectEvent event) {
		// TODO Auto-generated method stub
    	super.onAfterCommit(event);
		this.createResponseObject();
		this.clear();
	}

	/**
	 * Create the composite response object.
	 */
	private void createResponseObject() {
		// Persistable objects we can modify, so first make transient.
		this.makeTransient();

		if (shopMonthlyPlanObject != null) {
			this.setSHOP_MONTHLY_PLANObject(shopMonthlyPlanObject);
		}

		for (APPROVAL_HISTORY approvalHistory : approvalHistorys) {
			this.addAPPROVAL_HISTORYObject(approvalHistory);
		}

		for (ATTACHMENT attachment : attachments) {
			this.addATTACHMENTObject(attachment);
		}
	}

	/**
	 * Clears the local variables.
	 */
	private void clear() {
		shopMonthlyPlanObject = null;
		approvalHistorys.clear();
		attachments.clear();
	}
}

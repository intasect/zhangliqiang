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
import com.laiyifen.common.util.GUIDUtil;
import com.laiyifen.shop.util.ShopUtil;


public class CompositeShopSealInfo extends CompositeShopSealInfoBase
{
	
	private SHOP_SEAL_APPLICATION shopSealApplicationObject = null;
	private SUPPLIERS_MASTER suppliersMasterObject = null;
	private SHOP_MASTER shopMasterObject = null;
	
	private APPROVAL_HISTORY approvalHistoryObject = null;
	private Vector<APPROVAL_HISTORY> approvalHistorys = new Vector<APPROVAL_HISTORY>();
	private ATTACHMENT attachment = null;
	private Vector<ATTACHMENT> attachments = new Vector<ATTACHMENT>();
	
	private static CordysLogger logger = CordysLogger.getCordysLogger(CompositeShopSealInfo.class);
	
    public CompositeShopSealInfo()
    {
        this((BusObjectConfig)null);
    }

    public CompositeShopSealInfo(BusObjectConfig config)
    {
        super(config);
    }

    public static com.laiyifen.shop.CompositeShopSealInfo getCompositeShopSealInfoObject(String formId, String shopId)
    {
    	// 新建合同用印复合对象实例
    	CompositeShopSealInfo compositeShopSealObject = new CompositeShopSealInfo();

    	logger.log(Severity.INFO, "getCompositeShopSealInfoObject--开始", null);
    	
    	if (null != formId && !("".equals(formId)) && "" != formId)
    	{
    		
    		logger.log(Severity.INFO, "formId有值为==" + formId, null);
    		
    		// 根据formID取得SHOP_SEAL_APPLICATION表中记录并设置到复合对象
        	SHOP_SEAL_APPLICATION shopSealApplication = SHOP_SEAL_APPLICATION.getShopSealApplicationObject(formId);
        	if (null != shopSealApplication)
        	{
        		compositeShopSealObject.setSHOP_SEAL_APPLICATIONObject(shopSealApplication);
        	}
        	
        	
        	// 根据formID取得SUPPLIERS_MASTER表中记录并设置到复合对象
        	SUPPLIERS_MASTER suppliersMaster = SUPPLIERS_MASTER.getSuppliersMasterObject(formId);
        	if (null != suppliersMaster)
        	{
        		compositeShopSealObject.setSUPPLIERS_MASTERObject(suppliersMaster);
        	}
        	
    	}
    	
    	// 根据shopId取得SHOP_MASTER表中记录并设置到复合对象
    	SHOP_MASTER shopMaster = SHOP_MASTER.getShopMasterObject(shopId);
    	
    	if (null != shopMaster)
    	{
    		compositeShopSealObject.setSHOP_MASTERObject(shopMaster);
    	}
    	
    	
    		
    	//取得上传附件数据并设置到复合对象
		BusObjectIterator<ATTACHMENT> attachmentObj = ATTACHMENT
				.getAttachment(formId);
		if (attachmentObj != null) {
			while (attachmentObj.hasMoreElements()) {
				ATTACHMENT attachmentInfo = (ATTACHMENT) attachmentObj
						.nextElement();
				compositeShopSealObject.addATTACHMENTObject(attachmentInfo);
			}
		}
		
		//取得审批记录数据并设置到复合对象
		BusObjectIterator<APPROVAL_HISTORY> approvalHistoryObj = APPROVAL_HISTORY
				.getApprovalHistory(formId);
		if (approvalHistoryObj != null) {
			while (approvalHistoryObj.hasMoreElements()) {
				APPROVAL_HISTORY approvalHistoryInfo = (APPROVAL_HISTORY) approvalHistoryObj
						.nextElement();
				compositeShopSealObject
						.addAPPROVAL_HISTORYObject(approvalHistoryInfo);
			}
		}
		compositeShopSealObject.makeTransient();
		
		logger.log(Severity.INFO, "getCompositeShopSealInfoObject--结束", null);
		
		return compositeShopSealObject;
    }

    public static BusObjectIterator<com.laiyifen.shop.CompositeShopSealInfo> getCompositeShopSealInfoObjects(String shopid, String formID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        // TODO implement body
        return null;
    }

    public void onInsert()
    {
    	// Removing the unnecessary objects from the parent object.
		BusObjectHelper.removeEmptyChildren(this);
		
		ShopUtil _formIDGenerator = new ShopUtil();
		String formID = _formIDGenerator.setFormID(null);
		
		//从报文取得设置SHOP_SEAL_APPLICATION对象值
		SHOP_SEAL_APPLICATION shopSealApplication = this.getSHOP_SEAL_APPLICATIONObject();
		if (shopSealApplication != null) {
			
			//shopSealApplication.setFORM_ID(formID);
			shopSealApplicationObject = (SHOP_SEAL_APPLICATION) ObjectHelper.createObjectForInsert(shopSealApplication, 
					com.laiyifen.shop.SHOP_SEAL_APPLICATION.class, false);
			shopSealApplicationObject.insert();
		}
		
		//从报文取得设置SUPPLIERS_MASTER对象值
		SUPPLIERS_MASTER suppliersMaster = this.getSUPPLIERS_MASTERObject();
		if (suppliersMaster != null) {
			
			suppliersMaster.setID(shopSealApplication.getFORM_ID());
			suppliersMaster.setCOMPANY_CODE(shopSealApplication.getSUBCOMPANY_CODE());
			suppliersMaster.setCOMPANY_NAME(shopSealApplication.getSUBCOMPANY_NAME());
			suppliersMasterObject = (SUPPLIERS_MASTER) ObjectHelper.createObjectForInsert(suppliersMaster, 
					com.laiyifen.shop.SUPPLIERS_MASTER.class, false);
			suppliersMasterObject.insert();
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
    }

    public void onUpdate()
    {
    	
    	BusObjectHelper.removeEmptyChildren(this);
    	
		//从报文取得设置SHOP_SEAL_APPLICATION对象值
		SHOP_SEAL_APPLICATION shopSealApplication = this.getSHOP_SEAL_APPLICATIONObject();
		if (shopSealApplication != null) {
			shopSealApplicationObject = (SHOP_SEAL_APPLICATION) ObjectHelper
					.createObjectForUpdate(shopSealApplication,
							com.laiyifen.shop.SHOP_SEAL_APPLICATION.class, false);
			shopSealApplicationObject.update();
		}
		
		//从报文取得设置SUPPLIERS_MASTER对象值
		SUPPLIERS_MASTER suppliersMaster = this.getSUPPLIERS_MASTERObject();
		if (suppliersMaster != null) {
			suppliersMasterObject = (SUPPLIERS_MASTER) ObjectHelper.createObjectForUpdate(suppliersMaster, 
					com.laiyifen.shop.SUPPLIERS_MASTER.class, false);
			suppliersMasterObject.update();
		}
		
		//从报文取得设置SHOP_MASTER对象值
		SHOP_MASTER shopMaster = this.getSHOP_MASTERObject();
		if (shopMaster != null) {
			shopMasterObject = (SHOP_MASTER) ObjectHelper.createObjectForUpdate(shopMaster, 
					com.laiyifen.shop.SHOP_MASTER.class, false);
			shopMasterObject.update();
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
    }

    public void onDelete()
    {
    	
    	BusObjectHelper.removeEmptyChildren(this);
    	
    	//从报文取得设置SHOP_SEAL_APPLICATION对象值
		SHOP_SEAL_APPLICATION shopSealApplication = this.getSHOP_SEAL_APPLICATIONObject();
		if (shopSealApplication != null) {
			ObjectHelper.createObjectForDelete(shopSealApplication,
					com.laiyifen.shop.SHOP_SEAL_APPLICATION.class, true);
		}
		
		//从报文取得设置SUPPLIERS_MASTER对象值
		SUPPLIERS_MASTER suppliersMaster = this.getSUPPLIERS_MASTERObject();
		if (suppliersMaster != null) {
			ObjectHelper.createObjectForDelete(suppliersMaster,
					com.laiyifen.shop.SUPPLIERS_MASTER.class, true);
		}
		
		//从报文取得设置SHOP_MASTER对象值
		SHOP_MASTER shopMaster = this.getSHOP_MASTERObject();
		if (shopMaster != null) {
			ObjectHelper.createObjectForDelete(shopMaster,
					com.laiyifen.shop.SHOP_MASTER.class, true);
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

		if (shopSealApplicationObject != null) {
			this.setSHOP_SEAL_APPLICATIONObject(shopSealApplicationObject);
		}
		
		if (suppliersMasterObject != null) {
			this.setSUPPLIERS_MASTERObject(suppliersMasterObject);
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
		shopSealApplicationObject =null;
		suppliersMasterObject = null;
		shopMasterObject = null;
		approvalHistorys.clear();
		attachments.clear();
	}

}

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


public class CompositeShopDrawingsInfo extends CompositeShopDrawingsInfoBase
{
	private SHOP_DRAWINGS shopDrawingsObject = null;
	
	private APPROVAL_HISTORY approvalHistoryObject = null;
	private Vector<APPROVAL_HISTORY> approvalHistorys = new Vector<APPROVAL_HISTORY>();
	private ATTACHMENT attachment = null;
	private Vector<ATTACHMENT> attachments = new Vector<ATTACHMENT>();
	
	private static CordysLogger logger = CordysLogger.getCordysLogger(CompositeShopDrawingsInfo.class);
	
    public CompositeShopDrawingsInfo()
    {
        this((BusObjectConfig)null);
    }

    public CompositeShopDrawingsInfo(BusObjectConfig config)
    {
        super(config);
    }

    public static com.laiyifen.shop.CompositeShopDrawingsInfo getCompositeShopDrawingsInfoObject(String formId, String shopId)
    {
    	// 新建装修设计审核对象实例
    	CompositeShopDrawingsInfo compositeShopDrawingsObject = new CompositeShopDrawingsInfo();

    	logger.log(Severity.INFO, "getCompositeShopDrawingsInfoObject--开始", null);
    	
    	if (null != formId && !("".equals(formId)) && "" != formId)
    	{
    		
    		logger.log(Severity.INFO, "formId有值为==" + formId, null);
    		
    		// 根据formID取得SHOP_SEAL_APPLICATION表中记录并设置到复合对象
    		SHOP_DRAWINGS shopDrawings = SHOP_DRAWINGS.getShopDrawingsObject(formId);
        	
        	if (null != shopDrawings)
        	{
        		compositeShopDrawingsObject.setSHOP_DRAWINGSObject(shopDrawings);
        	}
    	}
    
    	
    	// 根据shopId取得SHOP_MASTER表中记录并设置到复合对象
    	SHOP_MASTER shopMaster = SHOP_MASTER.getShopMasterObject(shopId);
    	
    	if (null == shopMaster)
    	{
    		return null;
    	}
    	
    	compositeShopDrawingsObject.setSHOP_MASTERObject(shopMaster);
    		
    	//取得上传附件数据并设置到复合对象
		BusObjectIterator<ATTACHMENT> attachmentObj = ATTACHMENT
				.getAttachment(formId);
		if (attachmentObj != null) {
			while (attachmentObj.hasMoreElements()) {
				ATTACHMENT attachmentInfo = (ATTACHMENT) attachmentObj
						.nextElement();
				compositeShopDrawingsObject.addATTACHMENTObject(attachmentInfo);
			}
		}
		
		//取得审批记录数据并设置到复合对象
		BusObjectIterator<APPROVAL_HISTORY> approvalHistoryObj = APPROVAL_HISTORY
				.getApprovalHistory(formId);
		if (approvalHistoryObj != null) {
			while (approvalHistoryObj.hasMoreElements()) {
				APPROVAL_HISTORY approvalHistoryInfo = (APPROVAL_HISTORY) approvalHistoryObj
						.nextElement();
				compositeShopDrawingsObject
						.addAPPROVAL_HISTORYObject(approvalHistoryInfo);
			}
		}
		compositeShopDrawingsObject.makeTransient();
		
		logger.log(Severity.INFO, "getCompositeShopDrawingsInfoObject--结束", null);
		
		return compositeShopDrawingsObject;
    }

    public static BusObjectIterator<com.laiyifen.shop.CompositeShopDrawingsInfo> getCompositeShopDrawingsInfoObjects(com.cordys.cpc.bsf.query.Cursor cursor)
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
		
		//从报文取得设置SHOP_DRAWINGS对象值
		SHOP_DRAWINGS shopDrawings = this.getSHOP_DRAWINGSObject();
		if (shopDrawings != null) {
			
			//shopSealApplication.setFORM_ID(formID);
			shopDrawingsObject = (SHOP_DRAWINGS) ObjectHelper.createObjectForInsert(shopDrawings, 
					com.laiyifen.shop.SHOP_DRAWINGS.class, false);
			shopDrawingsObject.insert();
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
    	
		//从报文取得设置SHOP_DRAWINGS对象值
		SHOP_DRAWINGS shopDrawings = this.getSHOP_DRAWINGSObject();
		if (shopDrawings != null) {
			shopDrawingsObject = (SHOP_DRAWINGS) ObjectHelper
					.createObjectForUpdate(shopDrawings,
							com.laiyifen.shop.SHOP_DRAWINGS.class, false);
			shopDrawingsObject.update();
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
    	
    	//从报文取得设置SHOP_DRAWINGS对象值
    	SHOP_DRAWINGS shopDrawings = this.getSHOP_DRAWINGSObject();
		if (shopDrawings != null) {
			ObjectHelper.createObjectForDelete(shopDrawings,
					com.laiyifen.shop.SHOP_DRAWINGS.class, true);
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

		if (shopDrawingsObject != null) {
			this.setSHOP_DRAWINGSObject(shopDrawingsObject);
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
		shopDrawingsObject = null;
		approvalHistorys.clear();
		attachments.clear();
	}

}

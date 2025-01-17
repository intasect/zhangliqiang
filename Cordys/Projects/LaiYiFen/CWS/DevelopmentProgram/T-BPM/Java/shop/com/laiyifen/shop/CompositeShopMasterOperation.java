/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.shop;

import java.util.Vector;
import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.event.AfterCommitObjectEvent;
import com.cordys.cpc.bsf.util.ObjectHelper;
import com.laiyifen.common.core.ATTACHMENT;
import com.laiyifen.common.util.BusObjectHelper;
import com.laiyifen.shop.util.ShopUtil;

public class CompositeShopMasterOperation extends CompositeShopMasterOperationBase
{
	private SHOP_MASTER_OPERATION shopMasterOperationObject = null;
	private SHOP_MASTER shopMasterObject = null;
	private ATTACHMENT attachment = null;
	private Vector<ATTACHMENT> attachments = new Vector<ATTACHMENT>();
    public CompositeShopMasterOperation()
    {
        this((BusObjectConfig)null);
    }

    public CompositeShopMasterOperation(BusObjectConfig config)
    {
        super(config);
    }

    public static com.laiyifen.shop.CompositeShopMasterOperation getCompositeShopMasterOperationByShopId(String shopId)
    {
    	CompositeShopMasterOperation  compositeShopMasterOperation = new CompositeShopMasterOperation(); 
    	
    	SHOP_MASTER shopMaster = SHOP_MASTER.getShopMasterObject(shopId);
    	compositeShopMasterOperation.setSHOP_MASTERObject(shopMaster);
		
    	SHOP_MASTER_OPERATION shopMasterOperation = SHOP_MASTER_OPERATION.
    			getShopMasterOperationByShopId(shopId);
    	compositeShopMasterOperation.setSHOP_MASTER_OPERATIONObject(shopMasterOperation);	
    	String formId = null;
    	if(shopMasterOperation!=null){
    		formId = shopMasterOperation.getFORM_ID();
    	}
    	BusObjectIterator<ATTACHMENT> attachmentObj = ATTACHMENT
		.getAttachment(formId);
		if (attachmentObj != null) {
			while (attachmentObj.hasMoreElements()) {
				ATTACHMENT attachmentInfo = (ATTACHMENT) attachmentObj
						.nextElement();
				compositeShopMasterOperation.addATTACHMENTObject(attachmentInfo);
			}
		}
		compositeShopMasterOperation.makeTransient();
        return compositeShopMasterOperation;
    }

    public static BusObjectIterator<com.laiyifen.shop.CompositeShopMasterOperation> getCompositeShopMasterOperationObjects(com.cordys.cpc.bsf.query.Cursor cursor)
    {
        // TODO implement body
        return null;
    }

    public void onInsert()
    {
    	BusObjectHelper.removeEmptyChildren(this);
    	SHOP_MASTER_OPERATION shopMasterOperation = this.getSHOP_MASTER_OPERATIONObject();
		if (shopMasterOperation != null) {
			ShopUtil _formIDGenerator = new ShopUtil();
			String formID = _formIDGenerator.setFormID(null);
			shopMasterOperation.setFORM_ID(formID);
		
			shopMasterOperationObject = (SHOP_MASTER_OPERATION) ObjectHelper
					.createObjectForInsert(shopMasterOperation,
							SHOP_MASTER_OPERATION.class, false);
			shopMasterOperationObject.insert();
		}
		
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
    	SHOP_MASTER_OPERATION shopMasterOperation = this.getSHOP_MASTER_OPERATIONObject();

		if (shopMasterOperation != null) {
			shopMasterOperationObject = (SHOP_MASTER_OPERATION) ObjectHelper
					.createObjectForUpdate(shopMasterOperation,
							com.laiyifen.shop.SHOP_MASTER_OPERATION.class, false);
			shopMasterOperationObject.update();
		}
		
		SHOP_MASTER shopMaster = this.getSHOP_MASTERObject();
		
		if(shopMaster != null){
			shopMasterObject = (SHOP_MASTER) ObjectHelper
			.createObjectForUpdate(shopMaster,
					com.laiyifen.shop.SHOP_MASTER.class, false);
			shopMasterObject.update();
		}
		
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

		if (shopMasterOperationObject != null) {
			this.setSHOP_MASTER_OPERATIONObject(shopMasterOperationObject);
		}
		
		if (shopMasterObject != null) {
			this.setSHOP_MASTERObject(shopMasterObject);
		}

		for (ATTACHMENT attachment : attachments) {
			this.addATTACHMENTObject(attachment);
		}
	}
	/**
	 * Clears the local variables.
	 */
	private void clear() {
		shopMasterOperationObject = null;
		shopMasterObject = null;
		attachment = null;
		attachments.clear();
	}

}

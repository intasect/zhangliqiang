/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.shop;

import java.util.Date;
import java.util.Vector;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.event.AfterCommitObjectEvent;
import com.cordys.cpc.bsf.util.ObjectHelper;
import com.laiyifen.common.core.ATTACHMENT;
import com.laiyifen.common.core.User;
import com.laiyifen.common.util.BusObjectHelper;
import com.laiyifen.shop.util.ShopUtil;


public class CompositeShopApplicense extends CompositeShopApplicenseBase
{
	private SHOP_APPLICENSE shopApplicenseObject = null;
	private ATTACHMENT attachment = null;
	private Vector<ATTACHMENT> attachments = new Vector<ATTACHMENT>();
	
    public CompositeShopApplicense()
    {
        this((BusObjectConfig)null);
    }

    public CompositeShopApplicense(BusObjectConfig config)
    {
        super(config);
    }

    public static com.laiyifen.shop.CompositeShopApplicense getCompositeShopApplicenseObject(String formId, String shopId)
    {
    	
    	CompositeShopApplicense  compositeShopApplicense = new CompositeShopApplicense();
    	SHOP_APPLICENSE shopApplicense = SHOP_APPLICENSE.
    			getShopApplicenseByShopId(shopId);
    	compositeShopApplicense.setSHOP_APPLICENSEObject(shopApplicense);	
    	if(shopApplicense!=null){
    		formId = shopApplicense.getFORM_ID();
    	}
    	SHOP_MASTER shopMaster = SHOP_MASTER.getShopMasterObject(shopId);
    			compositeShopApplicense.setSHOP_MASTERObject(shopMaster);
    			
    	BusObjectIterator<ATTACHMENT> attachmentObj = ATTACHMENT
		.getAttachment(formId);
		if (attachmentObj != null) {
			while (attachmentObj.hasMoreElements()) {
				ATTACHMENT attachmentInfo = (ATTACHMENT) attachmentObj
						.nextElement();
				compositeShopApplicense.addATTACHMENTObject(attachmentInfo);
			}
		}
    	compositeShopApplicense.makeTransient();
        return compositeShopApplicense;
    }

    public static BusObjectIterator<com.laiyifen.shop.CompositeShopApplicense> getCompositeShopApplicenseObjects(com.cordys.cpc.bsf.query.Cursor cursor)
    {
        // TODO implement body
        return null;
    }

    public void onInsert()
    {
    	BusObjectHelper.removeEmptyChildren(this);

		SHOP_APPLICENSE shopApplicense = this.getSHOP_APPLICENSEObject();
		if (shopApplicense != null) {
			ShopUtil _formIDGenerator = new ShopUtil();
			String formID = _formIDGenerator.setFormID(null);
			shopApplicense.setFORM_ID(formID);
			/**
			if(shopApplicense.getUSER_NAME()== null ||shopApplicense.getUSER_NAME().equals("")){
				shopApplicense.setUSER_NAME(User.getUserInfo().getUserName());
			}
			
			if(shopApplicense.getUSER_CODE()== null ||shopApplicense.getUSER_CODE().equals("")){
				shopApplicense.setUSER_CODE(User.getUserInfo().getUserCode());
			}
			
			if(shopApplicense.getDEPARTMENT_CODE()== null ||shopApplicense.getDEPARTMENT_CODE().equals("")){
				shopApplicense.setDEPARTMENT_CODE(User.getUserInfo().getDeptCode());
			}
			
			if(shopApplicense.getDEPARTMENT_NAME()== null ||shopApplicense.getDEPARTMENT_NAME().equals("")){
				shopApplicense.setDEPARTMENT_NAME(User.getUserInfo().getDeptName());
			}
			shopApplicense.setAPPLICATION_DATE(new Date());
                      */
			shopApplicenseObject = (SHOP_APPLICENSE) ObjectHelper
					.createObjectForInsert(shopApplicense,
							com.laiyifen.shop.SHOP_APPLICENSE.class, false);
			shopApplicenseObject.insert();
		}
		/**
		SHOP_MASTER shopMasteInfo = this.getSHOP_MASTERObject();
		if (shopMasteInfo != null) {
			shopMasterObject = (SHOP_MASTER) ObjectHelper
					.createObjectForInsert(shopMasteInfo,
							com.laiyifen.shop.SHOP_MASTER.class, false);
			shopMasterObject.insert();
		}
		*/
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
    	SHOP_APPLICENSE shopApplicense = this.getSHOP_APPLICENSEObject();

		if (shopApplicense != null) {
			shopApplicenseObject = (SHOP_APPLICENSE) ObjectHelper
					.createObjectForUpdate(shopApplicense,
							com.laiyifen.shop.SHOP_APPLICENSE.class, false);
			shopApplicenseObject.update();
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

		if (shopApplicenseObject != null) {
			this.setSHOP_APPLICENSEObject(shopApplicenseObject);
		}

		for (ATTACHMENT attachment : attachments) {
			this.addATTACHMENTObject(attachment);
		}
	}
	/**
	 * Clears the local variables.
	 */
	private void clear() {
		shopApplicenseObject = null;
		attachment = null;
		attachments.clear();
	}

}

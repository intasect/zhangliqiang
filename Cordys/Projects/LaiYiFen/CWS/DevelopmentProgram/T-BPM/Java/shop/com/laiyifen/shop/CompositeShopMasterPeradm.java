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


public class CompositeShopMasterPeradm extends CompositeShopMasterPeradmBase
{
	private SHOP_MASTER_PERADM shopMasterPeradmObject = null;
	private ATTACHMENT attachment = null;
	private Vector<ATTACHMENT> attachments = new Vector<ATTACHMENT>();
    public CompositeShopMasterPeradm()
    {
        this((BusObjectConfig)null);
    }

    public CompositeShopMasterPeradm(BusObjectConfig config)
    {
        super(config);
    }

    public static com.laiyifen.shop.CompositeShopMasterPeradm getCompositeShopMasterPeradmByShopId(String shopId)
    {
    	CompositeShopMasterPeradm  compositeShopMasterPeradm = new CompositeShopMasterPeradm();        
    	SHOP_MASTER shopMaster = SHOP_MASTER.getShopMasterObject(shopId);
    	compositeShopMasterPeradm.setSHOP_MASTERObject(shopMaster);
    	
    	SHOP_MASTER_PERADM shopMasterPeradm = SHOP_MASTER_PERADM.getShopMasterPeradmByShopId(shopId);
    	compositeShopMasterPeradm.setSHOP_MASTER_PERADMObject(shopMasterPeradm);
    	String formId = null;
    	if(shopMasterPeradm!=null){
    		formId = shopMasterPeradm.getFORM_ID();
    	}
    	BusObjectIterator<ATTACHMENT> attachmentObj = ATTACHMENT
		.getAttachment(formId);
		if (attachmentObj != null) {
			while (attachmentObj.hasMoreElements()) {
				ATTACHMENT attachmentInfo = (ATTACHMENT) attachmentObj
						.nextElement();
				compositeShopMasterPeradm.addATTACHMENTObject(attachmentInfo);
			}
		}
		compositeShopMasterPeradm.makeTransient();
        return compositeShopMasterPeradm;
    }

    public static BusObjectIterator<com.laiyifen.shop.CompositeShopMasterPeradm> getCompositeShopMasterPeradmObjects(com.cordys.cpc.bsf.query.Cursor cursor)
    {
        // TODO implement body
        return null;
    }

    public void onInsert()
    {
    	BusObjectHelper.removeEmptyChildren(this);
    	SHOP_MASTER_PERADM shopMasterPeradm = this.getSHOP_MASTER_PERADMObject();
		if (shopMasterPeradm != null) {
			ShopUtil _formIDGenerator = new ShopUtil();
			String formID = _formIDGenerator.setFormID(null);
			shopMasterPeradm.setFORM_ID(formID);		
			shopMasterPeradmObject = (SHOP_MASTER_PERADM) ObjectHelper
					.createObjectForInsert(shopMasterPeradm,
							SHOP_MASTER_PERADM.class, false);
			shopMasterPeradmObject.insert();
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
    	SHOP_MASTER_PERADM shopMasterPeradm = this.getSHOP_MASTER_PERADMObject();

		if (shopMasterPeradm != null) {
			shopMasterPeradmObject = (SHOP_MASTER_PERADM) ObjectHelper
					.createObjectForUpdate(shopMasterPeradm,
							com.laiyifen.shop.SHOP_MASTER_PERADM.class, false);
			shopMasterPeradmObject.update();
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
		//this.createResponseObject();
		this.clear();
	}

	/**
	 * Create the composite response object.
	 */
	private void createResponseObject() {
		// Persistable objects we can modify, so first make transient.
		this.makeTransient();

		if (shopMasterPeradmObject != null) {
			this.setSHOP_MASTER_PERADMObject(shopMasterPeradmObject);
		}

		for (ATTACHMENT attachment : attachments) {
			this.addATTACHMENTObject(attachment);
		}
	}
	/**
	 * Clears the local variables.
	 */
	private void clear() {
		shopMasterPeradmObject = null;
		attachment = null;
		attachments.clear();
	}

}
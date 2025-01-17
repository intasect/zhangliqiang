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
import com.laiyifen.common.core.ATTACHMENT;
import com.laiyifen.common.core.User;
import com.laiyifen.common.util.BusObjectHelper;
import com.laiyifen.shop.util.ShopUtil;


public class CompositeShopTransPos extends CompositeShopTransPosBase
{
	private SHOP_TRANSFER_POS shopTransPosObject = null;
	private ATTACHMENT attachment = null;
	private Vector<ATTACHMENT> attachments = new Vector<ATTACHMENT>();
	private static CordysLogger logger=CordysLogger.getCordysLogger(CompositeShopTransPos.class);
    public CompositeShopTransPos()
    {
        this((BusObjectConfig)null);
    }

    public CompositeShopTransPos(BusObjectConfig config)
    {
        super(config);
    }

    public static com.laiyifen.shop.CompositeShopTransPos getCompositeShopTransPosObject(String shopId)
    {
    		   String formId = null;
    	       CompositeShopTransPos  compositeShoptransPos = new CompositeShopTransPos();
		    	
		    	SHOP_MASTER shopMaster = SHOP_MASTER.getShopMasterObject(shopId);
		    	compositeShoptransPos.setSHOP_MASTERObject(shopMaster);
				
				SHOP_TRANSFER_POS shopTransPos = SHOP_TRANSFER_POS.
				     getShopTransPosByShopId(shopId);
				if(shopTransPos!=null){
					formId = shopTransPos.getFORM_ID();
				}
				compositeShoptransPos.setSHOP_TRANSFER_POSObject(shopTransPos);	
		    	BusObjectIterator<ATTACHMENT> attachmentObj = ATTACHMENT
				.getAttachment(formId);
				if (attachmentObj != null) {
					while (attachmentObj.hasMoreElements()) {
						ATTACHMENT attachmentInfo = (ATTACHMENT) attachmentObj
								.nextElement();
						compositeShoptransPos.addATTACHMENTObject(attachmentInfo);
					}
				}
				compositeShoptransPos.makeTransient();
		        return compositeShoptransPos;
    }

    public static BusObjectIterator<com.laiyifen.shop.CompositeShopTransPos> getCompositeShopTransPosObjects(com.cordys.cpc.bsf.query.Cursor cursor)
    {
        // TODO implement body
        return null;
    }

    public void onInsert()
    {
    	BusObjectHelper.removeEmptyChildren(this);

    	SHOP_TRANSFER_POS shopTransPos = this.getSHOP_TRANSFER_POSObject();
		User user = User.getUserInfo();
		if (shopTransPos != null) {
			ShopUtil _formIDGenerator = new ShopUtil();
			String formID = _formIDGenerator.setFormID(null);
			shopTransPos.setFORM_ID(formID);
			/**
			if(shopTransPos.getUSER_NAME()== null ||shopTransPos.getUSER_NAME().equals("")){
				shopTransPos.setUSER_NAME(user.getUserName());
			}
			
			if(shopTransPos.getUSER_CODE()== null ||shopTransPos.getUSER_CODE().equals("")){
				shopTransPos.setUSER_CODE(user.getUserCode());
			}
			
			if(shopTransPos.getDEPARTMENT_CODE()== null ||shopTransPos.getDEPARTMENT_CODE().equals("")){
				shopTransPos.setDEPARTMENT_CODE(user.getDeptCode());
			}
			
			if(shopTransPos.getDEPARTMENT_NAME()== null ||shopTransPos.getDEPARTMENT_NAME().equals("")){
				shopTransPos.setDEPARTMENT_NAME(User.getUserInfo().getDeptName());
			}
			shopTransPos.setAPPLICATION_DATE(new Date());
			*/
			shopTransPosObject = (SHOP_TRANSFER_POS) ObjectHelper
					.createObjectForInsert(shopTransPos,
							SHOP_TRANSFER_POS.class, false);
			shopTransPosObject.insert();
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
    	SHOP_TRANSFER_POS shopTransPos = this.getSHOP_TRANSFER_POSObject();

		if (shopTransPos != null) {
			shopTransPosObject = (SHOP_TRANSFER_POS) ObjectHelper
					.createObjectForUpdate(shopTransPos,
							com.laiyifen.shop.SHOP_TRANSFER_POS.class, false);
			shopTransPosObject.update();
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

		if (shopTransPosObject != null) {
			this.setSHOP_TRANSFER_POSObject(shopTransPosObject);
		}

		for (ATTACHMENT attachment : attachments) {
			this.addATTACHMENTObject(attachment);
		}
	}
	/**
	 * Clears the local variables.
	 */
	private void clear() {
		shopTransPosObject = null;
		attachment = null;
		attachments.clear();
	}

}

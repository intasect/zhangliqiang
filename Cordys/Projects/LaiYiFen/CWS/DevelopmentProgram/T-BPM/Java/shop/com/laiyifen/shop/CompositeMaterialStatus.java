/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.shop;

import java.util.Vector;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.busobject.DMLStatement;
import com.cordys.cpc.bsf.event.AfterCommitObjectEvent;
import com.cordys.cpc.bsf.util.ObjectHelper;
import com.eibus.util.logger.CordysLogger;
import com.eibus.util.logger.Severity;
import com.laiyifen.common.core.APPROVAL_HISTORY;
import com.laiyifen.common.core.ATTACHMENT;
import com.laiyifen.common.util.BusObjectHelper;
import com.laiyifen.shop.util.ShopUtil;


public class CompositeMaterialStatus extends CompositeMaterialStatusBase
{
	private SHOP_MATERIAL_STATUS shopMaterialStatusObject = null;
	
	private SHOP_MATERIAL_STATUS_DETAIL shopMaterialStatusDetailObject = null;
	private Vector<SHOP_MATERIAL_STATUS_DETAIL> shopMaterialStatusDetail = new Vector<SHOP_MATERIAL_STATUS_DETAIL>();

	private static CordysLogger logger = CordysLogger.getCordysLogger(CompositeShopDrawingsInfo.class);
	
    public CompositeMaterialStatus()
    {
        this((BusObjectConfig)null);
    }

    public CompositeMaterialStatus(BusObjectConfig config)
    {
        super(config);
    }

    public static com.laiyifen.shop.CompositeMaterialStatus getCompositeMaterialStatusObject(String formId, String shopId)
    {
    	// 新建物料状态对象实例
    	CompositeMaterialStatus compositeMaterialStatusObject = new CompositeMaterialStatus();

    	logger.log(Severity.INFO, "getCompositeMaterialStatusObject--开始", null);
    	
    	if (null != formId && !("".equals(formId)) && "" != formId)
    	{
    		
    		logger.log(Severity.INFO, "formId有值为==" + formId, null);
    		
    		// 根据formID取得SHOP_MATERIAL_STATUS表中记录并设置到复合对象
    		SHOP_MATERIAL_STATUS shopMaterialStatus = SHOP_MATERIAL_STATUS.getShopMaterialStatusObject(formId);
        	
        	if (null != shopMaterialStatus)
        	{
        		compositeMaterialStatusObject.setSHOP_MATERIAL_STATUSObject(shopMaterialStatus);
        	}
    	}
    	//查询页面弹出窗口时没法传入formId时用
    	else
    	{
    		SHOP_MATERIAL_STATUS shopMaterialStatus = SHOP_MATERIAL_STATUS.getShopIdMaterialStatusObject(shopId);
    		if (null != shopMaterialStatus)
        	{
        		compositeMaterialStatusObject.setSHOP_MATERIAL_STATUSObject(shopMaterialStatus);
        		formId = shopMaterialStatus.getFORM_ID();
        	}
    		
    	}
    
    	
    	// 根据shopId取得SHOP_MASTER表中记录并设置到复合对象
    	SHOP_MASTER shopMaster = SHOP_MASTER.getShopMasterObject(shopId);
    	
    	if (null == shopMaster)
    	{
    		return null;
    	}
    	
    	compositeMaterialStatusObject.setSHOP_MASTERObject(shopMaster);
    		
    	//取得物料状态详细信息
		BusObjectIterator<SHOP_MATERIAL_STATUS_DETAIL> shopMaterialStatusDetailObj = SHOP_MATERIAL_STATUS_DETAIL
				.getShopMaterialStatusDetailObjectsForFormId(formId);
		if (shopMaterialStatusDetailObj != null) {
			while (shopMaterialStatusDetailObj.hasMoreElements()) {
				SHOP_MATERIAL_STATUS_DETAIL shopMaterialStatusDetailInfo = (SHOP_MATERIAL_STATUS_DETAIL) shopMaterialStatusDetailObj
						.nextElement();
				compositeMaterialStatusObject.addSHOP_MATERIAL_STATUS_DETAILObject(shopMaterialStatusDetailInfo);
			}
		}
		
		
		compositeMaterialStatusObject.makeTransient();
		
		logger.log(Severity.INFO, "getCompositeMaterialStatusObject--结束", null);
		
		return compositeMaterialStatusObject;
    }

    public static BusObjectIterator<com.laiyifen.shop.CompositeMaterialStatus> getCompositeMaterialStatusObjects(com.cordys.cpc.bsf.query.Cursor cursor)
    {
        // TODO implement body
        return null;
    }

    public void onInsert()
    {
    	// Removing the unnecessary objects from the parent object.
		BusObjectHelper.removeEmptyChildren(this);
		
		
		//从报文取得设置SHOP_MATERIAL_STATUS对象值
		SHOP_MATERIAL_STATUS shopMaterialStatus = this.getSHOP_MATERIAL_STATUSObject();
		if (shopMaterialStatus != null) {
			
			shopMaterialStatusObject = (SHOP_MATERIAL_STATUS) ObjectHelper.createObjectForInsert(shopMaterialStatus, 
					com.laiyifen.shop.SHOP_MATERIAL_STATUS.class, false);
			shopMaterialStatusObject.insert();
			
			String dmlQueryText = "update SHOP_MATERIAL_STATUS_DETAIL a set a.PRICE=(select b.PRICE from MATERIAL_AVER_PRICE b " +
					"where b.PRODUCT_CODE=a.Product_Code and b.Companycode='"+shopMaterialStatus.getEXT1()+"') " +
					"where a.FORM_ID='"+shopMaterialStatus.getFORM_ID()+"'";
	    	DMLStatement dml = new DMLStatement(dmlQueryText);
	    	 try 
	    	 { 
	    		 dml.execute();
	    	 }
	    	catch (Exception e) 
	    	{
	    		e.printStackTrace(); 
	    	} 
		}
		
		BusObjectIterator<SHOP_MATERIAL_STATUS_DETAIL> shopMaterialStatusDetailObj = this
				.getSHOP_MATERIAL_STATUS_DETAILObjects();
		if (shopMaterialStatusDetailObj != null) {
			while (shopMaterialStatusDetailObj.hasMoreElements()) {
				SHOP_MATERIAL_STATUS_DETAIL shopMaterialStatusDetailInfo = (SHOP_MATERIAL_STATUS_DETAIL) shopMaterialStatusDetailObj
						.nextElement();
				shopMaterialStatusDetailObject = (SHOP_MATERIAL_STATUS_DETAIL) ObjectHelper
						.createObjectForInsert(
								shopMaterialStatusDetailInfo,
								com.laiyifen.shop.SHOP_MATERIAL_STATUS_DETAIL.class,
								false);
				shopMaterialStatusDetailObject.insert();
				shopMaterialStatusDetail.add(shopMaterialStatusDetailObject);
			}
		}
		BusObjectHelper.unlinkChildren(this, SHOP_MATERIAL_STATUS_DETAIL.class);

    }

    public void onUpdate()
    {
    	
    	BusObjectHelper.removeEmptyChildren(this);
    	
    	//从报文取得设置SHOP_MATERIAL_STATUS对象值
		SHOP_MATERIAL_STATUS shopMaterialStatus = this.getSHOP_MATERIAL_STATUSObject();
		if (shopMaterialStatus != null) {
			shopMaterialStatusObject = (SHOP_MATERIAL_STATUS) ObjectHelper
					.createObjectForUpdate(shopMaterialStatus,
							com.laiyifen.shop.SHOP_MATERIAL_STATUS.class, false);
			shopMaterialStatusObject.update();
			
			String dmlQueryText = "update SHOP_MATERIAL_STATUS_DETAIL a set a.PRICE=(select b.PRICE from MATERIAL_AVER_PRICE b " +
			"where b.PRODUCT_CODE=a.Product_Code and b.Companycode='"+shopMaterialStatus.getEXT1()+"') " +
			"where a.FORM_ID='"+shopMaterialStatus.getFORM_ID()+"'";
			DMLStatement dml = new DMLStatement(dmlQueryText);
			 try 
			 { 
				 dml.execute();
			 }
			catch (Exception e) 
			{
				e.printStackTrace(); 
			} 
		}
		
		BusObjectIterator<SHOP_MATERIAL_STATUS_DETAIL> shopMaterialStatusDetailObj = this
		.getSHOP_MATERIAL_STATUS_DETAILObjects();
		if (shopMaterialStatusDetailObj != null) {
			while (shopMaterialStatusDetailObj.hasMoreElements()) {
		SHOP_MATERIAL_STATUS_DETAIL shopMaterialStatusDetailInfo = (SHOP_MATERIAL_STATUS_DETAIL) shopMaterialStatusDetailObj
				.nextElement();
		shopMaterialStatusDetailObject = (SHOP_MATERIAL_STATUS_DETAIL) ObjectHelper
				.createObjectForUpdate(
						shopMaterialStatusDetailInfo,
						com.laiyifen.shop.SHOP_MATERIAL_STATUS_DETAIL.class,
						false);
		shopMaterialStatusDetailObject.update();
		shopMaterialStatusDetail.add(shopMaterialStatusDetailObject);
			}
		}
		
		BusObjectHelper.unlinkChildren(this, SHOP_MATERIAL_STATUS_DETAIL.class);
    	
    }

    public void onDelete()
    {
    	BusObjectHelper.removeEmptyChildren(this);
    	
    	//从报文取得设置SHOP_MATERIAL_STATUS对象值
		SHOP_MATERIAL_STATUS shopMaterialStatus = this.getSHOP_MATERIAL_STATUSObject();
		if (shopMaterialStatus != null) {
			ObjectHelper.createObjectForDelete(shopMaterialStatus,
					com.laiyifen.shop.SHOP_MATERIAL_STATUS.class, true);
		}

		BusObjectIterator<SHOP_MATERIAL_STATUS_DETAIL> shopMaterialStatusDetailObj = this
		.getSHOP_MATERIAL_STATUS_DETAILObjects();
		if (shopMaterialStatusDetailObj != null) {
			while (shopMaterialStatusDetailObj.hasMoreElements()) {
				SHOP_MATERIAL_STATUS_DETAIL shopMaterialStatusDetailInfo = (SHOP_MATERIAL_STATUS_DETAIL) shopMaterialStatusDetailObj
						.nextElement();
				shopMaterialStatusDetailObject = (SHOP_MATERIAL_STATUS_DETAIL)ObjectHelper.createObjectForDelete(shopMaterialStatusDetailInfo,
						com.laiyifen.shop.SHOP_MATERIAL_STATUS_DETAIL.class, true);
				shopMaterialStatusDetail.add(shopMaterialStatusDetailObject);
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

		if (shopMaterialStatusObject != null) {
			this.setSHOP_MATERIAL_STATUSObject(shopMaterialStatusObject);
		}
		if (!shopMaterialStatusDetail.isEmpty())
		{
			BusObjectIterator<SHOP_MATERIAL_STATUS_DETAIL> shopMaterialStatusDetailObj = SHOP_MATERIAL_STATUS_DETAIL
			.getShopMaterialStatusDetailObjectsForFormId(shopMaterialStatusDetail.firstElement().getFORM_ID());
			if (shopMaterialStatusDetailObj != null) {
				while (shopMaterialStatusDetailObj.hasMoreElements()) {
					SHOP_MATERIAL_STATUS_DETAIL shopMaterialStatusDetailInfo = (SHOP_MATERIAL_STATUS_DETAIL) shopMaterialStatusDetailObj
						.nextElement();
					this.addSHOP_MATERIAL_STATUS_DETAILObject(shopMaterialStatusDetailInfo);
				}
			}
		}
//		String foid = "";
//		for (SHOP_MATERIAL_STATUS_DETAIL mmm : shopMaterialStatusDetail) {
//			foid = mmm.getFORM_ID();
//		}
//		
//		BusObjectIterator<SHOP_MATERIAL_STATUS_DETAIL> shopMaterialStatusDetailObj = SHOP_MATERIAL_STATUS_DETAIL
//		.getShopMaterialStatusDetailObjectsForFormId(foid);
//		if (shopMaterialStatusDetailObj != null) {
//			logger.log(Severity.ERROR, "asdsadd--进来啦rrrrr=="+foid, null);
//			BusObjectIterator<SHOP_MATERIAL_STATUS_DETAIL> shopMaterialStatusDetailObjs = _getMultiRelation("AGGR:SHOP_MATERIAL_STATUS_DETAIL", true).getObjects();
//			while (shopMaterialStatusDetailObjs.hasMoreElements()) {
//				logger.log(Severity.ERROR, "asdsadd--进来啦ffff=="+foid, null);
//				_getMultiRelation("AGGR:SHOP_MATERIAL_STATUS_DETAIL", true).removeObject((SHOP_MATERIAL_STATUS_DETAIL) shopMaterialStatusDetailObjs.nextElement());
//			}
//			logger.log(Severity.ERROR, "asdsadd--进来啦111=="+foid, null);
//			while (shopMaterialStatusDetailObj.hasMoreElements()) {
//				logger.log(Severity.ERROR, "asdsadd--进来啦222=="+foid, null);
//				SHOP_MATERIAL_STATUS_DETAIL shopMaterialStatusDetailInfo = (SHOP_MATERIAL_STATUS_DETAIL) shopMaterialStatusDetailObj
//					.nextElement();
//				this.addSHOP_MATERIAL_STATUS_DETAILObject(shopMaterialStatusDetailInfo);
//			}
//		}
	}

	/**
	 * Clears the local variables.
	 */
	private void clear() {
		shopMaterialStatusObject = null;
		shopMaterialStatusDetail.clear();
	}


}

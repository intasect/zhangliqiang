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
import com.laiyifen.common.core.APPROVAL_HISTORY;
import com.laiyifen.common.core.ATTACHMENT;
import com.laiyifen.common.util.BusObjectHelper;
import com.laiyifen.shop.util.ShopMasterUtil;
import com.laiyifen.shop.util.ShopUtil;

public class CompositeShopTransInfo extends CompositeShopTransInfoBase {
	private SHOP_TRANS_INFO shopTransInfoObject = null;
	private SHOP_MASTER shopMasterObject = null;
	private SHOP_TRANS_ENG shopTransEngObject = null;
	private SHOP_TRANS_DISTRIBUTION shopTransDistributionObject = null;
	private Vector<SHOP_TRANS_DISTRIBUTION> shopTransDistributionObjects = new Vector<SHOP_TRANS_DISTRIBUTION>();
	private APPROVAL_HISTORY approvalHistoryObject = null;
	private Vector<APPROVAL_HISTORY> approvalHistoryObjects = new Vector<APPROVAL_HISTORY>();
	private ATTACHMENT attachment = null;
	private Vector<ATTACHMENT> attachmentObjects = new Vector<ATTACHMENT>();

	public CompositeShopTransInfo() {
		this((BusObjectConfig) null);
	}

	public CompositeShopTransInfo(BusObjectConfig config) {
		super(config);
	}

	/**
     * 使用复合对象查询页面上所需要的数据
     * @param formID	表单ID
     * @param shopID	门店ID
     * @return 复合对象
     */
	public static com.laiyifen.shop.CompositeShopTransInfo getCompositeShopTransInfoObject(
			String formID, String shopID) {
		CompositeShopTransInfo compositeShopTransInfo = new CompositeShopTransInfo();

		SHOP_TRANS_INFO shopTransInfo = SHOP_TRANS_INFO
				.getShopTransInfoByShopId(shopID);
		compositeShopTransInfo.setSHOP_TRANS_INFOObject(shopTransInfo);

		SHOP_MASTER shopMaster = SHOP_MASTER.getShopMasterObject(shopID);
		//门店连锁号为空时自动生成门店连锁号，门店编号，SAP编码以及门店名称
		if("".equals(shopMaster.getSHOP_SERIALNO()) || shopMaster.getSHOP_SERIALNO() == null){
			String areaName = shopMaster.getSHOP_AREACODE().substring(0, 2);
			String sapNo = ShopMasterUtil.getMDMShopId(areaName, "1");
			String shopSerialNo = ShopMasterUtil.getShopId();
			String shopNo = shopSerialNo.substring(shopSerialNo.length()-4);
			String shopName = ShopMasterUtil.getShopName(shopMaster.getSHOP_AREA(), shopMaster.getSHOP_ADDR(), shopID);
			shopMaster.setSHOP_NO(shopNo);
			shopMaster.setSAP_NO(sapNo);
			shopMaster.setSHOP_SERIALNO(shopSerialNo);
			shopMaster.setSHOP_NAME(shopName);
			updateShopMasterObject(shopMaster);
		}
				
		compositeShopTransInfo.setSHOP_MASTERObject(shopMaster);
		
		//将SHOP_TRANS_INFO表中FORM_ID赋给此方法的输入参数formID
		if(shopTransInfo != null && !"".equals(shopTransInfo.getFORM_ID()) && shopTransInfo.getFORM_ID() != null){
			formID = shopTransInfo.getFORM_ID();
		}
		
		SHOP_TRANS_ENG shopTransEng = SHOP_TRANS_ENG
				.getShopTransEngObjectByFormId(formID);
		compositeShopTransInfo.setSHOP_TRANS_ENGObject(shopTransEng);

		BusObjectIterator<SHOP_TRANS_DISTRIBUTION> shopTransDistribution = SHOP_TRANS_DISTRIBUTION
				.getShopTransDistributionObjectsByFormId(formID);

		if (shopTransDistribution != null) {
			while (shopTransDistribution.hasMoreElements()) {
				SHOP_TRANS_DISTRIBUTION shopTransDistributionInfo = (SHOP_TRANS_DISTRIBUTION) shopTransDistribution
						.nextElement();
				compositeShopTransInfo
						.addSHOP_TRANS_DISTRIBUTIONObject(shopTransDistributionInfo);
			}
		}

		BusObjectIterator<ATTACHMENT> attachmentObj = ATTACHMENT
				.getAttachment(formID);
		if (attachmentObj != null) {
			while (attachmentObj.hasMoreElements()) {
				ATTACHMENT attachmentInfo = (ATTACHMENT) attachmentObj
						.nextElement();
				compositeShopTransInfo.addATTACHMENTObject(attachmentInfo);
			}
		}

		BusObjectIterator<APPROVAL_HISTORY> approvalHistoryObj = APPROVAL_HISTORY
				.getApprovalHistory(formID);
		if (approvalHistoryObj != null) {
			while (approvalHistoryObj.hasMoreElements()) {
				APPROVAL_HISTORY approvalHistoryInfo = (APPROVAL_HISTORY) approvalHistoryObj
						.nextElement();
				compositeShopTransInfo
						.addAPPROVAL_HISTORYObject(approvalHistoryInfo);
			}
		}

		compositeShopTransInfo.makeTransient();
		return compositeShopTransInfo;
	}

	public static BusObjectIterator<com.laiyifen.shop.CompositeShopTransInfo> getCompositeShopTransInfoObjects(
			com.cordys.cpc.bsf.query.Cursor cursor) {
		// TODO implement body
		return null;
	}

	/**
     * 新增时触发此事件对复合对象中的子对象进行新增操作
     * @param
     * @return
     */
	public void onInsert() {
		// Removing the unnecessary objects from the parent object.
		BusObjectHelper.removeEmptyChildren(this);

		SHOP_TRANS_INFO shopTransInfo = this.getSHOP_TRANS_INFOObject();
		if (shopTransInfo != null) {
			ShopUtil _formIDGenerator = new ShopUtil();
			String formID = _formIDGenerator.setFormID(null);
			shopTransInfo.setFORM_ID(formID);
			
			shopTransInfoObject = (SHOP_TRANS_INFO) ObjectHelper
					.createObjectForInsert(shopTransInfo,
							com.laiyifen.shop.SHOP_TRANS_INFO.class, false);
			shopTransInfoObject.insert();
		}

		SHOP_MASTER shopMasterInfo = this.getSHOP_MASTERObject();
		if (shopMasterInfo != null) {
			shopMasterObject = (SHOP_MASTER) ObjectHelper
					.createObjectForInsert(shopMasterInfo,
							com.laiyifen.shop.SHOP_MASTER.class, false);
			shopMasterObject.insert();
		}

		SHOP_TRANS_ENG shopTransEngInfo = this.getSHOP_TRANS_ENGObject();
		if (shopTransEngInfo != null) {
			ShopUtil _formIDGenerator = new ShopUtil();
			String ID = _formIDGenerator.setFormID(null);
			shopTransEngInfo.setID(ID);
			
			shopTransEngObject = (SHOP_TRANS_ENG) ObjectHelper
					.createObjectForInsert(shopTransEngInfo,
							com.laiyifen.shop.SHOP_TRANS_ENG.class, false);
			shopTransEngObject.insert();
		}

		BusObjectIterator<SHOP_TRANS_DISTRIBUTION> shopTransDistributionObj = this
				.getSHOP_TRANS_DISTRIBUTIONObjects();
		if (shopTransDistributionObj != null
				&& !"".equals(shopTransDistributionObj)) {
			while (shopTransDistributionObj.hasMoreElements()) {
				SHOP_TRANS_DISTRIBUTION shopTransDistributionInfo = (SHOP_TRANS_DISTRIBUTION) shopTransDistributionObj
						.nextElement();
				
				ShopUtil _formIDGenerator = new ShopUtil();
				String ID = _formIDGenerator.setFormID(null);
				shopTransDistributionInfo.setID(ID);
				
				shopTransDistributionObject = (SHOP_TRANS_DISTRIBUTION) ObjectHelper
						.createObjectForInsert(
								shopTransDistributionInfo,
								com.laiyifen.shop.SHOP_TRANS_DISTRIBUTION.class,
								false);
				shopTransDistributionObject.insert();
				shopTransDistributionObjects.add(shopTransDistributionObject);
			}
		}

		BusObjectHelper.unlinkChildren(this, SHOP_TRANS_DISTRIBUTION.class);

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
				approvalHistoryObjects.add(approvalHistoryObject);
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
				attachmentObjects.add(attachment);
			}
		}
		BusObjectHelper.unlinkChildren(this, ATTACHMENT.class);
	}

	/**
     * 更改时触发此事件对复合对象中的子对象进行更改操作
     * @param
     * @return
     */
	public void onUpdate() {
		BusObjectHelper.removeEmptyChildren(this);
		SHOP_TRANS_INFO shopTransInfo = this.getSHOP_TRANS_INFOObject();

		if (shopTransInfo != null) {
			shopTransInfoObject = (SHOP_TRANS_INFO) ObjectHelper
					.createObjectForUpdate(shopTransInfo,
							com.laiyifen.shop.SHOP_TRANS_INFO.class, false);
			shopTransInfoObject.update();
		}

		SHOP_MASTER shopMasterInfo = this.getSHOP_MASTERObject();
		if (shopMasterInfo != null) {
			shopMasterObject = (SHOP_MASTER) ObjectHelper
					.createObjectForUpdate(shopMasterInfo,
							com.laiyifen.shop.SHOP_MASTER.class, false);
			shopMasterObject.update();
		}

		SHOP_TRANS_ENG shopTransEngInfo = this.getSHOP_TRANS_ENGObject();
		if (shopTransEngInfo != null) {
			shopTransEngObject = (SHOP_TRANS_ENG) ObjectHelper
					.createObjectForUpdate(shopTransEngInfo,
							com.laiyifen.shop.SHOP_TRANS_ENG.class, false);
			shopTransEngObject.update();
		}

		BusObjectIterator<SHOP_TRANS_DISTRIBUTION> shopTransDistributionObj = this
				.getSHOP_TRANS_DISTRIBUTIONObjects();
		if (shopTransDistributionObj != null) {
			while (shopTransDistributionObj.hasMoreElements()) {
				SHOP_TRANS_DISTRIBUTION shopTransDistributionInfo = (SHOP_TRANS_DISTRIBUTION) shopTransDistributionObj
						.nextElement();
				shopTransDistributionObject = (SHOP_TRANS_DISTRIBUTION) ObjectHelper
						.createObjectForUpdate(
								shopTransDistributionInfo,
								com.laiyifen.shop.SHOP_TRANS_DISTRIBUTION.class,
								false);
				shopTransDistributionObject.update();
				shopTransDistributionObjects.add(shopTransDistributionObject);
			}
		}

		BusObjectHelper.unlinkChildren(this, SHOP_TRANS_DISTRIBUTION.class);

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
				approvalHistoryObjects.add(approvalHistoryObject);
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
				attachmentObjects.add(attachment);
			}
		}

		BusObjectHelper.unlinkChildren(this, ATTACHMENT.class);
	}

	/**
     * 删除时触发此事件对复合对象中的子对象进行删除操作
     * @param
     * @return
     */
	public void onDelete() {
		BusObjectHelper.removeEmptyChildren(this);
		SHOP_TRANS_INFO shopTransInfo = this.getSHOP_TRANS_INFOObject();
		if (shopTransInfo != null) {
			shopTransInfoObject = (SHOP_TRANS_INFO) ObjectHelper
					.createObjectForDelete(shopTransInfo,
							com.laiyifen.shop.SHOP_TRANS_INFO.class, true);
			shopTransInfoObject.delete();
		}

		SHOP_MASTER shopMasterInfo = this.getSHOP_MASTERObject();
		if (shopMasterInfo != null) {
			shopMasterObject = (SHOP_MASTER) ObjectHelper
					.createObjectForDelete(shopMasterInfo,
							com.laiyifen.shop.SHOP_MASTER.class, true);
			shopMasterObject.delete();
		}

		SHOP_TRANS_ENG shopTransEngInfo = this.getSHOP_TRANS_ENGObject();
		if (shopTransEngInfo != null) {
			shopTransEngObject = (SHOP_TRANS_ENG) ObjectHelper
					.createObjectForDelete(shopTransEngInfo,
							com.laiyifen.shop.SHOP_TRANS_ENG.class, true);
			shopTransEngObject.delete();
		}

		BusObjectIterator<SHOP_TRANS_DISTRIBUTION> shopTransDistributionObj = this
				.getSHOP_TRANS_DISTRIBUTIONObjects();
		if (shopTransDistributionObj != null) {
			while (shopTransDistributionObj.hasMoreElements()) {
				SHOP_TRANS_DISTRIBUTION shopTransDistributionInfo = (SHOP_TRANS_DISTRIBUTION) shopTransDistributionObj
						.nextElement();
				ObjectHelper.createObjectForDelete(shopTransDistributionInfo,
						com.laiyifen.shop.SHOP_TRANS_DISTRIBUTION.class, true);
				shopTransDistributionInfo.delete();
			}
		}

		BusObjectIterator<APPROVAL_HISTORY> approvalHistoryObj = this
				.getAPPROVAL_HISTORYObjects();
		if (approvalHistoryObj != null) {
			while (approvalHistoryObj.hasMoreElements()) {
				APPROVAL_HISTORY approvalHistoryInfo = (APPROVAL_HISTORY) approvalHistoryObj
						.nextElement();
				ObjectHelper.createObjectForDelete(approvalHistoryInfo,
						com.laiyifen.common.core.APPROVAL_HISTORY.class, true);
				approvalHistoryInfo.delete();
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
				attachmentInfo.delete();
			}
		}
	}

	/**
     * 提交后把复合对象返回前端,并且清空变量
     * @param event
     * @return
     */
	public void onAfterCommit(AfterCommitObjectEvent event) {
		// TODO Auto-generated method stub
		super.onAfterCommit(event);
		this.clear();
	}

	/**
     * 清除本地变量
     * @param
     * @return
     */
	private void clear() {
		shopTransInfoObject = null;
		shopMasterObject = null;
		shopTransEngObject = null;
		shopTransDistributionObjects.clear();
		approvalHistoryObjects.clear();
		attachmentObjects.clear();
	}
	
	/**
     * 将自动生成的门店编码，门店连锁号以及门店名称更新到门店主数据表.
     * @param 	shopMaster	门店主数据对象
     * @return
     */
	private static void updateShopMasterObject(SHOP_MASTER shopMaster){
		if (shopMaster != null) {
			SHOP_MASTER shopMasterObject = (SHOP_MASTER) ObjectHelper
					.createObjectForUpdate(shopMaster,
							com.laiyifen.shop.SHOP_MASTER.class, false);
			shopMasterObject.update();
		}
	}
	/**
     * 将目标调查表中和供应商主数据中的部分数据同步到新开店信息移交单中.
     * @param 	shopId	门店编码
     * @return
     */
	public static void initialShopTransInfo(String shopId)
    {
		SHOP_TARGET_INFO shopTargetInfo = SHOP_TARGET_INFO
		.getShopTargetInfoByShopid(shopId);
		SUPPLIERS_MASTER supplierInfo = SUPPLIERS_MASTER.getSupplierInfoByShopId(shopId);
		SHOP_TRANS_INFO shopTransInfo = new SHOP_TRANS_INFO();
		//生成formID
		ShopUtil _formIDGenerator = new ShopUtil();
		String formID = _formIDGenerator.setFormID(null);
		shopTransInfo.setFORM_ID(formID);

		shopTransInfo.setSHOP_ID(shopId);
		shopTransInfo.setSTATUS("1");
		if(shopTargetInfo.getUSE_AREA() > 0){
			shopTransInfo.setTOTAL_AREA(shopTargetInfo.getUSE_AREA());
		}
		if(shopTargetInfo.getDOOR_WIDTH() > 0){
			shopTransInfo.setWIDTH(shopTargetInfo.getDOOR_WIDTH());
		}
		if(shopTargetInfo.getINNER_DOOR_WIDTH() > 0){
			shopTransInfo.setINNER_WIDTH(shopTargetInfo.getINNER_DOOR_WIDTH());
		}
		if(shopTargetInfo.getSHOP_DEPTH() > 0){
			shopTransInfo.setDEPTH(shopTargetInfo.getSHOP_DEPTH());
		}
		if(shopTargetInfo.getRENTFREE() > 0){
			shopTransInfo.setRENTFREE_PERIOD(shopTargetInfo.getRENTFREE());
		}
		if(shopTargetInfo.getONEROOM_CODE() != null){
			shopTransInfo.setBAY_CODE(shopTargetInfo.getONEROOM_CODE());
		}
		if(shopTargetInfo.getONEROOM() != null){
			shopTransInfo.setBAY_NAME(shopTargetInfo.getONEROOM());
		}
		if(shopTargetInfo.getMAIN_BUSROUTES() != null){
			shopTransInfo.setBUS_ROUTE(shopTargetInfo.getMAIN_BUSROUTES());
		}
		if(shopTargetInfo.getNEAR_BANK() != null){
			shopTransInfo.setPUBLIC_BANK(shopTargetInfo.getNEAR_BANK());
		}
		shopTransInfo.setLANDLORD_CONTACT(supplierInfo.getCONTACTER());
		shopTransInfo.setCONTACT_PHONE(supplierInfo.getCONTACTER_PHONE());
		Date date = new Date();
		shopTransInfo.setCREATE_TIME(date);
		
		shopTransInfo.insert();
    }
}
/*
  This class has been generated by the Code Generator
 */

package com.laiyifen.shop;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.QueryObject;
import com.laiyifen.common.util.GUIDUtil;

/*
 * 插入复合对象前初始化 @Author Jiang
 */
public class SHOP_TARGET_ASSESMENT extends SHOP_TARGET_ASSESMENTBase {
	private GUIDUtil guidGenerator = null;
	private String guidValue = null;

	public SHOP_TARGET_ASSESMENT() {
		this((BusObjectConfig) null);
	}

	public SHOP_TARGET_ASSESMENT(BusObjectConfig config) {
		super(config);
	}

	/*
	 * 插入复合对象前初始化GUID
	 * 
	 * @see com.cordys.cpc.bsf.busobject.BusObject#onBeforeInsert()
	 */
	public void onBeforeInsert() {

		if (guidGenerator == null) {
			guidGenerator = new GUIDUtil();
		}
		if (guidValue == null) {
			guidValue = guidGenerator.setGUID(null);
		}

		if (this.getID() == null || this.getID() == null) {
			this.setID(guidValue);
		}
	}

	public static com.laiyifen.shop.SHOP_TARGET_ASSESMENT getShopTargetAssesmentObjectForFormId(
			String FORM_ID) {
		String queryText = "select * from \"SHOP_TARGET_ASSESMENT\" where \"FORM_ID\" = :FORM_ID";
		QueryObject query = new QueryObject(queryText);
		query.addParameter("FORM_ID", "SHOP_TARGET_ASSESMENT.FORM_ID", QueryObject.PARAM_STRING,FORM_ID);
		query.setResultClass(SHOP_TARGET_ASSESMENT.class);
		return (SHOP_TARGET_ASSESMENT)query.getObject();
	}

}

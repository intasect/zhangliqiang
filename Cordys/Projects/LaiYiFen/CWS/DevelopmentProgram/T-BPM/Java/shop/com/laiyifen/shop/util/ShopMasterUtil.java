package com.laiyifen.shop.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cordys.cpc.bsf.busobject.StoredProcedure;
import com.laiyifen.shop.SHOP_TARGET_INFO;

/**
 * ShopMasterUtil 公共工具类，当前主要是序列号相关功能
 * 
 * @author Lee Chengye
 * 
 */

public class ShopMasterUtil {
	/**
	 * 调用存储过程生成序列号
	 * 
	 * @param seqType
	 *            序列号类型，为自定为标示，如'SHOPID','FLOWID'
	 * @return 序列号
	 */
	private static String getSeqNumber(String seqType) {
		String value = null;
		StoredProcedure sp = new StoredProcedure("SF_GET_SEQ");
		sp.prepareCall(":RESULT = \"SF_GET_SEQ\"( :PARAM1)");
		sp.setReturnParameter("RESULT");
		sp.setParameter("PARAM1", seqType);
		sp.execute();
		value = sp.getString("RESULT");
		return value;
	}

	/**
	 * 获取门店编码，不足7位时用0补齐
	 * 
	 * @return
	 */
	public static String getShopId() {
		String shopId = getSeqNumber("SHOPID");

		DecimalFormat df = new DecimalFormat("0000000");
		shopId = df.format(Integer.parseInt(shopId));
		return shopId;
	}

	/**
	 * 根据中文数字获取阿拉伯数字，支持1位数字
	 * 
	 * @param chineseName
	 *            中文数字
	 * @return 阿拉伯数字
	 */
	private static int getInt(String chineseName) {
		final String[] DigitalCode = { "零", "一", "二", "三", "四", "五", "六", "七",
				"八", "九" };
		int number = 0;
		for (int i = 0; i < DigitalCode.length; i++) {
			if (chineseName.equals(DigitalCode[i])) {
				number = i;
				break;
			}

		}
		return number;
	}

	/**
	 * 根据阿拉伯数字，获取中文数字，支持1位长度数字
	 * 
	 * @param number
	 *            阿拉伯数字
	 * @return 中文数字
	 */
	private static String getCNNumber(int number) {
		final String[] DigitalCode = { "零 ", "一", "二", "三", "四", "五", "六", "七",
				"八", "九" };
		return DigitalCode[number];
	}

	/**
	 * 调用存储过程从SHOP_MASTER获取同一路名下的门店最大序列号，如七或者八等
	 * 
	 * @param district
	 *            行政区
	 * @param street
	 *            路名
	 * @return 同一路名下的门店最大序列号
	 */
	private static String getMaxShopShortId(String district, String street) {
		String value = null;
		StoredProcedure sp = new StoredProcedure("SF_GET_MAXSHOPSHORTID");
		sp.prepareCall(":RESULT = \"SF_GET_MAXSHOPSHORTID\"( :PARAM1,:PARAM2)");
		sp.setReturnParameter("RESULT");
		sp.setParameter("PARAM1", district);
		sp.setParameter("PARAM2", street);
		sp.execute();
		value = sp.getString("RESULT");

		return value;

	}

	/**
	 * 获取门店名称
	 * 
	 * @param district
	 *            行政区
	 * @param street
	 *            路名
	 * @param shopId
	 *            shop主键
	 * @return 门店名称
	 */
	public static String getShopName(String district, String street,
			String shopId) {
		String shopName = null;
		String shortId = getMaxShopShortId(district, street);
		String mallName = "";
		if ("".equals(shortId) || shortId == null)
			shortId = "";
		else
			shortId = getCNNumber(getInt(shortId) + 1);
		if (shopId == null) {
			shopId = "";
			mallName = "";
		} else {
			SHOP_TARGET_INFO info = SHOP_TARGET_INFO
					.getShopTargetInfoByShopId(shopId);
			if (info != null)
				mallName = info.getSUPER_NAME();

		}
		if (mallName == null)
			mallName = "";
		if (mallName.length() > 0) {
			shopName = district + mallName + street + shortId + "店";
		} else
			shopName = district + street + shortId + "店";
		return shopName;
	}

	/**
	 * 获取门店的MDM编码
	 * 
	 * @param operationType
	 * @param areaId
	 *            地区码 0-9，A-Z，不包括I,O
	 * @param shopType
	 *            门店类型，1为分支门店，区别码范围0-9及A-Q; 2为加盟店，区别码范围R-W;
	 *            3为非分支门店，区别码范围X-Y,4为展会门店，区别码范围Z
	 * @param shopCode
	 *            区别码编号 0-9，A-Z，不包括I,O
	 * 
	 * @return
	 * @deprecated
	 */
	public static String getMDMShopId(String operationType, String areaId,
			String shopType, String shopCode) {
		String value = null;
		StoredProcedure sp = new StoredProcedure("SF_GET_MDM_SHOPID");
		sp.prepareCall(":RESULT = \"SF_GET_MDM_SHOPID\"( :PARAM1,:PARAM2,:PARAM3,:PARAM4)");
		sp.setReturnParameter("RESULT");
		sp.setParameter("PARAM1", areaId);
		sp.setParameter("PARAM2", shopType);
		sp.setParameter("PARAM3", shopCode);
		sp.setParameter("PARAM4", operationType);
		sp.execute();
		value = sp.getString("RESULT");

		return value;

	}

	/**
	 * 获取门店的MDM编码
	 * 
	 * @param areaName
	 *            行政区名称
	 * @param branchType
	 *            门店类型，1为分支门店，区别码范围0-9及A-Q;
	 *            2为加盟店，区别码范围R-W;3为非分支门店，区别码范围X-Y,4为展会门店，区别码范围Z
	 * 
	 * @return MDM编码
	 */
	public static String getMDMShopId(String areaName, String branchType) {
		String value = null;
		StoredProcedure sp = new StoredProcedure("SF_GET_MDM_SHOPID2");
		sp.prepareCall(":RESULT = \"SF_GET_MDM_SHOPID2\"( :PARAM1,:PARAM2)");
		sp.setReturnParameter("RESULT");
		sp.setParameter("PARAM1", areaName);
		sp.setParameter("PARAM2", branchType);
		sp.execute();
		value = sp.getString("RESULT");

		return value;

	}

	/**
	 * 获取批处理号
	 * 
	 * @param sequenceName
	 *            序列名称
	 * @return
	 */
	public static String getBatchNo() {
		String seq = getSeqNumber("BATCH_NO");
		String dateString = "";
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		dateString = dateFormat.format(date);
		return dateString + "_" + seq;
	}

}

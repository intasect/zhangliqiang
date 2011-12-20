package com.laiyifen.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.cordys.cpc.bsf.busobject.BSF;
import com.cordys.cpc.bsf.busobject.BsfContext;
import com.cordys.cpc.bsf.busobject.StoredProcedure;
import com.eibus.xml.nom.Document;
import com.eibus.xml.nom.Node;

/*
 * @Author Jiang.
 */
public class CommonUtil {
	/*
	 * 获取当前系统时间
	 */
	public static String getSystemCurrentDate() {
		String dateString = "";
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateString = dateFormat.format(date);
		return dateString;
	}

	private static Document document = null;

	public static String getOrganization(String dn, boolean isUserDn) {
		String orgDN = isUserDn ? getOrganizationDn(dn) : dn;
		String orgName = orgDN.substring(orgDN.indexOf("=") + 1,
				orgDN.indexOf(","));
		return orgName;
	}

	public static String getOrganizationDn(String userDn) {
		return userDn.substring(userDn.indexOf("o="));
	}

	public static String getDateString() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.'S");
		return df.format(date);
	}

	// This function is added for getting server date for VML
	public static void getServerDate(int requestNode, int responseNode) {
		long currentTime = System.currentTimeMillis();
		Date date = new Date(currentTime);
		DateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss'.'S");
		String serverDate = dateFormat.format(date);
		Node.createTextElement("serverdate", serverDate, responseNode);
	}

	public static long _getDateInMs() {
		Calendar cal = Calendar.getInstance();
		return cal.getTimeInMillis();
	}

	public static synchronized Document getDefaultDocument() {
		if (document == null)
			document = new Document();
		return document;
	}

	/**
	 * Round a double value to a specified number of decimal places.
	 * 
	 * @param val
	 *            the value to be rounded.
	 * @param places
	 *            the number of decimal places to round to.
	 * @return val rounded to places decimal places.
	 */
	public static double round(double val, int places) {
		long factor = (long) Math.pow(10, places);
		// Shift the decimal the correct number of places to the right.
		val = val * factor;
		// Round to the nearest integer.
		long tmp = Math.round(val);
		// Shift the decimal the correct number of places back to the left.
		return (double) tmp / factor;
	}
	
	/**
	 * 调用存储过程生成序列号
	 * 
	 * @param seqType
	 *            序列号类型
	 * @return 序列号
	 *
	 */
	public static String getSeqNumber(String seqType) {
		BsfContext mycontext = AssignmentUtils.initBSF();
		String value = null;
		BSF.getObjectManager().startTransaction("SEQ_TRANS");
		StoredProcedure sp = new StoredProcedure("SF_GET_AUTO_NUMBER");
		sp.prepareCall(":RESULT = \"SF_GET_AUTO_NUMBER\"( :PARAM1)");
		sp.setReturnParameter("RESULT");
		sp.setParameter("PARAM1", seqType);
		sp.execute();
		value = sp.getString("RESULT");
		BSF.getObjectManager().commitTransaction("SEQ_TRANS",true);
		AssignmentUtils.release(mycontext);
		return value;
	}

}
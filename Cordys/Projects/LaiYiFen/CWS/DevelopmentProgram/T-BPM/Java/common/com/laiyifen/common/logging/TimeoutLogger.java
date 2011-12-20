package com.laiyifen.common.logging;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.cordys.cpc.bsf.busobject.BSF;
import com.cordys.cpc.bsf.busobject.BsfContext;
import com.laiyifen.common.core.EMPLOYEE_INFO;
import com.laiyifen.common.core.ORG_COMPANY_INFO;
import com.laiyifen.common.core.TIMEOUT_LOG;
import com.laiyifen.common.util.AssignmentUtils;
import com.laiyifen.common.util.WsAppsEnvInitializer;

public class TimeoutLogger {
	private static Date UTCToDate(String utcDate) throws ParseException {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		date = sdf.parse(utcDate);
		return date;
	}

	public static String log(String instanceId, String activityId,
			String userDN, String startTime, String leadTime, String periodTime)
			throws Exception {
		BsfContext mycontext=null;
		try {
			mycontext=AssignmentUtils.initBSF();
			String result = null;
			EMPLOYEE_INFO info = getEmployeeInfo(userDN);
			Date start = UTCToDate(startTime);
			Date lead = null;
			int leadsec = 0;
			try {
				leadsec = Integer.parseInt(leadTime);
			} catch (Exception e) {
				leadsec = 0;
			}
			Calendar cd = Calendar.getInstance();
			cd.setTime(start);
			cd.add(Calendar.SECOND, leadsec);
			lead = cd.getTime();

			int period = 0;
			try {
				period = Integer.parseInt(periodTime);
			} catch (Exception e) {
				period = 0;
			}
			cd.setTime(start);
			cd.add(Calendar.DAY_OF_MONTH, period);
			Date end = cd.getTime();
			if (!BSF.getTransactionStarted())
				BSF.startTransaction();
			TIMEOUT_LOG log = new TIMEOUT_LOG();
			if (info != null) {
				log.setID(AssignmentUtils.getSeqNumber("TIMEOUT_LOGID"));
				log.setINSTANCE_ID(instanceId);
				log.setACTIVITY_ID(activityId);
				log.setUSER_DN(userDN);
				log.setUSER_CODE(info.getEMPLOYEEID());
				log.setUSER_NAME(info.getSN() + info.getGIVENNAME());
				log.setDEPARTMENT_CODE(info.getDEPTID());
				log.setDEPARTMENT_DN("N/A");
				log.setDEPARTMENT_NAME(info.getDEPTNAME());
				log.setCOMPANY_CODE(info.getCOMPANYCODE());
				ORG_COMPANY_INFO cinfo = ORG_COMPANY_INFO
						.getOrgCompanyInfoObject(info.getCOMPANYCODE());
				if (cinfo != null)
					log.setCOMPANY_NAME(cinfo.getCOMPANY_NAME());
				log.setTIMEOUT_TIME(new Date());
				log.setDUE_TIME(end);
			}
			if (BSF.getTransactionStarted())
				BSF.commitTransaction();
			AssignmentUtils.release(mycontext);
			return result;
		} catch (Exception e) {
			if (BSF.getTransactionStarted())
				BSF.abortTransaction();
			throw e;
		}
	}

	/**
	 * 获取EMPLOYEE_INFO对象
	 * 
	 * @param userDN
	 *            用户DN
	 * @return EMPLOYEE_INFO对象
	 */
	private static EMPLOYEE_INFO getEmployeeInfo(String userDN) {
		return null;
	}
}
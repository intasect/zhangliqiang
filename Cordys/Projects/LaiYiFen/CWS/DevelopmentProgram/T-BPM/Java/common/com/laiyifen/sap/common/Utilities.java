package com.laiyifen.sap.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.eibus.util.logger.CordysLogger;
import com.eibus.util.logger.Severity;
import com.eibus.util.system.EIBProperties;
import com.eibus.xml.nom.Document;
import com.eibus.xml.nom.Node;
import com.laiyifen.common.ConnectorManager;

public class Utilities
{
	private static CordysLogger laiyifenLogger = CordysLogger.getCordysLogger(Utilities.class);
	private static Document document = null;
	
	public static String getOrganization(String dn, boolean isUserDn)
	{
		String orgDN = isUserDn ? getOrganizationDn(dn) : dn;
		String orgName = orgDN.substring(orgDN.indexOf("=") + 1, orgDN.indexOf(","));
		return orgName;
	}
	
	public static String getOrganizationDn(String userDn)
	{
		return userDn.substring(userDn.indexOf("o="));
	}
	
	public static String getOrganizationDn()
	{
		return getOrganizationDn(getOrganizationUserDn());
	}

	public static String getOrganizationUserDn()
	{
		return RequestContext.getRequestContext().getOrganizationUser();
	}
	
	public static int createDocumentElement(String tagName)
	{
		if(RequestContext.getRequestContext() != null)
			return RequestContext.getRequestContext().getDocument().createElement(tagName);
		else
			return getDefaultDocument().createElement(tagName);
		
	}

	public static String getDateString()
	{
		Date date = new Date();
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.'S");
	    return df.format(date);
	}
	
	//This function is added for getting  server date for VML
	public static void getServerDate(int requestNode,int responseNode)
	{		
		long currentTime = System.currentTimeMillis();
		Date date = new Date(currentTime);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.'S");
		String serverDate = dateFormat.format(date);
		Node.createTextElement("serverdate", serverDate, responseNode);
	}
	
	public static long _getDateInMs()
	{
		Calendar cal = Calendar.getInstance();
		return cal.getTimeInMillis();
	}
	public static Document getDefaultDocument()
	{
		if(document == null)
			document = new Document();
		return document;
	}
	
	public static int getDefaultResponse()
	{
		return Utilities._parseString("<data/>");
	}
		
	/**
	 * use this method for only internal XML, where we are sure that XMLs are valid.
	 * */
	public static int _parseString(String xmlString)
	{
		int xmlNode = 0;
		try {
			if(RequestContext.getRequestContext() == null)
				xmlNode = getDefaultDocument().parseString(xmlString);
			else
				xmlNode = RequestContext.getRequestContext().getDocument().parseString(xmlString);
		}
		catch(Exception exception)
		{
			if(laiyifenLogger.isEnabled(Severity.ERROR)) {
				laiyifenLogger.log(Severity.ERROR, exception.getMessage(),exception);
			}
		}	
		return xmlNode;
	}
	
	public static int loadFile(String fileName)
	{
		int loadFileNode = 0;
		try {
			if(RequestContext.getRequestContext() == null)
				loadFileNode =  getDefaultDocument().load(fileName);
			else
				loadFileNode = RequestContext.getRequestContext().getDocument().load(fileName);
		}
		catch (Exception e) {
			if(laiyifenLogger.isEnabled(Severity.ERROR)) {
				laiyifenLogger.log(Severity.ERROR, e.getMessage(), e);
			}
		}
		return loadFileNode;
	}
	/*
	public static boolean _validateScheduleDate(int scheduleNode)
	{
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int scheduleStartTimeNode = XPathUtil._getResultNode("//datetime/starttime",scheduleNode,null);
		int scheduleEndTimeNode = XPathUtil._getResultNode("//datetime/endtime",scheduleNode,null);
		String scheduleStartTime = Node.getData(scheduleStartTimeNode);
		
		String scheduleEndTime = Node.getData(scheduleEndTimeNode);
		if(!scheduleStartTime.equals("") && (scheduleStartTime.compareTo(df.format(date)) < 0)
				|| (!scheduleEndTime.equals("-1") && (scheduleEndTime.compareTo(df.format(date))< 0))
				|| (!scheduleEndTime.equals("-1") && !scheduleStartTime.equals("") && (scheduleStartTime.compareTo(scheduleEndTime) > 0)))
		{
			if(laiyifenLogger.isEnabled(Severity.ERROR)) {
				laiyifenLogger.log(Severity.ERROR, "Invalid Schedule Dates; " +
											"start time : " + scheduleStartTime + "; " +
											"end time : " + Node.getData(scheduleEndTimeNode));
			}
			return false;
		}
		return true; 	   
	}
	*/
	
	/**
	 * Round a double value to a specified number of decimal places.
	 * 
	 * @param val the value to be rounded.
	 * @param places the number of decimal places to round to.
	 * @return val rounded to places decimal places.
	 */
	public static double round(double val, int places)
	{
		long factor = (long)Math.pow(10,places);
		// Shift the decimal the correct number of places to the right.
		val = val * factor;
		// Round to the nearest integer.
		long tmp = Math.round(val);
		// Shift the decimal the correct number of places back to the left.
		return (double)tmp / factor;
	}
	
	public static void _resetProcessors(String soapProcessorDn)
	{
		String monitorNs = "http://schemas.cordys.com/1.0/monitor";
		String methodName = "Reset";
		try {
			
			String ldapProcessorDn = "cn=LDAP Processor,cn=LDAP Service,cn=soap nodes,o=system," + EIBProperties.getProperty(EIBProperties.LDAP_ROOT);
			int child1[] = { _parseString("<dn>" + ldapProcessorDn + "</dn>") };
			int child2[] = { _parseString("<dn>" + soapProcessorDn + "</dn>") };
			int ldapResponse = ConnectorManager._callSoapMethod(monitorNs, methodName, child1);
			Node.delete(ldapResponse);
			ldapResponse = 0;
			int processorResponse = ConnectorManager._callSoapMethod(monitorNs, methodName, child2);
			Node.delete(processorResponse);
			processorResponse = 0;
		}
		catch (Exception e) {
			if(laiyifenLogger.isEnabled(Severity.ERROR)) {
				laiyifenLogger.log(Severity.ERROR, "Error while resetting the LDAP processor : " + e.getMessage(), e);
			}
		}
	}

}

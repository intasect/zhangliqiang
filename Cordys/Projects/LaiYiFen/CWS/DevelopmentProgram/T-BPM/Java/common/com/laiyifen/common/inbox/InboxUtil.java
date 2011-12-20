package com.laiyifen.common.inbox;

import com.cordys.cpc.bsf.busobject.BSF;
import com.cordys.cpc.bsf.busobject.exception.WSAppServerRunTimeException;
import com.cordys.cpc.bsf.soap.SOAPRequestObject;
import com.eibus.xml.nom.Node;
import com.eibus.xml.xpath.XPath;
/**
 * For project LaiYifen T-BPM
 * This is an utility class for InboxHandler.
 * 
 * @author Zhang Li Qiang
 *
 */
public class InboxUtil {
	//joint all conditions for the query text in QueryAdminData
    public static String getConditions(String processtype, String processcode, String sender, String fromdate, String todate, String subject){
    	return getInstanceidCondition(processtype, processcode) + getSenderCondition(sender) + getDateCondition(fromdate, todate) + getSubjectCondition(subject);
    }
    //construct a SQL which can get INSTANCE_ID by business_identifiers
    private static String getInstanceidCondition(String processtype, String processcode){
    	if(processtype.equals("") == true && processcode.equals("") == true){
    		return "";
    	}
    	if(processtype.equals("") == false && processcode.equals("") == false){
    		String sql_processtye = InboxConstants.SQL_GET_INSTANCE_ID_BY_IDENTIFIER;
    		String sql_processcode = InboxConstants.SQL_GET_INSTANCE_ID_BY_IDENTIFIER;
			String sql_internal = sql_processtye.replace(
					"$IDENTIFIER_DESCRIPTION$",
					InboxConstants.BUSINESS_IDENTIFIER_PROCESSTYPE).replace(
					"$IDENTIFIER_VALUE$", processtype)
					+ InboxConstants.SQL_INTERSECT
					+ " "
					+ sql_processcode.replace("$IDENTIFIER_DESCRIPTION$",
							InboxConstants.BUSINESS_IDENTIFIER_PROCESSCODE)
							.replace("$IDENTIFIER_VALUE$", processcode);
			return " AND SOURCE_INSTANCE_ID IN (SELECT INSTANCE_ID FROM (" +sql_internal+ "))";
			
    	}
    	if(processtype.equals("") == false){
    		String sql_processtye = InboxConstants.SQL_GET_INSTANCE_ID_BY_IDENTIFIER;
			return " AND SOURCE_INSTANCE_ID IN ("
					+ sql_processtye.replace("$IDENTIFIER_DESCRIPTION$",
							InboxConstants.BUSINESS_IDENTIFIER_PROCESSTYPE)
							.replace("$IDENTIFIER_VALUE$", processtype)
					+ ")";
    	}
    	if(processcode.equals("") == false){
    		String sql_processcode = InboxConstants.SQL_GET_INSTANCE_ID_BY_IDENTIFIER;
			return " AND SOURCE_INSTANCE_ID IN ("
					+ sql_processcode.replace("$IDENTIFIER_DESCRIPTION$",
							InboxConstants.BUSINESS_IDENTIFIER_PROCESSCODE)
							.replace("$IDENTIFIER_VALUE$", processcode)
					+ ")";
    	}
		return "";
    }
    //construct a filter "sender"
    private static String getSenderCondition(String sender){
    	if(sender.equals("") == false){
    		String sql_internal = InboxConstants.SQL_GET_PARTICIPANT_ID_BY_DISTINCT_NAME;
    		return " AND SENDER = (" + sql_internal.replace("$DISTINCT_NAME$", sender) + ")";
    	}
    	return "";
    }
    //construct a filter "fromdate <= delevery_date <= todate"
    private static String getDateCondition(String fromdate, String todate){
    	if(fromdate.equals("") == true && todate.equals("") == true){
    		return "";
    	}
    	if(fromdate.equals("") == false && todate.equals("") == false){
    		return " AND DELIVERY_DATE+8/24 &gt;= TO_DATE('"+fromdate+"', '"+InboxConstants.SQL_DATE_FORMAT+"') AND DELIVERY_DATE+8/24 &lt;= TO_DATE('"+todate+"', '"+InboxConstants.SQL_DATE_FORMAT+"')+1";
    	}
    	if(fromdate.equals("") == false){
    		return " AND DELIVERY_DATE+8/24 &gt;= TO_DATE('"+fromdate+"', '"+InboxConstants.SQL_DATE_FORMAT+"')";
    	}
    	if(todate.equals("") == false){
    		return " AND DELIVERY_DATE+8/24 &lt;= TO_DATE('"+todate+"', '"+InboxConstants.SQL_DATE_FORMAT+"')+1";
    	}
    	return "";
    }
    //construct a filter "subject like '%value%'"
    private static String getSubjectCondition(String subject){
    	if(subject.equals("") == false){
    		return " AND SUBJECT LIKE '%"+subject+"%'";
    	}
    	return "";
    }
    //invoke method QueryAdminData, and return the response of it
    public static int queryAdminData(String requestStr){
    	int request = 0;
		int response = 0;
    	try {
    		request = BSF.getXMLDocument().load(requestStr.getBytes("UTF-8"));
			SOAPRequestObject sro = new SOAPRequestObject(BSF.getOrganization(), InboxConstants.NAMESPACE_QUERYADMINDATA, InboxConstants.METHODNAME_QUERYADMINDATA, null, null);
			sro.addParameterAsXml(request);
			response = sro.execute();
			return Node.duplicate(XPath.getXPathInstance(".//dataset").firstMatch(response, null));
		} catch (Exception e) {
			throw new WSAppServerRunTimeException(e.getMessage());
		} finally {
			if (request != 0){
				Node.delete(request);
				request = 0;
			}
			if (response != 0){
				Node.delete(response);
				response = 0;
			}
		}
    }
}

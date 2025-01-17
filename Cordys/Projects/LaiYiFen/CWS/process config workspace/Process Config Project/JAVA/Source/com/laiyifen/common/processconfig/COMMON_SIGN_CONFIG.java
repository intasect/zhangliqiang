/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.common.processconfig;

import java.util.UUID;

import com.cordys.cpc.bsf.busobject.BSF;
import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.busobject.QueryObject;
import com.cordys.cpc.bsf.busobject.exception.WSAppServerRunTimeException;
import com.cordys.cpc.bsf.soap.SOAPRequestObject;
import com.eibus.xml.nom.Node;
import com.eibus.xml.xpath.XPath;


public class COMMON_SIGN_CONFIG extends COMMON_SIGN_CONFIGBase
{
    public COMMON_SIGN_CONFIG()
    {
        this((BusObjectConfig)null);
    }

    public COMMON_SIGN_CONFIG(BusObjectConfig config)
    {
        super(config);
    }
    @Override
	protected void onBeforeInsert() {
		this.setID(UUID.randomUUID().toString());
	}
    public static BusObjectIterator<com.laiyifen.common.processconfig.COMMON_SIGN_CONFIG> getMyCOMMON_SIGN_CONFIG(String processname, String stepname)
    {
    	StringBuffer filter = new StringBuffer("(");
    	String[] roles = getRoleListForCurrentUser().split(";");
    	int c = 0;
    	for(int i=0; i<roles.length; i++){
    		if(roles[i].equals("")){
    			continue;
    		}
    		if(c == 0) {
    			filter.append("'").append(roles[i].substring(roles[i].indexOf('=')+1, roles[i].indexOf(','))).append("'");
    		}else {
    			filter.append(", '").append(roles[i].substring(roles[i].indexOf('=')+1, roles[i].indexOf(','))).append("'");
    		}
    		c++;
    	}
    	filter.append(")");
    	String queryText = "";
    	processname = processname.trim();
    	stepname = stepname.trim();
        if(processname.equals("") && stepname.equals("")){
        	queryText = "SELECT CSC.* FROM COMMON_SIGN_CONFIG CSC, COMMON_PROCESS_CONFIG CPC WHERE CSC.PROCESS_CODE = CPC.PROCESS_CODE AND CSC.STEP_CODE = CPC.STEP_CODE AND CPC.ROLE_NAME IN " + filter.toString();
        } else {
        	if(processname.equals("")){
        		queryText = "SELECT * FROM (SELECT CSC.* FROM COMMON_SIGN_CONFIG CSC, COMMON_PROCESS_CONFIG CPC WHERE CSC.PROCESS_CODE = CPC.PROCESS_CODE AND CSC.STEP_CODE = CPC.STEP_CODE AND CPC.ROLE_NAME IN " + filter.toString() + ") WHERE STEP_NAME LIKE '%"+stepname+"%'";
        	}
        	if(stepname.equals("")){
        		queryText = "SELECT * FROM (SELECT CSC.* FROM COMMON_SIGN_CONFIG CSC, COMMON_PROCESS_CONFIG CPC WHERE CSC.PROCESS_CODE = CPC.PROCESS_CODE AND CSC.STEP_CODE = CPC.STEP_CODE AND CPC.ROLE_NAME IN " + filter.toString() + ") WHERE PROCESS_NAME LIKE '%"+processname+"%'";
        	}
        	if(!processname.equals("") && !stepname.equals("")){
        		queryText = "SELECT * FROM (SELECT CSC.* FROM COMMON_SIGN_CONFIG CSC, COMMON_PROCESS_CONFIG CPC WHERE CSC.PROCESS_CODE = CPC.PROCESS_CODE AND CSC.STEP_CODE = CPC.STEP_CODE AND CPC.ROLE_NAME IN " + filter.toString() + ") WHERE PROCESS_NAME LIKE '%"+processname+"%' AND STEP_NAME LIKE '%"+stepname+"%'";
        	}
        }
    	QueryObject query = new QueryObject(queryText);
        query.setResultClass(COMMON_SIGN_CONFIG.class);
        return query.getObjects();
    }
    private static String getRoleListForCurrentUser(){
    	int response = 0;
    	String role_list = "";
    	try{
    		SOAPRequestObject sro = new SOAPRequestObject(BSF.getOrganization(), "http://schemas.cordys.com/1.1/ldap", "GetUserDetails", new String[] {"dn"}, new Object[] {BSF.getUser()});
    		response = sro.execute();
    		int[] roleNodes = XPath.getMatchingNodes(".//role", null, response);
    		for(int i=0; i<roleNodes.length; i++){
    			role_list += Node.getAttribute(roleNodes[i], "id", "") + ";";
    		}
    		return role_list;
    	} catch (Exception e){
    		throw new WSAppServerRunTimeException(e.getMessage());
    	} finally {
    		if(response != 0){
    			Node.delete(response);
    			response = 0;
    		}
    	}
    }
    
    public static BusObjectIterator<com.laiyifen.common.processconfig.COMMON_SIGN_CONFIG> getMyCOMMON_SIGN_CONFIGByFilters(String PROCESS_CODE, String STEP_CODE)
    {
        String queryText = "SELECT * FROM \"COMMON_SIGN_CONFIG\" WHERE \"PROCESS_CODE\" = :PROCESS_CODE AND \"STEP_CODE\" = :STEP_CODE";
    	QueryObject query = new QueryObject(queryText);
    	query.addParameter("PROCESS_CODE", "COMMON_SIGN_CONFIG.PROCESS_CODE", QueryObject.PARAM_STRING, PROCESS_CODE);
    	query.addParameter("STEP_CODE", "COMMON_SIGN_CONFIG.STEP_CODE", QueryObject.PARAM_STRING, STEP_CODE);
    	query.setResultClass(COMMON_SIGN_CONFIG.class);
        return query.getObjects();
    }
}
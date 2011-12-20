/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.common.core;

import java.util.UUID;

import com.cordys.cpc.bsf.busobject.BSF;
import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.busobject.QueryObject;
import com.cordys.cpc.bsf.busobject.exception.WSAppServerRunTimeException;
import com.cordys.cpc.bsf.soap.SOAPRequestObject;
import com.eibus.xml.nom.Node;
import com.eibus.xml.xpath.XPath;


public class COMMON_PROCESS_CONFIG extends COMMON_PROCESS_CONFIGBase
{
    public COMMON_PROCESS_CONFIG()
    {
        this((BusObjectConfig)null);
    }

    public COMMON_PROCESS_CONFIG(BusObjectConfig config)
    {
        super(config);
    }
protected void onBeforeInsert() {
		this.setID(UUID.randomUUID().toString());
	}
    public static BusObjectIterator<com.laiyifen.common.core.COMMON_PROCESS_CONFIG> getAllCOMMON_PROCESS_CONFIG()
    {
    	String queryText = "select * from \"COMMON_PROCESS_CONFIG\"";
        QueryObject query = new QueryObject(queryText);
        query.setResultClass(COMMON_PROCESS_CONFIG.class);
        return query.getObjects();
    }

    public static BusObjectIterator<com.laiyifen.common.core.COMMON_PROCESS_CONFIG> getMyCOMMON_PROCESS_CONFIG()
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
    	String queryText = "SELECT * FROM COMMON_PROCESS_CONFIG WHERE ROLE_NAME IN " + filter.toString();
        QueryObject query = new QueryObject(queryText);
        query.setResultClass(COMMON_PROCESS_CONFIG.class);
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

}

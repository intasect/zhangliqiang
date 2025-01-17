/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.bpc.core;

import java.io.UnsupportedEncodingException;

import com.cordys.cpc.bsf.busobject.BSF;
import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.busobject.QueryObject;
import com.eibus.xml.nom.Document;
import com.eibus.xml.nom.Node;
import com.eibus.xml.nom.XMLException;
import com.eibus.xml.xpath.NodeSet;
import com.eibus.xml.xpath.ResultNode;
import com.eibus.xml.xpath.XPath;
import com.eibus.xml.xpath.XPathMetaInfo;
import com.laiyifen.common.ConnectorManager;
import com.laiyifen.common.util.LaiYiFenCommunicationFailure;


public class BPC_BUDGET_COSTCENTER extends BPC_BUDGET_COSTCENTERBase
{
    public BPC_BUDGET_COSTCENTER()
    {
        this((BusObjectConfig)null);
    }

    public BPC_BUDGET_COSTCENTER(BusObjectConfig config)
    {
        super(config);
    }
    
    public static com.laiyifen.bpc.core.BPC_BUDGET_COSTCENTER getBpcBudgetCostcenterByCode(String costcenterCode)
    {
    	// TODO implement body
   	 	String queryText = "select * from \"BPC_BUDGET_COSTCENTER\" where \"costcenterCode\" = :costcenterCode";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("costcenterCode", "BPC_BUDGET_COSTCENTER.COSTCENTER_CODE", QueryObject.PARAM_STRING, costcenterCode);
        query.setResultClass(BPC_BUDGET_COSTCENTER.class);
        return (BPC_BUDGET_COSTCENTER)query.getObject();
    }
    
    public static BusObjectIterator<com.laiyifen.bpc.core.BPC_BUDGET_COSTCENTER> getAllBpcBudgetCostcenter()
    {
        // TODO implement body
    	 String queryText = "select * from BPC_BUDGET_COSTCENTER order by id";
         QueryObject query = new QueryObject(queryText);
         query.setResultClass(BPC_BUDGET_COSTCENTER.class);
         return query.getObjects();
    }
    
    public static void newnBpcBudgetCostcenterObject(String KOSTL,String BUKRS,String VERAK,String PRCTR,String LTEXT,String MCTXT,String KOSTL_NOZERO){
    	BPC_BUDGET_COSTCENTER BpcBudgetCostcenter = new BPC_BUDGET_COSTCENTER();
    	BpcBudgetCostcenter.setCOMPANY_CODE(BUKRS);
    	BpcBudgetCostcenter.setCOSTCENTER_CODE(KOSTL_NOZERO);
    	BpcBudgetCostcenter.setCOSTCENTER_NAME(LTEXT);
    	BpcBudgetCostcenter.setCOSTCENTER_NAME_S(MCTXT);
    	BpcBudgetCostcenter.setDEPARTMENT_CODE(VERAK);
    	BpcBudgetCostcenter.setPROFIT_CENTER(PRCTR);
    	BpcBudgetCostcenter.setEXT1(KOSTL);
    }
    
    public static boolean sycnBpcCostcenterFromBPC() throws Exception, XMLException{
    	boolean ret = false;
    	String organization = BSF.getOrganization();
  		String workspaceID = "__Organization Staging__";
  		Document doc = new Document();
  		int params = doc.parseString("<T_KOSTL></T_KOSTL>");
  		int response = ConnectorManager._callSoapMethod(organization,
  				SAPSyncService.BPC_SAP_NAMESPACE, "ZIFFI_GET_KOSTL", null,null,params);
  		String s1 = Node.writeToString(response, false);
  		try {
   			if (response > 0) { 
   				XPathMetaInfo info = new XPathMetaInfo();
   				info.addNamespaceBinding("", SAPSyncService.BPC_SAP_NAMESPACE);
   				XPath opath = XPath.getXPathInstance(".//T_KOSTL/item");
   				NodeSet oNodeSet = opath.selectNodeSet(response, info);
   				while (oNodeSet.hasNext()) {
					long iResultNode = oNodeSet.next();
					int iNode = ResultNode.getElementNode(iResultNode);
					String KOSTL = Node.getData(Node.getElement(iNode, "KOSTL"));
					String BUKRS = Node.getData(Node.getElement(iNode, "BUKRS"));
					String VERAK = Node.getData(Node.getElement(iNode, "VERAK"));
					String PRCTR = Node.getData(Node.getElement(iNode, "PRCTR"));
					String LTEXT = Node.getData(Node.getElement(iNode, "LTEXT"));
					String MCTXT = Node.getData(Node.getElement(iNode, "MCTXT"));
					String KOSTL_NOZERO = Node.getData(Node.getElement(iNode, "KOSTL_NOZERO"));
					if(KOSTL!=null)
					newnBpcBudgetCostcenterObject(KOSTL, BUKRS, VERAK, PRCTR, LTEXT, MCTXT,KOSTL_NOZERO);
   				}
   			}
   		} finally {
   			Node.delete(response);
   			response = 0;
   		}
		return true;
    }
}

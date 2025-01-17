/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.bpc.core;

import com.cordys.cpc.bsf.busobject.BSF;
import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.busobject.QueryObject;
import com.eibus.xml.nom.Document;
import com.eibus.xml.nom.Node;
import com.eibus.xml.xpath.NodeSet;
import com.eibus.xml.xpath.ResultNode;
import com.eibus.xml.xpath.XPath;
import com.eibus.xml.xpath.XPathMetaInfo;
import com.laiyifen.bpc.util.BpcCommonUtil;
import com.laiyifen.common.ConnectorManager;


public class CopyOfBPC_BUDGET_MONTH_20110928 extends BPC_BUDGETBase
{
	public static final String BPC_BUDGET_NAMESPACE = "http://tempuri.org/OSoft.Services.Webservice.MetaDataWSExt/MetaDataWSExt";
	
    public CopyOfBPC_BUDGET_MONTH_20110928()
    {
        this((BusObjectConfig)null);
    }

    public CopyOfBPC_BUDGET_MONTH_20110928(BusObjectConfig config)
    {
        super(config);
    }

    public static boolean UpdateBpcBudgetByXML(int NOMxml) throws Exception
    {
        return true;
    }

    
    public static boolean SycnBpcBudgetByMonth(String strP_CC, String strTIME, String strP_ACCT) throws Exception
    {
    	if(strP_CC==null || "".equals(strP_CC)){
    		BusObjectIterator<BPC_BUDGET_COSTCENTER> bpcBugetCostcenterObjects = BPC_BUDGET_COSTCENTER.getAllBpcBudgetCostcenter();
    		if (bpcBugetCostcenterObjects!=null){
    			while(bpcBugetCostcenterObjects.hasMoreElements()){
    				BPC_BUDGET_COSTCENTER bpcBugetCostcenterObject = bpcBugetCostcenterObjects.nextElement();
    				UpdateBpcBudgetByParamsByOne(bpcBugetCostcenterObject.getCOSTCENTER_CODE(),strTIME,strP_ACCT);
    			}
    		}
    	}else {
    		String[] arrP_cc = strP_CC.split(",");
    		for(int i=0;i<arrP_cc.length;i++){
    			UpdateBpcBudgetByParamsByOne(arrP_cc[i],strTIME,strP_ACCT);
    		}
    	}
        return true;
    }
    
    public static boolean SycnBpcBudgetByParams(String strP_CC, String strTIME, String strP_ACCT) throws Exception{
    	 // TODO implement body
    	if(strP_CC==null || "".equals(strP_CC)){
    		BusObjectIterator<BPC_BUDGET_COSTCENTER> bpcBugetCostcenterObjects = BPC_BUDGET_COSTCENTER.getAllBpcBudgetCostcenter();
    		if (bpcBugetCostcenterObjects!=null){
    			while(bpcBugetCostcenterObjects.hasMoreElements()){
    				BPC_BUDGET_COSTCENTER bpcBugetCostcenterObject = bpcBugetCostcenterObjects.nextElement();
    				SycnBpcBudgetByParamsByOne(bpcBugetCostcenterObject.getCOSTCENTER_CODE(),strTIME,strP_ACCT);
    			}
    		}
    	}else {
    		String[] arrP_cc = strP_CC.split(",");
    		for(int i=0;i<arrP_cc.length;i++){
    			SycnBpcBudgetByParamsByOne(arrP_cc[i],strTIME,strP_ACCT);
    		}
    	}
        return true;
    }
    
    public static boolean SycnBpcBudgetByParamsByOne(String strP_CC, String strTIME, String strP_ACCT) throws Exception{
   	 // TODO implement body
   	String organization = BSF.getOrganization();
		String workspaceID = "__Organization Staging__";
		
		if(strTIME==null || "".equals(strTIME)){
			strTIME = "2016.JAN,2016.FEB,2016.MAR,2016.APR,2016.MAY,2016.JUN,2016.JUL,2016.AUG,2016.SEP,2016.OCT,2016.NOV,2016.DEC";
		}
		if(strP_ACCT==null || "".equals(strP_ACCT)){
			strP_ACCT="ACCT_0066010306_SUM,ACCT_0066010600,ACCT_0066010700_SUM,ACCT_0066010800,ACCT_0066010901,ACCT_0066010902_SUM,ACCT_0066010903,ACCT_0066010904,ACCT_0066011001_1SUM,ACCT_0066011001_2,ACCT_0066011001_3SUM,ACCT_0066011002,ACCT_0066011100,ACCT_0066011201,ACCT_0066011202,ACCT_0066011203,ACCT_0066011204,ACCT_0066011301,ACCT_0066011302,ACCT_0066011400,ACCT_0066011501,ACCT_0066011502,ACCT_0066011503,ACCT_0066011600,ACCT_0066011701,ACCT_0066011702,ACCT_0066011703,ACCT_0066011800_SUM,ACCT_0066011900,ACCT_0066012000,ACCT_0066012100,ACCT_0066012201,ACCT_0066012202,ACCT_0066012203,ACCT_0066012204,ACCT_0066012205,ACCT_0066012300_SUM,ACCT_0066012501,ACCT_0066012502,ACCT_0066012503,ACCT_0066012600,ACCT_0066012700,ACCT_0066012802,ACCT_0066012900,ACCT_0066013002,ACCT_0066013003,ACCT_0066013004,ACCT_0066013005,ACCT_0066013100_SUM,ACCT_0066013500,ACCT_0066013700,ACCT_0066013900_SUM,ACCT_0066014000_SUM,ACCT_0066014200,ACCT_0066014100_SUM,ACCT_0066014300,ACCT_0066014400,ACCT_0066014500,ACCT_0066014600,ACCT_0066014700,ACCT_0066015100";
		}
		int params = getBudgetDimensionList(strP_CC,strTIME,strP_ACCT);
		int response = ConnectorManager._callSoapMethod(organization,
				BPC_BUDGET_NAMESPACE, "GetCellValueEx", null,
				null,params);
		try {
			int teamIDNode = 0; 
			if (response > 0) {
				XPathMetaInfo metaInfo = new XPathMetaInfo();
				metaInfo.addNamespaceBinding("", BPC_BUDGET_NAMESPACE);
				XPath opath = XPath.getXPathInstance(".//DATA/CORDS");

				NodeSet oNodeSet = opath.selectNodeSet(response, metaInfo);

				if (oNodeSet != null) {
					String temp = null;
					while (oNodeSet.hasNext()) {
						
						long iResultNode = oNodeSet.next();
						int iNode = ResultNode.getElementNode(iResultNode);
						temp = Node.getData(iNode);
						String[] tmpArray = temp.split("!");
						String year = tmpArray[9].split("\\.")[0];
						String month = BpcCommonUtil.changeMonthStringToNumber(tmpArray[9].split("\\.")[1]);
						insertBudget(year,month,tmpArray[6],tmpArray[5],tmpArray[11]);
						Node.delete(iNode);
						iNode = 0;
					}
				}
			}
		} finally {
			Node.delete(response);
			response = 0;
		}
       return true;
   	
   }
    
    public static boolean UpdateBpcBudgetByParamsByOne(String strP_CC, String strTIME, String strP_ACCT) throws Exception{
      	 // TODO implement body
      	String organization = BSF.getOrganization();
   		String workspaceID = "__Organization Staging__";
   		
   		if(strTIME==null || "".equals(strTIME)){
   			strTIME = "2016.JAN,2016.FEB,2016.MAR,2016.APR,2016.MAY,2016.JUN,2016.JUL,2016.AUG,2016.SEP,2016.OCT,2016.NOV,2016.DEC";
   		}
   		if(strP_ACCT==null || "".equals(strP_ACCT)){
   			strP_ACCT="ACCT_0066010306_SUM,ACCT_0066010600,ACCT_0066010700_SUM,ACCT_0066010800,ACCT_0066010901,ACCT_0066010902_SUM,ACCT_0066010903,ACCT_0066010904,ACCT_0066011001_1SUM,ACCT_0066011001_2,ACCT_0066011001_3SUM,ACCT_0066011002,ACCT_0066011100,ACCT_0066011201,ACCT_0066011202,ACCT_0066011203,ACCT_0066011204,ACCT_0066011301,ACCT_0066011302,ACCT_0066011400,ACCT_0066011501,ACCT_0066011502,ACCT_0066011503,ACCT_0066011600,ACCT_0066011701,ACCT_0066011702,ACCT_0066011703,ACCT_0066011800_SUM,ACCT_0066011900,ACCT_0066012000,ACCT_0066012100,ACCT_0066012201,ACCT_0066012202,ACCT_0066012203,ACCT_0066012204,ACCT_0066012205,ACCT_0066012300_SUM,ACCT_0066012501,ACCT_0066012502,ACCT_0066012503,ACCT_0066012600,ACCT_0066012700,ACCT_0066012802,ACCT_0066012900,ACCT_0066013002,ACCT_0066013003,ACCT_0066013004,ACCT_0066013005,ACCT_0066013100_SUM,ACCT_0066013500,ACCT_0066013700,ACCT_0066013900_SUM,ACCT_0066014000_SUM,ACCT_0066014200,ACCT_0066014100_SUM,ACCT_0066014300,ACCT_0066014400,ACCT_0066014500,ACCT_0066014600,ACCT_0066014700,ACCT_0066015100";
   		}
   		int params = getBudgetDimensionList(strP_CC,strTIME,strP_ACCT);
   		int response = ConnectorManager._callSoapMethod(organization,
   				BPC_BUDGET_NAMESPACE, "GetCellValueEx", null,
   				null,params);
   		try {
   			int teamIDNode = 0; 
   			if (response > 0) {
   				XPathMetaInfo metaInfo = new XPathMetaInfo();
   				metaInfo.addNamespaceBinding("", BPC_BUDGET_NAMESPACE);
   				XPath opath = XPath.getXPathInstance(".//DATA/CORDS");

   				NodeSet oNodeSet = opath.selectNodeSet(response, metaInfo);

   				if (oNodeSet != null) {
   					String temp = null;
   					while (oNodeSet.hasNext()) {
   						
   						long iResultNode = oNodeSet.next();
   						int iNode = ResultNode.getElementNode(iResultNode);
   						temp = Node.getData(iNode);
   						String[] tmpArray = temp.split("!");
   						String year = tmpArray[9].split("\\.")[0];
   						String month = BpcCommonUtil.changeMonthStringToNumber(tmpArray[9].split("\\.")[1]);
   						insertOrUpdateBudget(year,month,tmpArray[6],tmpArray[5],tmpArray[11]);
   						Node.delete(iNode);
   						iNode = 0;
   					}
   				}
   			}
   		} finally {
   			Node.delete(response);
   			response = 0;
   		}
          return true;
      	
      }

    public static void insertOrUpdateBudget(String year,String month,String costcenter ,String subject_code,String budget){
    	CopyOfBPC_BUDGET_MONTH_20110928 bpc_budget = getBpcBudgetInfoObject(year,month,costcenter,subject_code);
    	if(bpc_budget==null){
    		insertBudget(year,month,costcenter,subject_code,budget);
    	}else{
        	bpc_budget.setCOSTCENTER_CODE(costcenter);
        	bpc_budget.setBUDGET_STANDARD(Double.parseDouble(budget));
        	bpc_budget.setBUDGET_SUM(Double.parseDouble(budget));
        	bpc_budget.setBUDGET_FREESE(0.0);
        	bpc_budget.setBUDGET_RATIO(0.0);
        	bpc_budget.setBUDGET_SURPLUS(Double.parseDouble(budget));
        	bpc_budget.setBUDGET_USED(0.0);
        	bpc_budget.setSTATUS("1");
    	}
    }
    
    public static void insertBudget(String year,String month,String costcenter ,String subject_code,String budget){
    	CopyOfBPC_BUDGET_MONTH_20110928 bpc_budget = new CopyOfBPC_BUDGET_MONTH_20110928();
    	bpc_budget.setYEAR(year);
    	bpc_budget.setMONTH(month);
    	bpc_budget.setCOSTCENTER_CODE(costcenter);
    	bpc_budget.setSUBJECT_CODE(subject_code);
    	bpc_budget.setSUBJECT_CODE_SAP(subject_code.substring(7, 15));
    	bpc_budget.setBUDGET_STANDARD(Double.parseDouble(budget));
    	bpc_budget.setBUDGET_SUM(Double.parseDouble(budget));
    	bpc_budget.setBUDGET_FREESE(0.0);
    	bpc_budget.setBUDGET_RATIO(0.0);
    	bpc_budget.setBUDGET_SURPLUS(Double.parseDouble(budget));
    	bpc_budget.setBUDGET_USED(0.0);
    	bpc_budget.setSTATUS("1");
    }
	
	public static boolean UpdateBpcBudgetAmount(String operateType, String year, String month, String costcenter, String subjectCode, double amount)
    {
        // TODO implement body
		CopyOfBPC_BUDGET_MONTH_20110928 bpc_budget = getBpcBudgetInfoObject(year,month,costcenter,subjectCode);
		Double budget_sum = bpc_budget.getBUDGET_SUM();
		Double budget_used = bpc_budget.getBUDGET_USED();
		Double budget_freese = bpc_budget.getBUDGET_FREESE();
		Double budget_surplus =bpc_budget.getBUDGET_SURPLUS();
		Double budget_ratio = bpc_budget.getBUDGET_RATIO();
		//初始化数据
		if(budget_sum == null || budget_sum == 0.0){
			budget_sum = bpc_budget.getBUDGET_STANDARD();
		}
		if(budget_used == null){
			budget_used =  0.0;
		}
		if(budget_freese == null){
			budget_freese = 0.0 ;
		}
		if(budget_surplus == null || budget_surplus == 0.0){
			budget_surplus = budget_sum;
		}
		//流程提交，锁定预算。冻结增加，剩余减少，已用不变
		if("1".equals(operateType)){
			budget_freese = budget_freese +amount;
			budget_surplus = budget_surplus - amount;
			budget_used = budget_used;
		//流程驳回,作废，解除锁定。冻结减少，剩余增加，已用不变
		}else if("2".equals(operateType)){
			budget_freese = budget_freese - amount;
			budget_surplus = budget_surplus + amount;
			budget_used = budget_used;
		//流程结束，解除锁定。冻结减少，剩余不变,已用增加
		}else if("3".equals(operateType)){
			budget_freese = budget_freese - amount;
			budget_surplus = budget_surplus;
			budget_used = budget_used  + amount;
		//流程作废（已结束）。冻结不变，剩余增加，已用减少	
		}else if("4".equals(operateType)){
			budget_freese = budget_freese ;
			budget_surplus = budget_surplus + amount;
			budget_used = budget_used  - amount;
		}
		bpc_budget.setBUDGET_SUM(budget_sum);
		bpc_budget.setBUDGET_FREESE(budget_freese);
		bpc_budget.setBUDGET_USED(budget_used);
		bpc_budget.setBUDGET_SURPLUS(budget_surplus);
		bpc_budget.setBUDGET_RATIO(budget_ratio);
        return true;
    }
    
    public static int getBudgetDimensionList(String strP_CC,String strTIME,String strP_ACCT) throws Exception{
    	
    	String Param1 ="<APPSET>LYF_BPC</APPSET><APP>LYF_PLANNING</APP>"
        + "<DIMENSIONS>CATEGORY!CUSTOMER_VENDOR!ITEM!JOB_LVL!PRO_CATEGORY!P_ACCT!P_CC!P_DATASRC!RPTCURRENCY!TIME!MEASURES</DIMENSIONS>";
    	String Param2 =   "<DimensionFilters><DimensionCode>CATEGORY</DimensionCode><DimensionCodeValue>YZ00</DimensionCodeValue></DimensionFilters>"
    					+ "<DimensionFilters><DimensionCode>CUSTOMER_VENDOR</DimensionCode><DimensionCodeValue>V_NONE</DimensionCodeValue></DimensionFilters>"
    					+ "<DimensionFilters><DimensionCode>PRO_CATEGORY</DimensionCode><DimensionCodeValue>P_NONE</DimensionCodeValue></DimensionFilters>"
    					+ "<DimensionFilters><DimensionCode>ITEM</DimensionCode><DimensionCodeValue>DUMMY</DimensionCodeValue></DimensionFilters>"
    					+ "<DimensionFilters><DimensionCode>JOB_LVL</DimensionCode><DimensionCodeValue>DUMMY</DimensionCodeValue></DimensionFilters><DimensionFilters>"
    					+ "<DimensionCode>P_DATASRC</DimensionCode><DimensionCodeValue>D_NONE</DimensionCodeValue></DimensionFilters>";
    	
    	String root = "<param><Query>" +Param1 + "<DataFilters>" + Param2 + "</DataFilters></Query></param>";
		String item = "<DimensionFilters>" + "<DimensionCode/>" + "<DimensionCodeValue/>" + "</DimensionFilters>";
    	
		Document doc = new Document();
		int rootNode = doc.parseString(root);
		int queryNode = Node.getElement(rootNode, "Query");
		int listNode = Node.getElement(queryNode,"DataFilters");
		int itemNode = doc.parseString(item);
		
		int tempNode1 = Node.duplicate(itemNode);
		Node.setDataElement(tempNode1, "DimensionCode", "P_CC");
		Node.setDataElement(tempNode1, "DimensionCodeValue", strP_CC);
		Node.appendToChildren(tempNode1, listNode);
		
		int tempNode2 = Node.duplicate(itemNode);
		Node.setDataElement(tempNode2, "DimensionCode", "P_ACCT");
		Node.setDataElement(tempNode2, "DimensionCodeValue", strP_ACCT);
		Node.appendToChildren(tempNode2, listNode);
		
		int tempNode3 = Node.duplicate(itemNode);
		Node.setDataElement(tempNode3, "DimensionCode", "TIME");
		Node.setDataElement(tempNode3, "DimensionCodeValue", strTIME);
		Node.appendToChildren(tempNode3, listNode);
		
    	return rootNode;
    	
    }
    
    public static CopyOfBPC_BUDGET_MONTH_20110928 getBpcBudgetInfoObject(String year, String month, String costcenter, String subjectcode)
    {
    	String queryText = "select * from BPC_BUDGET where YEAR = '"+year+"' AND MONTH= '"+month+"' AND COSTCENTER_CODE= '"+costcenter+"' AND SUBJECT_CODE = '"+subjectcode+"'";
        QueryObject query = new QueryObject(queryText);
        query.setResultClass(CopyOfBPC_BUDGET_MONTH_20110928.class);
        return (CopyOfBPC_BUDGET_MONTH_20110928)query.getObject();
    }

    public static BusObjectIterator<CopyOfBPC_BUDGET_MONTH_20110928> getBpcBudgetInfoObjects(String year, String month, String costcenter, String objectcode)
    {
        // TODO implement body
        return null;
    }
}

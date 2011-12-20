package com.laiyifen.goods.core.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.cordys.cpc.bsf.busobject.BSF;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.busobject.DMLStatement;
import com.cordys.cpc.bsf.busobject.QueryObject;
import com.eibus.xml.nom.Node;
import com.eibus.xml.xpath.NodeSet;
import com.eibus.xml.xpath.ResultNode;
import com.eibus.xml.xpath.XPath;
import com.eibus.xml.xpath.XPathMetaInfo;
import com.laiyifen.common.ConnectorManager;
import com.laiyifen.goods.core.GROUPBUY_MATERIAL_INVENTO;
import com.laiyifen.goods.core.GROUPBUY_MATERIAL_PRICE;
import com.laiyifen.goods.core.GROUPBUY_ORDER_DETAIL;

public class GoodsSapUtil {
	
	public static final String SAP_NAMESPACE = "http://schemas.laiyifen.com/saprfc";
	
	/**
	 * 同步物料价格库存
	 * 
	 * @return 名称-数据的Mapping
	 * @throws Exception
	 */
	public static CollectionModeObject getSAPResult()
			throws Exception {
		
		CollectionModeObject collectionModeObject = new CollectionModeObject();
		
		ArrayList<GROUPBUY_MATERIAL_PRICE> groupbuyMaterialPriceList = new ArrayList<GROUPBUY_MATERIAL_PRICE>();
		ArrayList<GROUPBUY_MATERIAL_INVENTO> groupbuyMaterialInventoList = new ArrayList<GROUPBUY_MATERIAL_INVENTO>();
		
		String[] paramNamesIAR = {};
		Object[] paramValuesIAR = {};
		String organization = BSF.getOrganization();

		String requestString = "";
		String responseString = "";
		String message = "";
		boolean isSuccess = true;
		int response = 0;
		try {
			response = ConnectorManager._callSoapMethod(organization,
					GoodsSapUtil.SAP_NAMESPACE, "ZIFSD_TBPM_SENDDATA",
					paramNamesIAR, paramValuesIAR);
			responseString = Node.writeToString(response, true);
			XPathMetaInfo info = new XPathMetaInfo();
			info.addNamespaceBinding("", GoodsSapUtil.SAP_NAMESPACE);
			XPath opath = XPath.getXPathInstance(".//item");
			NodeSet oNodeSet = opath.selectNodeSet(response, info);
			
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");      
	    	String date = sDateFormat.format(new Date());
			
			if (oNodeSet != null) {

				String dmlDeleteText = "delete from GROUPBUY_MATERIAL_PRICE";
				DMLStatement dmlDelete = new DMLStatement(dmlDeleteText);
				dmlDelete.execute();
				
				String dmlDeleteText_t = "delete from GROUPBUY_MATERIAL_INFO";
				DMLStatement dmlDelete_t = new DMLStatement(dmlDeleteText_t);
				dmlDelete_t.execute();
				
//				StringBuffer strBuf = new StringBuffer();
//				strBuf.append("SELECT b.product_code,a.factory_shipment as ext1,sum(b.weight) as weight FROM " +
//						"GROUPBUY_ORDER a,GROUPBUY_ORDER_DETAIL b WHERE " +
//						"a.order_no = b.order_no and a.status = '1' GROUP BY b.product_code,a.factory_shipment");
//				QueryObject query = new QueryObject(strBuf.toString());
//				
//				query.setResultClass(GROUPBUY_ORDER_DETAIL.class);
//				BusObjectIterator<com.laiyifen.goods.core.GROUPBUY_ORDER_DETAIL> groupbuyOrderDetailObjs = query.getObjects();
//				ArrayList<GROUPBUY_ORDER_DETAIL> groupbuyOrderDetailObjList = new ArrayList<GROUPBUY_ORDER_DETAIL>();
//				if (groupbuyOrderDetailObjs != null) {
//					while (groupbuyOrderDetailObjs.hasMoreElements()) {
//						GROUPBUY_ORDER_DETAIL groupbuyOrderDetailObj = (GROUPBUY_ORDER_DETAIL)groupbuyOrderDetailObjs
//						.nextElement();
//						groupbuyOrderDetailObjList.add(groupbuyOrderDetailObj);
//					}
//				
//				}
				
				
				while (oNodeSet.hasNext()) {

					long iResultNode = oNodeSet.next();

					int iNode = ResultNode.getElementNode(iResultNode);
					String vkorgValue = Node.getDataElement(iNode, "VKORG", "");
					if (!vkorgValue.equals(""))
					{
						
						String dmlQueryText = "insert into GROUPBUY_MATERIAL_PRICE(PRODUCT_CODE,SALE_ORG_CODE,PRICE,CURRENCY,COND_UNIT,SALES_UNIT,LST_UPDATE) values('"+Node.getDataElement(iNode, "MATNR", "")+"','"+vkorgValue+"',"+Double.parseDouble(Node.getDataElement(iNode, "KBETR", ""))+",'"+Node.getDataElement(iNode, "KONWA", "")+"','"+Node.getDataElement(iNode, "KPEIN", "")+"','"+Node.getDataElement(iNode, "KMEIN", "")+"',to_date('"+date+"','yyyy-MM-dd'))"; 
				    	DMLStatement dml = new DMLStatement(dmlQueryText);
						dml.execute();
					}
					else
					{
//						String matnrTemp = Node.getDataElement(iNode, "MATNR", "");
//						String WERKSTemp = Node.getDataElement(iNode, "WERKS", "");
//						double labstTemp = Double.parseDouble(Node.getDataElement(iNode, "LABST", ""));
//						double weight = findArrayMaterialDetial(groupbuyOrderDetailObjList,matnrTemp,WERKSTemp);
//						if (weight != 0) {
//							weight = labstTemp - weight;
//						}
//						else
//						{
//							weight = labstTemp;
//						}
//						String dmlQueryText = "insert into GROUPBUY_MATERIAL_INVENTO(PRODUCT_CODE,PRODUCT_NAME,FACTORY,INVENTORY,OA_INVENTORY,LST_UPDATE) values('"+Node.getDataElement(iNode, "MATNR", "")+"','"+Node.getDataElement(iNode, "MAKTX", "")+"','"+Node.getDataElement(iNode, "WERKS", "")+"',"+Node.getDataElement(iNode, "LABST", "")+","+weight+",to_date('"+date+"','yyyy-MM-dd'))"; 
//				    	DMLStatement dml = new DMLStatement(dmlQueryText);
//						dml.execute();
						
						String dmlQueryText = "insert into GROUPBUY_MATERIAL_INFO(PRODUCT_CODE,PRODUCT_NAME,LST_UPDATE) values('"+Node.getDataElement(iNode, "MATNR", "")+"','"+Node.getDataElement(iNode, "MAKTX", "")+"',to_date('"+date+"','yyyy-MM-dd'))"; 
				    	DMLStatement dml = new DMLStatement(dmlQueryText);
						dml.execute();
						
						
					}
				}
			}
		} catch (Exception e) {
			isSuccess = false;
			message = e.getMessage();
			throw new Exception("Error occurred while sync DICT TYPE:"
					 + e.getMessage());
		} finally {
			if (response > 0 && Node.isValidNode(response)) {
				Node.delete(response);
				response = 0;
			}
		}
		return collectionModeObject;
	}
	
	/**
	 * 同步老客户数据
	 * 
	 * @return 名称-数据的Mapping
	 * @throws Exception
	 */
	public static void getSAPCustomer() throws Exception {
		
		String[] paramNamesIAR = {};
		Object[] paramValuesIAR = {};
		String organization = BSF.getOrganization();

		String requestString = "";
		String responseString = "";
		String message = "";
		boolean isSuccess = true;
		int response = 0;
		try {
			response = ConnectorManager._callSoapMethod(organization,
					GoodsSapUtil.SAP_NAMESPACE, "ZIFSD_TBPM_SENDKUNNR",
					paramNamesIAR, paramValuesIAR);
			responseString = Node.writeToString(response, true);
			XPathMetaInfo info = new XPathMetaInfo();
			info.addNamespaceBinding("", GoodsSapUtil.SAP_NAMESPACE);
			XPath opath = XPath.getXPathInstance(".//item");
			NodeSet oNodeSet = opath.selectNodeSet(response, info);
			
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");      
	    	String date = sDateFormat.format(new Date());
			
			if (oNodeSet != null) {

				String dmlDeleteText = "delete from GROUPBUY_CUSTOMER";
				DMLStatement dmlDelete = new DMLStatement(dmlDeleteText);
				dmlDelete.execute();
				
				while (oNodeSet.hasNext()) {

					long iResultNode = oNodeSet.next();

					int iNode = ResultNode.getElementNode(iResultNode);
						
						String dmlQueryText = "insert into GROUPBUY_CUSTOMER(CUSTOMER_CODE,CUSTOMER_NAME,CUSTOMER_CITY,CUSTOMER_ADDR,LST_UPDATE) values('"+Node.getDataElement(iNode, "KUNNR", "")+"','"+Node.getDataElement(iNode, "NAME1", "")+"','"+Node.getDataElement(iNode, "ORT01", "")+"','"+Node.getDataElement(iNode, "STRAS", "")+"',to_date('"+date+"','yyyy-MM-dd'))"; 
				    	DMLStatement dml = new DMLStatement(dmlQueryText);
						dml.execute();
				}
			}
		} catch (Exception e) {
			isSuccess = false;
			message = e.getMessage();
			throw new Exception("Error occurred while sync DICT TYPE:"
					 + e.getMessage());
		} finally {
			if (response > 0 && Node.isValidNode(response)) {
				Node.delete(response);
				response = 0;
			}
		}
		
	}
	private static double findArrayMaterialDetial(ArrayList<GROUPBUY_ORDER_DETAIL> para, String productCode, String  factory_shipment)
	{
		if (productCode.equals("000000000000010432"))
		{
			System.out.print(productCode);
		}
			
		for (GROUPBUY_ORDER_DETAIL groupbuyOrderDetailObj : para) {
			if (groupbuyOrderDetailObj.getPRODUCT_CODE().equals(productCode) && groupbuyOrderDetailObj.getEXT1().equals(factory_shipment))
			{
				return groupbuyOrderDetailObj.getWEIGHT();
			}
		}
		
		return 0;
	}
}

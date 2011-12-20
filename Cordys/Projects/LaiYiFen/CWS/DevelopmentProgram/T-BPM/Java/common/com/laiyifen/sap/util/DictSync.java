package com.laiyifen.sap.util;

import java.util.HashMap;
import java.util.Iterator;
import com.cordys.cpc.bsf.busobject.BSF;
import com.cordys.cpc.bsf.busobject.BusObject;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.busobject.QueryObject;
import com.eibus.xml.nom.Document;
import com.eibus.xml.nom.Node;
import com.eibus.xml.xpath.NodeSet;
import com.eibus.xml.xpath.ResultNode;
import com.eibus.xml.xpath.XPath;
import com.eibus.xml.xpath.XPathMetaInfo;
import com.laiyifen.common.ConnectorManager;
import com.laiyifen.common.core.DICTIONARY;
import com.laiyifen.common.util.AssignmentUtils;
import com.laiyifen.common.util.CommonUtil;
import com.laiyifen.common.util.LaiYiFenConstants;

/**
 * 同步SAP数据字典到Oracle数据库中的DICTIONARY表
 * 
 * @author Lee Chengye
 * 
 */
public class DictSync {
	/**
	 * 批量同步数据
	 * 
	 * @return 是否成功处理
	 * @throws Exception
	 */
	public static final String SAP_NAMESPACE = "http://schemas.laiyifen.com/saprfcs";
	public static boolean batchSync() throws Exception {
		boolean result = true;
		String type = null;
		try {
			BusObjectIterator<com.laiyifen.common.core.DICTIONARY> queryResponse = executeSelect("SELECT DICTIONARY_TYPE FROM DIC_INTERFACE_MAPPING WHERE (INTERFACENAME='ZIFSD_GET_FIELD' OR  INTERFACENAME='ZIFSD_TBPM_LIFNR_OUT') and DIC_SOURCE='SAP'");
			if (queryResponse == null)
				throw new Exception(
						"can not find data from TABLE DIC_INTERFACE_MAPPING");
			while (queryResponse.hasMoreElements()) {
				BusObject bo = queryResponse.nextElement();
				int queryResponseNode = bo._getObjectData();
				type = Node.getDataElement(queryResponseNode,
						"DICTIONARY_TYPE", "");
				sync(type);

			}
			return result;
		} catch (Exception e) {
			throw new Exception("DICTTYPE:" + type + ":" + e.getMessage());
		}
	}

	/**
	 * 根据字典类型同步数据字典
	 * 
	 * @param dictType
	 *            字典类型
	 * @return 是否成功同步
	 * @throws Exception
	 */

	public static boolean sync(String dictType) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		if((dictType.equals("9001")||dictType.equals("9007"))){
			 map = getSAPResultProvider(dictType);
		}else{
			 map = getSAPResult(dictType);
		}	
		Iterator<String> it = map.keySet().iterator();
		String key = null;
		String result = null;
		deleteDict(dictType);
		while (it.hasNext()) {
			key = (String) it.next();
			result = (String) map.get(key);
			insertDict(dictType, key, result);
		}
		return true;
	}

	/**
	 * 根据字典类型获取SAP字典结果
	 * 
	 * @param dictType
	 *            字典类型
	 * @return 名称-数据的Mapping
	 * @throws Exception
	 */
	private static HashMap<String, String> getSAPResult(String dictType)
			throws Exception {

		HashMap<String, String> map = new HashMap<String, String>();
		String[] paramNamesIAR = { "FIELDNAME_IN" };
		Object[] paramValuesIAR = { dictType };
		String organization = BSF.getOrganization();

		String requestString = "";
		String responseString = "";
		String message = "";
		boolean isSuccess = true;
		int response = 0;
		try {
			response = ConnectorManager._callSoapMethod(organization,
					LaiYiFenConstants.TEMP_NAMESPACE, "ZIFSD_GET_FIELD",
					paramNamesIAR, paramValuesIAR);
			responseString = Node.writeToString(response, true);
			XPathMetaInfo info = new XPathMetaInfo();
			info.addNamespaceBinding("", LaiYiFenConstants.TEMP_NAMESPACE);
			XPath opath = XPath.getXPathInstance(".//item");
			NodeSet oNodeSet = opath.selectNodeSet(response, info);
			if (oNodeSet != null) {

				while (oNodeSet.hasNext()) {

					// this is a handle to theXmlResultNode object in the native
					// layer
					long iResultNode = oNodeSet.next();

					int iNode = ResultNode.getElementNode(iResultNode);
					String value = Node.getDataElement(iNode, "FIELDVALUE", "");
					String desc = Node.getDataElement(iNode, "FIELDDESCRIBE",
							"");
					// insertDict(dictType, value, desc);
					map.put(value, desc);
				}
			}
		} catch (Exception e) {
			isSuccess = false;
			message = e.getMessage();
			throw new Exception("Error occurred while sync DICT TYPE:"
					+ dictType + e.getMessage());
		} finally {
			if (response > 0 && Node.isValidNode(response)) {
				Node.delete(response);
				response = 0;
			}
			/*
			BSF.getObjectManager().startTransaction("common_log_trans_dictsync");
			AppLogger logger = AppLogger.getAppLogger(DictSync.class);
			String level = "";
			if (isSuccess)
				level = "INFO";
			else
				level = "ERROR";
			logger.log(level, "", "", BSF.getUser(), "", "ZIFSD_GET_FIELD",
					requestString, responseString, "", message);
			BSF.getObjectManager().commitTransaction("common_log_trans_dictsync", true);
             */
		}
		return map;
	}
	
	
	/**
	 * 根据字典类型获取SAP字典结果
	 * 
	 * @param dictType
	 *            字典类型
	 * @return 名称-数据的Mapping
	 * @throws Exception
	 */
	private static HashMap<String, String> getSAPResultProvider(String dictType)
			throws Exception { 
		String org = ConnectorManager.getDefaultOrg();// BSF.getOrganization();
		HashMap<String, String> map = new HashMap<String, String>();
		String root = "<ZIFSD_TBPM_LIFNR_OUT><ITB_IN><item><KTOKK>"+dictType+"</KTOKK><LIFNR></LIFNR><NAME1></NAME1></item></ITB_IN></ZIFSD_TBPM_LIFNR_OUT>";
		Document doc = CommonUtil.getDefaultDocument();
		int rootNode = doc.parseString(root);	
		//String requestStr = null;
		//String responseStr = null;
		int response = 0;
		String message = "";
		boolean isSuccess = true;
		try {
		//requestStr = Node.writeToString(rootNode, true);
		response = ConnectorManager._callSoapMethod(org,
				SAP_NAMESPACE, "ZIFSD_TBPM_LIFNR_OUT",
				null, null, rootNode);
		//responseStr = Node.writeToString(response, true);
		XPathMetaInfo info = new XPathMetaInfo();
		info.addNamespaceBinding("", LaiYiFenConstants.TEMP_NAMESPACE);
		XPath opath = XPath.getXPathInstance(".//item");
		NodeSet oNodeSet = opath.selectNodeSet(response, info);
		if (oNodeSet != null) {
			while (oNodeSet.hasNext()) {
						// this is a handle to theXmlResultNode object in the native
						// layer
				long iResultNode = oNodeSet.next();
				int iNode = ResultNode.getElementNode(iResultNode);
				String lifnr = Node.getDataElement(iNode, "LIFNR", "");
				String name1 = Node.getDataElement(iNode, "NAME1","");
				// insertDict(dictType, value, desc);
				if(lifnr != null && !"".equals(lifnr.trim())){
					map.put(lifnr, name1);
				}
			}
		}
	}
		 catch (Exception e) {
			isSuccess = false;
			message = e.getMessage();
			throw new Exception("Error occurred while sync DICT TYPE:"
					+ dictType + e.getMessage());
		} finally {
			if (response > 0 && Node.isValidNode(response)) {
				Node.delete(response);
				response = 0;
			}
		}
		return map;
	}

	/**
	 * 根据字典类型清除数据库中的字典数据
	 * 
	 * @param dictType
	 *            字典类型
	 */
	public static void deleteDict(String dictType) {
		BusObjectIterator<DICTIONARY> it = DICTIONARY
				.getDictionaryObjectsByType(dictType);
		DICTIONARY dict = null;
		if (it != null)
			while (it.hasMoreElements()) {
				dict = (DICTIONARY) it.nextElement();
				dict.delete();
			}
	}

	/**
	 * 插入数据字典表
	 * 
	 * @param dictType
	 *            字典类型
	 * @param value
	 *            值
	 * @param desc
	 *            描述
	 */
	public static void insertDict(String dictType, String value, String desc) {
		DICTIONARY dict = new DICTIONARY();
		dict.setCODE(value);
		dict.setDESCRIPTION(desc);
		dict.setDICTIONARY_TYPE(dictType);
		dict.setID(AssignmentUtils.getSeqNumber("DICT_ID"));		
	}

	/**
	 * 执行SQL语句，返回BusObject对象
	 * 
	 * @param QueryText
	 * @return DICTIONARY列表
	 */
	public static BusObjectIterator<com.laiyifen.common.core.DICTIONARY> executeSelect(
			String QueryText) {
		QueryObject query = new QueryObject(QueryText);
		// TODO - Check that can we remove this setResultClass
		query.setResultClass(DICTIONARY.class);
		return query.getObjects();
	}

}
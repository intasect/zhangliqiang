package com.laiyifen.sap.common;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.cordys.cpc.bsf.busobject.BSF;
import com.cordys.cpc.bsf.busobject.BusObject;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.eibus.xml.nom.Node;
import com.laiyifen.common.ConnectorManager;
import com.laiyifen.common.core.RFC_FIELD_MAPPING;
import com.laiyifen.common.core.RFC_INFORMATION;
import com.laiyifen.common.logging.AppLogger;
import com.laiyifen.common.util.LaiYiFenConstants;
import com.laiyifen.common.util.XPathUtil;

public class SAPWrapper {
	private static String currentOrganizationDN = "";

	/**
	 * 将UTC格式日期字符串转换为20111011格式
	 * 
	 * @param originalDate
	 *            UTC格式日期字符串
	 * @return yyyyMMdd格式化后的日期
	 */
	private static String convertUTCDate(String originalDate) {
		String result = "";
		String srcDate = "";
		/*
		 * SimpleDateFormat utfFormat = new SimpleDateFormat(
		 * "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		 */
		if (originalDate != null)
			srcDate = originalDate.substring(0, 10);
		SimpleDateFormat utfFormat = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat shortForamt = new SimpleDateFormat("yyyyMMdd");
		try {
			result = shortForamt.format(utfFormat.parse(srcDate));
		} catch (Exception e) {
		}
		return result;

	}
	
	/**
	 * 将UTC格式日期字符串转换为20110101格式,当前年份+"0101"
	 * 
	 * @param originalDate
	 *            UTC格式日期字符串
	 * @return yyyyMMdd格式化后的日期
	 */
	private static String convertUTCDate2(String originalDate) {
		String result = "";
		String srcDate = "";

		if (originalDate != null)
			srcDate = originalDate.substring(0, 4);
		
		if("".equals(srcDate)){
			SimpleDateFormat shortForamt = new SimpleDateFormat("yyyy");
			srcDate = shortForamt.format(new Date());
		}
		
		result = srcDate + "0101";
		
		return result;

	}

	public static int invokeAndLogSAPTransactions(int operationType,
			String operationCode, String batchNumber, String shopId)
			throws Exception {
		String request = null;
		String response = null;
		String errorMsg = null;
		int SAPResponse = 0;
		int inputParametersNode = 0;
		boolean isSuccess = true;
		try {

			currentOrganizationDN = ConnectorManager.getDefaultOrg();// WsAppsManager.getOrganizationDN();

			RFC_INFORMATION rfcInformationObject = RFC_INFORMATION
					.getRfcInformationObject(operationCode);
			String inputParameters = rfcInformationObject
					.getRFC_INPUT_PARAMETERS();
			String synFlag = rfcInformationObject.getSYNFLAG();
			inputParametersNode = Utilities._parseString(inputParameters);

			BusObjectIterator<RFC_FIELD_MAPPING> rfcTableMappings = RFC_FIELD_MAPPING
					.getTableMappings(operationCode);

			while (rfcTableMappings.hasMoreElements()) {
				RFC_FIELD_MAPPING rfcTableMapping = rfcTableMappings
						.nextElement();

				String tableName = rfcTableMapping.getSELECT_TABLE();
				int mappingCount = rfcTableMapping.getIntProperty("MAPPINGS");

				if (mappingCount == 1) {
					BusObjectIterator<RFC_FIELD_MAPPING> rfcSingleFieldMappings = RFC_FIELD_MAPPING
							.getFieldMappingsForTable(operationCode, tableName);
					RFC_FIELD_MAPPING rfcSingleFieldMapping = rfcSingleFieldMappings
							.nextElement();
					inputParametersNode = mapInputParameters(
							rfcSingleFieldMapping, inputParametersNode, shopId);
				} else {
					SAPWrapper.constructInputParams(operationCode, shopId,
							batchNumber, tableName, inputParametersNode);
				}
			}

			// begin to invoke SAP Function
			request = Node.writeToString(inputParametersNode, true);
			String[] inputParams = null;
			Object[] inputValues = null;
			if("1".equals(synFlag)){
				ConnectorManager._callSoapMethod(
						currentOrganizationDN, LaiYiFenConstants.TEMP_NAMESPACE,
						operationCode, inputParams, inputValues,
						inputParametersNode);
				return 0;
			} else {
				SAPResponse = ConnectorManager._callSoapMethod(
						currentOrganizationDN, LaiYiFenConstants.TEMP_NAMESPACE,
						operationCode, inputParams, inputValues,
						inputParametersNode);
			}
			String batchNumberFromSAP = batchNumber;
			String errorCodeFromSAP = "";
			String errorDetailsFromSAP = "";

			response = Node.writeToString(SAPResponse, true);
			String responseNodeString = "<responsedata/>";
			int responseNode = 0;
			responseNode = Utilities._parseString(responseNodeString);
			boolean sapError = false;
			if (SAPResponse > 0) {
				try {
					/*
					 * int returnItem = XPathUtil.firstMatch(".//RETURN/item",
					 * SAPResponse); returnType =
					 * Node.getDataElement(returnItem, "TYPE", "");
					 * 
					 * if (!"S".equals(returnType)) { errorCodeFromSAP =
					 * Node.getDataElement(returnItem, "NUMBER", "");
					 * errorDetailsFromSAP = Node.getDataElement(returnItem,
					 * "MESSAGE", ""); }
					 */
					int returnTypeNode = XPathUtil.firstMatch(
							".//RETURN/item[TYPE!='S']", SAPResponse);

					if (returnTypeNode != 0 && Node.isValidNode(returnTypeNode)) {
						errorCodeFromSAP = Node.getDataElement(returnTypeNode,
								"NUMBER", "");
						errorDetailsFromSAP = Node.getDataElement(
								returnTypeNode, "MESSAGE", "");
						sapError = true;
					}

				} catch (Exception e) {
					errorDetailsFromSAP = "parsing SAP Result error";
					sapError = true;
				}
				if (sapError) {
					throw new Exception("SAP Error:" + errorDetailsFromSAP);
				}

				Node.setDataElement(responseNode, "batchNumber",
						batchNumberFromSAP);
				Node.setDataElement(responseNode, "shopID", shopId);
				Node.setDataElement(responseNode, "sapErrorCode",
						errorCodeFromSAP);
				Node.setDataElement(responseNode, "sapErrorDetails",
						errorDetailsFromSAP);
				int SAPReturnPart = XPathUtil.firstMatch(".//RETURN",
						SAPResponse);
				if (SAPReturnPart > 0) {
					SAPReturnPart = Node.duplicate(SAPReturnPart);
					Node.appendToChildren(SAPReturnPart, responseNode);
				}
			}

			return responseNode;
		} catch (Exception e) {
			isSuccess = false;
			errorMsg = getDetailError(e);
			throw e;
		} finally {
			if (SAPResponse > 0 && Node.isValidNode(SAPResponse)) {
				Node.delete(SAPResponse);
				SAPResponse = 0;
			}
			if (inputParametersNode > 0
					&& Node.isValidNode(inputParametersNode)) {
				Node.delete(inputParametersNode);
				inputParametersNode = 0;
			}
			BSF.getObjectManager().startTransaction("common_log_trans");
			AppLogger logger = AppLogger.getAppLogger(SAPWrapper.class);
			String level = "";
			if (isSuccess) {
				level = "INFO";
			} else {
				level = "ERROR";
			}
			logger.log(level, "", "", BSF.getUser(), shopId, operationCode,
					request, response, batchNumber, errorMsg);
			BSF.getObjectManager().commitTransaction("common_log_trans", true);
		}
	}

	/**
	 * get detail error information from exception
	 * 
	 * @param e
	 *            Exception
	 * @return detail exception string
	 */
	private static String getDetailError(Exception e) {
		String error = "";
		if (e == null)
			return error;
		PrintWriter pw = null;
		StringWriter sw = new StringWriter();
		pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		if (pw != null) {
			error = sw.toString();
		}
		return error;
	}

	private static int mapInputParameters(BusObject inputMapping,
			int inputParametersNode, String ShopID) {

		RFC_FIELD_MAPPING rfcMapping = (RFC_FIELD_MAPPING) inputMapping;

		String whereClause = rfcMapping.getCONDITION();
		String tableInfo = rfcMapping.getSELECT_TABLE();
		String sourcePath = rfcMapping.getSELECT_FIELD();
		String targetXpath = rfcMapping.getTARGET_XPATH_FIELD();
		String conversionData = rfcMapping.getCONVERSION();

		String finalQuery = null;
		if (!"".equals(whereClause) && whereClause != null) {
			String[] splittedString = whereClause.split("=:");
			finalQuery = "SELECT " + sourcePath + " FROM " + tableInfo
					+ " WHERE " + splittedString[0] + " = '" + ShopID + "'";
		} else
			finalQuery = "SELECT " + sourcePath + " FROM " + tableInfo;
		// For same table, run the query once and get data
		BusObject queryResponse = RFC_INFORMATION.executeSelect(finalQuery);

		if (queryResponse != null) {
			int queryResponseNode = queryResponse._getObjectData();
			String responseData = Node.getData(XPathUtil.firstMatch(".//"
					+ sourcePath, queryResponseNode));

			if (conversionData != null && "2".equals(conversionData.trim()))
				if (responseData != null && responseData.length() > 0)
					responseData = convertUTCDate(responseData);

			if (conversionData != null && "3".equals(conversionData.trim()))
				if (responseData != null && responseData.length() > 0)
					responseData = convertUTCDate2(responseData);
			
			int targetNode = XPathUtil.firstMatch(".//" + targetXpath,
					inputParametersNode);
			Node.setDataElement(targetNode, "", responseData);
		}
		// TODO - check for memory leaks
		return inputParametersNode;
	}

	private static void constructInputParams(String operationCode,
			String shopId, String batchNumber, String tableName,
			int inputParamsNode) throws Exception {
		BusObjectIterator<RFC_FIELD_MAPPING> rfcMultipleFieldMappings = RFC_FIELD_MAPPING
				.getFieldMappingsForTable(operationCode, tableName);
		String whereClause = "";
		String sourceFieldList = "";
		String targetFieldList = "";
		String occList = "";
		String convertList = "";

		while (rfcMultipleFieldMappings.hasMoreElements()) {
			RFC_FIELD_MAPPING rfcField = rfcMultipleFieldMappings.nextElement();
			if ("".equals(sourceFieldList) && "".equals(targetFieldList)) {
				sourceFieldList = rfcField.getSELECT_FIELD();
				targetFieldList = rfcField.getTARGET_XPATH_FIELD();
				occList = rfcField.getOCCURRENCE();
				convertList = rfcField.getCONVERSION();
			} else {
				sourceFieldList = sourceFieldList + ","
						+ rfcField.getSELECT_FIELD();
				targetFieldList = targetFieldList + ","
						+ rfcField.getTARGET_XPATH_FIELD();
				occList = occList + "," + rfcField.getOCCURRENCE();
				convertList = convertList + "," + rfcField.getCONVERSION();
			}
			// TODO this is not required to be called all the times.
			// Try
			// to restrict to only once. Only when condition is same
			whereClause = rfcField.getCONDITION();
		}

		String[] splittedString = null;
		String finalQuery = null;
		if (!"".equals(whereClause) && whereClause != null) {
			splittedString = whereClause.split("=:");
			finalQuery = "SELECT " + sourceFieldList + " FROM " + tableName
					+ " WHERE " + splittedString[0] + " = '" + shopId + "'";
		} else
			finalQuery = "SELECT " + sourceFieldList + " FROM " + tableName;

		// BusObject queryResponse = RFC_INFORMATION.executeSelect(finalQuery);
		// BusObject s = RFC_INFORMATION.executeSQL(finalQuery);
		BusObjectIterator<BusObject> boi = RFC_INFORMATION
				.executeSQL(finalQuery);

		int queryResponseNode = 0;// s._getObjectData();
		if (boi == null)
			throw new Exception("can not find data from TABLE " + tableName);
		ArrayList<Integer> list = new ArrayList<Integer>();
		while (boi.hasMoreElements()) {
			BusObject bo = boi.nextElement();
			list.add(bo._getObjectData());
		}
		Object[] boArray = list.toArray();
		String[] sourceFieldArray = sourceFieldList.split(",");
		String[] targetFieldArray = targetFieldList.split(",");
		String[] occArray = occList.split(",");
		String[] convertArray = convertList.split(",");

		int fieldNode = 0;// XPathUtil.firstMatch(".//item", inputParamsNode);
		int itemNode = 0;
		int fieldLengh = sourceFieldArray.length;

		for (int fieldCount = 0; fieldCount < fieldLengh; fieldCount++) {
			String arr[] = sourceFieldArray[fieldCount].split(" ");
			String srcField = null;
			int arrLen = arr.length;
			if (arrLen == 2)
				srcField = arr[1];
			else
				srcField = arr[0];

			String targetNodeName = Node.getName(XPathUtil.firstMatch(".//"
					+ targetFieldArray[fieldCount], inputParamsNode));

			if ("*".equals(occArray[fieldCount])) {

				for (int boPosition = 0; boPosition < boArray.length; boPosition++) {
					queryResponseNode = (Integer) boArray[boPosition];
					String srcData = Node.getData(XPathUtil.firstMatch(".//"
							+ srcField, queryResponseNode));
					int fieldNodeArray[] = XPathUtil.findMatches(".//"
							+ targetFieldArray[fieldCount], inputParamsNode);
					fieldNode = 0;
					if (boPosition < fieldNodeArray.length)
						fieldNode = fieldNodeArray[boPosition];

					if (fieldNode == 0 || !Node.isValidNode(fieldNode)) {
						fieldNode = XPathUtil
								.firstMatch(".//"
										+ targetFieldArray[fieldCount],
										inputParamsNode);

						itemNode = Node.getParent(fieldNode);

						int parentNode = Node.getParent(itemNode);

						itemNode = Node.duplicate(itemNode);
						Node.appendToChildren(itemNode, parentNode);

					} else
						itemNode = Node.getParent(fieldNode);
					if (convertArray[fieldCount] != null
							&& "2".equals(convertArray[fieldCount].trim()))
						if (srcData != null && srcData.length() > 0)
							srcData = convertUTCDate(srcData);
					
					if (convertArray[fieldCount] != null
							&& "3".equals(convertArray[fieldCount].trim()))
						if (srcData != null && srcData.length() > 0)
							srcData = convertUTCDate2(srcData);
					
					Node.setDataElement(itemNode, targetNodeName, srcData);
				}
			} else {

				if (boArray != null && boArray.length > 0)
					queryResponseNode = (Integer) boArray[0];
				fieldNode = XPathUtil.firstMatch(".//"
						+ targetFieldArray[fieldCount], inputParamsNode);
				itemNode = Node.getParent(fieldNode);

				String responseData = Node.getData(XPathUtil.firstMatch(".//"
						+ srcField, queryResponseNode));
				if (convertArray[fieldCount] != null
						&& "2".equals(convertArray[fieldCount].trim()))
					if (responseData != null && responseData.length() > 0)
						responseData = convertUTCDate(responseData);
				
				if (convertArray[fieldCount] != null
						&& "3".equals(convertArray[fieldCount].trim()))
					if (responseData != null && responseData.length() > 0)
						responseData = convertUTCDate2(responseData);
				
				Node.setDataElement(itemNode, targetNodeName, responseData);

			}
		}

		int pnoNode = XPathUtil.firstMatch(".//PNO", inputParamsNode);
		if (pnoNode > 0 && Node.isValidNode(pnoNode)) {
			Node.setDataElement(pnoNode, "", batchNumber);
		}
	} 
}

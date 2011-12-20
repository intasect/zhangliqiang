package com.laiyifen.hisense;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.cordys.cpc.bsf.busobject.BSF;
import com.cordys.cpc.bsf.busobject.BusObject;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.busobject.QueryObject;
import com.eibus.xml.nom.Document;
import com.eibus.xml.nom.Node;
import com.eibus.xml.xpath.XPath;
import com.laiyifen.common.ConnectorManager;
import com.laiyifen.common.core.DICTIONARY;
import com.laiyifen.common.logging.AppLogger;
import com.laiyifen.common.util.CommonUtil;
import com.laiyifen.common.util.LaiYiFenConstants;

/**
 * 海信接口
 * 
 * @author LeeChengye
 * 
 */
public class HisenseWrapper {
	private static AppLogger logger = AppLogger
			.getAppLogger(HisenseWrapper.class);

	/**
	 * 设置卡类型
	 * 
	 * @param shopId
	 *            shop Id
	 * @return 是否完成处理
	 * @throws Exception
	 */
	public static boolean setCardTypeOrg(String shopId) throws Exception {
		int params = 0;
		int response = 0;
		String requestStr = null;
		String responseStr = null;
		String logLevel = "INFO";
		String errorMsg = "";
		try {
			String org = ConnectorManager.getDefaultOrg();// BSF.getOrganization();
			BusObject bo = executeSingleSelect("SELECT CORP_CODE,SAP_NO FROM SHOP_MASTER WHERE SHOP_ID='"
					+ shopId + "'");
			if (bo == null) {
				throw new Exception("SHOP_MASTER表无该shop Id数据");
			}

			int queryResponseNode = bo._getObjectData();
			String corpCode = Node.getDataElement(queryResponseNode,
					"CORP_CODE", "");
			String sapNo = Node.getDataElement(queryResponseNode, "SAP_NO", "");
			params = getCardTypeList(corpCode, sapNo);
			requestStr = Node.writeToString(params, true);
			response = ConnectorManager._callSoapMethod(org,
					LaiYiFenConstants.LYE_DEFAULT_NAMESPACE, "SendCardTypeOrg",
					null, null, params);

			responseStr = Node.writeToString(response, true);

			XPath opath = XPath.getXPathInstance(".//*[local-name()='Remark']");
			int resultNode = opath.firstMatch(response, null);
			String remark = Node.getDataWithDefault(resultNode, "0");
			opath = XPath.getXPathInstance(".//*[local-name()='Message']");
			int messageNode = opath.firstMatch(response, null);

			String message = Node.getDataWithDefault(messageNode, "");
			if (!"0".equals(remark))
				throw new Exception("Hisense Interface invoked error:"
						+ message);

			return true;
		} catch (Exception e) {
			logLevel = "ERROR";
			errorMsg = getDetailError(e);
			throw e;
		} finally {
			if (Node.isValidNode(params)) {
				Node.delete(params);
				params = 0;
			}
			if (Node.isValidNode(response)) {
				Node.delete(response);
				response = 0;
			}

			BSF.getObjectManager().startTransaction("common_log_trans");
			logger.log(logLevel, "", "", BSF.getUser(), shopId,
					"SendCardTypeOrg", requestStr, responseStr, "N/A", errorMsg);
			BSF.getObjectManager().commitTransaction("common_log_trans", true);
		}
	}

	/**
	 * 设置交易客户端
	 * 
	 * @param shopId
	 *            shopId
	 * @return 海信返回结果
	 * @throws Exception
	 */
	public static int setTradeClient(String shopId) throws Exception {
		// AssignmentUtils.initBSF();
		String org = ConnectorManager.getDefaultOrg();// BSF.getOrganization();
		BusObject bo = executeSingleSelect("SELECT * FROM SHOP_EQUACC WHERE SHOP_ID='"
				+ shopId + "'");
		if (bo == null)
			return 0;
		int queryResponseNode = bo._getObjectData();
		String elect_scales_no = Node.getDataElement(queryResponseNode,
				"ELECT_SCALES_NO", "0");
		String isuse_balance = Node.getDataElement(queryResponseNode,
				"ISUSE_BALANCE", "0");
		if ("".equals(elect_scales_no))
			elect_scales_no = "0";
		BusObject shopMaster = executeSingleSelect("SELECT CORP_CODE,SAP_NO FROM SHOP_MASTER WHERE SHOP_ID='"
				+ shopId + "'");
		if (shopMaster == null) {
			throw new Exception("SHOP_MASTER表无该shop Id数据");
		}

		int shopMasterResponseNode = shopMaster._getObjectData();
		String corpCode = Node.getDataElement(shopMasterResponseNode,
				"CORP_CODE", "");
		String sapNo = Node
				.getDataElement(shopMasterResponseNode, "SAP_NO", "");
		int params = 0;
		int response = 0;
		int result = 0;
		String logLevel = "INFO";
		String requestStr = null;
		String responseStr = null;
		String errorMsg = "";
		try {
			params = prepareSetTradeClientParams(corpCode, sapNo,
					Integer.valueOf(elect_scales_no), isuse_balance);
			requestStr = Node.writeToString(params, true);
			response = ConnectorManager._callSoapMethod(org,
					LaiYiFenConstants.LYE_DEFAULT_NAMESPACE, "SendTradeClient",
					null, null, params);
			responseStr = Node.writeToString(response, true);
			// AssignmentUtils.release();
			XPath opath = XPath.getXPathInstance(".//*[local-name()='Remark']");
			int resultNode = opath.firstMatch(response, null);
			String remark = Node.getDataWithDefault(resultNode, "0");
			opath = XPath.getXPathInstance(".//*[local-name()='Message']");
			int messageNode = opath.firstMatch(response, null);

			String message = Node.getDataWithDefault(messageNode, "");
			if (!"0".equals(remark))
				throw new Exception("Hisense Interface invoked error:"
						+ message);
			result = response;
			response = 0;
			return result;
		} catch (Exception e) {
			logLevel = "ERROR";
			errorMsg = getDetailError(e);
			throw e;
		} finally {
			if (Node.isValidNode(params)) {
				Node.delete(params);
				params = 0;
			}
			if (Node.isValidNode(response)) {
				Node.delete(response);
				response = 0;
			}

			BSF.getObjectManager().startTransaction("common_log_trans");
			logger.log(logLevel, "", "", BSF.getUser(), shopId,
					"setTradeClient", requestStr, responseStr, "N/A", errorMsg);
			BSF.getObjectManager().commitTransaction("common_log_trans", true);
		}
	}

	/**
	 * 准备设置交易客户端时的参数数据
	 * 
	 * @param shopId
	 *            shop Id
	 * @param elect_scales_no
	 *            电子秤数量
	 * @param issue_balance
	 *            是否电子秤，1是,0否
	 * @return 参数的XML结果集
	 * @throws Exception
	 */
	private static int prepareSetTradeClientParams(String corpCode,
			String sapNo, int elect_scales_no, String issue_balance)
			throws Exception {
		String root = "<SendTradeClient><TradeClientList></TradeClientList></SendTradeClient>";
		String item = "<TradeClient>" + "<ClientCode></ClientCode>"
				+ "<Location></Location>" + "<Status></Status>"
				+ "<OrgCode></OrgCode>" + "<PosNo></PosNo>"
				+ "<IPAddress></IPAddress>" + "<VerifyKind></VerifyKind>"
				+ "<VerifyInfo></VerifyInfo>" + "<Remark></Remark>"
				+ "<ClientType></ClientType>" + "</TradeClient>";
		Document doc = CommonUtil.getDefaultDocument();
		int rootNode = 0;
		int tradeClient = 0;
		int result = 0;
		try {
			rootNode = doc.parseString(root);
			int listNode = Node.getElement(rootNode, "TradeClientList");
			tradeClient = doc.parseString(item);
			int itemNode = 0;
			if ("1".equals(issue_balance) || "是".equals(issue_balance)) {
				for (int i = 0; i < elect_scales_no; i++) {
					itemNode = Node.duplicate(tradeClient);
					Node.setDataElement(itemNode, "ClientCode",
							sapNo + lpad(i + 1, 4));
					Node.setDataElement(itemNode, "Location", "");
					Node.setDataElement(itemNode, "Status", "1");
					Node.setDataElement(itemNode, "OrgCode", corpCode);
					Node.setDataElement(itemNode, "PosNo", "");
					Node.setDataElement(itemNode, "IPAddress", "");
					Node.setDataElement(itemNode, "VerifyKind", "1");
					Node.setDataElement(itemNode, "VerifyInfo", "UUDDLRLRBA");
					Node.setDataElement(itemNode, "Remark", "");
					Node.setDataElement(itemNode, "ClientType", "1");
					Node.appendToChildren(itemNode, listNode);
				}
			} else {
				itemNode = Node.duplicate(tradeClient);
				Node.setDataElement(itemNode, "ClientCode", sapNo + lpad(1, 4));
				Node.setDataElement(itemNode, "Location", "");
				Node.setDataElement(itemNode, "Status", "1");
				Node.setDataElement(itemNode, "OrgCode", corpCode);
				Node.setDataElement(itemNode, "PosNo", "");
				Node.setDataElement(itemNode, "IPAddress", "");
				Node.setDataElement(itemNode, "VerifyKind", "1");
				Node.setDataElement(itemNode, "VerifyInfo", "UUDDLRLRBA");
				Node.setDataElement(itemNode, "Remark", "");
				Node.setDataElement(itemNode, "ClientType", "1");
				Node.appendToChildren(itemNode, listNode);
				itemNode = Node.duplicate(tradeClient);
				Node.setDataElement(itemNode, "ClientCode",
						sapNo + lpad(9999, 4));
				Node.setDataElement(itemNode, "Location", "");
				Node.setDataElement(itemNode, "Status", "1");
				Node.setDataElement(itemNode, "OrgCode", corpCode);
				Node.setDataElement(itemNode, "PosNo", "");
				Node.setDataElement(itemNode, "IPAddress", "");
				Node.setDataElement(itemNode, "VerifyKind", "1");
				Node.setDataElement(itemNode, "VerifyInfo", "UUDDLRLRBA");
				Node.setDataElement(itemNode, "Remark", "");
				Node.setDataElement(itemNode, "ClientType", "1");
				Node.appendToChildren(itemNode, listNode);
			}
			result = rootNode;
			rootNode = 0;
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			if (Node.isValidNode(rootNode)) {
				Node.delete(rootNode);
				rootNode = 0;
			}
			if (Node.isValidNode(tradeClient)) {
				Node.delete(tradeClient);
				tradeClient = 0;
			}
		}
	}

	/**
	 * 按规定长度，往左对数字补0
	 * 
	 * @param number
	 *            数字
	 * @param length
	 *            长度
	 * @return 补0后的数字
	 */
	private static String lpad(int number, int length) {
		String result = String.valueOf(number);
		int strLen = result.length();
		for (int i = 0; i < length - strLen; i++) {
			result = "0" + result;
		}
		return result;
	}

	/**
	 * 根据参数表构造表类型的XML参数
	 * 
	 * @param corpCode
	 *            组织code
	 * @return XML结果集
	 * @throws Exception
	 */
	private static int getCardTypeList(String corpCode, String sapNo)
			throws Exception {
		BusObjectIterator queryResponse = executeSelect("SELECT * FROM SHOP_CARDTYPE");
		if (queryResponse == null)
			throw new Exception("can not find data from TABLE SHOP_CARDTYPE");

		String root = "<SendCardTypeOrg><CardTypeOrgList>"
				+ "</CardTypeOrgList></SendCardTypeOrg>";
		String item = "<CardTypeOrg>" + "<CardTypeCode/>" + "<OrgCode/>"
				+ "<IsRefund/>" + "</CardTypeOrg>";
		Document doc = CommonUtil.getDefaultDocument();
		int rootNode = 0;
		int cardTypeOrg = 0;
		int resultNode = 0;
		try {
			rootNode = doc.parseString(root);
			int listNode = Node.getElement(rootNode, "CardTypeOrgList");
			cardTypeOrg = doc.parseString(item);
			while (queryResponse.hasMoreElements()) {
				BusObject bo = queryResponse.nextElement();
				int queryResponseNode = bo._getObjectData();
				String cardtypeCode = Node.getDataElement(queryResponseNode,
						"CARDTYPE_CODE", "");
				String refund = Node.getDataElement(queryResponseNode,
						"REFUND", "");
				int tempNode = Node.duplicate(cardTypeOrg);
				Node.setDataElement(tempNode, "OrgCode", corpCode);
				Node.setDataElement(tempNode, "CardTypeOrg", sapNo);
				Node.setDataElement(tempNode, "CardTypeCode", cardtypeCode);
				Node.setDataElement(tempNode, "IsRefund", refund);
				Node.appendToChildren(tempNode, listNode);
			}
			resultNode = rootNode;
			rootNode = 0;
			return resultNode;
		} catch (Exception e) {
			throw e;
		} finally {
			if (Node.isValidNode(rootNode)) {
				Node.delete(rootNode);
				rootNode = 0;
			}
			if (Node.isValidNode(cardTypeOrg)) {
				Node.delete(cardTypeOrg);
				cardTypeOrg = 0;
			}
		}
	}

	/**
	 * 执行SQL语句，返回DICTIONARY集合
	 * 
	 * @param QueryText
	 *            SQL语句
	 * @return DICTIONARY集合
	 */
	public static BusObjectIterator<com.laiyifen.common.core.DICTIONARY> executeSelect(
			String QueryText) {
		QueryObject query = new QueryObject(QueryText);
		// TODO - Check that can we remove this setResultClass
		query.setResultClass(DICTIONARY.class);
		return query.getObjects();
	}

	/**
	 * 执行SQL语句，返回DICTIONARY对象
	 * 
	 * @param QueryText
	 *            SQL语句
	 * @return DICTIONARY对象
	 */
	public static BusObject executeSingleSelect(String QueryText) {
		QueryObject query = new QueryObject(QueryText);
		// TODO - Check that can we remove this setResultClass
		query.setResultClass(DICTIONARY.class);
		return query.getObject();
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
		if (pw != null)
			error = sw.toString();
		return error;
	}
}
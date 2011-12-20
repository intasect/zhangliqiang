package com.laiyifen.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.cordys.cpc.bsf.busobject.BSF;
import com.cordys.cpc.bsf.busobject.BsfContext;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.busobject.StoredProcedure;
import com.cordys.cpc.bsf.soap.SOAPRequestObject;

import com.eibus.util.logger.CordysLogger;
import com.eibus.xml.nom.Document;
import com.eibus.xml.nom.Node;
import com.eibus.xml.xpath.ResultNode;
import com.eibus.xml.xpath.XPath;
import com.eibus.xml.xpath.NodeSet;
import com.eibus.xml.xpath.XPathMetaInfo;
import com.laiyifen.common.ConnectorManager;
import com.laiyifen.common.core.COMMON_SIGN_CONFIG;
import com.laiyifen.common.core.EMPLOYEE_INFO;
import com.laiyifen.common.core.ORG_TEAMDEP_MAPPING;
import com.laiyifen.common.core.User;

/**
 * Assignment工具类
 * 
 * @author Lee Chengye
 * 
 */
public class AssignmentUtils {
	
	private static CordysLogger logger = CordysLogger.getCordysLogger(AssignmentUtils.class);
	
	public static final String ASSIGNMENT_NAMESPACE = "http://schemas.cordys.com/userassignment/UserAssignmentService/1.0";
	public static final String ORGMODEL_NAMESPACE = "http://schemas.cordys.com/orgmodel/runtime/OrgModel/1.0";
	public static final String LDAP10_NAMESPACE = "http://schemas.cordys.com/1.0/ldap";
	public static final String EMPLOYEE_NAMESPACE = "http://schemas.cordys.com/tbpm/common";

	/**
	 * 根据角色DN获取用户列表
	 * 
	 * @param roleDN
	 *            角色DN
	 * @return 以";"分割的用户DN列表
	 * @throws LaiYiFenCommunicationFailure
	 */
	public static String getUsersForRole(String roleDN)
			throws LaiYiFenCommunicationFailure {
		long timeS = System.currentTimeMillis();
		logger.error("======================== Method [getUsersForRole] Start");

		if (roleDN == null || "".equals(roleDN))
			return null;
		int begin = roleDN.indexOf("o=");
		String defaultOrg = roleDN.substring(begin);
		String[] paramNamesIAR = { "dn", "role" };
		Object[] paramValuesIAR = { defaultOrg, roleDN };
		String organization = ConnectorManager.getDefaultOrg();// BSF.getOrganization();
		int response = ConnectorManager._callSoapMethod(organization,
				LDAP10_NAMESPACE, "GetUsersForRole", paramNamesIAR,
				paramValuesIAR);// sroIAR.execute();
		String result = "";
		try {
			XPathMetaInfo info = new XPathMetaInfo();
			info.addNamespaceBinding("", LDAP10_NAMESPACE);
			XPath opath = XPath.getXPathInstance(".//entry/@dn");
			NodeSet oNodeSet = opath.selectNodeSet(response, info);

			if (oNodeSet != null) {
				String temp = null;
				while (oNodeSet.hasNext()) {
					long iResultNode = oNodeSet.next();
					if (ResultNode.isAttribute(iResultNode)) {
						temp = ResultNode.getStringValue(iResultNode);
					} else {
						int iNode = ResultNode.getElementNode(iResultNode);
						temp = Node.getData(iNode);
						Node.delete(iNode);
						iNode = 0;
					}
					if ("".equals(result)) {
						result = temp;
					} else {
						result = result + ";" + temp;
					}
				}
			}
		} finally {
			if (Node.isValidNode(response)) {
				Node.delete(response);
				response = 0;
			}
		}
		
		long timeE = System.currentTimeMillis();
		logger.error("======================== Method [getUsersForRole]['GetUsersForRole'] End. Cost " + (timeE-timeS) + "Ms");
		
		return result;
	}

	/**
	 * 根据业务角色获取用户列表，程序将获取该角色包括的子功能角色，然后获取子功能角色所包含的用户
	 * 
	 * 
	 * @param roleDN
	 *            业务角色DN
	 * @return 以";"分割的用户DN列表
	 * @throws LaiYiFenCommunicationFailure
	 */

	public static String getUsersForFunctionalRole(String roleDN)
			throws LaiYiFenCommunicationFailure {
		long timeS = System.currentTimeMillis();
		logger.error("======================== Method [getUsersForFunctionalRole] Start");
			
		String[] paramNamesIAR = { "dn", "depth" };
		Object[] paramValuesIAR = { roleDN, "1" };
		String organization = ConnectorManager.getDefaultOrg();// BSF.getOrganization();
		int response = ConnectorManager._callSoapMethod(organization,
				LDAP10_NAMESPACE, "GetRoles", paramNamesIAR, paramValuesIAR);// sroIAR.execute();
		String result = "";
		try {
			XPathMetaInfo info = new XPathMetaInfo();
			info.addNamespaceBinding("", LDAP10_NAMESPACE);
			XPath opath = XPath
					.getXPathInstance(".//role[busorganizationalroletype='Functional']/@id");
			NodeSet oNodeSet = opath.selectNodeSet(response, info);

			if (oNodeSet != null) {
				String temp = null;
				while (oNodeSet.hasNext()) {

					// this is a handle to theXmlResultNode object in the native
					// layer
					long iResultNode = oNodeSet.next();
					if (ResultNode.isAttribute(iResultNode)) {
						temp = ResultNode.getStringValue(iResultNode);
						if ("".equals(result)) {
							result = AssignmentUtils.getUsersForRole(temp);
						} else {
							result = result + ";"
									+ AssignmentUtils.getUsersForRole(temp);
						}
					}
				}
			}
		} finally {
			if (Node.isValidNode(response)) {
				Node.delete(response);
				response = 0;
			}
		}
		// to distinguish users in the List
		String[] arr = result.split(";");
		StringBuffer userList = new StringBuffer("");
		if (arr.length > 0) {
			for (int i = 0; i < arr.length; i++) {
				if (userList.indexOf(arr[i]) < 0) {
					userList.append(arr[i]).append(";");
				}
			}
		}
		if (userList.length() > 0) {
			result = userList.substring(0, userList.length() - 1);
		} else {
			result = "";
		}
		
		long timeE = System.currentTimeMillis();
		logger.error("======================== Method [getUsersForFunctionalRole]['GetRoles'] End. Cost " + (timeE-timeS) + "Ms");
		
		return result;
	}

	/**
	 * 获取子角色
	 * 
	 * @param roleDN
	 *            角色DN
	 * @return 以;分割的子角色列表
	 * @throws Exception
	 */

	public static String getSubRoles(String roleDN) throws Exception {
		long timeS = System.currentTimeMillis();
		logger.error("======================== Method [getSubRoles] Start");

		if ("".equals(roleDN) || roleDN == null)
			return "";
		String[] paramNamesIAR = { "dn", "depth" };
		Object[] paramValuesIAR = { roleDN, "1" };
		String organization = ConnectorManager.getDefaultOrg();
		String result = "";
		int response = 0;
		try {
			response = ConnectorManager
					._callSoapMethod(organization, LDAP10_NAMESPACE,
							"GetRoles", paramNamesIAR, paramValuesIAR);
			XPathMetaInfo info = new XPathMetaInfo();
			info.addNamespaceBinding("", LDAP10_NAMESPACE);
			XPath opath = XPath
					.getXPathInstance(".//role[busorganizationalroletype='Functional']/@id");
			NodeSet oNodeSet = opath.selectNodeSet(response, info);

			if (oNodeSet != null) {
				String temp = null;
				while (oNodeSet.hasNext()) {
					long iResultNode = oNodeSet.next();

					temp = ResultNode.getStringValue(iResultNode);
					if ("".equals(result)) {
						result = temp;
					} else {
						result = result + ";" + temp;
					}

				}
			}
		} finally {
			try {
				if (Node.isValidNode(response)) {
					Node.delete(response);
					response = 0;
				}
			} catch (Exception e) {
			}
		}
		// to distinguish users in the List
		String[] arr = result.split(";");
		StringBuffer temp = new StringBuffer("");
		if (arr.length > 0) {
			for (int i = 0; i < arr.length; i++) {
				if (temp.indexOf(arr[i]) < 0) {
					temp.append(arr[i]).append(";");
				}
			}
		}
		if (temp.length() > 0) {
			result = temp.substring(0, temp.length() - 1);
		} else {
			result = "";
		}
		
		long timeE = System.currentTimeMillis();
		logger.error("======================== Method [getUsersForFunctionalRole]['GetRoles'] End. Cost " + (timeE-timeS) + "Ms");
		
		return result;
	}

	/**
	 * 获取Parent Unit Id
	 * 
	 * @param unitId
	 *            Unit Id
	 * @return Parent Unit Id
	 * @throws LaiYiFenCommunicationFailure
	 */
	public static String getParentUnitId(String unitId)
			throws LaiYiFenCommunicationFailure {
		long timeS = System.currentTimeMillis();
		logger.error("======================== Method [getParentUnitId] Start");
		
		String organization = ConnectorManager.getDefaultOrg();// BSF.getOrganization();
		String[] paramNamesIAR = { "unitId" };
		Object[] paramValuesIAR = { unitId };
		int response = ConnectorManager._callSoapMethod(organization,
				ORGMODEL_NAMESPACE, "getUnitDetails", paramNamesIAR,
				paramValuesIAR);// sroIAR.execute();
		try {
			XPathMetaInfo info = new XPathMetaInfo();
			info.addNamespaceBinding("", ORGMODEL_NAMESPACE);
			XPath opath = XPath.getXPathInstance(".//ParentUnitID");
			int resultNode = opath.firstMatch(response, info);
			String result = Node.getDataWithDefault(resultNode, "");
			
			long timeE = System.currentTimeMillis();
			logger.error("======================== Method [getParentUnitId]['getUnitDetails'] End. Cost " + (timeE-timeS) + "Ms");
			
			return result;
		} finally {
			if (Node.isValidNode(response)) {
				Node.delete(response);
				response = 0;
			}
		}

	}

	/**
	 * 根据当前UnitId列表，获取所有上级Unit Id
	 * 
	 * @param unitIdList
	 *            unit Id列表
	 * @param level
	 *            上级部门的层级，1表示直接上级，2表示上级部门的上级部门
	 * @return 以;分割的unitId列表
	 * @throws LaiYiFenCommunicationFailure
	 */
	private static String getAllParentUnitId(String unitIdList, int level)
			throws LaiYiFenCommunicationFailure {
		long timeS = System.currentTimeMillis();
		logger.error("======================== Method [getAllParentUnitId] Start");
		
		if ("".equals(unitIdList) || unitIdList == null)
			return "";
		String[] unitIdArr = unitIdList.split(";");
		String unitId = null;
		String newUnitIdList = "";
		for (int i = 0; i < unitIdArr.length; i++) {
			unitId = unitIdArr[i];

			for (int j = 0; j < level; j++)
				unitId = getParentUnitId(unitId);
		}
		if ("".equals(newUnitIdList)) {
			newUnitIdList = unitId;
		} else {
			newUnitIdList = newUnitIdList + ";" + unitId;
		}
		
		long timeE = System.currentTimeMillis();
		logger.error("======================== Method [getAllParentUnitId][getParentUnitId] End. Cost " + (timeE-timeS) + "Ms");
		
		return newUnitIdList;
	}

	/**
	 * 根据Unit Id获取Lead Role
	 * 
	 * @param unitId
	 *            Unit Id
	 * @return Lead Role
	 * @throws LaiYiFenCommunicationFailure
	 */
	private static String getLeadRole(String unitId)
			throws LaiYiFenCommunicationFailure {
		long timeS = System.currentTimeMillis();
		logger.error("======================== Method [getLeadRole] Start");
		
		String organization = ConnectorManager.getDefaultOrg();// BSF.getOrganization();
		String[] paramNamesIAR = { "unitId" };
		Object[] paramValuesIAR = { unitId };
		int response = ConnectorManager._callSoapMethod(organization,
				ORGMODEL_NAMESPACE, "getUnitDetails", paramNamesIAR,
				paramValuesIAR);// sroIAR.execute();
		try {
			XPathMetaInfo info = new XPathMetaInfo();
			info.addNamespaceBinding("", ORGMODEL_NAMESPACE);
			XPath opath = XPath.getXPathInstance(".//LeadRoleDN");
			int resultNode = opath.firstMatch(response, info);
			String result = Node.getDataWithDefault(resultNode, "");
			
			long timeE = System.currentTimeMillis();
			logger.error("======================== Method [getLeadRole]['getUnitDetails'] End. Cost " + (timeE-timeS) + "Ms");
			
			return result;
		} finally {
			if (Node.isValidNode(response)) {
				Node.delete(response);
				response = 0;
			}
		}
	}

	/**
	 * 根据用户DN和Team内角色DN获取对应的team Id，如果角色DN为空，则返回Principal Team Id
	 * 
	 * @param userDN
	 *            用户DN
	 * @param roleDN
	 *            角色DN
	 * @return team Id
	 * @throws LaiYiFenCommunicationFailure
	 */
	public static String getTeamId(String userDN, String roleDN)
			throws LaiYiFenCommunicationFailure {
		long timeS = System.currentTimeMillis();
		logger.error("======================== Method [getTeamId] Start");
		
		String organization = ConnectorManager.getDefaultOrg();// BSF.getOrganization();
		String workspaceID = "__Organization Staging__";
		String[] paramNamesIAR = { "WorkspaceID", "AssignmentRoot", "UserDN" };
		Object[] paramValuesIAR = { workspaceID,
				User.getInitializeAssignmentRoot(), userDN };
		int response = ConnectorManager._callSoapMethod(organization,
				ASSIGNMENT_NAMESPACE, "GetAssignments", paramNamesIAR,
				paramValuesIAR);// sroIAR.execute();
		try {
			int teamIDNode = 0;
			if (Node.isValidNode(response)) {
				XPathMetaInfo metaInfo = new XPathMetaInfo();
				metaInfo.addNamespaceBinding("", ASSIGNMENT_NAMESPACE);

				if ("".equals(roleDN) || roleDN == null) {
					teamIDNode = XPath.getFirstMatch(
							".//Assignment[IsPrincipalUnit='true']/UnitID",
							metaInfo, response);
				} else {
					teamIDNode = XPath.getFirstMatch(".//Assignment[RoleDN='"
							+ roleDN + "']/UnitID", metaInfo, response);
				}

			}
			String teamID = Node.getDataWithDefault(teamIDNode, "");
			
			long timeE = System.currentTimeMillis();
			logger.error("======================== Method [getTeamId]['GetAssignments'] End. Cost " + (timeE-timeS) + "Ms");
			
			return teamID;
		} finally {
			if (Node.isValidNode(response)) {
				Node.delete(response);
				response = 0;
			}
		}
	}

	/**
	 * 
	 * @param userDN
	 * @param bizRole
	 * @return
	 * @throws Exception
	 */
	public static String getOwnManagers(String userDN, String bizRole)
			throws Exception {
		long timeS = System.currentTimeMillis();
		logger.error("======================== Method [getOwnManagers] Start");
		
		if ("".equals(bizRole) || bizRole == null)
			return "";
		String result = "";
		String teamResult = "";
		String teamIdList = getAllTeamIds(userDN);
		String roleList = getSubRoles(bizRole);
		if ("".equals(teamIdList) || teamIdList == null)
			return result;
		if ("".equals(roleList) || roleList == null)
			return result;

		String arr[] = teamIdList.split(";");
		for (int i = 0; i < arr.length; i++) {
			String manager = getManager(arr[i]);
			String managerArr[] = manager.split(";");
			for (int j = 0; j < managerArr.length; j++) {
				if (isUserOfRole(managerArr[j], roleList)) {
					if ("".equals(result)) {
						result = managerArr[j];
						teamResult = arr[i];
					} else if (result.indexOf(managerArr[j]) < 0) {
						result = result + ";" + managerArr[j];
						teamResult = teamResult + ";" + arr[i];
					}
				}
			}
		}
		if (!"".equals(result)) {
			result = result + "#" + teamResult;
		}
		
		long timeE = System.currentTimeMillis();
		logger.error("======================== Method [getOwnManagers][getAllTeamIds][getSubRoles][getManager] End. Cost " + (timeE-timeS) + "Ms");
		
		return result;
	}

	public static String getOwnManagers(String userDN, String bizRole, int level)
			throws Exception {
		long timeS = System.currentTimeMillis();
		logger.error("======================== Method [getOwnManagers] Start");
		
		if ("".equals(bizRole) || bizRole == null)
			return "";
		String result = "";
		String teamResult = "";
		String teamIdList = getAllTeamIds(userDN);
		// 寻找所有的上级部门
		teamIdList = getAllParentUnitId(teamIdList, level);
		String roleList = getSubRoles(bizRole);
		if ("".equals(teamIdList) || teamIdList == null)
			return result;
		if ("".equals(roleList) || roleList == null)
			return result;

		String arr[] = teamIdList.split(";");
		for (int i = 0; i < arr.length; i++) {
			String manager = getManager(arr[i]);
			String managerArr[] = manager.split(";");
			for (int j = 0; j < managerArr.length; j++) {
				if (isUserOfRole(managerArr[j], roleList)) {
					if ("".equals(result)) {
						result = managerArr[j];
						teamResult = arr[i];
					} else if (result.indexOf(managerArr[j]) < 0) {
						result = result + ";" + managerArr[j];
						teamResult = teamResult + ";" + arr[i];
					}
				}
			}
		}
		if (!"".equals(result)) {
			result = result + "#" + teamResult;
		}
		
		long timeE = System.currentTimeMillis();
		logger.error("======================== Method [getOwnManagers][getAllTeamIds][getAllParentUnitId][getSubRoles][getManager] End. Cost " + (timeE-timeS) + "Ms");
		
		return result;

	}

	public static HashMap<String, String> getManagerTeamMap(String info) {
		HashMap<String, String> map = new HashMap<String, String>();
		if ("".equals(info) || info == null)
			return map;
		String arr[] = info.split("#");
		String managerList = arr[0];
		String teamIdList = arr[1];
		String managerArr[] = managerList.split(";");
		String teamArr[] = teamIdList.split(";");
		for (int i = 0; i < managerArr.length; i++) {
			map.put(managerArr[i], teamArr[i]);
		}
		return map;
	}

	/**
	 * 根据用户DN获取所有的TeamId
	 * 
	 * @param userDN
	 *            用户DN
	 * @return 以;分割的TeamID列表
	 * @throws LaiYiFenCommunicationFailure
	 */
	private static String getAllTeamIds(String userDN)
			throws LaiYiFenCommunicationFailure {
		
		long timeS = System.currentTimeMillis();
		logger.error("======================== Method [getAllTeamIds] Start");
		
		String teamIdList = "";
		if ("".equals(userDN) || userDN == null)
			return teamIdList;
		String organization = ConnectorManager.getDefaultOrg();// BSF.getOrganization();
		String workspaceID = "__Organization Staging__";
		String[] paramNamesIAR = { "WorkspaceID", "AssignmentRoot", "UserDN" };
		Object[] paramValuesIAR = { workspaceID,
				User.getInitializeAssignmentRoot(), userDN };
		int response = ConnectorManager._callSoapMethod(organization,
				ASSIGNMENT_NAMESPACE, "GetAssignments", paramNamesIAR,
				paramValuesIAR);// sroIAR.execute();
		try {
			if (Node.isValidNode(response)) {
				XPathMetaInfo metaInfo = new XPathMetaInfo();
				metaInfo.addNamespaceBinding("", ASSIGNMENT_NAMESPACE);
				XPath opath = XPath.getXPathInstance(".//Assignment/UnitID");

				NodeSet oNodeSet = opath.selectNodeSet(response, metaInfo);

				if (oNodeSet != null) {
					String temp = null;
					while (oNodeSet.hasNext()) {
						long iResultNode = oNodeSet.next();
						int iNode = ResultNode.getElementNode(iResultNode);
						temp = Node.getData(iNode);
						Node.delete(iNode);
						iNode = 0;
						if ("".equals(teamIdList))
							teamIdList = temp;
						else if (teamIdList.indexOf(temp) < 0) {
							teamIdList = teamIdList + ";" + temp;
						}

					}
				}
			}
			
			long timeE = System.currentTimeMillis();
			logger.error("======================== Method [getAllTeamIds]['GetAssignments'] End. Cost " + (timeE-timeS) + "Ms");
			
			return teamIdList;
		} finally {
			if (Node.isValidNode(response)) {
				Node.delete(response);
				response = 0;
			}
		}
	}

	/**
	 * 根据用户 DN及岗位(角色DN)获取Manager
	 * 
	 * @param userDN
	 * @param roleDN
	 * @return Manager DN
	 * @throws LaiYiFenCommunicationFailure
	 * @deprecated
	 */
	public static String getManager(String userDN, String roleDN)
			throws LaiYiFenCommunicationFailure {
		String unitId = getTeamId(userDN, roleDN);
		if ("".equals(unitId) || unitId == null)
			return null;
		String leadRole = AssignmentUtils.getLeadRole(unitId);
		if (!"".equals(leadRole) && leadRole != null) {
			return AssignmentUtils.getUsersForRole(leadRole);
		} else {
			return "";
		}
	}

	/**
	 * 根据Unit(Team) Id获取Manager
	 * 
	 * @param unitId
	 *            Unit Id
	 * @return Manager DN
	 * @throws LaiYiFenCommunicationFailure
	 */
	public static String getManager(String unitId)
			throws LaiYiFenCommunicationFailure {
		if ("".equals(unitId) || unitId == null)
			return null;
		String leadRole = AssignmentUtils.getLeadRole(unitId);
		String userList = "";
		if (!"".equals(leadRole) && leadRole != null) {
			userList = AssignmentUtils.getUsersForRole(leadRole);
		}
		return userList;
	}

	/**
	 * 获取有效用户列表，根据EMPLOYEE_INFO表中的PERSONALSTATUS判断用户是否有效，0为正常，1为离职
	 * 
	 * @param userList
	 *            用户DN列表
	 * @return 有效用户DN列表
	 */
	public static String getEffectiveUsers(String userList) {
		long timeS = System.currentTimeMillis();
		logger.error("======================== Method [getEffectiveUsers] Start");
		
		String userArray[] = userList.split(";");
		String effectiveUserList = "";
		for (int i = 0; i < userArray.length; i++) {
			if (!isDisabledUser(userArray[i])) {
				if ("".equals(effectiveUserList)) {
					effectiveUserList = userArray[i];
				} else {
					effectiveUserList = effectiveUserList + ";" + userArray[i];
				}
			}
		}
		
		long timeE = System.currentTimeMillis();
		logger.error("======================== Method [getEffectiveUsers][isDisabledUser] End. Cost " + (timeE-timeS) + "Ms");
		
		return effectiveUserList;
	}

	/**
	 * 根据子公司代码过滤用户DN
	 * 
	 * @param userList
	 *            用户DN列表
	 * @param companyCode
	 *            子公司代码
	 * @return 过滤后的用户列表及StationId列表，""表示无用户属于该子公司代码
	 */
	public static String[] getFilterUsers(String userList, String companyCode) {
		long timeS = System.currentTimeMillis();
		logger.error("======================== Method [getFilterUsers] Start");
		
		String result[] = new String[2];
		String userArray[] = userList.split(";");
		String effectiveUserList = "";
		String roleList = "";
		String stationIdList = "";
		String code = companyCode;
		if (code == null)
			code = "";
		for (int i = 0; i < userArray.length; i++) {
			EMPLOYEE_INFO ei = getEmployeeInfo(userArray[i], code);
			if (ei != null) {
				if ("".equals(effectiveUserList)) {
					effectiveUserList = userArray[i];
					stationIdList = ei.getSTATIONCODE();
				} else {
					effectiveUserList = effectiveUserList + ";" + userArray[i];
					stationIdList = stationIdList + ";" + ei.getSTATIONCODE();
				}

			}
		}
		result[0] = effectiveUserList;
		result[1] = stationIdList;
		
		long timeE = System.currentTimeMillis();
		logger.error("======================== Method [getFilterUsers][getEmployeeInfo] End. Cost " + (timeE-timeS) + "Ms");
		
		return result;
	}
	
	/**
	 * 根据子公司代码过滤用户DN
	 * 
	 * @param userList
	 *            用户DN列表
	 * @param companyCodes
	 *            子公司代码,多个以；号分隔
	 * @return 过滤后的用户列表及StationId列表，""表示无用户属于该子公司代码
	 */
	public static String[] getFilterUsersByCompanyCodes(String userList, String companyCodes) {
		long timeS = System.currentTimeMillis();
		logger.error("======================== Method [getFilterUsersByCompanyCodes] Start");
		
		String result[] = new String[2];
		String userArray[] = userList.split(";");
		String effectiveUserList = "";
		String stationIdList = "";
		String codes = companyCodes;
		if (codes == null)
			codes = "";
		for (int i = 0; i < userArray.length; i++) {
			EMPLOYEE_INFO ei = getEmployeeInfoByCompanyCodes(userArray[i], codes);
			if (ei != null) {
				if ("".equals(effectiveUserList)) {
					effectiveUserList = userArray[i];
					stationIdList = ei.getSTATIONCODE();
				} else {
					effectiveUserList = effectiveUserList + ";" + userArray[i];
					stationIdList = stationIdList + ";" + ei.getSTATIONCODE();
				}

			}
		}
		result[0] = effectiveUserList;
		result[1] = stationIdList;
		
		long timeE = System.currentTimeMillis();
		logger.error("======================== Method [getFilterUsersByCompanyCodes][getEmployeeInfoByCompanyCodes] End. Cost " + (timeE-timeS) + "Ms");
		
		return result;
	}

	/**
	 * 获取backup用户
	 * 
	 * @param userList
	 *            责任人DN列表
	 * @param unitId
	 *            Unit(Team) Id
	 * @return Backup用户DN列表
	 * @throws LaiYiFenCommunicationFailure
	 */
	public static String getBackupUsers(String userList, String unitId)
			throws LaiYiFenCommunicationFailure {
		long timeS = System.currentTimeMillis();
		logger.error("======================== Method [getBackupUsers] Start");
		
		String parentUnitId = null;
		String users = userList;
		// 如果找不到有效处理人，则寻找责任 人的Manager
		if ("".equals(users) || users == null) {
			parentUnitId = AssignmentUtils.getParentUnitId(unitId);
			users = AssignmentUtils.getManager(parentUnitId);
			users = AssignmentUtils.getEffectiveUsers(userList);
		}
		// 如果仍找不到有效处理人，则继续往上寻找Manager
		if ("".equals(users) || users == null) {
			parentUnitId = AssignmentUtils.getParentUnitId(unitId);
			users = AssignmentUtils.getManager(parentUnitId);
			users = AssignmentUtils.getEffectiveUsers(userList);
		}
		
		long timeE = System.currentTimeMillis();
		logger.error("======================== Method [getBackupUsers][getParentUnitId][parentUnitId][getEffectiveUsers] End. Cost " + (timeE-timeS) + "Ms");
		
		return users;
	}

	public static String getSignUsers(String processCode, String stepCode,
			String companyCode) {
		long timeS = System.currentTimeMillis();
		logger.error("======================== Method [getSignUsers] Start");
		
		String result = "";
		BusObjectIterator<COMMON_SIGN_CONFIG> it = COMMON_SIGN_CONFIG
				.getUsersInfo(processCode, stepCode, companyCode);
		COMMON_SIGN_CONFIG temp = null;
		String orgDN = ConnectorManager.getDefaultOrg();
		String userDN = null;
		while (it.hasMoreElements()) {
			temp = it.nextElement();
			userDN = "cn=" + temp.getEMPLOYEE_CODE()
					+ ",cn=organizational users," + orgDN;
			if ("".equals(result)) {
				result = userDN;
			} else {
				result = result + ";" + userDN;
			}
		}
		
		long timeE = System.currentTimeMillis();
		logger.error("======================== Method [getSignUsers][getUsersInfo][parentUnitId][getEffectiveUsers] End. Cost " + (timeE-timeS) + "Ms");
		
		return result;
	}

	/**
	 * 获取EMPLOYEE_INFO对象
	 * 
	 * @param userDN
	 *            用户DN
	 * @param companyCode
	 *            公司编码
	 * @return EMPLOYEE_INFO对象
	 */
	private static EMPLOYEE_INFO getEmployeeInfo(String userDN,
			String companyCode) {
		long timeS = System.currentTimeMillis();
		logger.error("======================== Method [getEmployeeInfo] Start");
		
		if (userDN == null || "".equals(userDN))
			return null;
		String id = userDN.substring(userDN.indexOf("=") + 1,
				userDN.indexOf(","));
		EMPLOYEE_INFO info = EMPLOYEE_INFO.getEmployeeInfo(id, companyCode);
		if (info == null)
			info = EMPLOYEE_INFO.getParttimeEmployeeInfo(id, companyCode);
		
		long timeE = System.currentTimeMillis();
		logger.error("======================== Method [getEmployeeInfo] End. Cost " + (timeE-timeS) + "Ms");
		
		return info;
	}
	
	/**
	 * 获取EMPLOYEE_INFO对象
	 * 
	 * @param userDN
	 *            用户DN
	 * @param companyCodes
	 *            公司编码,多个以；号分隔
	 * @return EMPLOYEE_INFO对象
	 */
	private static EMPLOYEE_INFO getEmployeeInfoByCompanyCodes(String userDN,
			String companyCodes) {
		long timeS = System.currentTimeMillis();
		logger.error("======================== Method [getEmployeeInfoByCompanyCodes] Start");
		
		if (userDN == null || "".equals(userDN))
			return null;
		String id = userDN.substring(userDN.indexOf("=") + 1,
				userDN.indexOf(","));
		EMPLOYEE_INFO info = EMPLOYEE_INFO.getEmployeeInfoByCompanyCodes(id, companyCodes);
		if (info == null)
			info = EMPLOYEE_INFO.getParttimeEmployeeInfoByCompanyCodes(id, companyCodes);
		
		long timeE = System.currentTimeMillis();
		logger.error("======================== Method [getEmployeeInfoByCompanyCodes] End. Cost " + (timeE-timeS) + "Ms");
		
		return info;
	}

	/**
	 * 检查用户列表是否唯一用户
	 * 
	 * @param userList
	 *            用户DN列表
	 * @return boolean 若是一个用户，返回true
	 */
	public static boolean isSingleUser(String userList) {

		if (userList != null && userList.length() > 0
				&& userList.indexOf(";") < 0)
			return true;
		return false;
	}

	/**
	 * 检查当前用户是否有效
	 * 
	 * @param userDN
	 *            用户DN
	 * @return boolean 如果用户无效则返回true
	 */
	public static boolean isDisabledUser(String userDN) {
		if (userDN == null || "".equals(userDN))
			return true;
		String id = userDN.substring(userDN.indexOf("=") + 1,
				userDN.indexOf(","));
		EMPLOYEE_INFO info = EMPLOYEE_INFO.getEmployeeInfoObject(id);
		if (info == null)
			return true;
		if ("0".equals(info.getPERSONALSTATUS()))
			return false;
		else
			return true;
	}

	/**
	 * 
	 * @param userDN
	 * @param roleList
	 * @return
	 * @throws LaiYiFenCommunicationFailure
	 */
	public static boolean isUserOfRole(String userDN, String roleList)
			throws LaiYiFenCommunicationFailure {
		long timeS = System.currentTimeMillis();
		logger.error("======================== Method [isUserOfRole] Start");
		
		boolean result = false;
		String organization = ConnectorManager.getDefaultOrg();// BSF.getOrganization();
		String[] paramNamesIAR = { "dn" };
		Object[] paramValuesIAR = { userDN };
		if ("".equals(userDN) || userDN == null)
			return result;
		// 这里返回true,便于测试，生产环境应该返回false
		if ("".equals(roleList) || roleList == null)
			return true;
		int response = ConnectorManager._callSoapMethod(organization,
				LDAP10_NAMESPACE, "GetLDAPObject", paramNamesIAR,
				paramValuesIAR);// sroIAR.execute();
		try {
			XPathMetaInfo info = new XPathMetaInfo();
			info.addNamespaceBinding("", LDAP10_NAMESPACE);
			XPath opath = XPath.getXPathInstance(".//role/string[contains('"
					+ roleList + "',text())]");
			int resultNode = opath.firstMatch(response, info);
			if (resultNode > 0)
				result = true;
			
			long timeE = System.currentTimeMillis();
			logger.error("======================== Method [isUserOfRole]['GetLDAPObject'] End. Cost " + (timeE-timeS) + "Ms");
			
			return result;
		} finally {
			if (Node.isValidNode(response)) {
				Node.delete(response);
				response = 0;
			}
		}
	}

	/**
	 * 调用存储过程生成序列号
	 * 
	 * @param seqType
	 *            序列号类型，为自定为标示，如'SHOPID','FLOWID'
	 * @return 序列号
	 * @deprecated
	 */
	public static String getSeqNumber(String seqType) {
		String value = null;
		StoredProcedure sp = new StoredProcedure("SF_GET_SEQ");
		sp.prepareCall(":RESULT = \"SF_GET_SEQ\"( :PARAM1)");
		sp.setReturnParameter("RESULT");
		sp.setParameter("PARAM1", seqType);
		sp.execute();
		value = sp.getString("RESULT");
		return value;
	}

	/**
	 * 获取批处理号
	 * 
	 * @param sequenceName
	 *            序列名称
	 * @return
	 * @deprecated
	 */
	public static String getBatchNo() {
		BsfContext mycontext = initBSF();
		if (!BSF.getTransactionStarted()) {
			BSF.startTransaction();
		}
		String seq = getSeqNumber("BATCH_NO");
		if (BSF.getTransactionStarted()) {
			BSF.commitTransaction();
		}
		String dateString = "";
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		dateString = dateFormat.format(date);
		release(mycontext);
		return dateString + "_" + seq;
	}

	/**
	 * 同步用户信息
	 * 
	 * @return 是否同步成功
	 */
	public static boolean syncParticipantInfo() {
		StoredProcedure sp = new StoredProcedure("SP_SYNCPARTICIPANT");
		sp.prepareCall("CORDYSSYS.SP_SYNCPARTICIPANT");
		sp.execute();
		return true;
	}

	public static BsfContext initBSF() {
		try {
			return WsAppsEnvInitializer.initEmbeddedBsfContext();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static void release(BsfContext context) {
		WsAppsEnvInitializer.releaseBsfContext(context);
	}

	/**
	 * 从Exception获取详细错误信息
	 * 
	 * @param e
	 *            Exception
	 * @return detail exception string
	 */
	public static String getDetailError(Exception e) {
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

	public static int getUserInfoByBizrole(String bizRoleDN,
			String currentDeptCode) throws Exception {
		long timeS = System.currentTimeMillis();
		logger.error("======================== Method [getUserInfoByBizrole] Start");
		
		int result = 0;
		String userListTemplate = "<UserList></UserList>";
		String userTemplate = "<User></User>";
		String roleList = getSubRoles(bizRoleDN);

		ORG_TEAMDEP_MAPPING map = ORG_TEAMDEP_MAPPING
				.getMappingByDeptid(currentDeptCode);
		String teamId = null;
		if (map != null)
			teamId = map.getTEAM_ID();
		else
			throw new Exception("DEPT CODE不存在");

		String organization = ConnectorManager.getDefaultOrg();// BSF.getOrganization();
		String workspaceID = "__Organization Staging__";
		Document doc = CommonUtil.getDefaultDocument();
		result = doc.parseString(userListTemplate);
		int userTemplateNode = doc.parseString(userTemplate);
		String companyName = "";

		String[] paramNamesIAR = { "WorkspaceID", "AssignmentRoot", "UnitID" };
		Object[] paramValuesIAR = { workspaceID,
				User.getInitializeAssignmentRoot(), teamId };
		// refactor to enhance it's performance

		int response = ConnectorManager._callSoapMethod(organization,
				ASSIGNMENT_NAMESPACE, "GetAssignments", paramNamesIAR,
				paramValuesIAR);
		try {
			if (Node.isValidNode(response)) {
				XPathMetaInfo metaInfo = new XPathMetaInfo();
				metaInfo.addNamespaceBinding("", ASSIGNMENT_NAMESPACE);
				XPath opath = XPath.getXPathInstance(".//Assignment[contains('"
						+ roleList + "',RoleDN)]");
				NodeSet oNodeSet = opath.selectNodeSet(response, metaInfo);
				String companyCode = "";
				if (oNodeSet != null) {
					while (oNodeSet.hasNext()) {
						long iResultNode = oNodeSet.next();
						int iNode = ResultNode.getElementNode(iResultNode);
						String UserDN = Node
								.getDataElement(iNode, "UserDN", "");
						String UnitID = Node
								.getDataElement(iNode, "UnitID", "");
						String empId = UserDN.substring(
								UserDN.indexOf("=") + 1, UserDN.indexOf(","));
						EMPLOYEE_INFO info = EMPLOYEE_INFO
								.getEmployeeInfoObject(empId);
						int userNode = 0;
						if (info != null) {
							companyCode = info.getCOMPANYCODE();
						}
						if (companyCode != "") {
							companyName = getCompanyName(companyCode);
							userNode = Node.duplicate(userTemplateNode);
							Node.setDataElement(userNode, "EmployeeId", empId);
							Node.setDataElement(userNode, "UserName",
									info.getSN() + info.getGIVENNAME());
							Node.setDataElement(userNode, "DeptCode",
									info.getDEPTID());
							Node.setDataElement(userNode, "DeptName",
									info.getDEPTNAME());
							Node.setDataElement(userNode, "CompanyCode",
									info.getCOMPANYCODE());
							Node.setDataElement(userNode, "CompanyName",
									companyName);
							Node.setDataElement(userNode, "UserDN", UserDN);
							Node.setDataElement(userNode, "UnitID", UnitID);
							Node.appendToChildren(userNode, result);

						}
						Node.delete(iNode);
						iNode = 0;

					}
				}

			}

		} catch (Exception e) {
			throw e;
		} finally {
			if (Node.isValidNode(response)) {
				Node.delete(response);
				response = 0;
			}
		}
		
		long timeE = System.currentTimeMillis();
		logger.error("======================== Method [getUserInfoByBizrole] End. Cost " + (timeE-timeS) + "Ms");
		
		return result;
	}

	public static String getRoles() throws Exception {
		long timeS = System.currentTimeMillis();
		logger.error("======================== Method [getRoles] Start");
		
		BsfContext mycontext = initBSF();

		String organization = ConnectorManager.getDefaultOrg();// BSF.getOrganization();
		String workspaceID = "__Organization Staging__";
		String[] paramNamesIAR = { "WorkspaceID", "AssignmentRoot" };
		Object[] paramValuesIAR = { workspaceID,
				User.getInitializeAssignmentRoot() };
		int response = ConnectorManager._callSoapMethod(organization,
				ASSIGNMENT_NAMESPACE, "GetAssignments", paramNamesIAR,
				paramValuesIAR);
		String result = "";

		try {
			if (response > 0) {
				XPathMetaInfo metaInfo = new XPathMetaInfo();
				metaInfo.addNamespaceBinding("", ASSIGNMENT_NAMESPACE);
				XPath opath = XPath.getXPathInstance(".//Assignment");

				NodeSet oNodeSet = opath.selectNodeSet(response, metaInfo);

				if (oNodeSet != null) {
					String temp = null;
					while (oNodeSet.hasNext()) {

						// this is a handle to theXmlResultNode object in the
						// native
						// layer
						long iResultNode = oNodeSet.next();
						int iNode = ResultNode.getElementNode(iResultNode);
						temp = Node.getDataElement(iNode, "UserDN", "") + "	"
								+ Node.getDataElement(iNode, "RoleDN", "")
								+ "	"
								+ Node.getDataElement(iNode, "UnitID", "");
						if ("".equals(result)) {
							result = temp;
						} else {
							result = result + "\n" + temp;
						}
					}
				}
			}
			release(mycontext);
			
			long timeE = System.currentTimeMillis();
			logger.error("======================== Method [getRoles]['GetAssignments'] End. Cost " + (timeE-timeS) + "Ms");
			
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			if (Node.isValidNode(response)) {
				Node.delete(response);
				response = 0;
			}
		}

	}

	public static String getAllRoles() throws Exception {
		long timeS = System.currentTimeMillis();
		logger.error("======================== Method [getAllRoles] Start");
		
		String request = "<SearchLDAP xmlns:xfr=\"http://schemas.cordys.com/1.0/xforms/runtime\" xmlns=\"http://schemas.cordys.com/1.0/ldap\">"
				+ "<dn xmlns=\"http://schemas.cordys.com/1.0/ldap\">o=laiyifen,cn=cordys,cn=defaultInst,o=laiyifen</dn>"
				+ "<scope xmlns=\"http://schemas.cordys.com/1.0/ldap\">2</scope>"
				+ "<filter xmlns=\"http://schemas.cordys.com/1.0/ldap\">&amp;(objectclass=busorganizationalrole)(|(cn=*)(cn=*)(cn=**))</filter>"
				+ "<sort xmlns=\"http://schemas.cordys.com/1.0/ldap\">ascending</sort>"
				+ "<returnValues xmlns=\"http://schemas.cordys.com/1.0/ldap\">false</returnValues>"
				+ "<return xmlns=\"http://schemas.cordys.com/1.0/ldap\" />"
				+ "</SearchLDAP>";
		BsfContext mycontext = initBSF();
		String org = ConnectorManager.getDefaultOrg();
		Document doc = new Document();
		int reqNode = doc.parseString(request);
		int response = ConnectorManager._callSoapMethod(org, LDAP10_NAMESPACE,
				"SearchLDAP", null, null, reqNode);

		String result = "";

		try {
			if (response > 0) {
				XPathMetaInfo metaInfo = new XPathMetaInfo();
				metaInfo.addNamespaceBinding("", LDAP10_NAMESPACE);
				XPath opath = XPath.getXPathInstance(".//cn/string");

				NodeSet oNodeSet = opath.selectNodeSet(response, metaInfo);

				if (oNodeSet != null) {
					String temp = null;
					while (oNodeSet.hasNext()) {

						// this is a handle to theXmlResultNode object in the
						// native
						// layer
						long iResultNode = oNodeSet.next();
						int iNode = ResultNode.getElementNode(iResultNode);
						temp = Node.getData(iNode);
						if ("".equals(result)) {
							result = temp;
						} else {
							result = result + "\n" + temp;
						}
					}
				}
			}
			release(mycontext);
			
			long timeE = System.currentTimeMillis();
			logger.error("======================== Method [getAllRoles]['SearchLDAP'] End. Cost " + (timeE-timeS) + "Ms");
			
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			if (Node.isValidNode(response)) {
				Node.delete(response);
				response = 0;
			}
		}

	}

	/*
	 * 获取部门编码，角色
	 * 
	 * @param teamId 部门ID return 部门名称
	 */
	public static String getCompanyName(String companyCode) {
		long timeS = System.currentTimeMillis();
		logger.error("======================== Method [getCompanyName] Start");
		
		String organization = BSF.getOrganization();
		String[] paramNamesIAR = { "COMPANY_CODE" };
		Object[] paramValuesIAR = { companyCode };
		SOAPRequestObject sroIAR = new SOAPRequestObject(organization,
				EMPLOYEE_NAMESPACE, "GetOrgCompanyInfoObject", paramNamesIAR,
				paramValuesIAR);
		int response = sroIAR.execute();
		try {
			int companyNameNode = 0;
			if (response > 0) {
				XPathMetaInfo metaInfo = new XPathMetaInfo();
				metaInfo.addNamespaceBinding("", EMPLOYEE_NAMESPACE);
				companyNameNode = XPath.getFirstMatch(".//COMPANY_NAME",
						metaInfo, response);
			}
			String companyName = Node.getDataWithDefault(companyNameNode, null);
			
			long timeE = System.currentTimeMillis();
			logger.error("======================== Method [getCompanyName]['GetOrgCompanyInfoObject'] End. Cost " + (timeE-timeS) + "Ms");
			
			return companyName;
		} finally {
			Node.delete(response);
			response = 0;
		}
	}
}
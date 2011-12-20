package com.laiyifen.common.usersync;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import com.cordys.cpc.bsf.busobject.BSF;
import com.eibus.xml.nom.Document;
import com.eibus.xml.nom.Node;
import com.eibus.xml.xpath.NodeSet;
import com.eibus.xml.xpath.ResultNode;
import com.eibus.xml.xpath.XPath;
import com.eibus.xml.xpath.XPathMetaInfo;
import com.laiyifen.common.ConnectorManager;
import com.laiyifen.common.core.*;
import com.laiyifen.common.util.CommonUtil;
import com.laiyifen.common.util.LaiYiFenCommunicationFailure;
import com.laiyifen.common.util.XPathUtil;

/**
 * Team 信息同步
 * 
 * @author Lee Chengye
 * 
 */
public class TeamSynchronizer {
	private final static String um_namespace = "http://schemas.cordys.com/userassignment/UserAssignmentService/1.0";
	private static String assignmentRoot = null;

	// private static HashMap<String, String> roleTeamMap = null;

	/**
	 * 初始化相关信息，在执行业务操作前执行
	 */
	public static void init() throws LaiYiFenCommunicationFailure {
		assignmentRoot = User.getInitializeAssignmentRoot();
		// roleTeamMap = getRoleTeamMap();
	}

	/**
	 * 导入用户的Team信息
	 * 
	 * @param empInfo
	 *            包含用户信息的EMPLOYEE_INFO_TRANS对象
	 * @param dbManager
	 *            DBManager
	 */
	public static void importTeamInfo(EMPLOYEE_INFO_TRANS empInfo,
			DBManager dbManager) {
		try {
			String eid = empInfo.getEMPLOYEEID();
			String userDN = "cn=" + eid + ",cn=organizational users,"
					+ ConnectorManager.getDefaultOrg();

			String stationCode = empInfo.getSTATIONCODE();

			EMPLOYEE_ROLE_TRANSMAP map = EMPLOYEE_ROLE_TRANSMAP
					.getRoleInfoByStationInfo(empInfo.getCOMPANYCODE(),
							empInfo.getDEPTID(), stationCode);
			if (map == null)
				throw new Exception("找不到角色映射关系");
			String roleDN = map.getROLEDN();
			String teamId = null;// roleTeamMap.get(roleDN);
			ORG_TEAMDEP_MAPPING teamInfo = ORG_TEAMDEP_MAPPING
					.getOrgTeamdepMappingObject(empInfo.getDEPTID());
			if (teamInfo != null) {
				teamId = teamInfo.getTEAM_ID();
			} else {
				throw new Exception("部门ID对应的TEAM ID不存在");
			}
			if ("TBD".equals(roleDN)) {
				throw new Exception("roleDN无效:" + roleDN);
			}
			addAssignment(assignmentRoot, teamId, userDN, roleDN);
		} catch (Exception e) {
			dbManager.saveErrorMessage(empInfo,
					"更新team及role信息出错:" + e.getMessage());
		}
	}

	/**
	 * 获取role-team映射关系
	 * 
	 * @return Map对象
	 * @throws LaiYiFenCommunicationFailure
	 * @deprecated
	 */
	public static HashMap<String, String> getRoleTeamMap()
			throws LaiYiFenCommunicationFailure {

		String org = ConnectorManager.getDefaultOrg();// BSF.getOrganization();
		int result = ConnectorManager._callSoapMethod(org, um_namespace,
				"GetUnits", null, null);

		XPathMetaInfo info = new XPathMetaInfo();
		info.addNamespaceBinding("", um_namespace);
		XPath opath = XPath.getXPathInstance(".//Unit");
		NodeSet oNodeSet = opath.selectNodeSet(result, info);

		HashMap<String, String> roleMap = new HashMap<String, String>();
		if (oNodeSet != null) {

			while (oNodeSet.hasNext()) {

				// this is a handle to theXmlResultNode object in the native
				// layer
				long iResultNode = oNodeSet.next();

				int iNode = ResultNode.getElementNode(iResultNode);
				String id = Node.getDataElement(iNode, "ID", "");
				// String qname = Node.getDataElement(iNode, "QName", "");
				Node.delete(iNode);
				iNode = 0;
				setMap(id, roleMap);
				// insertDict(dictType, value, desc);
				// map.put(value, desc);
			}
		}
		return roleMap;
	}

	/**
	 * 根据teamId，设置role-team关系
	 * 
	 * @param teamId
	 *            Team Id
	 * @param originMap
	 *            Original Map
	 * @throws LaiYiFenCommunicationFailure
	 * @deprecated
	 */
	private static void setMap(String teamId, HashMap<String, String> originMap)
			throws LaiYiFenCommunicationFailure {
		String org = BSF.getOrganization();
		String[] paramNamesIAR = { "UnitID" };
		Object[] paramValuesIAR = { teamId };
		int result = ConnectorManager._callSoapMethod(org, um_namespace,
				"GetUnitRoles", paramNamesIAR, paramValuesIAR);
		try {
			XPathMetaInfo info = new XPathMetaInfo();
			info.addNamespaceBinding("", um_namespace);
			XPath opath = XPath.getXPathInstance(".//UnitRole");
			NodeSet oNodeSet = opath.selectNodeSet(result, info);
			if (oNodeSet != null) {

				while (oNodeSet.hasNext()) {

					// this is a handle to theXmlResultNode object in the native
					// layer
					long iResultNode = oNodeSet.next();

					int iNode = ResultNode.getElementNode(iResultNode);
					String roleDN = Node.getDataElement(iNode, "RoleDN", "");
					String unitId = Node.getDataElement(iNode, "UnitID", "");
					Node.delete(iNode);
					iNode = 0;
					originMap.put(roleDN, unitId);
					// insertDict(dictType, value, desc);
					// map.put(value, desc);
				}
			}

		} finally {
			if (result > 0 && Node.isValidNode(result)) {
				Node.delete(result);
				result = 0;
			}
		}
	}

	private static boolean addAssignment(String assignmentRoot, String unitId,
			String userDN, String roleDN) throws Exception {
		String params = "<AddAssignmentsWithForcePrincipal>"
				+ "<WorkspaceID>__Organization Staging__</WorkspaceID>"
				+ "<AssignmentRoot></AssignmentRoot>"
				+ "<Assignments>"
				+ "<dataset>"
				+ "<tuple xmlns=\"http://schemas.cordys.com/userassignment/UserAssignmentService/1.0\">"
				+ "<new>" + "<Assignment>" + "<UnitID></UnitID>"
				+ "<RoleDN></RoleDN>" + "<UserDN></UserDN>" + "<Description />"
				+ "<EffectiveDate></EffectiveDate>"
				+ "<FinishDate></FinishDate>"
				+ "<IsPrincipalUnit>false</IsPrincipalUnit>" + "</Assignment>"
				+ "</new>" + "</tuple>" + "</dataset>" + "</Assignments>"
				+ "<DoPublish>true</DoPublish>"
				+ "<ForceSetPrincipal>true</ForceSetPrincipal>"
				+ "</AddAssignmentsWithForcePrincipal>";
		Document doc = CommonUtil.getDefaultDocument();
		int paramsNode = doc.parseString(params);
		try {
			int assignmentNode = XPathUtil.firstMatch(".//Assignment",
					paramsNode);
			Node.setDataElement(paramsNode, "AssignmentRoot", assignmentRoot);
			Node.setDataElement(assignmentNode, "UnitID", unitId);
			Node.setDataElement(assignmentNode, "RoleDN", roleDN);
			Node.setDataElement(assignmentNode, "UserDN", userDN);
			String org = ConnectorManager.getDefaultOrg();
			String ns = "http://schemas.cordys.com/userassignment/UserAssignmentService/1.0";
			ConnectorManager._callSoapMethod(org, ns,
					"AddAssignmentsWithForcePrincipal", null, null, paramsNode);
		} catch (Exception e) {
			throw e;
		} finally {
			if (paramsNode > 0 && Node.isValidNode(paramsNode)) {
				Node.delete(paramsNode);
				paramsNode = 0;
			}
		}
		return true;
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

}

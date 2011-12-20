package com.laiyifen.common.usersync;

import java.security.Security;
import java.util.ArrayList;

import com.laiyifen.common.core.EMPLOYEE_INFO_TRANS;
import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPJSSESecureSocketFactory;
import com.novell.ldap.LDAPModification;
import com.novell.ldap.LDAPSocketFactory;

public class CordysLDAPManager {

	private LDAPConnection connection;

	private CordysDBManager cordysDBManager;

	public CordysLDAPManager() {
		initialize();
	}

	/**
	 * 初始化
	 */
	@SuppressWarnings("deprecation")
	private void initialize() {
		// SSL=true时需要使用代码37行-41行，否则注释掉
		String ssl = "/opt/Cordys/defaultInst/certificates/truststore/CordysTrustStore.jks";
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		System.setProperty("javax.net.ssl.trustStore", ssl);
		LDAPSocketFactory ssf = new LDAPJSSESecureSocketFactory();
		LDAPConnection.setSocketFactory(ssf);

		cordysDBManager = new CordysDBManager();
		String server = PropertiesClass.getKeyValue("cordysldap.hostname");
		String port = PropertiesClass.getKeyValue("cordysldap.port");
		String user = PropertiesClass.getKeyValue("cordysldap.user");
		String pwd = PropertiesClass.getKeyValue("cordysldap.password");
		int portNumber = Integer.parseInt(port);
		try {
			connection = new LDAPConnection();
			connection.connect(server, portNumber);
			connection.bind(LDAPConnection.LDAP_V3, user, pwd);
		} catch (LDAPException le) {
			LogHandler
					.error("error occured when connecting to cordys ldap", le);
		}
	}

	/**
	 * 关闭连接
	 */
	public void dispose() {
		if (connection.isConnected()) {
			try {
				connection.disconnect();
			} catch (LDAPException e) {
				LogHandler.error(
						"error occured when close the cordys ldap connection",
						e);
			}
		}
		cordysDBManager.dispose();
	}

	public void importUsers(ArrayList<EMPLOYEE_INFO_TRANS> usersInfo,
			DBManager dbManager, CordysLDAPManager cordysLDAPManager)
			throws Exception {
		int i = 1;
		TeamSynchronizer.init();
		for (EMPLOYEE_INFO_TRANS userInfoObject : usersInfo) {
			importUser(userInfoObject, dbManager, cordysLDAPManager, i);
			TeamSynchronizer.importTeamInfo(userInfoObject, dbManager);
			i++;
		}
		// LogHandler.info("导入用户成功");
	}

	private void importUser(EMPLOYEE_INFO_TRANS userTrans, DBManager dbManager,
			CordysLDAPManager cordysLDAPManager, int num) {
		// LogHandler.info("要导入的用户信息:\n" + userObject.toString());
		LDAPEntry authUserEntry = null;
		LDAPEntry orgUserEntry = null;
		try {
			// 此数据状态是delete，不对ldap做操作，保留ldap数据。数据状态：I=新增；D=删除；U=更新
			if (userTrans.getMODIFYACTION().equals("D")) {
				// Delete EMPLOYEE_ALL_IMPORT table's data
				dbManager.deleteEmpAllTable(userTrans);
				dbManager.updateEmpPartTable(userTrans, "S");
				return;
			} else if (userTrans.getMODIFYACTION().equals("I")) {
				dbManager.insertEmpAllTable(userTrans);
			} else if (userTrans.getMODIFYACTION().equals("U")) {
				dbManager.updateEmpAllTable(userTrans);
			}
			// authentication user
			String authenticationUserDN = "cn="
					+ userTrans.getEMPLOYEEID()
					+ ","
					+ PropertiesClass
							.getKeyValue("cordysldap.authenticationuserbase");
			if ("".equals(userTrans.getEMPLOYEEID())) {
				// LogHandler.info("LoginID can't be empty.");
				return;
			}
			LDAPAttributeSet authenticationUserAttrs = new LDAPAttributeSet();
			LDAPAttribute loginid = new LDAPAttribute("cn", ""
					+ userTrans.getEMPLOYEEID());
			authenticationUserAttrs.add(loginid);
			LDAPAttribute osidentity = new LDAPAttribute("osidentity", ""
					+ userTrans.getEMPLOYEEID());
			authenticationUserAttrs.add(osidentity);

			String[] objectClass = new String[2];
			objectClass[0] = "top";
			objectClass[1] = "busauthenticationuser";
			LDAPAttribute objectclass = new LDAPAttribute("objectclass",
					objectClass);
			authenticationUserAttrs.add(objectclass);
			LDAPAttribute authenticationType = new LDAPAttribute(
					"authenticationtype", "custom");
			authenticationUserAttrs.add(authenticationType);
			LDAPAttribute defaultcontext = new LDAPAttribute("defaultcontext",
					PropertiesClass.getKeyValue("cordysldap.defaultcontext"));
			authenticationUserAttrs.add(defaultcontext);

			LDAPAttribute userPassword = new LDAPAttribute("userPassword",
					PropertiesClass.getKeyValue("cordysldap.userPassword"));
			authenticationUserAttrs.add(userPassword);

			// 邮箱
			if (userTrans.getLYFMAIL() != null
					&& !"".equals(userTrans.getLYFMAIL())) {
				LDAPAttribute mail = new LDAPAttribute("mail",
						userTrans.getLYFMAIL());
				authenticationUserAttrs.add(mail);
			}

			// 姓名
			LDAPAttribute description = new LDAPAttribute("description",
					userTrans.getSN() + userTrans.getGIVENNAME());
			authenticationUserAttrs.add(description);

			// 电话号码
			if (userTrans.getWEBSHORT() != null
					&& !"".equals(userTrans.getWEBSHORT())) {
				LDAPAttribute telephoneNumber = new LDAPAttribute(
						"telephoneNumber", userTrans.getWEBSHORT());
				authenticationUserAttrs.add(telephoneNumber);
			}

			// 人员状态，0在职；1离职
			LDAPAttribute enable = null;
			if ("0".equals(userTrans.getPERSONALSTATUS())) {
				enable = new LDAPAttribute("enable", "true");
				authenticationUserAttrs.add(enable);
			} else {
				enable = new LDAPAttribute("enable", "false");
				authenticationUserAttrs.add(enable);
			}

			// 组织简称
			LDAPAttribute orgShort = new LDAPAttribute("o",
					userTrans.getORGSHORT());
			authenticationUserAttrs.add(orgShort);

			// organization user
			String organizationDN = PropertiesClass
					.getKeyValue("cordysldap.organizationuserbase");
			String organizationUserDN = "cn=" + userTrans.getEMPLOYEEID() + ","
					+ organizationDN;
			LDAPAttributeSet organizationUserAttrs = new LDAPAttributeSet();
			LDAPAttribute organizationUserCNAttr = new LDAPAttribute("cn", ""
					+ userTrans.getEMPLOYEEID());
			organizationUserAttrs.add(organizationUserCNAttr);
			LDAPAttribute authenticationuserAttr = new LDAPAttribute(
					"authenticationuser", authenticationUserDN);
			organizationUserAttrs.add(authenticationuserAttr);
			String[] objectClass2 = new String[3];
			objectClass2[0] = "top";
			objectClass2[1] = "busorganizationaluser";
			objectClass2[2] = "busorganizationalobject";
			LDAPAttribute objectclass3 = new LDAPAttribute("objectclass",
					objectClass2);
			organizationUserAttrs.add(objectclass3);

			LDAPAttribute orgDesc = new LDAPAttribute("description",
					userTrans.getSN() + userTrans.getGIVENNAME());
			organizationUserAttrs.add(orgDesc);
			// to set default roles
			String[] defaultRoleArray = null;
			if (PropertiesClass.getKeyValue("cordysldap.roles") != null
					&& PropertiesClass.getKeyValue("cordysldap.roles").equals(
							"") == false) {
				defaultRoleArray = PropertiesClass.getKeyValue(
						"cordysldap.roles").split(";");
				// LDAPAttribute attribute = new LDAPAttribute("role",
				// roleArray);
				// organizationUserAttrs.add(attribute);
			}
			// to set postion role
			String roleDN = dbManager.getCordysRole(userTrans.getCOMPANYCODE(),
					userTrans.getDEPTID(), userTrans.getSTATIONCODE());
			String[] roleArray = null;
			if (roleDN != null && !"".equals(roleDN)) {
				if (!"TBD".equals(roleDN.toUpperCase())) {
					int roleNumber = 1;
					if (defaultRoleArray != null) {
						roleNumber = roleNumber + defaultRoleArray.length;
					}
					roleArray = new String[roleNumber];
					if (defaultRoleArray != null)
						for (int defaultRoleNum = 0; defaultRoleNum < defaultRoleArray.length; defaultRoleNum++) {
							roleArray[defaultRoleNum] = defaultRoleArray[defaultRoleNum];
						}
					roleArray[roleArray.length - 1] = roleDN;
				} else {
					roleArray = defaultRoleArray;
				}
				if (roleArray != null) {
					LDAPAttribute orgRoles = new LDAPAttribute("role",
							roleArray);
					organizationUserAttrs.add(orgRoles);
				}
			} else {
				dbManager.rollbackEmpAllTable(userTrans);
				dbManager.updateEmpPartTable(userTrans, "F");
				dbManager.saveErrorMessage(userTrans,
						"员工编号为" + userTrans.getEMPLOYEEID()
								+ "的数据在角色匹配表中未找到对应的cordys角色信息，请确认");
				LogHandler.info("员工编号为" + userTrans.getEMPLOYEEID()
						+ "的数据在角色匹配表中未找到对应的cordys角色信息，请确认");
				return;
			}

			// deal with authentication user
			try {
				authUserEntry = connection.read(authenticationUserDN);
				if (userTrans.getMODIFYACTION().equals("I")) {
					dbManager.rollbackEmpAllTable(userTrans);
					dbManager.updateEmpPartTable(userTrans, "F");
					LogHandler.info("员工编号为" + userTrans.getEMPLOYEEID()
							+ "的新增数据在cordys认证用户中已经存在");
					dbManager.saveErrorMessage(userTrans,
							"员工编号为" + userTrans.getEMPLOYEEID()
									+ "的新增数据在cordys认证用户中已经存在");
					return;
				} else if (userTrans.getMODIFYACTION().equals("U")) {
					connection.modify(authUserEntry.getDN(),
							new LDAPModification(LDAPModification.REPLACE,
									description));
					if (userTrans.getLYFMAIL() != null
							&& !"".equals(userTrans.getLYFMAIL())) {
						connection.modify(
								authUserEntry.getDN(),
								new LDAPModification(LDAPModification.REPLACE,
										new LDAPAttribute("mail", userTrans
												.getLYFMAIL())));
					}
					if (userTrans.getWEBSHORT() != null
							&& !"".equals(userTrans.getWEBSHORT())) {
						connection.modify(authUserEntry.getDN(),
								new LDAPModification(LDAPModification.REPLACE,
										new LDAPAttribute("telephoneNumber",
												userTrans.getWEBSHORT())));
					}
					connection.modify(authUserEntry.getDN(),
							new LDAPModification(LDAPModification.REPLACE,
									orgShort));
					connection.modify(authUserEntry.getDN(),
							new LDAPModification(LDAPModification.REPLACE,
									enable));
				}
			} catch (LDAPException e) {
				if (userTrans.getMODIFYACTION().equals("I")
						|| userTrans.getMODIFYACTION().equals("U")) {
					authUserEntry = new LDAPEntry(authenticationUserDN,
							authenticationUserAttrs);
					connection.add(authUserEntry);
				} else {
					dbManager.rollbackEmpAllTable(userTrans);
					dbManager.updateEmpPartTable(userTrans, "F");
					LogHandler.info("员工编号为" + userTrans.getEMPLOYEEID()
							+ "的修改数据在cordys认证用户中不存在");
					dbManager.saveErrorMessage(userTrans,
							"员工编号为" + userTrans.getEMPLOYEEID()
									+ "的修改数据在cordys认证用户中不存在");
					return;
				}
			}
			// deal with organization user
			try {
				orgUserEntry = connection.read(organizationUserDN);
				if (userTrans.getMODIFYACTION().equals("I")) {
					dbManager.rollbackEmpAllTable(userTrans);
					dbManager.updateEmpPartTable(userTrans, "F");
					LogHandler.info("员工编号为" + userTrans.getEMPLOYEEID()
							+ "的新增数据在cordys组织用户中已经存在");
					dbManager.saveErrorMessage(userTrans,
							"员工编号为" + userTrans.getEMPLOYEEID()
									+ "的新增数据在cordys组织用户中已经存在");
					return;
				} else if (userTrans.getMODIFYACTION().equals("U")) {
					connection.modify(orgUserEntry.getDN(),
							new LDAPModification(LDAPModification.REPLACE,
									orgDesc));
					if (roleArray != null) {
						connection.modify(orgUserEntry.getDN(),
								new LDAPModification(LDAPModification.REPLACE,
										new LDAPAttribute("role", roleArray)));
					}
				}
			} catch (LDAPException e) {
				if (userTrans.getMODIFYACTION().equals("I")
						|| userTrans.getMODIFYACTION().equals("U")) {
					orgUserEntry = new LDAPEntry(organizationUserDN,
							organizationUserAttrs);
					connection.add(orgUserEntry);
				} else {
					dbManager.rollbackEmpAllTable(userTrans);
					dbManager.updateEmpPartTable(userTrans, "F");
					LogHandler.info("员工编号为" + userTrans.getEMPLOYEEID()
							+ "的修改数据在cordys组织用户中不存在");
					dbManager.saveErrorMessage(userTrans,
							"员工编号为" + userTrans.getEMPLOYEEID()
									+ "的修改数据在cordys组织用户中不存在");
					return;
				}
			}
			// 在participant表中创建或修改用户信息
			cordysDBManager.updateParticipantInfo(dbManager,
					organizationUserDN,
					PropertiesClass.getKeyValue("cordysldap.defaultcontext"),
					userTrans);
			// set updateResult as 'S' in EMPLOYEE_INFO_TRANS table to identify
			// sync successfully
			dbManager.updateEmpPartTable(userTrans, "S");
		} catch (LDAPException e) {
			dbManager.rollbackEmpAllTable(userTrans);
			dbManager.updateEmpPartTable(userTrans, "F");
			dbManager.saveErrorMessage(userTrans, e.getMessage());
			LogHandler.error(
					"error occured when importing user into cordys ldap", e);
			return;
			// throw new
			// RuntimeException("error occured when importing user into cordys ldap");
		}
	}

	private void importParttimeUser(EMPLOYEE_INFO_TRANS userInfoObject,
			DBManager dbManager, CordysLDAPManager cordysLDAPManager, int num) {
		// LogHandler.info("要导入的用户信息:\n" + userObject.toString());
		LDAPEntry authenticationUserEntry = null;
		LDAPEntry organizationUserEntry = null;
		try {
			if (userInfoObject.getMODIFYACTION().equals("i")) {
				dbManager.insertEmpAllTable(userInfoObject);
			} else if (userInfoObject.getMODIFYACTION().equals("u")) {
				dbManager.updateEmpAllTable(userInfoObject);
			}
			// authentication user
			String authenticationUserDN = "cn="
					+ userInfoObject.getEMPLOYEEID()
					+ ","
					+ PropertiesClass
							.getKeyValue("cordysldap.authenticationuserbase");
			if ("".equals(userInfoObject.getEMPLOYEEID())) {
				// LogHandler.info("LoginID can't be empty.");
				return;
			}
			LDAPAttributeSet authenticationUserAttrs = new LDAPAttributeSet();
			LDAPAttribute loginid = new LDAPAttribute("cn", ""
					+ userInfoObject.getEMPLOYEEID());
			authenticationUserAttrs.add(loginid);
			LDAPAttribute osidentity = new LDAPAttribute("osidentity", ""
					+ userInfoObject.getEMPLOYEEID());
			authenticationUserAttrs.add(osidentity);

			String[] objectClass = new String[2];
			objectClass[0] = "top";
			objectClass[1] = "busauthenticationuser";
			LDAPAttribute objectclass = new LDAPAttribute("objectclass",
					objectClass);
			authenticationUserAttrs.add(objectclass);
			LDAPAttribute authenticationType = new LDAPAttribute(
					"authenticationtype", "custom");
			authenticationUserAttrs.add(authenticationType);
			LDAPAttribute defaultcontext = new LDAPAttribute("defaultcontext",
					PropertiesClass.getKeyValue("cordysldap.defaultcontext"));
			authenticationUserAttrs.add(defaultcontext);

			LDAPAttribute userPassword = new LDAPAttribute("userPassword",
					PropertiesClass.getKeyValue("cordysldap.userPassword"));
			authenticationUserAttrs.add(userPassword);

			// 邮箱
			if (userInfoObject.getLYFMAIL() != null
					&& !"".equals(userInfoObject.getLYFMAIL())) {
				LDAPAttribute mail = new LDAPAttribute("mail",
						userInfoObject.getLYFMAIL());
				authenticationUserAttrs.add(mail);
			}

			// 姓名
			LDAPAttribute description = new LDAPAttribute("description",
					userInfoObject.getSN() + userInfoObject.getGIVENNAME());
			authenticationUserAttrs.add(description);

			// 电话号码
			if (userInfoObject.getWEBSHORT() != null
					&& !"".equals(userInfoObject.getWEBSHORT())) {
				LDAPAttribute telephoneNumber = new LDAPAttribute(
						"telephoneNumber", userInfoObject.getWEBSHORT());
				authenticationUserAttrs.add(telephoneNumber);
			}

			// 人员状态，0在职；1离职
			LDAPAttribute enable = null;
			if ("0".equals(userInfoObject.getPERSONALSTATUS())) {
				enable = new LDAPAttribute("enable", "true");
				authenticationUserAttrs.add(enable);
			} else {
				enable = new LDAPAttribute("enable", "false");
				authenticationUserAttrs.add(enable);
			}

			// 组织简称
			LDAPAttribute orgShort = new LDAPAttribute("o",
					userInfoObject.getORGSHORT());
			authenticationUserAttrs.add(orgShort);

			// organization user
			String organizationDN = PropertiesClass
					.getKeyValue("cordysldap.organizationuserbase");
			String organizationUserDN = "cn=" + userInfoObject.getEMPLOYEEID()
					+ "," + organizationDN;
			LDAPAttributeSet organizationUserAttrs = new LDAPAttributeSet();
			LDAPAttribute organizationUserCNAttr = new LDAPAttribute("cn", ""
					+ userInfoObject.getEMPLOYEEID());
			organizationUserAttrs.add(organizationUserCNAttr);
			LDAPAttribute authenticationuserAttr = new LDAPAttribute(
					"authenticationuser", authenticationUserDN);
			organizationUserAttrs.add(authenticationuserAttr);
			String[] objectClass2 = new String[3];
			objectClass2[0] = "top";
			objectClass2[1] = "busorganizationaluser";
			objectClass2[2] = "busorganizationalobject";
			LDAPAttribute objectclass3 = new LDAPAttribute("objectclass",
					objectClass2);
			organizationUserAttrs.add(objectclass3);

			LDAPAttribute orgDesc = new LDAPAttribute("description",
					userInfoObject.getSN() + userInfoObject.getGIVENNAME());
			organizationUserAttrs.add(orgDesc);
			// to set default roles
			String[] defaultRoleArray = null;
			if (PropertiesClass.getKeyValue("cordysldap.roles") != null
					&& PropertiesClass.getKeyValue("cordysldap.roles").equals(
							"") == false) {
				defaultRoleArray = PropertiesClass.getKeyValue(
						"cordysldap.roles").split(";");
				// LDAPAttribute attribute = new LDAPAttribute("role",
				// roleArray);
				// organizationUserAttrs.add(attribute);
			}
			// to set postion role
			String roleDN = dbManager
					.getCordysRole(userInfoObject.getCOMPANYCODE(),
							userInfoObject.getDEPTID(),
							userInfoObject.getSTATIONCODE());
			String[] roleArray = null;
			if (roleDN != null && !"".equals(roleDN)) {
				if (!"TBD".equals(roleDN.toUpperCase())) {
					int roleNumber = 1;
					if (defaultRoleArray != null) {
						roleNumber = roleNumber + defaultRoleArray.length;
					}
					roleArray = new String[roleNumber];
					if (defaultRoleArray != null)
						for (int defaultRoleNum = 0; defaultRoleNum < defaultRoleArray.length; defaultRoleNum++) {
							roleArray[defaultRoleNum] = defaultRoleArray[defaultRoleNum];
						}
					roleArray[roleArray.length - 1] = roleDN;
				} else {
					roleArray = defaultRoleArray;
				}
				if (roleArray != null) {
					LDAPAttribute orgRoles = new LDAPAttribute("role",
							roleArray);
					organizationUserAttrs.add(orgRoles);
				}
			} else {
				dbManager.rollbackEmpAllTable(userInfoObject);
				dbManager.updateEmpPartTable(userInfoObject, "F");
				dbManager.saveErrorMessage(userInfoObject, "员工编号为"
						+ userInfoObject.getEMPLOYEEID()
						+ "的数据在角色匹配表中未找到对应的cordys角色信息，请确认");
				LogHandler.info("员工编号为" + userInfoObject.getEMPLOYEEID()
						+ "的数据在角色匹配表中未找到对应的cordys角色信息，请确认");
				return;
			}

			// deal with organization user
			try {
				organizationUserEntry = connection.read(organizationUserDN);
				if (userInfoObject.getMODIFYACTION().equals("I")) {
					dbManager.rollbackEmpAllTable(userInfoObject);
					dbManager.updateEmpPartTable(userInfoObject, "F");
					LogHandler.info("员工编号为" + userInfoObject.getEMPLOYEEID()
							+ "的新增数据在cordys组织用户中已经存在");
					dbManager.saveErrorMessage(userInfoObject, "员工编号为"
							+ userInfoObject.getEMPLOYEEID()
							+ "的新增数据在cordys组织用户中已经存在");
					return;
				} else if (userInfoObject.getMODIFYACTION().equals("U")) {
					connection.modify(organizationUserEntry.getDN(),
							new LDAPModification(LDAPModification.REPLACE,
									orgDesc));
					if (roleArray != null) {
						connection.modify(organizationUserEntry.getDN(),
								new LDAPModification(LDAPModification.REPLACE,
										new LDAPAttribute("role", roleArray)));
					}
				}
			} catch (LDAPException e) {
				if (userInfoObject.getMODIFYACTION().equals("I")
						|| userInfoObject.getMODIFYACTION().equals("U")) {
					organizationUserEntry = new LDAPEntry(organizationUserDN,
							organizationUserAttrs);
					connection.add(organizationUserEntry);
				} else {
					dbManager.rollbackEmpAllTable(userInfoObject);
					dbManager.updateEmpPartTable(userInfoObject, "F");
					LogHandler.info("员工编号为" + userInfoObject.getEMPLOYEEID()
							+ "的修改数据在cordys组织用户中不存在");
					dbManager.saveErrorMessage(userInfoObject, "员工编号为"
							+ userInfoObject.getEMPLOYEEID()
							+ "的修改数据在cordys组织用户中不存在");
					return;
				}
			}
			// 在participant表中创建或修改用户信息
			cordysDBManager.updateParticipantInfo(dbManager,
					organizationUserDN,
					PropertiesClass.getKeyValue("cordysldap.defaultcontext"),
					userInfoObject);
			// set updateResult as 'S' in EMPLOYEE_INFO_TRANS table to identify
			// sync successfully
			dbManager.updateEmpPartTable(userInfoObject, "S");
		} catch (LDAPException e) {
			dbManager.rollbackEmpAllTable(userInfoObject);
			dbManager.updateEmpPartTable(userInfoObject, "F");
			dbManager.saveErrorMessage(userInfoObject, e.getMessage());
			LogHandler.error(
					"error occured when importing user into cordys ldap", e);
			return;
			// throw new
			// RuntimeException("error occured when importing user into cordys ldap");
		}
	}
}

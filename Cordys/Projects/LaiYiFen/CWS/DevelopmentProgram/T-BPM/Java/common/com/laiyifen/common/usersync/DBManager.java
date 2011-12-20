package com.laiyifen.common.usersync;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.laiyifen.common.core.EMPLOYEE_INFO_TRANS;

public class DBManager {
	private static final int readConnections = 2;

	private Connection conn = null;

	private ExternalDBConnectionManager connMgr;

	Integer userID = 0;
	Long roleDNLong;
	Long deptNoLong;

	public DBManager() {
		initialize();
	}

	public void initialize() {
		connMgr = ExternalDBConnectionManager.getInstance(
				PropertiesClass.getKeyValue("db.driver"),
				PropertiesClass.getKeyValue("db.url"),
				PropertiesClass.getKeyValue("db.user"),
				PropertiesClass.getKeyValue("db.password"), readConnections);
		conn = connMgr.getConnection("ConnectionPool", 15000);
		if (conn == null) {
			LogHandler.error("Can not connect to the database");
			throw new RuntimeException("Can not connect to the database");
		}
	}

	public void dispose() {
		connMgr.freeConnection("ConnectionPool", conn);
		connMgr.release();
	}

	public ArrayList<EMPLOYEE_INFO_TRANS> getAllUsers() {
		ArrayList<EMPLOYEE_INFO_TRANS> users = new ArrayList<EMPLOYEE_INFO_TRANS>();
		String tableName = PropertiesClass.getKeyValue("db.table");
		String empID = PropertiesClass.getKeyValue("db.mapping.EMPLOYEEID");
		String EnglishNm = PropertiesClass
				.getKeyValue("db.mapping.ENGLISHNAME");
		String sn = PropertiesClass.getKeyValue("db.mapping.SN");
		String givenNm = PropertiesClass.getKeyValue("db.mapping.GIVENNAME");
		String pinYin = PropertiesClass.getKeyValue("db.mapping.CHINESEPINYIN");
		String birthDay = PropertiesClass.getKeyValue("db.mapping.BIRTHDAY");
		String birthPlace = PropertiesClass
				.getKeyValue("db.mapping.BIRTHPLACE");
		String sex = PropertiesClass.getKeyValue("db.mapping.SEX");
		String hireDate = PropertiesClass.getKeyValue("db.mapping.HIREDATE");
		String leaveDate = PropertiesClass.getKeyValue("db.mapping.LEAVEDATE");
		String deptNm = PropertiesClass.getKeyValue("db.mapping.DEPTNAME");
		String deptId = PropertiesClass.getKeyValue("db.mapping.DEPTID");
		String orgShort = PropertiesClass.getKeyValue("db.mapping.ORGSHORT");
		String workTime = PropertiesClass.getKeyValue("db.mapping.WORKTIME");
		String lyfMail = PropertiesClass.getKeyValue("db.mapping.LYFMAIL");
		String postCd = PropertiesClass.getKeyValue("db.mapping.POSTCODE");
		String empType = PropertiesClass.getKeyValue("db.mapping.EMPLOYEETYPE");
		String managerNum = PropertiesClass
				.getKeyValue("db.mapping.MANAGERNUMBER");
		String workArea = PropertiesClass.getKeyValue("db.mapping.WORKAREA");
		String costCenter = PropertiesClass
				.getKeyValue("db.mapping.COSTCENTER");
		String phoneNum = PropertiesClass.getKeyValue("db.mapping.PHONENUMBER");
		String webShort = PropertiesClass.getKeyValue("db.mapping.WEBSHORT");
		String personalMail = PropertiesClass
				.getKeyValue("db.mapping.PERSONALMAIL");
		String mobile = PropertiesClass.getKeyValue("db.mapping.MOBILE");
		String companyCd = PropertiesClass
				.getKeyValue("db.mapping.COMPANYCODE");
		String station = PropertiesClass.getKeyValue("db.mapping.STATION");
		String stationCd = PropertiesClass
				.getKeyValue("db.mapping.STATIONCODE");
		String modifyAction = PropertiesClass
				.getKeyValue("db.mapping.MODIFYACTION");
		String personalStatus = PropertiesClass
				.getKeyValue("db.mapping.PERSONALSTATUS");
		String updateResult = PropertiesClass
				.getKeyValue("db.mapping.UPDATERESULT");
		try {
			Statement stmt = conn.createStatement();
			String sql = "SELECT " + empID + "," + EnglishNm + "," + sn + ","
					+ givenNm + "," + pinYin + "," + birthDay + ","
					+ birthPlace + "," + sex + "," + hireDate + "," + leaveDate
					+ "," + deptNm + "," + deptId + "," + orgShort + ","
					+ workTime + "," + lyfMail + "," + postCd + "," + empType
					+ "," + managerNum + "," + workArea + "," + costCenter
					+ "," + phoneNum + "," + webShort + "," + personalMail
					+ "," + mobile + "," + companyCd + "," + station + ","
					+ stationCd + "," + modifyAction + "," + personalStatus
					+ " FROM " + tableName + " WHERE " + updateResult
					+ " = 'N'" + " OR " + updateResult + " = 'F'";
			ResultSet rs = stmt.executeQuery(sql);
			int number = 0;
			while (rs.next()) {
				EMPLOYEE_INFO_TRANS userPartInfo = new EMPLOYEE_INFO_TRANS();

				userPartInfo.makeTransient();
				userPartInfo.setEMPLOYEEID(rs.getString(empID));
				userPartInfo.setENGLISHNAME(rs.getString(EnglishNm));
				userPartInfo.setSN(rs.getString(sn));
				userPartInfo.setGIVENNAME(rs.getString(givenNm));
				userPartInfo.setCHINESEPINYIN(rs.getString(pinYin));
				userPartInfo.setBIRTHDAY(rs.getString(birthDay));
				userPartInfo.setHOMEPLACE(rs.getString(birthPlace));
				userPartInfo.setSEX(rs.getString(sex));
				userPartInfo.setHIREDATE(rs.getString(hireDate));
				userPartInfo.setLEAVEDATE(rs.getString(leaveDate));
				userPartInfo.setDEPTNAME(rs.getString(deptNm));
				userPartInfo.setDEPTID(rs.getString(deptId));
				userPartInfo.setORGSHORT(rs.getString(orgShort));
				userPartInfo.setWORKTIME(rs.getString(workTime));
				userPartInfo.setLYFMAIL(rs.getString(lyfMail));
				userPartInfo.setPOSTCODE(rs.getString(postCd));
				userPartInfo.setEMPLOYEETYPE(rs.getString(empType));
				userPartInfo.setMANAGERNUMBER(rs.getInt(managerNum));
				userPartInfo.setWORKAREA(rs.getString(workArea));
				userPartInfo.setCOSTCENTER(rs.getString(costCenter));
				userPartInfo.setPHONENUMBER(rs.getString(phoneNum));
				userPartInfo.setWEBSHORT(rs.getString(webShort));
				userPartInfo.setPERSONALMAIL(rs.getString(personalMail));
				userPartInfo.setMOBILE(rs.getString(mobile));
				userPartInfo.setCOMPANYCODE(rs.getString(companyCd));
				userPartInfo.setSTATION(rs.getString(station));
				userPartInfo.setSTATIONCODE(rs.getString(stationCd));
				userPartInfo.setMODIFYACTION(rs.getString(modifyAction));
				userPartInfo.setPERSONALSTATUS(rs.getString(personalStatus));

				users.add(userPartInfo);
				number++;
			}
			LogHandler.info("Database has:" + number + "User");
		} catch (SQLException e) {
			LogHandler.error("从employee_info_trans表中获取用户信息时出错", e);
			throw new RuntimeException("从employee_info_trans表中获取用户信息时出错");
		}
		return users;
	}

	public String getCordysRole(String CompanyCd, String DeptCd,
			String StationCd) {
		String returnValue = "";
		try {
			Statement stmt = conn.createStatement();
			String sql = "SELECT ROLEDN FROM EMPLOYEE_ROLE_TRANSMAP WHERE COMPANYCODE='"
					+ CompanyCd
					+ "'"
					+ " AND DEPTID='"
					+ DeptCd
					+ "' AND STATIONID='" + StationCd + "'";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				returnValue = rs.getString("ROLEDN");
			}
		} catch (SQLException e) {
			LogHandler.error("获取cordys角色信息时出错，出错的岗位编号为：" + StationCd, e);
			// throw new
			// RuntimeException("Error occured when retrieving data from db table");
		}
		return returnValue;
	}

	public void insertEmpAllTable(EMPLOYEE_INFO_TRANS empPart) {
		String tableName = PropertiesClass.getKeyValue("db.masterTable");
		String partTimeEmpTableName = PropertiesClass
				.getKeyValue("db.partTimeTable");
		String empID = PropertiesClass.getKeyValue("db.mapping.EMPLOYEEID");
		String EnglishNm = PropertiesClass
				.getKeyValue("db.mapping.ENGLISHNAME");
		String sn = PropertiesClass.getKeyValue("db.mapping.SN");
		String givenNm = PropertiesClass.getKeyValue("db.mapping.GIVENNAME");
		String pinYin = PropertiesClass.getKeyValue("db.mapping.CHINESEPINYIN");
		String birthDay = PropertiesClass.getKeyValue("db.mapping.BIRTHDAY");
		String birthPlace = PropertiesClass
				.getKeyValue("db.mapping.BIRTHPLACE");
		String sex = PropertiesClass.getKeyValue("db.mapping.SEX");
		String hireDate = PropertiesClass.getKeyValue("db.mapping.HIREDATE");
		String leaveDate = PropertiesClass.getKeyValue("db.mapping.LEAVEDATE");
		String deptNm = PropertiesClass.getKeyValue("db.mapping.DEPTNAME");
		String deptId = PropertiesClass.getKeyValue("db.mapping.DEPTID");
		String orgShort = PropertiesClass.getKeyValue("db.mapping.ORGSHORT");
		String workTime = PropertiesClass.getKeyValue("db.mapping.WORKTIME");
		String lyfMail = PropertiesClass.getKeyValue("db.mapping.LYFMAIL");
		String postCd = PropertiesClass.getKeyValue("db.mapping.POSTCODE");
		String empType = PropertiesClass.getKeyValue("db.mapping.EMPLOYEETYPE");
		String managerNum = PropertiesClass
				.getKeyValue("db.mapping.MANAGERNUMBER");
		String workArea = PropertiesClass.getKeyValue("db.mapping.WORKAREA");
		String costCenter = PropertiesClass
				.getKeyValue("db.mapping.COSTCENTER");
		String phoneNum = PropertiesClass.getKeyValue("db.mapping.PHONENUMBER");
		String webShort = PropertiesClass.getKeyValue("db.mapping.WEBSHORT");
		String personalMail = PropertiesClass
				.getKeyValue("db.mapping.PERSONALMAIL");
		String mobile = PropertiesClass.getKeyValue("db.mapping.MOBILE");
		String companyCd = PropertiesClass
				.getKeyValue("db.mapping.COMPANYCODE");
		String station = PropertiesClass.getKeyValue("db.mapping.STATION");
		String stationCd = PropertiesClass
				.getKeyValue("db.mapping.STATIONCODE");
		String personalStatus = PropertiesClass
				.getKeyValue("db.mapping.PERSONALSTATUS");
		if ("i".equals(empPart.getMODIFYACTION())
				|| "u".equals(empPart.getMODIFYACTION()))
			tableName = partTimeEmpTableName;
		try {
			Statement stmt = conn.createStatement();
			String sql = "INSERT INTO " + tableName + " (" + empID + ","
					+ EnglishNm + "," + sn + "," + givenNm + "," + pinYin + ","
					+ birthDay + "," + birthPlace + "," + sex + "," + hireDate
					+ "," + leaveDate + "," + deptNm + "," + deptId + ","
					+ orgShort + "," + workTime + "," + lyfMail + "," + postCd
					+ "," + empType + "," + managerNum + "," + workArea + ","
					+ costCenter + "," + phoneNum + "," + webShort + ","
					+ personalMail + "," + mobile + "," + companyCd + ","
					+ station + "," + stationCd + "," + personalStatus + ")"
					+ " VALUES('" + empPart.getEMPLOYEEID() + "','"
					+ empPart.getENGLISHNAME() + "','" + empPart.getSN()
					+ "','" + empPart.getGIVENNAME() + "','"
					+ empPart.getCHINESEPINYIN() + "','"
					+ empPart.getBIRTHDAY() + "','" + empPart.getHOMEPLACE()
					+ "','" + empPart.getSEX() + "','" + empPart.getHIREDATE()
					+ "','" + empPart.getLEAVEDATE() + "','"
					+ empPart.getDEPTNAME() + "'," + empPart.getDEPTID() + ",'"
					+ empPart.getORGSHORT() + "','" + empPart.getWORKTIME()
					+ "','" + empPart.getLYFMAIL() + "','"
					+ empPart.getPOSTCODE() + "','" + empPart.getEMPLOYEETYPE()
					+ "'," + empPart.getMANAGERNUMBER() + ",'"
					+ empPart.getWORKAREA() + "','" + empPart.getCOSTCENTER()
					+ "','" + empPart.getPHONENUMBER() + "','"
					+ empPart.getWEBSHORT() + "','" + empPart.getPERSONALMAIL()
					+ "','" + empPart.getMOBILE() + "','"
					+ empPart.getCOMPANYCODE() + "','" + empPart.getSTATION()
					+ "','" + empPart.getSTATIONCODE() + "','"
					+ empPart.getPERSONALSTATUS() + "')";

			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			LogHandler.error("插入EMPLOYEE_INFO表数据时出错，出错的员工编号为：" + empID, e);
			// throw new
			// RuntimeException("Error occured when inserting data into db table");
		}
	}

	public void updateEmpAllTable(EMPLOYEE_INFO_TRANS empPart) {
		String tableName = PropertiesClass.getKeyValue("db.masterTable");
		String partTimeEmpTableName = PropertiesClass
				.getKeyValue("db.partTimeTable");
		String empID = PropertiesClass.getKeyValue("db.mapping.EMPLOYEEID");
		String EnglishNm = PropertiesClass
				.getKeyValue("db.mapping.ENGLISHNAME");
		String sn = PropertiesClass.getKeyValue("db.mapping.SN");
		String givenNm = PropertiesClass.getKeyValue("db.mapping.GIVENNAME");
		String pinYin = PropertiesClass.getKeyValue("db.mapping.CHINESEPINYIN");
		String birthDay = PropertiesClass.getKeyValue("db.mapping.BIRTHDAY");
		String birthPlace = PropertiesClass
				.getKeyValue("db.mapping.BIRTHPLACE");
		String sex = PropertiesClass.getKeyValue("db.mapping.SEX");
		String hireDate = PropertiesClass.getKeyValue("db.mapping.HIREDATE");
		String leaveDate = PropertiesClass.getKeyValue("db.mapping.LEAVEDATE");
		String deptNm = PropertiesClass.getKeyValue("db.mapping.DEPTNAME");
		String deptId = PropertiesClass.getKeyValue("db.mapping.DEPTID");
		String orgShort = PropertiesClass.getKeyValue("db.mapping.ORGSHORT");
		String workTime = PropertiesClass.getKeyValue("db.mapping.WORKTIME");
		String lyfMail = PropertiesClass.getKeyValue("db.mapping.LYFMAIL");
		String postCd = PropertiesClass.getKeyValue("db.mapping.POSTCODE");
		String empType = PropertiesClass.getKeyValue("db.mapping.EMPLOYEETYPE");
		String managerNum = PropertiesClass
				.getKeyValue("db.mapping.MANAGERNUMBER");
		String workArea = PropertiesClass.getKeyValue("db.mapping.WORKAREA");
		String costCenter = PropertiesClass
				.getKeyValue("db.mapping.COSTCENTER");
		String phoneNum = PropertiesClass.getKeyValue("db.mapping.PHONENUMBER");
		String webShort = PropertiesClass.getKeyValue("db.mapping.WEBSHORT");
		String personalMail = PropertiesClass
				.getKeyValue("db.mapping.PERSONALMAIL");
		String mobile = PropertiesClass.getKeyValue("db.mapping.MOBILE");
		String companyCd = PropertiesClass
				.getKeyValue("db.mapping.COMPANYCODE");
		String station = PropertiesClass.getKeyValue("db.mapping.STATION");
		String stationCd = PropertiesClass
				.getKeyValue("db.mapping.STATIONCODE");
		String personalStatus = PropertiesClass
				.getKeyValue("db.mapping.PERSONALSTATUS");
		if ("i".equals(empPart.getMODIFYACTION())
				|| "u".equals(empPart.getMODIFYACTION()))
			tableName = partTimeEmpTableName;

		try {
			Statement stmt = conn.createStatement();
			String sql = "UPDATE " + tableName + " SET " + EnglishNm + "='"
					+ empPart.getENGLISHNAME() + "'," + sn + "='"
					+ empPart.getSN() + "'," + givenNm + "='"
					+ empPart.getGIVENNAME() + "'," + pinYin + "='"
					+ empPart.getCHINESEPINYIN() + "'," + birthDay + "='"
					+ empPart.getBIRTHDAY() + "'," + birthPlace + "='"
					+ empPart.getHOMEPLACE() + "'," + sex + "='"
					+ empPart.getSEX() + "'," + hireDate + "='"
					+ empPart.getHIREDATE() + "'," + leaveDate + "='"
					+ empPart.getLEAVEDATE() + "'," + deptNm + "='"
					+ empPart.getDEPTNAME() + "'," + deptId + "="
					+ empPart.getDEPTID() + "," + orgShort + "='"
					+ empPart.getORGSHORT() + "'," + workTime + "='"
					+ empPart.getWORKTIME() + "'," + lyfMail + "='"
					+ empPart.getLYFMAIL() + "'," + postCd + "='"
					+ empPart.getPOSTCODE() + "'," + empType + "='"
					+ empPart.getEMPLOYEETYPE() + "'," + managerNum + "="
					+ empPart.getMANAGERNUMBER() + "," + workArea + "='"
					+ empPart.getWORKAREA() + "'," + costCenter + "='"
					+ empPart.getCOSTCENTER() + "'," + phoneNum + "='"
					+ empPart.getPHONENUMBER() + "'," + webShort + "='"
					+ empPart.getWEBSHORT() + "'," + personalMail + "='"
					+ empPart.getPERSONALMAIL() + "'," + mobile + "='"
					+ empPart.getMOBILE() + "'," + companyCd + "='"
					+ empPart.getCOMPANYCODE() + "'," + station + "='"
					+ empPart.getSTATION() + "'," + stationCd + "='"
					+ empPart.getSTATIONCODE() + "'," + personalStatus + "='"
					+ empPart.getPERSONALSTATUS() + "'" + " WHERE " + empID
					+ "='" + empPart.getEMPLOYEEID() + "'";

			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			LogHandler.error("更新EMPLOYEE_INFO表数据时出错，出错的员工编号为：" + empID, e);
			// throw new
			// RuntimeException("Error occured when updating data into db table");
		}
	}

	public void deleteEmpAllTable(EMPLOYEE_INFO_TRANS empPart) {
		String tableName = PropertiesClass.getKeyValue("db.masterTable");
		String partTimeEmpTableName = PropertiesClass
				.getKeyValue("db.partTimeTable");
		String empID = PropertiesClass.getKeyValue("db.mapping.EMPLOYEEID");
		String personalStatus = PropertiesClass
				.getKeyValue("db.mapping.PERSONALSTATUS");

		if ("i".equals(empPart.getMODIFYACTION())
				|| "u".equals(empPart.getMODIFYACTION()))
			tableName = partTimeEmpTableName;

		try {
			Statement stmt = conn.createStatement();
			String sql = "UPDATE " + tableName + " SET " + personalStatus
					+ "='1' WHERE " + empID + "='" + empPart.getEMPLOYEEID()
					+ "'";

			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// operation failed
			updateEmpPartTable(empPart, "F");
			LogHandler.error("删除EMPLOYEE_INFO表数据时出错，出错的员工编号为：" + empID, e);
			// throw new
			// RuntimeException("Error occured when deleting data from db table");
		}
	}

	public void updateEmpPartTable(EMPLOYEE_INFO_TRANS empPart, String succeedYn) {
		String tableName = PropertiesClass.getKeyValue("db.table");
		String empID = PropertiesClass.getKeyValue("db.mapping.EMPLOYEEID");
		String updateResult = PropertiesClass
				.getKeyValue("db.mapping.UPDATERESULT");

		try {
			Statement stmt = conn.createStatement();
			String sql = "UPDATE " + tableName + " SET " + updateResult + "='"
					+ succeedYn + "' WHERE " + empID + "='"
					+ empPart.getEMPLOYEEID() + "'";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			LogHandler
					.error("更新EMPLOYEE_INFO_TRANS表UPDATERESULT字段时出错，出错的员工编号为："
							+ empID, e);
			// throw new
			// RuntimeException("Error occured when deleting data from db table");
		}
	}

	public void rollbackEmpAllTable(EMPLOYEE_INFO_TRANS empPart) {
		String tableName = PropertiesClass.getKeyValue("db.masterTable");
		String partTimeEmpTableName = PropertiesClass
				.getKeyValue("db.partTimeTable");
		String empID = PropertiesClass.getKeyValue("db.mapping.EMPLOYEEID");
		if ("i".equals(empPart.getMODIFYACTION())
				|| "u".equals(empPart.getMODIFYACTION()))
			tableName = partTimeEmpTableName;
		try {
			Statement stmt = conn.createStatement();
			String sql = "DELETE FROM " + tableName + " WHERE " + empID + "='"
					+ empPart.getEMPLOYEEID() + "'";

			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// operation failed
			// updateEmpPartTable(empPart, "F");
			LogHandler.error("回滚EMPLOYEE_INFO表数据时出错，出错的员工编号为：" + empID, e);
			// throw new
			// RuntimeException("Error occured when deleting data from db table");
		}
	}

	public void saveErrorMessage(EMPLOYEE_INFO_TRANS empPart, String errorMsg) {
		String tableName = PropertiesClass.getKeyValue("db.table");
		String empID = PropertiesClass.getKeyValue("db.mapping.EMPLOYEEID");
		String errorMessage = PropertiesClass
				.getKeyValue("db.mapping.ERRORMESSAGE");
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			String sql = "UPDATE " + tableName + " SET " + errorMessage + "='"
					+ errorMsg + "' WHERE " + empID + "='"
					+ empPart.getEMPLOYEEID() + "'";

			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// operation failed
			// updateEmpPartTable(empPart, "F");
			LogHandler.error("更新EMPLOYEE_INFO_TRANS表ERRORMESSAGE字段时出错，出错的员工编号为：" + empID, e);
			// throw new
			// RuntimeException("Error occured when deleting data from db table");
		}finally{
			closeStatment(stmt);
		}
	}
	
	public void closeStatment(Statement stmt) {
		if(stmt != null){
			try {
				stmt.close();
			} catch (SQLException e) {
				LogHandler.error("关闭数据库Statement出错.", e);				
			}
		}
	}
	
	public void closeResultSet(ResultSet rs) {
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				LogHandler.error("关闭数据库ResultSet出错.", e);				
			}
		}
	}
	
}

package com.laiyifen.common.usersync;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import com.laiyifen.common.core.EMPLOYEE_INFO_TRANS;
import com.laiyifen.common.usersync.GUIDUtil;

public class CordysDBManager
{
    private static final int readConnections = 2;

    private Connection conn = null;

    private CordysDBConnectionManager connMgr;
    
    Statement stmt;
    
    public CordysDBManager()
    {
        initialize();
    }

    /**
     * 初始化
     */ 
    private void initialize()
    {
        //建立数据库联接
        connMgr = CordysDBConnectionManager.getInstance(
                PropertiesClass.getKeyValue("cordysdb.driver"),
                PropertiesClass.getKeyValue("cordysdb.url"), 
                PropertiesClass.getKeyValue("cordysdb.user"),
                PropertiesClass.getKeyValue("cordysdb.password"), readConnections);
        conn = connMgr.getConnection("ConnectionPool", 15000);
        try
        {
            stmt = conn.createStatement();
        }
        catch (SQLException e)
        {
        }
        if (conn == null)
        {
            LogHandler.error("Can not connect to cordys database");
            throw new RuntimeException("Can not connect to cordys database");
        }
    }

    /**
     * 释放连接
     */
    public void dispose()
    {
        try
        {
            stmt.close();
        }
        catch (SQLException e)
        {
        }
        connMgr.freeConnection("ConnectionPool", conn);
        connMgr.release();
    }
    
    public void updateParticipantInfo(DBManager dbManager,String distinctName, String organization, EMPLOYEE_INFO_TRANS userInfoObject)
    {
        if (distinctName == null || distinctName.equals(""))return;
        if (organization == null || organization.equals(""))return;
        
        String mail = userInfoObject.getLYFMAIL();
        String action = userInfoObject.getMODIFYACTION();
        String userName = userInfoObject.getSN()+userInfoObject.getGIVENNAME();
        if (mail == null || mail.equals(""))mail = "";
        try
        {
            
            String sql = "SELECT * from participant where DISTINCT_NAME='" + distinctName + "'";
            ResultSet rs = stmt.executeQuery(sql);
            long lastModify = new Date().getTime();
            if (rs.next())
            {
            	if(action.equals("U")){
                	sql = "UPDATE participant " +
                    "SET LAST_MODIFIED=" + lastModify + ","
                    +" MAIL_ID='" + mail + "',"
                    +" DISPLAY_NAME='" + userName +  "'"
                    +" WHERE DISTINCT_NAME='" + distinctName + "'";
                	stmt.execute(sql);
                }
            	return;
            }
            else
            {
                if(action.equals("I")){
                	String guid = GUIDUtil.getGUIDString();
                    
	                sql = "INSERT into participant " +
	                        "(PARTICIPANT_ID,DISTINCT_NAME,LAST_MODIFIED,ORGANIZATION,MAIL_ID," +
	                            "DISPLAY_NAME,DEFAULT_DELIVERY,AUTO_DELETE_TASK,DELETE_FLAG)" +
	                            " values ('"+ guid + "','" + distinctName +"'," + lastModify + ",'" + organization + "','" + mail + "','" +
	                            userName +  "','',0,0)";
	                stmt.execute(sql);
                }
            }
            
        }
        catch (SQLException e)
        {	
        	dbManager.saveErrorMessage(userInfoObject, "participant table:"+e.getMessage());
            LogHandler.error("Error occured when updating cordys participant info", e);
           // throw new RuntimeException("Error occured when updating cordys participant info");
        }
    }
}
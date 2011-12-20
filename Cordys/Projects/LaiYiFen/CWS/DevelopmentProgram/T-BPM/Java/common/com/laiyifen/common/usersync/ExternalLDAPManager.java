package com.laiyifen.common.usersync;

import java.util.ArrayList;

import com.laiyifen.common.core.EMPLOYEE_INFO_TRANS;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPSearchResults;

public class ExternalLDAPManager
{

    private LDAPConnection connection;

    public ExternalLDAPManager()
    {
        initialize();
    }

    @SuppressWarnings("deprecation")
	private void initialize()
    {
        String server = PropertiesClass.getKeyValue("ldap.hostname");
        String port = PropertiesClass.getKeyValue("ldap.port");
        String user = PropertiesClass.getKeyValue("ldap.user");
        String pwd = PropertiesClass.getKeyValue("ldap.password");
        int portNumber = Integer.parseInt(port);
        try
        {
            connection = new LDAPConnection();
            connection.connect(server, portNumber);
            connection.bind(LDAPConnection.LDAP_V3, user, pwd);
        }
        catch (LDAPException le)
        {
            LogHandler.error("error occured when connecting to external ldap", le);
        }
    }

    public void dispose()
    {
        if (connection.isConnected())
        {
            try
            {
                connection.disconnect();
            }
            catch (LDAPException e)
            {
            }
        }
    }
    
    public ArrayList<EMPLOYEE_INFO_TRANS> getAllUsers()
    {
        ArrayList<EMPLOYEE_INFO_TRANS> users = new ArrayList<EMPLOYEE_INFO_TRANS>();
        String loginID = PropertiesClass.getKeyValue("ldap.mapping.loginID");
        String eMail = PropertiesClass.getKeyValue("ldap.mapping.eMail");
        String phoneNumber = PropertiesClass.getKeyValue("ldap.mapping.phoneNumber");
        String dept = PropertiesClass.getKeyValue("ldap.mapping.dept");
        String userName = PropertiesClass.getKeyValue("ldap.mapping.userName");
        
        String baseDN= PropertiesClass.getKeyValue("ldap.base");
        try
        {
            int number = 0;
            LDAPSearchResults results = connection.search(baseDN, 1, "",  null, false);
            while(results.hasMore())
            {
                LDAPEntry entry = results.next();
                String loginIDValue = getLDAPEntryAttributeValue(entry,loginID);
                String eMailValue =  getLDAPEntryAttributeValue(entry,eMail);
                String phoneNumberValue =  getLDAPEntryAttributeValue(entry,phoneNumber);
                String deptValue =  getLDAPEntryAttributeValue(entry,dept);
                String userNameValue =  getLDAPEntryAttributeValue(entry,userName);
                
                EMPLOYEE_INFO_TRANS userInfo = new EMPLOYEE_INFO_TRANS();
                userInfo.setLYFMAIL(eMailValue);
                userInfo.setCHINESEPINYIN(loginIDValue);
                userInfo.setPHONENUMBER(phoneNumberValue);
                userInfo.setORGSHORT(deptValue);
                userInfo.setGIVENNAME(userNameValue);
                users.add(userInfo);
                number++;            
            }
            LogHandler.info("LDAPUser Count:" + number);
        }
        catch (LDAPException e)
        {
            LogHandler.info(e.getLDAPErrorMessage());
        }
        return users;
    }    
    
    /**
     * @param entry
     * @param attributeName
     * @return
     */
    private String getLDAPEntryAttributeValue(LDAPEntry entry, String attributeName)
    {
        if (attributeName == null || attributeName.equals(""))
        {
            return null;
        }
        if (entry.getAttribute(attributeName) == null)
        {
            return null;
        }
        else
        {
            String result = entry.getAttribute(attributeName).getStringValue();
            if (result != null && result.equals(""))
            {
                return null;
            }
            return result;
        }
    }

}

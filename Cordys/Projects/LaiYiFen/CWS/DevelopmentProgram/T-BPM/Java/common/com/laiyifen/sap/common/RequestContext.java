package com.laiyifen.sap.common;

import com.eibus.xml.nom.Document;

public class RequestContext
{
    private Object identifier;		//Thread ID
    private String userDN;			//organization user DN
    private Document document;
    private String orgDN;
    private String orgName;

	RequestContext(Document document, String user, Object identifier) 
    {
		this.document = document;
		this.userDN = user;
		this.identifier = identifier;
		setOrgDN();
    }
	
   /**
     * This method is used by the framework.
     * Returns the identifier of this RequestContext object
	 * @return The identifier
	 */
	public Object _getIdentifier()
    {
        return identifier;
    }
	private void setOrgDN()
	{
		int orgIndex = userDN.indexOf("o=");
		this.orgDN = userDN.substring(orgIndex);
		this. orgName = orgDN.substring(orgDN.indexOf("=") + 1, orgDN.indexOf(","));
	}
	public void setUserDN(String userDN)
	{
		this.userDN =userDN;
		setOrgDN();
	}
	
	public static RequestContext getRequestContext()
	{
		return RequestContextManager.getRequestContext(Thread.currentThread());
	}
    public String toString()
    {
        return "RequestContext[identifier=" + identifier + ", user=" + userDN + "]";
    }
	public String getOrganizationUser()
    {
        return userDN;
    }
	public String getOrganizationDN()
    {
        return orgDN;
    }
	public Object getOrgName()
    {
        return orgName;
    }
	public Document getDocument()
	{
		return document;
	}

}

package com.laiyifen.sap.common;

import java.util.Hashtable;
import com.eibus.xml.nom.Document;

public class RequestContextManager
{
	private static Hashtable<Object,Object> requestContextIndex = new Hashtable<Object, Object>();
	
	//TODO Hashtable is already synchronized so function call on it must not require separate synchronization on this class methods. Confirm via testing.
	public static RequestContext getRequestContext(Object requestContextIdentifier)
	{
		return (RequestContext)requestContextIndex.get(requestContextIdentifier);
	}

	public static RequestContext registerContext(Document document,String user,Object requestIdentifer)
	{
		RequestContext requestContext = new RequestContext(document,user,requestIdentifer);
		requestContextIndex.put(requestIdentifer, requestContext);
		
		return requestContext;
	}
	
	public static void _releaseContext(RequestContext requestContext)
	{
		requestContextIndex.remove(requestContext._getIdentifier());
	}
	
	/**
	 * This method is used by processMonitoredObject to set userDN to user defined DN. It must not be used any where else.
	 * Method internally also sets organization DN.
	 * */
	public static void _setUserForCurrentContext(String userDN)
	{
		Object requestIdentifer = Thread.currentThread();
		RequestContext requestContext = getRequestContext(requestIdentifer);
		requestContext.setUserDN(userDN);
		requestContextIndex.put(requestIdentifer, requestContext);
	}
}

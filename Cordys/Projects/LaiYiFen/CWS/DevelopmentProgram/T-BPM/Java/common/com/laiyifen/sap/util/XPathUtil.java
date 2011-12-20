package com.laiyifen.sap.util;

import com.eibus.xml.xpath.XPath;
import com.eibus.xml.xpath.XPathMetaInfo;
import java.util.Hashtable;

public class XPathUtil 
{
	private static XPathMetaInfo xpmetaInfo = new XPathMetaInfo();
	private static Hashtable<String, String> namespaces = new Hashtable<String, String>();
	private static final String DEFAULT_PREFIX = "lyf";
	private static int namespaceCounter = 0;
	private final static String LDAP_NAMESPACE = "http://schemas.cordys.com/1.0/ldap";
	private final static String XMLSTORE_NAMESPACE = "http://schemas.cordys.com/1.0/xmlstore";
	public final static String SOAP_NAMESPACE = "http://schemas.xmlsoap.org/soap/envelope/";
	private static Object lock = new Object();

	static {
		registerNamespace(LDAP_NAMESPACE);
		registerNamespace(XMLSTORE_NAMESPACE);
		registerNamespace(SOAP_NAMESPACE);
	}
	
	private XPathUtil(){
		super();
	}
	
	/**
	 * Internal method used by the framework.
	 * This method is used to register a namespace binding in XPathMetaInfo object. 
	 * It is assumed that every namespace is associated with a unique binding(prefix)
	 * 
	 * @param namespace - namespace to be registered
	 */
	public static void registerNamespace(String namespace)
	{
		if(!namespaces.containsKey(namespace))
		{
			synchronized(lock) {
				if(namespaces.containsKey(namespace))
					return;
				String prefix = generatePrefix();
				namespaces.put(namespace, prefix);
				xpmetaInfo.addNamespaceBinding(prefix, namespace);
			}
		}
	}
	
	private static String generatePrefix()
	{
		namespaceCounter = namespaceCounter + 1;
		return DEFAULT_PREFIX + namespaceCounter;
	}
	
	/**
	 * Utility method that returns the metainfo binding associated with 
	 * the given namespace. 
	 * 
	 * @param namespace - namespace whose associated binding is needed.
	 * @return associated binding or null.
	 */
	public static String getAssociatedBinding(String namespace)
	{
		String prefix = (String)namespaces.get(namespace);
		return prefix;
	}

	public static int firstMatch(String searchPath, int objectNode)
	{
		int resultNode = 0;
		resultNode = XPath.getFirstMatch(searchPath, xpmetaInfo, objectNode);
		return resultNode;
	}

	public static int[] findMatches(String searchPath, int objectNode)
	{
		int rNodes[] = XPath.getMatchingNodes(searchPath, xpmetaInfo, objectNode);
		return rNodes;
	}

	
	/**
	 * Use this function, when you are sure that xpath expression will return a single node.
	 * If object does not have namespace then it can be passed null.
	 * 
	 *  @deprecated - Use XPathUtil.firstMatch instead
	 **/
	public static int _getResultNode(String xpathExpression, int objectNode, String namespace)
	{
		int resultNode = 0;
		int[] resultNodes =_getResultNodes(xpathExpression, objectNode, namespace);
		if(resultNodes != null && resultNodes.length > 0) {
			resultNode = resultNodes[0];
		}
		return resultNode;
	}
	/**
	 * Use this function, when you are sure that xpath expression will return multiple single node.
	 * If object does not have namespace then it can be passed null
	 * 
	 * @deprecated - Use XPathUtil.match instead
	 **/
	public static int[] _getResultNodes(String xpathExpression, int objectNode, String namespace)
	{
		int[] resultNodes = null;
		
		XPath xpath = new XPath(xpathExpression);
		if(namespace!=null && !namespace.trim().equals(""))
		{	
			XPathMetaInfo xpathMetaInfo = new XPathMetaInfo(); 
			xpathMetaInfo.addNamespaceBinding(DEFAULT_PREFIX, namespace);
		}
		com.eibus.xml.xpath.XPathResult xpathResult = xpath.evaluate(objectNode);
		com.eibus.xml.xpath.NodeSet nodeSet = xpathResult.removeNodeSetFromResult();
		resultNodes = nodeSet.getElementNodes();
		return resultNodes;
	}
}
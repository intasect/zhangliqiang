package com.laiyifen.common;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.xml.ws.soap.SOAPFaultException;

import com.laiyifen.common.util.LaiYiFenCommunicationFailure;
import com.laiyifen.common.util.LaiYiFenConstants;
import com.laiyifen.common.util.XPathUtil;
import com.laiyifen.sap.common.RequestContext;
import com.laiyifen.sap.common.Utilities;
import com.cordys.cpc.bsf.busobject.BSF;
import com.cordys.cpc.bsf.busobject.exception.WSAppServerException;
import com.cordys.cpc.bsf.soap.SOAPRequestObject;
import com.eibus.connector.nom.CancelRequestException;
import com.eibus.connector.nom.Connector;
import com.eibus.directory.soap.DirectoryException;
import com.eibus.directory.soap.LDAPDirectory;
import com.eibus.exception.ExceptionGroup;
import com.eibus.exception.TimeoutException;
import com.eibus.util.logger.CordysLogger;
import com.eibus.util.logger.Severity;
import com.eibus.xml.nom.Document;
import com.eibus.xml.nom.Node;

public class ConnectorManager {
	private static Connector connector;
	private static CordysLogger laiyifenLogger = CordysLogger
			.getCordysLogger(ConnectorManager.class);

	private static String[] ignoreErrors = { "did not find data" };
	private static String defaultOrg = null;

	/**
	 * Method returns the connector
	 * 
	 * @return
	 */
	public static LDAPDirectory _getLDAPDirectory() {
		if (connector != null) {
			return connector.getMiddleware().getDirectory();
		}
		return null;
	}

	public static synchronized String getDefaultOrg() {
		if (defaultOrg == null)
			defaultOrg = BSF.getOrganization();
		return defaultOrg;

	}

	public static int _callSoapMethod(String nameSpace, String methodName,
			int children) throws LaiYiFenCommunicationFailure {
		int[] childrenNodes = new int[1];
		childrenNodes[0] = children;
		return _callSoapMethod(nameSpace, methodName, childrenNodes);
	}

	public static int _callSoapMethod(String nameSpace, String methodName,
			int[] childrenNodes) throws LaiYiFenCommunicationFailure {
		// int soapRequest =
		// RequestContext.getRequestContext().getDocument().createElement(methodName);
		int soapRequest = Utilities.createDocumentElement(methodName);
		Node.setAttribute(soapRequest, "xmlns", nameSpace);

		for (int i = 0; i < childrenNodes.length; i++) {
			Node.appendToChildren(childrenNodes[i], soapRequest);
		}
		int soapResponse = 0;
		try {
			soapResponse = _callSoapMethod(soapRequest);
		} catch (Exception e) {
			throw new LaiYiFenCommunicationFailure(e.getMessage(), e);
		} finally {
			Node.delete(soapRequest);
			soapRequest = 0;
		}
		return soapResponse;
	}

	public static int _callSoapMethod(int soapRequest)
			throws LaiYiFenCommunicationFailure {
		return _callSoapMethod(soapRequest, null);
	}

	public static int _callSoapMethod(int soapRequest, String userDn)
			throws LaiYiFenCommunicationFailure {
		int request = 0;
		int soapResponse = 0;
		int tmpSoapRequest = 0;
		boolean communicationFailed = false;
		String organizationalUser = null;
		String organization = userDn;

		RequestContext context = RequestContext.getRequestContext();
		if (context != null) {
			organizationalUser = userDn == null ? RequestContext
					.getRequestContext().getOrganizationUser() : userDn;
			organization = RequestContext.getRequestContext()
					.getOrganizationDN();
		}
		String methodName = Node.getName(soapRequest);
		String nameSpace = Node.getAttribute(soapRequest, "xmlns");

		try {
			if (context != null) {
				tmpSoapRequest = connector.createSOAPMethod(organizationalUser,
						organization, nameSpace, "TMP_NODE");
			} else {
				tmpSoapRequest = connector.createSOAPMethod(nameSpace,
						"TMP_NODE");

			}
			int bodyNode = Node.getParent(tmpSoapRequest);
			Node.duplicateAndAppendToChildren(soapRequest, soapRequest,
					bodyNode);

			int tmpNode = Node.getElement(bodyNode, "TMP_NODE");
			Node.unlink(tmpNode);
			Node.delete(tmpNode);
			tmpNode = 0;

			request = Node.getParent(bodyNode);

			if (laiyifenLogger.isEnabled(Severity.DEBUG)) {
				laiyifenLogger.debug("ConnectorManager: SoapRequest: "
						+ Node.writeToString(request, true));
			}
			soapResponse = connector.sendAndWait(request,
					LaiYiFenConstants._TRANSACTION_TIMEOUT);
			if (laiyifenLogger.isEnabled(Severity.DEBUG)) {
				laiyifenLogger.debug("ConnectorManager: SoapResponse: "
						+ Node.writeToString(soapResponse, true));
			}
		} catch (DirectoryException de) {
			communicationFailed = true;
			if (laiyifenLogger.isEnabled(Severity.ERROR)) {
				laiyifenLogger.log(Severity.ERROR, de.getMessage(), de);
			}
			throw new LaiYiFenCommunicationFailure(de.getMessage(), de);
		} catch (TimeoutException timeoutException) {
			communicationFailed = true;
			if (laiyifenLogger.isEnabled(Severity.ERROR)) {
				laiyifenLogger.log(Severity.ERROR,
						timeoutException.getMessage(), timeoutException);
			}
			throw new LaiYiFenCommunicationFailure(
					timeoutException.getMessage(), timeoutException);
		} catch (ExceptionGroup exceptionGroup) {
			communicationFailed = true;
			if (laiyifenLogger.isEnabled(Severity.ERROR)) {
				laiyifenLogger.log(Severity.ERROR, exceptionGroup.getMessage(),
						exceptionGroup);
			}
			if (exceptionGroup.getMessage().indexOf(
					"No SOAPProcessor available") > 0) {
				throw new LaiYiFenCommunicationFailure(
						exceptionGroup.getMessage());
			}
		} catch (CancelRequestException cre) {
			communicationFailed = true;
			if (laiyifenLogger.isEnabled(Severity.ERROR)) {
				laiyifenLogger.log(Severity.ERROR, cre.getMessage(), cre);
			}
			throw new LaiYiFenCommunicationFailure(cre.getMessage(), cre);
		} finally {
			Node.delete(request);
			request = 0;
		}
		/*
		 * Note: SOAP Fault is treated as communication failure, so that user
		 * classes are abstraced from reason of failure. To really find a use
		 * case where we would like to take some action based on data in SOAP
		 * Fault
		 */
		String faultString = _getSOAPFault(soapResponse);
		if (communicationFailed || faultString != null) {
			if (faultString != null) {
				boolean isIgnoreError = false;
				for (int i = 0; i < ignoreErrors.length; i++) {
					if (faultString.indexOf(ignoreErrors[i]) > 0) {
						isIgnoreError = true;
						break;
					}
				}
				if (!isIgnoreError) {
					if (laiyifenLogger.isEnabled(Severity.ERROR)) {
						laiyifenLogger.log(Severity.ERROR, faultString, null);
					}
					Node.delete(soapResponse);
					soapResponse = 0;
					throw new LaiYiFenCommunicationFailure(methodName,
							nameSpace, faultString);
				}
			}
		}
		return soapResponse;
	}

	public static int _callSoapMethod(String currentOrganization,
			String nameSpace, String methodName, String[] inputParams,
			Object[] inputValues) throws LaiYiFenCommunicationFailure {
		int soapResponse = 0;
		boolean communicationFailed = false;
		SOAPRequestObject requestObject;
		if (currentOrganization == null)
			currentOrganization = getDefaultOrg();
		try {
			requestObject = new SOAPRequestObject(currentOrganization,
					nameSpace, methodName, inputParams, inputValues);
			soapResponse = requestObject.execute();
		} catch (SOAPFaultException genException) {
			communicationFailed = true;
			if (laiyifenLogger.isEnabled(Severity.ERROR)) {
				laiyifenLogger.log(Severity.ERROR, genException.getMessage()
						+ ":" + genException.getFault(), genException);
			}

			throw new LaiYiFenCommunicationFailure(genException.getMessage()
					+ ":" + genException.getFault(), genException);
		}
		/*
		 * Note: SOAP Fault is treated as communication failure, so that user
		 * classes are abstraced from reason of failure. To really find a use
		 * case where we would like to take some action based on data in SOAP
		 * Fault
		 */
		String faultString = _getSOAPFault(soapResponse);
		if (communicationFailed || faultString != null) {
			if (faultString != null) {
				boolean isIgnoreError = false;
				for (int i = 0; i < ignoreErrors.length; i++) {
					if (faultString.indexOf(ignoreErrors[i]) > 0) {
						isIgnoreError = true;
						break;
					}
				}
				if (!isIgnoreError) {
					if (laiyifenLogger.isEnabled(Severity.ERROR)) {
						laiyifenLogger.log(Severity.ERROR, faultString, null);
					}
					Node.delete(soapResponse);
					soapResponse = 0;
					throw new LaiYiFenCommunicationFailure(methodName,
							nameSpace, faultString);
				}
			}
		}
		return soapResponse;
	}

	public static int _callSoapMethod(String currentOrganization,
			String nameSpace, String methodName, String[] inputParams,
			Object[] inputValues, int children)
			throws LaiYiFenCommunicationFailure {
		int soapResponse = 0;
		boolean communicationFailed = false;
		SOAPRequestObject requestObject;

		if (currentOrganization == null)
			currentOrganization = getDefaultOrg();
		try {
			requestObject = new SOAPRequestObject(currentOrganization,
					nameSpace, methodName, inputParams, inputValues);
			// requestObject.addParameterAsXml(Node.getFirstChild(children));
			int c1 = Node.getFirstChild(children);
			while (Node.isValidNode(c1)) {
				requestObject.addParameterAsXml(c1);
				c1 = Node.getNextElement(c1);
			}
			soapResponse = requestObject.execute();
		} catch (SOAPFaultException genException) {
			communicationFailed = true;
			if (laiyifenLogger.isEnabled(Severity.ERROR)) {
				laiyifenLogger.log(Severity.ERROR, genException.getMessage()
						+ ":" + genException.getCause(), genException);
			}

			throw new LaiYiFenCommunicationFailure(genException.getMessage()
					+ ":" + genException.getCause(), genException);
		}

		/*
		 * Note: SOAP Fault is treated as communication failure, so that user
		 * classes are abstraced from reason of failure. To really find a use
		 * case where we would like to take some action based on data in SOAP
		 * Fault
		 */
		String faultString = _getSOAPFault(soapResponse);
		if (communicationFailed || faultString != null) {
			if (faultString != null) {
				boolean isIgnoreError = false;
				for (int i = 0; i < ignoreErrors.length; i++) {
					if (faultString.indexOf(ignoreErrors[i]) > 0) {
						isIgnoreError = true;
						break;
					}
				}
				if (!isIgnoreError) {
					if (laiyifenLogger.isEnabled(Severity.ERROR)) {
						laiyifenLogger.log(Severity.ERROR, faultString, null);
					}
					Node.delete(soapResponse);
					soapResponse = 0;
					throw new LaiYiFenCommunicationFailure(methodName,
							nameSpace, faultString);
				}
			}
		}
		return soapResponse;
	}

	public static boolean _isSOAPFault(int respNode) {
		if (_getSOAPFault(respNode) != null) {
			return true;
		} else {
			return false;
		}
	}

	public static String _getSOAPFault(int respNode) {
		String errMsg = "";
		if (respNode != 0) {
			int[] faults = XPathUtil._getResultNodes("//faultstring", respNode,
					null);
			for (int fault : faults) {
				errMsg += Node.getData(fault) + "\n";
			}
		}
		if ("".equals(errMsg)) {
			errMsg = null;
		}
		return errMsg;
	}
}

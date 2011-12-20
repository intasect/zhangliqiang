

package com.laiyifen.common.pim;

import com.cordys.cpc.bsf.busobject.BSF;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.busobject.exception.WSAppServerRunTimeException;
import com.cordys.cpc.bsf.soap.SOAPRequestObject;
import com.eibus.xml.nom.Node;
import com.eibus.xml.xpath.XPath;
import com.laiyifen.common.core.PimHandler;

public class PimUtil {
	//depending on businessIdentifer(formID),fetch process instance info
    public static int queryProcessInstanceSummary(String requestStr,String param,String status){
    	int request = 0;
		int response = 0;
		int processNmNode = 0;
    	try {
    		request = BSF.getXMLDocument().load(requestStr.getBytes("UTF-8"));
    		SOAPRequestObject sro = new SOAPRequestObject(BSF.getOrganization(), PimConstants.NAMESPACE_QUERYINSTANCEDATA, PimConstants.METHODNAME_PIMSUMMARY, null, null);
			sro.addParameterAsXml(request);
			response = sro.execute();
			processNmNode = XPath.getFirstMatch(".//*[local-name()='InstancesSummary']/*[local-name()='ProcessName']", null, response);
			String proNm = Node.getDataWithDefault(processNmNode, "");
			return getRootProcessDetail(proNm, param,status);
		} catch (Exception e) {
			throw new WSAppServerRunTimeException(e.getMessage());
		}finally{
			if (request != 0){
				Node.delete(request);
				request = 0;
			}
			if (processNmNode != 0){
				Node.delete(processNmNode);
				processNmNode = 0;
			}
		}
    }
    
    /**
	 * 获取顶级流程实例详细信息
	 * @param instanceId  流程实例中BusinessIdentifier
     * @return	以XML形式返回顶级流程实例信息
	 */
	public static int getRootProcessDetail(String processName,String param,String status)
    {
		String querytext = null;
		String request = null;
		if(status.equals("F")){
			querytext = PimConstants.REQUEST_GET_PROCESSINSTANCE;
			request = querytext.replace("$PROCESSNAME$", processName).replace("$FORMID$",param);
		}else{
			querytext = PimConstants.REQUEST_GET_PROCESSINSTANCE_INSTANCEID;
			request = querytext.replace("$PROCESSNAME$", processName).replace("$INSTANCEID$",param);
		}
		return PimUtil.queryProcessInstance(request);
    }
	
  //get process instance details using processname and formid
    public static int queryProcessInstance(String requestStr){
    	int request = 0;
		int response = 0;
		int instIdNode = 0;
		int rootIdNode = 0;
    	try {
    		request = BSF.getXMLDocument().load(requestStr.getBytes("UTF-8"));
			SOAPRequestObject sro = new SOAPRequestObject(BSF.getOrganization(), PimConstants.NAMESPACE_QUERYINSTANCEDATA, PimConstants.METHODNAME_PROCESSINSTANCE, null, null);
			sro.addParameterAsXml(request);
			response = sro.execute();
			instIdNode = Node.duplicate(XPath.getFirstMatch(".//*[local-name()='PROCESS_INSTANCE']/*[local-name()='INSTANCE_ID']", null, response));
			rootIdNode = Node.duplicate(XPath.getFirstMatch(".//*[local-name()='PROCESS_INSTANCE']/*[local-name()='ROOT_ID']", null, response));
			String instId = Node.getDataWithDefault(instIdNode, "");
			String rootId = Node.getDataWithDefault(rootIdNode, "");
			if(rootId !=null && !"".equals(rootId) && !rootId.equals(instId)){
				response = PimHandler.getRootProcessByInstId(rootId);
			}
			return response;
		} catch (Exception e) {
			throw new WSAppServerRunTimeException(e.getMessage());
		}finally{
			if (request != 0){
				Node.delete(request);
				request = 0;
			}
			if (instIdNode != 0){
				Node.delete(instIdNode);
				instIdNode = 0;
			}
			if (rootIdNode != 0){
				Node.delete(rootIdNode);
				rootIdNode = 0;
			}
		}
    }
    
  //get sub process instance which status equals 'waiting' using parentid
    public static String querySubProcessInstance(String requestStr,String instanceID){
    	String childId = instanceID;
    	int request = 0;
		int response = 0;
    	try {
    		request = BSF.getXMLDocument().load(requestStr.getBytes("UTF-8"));
			SOAPRequestObject sro = new SOAPRequestObject(BSF.getOrganization(), PimConstants.NAMESPACE_QUERYINSTANCEDATA, PimConstants.METHODNAME_PROCESSINSTANCE, null, null);
			sro.addParameterAsXml(request);
			response = sro.execute();
			int instanceIdNode = Node.duplicate(XPath.getFirstMatch("//*[local-name()='PROCESS_INSTANCE'][*[local-name()='STATUS']='WAITING']/*[local-name()='INSTANCE_ID']", null, response));
			if(instanceIdNode > 0){
					childId = Node.getDataWithDefault(instanceIdNode, "");
					childId = PimHandler.getSubProInstIdByParentId(childId);
			}
			return childId;
		} catch (Exception e) {
			throw new WSAppServerRunTimeException(e.getMessage());
		}finally{
			if (request != 0){
				Node.delete(request);
				request = 0;
			}
		}
    }
    
  //get sub process instance which status equals 'waiting' using parentid
    public static BusObjectIterator queryProActivities(String requestStr){
    	int request = 0;
    	BusObjectIterator response = null;
    	try {
    		request = BSF.getXMLDocument().load(requestStr.getBytes("UTF-8"));
			SOAPRequestObject sro = new SOAPRequestObject(BSF.getOrganization(), PimConstants.NAMESPACE_QUERYADMINDATA, PimConstants.METHODNAME_QUERYADMINDATA, null, null);
			sro.addParameterAsXml(request);
			response = sro.getObjects();
			return response;
		} catch (Exception e) {
			throw new WSAppServerRunTimeException(e.getMessage());
		}finally{
			if (request != 0){
				Node.delete(request);
				request = 0;
			}
		}
    }
}

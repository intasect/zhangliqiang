/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.common.core;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.busobject.QueryObject;
import com.eibus.xml.nom.Node;
import com.eibus.xml.xpath.XPath;
import com.laiyifen.common.pim.PimConstants;
import com.laiyifen.common.pim.PimUtil;

public class PimHandler extends PimHandlerBase
{
    public PimHandler()
    {
        this((BusObjectConfig)null);
    }

    public PimHandler(BusObjectConfig config)
    {
        super(config);
    }

    /**
	 * 获取顶级流程实例信息
	 * @param formId  流程实例中BusinessIdentifier
     * @return	以XML形式返回顶级流程实例信息
	 */
	public static int getRootProcessByFormId(String formId)
    {
		int response = 0;
		try{
			String querytext = PimConstants.REQUEST_GET_PROCESSINSTANCESUMMARY_FORMID;
			String request = querytext.replace("$FORMID$", formId);
			response = PimUtil.queryProcessInstanceSummary(request, formId, "F");
			return Node.duplicate(XPath.getXPathInstance(".//PROCESS_INSTANCE").firstMatch(response, null));
		}finally {
			if (response != 0){
				Node.delete(response);
				response = 0;
			}
		}
		
    }
	
	/**
	 * 获取顶级流程实例信息
	 * @param formId  流程实例中BusinessIdentifier
     * @return	以XML形式返回顶级流程实例信息
	 */
	public static int getRootProcessByInstId(String instanceId)
    {
		String querytext = PimConstants.REQUEST_GET_PROCESSINSTANCESUMMARY_INSTANCEID;
		String request = querytext.replace("$INSTANCEID$", instanceId);
		return PimUtil.queryProcessInstanceSummary(request, instanceId, "I");
    }
	
	/**
	 * 获取未完成的子流程实例ID
	 * @param parentId  流程实例ID
     * @return	流程实例ID
	 */
	public static String getSubProInstIdByParentId(String parentId)
    {
		String querytext = PimConstants.REQUEST_GET_PROCESSINSTANCE_PARENTID;
		String request = querytext.replace("$PARENTID$", parentId);
		return PimUtil.querySubProcessInstance(request,parentId);
    }

	/**
	 * 获取未完成的流程实例中所有任务信息
	 * @param parentId  流程实例ID
     * @return	返回所有任务信息
	 */
	public static BusObjectIterator<com.laiyifen.common.core.PimHandler> getProActivities(String instanceId)
    {
		instanceId = getSubProInstIdByParentId(instanceId);
		String queryText = PimConstants.SQL_GET_PROCESSACTIVITIES;
		String request = queryText.replace("$instanceID$", instanceId);
		BusObjectIterator<com.laiyifen.common.core.PimHandler> result = PimUtil.queryProActivities(request);
        return result;
    }
	
	@Override
	public void onDelete() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onInsert() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub
	}
}
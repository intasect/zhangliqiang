/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.common.core;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.busobject.QueryObject;


public class EMPLOYEE_INFO_TRANS extends EMPLOYEE_INFO_TRANSBase
{
    public EMPLOYEE_INFO_TRANS()
    {
        this((BusObjectConfig)null);
    }

    public EMPLOYEE_INFO_TRANS(BusObjectConfig config)
    {
        super(config);
    }

    public static BusObjectIterator<com.laiyifen.common.core.EMPLOYEE_INFO_TRANS> getEmployeeInfoTransByFilters(String EMPLOYEEID, String NAME, String DEPTID, String COMPANYCODE, String UPDATERESULT, String STATIONCODE, String MANAGERNUMBER, com.cordys.cpc.bsf.query.Cursor cursor)
    {
    	String queryText = "SELECT * FROM EMPLOYEE_INFO_TRANS WHERE 1=1";
    	if(EMPLOYEEID.equals("") != true){
        	queryText += "AND EMPLOYEEID = '" +EMPLOYEEID+"'";
        }
    	if(NAME.equals("") != true){
        	queryText += "AND SN||GIVENNAME LIKE '%" +NAME+"%'";
        }
    	if(DEPTID.equals("") != true){
        	queryText += "AND DEPTID = '" +DEPTID+"'";
        }
    	if(COMPANYCODE.equals("") != true){
        	queryText += "AND COMPANYCODE = '" +COMPANYCODE+"'";
        }
    	if(UPDATERESULT.trim().equals("") != true){
        	queryText += "AND UPDATERESULT = '" +UPDATERESULT+"'";
        }
    	if(STATIONCODE.equals("") != true){
        	queryText += "AND STATIONCODE = '" +STATIONCODE+"'";
        }
    	if(MANAGERNUMBER.equals("") != true){
        	queryText += "AND MANAGERNUMBER = '" +MANAGERNUMBER+"'";
        }
    	QueryObject query = new QueryObject(queryText);
        query.setResultClass(EMPLOYEE_INFO_TRANS.class);
        query.setCursor(cursor);
        return query.getObjects();
    }
}
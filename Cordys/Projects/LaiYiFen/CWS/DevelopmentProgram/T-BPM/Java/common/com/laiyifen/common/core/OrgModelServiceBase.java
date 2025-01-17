/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.common.core;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.classinfo.AttributeInfo;
import com.cordys.cpc.bsf.classinfo.ClassInfo;


public abstract class OrgModelServiceBase extends com.cordys.cpc.bsf.busobject.CustomBusObject
{
    // tags used in the XML document
    public final static String ATTR_userName = "userName";
    public final static String ATTR_enable = "enable";
    public final static String ATTR_deptCode = "deptCode";
    public final static String ATTR_deptName = "deptName";
    public final static String ATTR_companyCode = "companyCode";
    public final static String ATTR_companyName = "companyName";
    private static ClassInfo s_classInfo = null;
    public static ClassInfo _getClassInfo()//NOPMD framework ensures this is thread safe
    {
        if ( s_classInfo == null )//NOPMD
        {
            s_classInfo = newClassInfo(OrgModelService.class);
            s_classInfo.setUIDElements(new String[]{});
            {
                AttributeInfo ai = new AttributeInfo(ATTR_userName);
                ai.setJavaName(ATTR_userName);
                ai.setAttributeClass(String.class);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_enable);
                ai.setJavaName(ATTR_enable);
                ai.setAttributeClass(String.class);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_deptCode);
                ai.setJavaName(ATTR_deptCode);
                ai.setAttributeClass(String.class);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_deptName);
                ai.setJavaName(ATTR_deptName);
                ai.setAttributeClass(String.class);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_companyCode);
                ai.setJavaName(ATTR_companyCode);
                ai.setAttributeClass(String.class);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_companyName);
                ai.setJavaName(ATTR_companyName);
                ai.setAttributeClass(String.class);
                s_classInfo.addAttributeInfo(ai);
            }
        }
        return s_classInfo;
    }

    public OrgModelServiceBase(BusObjectConfig config)
    {
        super(config);
    }

    public String getUserName()
    {
        return getStringProperty(ATTR_userName);
    }

    public void setUserName(String value)
    {
        setProperty(ATTR_userName, value, 0);
    }

    public String getEnable()
    {
        return getStringProperty(ATTR_enable);
    }

    public void setEnable(String value)
    {
        setProperty(ATTR_enable, value, 0);
    }

    public String getDeptCode()
    {
        return getStringProperty(ATTR_deptCode);
    }

    public void setDeptCode(String value)
    {
        setProperty(ATTR_deptCode, value, 0);
    }

    public String getDeptName()
    {
        return getStringProperty(ATTR_deptName);
    }

    public void setDeptName(String value)
    {
        setProperty(ATTR_deptName, value, 0);
    }

    public String getCompanyCode()
    {
        return getStringProperty(ATTR_companyCode);
    }

    public void setCompanyCode(String value)
    {
        setProperty(ATTR_companyCode, value, 0);
    }

    public String getCompanyName()
    {
        return getStringProperty(ATTR_companyName);
    }

    public void setCompanyName(String value)
    {
        setProperty(ATTR_companyName, value, 0);
    }



}

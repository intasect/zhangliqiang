/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.shop;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.busobject.QueryObject;
import com.cordys.cpc.bsf.classinfo.AttributeInfo;
import com.cordys.cpc.bsf.classinfo.ClassInfo;
import com.cordys.cpc.bsf.listeners.constraint.NumberValidator;
import com.cordys.cpc.bsf.listeners.constraint.StringValidator;


public abstract class SHOP_SEAL_APPLICATIONBase extends com.cordys.cpc.bsf.busobject.StateBusObject
{
    // tags used in the XML document
    public final static String ATTR_SHOP_ID = "SHOP_ID";
    public final static String ATTR_FORM_ID = "FORM_ID";
    public final static String ATTR_FILE_NAME = "FILE_NAME";
    public final static String ATTR_SEAL_DATE = "SEAL_DATE";
    public final static String ATTR_SEAL_ID = "SEAL_ID";
    public final static String ATTR_AMOUNT = "AMOUNT";
    public final static String ATTR_COLLABORATORS = "COLLABORATORS";
    public final static String ATTR_SEAL_USED = "SEAL_USED";
    public final static String ATTR_SEAL_NAME = "SEAL_NAME";
    public final static String ATTR_USER_CODE = "USER_CODE";
    public final static String ATTR_USER_NAME = "USER_NAME";
    public final static String ATTR_DEPARTMENT_CODE = "DEPARTMENT_CODE";
    public final static String ATTR_DEPARTMENT_NAME = "DEPARTMENT_NAME";
    public final static String ATTR_SUBCOMPANY_CODE = "SUBCOMPANY_CODE";
    public final static String ATTR_SUBCOMPANY_NAME = "SUBCOMPANY_NAME";
    public final static String ATTR_STATUS = "STATUS";
    public final static String ATTR_EXT1 = "EXT1";
    public final static String ATTR_EXT2 = "EXT2";
    public final static String ATTR_CONTRACT_TYPE = "CONTRACT_TYPE";
    public final static String ATTR_APPLICATION_DATE = "APPLICATION_DATE";
    private static ClassInfo s_classInfo = null;
    public static ClassInfo _getClassInfo()//NOPMD framework ensures this is thread safe
    {
        if ( s_classInfo == null )//NOPMD
        {
            s_classInfo = newClassInfo(SHOP_SEAL_APPLICATION.class);
            s_classInfo.setTableName("SHOP_SEAL_APPLICATION");
            s_classInfo.setUIDElements(new String[]{ATTR_FORM_ID});
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SHOP_ID);
                ai.setJavaName(ATTR_SHOP_ID);
                ai.setColumnName(ATTR_SHOP_ID);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SHOP_ID);
                v.setMaxLength(40);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_FORM_ID);
                ai.setJavaName(ATTR_FORM_ID);
                ai.setColumnName(ATTR_FORM_ID);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_FORM_ID);
                v.setMaxLength(40);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_FILE_NAME);
                ai.setJavaName(ATTR_FILE_NAME);
                ai.setColumnName(ATTR_FILE_NAME);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_FILE_NAME);
                v.setMaxLength(30);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SEAL_DATE);
                ai.setJavaName(ATTR_SEAL_DATE);
                ai.setColumnName(ATTR_SEAL_DATE);
                ai.setAttributeClass(java.util.Date.class);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SEAL_ID);
                ai.setJavaName(ATTR_SEAL_ID);
                ai.setColumnName(ATTR_SEAL_ID);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SEAL_ID);
                v.setMaxLength(40);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_AMOUNT);
                ai.setJavaName(ATTR_AMOUNT);
                ai.setColumnName(ATTR_AMOUNT);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_AMOUNT);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_COLLABORATORS);
                ai.setJavaName(ATTR_COLLABORATORS);
                ai.setColumnName(ATTR_COLLABORATORS);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_COLLABORATORS);
                v.setMaxLength(30);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SEAL_USED);
                ai.setJavaName(ATTR_SEAL_USED);
                ai.setColumnName(ATTR_SEAL_USED);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SEAL_USED);
                v.setMaxLength(200);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SEAL_NAME);
                ai.setJavaName(ATTR_SEAL_NAME);
                ai.setColumnName(ATTR_SEAL_NAME);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SEAL_NAME);
                v.setMaxLength(20);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_USER_CODE);
                ai.setJavaName(ATTR_USER_CODE);
                ai.setColumnName(ATTR_USER_CODE);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_USER_CODE);
                v.setMaxLength(8);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_USER_NAME);
                ai.setJavaName(ATTR_USER_NAME);
                ai.setColumnName(ATTR_USER_NAME);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_USER_NAME);
                v.setMaxLength(40);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_DEPARTMENT_CODE);
                ai.setJavaName(ATTR_DEPARTMENT_CODE);
                ai.setColumnName(ATTR_DEPARTMENT_CODE);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_DEPARTMENT_CODE);
                v.setMaxLength(8);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_DEPARTMENT_NAME);
                ai.setJavaName(ATTR_DEPARTMENT_NAME);
                ai.setColumnName(ATTR_DEPARTMENT_NAME);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_DEPARTMENT_NAME);
                v.setMaxLength(40);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SUBCOMPANY_CODE);
                ai.setJavaName(ATTR_SUBCOMPANY_CODE);
                ai.setColumnName(ATTR_SUBCOMPANY_CODE);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SUBCOMPANY_CODE);
                v.setMaxLength(10);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SUBCOMPANY_NAME);
                ai.setJavaName(ATTR_SUBCOMPANY_NAME);
                ai.setColumnName(ATTR_SUBCOMPANY_NAME);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SUBCOMPANY_NAME);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_STATUS);
                ai.setJavaName(ATTR_STATUS);
                ai.setColumnName(ATTR_STATUS);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_STATUS);
                v.setMaxLength(1);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_EXT1);
                ai.setJavaName(ATTR_EXT1);
                ai.setColumnName(ATTR_EXT1);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_EXT1);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_EXT2);
                ai.setJavaName(ATTR_EXT2);
                ai.setColumnName(ATTR_EXT2);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_EXT2);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_CONTRACT_TYPE);
                ai.setJavaName(ATTR_CONTRACT_TYPE);
                ai.setColumnName(ATTR_CONTRACT_TYPE);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_CONTRACT_TYPE);
                v.setMaxLength(1);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_APPLICATION_DATE);
                ai.setJavaName(ATTR_APPLICATION_DATE);
                ai.setColumnName(ATTR_APPLICATION_DATE);
                ai.setAttributeClass(java.util.Date.class);
                s_classInfo.addAttributeInfo(ai);
            }
        }
        return s_classInfo;
    }

    public SHOP_SEAL_APPLICATIONBase(BusObjectConfig config)
    {
        super(config);
    }

    public String getSHOP_ID()
    {
        return getStringProperty(ATTR_SHOP_ID);
    }

    public void setSHOP_ID(String value)
    {
        setProperty(ATTR_SHOP_ID, value, 0);
    }

    public String getFORM_ID()
    {
        return getStringProperty(ATTR_FORM_ID);
    }

    public void setFORM_ID(String value)
    {
        setProperty(ATTR_FORM_ID, value, 0);
    }

    public String getFILE_NAME()
    {
        return getStringProperty(ATTR_FILE_NAME);
    }

    public void setFILE_NAME(String value)
    {
        setProperty(ATTR_FILE_NAME, value, 0);
    }

    public java.util.Date getSEAL_DATE()
    {
        return getDateTimestampProperty(ATTR_SEAL_DATE);
    }

    public void setSEAL_DATE(java.util.Date value)
    {
        setProperty(ATTR_SEAL_DATE, value, 0);
    }

    public String getSEAL_ID()
    {
        return getStringProperty(ATTR_SEAL_ID);
    }

    public void setSEAL_ID(String value)
    {
        setProperty(ATTR_SEAL_ID, value, 0);
    }

    public double getAMOUNT()
    {
        return getDoubleProperty(ATTR_AMOUNT);
    }

    public void setAMOUNT(double value)
    {
        setProperty(ATTR_AMOUNT, value, 0);
    }

    public String getCOLLABORATORS()
    {
        return getStringProperty(ATTR_COLLABORATORS);
    }

    public void setCOLLABORATORS(String value)
    {
        setProperty(ATTR_COLLABORATORS, value, 0);
    }

    public String getSEAL_USED()
    {
        return getStringProperty(ATTR_SEAL_USED);
    }

    public void setSEAL_USED(String value)
    {
        setProperty(ATTR_SEAL_USED, value, 0);
    }

    public String getSEAL_NAME()
    {
        return getStringProperty(ATTR_SEAL_NAME);
    }

    public void setSEAL_NAME(String value)
    {
        setProperty(ATTR_SEAL_NAME, value, 0);
    }

    public String getUSER_CODE()
    {
        return getStringProperty(ATTR_USER_CODE);
    }

    public void setUSER_CODE(String value)
    {
        setProperty(ATTR_USER_CODE, value, 0);
    }

    public String getUSER_NAME()
    {
        return getStringProperty(ATTR_USER_NAME);
    }

    public void setUSER_NAME(String value)
    {
        setProperty(ATTR_USER_NAME, value, 0);
    }

    public String getDEPARTMENT_CODE()
    {
        return getStringProperty(ATTR_DEPARTMENT_CODE);
    }

    public void setDEPARTMENT_CODE(String value)
    {
        setProperty(ATTR_DEPARTMENT_CODE, value, 0);
    }

    public String getDEPARTMENT_NAME()
    {
        return getStringProperty(ATTR_DEPARTMENT_NAME);
    }

    public void setDEPARTMENT_NAME(String value)
    {
        setProperty(ATTR_DEPARTMENT_NAME, value, 0);
    }

    public String getSUBCOMPANY_CODE()
    {
        return getStringProperty(ATTR_SUBCOMPANY_CODE);
    }

    public void setSUBCOMPANY_CODE(String value)
    {
        setProperty(ATTR_SUBCOMPANY_CODE, value, 0);
    }

    public String getSUBCOMPANY_NAME()
    {
        return getStringProperty(ATTR_SUBCOMPANY_NAME);
    }

    public void setSUBCOMPANY_NAME(String value)
    {
        setProperty(ATTR_SUBCOMPANY_NAME, value, 0);
    }

    public String getSTATUS()
    {
        return getStringProperty(ATTR_STATUS);
    }

    public void setSTATUS(String value)
    {
        setProperty(ATTR_STATUS, value, 0);
    }

    public String getEXT1()
    {
        return getStringProperty(ATTR_EXT1);
    }

    public void setEXT1(String value)
    {
        setProperty(ATTR_EXT1, value, 0);
    }

    public String getEXT2()
    {
        return getStringProperty(ATTR_EXT2);
    }

    public void setEXT2(String value)
    {
        setProperty(ATTR_EXT2, value, 0);
    }

    public String getCONTRACT_TYPE()
    {
        return getStringProperty(ATTR_CONTRACT_TYPE);
    }

    public void setCONTRACT_TYPE(String value)
    {
        setProperty(ATTR_CONTRACT_TYPE, value, 0);
    }

    public java.util.Date getAPPLICATION_DATE()
    {
        return getDateTimestampProperty(ATTR_APPLICATION_DATE);
    }

    public void setAPPLICATION_DATE(java.util.Date value)
    {
        setProperty(ATTR_APPLICATION_DATE, value, 0);
    }

    public static BusObjectIterator<com.laiyifen.shop.SHOP_SEAL_APPLICATION> getNextShopSealApplicationObjects(String FORM_ID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"SHOP_SEAL_APPLICATION\" where (\"FORM_ID\" > :FORM_ID) order by \"FORM_ID\" asc";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("FORM_ID", "SHOP_SEAL_APPLICATION.FORM_ID", QueryObject.PARAM_STRING, FORM_ID);//NOPMD
        query.setResultClass(SHOP_SEAL_APPLICATION.class);
        query.setCursor(cursor);
        return query.getObjects();
    }

    public static BusObjectIterator<com.laiyifen.shop.SHOP_SEAL_APPLICATION> getPreviousShopSealApplicationObjects(String FORM_ID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"SHOP_SEAL_APPLICATION\" where (\"FORM_ID\" < :FORM_ID) order by \"FORM_ID\" desc";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("FORM_ID", "SHOP_SEAL_APPLICATION.FORM_ID", QueryObject.PARAM_STRING, FORM_ID);//NOPMD
        query.setResultClass(SHOP_SEAL_APPLICATION.class);
        query.setCursor(cursor);
        return query.getObjects();
    }



    public static com.laiyifen.shop.SHOP_SEAL_APPLICATION getShopSealApplicationObject(String FORM_ID)
    {
        String queryText = "select * from \"SHOP_SEAL_APPLICATION\" where \"FORM_ID\" = :FORM_ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("FORM_ID", "SHOP_SEAL_APPLICATION.FORM_ID", QueryObject.PARAM_STRING, FORM_ID);//NOPMD
        query.setResultClass(SHOP_SEAL_APPLICATION.class);
        return (SHOP_SEAL_APPLICATION)query.getObject();
    }

    public static BusObjectIterator<com.laiyifen.shop.SHOP_SEAL_APPLICATION> getShopSealApplicationObjects(String fromFORM_ID, String toFORM_ID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"SHOP_SEAL_APPLICATION\" where \"FORM_ID\" between :fromFORM_ID and :toFORM_ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("fromFORM_ID", "SHOP_SEAL_APPLICATION.FORM_ID", QueryObject.PARAM_STRING, fromFORM_ID);
        query.addParameter("toFORM_ID", "SHOP_SEAL_APPLICATION.FORM_ID", QueryObject.PARAM_STRING, toFORM_ID);
        query.setResultClass(SHOP_SEAL_APPLICATION.class);
        query.setCursor(cursor);
        return query.getObjects();
    }

    public static BusObjectIterator<com.laiyifen.shop.SHOP_SEAL_APPLICATION> getShopSealApplicationObjects(String fromFORM_ID, String toFORM_ID)
    {
        String queryText = "select * from \"SHOP_SEAL_APPLICATION\" where \"FORM_ID\" between :fromFORM_ID and :toFORM_ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("fromFORM_ID", "SHOP_SEAL_APPLICATION.FORM_ID", QueryObject.PARAM_STRING, fromFORM_ID);
        query.addParameter("toFORM_ID", "SHOP_SEAL_APPLICATION.FORM_ID", QueryObject.PARAM_STRING, toFORM_ID);
        query.setResultClass(SHOP_SEAL_APPLICATION.class);
        return query.getObjects();
    }

}

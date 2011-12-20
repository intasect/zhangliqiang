/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.shop;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.busobject.QueryObject;
import com.cordys.cpc.bsf.classinfo.AttributeInfo;
import com.cordys.cpc.bsf.classinfo.ClassInfo;
import com.cordys.cpc.bsf.listeners.constraint.StringValidator;


public abstract class SHOP_MASTER_FINDEPTBase extends com.cordys.cpc.bsf.busobject.StateBusObject
{
    // tags used in the XML document
    public final static String ATTR_SHOP_ID = "SHOP_ID";
    public final static String ATTR_FORM_ID = "FORM_ID";
    public final static String ATTR_USER_NAME = "USER_NAME";
    public final static String ATTR_USER_CODE = "USER_CODE";
    public final static String ATTR_DEPARTMENT_CODE = "DEPARTMENT_CODE";
    public final static String ATTR_DEPARTMENT_NAME = "DEPARTMENT_NAME";
    public final static String ATTR_APPLICATION_DATE = "APPLICATION_DATE";
    public final static String ATTR_THIRD_MARKNAME = "THIRD_MARKNAME";
    public final static String ATTR_THIRD_MARKCODE = "THIRD_MARKCODE";
    public final static String ATTR_BANK_COUNTRYCODE = "BANK_COUNTRYCODE";
    public final static String ATTR_ACCOUNT_BANKCODE = "ACCOUNT_BANKCODE";
    public final static String ATTR_ACCOUNT_BANK = "ACCOUNT_BANK";
    public final static String ATTR_ACCOUNT_BANK_NAME = "ACCOUNT_BANK_NAME";
    public final static String ATTR_ACCOUNT = "ACCOUNT";
    public final static String ATTR_EXT1 = "EXT1";
    public final static String ATTR_EXT2 = "EXT2";
    public final static String ATTR_STATUS = "STATUS";
    private static ClassInfo s_classInfo = null;
    public static ClassInfo _getClassInfo()//NOPMD framework ensures this is thread safe
    {
        if ( s_classInfo == null )//NOPMD
        {
            s_classInfo = newClassInfo(SHOP_MASTER_FINDEPT.class);
            s_classInfo.setTableName("SHOP_MASTER_FINDEPT");
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
                AttributeInfo ai = new AttributeInfo(ATTR_DEPARTMENT_CODE);
                ai.setJavaName(ATTR_DEPARTMENT_CODE);
                ai.setColumnName(ATTR_DEPARTMENT_CODE);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_DEPARTMENT_CODE);
                v.setMaxLength(10);
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
                AttributeInfo ai = new AttributeInfo(ATTR_APPLICATION_DATE);
                ai.setJavaName(ATTR_APPLICATION_DATE);
                ai.setColumnName(ATTR_APPLICATION_DATE);
                ai.setAttributeClass(java.util.Date.class);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_THIRD_MARKNAME);
                ai.setJavaName(ATTR_THIRD_MARKNAME);
                ai.setColumnName(ATTR_THIRD_MARKNAME);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_THIRD_MARKNAME);
                v.setMaxLength(20);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_THIRD_MARKCODE);
                ai.setJavaName(ATTR_THIRD_MARKCODE);
                ai.setColumnName(ATTR_THIRD_MARKCODE);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_THIRD_MARKCODE);
                v.setMaxLength(1);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_BANK_COUNTRYCODE);
                ai.setJavaName(ATTR_BANK_COUNTRYCODE);
                ai.setColumnName(ATTR_BANK_COUNTRYCODE);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_BANK_COUNTRYCODE);
                v.setMaxLength(3);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ACCOUNT_BANKCODE);
                ai.setJavaName(ATTR_ACCOUNT_BANKCODE);
                ai.setColumnName(ATTR_ACCOUNT_BANKCODE);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_ACCOUNT_BANKCODE);
                v.setMaxLength(15);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ACCOUNT_BANK);
                ai.setJavaName(ATTR_ACCOUNT_BANK);
                ai.setColumnName(ATTR_ACCOUNT_BANK);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_ACCOUNT_BANK);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ACCOUNT_BANK_NAME);
                ai.setJavaName(ATTR_ACCOUNT_BANK_NAME);
                ai.setColumnName(ATTR_ACCOUNT_BANK_NAME);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_ACCOUNT_BANK_NAME);
                v.setMaxLength(60);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ACCOUNT);
                ai.setJavaName(ATTR_ACCOUNT);
                ai.setColumnName(ATTR_ACCOUNT);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_ACCOUNT);
                v.setMaxLength(22);
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
                AttributeInfo ai = new AttributeInfo(ATTR_STATUS);
                ai.setJavaName(ATTR_STATUS);
                ai.setColumnName(ATTR_STATUS);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_STATUS);
                v.setMaxLength(1);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
        }
        return s_classInfo;
    }

    public SHOP_MASTER_FINDEPTBase(BusObjectConfig config)
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

    public String getUSER_NAME()
    {
        return getStringProperty(ATTR_USER_NAME);
    }

    public void setUSER_NAME(String value)
    {
        setProperty(ATTR_USER_NAME, value, 0);
    }

    public String getUSER_CODE()
    {
        return getStringProperty(ATTR_USER_CODE);
    }

    public void setUSER_CODE(String value)
    {
        setProperty(ATTR_USER_CODE, value, 0);
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

    public java.util.Date getAPPLICATION_DATE()
    {
        return getDateTimestampProperty(ATTR_APPLICATION_DATE);
    }

    public void setAPPLICATION_DATE(java.util.Date value)
    {
        setProperty(ATTR_APPLICATION_DATE, value, 0);
    }

    public String getTHIRD_MARKNAME()
    {
        return getStringProperty(ATTR_THIRD_MARKNAME);
    }

    public void setTHIRD_MARKNAME(String value)
    {
        setProperty(ATTR_THIRD_MARKNAME, value, 0);
    }

    public String getTHIRD_MARKCODE()
    {
        return getStringProperty(ATTR_THIRD_MARKCODE);
    }

    public void setTHIRD_MARKCODE(String value)
    {
        setProperty(ATTR_THIRD_MARKCODE, value, 0);
    }

    public String getBANK_COUNTRYCODE()
    {
        return getStringProperty(ATTR_BANK_COUNTRYCODE);
    }

    public void setBANK_COUNTRYCODE(String value)
    {
        setProperty(ATTR_BANK_COUNTRYCODE, value, 0);
    }

    public String getACCOUNT_BANKCODE()
    {
        return getStringProperty(ATTR_ACCOUNT_BANKCODE);
    }

    public void setACCOUNT_BANKCODE(String value)
    {
        setProperty(ATTR_ACCOUNT_BANKCODE, value, 0);
    }

    public String getACCOUNT_BANK()
    {
        return getStringProperty(ATTR_ACCOUNT_BANK);
    }

    public void setACCOUNT_BANK(String value)
    {
        setProperty(ATTR_ACCOUNT_BANK, value, 0);
    }

    public String getACCOUNT_BANK_NAME()
    {
        return getStringProperty(ATTR_ACCOUNT_BANK_NAME);
    }

    public void setACCOUNT_BANK_NAME(String value)
    {
        setProperty(ATTR_ACCOUNT_BANK_NAME, value, 0);
    }

    public String getACCOUNT()
    {
        return getStringProperty(ATTR_ACCOUNT);
    }

    public void setACCOUNT(String value)
    {
        setProperty(ATTR_ACCOUNT, value, 0);
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

    public String getSTATUS()
    {
        return getStringProperty(ATTR_STATUS);
    }

    public void setSTATUS(String value)
    {
        setProperty(ATTR_STATUS, value, 0);
    }

    public static BusObjectIterator<com.laiyifen.shop.SHOP_MASTER_FINDEPT> getNextShopMasterFindeptObjects(String FORM_ID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"SHOP_MASTER_FINDEPT\" where (\"FORM_ID\" > :FORM_ID) order by \"FORM_ID\" asc";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("FORM_ID", "SHOP_MASTER_FINDEPT.FORM_ID", QueryObject.PARAM_STRING, FORM_ID);//NOPMD
        query.setResultClass(SHOP_MASTER_FINDEPT.class);
        query.setCursor(cursor);
        return query.getObjects();
    }

    public static BusObjectIterator<com.laiyifen.shop.SHOP_MASTER_FINDEPT> getPreviousShopMasterFindeptObjects(String FORM_ID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"SHOP_MASTER_FINDEPT\" where (\"FORM_ID\" < :FORM_ID) order by \"FORM_ID\" desc";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("FORM_ID", "SHOP_MASTER_FINDEPT.FORM_ID", QueryObject.PARAM_STRING, FORM_ID);//NOPMD
        query.setResultClass(SHOP_MASTER_FINDEPT.class);
        query.setCursor(cursor);
        return query.getObjects();
    }


    public static com.laiyifen.shop.SHOP_MASTER_FINDEPT getShopMasterFindeptObject(String FORM_ID)
    {
        String queryText = "select * from \"SHOP_MASTER_FINDEPT\" where \"FORM_ID\" = :FORM_ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("FORM_ID", "SHOP_MASTER_FINDEPT.FORM_ID", QueryObject.PARAM_STRING, FORM_ID);//NOPMD
        query.setResultClass(SHOP_MASTER_FINDEPT.class);
        return (SHOP_MASTER_FINDEPT)query.getObject();
    }

    public static BusObjectIterator<com.laiyifen.shop.SHOP_MASTER_FINDEPT> getShopMasterFindeptObjects(String fromFORM_ID, String toFORM_ID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"SHOP_MASTER_FINDEPT\" where \"FORM_ID\" between :fromFORM_ID and :toFORM_ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("fromFORM_ID", "SHOP_MASTER_FINDEPT.FORM_ID", QueryObject.PARAM_STRING, fromFORM_ID);
        query.addParameter("toFORM_ID", "SHOP_MASTER_FINDEPT.FORM_ID", QueryObject.PARAM_STRING, toFORM_ID);
        query.setResultClass(SHOP_MASTER_FINDEPT.class);
        query.setCursor(cursor);
        return query.getObjects();
    }

    public static BusObjectIterator<com.laiyifen.shop.SHOP_MASTER_FINDEPT> getShopMasterFindeptObjects(String fromFORM_ID, String toFORM_ID)
    {
        String queryText = "select * from \"SHOP_MASTER_FINDEPT\" where \"FORM_ID\" between :fromFORM_ID and :toFORM_ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("fromFORM_ID", "SHOP_MASTER_FINDEPT.FORM_ID", QueryObject.PARAM_STRING, fromFORM_ID);
        query.addParameter("toFORM_ID", "SHOP_MASTER_FINDEPT.FORM_ID", QueryObject.PARAM_STRING, toFORM_ID);
        query.setResultClass(SHOP_MASTER_FINDEPT.class);
        return query.getObjects();
    }

}
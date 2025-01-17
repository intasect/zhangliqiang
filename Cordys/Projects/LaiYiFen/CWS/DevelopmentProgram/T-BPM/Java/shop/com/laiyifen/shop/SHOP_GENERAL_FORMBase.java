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


public abstract class SHOP_GENERAL_FORMBase extends com.cordys.cpc.bsf.busobject.StateBusObject
{
    // tags used in the XML document
    public final static String ATTR_FORM_ID = "FORM_ID";
    public final static String ATTR_SHOP_ID = "SHOP_ID";
    public final static String ATTR_CREATER = "CREATER";
    public final static String ATTR_CREATE_NAME = "CREATE_NAME";
    public final static String ATTR_CREATE_TIME = "CREATE_TIME";
    public final static String ATTR_DEPT_ID = "DEPT_ID";
    public final static String ATTR_DEPT_NAME = "DEPT_NAME";
    public final static String ATTR_REMARK = "REMARK";
    public final static String ATTR_TYPE = "TYPE";
    public final static String ATTR_EXT1 = "EXT1";
    public final static String ATTR_EXT2 = "EXT2";
    public final static String ATTR_TITLE = "TITLE";
    private static ClassInfo s_classInfo = null;
    public static ClassInfo _getClassInfo()//NOPMD framework ensures this is thread safe
    {
        if ( s_classInfo == null )//NOPMD
        {
            s_classInfo = newClassInfo(SHOP_GENERAL_FORM.class);
            s_classInfo.setTableName("SHOP_GENERAL_FORM");
            s_classInfo.setUIDElements(new String[]{ATTR_FORM_ID});
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
                AttributeInfo ai = new AttributeInfo(ATTR_CREATER);
                ai.setJavaName(ATTR_CREATER);
                ai.setColumnName(ATTR_CREATER);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_CREATER);
                v.setMaxLength(8);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_CREATE_NAME);
                ai.setJavaName(ATTR_CREATE_NAME);
                ai.setColumnName(ATTR_CREATE_NAME);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_CREATE_NAME);
                v.setMaxLength(40);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_CREATE_TIME);
                ai.setJavaName(ATTR_CREATE_TIME);
                ai.setColumnName(ATTR_CREATE_TIME);
                ai.setAttributeClass(java.util.Date.class);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_DEPT_ID);
                ai.setJavaName(ATTR_DEPT_ID);
                ai.setColumnName(ATTR_DEPT_ID);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_DEPT_ID);
                v.setMaxLength(10);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_DEPT_NAME);
                ai.setJavaName(ATTR_DEPT_NAME);
                ai.setColumnName(ATTR_DEPT_NAME);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_DEPT_NAME);
                v.setMaxLength(40);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_REMARK);
                ai.setJavaName(ATTR_REMARK);
                ai.setColumnName(ATTR_REMARK);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_REMARK);
                v.setMaxLength(200);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_TYPE);
                ai.setJavaName(ATTR_TYPE);
                ai.setColumnName(ATTR_TYPE);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_TYPE);
                v.setMaxLength(2);
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
                AttributeInfo ai = new AttributeInfo(ATTR_TITLE);
                ai.setJavaName(ATTR_TITLE);
                ai.setColumnName(ATTR_TITLE);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_TITLE);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
        }
        return s_classInfo;
    }

    public SHOP_GENERAL_FORMBase(BusObjectConfig config)
    {
        super(config);
    }

    public String getFORM_ID()
    {
        return getStringProperty(ATTR_FORM_ID);
    }

    public void setFORM_ID(String value)
    {
        setProperty(ATTR_FORM_ID, value, 0);
    }

    public String getSHOP_ID()
    {
        return getStringProperty(ATTR_SHOP_ID);
    }

    public void setSHOP_ID(String value)
    {
        setProperty(ATTR_SHOP_ID, value, 0);
    }

    public String getCREATER()
    {
        return getStringProperty(ATTR_CREATER);
    }

    public void setCREATER(String value)
    {
        setProperty(ATTR_CREATER, value, 0);
    }

    public String getCREATE_NAME()
    {
        return getStringProperty(ATTR_CREATE_NAME);
    }

    public void setCREATE_NAME(String value)
    {
        setProperty(ATTR_CREATE_NAME, value, 0);
    }

    public java.util.Date getCREATE_TIME()
    {
        return getDateTimestampProperty(ATTR_CREATE_TIME);
    }

    public void setCREATE_TIME(java.util.Date value)
    {
        setProperty(ATTR_CREATE_TIME, value, 0);
    }

    public String getDEPT_ID()
    {
        return getStringProperty(ATTR_DEPT_ID);
    }

    public void setDEPT_ID(String value)
    {
        setProperty(ATTR_DEPT_ID, value, 0);
    }

    public String getDEPT_NAME()
    {
        return getStringProperty(ATTR_DEPT_NAME);
    }

    public void setDEPT_NAME(String value)
    {
        setProperty(ATTR_DEPT_NAME, value, 0);
    }

    public String getREMARK()
    {
        return getStringProperty(ATTR_REMARK);
    }

    public void setREMARK(String value)
    {
        setProperty(ATTR_REMARK, value, 0);
    }

    public String getTYPE()
    {
        return getStringProperty(ATTR_TYPE);
    }

    public void setTYPE(String value)
    {
        setProperty(ATTR_TYPE, value, 0);
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

    public String getTITLE()
    {
        return getStringProperty(ATTR_TITLE);
    }

    public void setTITLE(String value)
    {
        setProperty(ATTR_TITLE, value, 0);
    }

    public static BusObjectIterator<com.laiyifen.shop.SHOP_GENERAL_FORM> getNextShopGeneralFormObjects(String FORM_ID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"SHOP_GENERAL_FORM\" where (\"FORM_ID\" > :FORM_ID) order by \"FORM_ID\" asc";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("FORM_ID", "SHOP_GENERAL_FORM.FORM_ID", QueryObject.PARAM_STRING, FORM_ID);//NOPMD
        query.setResultClass(SHOP_GENERAL_FORM.class);
        query.setCursor(cursor);
        return query.getObjects();
    }

    public static BusObjectIterator<com.laiyifen.shop.SHOP_GENERAL_FORM> getPreviousShopGeneralFormObjects(String FORM_ID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"SHOP_GENERAL_FORM\" where (\"FORM_ID\" < :FORM_ID) order by \"FORM_ID\" desc";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("FORM_ID", "SHOP_GENERAL_FORM.FORM_ID", QueryObject.PARAM_STRING, FORM_ID);//NOPMD
        query.setResultClass(SHOP_GENERAL_FORM.class);
        query.setCursor(cursor);
        return query.getObjects();
    }


    public static com.laiyifen.shop.SHOP_GENERAL_FORM getShopGeneralFormObject(String FORM_ID)
    {
        String queryText = "select * from \"SHOP_GENERAL_FORM\" where \"FORM_ID\" = :FORM_ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("FORM_ID", "SHOP_GENERAL_FORM.FORM_ID", QueryObject.PARAM_STRING, FORM_ID);//NOPMD
        query.setResultClass(SHOP_GENERAL_FORM.class);
        return (SHOP_GENERAL_FORM)query.getObject();
    }

    public static BusObjectIterator<com.laiyifen.shop.SHOP_GENERAL_FORM> getShopGeneralFormObjects(String fromFORM_ID, String toFORM_ID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"SHOP_GENERAL_FORM\" where \"FORM_ID\" between :fromFORM_ID and :toFORM_ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("fromFORM_ID", "SHOP_GENERAL_FORM.FORM_ID", QueryObject.PARAM_STRING, fromFORM_ID);
        query.addParameter("toFORM_ID", "SHOP_GENERAL_FORM.FORM_ID", QueryObject.PARAM_STRING, toFORM_ID);
        query.setResultClass(SHOP_GENERAL_FORM.class);
        query.setCursor(cursor);
        return query.getObjects();
    }

    public static BusObjectIterator<com.laiyifen.shop.SHOP_GENERAL_FORM> getShopGeneralFormObjects(String fromFORM_ID, String toFORM_ID)
    {
        String queryText = "select * from \"SHOP_GENERAL_FORM\" where \"FORM_ID\" between :fromFORM_ID and :toFORM_ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("fromFORM_ID", "SHOP_GENERAL_FORM.FORM_ID", QueryObject.PARAM_STRING, fromFORM_ID);
        query.addParameter("toFORM_ID", "SHOP_GENERAL_FORM.FORM_ID", QueryObject.PARAM_STRING, toFORM_ID);
        query.setResultClass(SHOP_GENERAL_FORM.class);
        return query.getObjects();
    }

}

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


public abstract class SHOP_PROMOTIONAL_DETAILBase extends com.cordys.cpc.bsf.busobject.StateBusObject
{
    // tags used in the XML document
    public final static String ATTR_ID = "ID";
    public final static String ATTR_SHOP_NO = "SHOP_NO";
    public final static String ATTR_FORM_ID = "FORM_ID";
    public final static String ATTR_PROMOTIONAL_THEME = "PROMOTIONAL_THEME";
    public final static String ATTR_CONDITION_GROUP_MER_CODE = "CONDITION_GROUP_MER_CODE";
    public final static String ATTR_RESULT_GROUP_MER_CODE = "RESULT_GROUP_MER_CODE";
    public final static String ATTR_MAX_NO = "MAX_NO";
    public final static String ATTR_PROMOTIONAL_SHOP = "PROMOTIONAL_SHOP";
    public final static String ATTR_PROMOTIONAL_SHOP_GROUP = "PROMOTIONAL_SHOP_GROUP";
    public final static String ATTR_PROMOTIONAL_RULE_DESC = "PROMOTIONAL_RULE_DESC";
    public final static String ATTR_PROMOTION_GROUP_NAME = "PROMOTION_GROUP_NAME";
    public final static String ATTR_CONDITION_GROUP_TYPE = "CONDITION_GROUP_TYPE";
    public final static String ATTR_CONDITION_GROUP_MER_NAME = "CONDITION_GROUP_MER_NAME";
    public final static String ATTR_CONDITION_GROUP_NAME = "CONDITION_GROUP_NAME";
    public final static String ATTR_RESULT_GROUP_TYPE = "RESULT_GROUP_TYPE";
    public final static String ATTR_RESULT_GROUP_MER_NAME = "RESULT_GROUP_MER_NAME";
    public final static String ATTR_RESULT_GROUP_NO = "RESULT_GROUP_NO";
    public final static String ATTR_START_DATE = "START_DATE";
    public final static String ATTR_END_DATE = "END_DATE";
    public final static String ATTR_REMARK = "REMARK";
    public final static String ATTR_EXT1 = "EXT1";
    public final static String ATTR_EXT2 = "EXT2";
    private static ClassInfo s_classInfo = null;
    public static ClassInfo _getClassInfo()//NOPMD framework ensures this is thread safe
    {
        if ( s_classInfo == null )//NOPMD
        {
            s_classInfo = newClassInfo(SHOP_PROMOTIONAL_DETAIL.class);
            s_classInfo.setTableName("SHOP_PROMOTIONAL_DETAIL");
            s_classInfo.setUIDElements(new String[]{ATTR_ID});
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ID);
                ai.setJavaName(ATTR_ID);
                ai.setColumnName(ATTR_ID);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_ID);
                v.setMaxLength(40);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SHOP_NO);
                ai.setJavaName(ATTR_SHOP_NO);
                ai.setColumnName(ATTR_SHOP_NO);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SHOP_NO);
                v.setMaxLength(10);
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
                AttributeInfo ai = new AttributeInfo(ATTR_PROMOTIONAL_THEME);
                ai.setJavaName(ATTR_PROMOTIONAL_THEME);
                ai.setColumnName(ATTR_PROMOTIONAL_THEME);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_PROMOTIONAL_THEME);
                v.setMaxLength(100);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_CONDITION_GROUP_MER_CODE);
                ai.setJavaName(ATTR_CONDITION_GROUP_MER_CODE);
                ai.setColumnName(ATTR_CONDITION_GROUP_MER_CODE);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_CONDITION_GROUP_MER_CODE);
                v.setMaxLength(30);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_RESULT_GROUP_MER_CODE);
                ai.setJavaName(ATTR_RESULT_GROUP_MER_CODE);
                ai.setColumnName(ATTR_RESULT_GROUP_MER_CODE);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_RESULT_GROUP_MER_CODE);
                v.setMaxLength(30);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_MAX_NO);
                ai.setJavaName(ATTR_MAX_NO);
                ai.setColumnName(ATTR_MAX_NO);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_MAX_NO);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_PROMOTIONAL_SHOP);
                ai.setJavaName(ATTR_PROMOTIONAL_SHOP);
                ai.setColumnName(ATTR_PROMOTIONAL_SHOP);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_PROMOTIONAL_SHOP);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_PROMOTIONAL_SHOP_GROUP);
                ai.setJavaName(ATTR_PROMOTIONAL_SHOP_GROUP);
                ai.setColumnName(ATTR_PROMOTIONAL_SHOP_GROUP);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_PROMOTIONAL_SHOP_GROUP);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_PROMOTIONAL_RULE_DESC);
                ai.setJavaName(ATTR_PROMOTIONAL_RULE_DESC);
                ai.setColumnName(ATTR_PROMOTIONAL_RULE_DESC);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_PROMOTIONAL_RULE_DESC);
                v.setMaxLength(255);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_PROMOTION_GROUP_NAME);
                ai.setJavaName(ATTR_PROMOTION_GROUP_NAME);
                ai.setColumnName(ATTR_PROMOTION_GROUP_NAME);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_PROMOTION_GROUP_NAME);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_CONDITION_GROUP_TYPE);
                ai.setJavaName(ATTR_CONDITION_GROUP_TYPE);
                ai.setColumnName(ATTR_CONDITION_GROUP_TYPE);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_CONDITION_GROUP_TYPE);
                v.setMaxLength(30);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_CONDITION_GROUP_MER_NAME);
                ai.setJavaName(ATTR_CONDITION_GROUP_MER_NAME);
                ai.setColumnName(ATTR_CONDITION_GROUP_MER_NAME);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_CONDITION_GROUP_MER_NAME);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_CONDITION_GROUP_NAME);
                ai.setJavaName(ATTR_CONDITION_GROUP_NAME);
                ai.setColumnName(ATTR_CONDITION_GROUP_NAME);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_CONDITION_GROUP_NAME);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_RESULT_GROUP_TYPE);
                ai.setJavaName(ATTR_RESULT_GROUP_TYPE);
                ai.setColumnName(ATTR_RESULT_GROUP_TYPE);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_RESULT_GROUP_TYPE);
                v.setMaxLength(30);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_RESULT_GROUP_MER_NAME);
                ai.setJavaName(ATTR_RESULT_GROUP_MER_NAME);
                ai.setColumnName(ATTR_RESULT_GROUP_MER_NAME);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_RESULT_GROUP_MER_NAME);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_RESULT_GROUP_NO);
                ai.setJavaName(ATTR_RESULT_GROUP_NO);
                ai.setColumnName(ATTR_RESULT_GROUP_NO);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_RESULT_GROUP_NO);
                v.setMaxLength(30);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_START_DATE);
                ai.setJavaName(ATTR_START_DATE);
                ai.setColumnName(ATTR_START_DATE);
                ai.setAttributeClass(java.util.Date.class);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_END_DATE);
                ai.setJavaName(ATTR_END_DATE);
                ai.setColumnName(ATTR_END_DATE);
                ai.setAttributeClass(java.util.Date.class);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_REMARK);
                ai.setJavaName(ATTR_REMARK);
                ai.setColumnName(ATTR_REMARK);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_REMARK);
                v.setMaxLength(255);
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
        }
        return s_classInfo;
    }

    public SHOP_PROMOTIONAL_DETAILBase(BusObjectConfig config)
    {
        super(config);
    }

    public String getID()
    {
        return getStringProperty(ATTR_ID);
    }

    public void setID(String value)
    {
        setProperty(ATTR_ID, value, 0);
    }

    public String getSHOP_NO()
    {
        return getStringProperty(ATTR_SHOP_NO);
    }

    public void setSHOP_NO(String value)
    {
        setProperty(ATTR_SHOP_NO, value, 0);
    }

    public String getFORM_ID()
    {
        return getStringProperty(ATTR_FORM_ID);
    }

    public void setFORM_ID(String value)
    {
        setProperty(ATTR_FORM_ID, value, 0);
    }

    public String getPROMOTIONAL_THEME()
    {
        return getStringProperty(ATTR_PROMOTIONAL_THEME);
    }

    public void setPROMOTIONAL_THEME(String value)
    {
        setProperty(ATTR_PROMOTIONAL_THEME, value, 0);
    }

    public String getCONDITION_GROUP_MER_CODE()
    {
        return getStringProperty(ATTR_CONDITION_GROUP_MER_CODE);
    }

    public void setCONDITION_GROUP_MER_CODE(String value)
    {
        setProperty(ATTR_CONDITION_GROUP_MER_CODE, value, 0);
    }

    public String getRESULT_GROUP_MER_CODE()
    {
        return getStringProperty(ATTR_RESULT_GROUP_MER_CODE);
    }

    public void setRESULT_GROUP_MER_CODE(String value)
    {
        setProperty(ATTR_RESULT_GROUP_MER_CODE, value, 0);
    }

    public String getMAX_NO()
    {
        return getStringProperty(ATTR_MAX_NO);
    }

    public void setMAX_NO(String value)
    {
        setProperty(ATTR_MAX_NO, value, 0);
    }

    public String getPROMOTIONAL_SHOP()
    {
        return getStringProperty(ATTR_PROMOTIONAL_SHOP);
    }

    public void setPROMOTIONAL_SHOP(String value)
    {
        setProperty(ATTR_PROMOTIONAL_SHOP, value, 0);
    }

    public String getPROMOTIONAL_SHOP_GROUP()
    {
        return getStringProperty(ATTR_PROMOTIONAL_SHOP_GROUP);
    }

    public void setPROMOTIONAL_SHOP_GROUP(String value)
    {
        setProperty(ATTR_PROMOTIONAL_SHOP_GROUP, value, 0);
    }

    public String getPROMOTIONAL_RULE_DESC()
    {
        return getStringProperty(ATTR_PROMOTIONAL_RULE_DESC);
    }

    public void setPROMOTIONAL_RULE_DESC(String value)
    {
        setProperty(ATTR_PROMOTIONAL_RULE_DESC, value, 0);
    }

    public String getPROMOTION_GROUP_NAME()
    {
        return getStringProperty(ATTR_PROMOTION_GROUP_NAME);
    }

    public void setPROMOTION_GROUP_NAME(String value)
    {
        setProperty(ATTR_PROMOTION_GROUP_NAME, value, 0);
    }

    public String getCONDITION_GROUP_TYPE()
    {
        return getStringProperty(ATTR_CONDITION_GROUP_TYPE);
    }

    public void setCONDITION_GROUP_TYPE(String value)
    {
        setProperty(ATTR_CONDITION_GROUP_TYPE, value, 0);
    }

    public String getCONDITION_GROUP_MER_NAME()
    {
        return getStringProperty(ATTR_CONDITION_GROUP_MER_NAME);
    }

    public void setCONDITION_GROUP_MER_NAME(String value)
    {
        setProperty(ATTR_CONDITION_GROUP_MER_NAME, value, 0);
    }

    public String getCONDITION_GROUP_NAME()
    {
        return getStringProperty(ATTR_CONDITION_GROUP_NAME);
    }

    public void setCONDITION_GROUP_NAME(String value)
    {
        setProperty(ATTR_CONDITION_GROUP_NAME, value, 0);
    }

    public String getRESULT_GROUP_TYPE()
    {
        return getStringProperty(ATTR_RESULT_GROUP_TYPE);
    }

    public void setRESULT_GROUP_TYPE(String value)
    {
        setProperty(ATTR_RESULT_GROUP_TYPE, value, 0);
    }

    public String getRESULT_GROUP_MER_NAME()
    {
        return getStringProperty(ATTR_RESULT_GROUP_MER_NAME);
    }

    public void setRESULT_GROUP_MER_NAME(String value)
    {
        setProperty(ATTR_RESULT_GROUP_MER_NAME, value, 0);
    }

    public String getRESULT_GROUP_NO()
    {
        return getStringProperty(ATTR_RESULT_GROUP_NO);
    }

    public void setRESULT_GROUP_NO(String value)
    {
        setProperty(ATTR_RESULT_GROUP_NO, value, 0);
    }

    public java.util.Date getSTART_DATE()
    {
        return getDateTimestampProperty(ATTR_START_DATE);
    }

    public void setSTART_DATE(java.util.Date value)
    {
        setProperty(ATTR_START_DATE, value, 0);
    }

    public java.util.Date getEND_DATE()
    {
        return getDateTimestampProperty(ATTR_END_DATE);
    }

    public void setEND_DATE(java.util.Date value)
    {
        setProperty(ATTR_END_DATE, value, 0);
    }

    public String getREMARK()
    {
        return getStringProperty(ATTR_REMARK);
    }

    public void setREMARK(String value)
    {
        setProperty(ATTR_REMARK, value, 0);
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

    public static BusObjectIterator<com.laiyifen.shop.SHOP_PROMOTIONAL_DETAIL> getNextShopPromotionalDetailObjects(String ID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"SHOP_PROMOTIONAL_DETAIL\" where (\"ID\" > :ID) order by \"ID\" asc";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("ID", "SHOP_PROMOTIONAL_DETAIL.ID", QueryObject.PARAM_STRING, ID);//NOPMD
        query.setResultClass(SHOP_PROMOTIONAL_DETAIL.class);
        query.setCursor(cursor);
        return query.getObjects();
    }

    public static BusObjectIterator<com.laiyifen.shop.SHOP_PROMOTIONAL_DETAIL> getPreviousShopPromotionalDetailObjects(String ID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"SHOP_PROMOTIONAL_DETAIL\" where (\"ID\" < :ID) order by \"ID\" desc";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("ID", "SHOP_PROMOTIONAL_DETAIL.ID", QueryObject.PARAM_STRING, ID);//NOPMD
        query.setResultClass(SHOP_PROMOTIONAL_DETAIL.class);
        query.setCursor(cursor);
        return query.getObjects();
    }



    public static com.laiyifen.shop.SHOP_PROMOTIONAL_DETAIL getShopPromotionalDetailObject(String ID)
    {
        String queryText = "select * from \"SHOP_PROMOTIONAL_DETAIL\" where \"ID\" = :ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("ID", "SHOP_PROMOTIONAL_DETAIL.ID", QueryObject.PARAM_STRING, ID);//NOPMD
        query.setResultClass(SHOP_PROMOTIONAL_DETAIL.class);
        return (SHOP_PROMOTIONAL_DETAIL)query.getObject();
    }

    public static BusObjectIterator<com.laiyifen.shop.SHOP_PROMOTIONAL_DETAIL> getShopPromotionalDetailObjects(String fromID, String toID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"SHOP_PROMOTIONAL_DETAIL\" where \"ID\" between :fromID and :toID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("fromID", "SHOP_PROMOTIONAL_DETAIL.ID", QueryObject.PARAM_STRING, fromID);
        query.addParameter("toID", "SHOP_PROMOTIONAL_DETAIL.ID", QueryObject.PARAM_STRING, toID);
        query.setResultClass(SHOP_PROMOTIONAL_DETAIL.class);
        query.setCursor(cursor);
        return query.getObjects();
    }

    public static BusObjectIterator<com.laiyifen.shop.SHOP_PROMOTIONAL_DETAIL> getShopPromotionalDetailObjects(String fromID, String toID)
    {
        String queryText = "select * from \"SHOP_PROMOTIONAL_DETAIL\" where \"ID\" between :fromID and :toID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("fromID", "SHOP_PROMOTIONAL_DETAIL.ID", QueryObject.PARAM_STRING, fromID);
        query.addParameter("toID", "SHOP_PROMOTIONAL_DETAIL.ID", QueryObject.PARAM_STRING, toID);
        query.setResultClass(SHOP_PROMOTIONAL_DETAIL.class);
        return query.getObjects();
    }

}

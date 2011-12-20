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


public abstract class SHOP_GRP_CONFIGBase extends com.cordys.cpc.bsf.busobject.StateBusObject
{
    // tags used in the XML document
    public final static String ATTR_GRP_ID = "GRP_ID";
    public final static String ATTR_GRP_NAME = "GRP_NAME";
    public final static String ATTR_GRP_SAPID = "GRP_SAPID";
    public final static String ATTR_GRPTYPE = "GRPTYPE";
    public final static String ATTR_STATUS = "STATUS";
    public final static String ATTR_EXT2 = "EXT2";
    public final static String ATTR_EXT3 = "EXT3";
    private static ClassInfo s_classInfo = null;
    public static ClassInfo _getClassInfo()//NOPMD framework ensures this is thread safe
    {
        if ( s_classInfo == null )//NOPMD
        {
            s_classInfo = newClassInfo(SHOP_GRP_CONFIG.class);
            s_classInfo.setTableName("SHOP_GRP_CONFIG");
            s_classInfo.setUIDElements(new String[]{ATTR_GRP_ID});
            {
                AttributeInfo ai = new AttributeInfo(ATTR_GRP_ID);
                ai.setJavaName(ATTR_GRP_ID);
                ai.setColumnName(ATTR_GRP_ID);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_GRP_ID);
                v.setMaxLength(40);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_GRP_NAME);
                ai.setJavaName(ATTR_GRP_NAME);
                ai.setColumnName(ATTR_GRP_NAME);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_GRP_NAME);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_GRP_SAPID);
                ai.setJavaName(ATTR_GRP_SAPID);
                ai.setColumnName(ATTR_GRP_SAPID);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_GRP_SAPID);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_GRPTYPE);
                ai.setJavaName(ATTR_GRPTYPE);
                ai.setColumnName(ATTR_GRPTYPE);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_GRPTYPE);
                v.setMaxLength(2);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_STATUS);
                ai.setJavaName(ATTR_STATUS);
                ai.setColumnName(ATTR_STATUS);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_STATUS);
                v.setMaxLength(2);
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
                AttributeInfo ai = new AttributeInfo(ATTR_EXT3);
                ai.setJavaName(ATTR_EXT3);
                ai.setColumnName(ATTR_EXT3);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_EXT3);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
        }
        return s_classInfo;
    }

    public SHOP_GRP_CONFIGBase(BusObjectConfig config)
    {
        super(config);
    }

    public String getGRP_ID()
    {
        return getStringProperty(ATTR_GRP_ID);
    }

    public void setGRP_ID(String value)
    {
        setProperty(ATTR_GRP_ID, value, 0);
    }

    public String getGRP_NAME()
    {
        return getStringProperty(ATTR_GRP_NAME);
    }

    public void setGRP_NAME(String value)
    {
        setProperty(ATTR_GRP_NAME, value, 0);
    }

    public double getGRP_SAPID()
    {
        return getDoubleProperty(ATTR_GRP_SAPID);
    }

    public void setGRP_SAPID(double value)
    {
        setProperty(ATTR_GRP_SAPID, value, 0);
    }

    public String getGRPTYPE()
    {
        return getStringProperty(ATTR_GRPTYPE);
    }

    public void setGRPTYPE(String value)
    {
        setProperty(ATTR_GRPTYPE, value, 0);
    }

    public String getSTATUS()
    {
        return getStringProperty(ATTR_STATUS);
    }

    public void setSTATUS(String value)
    {
        setProperty(ATTR_STATUS, value, 0);
    }

    public String getEXT2()
    {
        return getStringProperty(ATTR_EXT2);
    }

    public void setEXT2(String value)
    {
        setProperty(ATTR_EXT2, value, 0);
    }

    public String getEXT3()
    {
        return getStringProperty(ATTR_EXT3);
    }

    public void setEXT3(String value)
    {
        setProperty(ATTR_EXT3, value, 0);
    }

    public static BusObjectIterator<com.laiyifen.shop.SHOP_GRP_CONFIG> getNextShopGrpConfigObjects(String GRP_ID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"SHOP_GRP_CONFIG\" where (\"GRP_ID\" > :GRP_ID) order by \"GRP_ID\" asc";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("GRP_ID", "SHOP_GRP_CONFIG.GRP_ID", QueryObject.PARAM_STRING, GRP_ID);//NOPMD
        query.setResultClass(SHOP_GRP_CONFIG.class);
        query.setCursor(cursor);
        return query.getObjects();
    }

    public static BusObjectIterator<com.laiyifen.shop.SHOP_GRP_CONFIG> getPreviousShopGrpConfigObjects(String GRP_ID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"SHOP_GRP_CONFIG\" where (\"GRP_ID\" < :GRP_ID) order by \"GRP_ID\" desc";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("GRP_ID", "SHOP_GRP_CONFIG.GRP_ID", QueryObject.PARAM_STRING, GRP_ID);//NOPMD
        query.setResultClass(SHOP_GRP_CONFIG.class);
        query.setCursor(cursor);
        return query.getObjects();
    }

    public static com.laiyifen.shop.SHOP_GRP_CONFIG getShopGrpConfigObject(String GRP_ID)
    {
        String queryText = "select * from \"SHOP_GRP_CONFIG\" where \"GRP_ID\" = :GRP_ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("GRP_ID", "SHOP_GRP_CONFIG.GRP_ID", QueryObject.PARAM_STRING, GRP_ID);//NOPMD
        query.setResultClass(SHOP_GRP_CONFIG.class);
        return (SHOP_GRP_CONFIG)query.getObject();
    }

    public static BusObjectIterator<com.laiyifen.shop.SHOP_GRP_CONFIG> getShopGrpConfigObjects(String fromGRP_ID, String toGRP_ID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"SHOP_GRP_CONFIG\" where \"GRP_ID\" between :fromGRP_ID and :toGRP_ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("fromGRP_ID", "SHOP_GRP_CONFIG.GRP_ID", QueryObject.PARAM_STRING, fromGRP_ID);
        query.addParameter("toGRP_ID", "SHOP_GRP_CONFIG.GRP_ID", QueryObject.PARAM_STRING, toGRP_ID);
        query.setResultClass(SHOP_GRP_CONFIG.class);
        query.setCursor(cursor);
        return query.getObjects();
    }

    public static BusObjectIterator<com.laiyifen.shop.SHOP_GRP_CONFIG> getShopGrpConfigObjects(String fromGRP_ID, String toGRP_ID)
    {
        String queryText = "select * from \"SHOP_GRP_CONFIG\" where \"GRP_ID\" between :fromGRP_ID and :toGRP_ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("fromGRP_ID", "SHOP_GRP_CONFIG.GRP_ID", QueryObject.PARAM_STRING, fromGRP_ID);
        query.addParameter("toGRP_ID", "SHOP_GRP_CONFIG.GRP_ID", QueryObject.PARAM_STRING, toGRP_ID);
        query.setResultClass(SHOP_GRP_CONFIG.class);
        return query.getObjects();
    }


}
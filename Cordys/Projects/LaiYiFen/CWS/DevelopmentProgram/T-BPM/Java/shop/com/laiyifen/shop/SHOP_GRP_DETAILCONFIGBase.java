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


public abstract class SHOP_GRP_DETAILCONFIGBase extends com.cordys.cpc.bsf.busobject.StateBusObject
{
    // tags used in the XML document
    public final static String ATTR_ID = "ID";
    public final static String ATTR_GRP_ID = "GRP_ID";
    public final static String ATTR_MATERIAL_CODE = "MATERIAL_CODE";
    public final static String ATTR_MATERIAL_NAME = "MATERIAL_NAME";
    public final static String ATTR_EXT1 = "EXT1";
    public final static String ATTR_EXT2 = "EXT2";
    private static ClassInfo s_classInfo = null;
    public static ClassInfo _getClassInfo()//NOPMD framework ensures this is thread safe
    {
        if ( s_classInfo == null )//NOPMD
        {
            s_classInfo = newClassInfo(SHOP_GRP_DETAILCONFIG.class);
            s_classInfo.setTableName("SHOP_GRP_DETAILCONFIG");
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
                AttributeInfo ai = new AttributeInfo(ATTR_MATERIAL_CODE);
                ai.setJavaName(ATTR_MATERIAL_CODE);
                ai.setColumnName(ATTR_MATERIAL_CODE);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_MATERIAL_CODE);
                v.setMaxLength(18);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_MATERIAL_NAME);
                ai.setJavaName(ATTR_MATERIAL_NAME);
                ai.setColumnName(ATTR_MATERIAL_NAME);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_MATERIAL_NAME);
                v.setMaxLength(50);
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

    public SHOP_GRP_DETAILCONFIGBase(BusObjectConfig config)
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

    public String getGRP_ID()
    {
        return getStringProperty(ATTR_GRP_ID);
    }

    public void setGRP_ID(String value)
    {
        setProperty(ATTR_GRP_ID, value, 0);
    }

    public String getMATERIAL_CODE()
    {
        return getStringProperty(ATTR_MATERIAL_CODE);
    }

    public void setMATERIAL_CODE(String value)
    {
        setProperty(ATTR_MATERIAL_CODE, value, 0);
    }

    public String getMATERIAL_NAME()
    {
        return getStringProperty(ATTR_MATERIAL_NAME);
    }

    public void setMATERIAL_NAME(String value)
    {
        setProperty(ATTR_MATERIAL_NAME, value, 0);
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

    public static BusObjectIterator<com.laiyifen.shop.SHOP_GRP_DETAILCONFIG> getNextShopGrpDetailconfigObjects(com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"SHOP_GRP_DETAILCONFIG\"";
        QueryObject query = new QueryObject(queryText);
        query.setResultClass(SHOP_GRP_DETAILCONFIG.class);
        query.setCursor(cursor);
        return query.getObjects();
    }

    public static BusObjectIterator<com.laiyifen.shop.SHOP_GRP_DETAILCONFIG> getPreviousShopGrpDetailconfigObjects(String ID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"SHOP_GRP_DETAILCONFIG\" where (\"ID\" < :ID) order by \"ID\" desc";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("ID", "SHOP_GRP_DETAILCONFIG.ID", QueryObject.PARAM_STRING, ID);//NOPMD
        query.setResultClass(SHOP_GRP_DETAILCONFIG.class);
        query.setCursor(cursor);
        return query.getObjects();
    }

    public static com.laiyifen.shop.SHOP_GRP_DETAILCONFIG getShopGrpDetailconfigObject(String ID)
    {
        String queryText = "select * from \"SHOP_GRP_DETAILCONFIG\" where \"ID\" = :ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("ID", "SHOP_GRP_DETAILCONFIG.ID", QueryObject.PARAM_STRING, ID);//NOPMD
        query.setResultClass(SHOP_GRP_DETAILCONFIG.class);
        return (SHOP_GRP_DETAILCONFIG)query.getObject();
    }

    public static BusObjectIterator<com.laiyifen.shop.SHOP_GRP_DETAILCONFIG> getShopGrpDetailconfigObjects(String fromID, String toID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"SHOP_GRP_DETAILCONFIG\" where \"ID\" between :fromID and :toID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("fromID", "SHOP_GRP_DETAILCONFIG.ID", QueryObject.PARAM_STRING, fromID);
        query.addParameter("toID", "SHOP_GRP_DETAILCONFIG.ID", QueryObject.PARAM_STRING, toID);
        query.setResultClass(SHOP_GRP_DETAILCONFIG.class);
        query.setCursor(cursor);
        return query.getObjects();
    }

    public static BusObjectIterator<com.laiyifen.shop.SHOP_GRP_DETAILCONFIG> getShopGrpDetailconfigObjects(String fromID, String toID)
    {
        String queryText = "select * from \"SHOP_GRP_DETAILCONFIG\" where \"ID\" between :fromID and :toID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("fromID", "SHOP_GRP_DETAILCONFIG.ID", QueryObject.PARAM_STRING, fromID);
        query.addParameter("toID", "SHOP_GRP_DETAILCONFIG.ID", QueryObject.PARAM_STRING, toID);
        query.setResultClass(SHOP_GRP_DETAILCONFIG.class);
        return query.getObjects();
    }


}

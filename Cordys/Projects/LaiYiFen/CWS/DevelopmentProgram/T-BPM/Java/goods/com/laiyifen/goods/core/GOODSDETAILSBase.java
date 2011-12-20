/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.goods.core;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.busobject.QueryObject;
import com.cordys.cpc.bsf.classinfo.AttributeInfo;
import com.cordys.cpc.bsf.classinfo.ClassInfo;
import com.cordys.cpc.bsf.listeners.constraint.NumberValidator;
import com.cordys.cpc.bsf.listeners.constraint.StringValidator;


public abstract class GOODSDETAILSBase extends com.cordys.cpc.bsf.busobject.StateBusObject
{
    // tags used in the XML document
    public final static String ATTR_FORM_ID = "FORM_ID";
    public final static String ATTR_ORDER_ID = "ORDER_ID";
    public final static String ATTR_PRODUCT_NAME = "PRODUCT_NAME";
    public final static String ATTR_ZHONGLIANG = "ZHONGLIANG";
    public final static String ATTR_DANJIA = "DANJIA";
    public final static String ATTR_JINER = "JINER";
    public final static String ATTR_FENBAOSHULIANG = "FENBAOSHULIANG";
    public final static String ATTR_HEJIZHONGLIANG = "HEJIZHONGLIANG";
    public final static String ATTR_HEJI = "HEJI";
    public final static String ATTR_BEIZHU = "BEIZHU";
    public final static String ATTR_ZONGJI = "ZONGJI";
    private static ClassInfo s_classInfo = null;
    public static ClassInfo _getClassInfo()//NOPMD framework ensures this is thread safe
    {
        if ( s_classInfo == null )//NOPMD
        {
            s_classInfo = newClassInfo(GOODSDETAILS.class);
            s_classInfo.setTableName("GOODSDETAILS");
            s_classInfo.setUIDElements(new String[]{ATTR_FORM_ID});
            {
                AttributeInfo ai = new AttributeInfo(ATTR_FORM_ID);
                ai.setJavaName(ATTR_FORM_ID);
                ai.setColumnName(ATTR_FORM_ID);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_FORM_ID);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ORDER_ID);
                ai.setJavaName(ATTR_ORDER_ID);
                ai.setColumnName(ATTR_ORDER_ID);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_ORDER_ID);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_PRODUCT_NAME);
                ai.setJavaName(ATTR_PRODUCT_NAME);
                ai.setColumnName(ATTR_PRODUCT_NAME);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_PRODUCT_NAME);
                v.setMaxLength(20);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ZHONGLIANG);
                ai.setJavaName(ATTR_ZHONGLIANG);
                ai.setColumnName(ATTR_ZHONGLIANG);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_ZHONGLIANG);
                v.setMaxLength(20);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_DANJIA);
                ai.setJavaName(ATTR_DANJIA);
                ai.setColumnName(ATTR_DANJIA);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_DANJIA);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_JINER);
                ai.setJavaName(ATTR_JINER);
                ai.setColumnName(ATTR_JINER);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_JINER);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_FENBAOSHULIANG);
                ai.setJavaName(ATTR_FENBAOSHULIANG);
                ai.setColumnName(ATTR_FENBAOSHULIANG);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_FENBAOSHULIANG);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_HEJIZHONGLIANG);
                ai.setJavaName(ATTR_HEJIZHONGLIANG);
                ai.setColumnName(ATTR_HEJIZHONGLIANG);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_HEJIZHONGLIANG);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_HEJI);
                ai.setJavaName(ATTR_HEJI);
                ai.setColumnName(ATTR_HEJI);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_HEJI);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_BEIZHU);
                ai.setJavaName(ATTR_BEIZHU);
                ai.setColumnName(ATTR_BEIZHU);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_BEIZHU);
                v.setMaxLength(100);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ZONGJI);
                ai.setJavaName(ATTR_ZONGJI);
                ai.setColumnName(ATTR_ZONGJI);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_ZONGJI);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
        }
        return s_classInfo;
    }

    public GOODSDETAILSBase(BusObjectConfig config)
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

    public String getORDER_ID()
    {
        return getStringProperty(ATTR_ORDER_ID);
    }

    public void setORDER_ID(String value)
    {
        setProperty(ATTR_ORDER_ID, value, 0);
    }

    public String getPRODUCT_NAME()
    {
        return getStringProperty(ATTR_PRODUCT_NAME);
    }

    public void setPRODUCT_NAME(String value)
    {
        setProperty(ATTR_PRODUCT_NAME, value, 0);
    }

    public String getZHONGLIANG()
    {
        return getStringProperty(ATTR_ZHONGLIANG);
    }

    public void setZHONGLIANG(String value)
    {
        setProperty(ATTR_ZHONGLIANG, value, 0);
    }

    public double getDANJIA()
    {
        return getDoubleProperty(ATTR_DANJIA);
    }

    public void setDANJIA(double value)
    {
        setProperty(ATTR_DANJIA, value, 0);
    }

    public double getJINER()
    {
        return getDoubleProperty(ATTR_JINER);
    }

    public void setJINER(double value)
    {
        setProperty(ATTR_JINER, value, 0);
    }

    public double getFENBAOSHULIANG()
    {
        return getDoubleProperty(ATTR_FENBAOSHULIANG);
    }

    public void setFENBAOSHULIANG(double value)
    {
        setProperty(ATTR_FENBAOSHULIANG, value, 0);
    }

    public double getHEJIZHONGLIANG()
    {
        return getDoubleProperty(ATTR_HEJIZHONGLIANG);
    }

    public void setHEJIZHONGLIANG(double value)
    {
        setProperty(ATTR_HEJIZHONGLIANG, value, 0);
    }

    public double getHEJI()
    {
        return getDoubleProperty(ATTR_HEJI);
    }

    public void setHEJI(double value)
    {
        setProperty(ATTR_HEJI, value, 0);
    }

    public String getBEIZHU()
    {
        return getStringProperty(ATTR_BEIZHU);
    }

    public void setBEIZHU(String value)
    {
        setProperty(ATTR_BEIZHU, value, 0);
    }

    public double getZONGJI()
    {
        return getDoubleProperty(ATTR_ZONGJI);
    }

    public void setZONGJI(double value)
    {
        setProperty(ATTR_ZONGJI, value, 0);
    }

    public static com.laiyifen.goods.core.GOODSDETAILS getGoodsdetailsObject(String FORM_ID)
    {
        String queryText = "select * from \"GOODSDETAILS\" where \"FORM_ID\" = :FORM_ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("FORM_ID", "GOODSDETAILS.FORM_ID", QueryObject.PARAM_STRING, FORM_ID);//NOPMD
        query.setResultClass(GOODSDETAILS.class);
        return (GOODSDETAILS)query.getObject();
    }

    public static BusObjectIterator<com.laiyifen.goods.core.GOODSDETAILS> getGoodsdetailsObjects(String fromFORM_ID, String toFORM_ID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"GOODSDETAILS\" where \"FORM_ID\" between :fromFORM_ID and :toFORM_ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("fromFORM_ID", "GOODSDETAILS.FORM_ID", QueryObject.PARAM_STRING, fromFORM_ID);
        query.addParameter("toFORM_ID", "GOODSDETAILS.FORM_ID", QueryObject.PARAM_STRING, toFORM_ID);
        query.setResultClass(GOODSDETAILS.class);
        query.setCursor(cursor);
        return query.getObjects();
    }

    public static BusObjectIterator<com.laiyifen.goods.core.GOODSDETAILS> getGoodsdetailsObjects(String fromFORM_ID, String toFORM_ID)
    {
        String queryText = "select * from \"GOODSDETAILS\" where \"FORM_ID\" between :fromFORM_ID and :toFORM_ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("fromFORM_ID", "GOODSDETAILS.FORM_ID", QueryObject.PARAM_STRING, fromFORM_ID);
        query.addParameter("toFORM_ID", "GOODSDETAILS.FORM_ID", QueryObject.PARAM_STRING, toFORM_ID);
        query.setResultClass(GOODSDETAILS.class);
        return query.getObjects();
    }

    public static BusObjectIterator<com.laiyifen.goods.core.GOODSDETAILS> getNextGoodsdetailsObjects(String FORM_ID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"GOODSDETAILS\" where (\"FORM_ID\" > :FORM_ID) order by \"FORM_ID\" asc";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("FORM_ID", "GOODSDETAILS.FORM_ID", QueryObject.PARAM_STRING, FORM_ID);//NOPMD
        query.setResultClass(GOODSDETAILS.class);
        query.setCursor(cursor);
        return query.getObjects();
    }

    public static BusObjectIterator<com.laiyifen.goods.core.GOODSDETAILS> getPreviousGoodsdetailsObjects(String FORM_ID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"GOODSDETAILS\" where (\"FORM_ID\" < :FORM_ID) order by \"FORM_ID\" desc";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("FORM_ID", "GOODSDETAILS.FORM_ID", QueryObject.PARAM_STRING, FORM_ID);//NOPMD
        query.setResultClass(GOODSDETAILS.class);
        query.setCursor(cursor);
        return query.getObjects();
    }

}

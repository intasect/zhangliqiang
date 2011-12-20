/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.bpc.core;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.busobject.QueryObject;
import com.cordys.cpc.bsf.classinfo.AttributeInfo;
import com.cordys.cpc.bsf.classinfo.ClassInfo;
import com.cordys.cpc.bsf.listeners.constraint.NumberValidator;
import com.cordys.cpc.bsf.listeners.constraint.StringValidator;


public abstract class BPC_FEEDEPBase extends com.cordys.cpc.bsf.busobject.StateBusObject
{
    // tags used in the XML document
    public final static String ATTR_ID = "ID";
    public final static String ATTR_FORM_ID = "FORM_ID";
    public final static String ATTR_DEP_CODE = "DEP_CODE";
    public final static String ATTR_DEP_NAME = "DEP_NAME";
    public final static String ATTR_SUM = "SUM";
    public final static String ATTR_REMARK = "REMARK";
    public final static String ATTR_SUBJECT_CODE = "SUBJECT_CODE";
    public final static String ATTR_SUBJECT_NAME = "SUBJECT_NAME";
    public final static String ATTR_ext1 = "ext1";
    public final static String ATTR_ext2 = "ext2";
    public final static String ATTR_ext3 = "ext3";
    private static ClassInfo s_classInfo = null;
    public static ClassInfo _getClassInfo()//NOPMD framework ensures this is thread safe
    {
        if ( s_classInfo == null )//NOPMD
        {
            s_classInfo = newClassInfo(BPC_FEEDEP.class);
            s_classInfo.setTableName("BPC_FEEDEP");
            s_classInfo.setUIDElements(new String[]{ATTR_ID});
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ID);
                ai.setJavaName(ATTR_ID);
                ai.setColumnName(ATTR_ID);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_ID);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
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
                AttributeInfo ai = new AttributeInfo(ATTR_DEP_CODE);
                ai.setJavaName(ATTR_DEP_CODE);
                ai.setColumnName(ATTR_DEP_CODE);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_DEP_CODE);
                v.setMaxLength(20);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_DEP_NAME);
                ai.setJavaName(ATTR_DEP_NAME);
                ai.setColumnName(ATTR_DEP_NAME);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_DEP_NAME);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SUM);
                ai.setJavaName(ATTR_SUM);
                ai.setColumnName(ATTR_SUM);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_SUM);
                ai.addConstraintHandler(v);
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
                AttributeInfo ai = new AttributeInfo(ATTR_SUBJECT_CODE);
                ai.setJavaName(ATTR_SUBJECT_CODE);
                ai.setColumnName(ATTR_SUBJECT_CODE);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SUBJECT_CODE);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SUBJECT_NAME);
                ai.setJavaName(ATTR_SUBJECT_NAME);
                ai.setColumnName(ATTR_SUBJECT_NAME);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SUBJECT_NAME);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ext1);
                ai.setJavaName(ATTR_ext1);
                ai.setColumnName(ATTR_ext1);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_ext1);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ext2);
                ai.setJavaName(ATTR_ext2);
                ai.setColumnName(ATTR_ext2);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_ext2);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ext3);
                ai.setJavaName(ATTR_ext3);
                ai.setColumnName(ATTR_ext3);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_ext3);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
        }
        return s_classInfo;
    }

    public BPC_FEEDEPBase(BusObjectConfig config)
    {
        super(config);
    }

    public double getID()
    {
        return getDoubleProperty(ATTR_ID);
    }

    public void setID(double value)
    {
        setProperty(ATTR_ID, value, 0);
    }

    public String getFORM_ID()
    {
        return getStringProperty(ATTR_FORM_ID);
    }

    public void setFORM_ID(String value)
    {
        setProperty(ATTR_FORM_ID, value, 0);
    }

    public String getDEP_CODE()
    {
        return getStringProperty(ATTR_DEP_CODE);
    }

    public void setDEP_CODE(String value)
    {
        setProperty(ATTR_DEP_CODE, value, 0);
    }

    public String getDEP_NAME()
    {
        return getStringProperty(ATTR_DEP_NAME);
    }

    public void setDEP_NAME(String value)
    {
        setProperty(ATTR_DEP_NAME, value, 0);
    }

    public double getSUM()
    {
        return getDoubleProperty(ATTR_SUM);
    }

    public void setSUM(double value)
    {
        setProperty(ATTR_SUM, value, 0);
    }

    public String getREMARK()
    {
        return getStringProperty(ATTR_REMARK);
    }

    public void setREMARK(String value)
    {
        setProperty(ATTR_REMARK, value, 0);
    }

    public String getSUBJECT_CODE()
    {
        return getStringProperty(ATTR_SUBJECT_CODE);
    }

    public void setSUBJECT_CODE(String value)
    {
        setProperty(ATTR_SUBJECT_CODE, value, 0);
    }

    public String getSUBJECT_NAME()
    {
        return getStringProperty(ATTR_SUBJECT_NAME);
    }

    public void setSUBJECT_NAME(String value)
    {
        setProperty(ATTR_SUBJECT_NAME, value, 0);
    }

    public String getExt1()
    {
        return getStringProperty(ATTR_ext1);
    }

    public void setExt1(String value)
    {
        setProperty(ATTR_ext1, value, 0);
    }

    public String getExt2()
    {
        return getStringProperty(ATTR_ext2);
    }

    public void setExt2(String value)
    {
        setProperty(ATTR_ext2, value, 0);
    }

    public String getExt3()
    {
        return getStringProperty(ATTR_ext3);
    }

    public void setExt3(String value)
    {
        setProperty(ATTR_ext3, value, 0);
    }

    public static com.laiyifen.bpc.core.BPC_FEEDEP getBpcFeedepObject(double ID)
    {
        String queryText = "select * from \"BPC_FEEDEP\" where \"ID\" = :ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("ID", "BPC_FEEDEP.ID", QueryObject.PARAM_DOUBLE, new Double(ID));//NOPMD
        query.setResultClass(BPC_FEEDEP.class);
        return (BPC_FEEDEP)query.getObject();
    }

    public static BusObjectIterator<com.laiyifen.bpc.core.BPC_FEEDEP> getBpcFeedepObjects(double fromID, double toID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"BPC_FEEDEP\" where \"ID\" between :fromID and :toID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("fromID", "BPC_FEEDEP.ID", QueryObject.PARAM_DOUBLE, new Double(fromID));
        query.addParameter("toID", "BPC_FEEDEP.ID", QueryObject.PARAM_DOUBLE, new Double(toID));
        query.setResultClass(BPC_FEEDEP.class);
        query.setCursor(cursor);
        return query.getObjects();
    }

    public static BusObjectIterator<com.laiyifen.bpc.core.BPC_FEEDEP> getBpcFeedepObjects(double fromID, double toID)
    {
        String queryText = "select * from \"BPC_FEEDEP\" where \"ID\" between :fromID and :toID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("fromID", "BPC_FEEDEP.ID", QueryObject.PARAM_DOUBLE, new Double(fromID));
        query.addParameter("toID", "BPC_FEEDEP.ID", QueryObject.PARAM_DOUBLE, new Double(toID));
        query.setResultClass(BPC_FEEDEP.class);
        return query.getObjects();
    }


    public static BusObjectIterator<com.laiyifen.bpc.core.BPC_FEEDEP> getNextBpcFeedepObjects(double ID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"BPC_FEEDEP\" where (\"ID\" > :ID) order by \"ID\" asc";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("ID", "BPC_FEEDEP.ID", QueryObject.PARAM_DOUBLE, new Double(ID));//NOPMD
        query.setResultClass(BPC_FEEDEP.class);
        query.setCursor(cursor);
        return query.getObjects();
    }

    public static BusObjectIterator<com.laiyifen.bpc.core.BPC_FEEDEP> getPreviousBpcFeedepObjects(double ID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"BPC_FEEDEP\" where (\"ID\" < :ID) order by \"ID\" desc";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("ID", "BPC_FEEDEP.ID", QueryObject.PARAM_DOUBLE, new Double(ID));//NOPMD
        query.setResultClass(BPC_FEEDEP.class);
        query.setCursor(cursor);
        return query.getObjects();
    }

}

/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.common.core;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.busobject.QueryObject;
import com.cordys.cpc.bsf.classinfo.AttributeInfo;
import com.cordys.cpc.bsf.classinfo.ClassInfo;
import com.cordys.cpc.bsf.listeners.constraint.StringValidator;


public abstract class ATTACHMENTBase extends com.cordys.cpc.bsf.busobject.StateBusObject
{
    // tags used in the XML document
    public final static String ATTR_ID = "ID";
    public final static String ATTR_INSTANCE_ID = "INSTANCE_ID";
    public final static String ATTR_FORM_ID = "FORM_ID";
    public final static String ATTR_FILENAME = "FILENAME";
    public final static String ATTR_FILETYPE = "FILETYPE";
    public final static String ATTR_FILEURL = "FILEURL";
    public final static String ATTR_UPLOAD_DATE = "UPLOAD_DATE";
    public final static String ATTR_OPERATOR_CODE = "OPERATOR_CODE";
    public final static String ATTR_OPERATOR_NAME = "OPERATOR_NAME";
    public final static String ATTR_DEPT_CODE = "DEPT_CODE";
    public final static String ATTR_DEPTMENT_NAME = "DEPTMENT_NAME";
    public final static String ATTR_SUBMIT_STATUS = "SUBMIT_STATUS";
    public final static String ATTR_VERSION_NUM = "VERSION_NUM";
    public final static String ATTR_EXT1 = "EXT1";
    public final static String ATTR_EXT2 = "EXT2";
    public final static String ATTR_EXT3 = "EXT3";
    private static ClassInfo s_classInfo = null;
    public static ClassInfo _getClassInfo()//NOPMD framework ensures this is thread safe
    {
        if ( s_classInfo == null )//NOPMD
        {
            s_classInfo = newClassInfo(ATTACHMENT.class);
            s_classInfo.setTableName("ATTACHMENT");
            s_classInfo.setUIDElements(new String[]{ATTR_ID});
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ID);
                ai.setJavaName(ATTR_ID);
                ai.setColumnName(ATTR_ID);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_ID);
                v.setMaxLength(80);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_INSTANCE_ID);
                ai.setJavaName(ATTR_INSTANCE_ID);
                ai.setColumnName(ATTR_INSTANCE_ID);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_INSTANCE_ID);
                v.setMaxLength(100);
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
                AttributeInfo ai = new AttributeInfo(ATTR_FILENAME);
                ai.setJavaName(ATTR_FILENAME);
                ai.setColumnName(ATTR_FILENAME);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_FILENAME);
                v.setMaxLength(100);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_FILETYPE);
                ai.setJavaName(ATTR_FILETYPE);
                ai.setColumnName(ATTR_FILETYPE);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_FILETYPE);
                v.setMaxLength(20);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_FILEURL);
                ai.setJavaName(ATTR_FILEURL);
                ai.setColumnName(ATTR_FILEURL);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_FILEURL);
                v.setMaxLength(200);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_UPLOAD_DATE);
                ai.setJavaName(ATTR_UPLOAD_DATE);
                ai.setColumnName(ATTR_UPLOAD_DATE);
                ai.setAttributeClass(java.util.Date.class);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_OPERATOR_CODE);
                ai.setJavaName(ATTR_OPERATOR_CODE);
                ai.setColumnName(ATTR_OPERATOR_CODE);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_OPERATOR_CODE);
                v.setMaxLength(10);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_OPERATOR_NAME);
                ai.setJavaName(ATTR_OPERATOR_NAME);
                ai.setColumnName(ATTR_OPERATOR_NAME);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_OPERATOR_NAME);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_DEPT_CODE);
                ai.setJavaName(ATTR_DEPT_CODE);
                ai.setColumnName(ATTR_DEPT_CODE);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_DEPT_CODE);
                v.setMaxLength(10);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_DEPTMENT_NAME);
                ai.setJavaName(ATTR_DEPTMENT_NAME);
                ai.setColumnName(ATTR_DEPTMENT_NAME);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_DEPTMENT_NAME);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SUBMIT_STATUS);
                ai.setJavaName(ATTR_SUBMIT_STATUS);
                ai.setColumnName(ATTR_SUBMIT_STATUS);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SUBMIT_STATUS);
                v.setMaxLength(1);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_VERSION_NUM);
                ai.setJavaName(ATTR_VERSION_NUM);
                ai.setColumnName(ATTR_VERSION_NUM);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_VERSION_NUM);
                v.setMaxLength(4);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_EXT1);
                ai.setJavaName(ATTR_EXT1);
                ai.setColumnName(ATTR_EXT1);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_EXT1);
                v.setMaxLength(20);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_EXT2);
                ai.setJavaName(ATTR_EXT2);
                ai.setColumnName(ATTR_EXT2);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_EXT2);
                v.setMaxLength(20);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_EXT3);
                ai.setJavaName(ATTR_EXT3);
                ai.setColumnName(ATTR_EXT3);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_EXT3);
                v.setMaxLength(20);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
        }
        return s_classInfo;
    }

    public ATTACHMENTBase(BusObjectConfig config)
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

    public String getINSTANCE_ID()
    {
        return getStringProperty(ATTR_INSTANCE_ID);
    }

    public void setINSTANCE_ID(String value)
    {
        setProperty(ATTR_INSTANCE_ID, value, 0);
    }

    public String getFORM_ID()
    {
        return getStringProperty(ATTR_FORM_ID);
    }

    public void setFORM_ID(String value)
    {
        setProperty(ATTR_FORM_ID, value, 0);
    }

    public String getFILENAME()
    {
        return getStringProperty(ATTR_FILENAME);
    }

    public void setFILENAME(String value)
    {
        setProperty(ATTR_FILENAME, value, 0);
    }

    public String getFILETYPE()
    {
        return getStringProperty(ATTR_FILETYPE);
    }

    public void setFILETYPE(String value)
    {
        setProperty(ATTR_FILETYPE, value, 0);
    }

    public String getFILEURL()
    {
        return getStringProperty(ATTR_FILEURL);
    }

    public void setFILEURL(String value)
    {
        setProperty(ATTR_FILEURL, value, 0);
    }

    public java.util.Date getUPLOAD_DATE()
    {
        return getDateTimestampProperty(ATTR_UPLOAD_DATE);
    }

    public void setUPLOAD_DATE(java.util.Date value)
    {
        setProperty(ATTR_UPLOAD_DATE, value, 0);
    }

    public String getOPERATOR_CODE()
    {
        return getStringProperty(ATTR_OPERATOR_CODE);
    }

    public void setOPERATOR_CODE(String value)
    {
        setProperty(ATTR_OPERATOR_CODE, value, 0);
    }

    public String getOPERATOR_NAME()
    {
        return getStringProperty(ATTR_OPERATOR_NAME);
    }

    public void setOPERATOR_NAME(String value)
    {
        setProperty(ATTR_OPERATOR_NAME, value, 0);
    }

    public String getDEPT_CODE()
    {
        return getStringProperty(ATTR_DEPT_CODE);
    }

    public void setDEPT_CODE(String value)
    {
        setProperty(ATTR_DEPT_CODE, value, 0);
    }

    public String getDEPTMENT_NAME()
    {
        return getStringProperty(ATTR_DEPTMENT_NAME);
    }

    public void setDEPTMENT_NAME(String value)
    {
        setProperty(ATTR_DEPTMENT_NAME, value, 0);
    }

    public String getSUBMIT_STATUS()
    {
        return getStringProperty(ATTR_SUBMIT_STATUS);
    }

    public void setSUBMIT_STATUS(String value)
    {
        setProperty(ATTR_SUBMIT_STATUS, value, 0);
    }

    public String getVERSION_NUM()
    {
        return getStringProperty(ATTR_VERSION_NUM);
    }

    public void setVERSION_NUM(String value)
    {
        setProperty(ATTR_VERSION_NUM, value, 0);
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

    public String getEXT3()
    {
        return getStringProperty(ATTR_EXT3);
    }

    public void setEXT3(String value)
    {
        setProperty(ATTR_EXT3, value, 0);
    }




    public static com.laiyifen.common.core.ATTACHMENT getAttachmentObject(String ID)
    {
        String queryText = "select * from \"ATTACHMENT\" where \"ID\" = :ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("ID", "ATTACHMENT.ID", QueryObject.PARAM_STRING, ID);//NOPMD
        query.setResultClass(ATTACHMENT.class);
        return (ATTACHMENT)query.getObject();
    }

    public static BusObjectIterator<com.laiyifen.common.core.ATTACHMENT> getAttachmentObjects(String fromID, String toID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"ATTACHMENT\" where \"ID\" between :fromID and :toID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("fromID", "ATTACHMENT.ID", QueryObject.PARAM_STRING, fromID);
        query.addParameter("toID", "ATTACHMENT.ID", QueryObject.PARAM_STRING, toID);
        query.setResultClass(ATTACHMENT.class);
        query.setCursor(cursor);
        return query.getObjects();
    }

    public static BusObjectIterator<com.laiyifen.common.core.ATTACHMENT> getAttachmentObjects(String fromID, String toID)
    {
        String queryText = "select * from \"ATTACHMENT\" where \"ID\" between :fromID and :toID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("fromID", "ATTACHMENT.ID", QueryObject.PARAM_STRING, fromID);
        query.addParameter("toID", "ATTACHMENT.ID", QueryObject.PARAM_STRING, toID);
        query.setResultClass(ATTACHMENT.class);
        return query.getObjects();
    }

    public static BusObjectIterator<com.laiyifen.common.core.ATTACHMENT> getNextAttachmentObjects(String ID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"ATTACHMENT\" where (\"ID\" > :ID) order by \"ID\" asc";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("ID", "ATTACHMENT.ID", QueryObject.PARAM_STRING, ID);//NOPMD
        query.setResultClass(ATTACHMENT.class);
        query.setCursor(cursor);
        return query.getObjects();
    }

    public static BusObjectIterator<com.laiyifen.common.core.ATTACHMENT> getPreviousAttachmentObjects(String ID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"ATTACHMENT\" where (\"ID\" < :ID) order by \"ID\" desc";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("ID", "ATTACHMENT.ID", QueryObject.PARAM_STRING, ID);//NOPMD
        query.setResultClass(ATTACHMENT.class);
        query.setCursor(cursor);
        return query.getObjects();
    }


}

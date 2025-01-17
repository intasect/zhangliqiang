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


public abstract class GOODSORDERBase extends com.cordys.cpc.bsf.busobject.StateBusObject
{
    // tags used in the XML document
    public final static String ATTR_FORM_ID = "FORM_ID";
    public final static String ATTR_ORDER_ID = "ORDER_ID";
    public final static String ATTR_USER_NAME = "USER_NAME";
    public final static String ATTR_USER_CODE = "USER_CODE";
    public final static String ATTR_DEPARTMENT_CODE = "DEPARTMENT_CODE";
    public final static String ATTR_DEPARTMENT_NAME = "DEPARTMENT_NAME";
    public final static String ATTR_APPLICATION_DATE = "APPLICATION_DATE";
    public final static String ATTR_JIAOHUODAN_ID = "JIAOHUODAN_ID";
    public final static String ATTR_DINGGOUDANWEI = "DINGGOUDANWEI";
    public final static String ATTR_SONGHUODIZHI = "SONGHUODIZHI";
    public final static String ATTR_DINGGOUREN = "DINGGOUREN";
    public final static String ATTR_LIANXIFANGSHI = "LIANXIFANGSHI";
    public final static String ATTR_KEHUFENLEI = "KEHUFENLEI";
    public final static String ATTR_DINGGOURIQI = "DINGGOURIQI";
    public final static String ATTR_DANWEIDIZHI = "DANWEIDIZHI";
    public final static String ATTR_QUYU = "QUYU";
    public final static String ATTR_SONGHUORIQI = "SONGHUORIQI";
    public final static String ATTR_BAOZHUANGYAOQIU = "BAOZHUANGYAOQIU";
    public final static String ATTR_XIANZHEKOU = "XIANZHEKOU";
    public final static String ATTR_YUANZHEKOU = "YUANZHEKOU";
    public final static String ATTR_CAIWUSHUOMING = "CAIWUSHUOMING";
    public final static String ATTR_ZONGJINE = "ZONGJINE";
    public final static String ATTR_FUKUANFANGSHI = "FUKUANFANGSHI";
    public final static String ATTR_JIESUANFANGSHI = "JIESUANFANGSHI";
    public final static String ATTR_DINGDANJIESHAOREN = "DINGDANJIESHAOREN";
    public final static String ATTR_BUMEN = "BUMEN";
    public final static String ATTR_JIESHAOREN = "JIESHAOREN";
    public final static String ATTR_MENDIANBIANHAO = "MENDIANBIANHAO";
    public final static String ATTR_QUYUJINGLI = "QUYUJINGLI";
    public final static String ATTR_DAQUJINGLI = "DAQUJINGLI";
    public final static String ATTR_BEIZHU = "BEIZHU";
    public final static String ATTR_SAPORDER_ID = "SAPORDER_ID";
    private static ClassInfo s_classInfo = null;
    public static ClassInfo _getClassInfo()//NOPMD framework ensures this is thread safe
    {
        if ( s_classInfo == null )//NOPMD
        {
            s_classInfo = newClassInfo(GOODSORDER.class);
            s_classInfo.setTableName("GOODSORDER");
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
                AttributeInfo ai = new AttributeInfo(ATTR_JIAOHUODAN_ID);
                ai.setJavaName(ATTR_JIAOHUODAN_ID);
                ai.setColumnName(ATTR_JIAOHUODAN_ID);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_JIAOHUODAN_ID);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_DINGGOUDANWEI);
                ai.setJavaName(ATTR_DINGGOUDANWEI);
                ai.setColumnName(ATTR_DINGGOUDANWEI);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_DINGGOUDANWEI);
                v.setMaxLength(40);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SONGHUODIZHI);
                ai.setJavaName(ATTR_SONGHUODIZHI);
                ai.setColumnName(ATTR_SONGHUODIZHI);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SONGHUODIZHI);
                v.setMaxLength(40);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_DINGGOUREN);
                ai.setJavaName(ATTR_DINGGOUREN);
                ai.setColumnName(ATTR_DINGGOUREN);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_DINGGOUREN);
                v.setMaxLength(20);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_LIANXIFANGSHI);
                ai.setJavaName(ATTR_LIANXIFANGSHI);
                ai.setColumnName(ATTR_LIANXIFANGSHI);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_LIANXIFANGSHI);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_KEHUFENLEI);
                ai.setJavaName(ATTR_KEHUFENLEI);
                ai.setColumnName(ATTR_KEHUFENLEI);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_KEHUFENLEI);
                v.setMaxLength(10);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_DINGGOURIQI);
                ai.setJavaName(ATTR_DINGGOURIQI);
                ai.setColumnName(ATTR_DINGGOURIQI);
                ai.setAttributeClass(java.util.Date.class);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_DANWEIDIZHI);
                ai.setJavaName(ATTR_DANWEIDIZHI);
                ai.setColumnName(ATTR_DANWEIDIZHI);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_DANWEIDIZHI);
                v.setMaxLength(80);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_QUYU);
                ai.setJavaName(ATTR_QUYU);
                ai.setColumnName(ATTR_QUYU);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_QUYU);
                v.setMaxLength(20);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SONGHUORIQI);
                ai.setJavaName(ATTR_SONGHUORIQI);
                ai.setColumnName(ATTR_SONGHUORIQI);
                ai.setAttributeClass(java.util.Date.class);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_BAOZHUANGYAOQIU);
                ai.setJavaName(ATTR_BAOZHUANGYAOQIU);
                ai.setColumnName(ATTR_BAOZHUANGYAOQIU);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_BAOZHUANGYAOQIU);
                v.setMaxLength(10);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_XIANZHEKOU);
                ai.setJavaName(ATTR_XIANZHEKOU);
                ai.setColumnName(ATTR_XIANZHEKOU);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_XIANZHEKOU);
                v.setMaxLength(10);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_YUANZHEKOU);
                ai.setJavaName(ATTR_YUANZHEKOU);
                ai.setColumnName(ATTR_YUANZHEKOU);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_YUANZHEKOU);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_CAIWUSHUOMING);
                ai.setJavaName(ATTR_CAIWUSHUOMING);
                ai.setColumnName(ATTR_CAIWUSHUOMING);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_CAIWUSHUOMING);
                v.setMaxLength(10);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ZONGJINE);
                ai.setJavaName(ATTR_ZONGJINE);
                ai.setColumnName(ATTR_ZONGJINE);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_ZONGJINE);
                v.setMaxLength(40);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_FUKUANFANGSHI);
                ai.setJavaName(ATTR_FUKUANFANGSHI);
                ai.setColumnName(ATTR_FUKUANFANGSHI);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_FUKUANFANGSHI);
                v.setMaxLength(10);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_JIESUANFANGSHI);
                ai.setJavaName(ATTR_JIESUANFANGSHI);
                ai.setColumnName(ATTR_JIESUANFANGSHI);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_JIESUANFANGSHI);
                v.setMaxLength(10);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_DINGDANJIESHAOREN);
                ai.setJavaName(ATTR_DINGDANJIESHAOREN);
                ai.setColumnName(ATTR_DINGDANJIESHAOREN);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_DINGDANJIESHAOREN);
                v.setMaxLength(10);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_BUMEN);
                ai.setJavaName(ATTR_BUMEN);
                ai.setColumnName(ATTR_BUMEN);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_BUMEN);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_JIESHAOREN);
                ai.setJavaName(ATTR_JIESHAOREN);
                ai.setColumnName(ATTR_JIESHAOREN);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_JIESHAOREN);
                v.setMaxLength(100);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_MENDIANBIANHAO);
                ai.setJavaName(ATTR_MENDIANBIANHAO);
                ai.setColumnName(ATTR_MENDIANBIANHAO);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_MENDIANBIANHAO);
                v.setMaxLength(100);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_QUYUJINGLI);
                ai.setJavaName(ATTR_QUYUJINGLI);
                ai.setColumnName(ATTR_QUYUJINGLI);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_QUYUJINGLI);
                v.setMaxLength(10);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_DAQUJINGLI);
                ai.setJavaName(ATTR_DAQUJINGLI);
                ai.setColumnName(ATTR_DAQUJINGLI);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_DAQUJINGLI);
                v.setMaxLength(10);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_BEIZHU);
                ai.setJavaName(ATTR_BEIZHU);
                ai.setColumnName(ATTR_BEIZHU);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_BEIZHU);
                v.setMaxLength(1000);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SAPORDER_ID);
                ai.setJavaName(ATTR_SAPORDER_ID);
                ai.setColumnName(ATTR_SAPORDER_ID);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SAPORDER_ID);
                v.setMaxLength(20);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
        }
        return s_classInfo;
    }

    public GOODSORDERBase(BusObjectConfig config)
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

    public String getJIAOHUODAN_ID()
    {
        return getStringProperty(ATTR_JIAOHUODAN_ID);
    }

    public void setJIAOHUODAN_ID(String value)
    {
        setProperty(ATTR_JIAOHUODAN_ID, value, 0);
    }

    public String getDINGGOUDANWEI()
    {
        return getStringProperty(ATTR_DINGGOUDANWEI);
    }

    public void setDINGGOUDANWEI(String value)
    {
        setProperty(ATTR_DINGGOUDANWEI, value, 0);
    }

    public String getSONGHUODIZHI()
    {
        return getStringProperty(ATTR_SONGHUODIZHI);
    }

    public void setSONGHUODIZHI(String value)
    {
        setProperty(ATTR_SONGHUODIZHI, value, 0);
    }

    public String getDINGGOUREN()
    {
        return getStringProperty(ATTR_DINGGOUREN);
    }

    public void setDINGGOUREN(String value)
    {
        setProperty(ATTR_DINGGOUREN, value, 0);
    }

    public double getLIANXIFANGSHI()
    {
        return getDoubleProperty(ATTR_LIANXIFANGSHI);
    }

    public void setLIANXIFANGSHI(double value)
    {
        setProperty(ATTR_LIANXIFANGSHI, value, 0);
    }

    public String getKEHUFENLEI()
    {
        return getStringProperty(ATTR_KEHUFENLEI);
    }

    public void setKEHUFENLEI(String value)
    {
        setProperty(ATTR_KEHUFENLEI, value, 0);
    }

    public java.util.Date getDINGGOURIQI()
    {
        return getDateTimestampProperty(ATTR_DINGGOURIQI);
    }

    public void setDINGGOURIQI(java.util.Date value)
    {
        setProperty(ATTR_DINGGOURIQI, value, 0);
    }

    public String getDANWEIDIZHI()
    {
        return getStringProperty(ATTR_DANWEIDIZHI);
    }

    public void setDANWEIDIZHI(String value)
    {
        setProperty(ATTR_DANWEIDIZHI, value, 0);
    }

    public String getQUYU()
    {
        return getStringProperty(ATTR_QUYU);
    }

    public void setQUYU(String value)
    {
        setProperty(ATTR_QUYU, value, 0);
    }

    public java.util.Date getSONGHUORIQI()
    {
        return getDateTimestampProperty(ATTR_SONGHUORIQI);
    }

    public void setSONGHUORIQI(java.util.Date value)
    {
        setProperty(ATTR_SONGHUORIQI, value, 0);
    }

    public String getBAOZHUANGYAOQIU()
    {
        return getStringProperty(ATTR_BAOZHUANGYAOQIU);
    }

    public void setBAOZHUANGYAOQIU(String value)
    {
        setProperty(ATTR_BAOZHUANGYAOQIU, value, 0);
    }

    public String getXIANZHEKOU()
    {
        return getStringProperty(ATTR_XIANZHEKOU);
    }

    public void setXIANZHEKOU(String value)
    {
        setProperty(ATTR_XIANZHEKOU, value, 0);
    }

    public double getYUANZHEKOU()
    {
        return getDoubleProperty(ATTR_YUANZHEKOU);
    }

    public void setYUANZHEKOU(double value)
    {
        setProperty(ATTR_YUANZHEKOU, value, 0);
    }

    public String getCAIWUSHUOMING()
    {
        return getStringProperty(ATTR_CAIWUSHUOMING);
    }

    public void setCAIWUSHUOMING(String value)
    {
        setProperty(ATTR_CAIWUSHUOMING, value, 0);
    }

    public String getZONGJINE()
    {
        return getStringProperty(ATTR_ZONGJINE);
    }

    public void setZONGJINE(String value)
    {
        setProperty(ATTR_ZONGJINE, value, 0);
    }

    public String getFUKUANFANGSHI()
    {
        return getStringProperty(ATTR_FUKUANFANGSHI);
    }

    public void setFUKUANFANGSHI(String value)
    {
        setProperty(ATTR_FUKUANFANGSHI, value, 0);
    }

    public String getJIESUANFANGSHI()
    {
        return getStringProperty(ATTR_JIESUANFANGSHI);
    }

    public void setJIESUANFANGSHI(String value)
    {
        setProperty(ATTR_JIESUANFANGSHI, value, 0);
    }

    public String getDINGDANJIESHAOREN()
    {
        return getStringProperty(ATTR_DINGDANJIESHAOREN);
    }

    public void setDINGDANJIESHAOREN(String value)
    {
        setProperty(ATTR_DINGDANJIESHAOREN, value, 0);
    }

    public String getBUMEN()
    {
        return getStringProperty(ATTR_BUMEN);
    }

    public void setBUMEN(String value)
    {
        setProperty(ATTR_BUMEN, value, 0);
    }

    public String getJIESHAOREN()
    {
        return getStringProperty(ATTR_JIESHAOREN);
    }

    public void setJIESHAOREN(String value)
    {
        setProperty(ATTR_JIESHAOREN, value, 0);
    }

    public String getMENDIANBIANHAO()
    {
        return getStringProperty(ATTR_MENDIANBIANHAO);
    }

    public void setMENDIANBIANHAO(String value)
    {
        setProperty(ATTR_MENDIANBIANHAO, value, 0);
    }

    public String getQUYUJINGLI()
    {
        return getStringProperty(ATTR_QUYUJINGLI);
    }

    public void setQUYUJINGLI(String value)
    {
        setProperty(ATTR_QUYUJINGLI, value, 0);
    }

    public String getDAQUJINGLI()
    {
        return getStringProperty(ATTR_DAQUJINGLI);
    }

    public void setDAQUJINGLI(String value)
    {
        setProperty(ATTR_DAQUJINGLI, value, 0);
    }

    public String getBEIZHU()
    {
        return getStringProperty(ATTR_BEIZHU);
    }

    public void setBEIZHU(String value)
    {
        setProperty(ATTR_BEIZHU, value, 0);
    }

    public String getSAPORDER_ID()
    {
        return getStringProperty(ATTR_SAPORDER_ID);
    }

    public void setSAPORDER_ID(String value)
    {
        setProperty(ATTR_SAPORDER_ID, value, 0);
    }

    public static com.laiyifen.goods.core.GOODSORDER getGoodsorderObject(String FORM_ID)
    {
        String queryText = "select * from \"GOODSORDER\" where \"FORM_ID\" = :FORM_ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("FORM_ID", "GOODSORDER.FORM_ID", QueryObject.PARAM_STRING, FORM_ID);//NOPMD
        query.setResultClass(GOODSORDER.class);
        return (GOODSORDER)query.getObject();
    }

    public static BusObjectIterator<com.laiyifen.goods.core.GOODSORDER> getGoodsorderObjects(String fromFORM_ID, String toFORM_ID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"GOODSORDER\" where \"FORM_ID\" between :fromFORM_ID and :toFORM_ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("fromFORM_ID", "GOODSORDER.FORM_ID", QueryObject.PARAM_STRING, fromFORM_ID);
        query.addParameter("toFORM_ID", "GOODSORDER.FORM_ID", QueryObject.PARAM_STRING, toFORM_ID);
        query.setResultClass(GOODSORDER.class);
        query.setCursor(cursor);
        return query.getObjects();
    }

    public static BusObjectIterator<com.laiyifen.goods.core.GOODSORDER> getGoodsorderObjects(String fromFORM_ID, String toFORM_ID)
    {
        String queryText = "select * from \"GOODSORDER\" where \"FORM_ID\" between :fromFORM_ID and :toFORM_ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("fromFORM_ID", "GOODSORDER.FORM_ID", QueryObject.PARAM_STRING, fromFORM_ID);
        query.addParameter("toFORM_ID", "GOODSORDER.FORM_ID", QueryObject.PARAM_STRING, toFORM_ID);
        query.setResultClass(GOODSORDER.class);
        return query.getObjects();
    }

    public static BusObjectIterator<com.laiyifen.goods.core.GOODSORDER> getNextGoodsorderObjects(String FORM_ID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"GOODSORDER\" where (\"FORM_ID\" > :FORM_ID) order by \"FORM_ID\" asc";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("FORM_ID", "GOODSORDER.FORM_ID", QueryObject.PARAM_STRING, FORM_ID);//NOPMD
        query.setResultClass(GOODSORDER.class);
        query.setCursor(cursor);
        return query.getObjects();
    }

    public static BusObjectIterator<com.laiyifen.goods.core.GOODSORDER> getPreviousGoodsorderObjects(String FORM_ID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"GOODSORDER\" where (\"FORM_ID\" < :FORM_ID) order by \"FORM_ID\" desc";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("FORM_ID", "GOODSORDER.FORM_ID", QueryObject.PARAM_STRING, FORM_ID);//NOPMD
        query.setResultClass(GOODSORDER.class);
        query.setCursor(cursor);
        return query.getObjects();
    }

}

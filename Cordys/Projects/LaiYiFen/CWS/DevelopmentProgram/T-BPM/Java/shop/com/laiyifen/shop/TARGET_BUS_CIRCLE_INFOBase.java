/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.shop;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.busobject.QueryObject;
import com.cordys.cpc.bsf.classinfo.AttributeInfo;
import com.cordys.cpc.bsf.classinfo.ClassInfo;
import com.cordys.cpc.bsf.classinfo.RelationInfo_FK;
import com.cordys.cpc.bsf.listeners.constraint.NumberValidator;
import com.cordys.cpc.bsf.listeners.constraint.StringValidator;
 

public abstract class TARGET_BUS_CIRCLE_INFOBase extends com.cordys.cpc.bsf.busobject.StateBusObject
{
    // tags used in the XML document
    public final static String ATTR_ID = "ID";
    public final static String ATTR_FORM_ID = "FORM_ID";
    public final static String ATTR_MOR_START_TIME = "MOR_START_TIME";
    public final static String ATTR_MOR_END_TIME = "MOR_END_TIME";
    public final static String ATTR_MOR_PERSON_AMOUNT = "MOR_PERSON_AMOUNT";
    public final static String ATTR_MOR_WEATHER = "MOR_WEATHER";
    public final static String ATTR_MID_START_TIME = "MID_START_TIME";
    public final static String ATTR_MIDDAY_END_TIME = "MIDDAY_END_TIME";
    public final static String ATTR_MID_PERSON_AMOUNT = "MID_PERSON_AMOUNT";
    public final static String ATTR_MID_WEATHER = "MID_WEATHER";
    public final static String ATTR_NIGHTS_START_TIME = "NIGHTS_START_TIME";
    public final static String ATTR_NIGHTS_END_TIME = "NIGHTS_END_TIME";
    public final static String ATTR_NIGHTS_PERSON_AMOUNT = "NIGHTS_PERSON_AMOUNT";
    public final static String ATTR_NIGHTS_WEATHER = "NIGHTS_WEATHER";
    public final static String ATTR_ISNEAR_UPTOWN = "ISNEAR_UPTOWN";
    public final static String ATTR_ISUPTOWN_CENTER = "ISUPTOWN_CENTER";
    public final static String ATTR_ISMATURE_AREA = "ISMATURE_AREA";
    public final static String ATTR_TOTAL_APARTMENTS = "TOTAL_APARTMENTS";
    public final static String ATTR_LIVING_RATE = "LIVING_RATE";
    public final static String ATTR_LIVING_QUALITY = "LIVING_QUALITY";
    public final static String ATTR_ISMALL = "ISMALL";
    public final static String ATTR_ISCOMPETITOR = "ISCOMPETITOR";
    public final static String ATTR_COMPETITOR_STRENGTH = "COMPETITOR_STRENGTH";
    public final static String ATTR_DETAILS = "DETAILS";
    public final static String ATTR_ONELEFTBUSINESS_ITEM = "ONELEFTBUSINESS_ITEM";
    public final static String ATTR_ONELEFT_AREA = "ONELEFT_AREA";
    public final static String ATTR_ONELEFT_RENT = "ONELEFT_RENT";
    public final static String ATTR_ONELEFT_TURNOVER = "ONELEFT_TURNOVER";
    public final static String ATTR_TWOLEFT_MAN_PROJECT = "TWOLEFT_MAN_PROJECT";
    public final static String ATTR_TWOLEFT_AREA = "TWOLEFT_AREA";
    public final static String ATTR_TWOLEFT_RENT = "TWOLEFT_RENT";
    public final static String ATTR_TWOLEFT_TURNOVER = "TWOLEFT_TURNOVER";
    public final static String ATTR_ONERIGHT_MAN_PROJECT = "ONERIGHT_MAN_PROJECT";
    public final static String ATTR_ONERIGHT_AREA = "ONERIGHT_AREA";
    public final static String ATTR_ONERIGHT_RENT = "ONERIGHT_RENT";
    public final static String ATTR_ONERIGHT_TURNOVER = "ONERIGHT_TURNOVER";
    public final static String ATTR_TWORIGHT_MAN_PROJECT = "TWORIGHT_MAN_PROJECT";
    public final static String ATTR_TWORIGHT_AREA = "TWORIGHT_AREA";
    public final static String ATTR_TWORIGHT_RENT = "TWORIGHT_RENT";
    public final static String ATTR_TWORIGHT_TURNOVER = "TWORIGHT_TURNOVER";
    public final static String ATTR_SUGGESTION = "SUGGESTION";
    private final static String REL_FORM_IDObject = "FK:TARGET_BUS_CIRCLE_INFO[FORM_ID]:TARGET_SHOP_INFO[FORM_ID]";
    private static ClassInfo s_classInfo = null;
    public static ClassInfo _getClassInfo()//NOPMD framework ensures this is thread safe
    {
        if ( s_classInfo == null )//NOPMD
        {
            s_classInfo = newClassInfo(TARGET_BUS_CIRCLE_INFO.class);
            s_classInfo.setTableName("TARGET_BUS_CIRCLE_INFO");
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
                AttributeInfo ai = new AttributeInfo(ATTR_MOR_START_TIME);
                ai.setJavaName(ATTR_MOR_START_TIME);
                ai.setColumnName(ATTR_MOR_START_TIME);
                ai.setAttributeClass(java.util.Date.class);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_MOR_END_TIME);
                ai.setJavaName(ATTR_MOR_END_TIME);
                ai.setColumnName(ATTR_MOR_END_TIME);
                ai.setAttributeClass(java.util.Date.class);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_MOR_PERSON_AMOUNT);
                ai.setJavaName(ATTR_MOR_PERSON_AMOUNT);
                ai.setColumnName(ATTR_MOR_PERSON_AMOUNT);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_MOR_PERSON_AMOUNT);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_MOR_WEATHER);
                ai.setJavaName(ATTR_MOR_WEATHER);
                ai.setColumnName(ATTR_MOR_WEATHER);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_MOR_WEATHER);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_MID_START_TIME);
                ai.setJavaName(ATTR_MID_START_TIME);
                ai.setColumnName(ATTR_MID_START_TIME);
                ai.setAttributeClass(java.util.Date.class);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_MIDDAY_END_TIME);
                ai.setJavaName(ATTR_MIDDAY_END_TIME);
                ai.setColumnName(ATTR_MIDDAY_END_TIME);
                ai.setAttributeClass(java.util.Date.class);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_MID_PERSON_AMOUNT);
                ai.setJavaName(ATTR_MID_PERSON_AMOUNT);
                ai.setColumnName(ATTR_MID_PERSON_AMOUNT);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_MID_PERSON_AMOUNT);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_MID_WEATHER);
                ai.setJavaName(ATTR_MID_WEATHER);
                ai.setColumnName(ATTR_MID_WEATHER);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_MID_WEATHER);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_NIGHTS_START_TIME);
                ai.setJavaName(ATTR_NIGHTS_START_TIME);
                ai.setColumnName(ATTR_NIGHTS_START_TIME);
                ai.setAttributeClass(java.util.Date.class);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_NIGHTS_END_TIME);
                ai.setJavaName(ATTR_NIGHTS_END_TIME);
                ai.setColumnName(ATTR_NIGHTS_END_TIME);
                ai.setAttributeClass(java.util.Date.class);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_NIGHTS_PERSON_AMOUNT);
                ai.setJavaName(ATTR_NIGHTS_PERSON_AMOUNT);
                ai.setColumnName(ATTR_NIGHTS_PERSON_AMOUNT);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_NIGHTS_PERSON_AMOUNT);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_NIGHTS_WEATHER);
                ai.setJavaName(ATTR_NIGHTS_WEATHER);
                ai.setColumnName(ATTR_NIGHTS_WEATHER);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_NIGHTS_WEATHER);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ISNEAR_UPTOWN);
                ai.setJavaName(ATTR_ISNEAR_UPTOWN);
                ai.setColumnName(ATTR_ISNEAR_UPTOWN);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_ISNEAR_UPTOWN);
                v.setMaxLength(2);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ISUPTOWN_CENTER);
                ai.setJavaName(ATTR_ISUPTOWN_CENTER);
                ai.setColumnName(ATTR_ISUPTOWN_CENTER);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_ISUPTOWN_CENTER);
                v.setMaxLength(2);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ISMATURE_AREA);
                ai.setJavaName(ATTR_ISMATURE_AREA);
                ai.setColumnName(ATTR_ISMATURE_AREA);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_ISMATURE_AREA);
                v.setMaxLength(2);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_TOTAL_APARTMENTS);
                ai.setJavaName(ATTR_TOTAL_APARTMENTS);
                ai.setColumnName(ATTR_TOTAL_APARTMENTS);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_TOTAL_APARTMENTS);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_LIVING_RATE);
                ai.setJavaName(ATTR_LIVING_RATE);
                ai.setColumnName(ATTR_LIVING_RATE);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_LIVING_RATE);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_LIVING_QUALITY);
                ai.setJavaName(ATTR_LIVING_QUALITY);
                ai.setColumnName(ATTR_LIVING_QUALITY);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_LIVING_QUALITY);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ISMALL);
                ai.setJavaName(ATTR_ISMALL);
                ai.setColumnName(ATTR_ISMALL);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_ISMALL);
                v.setMaxLength(2);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ISCOMPETITOR);
                ai.setJavaName(ATTR_ISCOMPETITOR);
                ai.setColumnName(ATTR_ISCOMPETITOR);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_ISCOMPETITOR);
                v.setMaxLength(2);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_COMPETITOR_STRENGTH);
                ai.setJavaName(ATTR_COMPETITOR_STRENGTH);
                ai.setColumnName(ATTR_COMPETITOR_STRENGTH);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_COMPETITOR_STRENGTH);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_DETAILS);
                ai.setJavaName(ATTR_DETAILS);
                ai.setColumnName(ATTR_DETAILS);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_DETAILS);
                v.setMaxLength(200);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ONELEFTBUSINESS_ITEM);
                ai.setJavaName(ATTR_ONELEFTBUSINESS_ITEM);
                ai.setColumnName(ATTR_ONELEFTBUSINESS_ITEM);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_ONELEFTBUSINESS_ITEM);
                v.setMaxLength(100);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ONELEFT_AREA);
                ai.setJavaName(ATTR_ONELEFT_AREA);
                ai.setColumnName(ATTR_ONELEFT_AREA);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_ONELEFT_AREA);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ONELEFT_RENT);
                ai.setJavaName(ATTR_ONELEFT_RENT);
                ai.setColumnName(ATTR_ONELEFT_RENT);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_ONELEFT_RENT);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ONELEFT_TURNOVER);
                ai.setJavaName(ATTR_ONELEFT_TURNOVER);
                ai.setColumnName(ATTR_ONELEFT_TURNOVER);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_ONELEFT_TURNOVER);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_TWOLEFT_MAN_PROJECT);
                ai.setJavaName(ATTR_TWOLEFT_MAN_PROJECT);
                ai.setColumnName(ATTR_TWOLEFT_MAN_PROJECT);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_TWOLEFT_MAN_PROJECT);
                v.setMaxLength(100);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_TWOLEFT_AREA);
                ai.setJavaName(ATTR_TWOLEFT_AREA);
                ai.setColumnName(ATTR_TWOLEFT_AREA);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_TWOLEFT_AREA);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_TWOLEFT_RENT);
                ai.setJavaName(ATTR_TWOLEFT_RENT);
                ai.setColumnName(ATTR_TWOLEFT_RENT);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_TWOLEFT_RENT);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_TWOLEFT_TURNOVER);
                ai.setJavaName(ATTR_TWOLEFT_TURNOVER);
                ai.setColumnName(ATTR_TWOLEFT_TURNOVER);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_TWOLEFT_TURNOVER);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ONERIGHT_MAN_PROJECT);
                ai.setJavaName(ATTR_ONERIGHT_MAN_PROJECT);
                ai.setColumnName(ATTR_ONERIGHT_MAN_PROJECT);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_ONERIGHT_MAN_PROJECT);
                v.setMaxLength(100);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ONERIGHT_AREA);
                ai.setJavaName(ATTR_ONERIGHT_AREA);
                ai.setColumnName(ATTR_ONERIGHT_AREA);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_ONERIGHT_AREA);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ONERIGHT_RENT);
                ai.setJavaName(ATTR_ONERIGHT_RENT);
                ai.setColumnName(ATTR_ONERIGHT_RENT);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_ONERIGHT_RENT);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ONERIGHT_TURNOVER);
                ai.setJavaName(ATTR_ONERIGHT_TURNOVER);
                ai.setColumnName(ATTR_ONERIGHT_TURNOVER);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_ONERIGHT_TURNOVER);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_TWORIGHT_MAN_PROJECT);
                ai.setJavaName(ATTR_TWORIGHT_MAN_PROJECT);
                ai.setColumnName(ATTR_TWORIGHT_MAN_PROJECT);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_TWORIGHT_MAN_PROJECT);
                v.setMaxLength(100);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_TWORIGHT_AREA);
                ai.setJavaName(ATTR_TWORIGHT_AREA);
                ai.setColumnName(ATTR_TWORIGHT_AREA);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_TWORIGHT_AREA);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_TWORIGHT_RENT);
                ai.setJavaName(ATTR_TWORIGHT_RENT);
                ai.setColumnName(ATTR_TWORIGHT_RENT);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_TWORIGHT_RENT);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_TWORIGHT_TURNOVER);
                ai.setJavaName(ATTR_TWORIGHT_TURNOVER);
                ai.setColumnName(ATTR_TWORIGHT_TURNOVER);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_TWORIGHT_TURNOVER);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SUGGESTION);
                ai.setJavaName(ATTR_SUGGESTION);
                ai.setColumnName(ATTR_SUGGESTION);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SUGGESTION);
                v.setMaxLength(2000);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                // relation FORM_IDObject (FK:TARGET_BUS_CIRCLE_INFO[FORM_ID]:TARGET_SHOP_INFO[FORM_ID])
                RelationInfo_FK ri = new RelationInfo_FK(REL_FORM_IDObject);
                ri.setName("FORM_IDObject");
                ri.setLocalAttributes(new String[]{ATTR_FORM_ID});
                ri.setLocalIsPK(false);
                ri.setRelatedClass(com.laiyifen.shop.TARGET_SHOP_INFO.class);
                ri.setRelatedAttributes(new String[]{"FORM_ID"});//NOPMD
                ri.setRelatedIdentifier("FK:TARGET_SHOP_INFO[FORM_ID]:TARGET_BUS_CIRCLE_INFO[FORM_ID]");
                ri.setLoadMethod("loadFORM_IDObject");
                s_classInfo.addRelationInfo(ri);
            }
        }
        return s_classInfo;
    }

    public TARGET_BUS_CIRCLE_INFOBase(BusObjectConfig config)
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

    public String getFORM_ID()
    {
        return getStringProperty(ATTR_FORM_ID);
    }

    public void setFORM_ID(String value)
    {
        String[] relations = new String[]{REL_FORM_IDObject};
        this.updateSingleRelations(relations, false);
        setProperty(ATTR_FORM_ID, value, 0);
        this.updateSingleRelations(relations, true);
    }

    public java.util.Date getMOR_START_TIME()
    {
        return getDateTimestampProperty(ATTR_MOR_START_TIME);
    }

    public void setMOR_START_TIME(java.util.Date value)
    {
        setProperty(ATTR_MOR_START_TIME, value, 0);
    }

    public java.util.Date getMOR_END_TIME()
    {
        return getDateTimestampProperty(ATTR_MOR_END_TIME);
    }

    public void setMOR_END_TIME(java.util.Date value)
    {
        setProperty(ATTR_MOR_END_TIME, value, 0);
    }

    public double getMOR_PERSON_AMOUNT()
    {
        return getDoubleProperty(ATTR_MOR_PERSON_AMOUNT);
    }

    public void setMOR_PERSON_AMOUNT(double value)
    {
        setProperty(ATTR_MOR_PERSON_AMOUNT, value, 0);
    }

    public String getMOR_WEATHER()
    {
        return getStringProperty(ATTR_MOR_WEATHER);
    }

    public void setMOR_WEATHER(String value)
    {
        setProperty(ATTR_MOR_WEATHER, value, 0);
    }

    public java.util.Date getMID_START_TIME()
    {
        return getDateTimestampProperty(ATTR_MID_START_TIME);
    }

    public void setMID_START_TIME(java.util.Date value)
    {
        setProperty(ATTR_MID_START_TIME, value, 0);
    }

    public java.util.Date getMIDDAY_END_TIME()
    {
        return getDateTimestampProperty(ATTR_MIDDAY_END_TIME);
    }

    public void setMIDDAY_END_TIME(java.util.Date value)
    {
        setProperty(ATTR_MIDDAY_END_TIME, value, 0);
    }

    public double getMID_PERSON_AMOUNT()
    {
        return getDoubleProperty(ATTR_MID_PERSON_AMOUNT);
    }

    public void setMID_PERSON_AMOUNT(double value)
    {
        setProperty(ATTR_MID_PERSON_AMOUNT, value, 0);
    }

    public String getMID_WEATHER()
    {
        return getStringProperty(ATTR_MID_WEATHER);
    }

    public void setMID_WEATHER(String value)
    {
        setProperty(ATTR_MID_WEATHER, value, 0);
    }

    public java.util.Date getNIGHTS_START_TIME()
    {
        return getDateTimestampProperty(ATTR_NIGHTS_START_TIME);
    }

    public void setNIGHTS_START_TIME(java.util.Date value)
    {
        setProperty(ATTR_NIGHTS_START_TIME, value, 0);
    }

    public java.util.Date getNIGHTS_END_TIME()
    {
        return getDateTimestampProperty(ATTR_NIGHTS_END_TIME);
    }

    public void setNIGHTS_END_TIME(java.util.Date value)
    {
        setProperty(ATTR_NIGHTS_END_TIME, value, 0);
    }

    public double getNIGHTS_PERSON_AMOUNT()
    {
        return getDoubleProperty(ATTR_NIGHTS_PERSON_AMOUNT);
    }

    public void setNIGHTS_PERSON_AMOUNT(double value)
    {
        setProperty(ATTR_NIGHTS_PERSON_AMOUNT, value, 0);
    }

    public String getNIGHTS_WEATHER()
    {
        return getStringProperty(ATTR_NIGHTS_WEATHER);
    }

    public void setNIGHTS_WEATHER(String value)
    {
        setProperty(ATTR_NIGHTS_WEATHER, value, 0);
    }

    public String getISNEAR_UPTOWN()
    {
        return getStringProperty(ATTR_ISNEAR_UPTOWN);
    }

    public void setISNEAR_UPTOWN(String value)
    {
        setProperty(ATTR_ISNEAR_UPTOWN, value, 0);
    }

    public String getISUPTOWN_CENTER()
    {
        return getStringProperty(ATTR_ISUPTOWN_CENTER);
    }

    public void setISUPTOWN_CENTER(String value)
    {
        setProperty(ATTR_ISUPTOWN_CENTER, value, 0);
    }

    public String getISMATURE_AREA()
    {
        return getStringProperty(ATTR_ISMATURE_AREA);
    }

    public void setISMATURE_AREA(String value)
    {
        setProperty(ATTR_ISMATURE_AREA, value, 0);
    }

    public double getTOTAL_APARTMENTS()
    {
        return getDoubleProperty(ATTR_TOTAL_APARTMENTS);
    }

    public void setTOTAL_APARTMENTS(double value)
    {
        setProperty(ATTR_TOTAL_APARTMENTS, value, 0);
    }

    public double getLIVING_RATE()
    {
        return getDoubleProperty(ATTR_LIVING_RATE);
    }

    public void setLIVING_RATE(double value)
    {
        setProperty(ATTR_LIVING_RATE, value, 0);
    }

    public String getLIVING_QUALITY()
    {
        return getStringProperty(ATTR_LIVING_QUALITY);
    }

    public void setLIVING_QUALITY(String value)
    {
        setProperty(ATTR_LIVING_QUALITY, value, 0);
    }

    public String getISMALL()
    {
        return getStringProperty(ATTR_ISMALL);
    }

    public void setISMALL(String value)
    {
        setProperty(ATTR_ISMALL, value, 0);
    }

    public String getISCOMPETITOR()
    {
        return getStringProperty(ATTR_ISCOMPETITOR);
    }

    public void setISCOMPETITOR(String value)
    {
        setProperty(ATTR_ISCOMPETITOR, value, 0);
    }

    public String getCOMPETITOR_STRENGTH()
    {
        return getStringProperty(ATTR_COMPETITOR_STRENGTH);
    }

    public void setCOMPETITOR_STRENGTH(String value)
    {
        setProperty(ATTR_COMPETITOR_STRENGTH, value, 0);
    }

    public String getDETAILS()
    {
        return getStringProperty(ATTR_DETAILS);
    }

    public void setDETAILS(String value)
    {
        setProperty(ATTR_DETAILS, value, 0);
    }

    public String getONELEFTBUSINESS_ITEM()
    {
        return getStringProperty(ATTR_ONELEFTBUSINESS_ITEM);
    }

    public void setONELEFTBUSINESS_ITEM(String value)
    {
        setProperty(ATTR_ONELEFTBUSINESS_ITEM, value, 0);
    }

    public double getONELEFT_AREA()
    {
        return getDoubleProperty(ATTR_ONELEFT_AREA);
    }

    public void setONELEFT_AREA(double value)
    {
        setProperty(ATTR_ONELEFT_AREA, value, 0);
    }

    public double getONELEFT_RENT()
    {
        return getDoubleProperty(ATTR_ONELEFT_RENT);
    }

    public void setONELEFT_RENT(double value)
    {
        setProperty(ATTR_ONELEFT_RENT, value, 0);
    }

    public double getONELEFT_TURNOVER()
    {
        return getDoubleProperty(ATTR_ONELEFT_TURNOVER);
    }

    public void setONELEFT_TURNOVER(double value)
    {
        setProperty(ATTR_ONELEFT_TURNOVER, value, 0);
    }

    public String getTWOLEFT_MAN_PROJECT()
    {
        return getStringProperty(ATTR_TWOLEFT_MAN_PROJECT);
    }

    public void setTWOLEFT_MAN_PROJECT(String value)
    {
        setProperty(ATTR_TWOLEFT_MAN_PROJECT, value, 0);
    }

    public double getTWOLEFT_AREA()
    {
        return getDoubleProperty(ATTR_TWOLEFT_AREA);
    }

    public void setTWOLEFT_AREA(double value)
    {
        setProperty(ATTR_TWOLEFT_AREA, value, 0);
    }

    public double getTWOLEFT_RENT()
    {
        return getDoubleProperty(ATTR_TWOLEFT_RENT);
    }

    public void setTWOLEFT_RENT(double value)
    {
        setProperty(ATTR_TWOLEFT_RENT, value, 0);
    }

    public double getTWOLEFT_TURNOVER()
    {
        return getDoubleProperty(ATTR_TWOLEFT_TURNOVER);
    }

    public void setTWOLEFT_TURNOVER(double value)
    {
        setProperty(ATTR_TWOLEFT_TURNOVER, value, 0);
    }

    public String getONERIGHT_MAN_PROJECT()
    {
        return getStringProperty(ATTR_ONERIGHT_MAN_PROJECT);
    }

    public void setONERIGHT_MAN_PROJECT(String value)
    {
        setProperty(ATTR_ONERIGHT_MAN_PROJECT, value, 0);
    }

    public double getONERIGHT_AREA()
    {
        return getDoubleProperty(ATTR_ONERIGHT_AREA);
    }

    public void setONERIGHT_AREA(double value)
    {
        setProperty(ATTR_ONERIGHT_AREA, value, 0);
    }

    public double getONERIGHT_RENT()
    {
        return getDoubleProperty(ATTR_ONERIGHT_RENT);
    }

    public void setONERIGHT_RENT(double value)
    {
        setProperty(ATTR_ONERIGHT_RENT, value, 0);
    }

    public double getONERIGHT_TURNOVER()
    {
        return getDoubleProperty(ATTR_ONERIGHT_TURNOVER);
    }

    public void setONERIGHT_TURNOVER(double value)
    {
        setProperty(ATTR_ONERIGHT_TURNOVER, value, 0);
    }

    public String getTWORIGHT_MAN_PROJECT()
    {
        return getStringProperty(ATTR_TWORIGHT_MAN_PROJECT);
    }

    public void setTWORIGHT_MAN_PROJECT(String value)
    {
        setProperty(ATTR_TWORIGHT_MAN_PROJECT, value, 0);
    }

    public double getTWORIGHT_AREA()
    {
        return getDoubleProperty(ATTR_TWORIGHT_AREA);
    }

    public void setTWORIGHT_AREA(double value)
    {
        setProperty(ATTR_TWORIGHT_AREA, value, 0);
    }

    public double getTWORIGHT_RENT()
    {
        return getDoubleProperty(ATTR_TWORIGHT_RENT);
    }

    public void setTWORIGHT_RENT(double value)
    {
        setProperty(ATTR_TWORIGHT_RENT, value, 0);
    }

    public double getTWORIGHT_TURNOVER()
    {
        return getDoubleProperty(ATTR_TWORIGHT_TURNOVER);
    }

    public void setTWORIGHT_TURNOVER(double value)
    {
        setProperty(ATTR_TWORIGHT_TURNOVER, value, 0);
    }

    public String getSUGGESTION()
    {
        return getStringProperty(ATTR_SUGGESTION);
    }

    public void setSUGGESTION(String value)
    {
        setProperty(ATTR_SUGGESTION, value, 0);
    }

    public TARGET_SHOP_INFO getFORM_IDObject()
    {
        return (TARGET_SHOP_INFO)getSingleRelationObject(REL_FORM_IDObject);
    }
    public TARGET_SHOP_INFO loadFORM_IDObject()
    {
        String queryText = "select * from \"TARGET_SHOP_INFO\" where \"FORM_ID\" = :FORM_ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("FORM_ID", "TARGET_SHOP_INFO.FORM_ID", QueryObject.PARAM_STRING, getFORM_ID());//NOPMD
        query.setResultClass(TARGET_SHOP_INFO.class);
        return (TARGET_SHOP_INFO)query.getObject();
    }


    public void setFORM_IDObject(TARGET_SHOP_INFO a_TARGET_SHOP_INFO)
    {
        if (a_TARGET_SHOP_INFO == null)
        {
            this.setNull("FORM_ID");
        }
        else
        {
            this.setFORM_ID(a_TARGET_SHOP_INFO.getFORM_ID());
        }
    }

    public void setNull(String element)
    {
        super.setNull(element);
        if (ATTR_FORM_ID.equals(element))
        {
            this.updateSingleRelation(REL_FORM_IDObject, false);
        }
    }
    public static BusObjectIterator<com.laiyifen.shop.TARGET_BUS_CIRCLE_INFO> getNextTargetBusCircleInfoObjects(String ID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"TARGET_BUS_CIRCLE_INFO\" where (\"ID\" > :ID) order by \"ID\" asc";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("ID", "TARGET_BUS_CIRCLE_INFO.ID", QueryObject.PARAM_STRING, ID);//NOPMD
        query.setResultClass(TARGET_BUS_CIRCLE_INFO.class);
        query.setCursor(cursor);
        return query.getObjects();
    }

    public static BusObjectIterator<com.laiyifen.shop.TARGET_BUS_CIRCLE_INFO> getPreviousTargetBusCircleInfoObjects(String ID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"TARGET_BUS_CIRCLE_INFO\" where (\"ID\" < :ID) order by \"ID\" desc";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("ID", "TARGET_BUS_CIRCLE_INFO.ID", QueryObject.PARAM_STRING, ID);//NOPMD
        query.setResultClass(TARGET_BUS_CIRCLE_INFO.class);
        query.setCursor(cursor);
        return query.getObjects();
    }

    public static com.laiyifen.shop.TARGET_BUS_CIRCLE_INFO getTargetBusCircleInfoObject(String ID)
    {
        String queryText = "select * from \"TARGET_BUS_CIRCLE_INFO\" where \"ID\" = :ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("ID", "TARGET_BUS_CIRCLE_INFO.ID", QueryObject.PARAM_STRING, ID);//NOPMD
        query.setResultClass(TARGET_BUS_CIRCLE_INFO.class);
        return (TARGET_BUS_CIRCLE_INFO)query.getObject();
    }

    public static BusObjectIterator<com.laiyifen.shop.TARGET_BUS_CIRCLE_INFO> getTargetBusCircleInfoObjects(String fromID, String toID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"TARGET_BUS_CIRCLE_INFO\" where \"ID\" between :fromID and :toID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("fromID", "TARGET_BUS_CIRCLE_INFO.ID", QueryObject.PARAM_STRING, fromID);
        query.addParameter("toID", "TARGET_BUS_CIRCLE_INFO.ID", QueryObject.PARAM_STRING, toID);
        query.setResultClass(TARGET_BUS_CIRCLE_INFO.class);
        query.setCursor(cursor);
        return query.getObjects();
    }

    public static BusObjectIterator<com.laiyifen.shop.TARGET_BUS_CIRCLE_INFO> getTargetBusCircleInfoObjects(String fromID, String toID)
    {
        String queryText = "select * from \"TARGET_BUS_CIRCLE_INFO\" where \"ID\" between :fromID and :toID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("fromID", "TARGET_BUS_CIRCLE_INFO.ID", QueryObject.PARAM_STRING, fromID);
        query.addParameter("toID", "TARGET_BUS_CIRCLE_INFO.ID", QueryObject.PARAM_STRING, toID);
        query.setResultClass(TARGET_BUS_CIRCLE_INFO.class);
        return query.getObjects();
    }

    public static com.laiyifen.shop.TARGET_BUS_CIRCLE_INFO getTargetBusCircleInfoObjectsForFormId(String FORM_ID)
    {
        String queryText = "select * from \"TARGET_BUS_CIRCLE_INFO\" where \"FORM_ID\" = :FORM_ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("FORM_ID", "TARGET_BUS_CIRCLE_INFO.FORM_ID", QueryObject.PARAM_STRING, FORM_ID);//NOPMD
        query.setResultClass(TARGET_BUS_CIRCLE_INFO.class);
        return (TARGET_BUS_CIRCLE_INFO)query.getObject();
    }

}

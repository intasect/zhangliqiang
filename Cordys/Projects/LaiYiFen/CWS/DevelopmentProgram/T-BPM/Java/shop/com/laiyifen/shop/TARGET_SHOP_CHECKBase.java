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


public abstract class TARGET_SHOP_CHECKBase extends com.cordys.cpc.bsf.busobject.StateBusObject
{
    // tags used in the XML document
    public final static String ATTR_ID = "ID";
    public final static String ATTR_FORM_ID = "FORM_ID";
    public final static String ATTR_TARGET_RENT = "TARGET_RENT";
    public final static String ATTR_APPORTION_SCHEME = "APPORTION_SCHEME";
    public final static String ATTR_ISINNER_PLANNING = "ISINNER_PLANNING";
    public final static String ATTR_COMPETITOR_NAME = "COMPETITOR_NAME";
    public final static String ATTR_AREA = "AREA";
    public final static String ATTR_ESTIMATED_TURNOVER = "ESTIMATED_TURNOVER";
    public final static String ATTR_ASSESS = "ASSESS";
    public final static String ATTR_ISNEAR_SHOP = "ISNEAR_SHOP";
    public final static String ATTR_ORISHOP_ADDRESS = "ORISHOP_ADDRESS";
    public final static String ATTR_ORISHOPMONTH_YRENT = "ORISHOPMONTH_YRENT";
    public final static String ATTR_ORISHOP_TURNOVER = "ORISHOP_TURNOVER";
    public final static String ATTR_SPACING = "SPACING";
    private final static String REL_FORM_IDObject = "FK:TARGET_SHOP_CHECK[FORM_ID]:TARGET_SHOP_INFO[FORM_ID]";
    private static ClassInfo s_classInfo = null;
    public static ClassInfo _getClassInfo()//NOPMD framework ensures this is thread safe
    {
        if ( s_classInfo == null )//NOPMD
        {
            s_classInfo = newClassInfo(TARGET_SHOP_CHECK.class);
            s_classInfo.setTableName("TARGET_SHOP_CHECK");
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
                AttributeInfo ai = new AttributeInfo(ATTR_TARGET_RENT);
                ai.setJavaName(ATTR_TARGET_RENT);
                ai.setColumnName(ATTR_TARGET_RENT);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_TARGET_RENT);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_APPORTION_SCHEME);
                ai.setJavaName(ATTR_APPORTION_SCHEME);
                ai.setColumnName(ATTR_APPORTION_SCHEME);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_APPORTION_SCHEME);
                v.setMaxLength(80);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ISINNER_PLANNING);
                ai.setJavaName(ATTR_ISINNER_PLANNING);
                ai.setColumnName(ATTR_ISINNER_PLANNING);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_ISINNER_PLANNING);
                v.setMaxLength(2);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_COMPETITOR_NAME);
                ai.setJavaName(ATTR_COMPETITOR_NAME);
                ai.setColumnName(ATTR_COMPETITOR_NAME);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_COMPETITOR_NAME);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_AREA);
                ai.setJavaName(ATTR_AREA);
                ai.setColumnName(ATTR_AREA);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_AREA);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ESTIMATED_TURNOVER);
                ai.setJavaName(ATTR_ESTIMATED_TURNOVER);
                ai.setColumnName(ATTR_ESTIMATED_TURNOVER);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_ESTIMATED_TURNOVER);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ASSESS);
                ai.setJavaName(ATTR_ASSESS);
                ai.setColumnName(ATTR_ASSESS);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_ASSESS);
                v.setMaxLength(200);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ISNEAR_SHOP);
                ai.setJavaName(ATTR_ISNEAR_SHOP);
                ai.setColumnName(ATTR_ISNEAR_SHOP);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_ISNEAR_SHOP);
                v.setMaxLength(2);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ORISHOP_ADDRESS);
                ai.setJavaName(ATTR_ORISHOP_ADDRESS);
                ai.setColumnName(ATTR_ORISHOP_ADDRESS);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_ORISHOP_ADDRESS);
                v.setMaxLength(200);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ORISHOPMONTH_YRENT);
                ai.setJavaName(ATTR_ORISHOPMONTH_YRENT);
                ai.setColumnName(ATTR_ORISHOPMONTH_YRENT);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_ORISHOPMONTH_YRENT);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ORISHOP_TURNOVER);
                ai.setJavaName(ATTR_ORISHOP_TURNOVER);
                ai.setColumnName(ATTR_ORISHOP_TURNOVER);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_ORISHOP_TURNOVER);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SPACING);
                ai.setJavaName(ATTR_SPACING);
                ai.setColumnName(ATTR_SPACING);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_SPACING);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                // relation FORM_IDObject (FK:TARGET_SHOP_CHECK[FORM_ID]:TARGET_SHOP_INFO[FORM_ID])
                RelationInfo_FK ri = new RelationInfo_FK(REL_FORM_IDObject);
                ri.setName("FORM_IDObject");
                ri.setLocalAttributes(new String[]{ATTR_FORM_ID});
                ri.setLocalIsPK(false);
                ri.setRelatedClass(com.laiyifen.shop.TARGET_SHOP_INFO.class);
                ri.setRelatedAttributes(new String[]{"FORM_ID"});//NOPMD
                ri.setRelatedIdentifier("FK:TARGET_SHOP_INFO[FORM_ID]:TARGET_SHOP_CHECK[FORM_ID]");
                ri.setLoadMethod("loadFORM_IDObject");
                s_classInfo.addRelationInfo(ri);
            }
        }
        return s_classInfo;
    }

    public TARGET_SHOP_CHECKBase(BusObjectConfig config)
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

    public double getTARGET_RENT()
    {
        return getDoubleProperty(ATTR_TARGET_RENT);
    }

    public void setTARGET_RENT(double value)
    {
        setProperty(ATTR_TARGET_RENT, value, 0);
    }

    public String getAPPORTION_SCHEME()
    {
        return getStringProperty(ATTR_APPORTION_SCHEME);
    }

    public void setAPPORTION_SCHEME(String value)
    {
        setProperty(ATTR_APPORTION_SCHEME, value, 0);
    }

    public String getISINNER_PLANNING()
    {
        return getStringProperty(ATTR_ISINNER_PLANNING);
    }

    public void setISINNER_PLANNING(String value)
    {
        setProperty(ATTR_ISINNER_PLANNING, value, 0);
    }

    public String getCOMPETITOR_NAME()
    {
        return getStringProperty(ATTR_COMPETITOR_NAME);
    }

    public void setCOMPETITOR_NAME(String value)
    {
        setProperty(ATTR_COMPETITOR_NAME, value, 0);
    }

    public double getAREA()
    {
        return getDoubleProperty(ATTR_AREA);
    }

    public void setAREA(double value)
    {
        setProperty(ATTR_AREA, value, 0);
    }

    public double getESTIMATED_TURNOVER()
    {
        return getDoubleProperty(ATTR_ESTIMATED_TURNOVER);
    }

    public void setESTIMATED_TURNOVER(double value)
    {
        setProperty(ATTR_ESTIMATED_TURNOVER, value, 0);
    }

    public String getASSESS()
    {
        return getStringProperty(ATTR_ASSESS);
    }

    public void setASSESS(String value)
    {
        setProperty(ATTR_ASSESS, value, 0);
    }

    public String getISNEAR_SHOP()
    {
        return getStringProperty(ATTR_ISNEAR_SHOP);
    }

    public void setISNEAR_SHOP(String value)
    {
        setProperty(ATTR_ISNEAR_SHOP, value, 0);
    }

    public String getORISHOP_ADDRESS()
    {
        return getStringProperty(ATTR_ORISHOP_ADDRESS);
    }

    public void setORISHOP_ADDRESS(String value)
    {
        setProperty(ATTR_ORISHOP_ADDRESS, value, 0);
    }

    public double getORISHOPMONTH_YRENT()
    {
        return getDoubleProperty(ATTR_ORISHOPMONTH_YRENT);
    }

    public void setORISHOPMONTH_YRENT(double value)
    {
        setProperty(ATTR_ORISHOPMONTH_YRENT, value, 0);
    }

    public double getORISHOP_TURNOVER()
    {
        return getDoubleProperty(ATTR_ORISHOP_TURNOVER);
    }

    public void setORISHOP_TURNOVER(double value)
    {
        setProperty(ATTR_ORISHOP_TURNOVER, value, 0);
    }

    public double getSPACING()
    {
        return getDoubleProperty(ATTR_SPACING);
    }

    public void setSPACING(double value)
    {
        setProperty(ATTR_SPACING, value, 0);
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
    public static BusObjectIterator<com.laiyifen.shop.TARGET_SHOP_CHECK> getNextTargetShopCheckObjects(String ID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"TARGET_SHOP_CHECK\" where (\"ID\" > :ID) order by \"ID\" asc";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("ID", "TARGET_SHOP_CHECK.ID", QueryObject.PARAM_STRING, ID);//NOPMD
        query.setResultClass(TARGET_SHOP_CHECK.class);
        query.setCursor(cursor);
        return query.getObjects();
    }

    public static BusObjectIterator<com.laiyifen.shop.TARGET_SHOP_CHECK> getPreviousTargetShopCheckObjects(String ID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"TARGET_SHOP_CHECK\" where (\"ID\" < :ID) order by \"ID\" desc";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("ID", "TARGET_SHOP_CHECK.ID", QueryObject.PARAM_STRING, ID);//NOPMD
        query.setResultClass(TARGET_SHOP_CHECK.class);
        query.setCursor(cursor);
        return query.getObjects();
    }

    public static com.laiyifen.shop.TARGET_SHOP_CHECK getTargetShopCheckObject(String ID)
    {
        String queryText = "select * from \"TARGET_SHOP_CHECK\" where \"ID\" = :ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("ID", "TARGET_SHOP_CHECK.ID", QueryObject.PARAM_STRING, ID);//NOPMD
        query.setResultClass(TARGET_SHOP_CHECK.class);
        return (TARGET_SHOP_CHECK)query.getObject();
    }

    public static BusObjectIterator<com.laiyifen.shop.TARGET_SHOP_CHECK> getTargetShopCheckObjects(String fromID, String toID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"TARGET_SHOP_CHECK\" where \"ID\" between :fromID and :toID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("fromID", "TARGET_SHOP_CHECK.ID", QueryObject.PARAM_STRING, fromID);
        query.addParameter("toID", "TARGET_SHOP_CHECK.ID", QueryObject.PARAM_STRING, toID);
        query.setResultClass(TARGET_SHOP_CHECK.class);
        query.setCursor(cursor);
        return query.getObjects();
    }

    public static BusObjectIterator<com.laiyifen.shop.TARGET_SHOP_CHECK> getTargetShopCheckObjects(String fromID, String toID)
    {
        String queryText = "select * from \"TARGET_SHOP_CHECK\" where \"ID\" between :fromID and :toID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("fromID", "TARGET_SHOP_CHECK.ID", QueryObject.PARAM_STRING, fromID);
        query.addParameter("toID", "TARGET_SHOP_CHECK.ID", QueryObject.PARAM_STRING, toID);
        query.setResultClass(TARGET_SHOP_CHECK.class);
        return query.getObjects();
    }

    public static com.laiyifen.shop.TARGET_SHOP_CHECK getTargetShopCheckObjectsForFormId(String FORM_ID)
    {
        String queryText = "select * from \"TARGET_SHOP_CHECK\" where \"FORM_ID\" = :FORM_ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("FORM_ID", "TARGET_SHOP_CHECK.FORM_ID", QueryObject.PARAM_STRING, FORM_ID);//NOPMD
        query.setResultClass(TARGET_SHOP_CHECK.class);
        return (TARGET_SHOP_CHECK)query.getObject();
    }

}

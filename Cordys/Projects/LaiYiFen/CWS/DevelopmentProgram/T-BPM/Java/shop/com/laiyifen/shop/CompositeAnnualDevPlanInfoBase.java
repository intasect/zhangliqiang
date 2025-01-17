/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.shop;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.classinfo.ClassInfo;
import com.cordys.cpc.bsf.classinfo.RelationInfo_Composite;


public abstract class CompositeAnnualDevPlanInfoBase extends com.cordys.cpc.bsf.busobject.CustomBusObject
{
    // tags used in the XML document
    private final static String REL_SHOP_MONTHLY_PLAN = "AGGR:SHOP_MONTHLY_PLAN";
    private final static String REL_ATTACHMENT = "AGGR:ATTACHMENT";
    private final static String REL_APPROVAL_HISTORY = "AGGR:APPROVAL_HISTORY";
    private static ClassInfo s_classInfo = null;
    public static ClassInfo _getClassInfo()//NOPMD framework ensures this is thread safe
    {
        if ( s_classInfo == null )//NOPMD
        {
            s_classInfo = newClassInfo(CompositeAnnualDevPlanInfo.class);
            s_classInfo.setUIDElements(new String[]{});
            {
                RelationInfo_Composite ri = new RelationInfo_Composite(REL_SHOP_MONTHLY_PLAN);
                ri.setName("SHOP_MONTHLY_PLAN");
                ri.setMultiOcc(false);
                ri.setRelatedClass(com.laiyifen.shop.SHOP_MONTHLY_PLAN.class);
                s_classInfo.addRelationInfo(ri);
            }
            {
                RelationInfo_Composite ri = new RelationInfo_Composite(REL_ATTACHMENT);
                ri.setName("ATTACHMENT");
                ri.setMultiOcc(true);
                ri.setRelatedClass(com.laiyifen.common.core.ATTACHMENT.class);
                s_classInfo.addRelationInfo(ri);
            }
            {
                RelationInfo_Composite ri = new RelationInfo_Composite(REL_APPROVAL_HISTORY);
                ri.setName("APPROVAL_HISTORY");
                ri.setMultiOcc(true);
                ri.setRelatedClass(com.laiyifen.common.core.APPROVAL_HISTORY.class);
                s_classInfo.addRelationInfo(ri);
            }
        }
        return s_classInfo;
    }

    public CompositeAnnualDevPlanInfoBase(BusObjectConfig config)
    {
        super(config);
    }

    public SHOP_MONTHLY_PLAN getSHOP_MONTHLY_PLANObject()
    {
        return (SHOP_MONTHLY_PLAN)getSingleRelationObject(REL_SHOP_MONTHLY_PLAN);
    }

    public SHOP_MONTHLY_PLAN setSHOP_MONTHLY_PLANObject(SHOP_MONTHLY_PLAN a_SHOP_MONTHLY_PLAN)
    {
        return(SHOP_MONTHLY_PLAN)_getSingleRelation(REL_SHOP_MONTHLY_PLAN, true).setLocalObject(a_SHOP_MONTHLY_PLAN);
    }

    public BusObjectIterator<com.laiyifen.common.core.ATTACHMENT> getATTACHMENTObjects()
    {
        return getMultiRelationObjects(REL_ATTACHMENT);
    }

    public com.laiyifen.common.core.ATTACHMENT addATTACHMENTObject(com.laiyifen.common.core.ATTACHMENT a_ATTACHMENT)
    {
        return (com.laiyifen.common.core.ATTACHMENT)_getMultiRelation(REL_ATTACHMENT, true).addObject(a_ATTACHMENT);
    }

    public void removeATTACHMENTObject(com.laiyifen.common.core.ATTACHMENT a_ATTACHMENT)
    {
        _getMultiRelation(REL_ATTACHMENT, true).removeObject(a_ATTACHMENT);
    }

    public BusObjectIterator<com.laiyifen.common.core.APPROVAL_HISTORY> getAPPROVAL_HISTORYObjects()
    {
        return getMultiRelationObjects(REL_APPROVAL_HISTORY);
    }

    public com.laiyifen.common.core.APPROVAL_HISTORY addAPPROVAL_HISTORYObject(com.laiyifen.common.core.APPROVAL_HISTORY a_APPROVAL_HISTORY)
    {
        return (com.laiyifen.common.core.APPROVAL_HISTORY)_getMultiRelation(REL_APPROVAL_HISTORY, true).addObject(a_APPROVAL_HISTORY);
    }

    public void removeAPPROVAL_HISTORYObject(com.laiyifen.common.core.APPROVAL_HISTORY a_APPROVAL_HISTORY)
    {
        _getMultiRelation(REL_APPROVAL_HISTORY, true).removeObject(a_APPROVAL_HISTORY);
    }



}

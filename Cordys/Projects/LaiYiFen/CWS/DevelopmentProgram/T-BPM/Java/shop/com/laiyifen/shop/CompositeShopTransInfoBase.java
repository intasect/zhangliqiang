/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.shop;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.classinfo.ClassInfo;
import com.cordys.cpc.bsf.classinfo.RelationInfo_Composite;


public abstract class CompositeShopTransInfoBase extends com.cordys.cpc.bsf.busobject.CustomBusObject
{
    // tags used in the XML document
    private final static String REL_SHOP_TRANS_INFO = "AGGR:SHOP_TRANS_INFO";
    private final static String REL_SHOP_TRANS_DISTRIBUTION = "AGGR:SHOP_TRANS_DISTRIBUTION";
    private final static String REL_SHOP_TRANS_ENG = "AGGR:SHOP_TRANS_ENG";
    private final static String REL_SHOP_MASTER = "AGGR:SHOP_MASTER";
    private final static String REL_APPROVAL_HISTORY = "AGGR:APPROVAL_HISTORY";
    private final static String REL_ATTACHMENT = "AGGR:ATTACHMENT";
    private final static String REL_SHOP_TARGET_INFO = "AGGR:SHOP_TARGET_INFO";
    private final static String REL_SUPPLIERS_MASTER = "AGGR:SUPPLIERS_MASTER";
    private static ClassInfo s_classInfo = null;
    public static ClassInfo _getClassInfo()//NOPMD framework ensures this is thread safe
    {
        if ( s_classInfo == null )//NOPMD
        {
            s_classInfo = newClassInfo(CompositeShopTransInfo.class);
            s_classInfo.setUIDElements(new String[]{});
            {
                RelationInfo_Composite ri = new RelationInfo_Composite(REL_SHOP_TRANS_INFO);
                ri.setName("SHOP_TRANS_INFO");
                ri.setMultiOcc(false);
                ri.setRelatedClass(com.laiyifen.shop.SHOP_TRANS_INFO.class);
                s_classInfo.addRelationInfo(ri);
            }
            {
                RelationInfo_Composite ri = new RelationInfo_Composite(REL_SHOP_TRANS_DISTRIBUTION);
                ri.setName("SHOP_TRANS_DISTRIBUTION");
                ri.setMultiOcc(true);
                ri.setRelatedClass(com.laiyifen.shop.SHOP_TRANS_DISTRIBUTION.class);
                s_classInfo.addRelationInfo(ri);
            }
            {
                RelationInfo_Composite ri = new RelationInfo_Composite(REL_SHOP_TRANS_ENG);
                ri.setName("SHOP_TRANS_ENG");
                ri.setMultiOcc(false);
                ri.setRelatedClass(com.laiyifen.shop.SHOP_TRANS_ENG.class);
                s_classInfo.addRelationInfo(ri);
            }
            {
                RelationInfo_Composite ri = new RelationInfo_Composite(REL_SHOP_MASTER);
                ri.setName("SHOP_MASTER");
                ri.setMultiOcc(false);
                ri.setRelatedClass(com.laiyifen.shop.SHOP_MASTER.class);
                s_classInfo.addRelationInfo(ri);
            }
            {
                RelationInfo_Composite ri = new RelationInfo_Composite(REL_APPROVAL_HISTORY);
                ri.setName("APPROVAL_HISTORY");
                ri.setMultiOcc(true);
                ri.setRelatedClass(com.laiyifen.common.core.APPROVAL_HISTORY.class);
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
                RelationInfo_Composite ri = new RelationInfo_Composite(REL_SHOP_TARGET_INFO);
                ri.setName("SHOP_TARGET_INFO");
                ri.setMultiOcc(false);
                ri.setRelatedClass(com.laiyifen.shop.SHOP_TARGET_INFO.class);
                s_classInfo.addRelationInfo(ri);
            }
            {
                RelationInfo_Composite ri = new RelationInfo_Composite(REL_SUPPLIERS_MASTER);
                ri.setName("SUPPLIERS_MASTER");
                ri.setMultiOcc(false);
                ri.setRelatedClass(com.laiyifen.shop.SUPPLIERS_MASTER.class);
                s_classInfo.addRelationInfo(ri);
            }
        }
        return s_classInfo;
    }

    public CompositeShopTransInfoBase(BusObjectConfig config)
    {
        super(config);
    }

    public SHOP_TRANS_INFO getSHOP_TRANS_INFOObject()
    {
        return (SHOP_TRANS_INFO)getSingleRelationObject(REL_SHOP_TRANS_INFO);
    }

    public SHOP_TRANS_INFO setSHOP_TRANS_INFOObject(SHOP_TRANS_INFO a_SHOP_TRANS_INFO)
    {
        return(SHOP_TRANS_INFO)_getSingleRelation(REL_SHOP_TRANS_INFO, true).setLocalObject(a_SHOP_TRANS_INFO);
    }

    public BusObjectIterator<SHOP_TRANS_DISTRIBUTION> getSHOP_TRANS_DISTRIBUTIONObjects()
    {
        return getMultiRelationObjects(REL_SHOP_TRANS_DISTRIBUTION);
    }

    public SHOP_TRANS_DISTRIBUTION addSHOP_TRANS_DISTRIBUTIONObject(SHOP_TRANS_DISTRIBUTION a_SHOP_TRANS_DISTRIBUTION)
    {
        return (SHOP_TRANS_DISTRIBUTION)_getMultiRelation(REL_SHOP_TRANS_DISTRIBUTION, true).addObject(a_SHOP_TRANS_DISTRIBUTION);
    }

    public void removeSHOP_TRANS_DISTRIBUTIONObject(SHOP_TRANS_DISTRIBUTION a_SHOP_TRANS_DISTRIBUTION)
    {
        _getMultiRelation(REL_SHOP_TRANS_DISTRIBUTION, true).removeObject(a_SHOP_TRANS_DISTRIBUTION);
    }

    public SHOP_TRANS_ENG getSHOP_TRANS_ENGObject()
    {
        return (SHOP_TRANS_ENG)getSingleRelationObject(REL_SHOP_TRANS_ENG);
    }

    public SHOP_TRANS_ENG setSHOP_TRANS_ENGObject(SHOP_TRANS_ENG a_SHOP_TRANS_ENG)
    {
        return(SHOP_TRANS_ENG)_getSingleRelation(REL_SHOP_TRANS_ENG, true).setLocalObject(a_SHOP_TRANS_ENG);
    }

    public SHOP_MASTER getSHOP_MASTERObject()
    {
        return (SHOP_MASTER)getSingleRelationObject(REL_SHOP_MASTER);
    }

    public SHOP_MASTER setSHOP_MASTERObject(SHOP_MASTER a_SHOP_MASTER)
    {
        return(SHOP_MASTER)_getSingleRelation(REL_SHOP_MASTER, true).setLocalObject(a_SHOP_MASTER);
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

    public SHOP_TARGET_INFO getSHOP_TARGET_INFOObject()
    {
        return (SHOP_TARGET_INFO)getSingleRelationObject(REL_SHOP_TARGET_INFO);
    }

    public SHOP_TARGET_INFO setSHOP_TARGET_INFOObject(SHOP_TARGET_INFO a_SHOP_TARGET_INFO)
    {
        return(SHOP_TARGET_INFO)_getSingleRelation(REL_SHOP_TARGET_INFO, true).setLocalObject(a_SHOP_TARGET_INFO);
    }

    public SUPPLIERS_MASTER getSUPPLIERS_MASTERObject()
    {
        return (SUPPLIERS_MASTER)getSingleRelationObject(REL_SUPPLIERS_MASTER);
    }

    public SUPPLIERS_MASTER setSUPPLIERS_MASTERObject(SUPPLIERS_MASTER a_SUPPLIERS_MASTER)
    {
        return(SUPPLIERS_MASTER)_getSingleRelation(REL_SUPPLIERS_MASTER, true).setLocalObject(a_SUPPLIERS_MASTER);
    }




}

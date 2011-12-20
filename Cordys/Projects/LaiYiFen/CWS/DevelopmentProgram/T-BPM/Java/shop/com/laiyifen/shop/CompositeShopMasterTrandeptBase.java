/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.shop;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.classinfo.ClassInfo;
import com.cordys.cpc.bsf.classinfo.RelationInfo_Composite;


public abstract class CompositeShopMasterTrandeptBase extends com.cordys.cpc.bsf.busobject.CustomBusObject
{
    // tags used in the XML document
    private final static String REL_SHOP_MASTER = "AGGR:SHOP_MASTER";
    private final static String REL_SHOP_MASTER_TRANDEPT = "AGGR:SHOP_MASTER_TRANDEPT";
    private final static String REL_ATTACHMENT = "AGGR:ATTACHMENT";
    private static ClassInfo s_classInfo = null;
    public static ClassInfo _getClassInfo()//NOPMD framework ensures this is thread safe
    {
        if ( s_classInfo == null )//NOPMD
        {
            s_classInfo = newClassInfo(CompositeShopMasterTrandept.class);
            s_classInfo.setUIDElements(new String[]{});
            {
                RelationInfo_Composite ri = new RelationInfo_Composite(REL_SHOP_MASTER);
                ri.setName("SHOP_MASTER");
                ri.setMultiOcc(false);
                ri.setRelatedClass(com.laiyifen.shop.SHOP_MASTER.class);
                s_classInfo.addRelationInfo(ri);
            }
            {
                RelationInfo_Composite ri = new RelationInfo_Composite(REL_SHOP_MASTER_TRANDEPT);
                ri.setName("SHOP_MASTER_TRANDEPT");
                ri.setMultiOcc(false);
                ri.setRelatedClass(com.laiyifen.shop.SHOP_MASTER_TRANDEPT.class);
                s_classInfo.addRelationInfo(ri);
            }
            {
                RelationInfo_Composite ri = new RelationInfo_Composite(REL_ATTACHMENT);
                ri.setName("ATTACHMENT");
                ri.setMultiOcc(true);
                ri.setRelatedClass(com.laiyifen.common.core.ATTACHMENT.class);
                s_classInfo.addRelationInfo(ri);
            }
        }
        return s_classInfo;
    }

    public CompositeShopMasterTrandeptBase(BusObjectConfig config)
    {
        super(config);
    }

    public SHOP_MASTER getSHOP_MASTERObject()
    {
        return (SHOP_MASTER)getSingleRelationObject(REL_SHOP_MASTER);
    }

    public SHOP_MASTER setSHOP_MASTERObject(SHOP_MASTER a_SHOP_MASTER)
    {
        return(SHOP_MASTER)_getSingleRelation(REL_SHOP_MASTER, true).setLocalObject(a_SHOP_MASTER);
    }

    public SHOP_MASTER_TRANDEPT getSHOP_MASTER_TRANDEPTObject()
    {
        return (SHOP_MASTER_TRANDEPT)getSingleRelationObject(REL_SHOP_MASTER_TRANDEPT);
    }

    public SHOP_MASTER_TRANDEPT setSHOP_MASTER_TRANDEPTObject(SHOP_MASTER_TRANDEPT a_SHOP_MASTER_TRANDEPT)
    {
        return(SHOP_MASTER_TRANDEPT)_getSingleRelation(REL_SHOP_MASTER_TRANDEPT, true).setLocalObject(a_SHOP_MASTER_TRANDEPT);
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



}
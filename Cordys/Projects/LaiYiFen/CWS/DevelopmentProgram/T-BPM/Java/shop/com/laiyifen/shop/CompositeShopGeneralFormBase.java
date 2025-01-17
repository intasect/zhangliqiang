/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.shop;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.classinfo.ClassInfo;
import com.cordys.cpc.bsf.classinfo.RelationInfo_Composite;


public abstract class CompositeShopGeneralFormBase extends com.cordys.cpc.bsf.busobject.CustomBusObject
{
    // tags used in the XML document
    private final static String REL_SHOP_MASTER = "AGGR:SHOP_MASTER";
    private final static String REL_SHOP_GENERAL_FORM = "AGGR:SHOP_GENERAL_FORM";
    private static ClassInfo s_classInfo = null;
    public static ClassInfo _getClassInfo()//NOPMD framework ensures this is thread safe
    {
        if ( s_classInfo == null )//NOPMD
        {
            s_classInfo = newClassInfo(CompositeShopGeneralForm.class);
            s_classInfo.setUIDElements(new String[]{});
            {
                RelationInfo_Composite ri = new RelationInfo_Composite(REL_SHOP_MASTER);
                ri.setName("SHOP_MASTER");
                ri.setMultiOcc(false);
                ri.setRelatedClass(com.laiyifen.shop.SHOP_MASTER.class);
                s_classInfo.addRelationInfo(ri);
            }
            {
                RelationInfo_Composite ri = new RelationInfo_Composite(REL_SHOP_GENERAL_FORM);
                ri.setName("SHOP_GENERAL_FORM");
                ri.setMultiOcc(false);
                ri.setRelatedClass(com.laiyifen.shop.SHOP_GENERAL_FORM.class);
                s_classInfo.addRelationInfo(ri);
            }
        }
        return s_classInfo;
    }

    public CompositeShopGeneralFormBase(BusObjectConfig config)
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

    public SHOP_GENERAL_FORM getSHOP_GENERAL_FORMObject()
    {
        return (SHOP_GENERAL_FORM)getSingleRelationObject(REL_SHOP_GENERAL_FORM);
    }

    public SHOP_GENERAL_FORM setSHOP_GENERAL_FORMObject(SHOP_GENERAL_FORM a_SHOP_GENERAL_FORM)
    {
        return(SHOP_GENERAL_FORM)_getSingleRelation(REL_SHOP_GENERAL_FORM, true).setLocalObject(a_SHOP_GENERAL_FORM);
    }



}

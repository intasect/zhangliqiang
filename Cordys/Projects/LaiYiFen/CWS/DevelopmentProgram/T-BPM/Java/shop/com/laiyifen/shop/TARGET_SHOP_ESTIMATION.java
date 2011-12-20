/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.shop;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.laiyifen.common.util.GUIDUtil;

public class TARGET_SHOP_ESTIMATION extends TARGET_SHOP_ESTIMATIONBase
{
	private GUIDUtil guidGenerator = null;
    private String guidValue = null;
    
    public TARGET_SHOP_ESTIMATION()
    {
        this((BusObjectConfig)null);
    }

    public TARGET_SHOP_ESTIMATION(BusObjectConfig config)
    {
        super(config);
    }
    
    public void onBeforeInsert() {
    	if(guidGenerator == null)
		{
			guidGenerator = new GUIDUtil();
		}
		if(guidValue == null)
		{
			guidValue=guidGenerator.setGUID(null);
		}
		if(this.getID()==null || this.getID().equals("")){
			this.setID(guidValue);
		}
		
		if(this.getDEPTMENT_CODE()==null || this.getDEPTMENT_CODE().equals("")){
			this.setDEPTMENT_CODE(com.laiyifen.common.core.User.getUserInfo().getDeptCode());
		}
		
		if(this.getDEPTMENT_NAME()==null || this.getDEPTMENT_NAME().equals("")){
			this.setDEPTMENT_NAME(com.laiyifen.common.core.User.getUserInfo().getTeamName());
		}
		
		if(this.getOPERATOR_NAME()==null || this.getOPERATOR_NAME().equals("")){
			this.setOPERATOR_NAME(com.laiyifen.common.core.User.getUserInfo().getUserName());
		}
		
		if(this.getOPERATOR_DN()==null || this.getOPERATOR_DN().equals("")){
			this.setOPERATOR_DN(com.laiyifen.common.core.User.getUserInfo().getUserDN());
		}
		
		if(this.getOPERATING_DATE()==null || this.getOPERATING_DATE().equals("")){
			this.setOPERATING_DATE(new java.util.Date());
		}
	}

}
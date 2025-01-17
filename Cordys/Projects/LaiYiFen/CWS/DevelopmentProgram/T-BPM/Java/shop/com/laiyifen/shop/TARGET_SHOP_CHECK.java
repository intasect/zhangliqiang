/*
  This class has been generated by the Code Generator
 */

package com.laiyifen.shop;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.laiyifen.common.util.GUIDUtil;

public class TARGET_SHOP_CHECK extends TARGET_SHOP_CHECKBase {
	
	private GUIDUtil guidGenerator = null;
    private String guidValue = null;
    
	public TARGET_SHOP_CHECK() {
		this((BusObjectConfig) null);
	}

	public TARGET_SHOP_CHECK(BusObjectConfig config) {
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
		
		if(this.getID()==null || this.getID()==null){
			this.setID(guidValue);
		}
	}

}

package com.laiyifen.shop.util;

import com.eibus.util.system.Native;

public class ShopUtil {
	
	private String formID ="";
	private String shopID ="";

	@SuppressWarnings("unused")
	public String getShopID() {
		return shopID;
	}

	public String setShopID(String _shopID) {
		if (_shopID == null) {
			_shopID =  Native.createGuid();
		}
		return this.shopID = _shopID;
	}

	@SuppressWarnings("unused")
	public String getFormID() {
		return formID;
	}

	public String setFormID(String _formID) {
		if (_formID == null) {
			_formID = Native.createGuid();
		}
		return this.formID = _formID;
	}
}

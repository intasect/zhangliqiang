/**
 * @Project:LYF
 * @FileName:GUIDUtil.java  2011-7-20
 * Copyright 2011 Cordys Info BV. All rights reserved.
 */
package com.laiyifen.common.util;

import com.eibus.util.system.Native;

public class GUIDUtil
{
	private String guidValue ="";
	
	public String getGUID() {
		return guidValue;
	}

	public String setGUID(String _guidValue) {
		if (_guidValue == null) {
			_guidValue = Native.createGuid();
		}
		return this.guidValue = _guidValue;
	}
	
	public static String getFormID() {
		return Native.createGuid();
	}
}

package com.laiyifen.bpc.util;

import com.eibus.util.system.Native;

public class BPCUtil {
	
	private String ID ="";
	private String formID ="";
	
	@SuppressWarnings("unused")
	public String getID() {
		return ID;
	}

	public String setID(String _ID) {
		if (_ID == null) {
			_ID =  Native.createGuid();
		}
		return this.ID = _ID;
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

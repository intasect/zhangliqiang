package com.laiyifen.goods.core.util;

import java.util.ArrayList;

import com.laiyifen.goods.core.GROUPBUY_MATERIAL_INVENTO;
import com.laiyifen.goods.core.GROUPBUY_MATERIAL_PRICE;

public class CollectionModeObject {
	private ArrayList<GROUPBUY_MATERIAL_PRICE> groupbuyMaterialPriceList ;
	private ArrayList<GROUPBUY_MATERIAL_INVENTO> groupbuyMaterialInventoList ;
	public ArrayList<GROUPBUY_MATERIAL_PRICE> getGroupbuyMaterialPriceList() {
		return groupbuyMaterialPriceList;
	}
	public void setGroupbuyMaterialPriceList(
			ArrayList<GROUPBUY_MATERIAL_PRICE> groupbuyMaterialPriceList) {
		this.groupbuyMaterialPriceList = groupbuyMaterialPriceList;
	}
	public ArrayList<GROUPBUY_MATERIAL_INVENTO> getGroupbuyMaterialInventoList() {
		return groupbuyMaterialInventoList;
	}
	public void setGroupbuyMaterialInventoList(
			ArrayList<GROUPBUY_MATERIAL_INVENTO> groupbuyMaterialInventoList) {
		this.groupbuyMaterialInventoList = groupbuyMaterialInventoList;
	}
}

//页面初始化
function initForm(eventObject)
{
	gpxhide.hide();
    //隐藏formid,shopid字段
    for(var i = 0; i<2; i++)
   {
      tabShopDrawings.hideColumn(i);
   }
    
    //初始化grid双击事件
    tabShopDrawings.ondblclick=function(){ 
    tabShopDrawings_ondblclick();
    };
}

//刷新grid模型数据
﻿function refreshModels()
{
    var request = GetSelectDrawingsObjectsModel.getMethodRequest();
    cordys.setNodeText(request ,".//*[local-name()='shopNo']",iptShopNo.getValue());
    cordys.setNodeText(request ,".//*[local-name()='shopName']",iptShopName.getValue());
    cordys.setNodeText(request ,".//*[local-name()='layoutType']",iptLayoutType.getValue());
    cordys.setNodeText(request ,".//*[local-name()='useArea']",iptUseArea.getValue());
    cordys.setNodeText(request ,".//*[local-name()='storageArea']",iptStorageArea.getValue());

    GetSelectDrawingsObjectsModel.setMethodRequest(request);
    GetSelectDrawingsObjectsModel.reset();
    GetSelectDrawingsObjectsModel.refreshAllViews();

}



//grid双击事件
function tabShopDrawings_ondblclick()
{
    if(tabShopDrawings.getIndex() != -1)
    {
        var subformid = form_id[tabShopDrawings.getIndex()].getValue();
        var data = new Object();
        data.subformid = form_id[tabShopDrawings.getIndex()].getValue();
        data.subshopid = shop_id[tabShopDrawings.getIndex()].getValue();
        application.showDialog(dialogShopDrawingsInfo.XMLDocument.documentElement,data, null, getShopDrawingsValue, false);
    
    }
}

//双击事件回调函数
function getShopDrawingsValue()
{
}

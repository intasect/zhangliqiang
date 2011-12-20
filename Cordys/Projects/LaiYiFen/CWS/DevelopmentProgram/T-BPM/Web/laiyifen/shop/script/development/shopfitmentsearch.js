//页面初始化
function initForm(eventObject)
{
    //隐藏formid,shopid字段
    for(var i = 0; i<3; i++)
   {
      tabShopFitment.hideColumn(i);
   }
    
    //初始化grid双击事件
    tabShopFitment.ondblclick=function(){ 
    tabShopFitment_ondblclick();
    };
}

//刷新grid模型数据
﻿function refreshModels()
{
    var request = GetSelectFitmentObjectsModel.getMethodRequest();
    cordys.setNodeText(request ,".//*[local-name()='shopNo']",iptShopNo.getValue());
    cordys.setNodeText(request ,".//*[local-name()='shopName']",iptShopName.getValue());
    cordys.setNodeText(request ,".//*[local-name()='actualInTimeBegin']",iptActualInTimeBegin.getValue());
    cordys.setNodeText(request ,".//*[local-name()='actualInTimeEnd']",iptActualInTimeEnd.getValue());
    cordys.setNodeText(request ,".//*[local-name()='estimateTimeBegin']",iptEstimateTimeBegin.getValue());
    cordys.setNodeText(request ,".//*[local-name()='estimateTimeEnd']",iptEstimateTimeEnd.getValue());

    GetSelectFitmentObjectsModel.setMethodRequest(request);
    GetSelectFitmentObjectsModel.reset();
    GetSelectFitmentObjectsModel.refreshAllViews();

}



//grid双击事件
function tabShopFitment_ondblclick()
{
    if(tabShopFitment.getIndex() != -1)
    {
        var subformid = form_id[tabShopFitment.getIndex()].getValue();
        var data = new Object();
        data.subformid = form_id[tabShopFitment.getIndex()].getValue();
        data.subshopid = shop_id[tabShopFitment.getIndex()].getValue();
        application.showDialog(dialogFitmentInfo.XMLDocument.documentElement,data, null, getShopFitmentValue, false);
    
    }
}

//双击事件回调函数
function getShopFitmentValue()
{
}

//页面初始化
function initForm(eventObject)
{
    //隐藏formid,shopid字段
    for(var i = 0; i<2; i++)
   {
      tabShopSeal.hideColumn(i);
   }
    
    //初始化grid双击事件
    tabShopSeal.ondblclick=function(){ 
    tabShopSeal_ondblclick();
    };
}

//刷新grid模型数据
﻿function refreshModels()
{
    var request = GetSelectShopSealAppObjectsModel.getMethodRequest();
    cordys.setNodeText(request ,".//*[local-name()='amount']",iptamount.getValue());
    cordys.setNodeText(request ,".//*[local-name()='seal_name']",iptsealname.getValue());
    cordys.setNodeText(request ,".//*[local-name()='collaborators']",iptcollaborators.getValue());
    cordys.setNodeText(request ,".//*[local-name()='seal_date_begin']",iptsealdatebegin.getValue());
    cordys.setNodeText(request ,".//*[local-name()='seal_date_end']",iptsealdataend.getValue());

    GetSelectShopSealAppObjectsModel.setMethodRequest(request);
    GetSelectShopSealAppObjectsModel.reset();
    GetSelectShopSealAppObjectsModel.refreshAllViews();

}



//grid双击事件
function tabShopSeal_ondblclick()
{
    if(tabShopSeal.getIndex() != -1)
    {
        var subformid = form_id[tabShopSeal.getIndex()].getValue();
        var data = new Object();
        data.subformid = form_id[tabShopSeal.getIndex()].getValue();
        data.subshopid = shop_id[tabShopSeal.getIndex()].getValue();
        application.showDialog(dialogShopSealInfo.XMLDocument.documentElement,data, null, getShopSealValue, false);
    
    }
}

//双击事件回调函数
function getShopSealValue()
{
}

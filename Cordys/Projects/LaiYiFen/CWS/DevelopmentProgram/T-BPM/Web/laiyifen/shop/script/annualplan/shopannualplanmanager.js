//页面初始化
function initForm(eventObject)
{
    //隐藏formid字段
    tabShopMonthlyPlan.hideColumn(0);
    
    //初始化grid双击事件
    tabShopMonthlyPlan.ondblclick=function(){ 
    tabShopMonthlyPlan_ondblclick();
    };
}

//刷新grid模型数据
﻿function refreshModels()
{
    var request = GetSelectShopMothlyPlanObjectsModel.getMethodRequest();
    cordys.setNodeText(request ,".//*[local-name()='year']",iptyear.getValue());
    cordys.setNodeText(request ,".//*[local-name()='department_code']",iptdepartmentcode.getValue());
    cordys.setNodeText(request ,".//*[local-name()='subcompany_code']",iptsubcompanycode.getValue());

    GetSelectShopMothlyPlanObjectsModel.setMethodRequest(request);
    GetSelectShopMothlyPlanObjectsModel.reset();
    GetSelectShopMothlyPlanObjectsModel.refreshAllViews();

}

//删除数据数据
﻿function deleteEvent(eventObject)
{
    var request = DeleteShopMothlyPlanObjectModel.getMethodRequest();
    cordys.setNodeText(request,".//*[local-name()='formId']",form_id[tabShopMonthlyPlan.getIndex()].getValue());
    DeleteShopMothlyPlanObjectModel.setMethodRequest(request);
    DeleteShopMothlyPlanObjectModel.reset();
    DeleteShopMothlyPlanObjectModel.refreshAllViews();
    
    refreshModels();
}

//grid双击事件
function tabShopMonthlyPlan_ondblclick()
{
    if(tabShopMonthlyPlan.getIndex() != -1)
    {
        var subformid = form_id[tabShopMonthlyPlan.getIndex()].getValue();
        var data = new Object();
        data.subformid = form_id[tabShopMonthlyPlan.getIndex()].getValue();
        application.showDialog(dialogNewShopAnnualPlan.XMLDocument.documentElement,data, null, GetNewShopAnnualValue, false);
    
    }
}

//新建按钮弹出框回调函数
function GetNewShopAnnualValue(rtnValue)
{
    var request = GetSelectShopMothlyPlanObjectsModel.getMethodRequest();
    cordys.setNodeText(request,".//*[local-name()='year']",iptyear.getValue());
    cordys.setNodeText(request,".//*[local-name()='department_code']",iptdepartmentcode.getValue());
    cordys.setNodeText(request,".//*[local-name()='subcompany_code']",iptsubcompanycode.getValue());
    
    GetSelectShopMothlyPlanObjectsModel.setMethodRequest(request);
    GetSelectShopMothlyPlanObjectsModel.reset();
    GetSelectShopMothlyPlanObjectsModel.refreshAllViews();
}


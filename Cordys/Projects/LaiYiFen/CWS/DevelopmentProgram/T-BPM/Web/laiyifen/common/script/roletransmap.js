function initialized_value()
{
    GetEmployeeRolesByConditionsModel.reset();
}

function do_GetEmployeeRolesByConditionsModel_OnSOAPFault(eventObject)
{
    eventObject.showError = false;
    application.notify("您的操作未能成功执行，请您重新尝试或联系系统管理员，谢谢");
}

function do_tbsave_Click()
{
    var isFlag=validateCommon(this.document);
    if(!isFlag){
       return false;
    }
    showConfirm(false,closeHandler);
}

function closeHandler(confirmReturnValue)
{
    if (confirmReturnValue == 1)
    {
          GetEmployeeRolesByConditionsModel.synchronize(false);
    }
    else if (confirmReturnValue == 0)
    {
    }
}

function do_tbquit_Click()
{
    application.container.close();
}
﻿
function getRoleDN(eventObject)
{
    var oParam = new Object();
    oParam.selectMode = "SINGLE";
    application.select(Application_SelectRoles.XMLDocument.documentElement, oParam);
    eventObject.returnValue=false;
    setRoleValues(oParam);
}

function setRoleValues(oParam)
{
    var idx = rolestable.getIndex();
    roledn[idx].setValue(oParam.selectedRoleList);
    rolename[idx].setValue(oParam.selectedRoleNameList);
}

function do_tbsearch_Click(eventObject)
{
    var request = GetEmployeeRolesByConditionsModel.getMethodRequest();
    cordys.setNodeText(request,".//*[local-name()='companycd']",iptcompanycd.getValue());
    cordys.setNodeText(request,".//*[local-name()='companyname']",iptcompanyname.getValue());
    cordys.setNodeText(request,".//*[local-name()='deptcd']",iptdeptcd.getValue());
    cordys.setNodeText(request,".//*[local-name()='deptname']",iptdeptname.getValue());
    cordys.setNodeText(request,".//*[local-name()='stationcd']",iptstationcd.getValue());
    cordys.setNodeText(request,".//*[local-name()='stationname']",iptstationname.getValue());
    
    GetEmployeeRolesByConditionsModel.setMethodRequest(request);
    GetEmployeeRolesByConditionsModel.reset();
    GetEmployeeRolesByConditionsModel.refreshAllViews();
}
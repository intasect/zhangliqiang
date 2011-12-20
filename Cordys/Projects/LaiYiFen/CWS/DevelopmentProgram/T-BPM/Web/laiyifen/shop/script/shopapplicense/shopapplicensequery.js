/***************************
*功能名称：do_toolbarbuttonQuery_Click(); 
* 描述:查询证照办理表单
*/
function do_toolbarbuttonQuery_Click(eventObject)
{
   var request = GetShopMasterObjectByFilterModel.getMethodRequest();
    cordys.setNodeText(request,".//*[local-name()='shopSerialno']",shopSerialno.getValue());
    cordys.setNodeText(request,".//*[local-name()='shopNo']",shopNo.getValue()); 
    cordys.setNodeText(request,".//*[local-name()='shopArea']",shopArea.getValue());
    cordys.setNodeText(request,".//*[local-name()='shopName']",shopName.getValue()); 
    cordys.setNodeText(request,".//*[local-name()='shopShortname']",shopShortname.getValue());
    cordys.setNodeText(request,".//*[local-name()='corpName']",corpName.getValue()); 
    cordys.setNodeText(request,".//*[local-name()='shopAddr']",shopAddr.getValue()); 
    GetShopMasterObjectByFilterModel.setMethodRequest(request);
    GetShopMasterObjectByFilterModel.reset();
    //GetShopMasterObjectByFilterModel.refreshAllViews();
}
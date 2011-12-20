var singleXMLDoc = '<xml id="dialogSingle">'
    + '<Application>' 
    + '<id>multiFunctionalgrid</id>'
    + '<description>角色人员列表</description>'
    + '<caption>角色人员列表</caption>'
    + '<url>/User Interface/com/laiyifen/common/usersync/角色下人员选择.caf</url>'
    + '<frame features="dialogWidth:300px;dialogHeight:180px;resizable:yes">_modal</frame>'
    + '</Application>'
    + '</xml>';
 var    uObject=null;
 
 function getDialogValueByRole(eventObject,inputParam,caption,width,height,GetReturnValue)
{   
    var xmlObject;
    //dataType: 1调用单选字典弹出页；*调用多选字典弹出页
   	xmlObject = cordys.loadXMLDocument(singleXMLDoc);
    if(width != null && width != "" && height != null && height != "")
    {
    	var feature = "dialogWidth:" + width + ";dialogHeight:" + height + ";resizable:yes";
    	cordys.setXMLAttribute(cordys.selectXMLNode(xmlObject,".//*[local-name()='frame']"),"","features",feature); 
    }
    var appDef = cordys.selectXMLNode(xmlObject,".//*[local-name()='Application']");
    cordys.setNodeText(appDef,".//*[local-name()='description']",caption);
    cordys.setNodeText(appDef,".//*[local-name()='caption']",caption);
    application.showDialog(appDef, inputParam, null, GetReturnValue, false);

}

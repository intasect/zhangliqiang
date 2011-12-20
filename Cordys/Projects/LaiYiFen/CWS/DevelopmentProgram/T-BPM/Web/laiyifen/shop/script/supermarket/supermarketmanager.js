var userArray = new Array();
var usercode = null;
/********************************************************************
 * 功能名称	: do_delete();
 * 描述: 删除记录
 * 输入参数：confirmReturnValue
 * 输出参数：无
 */
function do_delete(confirmReturnValue) {
	if (1 == confirmReturnValue) {
		var checkedRows = tabSupermarketTransInfo.getCheckedRows();
    	var isdel = 0;
    	var rowdel = 0;
		for ( var i = 0; i < checkedRows.length; i++) {
				var rowInx = checkedRows[i].sectionRowIndex;
				var status = statusid[rowInx].getValue();
				if(status=="1"){//非草稿状态
					 isdel = 1;
					 rowdel = rowInx;
				}
		}
		if(isdel==1){
			   application.notify("对不起，第" + rowdel + "行的数据为非草稿状态不能删除！");
	           return;  
		}
		userArray = getUserInfo();
		if(userArray){
			usercode = userArray.userCode;
		}
		for ( var i = 0; i < checkedRows.length; i++) {
			var rowInx = checkedRows[i].sectionRowIndex;
			var formid = form_id[rowInx].getValue();
			var newusercode = user_code[rowInx].getValue();
			if(newusercode!=null&&newusercode!=usercode){
				 application.notify("对不起，您没有权限删除第" + rowInx + "行的数据！");
				 isdel = 1;
				 break;
			}
			var request = DeleteSupermarketInfoModel.getMethodRequest();
			cordys.setNodeText(request, ".//*[local-name()='formId']",
					formid);
			DeleteSupermarketInfoModel.setMethodRequest(request);
			DeleteSupermarketInfoModel.reset();
			DeleteSupermarketInfoModel.refreshAllViews();
		}
		if(isdel==1){
			return;
		}else{
		application.inform("数据删除成功");
		toolbarbuttonQuery_Click();
		}
	}
}
/*******************************************************************************
 * 功能名称 : do_delete(); 
 * 描述: 删除记录 
 * 输入参数：无
 * 输出参数：无
 */
function do_supermarket_trans_infotable_ondblclick() {
	if (tabSupermarketTransInfo.getIndex() != -1) {
		var subformid = form_id[tabSupermarketTransInfo.getIndex()].getValue();
		var substatus = statusid[tabSupermarketTransInfo.getIndex()].getValue();
		var subusercode = user_code[tabSupermarketTransInfo.getIndex()].getValue();
		var data = new Object();
		data.subformid = subformid;
		data.status = substatus;
		data.subusercode = subusercode;
		application.showDialog(
		dialogNewSupermarketInfo.XMLDocument.documentElement, data,null, toolbarbuttonQuery_Click, false);
	}
}

/*******************************************************************************
 * 功能名称 : do_toolbarbuttonAdd_Click(); 
 * 描述:新增 
 * 输入参数：eventObject 
 * 输出参数：无
 */
function do_toolbarbuttonAdd_Click(eventObject) {
	application.showDialog(dialogNewSupermarketInfo.XMLDocument.documentElement, null, null,do_toolbarbuttonQuery_Click, false);
}


/*******************************************************************************
 * 功能名称 : do_toolbarbuttonQuery_Click();
 * 描述:双击 
 * 输入参数：eventObject 
 * 输出参数：无
 */
function do_toolbarbuttonQuery_Click(eventObject) {
	var request = GetSupermarektInfoObjectsByParamsModel.getMethodRequest();
	cordys.setNodeText(request, ".//*[local-name()='supermarketname']",supermarketname.getValue());
	cordys.setNodeText(request, ".//*[local-name()='contactperson']",contactperson.getValue());
	cordys.setNodeText(request, ".//*[local-name()='applicatedatebegin']",applicatedatebegin.getValue());
	cordys.setNodeText(request, ".//*[local-name()='applicatedateend']",applicatedateend.getValue());
	GetSupermarektInfoObjectsByParamsModel.setMethodRequest(request);
	GetSupermarektInfoObjectsByParamsModel.reset();
	//GetSupermarektInfoObjectsByParamsModel.refreshAllViews();
   /**     
    var supermarketInfoNodes=GetSupermarektInfoObjectsByParamsModel.getData();
    var supermarektInfoNodeList = cordys.selectXMLNodes(supermarketInfoNodes,".//*[local-name()='tuple']");
	for(var i=0; i < supermarektInfoNodeList.length; i++)
    {
	  if(i==0){
		  GetSupermarektInfoObjectsByParamsModel.clear();
	  }
      var supermarketInfoNode = cordys.selectXMLNode(supermarektInfoNodeList[i], ".");
	  var status=cordys.getNodeText(supermarketInfoNode,".//*[local-name()='STATUS']");

      var statusNameNode = cordys.createElementNS(supermarketInfoNode.ownerDocument,"http://schemas.cordys.com/tbpm/shop","STATUSNAME");
      var text = status==0? "草稿" : "非草稿";
      cordys.setTextContent(statusNameNode,text);
      cordys.appendXMLNode(statusNameNode,cordys.selectXMLNode(supermarketInfoNode,".//*[local-name()='old']/*[local-name()='SUPERMARKET_TRANS_INFO']"));
	  GetSupermarektInfoObjectsByParamsModel.putData(supermarketInfoNode);
	  GetSupermarektInfoObjectsByParamsModel.refreshAllViews();
    }
    */
       
}
/********************************************************************
 * 功能名称	: do_btnexit_Click();
 * 描述: 点击工具栏的退出按钮，调用此方法
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_btnexit_Click(eventObject)
{
    if(Workflow.getProcessInstanceId()!=null)
   {
       //待办中使用下面方式进行关闭页面
       parent.parent.parent.application.container.close();
   }
   else
  {
  		//不在流程中时使用下面方式进行关闭页面
      application.container.close();
  }
}
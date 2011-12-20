var userArray = new Array();
var insertOperation = 0;
var updateDocTemplate = null;
var deleteDocTemplate = null;
var insertDocTemplate = null;
var submitOperation = 0;
var popupEvent = null;
var modelCol_Single = new Array();
var modelCol_Multi = new Array();
var staticObject = null;
var data = new Object();
var validateControls = new Array();

var otherTabSum = 0;
var orderDetailTabSum = 0;
var certifDetailTabSum = 0;

/********************************************************************
* 功能名称：do_CompositeGroupbuyOrderModel_OnResponse
* 描述：请求返回之后重新绑定model里的数据
* 输入参数：eventObject
* 输出参数：无
*/
function do_CompositeGroupbuyOrderModel_OnResponse(eventObject)
{
    var responseNode = eventObject.response;
    if(cordys.selectXMLNode(responseNode,".//*[local-name()='faultcode']"))return;
    if(insertOperation == 1)
    { 
    	var myid = cordys.selectXMLNode(responseNode ,".//*[local-name()='ORDER_ID']");
    	if(myid){
    		 orderId.setValue(cordys.getTextContent(cordys.selectXMLNode(responseNode ,".//*[local-name()='ORDER_ID']")));
    	}else{
    		orderId.setValue(groupbuyorderid.getValue());
    	}
    	 insertOperation=0;
    	 GetGroupbuyOrderDetailByOrderNoModel.synchronize(true);
       	 GetGroupbuyCerfDetailByOrderNoModel.synchronize(true);
       	 GetGroupbuyGiftDetailByOrderNoModel.synchronize(true);
       	 GetGroupbuyOtherByOrderNoModel.synchronize(true);
      	 getCompositeObject();
    	if(submitOperation == 0)
      	{
      		application.inform("保存成功");
      	}
    }
}
/********************************************************************
 * 功能名称	: do_Form_Init();
 * 描述: 初始化表单
 * 输入参数：无
 * 输出参数：无
 */
function  do_Form_Init(eventObject)
{
	 gpxhide.hide();
	 for(var i=1;i<23;i++){ 
		 orderDetailTab.hideColumn(i+9); 
		 giftDetailTab.hideColumn(i+9);
		 certifDetailTab.hideColumn(i+9);
		 otherTab.hideColumn(i+9);
	    }
	 var stepVal = stepcode.getValue();
	 if(stepVal==""||stepVal=="00"){
		 tbnreturn.hide();
	 }
	//用户在其任务完成时,只能查看表单内容,而不能修改表单内容
	var workstatus = Workflow.getMessageStatus();
	if(workstatus == "COMPLETED" || workstatus == "ABORTED" || workstatus == "TERMINATED")
	{
		btsave.disable();
		btnsubmit.disable();
		btnreturn.disable();
	}
	
	if(stepcode.getValue() == "00")
    {
		tbnreturn.hide();	
    }
	else if(Workflow.getProcessInstanceId() != undefined && Workflow.getProcessInstanceId() != "")
		{
			customertype.disable();
			customercode.disable();
			orderorg.disable();
			orderuser.disable();
			orderCity.disable();
			deliveryAddr.disable();
			orderdate.disable();
			phoneno.disable();
			deliverydate.disable();
			deliveryno.disable();
			gpxclientinfo.disable();
			gpxdetail.disable();
		}
	else
	{
		tbnreturn.hide();
	}
	
	if(stepcode.getValue() == "40")
    {
		deliveryno.enable();	
    }
	
    for(var i=1;i<8;i++){ 
        File_RepositoryTable.hideColumn(i+5); 
    }  
    
    //隐藏待办页面缺省的按钮
    if(Workflow.getProcessInstanceId()!=null){
    	Workflow.hideTaskToolbar();
    } 
    gpxuserinfo.disable();
}
/********************************************************************
 * 功能名称	: do_Form_InitDone();
 * 描述: 表单加载完成后查询复合对象并绑定到相应的model
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_Form_InitDone(eventObject)
{
    //WebForm.getLayoutElement(gpxgoodsorder).style.height = "auto";
   // WebForm.getLayoutElement(gpxgoodsorder).className += "autoextent";
    if(Workflow.getProcessInstanceId()!=null&&Workflow.getProcessInstanceId() != undefined ){
    	printProgress(Workflow.getTask());
    }
    
    //放置需要必填项校验的output,select控件
	validateControls["saleorg"] = saleorg;
	validateControls["factoryShipment"] = factoryShipment;
	
	
    userArray=getUserInfo();
    gpxuserinfo.create(); 
    staticObject = getStaticValue();
    
    financialdec.setValue("1");
    
    cordys.addDOMListener(File_RepositoryTable, "ondblclick",
			completeDownloadFile);
    modelCol_Single["GetGroupbuyOrderModel"] = GetGroupbuyOrderModel;
    modelCol_Single["GetApprovalModel"] = GetApprovalModel;
    modelCol_Multi["GetGroupbuyCerfDetailByOrderNoModel"] = GetGroupbuyCerfDetailByOrderNoModel;
    modelCol_Multi["GetGroupbuyGiftDetailByOrderNoModel"] = GetGroupbuyGiftDetailByOrderNoModel;
    modelCol_Multi["GetGroupbuyOrderDetailByOrderNoModel"] = GetGroupbuyOrderDetailByOrderNoModel;
    modelCol_Multi["GetGroupbuyOtherByOrderNoModel"] = GetGroupbuyOtherByOrderNoModel;
    modelCol_Multi["GetAttachmentModel"] = GetAttachmentModel;     
    getCompositeObject();
}

/********************************************************************
 * 功能名称	: getCompositeObject();
 * 描述: 查询复合对象并绑定到相应的model
 * 输入参数：eventObject
 * 输出参数：无
 */
function getCompositeObject()
{					
	 	var getGroupbuyOrderXMLDocument = cordys.cloneXMLDocument(getGroupbuyOrderInfoXML.XMLDocument);
	 	if(application.event)
 		{
	 		var appData =  application.event.data;
 		}
		if(appData && appData.suborderno)
        {
             cordys.setNodeText(getGroupbuyOrderXMLDocument ,".//*[local-name()='ORDER_ID']", application.event.data.suborderid);         
        }else{
        	var myorderid = orderId.getValue();  
            cordys.setNodeText(getGroupbuyOrderXMLDocument ,".//*[local-name()='ORDER_ID']", myorderid);
        }    
		CompositeGroupbuyOrderModel.clear();
		CompositeGroupbuyOrderModel.setMethodRequest(getGroupbuyOrderXMLDocument);
		CompositeGroupbuyOrderModel.reset();
        var compositeData = CompositeGroupbuyOrderModel.getData();
	    bindDataToModels(compositeData);	 
}
/********************************************************************
 * 功能名称	: bindDataToModels();
 * 描述: 将复合对象的响应结构拆分到子对象中，将数据显示到页面
 * 输入参数：responseNode，响应节点
 * 输出参数：无
 */
function bindDataToModels(responseNode)
{ 
	//不是点击提交或驳回按钮时执行create()操作
	    if(submitOperation == 0)
	    { 
	       tabapprovalhistory.create();
	    }
	   //提交或驳回任务时刷新可编辑的审批意见model：GetApprovalModel
	    if(submitOperation == 1||submitOperation == 2)
	     {
	    	 GetApprovalModel.refreshAllViews();		
	     }
        if(cordys.selectXMLNode(responseNode,".//*[local-name()='GROUPBUY_ORDER']"))
        {
            var groupbuyOrderDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
            bindSingleTupleModel(GetGroupbuyOrderModel,responseNode,"GROUPBUY_ORDER",groupbuyOrderDataNode);
        }else{
        	sapordertype.setValue("Z001");
        	productgroup.setValue("00");
        	channel.setValue("03");
        }     
        if(cordys.selectXMLNode(responseNode,".//*[local-name()='GROUPBUY_ORDER_DETAIL']"))
        {
            var groupbuyOrderDetailDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
            bindMultiTupleModel(GetGroupbuyOrderDetailByOrderNoModel,responseNode,"GROUPBUY_ORDER_DETAIL",groupbuyOrderDetailDataNode);
            tableSeq(seq1,orderDetailTab);
        }
        if(cordys.selectXMLNode(responseNode,".//*[local-name()='ATTACHMENT']"))
        {
            var attachmentDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
            bindMultiTupleModel(GetAttachmentModel,responseNode,"ATTACHMENT",attachmentDataNode);
        }
        
       //处理人暂存后再次打开待办时将审批意见显示到可编辑区域
         var approvalNodes = cordys.cloneXMLDocument(responseNode);
          if(cordys.selectXMLNode(approvalNodes,".//*[local-name()='APPROVAL_HISTORY'][*[local-name()='EXT1']!='']"))
         {
             var approvalNodeList = cordys.selectXMLNodes(approvalNodes,".//*[local-name()='APPROVAL_HISTORY'][*[local-name()='EXT1']!='']");
             for(var i=0; i < approvalNodeList.length; i++)
             {
             			var approvalNode = cordys.selectXMLNode(approvalNodeList[i],  ".");
             			if(cordys.getNodeText(approvalNode,".//*[local-name()='EXT1']") == Workflow.getTaskInstanceId()||(Workflow.getTaskInstanceId()==null&&cordys.getNodeText(approvalNode,".//*[local-name()='EXT1']")=="draft"))
             			{
             					var approvalDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
             					cordys.appendXMLNode(approvalNode,cordys.selectXMLNode(approvalDataNode,".//*[local-name()='old']"));
             					pushdataToModel(GetApprovalModel,approvalDataNode);
             			}
             }
         }
        if(cordys.selectXMLNode(responseNode,".//*[local-name()='APPROVAL_HISTORY']"))
        {
            var approvalHistoryDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);         
            bindMultiTupleModel(GetApprovalHistoryModel,responseNode,"APPROVAL_HISTORY",approvalHistoryDataNode);
        }     
}

/********************************************************************
 * 功能名称	: do_tbnsave_Click();
 * 描述: 点击工具栏的保存按钮，触发此方法
 * 输入参数：eventObject
 * 输出参数：无
 */

function do_tbnsave_Click(eventObject)
{
   var flag = mycheck();
   if(!flag){
   	return false;
   }
   showConfirm(false,closeHandler);
}
/********************************************************************
 * 功能名称	: closeHandler();
 * 描述: 点击保存确认框的按钮时所触发的回调函数
 * 输入参数：confirmReturnValue，确认框的返回值：1--是；0--否
 * 输出参数：无
 */
function closeHandler(confirmReturnValue)
{
    if (confirmReturnValue == 1)
    {
    	saveForm();
    }
    else if (confirmReturnValue == 0)
    {
    }
}
/********************************************************************
 * 功能名称	: saveForm();
 * 描述: 发送更新的复合对象请求，进行保存操作
 * 输入参数：无
 * 输出参数：无
 */
function saveForm()
{
  insertOperation = 1;
  saveCompositeObject(CompositeGroupbuyOrderModel,modelCol_Single,modelCol_Multi);
  if(CompositeGroupbuyOrderModel.soapFaultOccurred) 
  { 
      return; 
  }
}

/********************************************************************
 * 功能名称	: fillBusiValueOnModels();
 * 描述: 保存前，对业务相关model进行单独赋值
 * 输入参数：modelName,modelDataDoc,modelBObjName,tupleNode
 * 输出参数：无
 */
function fillBusiValueOnModels(modelName,modelDataDoc,modelBObjName,tupleNode)
{
	 switch(modelName)
     {
         case "GetApprovalModel":
             fillValuesOnGetApprovalModel(modelDataDoc,modelBObjName,tupleNode);
         break;
         case "GetGroupbuyOrderModel":
        	 fillValuesInGetGroupbuyOrderObjectModel(modelDataDoc ,modelBObjName,tupleNode);
         break;
     }
}

/********************************************************************
 * 功能名称	: fnAddFile();
 * 描述: 将附件内容从弹出页中带到主页面
 * 输入参数：fileObject,fileUrl和fileName
 * 输出参数：无
 */
function fnAddFile(fileObject,fileUrl,fileName)
{
	 if(fileName != null && fileName !=""){
	    File_RepositoryTable.create();
	    var rowInx = File_RepositoryTable.getIndex();
	    if(rowInx==-1)rowInx=0;
	    filename[rowInx].setValue(fileName); 
	    filetype[rowInx].setValue(cordys.getNodeText(fileObject,".//*[local-name()='FileType']"));
	    directory[rowInx].setValue(fileUrl);
	    deptCd[rowInx].setValue(userArray.deptCode);
	    deptNm[rowInx].setValue(userArray.deptName);
	   if(Workflow.getProcessInstanceId()!=null){
  		   instanceId[rowInx].setValue(Workflow.getProcessInstanceId());
  	    }else{
  	       instanceId[rowInx].setValue("sd12323-kkl911-7913");
  	    }
	    formId[rowInx].setValue(groupbuyorderid.getValue());
	    userId[rowInx].setValue(userArray.userCode);
	    userNm[rowInx].setValue(userArray.userName);
	    submitstatus[rowInx].setValue("0");  
     }
}

/********************************************************************
 * 功能名称	: completeDownloadFile();
 * 描述: 点击table的某行，触发下载附件操作
 * 输入参数：无
 * 输出参数：无
 */

function completeDownloadFile() {
	var rowInx = File_RepositoryTable.getIndex();
	var urlNode = directory[rowInx].getValue();
	var request = DownloadFileModel.getMethodRequest();
	cordys.setNodeText(request, ".//*[local-name()='id']", urlNode);
        DownloadFileModel.setMethodRequest(request);
	DownloadFileModel.reset();
	var filePath = cordys.getNodeText(DownloadFileModel.getData(),
			".//*[local-name()='downloadFile']");
	if (filePath == "" || filePath == undefined) {
		application.notify("文件找不到,请跟管理员联系.");
		return;
	}
	filePath=staticObject.downloadUrl+filePath;
	window.open(filePath);
}

/********************************************************************
 * 功能名称	: do_btnsubmit_Click();
 * 描述: 点击工具栏的提交按钮，触发此操作
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_btnsubmit_Click(eventObject)
{
	 if(!WebForm.validateForm(application.container.applicationId))
	 return;
	 if(!validateControl(validateControls)) return;
	 var flag = mycheck();
	   if(!flag){
	   	return false;
	   }
    popupEvent = eventObject;
    showConfirm(false,submitHandler,"您确定提交任务到下一环节吗？");
}

/********************************************************************
 * 功能名称	: submitHandler();
 * 描述: 点击提交确认框的按钮时所触发的回调函数
 * 输入参数：confirmReturnValue，确认框的返回值：1--是；0--否
 * 输出参数：无
 */
function submitHandler(confirmReturnValue)
{
	var stepVal = stepcode.getValue();
    if (confirmReturnValue == 1)
    {
        changeStatusForAttachments(); 
    	submitOperation = 1;
    	iptstatus.setValue("1");//流程中
    	decisionattribute.setValue("01");	  	
        submitTask();
    }
    else if (confirmReturnValue == 0)
    {
    }
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


/********************************************************************
 * 功能名称	: submitTask();
 * 描述: 提交任务
 * 输入参数：无
 * 输出参数：无
 */
function submitTask()
{
	var processInstanceId = Workflow.getProcessInstanceId();
	saveForm();
   if(processInstanceId==undefined || processInstanceId=="")
   {
       var request = cordys.cloneXMLDocument(executeProcess.XMLDocument);
       cordys.setNodeText(request,".//*[local-name()='receiver']","Business Process Models/com/laiyifen/goodsorder/商品订购流程");   
       cordys.setNodeText(request,".//*[local-name()='orderId']",groupbuyorderid.getValue() );
       cordys.setNodeText(request,".//*[local-name()='DecisionAttribute']","01");
       cordys.setNodeText(request,".//*[local-name()='Ext2']",curdiscountrate.getValue());
       cordys.setNodeText(request,".//*[local-name()='CreatorDN']",getCurrentUserDN());
       cordys.setNodeText(request,".//*[local-name()='CreateName']",username.getValue());
       cordys.setNodeText(request,".//*[local-name()='CreateTime']",applicationdate.getValue()); 
       cordys.setNodeText(request,".//*[local-name()='CompanyCode']",userArray.companyCode);
       cordys.setNodeText(request,".//*[local-name()='CompanyName']",userArray.companyName);
       cordys.setNodeText(request,".//*[local-name()='CreatorDeptDN']",deparementcode.getValue());
       cordys.setNodeText(request,".//*[local-name()='ProcessCode']","KP03.03.01.01");
       cordys.setNodeText(request,".//*[local-name()='ProcessName']","商品订单流程");
       cordys.setNodeText(request,".//*[local-name()='DefaultDuration']",staticObject.defaultDuration);
       ExecuteProcessModel.setMethodRequest(request);
       ExecuteProcessModel.reset();
       application.container.close();
    }
    else
    { 
       Workflow.completeTask();
    }
}
/********************************************************************
 * 功能名称	: do_tbnreturn_Click();
 * 描述: 点击工具栏的驳回按钮，触发此操作
 * 输入参数：eventObject
 * 输出参数：无
 */
 function do_tbnreturn_Click(eventObject)
{
	 if(!WebForm.validateForm(application.container.applicationId))
		 return;
	 var flag = mycheck();
	   if(!flag){
	   	return false;
	   }
	   application.showDialog(dialogRevert.XMLDocument.documentElement,null, null, GetRevertValue, false);
	   placePopup(popupEvent);
}
 /********************************************************************
  * 功能名称	: returnHandler();
  * 描述: 点击驳回确认框的按钮时所触发的回调函数
  * 输入参数：confirmReturnValue，确认框的返回值：1--是；0--否
  * 输出参数：无
  */
function GetRevertValue(rtnValue)
{
    if(rtnValue != "" && rtnValue != null)
    {
    	decisionattribute.setValue(rtnValue);
    	submitOperation = 2; 
		changeStatusForAttachments();
		//点击驳回按钮时，记录当前驳回人
		var curUserCd = getCurrentUserDN().split(",")[0].substring(3);
		revertuser.setValue(curUserCd);
    	saveForm();
		if(CompositeGroupbuyOrderModel.soapFaultOccurred) 
        { 
            return; 
        }
		submitOperation = 0;
        Workflow.completeTask();
    }
    else
    {
        application.notify("请选择驳回位置");
    }
}

/***********************************
*  功能名称：do_CompositeGroupbuyOrderModel_OnSOAPFault();
*  功能描述：SOAP请求失败时消息提示
*  输入参数：eventObject
*  输出参数：无
*/
function do_CompositeGroupbuyOrderModel_OnSOAPFault(eventObject)
{
	insertOperation = 0;
	eventObject.showError = false;
	application.notify("您的操作未能成功执行，请您重新尝试或联系系统管理员，谢谢");
}

/********************************************************************
 * 功能名称	: do_navDelete2_BeforeDelete();
 * 描述: 删除附件记录
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_navDelete2_BeforeDelete(eventObject) {
	var rows = File_RepositoryTable.getCheckedRows();
	if(rows.length==0)
	{
	           application.notify("请选择所需删除的附件，谢谢");
	           return;     
	}
	showConfirm(false,removeHandler,"您确定删除所选附件吗？");
}
/********************************************************************
 * 功能名称	: validateAttachment();
 * 描述: 判断此附件是否可以被删除
 * 输入参数：rowInx
 * 输出参数：boolean
 */
function validateAttachment(rowInx)
{
	if(submitstatus[rowInx].getValue()=="1")
	{
		application.notify("对不起，您没有权限删除第" + rowInx + "行附件");
		return false;
	}
	return true;
}
/********************************************************************
 * 功能名称	: removeHandler();
 * 描述: 点击附件删除按钮时所触发的回调函数
 * 输入参数：confirmReturnValue，确认框的返回值：1--是；0--否
 * 输出参数：无
 */
function removeHandler(confirmReturnValue)
{
	if(confirmReturnValue == 1)
	{
		var rows=File_RepositoryTable.getCheckedRows();
		for(var i = 0;i < rows.length;i++)
		{
			//在文件系统中删除文件
			var rowInx = rows[i].sectionRowIndex;
			//判断是否有权限删除此附件
			if(!validateAttachment(rowInx))	return;
			var guid = directory[rowInx].getValue();
			var request = DeleteFileModel.getMethodRequest();
			cordys.setNodeText(request,".//*[local-name()='guid']",guid);
			DeleteFileModel.setMethodRequest(request);
			DeleteFileModel.reset();
			if(DeleteFileModel.soapFaultOccurred) 
			{ 
				application.notify("您的操作未能成功执行，请您重新尝试或联系系统管理员，谢谢");
				return; 
			}
		}
		removeDataFromModel();
	}
}
/********************************************************************
 * 功能名称	: removeDataFromModel();
 * 描述: 在页面上去除删除数据
 * 输入参数：eventObject
 * 输出参数：无
 */
function removeDataFromModel() {
	var newNodesDoc = cordys.loadXMLDocument("<collection/>");
	var modelData = GetAttachmentModel.getData();
	var chekedRows = File_RepositoryTable.getCheckedRows();
	for ( var i = 0; i < chekedRows.length; i++) {
		var rowBObjId = cordys.getNodeText(chekedRows[i].businessObject,
				".//*[local-name()='FILEURL']");
		var selectedRowTuple = cordys.selectXMLNode(modelData,
				".//*[local-name()='tuple'][.//*[local-name()='FILEURL']='"
						+ rowBObjId + "']");
		selectedRowTuple.parentNode.removeChild(selectedRowTuple);
	}
	var newNodes = cordys.selectXMLNodes(modelData, ".//*[local-name()='new']");
	for ( var i = 0; i < newNodes.length; i++) {
		var nde = newNodes[i].cloneNode(true);
		cordys.appendXMLNode(nde, newNodesDoc.documentElement);
	}
	GetAttachmentModel.refreshAllViews();
	rebindNewNodes(newNodesDoc);
}
/********************************************************************
 * 功能名称	: rebindNewNodes();
 * 描述: 将未删除的new tuple数据重新绑定到model
 * 输入参数：newNodesDoc
 * 输出参数：无
 */
function rebindNewNodes(newNodesDoc) {
	var newNodes = newNodesDoc.getElementsByTagName("new");
	for ( var i = 0; i < newNodes.length; i++) {
		//var rows = File_RepositoryTable.getRows();
		File_RepositoryTable.create();
		var numRows = File_RepositoryTable.getIndex();
		filename[numRows].setValue(cordys.getNodeText(newNodes[i],
				".//*[local-name()='FILENAME']"));
		filetype[numRows].setValue(cordys.getNodeText(newNodes[i],
				".//*[local-name()='FILETYPE']"));
		deptNm[numRows].setValue(cordys.getNodeText(newNodes[i],
				".//*[local-name()='DEPTMENT_NAME']"));
		userNm[numRows].setValue(cordys.getNodeText(newNodes[i],
				".//*[local-name()='OPERATOR_NAME']"));
		directory[numRows].setValue(cordys.getNodeText(newNodes[i],
				".//*[local-name()='FILEURL']"));
		formId[numRows].setValue(cordys.getNodeText(newNodes[i],
				".//*[local-name()='FORM_ID']"));
		instanceId[numRows].setValue(cordys.getNodeText(newNodes[i],
				".//*[local-name()='INSTANCE_ID']"));
	}
application.inform("附件删除成功");
}
/********************************************************************
 * 功能名称	: changeStatusForAttachments();
 * 描述: 点击提交或驳回按钮时将当前人上传的附件状态置为1，即不允许删除
 * 输入参数：无
 * 输出参数：无
 */
function changeStatusForAttachments()
{
	var rows = File_RepositoryTable.getRows();
	for(var i=0; i<rows.length;i++)
	{
           if(submitstatus[i+1].getValue()=="0")
           {
                    submitstatus[i+1].setValue("1");
                    continue;
           }
	}
}
/********************************************************************
 * 功能名称	: do_appresult_Change();
 * 描述: 审批意见中常用语变化时将常用语赋值到审批意见textarea中
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_appresult_Change(eventObject)
{
    if(appresult.getValue()=="")
    {
        return;
    }
    var oldValue = approvalremark.getFormattedValue();
    if(oldValue.search("\n")!=-1)
    {
        oldValue = oldValue.substring(oldValue.search("\n")+1);
    }   
    approvalremark.setValue(appresult.getValue()+"\n"+oldValue);
}



/******************************************************************** 
 * 功能名称	: fillValuesOnGetApprovalModel();
 * 描述: 为审批意见model增加必须节点
 * 输入参数：modelData modelBObjName tupleNode
 * 输出参数：无
 */
function fillValuesOnGetApprovalModel(modelData ,modelBObjName,tupleNode)
{ 
	var processInstanceId = Workflow.getTaskInstanceId();
    //点击保存按钮时，添加ext1标志位，用于区分打开待办时审批意见是update还是insert
    if(submitOperation == 0)
    {
          var ext1Node = cordys.createElementNS(modelData,"http://schemas.cordys.com/tbpm/goods","EXT1");
          if(processInstanceId){ 
             cordys.setTextContent(ext1Node, Workflow.getTaskInstanceId());
          }else{
         	 cordys.setTextContent(ext1Node, "draft");
          }
          cordys.appendXMLNode(ext1Node, cordys.selectXMLNode(tupleNode,".//*[local-name()='" + modelBObjName + "']"));
    }
	if(processInstanceId){
		    var instanceNode = cordys.createElementNS(modelData,"http://schemas.cordys.com/tbpm/goods","INSTANCE_ID");
		    var formIdNode = cordys.createElementNS(modelData,"http://schemas.cordys.com/tbpm/goods","FORM_ID");
		    var stepCdNode = cordys.createElementNS(modelData,"http://schemas.cordys.com/tbpm/goods","STEP_CODE");
		    var stepNmNode = cordys.createElementNS(modelData,"http://schemas.cordys.com/tbpm/goods","STEP_NAME");
		    var chargerCdNode = cordys.createElementNS(modelData,"http://schemas.cordys.com/tbpm/goods","CHARGER_CODE");
		    
		    cordys.setTextContent(instanceNode,processInstanceId);
		    cordys.setTextContent(formIdNode, groupbuyorderid.getValue());
		    cordys.setTextContent(stepCdNode, stepcode.getValue());
		    cordys.setTextContent(stepNmNode, stepname.getValue());
		    cordys.setTextContent(chargerCdNode, chargercode.getValue());
		    
		    var insertParentNewNode = cordys.selectXMLNode(tupleNode,".//*[local-name()='new']"); 
		    cordys.appendXMLNode(instanceNode, cordys.selectXMLNode(insertParentNewNode,".//*[local-name()='" + modelBObjName + "']"));
		    cordys.appendXMLNode(formIdNode, cordys.selectXMLNode(insertParentNewNode,".//*[local-name()='" + modelBObjName + "']"));
		    cordys.appendXMLNode(stepCdNode, cordys.selectXMLNode(insertParentNewNode,".//*[local-name()='" + modelBObjName + "']"));
		    cordys.appendXMLNode(stepNmNode, cordys.selectXMLNode(insertParentNewNode,".//*[local-name()='" + modelBObjName + "']"));
		    cordys.appendXMLNode(chargerCdNode, cordys.selectXMLNode(insertParentNewNode,".//*[local-name()='" + modelBObjName + "']"));
	   }else{
		   var stepCdNode = cordys.createElementNS(modelData,"http://schemas.cordys.com/tbpm/goods","STEP_CODE");
		    var stepNmNode = cordys.createElementNS(modelData,"http://schemas.cordys.com/tbpm/goods","STEP_NAME");
		    var chargerCdNode = cordys.createElementNS(modelData,"http://schemas.cordys.com/tbpm/goods","CHARGER_CODE");
	       
	        cordys.setTextContent(stepCdNode, "00");
	        cordys.setTextContent(stepNmNode, "填写商品订购表单");
	        cordys.setTextContent(chargerCdNode,usercode.getValue());  
	        
	        var insertParentNewNode = cordys.selectXMLNode(tupleNode,".//*[local-name()='new']"); 
	        cordys.appendXMLNode(stepCdNode, cordys.selectXMLNode(insertParentNewNode,".//*[local-name()='" + modelBObjName + "']"));
	        cordys.appendXMLNode(stepNmNode, cordys.selectXMLNode(insertParentNewNode,".//*[local-name()='" + modelBObjName + "']"));
	        cordys.appendXMLNode(chargerCdNode, cordys.selectXMLNode(insertParentNewNode,".//*[local-name()='" + modelBObjName + "']"));
    }

}

/********************************************************************
 * 功能名称	: fillValuesInGetGroupbuyOrderObjectModel();
 * 功能描述:  为 model增加状态位节点
 * 输入参数：modelData modelBObjName tupleNode
 * 输出参数：无
 */
function fillValuesInGetGroupbuyOrderObjectModel(modelData ,modelBObjName,tupleNode)
{  
    var statusNode = cordys.selectXMLNode(tupleNode,".//*[local-name()='" + modelBObjName + "']/*[local-name()='STATUS']");
    cordys.setXMLAttribute(statusNode,"","null","false");
    var processInstanceId = Workflow.getProcessInstanceId();
    var stepVal = stepcode.getValue();
    if((submitOperation!=1)&&(processInstanceId==undefined || processInstanceId==""))
    {     
        cordys.setTextContent(statusNode, "0");    //status=0
    }
    else
    {
    		 cordys.setTextContent(statusNode, "1");    //status=1       审批中
    }
}

/********************************************************************
 * 功能名称	: do_Form_BeforeClose();
 * 功能描述:  页面关闭前释放全局变量
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_Form_BeforeClose(eventObject)
{
	if(submitOperation == 1 || submitOperation == 2)
	{
		CordysRoot.application.inform("操作已成功，请继续其他操作");
	}
	userArray = null;
	insertOperation = 0;
	updateDocTemplate = null;
	deleteDocTemplate = null;
	insertDocTemplate = null;
	submitOperation = 0;
	popupEvent = null;
	modelCol_Single = null;
	modelCol_Multi = null;
}

/***********************************
*  功能名称：do_ExecuteProcessModel_OnSOAPFault();
*  功能描述：SOAP请求失败时消息提示
*  输入参数：eventObject
*  输出参数：无
*/
function do_ExecuteProcessModel_OnSOAPFault(eventObject)
{
	insertOperation = 0;
	eventObject.showError = false;
	application.notify("您的操作未能成功执行，请您重新尝试或联系系统管理员，谢谢");
}

function mycheck(){
	var flag = true;	
	var step = stepcode.getValue();
	if(Workflow.getProcessInstanceId()!=null){
		var approvalcontent = approvalremark.getValue();
		if((approvalcontent==null||approvalcontent=="")&&step!="00"){
			flag = false;
			application.notify("审批意见不能为空");
			approvalremark.setFocus();
			return flag;
		}
		
	}
	return flag;
}

/***********************************
*  功能名称：bigRMB();
*  功能描述：数字大写
*  输入参数：文本ID
*  输出参数：无
*/
function bigRMB(currencyDigits) {  
	// Constants:
	var MAXIMUM_NUMBER = 99999999999.99;
	// Predefine the radix characters and currency symbols for output:
	var CN_ZERO = "零";
	var CN_ONE = "壹";
	var CN_TWO = "贰";
	var CN_THREE = "叁";
	var CN_FOUR = "肆";
	var CN_FIVE = "伍";
	var CN_SIX = "陆";
	var CN_SEVEN = "柒";
	var CN_EIGHT = "捌";
	var CN_NINE = "玖";
	var CN_TEN = "拾";
	var CN_HUNDRED = "佰";
	var CN_THOUSAND = "仟";
	var CN_TEN_THOUSAND = "万";
	var CN_HUNDRED_MILLION = "亿";
	var CN_SYMBOL = "";
	//var CN_SYMBOL = "人民币";
	var CN_DOLLAR = "元";
	var CN_TEN_CENT = "角";
	var CN_CENT = "分";
	var CN_INTEGER = "整";

	// Variables:
	var integral; // Represent integral part of digit number.
	var decimal; // Represent decimal part of digit number.
	var outputCharacters; // The output result.
	var parts;
	var digits, radices, bigRadices, decimals;
	var zeroCount;
	var i, p, d;
	var quotient, modulus;
	
	// Validate input string:
	currencyDigits = currencyDigits.toString();
	if (currencyDigits == "") {
		alert("Empty input!");
		return "";
	}
	if (currencyDigits.match(/[^,.\d]/) != null) {
		alert("Invalid characters in the input string!");
		return "";
	}
	if ((currencyDigits).match(/^((\d{1,3}(,\d{3})*(.((\d{3},)*\d{1,3}))?)|(\d+(.\d+)?))$/) == null) {
		alert("Illegal format of digit number!");
		return "";
	}
	
	// Normalize the format of input digits:
	currencyDigits = currencyDigits.replace(/,/g, ""); // Remove comma delimiters.
	currencyDigits = currencyDigits.replace(/^0+/, ""); // Trim zeros at the beginning.
	// Assert the number is not greater than the maximum number.
	if (Number(currencyDigits) > MAXIMUM_NUMBER) {
		alert("Too large a number to convert!");
		return "";
	}
	
	// Process the coversion from currency digits to characters:
	// Separate integral and decimal parts before processing coversion:
	parts = currencyDigits.split(".");
	if (parts.length > 1) {
		integral = parts[0];
		decimal = parts[1];
	// Cut down redundant decimal digits that are after the second.
		decimal = decimal.substr(0, 2);
	}
	else {
		integral = parts[0];
		decimal = "";
	}
	// Prepare the characters corresponding to the digits:
	digits = new Array(CN_ZERO, CN_ONE, CN_TWO, CN_THREE, CN_FOUR, CN_FIVE, CN_SIX, CN_SEVEN, CN_EIGHT, CN_NINE);
	radices = new Array("", CN_TEN, CN_HUNDRED, CN_THOUSAND);
	bigRadices = new Array("", CN_TEN_THOUSAND, CN_HUNDRED_MILLION);
	decimals = new Array(CN_TEN_CENT, CN_CENT);
	// Start processing:
	outputCharacters = "";
	// Process integral part if it is larger than 0:
	if (Number(integral) > 0) {
		zeroCount = 0;
	for (i = 0; i < integral.length; i++) {
		p = integral.length - i - 1;
		d = integral.substr(i, 1);
		quotient = p / 4;
		modulus = p % 4;
	if (d == "0") {
		zeroCount++;
	}
	else {
	if (zeroCount > 0)
	{
		outputCharacters += digits[0];
	}
		zeroCount = 0;
		outputCharacters += digits[Number(d)] + radices[modulus];
	}
	if (modulus == 0 && zeroCount < 4) {
		outputCharacters += bigRadices[quotient];
	}
	}
		outputCharacters += CN_DOLLAR;
	}	
	// Process decimal part if there is:
	if (decimal != "") {
	for (i = 0; i < decimal.length; i++) {
		d = decimal.substr(i, 1);
	if (d != "0") {
		outputCharacters += digits[Number(d)] + decimals[i];
	}
	}
	}
	// Confirm and return the final output string:
	if (outputCharacters == "") {
		outputCharacters = CN_ZERO + CN_DOLLAR;
	}
	if (decimal == "") {
		outputCharacters += CN_INTEGER;
	}
	outputCharacters = CN_SYMBOL + outputCharacters;
	return outputCharacters;
}

function changeCaps(obj){
	var caps = obj.getValue();
	if(caps!=null&&caps!=""){
		caps = bigRMB(caps);
		totalamountcapital.setValue(caps); 
	}else{
		totalamountcapital.setValue("");
	}
}

function do_materialAdd_Click(eventObject){
	
	showDialogMaterialPrice("1",GetGroupbuyOrderDetailByOrderNoModel);
}

function do_giftAdd_Click(eventObject){
	
	showDialogMaterialPrice("2",GetGroupbuyGiftDetailByOrderNoModel);
}

function do_certifAdd_Click(eventObject){
	
	showDialogMaterialPrice("3",GetGroupbuyCerfDetailByOrderNoModel);
}

function do_otherAdd_Click(eventObject){
	
	showDialogMaterialPrice("4",GetGroupbuyOtherByOrderNoModel);
}

function showDialogMaterialPrice(producttype,datamode)
{
	if(!validateControl(validateControls)) return;
	
	GetGroupbuyOrderDetailByOrderNoModel.synchronize(true);
	GetGroupbuyCerfDetailByOrderNoModel.synchronize(true);
	GetGroupbuyGiftDetailByOrderNoModel.synchronize(true);
	GetGroupbuyOtherByOrderNoModel.synchronize(true);
  	 
    data.suborderid =  groupbuyorderid.getValue();
    data.subsaleorgcode =  saleorgcode.getValue();
    data.subfactoryshipment = factoryShipment.getValue();
    data.subproducttype = producttype;
    
    if (producttype == "3")
    {
    	data.subproductcode = "80800";
    }
    else
	{
    	data.subproductcode = null;
	}
    
    data.modedata = datamode;
    application.showDialog(dialogGroupbuyMaterialPrice.XMLDocument.documentElement,data, null, getGroupbuyOrderValue, false);
}

function showDialogCustomer()
{
	var data_t = new Object();
    application.showDialog(dialogGroupbuyCustomer.XMLDocument.documentElement,data_t, null, getGroupbuyCustomer, false);
}

function getGroupbuyOrderValue()
{
	getTableObject(data.subproducttype,data.modedata);
}

function getGroupbuyCustomer(dialogReturnValue)
{
	customercode.setValue(dialogReturnValue.subcustomercode);
	orderorg.setValue(dialogReturnValue.subcustomername);
	deliveryAddr.setValue(dialogReturnValue.subcustomeraddr);
}

function do_standard2_Change(eventObject)
{
	var typeNum = "2";
	weightSum(typeNum);
	amountSum(typeNum);
	totalSum(typeNum);
	old_totalamountlowerSum();
	totalamountlowerSum();
}

function do_quantity2_Change(eventObject)
{
	var typeNum = "2";
	weightSum(typeNum);
	totalSum(typeNum);
	old_totalamountlowerSum();
	totalamountlowerSum();
}

function do_unit_price2_Change(eventObject)
{
	var typeNum = "2";
	amountSum(typeNum);
	totalSum(typeNum);
	old_totalamountlowerSum();
	totalamountlowerSum();
}

function do_standard1_Change(eventObject)
{
	var typeNum = "1";
	weightSum(typeNum);
	amountSum(typeNum);
	totalSum(typeNum);
}

function do_quantity1_Change(eventObject)
{
	var typeNum = "1";
	weightSum(typeNum);
	totalSum(typeNum);
}

function do_unit_price1_Change(eventObject)
{
	var typeNum = "1";
	amountSum(typeNum);
	totalSum(typeNum);
}

function do_standard3_Change(eventObject)
{
	var typeNum = "3";
	//weightSum(typeNum);
	amountSum(typeNum);
	totalSum(typeNum);
	certifDetailTabSumFunction();
	totalamountlowerSum();
}

function do_unit_price3_Change(eventObject)
{
	var typeNum = "3";
	amountSum(typeNum);
	totalSum(typeNum);
	certifDetailTabSumFunction();
	totalamountlowerSum();
}

function do_quantity3_Change(eventObject)
{
	var typeNum = "3";
	//weightSum(typeNum);
	totalSum(typeNum);
	certifDetailTabSumFunction();
	totalamountlowerSum();
}

function do_standard4_Change(eventObject)
{
	var typeNum = "4";
	weightSum(typeNum);
	amountSum(typeNum);
	totalSum(typeNum);
	old_totalamountlowerSum();
	totalamountlowerSum();
}

function do_unit_price4_Change(eventObject)
{
	var typeNum = "4";
	amountSum(typeNum);
	totalSum(typeNum);
	old_totalamountlowerSum();
	totalamountlowerSum();
}

function do_quantity4_Change(eventObject)
{
	var typeNum = "4";
	weightSum(typeNum);
	totalSum(typeNum);
	old_totalamountlowerSum();
	totalamountlowerSum();
}

function do_olddiscountrate_Change(eventObject)
{
	totalamountlowerSum();
}

function weightSum(typeNum)
{
	 switch(typeNum)
	 {
	 	case "2":
	 		weight2[orderDetailTab.getIndex()].setValue(standard2[orderDetailTab.getIndex()].getValue()*quantity2[orderDetailTab.getIndex()].getValue());
	 		break;
	 	case "1":
	 		weight1[giftDetailTab.getIndex()].setValue(standard1[giftDetailTab.getIndex()].getValue()*quantity1[giftDetailTab.getIndex()].getValue());
	 		break;
	 	case "3":
	 		weight3[certifDetailTab.getIndex()].setValue(standard3[certifDetailTab.getIndex()].getValue()*quantity3[certifDetailTab.getIndex()].getValue());
	 		break;
	 	case "4":
	 		weight4[otherTab.getIndex()].setValue(standard4[otherTab.getIndex()].getValue()*quantity4[otherTab.getIndex()].getValue());
	 		break;
	 }
	
}

function amountSum(typeNum)
{
	 switch(typeNum)
	 {
	 	case "2":
	 		amount2[orderDetailTab.getIndex()].setValue(standard2[orderDetailTab.getIndex()].getValue()*unit_price2[orderDetailTab.getIndex()].getValue());
	 		break;
	 	case "1":
	 		amount1[giftDetailTab.getIndex()].setValue(standard1[giftDetailTab.getIndex()].getValue()*unit_price1[giftDetailTab.getIndex()].getValue());
	 		break;
	 	case "3":
	 		amount3[certifDetailTab.getIndex()].setValue(standard3[certifDetailTab.getIndex()].getValue()*unit_price3[certifDetailTab.getIndex()].getValue());
	 		break;
	 	case "4":
	 		amount4[otherTab.getIndex()].setValue(standard4[otherTab.getIndex()].getValue()*unit_price4[otherTab.getIndex()].getValue());
	 		break;
	 }
}

function totalSum(typeNum)
{
	switch(typeNum)
	 {
	 	case "2":
	 		total2[orderDetailTab.getIndex()].setValue(unit_price2[orderDetailTab.getIndex()].getValue()*weight2[orderDetailTab.getIndex()].getValue());
	 		break;
	 	case "1":
	 		total1[giftDetailTab.getIndex()].setValue(unit_price1[giftDetailTab.getIndex()].getValue()*weight1[giftDetailTab.getIndex()].getValue());
	 		break;
	 	case "3":
	 		total3[certifDetailTab.getIndex()].setValue(amount3[certifDetailTab.getIndex()].getValue()*quantity3[certifDetailTab.getIndex()].getValue());
	 		break;
	 	case "4":
	 		total4[otherTab.getIndex()].setValue(unit_price4[otherTab.getIndex()].getValue()*weight4[otherTab.getIndex()].getValue());
	 		break;
	 }
	
}

function totalamountlowerSum()
{
	totalamountlower.setValue(orderDetailTabSum*olddiscountrate.getValue()/10+otherTabSum);
	curdiscountrate.setValue(Math.round(totalamountlower.getValue()/(orderDetailTabSum+otherTabSum+certifDetailTabSum)*10*100)/100);
}
function old_totalamountlowerSum()
{
	 var allRows = orderDetailTab.getRows();
	 var sum = 0;
	 if (allRows.length == 1)
     {
		 sum = parseFloat(total2[allRows[0].index].getValue());
     }
	 else
	 {
		 for (var i = 0; i < allRows.length; i++)
	     {
			 if (total2[allRows[i].index].getValue() != "")
			 {
				 sum += parseFloat(total2[allRows[i].index].getValue());
			 }
			 
	     }
	 }
	 orderDetailTabSum = sum;
	 
	 allRows = otherTab.getRows();
	 if (allRows.length == 1)
     {
		 old_totalamountlower.setValue(parseFloat(total4[allRows[0].index].getValue())+sum);
		 otherTabSum = parseFloat(total4[allRows[0].index].getValue());
     }
	 else
	 {
		 for (var i = 0; i < allRows.length; i++)
	     {
			 if (total4[allRows[i].index].getValue() != "")
			 {
				 sum += parseFloat(total4[allRows[i].index].getValue());
				 
			 }
	     }
		 otherTabSum = sum - orderDetailTabSum;
		 old_totalamountlower.setValue(sum);
	 }
}

function certifDetailTabSumFunction()
{
	 var allRows = certifDetailTab.getRows();
	 if (allRows.length == 1)
     {
		 certifDetailTabSum = parseFloat(total3[allRows[0].index].getValue());
     }
}


function do_tabgroup1_ontabfocus(eventObject)
{
	//商品 
	if(eventObject.tabId=="goodstabpage"){
		var tupleNodes = cordys.selectXMLNodes(GetGroupbuyOrderDetailByOrderNoModel.getData(), ".//*[local-name()='tuple']");
		for(var i = 0;i < tupleNodes.length; i++){
			var tupleNode = cordys.selectXMLNode(tupleNodes[i],".");
			var bUpdateable = isModelUpdatable(tupleNode);
			if(bUpdateable) return;
		} 
		getTableObject("1",GetGroupbuyOrderDetailByOrderNoModel);
	} 

	//礼券 
	if(eventObject.tabId=="certftabpage"){ 
		var tupleNodes = cordys.selectXMLNodes(GetGroupbuyCerfDetailByOrderNoModel.getData(), ".//*[local-name()='tuple']"); 
		for(var i = 0;i < tupleNodes.length; i++) { var tupleNode = cordys.selectXMLNode(tupleNodes[i],"."); 
			var bUpdateable = isModelUpdatable(tupleNode); 
			if(bUpdateable) return; 
		}
		getTableObject("3",GetGroupbuyCerfDetailByOrderNoModel);
	} 
	//赠品 
	if(eventObject.tabId=="gifttabpage"){ 
		var tupleNodes = cordys.selectXMLNodes(GetGroupbuyGiftDetailByOrderNoModel.getData(), ".//*[local-name()='tuple']"); 
		for(var i = 0;i < tupleNodes.length; i++) { 
			var tupleNode = cordys.selectXMLNode(tupleNodes[i],"."); 
			var bUpdateable = isModelUpdatable(tupleNode); 
			if(bUpdateable) return; 
		}
		getTableObject("2",GetGroupbuyGiftDetailByOrderNoModel);
	} 
	//其他 
	if(eventObject.tabId=="othertabpage"){ 
		var tupleNodes = cordys.selectXMLNodes(GetGroupbuyOtherByOrderNoModel.getData(), ".//*[local-name()='tuple']"); 
		for(var i = 0;i < tupleNodes.length; i++) { 
			var tupleNode = cordys.selectXMLNode(tupleNodes[i],".");
			var bUpdateable = isModelUpdatable(tupleNode); 
			if(bUpdateable) return; 
		}
		getTableObject("4",GetGroupbuyOtherByOrderNoModel);
	} 
}
function getTableObject(subproducttype,datamode)
{
	var getGroupbuyOrderXMLDocument = cordys.cloneXMLDocument(getGroupbuyOrderInfoXML.XMLDocument);
	var myorderid = "";
	if (groupbuyorderid.getValue() == "")
	{
		myorderid = orderId.getValue();
	}
	else
	{
		myorderid = groupbuyorderid.getValue();
	}

	cordys.setNodeText(getGroupbuyOrderXMLDocument ,".//*[local-name()='ORDER_ID']", myorderid);
	cordys.setNodeText(getGroupbuyOrderXMLDocument ,".//*[local-name()='PRODUCT_TYPE']", subproducttype);
	CompositeGroupbuyOrderModel.clear();
	CompositeGroupbuyOrderModel.setMethodRequest(getGroupbuyOrderXMLDocument);
	CompositeGroupbuyOrderModel.reset();
	var responseNode = CompositeGroupbuyOrderModel.getData();

	if(cordys.selectXMLNode(responseNode,".//*[local-name()='GROUPBUY_ORDER_DETAIL']"))
	{
	    var groupbuyOrderDetailDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
	    bindMultiTupleModel(datamode,responseNode,"GROUPBUY_ORDER_DETAIL",groupbuyOrderDetailDataNode);
	}
	
	switch(subproducttype)
	 {
	 	case "2":
	 		tableSeq(seq2,giftDetailTab);
	 		break;
	 	case "1":
	 		tableSeq(seq1,orderDetailTab);
	 		break;
	 	case "3":
	 		tableSeq(seq3,certifDetailTab);
	 		break;
	 	case "4":
	 		tableSeq(seq4,otherTab);
	 		break;
	 }
}

function tableSeq(seq,tableId)
{
	var tempLength = tableId.getRows().length;
	for(var i = 0; i < tempLength; i++)
	{
		seq[i+1].setValue(i+1);
	}
}

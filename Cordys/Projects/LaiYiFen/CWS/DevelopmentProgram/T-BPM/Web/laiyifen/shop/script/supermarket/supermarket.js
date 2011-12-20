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

/********************************************************************
* 功能名称：refreshModels
* 描述：请求返回之后重新绑定model里的数据
* 输入参数：eventObject
* 输出参数：无
*/
function refreshModels(eventObject)
{
    var responseNode = eventObject.response;
    if(cordys.selectXMLNode(responseNode,".//*[local-name()='faultcode']"))return;
    if(insertOperation == 1)
    { 
    	if(cordys.selectXMLNode(responseNode ,".//*[local-name()='FORM_ID']")){
	         formid.setValue(cordys.getTextContent(cordys.selectXMLNode(responseNode ,".//*[local-name()='FORM_ID']")));
	         insertOperation=0;    
	      	 getCompositeObject();
    	}
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
	//用户在其任务完成时,只能查看表单内容,而不能修改表单内容
	var workstatus = Workflow.getMessageStatus();
	if(workstatus == "COMPLETED" || workstatus == "ABORTED" || workstatus == "TERMINATED")
	{
		btsave.disable();
		btnsubmit.disable();
		btnreturn.disable();
		btnsuspend.disable();
	}
    for(var i=1;i<8;i++){ 
        File_RepositoryTable.hideColumn(i+5); 
    }  
    var stepVal = stepcode.getValue();
    if(stepVal=='05'||stepVal=='10'||stepVal=='15'){
	      supermarketinfo.disable();
	 }
    //隐藏待办页面缺省的按钮
    if(Workflow.getProcessInstanceId()!=null){
    	Workflow.hideTaskToolbar();
    	if(stepVal=="10"||stepVal=="15"||stepVal=="00"){
            btnreturn.hide();
            btnsuspend.hide();
        }
    }else{
    	btnreturn.hide();
    	btnsuspend.hide();
    	tbbbpmmonitor.hide();
    	if(stepVal == '00'||stepVal ==''){
    		tabapprovalhistory.hide(); 
   	   }
    }
    var appData = application.event.data;
    if(appData)
    {
    	var appstatus = application.event.data.status;
    	if(appstatus){
    		approvalremark.disable();
    		appresult.disable();
    		btnreturn.hide(); 
            if(appstatus=="0"){
            }else{
            	btsave.disable();
            	btnsubmit.disable();
            	tabapprovalhistory.show();
            	approvalremark.disable();
        		appresult.disable();
        		btnUpload.disable();
        		navDelete2.disable();
            }    
    	}
    } 
    supermarketuserinfo.disable();
}
/********************************************************************
 * 功能名称	: initForm();
 * 描述: 表单加载完成后查询复合对象并绑定到相应的model
 * 输入参数：eventObject
 * 输出参数：无
 */
function initForm(eventObject)
{
    WebForm.getLayoutElement(supermarketobjective).style.height = "auto";
    WebForm.getLayoutElement(supermarketobjective).className += "autoextent";
    if(Workflow.getProcessInstanceId()!=null&&Workflow.getProcessInstanceId() != undefined ){
    	printProgress(Workflow.getTask());
    }
    userArray=getUserInfo();
    supermarketuserinfo.create();
    staticObject = getStaticValue();
    var appData = application.event.data;
    if(appData)
    {
    	var appstatus = appData.status;
    	if(appstatus&&appstatus=="0"){
    		    var addusercode = appData.subusercode;
    			var currentusercode = null;
    			if(userArray){
    				currentusercode = userArray.userCode;
    			}
    			if(currentusercode!=null&&currentusercode!=addusercode){
    				btsave.disable();
    		    	btnsubmit.disable();
    		    	tabapprovalhistory.show();
    		    	approvalremark.disable();
    				appresult.disable();
    				btnUpload.disable();
    				navDelete2.disable();
    			}
    	}
    }
    supermarketname.setFocus();
    var statusVal = iptStatus.getValue();
    if(statusVal==1||statusVal==2||statusVal==3){
    	btnUpload.disable();
    	navDelete2.disable();
    }
    cordys.addDOMListener(File_RepositoryTable, "ondblclick",
			completeDownloadFile);
    modelCol_Single["GetSupermarketTransInfoObjectModel"] = GetSupermarketTransInfoObjectModel;
    modelCol_Single["GetApprovalModel"] = GetApprovalModel;
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
	 	var getSupermarketTransInfoXMLDocument = cordys.cloneXMLDocument(getSupermarketTransInfoXML.XMLDocument);
	 	var appData =  application.event.data;
		if(appData && appData.subformid)
        {
             cordys.setNodeText(getSupermarketTransInfoXMLDocument ,".//*[local-name()='form_ID']", application.event.data.subformid);         
        }else{
        	var myformid = formid.getValue();  
            cordys.setNodeText(getSupermarketTransInfoXMLDocument ,".//*[local-name()='form_ID']", myformid);
        }    
	    CompositeSupermarketTransInfoModel.clear();
        CompositeSupermarketTransInfoModel.setMethodRequest(getSupermarketTransInfoXMLDocument);
        CompositeSupermarketTransInfoModel.reset();
        var compositeData = CompositeSupermarketTransInfoModel.getData();
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
        if(cordys.selectXMLNode(responseNode,".//*[local-name()='SUPERMARKET_TRANS_INFO']"))
        {
            //var supermarketTransInfoDataNode = dataXML.XMLDocument.cloneNode(true);
            var supermarketTransInfoDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
            bindSingleTupleModel(GetSupermarketTransInfoObjectModel,responseNode,"SUPERMARKET_TRANS_INFO",supermarketTransInfoDataNode);
            formid.setValue(cordys.getNodeText(GetSupermarketTransInfoObjectModel.getData(),".//*[local-name()='FORM_ID']"));
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
   if(!WebForm.validateForm(application.container.applicationId))
   return;
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
  saveCompositeObject(CompositeSupermarketTransInfoModel,modelCol_Single,modelCol_Multi);
  if(CompositeSupermarketTransInfoModel.soapFaultOccurred) 
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
         case "GetSupermarketTransInfoObjectModel":
         	fillValuesInGetSupermarketInfoObjectModel(modelDataDoc ,modelBObjName,tupleNode);
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
	    formId[rowInx].setValue(formid.getValue());
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
    		if(stepVal == "00"||stepVal == "")
    		{
    			submitOperation = 1;
    			iptStatus.setValue("1");//流程中
    			decisionattribute.setValue("01");		
                submitTask();
    		}else{
    			if(stepVal == "05"){
    				//iptStatus.setValue("1");//流程中
    		        btn_selectmanagers_Click(popupEvent);
    			}else if(stepVal == "10"){
    				//iptStatus.setValue("1");//流程中
    				btn_selectpersons_Click(popupEvent);  
    			}else{
    				submitOperation = 1;
    				iptStatus.setValue("2");//审批完成
    				decisionattribute.setValue("01");
                    submitTask();
    			}
             }
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
       cordys.setNodeText(request,".//*[local-name()='receiver']","Business Process Models/com/laiyifen/shop/development/商超信息移交审批流程");   
       cordys.setNodeText(request,".//*[local-name()='FormID']",formid.value );
       cordys.setNodeText(request,".//*[local-name()='DecisionAttribute']","01");
       cordys.setNodeText(request,".//*[local-name()='CreateName']",username.getValue());
       cordys.setNodeText(request,".//*[local-name()='CreateTime']",applicatedate.getValue()); 
       cordys.setNodeText(request,".//*[local-name()='CreatorDN']",getCurrentUserDN());
       cordys.setNodeText(request,".//*[local-name()='CompanyCode']",userArray.companyCode);
       cordys.setNodeText(request,".//*[local-name()='CompanyName']",userArray.companyName);
       cordys.setNodeText(request,".//*[local-name()='CreatorDeptDN']",departmentcode.getValue());
       cordys.setNodeText(request,".//*[local-name()='DecisionAttribute']","01");
       cordys.setNodeText(request,".//*[local-name()='ProcessCode']","SP04.02.03.01");
       cordys.setNodeText(request,".//*[local-name()='ProcessName']","商超信息移交");
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
    showConfirm(false,returnHandler,"您确定执行驳回操作吗？");
}
 /********************************************************************
  * 功能名称	: returnHandler();
  * 描述: 点击驳回确认框的按钮时所触发的回调函数
  * 输入参数：confirmReturnValue，确认框的返回值：1--是；0--否
  * 输出参数：无
  */
function returnHandler(confirmReturnValue)
{
    if (confirmReturnValue == 1)
    {
    		submitOperation = 2; 
    		changeStatusForAttachments();
    		//点击驳回按钮时，记录当前驳回人
    		var curUserCd = getCurrentUserDN().split(",")[0].substring(3);
			revertuser.setValue(curUserCd);
    		decisionattribute.setValue("03");
    		saveForm();
    		Workflow.completeTask();
    }
    else if (confirmReturnValue == 0)
    {
    }
}

/********************************************************************
 * 功能名称	: btn_selectpersons_Click();
 * 描述: 选择人员
 * 输入参数：eventObject
 * 输出参数：oParam
 */
function btn_selectpersons_Click(eventObject)
{
    var oParam = new Object();
    oParam.unitId =userArray.deptCode ;
    oParam.selectMode = "SINGLE";
    oParam.selectedUserNameList="";
    oParam.selectedUserList="";
    application.select(Application_SelectUsers.XMLDocument.documentElement, oParam);
     if(oParam.selectedUserList!=""){
    	submitOperation = 1;
	    nextactivityuser.setValue(oParam.selectedUserList);
	    decisionattribute.setValue("01");
	    submitTask();
     }
 }

/********************************************************************
 * 功能名称	: btn_selectmanagers_Click();
 * 描述: 查询子公司网点经理
 * 输入参数：eventObject
 * 输出参数：oParam
 */
function btn_selectmanagers_Click(eventObject)
{
 //创建对象,并设定角色名称
  var input =new Object();
  input.roleName="BR子公司网点开发部经理";

//300,300为打开xform的高度和宽度, GetReturnValue为回调函数
  getDialogValueByRole(eventObject,input ,"子公司网点开发部经理选择",800,600,GetManagerReturnValue);
}

/********************************************************************
 * 功能名称	: GetManagerReturnValue();
 * 描述: 查询子公司网点经理 返回值
 * 输入参数：eventObject
 * 输出参数：returnValue[0]为用户DN,retrunValue[1]为用户名
 */
function GetManagerReturnValue(returnValue)
{
	if(returnValue==null||returnValue.length<3)
	{
		application.notify("未选择后续处理人员,请选择!!!");
		return;
	}else{
		submitOperation = 1;
		decisionattribute.setValue("01");
	    nextactivityuser.value = returnValue[0];
	    submitTask();
	}
}

/***********************************
*  功能名称：do_CompositeSupermarketTransInfoModel_OnSOAPFault();
*  功能描述：SOAP请求失败时消息提示
*  输入参数：eventObject
*  输出参数：无
*/
function do_CompositeSupermarketTransInfoModel_OnSOAPFault(eventObject)
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
          var ext1Node = cordys.createElementNS(modelData,"http://schemas.cordys.com/tbpm/shop","EXT1");
          if(processInstanceId){ 
             cordys.setTextContent(ext1Node, Workflow.getTaskInstanceId());
          }else{
         	 cordys.setTextContent(ext1Node, "draft");
          }
          cordys.appendXMLNode(ext1Node, cordys.selectXMLNode(tupleNode,".//*[local-name()='" + modelBObjName + "']"));
    }
	if(processInstanceId){
		    var instanceNode = cordys.createElementNS(modelData,"http://schemas.cordys.com/tbpm/shop","INSTANCE_ID");
		    var formIdNode = cordys.createElementNS(modelData,"http://schemas.cordys.com/tbpm/shop","FORM_ID");
		    var stepCdNode = cordys.createElementNS(modelData,"http://schemas.cordys.com/tbpm/shop","STEP_CODE");
		    var stepNmNode = cordys.createElementNS(modelData,"http://schemas.cordys.com/tbpm/shop","STEP_NAME");
		    var chargerCdNode = cordys.createElementNS(modelData,"http://schemas.cordys.com/tbpm/shop","CHARGER_CODE");
		    
		    cordys.setTextContent(instanceNode,processInstanceId);
		    cordys.setTextContent(formIdNode, formid.getValue());
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
		   var stepCdNode = cordys.createElementNS(modelData,"http://schemas.cordys.com/tbpm/shop","STEP_CODE");
		    var stepNmNode = cordys.createElementNS(modelData,"http://schemas.cordys.com/tbpm/shop","STEP_NAME");
		    var chargerCdNode = cordys.createElementNS(modelData,"http://schemas.cordys.com/tbpm/shop","CHARGER_CODE");
	       
	        cordys.setTextContent(stepCdNode, "00");
	        cordys.setTextContent(stepNmNode, "填写商超信息移交单");
	        cordys.setTextContent(chargerCdNode,usercode.getValue());  
	        
	        var insertParentNewNode = cordys.selectXMLNode(tupleNode,".//*[local-name()='new']"); 
	        cordys.appendXMLNode(stepCdNode, cordys.selectXMLNode(insertParentNewNode,".//*[local-name()='" + modelBObjName + "']"));
	        cordys.appendXMLNode(stepNmNode, cordys.selectXMLNode(insertParentNewNode,".//*[local-name()='" + modelBObjName + "']"));
	        cordys.appendXMLNode(chargerCdNode, cordys.selectXMLNode(insertParentNewNode,".//*[local-name()='" + modelBObjName + "']"));
    }

}

/********************************************************************
 * 功能名称	: fillValuesInGetSupermarketInfoObjectModel();
 * 功能描述:  为 model增加状态位节点
 * 输入参数：modelData modelBObjName tupleNode
 * 输出参数：无
 */
function fillValuesInGetSupermarketInfoObjectModel(modelData ,modelBObjName,tupleNode)
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
    	if(stepVal=="05"){
    		 cordys.setTextContent(statusNode, "1");    //status=1       审批中
    	}
    	if(stepVal=="10"){
    		 cordys.setTextContent(statusNode, "1");    //status=1       审批中
    	}
    	if(stepVal=="15"){
   		 cordys.setTextContent(statusNode, "2");    //status=2       审批完成
    	}
        
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

function do_btnsuspend_Click(eventObject){
	var flag = mycheck();
	   if(!flag){
	   	return false;
	}
	showConfirm(false,stopHandler,"您确定执行终止流程操作吗？");
}

function stopHandler(confirmReturnValue){
    if (confirmReturnValue == 1)
    {
    	iptStatus.setValue("3");//流程终止
    	submitOperation = 1; 
    	changeStatusForAttachments();
    	decisionattribute.setValue("05");
    	saveForm();
    	Workflow.completeTask();
    }
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
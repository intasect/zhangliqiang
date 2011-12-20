var insertOperation = 0;	//1保存，0查询
var submitOperation = 0;	//辨别提交和驳回操作，1表示提交，2表示驳回
var popupEvent = null;
var uploadFileURL = new Array();	//存放附件的url
var loopNum = 0;	//在拼装复合对象请求进行保存操作时，记录上传附件model的循环次数
var formID = "";
var userInfo;

var updateDocTemplate; 
var deleteDocTemplate; 
var insertDocTemplate;

var modelCol_Single = new Array();
var modelCol_Multi = new Array();
var validateControls = new Array();


/********************************************************************
 * 功能名称	: do_Form_Init();
 * 描述: 初始化
 * 输入参数： 
 * 输出参数：
 */
function do_Form_Init(eventObject)
{
	//隐藏待办页面缺省的按钮
   if(Workflow!=null) Workflow.hideTaskToolbar();
}

/********************************************************************
 * 功能名称	: Form_InitDone();
 * 描述: 初始化表单
 * 输入参数： 
 * 输出参数：
 */
function do_Form_InitDone(eventObject)
{
	//WebForm.getLayoutElement(gbxshopannual).style.height = "auto";
	//WebForm.getLayoutElement(gbxshopannual).className += "autoextent"; 

	File_RepositoryTable.hideColumn(6);
	File_RepositoryTable.hideColumn(7);
	File_RepositoryTable.hideColumn(8);
	File_RepositoryTable.hideColumn(9);
	// create the composite request Objetct
	modelCol_Single["GetBpcLoanObjectModel"] = GetBpcLoanObjectModel;
	modelCol_Single["GetBpcAuditObjectModel"] = GetBpcAuditObjectModel;
	modelCol_Single["GetApprovalModel"] = GetApprovalModel;
	
	//create composite request object for multituple
	modelCol_Multi["GetBpcLoanItemObjectsModel"] = GetBpcLoanItemObjectsModel;
	modelCol_Multi["GetAttachmentModel"] = GetAttachmentModel;
	
	//放置需要必填项校验的output,select控件
	validateControls["collection_type"] = collection_type;
	validateControls["collection_mode"] = collection_mode;
	validateControls["is_approval"] = is_approval;
	//validateControls["finance_iscomplete"] = finance_iscomplete;
	//validateControls["finance_ismatching"] = finance_ismatching;
	//validateControls["finance_subject_modify"] = finance_subject_modify;
	//validateControls["finance_approva_same"] = finance_approva_same;
	//validateControls["cashier_ispay"] = cashier_ispay;
	//validateControls["cashier_iscleared"] = cashier_iscleared;
	//validateControls["accountant_subject_modify"] = accountant_subject_modify;
	//validateControls["accountant_recognized"] = accountant_recognized;
	
	userInfo = getUserInfo();
	getCompositeObject();
	
	//用户在其任务完成时,只能查看表单内容,而不能修改表单内容
	if(Workflow.getMessageStatus() == "COMPLETED" || Workflow.getMessageStatus() == "ABORTED" || Workflow.getMessageStatus() == "TERMINATED")
	{
		tbbSave.disable();
		tbbSubmit.disable();
		tbbReturn.disable();
	}
	//判断是否为新建打开
	if (formID ==""){
		gbxuserinfo.create();
		gbxfinanceinfo.create();
		tabapprovalhistory.create();
	}	
	//判断是否由业务类型选择页打开	
	if(undefined != application.event.data && undefined != application.event.data.businesscode && null != application.event.data.businesscode)
	{
		business_matter.setValue(application.event.data.businessmatter);
		business_code.setValue(application.event.data.businesscode );
		business_name.setValue(application.event.data.businessname );
	}
}


/********************************************************************
 * 功能名称	: getCompositeObject();
 * 描述: 查询复合对象并绑定到相应的model
 * 输入参数： 
 * 输出参数： 
 */
function getCompositeObject()
{
	//判断是否由管理窗口打开	
	if(undefined != application.event.data && undefined != application.event.data.subformid && null != application.event.data.subformid)
	{
		formID = application.event.data.subformid ;
		tbbSave.disable();
		tbbSubmit.disable();
		tbbReturn.disable();
	}else{
		formID = form_id.getValue();
	}
	var getCompleteXMLDocument = cordys.cloneXMLDocument(getCompleteXML.XMLDocument);
	cordys.setNodeText(getCompleteXMLDocument,".//*[local-name()='formID']",formID);
	CompositeBPCLoanInfoModel.setMethodRequest(getCompleteXMLDocument);
	CompositeBPCLoanInfoModel.reset();
	var compositeData=CompositeBPCLoanInfoModel.getData();
	bindDataToModels(compositeData);
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
	saveCompositeObject(CompositeBPCLoanInfoModel,modelCol_Single,modelCol_Multi);
	insertOperation = 0;
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
		/*
		case "GetAttachmentModel" :
			fillValuesOnGetAttachmentModel(modelDataDoc ,modelBObjName,tupleNode);
			break;
		*/
		
		case "GetBpcLoanItemObjectsModel" :
			createFormId(modelDataDoc ,modelBObjName,tupleNode);
		break;
		
		case "GetBpcAuditObjectModel":
			createFormId(modelDataDoc ,modelBObjName,tupleNode);
		break;
		
	}
}


/********************************************************************
 * 为需要的model增加formID节点
 */
function createFormId(modelDataDoc ,modelBObjName,tupleNode)
{
	var formIdNode = cordys.createElementNS(modelDataDoc,"http://schemas.cordys.com/tbpm/bpc","FORM_ID");
	var parentNewNode =  cordys.selectXMLNode(tupleNode,".//*[local-name()='new']")
	cordys.setTextContent(formIdNode, formID);
	cordys.appendXMLNode(formIdNode, cordys.selectXMLNode(parentNewNode,".//*[local-name()='" + modelBObjName + "'][last()]"));	
}


/********************************************************************
 * 为审批意见model增加必须节点
 */
function fillValuesOnGetApprovalModel(modelData ,modelBObjName,tupleNode)
{
	//点击保存按钮时，添加ext1标志位，用于区分打开待办时审批意见是update还是insert
	if(submitOperation == 0)
	{
		var ext1Node = cordys.createElementNS(modelData,"http://schemas.cordys.com/tbpm/bpc","EXT1");
		cordys.setTextContent(ext1Node, Workflow.getTaskInstanceId());
		cordys.appendXMLNode(ext1Node, cordys.selectXMLNode(tupleNode,".//*[local-name()='" + modelBObjName + "']"));
	}
	var instanceNode = cordys.createElementNS(modelData,"http://schemas.cordys.com/tbpm/bpc","INSTANCE_ID");
	var formIdNode = cordys.createElementNS(modelData,"http://schemas.cordys.com/tbpm/bpc","FORM_ID");
	var stepCdNode = cordys.createElementNS(modelData,"http://schemas.cordys.com/tbpm/bpc","STEP_CODE");
	var stepNmNode = cordys.createElementNS(modelData,"http://schemas.cordys.com/tbpm/bpc","STEP_NAME");
	var chargerCdNode = cordys.createElementNS(modelData,"http://schemas.cordys.com/tbpm/bpc","CHARGER_CODE");

	cordys.setTextContent(instanceNode,Workflow.getProcessInstanceId());
	cordys.setTextContent(formIdNode, formID);
	cordys.setTextContent(stepCdNode, stepcode.getValue());
	cordys.setTextContent(stepNmNode, stepname.getValue());
	cordys.setTextContent(chargerCdNode, chargercode.getValue());

	var insertParentNewNode = cordys.selectXMLNode(tupleNode,".//*[local-name()='new']");
	cordys.appendXMLNode(instanceNode, cordys.selectXMLNode(insertParentNewNode,".//*[local-name()='" + modelBObjName + "'][last()]"));
	cordys.appendXMLNode(formIdNode, cordys.selectXMLNode(insertParentNewNode,".//*[local-name()='" + modelBObjName + "'][last()]"));
	cordys.appendXMLNode(stepCdNode, cordys.selectXMLNode(insertParentNewNode,".//*[local-name()='" + modelBObjName + "'][last()]"));
	cordys.appendXMLNode(stepNmNode, cordys.selectXMLNode(insertParentNewNode,".//*[local-name()='" + modelBObjName + "'][last()]"));
	cordys.appendXMLNode(chargerCdNode, cordys.selectXMLNode(insertParentNewNode,".//*[local-name()='" + modelBObjName + "'][last()]"));
}

/********************************************************************
 * 功能名称: bindDataToModels();
 * 描述: 数据和model绑定
 * 输入参数：
 * 输出参数：
 */
function bindDataToModels(responseNode)
{
	if(insertOperation == 0)
	{
		 //在流程中并且不是点击提交或驳回按钮时执行create()操作
		if(Workflow.getProcessInstanceId()!= null && submitOperation == 0)
	   { 
		   tabapprovalhistory.create();
		}
		//提交或驳回任务时刷新可编辑的审批意见model：GetApprovalHistoryModel
		if(submitOperation == 1 || submitOperation == 2)
		{
			GetApprovalModel.clear();
			GetApprovalModel.refreshAllViews();
			tabapprovalhistory.disable();				
		}
		
		if(cordys.selectXMLNode(responseNode,".//*[local-name()='BPC_LOAN']"))
		{
			var objDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
			bindSingleTupleModel(GetBpcLoanObjectModel,responseNode,"BPC_LOAN",objDataNode);
		}
		
		if(cordys.selectXMLNode(responseNode,".//*[local-name()='BPC_LOAN_ITEM']"))
		{
			var objDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
			bindMultiTupleModel(GetBpcLoanItemObjectsModel,responseNode,"BPC_LOAN_ITEM",objDataNode);
		}
		
		if(cordys.selectXMLNode(responseNode,".//*[local-name()='BPC_AUDIT']"))
		{
			var objDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
			bindSingleTupleModel(GetBpcAuditObjectModel,responseNode,"BPC_AUDIT",objDataNode);
		}
		
		if(cordys.selectXMLNode(responseNode,".//*[local-name()='ATTACHMENT']"))
		{
			var objDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
			bindMultiTupleModel(GetAttachmentModel,responseNode,"ATTACHMENT",objDataNode);
		}
		
		//处理人暂存后再次打开待办时将审批意见显示到可编辑区域
		var approvalNodes = cordys.cloneXMLDocument(responseNode);
		 if(cordys.selectXMLNode(approvalNodes,".//*[local-name()='APPROVAL_HISTORY'][*[local-name()='EXT1']!='']"))
		{
			var approvalNodeList = cordys.selectXMLNodes(approvalNodes,".//*[local-name()='APPROVAL_HISTORY'][*[local-name()='EXT1']!='']");
			for(var i=0; i < approvalNodeList.length; i++)
			{
						var approvalNode = cordys.selectXMLNode(approvalNodeList[i],  ".");
						if(cordys.getNodeText(approvalNode,".//*[local-name()='EXT1']") == Workflow.getTaskInstanceId())
						{
								var approvalDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
								cordys.appendXMLNode(approvalNode,cordys.selectXMLNode(approvalDataNode,".//*[local-name()='old']"));
								GetApprovalModel.clear();
								GetApprovalModel.putData(approvalDataNode);
								GetApprovalModel.refreshAllViews();
						}
			}
		}
		
		if(cordys.selectXMLNode(responseNode,".//*[local-name()='APPROVAL_HISTORY']"))
		{
			var objDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
			bindMultiTupleModel(GetApprovalHistoryModel,responseNode,"APPROVAL_HISTORY",objDataNode);
		}
	}
}



/********************************************************************
 * 功能名称: do_tbbSave_Click();
 * 描述: 保存按钮，表单保存
 * 输入参数：
 * 输出参数：
 */

function do_tbbSave_Click(eventObject)
{
	//页面校验
	if(!WebForm.validateForm(application.container.applicationId)) return;
	if(!validateControl(validateControls)) return;
	if(!mycheck()){ return false;}
	showConfirm(false,closeHandler);
}

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
 * 功能名称: do_tbbsubmit_Click();
 * 描述: 提交按钮，保存、提交
 * 输入参数：
 * 输出参数：
 */
function do_tbbSubmit_Click(eventObject)
{
	//页面校验
	if(!WebForm.validateForm(application.container.applicationId)) return;
	if(!validateControl(validateControls)) return;
	if(!mycheck()){ return false;}
	showConfirm(false,submitHandler,"您确定提交任务到下一环节吗？");
}

function submitHandler(confirmReturnValue)
{
	if (confirmReturnValue == 1)
	{
			submitOperation = 1;
			setFormStauts();
			changeStatusForAttachments();		   
			saveForm();	
			
		   
			//Workflow.completeTask();
			startWorkflow();
			application.container.close();
	}
	else if (confirmReturnValue == 0)
	{
	}
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

//设置流程状态
function setFormStauts(){
if(stepcode.getValue()==""){ stepcode.setValue("01"); }
	//总部流程
	if (business_type.getValue().indexOf("总部")>-1){
		if(stepcode.getValue()=='55'){
			form_status.setValue("2");
		}else{
			form_status.setValue("1");
		}
	//子公司流程
	}else{
		if(stepcode.getValue()=='70'){
			form_status.setValue("2");
		}else{
			form_status.setValue("1");
		}
	}
}

function startWorkflow(){

	if(Workflow.getProcessInstanceId()==undefined || Workflow.getProcessInstanceId()=="")
	{
		var request = cordys.cloneXMLDocument(executeProcess.XMLDocument);
		var flowURL = "Business Process Models/com/laiyifen/bpc/loan/总部借款流程" ;
		if (business_type.getValue() != "总部借款流程") flowURL = "Business Process Models/com/laiyifen/bpc/loan/子公司借款流程" ;
		cordys.setNodeText(request,".//*[local-name()='receiver']",flowURL);
		cordys.setNodeText(request,".//*[local-name()='FormID']",formID);
		cordys.setNodeText(request,".//*[local-name()='DecisionAttribute']","01");
		cordys.setNodeText(request,".//*[local-name()='CreateName']",user_name.getValue());
		cordys.setNodeText(request,".//*[local-name()='CreateTime']",petition_date.getValue()); 
		cordys.setNodeText(request,".//*[local-name()='CompanyCode']",userInfo.companyCode);
		cordys.setNodeText(request,".//*[local-name()='CompanyName']",userInfo.companyName);
		cordys.setNodeText(request,".//*[local-name()='CreatorDeptDN']",department_code.getValue());
		cordys.setNodeText(request,".//*[local-name()='ProcessCode']","");
		cordys.setNodeText(request,".//*[local-name()='ProcessName']",business_type.getValue() + "-" + business_name.getValue());						
		ExecuteProcessModel.clear();
		ExecuteProcessModel.setMethodRequest(request);
		ExecuteProcessModel.reset();
		
	}
	else
	{ 
		if(CompositeBPCLoanInfoModel.soapFaultOccurred) 
		{ 
			return; 
		}
		decisionattribute.setValue("01");
		Workflow.completeTask();
		
	}
}

/********************************************************************
 * 功能名称: do_tbbReturn_Click();
 * 描述: 驳回按钮，保存、驳回
 * 输入参数：
 * 输出参数：
 */
function do_tbbReturn_Click(eventObject)
{
	//页面校验
	if(!WebForm.validateForm(application.container.applicationId)) return;
	if(!validateControl(validateControls)) return;
	if(!mycheck()){ return false;}
	popupEvent = eventObject;
	showConfirm(false,returnHandler,"您确定执行驳回操作吗？");
}

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
			
			if(CompositeBPCLoanInfoModel.soapFaultOccurred) 
			{ 
				return; 
			}
			Workflow.completeTask();
			
	}
	else if (confirmReturnValue == 0)
	{
	}
}
//驳回位置弹出页关闭时的回调函数
function GetRevertValue(rtnValue)
{
	decisionattribute.setValue(rtnValue);
	
	//complete current task
	if(decisionattribute.getValue() != "" && decisionattribute.getValue() != null)
	{
		saveForm();
		if(CompositeBPCLoanInfoModel.soapFaultOccurred) 
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
/********************************************************************
 * 功能名称: do_tbbControlView_Click();
 * 描述: 监控图
 * 输入参数：
 * 输出参数：
 */
function  do_tbbControlView_Click(eventObject){
	alert("no");
}
/********************************************************************
 * 功能名称: do_tbbPrint_Click();
 * 描述: 打印
 * 输入参数：
 * 输出参数：
 */
function  do_tbbPrint_Click(eventObject){
	alert("no");
}

/********************************************************************
 * 功能名称: do_CompositeModel_OnResponse();
 * 描述: 请求报文成功之后刷新model
 * 输入参数：
 * 输出参数：
 */
function do_CompositeModel_OnResponse(eventObject)
{
	if(cordys.selectXMLNode(responseNode,".//*[local-name()='faultcode']"))  return;
	var responseNode = eventObject.response;
	//操作数据后设置FormId供下条流程使用
	var tmpnode=cordys.selectXMLNode(responseNode ,".//*[local-name()='BPC_LOAN']/*[local-name()='FORM_ID']")
	if(tmpnode!=null && cordys.getTextContent(tmpnode)!=""){
		formID =cordys.getTextContent(tmpnode);
		form_id.setValue(formID);
	}
	if(insertOperation == 1)
	{
		 insertOperation=0;	
		 getCompositeObject();
		 if(submitOperation == 0)
	  	{
	  		application.notify("保存成功");
	  	}
	}
}

/********************************************************************
 * 功能名称: do_tbbQuit_Click()
 * 描述: 退出
 * 输入参数：
 * 输出参数：
 */
function do_tbbQuit_Click(eventObject)
{
	if(parent.parent.parent){
	parent.parent.parent.application.container.close();
	}else{
	application.container.close();
	}
}
/********************************************************************
 * 功能名称: do_CompositeModel__OnSOAPFault()
 * 描述: 报错友好提示
 * 输入参数：
 * 输出参数：
 */
function do_CompositeModel__OnSOAPFault(eventObject)
{
	eventObject.showError = false;
	application.notify("您的操作未能成功执行，请您重新尝试或联系系统管理员，谢谢");	
}

 /********************************************************************
   * 功能名称	: do_btnUpload_Click();
   * 描述: 上传附件
   * 输入参数：
   * 输出参数：无
   */
function do_btnUpload_Click(eventObject) { 
	application.select(uploadApplication.XMLDocument.documentElement); 
}

 /********************************************************************
   * 功能名称	: fnAddFile();
   * 描述: 将附件内容从弹出页中带到主页面
   * 输入参数：fileObject,fileUrl和fileName
   * 输出参数：无
   */
function fnAddFile(fileObject,fileUrl,fileName)
{
	if(fileName != null && fileName !="")
	{
	File_RepositoryTable.create();
	var rowInx = File_RepositoryTable.getIndex();
	filename[rowInx].setValue(fileName);
	filetype[rowInx].setValue(cordys.getNodeText(fileObject,".//*[local-name()='FileType']"));
	deptNm[rowInx].setValue(userInfo.deptName);
	userNm[rowInx].setValue(userInfo.userName);
	fileurl[rowInx].setValue(fileUrl);
	fileformid[rowInx].setValue(form_id.getValue());
	fileinstanceid[rowInx].setValue(Workflow.getProcessInstanceId());
	submitstatus[rowInx].setValue("0");	//status=0表示当前附件可以删除
	}
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


  function completeDownloadFile()
{
	var rowInx = File_RepositoryTable.getIndex();
	var urlNode = fileurl[rowInx].getValue();
	var request = DownloadFileModel.getMethodRequest();
	cordys.setNodeText(request,".//*[local-name()='id']",urlNode);
	DownloadFileModel.setMethodRequest(request);
	DownloadFileModel.reset();
	var filePath = cordys.getNodeText(DownloadFileModel.getData(),".//*[local-name()='downloadFile']");
	if(filePath == "" || filePath == undefined)
	{
	   application.notify("This file does not exist or has been backed up elsewhere, please contact the system administrator.");
	   return;
	}
   window.open(filePath);
}

  /********************************************************************
   * 功能名称	: do_navDelete2_BeforeDelete();
   * 描述: 删除附件记录
  * 输入参数：eventObject
   * 输出参数：无
  */
function do_navDelete2_BeforeDelete(eventObject)
{
	var rows=File_RepositoryTable.getCheckedRows();
	if(rows.length==0)
	{
		application.notify("请选择所需删除的附件，谢谢");
		return;	
	}
	showConfirm(false,removeHandler,"您确定删除所选附件吗？");
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
			
			var guid = fileurl[rowInx].getValue();
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
function removeDataFromModel()
{
	var newNodesDoc = cordys.loadXMLDocument("<collection/>");
	var modelData = GetAttachmentModel.getData();
	var chekedRows = File_RepositoryTable.getCheckedRows();
	for(var i=0;i<chekedRows.length;i++)
	{
		var rowBObjId = cordys.getNodeText(chekedRows[i].businessObject,".//*[local-name()='FILEURL']");
		var selectedRowTuple= cordys.selectXMLNode(modelData,".//*[local-name()='tuple'][.//*[local-name()='FILEURL']='"+rowBObjId+"']");
		selectedRowTuple.parentNode.removeChild(selectedRowTuple);
	}
	var newNodes = modelData.getElementsByTagName("new");
	for(var i=0;i<newNodes.length;i++)
	{
		var nde = newNodes[i].cloneNode(true);
		cordys.appendXMLNode(nde,newNodesDoc.documentElement);
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
function rebindNewNodes(newNodesDoc)
{
	var newNodes = newNodesDoc.getElementsByTagName("new");
	for(var i=0;i<newNodes.length;i++)
	{
		File_RepositoryTable.create();
		var numRows  = File_RepositoryTable.getIndex();
		filename[numRows].setValue(cordys.getNodeText(newNodes[i],".//*[local-name()='FILENAME']"));
		filetype[numRows].setValue(cordys.getNodeText(newNodes[i],".//*[local-name()='FILETYPE']"));
		deptNm[numRows].setValue(cordys.getNodeText(newNodes[i],".//*[local-name()='DEPTMENT_NAME']"));
		userNm[numRows].setValue(cordys.getNodeText(newNodes[i],".//*[local-name()='OPERATOR_NAME']"));
		fileurl[numRows].setValue(cordys.getNodeText(newNodes[i],".//*[local-name()='FILEURL']"));
		fileformid[numRows].setValue(cordys.getNodeText(newNodes[i],".//*[local-name()='FORM_ID']"));
		fileinstanceid[numRows].setValue(cordys.getNodeText(newNodes[i],".//*[local-name()='INSTANCE_ID']"));
	}
	application.inform("附件删除成功");
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
	var oldValue = remark.getFormattedValue();
	if(oldValue.search("\n")!=-1)
	{
		oldValue = oldValue.substring(oldValue.search("\n")+1);
	}   
	remark.setValue(appresult.getValue()+"\n"+oldValue);
}
   
/********************************************************************
 * 功能名称	: do_Form_BeforeClose();
 * 描述: 关闭页面前释放全局变量
 * 输入参数：eventObject
 * 输出参数：无
 */
function Form_BeforeClose(eventObject)
{
	if(submitOperation == 1 || submitOperation == 2)
	{

		CordysRoot.application.inform("操作已成功，请继续其他操作");
	}


	formID = null;
	userInfo = null;
	insertOperation = null;
	vsubmitOperation = null;   
	uploadFileURL = null;
	modelCol_Single =null;
	modelCol_Multi = null;
	updateDocTemplate = null;
	deleteDocTemplate = null;
	insertDocTemplate = null;
	validateControls = null;
}

function mycheck(){
	var flag = true;
	var step = stepcode.getValue();
	if(Workflow.getProcessInstanceId()!=null){
		var approvalcontent = remark.getValue();
		if((approvalcontent==null||approvalcontent=="")&&step!="00"){
			flag = false;
			application.notify("审批意见不能为空");
			remark.setFocus();
			return flag;
		}
		
	}
	return flag;
}
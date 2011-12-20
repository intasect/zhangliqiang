var insertOperation;
var submitOperation = 0;	//辨别提交和驳回操作，1表示提交，2表示驳回
var popupEvent = null;
var updateDocTemplate ;
var deleteDocTemplate;
var insertDocTemplate;
var modelCol_Single = new Array();
var modelCol_Multi = new Array();
var validateControls = new Array();
var userInfo;
var compositeData;

/********************************************************************
 * 功能名称	: do_Form_Init();
 * 描述: 初始化表单
 * 输入参数：无
 * 输出参数：无
 */
function do_Form_Init()
{
	gpxhide.hide();
	
	//隐藏待办页面缺省的按钮
	if(Workflow.getProcessInstanceId()!=null)
	{
		printProgress(Workflow.getTask());
		Workflow.hideTaskToolbar();
		var taskStatus = Workflow. getMessageStatus();
		if(taskStatus == "COMPLETED" || taskStatus == "ABORTED" || taskStatus == "TERMINATED")
		{
			tbbsave.disable();
			tbbsubmit.disable();
			tbbreturn.disable();
			gpxshoptransInfo.disable();
			gpxprojectinfo.disable();
			gpxfinancialinfo.disable();
			gpxassetsmanagement.disable();
			gpxcommon.disable();
		}
		else
		{	
			if(stepcode.getValue() == "05" || stepcode.getValue() == "20")
			{
				tbbreturn.disable();
				if(stepcode.getValue() == "05")
				{
					shopserialno.disable();
					shopno.disable();
					shopname.disable();
					sapno.disable();	
				}
			}
			else if(stepcode.getValue() == "10")
			{
				gpxprojectinfo.disable();
				gpxfinancialinfo.disable();
				gpxassetsmanagement.disable();
			}
			else
			{
				gpxshoptransInfo.disable();	
				gpxprojectinfo.disable();
				gpxfinancialinfo.disable();
				gpxassetsmanagement.disable();
			}
		}
	}
	else
	{
		tbbsave.disable();
		tbbsubmit.disable();
		tbbreturn.disable();
		tbbbpmmonitor.disable();
		gpxshoptransInfo.disable();
		gpxprojectinfo.disable();
		gpxfinancialinfo.disable();
		gpxassetsmanagement.disable();
		gpxcommon.disable();
		shopid.setValue(application.event.data);		
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
	WebForm.getLayoutElement(gpxshoptransinfo).style.height = "auto";
	WebForm.getLayoutElement(gpxshoptransinfo).className += "autoextent";
	
	File_RepositoryTable.hideColumn(6);
	File_RepositoryTable.hideColumn(7);
	File_RepositoryTable.hideColumn(8);
	File_RepositoryTable.hideColumn(9);
	userInfo = getUserInfo();
	cordys.addDOMListener(File_RepositoryTable,"ondblclick",completeDownloadFile);
	
	// create the composite request Objetct
	modelCol_Single["GetShopTransInfoObjectModel"] = GetShopTransInfoObjectModel;
	modelCol_Single["GetShopMasterObjectModel"] = GetShopMasterObjectModel;
	modelCol_Single["GetShopTransEngObjectModel"] = GetShopTransEngObjectModel;
	modelCol_Single["GetApprovalHistoryModel"] = GetApprovalHistoryModel;
	
	//create composite request object for multituple
	modelCol_Multi["GetShopTransDistributionObjectModel"] = GetShopTransDistributionObjectModel;
	modelCol_Multi["GetAttachmentModel"] = GetAttachmentModel;
	
	//放置需要必填项校验的output,select控件
	validateControls["saleschannelname"] = saleschannelname;
	validateControls["bayname"] = bayname;
	validateControls["bussattrname"] = bussattrname;
	validateControls["shopattrname"] = shopattrname;
	validateControls["storerecency"] = storerecency;
	validateControls["shoptypename"] = shoptypename;
	
	//获取当前用户DN
	userdn.setValue(getCurrentUserDN());
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
	var getCompleteXMLDocument = cordys.cloneXMLDocument(getCompleteXML.XMLDocument);
	cordys.setNodeText(getCompleteXMLDocument,".//*[local-name()='formID']",formid.getValue());
	cordys.setNodeText(getCompleteXMLDocument,".//*[local-name()='shopID']",shopid.getValue());
	CompositeShopTransInfoModel.setMethodRequest(getCompleteXMLDocument);
	CompositeShopTransInfoModel.reset();
	compositeData=CompositeShopTransInfoModel.getData();
	bindDataToModels(compositeData);
}

/********************************************************************
 * 功能名称	: do_CompositeShopTransInfoModel_OnResponse();
 * 描述: 复合对象返回响应时调用，进行model绑定操作
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_CompositeShopTransInfoModel_OnResponse(eventObject)
{
	var responseNode = eventObject.response;
	if(cordys.selectXMLNode(responseNode,".//*[local-name()='faultcode']"))	return;
	if(insertOperation == 1)
	{
		insertOperation=0;
		getCompositeObject();
		if(submitOperation == 0)
		{
			application.inform("保存成功");
		}else{
			GetApprovalHistoryModel.undo();
		}
	}
}

/********************************************************************
 * 功能名称	: bindDataToModels();
 * 描述: 将复合对象的响应结构拆分到子对象中，将数据显示到页面
 * 输入参数：responseNode，响应节点
 * 输出参数：无
 */
function bindDataToModels(responseNode)
{
	//在流程中并且不是点击提交或驳回按钮时执行create()操作
	if(Workflow.getProcessInstanceId()!= null && submitOperation == 0)
	{ 
		tabapprovalhistory.create();
	}
	
	if(cordys.selectXMLNode(responseNode,".//*[local-name()='SHOP_TRANS_INFO']"))
	{
		var shopInfoDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
		bindSingleTupleModel(GetShopTransInfoObjectModel,responseNode,"SHOP_TRANS_INFO",shopInfoDataNode);
		formid.setValue(cordys.getNodeText(GetShopTransInfoObjectModel.getData(),".//*[local-name()='FORM_ID']"));
		var formType = cordys.getNodeText(GetShopTransInfoObjectModel.getData(),".//*[local-name()='FORM_TYPE']");
		if(formType == null)
		{
			formtype.setValue("0");	//formType=0表示是一般门店
			username.setValue(userInfo.userName);
			usercode.setValue(userInfo.userCode);
			departmentname.setValue(userInfo.deptName);
			departmentcode.setValue(userInfo.deptCode);
			subcompanycode.setValue(userInfo.companyCode);
			subcompanyname.setValue(userInfo.companyName);
		}
	}
	
	if(cordys.selectXMLNode(responseNode,".//*[local-name()='SHOP_MASTER']"))
	{
		var shopMasterDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
		bindSingleTupleModel(GetShopMasterObjectModel,responseNode,"SHOP_MASTER",shopMasterDataNode);
		if(!saleschannel.getValue())
		{
			saleschannel.setValue("01");
			saleschannelname.setValue("门店销售");	
		}
		imgshoptype.hide();
		if(!shoptype.getValue())
		{
			shoptype.setValue("1");
			shoptypename.setValue("普通门店");
		}
	}
	
	if(!insertOperation)
	{
		do_gpxprojectinfo_OnExpand();
		do_gpxassetsmanagement_OnExpand();
		do_gpxcommon_OnExpand();
	}
}

/********************************************************************
 * 功能名称	: completeDownloadFile();
 * 描述: 点击table的某行，触发下载附件操作
 * 输入参数：无
 * 输出参数：无
 */
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
		application.notify("文件找不到,请跟管理员联系.");
		return;
	}
	window.open(getStaticValue().downloadUrl + filePath);
}

/********************************************************************
 * 功能名称	: do_tbbsave_Click();
 * 描述: 点击工具栏的保存按钮，触发此方法
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_tbbsave_Click(eventObject)
{
	//弹出保存的确认框
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
	if(confirmReturnValue == 1)
	{
		saveForm();
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
  saveCompositeObject(CompositeShopTransInfoModel,modelCol_Single,modelCol_Multi);
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
		case "GetApprovalHistoryModel":
		    fillValuesOnGetApprovalModel(modelDataDoc,modelBObjName,tupleNode);
		break;
		
		case "GetShopTransEngObjectModel" :
		case "GetShopTransDistributionObjectModel" :
		    createFormId(modelDataDoc ,modelBObjName,tupleNode);
		break;
	}
}

/********************************************************************
 * 功能名称	: createFormId();
 * 描述: 保存前，对相关model进行formID赋值
 * 输入参数：modelDataDoc,modelBObjName,tupleNode
 * 输出参数：无
 */
function createFormId(modelDataDoc ,modelBObjName,tupleNode)
{
	var formIdNode = cordys.createElementNS(modelDataDoc,"http://schemas.cordys.com/tbpm/shop","FORM_ID");
	var parentNewNode =  cordys.selectXMLNode(tupleNode,".//*[local-name()='new']");
	cordys.setTextContent(formIdNode, formid.getValue());
	cordys.appendXMLNode(formIdNode, cordys.selectXMLNode(parentNewNode,".//*[local-name()='" + modelBObjName + "']"));    
}

/********************************************************************
 * 功能名称	: fillValuesOnGetApprovalModel();
 * 描述: 保存前，对审批意见model进行单独赋值
 * 输入参数：modelDataDoc,modelBObjName,tupleNode
 * 输出参数：无
 */
function fillValuesOnGetApprovalModel(modelDataDoc,modelBObjName,tupleNode)
{
	//点击保存按钮时，添加ext1标志位，用于区分打开待办时审批意见是update还是insert
	if(submitOperation == 0)
	{
		var ext1Node = cordys.createElementNS(modelDataDoc,"http://schemas.cordys.com/tbpm/shop","EXT1");
		cordys.setTextContent(ext1Node, Workflow.getTaskInstanceId());
		cordys.appendXMLNode(ext1Node, cordys.selectXMLNode(tupleNode,".//*[local-name()='" + modelBObjName + "']"));
	}
	
	var instanceNode = cordys.createElementNS(modelDataDoc,"http://schemas.cordys.com/tbpm/shop","INSTANCE_ID");
	var formIdNode = cordys.createElementNS(modelDataDoc,"http://schemas.cordys.com/tbpm/shop","FORM_ID");
	var stepCdNode = cordys.createElementNS(modelDataDoc,"http://schemas.cordys.com/tbpm/shop","STEP_CODE");
	var stepNmNode = cordys.createElementNS(modelDataDoc,"http://schemas.cordys.com/tbpm/shop","STEP_NAME");
	var chargerCdNode = cordys.createElementNS(modelDataDoc,"http://schemas.cordys.com/tbpm/shop","CHARGER_CODE");
	
	cordys.setTextContent(instanceNode,Workflow.getProcessInstanceId());
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
}

/********************************************************************
 * 功能名称	: do_gpxprojectinfo_OnExpand();
 * 描述: 展开工程groupbox时判断是否需要创建新记录
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_gpxprojectinfo_OnExpand(eventObject)
{
	if(cordys.selectXMLNode(compositeData,".//*[local-name()='SHOP_TRANS_ENG']"))
	{
		var shopEngDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
		bindSingleTupleModel(GetShopTransEngObjectModel,compositeData,"SHOP_TRANS_ENG",shopEngDataNode);
	}
	createBusinessObjOnModel(GetShopTransEngObjectModel,gpxprojectinfo,"SHOP_TRANS_ENG");
}

function do_gpxfinancialinfo_OnExpand(eventObject)
{
	if(cordys.selectXMLNode(compositeData,".//*[local-name()='SHOP_TRANS_ENG']"))
	{
		var shopEngDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
		bindSingleTupleModel(GetShopTransEngObjectModel,compositeData,"SHOP_TRANS_ENG",shopEngDataNode);
	}
}

function do_gpxassetsmanagement_OnExpand(eventObject)
{
	if(cordys.selectXMLNode(compositeData,".//*[local-name()='SHOP_TRANS_DISTRIBUTION']"))
	{
		var dataDoc = dataXML.XMLDocument;
		bindMultiTupleModel(GetShopTransDistributionObjectModel,compositeData,"SHOP_TRANS_DISTRIBUTION",dataDoc);
	}
}

function do_gpxcommon_OnExpand(eventObject)
{
	if(cordys.selectXMLNode(compositeData,".//*[local-name()='ATTACHMENT']"))
	{
		var dataDoc = dataXML.XMLDocument;
		bindMultiTupleModel(GetAttachmentModel,compositeData,"ATTACHMENT",dataDoc);
	}
	//处理人暂存后再次打开待办时将审批意见显示到可编辑区域
	var approvalNodes = cordys.cloneXMLDocument(compositeData);
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
				GetApprovalHistoryModel.clear();
				GetApprovalHistoryModel.putData(approvalDataNode);
				GetApprovalHistoryModel.refreshAllViews();
			}
		}
	}
	
	if(cordys.selectXMLNode(compositeData,".//*[local-name()='APPROVAL_HISTORY']"))
	{
		var dataDoc = dataXML.XMLDocument;
		bindMultiTupleModel(GetApprovalHistoryObjectModel,compositeData,"APPROVAL_HISTORY",dataDoc);
	}
}

/********************************************************************
 * 功能名称	: do_btnUpload_Click();
 * 描述: 打开上传附件弹出页
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_btnUpload_Click(eventObject)
{
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
		fileformid[rowInx].setValue(formid.getValue());
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
 * 功能名称	: do_tbbsubmit_Click();
 * 描述: 点击工具栏的提交按钮，触发此操作
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_tbbsubmit_Click(eventObject)
{
	//页面校验
	if(!WebForm.validateForm(application.container.applicationId)) return;
	if(!validateControl(validateControls)) return;
	//审批意见是否填写判断
	if(!validateApporval())	return;
	//弹出提交的确认框
	showConfirm(false,submitHandler,"您确定提交任务到下一环节吗？");
}

/********************************************************************
 * 功能名称	: validateApporval();
 * 描述: 点击工具栏的提交按钮，判断审批意见是否填写
 * 输入参数：eventObject
 * 输出参数：无
 */
function validateApporval()
{
	if(stepcode.getValue() != "05")
	{
		if(remark.getValue() == null || remark.getValue() == "")
		{
			application.notify("请输入您的审批意见");
			gpxcommon.expand();
			remark.setFocus();
			return false;	
		}
		return true;
	}
	return true;
}

/********************************************************************
 * 功能名称	: submitHandler();
 * 描述: 点击提交确认框的按钮时所触发的回调函数
 * 输入参数：confirmReturnValue，确认框的返回值：1--是；0--否
 * 输出参数：无
 */
function submitHandler(confirmReturnValue)
{
	if(confirmReturnValue == 1)
	{
		submitOperation = 1;
		changeStatusForAttachments();
		decisionattribute.setValue("01");
		saveForm();
		if(CompositeShopTransInfoModel.soapFaultOccurred)	return;
		Workflow.completeTask();
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
/********************************************************************
 * 功能名称	: do_tbbreturn_Click();
 * 描述: 点击工具栏的驳回按钮，触发此操作
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_tbbreturn_Click(eventObject)
{
	//页面校验
	if(!WebForm.validateForm(application.container.applicationId)) return;
	if(!validateControl(validateControls)) return;
	//审批意见是否填写判断
	if(!validateApporval())	return;
	//弹出驳回确认框
	popupEvent = eventObject;
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
	if(confirmReturnValue == 1)
	{
		submitOperation = 2;
		changeStatusForAttachments();
		//点击驳回按钮时，记录当前驳回人
		var curUserCd = getCurrentUserDN().split(",")[0].substring(3);
		revertuser.setValue(curUserCd);
		decisionattribute.setValue("03");
		saveForm();
		if(CompositeShopTransInfoModel.soapFaultOccurred)	return;
		Workflow.completeTask();
	}
}

/********************************************************************
 * 功能名称	: do_tbbquit_Click();
 * 描述: 点击工具栏的退出按钮，调用此方法
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_tbbquit_Click(eventObject)
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
 * 功能名称	: do_imgsaleschannel_Click();
 * 描述: 调用分销渠道列表弹出页
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_imgsaleschannel_Click(eventObject)
{
	getDialogValue(eventObject,saleschannel,saleschannelname,"SALES_CHANNEL","分销渠道列表","320px","230px","1");
}

/********************************************************************
 * 功能名称	: do_imgsalesorg_Click();
 * 描述: 调用销售组织列表弹出页
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_imgsalesorg_Click(eventObject)
{
	getSubCompany(eventObject,salesorg,salesorgname);
}

/********************************************************************
 * 功能名称	: do_imgbussattr_Click();
 * 描述: 调用商圈性质列表弹出页
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_imgbussattr_Click(eventObject)
{
	getDialogValue(eventObject,bussattr,bussattrname,"ZMSQ1","商圈性质列表","320px","230px","1");
}

/********************************************************************
 * 功能名称	: do_imgshopattr_Click();
 * 描述: 调用门店性质列表弹出页
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_imgshopattr_Click(eventObject)
{
	getDialogValue(eventObject,shopattr,shopattrname,"ZMXZ1","门店性质列表","320px","230px","1");
}

/********************************************************************
 * 功能名称	: do_imgbayname_Click();
 * 描述: 调用开间列表弹出页
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_imgbayname_Click(eventObject)
{
	getDialogValue(eventObject,baycode,bayname,"ZMD_001","开间列表","320px","230px","1");
}

/********************************************************************
 * 功能名称	: do_imgshoptype_Click();
 * 描述: 调用门店属性区分弹出页
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_imgshoptype_Click(eventObject)
{
	getDialogValue(eventObject,shoptype,shoptypename,"ZMDLX","门店类型列表","320px","230px","1");
}

/********************************************************************
 * 功能名称	: do_CompositeShopTransInfoModel_OnSOAPFault();
 * 描述: 与后台交互发生soap请求失败时，提示友好信息
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_CompositeShopTransInfoModel_OnSOAPFault(eventObject)
{
	insertOperation = 0;
	eventObject.showError = false;
	application.notify("您的操作未能成功执行，请您重新尝试或联系系统管理员，谢谢");	
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
function do_Form_BeforeClose(eventObject)
{
	if(submitOperation == 1 || submitOperation == 2)
	{
		CordysRoot.application.inform("操作已成功，请继续其他操作");
	}
	insertOperation = null;
	submitOperation = null;
	popupEvent = null;
	modelCol_Single =null;
	modelCol_Multi = null;
	updateDocTemplate = null;
	deleteDocTemplate = null;
	insertDocTemplate = null;
	userInfo = null;
	validateControls = null;
	compositeData = null;
}
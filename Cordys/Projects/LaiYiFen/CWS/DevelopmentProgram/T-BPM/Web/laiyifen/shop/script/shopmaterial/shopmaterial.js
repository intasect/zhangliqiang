var insertOperation = 0;
var submitOperation = 0;
var updateDocTemplate ;
var deleteDocTemplate;
var insertDocTemplate;
var modelCol_Single = new Array();
var modelCol_Multi = new Array();
var validateControls = new Array();
var userInfo;

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
			gpxshopmaterial.disable();
			gpxmaterialdetails.disable();
			gpxcommon.disable();
		}
		else
		{	
			if(stepcode.getValue() == "05" || stepcode.getValue() == "15")
			{
				tbbreturn.disable();	
			}
			if(stepcode.getValue() != "05")
			{
				gpxshopmaterial.disable();
				gpxmaterialdetails.disable();
			}
		}
	}
	else
	{
		tbbsave.disable();
		tbbsubmit.disable();
		tbbreturn.disable();
		tbbbpmmonitor.disable();
		gpxshopmaterial.disable();
		gpxmaterialdetails.disable();
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
	WebForm.getLayoutElement(gpxshopmateriallist).style.height = "auto";
	WebForm.getLayoutElement(gpxshopmateriallist).className += "autoextent";
	
	File_RepositoryTable.hideColumn(6);
	File_RepositoryTable.hideColumn(7);
	File_RepositoryTable.hideColumn(8);
	File_RepositoryTable.hideColumn(9);
	userInfo = getUserInfo();
	cordys.addDOMListener(File_RepositoryTable,"ondblclick",completeDownloadFile);
	
	// create the composite request Objetct
	modelCol_Single["GetShopMaterialObjectModel"] = GetShopMaterialObjectModel;
	modelCol_Single["GetShopMasterObjectModel"] = GetShopMasterObjectModel;
	modelCol_Single["GetApprovalHistoryModel"] = GetApprovalHistoryModel;
	
	//create composite request object for multituple
	modelCol_Multi["GetShopMaterialDetailObjectModel"] = GetShopMaterialDetailObjectModel;
	modelCol_Multi["GetAttachmentModel"] = GetAttachmentModel;
	
	//放置需要必填项校验的output,select控件
	validateControls["shopincode"] = shopincode;
	validateControls["highcabinetsshop"] = highcabinetsshop;
	validateControls["shoppropertiesname"] = shoppropertiesname;
	
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
	cordys.setNodeText(getCompleteXMLDocument,".//*[local-name()='shopID']",shopid.getValue());
	CompositeShopMaterialModel.setMethodRequest(getCompleteXMLDocument);
	CompositeShopMaterialModel.reset();
	var compositeData=CompositeShopMaterialModel.getData();
	bindDataToModels(compositeData);
}

/********************************************************************
 * 功能名称	: do_CompositeShopMaterialModel_OnResponse();
 * 描述: 复合对象返回响应时调用，进行model绑定操作
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_CompositeShopMaterialModel_OnResponse(eventObject)
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
		}
	}
}

/********************************************************************
 * 功能名称	: do_CompositeShopMaterialModel_OnResponse();
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
	//提交或驳回任务时刷新可编辑的审批意见model：GetApprovalHistoryModel
	if(submitOperation == 1 || submitOperation == 2)
	{
		GetApprovalHistoryModel.clear();
		GetApprovalHistoryModel.refreshAllViews();
		tabapprovalhistory.disable();		
	}
	if(cordys.selectXMLNode(responseNode,".//*[local-name()='SHOP_MATERIAL']"))
	{
		var shopMaterialDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
		bindSingleTupleModel(GetShopMaterialObjectModel,responseNode,"SHOP_MATERIAL",shopMaterialDataNode);
		formid.setValue(cordys.getNodeText(GetShopMaterialObjectModel.getData(),".//*[local-name()='FORM_ID']"));
	}
	else
	{
		gpxuserinfo.create();
		iptstatus.setValue("1");
	}
	
	if(cordys.selectXMLNode(responseNode,".//*[local-name()='SHOP_MASTER']"))
	{
		var shopMasterDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
		bindSingleTupleModel(GetShopMasterObjectModel,responseNode,"SHOP_MASTER",shopMasterDataNode);
		if(shoppropertiesname.getValue()=="" || shoppropertiesname.getValue()==null)
		{
			shoppropertiesname.setValue(cordys.getNodeText(GetShopMasterObjectModel.getData(),".//*[local-name()='BUSS_ATTR_NAME']"));
			shopproperties.setValue(cordys.getNodeText(GetShopMasterObjectModel.getData(),".//*[local-name()='BUSS_ATTR']"));
		}
	}
	
	if(cordys.selectXMLNode(responseNode,".//*[local-name()='SHOP_MATERIAL_DETAIL']"))
	{
		var dataDoc = dataXML.XMLDocument;
		bindMultiTupleModel(GetShopMaterialDetailObjectModel,responseNode,"SHOP_MATERIAL_DETAIL",dataDoc);
	}
	else
	{
		var materialDetailDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
		var nodeList = cordys.selectXMLNodes(responseNode,".//*[local-name()='SHOP_MATERIAL_CONFIGURATION']");
		for( var i=0, length=nodeList.length; i < length; i++ )
		{
			var materialDetailNode = cordys.selectXMLNode(nodeList[i], ".");
			var materialNm = cordys.getNodeText(materialDetailNode,".//*[local-name()='MATERIAL_NAME']");
			var productCd = cordys.getNodeText(materialDetailNode,".//*[local-name()='PRODUCT_CODE']");
			var productNm = cordys.getNodeText(materialDetailNode,".//*[local-name()='PRODUCT_NAME']");
			shopmaterialdetailtable.create();
			var rowInx = shopmaterialdetailtable.getIndex();
			var rowObjects = shopmaterialdetailtable.getRows();
			cordys.getElementById(rowObjects[rowInx-1],"materialname").setValue(materialNm);
			cordys.getElementById(rowObjects[rowInx-1],"productcode").setValue(productCd);
			cordys.getElementById(rowObjects[rowInx-1],"productname").setValue(productNm);
			/*materialname[rowInx].setValue(materialNm);
			productcode[rowInx].setValue(productCd);
			productname[rowInx].setValue(productNm);*/
		}
	}
	
	if(cordys.selectXMLNode(responseNode,".//*[local-name()='ATTACHMENT']"))
	{
		var dataDoc = dataXML.XMLDocument;
		bindMultiTupleModel(GetAttachmentModel,responseNode,"ATTACHMENT",dataDoc);
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
				GetApprovalHistoryModel.clear();
				GetApprovalHistoryModel.putData(approvalDataNode);
				GetApprovalHistoryModel.refreshAllViews();
			}
		}
	}
	
	if(cordys.selectXMLNode(responseNode,".//*[local-name()='APPROVAL_HISTORY']"))
	{
		var dataDoc = dataXML.XMLDocument;
		bindMultiTupleModel(GetApprovalHistoryObjectModel,responseNode,"APPROVAL_HISTORY",dataDoc);
	}
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
	saveCompositeObject(CompositeShopMaterialModel,modelCol_Single,modelCol_Multi);
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
		
		case "GetShopMaterialDetailObjectModel" :
			createFormId(modelDataDoc ,modelBObjName,tupleNode);
		break;
		
		case "GetShopMaterialObjectModel":
			fillValuesInShopmaterial(modelDataDoc ,modelBObjName,tupleNode);
		break;
	}
}

/********************************************************************
 * 功能名称	: fillValuesInShopmaterial();
 * 描述: 保存前，对ShopTransInfo model进行单独赋值
 * 输入参数：modelDataDoc,modelBObjName,tupleNode
 * 输出参数：无
 */
function fillValuesInShopmaterial(modelDataDoc,modelBObjName,tupleNode)
{
	var shopIdNode = cordys.selectXMLNode(tupleNode,".//*[local-name()='" + modelBObjName + "']/*[local-name()='SHOP_ID']");
	cordys.setXMLAttribute(shopIdNode,"","null","false");
	cordys.setTextContent(shopIdNode, shopid.getValue()); 
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
 * 功能名称	: submitHandler();
 * 描述: 点击提交确认框的按钮时所触发的回调函数
 * 输入参数：confirmReturnValue，确认框的返回值：1--是；0--否
 * 输出参数：无
 */
function submitHandler(confirmReturnValue)
{
	if(confirmReturnValue == 1)
	{
		if(stepcode.getValue() == "10")
		{
			var oParam = new Object();
			oParam.unitId ="10000110";
			oParam.selectMode = "MULTIPLE";
			oParam.selectedUserNameList="";
			oParam.selectedUserList="";
			application.select(Application_SelectUsers.XMLDocument.documentElement, oParam);
			if("" == oParam.selectedUserList)	return;
			nextactivityuser.setValue(oParam.selectedUserList);
			oParam = null;
		}      
		submitOperation = 1;
		changeStatusForAttachments();
		decisionattribute.setValue("01");		
		saveForm();
		if(CompositeShopMaterialModel.soapFaultOccurred)	return;
		Workflow.completeTask();
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
		
		decisionattribute.setValue("04");
		saveForm();
		if(CompositeShopMaterialModel.soapFaultOccurred)	return;
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
 * 功能名称	: do_imgshopproperties_Click();
 * 描述: 调用商圈性质列表弹出页
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_imgshopproperties_Click(eventObject)
{
	getDialogValue(eventObject,shopproperties,shoppropertiesname,"BUSS_ATTR","商圈性质列表","320px","230px","1");
}

/********************************************************************
 * 功能名称	: do_CompositeShopTransInfoModel_OnSOAPFault();
 * 描述: 与后台交互发生soap请求失败时，提示友好信息
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_CompositeShopMaterialModel_OnSOAPFault(eventObject)
{
	eventObject.showError = false;
	application.notify("您的操作未能成功执行，请您重新尝试或联系系统管理员，谢谢");	
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
		submitstatus[rowInx].setValue("0");
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
	if(appresult.getValue()=="")	return;
	var oldValue = remark.getFormattedValue();
	if(oldValue.search("\n")!=-1)
	{
		oldValue = oldValue.substring(oldValue.search("\n")+1);
	}
	remark.setValue(appresult.getValue()+"\n"+oldValue);
}

/********************************************************************
 * 功能名称	: do_productcode_BeforeZoom();
 * 描述: 商品编码弹出页弹出前，对弹出页进行设置
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_productcode_BeforeZoom(eventObject)
{
	var frameNode = cordys.selectXMLNode(eventObject.applicationDefinition,".//*[local-name()='frame']");
	frameNode.setAttribute("features", "dialogWidth=650px, dialogHeight=550px");
	//setting caption
	cordys.setTextContent(cordys.selectXMLNode(eventObject.applicationDefinition,".//*[local-name()='caption']"),"辅料商品列表");
	var rowInx = shopmaterialdetailtable.getIndex();
	var data = new Object();
	data.type = "MATNR";
	data.code = productcode[rowInx].getValue();
	eventObject.data = data;
}

/********************************************************************
 * 功能名称	: do_productcode_AfterZoom();
 * 描述: 商品编码弹出页弹出后，对商品名称进行赋值
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_productcode_AfterZoom(eventObject)
{
	var desc = cordys.getNodeText(eventObject.businessObject,".//*[local-name()='DESCRIPTION']");
	if(desc != null && desc != "")
	{
		var rowInx = shopmaterialdetailtable.getIndex();
		productname[rowInx].setValue(desc);
	}
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
	modelCol_Single =null;
	modelCol_Multi = null;
	updateDocTemplate = null;
	deleteDocTemplate = null;
	insertDocTemplate = null;
	userInfo = null;
	validateControls = null;
}
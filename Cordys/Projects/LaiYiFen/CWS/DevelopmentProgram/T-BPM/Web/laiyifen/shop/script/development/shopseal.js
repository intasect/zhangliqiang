var insertOperation = 0;
var submitOperation = 0;   //辨别提交和其他操作，1表示提交，0表示其他,2表示驳回
var popupEvent = null;
var userInfo;
var updateDocTemplate ;
var deleteDocTemplate;
var insertDocTemplate;
var staticObject = null;

var modelCol_Single = new Array();
var modelCol_Multi = new Array();
var validateControls = new Array();
/********************************************************************
 * 功能名称	: refreshModels();
 * 描述: 刷新复合对象模型数据
 * 输入参数：eventObject
 * 输出参数：无
 */
function refreshModels(eventObject)
{
     var responseNode = eventObject.response;
     //判断返回报文有没有错误
    if(undefined == cordys.selectXMLNode(responseNode ,".//*[local-name()='faultcode']"))
    {
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
}

/********************************************************************
 * 功能名称	: do_Form_Init();
 * 描述: 初始化表单
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_Form_Init(eventObject)
{
	//用户在其任务完成时,只能查看表单内容,而不能修改表单内容
	var messageStatus = Workflow.getMessageStatus();
	if(messageStatus == "COMPLETED" || messageStatus == "ABORTED" || messageStatus == "TERMINATED")
	{
		tbbSave.disable();
		tbbSubmit.disable();
		tbbReturn.disable();
	}
	
    gpxhide.hide();
   
    if(stepcode.getValue() == "01")
    {
    	tbbReturn.hide();	
    }
    else
	{
    	gpxsealinfo.disable();
        gpxsuppliers.disable();
        gpxMaster.disable();
	}
    
    if(stepcode.getValue() == "35")
	{
    	tbbSave.disable();
		tbbSubmit.disable();
		tbbReturn.disable();
	}
    if(stepcode.getValue() == "30")
	{
    	tbbReturn.disable();
	}
  
    if(Workflow.getProcessInstanceId()!=null)
	{
    	//隐藏待办页面缺省的按钮
    	if(Workflow!=null) Workflow.hideTaskToolbar();
	}
	
}

/********************************************************************
 * 功能名称	: initForm();
 * 描述: 表单加载完成后查询复合对象并绑定到相应的model
 * 输入参数：eventObject
 * 输出参数：无
 */
function initForm(eventObject)
{
	 if(Workflow.getProcessInstanceId()!=null)
	 {
		//设置右上角超时图片
		printProgress(Workflow.getTask());
	 }
	
    WebForm.getLayoutElement(gpxshopseal).style.height = "auto";
    WebForm.getLayoutElement(gpxshopseal).className += "autoextent";
   
    File_RepositoryTable.hideColumn(6);
    File_RepositoryTable.hideColumn(7);
    File_RepositoryTable.hideColumn(8);
    File_RepositoryTable.hideColumn(9);
    userInfo = getUserInfo();
    staticObject = getStaticValue();
    cordys.addDOMListener(File_RepositoryTable,"ondblclick",completeDownloadFile);
    
    //放置需要必填项校验的output,select控件
	validateControls["seal_name"] = seal_name;
	validateControls["payconname"] = payconname;
	validateControls["payname"] = payname;
    
    //创建单条记录模型数组
    modelCol_Single["GetShopSealApplicationObjectModel"] = GetShopSealApplicationObjectModel;
    modelCol_Single["GetSuppliersMasterObjectModel"] = GetSuppliersMasterObjectModel;
    modelCol_Single["GetShopMasterObjectModel"] = GetShopMasterObjectModel;
    modelCol_Single["GetApprovalModel"] = GetApprovalModel;
    
    //创建多条记录模型数组
    modelCol_Multi["GetAttachmentModel"] = GetAttachmentModel;
    
    gpxsealinfo.create();
    gpxsuppliers.create();
    //设置供应商类别的值
    supplierCategory.setValue("9002");
    order_currency.setValue("CNY");
    //初始化合同类型为标准合同
    contrct_type.setValue("0");
    //初始化统驭科目
    re_acccode.setValue("22020200");
    //初始化银行国家代码
    bank_coucode.setValue("CN");

   //从上一流程取得整个新开店生命周期的shopId
   var shopId= shopid.getValue();
   if(undefined != shopId&& ""!= shopId&& null != shopId)
   {
          iptshopid.setValue(shopId);
          iptformid.setValue(formid.getValue());
          iptStatus.setValue("1");
          getCompositeObject();
   }
   else
   {
	   	var dataTemp = application.event.data;
		if(undefined != dataTemp && undefined != dataTemp.subformid && null != dataTemp.subformid)
		{
			formid.setValue(dataTemp.subformid);
			iptshopid.setValue(dataTemp.subshopid);
			getCompositeObject();
			
			gpxsealinfo.disable();
			gpxsuppliers.disable();
			gpxcommon.disable();
			tbbSave.disable();
			tbbSubmit.disable();
			tbbReturn.disable();
		}
		else
		{
			application.notify ("流程非法进入，请联系管理员！");
		}                 
   }
   if(stepcode.getValue() == "30")
   {
	   seal_id.setValue(iptSealNumber.getValue());
   }
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
    cordys.setNodeText(getCompleteXMLDocument,".//*[local-name()='formId']",formid.getValue());
    cordys.setNodeText(getCompleteXMLDocument,".//*[local-name()='shopId']",iptshopid.getValue());
    CompositeShopSealInfoModel.setMethodRequest(getCompleteXMLDocument);
    CompositeShopSealInfoModel.reset();
    var compositeData=CompositeShopSealInfoModel.getData();
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
	saveCompositeObject(CompositeShopSealInfoModel,modelCol_Single,modelCol_Multi);
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
    }
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
         //提交或驳回任务时刷新可编辑的审批意见model：GetApprovalModel
         if(submitOperation == 1 || submitOperation == 2)
         {
        	 GetApprovalModel.clear();
        	 GetApprovalModel.refreshAllViews();
        	 tabapprovalhistory.disable();		
         }
         
         if(cordys.selectXMLNode(responseNode,".//*[local-name()='SHOP_SEAL_APPLICATION']"))
         {
             var shopInfoDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
             bindSingleTupleModel(GetShopSealApplicationObjectModel,responseNode,"SHOP_SEAL_APPLICATION",shopInfoDataNode);
         }
         
         if(cordys.selectXMLNode(responseNode,".//*[local-name()='SUPPLIERS_MASTER']"))
         {
             var shopInfoDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
             bindSingleTupleModel(GetSuppliersMasterObjectModel,responseNode,"SUPPLIERS_MASTER",shopInfoDataNode);
         }
         
         if(cordys.selectXMLNode(responseNode,".//*[local-name()='SHOP_MASTER']"))
         {
             var shopMasterDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
             bindSingleTupleModel(GetShopMasterObjectModel,responseNode,"SHOP_MASTER",shopMasterDataNode);
         }
         
         if(cordys.selectXMLNode(responseNode,".//*[local-name()='ATTACHMENT']"))
         {
             dataDoc = dataXML.XMLDocument;
             bindMultiTupleModel(GetAttachmentModel,responseNode,"ATTACHMENT",dataDoc);
         }

         //处理人暂存后再次打开待办时将审批意见显示到可编辑区域
     	 var approvalNodes = cordys.cloneXMLDocument(responseNode.documentElement);
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
             dataDoc = dataXML.XMLDocument;
             bindMultiTupleModel(GetApprovalHistoryModel,responseNode,"APPROVAL_HISTORY",dataDoc);
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
	 var newNodesDoc = cordys.loadXMLDocument("<collection/>")
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
    filePath=staticObject.downloadUrl+filePath;
   window.open(filePath);
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
 * 功能名称	: do_tbbsave_Click();
 * 描述: 点击工具栏的保存按钮，触发此方法
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_tbbsave_Click(eventObject)
{
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
 * 功能名称	: submitTask();
 * 描述: 点击工具栏的提交按钮，触发此操作
 * 输入参数：eventObject
 * 输出参数：无
 */
function submitTask(eventObject)
{
	//审批意见是否填写判断
    if(!validateApporval())	return;
    
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
	if(stepcode.getValue() != "01" && Workflow.getProcessInstanceId()!= null)
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
		if (confirmReturnValue == 1)
		{
			submitOperation = 1;
			changeStatusForAttachments();
			if(stepcode.getValue() == "30")
			{
				iptStatus.setValue("2");
			}
			saveForm();
			decisionattribute.setValue("01");
			if(CompositeShopSealInfoModel.soapFaultOccurred)
			{
				submitOperation = 0;
			    return;
			}
			Workflow.completeTask();
		}
		else if (confirmReturnValue == 0)
		{
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
	//审批意见是否填写判断
    if(!validateApporval())	return;
	
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
    if (confirmReturnValue == 1)
    {
 		application.showDialog(dialogRevert.XMLDocument.documentElement,null, null, GetRevertValue, false); 
                placePopup(popupEvent);
    }
    else if (confirmReturnValue == 0)
    {
    }
}

/********************************************************************
 * 功能名称	: GetRevertValue();
 * 描述: 驳回位置弹出页关闭时的回调函数
 * 输入参数：rtnValue，03--驳回到发起人；04--驳回到上一级
 * 输出参数：无
 */
function GetRevertValue(rtnValue)
{
    decisionattribute.setValue(rtnValue);
    
    //complete current task
    if(decisionattribute.getValue() != "" && decisionattribute.getValue() != null)
    {
    	//点击驳回按钮时，记录当前驳回人
		var curUserCd = getCurrentUserDN().split(",")[0].substring(3);
		revertuser.setValue(curUserCd);
		
        submitOperation = 2;
        changeStatusForAttachments();
        saveForm();
        if(CompositeShopSealInfoModel.soapFaultOccurred)
       {
        	submitOperation = 0;
                return;
        }
        Workflow.completeTask();
    }
    else
    {
        application.notify("请选择驳回位置");
    }
}

/********************************************************************
 * 功能名称	: do_tbbquit_Click();
 * 描述: 点击工具栏的退出按钮，调用此方法
 * 输入参数：无
 * 输出参数：无
 */
function do_tbbquit_Click()
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
 * 功能名称	: do_appresult_Change();
 * 描述: 审批意见中常用语变化时将常用语赋值到审批意见textarea中
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_appresult_Change(eventObject)
{
    if(app_roval.getValue()=="")
    {
        return;
    }
    var oldValue = remark.getFormattedValue();
    if(oldValue.search("\n")!=-1)
    {
        oldValue = oldValue.substring(oldValue.search("\n")+1);
    }   
    remark.setValue(app_roval.getValue()+"\n"+oldValue);
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
}

/********************************************************************
 * 功能名称	: do_rent_Validate();
 * 描述: 判断租金是否比原来的大
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_rent_Validate(eventObject)
{
	var shopDataMaster=GetShopMasterObjectModel.getData();
	if(parseFloat(cordys.getNodeText(shopDataMaster,".//*[local-name()='RENT']")) < rent.getValue())
	{
		eventObject.valid = false;
		eventObject.errorMessage = "新租金不能高于原租金值！";
	}
	else
	{
		eventObject.valid = true;
	}
}

/********************************************************************
 * 功能名称	: do_agencyFees_Validate();
 * 描述: 判断中介费是否比原来的大
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_agencyFees_Validate(eventObject)
{
	var shopDataMaster=GetShopMasterObjectModel.getData();
	if(parseFloat(cordys.getNodeText(shopDataMaster,".//*[local-name()='AGENCY_FEES']")) < agencyFees.getValue())
	{
		eventObject.valid = false;
		eventObject.errorMessage = "新中介费不能高于原中介费值！";
	}
	else
	{
		eventObject.valid = true;
	}
}

/********************************************************************
 * 功能名称	: do_propertyManaFees_Validate();
 * 描述: 判断物业费是否比原来的大
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_propertyManaFees_Validate(eventObject)
{
	var shopDataMaster=GetShopMasterObjectModel.getData();
	if(parseFloat(cordys.getNodeText(shopDataMaster,".//*[local-name()='PROPERTY_MANAGEMENT_FEES']")) < propertyManaFees.getValue())
	{
		eventObject.valid = false;
		eventObject.errorMessage = "新物业费不能高于原物业费值！";
	}
	else
	{
		eventObject.valid = true;
	}
}

/********************************************************************
 * 功能名称	: do_transferfee_Validate();
 * 描述: 判断转让费是否比原来的大
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_transferfee_Validate(eventObject)
{
	var shopDataMaster=GetShopMasterObjectModel.getData();
	if(parseFloat(cordys.getNodeText(shopDataMaster,".//*[local-name()='TRANSFERFEE']")) < transferfee.getValue())
	{
		eventObject.valid = false;
		eventObject.errorMessage = "新转让费不能高于原转让费值！";
	}
	else
	{
		eventObject.valid = true;
	}
}

/********************************************************************
 * 功能名称	: do_costpayment_Validate();
 * 描述: 判断其他小额费用是否比原来的大
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_costpayment_Validate(eventObject)
{
	var shopDataMaster=GetShopMasterObjectModel.getData();
	if(parseFloat(cordys.getNodeText(shopDataMaster,".//*[local-name()='COSTPAYMENT']")) < costpayment.getValue())
	{
		eventObject.valid = false;
		eventObject.errorMessage = "新其他小额费用不能高于原其他小额费值！";
	}
	else
	{
		eventObject.valid = true;
	}
}

/********************************************************************
 * 功能名称	: do_useArea_Validate();
 * 描述: 判断总建筑面积和总使用面积是否比原来的小
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_useArea_Validate(eventObject)
{
	var shopDataMaster=GetShopMasterObjectModel.getData();
	if(parseFloat(cordys.getNodeText(shopDataMaster,".//*[local-name()='USE_AREA']")) > useArea.getValue())
	{
		eventObject.valid = false;
		eventObject.errorMessage = "新总建筑面积和总使用面积不能低于原总建筑面积和总使用面积值！";
	}
	else
	{
		eventObject.valid = true;
	}
}

/********************************************************************
 * 功能名称	: do_rentPeriod_Validate();
 * 描述: 判断合同租期是否比原来的小
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_rentPeriod_Validate(eventObject)
{
	var shopDataMaster=GetShopMasterObjectModel.getData();
	if(parseFloat(cordys.getNodeText(shopDataMaster,".//*[local-name()='RENT_PERIOD']")) > rentPeriod.getValue())
	{
		eventObject.valid = false;
		eventObject.errorMessage = "新合同租期不能低于原合同租期值！";
	}
	else
	{
		eventObject.valid = true;
	}
}

/********************************************************************
 * 功能名称	: do_open_bank_Validate();
 * 描述: 当付款方式为银行转账情况开户行名称必填
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_open_bank_Validate(eventObject)
{
	var paynameValue = iptpaymentcode.getValue();
	if (paynameValue == "B")
	{
		var open_bank_value = open_bank.getValue();
		if (open_bank_value == null ||  open_bank_value == "" || open_bank_value == undefined)
		{
			eventObject.valid = false;
			eventObject.errorMessage = "付款方式为银行转账时开户行名称必填！";
		}
		else
		{
			eventObject.valid = true;
		}
	}
	else
	{
		eventObject.valid = true;
	}
}

/********************************************************************
 * 功能名称	: open_bank_add_Validate();
 * 描述: 当付款方式为银行转账情况开户行地址必填
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_open_bank_add_Validate(eventObject)
{
	var paynameValue = iptpaymentcode.getValue();
	if (paynameValue == "B")
	{
		var open_bank_add_value = open_bank_add.getValue();
		if (open_bank_add_value == null ||  open_bank_add_value == "" || open_bank_add_value == undefined)
		{
			eventObject.valid = false;
			eventObject.errorMessage = "付款方式为银行转账时开户行地址必填！";
		}
		else
		{
			eventObject.valid = true;
		}
	}
	else
	{
		eventObject.valid = true;
	}
}

/********************************************************************
 * 功能名称	: do_accho_name_Validate();
 * 描述: 当付款方式为银行转账情况开户人名称必填
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_accho_name_Validate(eventObject)
{
	var paynameValue = iptpaymentcode.getValue();
	if (paynameValue == "B")
	{
		var accho_name_value = accho_name.getValue();
		if (accho_name_value == null ||  accho_name_value == "" || accho_name_value == undefined)
		{
			eventObject.valid = false;
			eventObject.errorMessage = "付款方式为银行转账时开户人名称必填！";
		}
		else
		{
			eventObject.valid = true;
		}
	}
	else
	{
		eventObject.valid = true;
	}
}

/********************************************************************
 * 功能名称	: do_bank_accounts_Validate();
 * 描述: 当付款方式为银行转账情况开户行账号必填
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_bank_accounts_Validate(eventObject)
{
	var paynameValue = iptpaymentcode.getValue();
	if (paynameValue == "B")
	{
		var bank_accounts_value = bank_accounts.getValue();
		if (bank_accounts_value == null ||  bank_accounts_value == "" || bank_accounts_value == undefined)
		{
			eventObject.valid = false;
			eventObject.errorMessage = "付款方式为银行转账时开户行账号必填！";
		}
		else
		{
			eventObject.valid = true;
		}
	}
	else
	{
		eventObject.valid = true;
	}
}

/********************************************************************
 * 功能名称	: do_ext1_Validate();
 * 描述: 当付款方式为银行转账情况开户银行必填
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_ext1_Validate(eventObject)
{
	var paynameValue = iptpaymentcode.getValue();
	if (paynameValue == "B")
	{
		var ext1_value = ext1.getValue();
		if (ext1_value == null ||  ext1_value == "" || ext1_value == undefined)
		{
			eventObject.valid = false;
			eventObject.errorMessage = "付款方式为银行转账时开户银行必填！";
		}
		else
		{
			eventObject.valid = true;
		}
	}
	else
	{
		eventObject.valid = true;
	}
}
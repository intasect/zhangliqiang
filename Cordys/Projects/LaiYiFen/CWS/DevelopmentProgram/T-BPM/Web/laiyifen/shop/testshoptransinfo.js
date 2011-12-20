var insertOperation = 0;
var tupleNodeForInsert = null;
var tupleNodeForUpdate = null;
var tupleNodeForDelete = null;
var submitOperation = 0;
var popupEvent = null;
var uploadFileURL = new Array();	//存放附件的url
var loopNum = 0;	//在拼装复合对象请求进行保存操作时，记录上传附件model的循环次数

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
   	Workflow.hideTaskToolbar();
   		if(stepcode.getValue() == "05" || stepcode.getValue() == "25")
	    {
	    	tbbreturn.hide();	
	    }
	 }
	 else
	 {
	 	tbbsave.hide();
    tbbsubmit.hide();
    tbbreturn.hide();
    shopid.setValue(application.event.data);		
	 }
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
    
    var getCompleteXMLDocument = cordys.cloneXMLDocument(getCompleteXML.XMLDocument);
    cordys.setNodeText(getCompleteXMLDocument,".//*[local-name()='formID']","");
    cordys.setNodeText(getCompleteXMLDocument,".//*[local-name()='shopID']","78e3b51c-e998-11e0-f9f5-a53166a55fba");
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
    if(insertOperation == 1)
    {
         bindDataToModels(responseNode);
         insertOperation=0;    
    }
}

/********************************************************************
 * 功能名称	: do_CompositeShopTransInfoModel_OnResponse();
 * 描述: 将复合对象的响应结构拆分到子对象中，将数据显示到页面
 * 输入参数：responseNode，响应节点
 * 输出参数：无
 */
function bindDataToModels(responseNode)
{
    if(insertOperation == 0)
    {
        if(Workflow.getProcessInstanceId()!=null)
       { 
           tabapprovalhistory.create();
        }  
        if(cordys.selectXMLNode(responseNode,".//*[local-name()='ATTACHMENT']"))
        {
            var attachmentDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
            var nodeList = cordys.selectXMLNodes(responseNode,".//*[local-name()='ATTACHMENT']");
            for ( var i=0, length=nodeList.length; i < length; i++ )
            {
                var attachmentNode = cordys.selectXMLNode(nodeList[i],  ".");
                cordys.appendXMLNode(attachmentNode,cordys.selectXMLNode(attachmentDataNode,".//*[local-name()='tuple'][last()]/*[local-name()='old']"));
                if(i<length-1){
                    var dataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
                    cordys.appendXMLNode(cordys.selectXMLNode(dataNode,".//*[local-name()='tuple']"),cordys.selectXMLNode(attachmentDataNode,".//*[local-name()='GetResponse']"));
                }
    
            }
            GetAttachmentModel.clear();
            GetAttachmentModel.putData(attachmentDataNode);
            GetAttachmentModel.refreshAllViews();
            downloadFile(-1);
        }
    }
    else
    {
        if(cordys.selectXMLNode(responseNode,".//*[local-name()='new']/*[local-name()='CompositeShopTransInfo']/*[local-name()='ATTACHMENT']"))
        {
            var dataNode4 = cordys.cloneXMLDocument(dataXML.XMLDocument);
            var nodeList = cordys.selectXMLNodes(responseNode,".//*[local-name()='new']/*[local-name()='CompositeShopTransInfo']/*[local-name()='ATTACHMENT']");
            for ( var i=0, length=nodeList.length; i < length; i++ )
            {
                var attachmentNode = cordys.selectXMLNode(nodeList[i],  ".");
                cordys.appendXMLNode(attachmentNode,cordys.selectXMLNode(dataNode4,".//*[local-name()='tuple'][last()]/*[local-name()='old']"));
                if(i<length-1)
                {
                    var dataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
                    cordys.appendXMLNode(cordys.selectXMLNode(dataNode,".//*[local-name()='tuple']"),cordys.selectXMLNode(dataNode4,".//*[local-name()='GetResponse']"));
                }

            }
            GetAttachmentModel.clear();
            GetAttachmentModel.putData(dataNode4);
            GetAttachmentModel.refreshAllViews();
            downloadFile();
        }
    }
}

/********************************************************************
 * 功能名称	: downloadFile();
 * 描述: 下载附件
 * 输入参数：rowInx，table中所选中的行标
 * 输出参数：无
 */
function downloadFile(rowInx)
{
    if(rowInx == null)
    {
			var rows=File_RepositoryTable.getRows();
			var tmpFileURL = new Array();
      for (var i=1;i<rows.length+1; i++) {
      	
      	for(var j=0;j<uploadFileURL.length;j++)
      	{
      		if(uploadFileURL[j])
      		{
      				tmpFileURL[i] = uploadFileURL[j];
      		}	
      	}
        filename[i].style.color = "blue";
        cordys.removeDOMListener(filename[i], "onclick", completeDownloadFile);
				cordys.addDOMListener(filename[i], "onclick", completeDownloadFile);
			}	
			uploadFileURL = tmpFileURL;
    }
    else if(rowInx == -1)
    {
    	var rows=File_RepositoryTable.getRows();
    	var urlNodes = cordys.selectXMLNodes(GetAttachmentModel.getData(),".//*[local-name()='FILEURL']");
    	for (var i=1;i<rows.length+1; i++) {
    		uploadFileURL[i] = cordys.getTextContent(urlNodes.item(i-1));
    		filename[i].style.color = "blue";
        cordys.removeDOMListener(filename[i], "onclick", completeDownloadFile);
				cordys.addDOMListener(filename[i], "onclick", completeDownloadFile);
    	}
    }	
    else
    {
    	filename[rowInx].style.color = "blue";
      cordys.removeDOMListener(filename[rowInx], "onclick", completeDownloadFile);
    	cordys.addDOMListener(filename[rowInx], "onclick", completeDownloadFile);
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
    var urlNode = uploadFileURL[rowInx];
    var request = DownloadFileModel.getMethodRequest();
    cordys.setNodeText(request,".//*[local-name()='id']",urlNode);
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
 * 功能名称	: do_tbbsave_Click();
 * 描述: 点击工具栏的保存按钮，触发此方法
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_tbbsave_Click(eventObject)
{
    //必填项校验
    var isFlag=validateCommon(this.document);
    if(!isFlag){
       return false;
    }
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
	//定义3个tuple nodes, 用于放置insert, update & delete结构.
    tupleNodeForInsert = null;
    tupleNodeForUpdate = null;
    tupleNodeForDelete = null;
    var applicationModels = WebForm.modelGlobalArray[application.container.applicationId].models;
    for (var modelId in applicationModels) 
    { 
       var model = applicationModels[modelId]; 
       if(model.id == "CompositeShopTransInfoModel" || model.id == "GetApprovalHistoryObjectModel" || model.id == "DownloadFileModel" || model.id == "DeleteFileModel" || model.id == "ValidateModel") continue; 
       prepareRequest(model);
    }

    updateRequestDocument = cordys.cloneXMLDocument(updateXML.XMLDocument);
    var UpdateNode = cordys.selectXMLNode(updateRequestDocument,".//*[local-name()='Update']");
    if(tupleNodeForInsert)
    {
         cordys.appendXMLNode(tupleNodeForInsert.documentElement,UpdateNode);
         tupleNodeForInsert = null;  
    }
    if(tupleNodeForUpdate)
    {
         cordys.appendXMLNode(tupleNodeForUpdate.documentElement,UpdateNode);
         tupleNodeForUpdate = null;
    }
    if(tupleNodeForDelete)
    {
         cordys.appendXMLNode(tupleNodeForDelete.documentElement,UpdateNode);
         tupleNodeForDelete = null;   
    }

    CompositeShopTransInfoModel.setMethodRequest(updateRequestDocument);
    CompositeShopTransInfoModel.reset();
    CompositeShopTransInfoModel.refreshAllViews();
}

/********************************************************************
 * 功能名称	: prepareRequest();
 * 描述: 为保存准备请求结构
 * 输入参数：model，xform中的model对象
 * 输出参数：无
 */
function prepareRequest(model)
{ 
    if(model.id == "GetAttachmentModel" || model.id == "GetShopTransDistributionObjectModel")
    {
        var tupleNodes = cordys.selectXMLNodes(model.getData(), ".//*[local-name()='tuple']");
        for(var i = 0;i < tupleNodes.length; i++)
        {
            var tupleNode = cordys.selectXMLNode(tupleNodes[i],".");
            loopNum = i;
            setRequests(tupleNode,model);
        }
        loopNum = 0;
    }
    else
    {
        var tupleNode = cordys.selectXMLNode(model.getData(),".//*[local-name()='tuple']");
        setRequests(tupleNode,model);
    }
       
}

/********************************************************************
 * 功能名称	: setRequests();
 * 描述: 将子对象结构按照增删改的不同，
 *       分别追加到tupleNodeForInsert、tupleNodeForDelete和tupleNodeForUpdate
 * 输入参数：tupleNode和model
 * 输出参数：无
 */
function setRequests(tupleNode,model)
{
    var hasModified = WebForm.getAttribute(tupleNode, "sync_id", false);
    if(hasModified > 0)
    {
       insertOperation = 1;
       //判断子对象结构中old和new节点是否存在
       var oldNode = cordys.selectXMLNode(tupleNode,".//*[local-name()='old']");
       var newNode = cordys.selectXMLNode(tupleNode,".//*[local-name()='new']");
       if(oldNode)
       {
          //old & new节点都在,表明是update  
          if(newNode)
          {
              if(tupleNodeForUpdate == null)
              {
                 tupleNodeForUpdate = cordys.cloneXMLDocument(tupleUpdateXML.XMLDocument);
              }
              var childOldNode = cordys.selectXMLNode(oldNode,".//*[local-name()='" + model.instance.businessObjectName + "']");
              var parentOldNode = cordys.selectXMLNode(tupleNodeForUpdate,".//*[local-name()='old']/*[local-name()='CompositeShopTransInfo']");
              if(childOldNode!=null){
                 cordys.appendXMLNode(childOldNode,parentOldNode);
                 var childNewNode = cordys.selectXMLNode(newNode,".//*[local-name()='" + model.instance.businessObjectName + "']");
                 var parentNewNode = cordys.selectXMLNode(tupleNodeForUpdate,".//*[local-name()='new']/*[local-name()='CompositeShopTransInfo']");
                 cordys.appendXMLNode(childNewNode,parentNewNode);
              }
              return;
          }
          
          //只有old节点，表明是delete.
          if(tupleNodeForDelete!=null)
          {
             tupleNodeForDelete = cordys.cloneXMLDocument(tupleOldXML.XMLDocument);
          }
          var childOldNode = cordys.selectXMLNode(oldNode,".//*[local-name()='" + model.instance.businessObjectName + "']");
          if(childOldNode!=null){
            var parentOldNode = cordys.selectXMLNode(tupleNodeForDelete,".//*[local-name()='CompositeShopTransInfo']");
            cordys.appendXMLNode(childOldNode,parentOldNode);
          }
          return;
       }
       else
       {
           //只有new节点，表明是new  
          if(newNode)
          {
              if(tupleNodeForInsert == null)
              {
                 tupleNodeForInsert = cordys.cloneXMLDocument(tupleNewXML.XMLDocument);
              }
              var childNewNode = cordys.selectXMLNode(newNode,".//*[local-name()='" + model.instance.businessObjectName + "']");
              var parentNewNode = cordys.selectXMLNode(tupleNodeForInsert,".//*[local-name()='CompositeShopTransInfo']");
              if(childNewNode!=null){
                  cordys.appendXMLNode(childNewNode,parentNewNode);
                  if(model.id == "GetApprovalHistoryModel")
                  {
                       //点击保存按钮时，添加ext1标志位，用于区分打开待办时审批意见是update还是insert
                       if(submitOperation == 0)
                       {
                                  var ext1Node = cordys.createElementNS(parentNewNode.ownerDocument,"http://schemas.cordys.com/tbpm/shop","EXT1");
                                  cordys.setTextContent(ext1Node, Workflow.getTaskInstanceId());
                                  cordys.appendXMLNode(ext1Node, cordys.selectXMLNode(parentNewNode,".//*[local-name()='" + model.instance.businessObjectName + "']"));
                       }
                       var instanceNode = cordys.createElementNS(parentNewNode.ownerDocument,"http://schemas.cordys.com/tbpm/shop","INSTANCE_ID");
                       var formIdNode = cordys.createElementNS(parentNewNode.ownerDocument,"http://schemas.cordys.com/tbpm/shop","FORM_ID");
                       var stepCdNode = cordys.createElementNS(parentNewNode.ownerDocument,"http://schemas.cordys.com/tbpm/shop","STEP_CODE");
                       var stepNmNode = cordys.createElementNS(parentNewNode.ownerDocument,"http://schemas.cordys.com/tbpm/shop","STEP_NAME");
                       var chargerCdNode = cordys.createElementNS(parentNewNode.ownerDocument,"http://schemas.cordys.com/tbpm/shop","CHARGER_CODE");
                       
                       cordys.setTextContent(instanceNode,Workflow.getProcessInstanceId());
                       cordys.setTextContent(formIdNode, formid.getValue());
                       cordys.setTextContent(stepCdNode, stepcode.getValue());
                       cordys.setTextContent(stepNmNode, stepname.getValue());
                       cordys.setTextContent(chargerCdNode, chargercode.getValue());
                       
                       cordys.appendXMLNode(instanceNode, cordys.selectXMLNode(parentNewNode,".//*[local-name()='" + model.instance.businessObjectName + "']"));
                       cordys.appendXMLNode(formIdNode, cordys.selectXMLNode(parentNewNode,".//*[local-name()='" + model.instance.businessObjectName + "']"));
                       cordys.appendXMLNode(stepCdNode, cordys.selectXMLNode(parentNewNode,".//*[local-name()='" + model.instance.businessObjectName + "']"));
                       cordys.appendXMLNode(stepNmNode, cordys.selectXMLNode(parentNewNode,".//*[local-name()='" + model.instance.businessObjectName + "']"));
                       cordys.appendXMLNode(chargerCdNode, cordys.selectXMLNode(parentNewNode,".//*[local-name()='" + model.instance.businessObjectName + "']"));
                  }
                  if(model.id == "GetShopTransEngObjectModel" || model.id == "GetShopTransDistributionObjectModel")
                  {
                       var formIdNode = cordys.createElementNS(parentNewNode.ownerDocument,"http://schemas.cordys.com/tbpm/shop","FORM_ID");
                       cordys.setTextContent(formIdNode, formid.getValue());
                       cordys.appendXMLNode(formIdNode, cordys.selectXMLNode(parentNewNode,".//*[local-name()='" + model.instance.businessObjectName + "'][last()]")); 
                  }
                  if(model.id == "GetShopTransInfoObjectModel")
                  {
                      var shopIdNode = cordys.selectXMLNode(parentNewNode,".//*[local-name()='" + model.instance.businessObjectName + "']/*[local-name()='SHOP_ID']");
                      cordys.setXMLAttribute(shopIdNode,"","null","false");
                      cordys.setTextContent(shopIdNode, shopid.getValue()); 
                      var formTypeNode = cordys.selectXMLNode(parentNewNode,".//*[local-name()='" + model.instance.businessObjectName + "']/*[local-name()='FORM_TYPE']");
                      cordys.setXMLAttribute(formTypeNode,"","null","false");
                      cordys.setTextContent(formTypeNode, "0");	//formType=0表示是一般门店
                  }
                  if(model.id == "GetAttachmentModel")
                  {
                      var fileUrlNode = cordys.createElementNS(parentNewNode.ownerDocument,"http://schemas.cordys.com/tbpm/shop","FILEURL");
                      var deptCdNode = cordys.createElementNS(parentNewNode.ownerDocument,"http://schemas.cordys.com/tbpm/shop","DEPT_CODE");
                      var userCdNode = cordys.createElementNS(parentNewNode.ownerDocument,"http://schemas.cordys.com/tbpm/shop","OPERATOR_CODE");
                      var formIdNode = cordys.createElementNS(parentNewNode.ownerDocument,"http://schemas.cordys.com/tbpm/shop","FORM_ID");
                      var instanceIdNode = cordys.createElementNS(parentNewNode.ownerDocument,"http://schemas.cordys.com/tbpm/shop","INSTANCE_ID");
                      cordys.setTextContent(fileUrlNode, uploadFileURL[loopNum+1]);
                      cordys.setTextContent(deptCdNode, "10010");
                      cordys.setTextContent(userCdNode, "1909011");
                      cordys.setTextContent(formIdNode, "3779-099-9889");
                      cordys.setTextContent(instanceIdNode, "228-39-0-098777");
                      cordys.appendXMLNode(fileUrlNode, cordys.selectXMLNode(parentNewNode,".//*[local-name()='" + model.instance.businessObjectName + "'][last()]"));
                      cordys.appendXMLNode(deptCdNode, cordys.selectXMLNode(parentNewNode,".//*[local-name()='" + model.instance.businessObjectName + "'][last()]"));
                      cordys.appendXMLNode(userCdNode, cordys.selectXMLNode(parentNewNode,".//*[local-name()='" + model.instance.businessObjectName + "'][last()]"));
                      cordys.appendXMLNode(formIdNode, cordys.selectXMLNode(parentNewNode,".//*[local-name()='" + model.instance.businessObjectName + "'][last()]"));
                      cordys.appendXMLNode(instanceIdNode, cordys.selectXMLNode(parentNewNode,".//*[local-name()='" + model.instance.businessObjectName + "'][last()]"));
                  }
              }
              return;
          }
       }
   }
}

/********************************************************************
 * 功能名称	: do_gpxprojectinfo_OnExpand();
 * 描述: 展开工程groupbox时判断是否需要创建新记录
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_gpxprojectinfo_OnExpand(eventObject)
{
    if(!cordys.selectXMLNode(GetShopTransEngObjectModel.getData(), ".//*[local-name()='SHOP_TRANS_ENG']"))
    {
        gpxprojectinfo.create();
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
    deptNm[rowInx].setValue(departmentname.getValue());
    userNm[rowInx].setValue(username.getValue());
    uploadFileURL[rowInx] = fileUrl;
    downloadFile(rowInx);
    }
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
    for(var i = 0;i < rows.length;i++)
    {
        //在文件系统中删除文件
        var rowInx = rows[i].sectionRowIndex;
        var guid = uploadFileURL[rowInx];
        var request = DeleteFileModel.getMethodRequest();
        cordys.setNodeText(request,".//*[local-name()='guid']",guid);
        DeleteFileModel.setMethodRequest(request);
        DeleteFileModel.reset();
				//在存储url的数组中删除被删除的记录
        delete uploadFileURL[rowInx];
    }
}

/********************************************************************
 * 功能名称	: do_tbbsubmit_Click();
 * 描述: 点击工具栏的提交按钮，触发此操作
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_tbbsubmit_Click(eventObject)
{
    //必填项校验
    var isFlag=validateCommon(this.document);
    if(!isFlag){
       return false;
    }
    //弹出提交的确认框
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
    if (confirmReturnValue == 1)
    {
           submitOperation = 1;
           decisionattribute.setValue("01");		
           saveForm();
           submitOperation = 0;
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
		//必填项校验
    var isFlag=validateCommon(this.document);
    if(!isFlag){
       return false;
    }
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
    if (confirmReturnValue == 1)
    {
    		submitOperation = 1;
    		if(stepcode.getValue() == "20")
    		{
    				application.showDialog(dialogRevert.XMLDocument.documentElement,null, null, GetRevertValue, false);
    				placePopup(popupEvent);
    		}
    		else
    		{
    				decisionattribute.setValue("03");
    				saveForm();
    				submitOperation = 0;
    		 		Workflow.completeTask();
    		}
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
    
    //decisionattribute赋值后完成当前任务
    if(decisionattribute.getValue() != "" && decisionattribute.getValue() != null)
    {
        saveForm();
        submitOperation = 0;
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
    getDialogValue(eventObject,salesorg,salesorgname,"TS_Resources","销售组织列表","320px","230px","1");
}

/********************************************************************
 * 功能名称	: do_imgbussattr_Click();
 * 描述: 调用商圈性质列表弹出页
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_imgbussattr_Click(eventObject)
{
    getDialogValue(eventObject,bussattr,bussattrname,"BUSS_ATTR","商圈性质列表","320px","230px","1");
}

/********************************************************************
 * 功能名称	: do_imgshopattr_Click();
 * 描述: 调用门店性质列表弹出页
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_imgshopattr_Click(eventObject)
{
    getDialogValue(eventObject,shopattr,shopattrname,"SHOP_ATTR","门店性质列表","320px","230px","1");
}

/********************************************************************
 * 功能名称	: do_imgbayname_Click();
 * 描述: 调用开间列表弹出页
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_imgbayname_Click(eventObject)
{
    getDialogValue(eventObject,baycode,bayname,"ROOM_TYPE","开间列表","320px","230px","1");
}

/********************************************************************
 * 功能名称	: do_CompositeShopTransInfoModel_OnSOAPFault();
 * 描述: 与后台交互发生soap请求失败时，提示友好信息
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_CompositeShopTransInfoModel_OnSOAPFault(eventObject)
{
    eventObject.showError = false;
    application.notify("您的操作未能成功执行，请您重新尝试或联系系统管理员，谢谢");	
}

/********************************************************************
 * 功能名称	: do_tbbbpmmonitor_Click();
 * 描述: 点击工具栏的监控图按钮，调用此方法
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_tbbbpmmonitor_Click(eventObject)
{
}
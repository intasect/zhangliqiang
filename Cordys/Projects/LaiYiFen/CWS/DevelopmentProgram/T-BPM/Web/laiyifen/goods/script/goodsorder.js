var insertOperation = 0;
var tupleNodeForInsert = null;
var tupleNodeForUpdate = null;
var tupleNodeForDelete = null;

var submitOperation = 0;
var prepareDone = null;
var userArray = new Array();
var formTempId = "";

//刷新复合对象模型数据
function refreshModels(eventObject)
{
     var responseNode = eventObject.response;
     //操作数据后设置FormId供下条流程使用
     formTempId =cordys.getTextContent(cordys.selectXMLNode(responseNode ,".//*[local-name()='FORM_ID']"));
    if(insertOperation == 1)
    {
         bindDataToModels(responseNode);
         insertOperation=0;    
    }
}

function do_Form_Init(eventObject)
{
    gpxhide.hide();
    if(stepcode.getValue() == "01")
    {
    		tbbReturn.hide();	
    }
  
		//隐藏待办页面缺省的按钮
		if(Workflow!=null) Workflow.hideTaskToolbar();

     //初始化每月事件
     for (var i = 1; i < 13; i++) 
    { 
           document.getElementById("iptMonth"+i).onchange = function() { 
           setSumValue(); 
     } 
}

}


//页面初始化
function initForm(eventObject)
{
    //WebForm.getLayoutElement(gbxshopannual).style.height = "auto";
    //WebForm.getLayoutElement(gbxshopannual).className += "autoextent";
    //隐藏formid,shopid字段
    for(var i = 6; i<11; i++)
   {
      File_RepositoryTable.hideColumn(i);
   } 

    if(Workflow.getProcessInstanceId() == undefined || Workflow.getProcessInstanceId() == "")
    {
         gbxuserinfo.create();
         if(undefined != application.event.data && undefined != application.event.data.subformid && null != application.event.data.subformid)
        {
              var getCompleteXMLDocument = cordys.cloneXMLDocument(getCompleteXML.XMLDocument);
               //getCompleteXMLDocument.selectSingleNode(".//Form_ID").text =formid.getValue();
              cordys.setNodeText(getCompleteXMLDocument,".//*[local-name()='formId']", application.event.data.subformid);
              CompositeAnnualDevPlanInfoModel.setMethodRequest(getCompleteXMLDocument);
              CompositeAnnualDevPlanInfoModel.reset();
              var compositeData=CompositeAnnualDevPlanInfoModel.getData();
              bindDataToModels(compositeData);
              setSumValue();
        }

    }
    else
    {
        var getCompleteXMLDocument = cordys.cloneXMLDocument(getCompleteXML.XMLDocument);
        //getCompleteXMLDocument.selectSingleNode(".//Form_ID").text =formid.getValue();
        cordys.setNodeText(getCompleteXMLDocument,".//*[local-name()='formId']",formid.getValue());
        CompositeAnnualDevPlanInfoModel.setMethodRequest(getCompleteXMLDocument);
        CompositeAnnualDevPlanInfoModel.reset();
        var compositeData=CompositeAnnualDevPlanInfoModel.getData();
        bindDataToModels(compositeData);
        setSumValue();
    }
}

//保存按钮保存数据
function saveCompositeObject()
{   

var isFlag=validateCommon(this.document);
    if(isFlag=="false"){
       return false;
    }


    //Define 3 tuple nodes, which are used to insert, update & delete respectively.
    tupleNodeForInsert = null;
    tupleNodeForUpdate = null;
    tupleNodeForDelete = null;
    var applicationModels = WebForm.modelGlobalArray[application.container.applicationId].models;
    for (var modelId in applicationModels) 
    { 
       var model = applicationModels[modelId]; 
       if(model.id == "CompositeAnnualDevPlanInfoModel" || model.id == "GetApprovalHistoryModel" || model.id == "DownloadFileModel" || model.id == "DeleteFileModel" || model.id == "ExecuteProcessModel" || model.id == "UserModel" || model.id == "ValidateModel") continue; 
       prepareRequest(model);
    }
     
     //不修改数据点击保存按钮直接跳出
     if(null == tupleNodeForInsert && null == tupleNodeForUpdate && null == tupleNodeForDelete)
    {
         return;
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

    CompositeAnnualDevPlanInfoModel.setMethodRequest(updateRequestDocument);
    CompositeAnnualDevPlanInfoModel.reset();
    CompositeAnnualDevPlanInfoModel.refreshAllViews();
}

//组装设置请求报文
function prepareRequest(model)
{
    if(model.id == "GetAttachmentModel")
    {
        var tupleNodes = cordys.selectXMLNodes(model.getData(), ".//*[local-name()='tuple']");
        for(var i = 0;i < tupleNodes.length; i++)
        {
            var tupleNode = cordys.selectXMLNode(tupleNodes[i],".");
            setRequests(tupleNode,model);
        }
    }
    else
    {
        var tupleNode = cordys.selectXMLNode(model.getData(),".//*[local-name()='tuple']");
        setRequests(tupleNode,model);
    }
       
}

//组装设置请求报文
function setRequests(tupleNode,model)
{
    var hasModified = WebForm.getAttribute(tupleNode, "sync_id", false);
    if(hasModified > 0)
    {
       insertOperation = 1;
       //Check the old and new nodes are there in tuple.
       var oldNode = cordys.selectXMLNode(tupleNode,".//*[local-name()='old']");
       var newNode = cordys.selectXMLNode(tupleNode,".//*[local-name()='new']");
       if(oldNode)
       {
          //if old & new node is there, then it is update tuple  
          if(newNode)
          {
              if(tupleNodeForUpdate == null)
              {
                 tupleNodeForUpdate = cordys.cloneXMLDocument(tupleUpdateXML.XMLDocument);
              }
              var childOldNode = cordys.selectXMLNode(oldNode,".//*[local-name()='" + model.instance.businessObjectName + "']");
              var parentOldNode = cordys.selectXMLNode(tupleNodeForUpdate,".//*[local-name()='old']/*[local-name()='CompositeAnnualDevPlanInfo']");
              cordys.appendXMLNode(childOldNode,parentOldNode);
              
              var childNewNode = cordys.selectXMLNode(newNode,".//*[local-name()='" + model.instance.businessObjectName + "']");
              var parentNewNode = cordys.selectXMLNode(tupleNodeForUpdate,".//*[local-name()='new']/*[local-name()='CompositeAnnualDevPlanInfo']");
              cordys.appendXMLNode(childNewNode,parentNewNode);

              //设置流程状态(0为草稿，1为流程中)
              if(model.id == "GetShopMonthlyPlanObjectModel")
              {
                   var statusNode  = cordys.selectXMLNode(parentNewNode,".//*[local-name()='SHOP_MONTHLY_PLAN']/*[local-name()='STATUS']");
                 if(Workflow.getProcessInstanceId() != undefined && Workflow.getProcessInstanceId() != "")
                 {
                    cordys.setXMLAttribute(statusNode,"","null","false");
                    cordys.setTextContent(statusNode, "1");
                 }
                  else
                 {
                    cordys.setXMLAttribute(statusNode,"","null","false");
                    cordys.setTextContent(statusNode, "0");
                 }
                }
              return;
          }
          
          //Default oldNode is there, so it is delete tuple.
          if(tupleNodeForDelete == null)
          {
             tupleNodeForDelete = cordys.cloneXMLDocument(tupleOldXML.XMLDocument);
          }
          var childOldNode = cordys.selectXMLNode(oldNode,".//*[local-name()='" + model.instance.businessObjectName + "']");
          var parentOldNode = cordys.selectXMLNode(tupleNodeForDelete,".//*[local-name()='CompositeAnnualDevPlanInfo']");
          cordys.appendXMLNode(childOldNode,parentOldNode);
          return;
       }
       else
       {
           //if new node is there, then it is insert tuple  
          if(newNode)
          {
              if(tupleNodeForInsert == null)
              {
                 tupleNodeForInsert = cordys.cloneXMLDocument(tupleNewXML.XMLDocument);
              }
              var childNewNode = cordys.selectXMLNode(newNode,".//*[local-name()='" + model.instance.businessObjectName + "']");
              var parentNewNode = cordys.selectXMLNode(tupleNodeForInsert,".//*[local-name()='CompositeAnnualDevPlanInfo']");
              cordys.appendXMLNode(childNewNode,parentNewNode);
              if(model.id == "GetApprovalModel")
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
              //设置流程状态(0为草稿，1为流程中)
              if(model.id == "GetShopMonthlyPlanObjectModel")
              {
                   var statusNode  = cordys.selectXMLNode(tupleNodeForInsert,".//*[local-name()='SHOP_MONTHLY_PLAN']/*[local-name()='STATUS']");
                 if(Workflow.getProcessInstanceId() != undefined && Workflow.getProcessInstanceId() != "")
                 {
                 		cordys.setXMLAttribute(statusNode,"","null","false");
                    cordys.setTextContent(statusNode, "1");
                 }
                  else
                 {
                		cordys.setXMLAttribute(statusNode,"","null","false");
                    cordys.setTextContent(statusNode, "0");
                 }
              }
              return;
          }
       }
   }
}

//绑定模型数据
function bindDataToModels(responseNode)
{
    if(insertOperation == 0)
    {
    		//在流程启动后才会开启保存审批意见
     		if(Workflow.getProcessInstanceId() != undefined && Workflow.getProcessInstanceId() != "")
     		{
        		tabapprovalhistory.create();
      	        }
      
        if(cordys.selectXMLNode(responseNode,".//*[local-name()='SHOP_MONTHLY_PLAN']"))
        {
            var shopInfoDataNode = dataXML.XMLDocument.cloneNode(true);
            cordys.appendXMLNode(cordys.selectXMLNode(responseNode,".//*[local-name()='SHOP_MONTHLY_PLAN']"),cordys.selectXMLNode(shopInfoDataNode,".//*[local-name()='old']"));
            GetShopMonthlyPlanObjectModel.clear();
            GetShopMonthlyPlanObjectModel.putData(shopInfoDataNode);
            GetShopMonthlyPlanObjectModel.refreshAllViews();
        }
        
        if(cordys.selectXMLNode(responseNode,".//*[local-name()='ATTACHMENT']"))
        {
            var attachmentDataNode = dataXML.XMLDocument.cloneNode(true);
            var nodeList = cordys.selectXMLNodes(responseNode,".//*[local-name()='ATTACHMENT']");
            for ( var i=0, length=nodeList.length; i < length; i++ )
            {
                var attachmentNode = cordys.selectXMLNode(nodeList[i],  ".");
                cordys.appendXMLNode(attachmentNode,cordys.selectXMLNode(attachmentDataNode,".//*[local-name()='tuple'][last()]/*[local-name()='old']"));
                if(i<length-1){
                    var dataNode = dataXML.XMLDocument.cloneNode(true);
                    cordys.appendXMLNode(cordys.selectXMLNode(dataNode,".//*[local-name()='tuple']"),cordys.selectXMLNode(attachmentDataNode,".//*[local-name()='data']"));
                }
    
            }
            GetAttachmentModel.clear();
            GetAttachmentModel.putData(attachmentDataNode);
            GetAttachmentModel.refreshAllViews();
            downloadFile();
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
            var approvalHistoryDataNode = dataXML.XMLDocument.cloneNode(true);
            var nodeList = cordys.selectXMLNodes(responseNode,".//*[local-name()='APPROVAL_HISTORY']");
            for ( var i=0, length=nodeList.length; i < length; i++ )
            {
                var approvalHistoryNode = cordys.selectXMLNode(nodeList[i],  ".");
                cordys.appendXMLNode(approvalHistoryNode,cordys.selectXMLNode(approvalHistoryDataNode,".//*[local-name()='tuple'][last()]/*[local-name()='old']"));
                if(i<length-1){
                    var dataNode = dataXML.XMLDocument.cloneNode(true);
                    cordys.appendXMLNode(cordys.selectXMLNode(dataNode,".//*[local-name()='tuple']"),cordys.selectXMLNode(approvalHistoryDataNode,".//*[local-name()='data']"));
                }
    
            }
            GetApprovalHistoryModel.clear();
            GetApprovalHistoryModel.putData(approvalHistoryDataNode);
            GetApprovalHistoryModel.refreshAllViews();
        }
     
    }
    else
    {
        if(cordys.selectXMLNode(responseNode,".//*[local-name()='new']/*[local-name()='CompositeAnnualDevPlanInfo']/*[local-name()='SHOP_MONTHLY_PLAN']"))
        {
            var dataNode = dataXML.XMLDocument.cloneNode(true);  
            cordys.appendXMLNode(cordys.selectXMLNode(responseNode,".//*[local-name()='new']/*[local-name()='CompositeAnnualDevPlanInfo']/*[local-name()='SHOP_MONTHLY_PLAN']"),cordys.selectXMLNode(dataNode,".//*[local-name()='old']"));
            GetShopMonthlyPlanObjectModel.clear();
            GetShopMonthlyPlanObjectModel.putData(dataNode);
            GetShopMonthlyPlanObjectModel.refreshAllViews();
        }
        if(cordys.selectXMLNode(responseNode,".//*[local-name()='new']/*[local-name()='CompositeAnnualDevPlanInfo']/*[local-name()='ATTACHMENT']"))
        {
            var dataNode4 = dataXML.XMLDocument.cloneNode(true);
            var nodeList = cordys.selectXMLNodes(responseNode,".//*[local-name()='new']/*[local-name()='CompositeAnnualDevPlanInfo']/*[local-name()='ATTACHMENT']");
            for ( var i=0, length=nodeList.length; i < length; i++ )
            {
                var attachmentNode = cordys.selectXMLNode(nodeList[i],  ".");
                cordys.appendXMLNode(attachmentNode,cordys.selectXMLNode(dataNode4,".//*[local-name()='tuple'][last()]/*[local-name()='old']"));
                if(i<length-1)
                {
                    var dataNode = dataXML.XMLDocument.cloneNode(true);
                    cordys.appendXMLNode(cordys.selectXMLNode(dataNode,".//*[local-name()='tuple']"),cordys.selectXMLNode(dataNode4,".//*[local-name()='data']"));
                }

            }
            GetAttachmentModel.clear();
            GetAttachmentModel.putData(dataNode4);
            GetAttachmentModel.refreshAllViews();
            downloadFile();
        }

	//处理人暂存后再次打开待办时将审批意见显示到可编辑区域
    	 var approvalNodess = cordys.cloneXMLDocument(responseNode);
         if(cordys.selectXMLNode(approvalNodess ,".//*[local-name()='new']/*[local-name()='CompositeAnnualDevPlanInfo']/*[local-name()='APPROVAL_HISTORY'][*[local-name()='EXT1']!='']"))
        {
            var approvalNodeList = cordys.selectXMLNodes(approvalNodess ,".//*[local-name()='new']/*[local-name()='CompositeAnnualDevPlanInfo']/*[local-name()='APPROVAL_HISTORY'][*[local-name()='EXT1']!='']");
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

        if(cordys.selectXMLNode(responseNode,".//*[local-name()='new']/*[local-name()='CompositeAnnualDevPlanInfo']/*[local-name()='APPROVAL_HISTORY']"))
        {
            var dataNode5 = dataXML.XMLDocument.cloneNode(true);
            cordys.appendXMLNode(cordys.selectXMLNode(responseNode,".//*[local-name()='new']/*[local-name()='CompositeAnnualDevPlanInfo']/*[local-name()='APPROVAL_HISTORY']"),cordys.selectXMLNode(dataNode5,".//*[local-name()='old']"));
            GetApprovalHistoryModel.clear();
            GetApprovalHistoryModel.putData(dataNode5);
            GetApprovalHistoryModel.refreshAllViews();
            GetApprovalModel.refreshAllViews();
        }
    }
}

function downloadFile()
{
     var rows=File_RepositoryTable.getRows();
     for (var i=1;i<rows.length+1; i++) {
        var dirUrl= directory[i].getValue();
            filename[i].style.color = "blue";
            filename[i].onclick= completeDownloadFile;
     }
}



//保存按钮事件
function do_tbbsave_Click(eventObject)
{
    var isFlag=validateCommon(this.document);
    if(!isFlag){
       return false;
    }
    showConfirm(false,closeHandler);
}

function closeHandler(confirmReturnValue)
{
    if (confirmReturnValue == 1)
    {
          saveCompositeObject();
    }
    else if (confirmReturnValue == 0)
    {
    }
}


//提交按钮事件
function submitTask(eventObject)
{
	
	var isFlag=validateCommon(this.document);
    if(!isFlag){
       return false;
    }
    showConfirm(false,submitHandler,"您确定提交任务到下一环节吗？");
    
}

function submitHandler(confirmReturnValue)
{
		if (confirmReturnValue == 1)
		{
                                submitOperation = 1;
				saveCompositeObject();
				//start process
				if(Workflow.getProcessInstanceId()==undefined || Workflow.getProcessInstanceId()=="")
				{
						var request = executeProcess.XMLDocument.cloneNode(true);
						cordys.setNodeText(request,".//*[local-name()='receiver']","Business Process Models/com/laiyifen/shop/planning/网点年度开发计划");
						cordys.setNodeText(request,".//*[local-name()='FormID']",formTempId);
						cordys.setNodeText(request,".//*[local-name()='DecisionAttribute']","10");
						cordys.setNodeText(request,".//*[local-name()='ExecutionType']","1");
                                                cordys.setNodeText(request,".//*[local-name()='CompanyCode']",subcompanycode.getValue());
                                                cordys.setNodeText(request,".//*[local-name()='CompanyName']",subcompanyname.getValue());
                                                cordys.setNodeText(request,".//*[local-name()='CreateName']",username.getValue());
                                                cordys.setNodeText(request,".//*[local-name()='CreateTime']",applicationdate.getValue());                                                

						ExecuteProcessModel.clear();
						ExecuteProcessModel.setMethodRequest(request);
						ExecuteProcessModel.reset();
                                                application.container.close();
				}
				else
				{ 
						decisionattribute.setValue("01");
						Workflow.completeTask();
                                                application.container.close();
				}
		}
				else if (confirmReturnValue == 0)
		{
		}
}

//上传文件窗口关闭后事件
function fnAddFile(fileObject,fileUrl,fileName)
{
    File_RepositoryTable.create();
    var rowInx = File_RepositoryTable.getIndex();
    filename[rowInx].setValue(fileName);
    filetype[rowInx].setValue(cordys.getNodeText(fileObject,".//*[local-name()='FileType']"));
    directory[rowInx].setValue(fileUrl);
    deptCd[rowInx].setValue(departmentcode.getValue());
    deptNm[rowInx].setValue(departmentname.getValue());
    instanceId[rowInx].setValue(Workflow.getProcessInstanceId());
    formId[rowInx].setValue(formid.getValue());
    userId[rowInx].setValue(usercode.getValue());
    userNm[rowInx].setValue(username.getValue());
    downloadFile();
}

//驳回按钮执行事件
function do_tbbreturn_Click(eventObject)
{
    var isFlag=validateCommon(this.document);
    if(!isFlag){
       return false;
    }
    popupEvent = eventObject;
    showConfirm(false,returnHandler,"您确定执行驳回操作吗？");
}

function returnHandler(confirmReturnValue)
{
    if (confirmReturnValue == 1)
    {
    		submitOperation = 1;
 		application.showDialog(dialogRevert.XMLDocument.documentElement,null, null, GetRevertValue, false); 
                placePopup(eventObject);
    }
    else if (confirmReturnValue == 0)
    {
    }
}

//驳回按钮弹出框回调函数
function GetRevertValue(rtnValue)
{
    decisionattribute.setValue(rtnValue);
    
    //complete current task
    if(decisionattribute.getValue() != "" && decisionattribute.getValue() != null)
    {
        saveCompositeObject();
        Workflow.completeTask();
    }
    else
    {
        application.notify("请选择驳回位置");
    }
}

function completeDownloadFile()
{
    var rowInx = File_RepositoryTable.getIndex();
    id = directory[rowInx].getValue();
    var request = DownloadFileModel.getMethodRequest();
    cordys.setNodeText(request,".//*[local-name()='id']",id);
    DownloadFileModel.reset();
    var filePath = cordys.getNodeText(DownloadFileModel.getData(),".//*[local-name()='downloadFile']");
    if(filePath == "")
    {
       application.notify("This file does not exist or has been backed up elsewhere, please contact the system administrator.");
    }
   window.open(filePath);
}

//计算每月计划总数
function setSumValue()
{
      var sumValue = new Number("0") ;
      for(var i=1; i<13; i++)
     {
         if (""  != document.getElementById("iptMonth"+i).value)
         {
           sumValue= sumValue + parseInt(document.getElementById("iptMonth"+i).value);
         }
     }
     iptsum.setValue(sumValue);
}
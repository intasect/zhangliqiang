var insertOperation = 0;
var submitOperation = 0;
var popupEvent = null;
var updateDocTemplate ; 
var deleteDocTemplate; 
var insertDocTemplate;

var formID = "";
var modelCollection = new Array();
var uploadFileURL = new Array();	//存放附件的url


/********************************************************************
 * 功能名称	: do_Form_Init();
 * 描述: 初始化
 */
function do_Form_Init(eventObject)
{
    //隐藏待办页面缺省的按钮
   if(Workflow!=null) Workflow.hideTaskToolbar();
}

/********************************************************************
 * 功能名称	: Form_InitDone();
 * 描述: 初始化表单
 */
function do_Form_InitDone(eventObject)
{
	//WebForm.getLayoutElement(gbxshopannual).style.height = "auto";
    //WebForm.getLayoutElement(gbxshopannual).className += "autoextent"; 

	modelCollection["GetBpcFeeObjectModel"] = GetBpcFeeObjectModel;
    modelCollection["GetBpcAuditObjectModel"] = GetBpcAuditObjectModel;
    modelCollection["GetApprovalModel"] = GetApprovalModel;
	
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
	 if(Workflow.getProcessInstanceId() == undefined || Workflow.getProcessInstanceId() == "" )
    {
		
		//判断是否由上窗口打开	
		if(undefined != application.event.data && undefined != application.event.data.subformid && null != application.event.data.subformid)
		{
			var getCompleteXMLDocument = cordys.cloneXMLDocument(getCompleteXML.XMLDocument);
			cordys.setNodeText(getCompleteXMLDocument,".//*[local-name()='formID']", application.event.data.subformid);
			CompositeBPCFeeInfoModel.setMethodRequest(getCompleteXMLDocument);
			CompositeBPCFeeInfoModel.reset();
			var compositeData=CompositeBPCFeeInfoModel.getData();
			bindDataToModels(compositeData);
		}
		else
		{	//判断是否为新建打开
			if (formID ==""){
				gbxuserinfo.create();
				tbitemsinfo.create();
				gbxfinanceinfo.create();
				//tabapprovalhistory.create();
			}
			else
			{
				var getCompleteXMLDocument = cordys.cloneXMLDocument(getCompleteXML.XMLDocument);
				// getCompleteXMLDocument.selectSingleNode(".//Form_ID").text =formid.getValue();
				cordys.setNodeText(getCompleteXMLDocument,".//*[local-name()='formID']", formID);
				CompositeBPCFeeInfoModel.setMethodRequest(getCompleteXMLDocument);
				CompositeBPCFeeInfoModel.reset();
				var compositeData=CompositeBPCFeeInfoModel.getData();
				bindDataToModels(compositeData);
			}
		}	
    }
    else
    {
        var getCompleteXMLDocument = cordys.cloneXMLDocument(getCompleteXML.XMLDocument);
        //getCompleteXMLDocument.selectSingleNode(".//Form_ID").text =formid.getValue();
        cordys.setNodeText(getCompleteXMLDocument,".//*[local-name()='formID']",form_id.getValue());
        CompositeBPCFeeInfoModel.setMethodRequest(getCompleteXMLDocument);
        CompositeBPCFeeInfoModel.reset();
        var compositeData=CompositeBPCFeeInfoModel.getData();
        bindDataToModels(compositeData);
    }
}

/********************************************************************
 * 功能名称: saveCompositeObject();
 * 描述: 保存
 */
function saveCompositeObject()
{   
	
	debugger;
    var updateRequestDocument = cordys.cloneXMLDocument(updateXML.XMLDocument);		//复合对象的xml document
    updateDocTemplate = cordys.cloneXMLDocument(tupleUpdateXML.XMLDocument);		//存储所有更新节点的document
    deleteDocTemplate = cordys.cloneXMLDocument(tupleOldXML.XMLDocument);		//存储所有删除节点的document
    insertDocTemplate = cordys.cloneXMLDocument(tupleNewXML.XMLDocument);		//存储所有新增节点的document
    //对只有一个tuple的model进行复合对象请求准备
    for (var modelName in modelCollection)
    {
        var model = modelCollection[modelName];
        var modelData = cordys.cloneXMLDocument(model.getData());
           var tupleNode = cordys.selectXMLNode(modelData, ".//*[local-name()='tuple']");
        var bUpdateable = isModelUpdatable(tupleNode);
        if(!bUpdateable)  continue;
        var modeObject = addRequestObjectForModel(model,tupleNode,modelName);
            
    }
    // 对含有多个tuple的model进行复合对象请求准备
	getUpdateForMultiTuple(GetBpcFeedepObjectsModel);
	getUpdateForMultiTuple(GetBpcFeeItemObjectsModel);
    getUpdateForMultiTuple(GetAttachmentModel);
    
    var updateNode = cordys.selectXMLNode(updateRequestDocument, ".//*[local-name()='Update']");
    if(isValidToAdd(updateDocTemplate))        cordys.appendXMLNode(cordys.selectXMLNode(updateDocTemplate, ".//*[local-name()='tuple']"),updateNode);
    if(isValidToAdd(insertDocTemplate))        cordys.appendXMLNode(cordys.selectXMLNode(insertDocTemplate, ".//*[local-name()='tuple']"),updateNode);
    if(isValidToAdd(deleteDocTemplate))        cordys.appendXMLNode(cordys.selectXMLNode(deleteDocTemplate, ".//*[local-name()='tuple']"),updateNode);

    CompositeBPCFeeInfoModel.setMethodRequest(updateRequestDocument);
    CompositeBPCFeeInfoModel.reset();
    CompositeBPCFeeInfoModel.refreshAllViews();
}


/********************************************************************
 * 判断model是否有数据变化
 */
function isModelUpdatable(tupleNode)
{
    var hasModified = WebForm.getAttribute(tupleNode, "sync_id", false) >0?true:false;
    return hasModified;
}

/********************************************************************
 * 将model中的数据根据新增，修改和删除分别存放到不同的document中
 */
function addRequestObjectForModel(model,tupleNode,modelName)
{
    var oldNode = cordys.selectXMLNode(tupleNode,".//*[local-name()='old']");
    var newNode = cordys.selectXMLNode(tupleNode,".//*[local-name()='new']");
    var modelDataDoc = tupleNode.ownerDocument;
    var modelBObjName = model.instance.businessObjectName;
    if(oldNode)// it can be delete or update
    {
        if(newNode)// update
        {
            var childOldNode = cordys.selectXMLNode(oldNode,".//*[local-name()='" + modelBObjName+ "']");
            var updateOldNode = cordys.selectXMLNode(updateDocTemplate,".//*[local-name()='old']/*[local-name()='CompositeBPCFeeInfoModel']");
            if(childOldNode!=null)
            {
                cordys.appendXMLNode(childOldNode,updateOldNode);
                var childNewNode = cordys.selectXMLNode(newNode,".//*[local-name()='" + modelBObjName+ "']");
                var updateNewNode = cordys.selectXMLNode(updateDocTemplate,".//*[local-name()='new']/*[local-name()='CompositeBPCFeeInfoModel']");
                cordys.appendXMLNode(childNewNode,updateNewNode);
            }
        }
        else //delete
        {
            var childOldNode = cordys.selectXMLNode(oldNode,".//*[local-name()='" + model.instance.businessObjectName + "']");
            if(childOldNode!=null)
            {
                var updateOldNode = cordys.selectXMLNode(deleteDocTemplate,".//*[local-name()='CompositeBPCFeeInfoModel']");
                cordys.appendXMLNode(childOldNode,updateOldNode);
            }
        }
    }
    else //insert
    {
        if(newNode)
        {
            var childNewNode = cordys.selectXMLNode(newNode,".//*[local-name()='" + modelBObjName+ "']");
            var insertParentNewNode = cordys.selectXMLNode(insertDocTemplate,".//*[local-name()='CompositeBPCFeeInfoModel']");
            switch(modelName)
            {
                case "GetApprovalModel":
                    fillValuesOnGetApprovalModel(modelDataDoc,modelBObjName,tupleNode);
                break;
                
                case "GetAttachmentModel" :
					fillValuesOnGetAttachmentModel(modelDataDoc ,modelBObjName,tupleNode);
					break;
					
                case "GetBpcFeedepObjectsModel" :
					createFormId(modelDataDoc ,modelBObjName,tupleNode);
                break;
				
                case "GetBpcFeeItemObjectsModel" :
					createFormId(modelDataDoc ,modelBObjName,tupleNode);
                break;
				
                case "GetBpcAuditObjectModel":
                    createFormId(modelDataDoc ,modelBObjName,tupleNode);
                break;
                
            }
        }
        cordys.appendXMLNode(childNewNode,insertParentNewNode);       
    }
}

/********************************************************************
 * 为含有多个tuple的model准备复合对象请求
 */
function getUpdateForMultiTuple(model)
{
    var tupleNodes = cordys.selectXMLNodes(model.getData(), ".//*[local-name()='tuple']");
    for(var i = 0;i < tupleNodes.length; i++)
    {
        var tupleNode = cordys.selectXMLNode(tupleNodes[i],".");
        var bUpdateable = isModelUpdatable(tupleNode);
        if(!bUpdateable) return;
        addRequestObjectForModel(model,tupleNode);
    }
}

/********************************************************************
 * 判断准备好的document是否有数据存在
 */
function isValidToAdd(template)
{
    var isValid = false;
    var compositeShopTargetInfoNode = cordys.selectXMLNode(template, ".//*[local-name()='CompositeShopTargetInfo']");
    if(compositeShopTargetInfoNode && compositeShopTargetInfoNode.childNodes.length >0) isValid = true;
    return isValid;
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
		var ext1Node = cordys.createElementNS(parentNewNode.ownerDocument,"http://schemas.cordys.com/tbpm/bpc","EXT1");
		cordys.setTextContent(ext1Node, Workflow.getTaskInstanceId());
		cordys.appendXMLNode(ext1Node, cordys.selectXMLNode(parentNewNode,".//*[local-name()='" + model.instance.businessObjectName + "']"));
	}
	var instanceNode = cordys.createElementNS(parentNewNode.ownerDocument,"http://schemas.cordys.com/tbpm/bpc","INSTANCE_ID");
	var formIdNode = cordys.createElementNS(parentNewNode.ownerDocument,"http://schemas.cordys.com/tbpm/bpc","FORM_ID");
	var stepCdNode = cordys.createElementNS(parentNewNode.ownerDocument,"http://schemas.cordys.com/tbpm/bpc","STEP_CODE");
	var stepNmNode = cordys.createElementNS(parentNewNode.ownerDocument,"http://schemas.cordys.com/tbpm/bpc","STEP_NAME");
	var chargerCdNode = cordys.createElementNS(parentNewNode.ownerDocument,"http://schemas.cordys.com/tbpm/bpc","CHARGER_CODE");

	cordys.setTextContent(instanceNode,Workflow.getProcessInstanceId());
	cordys.setTextContent(formIdNode, formTempId);
	cordys.setTextContent(stepCdNode, stepcode.getValue());
	cordys.setTextContent(stepNmNode, stepname.getValue());
	cordys.setTextContent(chargerCdNode, chargercode.getValue());

	cordys.appendXMLNode(instanceNode, cordys.selectXMLNode(parentNewNode,".//*[local-name()='" + model.instance.businessObjectName + "'][last()]"));
	cordys.appendXMLNode(formIdNode, cordys.selectXMLNode(parentNewNode,".//*[local-name()='" + model.instance.businessObjectName + "'][last()]"));
	cordys.appendXMLNode(stepCdNode, cordys.selectXMLNode(parentNewNode,".//*[local-name()='" + model.instance.businessObjectName + "'][last()]"));
	cordys.appendXMLNode(stepNmNode, cordys.selectXMLNode(parentNewNode,".//*[local-name()='" + model.instance.businessObjectName + "'][last()]"));
	cordys.appendXMLNode(chargerCdNode, cordys.selectXMLNode(parentNewNode,".//*[local-name()='" + model.instance.businessObjectName + "'][last()]"));
}


/********************************************************************
 * 为附件model增加必须的节点
 */
function fillValuesOnGetAttachmentModel(modelData ,modelBObjName,tupleNode)
{
	var fileUrlNode = cordys.createElementNS(parentNewNode.ownerDocument,"http://schemas.cordys.com/tbpm/bpc","FILEURL");
	var deptCdNode = cordys.createElementNS(parentNewNode.ownerDocument,"http://schemas.cordys.com/tbpm/bpc","DEPT_CODE");
	var userCdNode = cordys.createElementNS(parentNewNode.ownerDocument,"http://schemas.cordys.com/tbpm/bpc","OPERATOR_CODE");
	var formIdNode = cordys.createElementNS(parentNewNode.ownerDocument,"http://schemas.cordys.com/tbpm/bpc","FORM_ID");
	var instanceIdNode = cordys.createElementNS(parentNewNode.ownerDocument,"http://schemas.cordys.com/tbpm/bpc","INSTANCE_ID");
	cordys.setTextContent(fileUrlNode, uploadFileURL);
	cordys.setTextContent(deptCdNode, department_code.getValue());
	cordys.setTextContent(userCdNode, user_code.getValue());
	cordys.setTextContent(formIdNode, formTempId);
	cordys.setTextContent(instanceIdNode, Workflow.getProcessInstanceId());
	cordys.appendXMLNode(fileUrlNode, cordys.selectXMLNode(parentNewNode,".//*[local-name()='" + model.instance.businessObjectName + "'][last()]"));
	cordys.appendXMLNode(deptCdNode, cordys.selectXMLNode(parentNewNode,".//*[local-name()='" + model.instance.businessObjectName + "'][last()]"));
	cordys.appendXMLNode(userCdNode, cordys.selectXMLNode(parentNewNode,".//*[local-name()='" + model.instance.businessObjectName + "'][last()]"));
	cordys.appendXMLNode(formIdNode, cordys.selectXMLNode(parentNewNode,".//*[local-name()='" + model.instance.businessObjectName + "'][last()]"));
	cordys.appendXMLNode(instanceIdNode, cordys.selectXMLNode(parentNewNode,".//*[local-name()='" + model.instance.businessObjectName + "'][last()]"));
}


/********************************************************************
 * 功能名称: bindDataToModels();
 * 描述: 数据和model绑定
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
        if(submitOperation == 1)
        {
        	GetApprovalModel.refreshAllViews();		
        }
		
        if(cordys.selectXMLNode(responseNode,".//*[local-name()='BPC_FEE']"))
        {
            var bpcFeeInfoDataNode = dataXML.XMLDocument.cloneNode(true);
            cordys.appendXMLNode(cordys.selectXMLNode(responseNode,".//*[local-name()='BPC_FEE']"),cordys.selectXMLNode(bpcFeeInfoDataNode,".//*[local-name()='old']"));
            GetBpcFeeObjectModel.clear();
            GetBpcFeeObjectModel.putData(bpcFeeInfoDataNode);
            GetBpcFeeObjectModel.refreshAllViews();
        }
		
		if(cordys.selectXMLNode(responseNode,".//*[local-name()='BPC_FEEDEP']"))
        {
            var objDataNode = dataXML.XMLDocument.cloneNode(true);
            var nodeList = cordys.selectXMLNodes(responseNode,".//*[local-name()='BPC_FEEDEP']");
            for ( var i=0, length=nodeList.length; i < length; i++ )
            {
                var attachmentNode = cordys.selectXMLNode(nodeList[i],  ".");
                cordys.appendXMLNode(attachmentNode,cordys.selectXMLNode(objDataNode,".//*[local-name()='tuple'][last()]/*[local-name()='old']"));
                if(i<length-1){
                    var dataNode = dataXML.XMLDocument.cloneNode(true);
                    cordys.appendXMLNode(cordys.selectXMLNode(dataNode,".//*[local-name()='tuple']"),cordys.selectXMLNode(objDataNode,".//*[local-name()='GetResponse']"));
                }
    
            }
            GetBpcFeedepObjectsModel.clear();
            GetBpcFeedepObjectsModel.putData(objDataNode);
            GetBpcFeedepObjectsModel.refreshAllViews();
        }
		
		if(cordys.selectXMLNode(responseNode,".//*[local-name()='BPC_FEE_ITEM']"))
        {
            var objDataNode = dataXML.XMLDocument.cloneNode(true);
            var nodeList = cordys.selectXMLNodes(responseNode,".//*[local-name()='BPC_FEE_ITEM']");
            for ( var i=0, length=nodeList.length; i < length; i++ )
            {
                var attachmentNode = cordys.selectXMLNode(nodeList[i],  ".");
                cordys.appendXMLNode(attachmentNode,cordys.selectXMLNode(objDataNode,".//*[local-name()='tuple'][last()]/*[local-name()='old']"));
                if(i<length-1){
                    var dataNode = dataXML.XMLDocument.cloneNode(true);
                    cordys.appendXMLNode(cordys.selectXMLNode(dataNode,".//*[local-name()='tuple']"),cordys.selectXMLNode(objDataNode,".//*[local-name()='GetResponse']"));
                }
    
            }
            GetBpcFeeItemObjectsModel.clear();
            GetBpcFeeItemObjectsModel.putData(objDataNode);
            GetBpcFeeItemObjectsModel.refreshAllViews();
        }
		
	    if(cordys.selectXMLNode(responseNode,".//*[local-name()='BPC_AUDIT']"))
        {
            var objDataNode = dataXML.XMLDocument.cloneNode(true);
            cordys.appendXMLNode(cordys.selectXMLNode(responseNode,".//*[local-name()='BPC_AUDIT']"),cordys.selectXMLNode(objDataNode,".//*[local-name()='old']"));
            GetBpcAuditObjectModel.clear();
            GetBpcAuditObjectModel.putData(objDataNode);
            GetBpcAuditObjectModel.refreshAllViews();
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
            downloadFile();
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
            var approvalHistoryDataNode = dataXML.XMLDocument.cloneNode(true);
            var nodeList = cordys.selectXMLNodes(responseNode,".//*[local-name()='APPROVAL_HISTORY']");
            for ( var i=0, length=nodeList.length; i < length; i++ )
            {
                var approvalHistoryNode = cordys.selectXMLNode(nodeList[i],  ".");
                cordys.appendXMLNode(approvalHistoryNode,cordys.selectXMLNode(approvalHistoryDataNode,".//*[local-name()='tuple'][last()]/*[local-name()='old']"));
                if(i<length-1){
                    var dataNode = dataXML.XMLDocument.cloneNode(true);
                    cordys.appendXMLNode(cordys.selectXMLNode(dataNode,".//*[local-name()='tuple']"),cordys.selectXMLNode(approvalHistoryDataNode,".//*[local-name()='GetResponse']"));
                }
    
            }
            GetApprovalHistoryModel.clear();
            GetApprovalHistoryModel.putData(approvalHistoryDataNode);
            GetApprovalHistoryModel.refreshAllViews();
        }
    }
}



/********************************************************************
 * 功能名称: do_tbbSave_Click();
 * 描述: 保存按钮，表单保存
 */

function do_tbbSave_Click(eventObject)
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


/********************************************************************
 * 功能名称: do_tbbsubmit_Click();
 * 描述: 提交按钮，保存、提交
 */
function do_tbbSubmit_Click(eventObject)
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
           submitOperation = 0;
           //Workflow.completeTask();
		   startWorkflow();
    }
    else if (confirmReturnValue == 0)
    {
    }
}

function startWorkflow(){
	if(Workflow.getProcessInstanceId()==undefined || Workflow.getProcessInstanceId()=="")
	{
		var request = executeProcess.XMLDocument.cloneNode(true);
		cordys.setNodeText(request,".//*[local-name()='receiver']","Business Process Models/com/laiyifen/bpc/fee/总部报销主流程");
		cordys.setNodeText(request,".//*[local-name()='FormID']",formID);
		cordys.setNodeText(request,".//*[local-name()='DecisionAttribute']","01");
		//cordys.setNodeText(request,".//*[local-name()='ExecutionType']","1");
		//cordys.setNodeText(request,".//*[local-name()='CreateName']",user_name.getValue());
		//cordys.setNodeText(request,".//*[local-name()='CreateTime']",applicationdate.getValue());                                                
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

/********************************************************************
 * 功能名称: do_tbbReturn_Click();
 * 描述: 驳回按钮，保存、驳回
 */
function do_tbbReturn_Click(eventObject)
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
    		
			decisionattribute.setValue("03");
			saveCompositeObject();
			submitOperation = 0;
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
        saveCompositeObject();
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
 */
function  do_tbbControlView_Click(eventObject){
	alert("no");
}
/********************************************************************
 * 功能名称: do_tbbPrint_Click();
 * 描述: 打印
 */
function  do_tbbPrint_Click(eventObject){
	alert("no");
}

/********************************************************************
 * 功能名称: do_CompositeModel_OnResponse();
 * 描述: 请求报文成功之后刷新model
 */
function do_CompositeModel_OnResponse(eventObject)
{
    var responseNode = eventObject.response;
	//操作数据后设置FormId供下条流程使用
    var tmpnode=cordys.selectXMLNode(responseNode ,".//*[local-name()='BPC_FEE']/*[local-name()='FORM_ID']")
	if(tmpnode!=null){
		if(cordys.getTextContent(tmpnode)!="")
		formID =cordys.getTextContent(tmpnode);
	}
    if(insertOperation == 1)
    {
         insertOperation=0;    
		 getCompositeObject();
    }
}

/********************************************************************
 * 功能名称: do_tbbQuit_Click()
 * 描述: 退出
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
 */
function do_CompositeModel__OnSOAPFault(eventObject)
{
    eventObject.showError = false;
    application.notify("您的操作未能成功执行，请您重新尝试或联系系统管理员，谢谢");	
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
  	    deptNm[rowInx].setValue(department_name.getValue());
  	    userNm[rowInx].setValue(user_name.getValue());
  	    uploadFileURL[rowInx] = fileUrl;
  	    downloadFile(rowInx);
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
 * 页面关闭前释放全局变量
 */
function Form_BeforeClose(eventObject)
{
    formID=null;
	uploadFileURL = null;
    modelCollection =null;
    updateDocTemplate = null;
    deleteDocTemplate = null;
    insertDocTemplate = null;
}

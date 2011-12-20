var gsShopId=null;
var insertOperation = 0;
var shopTempId = null;
var formTempId = null;
var userArray = new Array();
var updateDocTemplate = null;
var deleteDocTemplate = null;
var insertDocTemplate = null;
var submitOperation = 0;
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
   if(cordys.selectXMLNode(responseNode ,".//*[local-name()='SHOP_ID']")){
    insertshopid.setValue(cordys.getTextContent(cordys.selectXMLNode(responseNode ,".//*[local-name()='SHOP_ID']")));
   }
   if(cordys.selectXMLNode(responseNode ,".//*[local-name()='FORM_ID']")){
    formTempId = cordys.getTextContent(cordys.selectXMLNode(responseNode ,".//*[local-name()='FORM_ID']"));
   formid.setValue(formTempId);
   }
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
 * 功能名称	: do_Form_Init();
 * 描述: 初始化表单
 * 输入参数：无
 * 输出参数：无
 */
function do_Form_Init(eventObject)
{
//用户在其任务完成时,只能查看表单内容,而不能修改表单内容
	var workstatus = Workflow.getMessageStatus();
	if(workstatus == "COMPLETED" || workstatus == "ABORTED" || workstatus == "TERMINATED")
	{
		btnsave.disable();
		btnsubmit.disable();
	}
	    gpxhidden.hide();
	    for(var i=1;i<8;i++){ 
	        File_RepositoryTable.hideColumn(i+5); 
	    }   
	    //隐藏待办页面缺省的按钮
	    if(Workflow.getProcessInstanceId()!=null){
	    	Workflow.hideTaskToolbar(); 
	    	printProgress(Workflow.getTask());
	    } 
	    gpxuserinfo.disable();
	    gpxShopMaster.disable();
	    var eventdata =  application.event.data;
	    if(eventdata){
    	 var eventshopid = eventdata.shopid;
    	 if(eventshopid){
    		 btnsave.disable();
    		 btnsubmit.disable();
    		 tbbbpmmonitor.disable();
    		 btnUpload.disable();
    		 navDelete2.disable();
    	 }
    }
	    renewaldate.setFocus();
}

/********************************************************************
 * 功能名称	: do_Form_InitDone();
 * 描述: 表单加载完成后查询复合对象并绑定到相应的model
 * 输入参数：eventObject
 * 输出参数：无
 */
function initForm(eventObject)
{
 WebForm.getLayoutElement(gpxCompositeShopMO).style.height = "auto";
     WebForm.getLayoutElement(gpxCompositeShopMO).className += "autoextent";
    gsShopId = shopid.getValue();
   //gsShopId = "78e3b51c-e998-11e0-fa92-aea629981b2e";
    userArray=getUserInfo();
    gpxuserinfo.create();
    staticObject = getStaticValue();
    cordys.addDOMListener(File_RepositoryTable, "ondblclick",
			completeDownloadFile);
    modelCol_Single["GetShopMasterOperationModel"] = GetShopMasterOperationModel;
    modelCol_Single["GetShopMasterModel"] = GetShopMasterModel;
    modelCol_Multi["GetAttachmentModel"] = GetAttachmentModel; 
    getCompositeObject();
}

/********************************************************************
 * 功能名称	: getCompositeObject();
 * 描述: 查询复合对象并绑定到相应的model
 * 输入参数：eventObject
 * 输出参数：无
 */
function getCompositeObject(){
	 var getShopMasterObjectReq = cordys.cloneXMLDocument(GetShopMasterObjectSoap.XMLDocument);
	 var appData = application.event.data;
	 if(appData && appData.shopid)
	 {
	        cordys.setNodeText(getShopMasterObjectReq, ".//*[local-name()='shopId']", application.event.data.shopid);
	  }else{
	        cordys.setNodeText(getShopMasterObjectReq, ".//*[local-name()='shopId']", gsShopId);
	 }
	    CompositeShopMasterOperationModel.clear();
	    CompositeShopMasterOperationModel.setMethodRequest(getShopMasterObjectReq);
	    CompositeShopMasterOperationModel.reset(); 
	    var compositeData=CompositeShopMasterOperationModel.getData(); 
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
       if(cordys.selectXMLNode(responseNode,".//*[local-name()='SHOP_MASTER_OPERATION']"))
        {
            var shopMasterOperationDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
            bindSingleTupleModel(GetShopMasterOperationModel,responseNode,"SHOP_MASTER_OPERATION",shopMasterOperationDataNode);
        }
       if(cordys.selectXMLNode(responseNode,".//*[local-name()='SHOP_MASTER']"))
       {
           var shopMasterDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
           bindSingleTupleModel(GetShopMasterModel,responseNode,"SHOP_MASTER",shopMasterDataNode);
           opendate.setValue(cordys.getNodeText(GetShopMasterModel.getData(),".//*[local-name()='OPEN_DATE']"));
           operationshopstatusext.setValue(cordys.getNodeText(GetShopMasterModel.getData(),".//*[local-name()='SHOP_STATUS_DESC']"));
           operationshopstatus.setValue(cordys.getNodeText(GetShopMasterModel.getData(),".//*[local-name()='SHOP_STATUS']"));
           iscentershop.setValue(cordys.getNodeText(GetShopMasterModel.getData(),".//*[local-name()='ISCENTER']"));
           renewaldate.setValue(cordys.getNodeText(GetShopMasterModel.getData(),".//*[local-name()='RENEWAL_DATE']"));
       }
        
        if(cordys.selectXMLNode(responseNode,".//*[local-name()='ATTACHMENT']"))
        {
        	var attachmentDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument); 
			 bindMultiTupleModel(GetAttachmentModel,responseNode,"ATTACHMENT",attachmentDataNode);
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
		 var flag = check();
		if(!flag){
			   return false;
		}
		 setShopMasterValue();
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
  * 功能名称	: do_btnsubmit_Click();
  * 描述: 点击工具栏的提交按钮，触发此操作
  * 输入参数：eventObject
  * 输出参数：无
  */
 function do_btnsubmit_Click(eventObject)
 {
	 if(!WebForm.validateForm(application.container.applicationId))
	     return;
		 var flag = check();
		if(!flag){
			   return false;
		}
		setShopMasterValue();
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
            changeStatusForAttachments();
            decisionattribute.setValue("01");		
            saveForm();
            Workflow.completeTask();
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
function do_imgpremiumregion_Click(eventObject)
{
   getDialogValue(eventObject,premiumregion,premiumregionaname,"ZPRCR","牌价地区","320px","230px","1");
}
/********************************************************************
 * 功能名称	: do_imgstatus_Click();
  * 描述: 门店状态
  * 输入参数：eventObject
  * 输出参数：无
 *  operationshopstatus 是营运部部的状态
 */
function do_imgstatus_Click(eventObject)
{
   getDialogValue(eventObject,operationshopstatus,operationshopstatusext,"SPGR1","门店状态","320px","230px","1");
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
/***********************************
*  功能名称：do_CompositeShopMasterOperationModel_OnSOAPFault();
*  功能描述：SOAP请求失败时消息提示
*  输入参数:eventObject
*  输出参数：无
*/
function do_CompositeShopMasterOperationModel_OnSOAPFault(eventObject)
{
       insertOperation = 0 ;
	eventObject.showError = false;
	application.notify("您的操作未能成功执行，请您重新尝试或联系系统管理员，谢谢");
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
  saveCompositeObject(CompositeShopMasterOperationModel,modelCol_Single,modelCol_Multi);
  if(CompositeShopMasterOperationModel.soapFaultOccurred) 
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
         case "GetShopMasterOperationModel":
         	fillValuesInGetShopMasterOperationObjectModel(modelDataDoc ,modelBObjName,tupleNode);
         break;
     }
}

/********************************************************************
 * 功能名称	: fillValuesInGetShopMasterOperationObjectModel();
 * 功能描述:  为 model增加状态位节点
 * 输入参数：modelData modelBObjName tupleNode
 * 输出参数：无
 */
function fillValuesInGetShopMasterOperationObjectModel(modelData ,modelBObjName,tupleNode)
{  
    var statusNode = cordys.selectXMLNode(tupleNode,".//*[local-name()='" + modelBObjName + "']/*[local-name()='STATUS']");
    cordys.setXMLAttribute(statusNode,"","null","false");
    if((submitOperation!=1)&&(Workflow.getProcessInstanceId()==undefined || Workflow.getProcessInstanceId()==""))
    {     
        cordys.setTextContent(statusNode, "0");    //status=0
    }
    else
    {
        cordys.setTextContent(statusNode, "1");    //status=1
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
	 gsShopId=null;
	 insertOperation = 0;
	 shopTempId = null;
	 formTempId = null;
	 userArray = new Array();
	 updateDocTemplate = null;
	 deleteDocTemplate = null;
	 insertDocTemplate = null;
	 submitOperation = 0;
	 modelCol_Single = null;
	 modelCol_Multi = null;
	 staticObject = null;
}

function check(){
	var flag = true;
    var openhour = openinghours.getValue();
    if(openhour!=null&&openhour!=""&&openhour!="无"){
   		 var flag = checkme(openinghours,"^[0-9, 10-24]{1,2}:[0-30, 30-59]{1,2}$","请按照正确的格式填写营业时间!"); 
		     if(!flag){
		    	 flag = false;
		    	 return false;
		     }
    }  
    
    var closehour = closehours.getValue();
    if(closehour!=null&&closehour!=""&&closehour!="无"){
   		 var flag = checkme(closehours,"^[0-9, 10-24]{1,2}:[0-30, 30-59]{1,2}$","请按照正确的格式填写营业时间!");  
		     if(!flag){
		    	 flag = false;
		    	 return false;
		     }
    }
    var center = iscentershop.getValue();
    if(center==null||center==""){
    	 flag = false;
    	 iscentershop.setFocus();
    	 application.notify("请选择是否是中心门店！");
    	 return false;
    }
    var regionname = premiumregionaname.getValue();
    if(regionname==null||regionname==""){
      	 flag = false;
      	 premiumregionaname.setFocus();
      	 application.notify("请选择牌价地区！");
      	 return false;
      }
    var shopstatus = operationshopstatusext.getValue();
    if(shopstatus==null||shopstatus==""){
   	 flag = false;
   	 operationshopstatusext.setFocus();
   	 application.notify("请填写门店状态！");
   	 return false;
   }
    
    var regionper = regionpersongname.getValue();
	if(regionper==null||regionper==""){
		 flag = false;
		 regionpersongname.setFocus();
	   	 application.notify("请选择大区负责人姓名！");
	   	 return false;
	}
	
	var regionpercode = regionpersongcode.getValue();
	if(regionpercode==null||regionpercode==""){
		 flag = false;
		 regionpersongcode.setFocus();
	   	 application.notify("大区负责人工号不能为空！");
	   	 return false;
	}
	var areaname = areapersongname.getValue();
	if(areaname==null||areaname==""){
		 flag = false;
		 areapersongname.setFocus();
	   	 application.notify("请选择区域负责人姓名！");
	   	 return false;
	}
	var areacode = areapersongcode.getValue();
	if(areacode==null||areacode==""){
		 flag = false;
		 areapersongcode.setFocus();
	   	 application.notify("区域负责人工号不能为空！");
	   	 return false;
	}
     
    return flag;
     
}
/********************************************************************
 * 功能名称	: setShopMasterValue();
 * 描述: 主数据赋值
 * 输入参数：无
 * 输出参数：无
 */
function setShopMasterValue(){
	shopshopstatus.setValue(operationshopstatus.getValue());
	shopstatusdesc.setValue(operationshopstatusext.getValue());
	iscenter.setValue(iscentershop.getValue());
	shopopendate.setValue(opendate.getValue());
	shoprenewaldate.setValue(renewaldate.getValue());
}

/********************************************************************
 * 功能名称	: btn_selectregionareapersons_Click();
 * 描述: 选大区负责人
 * 输入参数：无
 * 输出参数：无
 */
function btn_selectregionareapersons_Click(eventObject) {
	var oParam = new Object();
	oParam.unitId = userArray.deptCode;
	oParam.selectMode = "SINGLE";
	oParam.selectedUserNameList = "";
	oParam.selectedUserList = "";
	application.select(Application_SelectUsers.XMLDocument.documentElement,
			oParam);
	if (oParam.selectedUserList != "") {
		regionpersongname.setValue(oParam.selectedUserNameList);
		regionpersongcode.setValue(oParam.currentSslectedUserCodeList);
	}
}
/********************************************************************
 * 功能名称	: btn_selectareapersons_Click();
 * 描述: 选区域负责人
 * 输入参数：无
 * 输出参数：无
 */
function btn_selectareapersons_Click(eventObject) {
	var oParam = new Object();
	oParam.unitId = userArray.deptCode;
	oParam.selectMode = "SINGLE";
	oParam.selectedUserNameList = "";
	oParam.selectedUserList = "";
	application.select(Application_SelectUsers.XMLDocument.documentElement,
			oParam);
	if (oParam.selectedUserList != "") {
		areapersongname.setValue(oParam.selectedUserNameList);
		areapersongcode.setValue(oParam.currentSslectedUserCodeList);
	}
}
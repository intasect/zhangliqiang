var gsShopId=null;
var insertOperation = 0;
var updateDocTemplate = null;
var deleteDocTemplate = null;
var insertDocTemplate = null;
var submitOperation = 0;
var modelCol_Single = new Array();
var modelCol_Multi = new Array();
/********************************************************************
 * 功能名称	: do_CompositeShopGeneralFormModel_OnResponse();
 * 描述: 复合对象返回响应时调用，进行model绑定操作
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_CompositeShopGeneralFormModel_OnResponse(eventObject)
{
    var responseNode = eventObject.response;
     if(cordys.selectXMLNode(responseNode,".//*[local-name()='faultcode']"))return;
    if(insertOperation == 1)
    {
       var shopTempId=cordys.getTextContent(cordys.selectXMLNode(responseNode ,".//*[local-name()='SHOP_ID']"));
        insertshopid.setValue(gsShopId);
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
    //隐藏待办页面缺省的按钮
    if(Workflow.getProcessInstanceId()!=null){
    	Workflow.hideTaskToolbar(); 
    	printProgress(Workflow.getTask());
     } 
    gpxUserInfo.disable();
    gpxShopMaster.disable();
    var eventdata =  application.event.data;
    if(eventdata!=undefined){
    	 var eventshopid = eventdata.shopid;
    	 if(eventshopid!=undefined&&eventshopid!=null){
    		 btnsave.disable();
    		 btnsubmit.disable();
    		 tbbbpmmonitor.disable();
    	 }
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
    WebForm.getLayoutElement(gpxCompositeShopGeneralForm).style.height = "auto";
    WebForm.getLayoutElement(gpxCompositeShopGeneralForm).className += "autoextent";
    gsShopId = shopid.getValue(); 
  //  gsShopId = "78e3b51c-e998-11e0-fa92-aea629981b2e";
    modelCol_Single["GetShopGeneralFormModel"] = GetShopGeneralFormModel;
    modelCol_Single["GetShopMasterModel"] = GetShopMasterModel;
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
	var getShopMasterObjectReq = cordys.cloneXMLDocument(GetShopMasterObjectSoap.XMLDocument);
	var appData = application.event.data;
	 if(appData&&appData.shopid)
	 {
	        cordys.setNodeText(getShopMasterObjectReq, ".//*[local-name()='shopId']", application.event.data.shopid);
	  }else{
	        cordys.setNodeText(getShopMasterObjectReq, ".//*[local-name()='shopId']", gsShopId);
	  }
	 cordys.setNodeText(getShopMasterObjectReq, ".//*[local-name()='formId']", formid.getValue());
	 CompositeShopGeneralFormModel.setMethodRequest(getShopMasterObjectReq);
	 CompositeShopGeneralFormModel.reset(); 
	 compositeData=CompositeShopGeneralFormModel.getData(); 
	 bindDataToModels(compositeData);
	 CompositeShopGeneralFormModel.clear();
}


/********************************************************************
 * 功能名称	: bindDataToModels();
 * 描述: 将复合对象的响应结构拆分到子对象中，将数据显示到页面
 * 输入参数：responseNode，响应节点
 * 输出参数：无
 */
 function bindDataToModels(responseNode)
{
        if(cordys.selectXMLNode(responseNode,".//*[local-name()='SHOP_MASTER']"))
        {
            var shopMasterDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
            bindSingleTupleModel(GetShopMasterModel,responseNode,"SHOP_MASTER",shopMasterDataNode);
        }
      
       if(cordys.selectXMLNode(responseNode,".//*[local-name()='SHOP_GENERAL_FORM']"))
        {
            var shopMasterOperationDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
            bindSingleTupleModel(GetShopGeneralFormModel,responseNode,"SHOP_GENERAL_FORM",shopMasterOperationDataNode);
            formid.setValue();          
        }else{
            gpxUserInfo.create();
            insertshopid.setValue(gsShopId);
            shoptaxrate.setValue("1");
        }
  
}
 
 
 /********************************************************************
  * 功能名称	: do_btnsave_Click();
  * 描述: 点击工具栏的保存按钮，触发此方法
  * 输入参数：eventObject
  * 输出参数：无
  */
 function do_btnsave_Click(eventObject)
 {
	 if(!WebForm.validateForm(application.container.applicationId))
		    return;
	  	var flag = mycheck();
		if(!flag){
			   return false;
		}else{	 
		  setShopMasterValue();
		}
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
   
/***********************************
*  功能名称：do_CompositeShopGeneralFormModel_OnSOAPFault();
*  功能描述：SOAP请求失败时消息提示
*  输入参数:eventObject
*  输出参数：无
*/
function do_CompositeShopGeneralFormModel_OnSOAPFault(eventObject)
{
	insertOperation=0;
	eventObject.showError = false;
	application.notify("您的操作未能成功执行，请您重新尝试或联系系统管理员，谢谢");
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
   * 功能名称	: do_btnsubmit_Click();
   * 描述: 点击工具栏的提交按钮，触发此操作
   * 输入参数：eventObject
   * 输出参数：无
   */
  function do_btnsubmit_Click(eventObject)
  {
      //必填项校验
	  if(!WebForm.validateForm(application.container.applicationId))
		    return;
	   var flag = mycheck();
		if(!flag){
			   return false;
		}else{	 
		  setShopMasterValue();
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
             Workflow.completeTask();
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
    saveCompositeObject(CompositeShopGeneralFormModel,modelCol_Single,modelCol_Multi);
    if(CompositeShopGeneralFormModel.soapFaultOccurred) 
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
           case "GetShopGeneralFormModel":
          	 fillValuesInGetShopGeneralFormObjectModel(modelDataDoc ,modelBObjName,tupleNode);        	 
           break;
       }
  }

  /********************************************************************
   * 功能名称	: fillValuesInGetShopGeneralFormObjectModel();
   * 功能描述:  为 model增加状态位节点
   * 输入参数：modelData modelBObjName tupleNode
   * 输出参数：无
   */
  function fillValuesInGetShopGeneralFormObjectModel(modelData ,modelBObjName,tupleNode)
  {  
      var formNode = cordys.selectXMLNode(tupleNode,".//*[local-name()='" + modelBObjName + "']/*[local-name()='FORM_ID']");
      cordys.setTextContent(formNode, formid.getValue());
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
	   updateDocTemplate = null;
	   deleteDocTemplate = null;
	   insertDocTemplate = null;
	   submitOperation = 0;
	   modelCol_Single = null;
	   modelCol_Multi = null;
  }
  function setShopMasterValue(){  
	  shoptaxrate.setValue(title.getValue());
  }
  function mycheck(){
	  var flag = true;
	  var taxvalue = title.getValue();
	  if(taxvalue==null||taxvalue==""){
		  application.notify("门店税率不能为空！");
		  flag = false;
	  }
	  return flag;
  }
  
var insertOperation = 0;
var submitOperation = 0;   //辨别提交和其他操作，1表示提交，0表示其他,2表示驳回
var popupEvent = null;
var userInfo;
var updateDocTemplate ;
var deleteDocTemplate;
var insertDocTemplate;

var modelCol_Single = new Array();
var modelCol_Multi = new Array();

/********************************************************************
 * 功能名称	: refreshModels();
 * 描述: 刷新复合对象模型数据
 * 输入参数：eventObject
 * 输出参数：无
 */
function refreshModels(eventObject)
{
     var responseNode = eventObject.response;
     //操作数据后设置FormId供下条流程使用
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
  
    if(Workflow.getProcessInstanceId()!=null)
	{
    	//隐藏待办页面缺省的按钮
    	if(Workflow!=null) Workflow.hideTaskToolbar();
	}
}

//页面初始化
function initForm(eventObject)
{
	if(Workflow.getProcessInstanceId()!=null)
	{
		//设置右上角超时图片
		printProgress(Workflow.getTask());
	}
	
	WebForm.getLayoutElement(gpxMaterialStatus).style.height = "auto";
    WebForm.getLayoutElement(gpxMaterialStatus).className += "autoextent";

    //创建单条记录模型数组
    modelCol_Single["GetShopMaterialStatusObjectModel"] = GetShopMaterialStatusObjectModel;
    
    //创建多条记录模型数组
    modelCol_Multi["GetShopMaterialStatusDetailObjectsModel"] = GetShopMaterialStatusDetailObjectsModel;
    
	gpxuserinfo.create();
	
	var request = GetOrgCompanyByCompanyModel.getMethodRequest();
	var tempStr = iptCompanyCode.getValue();
    cordys.setNodeText(request ,".//*[local-name()='companyCode']",tempStr.substring(0,2));
    GetOrgCompanyByCompanyModel.setMethodRequest(request);
    GetOrgCompanyByCompanyModel.reset();
    GetOrgCompanyByCompanyModel.refreshAllViews();
    
       //从上一流程取得整个新开店生命周期的shopId
       var shopId= shopid.getValue();
       if(undefined != shopId&& ""!= shopId&& null != shopId)
       {
    	   iptshopid.setValue(shopId);
           iptformid.setValue(formid.getValue());
           iptStatus.setValue("1");
           selPriceArea.setValue(tempStr);
           getCompositeObject();
       }
       else
       {
    	   var dataTemp = application.event.data;
           if(undefined != dataTemp && undefined != dataTemp.shopid && null != dataTemp.shopid)
           {
        	   formid.setValue(dataTemp.subformid);
    		   iptshopid.setValue(dataTemp.subshopid);
    		   
    		   getCompositeObject();
               gpxMaster.disable();
               tbbSave.disable();
               tbbSubmit.disable();
               gpxuserinfo.disable();
           }
             else
             {
                   application.notify ("流程非法进入，请联系管理员！");
             }                 
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
    CompositeMaterialStatusModel.setMethodRequest(getCompleteXMLDocument);
    CompositeMaterialStatusModel.reset();
    var compositeData=CompositeMaterialStatusModel.getData();
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
	saveCompositeObject(CompositeMaterialStatusModel,modelCol_Single,modelCol_Multi);
}

/********************************************************************
 * 功能名称	: bindDataToModels();
 * 描述: 将复合对象的响应结构拆分到子对象中，将数据显示到页面
 * 输入参数：responseNode，响应节点
 * 输出参数：无
 */
function bindDataToModels(responseNode)
{
         if(cordys.selectXMLNode(responseNode,".//*[local-name()='SHOP_MATERIAL_STATUS']"))
         {
             var shopInfoDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
             bindSingleTupleModel(GetShopMaterialStatusObjectModel,responseNode,"SHOP_MATERIAL_STATUS",shopInfoDataNode);
         }
         
         if(cordys.selectXMLNode(responseNode,".//*[local-name()='SHOP_MASTER']"))
         {
             var shopMasterDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
             bindSingleTupleModel(GetShopMasterObjectModel,responseNode,"SHOP_MASTER",shopMasterDataNode);
         }
         
         if(cordys.selectXMLNode(responseNode,".//*[local-name()='SHOP_MATERIAL_STATUS_DETAIL']"))
         {
             dataDoc = dataXML.XMLDocument;
             bindMultiTupleModel(GetShopMaterialStatusDetailObjectsModel,responseNode,"SHOP_MATERIAL_STATUS_DETAIL",dataDoc);
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
		if (confirmReturnValue == 1)
		{
				submitOperation = 1;
				iptStatus.setValue("2");
				saveForm();

				decisionattribute.setValue("01");
	            if(CompositeMaterialStatusModel.soapFaultOccurred)
	            {
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
        saveForm();
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

function do_ebbAdd_Click()
{
      
        var data = new Object();
        data.subshopid =  iptshopid.getValue();
        data.subformid =  formid.getValue();
       data.subshopno = shopNo.getValue();
        application.showDialog(dialogShopMaterialAver.XMLDocument.documentElement,data, null, getShopMaterialAverValue, false);
}

function getShopMaterialAverValue()
{
               var getCompleteXMLDocument = cordys.cloneXMLDocument(getCompleteXML.XMLDocument);
               cordys.setNodeText(getCompleteXMLDocument,".//*[local-name()='formId']",formid.getValue());
               cordys.setNodeText(getCompleteXMLDocument,".//*[local-name()='shopId']",iptshopid.getValue());
               CompositeMaterialStatusModel.setMethodRequest(getCompleteXMLDocument);
               CompositeMaterialStatusModel.reset();
               var compositeData=CompositeMaterialStatusModel.getData();
               if(cordys.selectXMLNode(compositeData,".//*[local-name()='SHOP_MASTER']"))
              {
                    bindDataToModels(compositeData);
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
	popupEvent = null;
	modelCol_Single =null;
	modelCol_Multi = null;
	updateDocTemplate = null;
	deleteDocTemplate = null;
	insertDocTemplate = null;
	userInfo = null;
}
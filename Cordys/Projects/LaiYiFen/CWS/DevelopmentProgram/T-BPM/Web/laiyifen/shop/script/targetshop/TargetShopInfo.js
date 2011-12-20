var insertOperation;
var submitOperation = 0;
var gIsFlag =null;
var gStatus =null;
var oParam;
var popupEvent = null;
var userArray = new Array();
var updateDocTemplate;
var deleteDocTemplate;
var insertDocTemplate;
var modelCol_Single = new Array();
var modelCol_Multi = new Array();
var validateControls = new Array();
var staticObjects="";
var compositeData;

/********************************************************************
 * 功能名称	: do_CompositeShopTargetInfoModel_OnResponse();
 * 描述: 复合对象返回响应时调用，进行model绑定操作
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_CompositeShopTargetInfoModel_OnResponse(eventObject)
{
	var responseNode = eventObject.response;
	if(cordys.selectXMLNode(responseNode,".//*[local-name()='faultcode']"))return;
		var shopID=cordys.getNodeText(responseNode,".//*[local-name()='SHOP_TARGET_INFO']/*[local-name()='SHOP_ID']");
		var formID=cordys.getNodeText(responseNode,".//*[local-name()='SHOP_TARGET_INFO']/*[local-name()='FORM_ID']");
		var optShopID=iptshopid.getValue();
		var optformID=iptformid.getValue();
		if(shopID!=null){
		  shopid.setValue(shopID);
		}else{
		  shopid.setValue(optShopID);
		}

		if(formID!=null){
		  formid.setValue(formID);
		}else{
		  formid.setValue(optformID);
		}

		if(insertOperation ==1)
		{
			insertOperation=0;
			getCompositeObject();
			if(submitOperation == 0)
			{
				application.inform("保存成功");
			}else{
				GetApprovalModel.undo();
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
	 //隐藏待办页面缺省的按钮
   if(Workflow!=null)Workflow.hideTaskToolbar();
   oParam =window.application.event.data;
   if(oParam && oParam.isFlag && oParam.isFlag!=""){
		gIsFlag=oParam.isFlag;
   }

   if(oParam && oParam.shopID && oParam.shopID!=""){
		var gShopId=oParam.shopID;
		shopid.setValue(gShopId);
   }

   if(oParam && oParam.formID && oParam.formID!=""){
		var gFormId=oParam.formID;
		formid.setValue(gFormId);
   }

   if(oParam && oParam.status && oParam.status!=""){
		gStatus=oParam.status;
   }
   if(stepcode.getValue()=="" || stepcode.getValue()=="05"){
	   gpxshopinfo.enable();
	   gbxhouseproperty.enable();
	   gbxroadsituation.enable();
	   gbxadvertising.enable();
	   gpxshopcircle.enable();
       gpxanalyseinfo.enable();
	   gpxsuggestion.enable();
	   gbxbranchmanageapp.hide();
	   stepcode.setValue("05");
	   if(typeof(Workflow.getProcessInstanceId())=='undefined' || Workflow.getProcessInstanceId()==null){
		  tbbreturn.hide();
	      tbbbpmmonitor.hide();
	      tbbsuspend.hide();
	   }
	   if(gIsFlag==true && (gStatus=="1"||gStatus=="2")){
		  gpxshopinfo.disable();
		  gbxhouseproperty.disable();
		  gbxroadsituation.disable();
		  gbxadvertising.disable();
		  gpxshopcircle.disable();
		  gpxanalyseinfo.disable();
		  gpxsuggestion.disable();
		  gbxbranchmanageapp.disable();
	      tbbbpmmonitor.hide();
		  tbbreturn.hide();
		  tbbsuspend.hide();
		  tbbsave.hide();
		  tbbsubmit.hide();
		  gbxshopestimation.disable();
		  gpxcommon.disable();
	   }else{
		  if(Workflow.getProcessInstanceId()!=null){
			 tbbreturn.hide();
	         tbbbpmmonitor.show();
	         tbbsuspend.hide();
		  }else{
		    tbbreturn.hide();
	        tbbbpmmonitor.hide();
	        tbbsuspend.hide();
		  }
	   }
	}else if(stepcode.getValue()=="15"){
	   gbxbranchmanageapp.show();
       gbxbranchmanageapp.enable();
	   gbxbranchmanageapp.expand();
	   gpxshopcircle.disable();
	   gpxanalyseinfo.disable();
	   gpxsuggestion.disable();
	   gpxshopinfo.disable();
	   gbxhouseproperty.disable();
	   gbxroadsituation.disable();
	   gbxadvertising.disable();
	}else{
	   gpxshopinfo.disable();
	   gbxhouseproperty.disable();
	   gbxroadsituation.disable();
	   gbxadvertising.disable();
	   gpxshopcircle.disable();
	   gpxanalyseinfo.disable();
	   gpxsuggestion.disable();
	   gbxbranchmanageapp.disable();
	   gbxbranchmanageapp.collapse();
	}
	  gpxuserinfo.disable();
	  gbxhide.hide();
      gpxshopmaster.hide();
	  //provincecode.hide();
	  shopestimationtable.hideColumn(7);
	  gbxshopestimation.expand();
    //用户在其任务完成时,只能查看表单内容,而不能修改表单内容
	var workstatus = Workflow.getMessageStatus();
	if(workstatus == "COMPLETED" || workstatus == "ABORTED" || workstatus == "TERMINATED")
	{
		tbbsave.disable();
		tbbsubmit.disable();
		tbbreturn.disable();
		tbbsuspend.disable();
	}
   if(typeof(Workflow.getProcessInstanceId())=='undefined' || Workflow.getProcessInstanceId()==null){
	 if(gIsFlag==true && (gStatus=="1"||gStatus=="2")){
		tabapprovalhistory.show();
		gbxbranchmanageapp.show();

	 }else{
	    tabapprovalhistory.hide();
        tabapprovalhistory.disable();
	 }
   }else{
	  tabapprovalhistory.show();
      tabapprovalhistory.enable();
	  printProgress(Workflow.getTask());
   }
}

function do_btnUpload_Click(eventObject)
{
   application.select(uploadApplication.XMLDocument.documentElement);
}

function do_tbsave_Click(eventObject)
{
    //页面校验
    //if(!WebForm.validateForm(application.container.applicationId)) return;
	//if(!validateControl(validateControls)) return;
    showConfirm(false,closeHandler);
}

function closeHandler(confirmReturnValue)
{
    if (confirmReturnValue == 1)
    {
    	 lifecycle_status.setValue("10");
	     formtype.setValue("0");
		 //var isFlag=validateEstimation();
	     //if(isFlag==false)return;
		 saveCustomCompositeObject();
    }
    else if (confirmReturnValue == 0)
    {
    }
}

function do_tbsubmit_Click(eventObject)
{
	  //页面校验
	  try{
        if(!WebForm.validateForm(application.container.applicationId)) return;
	  }catch(e){
	  }
	  if(!validateControl(validateControls)) return;
	  if(typeof(Workflow.getProcessInstanceId())!='undefined' || Workflow.getProcessInstanceId()!=null){
		 var isFlag=do_validationApprovalHistory();
		 if(isFlag==false)return;
	  }
	  var isStatus=do_validate_Provice();
      if(isStatus==false){
		 if(region.getValue()==""){
            application.notify("区/县不能为空,请输入区/县.");
            region.setFocus();
			return;
		 }
	  }
	  //弹出选择主管确认框
      popupEvent = eventObject;
	  showConfirm(false,submitHandler,"您确定提交任务到下一环节吗？");
}

function submitHandler(confirmReturnValue)
{
	if (confirmReturnValue == 1)
    {      
		   if(stepcode.getValue()=="05"){
			 decisionattribute.setValue("01");
		   }
		   submitOperation = 1;
           lifecycle_status.setValue("11");
	       formtype.setValue("0");
		   if(document.frames["ifeattachment"].document.getElementById("formid")!=null){
		    document.frames["ifeattachment"].changeStatusForAttachments();
            document.frames["ifeattachment"].saveAttachment();
		   }
		   var isFlag=validateEstimation();
	       if(isFlag==false)return;
		   if(stepcode.getValue()=="" || stepcode.getValue()=="05"){
			   do_validation_Charge();
		   }else{
		       submitTask();
               submitOperation =0;
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
    application.container.maximize();
	application.addType(myutil,"wcp.library.util.HTMLUtil");
	WebForm.getLayoutElement(gbxshopobjective).style.height = "auto";
    WebForm.getLayoutElement(gbxshopobjective).className += "autoextent";
	gbxshopobjective.firstChild.firstChild.firstChild.style.marginLeft="40px";
	staticObjects=getStaticValue();
	userArray=getUserInfo();
	getCompositeObject();
	if(gIsFlag==true || Workflow.getProcessInstanceId()!=null){
	  controlSelectOption(eventObject);
	}
	//create the composite request Objetct
	modelCol_Single["GetShopTargetInfoObjectModel"] = GetShopTargetInfoObjectModel;
    modelCol_Single["GetShopTargetCircleInfoObjectModel"] = GetShopTargetCircleInfoObjectModel;
    modelCol_Single["GetShopTargetAssesmentObjectModel"] = GetShopTargetAssesmentObjectModel;
    modelCol_Single["GetShopTargetCheckObjectModel"] = GetShopTargetCheckObjectModel;
	modelCol_Single["GetShopMasterModel"] = GetShopMasterModel;
    modelCol_Single["GetApprovalModel"] = GetApprovalModel;

    //create composite request object for multituple
	modelCol_Multi["GetShopTargetEstimationObjectModel"] = GetShopTargetEstimationObjectModel;
    validateEstimationCommon();

	//放置需要必填项校验的output,select控件
	if(stepcode.getValue()=="" || stepcode.getValue()=="05"){
		validateControls["provicedec"] = provicedec;
		validateControls["isfullresource"] = isfullresource;
		validateControls["oneroom"] = oneroom;
		validateControls["branchproperty"] = branchproperty;
		validateControls["houseproperty"] = houseproperty;
		validateControls["landlord"] = landlord;
		validateControls["isrentincreased"] = isrentincreased;
		validateControls["isstepdec"] = isstepdec;
		validateControls["isolationdec"] = isolationdec;
		validateControls["isblockcode"] = isblockcode;
		validateControls["isdecorationdec"] = isdecorationdec;
		validateControls["isleasefitmentdec"] = isleasefitmentdec;
		validateControls["lightboxad"] = lightboxad;
		validateControls["morstarttime"] = morstarttime;
		validateControls["morendtime"] = morendtime;
		validateControls["morweather"] = morweather;
		validateControls["minstarttime"] = minstarttime;
		validateControls["mindayendtime"] = mindayendtime;
		validateControls["midweather"] = midweather;
		validateControls["nightsstarttime"] = nightsstarttime;
		validateControls["nightsendtime"] = nightsendtime;
		validateControls["nightsweather"] = nightsweather;
		validateControls["nightsweather"] = nightsweather;
		validateControls["isnearuptown"] = isnearuptown;
		validateControls["ismaturearea"] = ismaturearea;
		validateControls["livingqualitydec"] = livingqualitydec;
		validateControls["ismall"] = ismall;
		validateControls["hasoldshop"] = hasoldshop;
		validateControls["hasopponent"] = hasopponent;
	}else if(stepcode.getValue()=="15"){
      validateControls["boxnum"] = boxnum;
	  validateControls["rental"] = rental;
	  validateControls["isinnerplanning"] = isinnerplanning;
	  validateControls["taxrate"] = taxrate;
	  validateControls["subleaseprogram"] = subleaseprogram;
	  validateControls["addressdesc"] = addressdesc;
	  validateControls["maturitydesc"] = maturitydesc;
	}
}

/********************************************************************
 * 功能名称	: getCompositeObject();
 * 描述: 查询复合对象并绑定到相应的model
 * 输入参数：eventObject
 * 输出参数：无
 */
function getCompositeObject(){
	if(formid.getValue()=="" && shopid.getValue()==""){
	   gpxuserinfo.create();
	   gpxshopmaster.create();
	   do_initialize_info();
	}else{
	    var getCompleteXMLDocument = cordys.cloneXMLDocument(getCompleteXML.XMLDocument);
		cordys.setNodeText(getCompleteXMLDocument,".//*[local-name()='form_ID']",formid.getValue());
		cordys.setNodeText(getCompleteXMLDocument,".//*[local-name()='shop_ID']",shopid.getValue());
		CompositeShopTargetInfoModel.setMethodRequest(getCompleteXMLDocument);
		CompositeShopTargetInfoModel.reset();
		compositeData=CompositeShopTargetInfoModel.getData();
		var modelDataNode = cordys.selectXMLNode(compositeData,".");
		bindDataToModels(compositeData);
	}
}

function do_tbreturn_Click(eventObject)
{
	   //弹出驳回确认框
      popupEvent = eventObject;
	  showConfirm(false,returnHandler,"您确定执行驳回操作吗？");
}

function returnHandler(confirmReturnValue)
{
    //页面校验
	if(typeof(Workflow.getProcessInstanceId())!='undefined' || Workflow.getProcessInstanceId()!=null){
		 var isFlag=do_validationApprovalHistory();
		 if(isFlag==false)return;
	}

    if (confirmReturnValue == 1)
    {
    		submitOperation = 2;
			//点击驳回按钮时，记录当前驳回人
    		//var curUserCd = getCurrentUserDN().split(",")[0].substring(3);
			//	revertuser.setValue(curUserCd);
			if(document.frames["ifeattachment"].document.getElementById("formid")!=null){
			  document.frames["ifeattachment"].changeStatusForAttachments();
              document.frames["ifeattachment"].saveAttachment();
			}
    		if(stepcode.getValue() == "10")
    		{   
    			    decisionattribute.setValue("03");
					saveCustomCompositeObject();
					if(CompositeShopTargetInfoModel.soapFaultOccurred) 
                    { 
                      return; 
                    }
    		 		Workflow.completeTask();
    		}else if(stepcode.getValue() == "15"){
				    if(selectcharge.getValue()!=""&&selectcharge.getValue()=="1"){
                        decisionattribute.setValue("03");
					    saveCustomCompositeObject();
					    if(CompositeShopTargetInfoModel.soapFaultOccurred) 
                        { 
                            return; 
                        }
    		 		    Workflow.completeTask();
					}else{
                        application.showDialog(dialogRevert.XMLDocument.documentElement,null, null, GetRevertValue, false);
    				    placePopup(popupEvent);
					}
    		}else
    		{
                    application.showDialog(dialogRevert.XMLDocument.documentElement,null, null, GetRevertValue, false);
    				placePopup(popupEvent);
    		}
    }else if (confirmReturnValue == 0)
    {
    }
}

/********************************************************************
 * 功能名称	: do_gbxshopcircle_OnExpand();
 * 描述: 展开工程groupbox时判断是否需要创建新记录
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_gbxshopcircle_OnExpand(eventObject)
{
		var shopTargetCircleInfo = cordys.selectXMLNode(compositeData,".//*[local-name()='SHOP_TARGET_CIRCLE_INFO']");
        if(shopTargetCircleInfo)
        {
			var busCircleDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
			bindSingleTupleModel(GetShopTargetCircleInfoObjectModel,compositeData,"SHOP_TARGET_CIRCLE_INFO",busCircleDataNode);
        }
		createBusinessObjOnModel(GetShopTargetCircleInfoObjectModel,gpxshopcircle,"SHOP_TARGET_CIRCLE_INFO");
}
/********************************************************************
 * 功能名称	: do_gbxbranchmanageapp_OnExpand();
 * 描述: 展开工程groupbox时判断是否需要创建新记录
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_gbxbranchmanageapp_OnExpand(eventObject)
{
	    var shoptargetChkNode = cordys.selectXMLNode(compositeData,".//*[local-name()='SHOP_TARGET_CHECK']");
		if(shoptargetChkNode)
		{
			var shopCheckDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
			bindSingleTupleModel(GetShopTargetCheckObjectModel,compositeData,"SHOP_TARGET_CHECK",shopCheckDataNode);
		}
		createBusinessObjOnModel(GetShopTargetCheckObjectModel,gbxbranchmanageapp,"SHOP_TARGET_CHECK");
}
/********************************************************************
 * 功能名称	: do_gbxshopestimation_OnExpand();
 * 描述: 展开工程groupbox时判断是否需要创建新记录
 * 输入参数：eventObject
 * 输出参数：无
 */
function  do_gbxshopestimation_OnExpand(eventObject){          
        shopestimationtable.hideColumn(7);
}
/********************************************************************
 * 功能名称	: do_gpxanalyseinfo_OnExpand();
 * 描述: 展开工程groupbox时判断是否需要创建新记录
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_gpxanalyseinfo_OnExpand(eventObject){
		do_hasoldshop_Change(eventObject);
		do_hasopponent_Change(eventObject);
		var shopTargetAssignMntInfo  = cordys.selectXMLNode(compositeData,".//*[local-name()='SHOP_TARGET_ASSESMENT']");
		if(shopTargetAssignMntInfo)
        {
			var assesmentDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
			bindSingleTupleModel(GetShopTargetAssesmentObjectModel,compositeData,"SHOP_TARGET_ASSESMENT",assesmentDataNode);
        }
		createBusinessObjOnModel(GetShopTargetAssesmentObjectModel,gpxanalyseinfo,"SHOP_TARGET_ASSESMENT");
}

/********************************************************************
 * 功能名称	: do_gpxsuggestion_OnExpand();
 * 描述: 展开工程groupbox时判断是否需要创建新记录
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_gpxsuggestion_OnExpand(eventObject){
	   var shopTargetAssignMntInfo  = cordys.selectXMLNode(compositeData,".//*[local-name()='SHOP_TARGET_ASSESMENT']");
		if(shopTargetAssignMntInfo)
        {
			var assesmentDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
			bindSingleTupleModel(GetShopTargetAssesmentObjectModel,compositeData,"SHOP_TARGET_ASSESMENT",assesmentDataNode);
        }
		createBusinessObjOnModel(GetShopTargetAssesmentObjectModel,gpxsuggestion,"SHOP_TARGET_ASSESMENT");
}

/********************************************************************
 * 功能名称	: do_gbxadvertising_OnExpand();
 * 描述: 展开工程groupbox时,判断是否readonly输入字段
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_gbxadvertising_OnExpand(eventObject){
   do_lightboxad_Change(eventObject);
   do_isshowcase_Change(eventObject);
}

/********************************************************************
 * 功能名称	: do_gbxroadsituation_OnExpand(eventObject);
 * 描述: 展开工程groupbox时,判断是否readonly输入字段
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_gbxroadsituation_OnExpand(eventObject){
   do_isblockcode_Change(eventObject);
}

/********************************************************************
 * 功能名称	: do_gbxhouseproperty_OnExpand();
 * 描述:展开工程groupbox时,判断是否readonly输入字段
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_gbxhouseproperty_OnExpand(eventObject)
{
	do_isrentincreased_Change(eventObject);
}

/********************************************************************
 * 功能名称	: do_imgresources_Click();
 * 描述: 调用办证资料列表弹出页
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_imgresources_Click(eventObject){
    getDialogValue(eventObject,"",isfullresource,"FULLRESOURCE","办证资料列表","320px","230px","*");
}

/********************************************************************
 * 功能名称	: do_imgprovince_Click();
 * 描述: 调用省列表弹出页
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_imgprovince_Click(eventObject){
  getDialogValue(eventObject,shopareacode,provicedec,"CITYC","省列表","320px","230px","1");
}

function do_imghouseproperty_Click(eventObject){
	getDialogValue(eventObject,houseattr,houseproperty,"HOUSE_PROPERTY","房产性质列表","320px","230px","1");
}

function do_imglandlord_Click(eventObject){
	getDialogValue(eventObject,vendorno,landlord,"LANDLORD","房东性质列表","320px","230px","1");
}

/********************************************************************
 * 功能名称	: do_imgoneroom_Click();
 * 描述: 调用开间列表弹出页
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_imgoneroom_Click(eventObject)
{
    getDialogValue(eventObject,oneroomcode,oneroom,"ZMD_001","开间列表","320px","230px","1");
}

/********************************************************************
 * 功能名称	: do_imgbranchproperty_Click();
 * 描述: 调用所属网点性质列表弹出页
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_imgbranchproperty_Click(eventObject)
{
	 getDialogValue(eventObject,"",branchproperty,"BUSS_ATTR","网点性质","320px","230px","1");
}

/********************************************************************
 * 功能名称	: do_imglivingquality_Click();
 * 描述: 调用住宅区生活水平列表弹出页
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_imglivingquality_Click(eventObject)
{
	 getDialogValue(eventObject,livingqualitycode,livingqualitydec,"LIFE_LEVEL","住宅区生活水平如何","320px","230px","1");
}

/********************************************************************
 * 点击保存，用复合对象进行数据保存操作
 */
function saveCustomCompositeObject()
{ 
	//主数据赋值
    var isFlag=setShopMasterValue();
	if(isFlag==false)return;
	insertOperation = 1;
	saveCompositeObject(CompositeShopTargetInfoModel,modelCol_Single,modelCol_Multi);
    if(formid.getValue()!="" && document.frames["ifeattachment"].document.getElementById("formid")!=null){
     document.frames["ifeattachment"].document.getElementById("formid").value=formid.getValue();
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
		case "GetApprovalModel":
			fillValuesOnGetApprovalModel(modelDataDoc,modelBObjName,tupleNode);
		break;
		
		case "GetShopTargetCircleInfoObjectModel" :
		case "GetShopTargetCheckObjectModel" :
		case "GetShopTargetEstimationObjectModel" :
		case "GetShopTargetAssesmentObjectModel":
			createFormId(modelDataDoc,modelBObjName,tupleNode);
		break;
		
		case "GetShopTargetInfoObjectModel":
			fillValuesInGetShopTargetInfoObjectModel(modelDataDoc ,modelBObjName,tupleNode);
		break;
	}
}

/********************************************************************
 * 为需要的model增加formID节点
 */
function createFormId(modelDataDoc ,modelBObjName,tupleNode)
{
    var formIdNode = cordys.createElementNS(modelDataDoc,"http://schemas.cordys.com/tbpm/shop","FORM_ID");
    var parentNewNode =  cordys.selectXMLNode(tupleNode,".//*[local-name()='new']")
    cordys.setTextContent(formIdNode, formid.getValue());
    cordys.appendXMLNode(formIdNode, cordys.selectXMLNode(parentNewNode,".//*[local-name()='" + modelBObjName + "']"));
}

/******************************************************************** 
 * 功能名称	: fillValuesOnGetApprovalModel();
 * 描述: 为审批意见model增加必须节点
 * 输入参数：modelData modelBObjName tupleNode
 * 输出参数：无
 */
function fillValuesOnGetApprovalModel(modelData ,modelBObjName,tupleNode)
{
	//点击保存按钮时，添加ext1标志位，用于区分打开待办时审批意见是update还是insert
    if(submitOperation == 0)
    {
        var ext1Node = cordys.createElementNS(modelData,"http://schemas.cordys.com/tbpm/shop","EXT1");
		if(Workflow.getTaskInstanceId()!=null){ 
           cordys.setTextContent(ext1Node, Workflow.getTaskInstanceId());
        }else{
           cordys.setTextContent(ext1Node, "draft");
        }
        cordys.appendXMLNode(ext1Node, cordys.selectXMLNode(tupleNode,".//*[local-name()='" + modelBObjName + "']"));
    }
    var instanceNode = cordys.createElementNS(modelData,"http://schemas.cordys.com/tbpm/shop","INSTANCE_ID");
    var formIdNode = cordys.createElementNS(modelData,"http://schemas.cordys.com/tbpm/shop","FORM_ID");
    var stepCdNode = cordys.createElementNS(modelData,"http://schemas.cordys.com/tbpm/shop","STEP_CODE");
    var stepNmNode = cordys.createElementNS(modelData,"http://schemas.cordys.com/tbpm/shop","STEP_NAME");
    var chargerCdNode = cordys.createElementNS(modelData,"http://schemas.cordys.com/tbpm/shop","CHARGER_CODE");
    cordys.setTextContent(instanceNode,Workflow.getProcessInstanceId());
    cordys.setTextContent(formIdNode, iptformid.getValue());
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
 * 为TargetShopInfo model增加节点,用以区分一般店和商超店
 */
function fillValuesInGetShopTargetInfoObjectModel(modelData ,modelBObjName,tupleNode)
{   
	var formTypeNode = cordys.createElementNS(modelData,"http://schemas.cordys.com/tbpm/shop","FORM_TYPE");
	cordys.setTextContent(formTypeNode,"0");
	cordys.appendXMLNode(formTypeNode, cordys.selectXMLNode(tupleNode,".//*[local-name()='" + modelBObjName + "']"));
    
	var statusNode = cordys.createElementNS(modelData,"http://schemas.cordys.com/tbpm/shop","STATUS");
	if(submitOperation==1){
	   cordys.setTextContent(statusNode, "1");
	}else{
	   cordys.setTextContent(statusNode, "0");    //status=0
	}
    cordys.appendXMLNode(statusNode,cordys.selectXMLNode(tupleNode,".//*[local-name()='" + modelBObjName + "']"));
}

/********************************************************************
 * 功能名称	: bindDataToModels();
 * 描述: 将复合对象的响应结构拆分到子对象中，将数据显示到页面
 * 输入参数：responseNode，响应节点
 * 输出参数：无
 */
function bindDataToModels(responseNode)
{
		if(Workflow.getProcessInstanceId()!=null && submitOperation == 0)
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
		var shopTargetInfoNode = cordys.selectXMLNode(responseNode,".//*[local-name()='SHOP_TARGET_INFO']");
        if(shopTargetInfoNode)
        {
			 var shopInfoDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
			 bindSingleTupleModel(GetShopTargetInfoObjectModel,responseNode,"SHOP_TARGET_INFO",shopInfoDataNode);
        }
        
		 var shopMasterNode = cordys.selectXMLNode(responseNode,".//*[local-name()='SHOP_MASTER']");
		if(shopMasterNode)
        {
			var shopMasterDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
			bindSingleTupleModel(GetShopMasterModel,responseNode,"SHOP_MASTER",shopMasterDataNode);
        }
        var targetEstimationNodes = cordys.selectXMLNodes(responseNode,".//*[local-name()='SHOP_TARGET_ESTIMATION']");
        if(targetEstimationNodes && targetEstimationNodes.length>0)
        {
			var shopEstimationDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
			bindMultiTupleModel(GetShopTargetEstimationObjectModel,responseNode,"SHOP_TARGET_ESTIMATION",shopEstimationDataNode);
        }
        if(stepcode.getValue()=="15"||(gIsFlag==true && (gStatus=="1"||gStatus=="2"))){
			var shoptargetChkNode = cordys.selectXMLNode(compositeData,".//*[local-name()='SHOP_TARGET_CHECK']");
			if(shoptargetChkNode)
			{
				var shopCheckDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
				bindSingleTupleModel(GetShopTargetCheckObjectModel,compositeData,"SHOP_TARGET_CHECK",shopCheckDataNode);
			}
		}
		if(!insertOperation){
		    var shopTargetAssignMntInfo  = cordys.selectXMLNode(compositeData,".//*[local-name()='SHOP_TARGET_ASSESMENT']");
			if(shopTargetAssignMntInfo)
			{
				var assesmentDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
				bindSingleTupleModel(GetShopTargetAssesmentObjectModel,compositeData,"SHOP_TARGET_ASSESMENT",assesmentDataNode);
			}
            var shopTargetCircleInfo = cordys.selectXMLNode(compositeData,".//*[local-name()='SHOP_TARGET_CIRCLE_INFO']");
			if(shopTargetCircleInfo)
			{
				var busCircleDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
				bindSingleTupleModel(GetShopTargetCircleInfoObjectModel,compositeData,"SHOP_TARGET_CIRCLE_INFO",busCircleDataNode);
			}
			do_target_gpxcommon_OnExpand();
		}
}

function submitTask(eventObject)
{
   saveCustomCompositeObject();
   do_setShopTargetInfoStatus();
   if(CompositeShopTargetInfoModel.soapFaultOccurred) 
   { 
	  return;
   }
   //start process
   if(typeof(Workflow.getProcessInstanceId())=='undefined' || Workflow.getProcessInstanceId()==null)
   {
       var request = cordys.cloneXMLDocument(executeProcess.XMLDocument);
       cordys.setNodeText(request,".//*[local-name()='receiver']","Business Process Models/com/laiyifen/shop/mainthread/新开店主线流程");
       cordys.setNodeText(request,".//*[local-name()='ShopID']",shopid.getValue());
       cordys.setNodeText(request,".//*[local-name()='FormID']",formid.getValue());
       cordys.setNodeText(request,".//*[local-name()='CreatorDN']",getCurrentUserDN());
	   cordys.setNodeText(request,".//*[local-name()='CreateName']",userArray.userName);
	   cordys.setNodeText(request,".//*[local-name()='CreateTime']",getApplyTime());
	   cordys.setNodeText(request,".//*[local-name()='CreatorDeptDN']",userArray.deptCode);
       cordys.setNodeText(request,".//*[local-name()='DecisionAttribute']","00");
	   cordys.setNodeText(request,".//*[local-name()='CompanyCode']",userArray.companyCode);
	   cordys.setNodeText(request,".//*[local-name()='CompanyName']",userArray.companyName);
       cordys.setNodeText(request,".//*[local-name()='ProcessCode']","01");
       cordys.setNodeText(request,".//*[local-name()='DefaultDuration']",staticObjects.defaultDuration);
	   cordys.setNodeText(request,".//*[local-name()='ProcessName']","目标选址与发布");
       cordys.setNodeText(request,".//*[local-name()='Ext3']","0");
	   cordys.setNodeText(request,".//*[local-name()='Ext1']",selectcharge.getValue());
	   cordys.setNodeText(request,".//*[local-name()='Ext4']",chargedn.getValue());
       ExecuteProcessModel.clear();
       ExecuteProcessModel.setMethodRequest(request);
       ExecuteProcessModel.reset();
	   application.container.close();
    }
    else
    { 
	   decisionattribute.setValue("01");
       Workflow.completeTask();
    }
}

function GetRevertValue(rtnValue)
{
    decisionattribute.setValue(rtnValue);
    
    //complete current task
    if(decisionattribute.getValue() != "" && decisionattribute.getValue() != null)
    {
        saveCustomCompositeObject();
		if(CompositeShopTargetInfoModel.soapFaultOccurred) 
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
 * 功能名称	: do_imggooglemap_Click();
 * 描述: 展示GoogleMap地图
 * 输入参数：无
 * 输出参数：无
 */
function do_imggooglemap_Click(eventObject){
    gpxcommon.expand();
    tabgooglemap.select();
	var isFlag=do_validate_Provice();
	var shopAddress="";
	if(isFlag==true){
	   shopAddress=provicedec.getValue()+road.getValue()+unitnumber.getValue();
	}else{
	   shopAddress=provicedec.getValue()+region.getValue()+road.getValue()+unitnumber.getValue();
	}

	param={
	  shopAddress:shopAddress
	}
    application.select(googleMapApplication.XMLDocument.documentElement,param);
}

function do_tbbsuspend_Click(eventObject){
	showConfirm(false,stopHandler,"您确定执行终止流程操作吗？");
}

function stopHandler(confirmReturnValue){
    if (confirmReturnValue == 1)
    {
		submitOperation = 1;
		if(stepcode.getValue()!="35"|| stepcode.getValue()!="05")
		{   
			decisionattribute.setValue("02");
			saveCustomCompositeObject();
			submitOperation = 0;
			Workflow.completeTask();
		}
    }
}

/********************************************************************
 * 功能名称	: setShopMasterValue();
 * 描述: 主数据赋值
 * 输入参数：无
 * 输出参数：无
 */
function setShopMasterValue(){
	var isFlag=true;
	var isMunicipalitie=do_validate_Provice();
	var shopAddress="";
	var shopDetailAddress="";
	    shopAddress=road.getValue()+unitnumber.getValue();
	if(isMunicipalitie==true){
      shopDetailAddress=provicedec.getValue()+road.getValue()+unitnumber.getValue();
	}else{
	  shopDetailAddress=provicedec.getValue()+region.getValue()+road.getValue()+unitnumber.getValue();
	}
	if(shopDetailAddress.length>60){
	  application.notify("门店详细地址已超过60个字符,请精减相关信息.门店详细地址是通过省,市,区,路,门号组成.");
	  isFlag=false;
	}
    corpcode.setValue(companycode.getValue());
	corpname.setValue(companyname.getValue());
	if(shopDetailAddress!=""){
	    shopdetailaddr.setValue(shopDetailAddress);
	}
	salesorg.setValue(companycode.getValue());
	salesorgname.setValue(companyname.getValue());
	shoparea.setValue(provicedec.getValue());
	rentmaster.setValue(rent.getValue());
    agencyfeesmaster.setValue(agencyfees.getValue());
	propertymanagefees.setValue(propertymanagementfees.getValue());
	transferfeemaster.setValue(transferfee.getValue());
    useareamaster.setValue(totalusearea.getValue());
	rentperiodmaster.setValue(rentperiod.getValue());
	rentfreemaster.setValue(rentfree.getValue());
	if(houseproperty.getValue()!=""){
	   houseattrname.setValue(houseproperty.getValue());
	}
	if(landlord.getValue()!=""){
	  landlordproperty.setValue(landlord.getValue());
	}
    if(propertyaddress.getValue()!=""){
       shopadd2.setValue(propertyaddress.getValue());
	}
	if(shopAddress!=""){
	   shopaddr.setValue(shopAddress);
	}
	if(stepcode.getValue()=="15"){
	   var rowsCollection=shopestimationtable.getRows();
	   var junetosept="";
	   var octtomay="";
	   var onerentoccupation="";
	   var tworentcooupation="";
	   var rentoccupation="";
       for (var i=0;i<rowsCollection.length; i++) {
			var stepscodeObj=cordys.getElementById(rowsCollection[i],"stepscode");
			var junetoseptObj=cordys.getElementById(rowsCollection[i],"junetosept");
			var octtomayObj=cordys.getElementById(rowsCollection[i],"octtomay");
			var onerentoccupationObj=cordys.getElementById(rowsCollection[i],"onerentoccupation");
			var tworentcooupationObj=cordys.getElementById(rowsCollection[i],"tworentcooupation");
			var stepscode=stepscodeObj.getValue();
                junetosept=junetoseptObj.getValue();
                onerentoccupation=onerentoccupationObj.getValue();
			    tworentcooupation=tworentcooupationObj.getValue();
			    octtomay=octtomayObj.getValue();
	   }
	   outseasonrpice.setValue(junetosept);
	   seasonrpricle.setValue(octtomay);
	   if(onerentoccupation!=""&& tworentcooupation!=""){
		  if(parseFloat(onerentoccupation)>parseFloat(tworentcooupation))
		  {
             rentoccupation=tworentcooupation;
		  }else{
			 rentoccupation=onerentoccupation;
		  }
	   }
	   rentAccount.setValue(rentoccupation);
	}
	return isFlag;
}
/********************************************************************
 * 功能名称	: do_CompositeShopTargetInfoModel_OnSOAPFault();
 * 描述:SOAP请求出错处理
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_CompositeShopTargetInfoModel_OnSOAPFault(eventObject){
	eventObject.showError = false;
    application.notify("您的操作未能成功执行，请您重新尝试或联系系统管理员，谢谢");	
}

/********************************************************************
 * 功能名称	: do_isrentincreased_Change();
 * 描述: 根据租金是否递增来决定如何使用控件是否显示
 * 输入参数：eventObject
 * 输出参数：无
 */

 function do_isrentincreased_Change(eventObject){
	if(isrentincreased.getValue() == "1")
    {
        rentincreased.show();
		rentincreased.enable();
    }
    else
    {
        rentincreased.disable();
        rentincreased.setValue("");
    }	
 }

 /********************************************************************
 * 功能名称	: do_isblockcode_Change();
 * 描述: 根据店前是否有遮挡决定如何使用控件是否显示
 * 输入参数：eventObject
 * 输出参数：无
 */

 function do_isblockcode_Change(eventObject){
   if(isblockcode.getValue() == "1")
    {
        isblockdec.show();
        isblockdec.enable();
    }
    else
    {
        isblockdec.disable();
        isblockdec.setValue("");
    }
 }

/********************************************************************
 * 功能名称	: do_hasoldshop_Change();
 * 描述: 根据周边有无来伊份门店决定如何使用控件是否显示
 * 输入参数：eventObject
 * 输出参数：无
 */
 function do_hasoldshop_Change(eventObject){
    if(hasoldshop.getValue() == "1")
    {
        oldaddress.show();
		oldaddress.enable();
		oldoperating.show();
		oldoperating.enable();
		oldarea.show();
		oldarea.enable();
		distance.show();
		distance.enable();
		oldrental.show();
		oldrental.enable();
    }
    else
    {
        oldaddress.disable();
		oldaddress.setValue("");
		oldoperating.disable();
		oldoperating.setValue("");
		oldarea.disable();
		oldarea.setValue("");
		distance.disable();
		distance.setValue("");
		oldrental.disable();
		oldrental.setValue("");
    }
 }

/********************************************************************
 * 功能名称	: do_hasopponent_Change();
 * 描述: 根据周边有无来伊份门店决定如何使用控件是否显示
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_hasopponent_Change(eventObject){
   if(hasopponent.getValue() == "1")
    {
        opponentname.show();
		opponentname.enable();
		opponentarea.show();
		opponentarea.enable();
		turnover.show();
		turnover.enable();
		productvariety.show();
		productvariety.enable();
		contrast.show();
		contrast.enable();
    }
    else
    {
        opponentname.disable();
		opponentname.setValue("");
		opponentarea.disable();
		opponentarea.setValue("");
		turnover.disable();
		turnover.setValue("");
		productvariety.disable();
		productvariety.setValue("");
		contrast.disable();
		contrast.setValue("");
    }
}

/********************************************************************
 * 功能名称	: do_lightboxad_Change();
 * 描述: 根据灯箱广告有无决定如何使用控件是否显示
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_lightboxad_Change(eventObject){
    if(lightboxad.getValue() == "1")
    {
        lenght.show();
        lenght.enable();
		width.show();
		width.enable();
    }
    else
    {
        lenght.disable();
        lenght.setValue("");
        width.disable();
        width.setValue("");
    }
}

/********************************************************************
 * 功能名称	: do_isshowcase_Change();
 * 描述: 根据橱窗有无决定如何使用控件是否显示
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_isshowcase_Change(eventObject){
   if(isshowcase.getValue() == "1")
    {
        showcasenumber.show();
        showcasenumber.enable();
    }
    else
    {
        showcasenumber.disable();
        showcasenumber.setValue("");
    }
}

/********************************************************************
 * 功能名称	: do_Insertestimation_AfterInsert();
 * 描述: Table控件插入行后赋值
 * 输入参数：无
 * 输出参数：无
 */
function do_Insertestimation_AfterInsert(eventObject){
	 var rowInx = shopestimationtable.getIndex();
        operatoprole[rowInx].setValue(userArray.roleName);
        operatorname[rowInx].setValue(userArray.userName);
		stepscode[rowInx].setValue(stepcode.getValue());
    Insertestimation.disable();
}


/********************************************************************
 * 功能名称	: do_shopestimationtable_OnSelectRow();
 * 描述: 控制Table控件内容是否可修改
 * 输入参数：无
 * 输出参数：无
 */

function do_shopestimationtable_OnSelectRow(eventObject){
    var rowInx = shopestimationtable.getIndex();
    var stepCode=stepscode[rowInx].getValue();
    if(stepCode!=stepcode.getValue()&&(stepCode!="")){
      operatoprole[rowInx].disable();
      operatorname[rowInx].disable();
      junetosept[rowInx].disable();
      onerentoccupation[rowInx].disable();
      octtomay[rowInx].disable();
      tworentcooupation[rowInx].disable();
      stepscode[rowInx].disable();
    }
}

/********************************************************************
 * 功能名称	: do_deleteestimation_BeforeDelete();
 * 描述: 控制删除预估日销售金额
 * 输入参数：无
 * 输出参数：无
 */
function do_deleteestimation_BeforeDelete(eventObject){
    var rows=shopestimationtable.getCheckedRows();
    for(var i = 0;i < rows.length;i++)
    {
        var rowInx = rows[i].sectionRowIndex;
        var stepsCode = stepscode[rowInx].getValue();
		if(stepsCode!=stepcode.getValue()){
		   application.notify("只能删除自已填写日销售金额")
		   eventObject.returnValue=false;
		   return false;
		}
    }
}

/********************************************************************
 * 功能名称	: do_appresult_Change();
 * 描述: 审批意见中常用语变化时将常用语赋值到审批意见textarea中
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_appresult_Change(eventObject){
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
 * 功能名称	: do_tabCommon_ontabfocus();
 * 描述: 查找门店位置
 * 输入参数：无
 * 输出参数：无
 */
function do_tabCommon_ontabfocus(eventObject){
    if(eventObject.tabId=="tabgooglemap")
    {
		var isFlag=do_validate_Provice();
		var shopAddress="";
		if(isFlag==true){
           shopAddress=provicedec.getValue()+road.getValue()+unitnumber.getValue();
		}else{
           shopAddress=provicedec.getValue()+region.getValue()+road.getValue()+unitnumber.getValue();
		}
		if(shopAddress==""){
		   application.notify("请先输入门店详细地址.");
           eventObject.returnValue=false;
		}
	        param={
	             shopAddress:shopAddress
	        }
        application.select(googleMapApplication.XMLDocument.documentElement,param);
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
	if(submitOperation==1||submitOperation==2){
		CordysRoot.application.inform("操作已成功，请继续其它操作");
	}
	insertOperation="";
}

/********************************************************************
 * 功能名称	: validateEstimation();
 * 描述: 校验预估日销售金额是否为空
 * 输入参数：无
 * 输出参数：无
 */
function validateEstimation(){
	 var stepCodeArray = new Array();
	 var currentStepCode=stepcode.getValue();
     var rowsCollection=shopestimationtable.getRows();
	 var operatorname="";
	 var isFlag=true;
	 if(currentStepCode==""||currentStepCode=="05"||currentStepCode=="10"||currentStepCode=="15"||currentStepCode=="20"){
		 for (var i=0;i<rowsCollection.length; i++) {
			var  stepscodeObj= cordys.getElementById(rowsCollection[i],"stepscode");
			var  stepscode=stepscodeObj.getValue();
			var  junetoseptObj =cordys.getElementById(rowsCollection[i],"junetosept");
			var  junetosept=junetoseptObj.getValue();
			if(stepscode!=""||typeof(stepscode)!='undefined')
			{
				stepCodeArray.push(stepscode);
			}
		 }
		 if(stepCodeArray.length!=0){
			 if(stepCodeArray.toString().indexOf(currentStepCode)==-1){
				application.notify("请填写预估日销售金额.");
				isFlag=false;
			 }
		 }else{
			 if(junetosept==""||typeof(junetosept)=='undefined'){
				application.notify("请填写预估日销售金额.");
				isFlag=false;
			 }
		 }
	 }
	 return isFlag;
}

/********************************************************************
 * 功能名称	: validateEstimationCommon();
 * 描述: 校验是否填写预估日销售金额
 * 输入参数：无
 * 输出参数：无
 */
function validateEstimationCommon(){
	var stepCodeArray = new Array();
	var currentStepCode=stepcode.getValue();
    var rowsCollection=shopestimationtable.getRows();
    for (var i=0;i<rowsCollection.length; i++) {
		var  stepscodeObj= cordys.getElementById(rowsCollection[i],"stepscode");
		var  stepscode=stepscodeObj.getValue();
		if(stepscode!=""||typeof(stepscode)!='undefined')
		{
			stepCodeArray.push(stepscode);
		}
	 }
	 if(stepCodeArray.length!=0){
		 if(stepCodeArray.toString().indexOf(currentStepCode)!=-1){
			Insertestimation.disable();
		 }
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
 * 功能名称	: do_navRefresh1_AfterRefresh();
 * 描述: 点击刷新按钮后，判断是否隐藏插入按钮
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_navRefresh1_AfterRefresh(eventObject)
{
   do_common_ValidationRefreshButton();
}

/********************************************************************
 * 功能名称	: do_deleteestimation_AfterDelete();
 * 描述: 点击删除按钮后，判断是否隐藏插入按钮
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_deleteestimation_AfterDelete(eventObject)
{
   do_common_ValidationDeleteButton();
   shopestimationtable.refresh();
}

/********************************************************************
 * 功能名称	: do_common_ValidationInsertButton();
 * 描述: 点击删除按钮后，判断是否隐藏插入按钮
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_common_ValidationDeleteButton()
{
    var currentStepCode=stepcode.getValue();
    var rowsCollection=shopestimationtable.getRows();
	var stepCodeArray = new Array();
    for (var i=0;i<rowsCollection.length;i++){
		var  stepscodeObj= cordys.getElementById(rowsCollection[i],"stepscode");
		var  stepscode=stepscodeObj.getValue();
		if(stepscode!=""||typeof(stepscode)!='undefined')
		{
			stepCodeArray.push(stepscode);
		}
	}
	if(stepCodeArray.toString().indexOf(currentStepCode)==-1){
		Insertestimation.enable();
	}
}
/********************************************************************
 * 功能名称	: do_common_ValidationRefreshButton();
 * 描述: 点击刷新按钮后，判断是否隐藏插入按钮
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_common_ValidationRefreshButton()
{
    var bDataList = cordys.selectXMLNode(GetShopTargetEstimationObjectModel.getData(),".//*[local-name()='SHOP_TARGET_ESTIMATION']")
	var stepCodeArray = new Array();
	var currentStepCode=stepcode.getValue();
    if(bDataList && bDataList.length>0)
    {
        for(var i=0;i<bDataList.length; i++)
        {
            var estimationNode = cordys.selectXMLNode(bDataList[i],".");
			var stepscode=cordys.getNodeText(estimationNode,".//*[local-name()='STEP_CODE']");
            if(stepscode!=""||typeof(stepscode)!='undefined')
		    {
			   stepCodeArray.push(stepscode);
		    }
        }
    }
    if(stepCodeArray.toString().indexOf(currentStepCode)==-1)
	{
		Insertestimation.enable();
	}
}


/********************************************************************
 * 功能名称	: do_validationApprovalHistory();
 * 描述: 校验审批意见
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_validationApprovalHistory()
{
	var isFlag=true;
	if(remark.getValue()=="")
	{
	   application.notify("请输入您的审批意见");
	   gpxcommon.expand();
	   remark.setFocus();
	   isFlag=false;
	}
	return isFlag;
}

/********************************************************************
 * 功能名称	: do_junetosept_OutFocus();
 * 描述: 计算5到8月租金占比公式
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_junetosept_OutFocus(eventObject)
{
    var rowInx = shopestimationtable.getIndex();
	 var rentFloat="";
	 var agencyFeesFloat="";
	 var propertyManageFeesFloat="";
	 var getJuneToSeptFloat="";
	 var transferfeeFloat="";
	 var rentperiodInteger=0;
	 var getJuneToSept=junetosept[rowInx].getValue();
	 if(getJuneToSept!=""&& getJuneToSept!=null){
		getJuneToSeptFloat=parseFloat(getJuneToSept);
	 }
	 if(rent.getValue()!=""&& rent.getValue()!=null){
	    rentFloat=parseFloat(rent.getValue());
	 }
	 if(rentperiod.getValue()!=""&& rentperiod.getValue()!=null){
		rentperiodInteger=parseInt(rentperiod.getValue());
     }
	 if(agencyfees.getValue()!=""&& agencyfees.getValue()!=null){
		agencyFeesFloat=parseFloat(agencyfees.getValue())/rentperiodInteger;
	 }
	 if(propertymanagementfees.getValue()!=""&&propertymanagementfees.getValue()!=null){
	    propertyManageFeesFloat=parseFloat(propertymanagementfees.getValue())/12;
	 }
	 if(transferfee.getValue()!=""&& transferfee.getValue()!=null){
		transferfeeFloat=parseFloat(transferfee.getValue())/rentperiodInteger;
	 }

	 var onerentoccupationValue=((rentFloat+agencyFeesFloat+propertyManageFeesFloat+transferfeeFloat)*12/365/getJuneToSeptFloat)*100;
	 if(onerentoccupationValue!=""&& onerentoccupationValue!=null){
	    onerentoccupation[rowInx].setValue(onerentoccupationValue);
		onerentoccupation[rowInx].disable();
	 }
}

/********************************************************************
 * 功能名称	: do_octtomay_OutFocus();
 * 描述: 计算9到4月租金占比公式
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_octtomay_OutFocus(eventObject)
{
	 var rowInx = shopestimationtable.getIndex();
	 var rentFloat="";
	 var agencyFeesFloat="";
	 var propertyManageFeesFloat="";
	 var getOctToMayFloat="";
	 var transferfeeFloat="";
	 var rentperiodInteger=0;
	 var getocttomay=octtomay[rowInx].getValue();
	 if(getocttomay!=""&& getocttomay!=null){
		getOctToMayFloat=parseFloat(getocttomay);
	 }
	 if(rent.getValue()!=""&& rent.getValue()!=null){
	    rentFloat=parseFloat(rent.getValue());
	 }
	 if(rentperiod.getValue()!=""&& rentperiod.getValue()!=null){
		rentperiodInteger=parseInt(rentperiod.getValue());
     }
	 if(agencyfees.getValue()!=""&& agencyfees.getValue()!=null){
		agencyFeesFloat=parseFloat(agencyfees.getValue())/rentperiodInteger;
	 }
	 if(propertymanagementfees.getValue()!=""&&propertymanagementfees.getValue()!=null){
	    propertyManageFeesFloat=parseFloat(propertymanagementfees.getValue())/12;
	 }
	 if(transferfee.getValue()!=""&& transferfee.getValue()!=null){
		transferfeeFloat=parseFloat(transferfee.getValue())/rentperiodInteger;
	 }
	 var tworentcooupationValue=((rentFloat+agencyFeesFloat+propertyManageFeesFloat+transferfeeFloat)*12/365/getOctToMayFloat)*100;
	 if(tworentcooupationValue!=""&& tworentcooupationValue!=null)
	 {
	    tworentcooupation[rowInx].setValue(tworentcooupationValue);
		tworentcooupation[rowInx].disable();
	 }
}

/********************************************************************
 * 功能名称	: do_validation_Charge();
 * 描述: 判断是提交到子公司主管还是子公司开发经理
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_validation_Charge()
{
	var roleDN=staticObjects.chargeRole;
	var deptCode=userArray.deptCode;
    var request=GetUserInfoByBizModel.getMethodRequest();
	    cordys.setNodeText(request,".//*[local-name()='roleDn']",roleDN);
		cordys.setNodeText(request,".//*[local-name()='currentDeptCode']",deptCode);
        GetUserInfoByBizModel.setMethodRequest(request);
        GetUserInfoByBizModel.reset();

	var userInfoByRoleNode = cordys.selectXMLNode(GetUserInfoByBizModel.getData(),".//*[local-name()='EmployeeId']");
    if(userInfoByRoleNode)
    {
	  var data = new Object();
          data.roleDN = roleDN;
          data.currentDeptCode =deptCode;
          application.showDialog(dialogSelectCharge.XMLDocument.documentElement,data,null,GetSelectReturnValue, false);
	}else{
	   submitTask();
       submitOperation =0;
	}
   
}

/********************************************************************
 * 功能名称	: GetSelectReturnValue();
 * 描述: 选择页面返回值
 * 输入参数：eventObject
 * 输出参数：无
 */
function GetSelectReturnValue(rtnValue)
{
    var userType="";
	var userDN="";
	    formstatus.setValue("1");
	if(rtnValue && rtnValue.isReturn && rtnValue.isReturn!=""&& rtnValue.isReturn=="1"){
		if(rtnValue && rtnValue.userType && rtnValue.userType!=""){
			userType=rtnValue.userType;
		 if(userType=="0"){
			userDN=rtnValue.userDn;
			chargedn.setValue(userDN);
		 }
		   selectcharge.setValue(userType);
		   submitTask();
		   submitOperation =0;
		}
	}
}

/********************************************************************
 * 功能名称	: do_transferfee_Change(eventObject);
 * 描述: 控制转让费为0时，不需要输入.
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_transferfee_Change(eventObject){
   if(transferfee.getValue()==0){
	   transferfeeinfo.disable();
   }else{
	   transferfeeinfo.enable();
   }
}

/********************************************************************
 * 功能名称	: do_provicedec_Change(eventObject);
 * 描述: 选择直辖市时，城市不能输入.
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_provicedec_Change(eventObject){
   var isFlag=do_validate_Provice();
   if(isFlag==true){
	 region.setValue("");
     region.disable();
     street.setFocus();
   }else{
	 region.enable();
	 region.setFocus();
   }
}


/********************************************************************
 * 功能名称	: do_validate_Provice();
 * 描述: 校验是否是直辖市.
 * 输入参数：无
 * 输出参数：true/false
 */
function do_validate_Provice(){
   var isFlag=true;
   var proviceDecValue=provicedec.getValue();
   if(proviceDecValue.indexOf("北京")>=0||proviceDecValue.indexOf("上海")>=0||proviceDecValue.indexOf("天津")>=0||proviceDecValue.indexOf("重庆")>=0){
	   isFlag=true;
   }else{
       isFlag=false;
   }
   return isFlag;
}

/********************************************************************
 * 功能名称	: do_setShopTargetInfoStatus(eventObject);
 * 描述: 设置流程状态
 * 输入参数：无
 * 输出参数：无
 */
 
function do_setShopTargetInfoStatus(){
   var request = cordys.cloneXMLDocument(updateShopTargetInfoStatus.XMLDocument);
       cordys.setNodeText(request,".//*[local-name()='old']/*[local-name()='SHOP_TARGET_INFO']/*[local-name()='SHOP_ID']",shopid.value);
       cordys.setNodeText(request,".//*[local-name()='old']/*[local-name()='SHOP_TARGET_INFO']/*[local-name()='FORM_ID']",formid.value);
       cordys.setNodeText(request,".//*[local-name()='new']/*[local-name()='SHOP_TARGET_INFO']/*[local-name()='FORM_ID']",formid.value);
       cordys.setNodeText(request,".//*[local-name()='new']/*[local-name()='SHOP_TARGET_INFO']/*[local-name()='SHOP_ID']",shopid.value);
	   cordys.setNodeText(request,".//*[local-name()='new']/*[local-name()='SHOP_TARGET_INFO']/*[local-name()='STATUS']","1");
       UpdateShopTargetInfoModel.clear();
       UpdateShopTargetInfoModel.setMethodRequest(request);
       UpdateShopTargetInfoModel.reset();
}

/********************************************************************
 * 功能名称	: controlSelectOption();
 * 描述: 控制下拉框
 * 输入参数：无
 * 输出参数：无
 */
function controlSelectOption(eventObject){
	   do_isrentincreased_Change(eventObject);
       do_isblockcode_Change(eventObject);
	   do_hasoldshop_Change(eventObject);
	   do_hasopponent_Change(eventObject);
	   do_lightboxad_Change(eventObject);
	   do_isshowcase_Change(eventObject);
	   do_transferfee_Change(eventObject);
	   do_provicedec_Change(eventObject);
}

/********************************************************************
 * 功能名称	: do_provicedec_BeforeZoom();
 * 描述:选择省前调用
 * 输入参数：无
 * 输出参数：无
 */
function do_provicedec_BeforeZoom(eventObject){
   var frameNode = cordys.selectXMLNode(eventObject.applicationDefinition,".//*[local-name()='frame']"); 
   frameNode.setAttribute("features", "dialogWidth=650px, dialogHeight=800px");
   cordys.setTextContent(cordys.selectXMLNode(eventObject.applicationDefinition,".//*[local-name()='caption']"),"省/市信息列表");
   var data = new Object();
   data.type ="CITYC"; 
   eventObject.data = data;
}

/********************************************************************
 * 功能名称	: do_provicedec_BeforeZoom(eventObject);
 * 描述:选择省后调用
 * 输入参数：无
 * 输出参数：无
 */
function do_provicedec_AfterZoom(eventObject){
  eventObject.returnValue=false;
  var desc = cordys.getNodeText(eventObject.businessObject,".//*[local-name()='DESCRIPTION']");
  var code= cordys.getNodeText(eventObject.businessObject,".//*[local-name()='CODE']"); 
  if(desc!=null&&code!=null){
    provicedec.setValue(desc);
	shopareacode.setValue(code);
  }
}

/********************************************************************
 * 功能名称	: do_junetosept_InFocus(eventObject);
 * 描述:校验是否输入租金，物业费，中介费，转让费等
 * 输入参数：无
 * 输出参数：无
 */
function do_junetosept_InFocus(eventObject){
   if(rent.getValue()=="" && rentperiod.getValue()=="" && propertymanagementfees.getValue()=="" && agencyfees.getValue()=="" && transferfee.getValue()==""){
	   application.notify("请选输入租金,租房期限,物业管理费,中介费,转让费");
	   return;
   }
}

/********************************************************************
 * 功能名称	: do_target_gpxcommon_OnExpand(eventObject);
 * 描述:绑定审批历史记录
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_target_gpxcommon_OnExpand(eventObject){
    var param = new Object();
    param.formID = formid.getValue();
    param.userNm = userArray.userName;
    param.deptNm = userArray.deptName;
    application.select(attachmentApplication.XMLDocument.documentElement,param);

	//处理人暂存后再次打开待办时将审批意见显示到可编辑区域
	if(compositeData){
		var approvalNodes = cordys.cloneXMLDocument(compositeData);
		var approvalNodeList = cordys.selectXMLNodes(approvalNodes,".//*[local-name()='APPROVAL_HISTORY'][*[local-name()='EXT1']!='']");
		if(approvalNodeList && approvalNodeList.length>0)
		{
			for(var i=0; i < approvalNodeList.length; i++)
			{
				var approvalNode = cordys.selectXMLNode(approvalNodeList[i],  ".");
				var draftValue=cordys.getNodeText(approvalNode,".//*[local-name()='EXT1']");
				if(cordys.getNodeText(approvalNode,".//*[local-name()='EXT1']") == Workflow.getTaskInstanceId()||(Workflow.getTaskInstanceId()==null && draftValue=="draft"))
				{
					var approvalDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
					cordys.appendXMLNode(approvalNode,cordys.selectXMLNode(approvalDataNode,".//*[local-name()='old']"));
					pushdataToModel(GetApprovalModel,approvalDataNode);
				}
			}
		}

		var approvalHistoryNodeList = cordys.selectXMLNodes(compositeData,".//*[local-name()='APPROVAL_HISTORY']");
		if(approvalHistoryNodeList && approvalHistoryNodeList.length>0)
		{
			var approvalHistoryDataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
			bindMultiTupleModel(GetApprovalHistoryModel,compositeData,"APPROVAL_HISTORY",approvalHistoryDataNode);
		}
	}
}
/********************************************************************
 * 功能名称	: do_initialize_info();
 * 描述:初始化页面信息
 * 输入参数：无
 * 输出参数：无
 */
function do_initialize_info(){
   username.setValue(userArray.userName);
   usercode.setValue(userArray.userCode);
   departmentname.setValue(userArray.deptName);
   applicationdate.setValue(getApplyTime());
   companycode.setValue(userArray.companyCode);
   companyname.setValue(userArray.companyName);
   operatorrole.setValue(userArray.roleName);
   currenthandler.setValue(userArray.userName);
}

var gsShopId;
var gsShopNo;
var gsUserDN;
var gsUserCode;
var gsUserName;
var gsDeptCode;
var gsDeptName;
var gsCompanyCode;
var gsCompanyName;
var piIndex;	//记录索引

/********************************************************************
 * 功能名称: do_Form_InitDone()
 * 功能描述: 初始化当前用户信息，门店信息，以及门店已有的促销信息
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_Form_InitDone(eventObject)
{
	// 隐藏控件inputSHOP_ID
	inputSHOP_ID.hide();
	inputPROMOTION_GROUP_NAME.hide();
	
	// 设置调用Dialog所用到HTMLUtil
	application.addType(myutil,"wcp.library.util.HTMLUtil");
	
	// 处于流程中的表单，将封装页面的Taskbar隐藏
	if(Workflow.getProcessInstanceId()!=null){
		Workflow.hideTaskToolbar();
		printProgress(Workflow.getTask());
	}

	//用户在其任务完成时,只能查看表单内容,而不能修改表单内容
	var messageStatus = Workflow.getMessageStatus();
	if(messageStatus == "COMPLETED" || messageStatus == "ABORTED" || messageStatus == "TERMINATED")
	{
		toolbarbuttonAdd.disable();
		toolbarbuttonSave.disable();
		toolbarbuttonSubmit.disable();
		toolbarbuttonGroupConfig.disable();
	}

	// 查询用户信息，初始化用户的全局变量
	var getUserInfoReq  = cordys.cloneXMLDocument(SOAP_GetUserInfo.XMLDocument);
	SOAPRequestModel.clear();
	SOAPRequestModel.setMethodRequest(getUserInfoReq);
	SOAPRequestModel.reset();
	gsUserDN = cordys.getNodeText(SOAPRequestModel.getData(), ".//*[local-name()='userDN']", "");
	gsUserCode = cordys.getNodeText(SOAPRequestModel.getData(), ".//*[local-name()='userCode']", "");
	gsUserName = cordys.getNodeText(SOAPRequestModel.getData(), ".//*[local-name()='userName']", "");
	gsDeptCode = cordys.getNodeText(SOAPRequestModel.getData(), ".//*[local-name()='deptCode']", "");
	gsDeptName = cordys.getNodeText(SOAPRequestModel.getData(), ".//*[local-name()='deptName']", "");
	gsCompanyCode = cordys.getNodeText(SOAPRequestModel.getData(), ".//*[local-name()='companyCode']", "");
	gsCompanyName = cordys.getNodeText(SOAPRequestModel.getData(), ".//*[local-name()='companyName']", "");

	// 从隐藏控件中获取SHOP_ID的值
	gsShopId = inputSHOP_ID.getValue();

	// 查询门店信息，初始化表单上门店的信息
	var getShopMasterObjectReq = cordys.cloneXMLDocument(SOAP_GetShopMasterObject.XMLDocument);
	cordys.setNodeText(getShopMasterObjectReq, ".//*[local-name()='SHOP_ID']", gsShopId);
	SOAPRequestModel.clear();
	SOAPRequestModel.setMethodRequest(getShopMasterObjectReq);
	SOAPRequestModel.reset();
	gsShopNo = cordys.getNodeText(SOAPRequestModel.getData(), ".//*[local-name()='SHOP_NO']", "");

	outputSHOP_SERIALNO.setValue(cordys.getNodeText(SOAPRequestModel.getData(), ".//*[local-name()='SHOP_SERIALNO']", ""));
	outputSHOP_NO.setValue(cordys.getNodeText(SOAPRequestModel.getData(), ".//*[local-name()='SHOP_NO']", ""));
	outputSHOP_AREA.setValue(cordys.getNodeText(SOAPRequestModel.getData(), ".//*[local-name()='SHOP_AREA']", ""));
	outputSHOP_NAME.setValue(cordys.getNodeText(SOAPRequestModel.getData(), ".//*[local-name()='SHOP_NAME']", ""));
	outputSHOP_SHORTNAME.setValue(cordys.getNodeText(SOAPRequestModel.getData(), ".//*[local-name()='SHOP_SHORTNAME']", ""));
	outputSAP_NO.setValue(cordys.getNodeText(SOAPRequestModel.getData(), ".//*[local-name()='SAP_NO']", ""));
	outputSHOP_ADDR.setValue(cordys.getNodeText(SOAPRequestModel.getData(), ".//*[local-name()='SHOP_ADDR']", ""));
	outputSALES_CHANNEL_NAME.setValue(cordys.getNodeText(SOAPRequestModel.getData(), ".//*[local-name()='SALES_CHANNEL_NAME']", ""));
	outputSHOP_DETAIL_ADDR.setValue(cordys.getNodeText(SOAPRequestModel.getData(), ".//*[local-name()='SHOP_DETAIL_ADDR']", ""));
	outputSHOP_ADDR2.setValue(cordys.getNodeText(SOAPRequestModel.getData(), ".//*[local-name()='SHOP_ADDR2']", ""));
	outputCORP_NAME.setValue(cordys.getNodeText(SOAPRequestModel.getData(), ".//*[local-name()='CORP_NAME']", ""));

	// 查询门店的已有的促销信息
	var getShopPromotionalDetailByStatusReq = cordys.cloneXMLDocument(SOAP_GetShopPromotionalDetailByStatus.XMLDocument);
	cordys.setNodeText(getShopPromotionalDetailByStatusReq, ".//*[local-name()='SHOP_ID']", gsShopId);
	cordys.setNodeText(getShopPromotionalDetailByStatusReq, ".//*[local-name()='USER_CODE']", gsUserCode);
	cordys.setNodeText(getShopPromotionalDetailByStatusReq, ".//*[local-name()='STATUS']", "1");  //记录状态未定义 
	GetShopPromotionalDetailByStatusModel.clear();
	GetShopPromotionalDetailByStatusModel.setMethodRequest(getShopPromotionalDetailByStatusReq);
	GetShopPromotionalDetailByStatusModel.reset();

	// 隐藏列表中不显示的字段
	tableSHOP_PROMOTIONAL_DETAIL.hideColumn(7);
	tableSHOP_PROMOTIONAL_DETAIL.hideColumn(8);
	tableSHOP_PROMOTIONAL_DETAIL.hideColumn(9);
	tableSHOP_PROMOTIONAL_DETAIL.hideColumn(10);
	tableSHOP_PROMOTIONAL_DETAIL.hideColumn(11);
	tableSHOP_PROMOTIONAL_DETAIL.hideColumn(12);
	tableSHOP_PROMOTIONAL_DETAIL.hideColumn(13);
	tableSHOP_PROMOTIONAL_DETAIL.hideColumn(14);
	tableSHOP_PROMOTIONAL_DETAIL.hideColumn(15);
	tableSHOP_PROMOTIONAL_DETAIL.hideColumn(16);
	tableSHOP_PROMOTIONAL_DETAIL.hideColumn(17);
	tableSHOP_PROMOTIONAL_DETAIL.hideColumn(18);
	tableSHOP_PROMOTIONAL_DETAIL.hideColumn(19);
	tableSHOP_PROMOTIONAL_DETAIL.hideColumn(20);
	tableSHOP_PROMOTIONAL_DETAIL.hideColumn(21);
	tableSHOP_PROMOTIONAL_DETAIL.hideColumn(22);
}
/********************************************************************
 * 功能名称: do_tableSHOP_PROMOTIONAL_DETAIL_OnSelectRow()
 * 功能描述: 列表控件上选择某行数据的时候，将数据设置到INPUT控件
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_tableSHOP_PROMOTIONAL_DETAIL_OnSelectRow(eventObject)
{
	// 将列表中的某行数据设置到INPUT控件
	var rowIndex = eventObject.rowIndex;
	piIndex = rowIndex;

	inputPROMOTIONAL_THEME.setValue(columnPROMOTIONAL_THEME[rowIndex].getValue());
	inputCONDITION_GROUP_MER_CODE.setValue(columnCONDITION_GROUP_MER_CODE[rowIndex].getValue());
	inputRESULT_GROUP_MER_CODE.setValue(columnRESULT_GROUP_MER_CODE[rowIndex].getValue());
	inputMAX_NO.setValue(columnMAX_NO[rowIndex].getValue());
	inputPROMOTIONAL_SHOP.setValue(columnPROMOTIONAL_SHOP[rowIndex].getValue());
	inputPROMOTIONAL_SHOP_GROUP.setValue(columnPROMOTIONAL_SHOP_GROUP[rowIndex].getValue());
	textareaPROMOTIONAL_RULE_DESC.setValue(columnPROMOTIONAL_RULE_DESC[rowIndex].getValue());
	inputPROMOTION_GROUP_NAME.setValue(columnPROMOTION_GROUP_NAME[rowIndex].getValue());
	inputCONDITION_GROUP_TYPE.setValue(columnCONDITION_GROUP_TYPE[rowIndex].getValue());
	inputCONDITION_GROUP_MER_NAME.setValue(columnCONDITION_GROUP_MER_NAME[rowIndex].getValue());
	inputCONDITION_GROUP_NAME.setValue(columnCONDITION_GROUP_NAME[rowIndex].getValue());
	inputRESULT_GROUP_TYPE.setValue(columnRESULT_GROUP_TYPE[rowIndex].getValue());
	inputRESULT_GROUP_MER_NAME.setValue(columnRESULT_GROUP_MER_NAME[rowIndex].getValue());
	inputRESULT_GROUP_NO.setValue(columnRESULT_GROUP_NO[rowIndex].getValue());
	inputEXT1.setValue(columnEXT1[rowIndex].getValue());
	inputSTART_DATE.setValue(columnSTART_DATE[rowIndex].getValue());
	inputEND_DATE.setValue(columnEND_DATE[rowIndex].getValue());
	textareaREMARK.setValue(columnREMARK[rowIndex].getValue());
	inputPRE_PRICE.setValue(columnPRE_PRICE[rowIndex].getValue());

	// 选择输入的控件不允许输入操作
	inputPROMOTIONAL_SHOP.disable();
	inputCONDITION_GROUP_MER_CODE.disable();
	inputCONDITION_GROUP_MER_NAME.disable();
	inputCONDITION_GROUP_TYPE.disable();
	inputCONDITION_GROUP_NAME.disable();
	inputRESULT_GROUP_MER_CODE.disable();
	inputRESULT_GROUP_MER_NAME.disable();
	inputRESULT_GROUP_TYPE.disable();
	inputEXT1.disable();
}
/********************************************************************
 * 功能名称: do_toolbarbuttonAdd_Click()
 * 功能描述: 点击新增按钮
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_toolbarbuttonAdd_Click(eventObject)
{
	// 门店促销信息只有有一条
	if(tableSHOP_PROMOTIONAL_DETAIL.getRows().length >= 1){
		application.notify("只能增加一条促销明细！");
		return;
	}
	
	// 设置记录索引
	var rowIndex = tableSHOP_PROMOTIONAL_DETAIL.getRows();
	piIndex = rowIndex;
	
	// 列表控件创建一条记录
	tableSHOP_PROMOTIONAL_DETAIL.create();
	
	// 设置SHOP_NO
	inputPROMOTIONAL_SHOP.setValue(gsShopNo);
	columnPROMOTIONAL_SHOP[piIndex].setValue(gsShopNo);
	
	// 选择输入的控件不允许输入操作
	inputPROMOTIONAL_SHOP.disable();
	inputCONDITION_GROUP_MER_CODE.disable();
	inputCONDITION_GROUP_MER_NAME.disable();
	inputCONDITION_GROUP_TYPE.disable();
	inputCONDITION_GROUP_NAME.disable();
	inputRESULT_GROUP_MER_CODE.disable();
	inputRESULT_GROUP_MER_NAME.disable();
	inputRESULT_GROUP_TYPE.disable();
	inputEXT1.disable();
}
/********************************************************************
 * 功能名称: do_toolbarbuttonSave_Click()
 * 功能描述: 点击保存按钮，通过回调函数进行保存操作
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_toolbarbuttonSave_Click(eventObject)
{
	showConfirm(false, do_save, "确认要进行保存操作吗?");
}
/********************************************************************
 * 功能名称: do_save()
 * 功能描述: 将列表控件中的数据进行保存
 * 输入参数：confirmReturnValue
 * 输出参数：无
 */
function do_save(confirmReturnValue){

	// 保存操作确认
	if(1 == confirmReturnValue){

		// 从MODEL中获取各个节点
		var tupleNodes = cordys.selectXMLNodes(GetShopPromotionalDetailByStatusModel.getData(),".//*[local-name()='tuple']");
		
		// 判断MODEL中的数据是否有变化
		var hasModified;
		for(var i = 0; i < tupleNodes.length; i++){
			var tupleNode = cordys.selectXMLNode(tupleNodes[i], ".");
			hasModified = WebForm.getAttribute(tupleNode, "sync_id", false) >0?true:false;
			if(true == hasModified){
				break;
			}
		}
		if(false == hasModified){
			return;
		}
		
	
		// 查询SHOP_PROMOTION信息
		var queryShopPromotionReq = cordys.cloneXMLDocument(SOAP_QueryShopPromotionBySHOP_ID.XMLDocument);
		cordys.setNodeText(queryShopPromotionReq,".//*[local-name()='SHOP_ID']", gsShopId);

		SOAPRequestModel.clear();
		SOAPRequestModel.setMethodRequest(queryShopPromotionReq);
		SOAPRequestModel.reset();
		
		// 获取FORM_ID信息
		var queryShopPromotionReponse = SOAPRequestModel.getData();
		var formId = cordys.getNodeText(queryShopPromotionReponse, ".//*[local-name()='FORM_ID']", "");
		
		// 如果没有FORM_ID信息，说明主表SHOP_PROMOTION没有相应的促销信息
		if(undefined == formId || "" == formId){ 
			// 增加主表SHOP_PROMOTION中的记录
			var updateShopPromotionReq = cordys.cloneXMLDocument(SOAP_UpdateShopPromotion.XMLDocument);
			cordys.setNodeText(updateShopPromotionReq, ".//*[local-name()='SHOP_ID']", gsShopId);
			cordys.setNodeText(updateShopPromotionReq, ".//*[local-name()='USER_CODE']", gsUserCode);
			cordys.setNodeText(updateShopPromotionReq, ".//*[local-name()='USER_NAME']", gsUserName);
			cordys.setNodeText(updateShopPromotionReq, ".//*[local-name()='DEPARTMENT_CODE']", gsDeptCode);
			cordys.setNodeText(updateShopPromotionReq, ".//*[local-name()='DEPARTMENT_NAME']", gsDeptName);
			cordys.setNodeText(updateShopPromotionReq, ".//*[local-name()='COMPANY_CODE']", gsCompanyCode);
			cordys.setNodeText(updateShopPromotionReq, ".//*[local-name()='COMPANY_NAME']", gsCompanyName);
			cordys.setNodeText(updateShopPromotionReq, ".//*[local-name()='STATUS']", "1");	//记录状态信息未定义
			
			SOAPRequestModel.clear();
			SOAPRequestModel.setMethodRequest(updateShopPromotionReq);
			SOAPRequestModel.reset();
			
			// 设置FORM_ID的信息
			formId = cordys.getNodeText(SOAPRequestModel.getData(), ".//*[local-name()='FORM_ID']", "");
		}
		
		// 在GetShopPromotionalDetailByStatusModel中, 需要检查每个SHOP_PROMOTION_DETAIL节点，是否含有FORM_ID，以及SHOP_NO信息
		for(var x = 0; x < tupleNodes.length; x++){
			var tupleNode = cordys.selectXMLNode(tupleNodes[x], ".");
			var nodes = cordys.selectXMLNodes(tupleNode, ".//*[local-name()='SHOP_PROMOTIONAL_DETAIL']");
			for(var i = 0;i < nodes.length; i++)
			{
				var node = cordys.selectXMLNode(nodes[i],".");
				var old_formId = cordys.getNodeText(node, ".//*[local-name()='FORM_ID']", "");
				if(undefined == old_formId || "" == old_formId){
					// 节点中没有FORM_ID信息，需要加上
					var formIdNode = cordys.createElementNS(node.ownerDocument,"http://schemas.cordys.com/tbpm/shop","FORM_ID");
					cordys.setTextContent(formIdNode, formId);
					cordys.appendXMLNode(formIdNode, node);
				}
				var old_shopNo = cordys.getNodeText(node, ".//*[local-name()='SHOP_NO']", "");
				if(undefined == old_shopNo || "" == old_shopNo){
					// 节点中没有SHOP_NO信息，需要加上
					var shopNoNode = cordys.createElementNS(node.ownerDocument,"http://schemas.cordys.com/tbpm/shop","SHOP_NO");
					cordys.setTextContent(shopNoNode, gsShopNo);
					cordys.appendXMLNode(shopNoNode, node);
				}
			}
		}
		// 与后台进行数据的同步
		GetShopPromotionalDetailByStatusModel.reset();
		GetShopPromotionalDetailByStatusModel.refreshAllViews();
	}
}
function do_inputPROMOTIONAL_THEME_Change(eventObject)
{
	columnPROMOTIONAL_THEME[piIndex].setValue(inputPROMOTIONAL_THEME.getValue());
}
function do_inputCONDITION_GROUP_MER_CODE_Change(eventObject)
{
	columnCONDITION_GROUP_MER_CODE[piIndex].setValue(inputCONDITION_GROUP_MER_CODE.getValue());
}
function do_inputRESULT_GROUP_MER_CODE_Change(eventObject)
{
	columnRESULT_GROUP_MER_CODE[piIndex].setValue(inputRESULT_GROUP_MER_CODE.getValue());
}
function do_inputMAX_NO_Change(eventObject)
{
	columnMAX_NO[piIndex].setValue(inputMAX_NO.getValue());
}
function do_inputPROMOTIONAL_SHOP_Change(eventObject)
{
	columnPROMOTIONAL_SHOP[piIndex].setValue(inputPROMOTIONAL_SHOP.getValue());
}
function do_inputPROMOTIONAL_SHOP_GROUP_Change(eventObject)
{
	columnPROMOTIONAL_SHOP_GROUP[piIndex].setValue(inputPROMOTIONAL_SHOP_GROUP.getValue());
}
function do_textareaPROMOTIONAL_RULE_DESC_Change(eventObject)
{
	columnPROMOTIONAL_RULE_DESC[piIndex].setValue(textareaPROMOTIONAL_RULE_DESC.getValue());
}
function do_inputPROMOTION_GROUP_NAME_Change(eventObject)
{
	columnPROMOTION_GROUP_NAME[piIndex].setValue(inputPROMOTION_GROUP_NAME.getValue());
}
function do_inputCONDITION_GROUP_TYPE_Change(eventObject)
{
	columnCONDITION_GROUP_TYPE[piIndex].setValue(inputCONDITION_GROUP_TYPE.getValue());
}
function do_inputCONDITION_GROUP_MER_NAME_Change(eventObject)
{
	columnCONDITION_GROUP_MER_NAME[piIndex].setValue(inputCONDITION_GROUP_MER_NAME.getValue());
}
function do_inputCONDITION_GROUP_NAME_Change(eventObject)
{
	columnCONDITION_GROUP_NAME[piIndex].setValue(inputCONDITION_GROUP_NAME.getValue());
}
function do_inputRESULT_GROUP_TYPE_Change(eventObject)
{
	columnRESULT_GROUP_TYPE[piIndex].setValue(inputRESULT_GROUP_TYPE.getValue());
}
function do_inputRESULT_GROUP_MER_NAME_Change(eventObject)
{
	columnRESULT_GROUP_MER_NAME[piIndex].setValue(inputRESULT_GROUP_MER_NAME.getValue());
}
function do_inputRESULT_GROUP_NO_Change(eventObject)
{
	columnRESULT_GROUP_NO[piIndex].setValue(inputRESULT_GROUP_NO.getValue());
}
function do_inputSTART_DATE_Change(eventObject)
{
	var sd = inputSTART_DATE.getValue();
	if("" != sd ){
		var ssd = Date.fromCordysString(sd).format("yyyyMMdd");
		var n = new Date();
		var sn = n.format("yyyyMMdd");
		if(parseInt(ssd) < parseInt(sn)){
			application.notify("起始时间应该大于或者等于当期时间！");
			return;
		}
	}
	columnSTART_DATE[piIndex].setValue(inputSTART_DATE.getValue());
}
function do_inputEND_DATE_Change(eventObject)
{
	var ed = inputEND_DATE.getValue();
	var sd = inputSTART_DATE.getValue();
	if("" != ed){
		if("" == sd){
			application.notify("请输入起始时间！");
			return;
		}
		var sed = Date.fromCordysString(ed).format("yyyyMMdd");
		var n = new Date();
		var sn = n.format("yyyyMMdd");
		if(parseInt(sed) <= parseInt(sn)){
			application.notify("截止时间应该大于当期时间！");
			return;
		}
		var ssd = Date.fromCordysString(sd).format("yyyyMMdd");
		if(parseInt(sed) <= parseInt(ssd) ){
			application.notify("截止时间应该大于起始时间！");
			return;
		}
		if(parseInt(sed)-parseInt(ssd) != 6){
			application.notify("截止时间应该大于起始时间7天！");
			return;
		}
	}
	columnEND_DATE[piIndex].setValue(inputEND_DATE.getValue());
}
function do_inputEXT1_Change(eventObject)
{
	columnEXT1[piIndex].setValue(inputEXT1.getValue());
}
function do_textareaREMARK_Change(eventObject)
{
	columnREMARK[piIndex].setValue(textareaREMARK.getValue());
}
function do_inputPRE_PRICE_Change(eventObject)
{
	columnPRE_PRICE[piIndex].setValue(inputPRE_PRICE.getValue());
}
/********************************************************************
 * 功能名称: do_toolbarbuttonSubmit_Click()
 * 功能描述: 点击提交按钮，通过回调函数进行任务的提交
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_toolbarbuttonSubmit_Click(eventObject)
{
	//弹出提交的确认框
    showConfirm(false,do_Submit,"您确定提交任务到下一环节吗？");   
}
/********************************************************************
 * 功能名称: do_Submit()
 * 功能描述: 任务提交
 * 输入参数：confirmReturnValue
 * 输出参数：无
 */
function do_Submit(confirmReturnValue){
	if(1 == confirmReturnValue){
		do_save(confirmReturnValue);
		Workflow.completeTask();
	}
}
/********************************************************************
 * 功能名称: do_imageCONDITION_GROUP_MER_CODE_Click()
 * 功能描述: 检索条件组商品编码
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_imageCONDITION_GROUP_MER_CODE_Click(eventObject)
{
	var app=Application_SingleShopGroupConfig.XMLDocument.documentElement;
	application.showDialog(app, null, null, GetReturnValue_CONDITION_GROUP_MER_CODE, false);
	inputCONDITION_GROUP_MER_CODE.setFocus();
}
function GetReturnValue_CONDITION_GROUP_MER_CODE(returnValue){
	if(returnValue!="")
	{
		inputCONDITION_GROUP_MER_CODE.setValue(returnValue[0]);
		inputCONDITION_GROUP_MER_NAME.setValue(returnValue[1]);
	}
}
/********************************************************************
 * 功能名称: do_imageCONDITION_GROUP_TYPE_Click()
 * 功能描述: 检索条件组类型
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_imageCONDITION_GROUP_TYPE_Click(eventObject)
{
	getDialogValue(eventObject,inputCONDITION_GROUP_TYPE,inputCONDITION_GROUP_NAME,"ZREQTYPE","条件组类型","320px","230px","1");
	inputCONDITION_GROUP_TYPE.setFocus();
}
/********************************************************************
 * 功能名称: do_imageRESULT_GROUP_MER_CODE_Click()
 * 功能描述: 检索结果组商品编码
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_imageRESULT_GROUP_MER_CODE_Click(eventObject)
{
	var app=Application_SingleShopGroupConfig.XMLDocument.documentElement;
	application.showDialog(app, null, null, GetReturnValue_RESULT_GROUP_MER_CODE, false);
	inputRESULT_GROUP_MER_CODE.setFocus();
}
function GetReturnValue_RESULT_GROUP_MER_CODE(returnValue){
	if(returnValue!="")
	{
		inputRESULT_GROUP_MER_CODE.setValue(returnValue[0]);
		inputRESULT_GROUP_MER_NAME.setValue(returnValue[1]);
	}
}
/********************************************************************
 * 功能名称: do_imageRESULT_GROUP_TYPE_Click()
 * 功能描述: 检索结果组类型
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_imageRESULT_GROUP_TYPE_Click(eventObject)
{
	getDialogValue(eventObject,inputRESULT_GROUP_TYPE,inputEXT1,"ZRESULTTYPE","结果组类型","320px","230px","1");
	inputRESULT_GROUP_TYPE.setFocus();
}
/********************************************************************
 * 功能名称	: do_Form_BeforeClose();
 * 功能描述: 关闭页面前释放全局变量
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_Form_BeforeClose(eventObject)
{
	gsShopId = null;
	gsShopNo = null;
	gsUserDN = null;
	gsUserCode = null;
	gsUserName = null;
	gsDeptCode = null;
	gsDeptName = null;
	gsCompanyCode = null;
	gsCompanyName = null;
	piIndex = null;
}
/********************************************************************
 * 功能名称: do_toolbarbuttonGroupConfig_Click();
 * 功能描述: 调用物料组维护界面
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_toolbarbuttonGroupConfig_Click(eventObject)
{
	var app=Application_ShopGroupConfig.XMLDocument.documentElement;
	application.showDialog(app, null, null, null, false);
}
/********************************************************************
 * 功能名称: do_toolbarbuttonExit_Click();
 * 功能描述: 退出
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_toolbarbuttonExit_Click(eventObject)
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
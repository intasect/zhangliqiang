var gsUserDN;
var gsUserCode;
var gsUserName;
var gsDeptCode;
var gsDeptName;
var gsCompanyCode;
var gsCompanyName;
var gsHeadquarterFlag;
var psYear;	//年度
var psArea1;	//区域
var psArea2;	//地区
var piIndex;	//记录索引

/********************************************************************
*功能名称: do_Form_InitDone()
*功能描述: 初始化当前用户信息
*输入参数：eventObject
*输出参数：无
*/
function do_Form_InitDone(eventObject)
{
	//设置调用Dialog所用到HTMLUtil
	application.addType(myutil,"wcp.library.util.HTMLUtil");
	// 隐藏控件inputCIRCLE_PROPERTY_CODE
	inputCIRCLE_PROPERTY_CODE.hide();
	
	//查询当前用户信息
	var getUserInfoReq=cordys.cloneXMLDocument(SOAP_GetUserInfo.XMLDocument);
	SOAPRequestModel.clear();
	SOAPRequestModel.setMethodRequest(getUserInfoReq);
	SOAPRequestModel.reset();
	gsUserDN=cordys.getNodeText(SOAPRequestModel.getData(),".//*[local-name()='userDN']","");
	gsUserCode=cordys.getNodeText(SOAPRequestModel.getData(), ".//*[local-name()='userCode']", "");
	gsUserName = cordys.getNodeText(SOAPRequestModel.getData(), ".//*[local-name()='userName']", "");
	gsDeptCode = cordys.getNodeText(SOAPRequestModel.getData(), ".//*[local-name()='deptCode']", "");
	gsDeptName = cordys.getNodeText(SOAPRequestModel.getData(), ".//*[local-name()='deptName']", "");
	gsCompanyCode = cordys.getNodeText(SOAPRequestModel.getData(), ".//*[local-name()='companyCode']", "");
	gsCompanyName = cordys.getNodeText(SOAPRequestModel.getData(), ".//*[local-name()='companyName']", "");  
	if(undefined == gsCompanyCode || "" == gsCompanyCode || "1000" == gsCompanyCode){
		gsHeadquarterFlag = "true";
	}else{
		gsHeadquarterFlag = "false";
	}

	// 隐藏列表中不显示的字段
	SHOP_ANNUALPLAN_DETAILSTable.hideColumn(9);
	SHOP_ANNUALPLAN_DETAILSTable.hideColumn(10);
	SHOP_ANNUALPLAN_DETAILSTable.hideColumn(11);
	SHOP_ANNUALPLAN_DETAILSTable.hideColumn(12);
	SHOP_ANNUALPLAN_DETAILSTable.hideColumn(13);
	SHOP_ANNUALPLAN_DETAILSTable.hideColumn(14);
	SHOP_ANNUALPLAN_DETAILSTable.hideColumn(15);
	SHOP_ANNUALPLAN_DETAILSTable.hideColumn(16);
	SHOP_ANNUALPLAN_DETAILSTable.hideColumn(17);
	SHOP_ANNUALPLAN_DETAILSTable.hideColumn(18);
	SHOP_ANNUALPLAN_DETAILSTable.hideColumn(19);
	SHOP_ANNUALPLAN_DETAILSTable.hideColumn(20);
	SHOP_ANNUALPLAN_DETAILSTable.hideColumn(21);
	SHOP_ANNUALPLAN_DETAILSTable.hideColumn(22);
	SHOP_ANNUALPLAN_DETAILSTable.hideColumn(23);
	SHOP_ANNUALPLAN_DETAILSTable.hideColumn(24);

	// 输入控件初始化不可用
	inputAREA.disable();
	inputLOCATION.disable();
	inputHOUSE_NUMBER_RANGE.disable();
	inputCIRCLE_PROPERTY.disable();
	imagelookup.disable();
	inputMONTHLY_RENT.disable();
	inputEXPECTED_RESULT.disable();
	inputRENT_RATIO.disable();
	inputEXPECTED_NUMBER.disable();
	inputSHOP_AREA.disable();
	inputROOM_NUMBER.disable();
	inputTRANSFER_FEE.disable();
	inputAROUND_BUSINESS_STATE.disable();
	inputSECOND_LOCATION.disable();
	inputBEST_LOCATION.disable();
	textareaCIRCLE_BUSINESSSTATE_MATCH.disable();
	textareaPLAN_SECTIONLOCATION_MATCH.disable();
	textareaRENT_RESULT_REVIEW.disable();
	textareaGENERAL_AUDITOPINION.disable();
	textareaHEADQUARTERED_OPINION.disable();

	//如果用户是总部人员，隐藏新增和删除的功能按钮
	if("true" == gsHeadquarterFlag){
		toolbarbuttonNew.disable();
		toolbarbuttonDelete.disable();
	}
	
}
/********************************************************************
 * 功能名称: do_toolbarbuttonExit_Click()
 * 功能描述: 点击退出按钮
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_toolbarbuttonExit_Click(eventObject)
{
	//弹出退出的确认框
	showConfirm(false,do_Exit,"您确定退出吗？");
}
/********************************************************************
 * 功能名称: do_Exit()
 * 功能描述: 退出应用
 * 输入参数：confirmReturnValue
 * 输出参数：无
 */
function do_Exit(confirmReturnValue){
	if(1 == confirmReturnValue){
		application.container.close();
	}
}
/********************************************************************
 * 功能名称: do_toolbarbuttonQuery_Click()
 * 功能描述: 查询网点年度规划详细信息
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_toolbarbuttonQuery_Click(eventObject)
{
	//初始化输入控件
	inputAREA.setValue("");
	inputLOCATION.setValue("");
	inputHOUSE_NUMBER_RANGE.setValue("");
	inputCIRCLE_PROPERTY.setValue("");
	inputMONTHLY_RENT.setValue("");
	inputEXPECTED_RESULT.setValue("");
	inputRENT_RATIO.setValue("");
	inputEXPECTED_NUMBER.setValue("");
	inputSHOP_AREA.setValue("");
	inputROOM_NUMBER.setValue("");
	inputTRANSFER_FEE.setValue("");
	inputAROUND_BUSINESS_STATE.setValue("");
	inputSECOND_LOCATION.setValue("");
	inputBEST_LOCATION.setValue("");
	textareaCIRCLE_BUSINESSSTATE_MATCH.setValue("");
	textareaPLAN_SECTIONLOCATION_MATCH.setValue("");
	textareaRENT_RESULT_REVIEW.setValue("");
	textareaGENERAL_AUDITOPINION.setValue("");
	textareaHEADQUARTERED_OPINION.setValue("");
	
	//获取查询参数的值
	psYear = inputConditionYEAR.getValue();
	psArea1 = inputConditionAREA1.getValue();
	psArea2 = inputConditionAREA2.getValue();
	
	//检查查询参数
	if("" == psYear){
		application.notify("请输入年度的值！");
		return;
	}
	if("" == psArea1){
		application.notify("请输入地区的值！");
		return;
	}
	// 查询网点年度规划详细信息
	var getShopAnnualPlanDetailsByFilterReq= cordys.cloneXMLDocument(SOAP_GetShopAnnualPlanDetailsByFilter.XMLDocument);
	cordys.setNodeText(getShopAnnualPlanDetailsByFilterReq,".//*[local-name()='YEAR']",psYear);
	cordys.setNodeText(getShopAnnualPlanDetailsByFilterReq,".//*[local-name()='AREA1']",psArea1);
	cordys.setNodeText(getShopAnnualPlanDetailsByFilterReq,".//*[local-name()='AREA2']",psArea2);
	cordys.setNodeText(getShopAnnualPlanDetailsByFilterReq,".//*[local-name()='USER_CODE']",gsUserCode);
	cordys.setNodeText(getShopAnnualPlanDetailsByFilterReq,".//*[local-name()='HeadquarterFlag']",gsHeadquarterFlag);

	GetShopAnnualPlanDetailsByFilterModel.clear();
	GetShopAnnualPlanDetailsByFilterModel.setMethodRequest(getShopAnnualPlanDetailsByFilterReq);
	GetShopAnnualPlanDetailsByFilterModel.reset();

	// 根据用户是否为总部人员，设置输入控件是否可用，总部人员只能填写审批信息
	if(SHOP_ANNUALPLAN_DETAILSTable.getRows().length > 0){
		if("true"==gsHeadquarterFlag){
			textareaRENT_RESULT_REVIEW.enable();
			textareaGENERAL_AUDITOPINION.enable();
		textareaHEADQUARTERED_OPINION.enable();
		}else{
			inputAREA.disable();
			inputLOCATION.disable();
			inputHOUSE_NUMBER_RANGE.enable();
			inputCIRCLE_PROPERTY.enable();
			imagelookup.enable();
			inputMONTHLY_RENT.enable();
			inputEXPECTED_RESULT.enable();
			inputRENT_RATIO.enable();
			inputEXPECTED_NUMBER.enable();
			inputSHOP_AREA.enable();
			inputROOM_NUMBER.enable();
			inputTRANSFER_FEE.enable();
			inputAROUND_BUSINESS_STATE.enable();
			inputSECOND_LOCATION.enable();
			inputBEST_LOCATION.enable();
			textareaCIRCLE_BUSINESSSTATE_MATCH.enable();
			textareaPLAN_SECTIONLOCATION_MATCH.enable();
		}
	}
}
/********************************************************************
 * 功能名称: do_SHOP_ANNUALPLAN_DETAILSTable_OnSelectRow()
 * 功能描述: 列表控件上选择某行数据的时候，将数据设置到INPUT控件
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_SHOP_ANNUALPLAN_DETAILSTable_OnSelectRow(eventObject)
{
	// 将列表中的某行数据设置到INPUT控件
	var rowIndex = eventObject.rowIndex;
	piIndex = rowIndex;
	inputAREA.setValue(columnAREA[rowIndex].getValue());
	inputLOCATION.setValue(columnLOCATION[rowIndex].getValue());
	inputHOUSE_NUMBER_RANGE.setValue(columnHOUSE_NUMBER_RANGE[rowIndex].getValue());
	inputCIRCLE_PROPERTY.setValue(columnCIRCLE_PROPERTY[rowIndex].getValue());
	inputMONTHLY_RENT.setValue(columnMONTHLY_RENT[rowIndex].getValue());
	inputEXPECTED_RESULT.setValue(columnEXPECTED_RESULT[rowIndex].getValue());
	inputRENT_RATIO.setValue(columnRENT_RATIO[rowIndex].getValue());
	inputEXPECTED_NUMBER.setValue(columnEXPECTED_NUMBER[rowIndex].getValue());
	inputSHOP_AREA.setValue(columnSHOP_AREA[rowIndex].getValue());
	inputROOM_NUMBER.setValue(columnROOM_NUMBER[rowIndex].getValue());
	inputTRANSFER_FEE.setValue(columnTRANSFER_FEE[rowIndex].getValue());
	inputAROUND_BUSINESS_STATE.setValue(columnAROUND_BUSINESS_STATE[rowIndex].getValue());
	inputSECOND_LOCATION.setValue(columnSECOND_LOCATION[rowIndex].getValue());
	inputBEST_LOCATION.setValue(columnBEST_LOCATION[rowIndex].getValue());
	textareaCIRCLE_BUSINESSSTATE_MATCH.setValue(columnCIRCLE_BUSINESSSTATE_MATCH[rowIndex].getValue());
	textareaPLAN_SECTIONLOCATION_MATCH.setValue(columnPLAN_SECTIONLOCATION_MATCH[rowIndex].getValue());
	textareaRENT_RESULT_REVIEW.setValue(columnRENT_RESULT_REVIEW[rowIndex].getValue());
	textareaGENERAL_AUDITOPINION.setValue(columnGENERAL_AUDITOPINION[rowIndex].getValue());
	textareaHEADQUARTERED_OPINION.setValue(columnHEADQUARTERED_OPINION[rowIndex].getValue());
}
/********************************************************************
 * 功能名称: do_toolbarbuttonNew_Click()
 * 功能描述: 点击新增按钮
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_toolbarbuttonNew_Click(eventObject)
{
	// 获取查询参数
	psYear = inputConditionYEAR.getValue();
	psArea1 = inputConditionAREA1.getValue();
	psArea2 = inputConditionAREA2.getValue();
	// 检查查询参数
	if("" == psYear){
		application.notify("请输入年度的值！");
		return;
	}
	if("" == psArea1){
		application.notify("请输入区域的值！");
		return;
	}
	// 获取记录索引
	var rowIndex = SHOP_ANNUALPLAN_DETAILSTable.getRows();
	piIndex = rowIndex;
	// 列表控件创建一行记录
	SHOP_ANNUALPLAN_DETAILSTable.create();
	// 根据用户是否为总部人员，设置输入控件是否可用，总部人员只能填写审批信息
	if(SHOP_ANNUALPLAN_DETAILSTable.getRows().length > 0){
		if("true" == gsHeadquarterFlag){
			textareaRENT_RESULT_REVIEW.enable();
			textareaGENERAL_AUDITOPINION.enable();
			textareaHEADQUARTERED_OPINION.enable();
		}else{
			inputAREA.enable();
			inputLOCATION.enable();
			inputHOUSE_NUMBER_RANGE.enable();
			inputCIRCLE_PROPERTY.enable();
			imagelookup.enable();
			inputMONTHLY_RENT.enable();
			inputEXPECTED_RESULT.enable();
			inputRENT_RATIO.enable();
			inputEXPECTED_NUMBER.enable();
			inputSHOP_AREA.enable();
			inputROOM_NUMBER.enable();
			inputTRANSFER_FEE.enable();
			inputAROUND_BUSINESS_STATE.enable();
			inputSECOND_LOCATION.enable();
			inputBEST_LOCATION.enable();
			textareaCIRCLE_BUSINESSSTATE_MATCH.enable();
			textareaPLAN_SECTIONLOCATION_MATCH.enable();
		}
	}
}
function do_inputLOCATION_Change(eventObject)
{
	columnLOCATION[piIndex].setValue(inputLOCATION.getValue());
}
function do_inputHOUSE_NUMBER_RANGE_Change(eventObject)
{
	columnHOUSE_NUMBER_RANGE[piIndex].setValue(inputHOUSE_NUMBER_RANGE.getValue());
}
function do_inputTRANSFER_FEE_Change(eventObject)
{
	columnTRANSFER_FEE[piIndex].setValue(inputTRANSFER_FEE.getValue());
}
function do_inputMONTHLY_RENT_Change(eventObject)
{
	columnMONTHLY_RENT[piIndex].setValue(inputMONTHLY_RENT.getValue());
}
function do_inputEXPECTED_RESULT_Change(eventObject)
{
	columnEXPECTED_RESULT[piIndex].setValue(inputEXPECTED_RESULT.getValue());
}
function do_inputRENT_RATIO_Change(eventObject)
{
	columnRENT_RATIO[piIndex].setValue(inputRENT_RATIO.getValue());
}
function do_inputEXPECTED_NUMBER_Change(eventObject)
{
	columnEXPECTED_NUMBER[piIndex].setValue(inputEXPECTED_NUMBER.getValue());
}
function do_inputSHOP_AREA_Change(eventObject)
{
	columnSHOP_AREA[piIndex].setValue(inputSHOP_AREA.getValue());
}
function do_inputROOM_NUMBER_Change(eventObject)
{
	columnROOM_NUMBER[piIndex].setValue(inputROOM_NUMBER.getValue());
}
function do_inputAROUND_BUSINESS_STATE_Change(eventObject)
{
	columnAROUND_BUSINESS_STATE[piIndex].setValue(inputAROUND_BUSINESS_STATE.getValue());
}
function do_inputSECOND_LOCATION_Change(eventObject)
{
	columnSECOND_LOCATION[piIndex].setValue(inputSECOND_LOCATION.getValue());
}
function do_inputBEST_LOCATION_Change(eventObject)
{
	columnBEST_LOCATION[piIndex].setValue(inputBEST_LOCATION.getValue());
}
function do_textareaCIRCLE_BUSINESSSTATE_MATCH_Change(eventObject)
{
	columnCIRCLE_BUSINESSSTATE_MATCH[piIndex].setValue(textareaCIRCLE_BUSINESSSTATE_MATCH.getValue());
}
function do_textareaPLAN_SECTIONLOCATION_MATCH_Change(eventObject)
{
	columnPLAN_SECTIONLOCATION_MATCH[piIndex].setValue(textareaPLAN_SECTIONLOCATION_MATCH.getValue());
}
function do_textareaRENT_RESULT_REVIEW_Change(eventObject)
{
	columnRENT_RESULT_REVIEW[piIndex].setValue(textareaRENT_RESULT_REVIEW.getValue());
	columnCHECKER_NAME[piIndex].setValue(gsUserName);
	columnCHECKER_CODE[piIndex].setValue(gsUserCode);
}
function do_textareaGENERAL_AUDITOPINION_Change(eventObject)
{
	columnGENERAL_AUDITOPINION[piIndex].setValue(textareaGENERAL_AUDITOPINION.getValue());
	columnCHECKER_NAME[piIndex].setValue(gsUserName);
	columnCHECKER_CODE[piIndex].setValue(gsUserCode);
}
function do_textareaHEADQUARTERED_OPINION_Change(eventObject)
{
	columnHEADQUARTERED_OPINION[piIndex].setValue(textareaHEADQUARTERED_OPINION.getValue());
	columnCHECKER_NAME[piIndex].setValue(gsUserName);
	columnCHECKER_CODE[piIndex].setValue(gsUserCode);
}
function do_inputAREA_Change(eventObject)
{
	columnAREA[piIndex].setValue(inputAREA.getValue());
}
/********************************************************************
 * 功能名称: SOAPRequestModel_OnSOAPFault()
 * 功能描述: SOAPRequestModel出错处理函数
 * 输入参数：eventObject
 * 输出参数：无
 */
function SOAPRequestModel_OnSOAPFault(eventObject){
	eventObject.showError = false;
	application.notify('SOAPRequestModel出错了','错误',null, null);
}
/********************************************************************
 * 功能名称: GetShopAnnualPlanDetailsByFilterModel_OnSOAPFault()
 * 功能描述: GetShopAnnualPlanDetailsByFilterModel出错处理函数
 * 输入参数：eventObject
 * 输出参数：无
 */
function GetShopAnnualPlanDetailsByFilterModel_OnSOAPFault(eventObject){
	eventObject.showError = false;
	application.notify('GetShopAnnualPlanDetailsByFilterModel出错了','错误',null, null);
}
/********************************************************************
 * 功能名称: do_toolbarbuttonSave_Click()
 * 功能描述: 点击保存按钮
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_toolbarbuttonSave_Click(eventObject)
{
	if(SHOP_ANNUALPLAN_DETAILSTable.getRows().length > 0){
		showConfirm(false, do_Save, "确认要进行保存操作吗?");
	}
}
/********************************************************************
 * 功能名称: do_Save()
 * 功能描述: 保存列表控件中的数据
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_Save(confirmReturnValue)
{
	// 保存操作确认
	if(1 == confirmReturnValue){
	
		// 从MODEL中获取各个节点
		var tupleNodes = cordys.selectXMLNodes(GetShopAnnualPlanDetailsByFilterModel.getData(),".//*[local-name()='tuple']");
		
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

		// 查询网点年度规划信息
		var queryShopAnnualplanByFilterReq = cordys.cloneXMLDocument(SOAP_QueryShopAnnualplanByFilter.XMLDocument);
		cordys.setNodeText(queryShopAnnualplanByFilterReq,".//*[local-name()='YEAR']", psYear);
		cordys.setNodeText(queryShopAnnualplanByFilterReq,".//*[local-name()='AREA']", psArea1);
		cordys.setNodeText(queryShopAnnualplanByFilterReq,".//*[local-name()='USER_CODE']", gsUserCode);

		SOAPRequestModel.clear();
		SOAPRequestModel.setMethodRequest(queryShopAnnualplanByFilterReq);
		SOAPRequestModel.reset();
		
		// 获取FORM_ID信息
		var queryShopAnnualplanByFilterReponse = SOAPRequestModel.getData();
		var formId = cordys.getNodeText(queryShopAnnualplanByFilterReponse, ".//*[local-name()='FORM_ID']", "");

		// 如果没有FORM_ID信息，说明主表SHOP_ANNUALPLAN没有相应的年度规划信息
		if(undefined == formId || "" == formId){ 
			// 增加SHOP_ANNUALPLAN的记录
			var updateShopAnnualplanReq = cordys.cloneXMLDocument(SOAP_UpdateShopAnnualplan.XMLDocument);
			cordys.setNodeText(updateShopAnnualplanReq, ".//*[local-name()='YEAR']", psYear);
			cordys.setNodeText(updateShopAnnualplanReq, ".//*[local-name()='AREA']", psArea1);
			cordys.setNodeText(updateShopAnnualplanReq, ".//*[local-name()='USER_DN']", gsUserDN);
			cordys.setNodeText(updateShopAnnualplanReq, ".//*[local-name()='USER_CODE']", gsUserCode);
			cordys.setNodeText(updateShopAnnualplanReq, ".//*[local-name()='USER_NAME']", gsUserName);
			cordys.setNodeText(updateShopAnnualplanReq, ".//*[local-name()='DEPARTMENT_CODE']", gsDeptCode);
			cordys.setNodeText(updateShopAnnualplanReq, ".//*[local-name()='DEPARTMENT_NAME']", gsDeptName);
			cordys.setNodeText(updateShopAnnualplanReq, ".//*[local-name()='SUBCOMPANY_CODE']", gsCompanyCode);
			cordys.setNodeText(updateShopAnnualplanReq, ".//*[local-name()='SUBCOMPANY_NAME']", gsCompanyName);
			cordys.setNodeText(updateShopAnnualplanReq, ".//*[local-name()='STATUS']", "C");

			SOAPRequestModel.clear();
			SOAPRequestModel.setMethodRequest(updateShopAnnualplanReq);
			SOAPRequestModel.reset();

			// 获取FORM_ID信息
			formId = cordys.getNodeText(SOAPRequestModel.getData(), ".//*[local-name()='FORM_ID']", "");
		}

		// 在GetShopAnnualPlanDetailsByFilterModel中, 需要检查每个SHOP_ANNUALPLAN_DETAILS节点，是否含有FORM_ID信息
		for(var x = 0; x < tupleNodes.length; x++){
			var tupleNode = cordys.selectXMLNode(tupleNodes[x], ".");
			var nodes = cordys.selectXMLNodes(tupleNode, ".//*[local-name()='SHOP_ANNUALPLAN_DETAILS']");
			for(var i = 0;i < nodes.length; i++)
			{
				var node = cordys.selectXMLNode(nodes[i],".");
				var old_formId = cordys.getNodeText(node, ".//*[local-name()='FORM_ID']", "");
				if(undefined == old_formId || "" == old_formId){
					// 增加FORM_ID信息
					var formIdNode = cordys.createElementNS(node.ownerDocument,"http://schemas.cordys.com/tbpm/shop","FORM_ID");
					cordys.setTextContent(formIdNode, formId);
					cordys.appendXMLNode(formIdNode, node);
				}
			}
		}
		GetShopAnnualPlanDetailsByFilterModel.reset();
		GetShopAnnualPlanDetailsByFilterModel.refreshAllViews();
	}
}
/********************************************************************
 * 功能名称: do_toolbarbuttonDelete_Click()
 * 功能描述: 点击删除按钮
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_toolbarbuttonDelete_Click(eventObject)
{
	showConfirm(false, do_Delete, "确认要删除这些数据吗?");
}
/********************************************************************
 * 功能名称: do_Delete()
 * 功能描述: 进行删除操作
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_Delete(confirmReturnValue)
{
	if(1 == confirmReturnValue){
		SHOP_ANNUALPLAN_DETAILSTable.remove();
	}
}
/********************************************************************
 * 功能名称: do_imagelookup_Click()
 * 功能描述: 检索商圈性质
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_imagelookup_Click(eventObject)
{
	getDialogValue(eventObject,inputCIRCLE_PROPERTY_CODE,inputCIRCLE_PROPERTY,"001","商圈性质","320px","230px","1");
	inputCIRCLE_PROPERTY.setFocus();
}
function do_inputCIRCLE_PROPERTY_Change(eventObject)
{
	columnCIRCLE_PROPERTY[piIndex].setValue(inputCIRCLE_PROPERTY.getValue());
}
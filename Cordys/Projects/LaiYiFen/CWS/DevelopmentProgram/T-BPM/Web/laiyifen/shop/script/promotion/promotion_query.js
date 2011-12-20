var gsUserDN;
var gsUserCode;
var gsUserName;
var gsDeptCode;
var gsDeptName;
var gsCompanyCode;
var gsCompanyName;
var gsHeadquarterFlag;
var piIndex;	// 记录索引
var psCompanyCode;	// 公司代码
var psShopNo;	// 门店编号
var psStartDate;	// 开始时间
var psEndDate;	// 结束时间

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
 * 功能名称: do_Form_InitDone()
 * 功能描述: 初始化当前用户信息，门店信息，以及门店已有的促销信息
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_Form_InitDone(eventObject)
{
    // 查询当前用户信息
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
    if(undefined == gsCompanyCode || "" == gsCompanyCode || "1000" == gsCompanyCode){
        gsHeadquarterFlag = "true";
    } else {
        gsHeadquarterFlag = "false";
    }
    
	inputPROMOTION_GROUP_NAME.hide();
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
 * 功能名称: do_toolbarbuttonQuery_Click()
 * 功能描述: 点击查询按钮
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_toolbarbuttonQuery_Click(eventObject)
{
	// 初始化输入控件
	inputPROMOTIONAL_THEME.setValue("");
    inputCONDITION_GROUP_MER_CODE.setValue("");
    inputRESULT_GROUP_MER_CODE.setValue("");
    inputMAX_NO.setValue("");
    inputPROMOTIONAL_SHOP.setValue("");
    inputPROMOTIONAL_SHOP_GROUP.setValue("");
    textareaPROMOTIONAL_RULE_DESC.setValue("");
    inputCONDITION_GROUP_NAME.setValue("");
    inputCONDITION_GROUP_TYPE.setValue("");
    inputCONDITION_GROUP_MER_NAME.setValue("");
    inputPRE_PRICE.setValue("");
    inputRESULT_GROUP_TYPE.setValue("");
    inputRESULT_GROUP_MER_NAME.setValue("");
    inputRESULT_GROUP_NO.setValue("");
	inputEXT1.setValue("");
    inputSTART_DATE.setValue("");
    inputEND_DATE.setValue("");
    textareaREMARK.setValue("");
	// 获取查询参数
    psCompanyCode = conditionCOMPANY_CODE.getValue();
    psShopNo = conditionSHOP_NO.getValue();
    psStartDate = conditionSTART_DATE.getValue();
    psEndDate = conditionEND_DATE.getValue();
	// 查询促销规则详细信息
    var getShopPromotionalDetailByFilterReq = cordys.cloneXMLDocument(SOAP_GetShopPromotionalDetailByFilter.XMLDocument);
    cordys.setNodeText(getShopPromotionalDetailByFilterReq, ".//*[local-name()='COMPANY_CODE']", psCompanyCode);
    cordys.setNodeText(getShopPromotionalDetailByFilterReq, ".//*[local-name()='SHOP_NO']", psShopNo);
    cordys.setNodeText(getShopPromotionalDetailByFilterReq, ".//*[local-name()='START_DATE']", psStartDate);
    cordys.setNodeText(getShopPromotionalDetailByFilterReq, ".//*[local-name()='END_DATE']", psEndDate);
    cordys.setNodeText(getShopPromotionalDetailByFilterReq, ".//*[local-name()='USER_CODE']", gsUserCode);
    cordys.setNodeText(getShopPromotionalDetailByFilterReq, ".//*[local-name()='HeadquarterFlag']", gsHeadquarterFlag);

    GetShopPromotionalDetailByFilterModel.clear();
    GetShopPromotionalDetailByFilterModel.setMethodRequest(getShopPromotionalDetailByFilterReq);
    GetShopPromotionalDetailByFilterModel.reset()

}
/********************************************************************
 * 功能名称: do_tableSHOP_PROMOTIONAL_DETAIL_OnSelectRow()
 * 功能描述: 将列表控件中的字段值设置到INPUT控件中
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_tableSHOP_PROMOTIONAL_DETAIL_OnSelectRow(eventObject)
{
    // 将列表控件中的字段值设置到INPUT控件中
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
}
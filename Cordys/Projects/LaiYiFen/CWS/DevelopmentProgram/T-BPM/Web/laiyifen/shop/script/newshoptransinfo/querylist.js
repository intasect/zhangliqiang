var userInfo;

/********************************************************************
 * 功能名称	: do_Form_InitDone();
 * 描述: 表单加载完成后增加table的双击事件
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_Form_InitDone(eventObject)
{
	cordys.addDOMListener(shoptransinfotable,"ondblclick",getTransInfoDetail);
	shoptransinfotable.hideColumn(6);
	shoptransinfotable.hideColumn(7);
	userInfo = getUserInfo();
	if(userInfo.companyCode != "1000")
	{
		imgcompany.hide();
		iptsubcompany.disable();
		iptsubcompany.setValue(userInfo.companyName);
	}
}

/********************************************************************
 * 功能名称	: do_Form_InitDone();
 * 描述: 点击工具栏的查询按钮，进行查询操作
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_tbbsearch_Click(eventObject)
{
    var request = GetShopTransObjectsByConditionsModel.getMethodRequest();
    cordys.setNodeText(request,".//*[local-name()='shopNo']",iptshopno.getValue());
    cordys.setNodeText(request,".//*[local-name()='shopName']",iptshopname.getValue());
    cordys.setNodeText(request,".//*[local-name()='subCompany']",iptsubcompany.getValue());
    cordys.setNodeText(request,".//*[local-name()='startDate']",iptstartdate.getFormattedValue());
    cordys.setNodeText(request,".//*[local-name()='endDate']",iptenddate.getFormattedValue());
    
    GetShopTransObjectsByConditionsModel.setMethodRequest(request);
    GetShopTransObjectsByConditionsModel.reset();
}

/********************************************************************
 * 功能名称	: getTransInfoDetail();
 * 描述: 双击table的某一行，打开详细页面
 * 输入参数：无
 * 输出参数：无
 */
function getTransInfoDetail()
{
    var rowInx = shoptransinfotable.getIndex();
    if(formtype[rowInx].getValue() == "0")
    {
        application.select(shoptDetailApplication.XMLDocument.documentElement,shopid[rowInx].getValue());
    }
    else if(formtype[rowInx].getValue() == "1")
    {
        application.select(supermarketDetailApplication.XMLDocument.documentElement,shopid[rowInx].getValue());
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
	application.container.close();
}

/********************************************************************
 * 功能名称	: do_GetShopTransObjectsByConditionsModel_OnSOAPFault();
 * 描述: 与后台交互发生soap请求失败时，提示友好信息
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_GetShopTransObjectsByConditionsModel_OnSOAPFault(eventObject)
{
		eventObject.showError = false;
    application.notify("您的操作未能成功执行，请您重新尝试或联系系统管理员，谢谢");
}
/********************************************************************
 * 功能名称	: do_Form_InitDone();
 * 描述: 表单加载完成后增加table的双击事件
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_Form_InitDone(eventObject)
{
	gpxcondition.hide();
	shopmaterialconfigurationtable.hideColumn(4);
	shopmaterialconfigurationtable.hideColumn(5);
	iptsubcompanycd.setValue("1000");
	iptsubcompanynm.setValue("上海来伊份股份有限公司");
 do_tbbsearch_Click(eventObject);
}

/********************************************************************
 * 功能名称	: do_imgcompany_Click();
 * 描述: 子公司信息列表弹出页
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_imgcompany_Click(eventObject)
{
		getSubCompany(eventObject,iptsubcompanycd,iptsubcompanynm,"");
}

/********************************************************************
 * 功能名称	: do_tbbsearch_Click();
 * 描述: 点击工具栏的查询按钮，进行查询操作
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_tbbsearch_Click(eventObject)
{
    if(iptsubcompanycd.getValue()=="" || iptsubcompanycd.getValue()==null)
    {
    	application.notify("请在查询区域选择子公司编码和子公司名称，谢谢");	
    	return;
    }
    var request = GetShopMaterialConfigurationsModel.getMethodRequest();
    cordys.setNodeText(request,".//*[local-name()='subCompanyCd']",iptsubcompanycd.getValue());
    cordys.setNodeText(request,".//*[local-name()='subCompanyNm']",iptsubcompanynm.getValue());
    
    GetShopMaterialConfigurationsModel.setMethodRequest(request);
    GetShopMaterialConfigurationsModel.reset();
    GetShopMaterialConfigurationsModel.refreshAllViews();
}

/********************************************************************
 * 功能名称	: do_tbbsave_Click();
 * 描述: 点击工具栏的保存按钮，触发此方法
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_tbbsave_Click(eventObject)
{
    //页面校验
    if(!WebForm.validateForm(application.container.applicationId)) return;
    //弹出保存的确认框
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
 * 功能名称	: saveForm();
 * 描述: 发送SOAP请求，进行保存操作
 * 输入参数：无
 * 输出参数：无
 */
function saveForm()
{
	var rows=shopmaterialconfigurationtable.getRows();
	for (var i=1;i<rows.length+1; i++) {
		subcompanycode[i].setValue(iptsubcompanycd.getValue());
		subcompanyname[i].setValue(iptsubcompanynm.getValue());
	}
	GetShopMaterialConfigurationsModel.synchronize(true);
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
 * 功能名称	: do_GetShopMaterialConfigurationsModel_OnSOAPFault();
 * 描述: 与后台交互发生soap请求失败时，提示友好信息
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_GetShopMaterialConfigurationsModel_OnSOAPFault(eventObject)
{
		eventObject.showError = false;
    application.notify("您的操作未能成功执行，请您重新尝试或联系系统管理员，谢谢");
}
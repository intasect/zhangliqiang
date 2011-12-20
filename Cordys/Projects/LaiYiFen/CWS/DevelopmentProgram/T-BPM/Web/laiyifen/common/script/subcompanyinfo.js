var isReturn = 0;
String.prototype.trim=function(){return this.replace(/(^\s*)|(\s*$)/g, "");}

/********************************************************************
 * 功能名称	: do_Form_InitDone();
 * 描述: 表单加载完成后查询数据并增加table的双击事件
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_Form_InitDone(eventObject)
{
	cordys.addDOMListener(orgcompanyinfotable,"ondblclick",do_btnconfrim_Click);
	do_tbbsearch_Click(eventObject);
}

/********************************************************************
 * 功能名称	: do_tbbsearch_Click();
 * 描述: 点击工具栏的查询按钮，进行查询操作
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_tbbsearch_Click(eventObject)
{
    var request = GetOrgCompanyByconditionsModel.getMethodRequest();
    cordys.setNodeText(request,".//*[local-name()='subcompanyCd']",iptsubcompanycd.getValue());
    cordys.setNodeText(request,".//*[local-name()='subcompanyNm']",iptsubcompanynm.getValue());
    
    GetOrgCompanyByconditionsModel.setMethodRequest(request);
    GetOrgCompanyByconditionsModel.reset();
    GetOrgCompanyByconditionsModel.refreshAllViews();
}

/********************************************************************
 * 功能名称	: do_btnconfrim_Click();
 * 描述: 点击确认按钮，关闭表单
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_btnconfrim_Click(eventObject)
{
    isReturn = 1;
application.container.close();
}

/********************************************************************
 * 功能名称	: do_btncancel_Click();
 * 描述: 点击取消按钮时，关闭表单
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_btncancel_Click(eventObject)
{
    isReturn = 0;
    application.container.close();
}

/********************************************************************
 * 功能名称	: dialogReturnValue();
 * 描述: 关闭表单时调用的函数，用于将数据返回父页面
 * 输入参数：无
 * 输出参数：无
 */
function dialogReturnValue()
{
    var returnValue=new Array();
    if(isReturn == 1)
    {
        var rowInx = orgcompanyinfotable.getIndex();
        returnValue[0] = companycode[rowInx].getValue();
        returnValue[1] = (companyname[rowInx].getValue()).trim();
    }
    return returnValue;
}

/********************************************************************
 * 功能名称	: do_GetOrgCompanyByconditionsModel_OnSOAPFault();
 * 描述: 与后台交互发生soap请求失败时，提示友好信息
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_GetOrgCompanyByconditionsModel_OnSOAPFault(eventObject)
{
		eventObject.showError = false;
    application.notify("您的操作未能成功执行，请您重新尝试或联系系统管理员，谢谢");
}
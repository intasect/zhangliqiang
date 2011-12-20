/*
var formType = new Array();    //用于区分一般店和商超店的标志。0：一般店；1：商超店
var shopid = new Array();    //用于记录table中每行数据的shopid
var formid= new Array();    //用于记录table中每行数据的formid
var status= new Array();    //用于记录table中每行数据的status
*/
var staticObjects="";
var userInfo;
/********************************************************************
 * 功能名称	: do_Form_InitDone();
 * 描述: 表单加载完成后增加table的双击事件
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_Form_InitDone(eventObject)
{
    cordys.addDOMListener(shoptargetinfotable,"ondblclick",getShopTargetInfoDetail);
	userInfo = getUserInfo();
	staticObjects=getStaticValue();
	iptuserid.hide();
	iptuserid.setValue(userInfo.userCode);
	shoptargetinfotable.hideColumn(7);
	shoptargetinfotable.hideColumn(8);
	iptstartdate.setValue(getLastSixTime());
	iptenddate.setValue(getApplyTime());
}

/********************************************************************
 * 功能名称	: do_tbbsearch_Click(eventObject);
 * 描述: 点击工具栏的查询按钮，进行查询操作
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_tbbsearch_Click(eventObject)
{
    var request = GetShopTargetInfoByFilterModel.getMethodRequest();
    cordys.setNodeText(request,".//*[local-name()='companyName']","");
    cordys.setNodeText(request,".//*[local-name()='propertyAddress']",iptpropertyaddress.getValue());
    cordys.setNodeText(request,".//*[local-name()='rentPeriod']",iptrentperiod.getValue());
    cordys.setNodeText(request,".//*[local-name()='propertyName']",iptpropertyname.getValue());
    cordys.setNodeText(request,".//*[local-name()='startDate']",iptstartdate.getFormattedValue());
    cordys.setNodeText(request,".//*[local-name()='endDate']",iptenddate.getFormattedValue());
    cordys.setNodeText(request,".//*[local-name()='userId']",iptuserid.getValue());
    cordys.setNodeText(request,".//*[local-name()='formType']","");
    
    GetShopTargetInfoByFilterModel.setMethodRequest(request);
    GetShopTargetInfoByFilterModel.reset();
    GetShopTargetInfoByFilterModel.refreshAllViews();
}

function getShopTargetInfoDetail()
{               
    var rowInx = shoptargetinfotable.getIndex();
	var isFlagValue=true;
	param={
	    shopID:shopid[rowInx].getValue(), 
		formID:formid[rowInx].getValue(),
	    status:statusname[rowInx].getValue(),
		isFlag:isFlagValue
	}
    if(formtype[rowInx].getValue()== "0")
    {
        application.select(shoptDetailApplication.XMLDocument.documentElement,param);
    }
    else if(formtype[rowInx].getValue()== "1")
    {
        application.select(supermarketDetailApplication.XMLDocument.documentElement,param);
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
 * 功能名称	: do_tbbaddtarget_Click();
 * 描述: 弹出目示调查表(一般店)增加页面
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_tbbaddtarget_Click(eventObject){
	application.select(shoptDetailApplication.XMLDocument.documentElement);
}

/********************************************************************
 * 功能名称	: do_tbbaddsuper_Click();
 * 描述: 弹出目标调查表(商超店)增加页面
 * 输入参数：eventObject
 * 输出参数：无
 */
function do_tbbaddsuper_Click(eventObject){
   application.select(supermarketDetailApplication.XMLDocument.documentElement);
}

/********************************************************************
 * 功能名称	: do_tbbreset_Click(eventObject);
 * 描述: 重置清空查询输入条件
 * 输入参数：eventObject
 * 输出参数：无
 */

function do_tbbreset_Click(eventObject)
{
   iptpropertyaddress.setValue("");
   iptrentperiod.setValue("");
   iptpropertyname.setValue("");
   iptstartdate.setValue("");
   iptenddate.setValue("");
}
var staticObjects="";
var userInfo;
var companyName;
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
	if(userInfo.companyCode != staticObjects.mainCompCode)
	{
		imgcompany.hide();
		iptcompanyname.disable();
		iptcompanyname.setValue(userInfo.companyName);
        iptcompanycode.setValue(userInfo.companyCode);
	}else{
        iptcompanyname.setValue(userInfo.companyName);
		iptcompanycode.setValue(userInfo.companyCode);
	}
	shoptargetinfotable.hideColumn(7);
	shoptargetinfotable.hideColumn(8);
	iptcompanycode.hide();
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
	if(iptcompanycode.getValue()!=""&& iptcompanycode.getValue()==staticObjects.mainCompCode){
		companyName="";
	}else{
		companyName=iptcompanyname.getValue();
	}
    var request = GetShopTargetInfoByFilterModel.getMethodRequest();
    cordys.setNodeText(request,".//*[local-name()='companyName']",companyName);
    cordys.setNodeText(request,".//*[local-name()='propertyAddress']",iptpropertyaddress.getValue());
    cordys.setNodeText(request,".//*[local-name()='rentPeriod']",iptrentperiod.getValue());
    cordys.setNodeText(request,".//*[local-name()='propertyName']",iptpropertyname.getValue());
    cordys.setNodeText(request,".//*[local-name()='startDate']",iptstartdate.getFormattedValue());
    cordys.setNodeText(request,".//*[local-name()='endDate']",iptenddate.getFormattedValue());
	cordys.setNodeText(request,".//*[local-name()='userId']","");
	cordys.setNodeText(request,".//*[local-name()='formType']","0");
    
    GetShopTargetInfoByFilterModel.setMethodRequest(request);
    GetShopTargetInfoByFilterModel.reset();
    GetShopTargetInfoByFilterModel.refreshAllViews();
    //putHideDatas();
}

/*
function putHideDatas()
{
    var dataNodes = cordys.selectXMLNodes(GetShopTargetInfoByFilterModel.getData(),".//*[local-name()='SHOP_TARGET_INFO']");
    var rows=shoptargetinfotable.getRows();
     for (var i=1;i<rows.length+1; i++) {
           formType[i] = cordys.getNodeText(dataNodes.item(i-1),".//*[local-name()='FORM_TYPE']");
           shopid[i] = cordys.getNodeText(dataNodes.item(i-1),".//*[local-name()='SHOP_ID']");
		   formid[i] = cordys.getNodeText(dataNodes.item(i-1),".//*[local-name()='FORM_ID']");
           status[i] = cordys.getNodeText(dataNodes.item(i-1),".//*[local-name()='STATUS']");
     }
}
*/

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
 * 功能名称	: do_tbbreset_Click(eventObject);
 * 描述: 重置清空查询输入条件
 * 输入参数：eventObject
 * 输出参数：无
 */

function do_tbbreset_Click(eventObject)
{
   iptcompanycode.setValue("");
   iptcompanyname.setValue("");
   iptrentperiod.setValue("");
   iptpropertyaddress.setValue("");
   iptpropertyname.setValue("");
   iptstartdate.setValue("");
   iptenddate.setValue("");
}
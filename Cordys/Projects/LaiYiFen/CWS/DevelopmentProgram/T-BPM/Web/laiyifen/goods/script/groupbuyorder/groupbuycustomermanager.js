function do_Form_InitDone(eventObject)
{
	gpxhide.hide();
	
	//初始化grid双击事件
	GROUPBUY_CUSTOMERTable.ondblclick=function(){ 
		GROUPBUY_CUSTOMERTable_ondblclick();
    };
	
	var request = GetSelectGroupbuyCustomerObjectsModel.getMethodRequest();
	cordys.setNodeText(request ,".//*[local-name()='customerCode']",customercode.getValue());
	cordys.setNodeText(request ,".//*[local-name()='customerName']",customername.getValue());
	
	GetSelectGroupbuyCustomerObjectsModel.setMethodRequest(request);
	GetSelectGroupbuyCustomerObjectsModel.reset();
	GetSelectGroupbuyCustomerObjectsModel.refreshAllViews();

}

//刷新grid模型数据
﻿function do_tbbsearch_Click(eventObject)
{
	var request = GetSelectGroupbuyCustomerObjectsModel.getMethodRequest();
	cordys.setNodeText(request ,".//*[local-name()='customerCode']",customercode.getValue());
	cordys.setNodeText(request ,".//*[local-name()='customerName']",customername.getValue());
	
	GetSelectGroupbuyCustomerObjectsModel.setMethodRequest(request);
	GetSelectGroupbuyCustomerObjectsModel.reset();
	GetSelectGroupbuyCustomerObjectsModel.refreshAllViews();
}

function GROUPBUY_CUSTOMERTable_ondblclick()
{
	iptCustomerCode.setValue(customer_code[GROUPBUY_CUSTOMERTable.getIndex()].getValue());
	iptCustomerName.setValue(customer_name[GROUPBUY_CUSTOMERTable.getIndex()].getValue());
	iptCustomerAddr.setValue(customer_addr[GROUPBUY_CUSTOMERTable.getIndex()].getValue());
	application.container.close();
}

function dialogReturnValue()
{
	var data = new Object();
	data.subcustomercode = iptCustomerCode.getValue();
	data.subcustomername = iptCustomerName.getValue();
	data.subcustomeraddr = iptCustomerAddr.getValue();
	return data;
}
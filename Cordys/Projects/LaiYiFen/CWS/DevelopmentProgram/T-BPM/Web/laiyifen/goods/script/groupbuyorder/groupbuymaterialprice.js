//var dataArray = new Array();

function do_Form_InitDone(eventObject)
{
      gpxhide.hide();
      GROUPBUY_MATERIAL_PRICETable.hideColumn(6);
      GROUPBUY_MATERIAL_PRICETable.hideColumn(7);
      GROUPBUY_MATERIAL_PRICETable.hideColumn(8);
      GROUPBUY_MATERIAL_PRICETable.hideColumn(9);
      GROUPBUY_MATERIAL_PRICETable.hideColumn(10);
      
      
      if(undefined != application.event.data && undefined != application.event.data.suborderid && null != application.event.data.suborderid)
      {
    	  groupbuyorderid.setValue(application.event.data.suborderid);
    	  saleorgcode.setValue(application.event.data.subsaleorgcode);
    	  factoryShipment.setValue(application.event.data.subfactoryshipment);
    	  producttype.setValue(application.event.data.subproducttype);
    	  
    	  if (undefined != application.event.data.subproductcode)
		  {
    		  iptProductCode.setValue(application.event.data.subproductcode);
    		  productcode.setValue(application.event.data.subproductcode);
		  }
    	  else
		  {
    		  iptProductCode.setValue("");
		  }
    	  if (undefined != application.event.data.subproductname)
		  {
    		  iptProductName.setValue(application.event.data.subproductname);
		  }
    	  else
		  {
    		  iptProductName.setValue("");
		  }
    	  
            var request = GetSelectGroupbuyMaterialPriceObjectsModel.getMethodRequest();
            cordys.setNodeText(request ,".//*[local-name()='saleOrgCode']",saleorgcode.getValue());
            cordys.setNodeText(request ,".//*[local-name()='productType']",producttype.getValue());
            cordys.setNodeText(request ,".//*[local-name()='orderId']",groupbuyorderid.getValue());
            cordys.setNodeText(request ,".//*[local-name()='productCode']",iptProductCode.getValue());
            cordys.setNodeText(request ,".//*[local-name()='productName']",iptProductName.getValue());

            GetSelectGroupbuyMaterialPriceObjectsModel.setMethodRequest(request);
            GetSelectGroupbuyMaterialPriceObjectsModel.reset();
            GetSelectGroupbuyMaterialPriceObjectsModel.refreshAllViews();
      }

}

function do_tbbSave_Click(eventObject)
{
          var checkedRows = GROUPBUY_MATERIAL_PRICETable.getCheckedRows();
          if (checkedRows.length <= 0)
         {
             application.notify ("请至少选择一条数据！");
             return;
         }
          var getnewdataXMLDocument = cordys.cloneXMLDocument(newdata.XMLDocument);
          var newNode = cordys.selectXMLNode(getnewdataXMLDocument ,".//*[local-name()='UpdateGroupbuyOrderDetail']");
          var itemGateg = "";
          
          switch(producttype.getValue())
     	 {
     	 	case "2":
     	 		itemGateg = "Z09";
     	 		break;
     	 	case "1":
     	 		itemGateg = "Z01";
     	 		break;
     	 	case "3":
     	 		itemGateg = "TAX";
     	 		break;
     	 	case "4":
     	 		itemGateg = "Z01";
     	 		break;
     	 }
          
          for (var i = 0; i < checkedRows.length; i++)
         {
                var getdatainfoXMLDocument = cordys.cloneXMLDocument(datainfo.XMLDocument);
                var getdatainfoNode =  cordys.selectXMLNode(getdatainfoXMLDocument ,".//*[local-name()='tuple']");
                
                cordys.setNodeText(getdatainfoNode ,".//*[local-name()='ORDER_ID']", groupbuyorderid.getValue());
                cordys.setNodeText(getdatainfoNode ,".//*[local-name()='PRODUCT_NAME']", ext1[checkedRows[i].index].getValue());
                cordys.setNodeText(getdatainfoNode ,".//*[local-name()='PRODUCT_CODE']", product_code[checkedRows[i].index].getValue());
                if (product_code[checkedRows[i].index].getValue() == "000000000000080800")
            	{
                	cordys.setNodeText(getdatainfoNode ,".//*[local-name()='STANDARD']", "10");
            	}
                cordys.setNodeText(getdatainfoNode ,".//*[local-name()='OLD_UNIT_PRICE']", price[checkedRows[i].index].getValue());
                cordys.setNodeText(getdatainfoNode ,".//*[local-name()='UNIT_PRICE']", price[checkedRows[i].index].getValue());
                cordys.setNodeText(getdatainfoNode ,".//*[local-name()='PRODUCT_TYPE']", producttype.getValue());
                cordys.setNodeText(getdatainfoNode ,".//*[local-name()='SALES_UNIT']", sales_unit[checkedRows[i].index].getValue());
                //cordys.setNodeText(getdatainfoNode ,".//*[local-name()='EXT1']", ext4[checkedRows[i].index].getValue());
                cordys.setNodeText(getdatainfoNode ,".//*[local-name()='ITEM_CATEG']", itemGateg);
                cordys.appendXMLNode(getdatainfoNode ,newNode);
                
//                var data = new Object();
//                data.orderid = groupbuyorderid.getValue();
//                data.productname = ext1[checkedRows[i].index].getValue();
//                data.productcode = product_code[checkedRows[i].index].getValue();
//                data.price = price[checkedRows[i].index].getValue();
//                data.producttype = producttype.getValue();
//                data.salesunit = sales_unit[checkedRows[i].index].getValue();
//                data.itemgateg = itemGateg;
//                dataArray.push(data);
         }
         
          UpdateGroupbuyOrderDetailModel.setMethodRequest(getnewdataXMLDocument);
          UpdateGroupbuyOrderDetailModel.reset();
          UpdateGroupbuyOrderDetailModel.refreshAllViews();

          var request = GetSelectGroupbuyMaterialPriceObjectsModel.getMethodRequest();
          cordys.setNodeText(request ,".//*[local-name()='saleOrgCode']",saleorgcode.getValue());
          cordys.setNodeText(request ,".//*[local-name()='productType']",producttype.getValue());
          cordys.setNodeText(request ,".//*[local-name()='orderId']",groupbuyorderid.getValue());
          cordys.setNodeText(request ,".//*[local-name()='productCode']",iptProductCode.getValue());
          cordys.setNodeText(request ,".//*[local-name()='productName']",iptProductName.getValue());

          GetSelectGroupbuyMaterialPriceObjectsModel.setMethodRequest(request);
          GetSelectGroupbuyMaterialPriceObjectsModel.reset();
          GetSelectGroupbuyMaterialPriceObjectsModel.refreshAllViews();
            
            if(UpdateGroupbuyOrderDetailModel.soapFaultOccurred)
            {
                return;
            }
            else
        	{
            	application.inform("保存成功");
        	}
            
}

//刷新grid模型数据
﻿function do_tbbsearch_Click(eventObject)
{
	var request = GetSelectGroupbuyMaterialPriceObjectsModel.getMethodRequest();
    cordys.setNodeText(request ,".//*[local-name()='saleOrgCode']",saleorgcode.getValue());
    cordys.setNodeText(request ,".//*[local-name()='productType']",producttype.getValue());
    cordys.setNodeText(request ,".//*[local-name()='orderId']",groupbuyorderid.getValue());
    cordys.setNodeText(request ,".//*[local-name()='productCode']",productcode.getValue());
    cordys.setNodeText(request ,".//*[local-name()='productName']",productname.getValue());

    GetSelectGroupbuyMaterialPriceObjectsModel.setMethodRequest(request);
    GetSelectGroupbuyMaterialPriceObjectsModel.reset();
    GetSelectGroupbuyMaterialPriceObjectsModel.refreshAllViews();

}

//function dialogReturnValue()
//{
//	var data = new Object();
//	data.subcustomercode = iptCustomerCode.getValue();
//	data.subcustomername = iptCustomerName.getValue();
//	data.subcustomeraddr = iptCustomerAddr.getValue();
//	return data;
//}
function do_Form_InitDone(eventObject)
{
      gpxhide.hide();
      SHOP_MATERIAL_STATUS_DETAILTable.hideColumn(1);
      SHOP_MATERIAL_STATUS_DETAILTable.hideColumn(2);
      SHOP_MATERIAL_STATUS_DETAILTable.hideColumn(5);
      SHOP_MATERIAL_STATUS_DETAILTable.hideColumn(6);
      SHOP_MATERIAL_STATUS_DETAILTable.hideColumn(7);
      SHOP_MATERIAL_STATUS_DETAILTable.hideColumn(8);
      SHOP_MATERIAL_STATUS_DETAILTable.hideColumn(9);
      SHOP_MATERIAL_STATUS_DETAILTable.hideColumn(10);
      SHOP_MATERIAL_STATUS_DETAILTable.hideColumn(11);
      SHOP_MATERIAL_STATUS_DETAILTable.hideColumn(12);
      SHOP_MATERIAL_STATUS_DETAILTable.hideColumn(13);
      
      
      if(undefined != application.event.data && undefined != application.event.data.subshopid && null != application.event.data.subshopid)
      {
            shopno.setValue(application.event.data.subshopno);
            shopid.setValue(application.event.data.subshopid);
            formid.setValue(application.event.data.subformid);

            var request = GetShopMaterialStatusDetailObjectFormIdStatusModel.getMethodRequest();
            cordys.setNodeText(request ,".//*[local-name()='FORM_ID']",application.event.data.subformid);

            GetShopMaterialStatusDetailObjectFormIdStatusModel.setMethodRequest(request);
            GetShopMaterialStatusDetailObjectFormIdStatusModel.reset();
            GetShopMaterialStatusDetailObjectFormIdStatusModel.refreshAllViews();
      }

}

function do_tbbSave_Click(eventObject)
{
          var checkedRows = SHOP_MATERIAL_STATUS_DETAILTable.getCheckedRows();
          if (checkedRows.length <= 0)
         {
             application.notify ("请至少选择一条数据！");
             return;
         }
          var getnewdataXMLDocument = cordys.cloneXMLDocument(newdata.XMLDocument);
          var newNode = cordys.selectXMLNode(getnewdataXMLDocument ,".//*[local-name()='UpdateShopMaterialStatusDetail']");
          for (var i = 0; i < checkedRows.length; i++)
         {
                var getdatainfoXMLDocument = cordys.cloneXMLDocument(datainfo.XMLDocument);
                var getdatainfoNode =  cordys.selectXMLNode(getdatainfoXMLDocument ,".//*[local-name()='tuple']");
                
                cordys.setNodeText(getdatainfoNode ,".//*[local-name()='old']/*[local-name()='SHOP_MATERIAL_STATUS_DETAIL']/*[local-name()='ID']", id[checkedRows[i].index].getValue());
                cordys.setNodeText(getdatainfoNode ,".//*[local-name()='new']/*[local-name()='SHOP_MATERIAL_STATUS_DETAIL']/*[local-name()='ID']", id[checkedRows[i].index].getValue());
                cordys.setNodeText(getdatainfoNode ,".//*[local-name()='new']/*[local-name()='SHOP_MATERIAL_STATUS_DETAIL']/*[local-name()='PROCURE_DISTRIBU_STATE']", "0");
                cordys.setNodeText(getdatainfoNode ,".//*[local-name()='new']/*[local-name()='SHOP_MATERIAL_STATUS_DETAIL']/*[local-name()='PROCURE_DISTRIBU_DATE']", iptProcureDistribuDate.getValue());
                cordys.setNodeText(getdatainfoNode ,".//*[local-name()='new']/*[local-name()='SHOP_MATERIAL_STATUS_DETAIL']/*[local-name()='SALES_STATUS']", "X");
                cordys.setNodeText(getdatainfoNode ,".//*[local-name()='new']/*[local-name()='SHOP_MATERIAL_STATUS_DETAIL']/*[local-name()='SALES_DATE']", iptSalesDate.getValue());
                cordys.setNodeText(getdatainfoNode ,".//*[local-name()='new']/*[local-name()='SHOP_MATERIAL_STATUS_DETAIL']/*[local-name()='ISSHOP_RETURNGOODS']", "1");
                cordys.setNodeText(getdatainfoNode ,".//*[local-name()='new']/*[local-name()='SHOP_MATERIAL_STATUS_DETAIL']/*[local-name()='SHOP_NO']", shopno.getValue());
                cordys.appendXMLNode(getdatainfoNode ,newNode);
         }
         
         UpdateShopMaterialStatusDetailModel.setMethodRequest(getnewdataXMLDocument);
         UpdateShopMaterialStatusDetailModel.reset();
         UpdateShopMaterialStatusDetailModel.refreshAllViews();

            var request = GetShopMaterialStatusDetailObjectFormIdStatusModel.getMethodRequest();
            cordys.setNodeText(request ,".//*[local-name()='FORM_ID']",formid.getValue());

            GetShopMaterialStatusDetailObjectFormIdStatusModel.setMethodRequest(request);
            GetShopMaterialStatusDetailObjectFormIdStatusModel.reset();
            GetShopMaterialStatusDetailObjectFormIdStatusModel.refreshAllViews();
            
            if(UpdateShopMaterialStatusDetailModel.soapFaultOccurred)
            {
                return;
            }
            else
        	{
            	application.inform("保存成功");
        	}
            
}
var vtargetCdObject;
var vTargetDescObject;
var multiXMLDoc = '<xml id="dialogDef">'
    + '<Application>' 
    + '<id>multiFunctionalgrid</id>'
    + '<description>字典信息列表</description>'
    + '<caption>字典信息列表</caption>'
    + '<url>/User Interface/com/laiyifen/common/popup/多选字典弹出页.caf</url>'
    + '<frame features="dialogWidth:320px;dialogHeight:200px;resizable:yes">_modal</frame>'
    + '</Application>'
    + '</xml>';
   
var singleXMLDoc = '<xml id="selectLoan">'
    + '<Application>' 
    + '<id>selectLoan</id>'
    + '<description>借款单选择</description>'
    + '<caption>借款单选择</caption>'
    + '<url>/User Interface/com/laiyifen/bpc/loan/借款单选择表单.caf</url>'
    + '<frame features="dialogWidth:300px;dialogHeight:180px;resizable:yes">_modal</frame>'
    + '</Application>'
    + '</xml>';

var isMultiData = 0;
function getDialogValueLoan(eventObject,targetCdObject,targetDescObject,inputParam,caption,width,height,dataType)
{   
    if(targetCdObject != "" && targetCdObject != null)
    {
    	vTargetCdObject = targetCdObject;
    }
    vTargetDescObject = targetDescObject;
    var xmlObject;
    //dataType: 1调用单选字典弹出页；*调用多选字典弹出页
    if(dataType == "1")
    {
    	isMultiData = 0;
    	xmlObject = cordys.loadXMLDocument(singleXMLDoc);
    }else{
    	isMultiData = 1;
    	xmlObject = cordys.loadXMLDocument(multiXMLDoc);
    }
    if(width != null && width != "" && height != null && height != "")
    {
    	var feature = "dialogWidth:" + width + ";dialogHeight:" + height + ";resizable:yes";
    	cordys.setXMLAttribute(cordys.selectXMLNode(xmlObject,".//*[local-name()='frame']"),"","features",feature); 
    }
    var appDef = cordys.selectXMLNode(xmlObject,".//*[local-name()='Application']");
    cordys.setNodeText(appDef,".//*[local-name()='description']",caption);
    cordys.setNodeText(appDef,".//*[local-name()='caption']",caption);
    application.showDialog(appDef, inputParam, null, GetReturnValueLoan, false);
   // placePopupLoan(eventObject);
}

function GetReturnValueLoan(returnValue)
{
    if(returnValue!="")
    {
        if(isMultiData == 0)
        {
        	vTargetCdObject.setValue(returnValue[0]);
        	vTargetDescObject.setValue(returnValue[1]);
        }
        else
        {
        	vTargetDescObject.setValue(returnValue);
        }	
    }
}

function placePopupLoan(eventObject)
{
        var srcElement =  eventObject.srcElement;
        var currApplication =  eventObject.srcElement.ownerDocument.defaultView.application;
        var feedbackWrapper = currApplication.__ufo.feedbackWrapper;
        var windowWidth = currApplication.ownerDocument.body.clientWidth;
        var windowHeight = currApplication.ownerDocument.body.clientHeight;
        var feedbackWrapperWidth = feedbackWrapper.offsetWidth;
        var feedbackWrapperHeight = feedbackWrapper.offsetHeight;

        feedbackWrapper.style.display = "block";
        feedbackWrapper.style.position = "absolute";
        feedbackWrapper.style.left = "0%";
        feedbackWrapper.style.top = "0%";
        feedbackWrapper.style.margin = "0px";
        
        var offsets = myutil.getAbsoluteOffset(srcElement, srcElement.ownerDocument.body);

        var effectiveLeftOffset = Math.abs(offsets.offsetLeft);
        var effectiveTopOffset = Math.abs(offsets.offsetTop + srcElement.offsetHeight);
         
        if( (offsets.offsetTop + srcElement.offsetHeight + feedbackWrapperHeight) > windowHeight ) 
        {
            feedbackWrapper.style.top = (offsets.offsetTop - feedbackWrapperHeight) + "px" ;
        }  
        else
        {
             feedbackWrapper.style.top = effectiveTopOffset + "px" ;
        }
        if( (offsets.offsetLeft + srcElement.offsetWidth + feedbackWrapperWidth) > windowWidth )
        {
            feedbackWrapper.style.left = (offsets.offsetLeft - feedbackWrapperWidth)+ srcElement.offsetWidth + "px" ;
        }
        else
        {
            feedbackWrapper.style.left = effectiveLeftOffset + "px" ;

        }
}
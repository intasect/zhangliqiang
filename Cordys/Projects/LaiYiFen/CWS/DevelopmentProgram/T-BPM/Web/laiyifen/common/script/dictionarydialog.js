var vTargetCdObject=null;
var vTargetDescObject=null;
var applicationDefs = '<xml id="dialogDef">'
		+ '<Applications>'
    + '<Application id="multiXMLDoc">'
    + '<id>multidictionaygrid</id>'
    + '<description>字典信息列表</description>'
    + '<caption>字典信息列表</caption>'
    + '<url>/User Interface/com/laiyifen/common/popup/多选字典弹出页.caf</url>'
    + '<frame features="dialogWidth:320px;dialogHeight:200px;resizable:yes">_modal</frame>'
    + '</Application>'
    + '<Application id="singleXMLDoc">'
    + '<id>singledictionarygrid</id>'
    + '<description>字典信息列表</description>'
    + '<caption>字典信息列表</caption>'
    + '<url>/User Interface/com/laiyifen/common/popup/单选字典弹出页.caf</url>'
    + '<frame features="dialogWidth:300px;dialogHeight:180px;resizable:yes">_modal</frame>'
    + '</Application>'
    + '<Application id="companyXMLDoc">'
    + '<id>subcompanygrid</id>'
    + '<description>子公司信息列表</description>'
    + '<caption>子公司信息列表</caption>'
    + '<url>/User Interface/com/laiyifen/common/popup/子公司信息弹出页.caf</url>'
    + '<frame features="dialogWidth:360px;dialogHeight:350px;resizable:yes">_modal</frame>'
    + '</Application>'
    + '</Applications>'
    + '</xml>';
var xmlDoc = null;
var isMultiData = 0;
/********************************************************************
 * 功能名称	: getDialogValue();
 * 描述: 打开单选字典弹出页，并返回值
 * 输入参数：eventObject--弹出页图片对象
 * 					 targetCdObject--需要返回code值的目标对象
 *					 targetDescObject--需要返回description值的目标对象
 *					 inputParam--弹出页所需的字典类型
 *					 caption--弹出页标题
 *					 width--弹出页宽度
 *					 height--弹出页高度
 *					 dataType--返回值类型，1为返回单条，*为返回多条
 * 输出参数：无
 */
function getDialogValue(eventObject,targetCdObject,targetDescObject,inputParam,caption,width,height,dataType)
{
    if(targetCdObject != "" && targetCdObject != null)
    {
    	vTargetCdObject = targetCdObject;
    }
    vTargetDescObject = targetDescObject;

   if(xmlDoc == null)
   {
      xmlDoc = cordys.loadXMLDocument(applicationDefs);
   }

    //dataType: 1调用单选字典弹出页；*调用多选字典弹出页
    var appDef;
	if(dataType == "1")// refer the comment
    {
		isMultiData = 0;
		appDef = cordys.selectXMLNode(xmlDoc,".//*[local-name()='Application'][@id='singleXMLDoc']");
	}
	else
	{
		isMultiData = 1;
		appDef = cordys.selectXMLNode(xmlDoc,".//*[local-name()='Application'][@id='multiXMLDoc']");
	}
    if(width != null && width != "" && height != null && height != "")
    {
    	var feature = "dialogWidth:" + width + ";dialogHeight:" + height + ";resizable:yes";
    	cordys.setXMLAttribute(cordys.selectXMLNode(appDef,".//*[local-name()='frame']"),"","features",feature); 
    }
    cordys.setNodeText(appDef,".//*[local-name()='description']",caption);
    cordys.setNodeText(appDef,".//*[local-name()='caption']",caption);
    application.showDialog(appDef, inputParam, null, GetReturnValue, false);
    placePopup(eventObject);
}

/********************************************************************
 * 功能名称	: getSubCompany();
 * 描述: 打开子公司信息弹出页，并返回值
 * 输入参数：eventObject--弹出页图片对象
 * 					 targetCdObject--需要返回code值的目标对象
 *					 targetDescObject--需要返回description值的目标对象
 *					 inputParam--弹出页所需的输入参数
 *					 width--弹出页宽度
 *					 height--弹出页高度
 * 输出参数：无
 */
function getSubCompany(eventObject,targetCdObject,targetDescObject,inputParam,width,height)
{   
    if(targetCdObject != "" && targetCdObject != null)
    {
    	vTargetCdObject = targetCdObject;
    }
    vTargetDescObject = targetDescObject;
if(xmlDoc == null)
   {
      xmlDoc = cordys.loadXMLDocument(applicationDefs);
   }
	var appNode = cordys.selectXMLNode(xmlDoc,".//*[local-name()='Application'][@id='companyXMLDoc']");
    if(width != null && width != "" && height != null && height != "")
    {
    	var feature = "dialogWidth:" + width + ";dialogHeight:" + height + ";resizable:yes";
    	cordys.setXMLAttribute(cordys.selectXMLNode(appNode,".//*[local-name()='frame']"),"","features",feature); 
    }
    if(inputParam == "" || inputParam == null)
    {
    	application.showDialog(appNode, null, null, GetReturnValue, false);
    	placePopup(eventObject);
    }
    else
    {
    	application.showDialog(appNode, inputParam, null, GetReturnValue, false);
    	placePopup(eventObject);
    }	
}

/********************************************************************
 * 功能名称	: GetReturnValue();
 * 描述: 弹出页关闭后的回调函数
 * 输入参数：returnValue--弹出页中返回对象
 * 输出参数：无
 */
function GetReturnValue(returnValue)
{
    if(returnValue!="")
    {
        if(isMultiData == 0)
        {
        	if(vTargetCdObject)
                {
                     vTargetCdObject.setValue(returnValue[0]);
                }
        	vTargetDescObject.setValue(returnValue[1]);
        }
        else
        {
        	vTargetDescObject.setValue(returnValue);
        }	
    }
}

function placePopup(eventObject)
{
        var srcElement =  eventObject.srcElement;
        var currApplication =  eventObject.srcElement.ownerDocument.defaultView.application;
		// using internal peoperties in objects are not adivisable it can be changed even with a defect fix ex: currApplication.__ufo. use 'Id 'based DOM navigation to get the proper container. ex: cordys.getElementById(SourceObject,"targetId")..*/
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
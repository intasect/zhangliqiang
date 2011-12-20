function validateCommon(currentDocument){
	return true;
}

//控件必填项校验
function validateControl(objects)
{
	for(var name in objects)
	{
		if(objects[name].getValue()=="")
		{
			application.notify(objects[name].parentNode.previousSibling.innerHTML + "是必填项，请您填写，谢谢");
			objects[name].setFocus();
			return false;
		}
	}
	return true;
}

//Parameters: confirmMsg--提示信息内容；isCancel--是否需要显示cancel按钮，true显示、false不显示；callbackHandler回调函数,..
function showConfirm(isCancel,callbackHandler,confirmMsg)
{
	if(confirmMsg==null || confirmMsg=="")
	{
		confirmMsg="确认要进行保存操作吗?";
	}
	application.confirm(confirmMsg, isCancel, callbackHandler, false, "确认信息");
}

/**
 * 获取当前用户DN
 * @return userDN
 */
function getCurrentUserDN() 
{ 
	var userInfo = system.getUser(); 
	var userDN;
	if (userInfo.organizations[application.organization]) 
	{ 
		//Identify if this is default
		if (userInfo.organizations[application.organization].isDefault)
		{ 
			//Show the DN of the user 
			userDN= userInfo.organizations[application.organization].userDN;
		} 
	} 
	return userDN;
}

/**
 * 查看流程监控图
 */
function viewBPMMonitor(){
	var instanceId=Workflow.getProcessInstanceId();
	var organization=Workflow.getUserOrganization();
	var samlArt = CordysRoot.sso.getArtifact();
	var sourceName=Workflow.getSourceName();
	param={
		instanceId:instanceId,
		organization:organization,
		samlArt:samlArt,
		sourceName:sourceName
	}
	application.select(viewMonitor.XMLDocument.documentElement,param);
}

/**
 * 常量设置
 * @return 返回常量Object.
 */
function getStaticValue(){
	var duration="P0Y0M1DT0H0M0S";
	var staticObject = new Object();
	staticObject.defaultDuration=duration;
	staticObject.chargeRole="cn=BR子公司网点开发部网点开发主管,cn=organizational roles,o=laiyifen,cn=cordys,cn=defaultInst,o=laiyifen";
	staticObject.mainCompCode="1000";
	staticObject.downloadUrl="http://10.1.0.216";
	return staticObject;
}

/********************************************************************
 * 功能名称	: restructureApprovalHistory();
 * 描述: 重构审批意见显示样式
 * 输入参数：eventObject
 * 输出参数：无
 */
function restructureApprovalHistory(eventObject)
{
	var eventSrc = eventObject.srcElement;
	// read data values  using proper xpath form the business object
	var businessObject = eventObject.businessObject;
	var approvalList  = cordys.getNodeText(businessObject,".//*[local-name()='REMARK']");
	var det1  = cordys.getNodeText(businessObject,".//*[local-name()='STEP_NAME']");
	var det2  = cordys.getNodeText(businessObject,".//*[local-name()='OPERATOR_NAME']");
	var det3  = cordys.getNodeText(businessObject,".//*[local-name()='OPERATOR_ROLENAME']");
	var date  = cordys.getNodeText(businessObject,".//*[local-name()='OPERATING_DATE']");
	var timeArray = date.split("T");
	var det4 = timeArray[0]+" "+timeArray[1].split(".")[0];
	
	//为审批意见保存格式	
	var approval="";
	if(approvalList == null)
	{
		approval = "提交人提交表单";
	}
	else
	{	
		var txtlines = approvalList.split("\n");
		approval = "<div class='comment'>";
		for(var i=0;i<txtlines.length;i++)
		{
			approval = approval+"<p>"+txtlines[i]+"</p>";
		}
		approval = approval+"</div>";
	}
	if(!eventSrc.modifiedStruct)
	{
		var holder = document.createElement("div");
		holder.className = 'holder';
		var tweakedHTML =  "<div class='conentname' id='approval'>"+approval+"</div>"+
		"<div class='details'>"+
		"<div class='lbl1' id='label1'>环节名称</div><div class='val1' id='det1'>"+det1+"</div>"+
		"<div class='lbl2' id='label2'>审批人</div><div class='val2' id='det2'>"+det2+"</div>"+
		"<div class='lbl3' id='label3'>岗位名称</div><div class='val3' id='det3'>"+det3+"</div>"+
		"<div class='lbl4' id='label4'>审批时间</div><div class='val4' id='det4'>"+det4+"</div>"+
		"</div>";
		holder.innerHTML = tweakedHTML;    
		eventSrc.parentNode.parentNode.appendChild(holder);
		eventSrc.parentNode.style.display = "none";
		eventSrc.modifiedStruct= true;
	}
	else
	{
		var nodeElement = eventSrc.parentNode.parentNode;
		cordys.getElementById(nodeElement,"approval").innerHTML = approval;
		cordys.getElementById(nodeElement,"label1").innerHTML = "环节名称";
		cordys.getElementById(nodeElement,"det1").innerHTML = det1;
		cordys.getElementById(nodeElement,"label2").innerHTML = "审批人";
		cordys.getElementById(nodeElement,"det2").innerHTML = det2;
		cordys.getElementById(nodeElement,"label3").innerHTML = "岗位名称"; 
		cordys.getElementById(nodeElement,"det3").innerHTML = det3;
		cordys.getElementById(nodeElement,"label4").innerHTML = "审批时间";
		cordys.getElementById(nodeElement,"det4").innerHTML = det4;
	}
} 

/********************************************************************
 * 为空model创建busineObject
 */
function createBusinessObjOnModel(model, groupBox,path)
{
	var modelDataNode = cordys.selectXMLNode(model.getData(),".");
	if(!cordys.selectXMLNode(modelDataNode,".//*[local-name()='"+path+"']"))
	{
	if(groupBox.create)	groupBox.create();
	}
}

/********************************************************************
 * 将数据绑定到model中
 */
function pushdataToModel(model,data) 
{ 
	model.clear(); 
	model.putData(data); 
	model.refreshAllViews(); 
}

/******************************** Composite Object *************************************/
/********************************************************************
 * 用复合对象查询后将单条记录拆分到相应model中
 */
function bindSingleTupleModel(model,responseNode,busiObj,dataNode)
{
	cordys.appendXMLNode(cordys.selectXMLNode(responseNode,".//*[local-name()='" + busiObj + "']"),cordys.selectXMLNode(dataNode,".//*[local-name()='old']"));
	pushdataToModel(model,dataNode);
}

/********************************************************************
 * 用复合对象查询后将多条记录拆分到相应model中
 */
function bindMultiTupleModel(model,responseNode,busiObj,dataDoc)
{
	var nodeDoc = cordys.cloneXMLDocument(dataDoc);
	var nodeList = cordys.selectXMLNodes(responseNode,".//*[local-name()='" + busiObj + "']");
	for( var i=0, length=nodeList.length; i < length; i++)
	{
		var singleNode = cordys.selectXMLNode(nodeList[i],  ".");
		cordys.appendXMLNode(singleNode,cordys.selectXMLNode(nodeDoc,".//*[local-name()='tuple'][last()]/*[local-name()='old']"));
		if(i<length-1){
			var dataNode = cordys.cloneXMLDocument(dataDoc);
			cordys.appendXMLNode(cordys.selectXMLNode(dataNode,".//*[local-name()='tuple']"),cordys.selectXMLNode(nodeDoc,".//*[local-name()='GetResponse']"));
		}
	}
	pushdataToModel(model,nodeDoc);
}

/********************************************************************
 * 点击保存，用复合对象进行数据保存操作
 */
function saveCompositeObject(compositeModel,modelCol_Single,modelCol_Multi)
{														 
	var updateRequestDocument = cordys.cloneXMLDocument(updateXML.XMLDocument);		//复合对象的xml document
	updateDocTemplate = cordys.cloneXMLDocument(tupleUpdateXML.XMLDocument);		//存储所有更新节点的document
	deleteDocTemplate = cordys.cloneXMLDocument(tupleOldXML.XMLDocument);		//存储所有删除节点的document
	insertDocTemplate = cordys.cloneXMLDocument(tupleNewXML.XMLDocument);		//存储所有新增节点的document
	//对只有一个tuple的model进行复合对象请求准备
	for (var modelName in modelCol_Single)
	{
		var model = modelCol_Single[modelName];
		var modelData = cordys.cloneXMLDocument(model.getData());
		var tupleNode = cordys.selectXMLNode(modelData, ".//*[local-name()='tuple']");
		var bUpdateable = isModelUpdatable(tupleNode);
		if(!bUpdateable)  continue;
		var modeObject = addRequestObjectForModel(model,tupleNode,modelName,compositeModel);
	}
	// 对含有多个tuple的model进行复合对象请求准备
	if(modelCol_Multi)
	{
		for(var modelName in modelCol_Multi)
		{
			var model = modelCol_Multi[modelName];
			getUpdateForMultiTuple(model,modelName,compositeModel);
		}
	}
	
	var updateNode = cordys.selectXMLNode(updateRequestDocument, ".//*[local-name()='Update']");
	var bFireRequest = false;
	if(isValidToAdd(updateDocTemplate,compositeModel))  
	{ 
		bFireRequest=true;     
		cordys.appendXMLNode(cordys.selectXMLNode(updateDocTemplate, ".//*[local-name()='tuple']"),updateNode);
	}
	if(isValidToAdd(insertDocTemplate,compositeModel))
	{
		bFireRequest=true;
		cordys.appendXMLNode(cordys.selectXMLNode(insertDocTemplate, ".//*[local-name()='tuple']"),updateNode);
	}
	if(isValidToAdd(deleteDocTemplate,compositeModel))
	{
		bFireRequest=true;
		cordys.appendXMLNode(cordys.selectXMLNode(deleteDocTemplate, ".//*[local-name()='tuple']"),updateNode);
	}	
	
	if(bFireRequest)
	{
		compositeModel.setMethodRequest(updateRequestDocument);
		compositeModel.reset();
	}else{
		application.inform("保存成功");
	}
}

/********************************************************************
 * 判断model是否有数据变化
 */
function isModelUpdatable(tupleNode)
{
	var hasModified = WebForm.getAttribute(tupleNode, "sync_id", false) >0?true:false;
	return hasModified;
}

/********************************************************************
 * 将model中的数据根据新增，修改和删除分别存放到不同的document中
 */
function addRequestObjectForModel(model,tupleNode,modelName,compositeModel)
{
	var oldNode = cordys.selectXMLNode(tupleNode,".//*[local-name()='old']");
	var newNode = cordys.selectXMLNode(tupleNode,".//*[local-name()='new']");
	var modelDataDoc = tupleNode.ownerDocument;
	var modelBObjName = model.instance.businessObjectName;
	var compositeBObjNm = compositeModel.instance.businessObjectName;
	if(oldNode)// it can be delete or update
	{
		if(newNode)// update
		{
			var childOldNode = cordys.selectXMLNode(oldNode,".//*[local-name()='" + modelBObjName+ "']");
			var updateOldNode = cordys.selectXMLNode(updateDocTemplate,".//*[local-name()='old']/*[local-name()='" + compositeBObjNm + "']");
			if(childOldNode!=null)
			{
				var childOldNodeCopy = childOldNode.cloneNode(true);
				cordys.appendXMLNode(childOldNodeCopy,updateOldNode);
				var childNewNode = cordys.selectXMLNode(newNode,".//*[local-name()='" + modelBObjName+ "']");
				var updateNewNode = cordys.selectXMLNode(updateDocTemplate,".//*[local-name()='new']/*[local-name()='" + compositeBObjNm + "']");
				var childNewNodeCopy = childNewNode.cloneNode(true);
				cordys.appendXMLNode(childNewNodeCopy,updateNewNode);
			}
		}
		else //delete
		{
			var childOldNode = cordys.selectXMLNode(oldNode,".//*[local-name()='" + modelBObjName + "']");
			if(childOldNode!=null)
			{
				var updateOldNode = cordys.selectXMLNode(deleteDocTemplate,".//*[local-name()='" + compositeBObjNm + "']");
				var childOldNodeCopy = childOldNode.cloneNode(true);
				cordys.appendXMLNode(childOldNodeCopy,updateOldNode);
			}
		}
	}
	else //insert
	{
		if(newNode)
		{
			var childNewNode = cordys.selectXMLNode(newNode,".//*[local-name()='" + modelBObjName+ "']");
			var insertParentNewNode = cordys.selectXMLNode(insertDocTemplate,".//*[local-name()='" + compositeBObjNm + "']");
			try 
			{  
				if(typeof(eval(fillBusiValueOnModels))=="function")  
				{
					fillBusiValueOnModels(modelName,modelDataDoc,modelBObjName,tupleNode);
				}
				}catch(e)
				{
				}
				var childNewNodeCopy = childNewNode.cloneNode(true);
				cordys.appendXMLNode(childNewNodeCopy,insertParentNewNode);
		}
	}
}

/********************************************************************
 * 为含有多个tuple的model准备复合对象请求
 */
function getUpdateForMultiTuple(model,modelName,compositeModel)
{
	var tupleNodes = cordys.selectXMLNodes(model.getData(), ".//*[local-name()='tuple']");
	for(var i = 0;i < tupleNodes.length; i++)
	{
		var tupleNode = cordys.selectXMLNode(tupleNodes[i],".");
		var bUpdateable = isModelUpdatable(tupleNode);
		if(!bUpdateable) continue;
		addRequestObjectForModel(model,tupleNode,modelName,compositeModel);
	}
}

/********************************************************************
 * 判断准备好的document是否有数据存在
 */
function isValidToAdd(template,compositeModel)
{
	var isValid = false;
	var compositeShopTargetInfoNode = cordys.selectXMLNode(template, ".//*[local-name()='"+ compositeModel.instance.businessObjectName + "']");
	if(compositeShopTargetInfoNode && compositeShopTargetInfoNode.childNodes.length >0) isValid = true;
	return isValid;
}

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
	if((offsets.offsetTop + srcElement.offsetHeight + feedbackWrapperHeight) > windowHeight) 
	{
		feedbackWrapper.style.top = (offsets.offsetTop - feedbackWrapperHeight) + "px" ;
	}
	else
	{
		feedbackWrapper.style.top = effectiveTopOffset + "px" ;
	}
	if((offsets.offsetLeft + srcElement.offsetWidth + feedbackWrapperWidth) > windowWidth)
	{
		feedbackWrapper.style.left = (offsets.offsetLeft - feedbackWrapperWidth)+ srcElement.offsetWidth + "px" ;
	}
	else
	{
		feedbackWrapper.style.left = effectiveLeftOffset + "px" ;
	}
}

/************************************************ User Info Cache ***************************************************/
var userArray  =new Array();
function addOrGetCache(model)
{
	//First time, execute the webservice and return the result.
	if(system.data.USERINFO == null)
	{
		return executeRequest(model);
	}
	else
	{
		var userArray = system.data.USERINFO;
		return userArray;
	}
}

function executeRequest(model)
{    
	//Execute the webservice GetUserInfo.      
	var request = model.getMethodRequest();
	model.setMethodRequest(request);
	model.reset();
	compositeData = model.getData();
	
	var tuplesNode = cordys.selectXMLNode(compositeData,".//*[local-name()='User']");
	
	var optionObject = new Object();
	var vUserDN = cordys.selectXMLNode(tuplesNode,".//*[local-name()='userDN']");
	var vUserCode = cordys.selectXMLNode(tuplesNode,".//*[local-name()='userCode']");
	var vUserNm = cordys.selectXMLNode(tuplesNode,".//*[local-name()='userName']");
	var vDeptNm = cordys.selectXMLNode(tuplesNode,".//*[local-name()='deptName']");
	var vDeptCd = cordys.selectXMLNode(tuplesNode,".//*[local-name()='deptCode']");
	var vCompanyCd = cordys.selectXMLNode(tuplesNode,".//*[local-name()='companyCode']");
	var vCompanyNm = cordys.selectXMLNode(tuplesNode,".//*[local-name()='companyName']");
	var vRoleNm = cordys.selectXMLNode(tuplesNode,".//*[local-name()='roleNameList']");
	if(vUserDN)
		optionObject.userDN=  cordys.getTextContent(vUserDN);
	if(vUserCode)
		optionObject.userCode=  cordys.getTextContent(vUserCode);
	if(vUserNm)
		optionObject.userName=cordys.getTextContent(vUserNm);
	if(vDeptNm)
		optionObject.deptName=cordys.getTextContent(vDeptNm);
	if(vDeptCd)
		optionObject.deptCode=cordys.getTextContent(vDeptCd);
	if(vCompanyCd)
		optionObject.companyCode=cordys.getTextContent(vCompanyCd);
	if(vCompanyNm)
		optionObject.companyName=cordys.getTextContent(vCompanyNm);
	if(vRoleNm)
		optionObject.roleName=cordys.getTextContent(vRoleNm);
		
	userArray=optionObject;
	system.data.USERINFO = userArray;
	return userArray;
}

function clear()
{
	userArray = new Array();
	system.data.USERINFO = null;
}

function getUserInfo()
{
	if(system.data.USERINFO)
	{
		userArray =system.data.USERINFO;
	}
	else
	{
		userArray=addOrGetCache(UserModel);
	}
	return userArray;
}

function resetGroupBoxHeight(gtoupBox)
{
	//calculate height of all the vertical layout childs and reset groupbox height.need to be called on expand of parent &expand-collapse of childgroupboxes
	var newHeight = 0;
	var childNodes = gtoupBox.childNodes;
	var container;
	for(var i=0;i<childNodes.length;i++)
	{
		if(childNodes[i].className.indexOf("simplecontainer")!=-1) 
		{
			container = childNodes[i];
			break;
		}
	}
	var childGroupBoxs =  container.firstChild.childNodes;
	for(var i=0;i<childGroupBoxs.length;i++)
	{
		var node = childGroupBoxs[i];
		if( node.className.indexOf("v_layout")!=-1 && (node.id &&( node.id.indexOf("xfe")!=-1)))
		{
			newHeight += node.offsetHeight+30;
		}
	}
	gtoupBox.parentNode.parentNode.parentNode.style.height = newHeight+"px";
}

function StatusIndicator(htmlElement,warm, hot)
{
	var warm  = warm ? warm:70;
	var hot = hot ? hot:90;
	this.coolFactor = warm ;
	this.warmFactor = hot;
	var ownrDoc =  htmlElement.ownerDocument;
	var indicator = ownrDoc.createElement("div");
	var coolWidth = warm+"%";
	var wrmWidth = parseInt(hot-warm)+"%";
	var hotWidth = parseInt(100-hot)+"%";
	indicator.id="indicator";
	indicator.innerHTML=	"<div id='battery'>"+
									"<div id='cool' style='width:"+coolWidth+";'></div>"+
									"<div id='warm' style='width:"+wrmWidth+";left:"+coolWidth+"'></div>"+
									"<div id='hot' style='width:"+hotWidth+";right:0px'></div>"+
								"</div>"+
								"<div id='mask'></div>";
	this.indicatorContainer = indicator;
	this.__mask = indicator.lastChild;
	this._hotDiv = indicator.firstChild.lastChild;
	htmlElement.appendChild(indicator);
	var clock = ownrDoc.createElement("div");
	clock.id="clock";
	clock.innerHTML = "<img src='/cordys/laiyifen/common/image/clock_red.png'></img>";
	htmlElement.insertBefore(clock,indicator)
}

StatusIndicator.prototype.refresh = function(strtDate,endDate,currentDate)
{
	var startTime = getTimeMs(strtDate);
	var currentTime = getTimeMs(currentDate);
	var dueTime = getTimeMs(endDate);
	var duration = getDifferencefactor(dueTime,startTime);
	var remainng = getDifferencefactor(dueTime,currentTime);
	var elapsed = getDifferencefactor(currentTime,startTime);
	var remaningFactor;
	// finding with local time diff
	var difFctor = 8*60*60*1000;
	var locl = dueTime+difFctor;
	var loclDate = new Date(locl);
	var tipDate = loclDate.getFullYear()+"-"+formatVal(loclDate.getMonth())+"-"+formatVal(loclDate.getDate())+" "+formatVal(loclDate.getHours())+":"+formatVal(loclDate.getMinutes())+":"+formatVal(loclDate.getSeconds());
	this.indicatorContainer.title ="应完成时间:"+tipDate;
	if(duration>0 && remainng>0)	remaningFactor = (remainng*100)/duration;
	if(remaningFactor<100)
	{
		this._hotDiv.style.left = this.warmFactor+"%";
		this._hotDiv.style.width = "auto";
		this._hotDiv.style.right ="0px";
		this.__mask.style.width = remaningFactor+"%";
		
	}
	else if(remainng<=0)
	{
	// time expired show everything as red.
		this._hotDiv.style.width = "100%";
		this._hotDiv.style.left = "0px";
		this.__mask.style.width = "0px";
	}
}
function getTimeMs(dateString)
{
	//"2011-10-18T09:30:59.0" Date constructor
	var dateObj;
	var ar = dateString.split("T");
	var arD = ar[0].split("-");
	var arT = ar[1].split(":");
	var arS = arT[2].split(".");
	//yyyy,mm,dd,hh,mm,ss,ms
	dateObj = new Date(arD[0],arD[1],arD[2],arT[0],arT[1],arS[0],arS[1]);
	return dateObj.getTime();
}

function getDifferencefactor(date1_ms,date2_ms)
{
	var difference_ms = date1_ms - date2_ms;
	return difference_ms;
}
function printProgress(taskxml)
{
	var tmpdoc=null;
	tmpdoc=cordys.loadXMLDocument(taskxml);
	var startDate=cordys.getNodeText(tmpdoc,".//*[local-name()='Task']/*[local-name()='StartDate']");
	var due_date=cordys.getNodeText(tmpdoc,".//*[local-name()='Task']/*[local-name()='DueDate']");
	if(cordys.getNodeText(tmpdoc,".//*[local-name()='Task']/*[local-name()='CompletionDate']")!=null) return;
	if(due_date!=null)
	{
		var indicator = new StatusIndicator(toolbar);//default points are70,90
		toolbar.ownerDocument.defaultView["Indicator1"]= indicator ;
		indicator.refresh(startDate,due_date,getCurrentUTCtime());
	}
}

function formatVal(value)
{
	var formattedVal =  value.toString().length==2?value:"0"+value;
	return formattedVal; 
}

function getCurrentUTCtime()
{
	var date  = new Date();
	var dateString =  date.getUTCFullYear()+"-"+formatVal(date.getUTCMonth()+1)+"-"+formatVal(date.getUTCDate())+"T"+formatVal(date.getUTCHours())+":"+formatVal(date.getUTCMinutes())+":"+date.getUTCSeconds()+"."+date.getUTCMilliseconds();
	return dateString;

}
function getCurrentTime()
{
	var date  = new Date();
	var dateString =  date.getFullYear()+"-"+formatVal(date.getMonth()+1)+"-"+formatVal(date.getDate())+"T"+formatVal(date.getHours())+":"+formatVal(date.getMinutes())+":"+date.getSeconds()+"."+date.getMilliseconds();
	return dateString;
}

function getApplyTime()
{
   var date = new Date();
   var dateString=date.getFullYear()+"-"+formatVal(date.getMonth()+1)+"-"+formatVal(date.getDate());
   return dateString;
}

function getLastSixTime()
{
   var date = new Date(); 
   var lastSixTimeMilliseconds=date.getTime()-1000*60*60*24*6;         
       date.setTime(lastSixTimeMilliseconds);  
   var dateString=date.getFullYear()+"-"+formatVal(date.getMonth()+1)+"-"+formatVal(date.getDate());
   return dateString;
}
/*
***************************************************************
*****************************弹出页*************************
***************************************************************
*/
var vtargetCdObject;
var vTargetDescObject;

var singleXMLDoc = '<xml id="selectDialog">'
    + '<Application>' 
    + '<id>selectDialog</id>'
    + '<description>选择</description>'
    + '<caption>选择</caption>'
    + '<url>/User Interface/com/laiyifen/bpc/common/选择预算科目.caf</url>'
    + '<frame features="dialogWidth:300px;dialogHeight:180px;resizable:yes">_modal</frame>'
    + '</Application>'
    + '</xml>';

var isMultiData = 0;
function getURLDialogValue(eventObject,targetCdObject,targetDescObject,inputParam,url,caption,width,height,dataType)
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
    }else if(dataType == "suppliers") {
       isMultiData = 9;
	   xmlObject = cordys.loadXMLDocument(singleXMLDoc);
    }else{
    	isMultiData = 1;
    	xmlObject = cordys.loadXMLDocument(multiXMLDoc);
    }
	if(url!=null && url!=""){
		cordys.setTextContent(cordys.selectXMLNode(xmlObject,".//*[local-name()='url']"),"/User Interface/com/laiyifen/" + url); 
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

    if(returnValue!=null && returnValue!="")
    {
        if(isMultiData == 0)
        {
        	vTargetCdObject.setValue(returnValue[0]);
        	vTargetDescObject.setValue(returnValue[1]);
        }
        else if (isMultiData  == 9){
        collection_unit.setValue(returnValue[1]+"("+returnValue[0]+")");
        bank.setValue(returnValue[2]);
		bank_account.setValue(returnValue[3]);
		phone.setValue(returnValue[5]);
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

/*
***************************************************************
*****************************季度判断 *************************
***************************************************************
*/
function changeMonthToQuarter( month){ 
var ret = ""; 
switch (month*1){ 
case 1: case 2: case 3: ret = "Q1";break; 
case 4: case 5: case 6: ret = "Q2";break; 
case 7: case 8: case 9: ret = "Q3";break; 
case 10: case 11: case 12: ret = "Q4";break; 
} 
return ret; 
}


/*
***************************************************************
*****************************中文大小写*************************
***************************************************************
*/
function Arabia2Chinese(_val){
var money1 = new Number(_val);
if(money1> 1000000000000000000) {
alert("您输入的数字太大，重新输入！");
return;
}
var monee = Math.round(money1*100).toString(10)
var i,j;
j=0;
var leng = monee.length;
var monval="";
for( i=0;i<leng;i++)
{
monval= monval+to_upper(monee.charAt(i))+to_mon(leng-i-1);
}
return repace_acc(monval);
}
function to_upper( a)
{
switch(a){
case '0' : return '零'; break;
case '1' : return '壹'; break;
case '2' : return '贰'; break;
case '3' : return '叁'; break;
case '4' : return '肆'; break;
case '5' : return '伍'; break;
case '6' : return '陆'; break;
case '7' : return '柒'; break;
case '8' : return '捌'; break;
case '9' : return '玖'; break;
default: return '' ;
}
}
function to_mon(a){
if(a>10){ a=a - 8;
return(to_mon(a));}
switch(a){
case 0 : return '分'; break;
case 1 : return '角'; break;
case 2 : return '元'; break;
case 3 : return '拾'; break;
case 4 : return '佰'; break;
case 5 : return '仟'; break;
case 6 : return '万'; break;
case 7 : return '拾'; break;
case 8 : return '佰'; break;
case 9 : return '仟'; break;
case 10 : return '亿'; break;
}
}
function repace_acc(Money){
Money=Money.replace("零分","");
Money=Money.replace("零角","零");
var yy;
var outmoney;
outmoney=Money;
yy=0;
while(true){
var lett= outmoney.length;
outmoney= outmoney.replace("零元","元");
outmoney= outmoney.replace("零万","万");
outmoney= outmoney.replace("零亿","亿");
outmoney= outmoney.replace("零仟","零");
outmoney= outmoney.replace("零佰","零");
outmoney= outmoney.replace("零零","零");
outmoney= outmoney.replace("零拾","零");
outmoney= outmoney.replace("亿万","亿零");
outmoney= outmoney.replace("万仟","万零");
outmoney= outmoney.replace("仟佰","仟零");
yy= outmoney.length;
if(yy==lett) break;
}
yy = outmoney.length;
if ( outmoney.charAt(yy-1)=='零'){
outmoney=outmoney.substring(0,yy-1);
}
yy = outmoney.length;
if ( outmoney.charAt(yy-1)=='元'){
outmoney=outmoney +'整';
}
return outmoney;
}
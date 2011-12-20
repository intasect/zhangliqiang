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
				//cordys.setNodeText(request,".//*[local-name()='USERINFO_TYPE']","TS_Resources");
				model.setMethodRequest(request);
        model.reset();
        compositeData = model.getData();
        
        var tuplesNodes = cordys.selectXMLNodes(compositeData,".//*[local-name()='User']");
  
        for(var i =0; i < tuplesNodes.length; i++)
        {
             //userArray[i] = tuplesNode[i];
			 var optionObject = new Object();
if(cordys.selectXMLNode(tuplesNodes[i],".//*[local-name()='userDN']"))
		       optionObject.userCode=  cordys.getNodeText(tuplesNodes[i],".//*[local-name()='userDN']","",null);
if(cordys.selectXMLNode(tuplesNodes[i],".//*[local-name()='userName']"))
		       optionObject.userName=cordys.getNodeText(tuplesNodes[i],".//*[local-name()='userName']","",null);
if(cordys.selectXMLNode(tuplesNodes[i],".//*[local-name()='teamName']"))
		       optionObject.deptName=cordys.getNodeText(tuplesNodes[i],".//*[local-name()='teamName']","",null);
if(cordys.selectXMLNode(tuplesNodes[i],".//*[local-name()='deptCode']"))
               optionObject.deptCode=cordys.getNodeText(tuplesNodes[i],".//*[local-name()='deptCode']","",null);
if(cordys.selectXMLNode(tuplesNodes[i],".//*[local-name()='companyCode']"))
               optionObject.companyCode=cordys.getNodeText(tuplesNodes[i],".//*[local-name()='companyCode']","",null);
if(cordys.selectXMLNode(tuplesNodes[i],".//*[local-name()='companyName']"))
               optionObject.companyName=cordys.getNodeText(tuplesNodes[i],".//*[local-name()='companyName']","",null);
if(cordys.selectXMLNode(tuplesNodes[i],".//*[local-name()='roleNameList']"))
	            optionObject.roleName=cordys.getNodeText(tuplesNodes[i],".//*[local-name()='roleNameList']","",null);
		   userArray=optionObject;
        }
        system.data.USERINFO = userArray;
        return userArray;
}

function getDataForModel(model)
{
    var tupleNodes = addOrGetCache(model);
    if(tupleNodes)
	{
		for(var i =0; i < tupleNodes.length; i++)
		{
		   var optionObject = new Object();
		       optionObject.userCode=cordys.selectXMLNode(tupleNodes[i],".//*[local-name()='userDN']");
		       optionObject.userName=cordys.selectXMLNode(tupleNodes[i],".//*[local-name()='userName']");
		       optionObject.deptName=cordys.selectXMLNode(tupleNodes[i],".//*[local-name()='teamName']");
               optionObject.deptCode=cordys.selectXMLNode(tupleNodes[i],".//*[local-name()='deptCode']");
			   optionObject.companyCode=cordys.selectXMLNode(tupleNodes[i],".//*[local-name()='companyCode']");
			   optionObject.companyName=cordys.selectXMLNode(tupleNodes[i],".//*[local-name()='companyName']");
			   optionObject.roleName=cordys.selectXMLNode(tupleNodes[i],".//*[local-name()='roleNameList']");
		       userArray=optionObject;
		}
		return userArray;
	}
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
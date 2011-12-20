var allOptions = new Array();

function addOrGetCache(model,dictionaryType)
{
    if(dictionaryType != null && dictionaryType !="")
    {
       //First time, execute the webservice and return the result.
        if(system.data.DICTIONARY == null)
        {
            return executeRequest(model,dictionaryType);
        }
        else
        {
             var existDictionaryList = system.data.DICTIONARY;
			 //If exists, return the array of records.
             if(existDictionaryList[dictionaryType])
             {
                 return existDictionaryList[dictionaryType];
             } 
             else
             {
			    //If not, execute the webservice and fetch the records from database.
                return executeRequest(model,dictionaryType);
             }
        }
    }
 }

function executeRequest(model,dictionaryType)
{        
        //Execute the webservice getDictionaryByType.      
        var request = model.getMethodRequest();
				cordys.setNodeText(request,".//*[local-name()='dictionaryType']",dictionaryType);
				model.setMethodRequest(request);
        model.reset();
        compositeData = model.getData();
        
        var tuplesNode = cordys.selectXMLNodes(compositeData,".//*[local-name()='tuple']");
        if(system.data.DICTIONARY != null)
         {
		   //Existing cache is assigned to local variable.
           allOptions = system.data.DICTIONARY;
        }
        allOptions[dictionaryType] = new Array();
        for(var i =0; i < tuplesNode.length; i++)
        {
             allOptions[dictionaryType][i] = tuplesNode[i];
        }
        system.data.DICTIONARY = allOptions;
        return allOptions[dictionaryType];
}

function getDataForModel(model, dictionaryType)
{
    var tupleNodes = addOrGetCache(model,dictionaryType);
    if(tupleNodes)
	  {
		  var dataNode = cordys.cloneXMLDocument(dataXML.XMLDocument);
			for(var i =0; i < tupleNodes.length; i++)
			{
			   cordys.appendXMLNode(tupleNodes[i],cordys.selectXMLNode(dataNode,".//*[local-name()='data']"));
			}
			model.clear();
			model.putData(dataNode);
			model.refreshAllViews();
		}
}

function clear()
{
    allOptions = new Array();
    system.data.DICTIONARY = null;
}

/*************************
*  id 要验证的文本框id
*  patrntxt  正则表达式
*  hint 提示信息
*/
function checkme(id,patrntxt,hint){
	   var textvalue = id.getValue();
	   var patrn = new RegExp(patrntxt);
	   if(textvalue!=null&&textvalue!=''){
		   if(patrn.test(textvalue)){
				  return true;
			   }else{
				   if(hint!=null&&hint!=''){
					   application.notify(hint);
				   }else{
					   application.notify("请填写正确的格式"); 
				   }
				   id.focus();
				  return false;
			   }
	   }
   }

/*************************8
*  textvalue 要验证的文本框内容
*  patrntxt  正则表达式
*/
function checkInputValue(textvalue,patrntxt,hint){
	   var patrn = new RegExp(patrntxt);
	   if(textvalue!=null&&textvalue!=''){
		   if(patrn.test(textvalue)){
				  return true;
			   }else{
                                   if(hint!=null&&hint!=''){
					   application.notify(hint);
				   }else{
					   application.notify("请填写正确的格式"); 
				   }
				  return false;
			   }
	   }
   }

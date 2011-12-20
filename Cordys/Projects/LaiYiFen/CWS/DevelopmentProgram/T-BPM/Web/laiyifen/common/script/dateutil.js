/**
 * This file is a Date extension which can handle Date string in Cordys format, 
 * 
 * @author hsong
 */

/**
 * convert a Cordys format string to a Date object 
 * @param {String} a string with Cordys date format, for instance, "2007-03-26T00:00:00.0"
 */
Date.fromCordysString=function(sDateTime)
{
	if (sDateTime=="")
		return null;
	var rgxCordysDataTime=/^(\d\d\d\d)-(\d\d)-(\d\d)T(\d\d):(\d\d):(\d\d)\.(\d){0,3}$/;
	if (rgxCordysDataTime.test(sDateTime)) {
		var iYear=Number(RegExp.$1);
		var iMonth=Number(RegExp.$2)-1;
		var iDate=Number(RegExp.$3);
		var iHour=Number(RegExp.$4);
		var iMinute=Number(RegExp.$5);
		var iSecond=Number(RegExp.$6);
		var sMs=RegExp.$7;
		for (var i=0, len=3-sMs.length;i<len;i++) {
			sMs+="0";
		}
		var iMs=Number(sMs);
	} else {
		throw "The input string '"+sDateTime+"' is not well formatted!"
	}
	return new Date( Date.UTC(iYear,iMonth,iDate,iHour,iMinute,iSecond,iMs) );
}

/**
 * convert the Date object to a Cordys format string, for instance, "2007-03-26T00:00:00.0"
 */
Date.prototype.toCordysString=function()
{
	var aryDateTime=[];

	var aryDate=[];
	aryDate.push(this.getUTCFullYear());
	aryDate.push(this.getUTCMonth()+1>9 ? this.getUTCMonth()+1 : "0"+(this.getUTCMonth()+1));
	aryDate.push(this.getUTCDate()>9 ? this.getUTCDate() : "0"+this.getUTCDate());
	aryDateTime.push(aryDate.join('-'));
	
	var aryTime=[];
	aryTime.push(this.getUTCHours()>9 ? this.getUTCHours() : "0"+this.getUTCHours());
	aryTime.push(this.getUTCMinutes()>9 ? this.getUTCMinutes() : "0"+this.getUTCMinutes());
	aryTime.push(this.getUTCSeconds()>9 ? this.getUTCSeconds() : "0"+this.getUTCSeconds());
	var sTime=aryTime.join(':')+"."+Math.floor( this.getUTCMilliseconds()/100 );
	aryDateTime.push(sTime);
	
	return aryDateTime.join('T');
}

/**
 * set the Date object to a specific local moment
 * @param {enum} iMoment the specific moment, can reference those extened properties of Date.
 */
Date.prototype.setToLocalMoment=function(iMoment)
{
	switch (iMoment) {
		case Date.MOMENT_DATE_START:
			this.setHours(0,0,0,0);
			break;
		case Date.MOMENT_DATE_END:
			this.setHours(23,59,59,999);
			break;
		case Date.MOMENT_MONTH_START:
			this.setHours(0,0,0,0);
			this.setDate(1);
			break;
		case Date.MOMENT_MONTH_END:
			this.setHours(23,59,59,999);
			//can not get the last day of a month directly,
			//so set to the first day in next month, and minus one day.
			//numDate is start from 1, so 0 means the last day before 1st 
			this.setMonth(this.getMonth()+1, 0);
			break;
		case Date.MOMENT_YEAR_START:
			this.setHours(0,0,0,0);
			//numMonth is start from 0, to 11, but numDate is from 1 to 31, weird 
			this.setMonth(0, 1);
			break;
		case Date.MOMENT_YEAR_END:
			this.setHours(23,59,59,999);
			this.setMonth(11, 31);
			break;
		default:
	}
}

/**
 * plus the specific values to this Date object
 * All parameters are optional, if one of them is positive, it's plused, otherwise, it's minused. 
 * @param {int} iYear
 * @param {int} iMonth
 * @param {int} iDate
 * @param {int} iHours
 * @param {int} iMinutes
 * @param {int} iSeconds
 * @param {int} iMilliseconds
 */
Date.prototype.plus=function(iYear,iMonth,iDate,iHours,iMinutes,iSeconds,iMs)
{
	var ilYear=this.getFullYear(); ilYear+=iYear?iYear:0;
	var ilMonth=this.getMonth(); ilMonth+=iMonth?iMonth:0;
	var ilDate=this.getDate(); ilDate+=iDate?iDate:0;
	var ilHours=this.getHours(); ilHours+=iHours?iHours:0;
	var ilMinutes=this.getMinutes(); ilMinutes+=iMinutes?iMinutes:0;
	var ilSeconds=this.getSeconds(); ilSeconds+=iSeconds?iSeconds:0;
	var ilMs=this.getMilliseconds(); ilMs+=iMs?iMs:0;
	var date=new Date();
	date.setFullYear(ilYear, ilMonth, ilDate);
	date.setHours(ilHours,ilMinutes, ilSeconds,ilMs);
	return date;
}
Date.prototype.minus=function(iYear,iMonth,iDate,iHours,iMinutes,iSeconds,iMs)
{
	return this.plus(-iYear,-iMonth,-iDate,-iHours,-iMinutes,-iSeconds,-iMs);
}
/**
 * extened properties of Date: to define a set of special moment 
 */
Date.MOMENT_DATE_START=100;
Date.MOMENT_DATE_END=101;
Date.MOMENT_MONTH_START=110;
Date.MOMENT_MONTH_END=111;
Date.MOMENT_YEAR_START=120;
Date.MOMENT_YEAR_END=121;

/**
 * convert a Cordys string to a specific local moment
 * @param {String} the Cordys format string 
 * @param {enum} iMoment the specific moment, can reference those extened properties of Date. 
 * @return a new Cordys string based on sDateTime with the specific moment(iMonment).
 */
Date.setCordysStringToLocalMoment=function(sDateTime,iMoment)
{
	var dateTime=Date.fromCordysString(sDateTime);
	if (!dateTime)
		return "";
	dateTime.setToLocalMoment(iMoment);
	return dateTime.toCordysString();
}


/**
* 返回Cordys格式的下个月第一天的日期
*/
Date.getNextMonth=function()
{
    var oNextMonthDate = (new Date()).plus(0,1,0,0,0,0,0);
    return Date.setCordysStringToLocalMoment(oNextMonthDate.toCordysString(),Date.MOMENT_MONTH_START);
}

/**
 * add option to month control with 12 months
 * @param {selectbox object}
 */
function addMonthOptions(oSelect)
{
	oSelect.addOption({vlaue:"", description:""},false,0);
	for(var i=1;i<=12;i++)
	{
		var option = {vlaue:i, description:i};
		oSelect.addOption(option);
	}
	oSelect.setValue("");
}

/**
 * add option to year control with current year and last 4 years
 * @param {selectbox object}
 */
function addYearOptions(oSelect)
{
	var d = new Date();
	var iYear = d.getFullYear();
	for(var i=0;i<5;i++)
	{
		var option = {vlaue:iYear-i, description:iYear-i};
		oSelect.addOption(option);		
	}
	oSelect.setValue(iYear);
}

Date.prototype.format = function(format){
 /*
  * eg:format="yyyy-MM-dd hh:mm:ss";
  */
 var o = {
  "M+" :  this.getMonth()+1,  //month
  "d+" :  this.getDate(),     //day
  "h+" :  this.getHours(),    //hour
      "m+" :  this.getMinutes(),  //minute
      "s+" :  this.getSeconds(), //second
      "q+" :  Math.floor((this.getMonth()+3)/3),  //quarter
      "S"  :  this.getMilliseconds() //millisecond
   }
  
   if(/(y+)/.test(format)) {
    format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
   }
 
   for(var k in o) {
    if(new RegExp("("+ k +")").test(format)) {
      format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
    }
   }
 return format;
}

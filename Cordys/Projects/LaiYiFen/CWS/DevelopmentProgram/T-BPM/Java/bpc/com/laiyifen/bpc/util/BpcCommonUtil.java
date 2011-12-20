package com.laiyifen.bpc.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.cordys.cpc.bsf.busobject.BSF;
import com.cordys.cpc.bsf.busobject.BsfContext;
import com.cordys.cpc.bsf.busobject.StoredProcedure;
import com.eibus.xml.nom.Node;
import com.eibus.xml.xpath.NodeSet;
import com.eibus.xml.xpath.XPath;
import com.eibus.xml.xpath.XPathMetaInfo;
import com.laiyifen.common.util.AssignmentUtils;

public class BpcCommonUtil {
	public static List<String> MONTH_LIST = Arrays.asList("NULL","JAN", "FEB", "MAR",   
			   "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC");  
	
	public static String changeMonthToQuarter(String month){
		String str ="";
		  switch(Integer.parseInt(month))
	         {
	         case 1:
	         case 2:
	         case 3:
	        	 str="Q1";
	        	 break ;
	         case 4:
	         case 5:
	         case 6:
	        	 str="Q2";
	        	 break ;
	         case 7:
	         case 8:
	         case 9:
	        	 str="Q3";
	        	 break ;
	         case 10:
	         case 11:
	         case 12:
		         str="Q4";
		         break ;
	         }
		return str;
	}
	public static String changeMonthNumberToString(String releaseVersion) {   
		  
		  if (null == releaseVersion) {   
		   throw new IllegalArgumentException("ReleaseVersion cannot be null");   
		  }
		  String strNumber = releaseVersion;
		  if(releaseVersion.length()>2){
			  strNumber= releaseVersion.substring(0, 2);
		  }
		  String strMonth = null;   
		  try {   
		   strMonth = MONTH_LIST.get(Integer.parseInt(strNumber));   
		  } catch (NumberFormatException e) {   
		   e.printStackTrace();   
		   System.out.println("Parse month error........");   
		  }   
		  return strMonth ;   
		 }   
		  
		 public static String changeMonthStringToNumber(String releaseVersion) {   
		  if (null == releaseVersion) {   
		   throw new IllegalArgumentException("ReleaseVersion cannot be null");   
		  }   
		  for (int i = 0; i < MONTH_LIST.size(); i++) {   
		   if (releaseVersion.toUpperCase().startsWith(MONTH_LIST.get(i))) {   
		    if (i < 10) {   
		     return "0" + i;   
		    }   
		    return i + "";   
		   }   
		  }   
		  return -1 + "";   
		 }  
		 
	public static String DoubleFormatToString(Double d){
		DecimalFormat   df1   =   new   DecimalFormat( "0.##");
		String   target=df1.format(d);
		return target;
	}
	
	public static String DateFormatToString(Date date,String Format){
		if(date==null){
			date= new Date();
		}
		if(Format ==null || "".equals(Format)){
			Format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat from = new SimpleDateFormat(Format);
		String time=from.format(date);  
		return time;
	}
	
	public static String subString_before(String str ,String substr) {
		return str.substring(0, str.indexOf(substr));
	}
	
	public static String subString_afert(String str ,String substr) {
		return str.substring(str.indexOf(substr)+substr.length());
	}
	
	public static String getBPCWorkFlowRoleByNode(String str ,String strNode){
		String tmpSplit = "NC=" + strNode + ",ROLE=";
		return subString_before(subString_afert(str,tmpSplit),";");   
	}
	
	public static String getBPCWorkFlowDeleteNode(String str ,String strNode){
		String tmpSplit = "NC=" + strNode + "";
		String tmp1,tmp2;
		tmp1 = subString_before(str,tmpSplit);
		tmp2 = subString_afert(subString_afert(str,tmpSplit),";");
		return tmp1 + tmp2;   
	}
	
	public static String getCostcenterFormatZero(String costcenter){
		String tmp = costcenter;
		if (costcenter.length()>5){
			tmp = costcenter.substring(5);
		}
		return tmp;
	}
	
	public static String getBPCTupleValueByOne(int  xml ,int pos ,String tag){
		
	    String xmlStr=Node.writeToString(xml,false);
		String[] xmlArray=xmlStr.split("</tuple>");
		String a=xmlArray[pos];
		int startPos=a.indexOf("<"+tag+">" )+ tag.length()+2;
		int endPos=a.lastIndexOf("</"+tag+">");
		return a.substring(startPos,endPos);
	}
	//副总裁替换为总裁
	public static String changeShopIDWhenVicePresidentZ(String shopid){
		String tmp = "";
		if(shopid.indexOf("NC=40")>0){
			tmp = shopid.replace("NC=15", "");
		}else{
			tmp = shopid.replace("NC=15", "NC=40");
		}
		return tmp;
	}
	
	/**
	 * 调用存储过程生成序列号
	 * 
	 * @param seqType
	 *            序列号类型
	 * @return 序列号
	 *
	 */
	public static String getSeqNumber(String seqType) {
//		BsfContext mycontext = AssignmentUtils.initBSF();
		String value = null;
//		BSF.getObjectManager().startTransaction("SEQ_TRANS");
		StoredProcedure sp = new StoredProcedure("SF_GET_AUTO_NUMBER");
		sp.prepareCall(":RESULT = \"SF_GET_AUTO_NUMBER\"( :PARAM1)");
		sp.setReturnParameter("RESULT");
		sp.setParameter("PARAM1", seqType);
		sp.execute();
		value = sp.getString("RESULT");
//		BSF.getObjectManager().commitTransaction("SEQ_TRANS",true);
//		AssignmentUtils.release(mycontext);
		return value;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println(CommonUtil.getBPCWorkFlowDeleteNode("NC=01;NC=05;NC=25;NC=30,ROLE=BR法务人员;NC=35;NC=50;NC=55", "25"));
		String xmlStr = "<tuple><name>234</name><nam>123</nam></tuple><tuple><name>222</name><nam>333</nam></tuple><tuple><name>444</name><nam>555</nam></tuple>";
	}
}

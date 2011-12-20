/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.bpc.core;

import java.util.Date;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;


public class BPC_LOG_LOAN extends BPC_LOG_LOANBase
{
    public BPC_LOG_LOAN()
    {
        this((BusObjectConfig)null);
    }

    public BPC_LOG_LOAN(BusObjectConfig config)
    {
        super(config);
    }
    
    public static void newBpcLogLoanObject(String FORM_ID,String FORM_TYPE,
    		String YEAR,String VOUCHER_CODE,
    		String LOAN_ID,Double LOAN_SUM,Double OPERATE_SUM ,
    		String EXT1,String EXT2,String EXT3){
    	BPC_LOG_LOAN logLoan = new BPC_LOG_LOAN();
    	logLoan.setFORM_ID(FORM_ID);
    	logLoan.setFORM_TYPE(FORM_TYPE);
    	logLoan.setYEAR(YEAR); 
    	logLoan.setVOUCHER_CODE(VOUCHER_CODE);
    	logLoan.setLOAN_ID(LOAN_ID);
    	logLoan.setLOAN_SUM(LOAN_SUM); 
    	logLoan.setOPERATE_SUM(OPERATE_SUM);
    	logLoan.setSTATUS("1");
    	logLoan.setUPDATE_DATE(new Date());
    	logLoan.setEXT1(EXT1);
    	logLoan.setEXT2(EXT2);
    	logLoan.setEXT2(EXT3);
    }
}
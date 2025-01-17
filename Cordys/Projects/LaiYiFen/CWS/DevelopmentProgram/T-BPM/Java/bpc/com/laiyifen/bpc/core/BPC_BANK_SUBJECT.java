/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.bpc.core;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.QueryObject;


public class BPC_BANK_SUBJECT extends BPC_BANK_SUBJECTBase
{
    public BPC_BANK_SUBJECT()
    {
        this((BusObjectConfig)null);
    }

    public BPC_BANK_SUBJECT(BusObjectConfig config)
    {
        super(config);
    }

    public static String getBpcBankSubjectByCode(String companycode){
    	BPC_BANK_SUBJECT  bpcBankSubject = getBpcBankSubjectObjectByCode(companycode);
        return bpcBankSubject.getBANK_SUBJECT();
    }
    
    public static com.laiyifen.bpc.core.BPC_BANK_SUBJECT getBpcBankSubjectObjectByCode(String companycode)
    {
        // TODO implement body
    	String queryText = "select * from \"BPC_BANK_SUBJECT\" where \"COMPANY_CODE\" = :COMPANY_CODE";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("COMPANY_CODE", "BPC_AUDIT.COMPANY_CODE", QueryObject.PARAM_STRING, companycode);//NOPMD
        query.setResultClass(BPC_BANK_SUBJECT.class);
        return (BPC_BANK_SUBJECT)query.getObject();
    }

}

/*
  This class has been generated by the Code Generator
*/

package com.laiyifen.shop;

import com.cordys.cpc.bsf.busobject.BusObjectConfig;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.busobject.QueryObject;
import com.cordys.cpc.bsf.classinfo.AttributeInfo;
import com.cordys.cpc.bsf.classinfo.ClassInfo;
import com.cordys.cpc.bsf.listeners.constraint.NumberValidator;
import com.cordys.cpc.bsf.listeners.constraint.StringValidator;


public abstract class SHOP_MASTERBase extends com.cordys.cpc.bsf.busobject.StateBusObject
{
    // tags used in the XML document
    public final static String ATTR_SHOP_ID = "SHOP_ID";
    public final static String ATTR_SHOP_NO = "SHOP_NO";
    public final static String ATTR_SHOP_NAME = "SHOP_NAME";
    public final static String ATTR_SHOP_SHORTNAME = "SHOP_SHORTNAME";
    public final static String ATTR_SHOP_SERIALNO = "SHOP_SERIALNO";
    public final static String ATTR_CORP_CODE = "CORP_CODE";
    public final static String ATTR_CORP_NAME = "CORP_NAME";
    public final static String ATTR_SALES_ORG = "SALES_ORG";
    public final static String ATTR_SALES_ORG_NAME = "SALES_ORG_NAME";
    public final static String ATTR_SHOP_ADDR = "SHOP_ADDR";
    public final static String ATTR_SHOP_DETAIL_ADDR = "SHOP_DETAIL_ADDR";
    public final static String ATTR_SALES_CHANNEL = "SALES_CHANNEL";
    public final static String ATTR_SALES_CHANNEL_NAME = "SALES_CHANNEL_NAME";
    public final static String ATTR_SHOP_ATTR = "SHOP_ATTR";
    public final static String ATTR_SHOP_ATTR_NAME = "SHOP_ATTR_NAME";
    public final static String ATTR_HOUSE_ATTR = "HOUSE_ATTR";
    public final static String ATTR_HOUSE_ATTR_NAME = "HOUSE_ATTR_NAME";
    public final static String ATTR_VENDOR_NO = "VENDOR_NO";
    public final static String ATTR_BUSS_ATTR = "BUSS_ATTR";
    public final static String ATTR_BUSS_ATTR_NAME = "BUSS_ATTR_NAME";
    public final static String ATTR_SHOP_AREA = "SHOP_AREA";
    public final static String ATTR_SHOP_TYPE = "SHOP_TYPE";
    public final static String ATTR_SHOP_TYPENAME = "SHOP_TYPENAME";
    public final static String ATTR_SHOP_ADDR2 = "SHOP_ADDR2";
    public final static String ATTR_SHOP_STATUS = "SHOP_STATUS";
    public final static String ATTR_SHOP_STATUS_DESC = "SHOP_STATUS_DESC";
    public final static String ATTR_ISCENTER = "ISCENTER";
    public final static String ATTR_OPEN_DATE = "OPEN_DATE";
    public final static String ATTR_SUC_OPEN_DATE = "SUC_OPEN_DATE";
    public final static String ATTR_RENEWAL_DATE = "RENEWAL_DATE";
    public final static String ATTR_SAP_NO = "SAP_NO";
    public final static String ATTR_EXT1 = "EXT1";
    public final static String ATTR_EXT2 = "EXT2";
    public final static String ATTR_SEASON_RPRICE = "SEASON_RPRICE";
    public final static String ATTR_OUT_SEASON_RPICE = "OUT_SEASON_RPICE";
    public final static String ATTR_LIFECYCLE_STATUS = "LIFECYCLE_STATUS";
    public final static String ATTR_CREATE_TIME = "CREATE_TIME";
    public final static String ATTR_FORM_TYPE = "FORM_TYPE";
    public final static String ATTR_SHOP_AREACODE = "SHOP_AREACODE";
    public final static String ATTR_RENT = "RENT";
    public final static String ATTR_AGENCY_FEES = "AGENCY_FEES";
    public final static String ATTR_PROPERTY_MANAGEMENT_FEES = "PROPERTY_MANAGEMENT_FEES";
    public final static String ATTR_TRANSFERFEE = "TRANSFERFEE";
    public final static String ATTR_COSTPAYMENT = "COSTPAYMENT";
    public final static String ATTR_USE_AREA = "USE_AREA";
    public final static String ATTR_RENT_PERIOD = "RENT_PERIOD";
    public final static String ATTR_RENT_FREE = "RENT_FREE";
    public final static String ATTR_SHOP_TAXRATE = "SHOP_TAXRATE";
    public final static String ATTR_EXT3 = "EXT3";
    public final static String ATTR_EXT4 = "EXT4";
    private static ClassInfo s_classInfo = null;
    public static ClassInfo _getClassInfo()//NOPMD framework ensures this is thread safe
    {
        if ( s_classInfo == null )//NOPMD
        {
            s_classInfo = newClassInfo(SHOP_MASTER.class);
            s_classInfo.setTableName("SHOP_MASTER");
            s_classInfo.setUIDElements(new String[]{ATTR_SHOP_ID});
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SHOP_ID);
                ai.setJavaName(ATTR_SHOP_ID);
                ai.setColumnName(ATTR_SHOP_ID);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SHOP_ID);
                v.setMaxLength(40);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SHOP_NO);
                ai.setJavaName(ATTR_SHOP_NO);
                ai.setColumnName(ATTR_SHOP_NO);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SHOP_NO);
                v.setMaxLength(4);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SHOP_NAME);
                ai.setJavaName(ATTR_SHOP_NAME);
                ai.setColumnName(ATTR_SHOP_NAME);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SHOP_NAME);
                v.setMaxLength(30);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SHOP_SHORTNAME);
                ai.setJavaName(ATTR_SHOP_SHORTNAME);
                ai.setColumnName(ATTR_SHOP_SHORTNAME);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SHOP_SHORTNAME);
                v.setMaxLength(30);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SHOP_SERIALNO);
                ai.setJavaName(ATTR_SHOP_SERIALNO);
                ai.setColumnName(ATTR_SHOP_SERIALNO);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SHOP_SERIALNO);
                v.setMaxLength(8);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_CORP_CODE);
                ai.setJavaName(ATTR_CORP_CODE);
                ai.setColumnName(ATTR_CORP_CODE);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_CORP_CODE);
                v.setMaxLength(4);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_CORP_NAME);
                ai.setJavaName(ATTR_CORP_NAME);
                ai.setColumnName(ATTR_CORP_NAME);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_CORP_NAME);
                v.setMaxLength(40);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SALES_ORG);
                ai.setJavaName(ATTR_SALES_ORG);
                ai.setColumnName(ATTR_SALES_ORG);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SALES_ORG);
                v.setMaxLength(4);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SALES_ORG_NAME);
                ai.setJavaName(ATTR_SALES_ORG_NAME);
                ai.setColumnName(ATTR_SALES_ORG_NAME);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SALES_ORG_NAME);
                v.setMaxLength(40);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SHOP_ADDR);
                ai.setJavaName(ATTR_SHOP_ADDR);
                ai.setColumnName(ATTR_SHOP_ADDR);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SHOP_ADDR);
                v.setMaxLength(60);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SHOP_DETAIL_ADDR);
                ai.setJavaName(ATTR_SHOP_DETAIL_ADDR);
                ai.setColumnName(ATTR_SHOP_DETAIL_ADDR);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SHOP_DETAIL_ADDR);
                v.setMaxLength(60);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SALES_CHANNEL);
                ai.setJavaName(ATTR_SALES_CHANNEL);
                ai.setColumnName(ATTR_SALES_CHANNEL);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SALES_CHANNEL);
                v.setMaxLength(2);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SALES_CHANNEL_NAME);
                ai.setJavaName(ATTR_SALES_CHANNEL_NAME);
                ai.setColumnName(ATTR_SALES_CHANNEL_NAME);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SALES_CHANNEL_NAME);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SHOP_ATTR);
                ai.setJavaName(ATTR_SHOP_ATTR);
                ai.setColumnName(ATTR_SHOP_ATTR);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SHOP_ATTR);
                v.setMaxLength(4);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SHOP_ATTR_NAME);
                ai.setJavaName(ATTR_SHOP_ATTR_NAME);
                ai.setColumnName(ATTR_SHOP_ATTR_NAME);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SHOP_ATTR_NAME);
                v.setMaxLength(10);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_HOUSE_ATTR);
                ai.setJavaName(ATTR_HOUSE_ATTR);
                ai.setColumnName(ATTR_HOUSE_ATTR);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_HOUSE_ATTR);
                v.setMaxLength(4);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_HOUSE_ATTR_NAME);
                ai.setJavaName(ATTR_HOUSE_ATTR_NAME);
                ai.setColumnName(ATTR_HOUSE_ATTR_NAME);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_HOUSE_ATTR_NAME);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_VENDOR_NO);
                ai.setJavaName(ATTR_VENDOR_NO);
                ai.setColumnName(ATTR_VENDOR_NO);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_VENDOR_NO);
                v.setMaxLength(10);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_BUSS_ATTR);
                ai.setJavaName(ATTR_BUSS_ATTR);
                ai.setColumnName(ATTR_BUSS_ATTR);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_BUSS_ATTR);
                v.setMaxLength(2);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_BUSS_ATTR_NAME);
                ai.setJavaName(ATTR_BUSS_ATTR_NAME);
                ai.setColumnName(ATTR_BUSS_ATTR_NAME);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_BUSS_ATTR_NAME);
                v.setMaxLength(20);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SHOP_AREA);
                ai.setJavaName(ATTR_SHOP_AREA);
                ai.setColumnName(ATTR_SHOP_AREA);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SHOP_AREA);
                v.setMaxLength(20);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SHOP_TYPE);
                ai.setJavaName(ATTR_SHOP_TYPE);
                ai.setColumnName(ATTR_SHOP_TYPE);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SHOP_TYPE);
                v.setMaxLength(2);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SHOP_TYPENAME);
                ai.setJavaName(ATTR_SHOP_TYPENAME);
                ai.setColumnName(ATTR_SHOP_TYPENAME);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SHOP_TYPENAME);
                v.setMaxLength(20);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SHOP_ADDR2);
                ai.setJavaName(ATTR_SHOP_ADDR2);
                ai.setColumnName(ATTR_SHOP_ADDR2);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SHOP_ADDR2);
                v.setMaxLength(30);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SHOP_STATUS);
                ai.setJavaName(ATTR_SHOP_STATUS);
                ai.setColumnName(ATTR_SHOP_STATUS);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SHOP_STATUS);
                v.setMaxLength(2);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SHOP_STATUS_DESC);
                ai.setJavaName(ATTR_SHOP_STATUS_DESC);
                ai.setColumnName(ATTR_SHOP_STATUS_DESC);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SHOP_STATUS_DESC);
                v.setMaxLength(40);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_ISCENTER);
                ai.setJavaName(ATTR_ISCENTER);
                ai.setColumnName(ATTR_ISCENTER);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_ISCENTER);
                v.setMaxLength(1);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_OPEN_DATE);
                ai.setJavaName(ATTR_OPEN_DATE);
                ai.setColumnName(ATTR_OPEN_DATE);
                ai.setAttributeClass(java.util.Date.class);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SUC_OPEN_DATE);
                ai.setJavaName(ATTR_SUC_OPEN_DATE);
                ai.setColumnName(ATTR_SUC_OPEN_DATE);
                ai.setAttributeClass(java.util.Date.class);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_RENEWAL_DATE);
                ai.setJavaName(ATTR_RENEWAL_DATE);
                ai.setColumnName(ATTR_RENEWAL_DATE);
                ai.setAttributeClass(java.util.Date.class);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SAP_NO);
                ai.setJavaName(ATTR_SAP_NO);
                ai.setColumnName(ATTR_SAP_NO);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SAP_NO);
                v.setMaxLength(10);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_EXT1);
                ai.setJavaName(ATTR_EXT1);
                ai.setColumnName(ATTR_EXT1);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_EXT1);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_EXT2);
                ai.setJavaName(ATTR_EXT2);
                ai.setColumnName(ATTR_EXT2);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_EXT2);
                v.setMaxLength(50);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SEASON_RPRICE);
                ai.setJavaName(ATTR_SEASON_RPRICE);
                ai.setColumnName(ATTR_SEASON_RPRICE);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_SEASON_RPRICE);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_OUT_SEASON_RPICE);
                ai.setJavaName(ATTR_OUT_SEASON_RPICE);
                ai.setColumnName(ATTR_OUT_SEASON_RPICE);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_OUT_SEASON_RPICE);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_LIFECYCLE_STATUS);
                ai.setJavaName(ATTR_LIFECYCLE_STATUS);
                ai.setColumnName(ATTR_LIFECYCLE_STATUS);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_LIFECYCLE_STATUS);
                v.setMaxLength(2);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_CREATE_TIME);
                ai.setJavaName(ATTR_CREATE_TIME);
                ai.setColumnName(ATTR_CREATE_TIME);
                ai.setAttributeClass(java.util.Date.class);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_FORM_TYPE);
                ai.setJavaName(ATTR_FORM_TYPE);
                ai.setColumnName(ATTR_FORM_TYPE);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_FORM_TYPE);
                v.setMaxLength(1);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SHOP_AREACODE);
                ai.setJavaName(ATTR_SHOP_AREACODE);
                ai.setColumnName(ATTR_SHOP_AREACODE);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SHOP_AREACODE);
                v.setMaxLength(4);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_RENT);
                ai.setJavaName(ATTR_RENT);
                ai.setColumnName(ATTR_RENT);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_RENT);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_AGENCY_FEES);
                ai.setJavaName(ATTR_AGENCY_FEES);
                ai.setColumnName(ATTR_AGENCY_FEES);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_AGENCY_FEES);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_PROPERTY_MANAGEMENT_FEES);
                ai.setJavaName(ATTR_PROPERTY_MANAGEMENT_FEES);
                ai.setColumnName(ATTR_PROPERTY_MANAGEMENT_FEES);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_PROPERTY_MANAGEMENT_FEES);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_TRANSFERFEE);
                ai.setJavaName(ATTR_TRANSFERFEE);
                ai.setColumnName(ATTR_TRANSFERFEE);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_TRANSFERFEE);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_COSTPAYMENT);
                ai.setJavaName(ATTR_COSTPAYMENT);
                ai.setColumnName(ATTR_COSTPAYMENT);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_COSTPAYMENT);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_USE_AREA);
                ai.setJavaName(ATTR_USE_AREA);
                ai.setColumnName(ATTR_USE_AREA);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_USE_AREA);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_RENT_PERIOD);
                ai.setJavaName(ATTR_RENT_PERIOD);
                ai.setColumnName(ATTR_RENT_PERIOD);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_RENT_PERIOD);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_RENT_FREE);
                ai.setJavaName(ATTR_RENT_FREE);
                ai.setColumnName(ATTR_RENT_FREE);
                ai.setAttributeClass(double.class);
                NumberValidator v = new NumberValidator(ATTR_RENT_FREE);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_SHOP_TAXRATE);
                ai.setJavaName(ATTR_SHOP_TAXRATE);
                ai.setColumnName(ATTR_SHOP_TAXRATE);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_SHOP_TAXRATE);
                v.setMaxLength(2);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_EXT3);
                ai.setJavaName(ATTR_EXT3);
                ai.setColumnName(ATTR_EXT3);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_EXT3);
                v.setMaxLength(20);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
            {
                AttributeInfo ai = new AttributeInfo(ATTR_EXT4);
                ai.setJavaName(ATTR_EXT4);
                ai.setColumnName(ATTR_EXT4);
                ai.setAttributeClass(String.class);
                StringValidator v = new StringValidator(ATTR_EXT4);
                v.setMaxLength(20);
                ai.addConstraintHandler(v);
                s_classInfo.addAttributeInfo(ai);
            }
        }
        return s_classInfo;
    }

    public SHOP_MASTERBase(BusObjectConfig config)
    {
        super(config);
    }

    public String getSHOP_ID()
    {
        return getStringProperty(ATTR_SHOP_ID);
    }

    public void setSHOP_ID(String value)
    {
        setProperty(ATTR_SHOP_ID, value, 0);
    }

    public String getSHOP_NO()
    {
        return getStringProperty(ATTR_SHOP_NO);
    }

    public void setSHOP_NO(String value)
    {
        setProperty(ATTR_SHOP_NO, value, 0);
    }

    public String getSHOP_NAME()
    {
        return getStringProperty(ATTR_SHOP_NAME);
    }

    public void setSHOP_NAME(String value)
    {
        setProperty(ATTR_SHOP_NAME, value, 0);
    }

    public String getSHOP_SHORTNAME()
    {
        return getStringProperty(ATTR_SHOP_SHORTNAME);
    }

    public void setSHOP_SHORTNAME(String value)
    {
        setProperty(ATTR_SHOP_SHORTNAME, value, 0);
    }

    public String getSHOP_SERIALNO()
    {
        return getStringProperty(ATTR_SHOP_SERIALNO);
    }

    public void setSHOP_SERIALNO(String value)
    {
        setProperty(ATTR_SHOP_SERIALNO, value, 0);
    }

    public String getCORP_CODE()
    {
        return getStringProperty(ATTR_CORP_CODE);
    }

    public void setCORP_CODE(String value)
    {
        setProperty(ATTR_CORP_CODE, value, 0);
    }

    public String getCORP_NAME()
    {
        return getStringProperty(ATTR_CORP_NAME);
    }

    public void setCORP_NAME(String value)
    {
        setProperty(ATTR_CORP_NAME, value, 0);
    }

    public String getSALES_ORG()
    {
        return getStringProperty(ATTR_SALES_ORG);
    }

    public void setSALES_ORG(String value)
    {
        setProperty(ATTR_SALES_ORG, value, 0);
    }

    public String getSALES_ORG_NAME()
    {
        return getStringProperty(ATTR_SALES_ORG_NAME);
    }

    public void setSALES_ORG_NAME(String value)
    {
        setProperty(ATTR_SALES_ORG_NAME, value, 0);
    }

    public String getSHOP_ADDR()
    {
        return getStringProperty(ATTR_SHOP_ADDR);
    }

    public void setSHOP_ADDR(String value)
    {
        setProperty(ATTR_SHOP_ADDR, value, 0);
    }

    public String getSHOP_DETAIL_ADDR()
    {
        return getStringProperty(ATTR_SHOP_DETAIL_ADDR);
    }

    public void setSHOP_DETAIL_ADDR(String value)
    {
        setProperty(ATTR_SHOP_DETAIL_ADDR, value, 0);
    }

    public String getSALES_CHANNEL()
    {
        return getStringProperty(ATTR_SALES_CHANNEL);
    }

    public void setSALES_CHANNEL(String value)
    {
        setProperty(ATTR_SALES_CHANNEL, value, 0);
    }

    public String getSALES_CHANNEL_NAME()
    {
        return getStringProperty(ATTR_SALES_CHANNEL_NAME);
    }

    public void setSALES_CHANNEL_NAME(String value)
    {
        setProperty(ATTR_SALES_CHANNEL_NAME, value, 0);
    }

    public String getSHOP_ATTR()
    {
        return getStringProperty(ATTR_SHOP_ATTR);
    }

    public void setSHOP_ATTR(String value)
    {
        setProperty(ATTR_SHOP_ATTR, value, 0);
    }

    public String getSHOP_ATTR_NAME()
    {
        return getStringProperty(ATTR_SHOP_ATTR_NAME);
    }

    public void setSHOP_ATTR_NAME(String value)
    {
        setProperty(ATTR_SHOP_ATTR_NAME, value, 0);
    }

    public String getHOUSE_ATTR()
    {
        return getStringProperty(ATTR_HOUSE_ATTR);
    }

    public void setHOUSE_ATTR(String value)
    {
        setProperty(ATTR_HOUSE_ATTR, value, 0);
    }

    public String getHOUSE_ATTR_NAME()
    {
        return getStringProperty(ATTR_HOUSE_ATTR_NAME);
    }

    public void setHOUSE_ATTR_NAME(String value)
    {
        setProperty(ATTR_HOUSE_ATTR_NAME, value, 0);
    }

    public String getVENDOR_NO()
    {
        return getStringProperty(ATTR_VENDOR_NO);
    }

    public void setVENDOR_NO(String value)
    {
        setProperty(ATTR_VENDOR_NO, value, 0);
    }

    public String getBUSS_ATTR()
    {
        return getStringProperty(ATTR_BUSS_ATTR);
    }

    public void setBUSS_ATTR(String value)
    {
        setProperty(ATTR_BUSS_ATTR, value, 0);
    }

    public String getBUSS_ATTR_NAME()
    {
        return getStringProperty(ATTR_BUSS_ATTR_NAME);
    }

    public void setBUSS_ATTR_NAME(String value)
    {
        setProperty(ATTR_BUSS_ATTR_NAME, value, 0);
    }

    public String getSHOP_AREA()
    {
        return getStringProperty(ATTR_SHOP_AREA);
    }

    public void setSHOP_AREA(String value)
    {
        setProperty(ATTR_SHOP_AREA, value, 0);
    }

    public String getSHOP_TYPE()
    {
        return getStringProperty(ATTR_SHOP_TYPE);
    }

    public void setSHOP_TYPE(String value)
    {
        setProperty(ATTR_SHOP_TYPE, value, 0);
    }

    public String getSHOP_TYPENAME()
    {
        return getStringProperty(ATTR_SHOP_TYPENAME);
    }

    public void setSHOP_TYPENAME(String value)
    {
        setProperty(ATTR_SHOP_TYPENAME, value, 0);
    }

    public String getSHOP_ADDR2()
    {
        return getStringProperty(ATTR_SHOP_ADDR2);
    }

    public void setSHOP_ADDR2(String value)
    {
        setProperty(ATTR_SHOP_ADDR2, value, 0);
    }

    public String getSHOP_STATUS()
    {
        return getStringProperty(ATTR_SHOP_STATUS);
    }

    public void setSHOP_STATUS(String value)
    {
        setProperty(ATTR_SHOP_STATUS, value, 0);
    }

    public String getSHOP_STATUS_DESC()
    {
        return getStringProperty(ATTR_SHOP_STATUS_DESC);
    }

    public void setSHOP_STATUS_DESC(String value)
    {
        setProperty(ATTR_SHOP_STATUS_DESC, value, 0);
    }

    public String getISCENTER()
    {
        return getStringProperty(ATTR_ISCENTER);
    }

    public void setISCENTER(String value)
    {
        setProperty(ATTR_ISCENTER, value, 0);
    }

    public java.util.Date getOPEN_DATE()
    {
        return getDateTimestampProperty(ATTR_OPEN_DATE);
    }

    public void setOPEN_DATE(java.util.Date value)
    {
        setProperty(ATTR_OPEN_DATE, value, 0);
    }

    public java.util.Date getSUC_OPEN_DATE()
    {
        return getDateTimestampProperty(ATTR_SUC_OPEN_DATE);
    }

    public void setSUC_OPEN_DATE(java.util.Date value)
    {
        setProperty(ATTR_SUC_OPEN_DATE, value, 0);
    }

    public java.util.Date getRENEWAL_DATE()
    {
        return getDateTimestampProperty(ATTR_RENEWAL_DATE);
    }

    public void setRENEWAL_DATE(java.util.Date value)
    {
        setProperty(ATTR_RENEWAL_DATE, value, 0);
    }

    public String getSAP_NO()
    {
        return getStringProperty(ATTR_SAP_NO);
    }

    public void setSAP_NO(String value)
    {
        setProperty(ATTR_SAP_NO, value, 0);
    }

    public String getEXT1()
    {
        return getStringProperty(ATTR_EXT1);
    }

    public void setEXT1(String value)
    {
        setProperty(ATTR_EXT1, value, 0);
    }

    public String getEXT2()
    {
        return getStringProperty(ATTR_EXT2);
    }

    public void setEXT2(String value)
    {
        setProperty(ATTR_EXT2, value, 0);
    }

    public double getSEASON_RPRICE()
    {
        return getDoubleProperty(ATTR_SEASON_RPRICE);
    }

    public void setSEASON_RPRICE(double value)
    {
        setProperty(ATTR_SEASON_RPRICE, value, 0);
    }

    public double getOUT_SEASON_RPICE()
    {
        return getDoubleProperty(ATTR_OUT_SEASON_RPICE);
    }

    public void setOUT_SEASON_RPICE(double value)
    {
        setProperty(ATTR_OUT_SEASON_RPICE, value, 0);
    }

    public String getLIFECYCLE_STATUS()
    {
        return getStringProperty(ATTR_LIFECYCLE_STATUS);
    }

    public void setLIFECYCLE_STATUS(String value)
    {
        setProperty(ATTR_LIFECYCLE_STATUS, value, 0);
    }

    public java.util.Date getCREATE_TIME()
    {
        return getDateTimestampProperty(ATTR_CREATE_TIME);
    }

    public void setCREATE_TIME(java.util.Date value)
    {
        setProperty(ATTR_CREATE_TIME, value, 0);
    }

    public String getFORM_TYPE()
    {
        return getStringProperty(ATTR_FORM_TYPE);
    }

    public void setFORM_TYPE(String value)
    {
        setProperty(ATTR_FORM_TYPE, value, 0);
    }

    public String getSHOP_AREACODE()
    {
        return getStringProperty(ATTR_SHOP_AREACODE);
    }

    public void setSHOP_AREACODE(String value)
    {
        setProperty(ATTR_SHOP_AREACODE, value, 0);
    }

    public double getRENT()
    {
        return getDoubleProperty(ATTR_RENT);
    }

    public void setRENT(double value)
    {
        setProperty(ATTR_RENT, value, 0);
    }

    public double getAGENCY_FEES()
    {
        return getDoubleProperty(ATTR_AGENCY_FEES);
    }

    public void setAGENCY_FEES(double value)
    {
        setProperty(ATTR_AGENCY_FEES, value, 0);
    }

    public double getPROPERTY_MANAGEMENT_FEES()
    {
        return getDoubleProperty(ATTR_PROPERTY_MANAGEMENT_FEES);
    }

    public void setPROPERTY_MANAGEMENT_FEES(double value)
    {
        setProperty(ATTR_PROPERTY_MANAGEMENT_FEES, value, 0);
    }

    public double getTRANSFERFEE()
    {
        return getDoubleProperty(ATTR_TRANSFERFEE);
    }

    public void setTRANSFERFEE(double value)
    {
        setProperty(ATTR_TRANSFERFEE, value, 0);
    }

    public double getCOSTPAYMENT()
    {
        return getDoubleProperty(ATTR_COSTPAYMENT);
    }

    public void setCOSTPAYMENT(double value)
    {
        setProperty(ATTR_COSTPAYMENT, value, 0);
    }

    public double getUSE_AREA()
    {
        return getDoubleProperty(ATTR_USE_AREA);
    }

    public void setUSE_AREA(double value)
    {
        setProperty(ATTR_USE_AREA, value, 0);
    }

    public double getRENT_PERIOD()
    {
        return getDoubleProperty(ATTR_RENT_PERIOD);
    }

    public void setRENT_PERIOD(double value)
    {
        setProperty(ATTR_RENT_PERIOD, value, 0);
    }

    public double getRENT_FREE()
    {
        return getDoubleProperty(ATTR_RENT_FREE);
    }

    public void setRENT_FREE(double value)
    {
        setProperty(ATTR_RENT_FREE, value, 0);
    }

    public String getSHOP_TAXRATE()
    {
        return getStringProperty(ATTR_SHOP_TAXRATE);
    }

    public void setSHOP_TAXRATE(String value)
    {
        setProperty(ATTR_SHOP_TAXRATE, value, 0);
    }

    public String getEXT3()
    {
        return getStringProperty(ATTR_EXT3);
    }

    public void setEXT3(String value)
    {
        setProperty(ATTR_EXT3, value, 0);
    }

    public String getEXT4()
    {
        return getStringProperty(ATTR_EXT4);
    }

    public void setEXT4(String value)
    {
        setProperty(ATTR_EXT4, value, 0);
    }


    public static BusObjectIterator<com.laiyifen.shop.SHOP_MASTER> getNextShopMasterObjects(String SHOP_ID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"SHOP_MASTER\" where (\"SHOP_ID\" > :SHOP_ID) order by \"SHOP_ID\" asc";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("SHOP_ID", "SHOP_MASTER.SHOP_ID", QueryObject.PARAM_STRING, SHOP_ID);//NOPMD
        query.setResultClass(SHOP_MASTER.class);
        query.setCursor(cursor);
        return query.getObjects();
    }

    public static BusObjectIterator<com.laiyifen.shop.SHOP_MASTER> getPreviousShopMasterObjects(String SHOP_ID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"SHOP_MASTER\" where (\"SHOP_ID\" < :SHOP_ID) order by \"SHOP_ID\" desc";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("SHOP_ID", "SHOP_MASTER.SHOP_ID", QueryObject.PARAM_STRING, SHOP_ID);//NOPMD
        query.setResultClass(SHOP_MASTER.class);
        query.setCursor(cursor);
        return query.getObjects();
    }



    public static com.laiyifen.shop.SHOP_MASTER getShopMasterObject(String SHOP_ID)
    {
        String queryText = "select * from \"SHOP_MASTER\" where \"SHOP_ID\" = :SHOP_ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("SHOP_ID", "SHOP_MASTER.SHOP_ID", QueryObject.PARAM_STRING, SHOP_ID);//NOPMD
        query.setResultClass(SHOP_MASTER.class);
        return (SHOP_MASTER)query.getObject();
    }

    public static BusObjectIterator<com.laiyifen.shop.SHOP_MASTER> getShopMasterObjects(String fromSHOP_ID, String toSHOP_ID, com.cordys.cpc.bsf.query.Cursor cursor)
    {
        String queryText = "select * from \"SHOP_MASTER\" where \"SHOP_ID\" between :fromSHOP_ID and :toSHOP_ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("fromSHOP_ID", "SHOP_MASTER.SHOP_ID", QueryObject.PARAM_STRING, fromSHOP_ID);
        query.addParameter("toSHOP_ID", "SHOP_MASTER.SHOP_ID", QueryObject.PARAM_STRING, toSHOP_ID);
        query.setResultClass(SHOP_MASTER.class);
        query.setCursor(cursor);
        return query.getObjects();
    }

    public static BusObjectIterator<com.laiyifen.shop.SHOP_MASTER> getShopMasterObjects(String fromSHOP_ID, String toSHOP_ID)
    {
        String queryText = "select * from \"SHOP_MASTER\" where \"SHOP_ID\" between :fromSHOP_ID and :toSHOP_ID";
        QueryObject query = new QueryObject(queryText);
        query.addParameter("fromSHOP_ID", "SHOP_MASTER.SHOP_ID", QueryObject.PARAM_STRING, fromSHOP_ID);
        query.addParameter("toSHOP_ID", "SHOP_MASTER.SHOP_ID", QueryObject.PARAM_STRING, toSHOP_ID);
        query.setResultClass(SHOP_MASTER.class);
        return query.getObjects();
    }


}
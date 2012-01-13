drop table SHOP_MASTER cascade constraints;

/*==============================================================*/
/* Table: SHOP_MASTER                                           */
/*==============================================================*/
create table SHOP_MASTER  (
   SHOP_ID              NUMBER                          not null,
   SHOP_NO              NVARCHAR2(4),
   SHOP_NAME            NVARCHAR2(50),
   SHOP_SHORTNAME       NVARCHAR2(25),
   SHOP_SERIALNO        INTEGER,
   CORP_CODE            NVARCHAR2(10),
   SALES_ORG            NVARCHAR2(10),
   SHOP_ADDR            NVARCHAR2(50),
   SALES_CHANNEL        NVARCHAR2(10),
   SHOP_ATTR            CHAR(1),
   HOUSE_ATTR           CHAR(1),
   VENDOR_NO            NVARCHAR2(10),
   BUSS_ATTR            NVARCHAR2(4),
   SHOP_AREA            NVARCHAR2(25),
   SHOP_TYPE            NVARCHAR2(2),
   SHOP_ADDR2           NVARCHAR2(50),
   SHOP_STATUS          CHAR(1),
   ISCENTER             CHAR(1),
   OPEN_DATE            DATE,
   SUC_OPEN_DATE        DATE,
   RENEWAL_DATE         DATE,
   SAP_NO               NVARCHAR2(10),
   constraint PK_SHOP_MASTER primary key (SHOP_ID)
);

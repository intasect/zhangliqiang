/*==============================================================*/
/* DBMS NAME:      ORACLE VERSION 10GR2                         */
/* CREATED ON:     2011-7-26 18:02:07                           */
/*==============================================================*/


BEGIN EXECUTE IMMEDIATE 'DROP TABLE TARGET_STORE_INFO CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN NULL; END;
/

BEGIN EXECUTE IMMEDIATE 'DROP TABLE TARGET_STORE_CHECK CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN NULL; END;
/

BEGIN EXECUTE IMMEDIATE 'DROP TABLE BUSINESS_CIRCLE CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN NULL; END;
/

BEGIN EXECUTE IMMEDIATE 'DROP TABLE ATTACHMENT CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN NULL; END;
/

BEGIN EXECUTE IMMEDIATE 'DROP TABLE TARGET_SHOP_ESTIMATION CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN NULL; END;
/

BEGIN EXECUTE IMMEDIATE 'DROP TABLE APPROVAL_HISTORY CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN NULL; END;
/

BEGIN EXECUTE IMMEDIATE 'DROP TABLE DICTIONARY CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN NULL; END;
/

DROP SEQUENCE SEQ_APPROVAL_HISTORY;

DROP SEQUENCE SEQ_ATTACHMENT;

DROP SEQUENCE SEQ_TARGET_STORE_CHECK;

DROP SEQUENCE SEQ_BUSINESS_CIRCLE;

DROP SEQUENCE SEQ_TARGET_SHOP_ESTIMATION;

CREATE SEQUENCE SEQ_APPROVAL_HISTORY
START WITH 1
INCREMENT BY 1;

CREATE SEQUENCE SEQ_ATTACHMENT
START WITH 1
INCREMENT BY 1;

CREATE SEQUENCE SEQ_TARGET_STORE_CHECK
START WITH 1
INCREMENT BY 1;

CREATE SEQUENCE SEQ_BUSINESS_CIRCLE
INCREMENT BY 1
START WITH 1;

CREATE SEQUENCE SEQ_TARGET_SHOP_ESTIMATION
START WITH 1
INCREMENT BY 1;

/*==============================================================*/
/* TABLE: TARGET_STORE_INFO                                    */
/*==============================================================*/
CREATE TABLE TARGET_STORE_INFO  (
   FORM_ID            	NVARCHAR2(50)                   NOT NULL,
   USER_NAME            NVARCHAR2(50)                   NOT NULL,
   USER_DN              NVARCHAR2(255)                  NOT NULL,
   USER_CODE            NVARCHAR2(30)                   NOT NULL,
   DEPARTMENT_CODE      NVARCHAR2(30),
   DEPARTMENT_NAME      NVARCHAR2(40)                   NOT NULL,
   APPLICATION_DATE   	TIMESTAMP(14)                   NOT NULL,
   CITY                 NVARCHAR2(50),
   REGION               NVARCHAR2(50),
   STREET               NVARCHAR2(80),
   ROAD                 NVARCHAR2(200),
   UNIT_NUMBER          INTEGER,
   NEARROAD             NVARCHAR2(100),
   PROPERTY_ADDRESS   	NVARCHAR2(200),
   PROPERTY_NAME     	NVARCHAR2(200),
   ISFULL_RESOURCE_CODE NVARCHAR2(50),
   ISFULL_RESOURCE_DEC  NVARCHAR2(100),
   TOTAL_FLOOR_AREA     NUMBER(10,2),
   TOTAL_USE_AREA       NUMBER(10,2),
   DOOR_WIDTH           NUMBER(6,2),
   INNER_DOOR_WIDTH     NUMBER(6,2),
   SHOP_DEPTH           NUMBER(6,2),
   FLOORS               INTEGER,
   ONEROOM              NVARCHAR2(30),
   ROAD_WIDTH           NUMBER(6,2),
   MAIN_BUSROUTES       NVARCHAR2(80),
   BRANCH_PROPERTY_CODE CHAR(2),
   BRANCH_PROPERTY_DEC  NVARCHAR2(50),
   BUSINESS_ITEM        NVARCHAR2(50),
   CONTACT              NVARCHAR2(30),
   TELEPHONE            NVARCHAR2(30),
   NEAR_BANK            NVARCHAR2(100),
   HOUSE_PROPERTY_CODE  CHAR(2),
   HOUSE_PROPERTY_DEC   NVARCHAR2(50),
   LANDLORD_CODE        CHAR(2),
   LANDLORD_DEC         NVARCHAR2(20),
   RENT                 NUMBER(6,4),
   RENT_PERIOD          TIMESTAMP(14),
   RENT_PAYMENT         NVARCHAR2(30),
   RENT_FREE            NVARCHAR2(20),
   ISRENT_INCREASED     CHAR(2),
   RENT_INCREASED       NVARCHAR2(20),
   TRANSFERFEE          NUMBER(6,2),
   TRANSFERFEE_INFO     NVARCHAR2(20),
   COSTNAME             NVARCHAR2(20),
   AMOUNT               NUMBER(10,2),
   PAYMENT_CODE         CHAR(2),
   PAYMENT_DEC          NVARCHAR2(20),
   SUGGESTION           NVARCHAR2(200),
   LIGHTBOXAD           NVARCHAR2(100),
   LIGHTBOXAD_LENGTH    NUMBER(6,4),
   LIGHTBOXAD_WIDTH     NUMBER(6,4),
   SHOWCASE             NVARCHAR2(30),
   SHOWCASE_NUMBER    	INTEGER,
   SHOP_ID              INTEGER                         NOT NULL,
   CONSTRAINT PK_TARGET_STORE_INFO PRIMARY KEY (FORM_ID)
);

CREATE  INDEX TARGET_STORE_INFO_SHOP_ID ON TARGET_STORE_INFO(SHOP_ID);

/*==============================================================*/
/* TABLE: TARGET_STORE_CHECK                                       */
/*==============================================================*/
CREATE TABLE TARGET_STORE_CHECK  (
   ID                   INTEGER                         NOT NULL,
   FORM_ID              NVARCHAR2(50)                   NOT NULL,
   TARGET_RENT          NUMBER(10,2),
   APPORTION_SCHEME     NVARCHAR2(80),
   ISINNER_PLANNING     CHAR(2),
   COMPETITOR_NAME      NVARCHAR2(50),
   AREA                 NUMBER(10,4),
   ESTIMATED_TURNOVER   NUMBER(10,4),
   ASSESS               NVARCHAR2(200),
   ISNEAR_SHOP          CHAR(2),
   ORISHOP_ADDRESS      NVARCHAR2(200),
   ORISHOPMONTH_YRENT   NUMBER(10,2),
   ORISHOP_TURNOVER     NUMBER(10,2),
   SPACING              NUMBER(10,2),
   CONSTRAINT PK_TARGET_STORE_CHECK PRIMARY KEY (ID),
   CONSTRAINT FK_TSI_TARGETSTORECHECK FOREIGN KEY (FORM_ID) REFERENCES TARGET_STORE_INFO (FORM_ID)
);


/*==============================================================*/
/* TABLE: BUSINESS_CIRCLE                                      */
/*==============================================================*/
CREATE TABLE BUSINESS_CIRCLE  (
   ID                   INTEGER                         NOT NULL,
   FORM_ID              NVARCHAR2(50)                   NOT NULL,
   MOR_START_TIME       TIMESTAMP(14),
   MOR_END_TIME         TIMESTAMP(14),
   MOR_PERSON_AMOUNT    INTEGER,
   MOR_WEATHER          NVARCHAR2(50),
   MID_START_TIME       TIMESTAMP(14),
   MIDDAY_END_TIME      TIMESTAMP(14),
   MID_PERSON_AMOUNT    INTEGER,
   MID_WEATHER          NVARCHAR2(50),
   NIGHTS_START_TIME    TIMESTAMP(14),
   NIGHTS_END_TIME      TIMESTAMP(14),
   NIGHTS_PERSON_AMOUNT INTEGER,
   NIGHTS_WEATHER       NVARCHAR2(50),
   ISNEAR_UPTOWN        CHAR(2),
   ISUPTOWN_CENTER      CHAR(2),
   ISMATURE_AREA        CHAR(2),
   TOTAL_APARTMENTS   	INTEGER,
   LIVING_RATE       	NUMBER(6,4),
   LIVING_QUALITY     	NVARCHAR2(50),
   ISMALL               CHAR(2),
   ISCOMPETITOR         CHAR(2),
   COMPETITOR_STRENGTH  NVARCHAR2(50),
   DETAILS              NVARCHAR2(200),
   ONELEFTBUSINESS_ITEM NVARCHAR2(100),
   ONELEFT_AREA         NUMBER(10,2),
   ONELEFT_RENT         NUMBER(10,2),
   ONELEFT_TURNOVER     NUMBER(10,2),
   TWOLEFT_MAN_PROJECT  NVARCHAR2(100),
   TWOLEFT_AREA         NUMBER(10,2),
   TWOLEFT_RENT         NUMBER(10,2),
   TWOLEFT_TURNOVER     NUMBER(10,2),
   ONERIGHT_MAN_PROJECT NVARCHAR2(100),
   ONERIGHT_AREA        NUMBER(10,2),
   ONERIGHT_RENT        NUMBER(10,2),
   ONERIGHT_TURNOVER    NUMBER(10,2),
   TWORIGHT_MAN_PROJECT NVARCHAR2(100),
   TWORIGHT_AREA        NUMBER(10,2),
   TWORIGHT_RENT        NUMBER(10,2),
   TWORIGHT_TURNOVER    NUMBER(10,2),
   SUGGESTION           NVARCHAR2(2000),
   CONSTRAINT PK_BUSINESS_CIRCLE PRIMARY KEY (ID),
   CONSTRAINT FK_TSI_BUSINESSCIRCLE FOREIGN KEY (FORM_ID) REFERENCES TARGET_STORE_INFO (FORM_ID)
);

/*==============================================================*/
/* TABLE: ATTACHMENT                                            */
/*==============================================================*/
CREATE TABLE ATTACHMENT  (
   ID                   INTEGER                         NOT NULL,
   FORM_ID              NVARCHAR2(50)                   NOT NULL,
   FILENAME             NVARCHAR2(100)                  NOT NULL,
   FILETYPE             NVARCHAR2(20)                   NOT NULL,
   FILEURL              NVARCHAR2(200)                  NOT NULL,
   UPLOAD_DATE          TIMESTAMP(14)                   NOT NULL,
   OPERATOR_CODE        NVARCHAR2(10)                   NOT NULL,
   OPERATOR_NAME        NVARCHAR2(50)                   NOT NULL,
   DEPT_CODE            NVARCHAR2(10)                   NOT NULL,
   DEPTMENT_NAME        NVARCHAR2(50)                   NOT NULL,
   CONSTRAINT PK_ATTACHMENT PRIMARY KEY (ID),
   CONSTRAINT FK_TSI_ATTACHMENT FOREIGN KEY (FORM_ID) REFERENCES TARGET_STORE_INFO (FORM_ID)
);

/*==============================================================*/
/* TABLE: TARGET_SHOP_ESTIMATION                                */
/*==============================================================*/
CREATE TABLE TARGET_SHOP_ESTIMATION  (
   ID                   INTEGER                         NOT NULL,
   FORM_ID            	NVARCHAR2(50)                   NOT NULL,
   DEPTMENT_CODE        NVARCHAR2(10)                   NOT NULL,
   DEPTMENT_NAME        NVARCHAR2(100)                  NOT NULL,
   OPERATOR_NAME        NVARCHAR2(100)                  NOT NULL,
   OPERATOR_DN          NVARCHAR2(10)                   NOT NULL,
   OPERATING_DATE       TIMESTAMP(14)                   NOT NULL,
   JUNE_TO_SEPT         NUMBER(10,2),
   ONERENT_OCCUPATION   NUMBER(10,4),
   OCT_TO_MAY           NUMBER(10,2),
   TWORENT_OCCUPATION   NUMBER(10,4),
   CONSTRAINT PK_TARGET_SHOP_ESTIMATION PRIMARY KEY (ID),
   CONSTRAINT FK_TSI_TARGETSHOPESTIMATION FOREIGN KEY (FORM_ID) REFERENCES TARGET_STORE_INFO (FORM_ID)
);

/*==============================================================*/
/* TABLE: APPROVAL_HISTORY                                           */
/*==============================================================*/
CREATE TABLE APPROVAL_HISTORY  (
   ID                   INTEGER                         NOT NULL,
   FORM_ID              NVARCHAR2(50)                   NOT NULL,
   DEPT_CODE            NVARCHAR2(10)                   NOT NULL,
   DEPT_NAME            NVARCHAR2(100)                  NOT NULL,
   APP_RESULT           NVARCHAR2(20)                   NOT NULL,
   REMARK               NVARCHAR2(2000),
   OPERATOR_NAME        NVARCHAR2(100)                  NOT NULL,
   OPERATOR_CODE        NVARCHAR2(10)                   NOT NULL,
   OPERATING_DATE       TIMESTAMP(14)                   NOT NULL,
   OPERATOR_ROLE_CODE   NVARCHAR2(255)                  NOT NULL,
   OPERATOR_ROLENAME    NVARCHAR2(100)                  NOT NULL,
   CONSTRAINT PK_APPROVAL_HISTORY PRIMARY KEY (ID),
   CONSTRAINT FK_TSI_APPROVAL_HISTORY FOREIGN KEY (FORM_ID) REFERENCES TARGET_STORE_INFO (FORM_ID)
);

/*==============================================================*/
/* TABLE: DICTIONARY                                        */
/*==============================================================*/
CREATE TABLE DICTIONARY  (
   ID                   INTEGER                         NOT NULL,
   CODE                 NVARCHAR2(50)                   NOT NULL,
   DESCRIPTION          NVARCHAR2(100)                  NOT NULL,
   DICTIONARY_TYPE      NVARCHAR2(20)                   NOT NULL,
   UPDATE_DATE          TIMESTAMP(14)                   NOT NULL,
   CONSTRAINT PK_DICTIONARY PRIMARY KEY (ID)
);


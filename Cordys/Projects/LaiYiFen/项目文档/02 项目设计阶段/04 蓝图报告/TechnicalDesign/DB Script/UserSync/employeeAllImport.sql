USE [Northwind]
GO
/****** 对象:  Table [dbo].[EMPLOYEE_ALL_IMPORT]    脚本日期: 08/03/2011 13:09:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[EMPLOYEE_ALL_IMPORT](
	[ENGLISHNAME] [varchar](50) COLLATE Chinese_PRC_CI_AS NULL,
	[SN] [varchar](50) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[GIVENNAME] [nvarchar](50) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[EMPLOYEEID] [numeric](8, 0) NOT NULL,
	[CHINESEPINYIN] [varchar](50) COLLATE Chinese_PRC_CI_AS NULL,
	[BIRTHDAY] [varchar](20) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[BIRTHPLACE] [varchar](50) COLLATE Chinese_PRC_CI_AS NULL,
	[SEX] [char](1) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[NATIONALITY] [varchar](50) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[IDENTIFICATION] [varchar](30) COLLATE Chinese_PRC_CI_AS NULL,
	[PASSPORT] [varchar](30) COLLATE Chinese_PRC_CI_AS NULL,
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[HIREDATE] [varchar](20) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[LEAVEDATE] [varchar](20) COLLATE Chinese_PRC_CI_AS NULL,
	[DEPTNAME] [varchar](50) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[DEPTID] [numeric](8, 0) NOT NULL,
	[ORGSHORT] [varchar](20) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[WORKTIME] [varchar](8) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[LYFMAIL] [varchar](250) COLLATE Chinese_PRC_CI_AS NULL,
	[POSTCODE] [varchar](50) COLLATE Chinese_PRC_CI_AS NULL,
	[EMPLOYEETYPE] [char](1) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[MANAGERNUMBER] [numeric](8, 0) NOT NULL,
	[WORKAREA] [varchar](50) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[COSTCENTER] [varchar](50) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[PHONENUMBER] [varchar](30) COLLATE Chinese_PRC_CI_AS NULL,
	[WEBSHORT] [varchar](30) COLLATE Chinese_PRC_CI_AS NULL,
	[PERSONALMAIL] [varchar](250) COLLATE Chinese_PRC_CI_AS NULL,
	[MOBILE] [varchar](30) COLLATE Chinese_PRC_CI_AS NULL,
	[COMPANYCODE] [varchar](4) COLLATE Chinese_PRC_CI_AS NULL,
	[FAXNUMBER] [varchar](30) COLLATE Chinese_PRC_CI_AS NULL,
	[STATION] [varchar](30) COLLATE Chinese_PRC_CI_AS NULL,
	[STATIONCODE] [varchar](8) COLLATE Chinese_PRC_CI_AS NULL,
	[MODIFYACTION] [char](1) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[MODIFYDATE] [varchar](8) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[MODIFYTIME] [varchar](20) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[USEYN] [char](1) COLLATE Chinese_PRC_CI_AS NULL CONSTRAINT [DF_EMPLOYEE_ALL_IMPORT_USEYN]  DEFAULT ('Y'),
 CONSTRAINT [PK_EMPLOYEE_ALL_IMPORT] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'姓（中文）' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'EMPLOYEE_ALL_IMPORT', @level2type=N'COLUMN', @level2name=N'SN'

GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Y:validate; N:invalidate' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'EMPLOYEE_ALL_IMPORT', @level2type=N'COLUMN', @level2name=N'USEYN'

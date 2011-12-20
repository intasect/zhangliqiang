package com.laiyifen.common.util;

public final class LaiYiFenConstants
{
	public static final int _DB_TRANSACTION_TIMEOUT = 20;
	public static final int _TRANSACTION_TIMEOUT = 200000;
	
	/* 
	 * ********* START OF SUPPORTED METHOD TYPES ********************
	 */
	public static final String EXAMPLE = "desired values";
	
	/*
	 * ************ END OF THE  SUPPORTED METHOD TYPES ***************
	 */
	
	/*
	 * START OF  IMPLEMENTATION CLASSES
	 * 
	 */
	public static final String _BMS_IMPLEMENTATION_CLASS = "com.cordys..applicationconnector.ApplicationConnector";
	/*
	 * END OF  IMPLEMENATION CLASSES
	 */
	
	/*
	 * ************** START OF PROCESSOR NAMESPACES *********************
	 */
	public static final String ORG_SERVICE_NAMESPACE = "http://schemas.cordys.com/userassignment/UserAssignmentService/1.0";
	public static final String ORG_READER_NAMESPACE = "http://schemas.cordys.com/userassignment/UserAssignmentReader/1.0";
	public static final String TEMP_NAMESPACE = "http://schemas.laiyifen.com/saprfcs";
	public static final String LYE_DEFAULT_NAMESPACE="http://laiyifen.com/";
	
	public static final String _LDAP_NAMESPACE = "http://schemas.cordys.com/1.0/ldap";
	public static final String _EMAIL_NAMESPACE = "http://schemas.cordys.com/1.0/email";
	public static final String _COBOC_NAMESPACE = "http://schemas.cordys.com/1.0/coboc";
	public static final String _RULE_NAMESPACE = "http://schemas.cordys.com/ruleengine/4.2";
	public static final String _XMLSTORE_NAMESPACE = "http://schemas.cordys.com/1.0/xmlstore";
	public static final String _SCHEDULE_NAMESPACE = "http://schemas.cordys.com/scheduler/1.0";
	public static final String _NOTIFICATION_NAMESPACE = "http://schemas.cordys.com/1.0/notification";
		
	/*
	 * ************** END OF THE PROCESSOR NAMESPACES *******************
	 */

	/*
	 * **************** START OF  SUPPORTED DB TYPES ******************
	 */
	public static final int MSSQL = 1;
	public static final int ORACLE = 2;
	public static final int MYSQL = 3;
	/*
	 * **************** END OF  SUPPORTED DB TYPES ******************
	 */
	
	/*
	 * ***************** START OF THE CONSTANTS USED ******************
	 */
	
	public static final String REPOSITORY_PREFIX = "_OBJECTS_";
	public static final String BUSINESS_PROCSES_ATTRIBUTES = "Business Process";
	public static final String WEB_SERVICE_ATTRIBUTES = "Web Service";
	public static final String _OBJECT_DESCRIPTION = "This business Event Object is inserted by the  framework.";
	/*
	 * ***************** END OF THE CONSTANTS USED ******************
	 */
	
	/*
	 * ****************** START OF THE PATHS USED ****************
	 */
	public static final String _ACTIONABLE_TEMPLATE_PATH = "//monitored_entities/actionable_entities/templates/";

	/*
	 * ****************** END OF THE PATHS USED ****************
	 */

	/*
	 * ****************** START OF THE  ROLES ****************
	 */
	public static final String ROLE__ANALYST = " Analyst";
	public static final String ROLE__DEVELOPER = " Developer";
	public static final String ROLE__ADMINISTRATOR = " Administrator";
	/*
	 * ****************** END OF THE  ROLES ****************
	 */
}

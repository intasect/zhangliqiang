package com.laiyifen.common.pim;
/**
 * For project LaiYifen T-BPM
 * This is a constant class which declares final variables for PimUtil and PimHandler
 * 
 * @author hougang
 *
 */
public class PimConstants {
	public static final String METHODNAME_PIMSUMMARY = "GetProcessInstanceSummary";
	public static final String METHODNAME_PROCESSINSTANCE = "GetProcessInstances";
	public static final String NAMESPACE_QUERYINSTANCEDATA = "http://schemas.cordys.com/pim/queryinstancedata/1.0";
	
	public static final String METHODNAME_QUERYADMINDATA = "QueryAdminData";
	public static final String NAMESPACE_QUERYADMINDATA = "http://schemas.cordys.com/bpm/monitoring/1.0";
	
	public static final String REQUEST_GET_PROCESSINSTANCESUMMARY_FORMID =
		"<Query xmlns=\"http://schemas.cordys.com/cql/1.0\">"+
			"<Select>"+
				"<QueryableObject>InstancesSummary</QueryableObject>"+
				"<Field>ProcessName</Field>"+
				"<Field>ShortProcessName</Field>"+
				"<Field>NrTotal</Field>"+
				"<Field>NrWaiting</Field>"+
				"<Field>NrComplete</Field>"+
			"</Select>"+
			"<Filters>"+
				"<EQ field=\"BusinessIdentifier:Business Identifier/shop/FormID\">"+
					"<Value>$FORMID$</Value>"+
				"</EQ>"+
			"</Filters>"+
		"</Query>";
	
	public static final String REQUEST_GET_PROCESSINSTANCESUMMARY_INSTANCEID =
		"<Query xmlns=\"http://schemas.cordys.com/cql/1.0\">"+
			"<Select>"+
				"<QueryableObject>InstancesSummary</QueryableObject>"+
				"<Field>ProcessName</Field>"+
				"<Field>ShortProcessName</Field>"+
				"<Field>NrTotal</Field>"+
				"<Field>NrWaiting</Field>"+
				"<Field>NrComplete</Field>"+
			"</Select>"+
			"<Filters>"+
				"<EQ field=\"INSTANCE_ID\">"+
					"<Value>$INSTANCEID$</Value>"+
				"</EQ>"+
			"</Filters>"+
		"</Query>";
	
	public static final String REQUEST_GET_PROCESSINSTANCE = 
		"<Query xmlns=\"http://schemas.cordys.com/cql/1.0\">"+
			"<Select>"+
				"<QueryableObject>PROCESS_INSTANCE</QueryableObject>"+
				"<Field>INSTANCE_ID</Field>"+
				"<Field>PROCESS_NAME</Field>"+
				"<Field>DESCRIPTION</Field>"+
				"<Field>START_TIME</Field>"+
				"<Field>END_TIME</Field>"+
				"<Field>STATUS</Field>"+
				"<Field>PROCESS_TYPE</Field>"+
				"<Field>PARENT_ID</Field>"+
				"<Field>PARENT_TYPE</Field>"+
				"<Field>ROOT_ID</Field>"+
				"<Field>ROOT_TYPE</Field>"+
				"<Field>MODEL_SPACE</Field>"+
			"</Select>"+
			"<Filters>"+
				"<And>"+
					"<And>"+
						"<EQ field=\"MODEL_SPACE\">"+
							"<Value>1</Value>"+
						"</EQ>"+
						"<EQ field=\"PROCESS_NAME\">"+
							"<Value>$PROCESSNAME$</Value>"+
						"</EQ>"+
					"</And>"+
					"<EQ field=\"BusinessIdentifier:Business Identifier/shop/FormID\">"+
						"<Value>$FORMID$</Value>"+
					"</EQ>"+
				"</And>"+
			"</Filters>"+
		"</Query>";
	
	public static final String REQUEST_GET_PROCESSINSTANCE_INSTANCEID = 
		"<Query xmlns=\"http://schemas.cordys.com/cql/1.0\">"+
			"<Select>"+
				"<QueryableObject>PROCESS_INSTANCE</QueryableObject>"+
				"<Field>INSTANCE_ID</Field>"+
				"<Field>PROCESS_NAME</Field>"+
				"<Field>DESCRIPTION</Field>"+
				"<Field>START_TIME</Field>"+
				"<Field>END_TIME</Field>"+
				"<Field>STATUS</Field>"+
				"<Field>PROCESS_TYPE</Field>"+
				"<Field>PARENT_ID</Field>"+
				"<Field>PARENT_TYPE</Field>"+
				"<Field>ROOT_ID</Field>"+
				"<Field>ROOT_TYPE</Field>"+
				"<Field>MODEL_SPACE</Field>"+
			"</Select>"+
			"<Filters>"+
				"<And>"+
					"<And>"+
						"<EQ field=\"MODEL_SPACE\">"+
							"<Value>1</Value>"+
						"</EQ>"+
						"<EQ field=\"PROCESS_NAME\">"+
							"<Value>$PROCESSNAME$</Value>"+
						"</EQ>"+
					"</And>"+
					"<EQ field=\"INSTANCE_ID\">"+
						"<Value>$INSTANCEID$</Value>"+
					"</EQ>"+
				"</And>"+
			"</Filters>"+
		"</Query>";
	
	public static final String REQUEST_GET_PROCESSINSTANCE_PARENTID = 
		"<Query xmlns=\"http://schemas.cordys.com/cql/1.0\">"+
			"<Select>"+
				"<QueryableObject>PROCESS_INSTANCE</QueryableObject>"+
				"<Field>INSTANCE_ID</Field>"+
				"<Field>STATUS</Field>"+
			"</Select>"+
			"<Filters>"+
				"<EQ field=\"PARENT_ID\">"+
					"<Value>$PARENTID$</Value>"+
				"</EQ>"+
			"</Filters>"+
		"</Query>";
	
	public static final String SQL_GET_PROCESSACTIVITIES = 
		"<dataset><constructor language=\"DBSQL\"><query>"+
		"select t.instance_id,i.description,t.activity_name,t.status,n.start_date,n.due_date,n.completion_date,p.employee_name task_owner"+
		" from process_activity t, notf_task_instance n,participant_infos p,process_instance i"+
		" where t.message_id = n.task_instance_id"+
		" and t.instance_id = '$instanceID$'"+
		" and n.task_owner = p.participant_id"+
		" and t.instance_id=i.instance_id"+
		" and t.status='WAITING'"+
		"</query><parameters/></constructor></dataset>";
}
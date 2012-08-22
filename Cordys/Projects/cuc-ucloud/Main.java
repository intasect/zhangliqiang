package com.cordys.webservice;

import java.util.List;
import com.cordys.facade.CORDYS;
import com.cordys.objects.CORDYS_PROCESS;
import com.cordys.objects.CORDYS_TASK;
import com.cordys.objects.CORDYS_USER;

public class Main {

	public static void main(String[] args) {
		
		try {
			CORDYS cordys = new CORDYS("ccn", "ccn");
			
			
			//添加用户
			/*CORDYS_USER user = new CORDYS_USER();
			user.setUSER_ID("ccn");
			user.setUSER_NAME("测试帐号");
			user.setPASSWORD("ccn");
			cordys.addUser(user);*/
			
			//发起流程
			/*CORDYS_PROCESS p = new CORDYS_PROCESS();
			p.setPROCESS_NAME("DDNElectricControl");
			p.setTO_USER("ccn");
			System.out.println(cordys.startProcess(p));*/
			
			//查询待办
			List<CORDYS_TASK> list =  cordys.getMyWaitingTasks();
			 
			//提交待办 
			cordys.commitTask(list.get(0).getTASK_ID(), "ccn", true);
			System.out.println(list.get(0).getPROCESS_VIEW_URL());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

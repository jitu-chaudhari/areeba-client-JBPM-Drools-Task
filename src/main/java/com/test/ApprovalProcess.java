package com.test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;

import com.test.service.JBPMService;


public class ApprovalProcess extends Thread {
	
	
		private static JBPMService jbpmService;
		private static String userId = "19000";
	
		
		public ApprovalProcess(String userId)
		{
			this.userId = userId;
			this.start();
		}

		public static void main(String[] args) {
			
			ApprovalProcess approvalProcess = new ApprovalProcess(userId);
			approvalProcess.startTravelRequestProcess(userId);
			
		}
		
		public void startTravelRequestProcess(String userId)
		{
			KieSession ksession = jbpmService.getKieSession();
			Map<String,Object> map =  new HashMap<String,Object>();
			map.put("traveler", "19001");
			map.put("approver1", userId);
			
			System.out.println("Rule params: " + map);
			
			ProcessInstance processInstance = ksession.startProcess("org.test.travelProcess",map);

			long processInstanceId = processInstance.getId();

			System.out.println("Process started ... : processInstanceId = "
					+ processInstanceId);
					
			
			approve1(processInstanceId, true, userId);
			System.out.println("Approver 1 approved");
			
			ksession.dispose();
		
		}
		
		public void approve1(long processInstanceId, boolean action, String userId)
		{
			TaskService taskService = jbpmService.getTaskService();
			List<Long> taskIds = taskService.getTasksByProcessInstanceId(processInstanceId);

			System.out.println("Task Id list:" + taskIds);
			
			if(taskIds != null && taskIds.size()>0)
			{
				long taskId = taskIds.get(0);
				taskService.start(taskId, userId);
				Map<String,Object> map =  new HashMap<String,Object>();
				map.put("isApproved", new Boolean(action));
				taskService.complete(taskId, userId, map);
			}
		}
		
		public void traveler(long processInstanceId)
		{
			TaskService taskService = jbpmService.getTaskService();
			List<Long> taskIds = taskService.getTasksByProcessInstanceId(processInstanceId);
			
			System.out.println("Task Id list:" + taskIds);
			
			if(taskIds != null && taskIds.size()>0)
			{
				long taskId = taskIds.get(1);
				taskService.start(taskId, "19001");
				Map<String,Object> map =  new HashMap<String,Object>();			
				taskService.complete(taskId, "19001",map);
			}
		}	
	
}
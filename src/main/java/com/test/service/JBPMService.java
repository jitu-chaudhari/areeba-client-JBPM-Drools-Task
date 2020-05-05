package com.test.service;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.task.TaskService;
import org.kie.internal.runtime.manager.context.EmptyContext;

public class JBPMService {
	
	RuntimeManager runtimeManager;
	RuntimeManager ruleRuntimeManager;
	
	public KieSession getKieSession()
	{
		System.out.println("runtimeManager: " + runtimeManager);
		 RuntimeEngine runtimeEngine = runtimeManager.getRuntimeEngine(EmptyContext.get());
		 
		 KieSession ksession = runtimeEngine.getKieSession();
		 System.out.println("runtimeEngine: " + runtimeEngine);
		 System.out.println("ksession: " + ksession);
		 return ksession;
	}
	public TaskService getTaskService()
	{
		 RuntimeEngine runtimeEngine = runtimeManager.getRuntimeEngine(EmptyContext.get());
		 
		 TaskService taskService = runtimeEngine.getTaskService();
		 
		 return taskService;
	}
	
	
}
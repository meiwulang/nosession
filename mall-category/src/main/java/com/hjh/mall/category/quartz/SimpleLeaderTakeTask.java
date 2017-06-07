package com.hjh.mall.category.quartz;

import javax.annotation.Resource;

import org.springframework.core.task.TaskExecutor;

public abstract class SimpleLeaderTakeTask {

	@Resource(name = "taskExecutor")
	private TaskExecutor taskExecutor;

	public void execute() {
		taskExecutor.execute(new Runnable() {
			public void run() {
				doTask();
			}
		});
	}

	public abstract void doTask();

}

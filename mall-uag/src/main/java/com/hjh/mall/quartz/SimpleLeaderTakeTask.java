package com.hjh.mall.quartz;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;

public abstract class SimpleLeaderTakeTask {

	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleLeaderTakeTask.class);

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

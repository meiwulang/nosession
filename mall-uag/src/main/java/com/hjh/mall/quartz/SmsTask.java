package com.hjh.mall.quartz;

/**
 * @author chenshijun
 *
 */
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hjh.mall.service.VerifyMsgService;

@Component
public class SmsTask extends SimpleLeaderTakeTask {

	private static final Logger LOGGER = LoggerFactory.getLogger(SmsTask.class);
	@Resource
	private VerifyMsgService verifyMsgService;
	private boolean isTaskenable;

	public boolean isTaskenable() {
		return isTaskenable;
	}

	@Value("${task.sms.execute.enable}")
	public void setTaskenable(boolean isTaskenable) {
		this.isTaskenable = isTaskenable;
	}

	@Override
	public void doTask() {
		if (isTaskenable) {
			LOGGER.info("sms task start");
		}
		verifyMsgService.deleteAllMessage();
		LOGGER.info("sms task end");
	}

}
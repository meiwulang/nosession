package com.hjh.mall.service;

import java.util.Map;

import com.hjh.mall.common.sms.base.service.SmsMessageServiceBase;
import com.hjh.mall.common.sms.entity.SendMessageEntity;

public interface SmsSendMessageService extends
		SmsMessageServiceBase<Map<String,Object>> {
	
	Map<String,Object> sendOrderMessage(SendMessageEntity<Map<String,Object>> sendMessageEntity);
		

}

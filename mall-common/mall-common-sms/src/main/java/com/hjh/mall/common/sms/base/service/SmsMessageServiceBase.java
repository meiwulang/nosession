package com.hjh.mall.common.sms.base.service;

import java.util.Map;

import com.hjh.mall.common.sms.entity.SendMessageEntity;

public interface SmsMessageServiceBase<T extends Map<String, Object>> {

	Map<String, Object> sendMessage(SendMessageEntity<T> t);


}

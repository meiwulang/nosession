package com.hjh.mall.util;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hjh.mall.common.sms.base.SmsMessageBaseImpl;
import com.hjh.mall.common.sms.client.SmsClient;
import com.hjh.mall.common.sms.entity.SendMessageEntity;
import com.hjh.mall.service.SmsSendMessageService;
import com.taobao.api.TaobaoClient;


public class SmsMessageImpl extends SmsMessageBaseImpl<Map<String, Object>>
		implements SmsSendMessageService {

	@Value("${sms_sign}")
	private String defulesgin;

	@Resource
	public SmsClient smsClient;

	@Override
	public TaobaoClient getSmsClient() {
		return smsClient.getSmsClient();
	}
	
	@Override
	public String getSgin() {
		// TODO Auto-generated method stub
		return defulesgin;
	}

	@Override
	public Map<String, Object> sendOrderMessage(
			SendMessageEntity<Map<String, Object>> sendMessageEntity) {
		return super.sendMessage(sendMessageEntity);
	}



}

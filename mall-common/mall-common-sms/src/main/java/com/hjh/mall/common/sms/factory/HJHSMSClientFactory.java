package com.hjh.mall.common.sms.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hjh.mall.common.sms.client.SmsClient;
import com.hjh.mall.common.sms.config.SmsConfig;




public class HJHSMSClientFactory {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HJHSMSClientFactory.class);

	public static SmsClient getTaobaoClient(SmsConfig smsConfig) {
		if (null == smsConfig) {
			LOGGER.error("smsConfig is null ");
			return null;
		}		
		return new SmsClient(smsConfig);
	}

}

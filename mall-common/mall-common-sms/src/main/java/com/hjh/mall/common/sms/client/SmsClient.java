package com.hjh.mall.common.sms.client;

import com.hjh.mall.common.sms.config.SmsConfig;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;

public class SmsClient {

	public static TaobaoClient smsClient;

	public TaobaoClient getSmsClient() {
		return smsClient;
	}

	public SmsClient(SmsConfig smsConfig) {
//		if (StringUtil.isNotBlank(smsConfig.getProxyHost())
//				&& StringUtil.isNotBlank(smsConfig.getProxyPort())) {
//			System.getProperties().setProperty("http.proxyHost",
//					smsConfig.getProxyHost());
//			System.getProperties().setProperty("http.proxyPort",
//					smsConfig.getProxyPort());
//		}
		smsClient = new DefaultTaobaoClient(smsConfig.getUrl(),
				smsConfig.getAppkey(), smsConfig.getSecret());

	}
	


}

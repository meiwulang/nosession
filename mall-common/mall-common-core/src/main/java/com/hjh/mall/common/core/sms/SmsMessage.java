package com.hjh.mall.common.core.sms;

/**
 * @author chenshijun 2016年4月18日
 */

public interface SmsMessage {
	String SendSmsMessage(String mobile, String verifyMsg, String stime);
}

package com.hjh.mall.common.sms.entity;

import java.util.Map;


/**
 * @author a
 *
 */
public class SendMessageEntity<T extends Map<String, Object>> {
	

	/**
	 * 手机号
	 */
	private String mobile;
	
	/**
	 * 模板编号
	 */
	private String temp;
	
	/**
	 * 自定义签名//默认签名【好机惠】
	 */
	private String sign;
	
	/**
	 * 短信类型 文字语音等
	 */
	private SmsType smsType=SmsType.NORMAL;
	
	/**
	 * 消息模板对应替换键值对，必须指定key和value来替换模板当中当中的值 value替换模板当中的key值
	 * 例子 xxxx${key1}xxxx${key2}...
	 */
	private T message;
	
	public SmsType getSmsType() {
		return smsType;
	}

	public void setSmsType(SmsType smsType) {
		this.smsType = smsType;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public T getMessage() {
		return message;
	}

	public void setMessage(T message) {
		this.message = message;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SendMessageEntity [mobile=").append(mobile)
				.append(", temp=").append(temp).append(", sign=").append(sign)
				.append(", smsType=").append(smsType).append(", message=")
				.append(message).append("]");
		return builder.toString();
	}



	
	

}

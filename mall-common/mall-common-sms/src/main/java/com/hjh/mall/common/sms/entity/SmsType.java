package com.hjh.mall.common.sms.entity;

//短信发送类型
public enum SmsType {
	
	//发送文字类型短信
	NORMAL("normal","文字短信");

	private final String val;

	private final String description;
	

	private String toString;

	private SmsType(String val, String description) {
		this.val = val;
		this.description = description;

	}

	public String getVal() {
		return val;
	}
	public String getDescription() {
		return description;
	}

	
	

	@Override
	public String toString() {
		if (null == toString) {
			toString = new StringBuilder().append("SmsType[").append(val).append(':').append(description)
					.append(']').toString();
		}
		return toString;
	}

}

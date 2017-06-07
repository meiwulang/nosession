package com.hjh.mall.field.type;
/**
 * 
 * @date 2016年8月3日 
 * 
 * @author 曾繁林 
 * 
 * @Description: 是否强制更新（0：选择更新，1：强制更新）  
 * 
 *
 */
public enum AppUpdateType {
	
	FORCE_UPDAT("1", "强制更新"),
	
	CHOICE_UPDAT("0", "选择更新"),

	;

	private final String val;

	private final String description;

	private String toString;

	private AppUpdateType(String val, String description) {
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
			toString = new StringBuilder().append("AppUpdateType[").append(val).append(':').append(description)
					.append(']').toString();
		}
		return toString;
	}
}

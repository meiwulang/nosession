package com.hjh.mall.field.type;

/**
 * @Project: hjy-filed
 * @Description 更新安装包类型(0:安卓，1：ios)
 * @author 王斌
 * @date 2016年7月26日
 * @version V1.0
 */
public enum AppType {
	/**
	 * 安卓
	 */
	ANDROID("0", "Android"),
	/**
	 * 苹果
	 */
	APPLE("1", "iOS"),

	;

	private final String val;

	private final String description;

	private String toString;

	private AppType(String val, String description) {
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
			toString = new StringBuilder().append("AppNameType[").append(val).append(':').append(description)
					.append(']').toString();
		}
		return toString;
	}

}

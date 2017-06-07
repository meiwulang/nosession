package com.hjh.mall.field.type;

/**
 * @Project: hjy-filed
 * @Description app类型
 * @author 王斌
 * @date 2016年7月26日
 * @version V1.0
 */
public enum AppNameType {
	/**
	 * 全知道
	 */
	ALLKNOWN("1", "全知道"),
	/**
	 * 集客
	 */
	JIKE("0", "集客"),
	/**
	 * 机惠多
	 */
	HJH_MALL("2", "机惠多");
	private final String description;

	private String toString;

	private final String val;

	private AppNameType(String val, String description) {
		this.val = val;
		this.description = description;
	}

	public String getVal() {
		return val;
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

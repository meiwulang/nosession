package com.hjh.mall.field.type;

/**
 * @Project: hjy-filed
 * @Description 是否强制更新 (0：不强制，1：强制)
 * @author 王斌
 * @date 2016年7月26日
 * @version V1.0
 */
public enum ForceUpdate {
	/**
	 * 不强制
	 */
	NOT_FORCE_UPDATE("0", "不强制"),
	/**
	 * 强制
	 */
	FORCE_UPDATE("1", "强制"),

	;

	private final String val;

	private final String description;

	private String toString;

	private ForceUpdate(String val, String description) {
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
			toString = new StringBuilder().append("DictionaryType[").append(val).append(':').append(description)
					.append(']').toString();
		}
		return toString;
	}

}

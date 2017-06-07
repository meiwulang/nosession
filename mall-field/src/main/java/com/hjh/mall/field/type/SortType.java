package com.hjh.mall.field.type;


/**
 * @Project: mall-field
 * @Description 排序类型
 * @author 李慧峰
 * @date 2017年4月5日
 * @version V1.0 
 */
public enum SortType {
	/**
	 * 拼音排序
	 */
	PINYINASC("1", "pinyinasc"),
	/**
	 * 日期倒序
	 */
	UPDATETIMEDESC("2", "updatetime_desc"),

	;

	private final String val;

	private final String description;

	private String toString;

	private SortType(String val, String description) {
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
			toString = new StringBuilder().append("SortType[").append(val).append(':').append(description)
					.append(']').toString();
		}
		return toString;
	}

}

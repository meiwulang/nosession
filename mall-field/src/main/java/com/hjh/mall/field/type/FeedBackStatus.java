package com.hjh.mall.field.type;

public enum FeedBackStatus {
	
	/**
	 * 删除
	 */
	DISENABLED("0", "删除"),

	/**
	 * 启用
	 */
	ENABLED("1", "初始值"),
	
	/**
	 * 已回复
	 */
	REPLYED("2", " 已回复"),

	;

	private final String val;

	private final String description;

	private String toString;

	private FeedBackStatus(String val, String description) {
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
			toString = new StringBuilder().append("Status[").append(val).append(':').append(description)
					.append(']').toString();
		}
		return toString;
	}

}

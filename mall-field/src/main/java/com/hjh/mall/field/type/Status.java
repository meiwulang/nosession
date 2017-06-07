package com.hjh.mall.field.type;

public enum Status {
	
	/**
	 * 未启用
	 */
	DISENABLED("0", "未启用"),

	/**
	 * 启用
	 */
	ENABLED("1", "启用"),

	;

	private final String val;

	private final String description;

	private String toString;

	private Status(String val, String description) {
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

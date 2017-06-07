package com.hjh.mall.field.type;

public enum FeedBackType {
	
	/**
	 * 产品建议
	 */
	PRODUCTPROPOSAL("0", "产品建议"),

	/**
	 * 程序问题
	 */
	program("1", "程序问题"),
	
	/**
	 * 其他
	 */
	OTHER("2", "其他"),

	;

	private final String val;

	private final String description;

	private String toString;

	private FeedBackType(String val, String description) {
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

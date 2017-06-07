package com.hjh.mall.field.type;

public enum FeedBackSource {
	
	/**
	 * 集客
	 */
	JIKE("0", "集客"),

	/**
	 * 全知道
	 */
	ALLKNOWN("1", "全知道"),
	
	/**
	 * 机惠多
	 */
	JIHUIDUO("2", "机惠多")

	;

	private final String val;

	private final String description;

	private String toString;

	private FeedBackSource(String val, String description) {
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

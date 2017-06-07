package com.hjh.mall.type;

/**
 * @Project: hjh-filed
 * @Description TODO
 * @author 曾繁林
 * @date 2017年2月14日
 * @version V1.0
 */
public enum BannerContentType {

	/**
	 * 商家
	 */
	CUSTOMER("customer", "商家"),

	/**
	 * 机友会
	 */
	TOPIC("topic", "机友会"),

	/**
	 * 优惠活动
	 */
	ACTIVITY("activity", "优惠活动"),

	/**
	 * 新闻
	 */
	NEWS("news", "新闻"),

	/**
	 * 其他
	 */
	OTHER("other", "其他")

	;

	private final String val;

	private final String description;

	private String toString;

	private BannerContentType(String val, String description) {
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
			toString = new StringBuilder().append("SexType[").append(val).append(':').append(description).append(']')
					.toString();
		}
		return toString;
	}
}

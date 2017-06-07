package com.hjh.mall.field.enums;

/**
 * @Project: mall-es
 * @Description 搜索引擎 ，排序
 * @author 杨益桦
 * @date 2017年3月17日
 * @version V1.0
 */
public enum SolrSortFields {

	/**
	 * 默认
	 */
	DEFAULT("1", "update_date,desc"),
	/**
	 * 价格正序
	 */
	PRICE_ASC("2", "min_price,asc"),

	/**
	 * 价格倒叙
	 */
	PRICE_DESC("3", "min_price,desc"),
	/**
	 * 指定排序 正序
	 */
	SORT_ASC("4", "update_date,desc"),
	/**
	 * 指定排序 倒序
	 */
	SORT_DESC("5", "update_date,desc"),
	/**
	 * 销量正序
	 */
	SALE_NUM_ASC("6", "sale_num,asc"),
	/**
	 * 销量倒序
	 */
	SALE_NUM_DESC("7", "sale_num,desc");

	private final String val;

	private final String description;

	private String toString;

	private SolrSortFields(String val, String description) {
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
			toString = new StringBuilder().append("SolrSortFields[").append(val).append(':').append(description)
					.append(']').toString();
		}
		return toString;
	}
}

package com.hjh.mall.field.enums;

/**
 * @Project: mall-es
 * @Description 搜索引擎 ，排序
 * @author 杨益桦
 * @date 2017年3月17日
 * @version V1.0
 */
public enum SolrFactesFields {

	/**
	 * 品牌
	 */
	BRAND_NAME("1", "brand_name"),
	/**
	 * 机型
	 */
	CAR_MODELS_NAME("2", "car_models_name"),

	/**
	 * 二级类目
	 */
	SECOND_CATEGORY_NAME("3", "second_category_name"),
	/**
	 * 三级类目
	 */
	THIRD_CATEGORY_NAME("4", "third_category_name"),
	/**
	 * 二级类目和品牌名称
	 */
	SECOND_BRAND("5", "second_category_name,brand_name");

	private final String val;

	private final String description;

	private String toString;

	private SolrFactesFields(String val, String description) {
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
			toString = new StringBuilder().append("SolrFactesFields[").append(val).append(':').append(description)
					.append(']').toString();
		}
		return toString;
	}
}

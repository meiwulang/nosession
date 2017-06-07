package com.hjh.mall.field.type;

/**
 * @Project: mall-field
 * @Description 支付类型
 * @author 杨益桦
 * @date 2017年5月17日
 * @version V1.0 
 */
public enum PayType {
	ALL(0, "全款"),
	PART(1, "预付款");
	private final String description;

	private String toString;

	private final Integer val;

	private PayType(Integer val, String description) {
		this.val = val;
		this.description = description;
	}

	public Integer getVal() {
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

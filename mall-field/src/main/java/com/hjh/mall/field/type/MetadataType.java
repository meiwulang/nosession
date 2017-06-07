package com.hjh.mall.field.type;

/**
 * @Project:mall-filed
 * @Description 元数据类型
 * @author zfl
 * @date 2017年3月15日
 * @version V1.0
 */
public enum MetadataType {
	/**
	 * 计量单位
	 */
	UNIT(0, "unit"),
	/**
	 * 机型类型
	 */
	MACHINE_TYPE(1, "machine_type"),
	
	/**
	 * 售后电话
	 */
	AFTER_SALE_CALL(2, "after_sale_call"),

	;

	private final int val;

	private final String description;

	private String toString;

	private MetadataType(int val, String description) {
		this.val = val;
		this.description = description;
	}

	public int getVal() {
		return val;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		if (null == toString) {
			toString = new StringBuilder().append("MetadataType[").append(val).append(':').append(description)
					.append(']').toString();
		}
		return toString;
	}

}

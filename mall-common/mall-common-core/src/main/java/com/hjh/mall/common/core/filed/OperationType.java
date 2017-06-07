package com.hjh.mall.common.core.filed;

/**
 * @Project: hjy-filed
 * @Description 操作类型
 * @author 王斌
 * @date 2016年12月13日
 * @version V1.0
 */
public enum OperationType {
	CHANGE_STATUS("0", "修改状态"), CHANGE_PWD("1", "修改密码"), RESET_PWD("2", "重置密码"), ADD("3", "添加账号");

	private final String val;

	private final String description;

	private String toString;

	private OperationType(String val, String description) {
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
			toString = new StringBuilder().append("OperationType[").append(val).append(':').append(description)
					.append(']').toString();
		}
		return toString;
	}

}

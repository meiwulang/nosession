package com.hjh.mall.field.type;

/**
 * @Project: hjy-filed
 * @Description 版本状态（0已失效，1生效中）
 * @author 曾繁林
 * @date 2016年8月3日
 * @version V1.0
 */
public enum VersionStatusType {

	IN_EFFECT("1", "生效中"),
	
	EXPIRED("0", "已失效"),

	;

	private final String val;

	private final String description;

	private String toString;

	private VersionStatusType(String val, String description) {
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
			toString = new StringBuilder().append("VersionStatusType[").append(val).append(':').append(description)
					.append(']').toString();
		}
		return toString;
	}

}

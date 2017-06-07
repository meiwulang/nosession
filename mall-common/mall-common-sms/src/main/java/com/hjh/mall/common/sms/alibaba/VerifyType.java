package com.hjh.mall.common.sms.alibaba;

public enum VerifyType {

	/**
	 * 1 登录 2注册 3修改密码 4修改个人信息 5身份验证 登录
	 */
	LOGIN("1", "登录"),

	/**
	 * 注册
	 */
	REGIST("2", "注册"),

	/**
	 * 重置密码
	 */
	UPDATEPWD("3", "重置密码"),

	/**
	 * 修改个人信息
	 */
	UPDATEINFO("4", "修改个人信息"),

	/**
	 * 身份验证
	 */
	IDENTITY_VERIFICATION("5", "身份验证"),

	;

	private final String val;

	private final String description;

	private String toString;

	private VerifyType(String val, String description) {
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
			toString = new StringBuilder().append("VerifyType[").append(val).append(':').append(description).append(']')
					.toString();
		}
		return toString;
	}

}

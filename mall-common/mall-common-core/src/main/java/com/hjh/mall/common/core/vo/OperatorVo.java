package com.hjh.mall.common.core.vo;

/**
 * @Project: hjy-common
 * @Description 保存操作人的vo
 * @author 杨益桦
 * @date 2017年3月15日
 * @version V1.0
 */
public class OperatorVo extends HJYVO {

	private static final long serialVersionUID = 1L;

	/**
	 * 操作人id
	 */
	private String hjy_operator_id;
	/**
	 * 操作人名称
	 */
	private String hjy_operator_name;

	public String getHjy_operator_id() {
		return hjy_operator_id;
	}

	public void setHjy_operator_id(String hjy_operator_id) {
		this.hjy_operator_id = hjy_operator_id;
	}

	public String getHjy_operator_name() {
		return hjy_operator_name;
	}

	public void setHjy_operator_name(String hjy_operator_name) {
		this.hjy_operator_name = hjy_operator_name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OperatorVo [hjy_operator_id=");
		builder.append(hjy_operator_id);
		builder.append(", hjy_operator_name=");
		builder.append(hjy_operator_name);
		builder.append(", access_token=");
		builder.append(access_token);
		builder.append(", functionId=");
		builder.append(functionId);
		builder.append("]");
		return builder.toString();
	}

}

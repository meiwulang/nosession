package com.hjh.mall.vo.operator;

import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;

import io.swagger.annotations.ApiModelProperty;

public class OperatorVo extends HJYVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotBlank
	@ApiModelProperty(notes="管理员姓名")
	private String operator_name;
	@NotBlank
	@ApiModelProperty(notes="密码")
	private String password;

	public String getOperator_name() {
		return operator_name;
	}

	public void setOperator_name(String operator_name) {
		this.operator_name = operator_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OperatorVo [operator_name=");
		builder.append(operator_name);
		builder.append(", password=");
		builder.append(password);
		builder.append("]");
		return builder.toString();
	}
	
	

}

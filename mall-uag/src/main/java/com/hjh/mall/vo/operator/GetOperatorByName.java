package com.hjh.mall.vo.operator;

import com.hjh.mall.common.core.vo.HJYVO;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: hjy-middle
 * @Description TODO
 * @author 曾繁林
 * @date 2017年1月18日
 * @version V1.0
 */
public class GetOperatorByName extends HJYVO {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty(notes="管理员姓名")
	private String operator_name;

	public String getOperator_name() {
		return operator_name;
	}

	public void setOperator_name(String operator_name) {
		this.operator_name = operator_name;
	}

}

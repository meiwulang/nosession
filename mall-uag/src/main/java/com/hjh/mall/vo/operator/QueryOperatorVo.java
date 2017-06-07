package com.hjh.mall.vo.operator;

import com.hjh.mall.common.core.vo.PageQueryVO;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: hjy-middle
 * @Description 修改管理员vo
 * @author 王斌
 * @date 2016年12月7日
 * @version V1.0
 */
public class QueryOperatorVo extends PageQueryVO {
	private static final long serialVersionUID = 1L;

	private String operator_id;
	@ApiModelProperty(notes="管理员姓名")
	private String operator_name;
	@ApiModelProperty(notes="管理员状态")
	private String status;

	public String getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getOperator_name() {
		return operator_name;
	}

	public void setOperator_name(String operator_name) {
		this.operator_name = operator_name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "QueryOperatorVo [operator_id=" + operator_id + ", operator_name=" + operator_name + ", status=" + status
				+ "]";
	}

}

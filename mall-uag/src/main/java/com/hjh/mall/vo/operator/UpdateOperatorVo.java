package com.hjh.mall.vo.operator;

import org.apache.curator.framework.api.transaction.OperationType;

import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: hjy-middle
 * @Description 修改管理员vo
 * @author 王斌
 * @date 2016年12月7日
 * @version V1.0
 */
public class UpdateOperatorVo extends HJYVO {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(notes="管理员id")
	private String operator_id;
	@ApiModelProperty(notes="管理员姓名")
	private String operator_name;
	@ApiModelProperty(notes="密码")
	private String password;
	private Long update_version;

	private String update_user;
	@ApiModelProperty(notes="备注")
	private String remark;
	private String status;
	@NotBlank
	@EnumValue(enumClass = OperationType.class)
	private String operate_type;

	public String getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOperator_name() {
		return operator_name;
	}

	public void setOperator_name(String operator_name) {
		this.operator_name = operator_name;
	}

	public Long getUpdate_version() {
		return update_version;
	}

	public void setUpdate_version(Long update_version) {
		this.update_version = update_version;
	}

	public String getUpdate_user() {
		return update_user;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperate_type() {
		return operate_type;
	}

	public void setOperate_type(String operate_type) {
		this.operate_type = operate_type;
	}

	@Override
	public String toString() {
		return "UpdateOperatorVo [operator_id=" + operator_id + ", operator_name=" + operator_name + ", password="
				+ password + ", update_version=" + update_version + ", update_user=" + update_user + ", remark="
				+ remark + ", status=" + status + ", operate_type=" + operate_type + "]";
	}

}

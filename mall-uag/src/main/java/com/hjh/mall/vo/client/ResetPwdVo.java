/**
 *
 */
package com.hjh.mall.vo.client;

import com.hjh.mall.common.core.vo.HJYVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: hjh-user-api
 * @Description 重置密码
 * @author 曾繁林
 * @date 2016年9月18日
 * @version V1.0
 */
@ApiModel
public class ResetPwdVo extends HJYVO {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty("用户编号")
	private String client_id;
	@ApiModelProperty("电话")
	private String mobile_tel;

	public String getMobile_tel() {
		return mobile_tel;
	}

	public void setMobile_tel(String mobile_tel) {
		this.mobile_tel = mobile_tel;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResetPwdVo [client_id=");
		builder.append(client_id);
		builder.append(", mobile_tel=");
		builder.append(mobile_tel);
		builder.append("]");
		return builder.toString();
	}

}

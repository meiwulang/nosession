/**
 *
 */
package com.hjh.mall.vo.client;

import com.hjh.mall.common.core.annotation.Mobile;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: hjy-api
 * @Description 用户登录
 * @author 曾繁林
 * @date 2016年6月29日
 * @version V1.0
 */
@ApiModel
public class ClientLogin extends HJYVO {
	private static final long serialVersionUID = 1L;
	@NotBlank
	@Mobile
	@ApiModelProperty("电话")
	private String mobile_tel;
	@NotBlank
	@ApiModelProperty("密码")
	private String password;

	public String getMobile_tel() {
		return mobile_tel;
	}

	public void setMobile_tel(String mobile_tel) {
		this.mobile_tel = mobile_tel;
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
		builder.append("UserLogin [mobile_tel=");
		builder.append(mobile_tel);
		builder.append(", password=");
		builder.append(password);
		builder.append("]");
		return builder.toString();
	}
}

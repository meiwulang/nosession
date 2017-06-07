/**
 *
 */
package com.hjh.mall.vo.client;

import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: hjy-api
 * @Description 更改手机时校验密码
 * @author 曾繁林
 * @date 2016年7月14日
 * @version V1.0
 */
@ApiModel
public class CheckPwd extends HJYVO {

	private static final long serialVersionUID = 1L;
	@NotBlank
	@ApiModelProperty("密码 32位md5字符")
	private String password;// 密码

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CheckPwd [password=");
		builder.append(password);
		builder.append("]");
		return builder.toString();
	}

}

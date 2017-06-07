/**
 *
 */
package com.hjh.mall.vo.client;

import com.hjh.mall.annotation.Md5;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.annotation.Password;
import com.hjh.mall.common.core.vo.HJYVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: hjy-api
 * @Description 用户修改密码
 * @author 曾繁林
 * @date 2016年6月29日
 * @version V1.0
 */
@ApiModel
public class UpdateClientPwd extends HJYVO {

	private static final long serialVersionUID = 1L;
	// 老密码MD5加密，新密码和验证密码不MD5加密
	@NotBlank
	@Md5
	@ApiModelProperty("32位小写md5加密字符")
	private String password_old;// 旧密码
	@Password
	@NotBlank
	@ApiModelProperty("确认密码")
	private String password_verify;// 确认密码
	@Password
	@NotBlank
	@ApiModelProperty("密码")
	private String password;// 新密码

	public String getPassword_old() {
		return password_old;
	}

	public void setPassword_old(String password_old) {
		this.password_old = password_old;
	}

	public String getPassword_verify() {
		return password_verify;
	}

	public void setPassword_verify(String password_verify) {
		this.password_verify = password_verify;
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
		builder.append("UpdateClientPwd [password_old=");
		builder.append(password_old);
		builder.append(", password_verify=");
		builder.append(password_verify);
		builder.append(", password=");
		builder.append(password);
		builder.append("]");
		return builder.toString();
	}

}

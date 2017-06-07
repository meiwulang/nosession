/**
 *
 */
package com.hjh.mall.vo.client;

import com.hjh.mall.common.core.annotation.Length;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: hjy-api
 * @Description 修改用户昵称
 * @author 曾繁林
 * @date 2016年8月10日
 * @version V1.0
 */
@ApiModel
public class UpdateClientName extends HJYVO {

	private static final long serialVersionUID = 1L;
	@NotBlank
	@Length(min = 1, max = 10)
	@ApiModelProperty("用户名")
	private String nick_name;// 用户名

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UpdateClientName [nick_name=");
		builder.append(nick_name);
		builder.append("]");
		return builder.toString();
	}
}

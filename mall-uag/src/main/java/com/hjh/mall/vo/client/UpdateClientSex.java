/**
 *
 */
package com.hjh.mall.vo.client;

import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.type.SexType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: hjy-api
 * @Description 修改用户性别
 * @author 曾繁林
 * @date 2016年8月10日
 * @version V1.0
 */
@ApiModel
public class UpdateClientSex extends HJYVO {

	private static final long serialVersionUID = 1L;
	@NotBlank
	@EnumValue(enumClass = SexType.class)
	@ApiModelProperty("用户性别")
	private String sex;// 用户性别

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UpdateClientSex [sex=");
		builder.append(sex);
		builder.append("]");
		return builder.toString();
	}

}

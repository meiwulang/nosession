/**
 *
 */
package com.hjh.mall.vo.client;

import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.common.core.annotation.Mobile;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.common.sms.alibaba.VerifyType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: hjy-api
 * @Description 获取验证码
 * @author 曾繁林
 * @date 2016年6月29日
 * @version V1.0
 */
@ApiModel
public class GetVerify extends HJYVO {
	private static final long serialVersionUID = 1L;

	@NotBlank
	@ApiModelProperty("验证码类型")
	@EnumValue(enumClass = VerifyType.class)
	private String authmsg_busi_type;// 验证码类型
	@Mobile
	@NotBlank
	@ApiModelProperty("电话")
	private String mobile_tel;// 电话

	public String getAuthmsg_busi_type() {
		return authmsg_busi_type;
	}

	public void setAuthmsg_busi_type(String authmsg_busi_type) {
		this.authmsg_busi_type = authmsg_busi_type;
	}

	public String getMobile_tel() {
		return mobile_tel;
	}

	public void setMobile_tel(String mobile_tel) {
		this.mobile_tel = mobile_tel;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GetVerify [authmsg_busi_type=");
		builder.append(authmsg_busi_type);
		builder.append(", mobile_tel=");
		builder.append(mobile_tel);
		builder.append("]");
		return builder.toString();
	}

}

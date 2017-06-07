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
 * @Description 校验验证码
 * @author 曾繁林
 * @date 2016年7月13日
 * @version V1.0
 */
@ApiModel
public class CheckVerify extends HJYVO {

	private static final long serialVersionUID = 1L;
	@NotBlank
	@EnumValue(enumClass = VerifyType.class)
	@ApiModelProperty("验证码类型")
	private String authmsg_busi_type;// 验证码类型
	@NotBlank
	@ApiModelProperty("验证码")
	private String verify_msg;// 验证码
	@NotBlank
	@ApiModelProperty("验证码ID")
	private String verify_msg_id;// 验证码ID
	@Mobile
	@NotBlank
	@ApiModelProperty("手机号码")
	private String mobile_tel;// 手机号码

	public String getAuthmsg_busi_type() {
		return authmsg_busi_type;
	}

	public void setAuthmsg_busi_type(String authmsg_busi_type) {
		this.authmsg_busi_type = authmsg_busi_type;
	}

	public String getVerify_msg() {
		return verify_msg;
	}

	public void setVerify_msg(String verify_msg) {
		this.verify_msg = verify_msg;
	}

	public String getVerify_msg_id() {
		return verify_msg_id;
	}

	public void setVerify_msg_id(String verify_msg_id) {
		this.verify_msg_id = verify_msg_id;
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
		builder.append("CheckVerify [authmsg_busi_type=");
		builder.append(authmsg_busi_type);
		builder.append(", verify_msg=");
		builder.append(verify_msg);
		builder.append(", verify_msg_id=");
		builder.append(verify_msg_id);
		builder.append(", mobile_tel=");
		builder.append(mobile_tel);
		builder.append("]");
		return builder.toString();
	}
}

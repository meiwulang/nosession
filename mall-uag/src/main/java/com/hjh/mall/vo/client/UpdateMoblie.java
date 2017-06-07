/**
 *
 */
package com.hjh.mall.vo.client;

import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.common.core.annotation.Length;
import com.hjh.mall.common.core.annotation.Mobile;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.common.sms.alibaba.VerifyType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: hjy-api
 * @Description TODO
 * @author 曾繁林
 * @date 2016年7月14日
 * @version V1.0
 */
@ApiModel
public class UpdateMoblie extends HJYVO {

	private static final long serialVersionUID = 1L;
	@Mobile
	@NotBlank
	@ApiModelProperty("电话")
	private String mobile_tel;
	@NotBlank
	@EnumValue(enumClass = VerifyType.class)
	@ApiModelProperty("4：修改个人信息")
	private String authmsg_busi_type;
	@NotBlank
	@Length(max = 16)
	@ApiModelProperty("验证码编号")
	private String verify_msg_id;
	@NotBlank
	@Length(max = 4)
	@ApiModelProperty("验证码")
	private String verify_msg;
	@NotBlank
	@ApiModelProperty("密码")
	private String password;

	public String getMobile_tel() {
		return mobile_tel;
	}

	public void setMobile_tel(String mobile_tel) {
		this.mobile_tel = mobile_tel;
	}

	public String getAuthmsg_busi_type() {
		return authmsg_busi_type;
	}

	public void setAuthmsg_busi_type(String authmsg_busi_type) {
		this.authmsg_busi_type = authmsg_busi_type;
	}

	public String getVerify_msg_id() {
		return verify_msg_id;
	}

	public void setVerify_msg_id(String verify_msg_id) {
		this.verify_msg_id = verify_msg_id;
	}

	public String getVerify_msg() {
		return verify_msg;
	}

	public void setVerify_msg(String verify_msg) {
		this.verify_msg = verify_msg;
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
		builder.append("UpdateMoblie [mobile_tel=");
		builder.append(mobile_tel);
		builder.append(", authmsg_busi_type=");
		builder.append(authmsg_busi_type);
		builder.append(", verify_msg_id=");
		builder.append(verify_msg_id);
		builder.append(", verify_msg=");
		builder.append(verify_msg);
		builder.append(", password=");
		builder.append(password);
		builder.append("]");
		return builder.toString();
	}

}

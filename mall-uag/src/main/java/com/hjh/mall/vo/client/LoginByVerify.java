/**
 * 
 */
package com.hjh.mall.vo.client;

import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.common.core.annotation.Mobile;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.common.sms.alibaba.VerifyType;

/**
 * @Project: hjy-api
 * @Description 验证码登录
 * @author 曾繁林
 * @date 2016年7月14日
 * @version V1.0
 */
public class LoginByVerify extends HJYVO {

	private static final long serialVersionUID = 1L;
	@NotBlank
	@Mobile
	private String mobile_tel;// 电话
	@NotBlank
	@EnumValue(enumClass = VerifyType.class)
	private String authmsg_busi_type;// 验证码类型
	@NotBlank
	private String verify_msg;// 验证码
	@NotBlank
	private String verify_msg_id;// 验证码ID

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoginByVerify [mobile_tel=");
		builder.append(mobile_tel);
		builder.append(", authmsg_busi_type=");
		builder.append(authmsg_busi_type);
		builder.append(", verify_msg=");
		builder.append(verify_msg);
		builder.append(", verify_msg_id=");
		builder.append(verify_msg_id);
		builder.append("]");
		return builder.toString();
	}

}

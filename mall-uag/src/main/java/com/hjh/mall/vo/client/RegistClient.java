/**
 *
 */
package com.hjh.mall.vo.client;

import com.hjh.mall.annotation.MobileOrTel;
import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.common.core.annotation.Length;
import com.hjh.mall.common.core.annotation.Mobile;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.annotation.Password;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.common.sms.alibaba.VerifyType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: hjy-api
 * @Description 注册用户
 * @author 曾繁林
 * @date 2016年6月29日
 * @version V1.0
 */
@ApiModel
public class RegistClient extends HJYVO {

	private static final long serialVersionUID = 1L;
	@NotBlank
	@Mobile
	@ApiModelProperty("电话")
	private String mobile_tel;// 电话
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
	@NotBlank
	@Password
	@ApiModelProperty("密码")
	private String password;// 密码

	@NotBlank
	@ApiModelProperty("邀请码")
	private String invite_code;// 邀请码
	@NotBlank
	@Length(min = 1, max = 20)
	@ApiModelProperty("企业名称")
	private String enterprise_name;// 企业名称
	@NotBlank
	@ApiModelProperty("企业所在省")
	private String enterprise_province;// 企业所在省
	@NotBlank
	@ApiModelProperty("企业所在市")
	private String enterprise_city;// 企业所在市
	@NotBlank
	@ApiModelProperty("企业所在区")
	private String enterprise_area;// 企业所在区
	@NotBlank
	@ApiModelProperty("企业详情地址（不包含省市区）")
	@Length(min = 1, max = 20)
	private String enterprise_street;// 企业详情地址（不包含省市区）
	@ApiModelProperty("企业联系电话")
	@NotBlank
	@MobileOrTel
	private String enterprise_tel;// 企业联系电话
	@ApiModelProperty("企业联系人")
	@NotBlank
	@Length(min = 1, max = 10)
	private String enterprise_linkman;// 企业联系人
	@ApiModelProperty("主营")
	@NotBlank
	@Length(min = 1, max = 20)
	private String major_business;// 主营

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getInvite_code() {
		return invite_code;
	}

	public void setInvite_code(String invite_code) {
		this.invite_code = invite_code;
	}

	public String getEnterprise_name() {
		return enterprise_name;
	}

	public void setEnterprise_name(String enterprise_name) {
		this.enterprise_name = enterprise_name;
	}

	public String getEnterprise_province() {
		return enterprise_province;
	}

	public void setEnterprise_province(String enterprise_province) {
		this.enterprise_province = enterprise_province;
	}

	public String getEnterprise_city() {
		return enterprise_city;
	}

	public void setEnterprise_city(String enterprise_city) {
		this.enterprise_city = enterprise_city;
	}

	public String getEnterprise_area() {
		return enterprise_area;
	}

	public void setEnterprise_area(String enterprise_area) {
		this.enterprise_area = enterprise_area;
	}

	public String getEnterprise_street() {
		return enterprise_street;
	}

	public void setEnterprise_street(String enterprise_street) {
		this.enterprise_street = enterprise_street;
	}

	public String getEnterprise_tel() {
		return enterprise_tel;
	}

	public void setEnterprise_tel(String enterprise_tel) {
		this.enterprise_tel = enterprise_tel;
	}

	public String getMajor_business() {
		return major_business;
	}

	public void setMajor_business(String major_business) {
		this.major_business = major_business;
	}

	public String getEnterprise_linkman() {
		return enterprise_linkman;
	}

	public void setEnterprise_linkman(String enterprise_linkman) {
		this.enterprise_linkman = enterprise_linkman;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RegistClient [mobile_tel=");
		builder.append(mobile_tel);
		builder.append(", authmsg_busi_type=");
		builder.append(authmsg_busi_type);
		builder.append(", verify_msg=");
		builder.append(verify_msg);
		builder.append(", verify_msg_id=");
		builder.append(verify_msg_id);
		builder.append(", password=");
		builder.append(password);
		builder.append(", invite_code=");
		builder.append(invite_code);
		builder.append(", enterprise_name=");
		builder.append(enterprise_name);
		builder.append(", enterprise_province=");
		builder.append(enterprise_province);
		builder.append(", enterprise_city=");
		builder.append(enterprise_city);
		builder.append(", enterprise_area=");
		builder.append(enterprise_area);
		builder.append(", enterprise_street=");
		builder.append(enterprise_street);
		builder.append(", enterprise_tel=");
		builder.append(enterprise_tel);
		builder.append(", enterprise_linkman=");
		builder.append(enterprise_linkman);
		builder.append(", major_business=");
		builder.append(major_business);
		builder.append("]");
		return builder.toString();
	}

}

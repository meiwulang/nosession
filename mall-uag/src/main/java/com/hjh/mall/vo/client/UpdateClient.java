/**
 *
 */
package com.hjh.mall.vo.client;

import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.common.core.annotation.Length;
import com.hjh.mall.common.core.annotation.Mobile;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.type.SexType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: hjy-api
 * @Description 修改用户扩展信息VO
 * @author 曾繁林
 * @date 2016年6月29日
 * @version V1.0
 */
@ApiModel
public class UpdateClient extends HJYVO {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty("用户名")
	@Length(min = 1, max = 10)
	private String nick_name;// 用户名
	@Length(min = 1, max = 100)
	@ApiModelProperty("常住地址")
	private String address;// 常住地址
	@Length(min = 1, max = 100)
	@ApiModelProperty("地区编码")
	private String area_code;// 地区编码
	@Length(min = 1, max = 100)
	@ApiModelProperty("个性签名")
	private String signature;// 个性签名
	@Length(min = 1, max = 20)
	@ApiModelProperty("真实姓名")
	private String real_name;// 真实姓名
	@Length(min = 1, max = 50)
	@ApiModelProperty("街道")
	private String street;// 街道
	@EnumValue(enumClass = SexType.class)
	@ApiModelProperty("性别")
	private String sex;// 性别
	@Length(min = 1, max = 50)
	@ApiModelProperty("头像")
	private String avatar_path;// 头像
	@NotBlank
	@Length(min = 1, max = 16)
	@ApiModelProperty("用户编号")
	private String client_id;
	@NotBlank
	@Mobile
	@ApiModelProperty("电话")
	private String mobile_tel;

	public String getMobile_tel() {
		return mobile_tel;
	}

	public void setMobile_tel(String mobile_tel) {
		this.mobile_tel = mobile_tel;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAvatar_path() {
		return avatar_path;
	}

	public void setAvatar_path(String avatar_path) {
		this.avatar_path = avatar_path;
	}

	public String getArea_code() {
		return area_code;
	}

	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UpdateClient [nick_name=");
		builder.append(nick_name);
		builder.append(", address=");
		builder.append(address);
		builder.append(", area_code=");
		builder.append(area_code);
		builder.append(", signature=");
		builder.append(signature);
		builder.append(", real_name=");
		builder.append(real_name);
		builder.append(", street=");
		builder.append(street);
		builder.append(", sex=");
		builder.append(sex);
		builder.append(", avatar_path=");
		builder.append(avatar_path);
		builder.append(", client_id=");
		builder.append(client_id);
		builder.append(", mobile_tel=");
		builder.append(mobile_tel);
		builder.append("]");
		return builder.toString();
	}
}

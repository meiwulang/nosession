/**
 * 
 */
package com.hjh.mall.entity;

import com.hjh.mall.common.core.entity.Updatable;

/**
 * @Project: hjy-middle
 * @Description 用户详情
 * @author 曾繁林
 * @date 2016年7月11日
 * @version V1.0
 */
public class ClientDetail extends Updatable {

	private static final long serialVersionUID = 1L;
	private String address;// 常住地址
	private String street;// 当前位置坐标
	private String avatar_path;// 头像图片路径
	private String real_name;// 真实姓名
	private String sex;// 性别（1：男；0：女）
	private String signature;// 个性签名
	private String client_id;// 客户ID
	private String nick_name;// 昵称
	private String area_code;// 地区编码

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAvatar_path() {
		return avatar_path;
	}

	public void setAvatar_path(String avatar_path) {
		this.avatar_path = avatar_path;
	}

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
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

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
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
		builder.append("ClientDetail [address=");
		builder.append(address);
		builder.append(", street=");
		builder.append(street);
		builder.append(", avatar_path=");
		builder.append(avatar_path);
		builder.append(", real_name=");
		builder.append(real_name);
		builder.append(", sex=");
		builder.append(sex);
		builder.append(", signature=");
		builder.append(signature);
		builder.append(", client_id=");
		builder.append(client_id);
		builder.append(", nick_name=");
		builder.append(nick_name);
		builder.append(", area_code=");
		builder.append(area_code);
		builder.append("]");
		return builder.toString();
	}

}

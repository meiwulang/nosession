package com.hjh.mall.entity;

import com.hjh.mall.common.core.entity.Updatable;

/**
 * @Project: hjh.mall
 * @Description TODO
 * @author 曾繁林
 * @date 2017年2月20日
 * @version V1.0
 */
public class ClientEnterprise extends Updatable {

	private static final long serialVersionUID = 1L;

	private String client_enterprise_id;
	private String client_id;// 用户ID
	private String enterprise_name;// 企业名称
	private String enterprise_short_name;// 企业简称
	private String enterprise_province;// 企业所在省
	private String enterprise_city;// 企业所在市
	private String enterprise_area;// 企业所在区
	private String enterprise_street;// 企业详情地址（不包含省市区）
	private String enterprise_address;// 企业地址(包含省市区)
	private String enterprise_tel;// 企业联系电话
	private String enterprise_linkman;// 企业联系人
	private String major_business;// 主营
	private String invite_code;// 邀请码

	public String getInvite_code() {
		return invite_code;
	}

	public void setInvite_code(String invite_code) {
		this.invite_code = invite_code;
	}

	public String getClient_enterprise_id() {
		return client_enterprise_id;
	}

	public void setClient_enterprise_id(String client_enterprise_id) {
		this.client_enterprise_id = client_enterprise_id;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
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

	public String getEnterprise_address() {
		return enterprise_address;
	}

	public void setEnterprise_address(String enterprise_address) {
		this.enterprise_address = enterprise_address;
	}

	public String getEnterprise_tel() {
		return enterprise_tel;
	}

	public void setEnterprise_tel(String enterprise_tel) {
		this.enterprise_tel = enterprise_tel;
	}

	public String getEnterprise_linkman() {
		return enterprise_linkman;
	}

	public void setEnterprise_linkman(String enterprise_linkman) {
		this.enterprise_linkman = enterprise_linkman;
	}

	public String getMajor_business() {
		return major_business;
	}

	public void setMajor_business(String major_business) {
		this.major_business = major_business;
	}

	public String getEnterprise_short_name() {
		return enterprise_short_name;
	}

	public void setEnterprise_short_name(String enterprise_short_name) {
		this.enterprise_short_name = enterprise_short_name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ClientEnterprise [client_enterprise_id=");
		builder.append(client_enterprise_id);
		builder.append(", client_id=");
		builder.append(client_id);
		builder.append(", enterprise_name=");
		builder.append(enterprise_name);
		builder.append(", enterprise_short_name=");
		builder.append(enterprise_short_name);
		builder.append(", enterprise_province=");
		builder.append(enterprise_province);
		builder.append(", enterprise_city=");
		builder.append(enterprise_city);
		builder.append(", enterprise_area=");
		builder.append(enterprise_area);
		builder.append(", enterprise_street=");
		builder.append(enterprise_street);
		builder.append(", enterprise_address=");
		builder.append(enterprise_address);
		builder.append(", enterprise_tel=");
		builder.append(enterprise_tel);
		builder.append(", enterprise_linkman=");
		builder.append(enterprise_linkman);
		builder.append(", major_business=");
		builder.append(major_business);
		builder.append(", invite_code=");
		builder.append(invite_code);
		builder.append("]");
		return builder.toString();
	}

}

package com.hjh.mall.category.entity;

import com.hjh.mall.common.core.entity.Updatable;

/**
 * @Project: hjy-middle
 * @Description 客户
 * @author 王斌
 * @date 2016年6月25日
 * @version V1.0
 */
public class Client extends Updatable {
	private static final long serialVersionUID = 1L;
	private String client_id;// 用户ID
	private String client_name;// 用户名
	private String mobile_tel;// 联系电话
	private String password;// 密码
	private String dictionary_id;// 企业编号
	private String status;// 启用状态
	private String location;// 地理位置
	private String create_date;// 注册日期
	private String create_time;// 注册时间
	private String enterprise_name;// 单位名称

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile_tel() {
		return mobile_tel;
	}

	public void setMobile_tel(String mobile_tel) {
		this.mobile_tel = mobile_tel;
	}

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	public String getDictionary_id() {
		return dictionary_id;
	}

	public void setDictionary_id(String dictionary_id) {
		this.dictionary_id = dictionary_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getEnterprise_name() {
		return enterprise_name;
	}

	public void setEnterprise_name(String enterprise_name) {
		this.enterprise_name = enterprise_name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Client [client_id=").append(client_id).append(", client_name=").append(client_name)
				.append(", mobile_tel=").append(mobile_tel).append(", password=").append(password)
				.append(", dictionary_id=").append(dictionary_id).append(", status=").append(status)
				.append(", location=").append(location).append(", create_date=").append(create_date)
				.append(", create_time=").append(create_time).append(", enterprise_name=").append(enterprise_name)
				.append("]");
		return builder.toString();
	}

}

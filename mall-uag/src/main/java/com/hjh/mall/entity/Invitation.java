package com.hjh.mall.entity;

import com.hjh.mall.common.core.entity.Updatable;

public class Invitation extends Updatable {
	private String invite_id;

	// 邀请码
	private String invite_code;

	// 企业id
	private Long invite_customer;

	// 企业名称
	private String invite_customer_name;

	private String remark;

	// 修改日期
	private String update_date;

	private String update_time;

	// 创建人
	private String create_user;
	// 创建人ID
	private String create_user_name;
	// 修改人ID
	private String update_user_name;

	private String business_hotline;

	private static final long serialVersionUID = 1L;

	public String getInvite_id() {
		return invite_id;
	}

	public void setInvite_id(String invite_id) {
		this.invite_id = invite_id;
	}

	public String getInvite_code() {
		return invite_code;
	}

	public void setInvite_code(String invite_code) {
		this.invite_code = invite_code;
	}

	public Long getInvite_customer() {
		return invite_customer;
	}

	public void setInvite_customer(Long invite_customer) {
		this.invite_customer = invite_customer;
	}

	public String getInvite_customer_name() {
		return invite_customer_name;
	}

	public void setInvite_customer_name(String invite_customer_name) {
		this.invite_customer_name = invite_customer_name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getInit_date() {
		return init_date;
	}

	public void setInit_date(String init_date) {
		this.init_date = init_date;
	}

	public String getInit_time() {
		return init_time;
	}

	public void setInit_time(String init_time) {
		this.init_time = init_time;
	}

	public String getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public String getCreate_user_name() {
		return create_user_name;
	}

	public void setCreate_user_name(String create_user_name) {
		this.create_user_name = create_user_name;
	}

	public String getUpdate_user_name() {
		return update_user_name;
	}

	public void setUpdate_user_name(String update_user_name) {
		this.update_user_name = update_user_name;
	}

	public String getBusiness_hotline() {
		return business_hotline;
	}

	public void setBusiness_hotline(String business_hotline) {
		this.business_hotline = business_hotline;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Invitation [invite_id=");
		builder.append(invite_id);
		builder.append(", invite_code=");
		builder.append(invite_code);
		builder.append(", invite_customer=");
		builder.append(invite_customer);
		builder.append(", invite_customer_name=");
		builder.append(invite_customer_name);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", update_date=");
		builder.append(update_date);
		builder.append(", update_time=");
		builder.append(update_time);
		builder.append(", create_user=");
		builder.append(create_user);
		builder.append(", create_user_name=");
		builder.append(create_user_name);
		builder.append(", update_user_name=");
		builder.append(update_user_name);
		builder.append(", business_hotline=");
		builder.append(business_hotline);
		builder.append("]");
		return builder.toString();
	}

}
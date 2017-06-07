package com.hjh.mall.entity;

import com.hjh.mall.common.core.entity.Updatable;

public class Client extends Updatable {
	private static final long serialVersionUID = 1L;

	private String client_id;

	private String password;

	private String mobile_tel;

	private String email;

	private String invite_code;
	private String client_code;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInvite_code() {
		return invite_code;
	}

	public void setInvite_code(String invite_code) {
		this.invite_code = invite_code;
	}

	public String getClient_code() {
		return client_code;
	}

	public void setClient_code(String client_code) {
		this.client_code = client_code;
	}

}
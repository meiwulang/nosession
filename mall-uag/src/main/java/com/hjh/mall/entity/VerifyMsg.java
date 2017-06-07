package com.hjh.mall.entity;

import com.hjh.mall.common.core.entity.Updatable;

public class VerifyMsg extends Updatable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String verify_msg_id;

	private String verify_msg;

	private String mobile_tel;

	private String authmsg_busi_type;

	private String add_date;

	private String add_time;

	private Integer consecutive_requests;

	private String verify_date;

	private String verify_time;

	private String last_fail_date;

	private String last_fail_time;

	private Integer consecutive_failures;

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

	public String getAdd_date() {
		return add_date;
	}

	public void setAdd_date(String add_date) {
		this.add_date = add_date;
	}

	public String getAdd_time() {
		return add_time;
	}

	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}

	public Integer getConsecutive_requests() {
		return consecutive_requests;
	}

	public void setConsecutive_requests(Integer consecutive_requests) {
		this.consecutive_requests = consecutive_requests;
	}

	public String getVerify_date() {
		return verify_date;
	}

	public void setVerify_date(String verify_date) {
		this.verify_date = verify_date;
	}

	public String getVerify_time() {
		return verify_time;
	}

	public void setVerify_time(String verify_time) {
		this.verify_time = verify_time;
	}

	public String getLast_fail_date() {
		return last_fail_date;
	}

	public void setLast_fail_date(String last_fail_date) {
		this.last_fail_date = last_fail_date;
	}

	public String getLast_fail_time() {
		return last_fail_time;
	}

	public void setLast_fail_time(String last_fail_time) {
		this.last_fail_time = last_fail_time;
	}

	public Integer getConsecutive_failures() {
		return consecutive_failures;
	}

	public void setConsecutive_failures(Integer consecutive_failures) {
		this.consecutive_failures = consecutive_failures;
	}
}
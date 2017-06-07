package com.hjh.mall.model;

import com.hjh.mall.common.core.annotation.ExcelResources;

/**
 * @Project: hjh.mall
 * @Description TODO
 * @author 李慧峰
 * @date 2017年2月25日
 * @version V1.0
 */
public class InvitationModel {
	// 邀请码
	private String invite_code;

	// 企业id
	private Long invite_customer;

	// 企业名称
	private String invite_customer_name;

	private String remark;

	private String client_num;

	private String business_hotline;

	private int sort;

	@ExcelResources(title = "序号", order = 1)
	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	@ExcelResources(title = "邀请码", order = 2)
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

	@ExcelResources(title = "名称", order = 3)
	public String getInvite_customer_name() {
		return invite_customer_name;
	}

	public void setInvite_customer_name(String invite_customer_name) {
		this.invite_customer_name = invite_customer_name;
	}

	@ExcelResources(title = "备注", order = 5)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@ExcelResources(title = "会员数", order = 6)
	public String getClient_num() {
		return client_num;
	}

	public void setClient_num(String client_num) {
		this.client_num = client_num;
	}

	@ExcelResources(title = "业务咨询电话", order = 4)
	public String getBusiness_hotline() {
		return business_hotline;
	}

	public void setBusiness_hotline(String business_hotline) {
		this.business_hotline = business_hotline;
	}

}

package com.hjh.mall.vo.Invitation;

import com.hjh.mall.common.core.vo.PageQueryVO;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: hjh.mall
 * @Description 邀请码查询vo
 * @author 李慧峰
 * @date 2017年2月20日
 * @version V1.0
 */
public class InvitationQueryVo extends PageQueryVO {

	private static final long serialVersionUID = 1L;

	private Long invite_id;

	// 邀请码
	@ApiModelProperty(notes="邀请码")
	private String invite_code = null;
	// 邀请码 下限
	@ApiModelProperty(notes="邀请码 下限")
	private String invite_code_start = null;

	// 邀请码 上限
	@ApiModelProperty(notes="邀请码 上限")
	private String invite_code_end = null;

	// 企业id
	@ApiModelProperty(notes="企业id")
	private Long invite_customer = null;

	// 企业名称
	@ApiModelProperty(notes="企业名称")
	private String invite_customer_name = null;

	private String remark = null;

	private String start_time;

	private String end_time;

	private String create_user_name;
	private String start_date;
	private String end_date;
	private String business_hotline;

	public String getBusiness_hotline() {
		return business_hotline;
	}

	public void setBusiness_hotline(String business_hotline) {
		this.business_hotline = business_hotline;
	}

	public Long getInvite_id() {
		return invite_id;
	}

	public void setInvite_id(Long invite_id) {
		this.invite_id = invite_id;
	}

	public String getInvite_code_start() {
		return invite_code_start;
	}

	public void setInvite_code_start(String invite_code_start) {
		this.invite_code_start = invite_code_start;
	}

	public String getInvite_code_end() {
		return invite_code_end;
	}

	public void setInvite_code_end(String invite_code_end) {
		this.invite_code_end = invite_code_end;
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

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getCreate_user_name() {
		return create_user_name;
	}

	public void setCreate_user_name(String create_user_name) {
		this.create_user_name = create_user_name;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getInvite_code() {
		return invite_code;
	}

	public void setInvite_code(String invite_code) {
		this.invite_code = invite_code;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}

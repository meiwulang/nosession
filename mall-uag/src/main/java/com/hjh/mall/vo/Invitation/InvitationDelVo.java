package com.hjh.mall.vo.Invitation;

import com.hjh.mall.common.core.vo.HJYVO;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: hjh.mall
 * @Description TODO
 * @author 李慧峰
 * @date 2017年2月20日
 * @version V1.0 
 */
public class InvitationDelVo extends HJYVO{
	
	
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
	
	
	public String getInvite_id() {
		return invite_id;
	}
	public void setInvite_id(String invite_id) {
		this.invite_id = invite_id;
	}


	private static final long serialVersionUID = 1L;

	// 企业名称 
	@ApiModelProperty(notes="邀请码")
    private String invite_customer_name=null;
	@ApiModelProperty(notes="邀请码")
    private String remark=null;
    

    private String invite_code=null;

    private Long invite_customer=null;
	@ApiModelProperty(notes="id")
    private String invite_id=null;

}

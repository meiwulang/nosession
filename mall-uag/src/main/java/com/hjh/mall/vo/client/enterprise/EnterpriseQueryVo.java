package com.hjh.mall.vo.client.enterprise;

import com.hjh.mall.common.core.vo.HJYVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class EnterpriseQueryVo extends HJYVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty("用户所在企业编号")
	private String client_enterprise_id;
	@ApiModelProperty("用户编号")
	private String client_id;// 用户ID

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

}

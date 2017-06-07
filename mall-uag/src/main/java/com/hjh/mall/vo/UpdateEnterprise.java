package com.hjh.mall.vo;

import com.hjh.mall.common.core.annotation.Length;
import com.hjh.mall.common.core.vo.HJYVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: hjh.mall
 * @Description 修改公司别名
 * @author 曾繁林
 * @date 2017年3月13日
 * @version V1.0
 */
@ApiModel
public class UpdateEnterprise extends HJYVO {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty("用户企业编号")
	private String client_enterprise_id;
	@Length(max = 6)
	@ApiModelProperty("企业简称")
	private String enterprise_short_name;

	public String getClient_enterprise_id() {
		return client_enterprise_id;
	}

	public void setClient_enterprise_id(String client_enterprise_id) {
		this.client_enterprise_id = client_enterprise_id;
	}

	public String getEnterprise_short_name() {
		return enterprise_short_name;
	}

	public void setEnterprise_short_name(String enterprise_short_name) {
		this.enterprise_short_name = enterprise_short_name;
	}

}

package com.hjh.mall.vo;

import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * * @author csj:
 *
 * @date 创建时间：2017年2月20日 上午11:46:47
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
@ApiModel
public class DeleteClientAddress extends HJYVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@NotBlank
	@ApiModelProperty("用户地址编号")
	private String client_address_id;

	/**
	 * @return the client_address_id
	 */
	public String getClient_address_id() {
		return client_address_id;
	}

	/**
	 * @param client_address_id
	 *            the client_address_id to set
	 */
	public void setClient_address_id(String client_address_id) {
		this.client_address_id = client_address_id;
	}

}

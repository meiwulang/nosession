package com.hjh.mall.vo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;
@ApiModel("更新用户")
public class ToUserReceivedVo extends HJYVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */


	
	@ApiModelProperty(name = "orderId", value = "订单ID", required = true)
	@NotBlank
	private String orderId;


	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}

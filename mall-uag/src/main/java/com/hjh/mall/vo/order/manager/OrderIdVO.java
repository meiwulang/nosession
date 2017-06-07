package com.hjh.mall.vo.order.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;

@ApiModel(value="订单编号实体")


public class OrderIdVO extends HJYVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@NotBlank
	@ApiModelProperty(name = "orderId", value = "订单主键", required = true)
	private String orderId;


	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderIdVO [orderId=").append(orderId).append("]");
		return builder.toString();
	}

}

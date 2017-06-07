package com.hjh.mall.vo.order.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;

@ApiModel("订单详情")
public class OrderDetailsVO extends HJYVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(name = "client_id", value = "用户主键", required = true)
	@NotBlank
	private String client_id;
	
	@NotBlank
	@ApiModelProperty(name = "orderId", value = "订单主键", required = true)
	private String orderId;

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderDetailsVO [client_id=").append(client_id)
				.append(", orderId=").append(orderId).append("]");
		return builder.toString();
	}

}

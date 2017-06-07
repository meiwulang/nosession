package com.hjh.mall.vo.order.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.hjh.mall.common.core.annotation.NotNull;
import com.hjh.mall.common.core.vo.HJYVO;

@ApiModel("订单编号实体入参")
public class OrderTransactionPayAmountVo extends HJYVO{
	
	private static final long serialVersionUID = 1L;

	
	@NotNull
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
		builder.append("OrderTransactionPayAmountVo [orderId=").append(orderId)
				.append("]");
		return builder.toString();
	}

}

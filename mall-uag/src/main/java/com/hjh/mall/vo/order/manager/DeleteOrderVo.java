package com.hjh.mall.vo.order.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.hjh.mall.common.core.annotation.NotNull;
import com.hjh.mall.common.core.vo.HJYVO;

@ApiModel(value="删除订单编号")
public class DeleteOrderVo extends HJYVO {
	
	/**
	 * 
	 */
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
		builder.append("DeleteOrderVo [orderId=").append(orderId).append("]");
		return builder.toString();
	}
	
	
	

}

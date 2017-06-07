package com.hjh.mall.vo.order.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;

@ApiModel("更新订单预计发货时间描述")
public class UpdateOrderExceptDeliveryDesc extends HJYVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(name = "orderId", value = "订单主键", required = true)
	@NotBlank
	private	String orderId;
	@ApiModelProperty(name = "exceptDeliveryDesc", value = "预计订单发送时间(推荐字符串日期格式)", required = true)
	@NotBlank
	private	String exceptDeliveryDesc;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getExceptDeliveryDesc() {
		return exceptDeliveryDesc;
	}
	public void setExceptDeliveryDesc(String exceptDeliveryDesc) {
		this.exceptDeliveryDesc = exceptDeliveryDesc;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UpdateOrderExceptDeliveryDesc [orderId=")
				.append(orderId).append(", exceptDeliveryDesc=")
				.append(exceptDeliveryDesc).append("]");
		return builder.toString();
	}

	
}

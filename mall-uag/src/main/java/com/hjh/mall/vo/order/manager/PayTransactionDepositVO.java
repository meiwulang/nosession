package com.hjh.mall.vo.order.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.annotation.NotNull;
import com.hjh.mall.common.core.vo.HJYVO;

@ApiModel("支付预付款")
public class PayTransactionDepositVO extends HJYVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotBlank
	@ApiModelProperty(name = "orderId", value = "订单主键", required = true)
	private String orderId;
	@NotNull
	@ApiModelProperty(name = "deposit", value = "定金", required = true)
	private double deposit;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public double getDeposit() {
		return deposit;
	}

	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PayTransactionDepositVO [orderId=").append(orderId)
				.append(", deposit=").append(deposit).append("]");
		return builder.toString();
	}

}

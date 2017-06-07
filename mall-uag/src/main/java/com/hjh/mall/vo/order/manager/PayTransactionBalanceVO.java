package com.hjh.mall.vo.order.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.annotation.NotNull;
import com.hjh.mall.common.core.vo.HJYVO;
@ApiModel("支付余款实体")
public class PayTransactionBalanceVO extends HJYVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(name = "orderId", value = "订单主键", required = true)
	@NotBlank
	private String orderId;
	@ApiModelProperty(name = "balance", value = "余款", required = true)
	@NotNull
	private double balance;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PayTransactionBalanceVO [orderId=").append(orderId)
				.append(", balance=").append(balance).append("]");
		return builder.toString();
	}

}

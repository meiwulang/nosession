package com.hjh.mall.vo.order.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.annotation.NotNull;
import com.hjh.mall.common.core.vo.HJYVO;

@ApiModel("支付入参实体")
public class PayTransactionAmountVo extends HJYVO{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(name = "orderId", value = "订单主键", required = true)
	@NotBlank
	private   String orderId;
	@NotNull
	@ApiModelProperty(name = "amount", value = "全额付款金额", required = true)
    private   double amount;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PayTransactionAmountVo [orderId=").append(orderId)
				.append(", amount=").append(amount).append("]");
		return builder.toString();
	}
    
    
}

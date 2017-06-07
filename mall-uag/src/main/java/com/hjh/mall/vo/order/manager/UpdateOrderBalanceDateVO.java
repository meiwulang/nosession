package com.hjh.mall.vo.order.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.hjh.mall.common.core.vo.HJYVO;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@ApiModel("更新订单余款到期时间")
public class UpdateOrderBalanceDateVO extends HJYVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(name = "orderId", value = "订单主键", required = true)
	@NotBlank
	private String orderId;

	@ApiModelProperty(name = "balanceDateCount", value = "余款到期时间期限,单位天", required = true)
	@NotNull
	private Integer balanceDateCount;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getBalanceDateCount() {
		return balanceDateCount;
	}

	public void setBalanceDateCount(Integer balanceDateCount) {
		this.balanceDateCount = balanceDateCount;
	}
}

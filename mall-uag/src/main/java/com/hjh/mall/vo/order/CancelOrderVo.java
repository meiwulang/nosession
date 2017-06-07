package com.hjh.mall.vo.order;

import com.hjh.mall.common.core.annotation.NotNull;
import com.hjh.mall.common.core.vo.HJYVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by qiuxianxiang on 17/5/24.
 */

@ApiModel("取消订单入参实体")
public class CancelOrderVo extends HJYVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@NotNull
	@ApiModelProperty(name = "orderId", value = "订单主键", required = true)
	private String orderId;
	//@NotNull
	@ApiModelProperty(name = "cancelReason", value = "原因", required = false)
	private String cancelReason;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
}

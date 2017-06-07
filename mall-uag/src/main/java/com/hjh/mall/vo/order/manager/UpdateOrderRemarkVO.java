package com.hjh.mall.vo.order.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;

@ApiModel(value="更新订单备注实体")
public class UpdateOrderRemarkVO extends HJYVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotBlank
	@ApiModelProperty(name = "orderId", value = "订单主键", required = true)
	private String orderId;
	@ApiModelProperty(name = "remark", value = "订单备注", required = true)
	@NotBlank
	private String remark;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UpdateOrderRemarkVO [orderId=").append(orderId)
				.append(", remark=").append(remark).append("]");
		return builder.toString();
	}
	
	

}

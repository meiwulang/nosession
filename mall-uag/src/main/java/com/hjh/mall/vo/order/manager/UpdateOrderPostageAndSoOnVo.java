package com.hjh.mall.vo.order.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.hjh.mall.common.core.vo.HJYVO;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@ApiModel("更新订单邮费等信息")
public class UpdateOrderPostageAndSoOnVo extends HJYVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotBlank
	@ApiModelProperty(name = "orderId", value = "订单主键", required = true)
	private String orderId;

	@NotNull
	//@Range(min=1, max=999999999)
	@ApiModelProperty(name = "postage", value = "邮费", required = true)
	private Double postage;


	@ApiModelProperty(name = "estimateDeliveryDesc", value = "预计订单发送时间(推荐字符串日期格式)", required = true)
	private String estimateDeliveryDesc;

	@ApiModelProperty(name = "balanceDateCount", value = "余款到期时间期限,单位天", required = false)
	@NotNull
	private Integer balanceDateCount=0;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Double getPostage() {
		return postage;
	}

	public void setPostage(Double postage) {
		this.postage = postage;
	}

	public String getEstimateDeliveryDesc() {
		return estimateDeliveryDesc;
	}

	public void setEstimateDeliveryDesc(String estimateDeliveryDesc) {
		this.estimateDeliveryDesc = estimateDeliveryDesc;
	}

	public int getBalanceDateCount() {
		return balanceDateCount;
	}

	public void setBalanceDateCount(int balanceDateCount) {
		this.balanceDateCount = balanceDateCount;
	}

	@Override
	public String toString() {
		return "UpdateOrderPostageAndSoOnVo{" + "orderId='" + orderId + '\''
				+ ", postage=" + postage + ", estimateDeliveryDesc='"
				+ estimateDeliveryDesc + '\'' + ", balanceDateCount="
				+ balanceDateCount + '}';
	}
}

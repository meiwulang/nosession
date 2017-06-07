package com.hjh.mall.order.vo;

import io.swagger.annotations.ApiModelProperty;

import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;

/**
 * @Project: mall-order-sdk
 * @Description 获得物流信息vo
 * @author 杨益桦
 * @date 2017年5月24日
 * @version V1.0
 */
public class GetLogisticsVo  extends HJYVO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(name = "logisticsId", value = "主键id", required = true)
	@NotBlank
	private String orderId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

//		@ApiModelProperty(name = "logisticsId", value = "主键id", required = true)
//	@NotBlank
//	private String logisticsId;
//
//	public String getLogisticsId() {
//		return logisticsId;
//	}
//
//	public void setLogisticsId(String logisticsId) {
//		this.logisticsId = logisticsId;
//	}

}

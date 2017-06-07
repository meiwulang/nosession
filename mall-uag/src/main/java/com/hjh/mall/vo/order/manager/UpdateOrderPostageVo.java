package com.hjh.mall.vo.order.manager;

import com.hjh.mall.common.core.annotation.Length;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import com.hjh.mall.common.core.vo.HJYVO;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@ApiModel("更新订单运费信息")
public class UpdateOrderPostageVo extends HJYVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(name = "orderId", value = "订单主键", required = true)
	@NotBlank
    private String orderId;
    
    @ApiModelProperty(name = "postage", value = "运费", required = true)
    @NotNull
    private Double postage;



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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UpdateOrderPostageVo [orderId=").append(orderId)
				.append(", postage=").append(postage).append("]");
		return builder.toString();
	}
    
    

}

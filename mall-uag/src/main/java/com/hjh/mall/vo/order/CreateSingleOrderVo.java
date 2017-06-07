package com.hjh.mall.vo.order;

import com.hjh.mall.common.core.annotation.Length;
import com.hjh.mall.common.core.annotation.NotNull;
import com.hjh.mall.common.core.vo.HJYVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Size;

@ApiModel
public class CreateSingleOrderVo extends HJYVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(name = "productCompoundInfo", value = "商品下单的组合信息, 其构成为:  商品Id_规格Id_数量。 例如: [59_34_5,59_34_5] ", required = true, example = "59_34_5,59_34_5")
	private String[] productCompoundInfo;
	@ApiModelProperty(name = "deliveryAddressId", value = "收获地址ID", required = true)
	private String deliveryAddressId;
	@Length(max = 200)
	@ApiModelProperty(name = "buyerComments", value = "买家订单留言", required = false)
	private String buyerComments;
	@NotNull
	@ApiModelProperty(name = "resubmitToken", value = "防止用户连续提交的Token", required = true)
	private String resubmitToken;

	@NotNull
	public String[] getProductCompoundInfo() {
		return productCompoundInfo;
	}

	public void setProductCompoundInfo(String[] productCompoundInfo) {
		this.productCompoundInfo = productCompoundInfo;
	}

	public String getDeliveryAddressId() {
		return deliveryAddressId;
	}

	public void setDeliveryAddressId(String deliveryAddressId) {
		this.deliveryAddressId = deliveryAddressId;
	}

	public String getBuyerComments() {
		return buyerComments;
	}

	public void setBuyerComments(String buyerComments) {
		this.buyerComments = buyerComments;
	}

	public String getResubmitToken() {
		return resubmitToken;
	}

	public void setResubmitToken(String resubmitToken) {
		this.resubmitToken = resubmitToken;
	}

}

package com.hjh.mall.vo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.hjh.mall.common.core.vo.PageQueryVO;
import com.hjh.mall.order.constants.OrderStatus;

@ApiModel
public class ListOrderVo extends PageQueryVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(name = "orderStatus", value = "组合订单状态，订单状态之间是 \"OR\"关系, 以枚举字符串方式传递比如:  UN_DELIVERY,NON_PAYMENT 等", required = false)
	private OrderStatus[] orderStatusList;

	@ApiModelProperty(name = "isPostageCalculation", value = "邮费是否已经计算", required = false)
	private Boolean isPostageCalculation;

	@ApiModelProperty(name = "haveFullPayProof", value = "是否具有全款支付的凭证，不传则不作为过滤条件", required = false)
	private Boolean haveFullPayProof;

	@ApiModelProperty(name = "haveDepositPayProof", value = "是否具有定金支付的凭证，不传则不作为过滤条件", required = false)
	private Boolean haveDepositPayProof;


	public OrderStatus[] getOrderStatusList() {
		return orderStatusList;
	}

	public void setOrderStatusList(OrderStatus[] orderStatusList) {
		this.orderStatusList = orderStatusList;
	}

	public Boolean getPostageCalculation() {
		return isPostageCalculation;
	}

	public void setPostageCalculation(Boolean postageCalculation) {
		isPostageCalculation = postageCalculation;
	}

	public Boolean getHaveFullPayProof() {
		return haveFullPayProof;
	}

	public void setHaveFullPayProof(Boolean haveFullPayProof) {
		this.haveFullPayProof = haveFullPayProof;
	}

	public Boolean getHaveDepositPayProof() {
		return haveDepositPayProof;
	}

	public void setHaveDepositPayProof(Boolean haveDepositPayProof) {
		this.haveDepositPayProof = haveDepositPayProof;
	}
}

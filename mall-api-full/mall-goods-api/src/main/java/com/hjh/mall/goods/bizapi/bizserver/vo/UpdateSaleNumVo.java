package com.hjh.mall.goods.bizapi.bizserver.vo;

import java.util.List;

import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;

/**
 * @Project: mall-goods-api
 * @Description 修改商品已售数量vo
 * @author 杨益桦
 * @date 2017年3月20日
 * @version V1.0
 */
public class UpdateSaleNumVo extends HJYVO {

	private static final long serialVersionUID = 1L;

	// 商品编号
	@NotBlank
	private String prdt_id;

	// 规格集合
	private List<OrderDetailVo> orderDetailVos;

	public String getPrdt_id() {
		return prdt_id;
	}

	public void setPrdt_id(String prdt_id) {
		this.prdt_id = prdt_id;
	}

	public List<OrderDetailVo> getOrderDetailVos() {
		return orderDetailVos;
	}

	public void setOrderDetailVos(List<OrderDetailVo> orderDetailVos) {
		this.orderDetailVos = orderDetailVos;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UpdateSaleNumVo [prdt_id=");
		builder.append(prdt_id);
		builder.append(", orderDetailVos=");
		builder.append(orderDetailVos);
		builder.append(", access_token=");
		builder.append(access_token);
		builder.append(", functionId=");
		builder.append(functionId);
		builder.append("]");
		return builder.toString();
	}

}

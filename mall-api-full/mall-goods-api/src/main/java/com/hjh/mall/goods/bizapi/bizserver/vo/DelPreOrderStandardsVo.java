package com.hjh.mall.goods.bizapi.bizserver.vo;

import java.util.List;

import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;


/**
 * @Project: mall-goods-api-bran
 * @Description 删除购物车单个商品的规格vo
 * @author 王斌
 * @date 2017年4月28日
 * @version V1.0
 */
public class DelPreOrderStandardsVo extends HJYVO {

	private static final long serialVersionUID = 1L;
	// 购物车编号
	@NotBlank
	private String shopping_cart_id;
	// 规格编号集合
	private List<String> shopping_cart_detail_ids;

	public String getShopping_cart_id() {
		return shopping_cart_id;
	}

	public void setShopping_cart_id(String shopping_cart_id) {
		this.shopping_cart_id = shopping_cart_id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<String> getShopping_cart_detail_ids() {
		return shopping_cart_detail_ids;
	}

	public void setShopping_cart_detail_ids(List<String> shopping_cart_detail_ids) {
		this.shopping_cart_detail_ids = shopping_cart_detail_ids;
	}

}

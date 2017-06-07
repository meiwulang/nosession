package com.hjh.mall.goods.bizapi.bizserver.vo;

import java.util.List;

import com.hjh.mall.common.core.vo.HJYVO;

/**
 * 获取购物车最新信息
 *
 * @author 王斌
 *
 */
public class GetLastInfoParamsVo extends HJYVO {

	private static final long serialVersionUID = 1L;
	private String shopping_cart_id;// 购物车编号
	private List<String> shopping_cart_detail_ids;// 购物车详情编号集合

	public String getShopping_cart_id() {
		return shopping_cart_id;
	}

	public void setShopping_cart_id(String shopping_cart_id) {
		this.shopping_cart_id = shopping_cart_id;
	}

	public List<String> getShopping_cart_detail_ids() {
		return shopping_cart_detail_ids;
	}

	public void setShopping_cart_detail_ids(List<String> shopping_cart_detail_ids) {
		this.shopping_cart_detail_ids = shopping_cart_detail_ids;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

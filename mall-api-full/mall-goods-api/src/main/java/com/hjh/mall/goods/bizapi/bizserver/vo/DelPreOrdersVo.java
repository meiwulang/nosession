package com.hjh.mall.goods.bizapi.bizserver.vo;

import java.util.ArrayList;

import com.hjh.mall.common.core.annotation.NotNull;
import com.hjh.mall.common.core.vo.HJYVO;

/**
 * @Project: mall-goods-api-bran
 * @Description 删除购物车多个商品vo
 * @author 王斌
 * @date 2017年4月28日
 * @version V1.0
 */
public class DelPreOrdersVo extends HJYVO {

	private static final long serialVersionUID = 1L;
	// 购物车编号
	@NotNull
	private ArrayList<String> shopping_cart_ids;

	public ArrayList<String> getShopping_cart_ids() {
		return shopping_cart_ids;
	}

	public void setShopping_cart_ids(ArrayList<String> shopping_cart_ids) {
		this.shopping_cart_ids = shopping_cart_ids;
	}

}

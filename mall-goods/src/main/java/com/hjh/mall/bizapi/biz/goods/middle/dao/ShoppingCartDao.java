package com.hjh.mall.bizapi.biz.goods.middle.dao;

import java.util.List;
import java.util.Map;

import com.hjh.mall.bizapi.biz.goods.middle.entity.ShoppingCart;
import com.hjh.mall.common.core.dao.base.DAOBase;

public interface ShoppingCartDao extends DAOBase<ShoppingCart, String> {
	List<Map<String, Object>> queryByLike(Map<String, Object> example);

	List<Map<String, Object>> queryReturnMap(ShoppingCart example);

	Map<String, Object> getLastshopCart(ShoppingCart example);

	int countByLike(Map<String, Object> example);

	String queryStandardIds(ShoppingCart example);

	int batchDel(ShoppingCart example);
}
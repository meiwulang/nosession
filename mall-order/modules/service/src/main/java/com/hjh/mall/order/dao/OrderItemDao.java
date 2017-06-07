/*
 * OrderItemDomainMapper.java
 * Copyright(C) 20xx-2015 xxxxxx��˾
 * All rights reserved.
 * -----------------------------------------------
 * 2017-05-11 Created
 */
package com.hjh.mall.order.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hjh.mall.order.model.OrderItemDomain;

@Mapper
public interface OrderItemDao {
	int delete(String orderItemId);

	int insert(OrderItemDomain orderItemDomain);

	OrderItemDomain get(String orderItemId);

	int update(OrderItemDomain record);
	
	List<OrderItemDomain> query(OrderItemDomain orderItemDomain);

	List<OrderItemDomain> queryOrderItems(String orderId);

}
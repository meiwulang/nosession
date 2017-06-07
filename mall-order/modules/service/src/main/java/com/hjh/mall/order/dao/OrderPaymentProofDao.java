/*
 * OrderPaymentProofDomainMapper.java
 * Copyright(C) 20xx-2015 xxxxxx��˾
 * All rights reserved.
 * -----------------------------------------------
 * 2017-05-11 Created
 */
package com.hjh.mall.order.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hjh.mall.order.model.OrderPaymentProofDomain;

@Mapper
public interface OrderPaymentProofDao {

	public void insert(OrderPaymentProofDomain orderPaymentProofDomain);
	
	public void update(OrderPaymentProofDomain orderPaymentProof);

	public List<OrderPaymentProofDomain> getOrderPayProof(String orderId);
	
	public List<OrderPaymentProofDomain> query(OrderPaymentProofDomain orderPaymentProofDomain);

	OrderPaymentProofDomain get(String orderId);
	
	public void delete(String id);

}
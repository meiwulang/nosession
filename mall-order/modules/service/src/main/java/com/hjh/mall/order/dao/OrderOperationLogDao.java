package com.hjh.mall.order.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hjh.mall.order.model.OrderOperationLogDomain;
import org.apache.ibatis.annotations.Param;

/**
 * Created by qiuxianxiang on 17/5/11.
 */

@Mapper
public interface OrderOperationLogDao {

	void insert(OrderOperationLogDomain orderOperationLogDomain);
	
	int update(OrderOperationLogDomain orderOperationLogDomain);
	
	int delete(OrderOperationLogDomain orderOperationLogDomain);

	OrderOperationLogDomain get(String id);

	List<OrderOperationLogDomain> getOrderOperationLog(@Param("orderId") String orderId);


	List<OrderOperationLogDomain> query (OrderOperationLogDomain orderOperationLogDomain);

}

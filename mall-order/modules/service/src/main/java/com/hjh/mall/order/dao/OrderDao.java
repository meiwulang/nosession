/*
 * OrderDomainMapper.java
 * Copyright(C) 20xx-2015 xxxxxx��˾
 * All rights reserved.
 * -----------------------------------------------
 * 2017-05-11 Created
 */
package com.hjh.mall.order.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hjh.mall.order.vo.QueryOrderVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hjh.mall.order.model.OrderDomain;

@Mapper
public interface OrderDao {


	public List<OrderDomain> query(QueryOrderVo queryOrderVo);
	
	public Integer queryCount(Map queryParam);

	int insert(OrderDomain orderDomain);

	int update(OrderDomain orderDomain);

	int delete(@Param("orderId")String orderId);

	OrderDomain get(@Param("orderId")String orderId);

	Integer checkIsUserOrder(@Param("orderId")String orderId, @Param("userId")String userId);


	void deleteOrder(@Param("orderId")String orderId,@Param("status")int status);


	//--   订单金额相关
	BigDecimal getOrderTransactionAmount(@Param("orderId")String orderId);
	void updateOrderTransactionAmount(@Param("orderId")String orderId, @Param("amount")double amount);

	BigDecimal getOrderTransactionActualPayAmount(String orderId);
	void updateOrderTransactionActualPayAmount(@Param("orderId")String orderId, @Param("actualPayAmount")double actualPayAmount);

	BigDecimal getOrderTransactionDeposit(@Param("orderId")String orderId);
	void updateOrderTransactionDeposit(@Param("orderId")String orderId, @Param("deposit")double deposit);

	BigDecimal getOrderTransactionActualPayBalance(@Param("orderId")String orderId);
	void updateOrderTransactionActualPayBalance(@Param("orderId")String orderId, @Param("actualPayBalance")double actualPayBalance);

	BigDecimal getOrderPostage(@Param("orderId")String orderId);

	void updateOrderPostage(@Param("orderId")String orderId, @Param("postage")double postage);

	//--   订单金额相关

	/**
	 * 修改订单状态
	 * @param orderId
	 * @param orderStatus
	 */
	void updateOrderStatus(@Param("orderId")String orderId, @Param("orderStatus")int orderStatus);
	void updateOrderToTerminal(@Param("orderId")String orderId, @Param("orderStatus")int orderStatus, @Param("terminalDate")Date terminalDate);
	void updateOrderToUnDelivery(@Param("orderId")String orderId, @Param("orderStatus")int orderStatus, @Param("payDate")Date payDate);
	void updateOrderToRefunding(@Param("orderId")String orderId, @Param("orderStatus")int orderStatus, @Param("applyRefundDate")Date applyRefundDate);
	void updateOrderToCancel(@Param("orderId")String orderId, @Param("orderStatus")int orderStatus, @Param("cancelDate")Date cancelDate);





	
	void updateDeliveryTime(@Param("orderId")String orderId, @Param("orderStatus")int orderStatus,@Param("actualDeliveryDate")Date actualDeliveryDate);



	public void updateOrderEstimateDeliveryDesc(@Param("orderId") String orderId, @Param("exceptDeliveryDesc")String exceptDeliveryDesc);

	public void updateOrderBalanceDateCount(@Param("orderId")String orderId, @Param("balanceDateCount")int balanceDateCount);

	public void updateOrderPostageEtc(@Param("orderId")String orderId, @Param("postage")double postage,
									  @Param("estimateDeliveryDesc")String estimateDeliveryDesc, @Param("balanceDateCount")int balanceDateCount);

	public void updateOrderRemark(@Param("orderId")String orderId, @Param("remark")String remark);

	public String getOrderRemark(@Param("orderId")String orderId);


	public void updateOrderDeliveryDate(@Param("orderId")String orderId, @Param("deliveryDate")Date deliveryDate);



	public void updateDepositProofDate(@Param("orderId")String orderId, @Param("depositProofDate")Date depositProofDate);
	public void updateBalanceProofDate(@Param("orderId")String orderId, @Param("balanceProofDate")Date balanceProofDate);



	public void updateRefundingInfo(@Param("orderId")String orderId, @Param("refundingAmount")double refundingAmount, @Param("refundingExplain")String refundingExplain);



}
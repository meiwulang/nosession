package com.hjh.mall.order.model;

import java.util.Date;

/**
 * 订单表操作记录 Created by qiuxianxiang on 17/5/11.
 */
public class OrderOperationLogDomain {
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 订单主键
	 */
	private String orderId;
	/**
	 * 订单操作前状态
	 */
	private Integer orderPreStatus;
	/**
	 * 订单当前状态
	 */
	private Integer orderStatus;
	/**
	 * 操作描述
	 */
	private String operationMsg;

	private String operatorId ="defaultValue";
	private String operatorName ="defaultValue";

	/**
	 * 创建时间
	 */
	private Date createTime = new Date();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getOrderPreStatus() {
		return orderPreStatus;
	}

	public void setOrderPreStatus(Integer orderPreStatus) {
		this.orderPreStatus = orderPreStatus;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOperationMsg() {
		return operationMsg;
	}

	public void setOperationMsg(String operationMsg) {
		this.operationMsg = operationMsg;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
}

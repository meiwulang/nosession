package com.hjh.mall.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单模块--订单支付凭证表。 该表主要记录用户上传的支付凭证 Created by qiuxianxiang on 17/5/11.
 */
public class OrderPaymentProofDomain {

	/**
	 * 主键
	 */
	private String id;
	/**
	 * 订单主键
	 */
	private String orderId;

	/**
	 * 付款人
	 */
	private String paymentUserName;

	/**
	 * 付款支付帐号
	 */
	private String paymentAccount;

	/**
	 * 付款银行
	 */
	private String bankAccount;

	/**
	 * 金额
	 */
	private BigDecimal amount;

	/**
	 * 支付凭证快照
	 */
	private String paymentProofSnapshot;

	/**
	 * 创建时间
	 */
	private Date createDate;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPaymentUserName() {
		return paymentUserName;
	}

	public void setPaymentUserName(String paymentUserName) {
		this.paymentUserName = paymentUserName;
	}

	public String getPaymentAccount() {
		return paymentAccount;
	}

	public void setPaymentAccount(String paymentAccount) {
		this.paymentAccount = paymentAccount;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getPaymentProofSnapshot() {
		return paymentProofSnapshot;
	}

	public void setPaymentProofSnapshot(String paymentProofSnapshot) {
		this.paymentProofSnapshot = paymentProofSnapshot;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}

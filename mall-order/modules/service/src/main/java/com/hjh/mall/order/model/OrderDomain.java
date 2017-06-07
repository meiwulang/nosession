package com.hjh.mall.order.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单模块--订单主表 Created by qiuxianxiang on 17/5/11.
 */
public class OrderDomain {
	private String orderId;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 订单状态： (左边订单状态，右边状态码)
	 * 
	 * 未付款、等待买家付款 =1 已付预付款 =2 买家已付款、卖家待发货 =3 卖家已发货、用户待收货 =4 买家确认收货 =5 交易成功 =6
	 * 
	 * 交易关闭--买家取消订单 =7 交易关闭--卖家取消订单 =8
	 * 
	 * 退款中 =9 退货退款中 =10
	 * 
	 * 交易关闭--退款 =11 交易关闭--退货且退款 =12 交易关闭--异常订单 =13
	 */
	private Integer orderStatus;
	/**
	 * 买家ID
	 */
	private String userId;

	private String userName;

	private String userMobile;
	/**
	 * 店铺ID, 目前只有一家店铺，默认值为0
	 */
	private String shopId;

	/**
	 * 品牌主键
	 */
	private String brandId;

	/**
	 * 品牌名称
	 */
	private String brandName;


	/**
	 * 目前只存在两种交易类型: 普通(0) 默认值 预付款(1)
	 */
	private Integer transactionType;
	/**
	 * 交易总金额(除邮费)。 用户最终需要支付的费用= 交易总金额+邮费
	 */
	private BigDecimal transactionAmount;
	/**
	 * 交易实际支付金额
	 */
	private BigDecimal transactionActualPayAmount;
	/**
	 * 交易定金
	 */
	private BigDecimal transactionDeposit;
	/**
	 * 实际支付余额
	 */
	private BigDecimal transactionActualPayBalance;
	/**
	 * 邮费
	 */
	private BigDecimal postage;
	/**
	 * 支付时间
	 */
	private Date payDate;
	/**
	 * 订单来源的渠道。目前只有手机端
	 */
	private String channel = "手机端";
	/**
	 * 订单生成时间
	 */
	private Date createdDate;
	private Date modifiedDate;
	/**
	 * 逻辑删除标记： 默认(0) ， 表示未删除 用户删除(1) , 表示用户删除订单，用户对订单不可见，商家可见 商户删除订单(2) ,
	 * 表示该订单已经删除，用户、商家都不可见
	 */
	private Integer delFlag;
	/**
	 * 收货人姓名
	 */
	private String consigneeName;
	/**
	 * 收货人手机
	 */
	private String consigneeMobile;
	/**
	 * 收货人电话
	 */
	private String consigneeTelephone;
	/**
	 * 收货人地址
	 */
	private String consigneeAddress;
	/**
	 * 收货人所在省份
	 */
	private String consigneeProvince;
	/**
	 * 收货人所在城市
	 */
	private String consigneeCity;
	/**
	 * 收货人所在地区
	 */
	private String consigneeDistrict;
	/**
	 * 收货人地区邮编
	 */
	private String consigneeZip;
	/**
	 * 买家订单留言
	 */
	private String buyerComments;


	/**
	 * 订单备注。记录订单相关信息 如 交易关闭、订单异常情况的原因
	 */
	private String orderRemark;


	/**
	 * 邀请码
	 */
	private String inviteCode;


	/**
	 * 预计发货时间
	 */
	private String estimateDeliveryDesc;


	//DEPOSIT_PROOF_DATE
	/**
	 * 定金支付凭证时间
	 */
	private Date depositProofDate;

	/**
	 * 余款或全款支付凭证时间
	 */
	private Date balanceProofDate;

//
//	/**
//	 * 余款到期时间
//	 */
//	private Date balanceDate;

	/**
	 * 余款延期日期天数
	 */
	private int balanceDateCount;

	/**
	 * 订单发货时间
	 */
	private Date actualDeliveryDate;//ACTUAL_DELIVERY_DATE

	/**
	 * 退款金额
	 */
	private BigDecimal refundingAmount;

	/**
	 * 退款说明  EXPLAIN
	 * @return
	 */
	private String refundingExplain;

	/**
	 *  申请退款时间
	 */
	private Date applyRefundDate;

	/**
	 * 退款路径
	 */
	private String refundingPath = "银行汇款";

	private Date terminalDate;


	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Integer getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(Integer transactionType) {
		this.transactionType = transactionType;
	}

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public BigDecimal getTransactionActualPayAmount() {
		return transactionActualPayAmount;
	}

	public void setTransactionActualPayAmount(BigDecimal transactionActualPayAmount) {
		this.transactionActualPayAmount = transactionActualPayAmount;
	}

	public BigDecimal getTransactionDeposit() {
		return transactionDeposit;
	}

	public void setTransactionDeposit(BigDecimal transactionDeposit) {
		this.transactionDeposit = transactionDeposit;
	}

	public BigDecimal getTransactionActualPayBalance() {
		return transactionActualPayBalance;
	}

	public void setTransactionActualPayBalance(BigDecimal transactionActualPayBalance) {
		this.transactionActualPayBalance = transactionActualPayBalance;
	}

	public BigDecimal getPostage() {
		return postage;
	}

	public void setPostage(BigDecimal postage) {
		this.postage = postage;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneeMobile() {
		return consigneeMobile;
	}

	public void setConsigneeMobile(String consigneeMobile) {
		this.consigneeMobile = consigneeMobile;
	}

	public String getConsigneeTelephone() {
		return consigneeTelephone;
	}

	public void setConsigneeTelephone(String consigneeTelephone) {
		this.consigneeTelephone = consigneeTelephone;
	}

	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}

	public String getConsigneeProvince() {
		return consigneeProvince;
	}

	public void setConsigneeProvince(String consigneeProvince) {
		this.consigneeProvince = consigneeProvince;
	}

	public String getConsigneeCity() {
		return consigneeCity;
	}

	public void setConsigneeCity(String consigneeCity) {
		this.consigneeCity = consigneeCity;
	}

	public String getConsigneeDistrict() {
		return consigneeDistrict;
	}

	public void setConsigneeDistrict(String consigneeDistrict) {
		this.consigneeDistrict = consigneeDistrict;
	}

	public String getConsigneeZip() {
		return consigneeZip;
	}

	public void setConsigneeZip(String consigneeZip) {
		this.consigneeZip = consigneeZip;
	}

	public String getBuyerComments() {
		return buyerComments;
	}

	public void setBuyerComments(String buyerComments) {
		this.buyerComments = buyerComments;
	}

	public String getOrderRemark() {
		return orderRemark;
	}

	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public String getEstimateDeliveryDesc() {
		return estimateDeliveryDesc;
	}

	public void setEstimateDeliveryDesc(String estimateDeliveryDesc) {
		this.estimateDeliveryDesc = estimateDeliveryDesc;
	}

	public Date getDepositProofDate() {
		return depositProofDate;
	}

	public void setDepositProofDate(Date depositProofDate) {
		this.depositProofDate = depositProofDate;
	}

	public Date getBalanceProofDate() {
		return balanceProofDate;
	}

	public void setBalanceProofDate(Date balanceProofDate) {
		this.balanceProofDate = balanceProofDate;
	}

	public Date getActualDeliveryDate() {
		return actualDeliveryDate;
	}

	public void setActualDeliveryDate(Date actualDeliveryDate) {
		this.actualDeliveryDate = actualDeliveryDate;
	}

	public int getBalanceDateCount() {
		return balanceDateCount;
	}

	public void setBalanceDateCount(int balanceDateCount) {
		this.balanceDateCount = balanceDateCount;
	}

	public BigDecimal getRefundingAmount() {
		return refundingAmount;
	}

	public void setRefundingAmount(BigDecimal refundingAmount) {
		this.refundingAmount = refundingAmount;
	}

	public String getRefundingExplain() {
		return refundingExplain;
	}

	public void setRefundingExplain(String refundingExplain) {
		this.refundingExplain = refundingExplain;
	}

	public Date getApplyRefundDate() {
		return applyRefundDate;
	}

	public void setApplyRefundDate(Date applyRefundDate) {
		this.applyRefundDate = applyRefundDate;
	}

	public String getRefundingPath() {
		return refundingPath;
	}

	public void setRefundingPath(String refundingPath) {
		this.refundingPath = refundingPath;
	}

	public Date getTerminalDate() {
		return terminalDate;
	}

	public void setTerminalDate(Date terminalDate) {
		this.terminalDate = terminalDate;
	}
}

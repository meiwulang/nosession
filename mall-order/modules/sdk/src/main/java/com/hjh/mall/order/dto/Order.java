package com.hjh.mall.order.dto;


import com.hjh.mall.order.constants.OrderStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by qiuxianxiang on 17/5/8.
 */
public class Order implements java.io.Serializable {

    private static final long serialVersionUID = 12739127391L;

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
     *  总价 用户要付的钱
     */
    private BigDecimal transactionPayAmount;

    /**
     * 交易实际支付金额
     */
    private BigDecimal transactionActualPayAmount;
    /**
     * 交易定金
     */
    private BigDecimal transactionDeposit;
    /**
     * 实际交易余额
     */
    private BigDecimal transactionActualPayBalance;

    /**
     * 需要支付的尾款
     */
    private BigDecimal transactionBalance;

    /**
     * 邮费
     */
    private BigDecimal postage;


    /**
     * 邮费是否确认
     */
    //private Boolean isPostageCalculation;


    /**
     * 支付时间
     */
    private Date payDate;

    /**
     * 订单生成时间
     */
    private Date createdDate;


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
     * 买家订单留言
     */
    private String buyerComments;


    /**
     * 邀请码
     */
    private String inviteCode;


    /**
     * 预计发货时间的描述
     */
    private String estimateDeliveryDesc;


    /**
     * 定金凭证上传时间
     */
    private Date depositProofDate;

    /**
     * 余款或全款支付凭证时间
     */
    private Date balanceProofDate;

//    /**
//     * 余款到期时间
//     */
//    private Date balanceDate;

    /**
     * 余款延期日期天数
     */
    private int balanceDateCount;


    /**
     * 订单发货时间
     */
    private Date actualDeliveryDate;//ACTUAL_DELIVERY_DATE


    private String paymentMode;


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

    /**
     * 服务器当前时间值
     */
    private Long currentDate = System.currentTimeMillis();

    //private List<OrderItem> orderItemList;

    private List<Product> productList;


    // 订单邮费是否 核算
    public boolean isPostageCalculation() {

        if (postage == null) {
            return false;
        }

        return true;
    }

    // 订单预付款  是否凭证支付上传
    public boolean isPayDeposit() {
        if (depositProofDate == null) {
            return false;
        }
        return true;
    }

    // 订单余款或全款 是否凭证支付上传
    public boolean isPayBalance() {
        if (balanceProofDate == null) {
            return false;
        }
        return true;
    }



    public BigDecimal getTransactionPayAmount() {

        if (getPostage() != null && getTransactionAmount() != null) {
            return getTransactionAmount().add(getPostage());
        }

        return getTransactionAmount();

    }

    public void setTransactionPayAmount(BigDecimal transactionPayAmount) {
        this.transactionPayAmount = transactionPayAmount;
    }

    public BigDecimal getTransactionBalance() {
        if (transactionBalance == null) {
            if (getTransactionPayAmount() != null && getTransactionActualPayAmount() != null) {
                transactionBalance = getTransactionPayAmount().subtract(getTransactionActualPayAmount());
            }
        }
        if (transactionBalance == null) {
            return new BigDecimal("0");
        }
        return transactionBalance;
    }

    public void setTransactionBalance(BigDecimal transactionBalance) {
        this.transactionBalance = transactionBalance;
    }




    // ========== 以下 是生成的get set 方法


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public String getOrderStatusDescription() {
        return OrderStatus.getCustomerDescription(getOrderStatus());
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    public String getBuyerComments() {
        return buyerComments;
    }

    public void setBuyerComments(String buyerComments) {
        this.buyerComments = buyerComments;
    }

//    public List<OrderItem> getOrderItemList() {
//        return orderItemList;
//    }
//
//    public void setOrderItemList(List<OrderItem> orderItemList) {
//        this.orderItemList = orderItemList;
//    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
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

    public String getPaymentMode() {
        if(paymentMode == null || "".equals(paymentMode)) {
            return "银行汇款";
        }
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
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

    private String testColum;

    public String getTestColum() {
        return testColum;
    }

    public void setTestColum(String testColum) {
        this.testColum = testColum;
    }

    public Date getTerminalDate() {
        return terminalDate;
    }

    public void setTerminalDate(Date terminalDate) {
        this.terminalDate = terminalDate;
    }

    public Long getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Long currentDate) {
        this.currentDate = currentDate;
    }
}

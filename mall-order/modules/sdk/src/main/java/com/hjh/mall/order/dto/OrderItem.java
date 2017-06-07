package com.hjh.mall.order.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by qiuxianxiang on 17/5/8.
 */
public class OrderItem implements java.io.Serializable  {

    private static final long serialVersionUID = 1239999123L;

    /**
     * 订单明细主键
     */
    private String orderItemId;
    /**
     * 订单主键
     */
    private String orderId;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 商品主键
     */
    private String productId;
    /**
     * 商品规格主键
     */
    private String productItemId;
    /**
     * 类目主键
     */
    private String categoryId;

    private String categoryName;
    /**
     * 品牌主键
     */
    private String brandId;
    /**
     * 总金额
     */
    private BigDecimal amount;

    /**
     * 商品定金
     */
    private BigDecimal deposit;

    /**
     * 实际购买单价
     */
    private BigDecimal price;
    /**
     * 购买数量
     */
    private Integer quantity;


    // 尾款
    /**
     * 商品需要支付的尾款
     */
    private BigDecimal transactionBalance;


    /**
     * 计量单位描述
     */
    private String unit;
    /**
     * 商品名称
     */
    private String productName;


    /**
     * 商品图片
     */
    private String pictureCode;

    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 规格一(暂时字段，后会删除)
     */
    private String productStandardMust;
    /**
     * 规格二(暂时字段，后会删除)
     */
    private String productOptionalFirst;
    /**
     * 规格三(暂时字段，后会删除)
     */
    private String productOptionalSecond;




    public BigDecimal getTransactionBalance() {

        if (transactionBalance == null) {
            if (deposit != null) {
                transactionBalance = deposit.multiply(deposit);
            }
        }

        return transactionBalance;
    }

    public void setTransactionBalance(BigDecimal transactionBalance) {
        this.transactionBalance = transactionBalance;
    }



    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductItemId() {
        return productItemId;
    }

    public void setProductItemId(String productItemId) {
        this.productItemId = productItemId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPictureCode() {
        return pictureCode;
    }

    public void setPictureCode(String pictureCode) {
        this.pictureCode = pictureCode;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getProductStandardMust() {
        return productStandardMust;
    }

    public void setProductStandardMust(String productStandardMust) {
        this.productStandardMust = productStandardMust;
    }

    public String getProductOptionalFirst() {
        return productOptionalFirst;
    }

    public void setProductOptionalFirst(String productOptionalFirst) {
        this.productOptionalFirst = productOptionalFirst;
    }

    public String getProductOptionalSecond() {
        return productOptionalSecond;
    }

    public void setProductOptionalSecond(String productOptionalSecond) {
        this.productOptionalSecond = productOptionalSecond;
    }


    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

}

package com.hjh.mall.order.model;

import java.math.BigDecimal;

/**
 * 订单模块--订单明细表，记录具体订单商品信息 Created by qiuxianxiang on 17/5/11.
 */
public class OrderItemDomain {
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
	/**
	 * 计量单位描述
	 */
	private String unit;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 商品描述
	 */
	private String productDesc;
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

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderItemDomain [orderItemId=");
		builder.append(orderItemId);
		builder.append(", orderId=");
		builder.append(orderId);
		builder.append(", orderNo=");
		builder.append(orderNo);
		builder.append(", productId=");
		builder.append(productId);
		builder.append(", productItemId=");
		builder.append(productItemId);
		builder.append(", categoryId=");
		builder.append(categoryId);
		builder.append(", brandId=");
		builder.append(brandId);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", price=");
		builder.append(price);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", unit=");
		builder.append(unit);
		builder.append(", productName=");
		builder.append(productName);
		builder.append(", productDesc=");
		builder.append(productDesc);
		builder.append(", pictureCode=");
		builder.append(pictureCode);
		builder.append(", brandName=");
		builder.append(brandName);
		builder.append(", productStandardMust=");
		builder.append(productStandardMust);
		builder.append(", productOptionalFirst=");
		builder.append(productOptionalFirst);
		builder.append(", productOptionalSecond=");
		builder.append(productOptionalSecond);
		builder.append(", getOrderItemId()=");
		builder.append(getOrderItemId());
		builder.append(", getOrderId()=");
		builder.append(getOrderId());
		builder.append(", getOrderNo()=");
		builder.append(getOrderNo());
		builder.append(", getProductId()=");
		builder.append(getProductId());
		builder.append(", getProductItemId()=");
		builder.append(getProductItemId());
		builder.append(", getCategoryId()=");
		builder.append(getCategoryId());
		builder.append(", getBrandId()=");
		builder.append(getBrandId());
		builder.append(", getAmount()=");
		builder.append(getAmount());
		builder.append(", getPrice()=");
		builder.append(getPrice());
		builder.append(", getQuantity()=");
		builder.append(getQuantity());
		builder.append(", getUnit()=");
		builder.append(getUnit());
		builder.append(", getProductName()=");
		builder.append(getProductName());
		builder.append(", getProductDesc()=");
		builder.append(getProductDesc());
		builder.append(", getPictureCode()=");
		builder.append(getPictureCode());
		builder.append(", getBrandName()=");
		builder.append(getBrandName());
		builder.append(", getProductStandardMust()=");
		builder.append(getProductStandardMust());
		builder.append(", getProductOptionalFirst()=");
		builder.append(getProductOptionalFirst());
		builder.append(", getProductOptionalSecond()=");
		builder.append(getProductOptionalSecond());
		builder.append("]");
		return builder.toString();
	}
}

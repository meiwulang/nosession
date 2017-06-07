package com.hjh.mall.bizapi.biz.goods.middle.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Project: mall-goods-api
 * @Description 给订单系统提供的商品信息
 * @author 杨益桦
 * @date 2017年5月16日
 * @version V1.0
 */
public class Product4Order implements Serializable {

	private static final long serialVersionUID = 1L;
	private String productId;
	private String standardId;
	private String categoryId;// 三级类目id
	private String categoryName;// 三级类目名称
	private String brandId;
	private String brandName;
	private BigDecimal price;// 单价
	private int fontMoneyRate;// 定金比例
	private String productName;
	private String showUrl;// 首页展示图
	private String standard_must;// 必填规格
	private String optional_first;// 第一个选填规格
	private String optional_second;// 第二个选填规格
	private int maxSaleNum;// 最大采购量
	private int storeNum;// 库存量
	private int payType;// 支付类型  全款(0)、预付款(1)'
	private int prdtNum;// 购买数量
	private String unitName;// 计量单位
	
	
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public int getFontMoneyRate() {
		return fontMoneyRate;
	}
	public void setFontMoneyRate(int fontMoneyRate) {
		this.fontMoneyRate = fontMoneyRate;
	}
	public int getPrdtNum() {
		return prdtNum;
	}
	public void setPrdtNum(int prdtNum) {
		this.prdtNum = prdtNum;
	}
	public int getPayType() {
		return payType;
	}
	public void setPayType(int payType) {
		this.payType = payType;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getStandardId() {
		return standardId;
	}
	public void setStandardId(String standardId) {
		this.standardId = standardId;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
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
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getShowUrl() {
		return showUrl;
	}
	public void setShowUrl(String showUrl) {
		this.showUrl = showUrl;
	}
	public String getStandard_must() {
		return standard_must;
	}
	public void setStandard_must(String standard_must) {
		this.standard_must = standard_must;
	}
	public String getOptional_first() {
		return optional_first;
	}
	public void setOptional_first(String optional_first) {
		this.optional_first = optional_first;
	}
	public String getOptional_second() {
		return optional_second;
	}
	public void setOptional_second(String optional_second) {
		this.optional_second = optional_second;
	}
	public int getMaxSaleNum() {
		return maxSaleNum;
	}
	public void setMaxSaleNum(int maxSaleNum) {
		this.maxSaleNum = maxSaleNum;
	}
	public int getStoreNum() {
		return storeNum;
	}
	public void setStoreNum(int storeNum) {
		this.storeNum = storeNum;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Product4Order [productId=");
		builder.append(productId);
		builder.append(", standardId=");
		builder.append(standardId);
		builder.append(", categoryId=");
		builder.append(categoryId);
		builder.append(", categoryName=");
		builder.append(categoryName);
		builder.append(", brandId=");
		builder.append(brandId);
		builder.append(", brandName=");
		builder.append(brandName);
		builder.append(", price=");
		builder.append(price);
		builder.append(", fontMoneyRate=");
		builder.append(fontMoneyRate);
		builder.append(", productName=");
		builder.append(productName);
		builder.append(", showUrl=");
		builder.append(showUrl);
		builder.append(", standard_must=");
		builder.append(standard_must);
		builder.append(", optional_first=");
		builder.append(optional_first);
		builder.append(", optional_second=");
		builder.append(optional_second);
		builder.append(", maxSaleNum=");
		builder.append(maxSaleNum);
		builder.append(", storeNum=");
		builder.append(storeNum);
		builder.append(", payType=");
		builder.append(payType);
		builder.append(", prdtNum=");
		builder.append(prdtNum);
		builder.append(", unitName=");
		builder.append(unitName);
		builder.append(", getCategoryName()=");
		builder.append(getCategoryName());
		builder.append(", getUnitName()=");
		builder.append(getUnitName());
		builder.append(", getFontMoneyRate()=");
		builder.append(getFontMoneyRate());
		builder.append(", getPrdtNum()=");
		builder.append(getPrdtNum());
		builder.append(", getPayType()=");
		builder.append(getPayType());
		builder.append(", getProductId()=");
		builder.append(getProductId());
		builder.append(", getStandardId()=");
		builder.append(getStandardId());
		builder.append(", getCategoryId()=");
		builder.append(getCategoryId());
		builder.append(", getBrandId()=");
		builder.append(getBrandId());
		builder.append(", getBrandName()=");
		builder.append(getBrandName());
		builder.append(", getPrice()=");
		builder.append(getPrice());
		builder.append(", getProductName()=");
		builder.append(getProductName());
		builder.append(", getShowUrl()=");
		builder.append(getShowUrl());
		builder.append(", getStandard_must()=");
		builder.append(getStandard_must());
		builder.append(", getOptional_first()=");
		builder.append(getOptional_first());
		builder.append(", getOptional_second()=");
		builder.append(getOptional_second());
		builder.append(", getMaxSaleNum()=");
		builder.append(getMaxSaleNum());
		builder.append(", getStoreNum()=");
		builder.append(getStoreNum());
		builder.append("]");
		return builder.toString();
	}
	

}

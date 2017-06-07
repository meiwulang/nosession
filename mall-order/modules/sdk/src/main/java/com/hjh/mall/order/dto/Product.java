package com.hjh.mall.order.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by qiuxianxiang on 17/5/23.
 */
public class Product implements java.io.Serializable   {
    private static final long serialVersionUID = 1239999123L;


    /**
     * 商品主键
     */
    private String productId;

    /**
     * 类目主键
     */
    private String categoryId;
    /**
     * 品牌主键
     */
    private String brandId;


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

    private List<OrderItem> orderItemList;


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }
}

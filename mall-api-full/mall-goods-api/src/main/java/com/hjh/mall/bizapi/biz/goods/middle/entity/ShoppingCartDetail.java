package com.hjh.mall.bizapi.biz.goods.middle.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.hjh.mall.common.core.entity.Updatable;

public class ShoppingCartDetail extends Updatable implements Serializable {
	private static final long serialVersionUID = 179930528272961614L;

	// 主键
	private String shoppingCartDetailId;

	private String goods_name;

	// 必填规格
	private String standard_must;

	// 第一个选填规格
	private String optional_first;

	// 第二个选填规格
	private String optional_second;

	// 购买数量
	private Integer prdt_num;

	// 商品单价
	private BigDecimal price;

	// 商品规格编号
	private String standard_id;

	// 计量单位名称
	private String metadata_name;
	// 最大采购量，最大999
	private Integer max_sale_num;
	// 购物车编号
	private String shopping_cart_id;

	public String getShoppingCartDetailId() {
		return shoppingCartDetailId;
	}

	public void setShoppingCartDetailId(String shoppingCartDetailId) {
		this.shoppingCartDetailId = shoppingCartDetailId;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
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

	public Integer getPrdt_num() {
		return prdt_num;
	}

	public void setPrdt_num(Integer prdt_num) {
		this.prdt_num = prdt_num;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getStandard_id() {
		return standard_id;
	}

	public void setStandard_id(String standard_id) {
		this.standard_id = standard_id;
	}

	public String getMetadata_name() {
		return metadata_name;
	}

	public void setMetadata_name(String metadata_name) {
		this.metadata_name = metadata_name;
	}

	public Integer getMax_sale_num() {
		return max_sale_num;
	}

	public void setMax_sale_num(Integer max_sale_num) {
		this.max_sale_num = max_sale_num;
	}

	public String getShopping_cart_id() {
		return shopping_cart_id;
	}

	public void setShopping_cart_id(String shopping_cart_id) {
		this.shopping_cart_id = shopping_cart_id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private String shopping_cart_detail_id;

	public String getShopping_cart_detail_id() {
		return shopping_cart_detail_id;
	}

	public void setShopping_cart_detail_id(String shopping_cart_detail_id) {
		this.shopping_cart_detail_id = shopping_cart_detail_id;
	}

}
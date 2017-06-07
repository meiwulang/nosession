package com.hjh.mall.bizapi.biz.goods.middle.entity;

import java.io.Serializable;

import com.hjh.mall.common.core.entity.Updatable;

public class ShoppingCart extends Updatable implements Serializable {
	private static final long serialVersionUID = 769005357164786212L;

	// 购物车主键
	private String shoppingCartId;

	// 商品主键
	private String goods_id;

	// 商品名称
	private String goods_name;

	// 品牌拼音
	private String brand_spell;

	// 首页展示图
	private String show_url;

	// 品牌名称
	private String brand_name;
	// 品牌编号
	private String brand_id;

	// 用户编号
	private String clientId;

	// 规格编号集合，多个用逗号分隔
	private String standardIds;

	// 初始化日期
	private String initDate;

	// 初始化时间
	private String initTime;
	// 更新日期
	private String updateDate;
	// 更新时间
	private String updateTime;

	public String getShoppingCartId() {
		return shoppingCartId;
	}

	public void setShoppingCartId(String shoppingCartId) {
		this.shoppingCartId = shoppingCartId;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getShow_url() {
		return show_url;
	}

	public void setShow_url(String show_url) {
		this.show_url = show_url;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getStandardIds() {
		return standardIds;
	}

	public void setStandardIds(String standardIds) {
		this.standardIds = standardIds;
	}

	public String getInitDate() {
		return initDate;
	}

	public void setInitDate(String initDate) {
		this.initDate = initDate;
	}

	public String getInitTime() {
		return initTime;
	}

	public void setInitTime(String initTime) {
		this.initTime = initTime;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getBrand_spell() {
		return brand_spell;
	}

	public void setBrand_spell(String brand_spell) {
		this.brand_spell = brand_spell;
	}

	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}

}
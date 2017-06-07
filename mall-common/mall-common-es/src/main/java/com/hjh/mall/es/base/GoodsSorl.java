package com.hjh.mall.es.base;

import org.apache.solr.client.solrj.beans.Field;

public class GoodsSorl extends SolrQueryBaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Field
	private String id;// 主键
	@Field
	private String brand_name;// 品牌名称
	@Field
	private String goods_name;// 商品名称
	@Field
	private String goods_status;// 上架，下架状态
	@Field
	private String update_date;//
	@Field
	private Integer sort;// 排序字段
	@Field
	private String show_url;// 首页展示图
	@Field
	private String first_category_name;// 第一级类目名称
	@Field
	private String second_category_name;// 第二级类目名称
	@Field
	private String third_category_name;// 第三级类目名称
	@Field
	private String car_brand_name;// 车型品牌
	@Field
	private String min_price;// 最低价格
	@Field
	private String car_type;// 类型

	@Field
	private String categoryAll;
	@Field
	private Double sale_num;

	@Field
	private String car_models_name;// 车型型号

	@Field
	private Integer adapt_all_models;// 适配全部的机型

	public Integer getAdapt_all_models() {
		return adapt_all_models;
	}

	public void setAdapt_all_models(Integer adapt_all_models) {
		this.adapt_all_models = adapt_all_models;
	}

	public Double getSale_num() {
		return sale_num;
	}

	public void setSale_num(Double sale_num) {
		this.sale_num = sale_num;
	}

	public String getCategoryAll() {
		return categoryAll;
	}

	public void setCategoryAll(String categoryAll) {
		this.categoryAll = categoryAll;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getGoods_status() {
		return goods_status;
	}

	public void setGoods_status(String goods_status) {
		this.goods_status = goods_status;
	}

	public String getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getShow_url() {
		return show_url;
	}

	public void setShow_url(String show_url) {
		this.show_url = show_url;
	}

	public String getFirst_category_name() {
		return first_category_name;
	}

	public void setFirst_category_name(String first_category_name) {
		this.first_category_name = first_category_name;
	}

	public String getSecond_category_name() {
		return second_category_name;
	}

	public void setSecond_category_name(String second_category_name) {
		this.second_category_name = second_category_name;
	}

	public String getThird_category_name() {
		return third_category_name;
	}

	public void setThird_category_name(String third_category_name) {
		this.third_category_name = third_category_name;
	}

	public String getCar_brand_name() {
		return car_brand_name;
	}

	public void setCar_brand_name(String car_brand_name) {
		this.car_brand_name = car_brand_name;
	}

	public String getCar_models_name() {
		return car_models_name;
	}

	public void setCar_models_name(String car_models_name) {
		this.car_models_name = car_models_name;
	}

	public String getMin_price() {
		return min_price;
	}

	public void setMin_price(String min_price) {
		this.min_price = min_price;
	}

	public String getCar_type() {
		return car_type;
	}

	public void setCar_type(String car_type) {
		this.car_type = car_type;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GoodsSorl [id=");
		builder.append(id);
		builder.append(", brand_name=");
		builder.append(brand_name);
		builder.append(", goods_name=");
		builder.append(goods_name);
		builder.append(", goods_status=");
		builder.append(goods_status);
		builder.append(", update_date=");
		builder.append(update_date);
		builder.append(", sort=");
		builder.append(sort);
		builder.append(", show_url=");
		builder.append(show_url);
		builder.append(", first_category_name=");
		builder.append(first_category_name);
		builder.append(", second_category_name=");
		builder.append(second_category_name);
		builder.append(", third_category_name=");
		builder.append(third_category_name);
		builder.append(", car_brand_name=");
		builder.append(car_brand_name);
		builder.append(", car_models_name=");
		builder.append(car_models_name);
		builder.append(", min_price=");
		builder.append(min_price);
		builder.append(", car_type=");
		builder.append(car_type);
		builder.append(", categoryAll=");
		builder.append(categoryAll);
		builder.append(", sale_num=");
		builder.append(sale_num);
		builder.append(", adapt_all_models=");
		builder.append(adapt_all_models);
		builder.append(", access_token=");
		builder.append(access_token);
		builder.append(", functionId=");
		builder.append(functionId);
		builder.append("]");
		return builder.toString();
	}

}

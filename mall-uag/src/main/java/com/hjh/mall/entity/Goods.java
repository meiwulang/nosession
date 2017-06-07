package com.hjh.mall.entity;

import com.hjh.mall.common.core.entity.Updatable;

/**
 * @Project: hjh-mall
 * @Description 商品表
 * @author 杨益桦
 * @date 2017年2月18日
 * @version V1.0
 */
public class Goods extends Updatable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String goods_id;// 主键
	private String category_id;// 类目id
	private String brand_name;// 厂家名称
	private String factory_detail;// 厂家简介
	private String goods_name;// 商品名称
	private String price_area;// 价格区间
	private String banner_ids;// banner的pic id
	private String detail_ids;// 详情的pic id
	private String info_ids;// 商品介绍 id
	private String standard_ids;// 规格 id
	private String tel;// 规格 id
	private String goods_status;// 规格 id
	private String ad_url;// 规格 id
	private String update_date;//
	private String update_time;//
	private int sort;// 排序字段

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getGoods_status() {
		return goods_status;
	}

	public void setGoods_status(String goods_status) {
		this.goods_status = goods_status;
	}

	public String getAd_url() {
		return ad_url;
	}

	public void setAd_url(String ad_url) {
		this.ad_url = ad_url;
	}

	public String getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getPrice_area() {
		return price_area;
	}

	public void setPrice_area(String price_area) {
		this.price_area = price_area;
	}

	public String getBanner_ids() {
		return banner_ids;
	}

	public void setBanner_ids(String banner_ids) {
		this.banner_ids = banner_ids;
	}

	public String getDetail_ids() {
		return detail_ids;
	}

	public void setDetail_ids(String detail_ids) {
		this.detail_ids = detail_ids;
	}

	public String getInfo_ids() {
		return info_ids;
	}

	public void setInfo_ids(String info_ids) {
		this.info_ids = info_ids;
	}

	public String getStandard_ids() {
		return standard_ids;
	}

	public void setStandard_ids(String standard_ids) {
		this.standard_ids = standard_ids;
	}

	public String getFactory_detail() {
		return factory_detail;
	}

	public void setFactory_detail(String factory_detail) {
		this.factory_detail = factory_detail;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Goods [goods_id=");
		builder.append(goods_id);
		builder.append(", category_id=");
		builder.append(category_id);
		builder.append(", brand_name=");
		builder.append(brand_name);
		builder.append(", factory_detail=");
		builder.append(factory_detail);
		builder.append(", goods_name=");
		builder.append(goods_name);
		builder.append(", price_area=");
		builder.append(price_area);
		builder.append(", banner_ids=");
		builder.append(banner_ids);
		builder.append(", detail_ids=");
		builder.append(detail_ids);
		builder.append(", info_ids=");
		builder.append(info_ids);
		builder.append(", standard_ids=");
		builder.append(standard_ids);
		builder.append(", tel=");
		builder.append(tel);
		builder.append(", goods_status=");
		builder.append(goods_status);
		builder.append(", ad_url=");
		builder.append(ad_url);
		builder.append(", update_date=");
		builder.append(update_date);
		builder.append(", update_time=");
		builder.append(update_time);
		builder.append(", sort=");
		builder.append(sort);
		builder.append(", update_version=");
		builder.append(update_version);
		builder.append(", update_user=");
		builder.append(update_user);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", status=");
		builder.append(status);
		builder.append(", init_date=");
		builder.append(init_date);
		builder.append(", init_time=");
		builder.append(init_time);
		builder.append(", page=");
		builder.append(page);
		builder.append(", sortMarkers=");
		builder.append(sortMarkers);
		builder.append(", ids=");
		builder.append(ids);
		builder.append("]");
		return builder.toString();
	}

}

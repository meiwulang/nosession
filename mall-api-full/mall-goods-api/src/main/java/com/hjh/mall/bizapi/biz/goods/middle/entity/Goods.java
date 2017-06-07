package com.hjh.mall.bizapi.biz.goods.middle.entity;

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
	private String category_id;// 类目id，不用了
	private String brand_name;// 厂家名称
	private String factory_detail;// 厂家简介,预留
	private String goods_name;// 商品名称
	private String price_area;// 价格区间,预留
	private String banner_ids;// banner的pic id
	private String detail_ids;// 详情的pic id
	private String info_ids;// 商品介绍 id
	private String standard_ids;// 规格 id
	private String tel;// 电话
	private String goods_status;// 上架，下架状态
	private String ad_url;// 占位图
	private String update_date;//
	private String update_time;//
	private int sort;// 排序字段
	private String brand_id;// 品牌id
	private String update_user_name;// 操作人姓名
	private String unit_id;// 计量单位id
	private String unit_name;// 计量单位名称
	private String show_url;// 首页展示图
	private String first_category_id;// 第一级类目id
	private String first_category_name;// 第一级类目名称
	private String second_category_id;// 第二级类目id
	private String second_category_name;// 第二级类目名称
	private String third_category_id;// 三级类目id,区分原有categor id
	private String third_category_name;// 第三级类目名称
	private String create_user_id;// 创建人id
	private String create_user_name;// 创建人名称
	private String min_price;// 最低价格
	private Double sale_num;// 总销量

	private Integer adapt_all_models;// 适配全部的机型
	
	private Integer pay_type;// 支付类型：全款(0)、预付款(1)
	private Integer font_money_rate;// 定金比例，与pay_type有关联

	public Integer getPay_type() {
		return pay_type;
	}

	public void setPay_type(Integer pay_type) {
		this.pay_type = pay_type;
	}

	public Integer getFont_money_rate() {
		return font_money_rate;
	}

	public void setFont_money_rate(Integer font_money_rate) {
		this.font_money_rate = font_money_rate;
	}

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

	public String getMin_price() {
		return min_price;
	}

	public void setMin_price(String min_price) {
		this.min_price = min_price;
	}

	public String getUnit_name() {
		return unit_name;
	}

	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}

	public String getCreate_user_id() {
		return create_user_id;
	}

	public void setCreate_user_id(String create_user_id) {
		this.create_user_id = create_user_id;
	}

	public String getCreate_user_name() {
		return create_user_name;
	}

	public void setCreate_user_name(String create_user_name) {
		this.create_user_name = create_user_name;
	}

	public String getFirst_category_id() {
		return first_category_id;
	}

	public void setFirst_category_id(String first_category_id) {
		this.first_category_id = first_category_id;
	}

	public String getFirst_category_name() {
		return first_category_name;
	}

	public void setFirst_category_name(String first_category_name) {
		this.first_category_name = first_category_name;
	}

	public String getSecond_category_id() {
		return second_category_id;
	}

	public void setSecond_category_id(String second_category_id) {
		this.second_category_id = second_category_id;
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

	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}

	public String getThird_category_id() {
		return third_category_id;
	}

	public void setThird_category_id(String third_category_id) {
		this.third_category_id = third_category_id;
	}

	public String getUpdate_user_name() {
		return update_user_name;
	}

	public void setUpdate_user_name(String update_user_name) {
		this.update_user_name = update_user_name;
	}

	public String getUnit_id() {
		return unit_id;
	}

	public void setUnit_id(String unit_id) {
		this.unit_id = unit_id;
	}

	public String getShow_url() {
		return show_url;
	}

	public void setShow_url(String show_url) {
		this.show_url = show_url;
	}

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
		builder.append(", brand_id=");
		builder.append(brand_id);
		builder.append(", update_user_name=");
		builder.append(update_user_name);
		builder.append(", unit_id=");
		builder.append(unit_id);
		builder.append(", unit_name=");
		builder.append(unit_name);
		builder.append(", show_url=");
		builder.append(show_url);
		builder.append(", first_category_id=");
		builder.append(first_category_id);
		builder.append(", first_category_name=");
		builder.append(first_category_name);
		builder.append(", second_category_id=");
		builder.append(second_category_id);
		builder.append(", second_category_name=");
		builder.append(second_category_name);
		builder.append(", third_category_id=");
		builder.append(third_category_id);
		builder.append(", third_category_name=");
		builder.append(third_category_name);
		builder.append(", create_user_id=");
		builder.append(create_user_id);
		builder.append(", create_user_name=");
		builder.append(create_user_name);
		builder.append(", min_price=");
		builder.append(min_price);
		builder.append(", sale_num=");
		builder.append(sale_num);
		builder.append(", adapt_all_models=");
		builder.append(adapt_all_models);
		builder.append(", pay_type=");
		builder.append(pay_type);
		builder.append(", font_money_rate=");
		builder.append(font_money_rate);
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

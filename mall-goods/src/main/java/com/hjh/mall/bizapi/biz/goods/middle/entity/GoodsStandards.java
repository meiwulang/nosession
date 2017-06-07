package com.hjh.mall.bizapi.biz.goods.middle.entity;

import java.math.BigDecimal;

import com.hjh.mall.common.core.entity.Updatable;

/**
 * @Project: hjh-mall
 * @Description 商品规格
 * @author 杨益桦
 * @date 2017年2月18日
 * @version V1.0
 */
public class GoodsStandards extends Updatable {
	private static final long serialVersionUID = 1L;
	private String standard_id;// 主键
	private String standard_must;// 必填规格
	private String optional_first;// 第一个选填规格
	private String optional_second;// 第二个选填规格
	private BigDecimal price;// 价格
	private String update_date;//
	private String update_time;//
	private double store_num;// 库存数量
	private double sale_num;// 已售数量
	private Integer max_sale_num;// 最大采购量
	private BigDecimal reference_price;//参考价格
	

	public BigDecimal getReference_price() {
		return reference_price;
	}

	public void setReference_price(BigDecimal reference_price) {
		this.reference_price = reference_price;
	}

	public Integer getMax_sale_num() {
		return max_sale_num;
	}

	public void setMax_sale_num(Integer max_sale_num) {
		this.max_sale_num = max_sale_num;
	}

	public double getStore_num() {
		return store_num;
	}

	public void setStore_num(double store_num) {
		this.store_num = store_num;
	}

	public double getSale_num() {
		return sale_num;
	}

	public void setSale_num(double sale_num) {
		this.sale_num = sale_num;
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

	public String getStandard_id() {
		return standard_id;
	}

	public void setStandard_id(String standard_id) {
		this.standard_id = standard_id;
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GoodsStandards [standard_id=");
		builder.append(standard_id);
		builder.append(", standard_must=");
		builder.append(standard_must);
		builder.append(", optional_first=");
		builder.append(optional_first);
		builder.append(", optional_second=");
		builder.append(optional_second);
		builder.append(", price=");
		builder.append(price);
		builder.append(", update_date=");
		builder.append(update_date);
		builder.append(", update_time=");
		builder.append(update_time);
		builder.append(", store_num=");
		builder.append(store_num);
		builder.append(", sale_num=");
		builder.append(sale_num);
		builder.append(", max_sale_num=");
		builder.append(max_sale_num);
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

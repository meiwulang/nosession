package com.hjh.mall.goods.bizapi.bizserver.vo;

import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;

/**
 * @Project: mall-goods-api
 * @Description
 * @author 杨益桦
 * @date 2017年3月17日
 * @version V1.0
 */
public class UpdateSolrVo extends HJYVO {

	private static final long serialVersionUID = 1L;

	@NotBlank
	private String goods_id;

	private String brand_names;// 品牌名称的集合，已逗号分隔
	private String car_models_names;// 型号名称的集合，已逗号分隔
	private String car_types;// 类型名称的集合，已逗号分隔

	private Integer adapt_all_models;// 适配全部的机型

	public Integer getAdapt_all_models() {
		return adapt_all_models;
	}

	public void setAdapt_all_models(Integer adapt_all_models) {
		this.adapt_all_models = adapt_all_models;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getBrand_names() {
		return brand_names;
	}

	public void setBrand_names(String brand_names) {
		this.brand_names = brand_names;
	}

	public String getCar_models_names() {
		return car_models_names;
	}

	public void setCar_models_names(String car_models_names) {
		this.car_models_names = car_models_names;
	}

	public String getCar_types() {
		return car_types;
	}

	public void setCar_types(String car_types) {
		this.car_types = car_types;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UpdateSolrVo [goods_id=");
		builder.append(goods_id);
		builder.append(", brand_names=");
		builder.append(brand_names);
		builder.append(", car_models_names=");
		builder.append(car_models_names);
		builder.append(", car_types=");
		builder.append(car_types);
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

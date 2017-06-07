package com.hjh.mall.category.bizapi.bizserver.car.vo;

import com.hjh.mall.common.core.vo.HJYVO;

/**
 * * @author csj:
 * 
 * @date 创建时间：2017年3月14日 下午2:14:25
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class QueryCarModelLike extends HJYVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 型号名称
	private String car_models_name;
	private String car_models_id;
	private String car_model_alias;
	private String brand_id;

	public String getCar_models_name() {
		return car_models_name;
	}

	public void setCar_models_name(String car_models_name) {
		this.car_models_name = car_models_name;
	}

	public String getCar_models_id() {
		return car_models_id;
	}

	public void setCar_models_id(String car_models_id) {
		this.car_models_id = car_models_id;
	}

	public String getCar_model_alias() {
		return car_model_alias;
	}

	public void setCar_model_alias(String car_model_alias) {
		this.car_model_alias = car_model_alias;
	}

	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QueryCarModelLike [car_models_name=");
		builder.append(car_models_name);
		builder.append(", car_models_id=");
		builder.append(car_models_id);
		builder.append(", car_model_alias=");
		builder.append(car_model_alias);
		builder.append(", brand_id=");
		builder.append(brand_id);
		builder.append("]");
		return builder.toString();
	}

}

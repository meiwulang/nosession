package com.hjh.mall.category.entity;

import java.io.Serializable;

import com.hjh.mall.common.core.entity.Updatable;

public class CarParams extends Updatable implements Serializable {
	private String car_params_id;

	// 参数名称
	private String car_params_name;

	// 参数值
	private String car_params_value;

	private static final long serialVersionUID = 1L;

	public String getCar_params_id() {
		return car_params_id;
	}

	public void setCar_params_id(String car_params_id) {
		this.car_params_id = car_params_id;
	}

	public String getCar_params_name() {
		return car_params_name;
	}

	public void setCar_params_name(String car_params_name) {
		this.car_params_name = car_params_name;
	}

	public String getCar_params_value() {
		return car_params_value;
	}

	public void setCar_params_value(String car_params_value) {
		this.car_params_value = car_params_value;
	}
}
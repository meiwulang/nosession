package com.hjh.mall.category.entity;

import java.io.Serializable;

import com.hjh.mall.common.core.entity.Updatable;

public class CarModels extends Updatable implements Serializable {
	private String car_models_id;

	// 型号名称
	private String car_models_name;

	private String brand_id;

	private String metadata_id;

	private String create_date;

	private String create_time;

	private String update_date;

	private String update_time;

	private String update_user_name;
	private String car_model_alias;
	// 参数集合
	private String car_params_ids;
	private String create_user;
	private String app_show;
	private static final long serialVersionUID = 1L;

	public String getCar_models_id() {
		return car_models_id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CarModels [car_models_id=");
		builder.append(car_models_id);
		builder.append(", car_models_name=");
		builder.append(car_models_name);
		builder.append(", brand_id=");
		builder.append(brand_id);
		builder.append(", metadata_id=");
		builder.append(metadata_id);
		builder.append(", create_date=");
		builder.append(create_date);
		builder.append(", create_time=");
		builder.append(create_time);
		builder.append(", update_date=");
		builder.append(update_date);
		builder.append(", update_time=");
		builder.append(update_time);
		builder.append(", update_user_name=");
		builder.append(update_user_name);
		builder.append(", car_model_alias=");
		builder.append(car_model_alias);
		builder.append(", car_params_ids=");
		builder.append(car_params_ids);
		builder.append(", create_user=");
		builder.append(create_user);
		builder.append(", app_show=");
		builder.append(app_show);
		builder.append("]");
		return builder.toString();
	}

	public void setCar_models_id(String car_models_id) {
		this.car_models_id = car_models_id;
	}

	public String getCar_models_name() {
		return car_models_name;
	}

	public void setCar_models_name(String car_models_name) {
		this.car_models_name = car_models_name;
	}

	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}

	public String getMetadata_id() {
		return metadata_id;
	}

	public void setMetadata_id(String metadata_id) {
		this.metadata_id = metadata_id;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
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

	public String getUpdate_user_name() {
		return update_user_name;
	}

	public void setUpdate_user_name(String update_user_name) {
		this.update_user_name = update_user_name;
	}

	public String getCar_params_ids() {
		return car_params_ids;
	}

	public void setCar_params_ids(String car_params_ids) {
		this.car_params_ids = car_params_ids;
	}

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public String getCar_model_alias() {
		return car_model_alias;
	}

	public void setCar_model_alias(String car_model_alias) {
		this.car_model_alias = car_model_alias;
	}

	public String getApp_show() {
		return app_show;
	}

	public void setApp_show(String app_show) {
		this.app_show = app_show;
	}
}
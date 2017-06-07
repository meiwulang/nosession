package com.hjh.mall.category.bizapi.bizserver.car.vo;

import java.util.List;

import com.hjh.mall.common.core.annotation.NotBlank;
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
public class AddCarModel extends HJYVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 型号名称
	@NotBlank
	private String car_models_name;
	@NotBlank
	private String brand_id;
	private String brand_name;
	@NotBlank
	private String metadata_id;
	@NotBlank
	private String status;
	@NotBlank
	private String app_show;

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public String getMetadata_name() {
		return metadata_name;
	}

	public void setMetadata_name(String metadata_name) {
		this.metadata_name = metadata_name;
	}

	private String metadata_name;
	private String create_date;

	private String create_time;

	private String update_date;

	private String update_time;

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

	public List<AddCarParams> getCarParamsList() {
		return carParamsList;
	}

	public void setCarParamsList(List<AddCarParams> carParamsList) {
		this.carParamsList = carParamsList;
	}

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	private String update_user_name;
	// 参数集合
	private List<AddCarParams> carParamsList;
	private String create_user;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApp_show() {
		return app_show;
	}

	public void setApp_show(String app_show) {
		this.app_show = app_show;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AddCarModel [car_models_name=");
		builder.append(car_models_name);
		builder.append(", brand_id=");
		builder.append(brand_id);
		builder.append(", brand_name=");
		builder.append(brand_name);
		builder.append(", metadata_id=");
		builder.append(metadata_id);
		builder.append(", status=");
		builder.append(status);
		builder.append(", app_show=");
		builder.append(app_show);
		builder.append(", metadata_name=");
		builder.append(metadata_name);
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
		builder.append(", carParamsList=");
		builder.append(carParamsList);
		builder.append(", create_user=");
		builder.append(create_user);
		builder.append("]");
		return builder.toString();
	}
}

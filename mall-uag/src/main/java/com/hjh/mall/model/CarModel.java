package com.hjh.mall.model;

import com.hjh.mall.common.core.annotation.ExcelResources;

/**
 * @Project: hjy-middle
 * @Description TODO
 * @author 陈士俊
 * @date 2017年2月7日
 * @version V1.0
 */
public class CarModel {

	private String car_models_id;// 代码
	private String brand_name;// 品牌
	private String metadata_name;// 类型
	private String car_models_name;// 型号
	private String carParamsList;// 参数

	private String create_date;// 创建日期
	private String create_user;// 创建者

	@ExcelResources(title = "代码", order = 1)
	public String getCar_models_id() {
		return car_models_id;
	}

	public void setCar_models_id(String car_models_id) {
		this.car_models_id = car_models_id;
	}

	@ExcelResources(title = "品牌", order = 2)
	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	@ExcelResources(title = "类型", order = 3)
	public String getMetadata_name() {
		return metadata_name;
	}

	public void setMetadata_name(String metadata_name) {
		this.metadata_name = metadata_name;
	}

	@ExcelResources(title = "型号", order = 4)
	public String getCar_models_name() {
		return car_models_name;
	}

	public void setCar_models_name(String car_models_name) {
		this.car_models_name = car_models_name;
	}

	@ExcelResources(title = "参数", order = 5)
	public String getCarParamsList() {
		return carParamsList;
	}

	public void setCarParamsList(String carParamsList) {
		this.carParamsList = carParamsList;
	}

	@ExcelResources(title = "创建日期", order = 6)
	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	@ExcelResources(title = "创建者", order = 6)
	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	@ExcelResources(title = "最后操作日期", order = 6)
	public String getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}

	@ExcelResources(title = "最后操作者", order = 6)
	public String getUpdate_user_name() {
		return update_user_name;
	}

	public void setUpdate_user_name(String update_user_name) {
		this.update_user_name = update_user_name;
	}

	@ExcelResources(title = "状态", order = 6)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private String update_date;// 最后操作日期
	private String update_user_name;// 最后操作人
	private String status;// 状态

}

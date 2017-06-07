/** * @author  csj
 * @date 创建时间：2017年3月14日 下午2:29:11 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  */
package com.hjh.mall.category.bizapi.bizserver.car.vo;

import com.hjh.mall.common.core.vo.PageQueryVO;

public class QueryCarModels extends PageQueryVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 型号名称
	private String car_models_id;
	// 型号名称
	private String car_models_name;
	private String brand_id;
	private String brand_name;
	private String metadata_id;
	private String metadata_name;
	private String start_date;

	private String end_date;

	// 有无参数(0:无，1：有)
	private String is_noparams;
	private String car_model_alias;
	
	private String app_show;//是否显示
	private String status;//启用禁用状态
	
	public String getApp_show() {
		return app_show;
	}

	public void setApp_show(String app_show) {
		this.app_show = app_show;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCar_models_id() {
		return car_models_id;
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

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getIs_noparams() {
		return is_noparams;
	}

	public void setIs_noparams(String is_noparams) {
		this.is_noparams = is_noparams;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getCar_model_alias() {
		return car_model_alias;
	}

	public void setCar_model_alias(String car_model_alias) {
		this.car_model_alias = car_model_alias;
	}

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QueryCarModels [car_models_id=");
		builder.append(car_models_id);
		builder.append(", car_models_name=");
		builder.append(car_models_name);
		builder.append(", brand_id=");
		builder.append(brand_id);
		builder.append(", brand_name=");
		builder.append(brand_name);
		builder.append(", metadata_id=");
		builder.append(metadata_id);
		builder.append(", metadata_name=");
		builder.append(metadata_name);
		builder.append(", start_date=");
		builder.append(start_date);
		builder.append(", end_date=");
		builder.append(end_date);
		builder.append(", is_noparams=");
		builder.append(is_noparams);
		builder.append(", car_model_alias=");
		builder.append(car_model_alias);
		builder.append("]");
		return builder.toString();
	}

}

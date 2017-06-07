package com.hjh.mall.model;

import com.hjh.mall.common.core.annotation.ExcelResources;

/**
 * @Project: mall-web
 * @Description TODO
 * @author 李慧峰
 * @date 2017年3月18日
 * @version V1.0 
 */
public class BrandModel {
	private String brand_id;
	private String brand_name;
	private String remark;
	private String model_num;
	
	@ExcelResources(title="编号",order=1)
	public String getBrand_id() {
		return brand_id;
	}
	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}
	@ExcelResources(title="品牌名称",order=2)
	public String getBrand_name() {
		return brand_name;
	}
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
	@ExcelResources(title="备注",order=3)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@ExcelResources(title="数量",order=4)
	public String getModel_num() {
		return model_num;
	}
	public void setModel_num(String model_num) {
		this.model_num = model_num;
	}
	
}

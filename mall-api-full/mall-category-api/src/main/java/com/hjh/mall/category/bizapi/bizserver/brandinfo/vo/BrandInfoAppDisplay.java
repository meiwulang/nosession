package com.hjh.mall.category.bizapi.bizserver.brandinfo.vo;

import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.field.type.Status;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: mall-category-api
 * @Description TODO
 * @author 李慧峰
 * @date 2017年5月8日
 * @version V1.0 
 */
public class BrandInfoAppDisplay extends HJYVO{
	private static final long serialVersionUID = 1L;
	@NotBlank
	@ApiModelProperty(name = "brand_info_id",dataType="String",notes="id")
	private String brand_info_id;

    // app是否
	@NotBlank
	@EnumValue(enumClass = Status.class)
	@ApiModelProperty(name = "app_display",dataType="String",notes="app是否")
    private String app_display;

    private String update_user;
    
    
	public String getBrand_info_id() {
		return brand_info_id;
	}

	public void setBrand_info_id(String brand_info_id) {
		this.brand_info_id = brand_info_id;
	}


	public String getApp_display() {
		return app_display;
	}

	public void setApp_display(String app_display) {
		this.app_display = app_display;
	}


	public String getUpdate_user() {
		return update_user;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
    
}

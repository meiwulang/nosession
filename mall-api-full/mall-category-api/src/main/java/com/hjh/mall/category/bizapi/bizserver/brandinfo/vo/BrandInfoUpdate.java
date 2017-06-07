package com.hjh.mall.category.bizapi.bizserver.brandinfo.vo;

import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.annotation.Range;
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
public class BrandInfoUpdate extends HJYVO{
	private static final long serialVersionUID = 1L;
	@NotBlank
	@ApiModelProperty(name = "brand_info_id",dataType="String",notes="id")
	private String brand_info_id;

    // 品牌介绍图片地址 
	@ApiModelProperty(name = "brand_info_img",dataType="String",notes="品牌介绍图片地址 ")
    private String brand_info_img;

    // 排序字段 
	@Range(max=999,min=1)
	@ApiModelProperty(name = "sort",dataType="String",notes="排序字段")
    private Integer sort;

    // 阿里云oss存储路径 
	@ApiModelProperty(name = "oss_url",dataType="String",notes="阿里云oss存储路径")
    private String oss_url;

    // 商品品牌id 
	@ApiModelProperty(name = "brand_id",dataType="String",notes="商品品牌id")
    private String brand_id;

    // app是否 
    @NotBlank
	@EnumValue(enumClass = Status.class)
    @ApiModelProperty(name = "app_display",dataType="String",notes="app是否 ")
    private String app_display;

    // 商品详情内容 
    @ApiModelProperty(name = "brand_info_content",dataType="String",notes="商品详情内容 ")
    private String brand_info_content;
    
    private String update_user;
    @NotBlank
	@EnumValue(enumClass = Status.class)
    private String status;
    
    private String remark;
    
    

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBrand_info_id() {
		return brand_info_id;
	}

	public void setBrand_info_id(String brand_info_id) {
		this.brand_info_id = brand_info_id;
	}

	public String getBrand_info_img() {
		return brand_info_img;
	}

	public void setBrand_info_img(String brand_info_img) {
		this.brand_info_img = brand_info_img;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getOss_url() {
		return oss_url;
	}

	public void setOss_url(String oss_url) {
		this.oss_url = oss_url;
	}

	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}

	public String getApp_display() {
		return app_display;
	}

	public void setApp_display(String app_display) {
		this.app_display = app_display;
	}

	public String getBrand_info_content() {
		return brand_info_content;
	}

	public void setBrand_info_content(String brand_info_content) {
		this.brand_info_content = brand_info_content;
	}

	public String getUpdate_user() {
		return update_user;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
    
}

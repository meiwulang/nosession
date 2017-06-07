package com.hjh.mall.category.bizapi.bizserver.brandinfo.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: mall-category-api
 * @Description TODO
 * @author 李慧峰
 * @date 2017年5月8日
 * @version V1.0 
 */
public class BrandInfoAppVo  implements Serializable{
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(name = "brand_info_id",dataType="String",notes="id")
	private String brand_info_id;

    // 品牌介绍图片地址 
	@ApiModelProperty(name = "brand_info_img",dataType="String",notes="品牌介绍图片地址 ")
    private String brand_info_img;

    // 更新年月日 
    private String update_date;

    // 更新时分秒 
    private String update_time;

    // 创建者id 
    @ApiModelProperty(name = "create_user",dataType="String",notes="创建者id")
    private String create_user;

    // 排序字段 
    @ApiModelProperty(name = "sort",dataType="String",notes="排序字段 ")
    private Integer sort;

    // 阿里云oss存储路径 
    @ApiModelProperty(name = "oss_url",dataType="String",notes="阿里云oss存储路径")
    private String oss_url;

    // 商品品牌id 
    @ApiModelProperty(name = "brand_id",dataType="String",notes="商品品牌id ")
    private String brand_id;
    
    // 商品品牌名称
    @ApiModelProperty(name = "brand_name",dataType="String",notes="商品品牌名称")
    private String brand_name;

    // app是否 
    @ApiModelProperty(name = "app_display",dataType="String",notes="app是否 ")
    private String app_display;

    // 商品详情内容
    @ApiModelProperty(name = "brand_info_content",dataType="String",notes="商品详情内容")
    private String brand_info_content;

    
	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
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

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
}

package com.hjh.mall.category.bizapi.bizserver.brandinfo.vo;

import com.hjh.mall.common.core.vo.HJYVO;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: mall-category-api
 * @Description TODO
 * @author 李慧峰
 * @date 2017年5月8日
 * @version V1.0 
 */
public class BrandInfoStatus extends HJYVO{
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(name = "brand_info_id",dataType="String",notes="id")
	private String brand_info_id;

    // 品牌介绍图片地址 
    private String brand_info_img;

    // 创建者id 
    private String create_user;

    // 排序字段 
    private Integer sort;

    // 阿里云oss存储路径 
    private String oss_url;

    // 商品品牌id 
    private String brand_id;

    // app是否 
    private String app_display;

    // 商品详情内容 
    private String brand_info_content;
    
    private String update_user;
    @ApiModelProperty(name = "status",dataType="String",notes="状态")
    private String status;
    
    

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

	public String getUpdate_user() {
		return update_user;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
    
}

package com.hjh.mall.category.bizapi.bizserver.brandinfo.vo;

import org.hibernate.validator.constraints.NotBlank;

import com.hjh.mall.common.core.annotation.EnumValue;
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
public class BrandInfoAdd extends HJYVO{
	private static final long serialVersionUID = 1L;


    // 品牌介绍图片地址 
	@NotBlank
	@ApiModelProperty(name = "brand_info_img",dataType="String",notes="品牌介绍图片地址 ")
    private String brand_info_img;

    // 创建者id 
	@ApiModelProperty(name = "create_user",dataType="String",notes="创建者id")
    private String create_user;

    // 排序字段 
	@Range(max=999,min=1)
	@ApiModelProperty(name = "sort",dataType="String",notes="排序字段 ")
    private Integer sort;

    // 阿里云oss存储路径 
	@ApiModelProperty(name = "oss_url",dataType="String",notes="阿里云oss存储路径 ")
    private String oss_url;

    // app是否
    @NotBlank
	@EnumValue(enumClass = Status.class)
    @ApiModelProperty(name = "app_display",dataType="String",notes="app是否")
    private String app_display;

    // 商品详情内容 
    @NotBlank
    @ApiModelProperty(name = "brand_info_content",dataType="String",notes="商品详情内容 ")
    private String brand_info_content;
    
    private String update_user;
    @ApiModelProperty(name = "remark",dataType="String",notes="remark")
    private String remark;
    
    private String status;
    
    private String brand_id;
    
    
    

	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUpdate_user() {
		return update_user;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BrandInfoAdd");
		builder.append(", brand_info_img=");
		builder.append(brand_info_img);
		builder.append(", create_user=");
		builder.append(create_user);
		builder.append(", sort=");
		builder.append(sort);
		builder.append(", oss_url=");
		builder.append(oss_url);
		builder.append(", app_display=");
		builder.append(app_display);
		builder.append(", brand_info_content=");
		builder.append(brand_info_content);
		builder.append("]");
		return builder.toString();
	}
    
    
}

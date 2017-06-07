package com.hjh.mall.category.entity;

import com.hjh.mall.common.core.entity.Updatable;

public class BrandInfo extends Updatable {
    private static final long serialVersionUID = 1L;

	private String brand_info_id;

    // 品牌介绍图片地址 
    private String brand_info_img;

    // 更新年月日 
    private String update_date;

    // 更新时分秒 
    private String update_time;
    
    // 创建年月日 
    private String init_date;

    // 创建时分秒 
    private String init_time;

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

	public String getInit_date() {
		return init_date;
	}

	public void setInit_date(String init_date) {
		this.init_date = init_date;
	}

	public String getInit_time() {
		return init_time;
	}

	public void setInit_time(String init_time) {
		this.init_time = init_time;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BrandInfo [brand_info_id=");
		builder.append(brand_info_id);
		builder.append(", brand_info_img=");
		builder.append(brand_info_img);
		builder.append(", update_date=");
		builder.append(update_date);
		builder.append(", update_time=");
		builder.append(update_time);
		builder.append(", create_user=");
		builder.append(create_user);
		builder.append(", sort=");
		builder.append(sort);
		builder.append(", oss_url=");
		builder.append(oss_url);
		builder.append(", brand_id=");
		builder.append(brand_id);
		builder.append(", app_display=");
		builder.append(app_display);
		builder.append(", brand_info_content=");
		builder.append(brand_info_content);
		builder.append("]");
		return builder.toString();
	}
    
}
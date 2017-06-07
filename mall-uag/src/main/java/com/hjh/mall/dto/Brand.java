package com.hjh.mall.dto;

import java.io.Serializable;

/**
 * @Project: mall-app
 * @Description TODO
 * @author 李慧峰
 * @date 2017年3月18日
 * @version V1.0 
 */
public class Brand implements Serializable{
	private static final long serialVersionUID = 1L;

	private String brand_id;
	
    private String brand_name;
    // logo url 地址 
    private String brand_logo;
    //品牌拼音
    private String pinyin;
    // 品牌拼音首字母
    private String pinyin_first;
	public String getBrand_id() {
		return brand_id;
	}
	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}
	public String getBrand_name() {
		return brand_name;
	}
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
	public String getBrand_logo() {
		return brand_logo;
	}
	public void setBrand_logo(String brand_logo) {
		this.brand_logo = brand_logo;
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public String getPinyin_first() {
		return pinyin_first;
	}
	public void setPinyin_first(String pinyin_first) {
		this.pinyin_first = pinyin_first;
	}
    


}

package com.hjh.mall.category.bizapi.bizserver.carbrand.vo;


import com.hjh.mall.common.core.annotation.Length;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;

import io.swagger.annotations.ApiModelProperty;


/**
 * @Project: mall-category-api
 * @Description TODO
 * @author 李慧峰
 * @date 2017年3月15日
 * @version V1.0 
 */
public class CarBrandUpdate extends HJYVO {

    private static final long serialVersionUID = 1L;
    @NotBlank
    @ApiModelProperty(name = "brand_id",dataType="String",notes="id")
	private String brand_id;
    // banner标题 
    @NotBlank
    @ApiModelProperty(name = "brand_name",dataType="String",notes="品牌名称")
    private String brand_name;
    // logo url 地址 
    @ApiModelProperty(name = "brand_logo",dataType="String",notes="地址")
    private String brand_logo;
    // 排序字段
    @ApiModelProperty(name = "sort",dataType="String",notes="排序字段")
    private Integer sort;
    // 备注 
    @ApiModelProperty(name = "remark",dataType="String",notes="备注")
    private String remark;
    @ApiModelProperty(name = "status",dataType="String",notes="状态")
    private String status;
    // 品牌拼音简写 
    @Length(max=50)
    @ApiModelProperty(name = "pinyin",dataType="String",notes="品牌拼音简写")
    private String pinyin;

    // 是否首页显示 
    @ApiModelProperty(name = "is_top",dataType="String",notes="是否首页显示 ")
    private String is_top;
    //更新人
    private String update_user;
    
    private String update_user_name;
	public String getUpdate_user_name() {
		return update_user_name;
	}
	public void setUpdate_user_name(String update_user_name) {
		this.update_user_name = update_user_name;
	}
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
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
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
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public String getIs_top() {
		return is_top;
	}
	public void setIs_top(String is_top) {
		this.is_top = is_top;
	}
	public String getUpdate_user() {
		return update_user;
	}
	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}

}

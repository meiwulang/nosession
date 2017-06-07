package com.hjh.mall.category.bizapi.bizserver.carbrand.vo;



import com.hjh.mall.common.core.vo.HJYVO;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: mall-category-api
 * @Description TODO
 * @author 李慧峰
 * @date 2017年3月15日
 * @version V1.0 
 */
public class CarBrandSort extends HJYVO {
    private static final long serialVersionUID = 1L;
	// 品牌名称
    @ApiModelProperty(name = "brand_id",dataType="String",notes="品牌名称")
    private String brand_id;
    // 排序字段 
    @ApiModelProperty(name = "sort",dataType="String",notes="排序字段 ")
    private Integer sort;

    // 是否首页显示 
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

	public String getUpdate_user() {
		return update_user;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}

	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getIs_top() {
		return is_top;
	}

	public void setIs_top(String is_top) {
		this.is_top = is_top;
	}

}

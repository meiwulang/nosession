package com.hjh.mall.category.bizapi.bizserver.carbrand.vo;

import com.hjh.mall.common.core.vo.PageQueryVO;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: mall-category-api
 * @Description TODO
 * @author 李慧峰
 * @date 2017年3月15日
 * @version V1.0 
 */
public class CarBrandQueryById extends PageQueryVO {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(name = "brand_id",dataType="String",notes="brand_id")
	private String brand_id;
   

	/**
	 * @param brand_id
	 */
	public CarBrandQueryById(String brand_id) {
		super();
		this.brand_id = brand_id;
	}

	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}
}

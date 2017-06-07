package com.hjh.mall.category.bizapi.bizserver.goodsbrand.vo;

import com.hjh.mall.common.core.vo.PageQueryVO;

/**
 * @Project: mall-category-api
 * @Description TODO
 * @author 李慧峰
 * @date 2017年3月15日
 * @version V1.0 
 */
public class GoodsBrandQueryById extends PageQueryVO {

    /**
	 * @param brand_id
	 */
	public GoodsBrandQueryById(String brand_id) {
		super();
		this.brand_id = brand_id;
	}

	private static final long serialVersionUID = 1L;
	private String brand_id;
   

	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}
}

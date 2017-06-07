package com.hjh.mall.category.bizapi.bizserver.metadata.vo;

import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;

/**
 * @Project: mall-web
 * @Description TODO
 * @author 曾繁林
 * @date 2017年3月16日
 * @version V1.0
 */
public class GetMetadataByBrand extends HJYVO {
	private static final long serialVersionUID = 1L;
	@NotBlank
	private String brand_id;

	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}

}

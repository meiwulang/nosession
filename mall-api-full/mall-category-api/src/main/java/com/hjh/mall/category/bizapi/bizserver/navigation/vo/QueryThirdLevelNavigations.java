package com.hjh.mall.category.bizapi.bizserver.navigation.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: mall-category-api-brunch
 * @Description 修改导航vo
 * @author 王斌
 * @date 2017年3月14日
 * @since 1.0
 */
public class QueryThirdLevelNavigations extends QuerySecondLevelNavigations {
	private static final long serialVersionUID = 1L;

	// 1级名称
	@ApiModelProperty(value = "祖级名称", required = false)
	private String grandName;

	public String getGrandName() {
		return grandName;
	}

	public void setGrandName(String grandName) {
		this.grandName = grandName;
	}

}

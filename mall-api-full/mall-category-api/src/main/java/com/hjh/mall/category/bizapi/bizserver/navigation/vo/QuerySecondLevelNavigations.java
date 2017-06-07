package com.hjh.mall.category.bizapi.bizserver.navigation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: mall-category-api-brunch
 * @Description 修改导航vo
 * @author 王斌
 * @date 2017年3月14日
 * @since 1.0
 */
@ApiModel
public class QuerySecondLevelNavigations extends QueryFirstLevelNavigations {
	private static final long serialVersionUID = 1L;
	// 1级名称
	@ApiModelProperty(value = "父级名称", required = false)
	protected String fatherName;

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

}

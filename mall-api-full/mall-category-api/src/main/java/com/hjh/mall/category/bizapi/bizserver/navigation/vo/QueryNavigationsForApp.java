package com.hjh.mall.category.bizapi.bizserver.navigation.vo;

import com.hjh.mall.common.core.vo.HJYVO;

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
public class QueryNavigationsForApp extends HJYVO {
	private static final long serialVersionUID = 1L;

	// 层级
	@ApiModelProperty(value = "level", required = true)
	private int level;
	// 总数
	@ApiModelProperty(value = "总数", required = true)
	private int size;

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}

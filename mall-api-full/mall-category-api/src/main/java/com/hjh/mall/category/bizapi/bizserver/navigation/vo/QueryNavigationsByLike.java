package com.hjh.mall.category.bizapi.bizserver.navigation.vo;

import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.PageQueryVO;
import com.hjh.mall.field.type.Status;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: mall-category-api-brunch
 * @Description 模糊查询
 * @author 王斌
 * @date 2017年3月14日
 * @since 1.0
 */
@ApiModel
public class QueryNavigationsByLike extends PageQueryVO {
	protected static final long serialVersionUID = 1L;

	// 名称
	@NotBlank
	@ApiModelProperty(value = "名称", required = true)
	private String categoryName;
	// 层级
	@NotBlank
	@ApiModelProperty(value = "层级", required = true)
	private String level;
	@EnumValue(enumClass = Status.class)

	@ApiModelProperty(value = "后台状态", required = false)
	private String status;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}

package com.hjh.mall.category.bizapi.bizserver.navigation.vo;

import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.field.type.Status;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: mall-category-api-brunch
 * @Description 修改单条导航状态 vo
 * @author 王斌
 * @date 2017年3月14日
 * @since 1.0
 */
@ApiModel("修改单条导航状态 ")
public class UpdatesingleNavigationStatus extends HJYVO {
	private static final long serialVersionUID = 1L;

	// 主键
	@NotBlank
	@ApiModelProperty(value = "主键", required = true)
	private String categoryId;
	@NotBlank
	@ApiModelProperty(value = "1或2", required = true)
	@EnumValue(enumClass = Status.class)
	private String status;
	// 操作人编号
	@ApiModelProperty(value = "操作人编号", required = false)
	private String updateUser;

	// 操作人
	@ApiModelProperty(value = "操作人", required = false)
	private String updater;

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

}

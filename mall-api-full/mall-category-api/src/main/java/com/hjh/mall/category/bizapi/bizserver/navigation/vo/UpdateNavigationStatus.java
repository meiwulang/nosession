package com.hjh.mall.category.bizapi.bizserver.navigation.vo;

import java.util.ArrayList;

import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.annotation.NotNull;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.field.type.Status;

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
public class UpdateNavigationStatus extends HJYVO {
	private static final long serialVersionUID = 1L;

	// 主键
	@NotNull
	@ApiModelProperty(name = "categoryIds", value = "[主键1,主键2]", required = true)
	private ArrayList<String> categoryIds;

	// 0禁用，1启用
	@NotBlank
	@ApiModelProperty(name = "status", value = "0禁用，1启用", required = true)
	@EnumValue(enumClass = Status.class)
	private String status;
	// 操作人编号
	@ApiModelProperty(name = "updateUser", value = "操作人编号", required = false)
	private String updateUser;

	// 操作人
	@ApiModelProperty(name = "updater", value = "操作人", required = false)
	private String updater;

	public ArrayList<String> getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(ArrayList<String> categoryIds) {
		this.categoryIds = categoryIds;
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

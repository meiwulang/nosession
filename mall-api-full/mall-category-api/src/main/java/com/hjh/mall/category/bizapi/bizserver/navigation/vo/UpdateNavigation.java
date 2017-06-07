package com.hjh.mall.category.bizapi.bizserver.navigation.vo;

import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.common.core.annotation.NotBlank;
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
@ApiModel("修改类目")
public class UpdateNavigation extends HJYVO {
	private static final long serialVersionUID = 1L;

	// 主键
	@NotBlank
	@ApiModelProperty(name = "categoryId", value = "主键", required = true, example = "123456")
	private String categoryId;

	// 祖类目编号
	@ApiModelProperty(name = "grandId", value = "祖类目编号", required = false)
	private String grandId;
	// 父类目编号
	@ApiModelProperty(name = "fatherId", value = "父类目编号", required = false)
	private String fatherId;
	// 类目名称
	@ApiModelProperty(name = "categoryName", value = "类目名称", required = false)
	private String categoryName;
	// 类目别名
	@ApiModelProperty(name = "nickName", value = "类目别名", required = false)
	private String nickName;

	// 排序字段
	@ApiModelProperty(name = "sort", value = "排序字段", required = false)
	private Integer sort = -1;
	// 0禁用，1启用
	@EnumValue(enumClass = Status.class)
	@ApiModelProperty(name = "status", value = "后台状态", required = false)
	private String status;

	// 备注
	@ApiModelProperty(name = "remark", value = "备注", required = false)
	private String remark;
	// 类目等级
	@ApiModelProperty(name = "level", value = "类目等级：1或2或3", required = false)
	private String level;

	// 图标
	@ApiModelProperty(name = "icon", value = "图标", required = false, example = "/sdf/sfdsdf.png")
	private String icon;

	// 操作人编号
	@ApiModelProperty(name = "updateUser", value = "操作人编号", required = false)
	private String updateUser;

	// 操作人
	@ApiModelProperty(name = "updater", value = "操作人", required = false)
	private String updater;
	// 热门排序
	@ApiModelProperty(name = "hotSort", value = "热门排序", required = false)
	private Integer hotSort;
	// 是否上热门
	@ApiModelProperty(name = "hotable", value = "是否上热门", required = false)
	private boolean hotable;

	// 热门图标
	@ApiModelProperty(name = "hotIcon", value = "热门图标", required = false)
	private String hotIcon;

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getHotSort() {
		return hotSort;
	}

	public void setHotSort(Integer hotSort) {
		this.hotSort = hotSort;
	}

	public String getHotIcon() {
		return hotIcon;
	}

	public void setHotIcon(String hotIcon) {
		this.hotIcon = hotIcon;
	}

	public boolean isHotable() {
		return hotable;
	}

	public void setHotable(boolean isHotable) {
		this.hotable = isHotable;
	}

	public String getGrandId() {
		return grandId;
	}

	public void setGrandId(String grandId) {
		this.grandId = grandId;
	}

	public String getFatherId() {
		return fatherId;
	}

	public void setFatherId(String fatherId) {
		this.fatherId = fatherId;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

}

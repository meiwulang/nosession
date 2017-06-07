package com.hjh.mall.category.bizapi.bizserver.navigation.vo;

import com.hjh.mall.common.core.annotation.Length;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.annotation.NotNull;
import com.hjh.mall.common.core.vo.HJYVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: mall-category-api-brunch
 * @Description TODO
 * @author 王斌
 * @date 2017年3月14日
 * @since 1.0
 */
@ApiModel("添加类目")
public class CreateNavigation extends HJYVO {

	private static final long serialVersionUID = 9007255226311870570L;

	// 类目名称
	@NotBlank
	@ApiModelProperty(name = "categoryName", value = "类目名称", required = true, example = "categoryName")
	private String categoryName;
	// 类目别名
	@Length(min = 2, max = 8)
	@ApiModelProperty(name = "nickName", value = "类目别名", required = false, example = "nickName")
	private String nickName;

	// 排序字段
	@NotNull
	@ApiModelProperty(name = "sort", value = "排序字段", required = true, example = "12")
	private Integer sort;

	// 备注
	@ApiModelProperty(name = "remark", value = "备注", required = false, example = "备注")
	private String remark;

	// 图标
	@NotBlank
	@ApiModelProperty(name = "icon", value = "图标", required = true, example = "/img/aa.png")
	private String icon;

	// 操作人编号
	@ApiModelProperty(name = "updateUser", value = "操作人编号", required = false, example = "123456", readOnly = true)
	private String updateUser;

	// 操作人
	@ApiModelProperty(name = "updater", value = "操作人", required = false, example = "sx", readOnly = true)
	private String updater;

	// 导航层级 1为1级 2为二级 3为3级
	@NotNull
	@ApiModelProperty(name = "updater", value = "操作人", required = true, example = "1")
	private Byte level;

	// 父节点
	@ApiModelProperty(name = "fatherId", value = "父节点", required = false, example = "0000000000000000")
	private String fatherId = "0000000000000000";
	// 热门排序
	@ApiModelProperty(name = "hotSort", value = "热门排序", required = false, example = "1")
	private Integer hotSort;
	// 是否上热门
	@ApiModelProperty(name = "hotable", value = "是否上热门", required = false, example = "true")
	private Boolean hotable;

	// 热门图标
	@ApiModelProperty(name = "hotIcon", value = "热门图标", required = false, example = "/img/aa.png")
	private String hotIcon;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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

	public Byte getLevel() {
		return level;
	}

	public void setLevel(Byte level) {
		this.level = level;
	}

	public String getFatherId() {
		return fatherId;
	}

	public void setFatherId(String fatherId) {
		this.fatherId = fatherId;
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

	public Boolean isHotable() {
		return hotable;
	}

	public void setHotable(boolean hotable) {
		this.hotable = hotable;
	}

	@Override
	public String toString() {
		return "CreateNavigation [categoryName=" + categoryName + ", nickName=" + nickName + ", sort=" + sort
				+ ", remark=" + remark + ", icon=" + icon + ", updateUser=" + updateUser + ", updater=" + updater
				+ ", level=" + level + ", fatherId=" + fatherId + "]";
	}
}

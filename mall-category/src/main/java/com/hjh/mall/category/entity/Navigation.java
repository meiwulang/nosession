package com.hjh.mall.category.entity;

import java.io.Serializable;

import com.hjh.mall.common.core.entity.Updatable;

public class Navigation extends Updatable implements Serializable {
	private static final long serialVersionUID = 187635569028378824L;

	// 主键
	private String categoryId;

	// 类目名称
	private String categoryName;

	// 排序字段
	private Integer sort;

	// 0禁用，1启用
	private String status;

	// 备注
	private String remark;

	// 图标
	private String icon;

	// 操作人编号
	private String updateUser;

	// 更新版本
	private Integer updateVersion;
	// 热门排序
	private Integer hotSort;

	// 热门图标
	private String hotIcon;
	// 初始化日期
	private String initDate;

	// 初始化时间
	private String initTime;

	// 更新日期
	private String updateDate;

	// 更新时间
	private String updateTime;

	// 操作人
	private String updater;

	// 导航层级 1为1级 2为二级 3为3级
	private Byte level;

	// 父节点
	private String fatherId;

	// 创建人
	private String creater;

	// 名称首字母
	private String firstChar;

	// 别名
	private String nickName;

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

	public Integer getUpdateVersion() {
		return updateVersion;
	}

	public void setUpdateVersion(Integer updateVersion) {
		this.updateVersion = updateVersion;
	}

	public String getInitDate() {
		return initDate;
	}

	public void setInitDate(String initDate) {
		this.initDate = initDate;
	}

	public String getInitTime() {
		return initTime;
	}

	public void setInitTime(String initTime) {
		this.initTime = initTime;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
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

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getFirstChar() {
		return firstChar;
	}

	public void setFirstChar(String firstChar) {
		this.firstChar = firstChar;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Navigation(String categoryName) {
		super();
		this.categoryName = categoryName;
	}

	public Navigation() {
		super();
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

	public Navigation(String fatherId, String status) {
		super();
		this.fatherId = fatherId;
		this.status = status;
	}

}
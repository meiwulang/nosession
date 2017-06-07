package com.hjh.mall.bizapi.biz.goods.middle.entity;

import java.io.Serializable;

import com.hjh.mall.common.core.entity.Updatable;

public class Activity extends Updatable implements Serializable {
	private static final long serialVersionUID = 50350167137247710L;

	// 主键
	private String activityId;

	// 活动标题
	private String title;

	// 活动图标
	private String img;

	// 排序
	private Integer sort;

	// 活动内容地址
	private String text;

	// app显示状态（1：显示，0：不显示）
	private String appDisplay;

	// 创建日期
	private String initDate;

	// 创建时间
	private String initTime;

	// 更新时间
	private String updateTime;

	// 更新日期
	private String updateDate;

	// 操作人
	private String initUser;

	// 修改人
	private String updateUser;

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getAppDisplay() {
		return appDisplay;
	}

	public void setAppDisplay(String appDisplay) {
		this.appDisplay = appDisplay;
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

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getInitUser() {
		return initUser;
	}

	public void setInitUser(String initUser) {
		this.initUser = initUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
}
package com.hjh.mall.entity;

import java.io.Serializable;

import com.hjh.mall.common.core.entity.Updatable;

public class Category extends Updatable implements Serializable {
	// 主键
	private String category_id;

	// 类目名称
	private String category_name;

	// 排序字段
	private Integer sort;

	// 图标
	private String icon;

	private String create_date;

	private String create_time;

	private String update_date;

	private String update_time;
	private String updater;
	private String remark;
	private static final long serialVersionUID = 1L;

	public Category(String category_name) {
		super();
		this.category_name = category_name;
	}

	public Category() {
		super();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Category [category_id=");
		builder.append(category_id);
		builder.append(", category_name=");
		builder.append(category_name);
		builder.append(", sort=");
		builder.append(sort);
		builder.append(", icon=");
		builder.append(icon);
		builder.append(", create_date=");
		builder.append(create_date);
		builder.append(", create_time=");
		builder.append(create_time);
		builder.append(", update_date=");
		builder.append(update_date);
		builder.append(", update_time=");
		builder.append(update_time);
		builder.append(", updater=");
		builder.append(updater);
		builder.append(", remark=");
		builder.append(remark);
		builder.append("]");
		return builder.toString();
	}

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
package com.hjh.mall.category.entity;

import com.hjh.mall.common.core.entity.Updatable;

public class Metadata extends Updatable {
	private String metadata_id;

	// 名称
	private String metadata_name;

	// banner内容
	private String alias;

	// 类别（0：单位；1：车型类型）
	private Integer type;

	// 修改日期
	private String update_date;

	private String update_time;

	// 创建人
	private String create_user;
	// 创建人
	private String create_user_id;
	// 创建人
	private String update_user_id;

	// 排序
	private Integer sort;

	private static final long serialVersionUID = 1L;

	public String getMetadata_id() {
		return metadata_id;
	}

	public void setMetadata_id(String metadata_id) {
		this.metadata_id = metadata_id;
	}

	public String getMetadata_name() {
		return metadata_name;
	}

	public void setMetadata_name(String metadata_name) {
		this.metadata_name = metadata_name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public String getCreate_user_id() {
		return create_user_id;
	}

	public void setCreate_user_id(String create_user_id) {
		this.create_user_id = create_user_id;
	}

	public String getUpdate_user_id() {
		return update_user_id;
	}

	public void setUpdate_user_id(String update_user_id) {
		this.update_user_id = update_user_id;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
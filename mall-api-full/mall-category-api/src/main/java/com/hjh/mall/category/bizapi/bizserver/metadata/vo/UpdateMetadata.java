package com.hjh.mall.category.bizapi.bizserver.metadata.vo;

import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.annotation.NotNull;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.field.type.MetadataType;
import com.hjh.mall.field.type.Status;

/**
 * @Project: mall-category-api
 * @Description TODO
 * @author 曾繁林
 * @date 2017年3月14日
 * @version V1.0
 */
public class UpdateMetadata extends HJYVO {

	private static final long serialVersionUID = 1L;

	@NotBlank
	private String metadata_id;
	// 名称
	@NotBlank
	private String metadata_name;

	// 别名
	private String alias;

	// 类别（0：单位；1：车型类型）
	@NotNull
	@EnumValue(enumClass = MetadataType.class)
	private Integer type;

	// 排序
	private Integer sort;

	@EnumValue(enumClass = Status.class)
	private String status;

	private String update_user;// 操作人

	private String update_user_id;

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

	public String getUpdate_user() {
		return update_user;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}

	public String getUpdate_user_id() {
		return update_user_id;
	}

	public void setUpdate_user_id(String update_user_id) {
		this.update_user_id = update_user_id;
	}

}

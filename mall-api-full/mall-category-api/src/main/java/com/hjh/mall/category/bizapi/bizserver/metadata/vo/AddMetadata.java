package com.hjh.mall.category.bizapi.bizserver.metadata.vo;

import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.annotation.NotNull;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.field.type.MetadataType;
import com.hjh.mall.field.type.Status;

/**
 * @Project: mall-category-api
 * @Description 添加元数据
 * @author 曾繁林
 * @date 2017年3月14日
 * @version V1.0
 */
public class AddMetadata extends HJYVO {

	private static final long serialVersionUID = 1L;

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

	private String update_user;// 操作人

	// 修改人
	private String create_user;
	// 修改人
	private String create_user_id;
	// 创建人
	private String update_user_id;

	@NotBlank
	@EnumValue(enumClass = Status.class)
	private String status;

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

}

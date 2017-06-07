package com.hjh.mall.category.bizapi.bizserver.metadata.vo;

import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.common.core.vo.PageQueryVO;
import com.hjh.mall.field.type.Status;

/**
 * @Project: mall-category-api
 * @Description TODO
 * @author 曾繁林
 * @date 2017年3月14日
 * @version V1.0
 */
public class GetMetadataList extends PageQueryVO {

	private static final long serialVersionUID = 1L;
	private String metadata_name;

	// 别名
	private String alias;

	// 类别（0：单位；1：车型类型）
	private Integer type;
	@EnumValue(enumClass = Status.class)
	private String status;

	private String update_user;// 操作人
	private String update_date_start;
	private String update_date_end;
	private String update_time_start;
	private String update_time_end;

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

	public String getUpdate_date_start() {
		return update_date_start;
	}

	public void setUpdate_date_start(String update_date_start) {
		this.update_date_start = update_date_start;
	}

	public String getUpdate_date_end() {
		return update_date_end;
	}

	public void setUpdate_date_end(String update_date_end) {
		this.update_date_end = update_date_end;
	}

	public String getUpdate_time_start() {
		return update_time_start;
	}

	public void setUpdate_time_start(String update_time_start) {
		this.update_time_start = update_time_start;
	}

	public String getUpdate_time_end() {
		return update_time_end;
	}

	public void setUpdate_time_end(String update_time_end) {
		this.update_time_end = update_time_end;
	}

}

package com.hjh.mall.category.bizapi.bizserver.metadata.vo;

import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.field.type.Status;

/**
 * @Project: mall-category-api
 * @Description TODO
 * @author 曾繁林
 * @date 2017年3月14日
 * @version V1.0
 */
public class UpdateMetadataStatus extends HJYVO {

	private static final long serialVersionUID = 1L;
	private String metadata_id;
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

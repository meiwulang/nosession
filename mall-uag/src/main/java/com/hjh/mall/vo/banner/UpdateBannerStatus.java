package com.hjh.mall.vo.banner;

import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.field.type.Status;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: hjh-blog-api
 * @Description TODO
 * @author 曾繁林
 * @date 2016年12月15日
 * @version V1.0
 */
public class UpdateBannerStatus extends HJYVO {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty(notes="id")
	private String banner_id;
	@EnumValue(enumClass = Status.class)
	@ApiModelProperty(notes="状态")
	private String status;
	private String update_user;
	private String update_user_name;

	public String getUpdate_user_name() {
		return update_user_name;
	}

	public void setUpdate_user_name(String update_user_name) {
		this.update_user_name = update_user_name;
	}

	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBanner_id() {
		return banner_id;
	}

	public void setBanner_id(String banner_id) {
		this.banner_id = banner_id;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UpdateBannerStatus [banner_id=");
		builder.append(banner_id);
		builder.append(", status=");
		builder.append(status);
		builder.append(", update_user=");
		builder.append(update_user);
		builder.append(", update_user_name=");
		builder.append(update_user_name);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}
}

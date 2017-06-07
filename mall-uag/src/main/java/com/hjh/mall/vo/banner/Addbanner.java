package com.hjh.mall.vo.banner;

import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.field.type.Status;
import com.hjh.mall.type.BannerContentType;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: hjh-blog-api
 * @Description 添加轮播图
 * @author 曾繁林
 * @date 2016年12月14日
 * @version V1.0
 */
public class Addbanner extends HJYVO {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty(notes="图片路径")
	private String banner_path;
	@ApiModelProperty(notes="标题")
	private String title;
	@ApiModelProperty(notes="跳转路径")
	private String skip_url;
	@ApiModelProperty(notes="状态")
	@EnumValue(enumClass = Status.class)
	private String status = "1";
	@ApiModelProperty(notes="备注")
	private String remark;
	@ApiModelProperty(notes="排序")
	private Integer sort = 1;
	@ApiModelProperty(notes="是否跳转")
	private String is_skip;
	private String update_user;
	private String create_user;
	private String create_user_name;
	private String update_user_name;
	@EnumValue(enumClass = BannerContentType.class)
	private String content_type;
	private String app_url;
	private String type = "0";

	public String getIs_skip() {
		return is_skip;
	}

	public void setIs_skip(String is_skip) {
		this.is_skip = is_skip;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getBanner_path() {
		return banner_path;
	}

	public void setBanner_path(String banner_path) {
		this.banner_path = banner_path;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSkip_url() {
		return skip_url;
	}

	public void setSkip_url(String skip_url) {
		this.skip_url = skip_url;
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

	public String getContent_type() {
		return content_type;
	}

	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}

	public String getApp_url() {
		return app_url;
	}

	public void setApp_url(String app_url) {
		this.app_url = app_url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreate_user_name() {
		return create_user_name;
	}

	public void setCreate_user_name(String create_user_name) {
		this.create_user_name = create_user_name;
	}

	public String getUpdate_user_name() {
		return update_user_name;
	}

	public void setUpdate_user_name(String update_user_name) {
		this.update_user_name = update_user_name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Addbanner [banner_path=");
		builder.append(banner_path);
		builder.append(", title=");
		builder.append(title);
		builder.append(", skip_url=");
		builder.append(skip_url);
		builder.append(", status=");
		builder.append(status);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", sort=");
		builder.append(sort);
		builder.append(", is_skip=");
		builder.append(is_skip);
		builder.append(", update_user=");
		builder.append(update_user);
		builder.append(", create_user=");
		builder.append(create_user);
		builder.append(", create_user_name=");
		builder.append(create_user_name);
		builder.append(", update_user_name=");
		builder.append(update_user_name);
		builder.append(", content_type=");
		builder.append(content_type);
		builder.append(", app_url=");
		builder.append(app_url);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}

}

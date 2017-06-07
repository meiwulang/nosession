package com.hjh.mall.entity;

import com.hjh.mall.common.core.entity.Updatable;

/**
 * @Project: hjh-blog-middle
 * @Description TODO
 * @author 曾繁林
 * @date 2016年12月14日
 * @version V1.0
 */
public class Banner extends Updatable {
	private static final long serialVersionUID = 1L;
	private String banner_id;
	private String banner_path;
	private String title;
	private String skip_url;// 微信跳转的url
	private String content_type;// 轮播图链接内容类型
	private String app_url;// 仅供APP使用
	private String category_id;
	private String create_user;
	private String create_user_name;
	private String update_user_name;
	private Integer sort;
	private String is_skip;
	private String type;
	private String update_date;
	private String update_time;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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

	public String getBanner_id() {
		return banner_id;
	}

	public void setBanner_id(String banner_id) {
		this.banner_id = banner_id;
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

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
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
		builder.append("Banner [banner_id=");
		builder.append(banner_id);
		builder.append(", banner_path=");
		builder.append(banner_path);
		builder.append(", title=");
		builder.append(title);
		builder.append(", skip_url=");
		builder.append(skip_url);
		builder.append(", content_type=");
		builder.append(content_type);
		builder.append(", app_url=");
		builder.append(app_url);
		builder.append(", category_id=");
		builder.append(category_id);
		builder.append(", create_user=");
		builder.append(create_user);
		builder.append(", create_user_name=");
		builder.append(create_user_name);
		builder.append(", update_user_name=");
		builder.append(update_user_name);
		builder.append(", sort=");
		builder.append(sort);
		builder.append(", is_skip=");
		builder.append(is_skip);
		builder.append(", type=");
		builder.append(type);
		builder.append(", update_date=");
		builder.append(update_date);
		builder.append(", update_time=");
		builder.append(update_time);
		builder.append("]");
		return builder.toString();
	}

}

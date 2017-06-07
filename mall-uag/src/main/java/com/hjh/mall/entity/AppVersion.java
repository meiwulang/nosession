package com.hjh.mall.entity;

import com.hjh.mall.common.core.entity.Updatable;

public class AppVersion extends Updatable {
	private static final long serialVersionUID = 1L;

	private String version_id;// 主键

	private String version_info;// 版本信息

	private String package_url;// 安装包下载地址

	private String update_date;// 更新日期
	
	private String update_time;//更新日期时分秒

	private String version_no;// 版本号

	private String app_type;// 更新安装包类型

	private String force_update = "0";// 是否强制更新 (0：不强制，1：强制)
	
	private String version_status = "1";// 版本状态（0已失效，1生效中）

	private String version_title;// 版本名称

	private String creater_name;// 发布人名称

	private String creater_id ;// 发布人编号
	
	private String app_name;// APP名称
	
	
	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getVersion_id() {
		return version_id;
	}

	public void setVersion_id(String version_id) {
		this.version_id = version_id;
	}

	public String getVersion_info() {
		return version_info;
	}

	public void setVersion_info(String version_info) {
		this.version_info = version_info;
	}

	public String getPackage_url() {
		return package_url;
	}

	public void setPackage_url(String package_url) {
		this.package_url = package_url;
	}

	public String getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}

	public String getVersion_no() {
		return version_no;
	}

	public void setVersion_no(String version_no) {
		this.version_no = version_no;
	}

	public String getApp_type() {
		return app_type;
	}

	public void setApp_type(String app_type) {
		this.app_type = app_type;
	}

	public String getForce_update() {
		return force_update;
	}

	public void setForce_update(String force_update) {
		this.force_update = force_update;
	}

	public String getVersion_status() {
		return version_status;
	}

	public void setVersion_status(String version_status) {
		this.version_status = version_status;
	}

	public String getVersion_title() {
		return version_title;
	}

	public void setVersion_title(String version_title) {
		this.version_title = version_title;
	}

	public String getCreater_name() {
		return creater_name;
	}

	public void setCreater_name(String creater_name) {
		this.creater_name = creater_name;
	}

	public String getCreater_id() {
		return creater_id;
	}

	public void setCreater_id(String creater_id) {
		this.creater_id = creater_id;
	}
	
	public String getApp_name() {
		return app_name;
	}

	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AppVersion [version_id=");
		builder.append(version_id);
		builder.append(", version_info=");
		builder.append(version_info);
		builder.append(", package_url=");
		builder.append(package_url);
		builder.append(", update_date=");
		builder.append(update_date);
		builder.append(", version_no=");
		builder.append(version_no);
		builder.append(", app_type=");
		builder.append(app_type);
		builder.append(", force_update=");
		builder.append(force_update);
		builder.append(", version_status=");
		builder.append(version_status);
		builder.append(", version_title=");
		builder.append(version_title);
		builder.append(", creater_name=");
		builder.append(creater_name);
		builder.append(", creater_id=");
		builder.append(creater_id);
		builder.append(", app_name=");
		builder.append(app_name);
		builder.append("]");
		return builder.toString();
	}
	
}
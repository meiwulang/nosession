package com.hjh.mall.vo.appversion;

import com.hjh.mall.common.core.vo.PageQueryVO;

/** 
 * @date 2016年8月3日 
 * 
 * @author 曾繁林
 * 
 * @Description: web端查询APP版本记录
 * 
 **/
public class GetVersionList extends PageQueryVO {

	private static final long serialVersionUID = 1L;
	private String version_status;//版本状态
	private String app_type;//APP类别
	private String force_update;//更新类型
	private String version_title;//版本名称
	private String version_no;//版本号
	private String creater_name;//发布人
	private String date_end;//查询开始时间
	private String date_start;//查询结束时间
	
	
	public String getDate_end() {
		return date_end;
	}
	public void setDate_end(String date_end) {
		this.date_end = date_end;
	}
	public String getDate_start() {
		return date_start;
	}
	public void setDate_start(String date_start) {
		this.date_start = date_start;
	}
	public String getVersion_status() {
		return version_status;
	}
	public void setVersion_status(String version_status) {
		this.version_status = version_status;
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
	public String getVersion_title() {
		return version_title;
	}
	public void setVersion_title(String version_title) {
		this.version_title = version_title;
	}
	public String getVersion_no() {
		return version_no;
	}
	public void setVersion_no(String version_no) {
		this.version_no = version_no;
	}
	public String getCreater_name() {
		return creater_name;
	}
	public void setCreater_name(String creater_name) {
		this.creater_name = creater_name;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GetVersionList [version_status=");
		builder.append(version_status);
		builder.append(", app_type=");
		builder.append(app_type);
		builder.append(", force_update=");
		builder.append(force_update);
		builder.append(", version_title=");
		builder.append(version_title);
		builder.append(", version_no=");
		builder.append(version_no);
		builder.append(", creater_name=");
		builder.append(creater_name);
		builder.append("]");
		return builder.toString();
	}
}

package com.hjh.mall.vo.appversion;

import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @date 2016年8月4日 
 * 
 * @author 曾繁林 
 * 
 * @Description: 修改APP版本信息
 * 
 **/
public class UpdateVersion extends HJYVO {

	private static final long serialVersionUID = 1L;
	@NotBlank
	@ApiModelProperty(notes="版本ID")
	private String version_id;// 主键
	@NotBlank
	@ApiModelProperty(notes="版本信息")
	private String version_info;// 版本信息
	@NotBlank
	@ApiModelProperty(notes="安装包下载地址")
	private String package_url;// 安装包下载地址
	@NotBlank
	@ApiModelProperty(notes="版本号")
	private String version_no;// 版本号
	@NotBlank
	@ApiModelProperty(notes="是否强制更新 (0：不强制，1：强制")
	private String force_update;// 是否强制更新 (0：不强制，1：强制)
	@NotBlank
	@ApiModelProperty(notes="版本名称")
	private String version_title;// 版本名称
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
	public String getVersion_no() {
		return version_no;
	}
	public void setVersion_no(String version_no) {
		this.version_no = version_no;
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
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UpdateVersion [version_id=");
		builder.append(version_id);
		builder.append(", version_info=");
		builder.append(version_info);
		builder.append(", package_url=");
		builder.append(package_url);
		builder.append(", version_no=");
		builder.append(version_no);
		builder.append(", force_update=");
		builder.append(force_update);
		builder.append(", version_title=");
		builder.append(version_title);
		builder.append("]");
		return builder.toString();
	}
	
}

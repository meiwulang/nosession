package com.hjh.mall.vo.appversion;

import org.hibernate.validator.constraints.URL;

import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.field.type.AppType;
import com.hjh.mall.field.type.ForceUpdate;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: hjy-middle
 * @Description app版本更新vo
 * @author 王斌
 * @date 2016年8月2日
 * @version V1.0
 */
public class AddAppVersionVO extends HJYVO {
	private static final long serialVersionUID = 1L;
	@NotBlank
	@ApiModelProperty(notes="版本信息")
	private String version_info;// 版本信息
	@NotBlank
	@URL
	@ApiModelProperty(notes="安装包下载地址")
	private String package_url;// 安装包下载地址
	@NotBlank
	@ApiModelProperty(notes="版本号")
	private String version_no;// 版本号
	@NotBlank
	@EnumValue(enumClass = AppType.class)
	@ApiModelProperty(notes="更新安装包类型(0:安卓，1：ios)")
	private String app_type;// 更新安装包类型(0:安卓，1：ios)
	@NotBlank
	@EnumValue(enumClass = ForceUpdate.class)
	@ApiModelProperty(notes="是否强制更新 (0：不强制，1：强制)")
	private String force_update = "0";// 是否强制更新 (0：不强制，1：强制)
	@NotBlank
	@ApiModelProperty(notes="APP名称")
	private String app_name;// APP名称
	@NotBlank
	@ApiModelProperty(notes="版本名称")
	private String version_title;// 版本名称
	@NotBlank
	@ApiModelProperty(notes="版本状态（0已失效，1生效中）")
	private String version_status = "1";// 版本状态（0已失效，1生效中）
	@NotBlank
	@ApiModelProperty(notes="标志位（0逻辑删除，1可查询）")
	private String status = "1";// 标志位（0逻辑删除，1可查询）

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

	public String getApp_name() {
		return app_name;
	}

	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}

	public String getVersion_title() {
		return version_title;
	}

	public void setVersion_title(String version_title) {
		this.version_title = version_title;
	}

	public String getVersion_status() {
		return version_status;
	}

	public void setVersion_status(String version_status) {
		this.version_status = version_status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}

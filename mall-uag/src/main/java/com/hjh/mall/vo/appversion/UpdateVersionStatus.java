package com.hjh.mall.vo.appversion;

import com.hjh.mall.common.core.vo.HJYVO;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @date 2016年8月3日 
 * 
 * @author 曾繁林
 * 
 * @Description: TODO  
 * 
 **/
public class UpdateVersionStatus extends HJYVO {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(notes="版本状态")
	private String version_status;//版本状态
	@ApiModelProperty(notes="版本ID")
	private String version_id;//版本ID
	
	public String getVersion_status() {
		return version_status;
	}
	public void setVersion_status(String version_status) {
		this.version_status = version_status;
	}
	public String getVersion_id() {
		return version_id;
	}
	public void setVersion_id(String version_id) {
		this.version_id = version_id;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UpdateVersionStatus [version_status=");
		builder.append(version_status);
		builder.append(", version_id=");
		builder.append(version_id);
		builder.append("]");
		return builder.toString();
	}
	
}

package com.hjh.mall.vo.appversion;

import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;

/**
 * @Description 逻辑删除版本信息VO
 * @author 曾繁林
 * @date 2016年8月4日
 * @version V1.0 
 */
public class DelVersion extends HJYVO {

	private static final long serialVersionUID = 1L;
	@NotBlank
	private String version_id;
	public String getVersion_id() {
		return version_id;
	}
	public void setVersion_id(String version_id) {
		this.version_id = version_id;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DelVersion [version_id=");
		builder.append(version_id);
		builder.append("]");
		return builder.toString();
	}
	
}

package com.hjh.mall.vo;

import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.filed.UploadType;
import com.hjh.mall.common.core.vo.HJYVO;

/**
 * @Project: mall-web-bran
 * @Description 图片上传vo
 * @author 王斌
 * @date 2017年5月9日
 * @version V1.0
 */
public class UploadImg extends HJYVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotBlank
	@EnumValue(enumClass = UploadType.class)
	private String imgType;// 图片类型
	@NotBlank
	private String base64Str;// 图片base64字符

	public String getImgType() {
		return imgType;
	}

	public void setImgType(String imgType) {
		this.imgType = imgType;
	}

	public String getBase64Str() {
		return base64Str;
	}

	public void setBase64Str(String base64Str) {
		this.base64Str = base64Str;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

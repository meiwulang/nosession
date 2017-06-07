package com.hjh.mall.vo.client;

import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.filed.UploadType;
import com.hjh.mall.common.core.vo.HJYVO;

public class UploadPicture extends HJYVO {

	private static final long serialVersionUID = 1L;
	@NotBlank
	private String imagecontext;// 图片内容 base64
	@EnumValue(enumClass = UploadType.class)
	@NotBlank
	private String upload_type;// 上传类型

	public String getImagecontext() {
		return imagecontext;
	}

	public void setImagecontext(String imagecontext) {
		this.imagecontext = imagecontext;
	}

	public String getUpload_type() {
		return upload_type;
	}

	public void setUpload_type(String upload_type) {
		this.upload_type = upload_type;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UploadPicture [imagecontext=");
		builder.append(imagecontext);
		builder.append(", upload_type=");
		builder.append(upload_type);
		builder.append("]");
		return builder.toString();
	}

}
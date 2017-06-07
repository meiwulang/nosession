package com.hjh.mall.vo;

import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;

/**
 * @Project: mall-web-bran
 * @Description 富文本上传vo
 * @author 王斌
 * @date 2017年5月9日
 * @version V1.0
 */
public class UpdateUploadHtml extends HJYVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotBlank
	private String text;// html内容
	@NotBlank
	private String textUri;// html路径

	public String getTextUri() {
		return textUri;
	}

	public void setTextUri(String textUri) {
		this.textUri = textUri;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

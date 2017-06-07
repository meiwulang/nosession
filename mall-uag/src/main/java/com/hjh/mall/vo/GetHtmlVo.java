package com.hjh.mall.vo;

import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;

/**
 * @Project: mall-web-bran
 * @Description 获取复文本内容vo
 * @author 王斌
 * @date 2017年5月9日
 * @version V1.0
 */
public class GetHtmlVo extends HJYVO {

	private static final long serialVersionUID = 1L;

	@NotBlank
	private String textUri;// html路径

	public String getTextUri() {
		return textUri;
	}

	public void setTextUri(String textUri) {
		this.textUri = textUri;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

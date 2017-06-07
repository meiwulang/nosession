package com.hjh.mall.bizapi.biz.goods.middle.service;

import java.io.InputStream;
import java.util.Map;

import com.hjh.mall.goods.bizapi.bizserver.vo.UploadPicture;

@Deprecated
public interface UploadService {
	@Deprecated
	public Map<String, Object> uploadPic(UploadPicture uploadPictureForm);

	/**
	 * @Description: 上传html
	 * @author 杨益桦
	 * @date 2016年8月17日
	 * @param imagecontext
	 *            base64
	 * @return Map<String,Object>
	 */
	@Deprecated
	public Map<String, Object> uploadHtml(String newsId, InputStream inputStream);

}

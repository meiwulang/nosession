package com.hjh.mall.service;

import java.util.Map;

import com.hjh.mall.vo.client.UploadPicture;

public interface PictureService {

	// 图片上传
	public Map<String, Object> uploadPicture(UploadPicture uploadPicture);

}
package com.hjh.mall.bizapi.biz.goods.middle.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hjh.mall.bizapi.biz.goods.middle.service.UploadService;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.exception.HJHBCSErrInfoException;
import com.hjh.mall.common.core.filed.ImageType;
import com.hjh.mall.common.core.filed.UploadType;
import com.hjh.mall.common.core.util.EnumUtil;
import com.hjh.mall.common.core.util.IDUtil;
import com.hjh.mall.common.core.util.IOUtil;
import com.hjh.mall.common.core.util.ImageUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.field.error.ErrorCode;
import com.hjh.mall.goods.bizapi.bizserver.vo.UploadPicture;
import com.hjh.mall.store.service.HjyStoreService;

@Service
@Deprecated
public class UploadServiceImpl implements UploadService {

	@Resource
	private HjyStoreService hjyStoreService;

	@Override
	@Deprecated
	public Map<String, Object> uploadPic(UploadPicture uploadPicture) {
		if (StringUtils.isNotEmpty(uploadPicture.getImagecontext())) {// 如果base
																		// 带前缀
																		// 则去掉
																		// By李慧峰
			if (uploadPicture.getImagecontext().indexOf(",") > 0) {
				uploadPicture.setImagecontext(uploadPicture.getImagecontext().split(",")[1]);
			}
		}
		byte[] bytes = IOUtil.base64StringToBytes(uploadPicture.getImagecontext());
		ImageType image_type = ImageUtil.getImageType(bytes);
		if (image_type == null) {
			throw new HJHBCSErrInfoException(ErrorCode.ImageCode.ERROR_TYPE);
		}
		String suffix = image_type.getSuffix();
		UploadType type = EnumUtil.valOf(uploadPicture.getUpload_type(), UploadType.class);
		StringBuffer key = new StringBuffer();
		key.append(type.getDescription()).append("/").append(IDUtil.getOrdID16()).append(".").append(suffix);

		InputStream inputStream = new ByteArrayInputStream(bytes);
		hjyStoreService.uploadFile(key.toString(), inputStream); // 调用oss接口上传图片到阿里云
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.IMAGE_KEY, key.toString());
		return result;
	}

	@Override
	@Deprecated
	public Map<String, Object> uploadHtml(String newsId, InputStream inputStream) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(UploadType.NEWS.getDescription()).append("/").append(newsId).append(".html");// NEWS/id.html

		String ossKey = stringBuilder.toString();
		hjyStoreService.uploadFile(ossKey, inputStream);

		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.IMAGE_KEY, ossKey);
		return result;
	}

}

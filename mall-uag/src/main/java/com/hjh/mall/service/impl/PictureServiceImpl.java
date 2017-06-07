package com.hjh.mall.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
import com.hjh.mall.service.PictureService;
import com.hjh.mall.store.service.HjyStoreService;
import com.hjh.mall.vo.client.UploadPicture;

/**
 * @Project: hjy-middle
 * @Description 图片
 * @author 曾繁林
 * @date 2016年7月14日
 * @version V1.0
 */
@Service
@Component
public class PictureServiceImpl implements PictureService {

	@Resource
	private HjyStoreService hjyStoreService;

	@Value("${http_proxy_ip: }")
	private String http_proxy_ip;

	@Value("${http_proxy_port: }")
	private String http_proxy_port;

	@Override
	public Map<String, Object> uploadPicture(UploadPicture uploadPicture) {
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
		hjyStoreService.setProxy(http_proxy_ip, http_proxy_port);
		hjyStoreService.uploadFile(key.toString(), inputStream);// 调用oss接口上传图片到阿里云
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.IMAGE_KEY, key.toString());
		return result;
	}

}

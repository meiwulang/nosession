package com.hjh.mall.service;

import java.io.InputStream;
import java.util.Map;

import com.hjh.mall.vo.client.UploadPicture;

public interface UploadService {

	public Map<String, Object> uploadPic(UploadPicture uploadPictureForm);

	/**
	 * @Description: 上传html
	 * @author 杨益桦
	 * @date 2016年8月17日
	 * @param imagecontext
	 *            base64
	 * @return Map<String,Object>
	 */
	public Map<String, Object> uploadHtml(String newsId, InputStream inputStream);

	/**
	 * @date 2017年3月15日
	 * @Description: 上传图片简化版
	 * @param path
	 * @param strbase64
	 * @return 图片路径 String
	 */
	public String uploadImg(String modelname, String strbase64);

	/**
	 * @date 2017年5月9日
	 * @Description: 上传html
	 * @author：王斌
	 * @param newsId
	 * @param htmlText
	 */
	public String uploadHtml(String newsId, String htmlText);

	/**
	 * @date 2017年5月10日
	 * @Description: 更新html内容
	 * @author：王斌
	 * @param newsId
	 * @param htmlText
	 */
	public String updateUploadHtml(String newsId, String htmlText);

	

	/** 
	 * @date 2017年5月9日
	 * @author boochy
	 * @Description: 上传图片简化版
	 * @param modelname
	 * @param html code
	 * @return html路径 key
	 * String
	 */
	public String uploadHtmlByModel(String modelname, String htmlcode);
}

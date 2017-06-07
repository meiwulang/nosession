package com.hjh.mall.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hjh.mall.category.bizapi.bizserver.metadata.BizMetadataService;
import com.hjh.mall.common.core.annotation.NoLogin;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.filed.UploadType;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.context.HJYBizDataContext;
import com.hjh.mall.dao.BannerDao;
import com.hjh.mall.entity.Banner;
import com.hjh.mall.field.RestFulAPI;
import com.hjh.mall.field.type.Status;
import com.hjh.mall.service.BannerService;
import com.hjh.mall.service.UploadService;
import com.hjh.mall.vo.banner.Addbanner;
import com.hjh.mall.vo.banner.GetBannerListVo;
import com.hjh.mall.vo.banner.UpdateBanner;
import com.hjh.mall.vo.banner.UpdateBannerStatus;
import com.hjh.mall.vo.client.UploadPicture;

/**
 * @Project: hjy-middle
 * @Description TODO
 * @author 曾繁林
 * @date 2016年12月14日
 * @version V1.0
 */
@Controller
public class BannerController {
	@Resource
	private BannerService bannerService;
	@Resource
	private UploadService uploadService;

	@Reference(version = "1.0.0")
	private BizMetadataService bizMetadataService;
	@Resource
	private BannerDao bannerDao;

	@NoLogin
	@RequestMapping(RestFulAPI.Banner.GET_BANNER_LIST)
	@ResponseBody
	public Map<String, Object> getBannerList(@RequestBody HJYVO hjyvo) {
		Banner banner = new Banner();
		banner.setStatus(Status.ENABLED.getVal());
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, bannerDao.getBannerListNew(banner));
		return result;
	}

	/**
	 * @Description: 添加banner图
	 * @param addBrandVo
	 * @return Map<String,Object>
	 */
	@RequestMapping("/addBanner")
	@ResponseBody
	public Map<String, Object> addbanner(@RequestBody Addbanner addbanner) {
		String operator_id = HJYBizDataContext.getSessionIdentity().getClientId();
		String operator_name = HJYBizDataContext.getSessionIdentity().getOperatorName();
		if (StringUtils.isNotBlank(addbanner.getBanner_path())) {
			if (addbanner.getBanner_path().contains("data")) {
				Map<String, Object> map = uploadOss(addbanner.getBanner_path(), UploadType.SHUFFLING_PICTURE.getVal());
				String banner_path = map.get(BasicFields.IMAGE_KEY).toString();
				addbanner.setBanner_path(banner_path);
			}
		}
		addbanner.setCreate_user_name(operator_name);
		addbanner.setUpdate_user_name(operator_name);
		addbanner.setCreate_user(operator_id);
		addbanner.setUpdate_user(operator_id);
		return bannerService.addBanner(addbanner);
	}

	/**
	 * @Description:修改轮播图
	 * @param addBrandVo
	 * @return Map<String,Object>
	 */
	@RequestMapping("/updateBanner")
	@ResponseBody
	public Map<String, Object> updatebanner(@RequestBody UpdateBanner updateBanner) {
		String operator_id = HJYBizDataContext.getSessionIdentity().getClientId();
		String operator_name = HJYBizDataContext.getSessionIdentity().getOperatorName();
		if (StringUtils.isNotBlank(updateBanner.getBanner_path())) {
			if (updateBanner.getBanner_path().contains("data")) {
				Map<String, Object> map = uploadOss(updateBanner.getBanner_path(),
						UploadType.SHUFFLING_PICTURE.getVal());
				String banner_path = map.get(BasicFields.IMAGE_KEY).toString();
				updateBanner.setBanner_path(banner_path);
			}
		}
		updateBanner.setUpdate_user_name(operator_name);
		updateBanner.setUpdate_user(operator_id);
		return bannerService.updateBanner(updateBanner);
	}

	/**
	 * @Description:获取轮播图列表
	 * @param addBrandVo
	 * @return Map<String,Object>
	 */
	@RequestMapping("/getBannerList")
	@ResponseBody
	public Map<String, Object> getBannerList(@RequestBody GetBannerListVo getBannerListVo) {
		return bannerService.getBannerList(getBannerListVo);
	}

	/**
	 * @Description:修改轮播图状态
	 * @param updateBannerStatus
	 * @return Map<String,Object>
	 */
	@RequestMapping("/updateBannerStatus")
	@ResponseBody
	public Map<String, Object> updateBannerStatus(@RequestBody UpdateBannerStatus updateBannerStatus) {
		String operator_id = HJYBizDataContext.getSessionIdentity().getClientId();
		String operator_name = HJYBizDataContext.getSessionIdentity().getOperatorName();
		updateBannerStatus.setUpdate_user(operator_id);
		updateBannerStatus.setUpdate_user_name(operator_name);
		return bannerService.updateBannerStatus(updateBannerStatus);
	}

	/**
	 * @Description: 上传图片
	 * @author 杨益桦
	 * @date 2016年8月1日
	 * @param imagecontext
	 * @param type
	 * @return Map<String,Object>
	 */
	private Map<String, Object> uploadOss(String imagecontext, String type) {
		UploadPicture uploadPicture = new UploadPicture();
		uploadPicture.setUpload_type(type);
		uploadPicture.setImagecontext(imagecontext);
		Map<String, Object> result = uploadService.uploadPic(uploadPicture);
		return result;
	}

}

package com.hjh.mall.service;

import java.util.Map;

import com.hjh.mall.common.core.service.base.ServiceBase;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.entity.Banner;
import com.hjh.mall.vo.banner.Addbanner;
import com.hjh.mall.vo.banner.GetBannerListVo;
import com.hjh.mall.vo.banner.UpdateBanner;
import com.hjh.mall.vo.banner.UpdateBannerStatus;

public interface BannerService extends ServiceBase<Banner, String> {
	// 添加轮播图
	public Map<String, Object> addBanner(Addbanner addbanner);

	// 修改轮播图
	public Map<String, Object> updateBanner(UpdateBanner updateBanner);

	// 按条件获取轮播图列表(后台管理)
	public Map<String, Object> getBannerList(GetBannerListVo getBannerListVo);

	// 按获取轮播图列表(含占位图)
	public Map<String, Object> getBannerListByMobile(HJYVO hjyvo);

	// 修改轮播图状态
	public Map<String, Object> updateBannerStatus(UpdateBannerStatus updateBannerStatus);
}
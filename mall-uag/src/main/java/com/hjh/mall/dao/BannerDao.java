package com.hjh.mall.dao;

import java.util.List;
import java.util.Map;

import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.entity.Banner;

/**
 * @Project: hjy-middle
 * @Description 轮播图业务层
 * @author 曾繁林
 * @date 2016年12月14日
 * @version V1.0
 */
public interface BannerDao extends DAOBase<Banner, String> {
	/**
	 * 
	 * @date 2016年12月14日
	 * @Description: 后台获取轮播图列表
	 * @param banner
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> getBannerList(Banner banner);

	/**
	 * 
	 * @date 2017年3月28日
	 * @Description: APP使用
	 * @param banner
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> getBannerListNew(Banner banner);

	/**
	 * 
	 * @date 2016年12月15日
	 * @Description: 获取轮播图列表(APP)
	 * @param banner
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> getBannerListByCategoryId(Banner banner);
}
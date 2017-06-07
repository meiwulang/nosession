package com.hjh.mall.category.service;

import java.util.List;

import com.hjh.mall.category.entity.BrandInfo;
import com.hjh.mall.category.entity.CarBrand;
import com.hjh.mall.common.core.service.base.ServiceBase;

/**
 * @Project: hjy-middle
 * @Description 品牌介绍业务层
 * @author boochy
 * @date 2017年05月08日
 * @version V1.0
 */
public interface BrandInfoService extends ServiceBase<BrandInfo, String> {
	
	public List<BrandInfo> isExist(BrandInfo example);

}

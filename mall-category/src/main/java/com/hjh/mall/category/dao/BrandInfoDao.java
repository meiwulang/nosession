package com.hjh.mall.category.dao;

import java.util.List;

import com.hjh.mall.category.entity.BrandInfo;
import com.hjh.mall.category.entity.CarBrand;
import com.hjh.mall.common.core.dao.base.DAOBase;
/**
 * @Project: hjy-middle
 * @Description 品牌介绍业务层
 * @author boochy
 * @date 2017年05月08日
 * @version V1.0
 */
public interface BrandInfoDao extends DAOBase<BrandInfo, String> {
	public List<BrandInfo> isExist(BrandInfo example);
}
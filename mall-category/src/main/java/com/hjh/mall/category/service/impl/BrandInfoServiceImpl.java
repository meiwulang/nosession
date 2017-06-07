package com.hjh.mall.category.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjh.mall.category.dao.BrandInfoDao;
import com.hjh.mall.category.entity.BrandInfo;
import com.hjh.mall.category.entity.CarBrand;
import com.hjh.mall.category.service.BrandInfoService;
import com.hjh.mall.category.service.base.HJYServiceImplBase;
import com.hjh.mall.common.core.dao.base.DAOBase;

/**
 * @Project: hjy-middle
 * @Description 品牌介绍业务层
 * @author boochy
 * @date 2017年05月08日
 * @version V1.0
 */

@Service
public class BrandInfoServiceImpl extends HJYServiceImplBase<BrandInfo, String>
		implements
			BrandInfoService {
	@Resource
	private BrandInfoDao brandInfoDao;
	@Override
	protected DAOBase<BrandInfo, String> getDAO() {
		return brandInfoDao;
	}
	
	@Override
	public List<BrandInfo> isExist(BrandInfo example) {
		return brandInfoDao.isExist(example);
	}

}

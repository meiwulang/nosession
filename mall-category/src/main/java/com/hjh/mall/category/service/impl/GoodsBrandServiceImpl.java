package com.hjh.mall.category.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjh.mall.category.dao.GoodsBrandDao;
import com.hjh.mall.category.entity.GoodsBrand;
import com.hjh.mall.category.service.GoodsBrandService;
import com.hjh.mall.category.service.base.HJYServiceImplBase;
import com.hjh.mall.common.core.dao.base.DAOBase;

/**
 * @Project: hjy-middle
 * @Description 商品品牌业务层
 * @author boochy
 * @date 2017年03月15日
 * @version V1.0
 */

@Service
public class GoodsBrandServiceImpl extends HJYServiceImplBase<GoodsBrand, String>
		implements
			GoodsBrandService {
	@Resource
	private GoodsBrandDao goodsBrandDao;
	@Override
	protected DAOBase<GoodsBrand, String> getDAO() {
		return goodsBrandDao;
	}
	@Override
	public int countBlur(Map example) {
		return goodsBrandDao.countBlur(example);
	}
	@Override
	public List<GoodsBrand> queryBlur(Map example) {
		return goodsBrandDao.queryBlur(example);
	}
	@Override
	public List<GoodsBrand> isExist(GoodsBrand example) {
		return goodsBrandDao.isExist(example);
	}
	
	@Override
	public void batchStatus(Map example) {
		goodsBrandDao.batchStatus(example);
	}
}

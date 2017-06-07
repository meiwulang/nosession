package com.hjh.mall.category.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjh.mall.category.dao.CarBrandDao;
import com.hjh.mall.category.entity.CarBrand;
import com.hjh.mall.category.service.CarBrandService;
import com.hjh.mall.category.service.base.HJYServiceImplBase;
import com.hjh.mall.common.core.dao.base.DAOBase;

/**
 * @Project: hjy-middle
 * @Description 车辆品牌业务层
 * @author boochy
 * @date 2017年03月14日
 * @version V1.0
 */

@Service
public class CarBrandServiceImpl extends HJYServiceImplBase<CarBrand, String>
		implements
			CarBrandService {
	@Resource
	private CarBrandDao carBrandDao;
	@Override
	protected DAOBase<CarBrand, String> getDAO() {
		return carBrandDao;
	}
	@Override
	public int countBlur(Map example) {
		return carBrandDao.countBlur(example);
	}
	@Override
	public List<CarBrand> queryBlur(Map example) {
		return carBrandDao.queryBlur(example);
	}
	@Override
	public List<CarBrand> isExist(CarBrand example) {
		return carBrandDao.isExist(example);
	}
	@Override
	public void batchStatus(Map example) {
		carBrandDao.batchStatus(example);
	}

}

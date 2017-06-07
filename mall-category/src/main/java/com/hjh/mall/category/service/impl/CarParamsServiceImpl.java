package com.hjh.mall.category.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjh.mall.category.dao.CarParamsDao;
import com.hjh.mall.category.entity.CarParams;
import com.hjh.mall.category.service.CarParamsService;
import com.hjh.mall.category.service.base.HJYServiceImplBase;
import com.hjh.mall.common.core.dao.base.DAOBase;

/**
 * @Project: hjy-middle
 * @Description 车型业务层
 * @author 陈士俊
 * @date 2017年03月14日
 * @version V1.0
 */

@Service
public class CarParamsServiceImpl extends HJYServiceImplBase<CarParams, String> implements CarParamsService {
	@Resource
	private CarParamsDao car_paramsDao;

	@Override
	protected DAOBase<CarParams, String> getDAO() {
		return car_paramsDao;
	}

	@Override
	public List<Map<String, String>> queryCarParamsByIds(List<String> ids) {
		return car_paramsDao.queryCarParamsByIds(ids);
	}

	@Override
	public void batchUpdate(List<CarParams> carList) {
		car_paramsDao.batchUpdate(carList);

	}

	@Override
	public void batchSavaCarParams(List<CarParams> carList) {
		car_paramsDao.batchSavaCarParams(carList);

	}

	@Override
	public void batchDelete(List<CarParams> carList) {
		car_paramsDao.batchDeleteCarParams(carList);
	}

}

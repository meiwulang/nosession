package com.hjh.mall.category.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjh.mall.category.dao.CarModelsDao;
import com.hjh.mall.category.entity.CarModels;
import com.hjh.mall.category.service.CarModelsService;
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
public class CarModelsServiceImpl extends HJYServiceImplBase<CarModels, String> implements CarModelsService {
	@Resource
	private CarModelsDao carModelsDao;

	@Override
	protected DAOBase<CarModels, String> getDAO() {
		return carModelsDao;
	}

	@Override
	public List<Map<String, Object>> queryCarModelList(Map<String, Object> map) {

		return carModelsDao.queryCarModelList(map);
	}

	@Override
	public int countCarModelBylike(Map<String, Object> map) {
		return carModelsDao.countByLike(map);
	}

	@Override
	public List<Map<String, Object>> queryModelsByLike(Map<String, Object> param) {
		return carModelsDao.queryModelsByLike(param);
	}

	@Override
	public void updateCarParamsBatch() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getMetedataIds(String brand_id) {
		// TODO Auto-generated method stub
		return carModelsDao.getMetedataIds(brand_id);
	}

	@Override
	public void updateCarStatusBatch(CarModels carModels) {
		carModelsDao.updateCarStatusBatch(carModels);
	}

	@Override
	public List<String> getIdsByMetadataOrBrand(CarModels carModels) {
		return carModelsDao.getIdsByMetadataOrBrand(carModels);
	}

	@Override
	public List<String> getBrandIdsByIds(CarModels carModels) {
		return carModelsDao.getBrandIdsByIds(carModels);
	}

}

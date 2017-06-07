package com.hjh.mall.category.service;

import java.util.List;
import java.util.Map;

import com.hjh.mall.category.entity.CarModels;
import com.hjh.mall.common.core.service.base.ServiceBase;

/**
 * @Project: hjy-middle
 * @Description 车型业务层
 * @author 陈士俊
 * @date 2017年03月14日
 * @version V1.0
 */
public interface CarModelsService extends ServiceBase<CarModels, String> {
	public List<Map<String, Object>> queryCarModelList(Map<String, Object> map);

	public int countCarModelBylike(Map<String, Object> map);

	public List<Map<String, Object>> queryModelsByLike(Map<String, Object> param);

	public void updateCarParamsBatch();

	public String getMetedataIds(String brand_id);

	void updateCarStatusBatch(CarModels carModels);

	public List<String> getIdsByMetadataOrBrand(CarModels carModels);

	public List<String> getBrandIdsByIds(CarModels carModels);
}

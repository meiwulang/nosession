package com.hjh.mall.category.dao;

import java.util.List;
import java.util.Map;

import com.hjh.mall.category.entity.CarModels;
import com.hjh.mall.common.core.dao.base.DAOBase;

/**
 * @Project: hjy-middle
 * @Description 车型业务层
 * @author 陈士俊
 * @date 2017年03月14日
 * @version V1.0
 */
public interface CarModelsDao extends DAOBase<CarModels, String> {

	public List<Map<String, Object>> queryCarModelList(Map<String, Object> param);

	public int countByLike(Map<String, Object> param);

	public List<Map<String, Object>> queryModelsByLike(Map<String, Object> param);

	public String getMetedataIds(String brand_id);

	public void updateCarStatusBatch(CarModels carModels);

	public List<String> getIdsByMetadataOrBrand(CarModels carModels);

	public List<String> getBrandIdsByIds(CarModels carModels);

}
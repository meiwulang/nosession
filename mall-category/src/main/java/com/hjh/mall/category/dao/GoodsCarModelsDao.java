package com.hjh.mall.category.dao;

import java.util.List;
import java.util.Map;

import com.hjh.mall.category.entity.GoodsCarModels;
import com.hjh.mall.common.core.dao.base.DAOBase;

/**
 * @Project: hjy-middle
 * @Description 商品车型关联表业务层
 * @author 陈士俊
 * @date 2017年03月15日
 * @version V1.0
 */
public interface GoodsCarModelsDao extends DAOBase<GoodsCarModels, String> {
	void saveBatchGoodsCar(List<GoodsCarModels> goodsCarModelsList);

	/**
	 * @date 2017年3月16日
	 * @Description: 根据id查询 modles
	 * @author 杨益桦
	 * @param goodsCarModels
	 * @return Map<String,Object>
	 */
	List<String> queryModelIds(GoodsCarModels goodsCarModels);

	void batchSave(List<GoodsCarModels> list);

	void batchDelete(GoodsCarModels goodsCarModels);

	Map<String, String> queryNamesByGoodsId(String goods_id);

	List<String> queryGoodsByCarId(String car_models_id);

	List<String> queryGoodsByCarIds(GoodsCarModels goodsCarModels);
}
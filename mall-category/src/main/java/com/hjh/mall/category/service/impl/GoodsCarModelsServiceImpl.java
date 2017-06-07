package com.hjh.mall.category.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjh.mall.category.dao.GoodsCarModelsDao;
import com.hjh.mall.category.entity.GoodsCarModels;
import com.hjh.mall.category.service.GoodsCarModelsService;
import com.hjh.mall.category.service.base.HJYServiceImplBase;
import com.hjh.mall.common.core.dao.base.DAOBase;

/**
 * @Project: hjy-middle
 * @Description 商品车型关联表业务层
 * @author 陈士俊
 * @date 2017年03月15日
 * @version V1.0
 */

@Service
public class GoodsCarModelsServiceImpl extends HJYServiceImplBase<GoodsCarModels, String>
		implements GoodsCarModelsService {
	@Resource
	private GoodsCarModelsDao goodsCarModelsDao;

	@Override
	protected DAOBase<GoodsCarModels, String> getDAO() {
		return goodsCarModelsDao;
	}

	@Override
	public void saveBatchGoodsCar(List<GoodsCarModels> goodsCarModelsList) {
		goodsCarModelsDao.saveBatchGoodsCar(goodsCarModelsList);

	}

	@Override
	public List<String> queryModelIds(GoodsCarModels goodsCarModels) {
		return goodsCarModelsDao.queryModelIds(goodsCarModels);
	}

	@Override
	public void batchSave(List<GoodsCarModels> list) {
		goodsCarModelsDao.batchSave(list);
	}

	@Override
	public void batchDelete(GoodsCarModels goodsCarModels) {
		goodsCarModelsDao.batchDelete(goodsCarModels);

	}

	@Override
	public Map<String, String> queryNamesByGoodsId(String goods_id) {

		return goodsCarModelsDao.queryNamesByGoodsId(goods_id);

	}

	@Override
	public List<String> queryGoodsByCarId(String car_models_id) {
		return goodsCarModelsDao.queryGoodsByCarId(car_models_id);
	}

	@Override
	public List<String> queryGoodsByCarIds(List<String> ids) {
		GoodsCarModels goodsCarModels = new GoodsCarModels();
		goodsCarModels.setIds(ids);
		return goodsCarModelsDao.queryGoodsByCarIds(goodsCarModels);
	}

}

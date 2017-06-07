/** * @author  csj
 * @date 创建时间：2017年3月15日 下午7:27:48 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  */
package com.hjh.mall.category.bizImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hjh.mall.cache.cache.helper.CacheHelper;
import com.hjh.mall.cache.cache.sequence.KeyGenerate;
import com.hjh.mall.category.bizapi.bizserver.goodscar.BizGoodsCarModelService;
import com.hjh.mall.category.bizapi.bizserver.goodscar.vo.AddGoodsCarModelsBatch;
import com.hjh.mall.category.entity.CarModels;
import com.hjh.mall.category.entity.GoodsCarModels;
import com.hjh.mall.category.service.CarBrandService;
import com.hjh.mall.category.service.CarModelsService;
import com.hjh.mall.category.service.GoodsCarModelsService;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.model.Pagination;
import com.hjh.mall.common.core.util.DateTimeUtil;
import com.hjh.mall.common.core.util.JSONUtil;
import com.hjh.mall.common.core.util.PinyinUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.field.constant.CacheKeys;
import com.hjh.mall.field.constant.MallFields;

@Component
public class BizGoodsCarModelServiceImpl implements BizGoodsCarModelService {
	@Resource
	private GoodsCarModelsService goodsCarModelsService;
	@Resource
	private KeyGenerate keyGenerate;
	@Resource
	private CacheHelper cacheHelper;
	@Resource
	private CarBrandService carBrandService;
	@Resource
	private CarModelsService carModelsService;

	@Override
	public Map<String, Object> addCarModelsBatch(AddGoodsCarModelsBatch addGoodsCarModelsBatch) {

		List<String> list = addGoodsCarModelsBatch.getCarModelList();
		List<GoodsCarModels> goodsCarModelsList = new ArrayList<GoodsCarModels>();
		if (list.size() > 0) {
			for (String carModelId : list) {
				GoodsCarModels goodsCarModels = new GoodsCarModels();
				Date date = new Date();
				String dateString = DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK);
				String timeString = DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK);
				goodsCarModels.setGoods_id(addGoodsCarModelsBatch.getGoods_id());
				goodsCarModels.setCar_models_id(carModelId);
				goodsCarModels.setGoods_car_models_id(keyGenerate.getKeyGenerate(MallFields.GOODS_CAR_MODELS));
				// String operateInfo = cacheHelper
				// .get("hjh.hjy.access_token." +
				// addGoodsCarModelsBatch.getAccess_token());
				// Map<String, String> map = JSONUtil.trans(operateInfo,
				// Map.class);
				goodsCarModels.setCreate_user_name(addGoodsCarModelsBatch.getAccess_token());
				goodsCarModels.setCreate_date(dateString);
				goodsCarModels.setCreate_time(timeString);
				goodsCarModelsList.add(goodsCarModels);
			}
			goodsCarModelsService.saveBatchGoodsCar(goodsCarModelsList);
		}
		return VOUtil.genSuccessResult();
	}

	@Override
	public Map<String, Object> queryGoodsCarModelList(AddGoodsCarModelsBatch addGoodsCarModelsBatch) {
		Map<String, Object> result = VOUtil.genSuccessResult();
		GoodsCarModels example = new GoodsCarModels();
		int tatol_num = 0;
		example.setGoods_id(addGoodsCarModelsBatch.getGoods_id());
		tatol_num = addPage(addGoodsCarModelsBatch, example, tatol_num);
		List<GoodsCarModels> list = goodsCarModelsService.query(example);
		List<Map<String, String>> resultList = getCarModelInfo(list);
		if (resultList.size() > 0) {
			sortCarNames(resultList);
		}
		result.put(BasicFields.RESULT_LIST, resultList);

		result.put(BasicFields.TOTAL_NUM, tatol_num);
		return result;
	}

	/**
	 * @param resultList
	 */
	private void sortCarNames(List<Map<String, String>> resultList) {
		Collections.sort(resultList, new Comparator<Map>() {
			public int compare(Map o1, Map o2) {
				if (PinyinUtil.getPingYin(o1.get(MallFields.CAR_BRAND_NAME).toString())
						.compareTo(PinyinUtil.getPingYin(o2.get(MallFields.CAR_BRAND_NAME).toString())) == 0) {
					if (PinyinUtil.getPingYin(o1.get(MallFields.CAR_TYPE).toString())
							.compareTo(PinyinUtil.getPingYin((o2.get(MallFields.CAR_TYPE).toString()))) == 0) {
						return PinyinUtil.getPingYin(o1.get(MallFields.CAR_MODELS_NAME).toString())
								.compareTo(PinyinUtil.getPingYin(o2.get(MallFields.CAR_MODELS_NAME).toString()));

					} else {
						return PinyinUtil.getPingYin(o1.get(MallFields.CAR_TYPE).toString())
								.compareTo(PinyinUtil.getPingYin((o2.get(MallFields.CAR_TYPE).toString())));
					}
				} else {
					return PinyinUtil.getPingYin(o1.get(MallFields.CAR_BRAND_NAME).toString())
							.compareTo(PinyinUtil.getPingYin(o2.get(MallFields.CAR_BRAND_NAME).toString()));
				}
			}
		});
	}

	/**
	 * @param list
	 * @return
	 */
	private List<Map<String, String>> getCarModelInfo(List<GoodsCarModels> list) {
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		if (list.size() > 0) {
			for (GoodsCarModels goodsCarModels : list) {
				Map<String, String> memberMap = cacheHelper
						.hgetAll(CacheKeys.CAR_MODELS_ID_PREFIX + goodsCarModels.getCar_models_id());
				if (memberMap.size() > 0) {
					resultList.add(memberMap);
				} else {
					CarModels carModels = carModelsService.get(goodsCarModels.getCar_models_id());
					memberMap.put(MallFields.CAR_BRAND_ID, carModels.getBrand_id());
					memberMap.put(MallFields.CAR_BRAND_NAME,
							carBrandService.get(carModels.getBrand_id()).getBrand_name());
					memberMap.put(MallFields.CAR_TYPE, cacheHelper.hmget(
							CacheKeys.METADATA_ID_PREFIX + carModels.getMetadata_id(), MallFields.METADATA_NAME));
					memberMap.put(MallFields.METADATA_ID, carModels.getMetadata_id());
					memberMap.put(MallFields.CAR_MODELS_ID, carModels.getCar_models_id());
					memberMap.put(MallFields.CAR_MODELS_NAME, carModels.getCar_models_name());
					cacheHelper.hmset(CacheKeys.CAR_MODELS_ID_PREFIX + carModels.getCar_models_id(), memberMap);
					resultList.add(memberMap);
				}
			}
		}
		return resultList;
	}

	/**
	 * @param addGoodsCarModelsBatch
	 * @param example
	 */
	private int addPage(AddGoodsCarModelsBatch addGoodsCarModelsBatch, GoodsCarModels example, int tatol_num) {
		Pagination p = new Pagination();
		p.setPage_no(addGoodsCarModelsBatch.getPage());
		p.setPage_size(addGoodsCarModelsBatch.getLimit());
		tatol_num = goodsCarModelsService.count(example);
		p.setTotal_item_num(tatol_num);
		p.calc();
		example.setPage(p);
		return tatol_num;
	}

	@Override
	public Map<String, Object> queryGoodsCarModelListIsChoosed(AddGoodsCarModelsBatch addGoodsCarModelsBatch) {
		Map<String, Object> result = VOUtil.genSuccessResult();
		GoodsCarModels example = new GoodsCarModels();
		int tatol_num = 0;
		example.setGoods_id(addGoodsCarModelsBatch.getGoods_id());
		tatol_num = addPage(addGoodsCarModelsBatch, example, tatol_num);

		List<GoodsCarModels> list = goodsCarModelsService.query(example);

		List<CarModels> allCarList = carModelsService.query(null);
		List<GoodsCarModels> abList = JSONUtil.transInSide(allCarList, GoodsCarModels.class);
		List<GoodsCarModels> abcList = JSONUtil.transInSide(allCarList, GoodsCarModels.class);
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();

		// 取并集
		abList.removeAll(list);
		if (allCarList.size() > 0) {
			for (GoodsCarModels goodsCarModels : abList) {
				Map<String, String> memberMap = cacheHelper
						.hgetAll(CacheKeys.CAR_MODELS_ID_PREFIX + goodsCarModels.getCar_models_id());
				resultList.add(memberMap);
				memberMap.put("is_choosed", "0");
			}
		}
		// 取交集
		list.retainAll(abcList);
		if (list.size() > 0) {
			for (GoodsCarModels goodsCarModels : list) {
				Map<String, String> memberMap = cacheHelper
						.hgetAll(CacheKeys.CAR_MODELS_ID_PREFIX + goodsCarModels.getCar_models_id());
				resultList.add(memberMap);
				memberMap.put("is_choosed", "1");
			}
		}

		result.put(BasicFields.RESULT_LIST, resultList);

		result.put(BasicFields.TOTAL_NUM, tatol_num);
		return result;
	}

	@Override
	public Map<String, Object> modifyGoodsCarModel(AddGoodsCarModelsBatch addGoodsCarModelsBatch) {
		GoodsCarModels goodsCarModels = new GoodsCarModels();
		goodsCarModels.setGoods_id(addGoodsCarModelsBatch.getGoods_id());
		List<String> existList = goodsCarModelsService.queryModelIds(goodsCarModels);
		List<String> existList2 = goodsCarModelsService.queryModelIds(goodsCarModels);
		List<String> currentList = addGoodsCarModelsBatch.getCarModelList();

		// 删除原来有的
		existList.removeAll(currentList);
		List<String> delList = existList;
		GoodsCarModels delEntity = new GoodsCarModels();
		delEntity.setGoods_id(addGoodsCarModelsBatch.getGoods_id());
		delEntity.setIds(delList);
		if (delList.size() > 0) {
			goodsCarModelsService.batchDelete(delEntity);
		}

		// 添加新增的
		currentList.removeAll(existList2);
		List<String> addList = currentList;
		List<GoodsCarModels> addEntityList = new ArrayList<>();
		for (String id : addList) {
			GoodsCarModels addEntity = new GoodsCarModels();
			Date date = new Date();
			String dateString = DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK);
			String timeString = DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK);
			addEntity.setGoods_car_models_id(keyGenerate.getKeyGenerate(MallFields.GOODS_CAR_MODELS));
			addEntity.setGoods_id(addGoodsCarModelsBatch.getGoods_id());
			addEntity.setCar_models_id(id);
			addEntity.setCreate_date(dateString);
			addEntity.setCreate_time(timeString);
			addEntity.setCreate_user_name(addGoodsCarModelsBatch.getAccess_token());
			addEntityList.add(addEntity);
		}
		if (addEntityList.size() > 0) {
			goodsCarModelsService.batchSave(addEntityList);
		}
		Map<String, Object> result = VOUtil.genSuccessResult();
		return result;
	}

	@Override
	public Map<String, Object> queryNamesByGoodsId(String goods_id) {
		Map<String, Object> result = VOUtil.genSuccessResult();
		Map<String, String> map = goodsCarModelsService.queryNamesByGoodsId(goods_id);
		// TypeNamesInSolr typeNamesInSolr = JSONUtil.trans(map,
		// TypeNamesInSolr.class);
		result.put("typeNamesInSolr", map);
		return result;
	}

	@Override
	public Map<String, Object> deleteBatchNamesByGoodsId(AddGoodsCarModelsBatch addGoodsCarModelsBatch) {
		GoodsCarModels goodsCarModels = new GoodsCarModels();
		goodsCarModels.setGoods_id(addGoodsCarModelsBatch.getGoods_id());
		List<String> list = addGoodsCarModelsBatch.getCarModelList();
		goodsCarModels.setIds(list);
		goodsCarModelsService.batchDelete(goodsCarModels);
		return VOUtil.genSuccessResult();
	}

	@Override
	public Map<String, Object> queryGoodsNamesByCarId(String car_models_id) {
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, goodsCarModelsService.queryGoodsByCarId(car_models_id));
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hjh.mall.category.bizapi.bizserver.goodscar.BizGoodsCarModelService
	 * #queryGoodsIdsByCarId(java.lang.String)
	 */
	@Override
	public Map<String, Object> queryGoodsIdsByCarId(String car_models_id) {
		Map<String, Object> result = VOUtil.genSuccessResult();
		GoodsCarModels example = new GoodsCarModels();
		List<GoodsCarModels> list = goodsCarModelsService.query(example);
		result.put(BasicFields.RESULT_LIST, list);
		return result;
	}

	@Override
	public List<String> queryGoodsIdsByCarIds(List<String> ids) {
		return goodsCarModelsService.queryGoodsByCarIds(ids);
	}

}

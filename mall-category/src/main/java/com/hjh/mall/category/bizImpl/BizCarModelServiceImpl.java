/** * @author  csj
 * @date 创建时间：2017年3月14日 下午3:05:31 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  */
package com.hjh.mall.category.bizImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.hjh.mall.cache.cache.helper.CacheHelper;
import com.hjh.mall.cache.cache.sequence.KeyGenerate;
//import com.hjh.hjy.cache.helper.CacheHelper;
//import com.hjh.hjy.cache.sequence.KeyGenerate;
//import com.hjh.hjy.jike.filed.constants.HjyFileds;
//import com.hjh.hjy.jike.filed.type.Status;
import com.hjh.mall.category.bizapi.bizserver.car.BizCarModelService;
import com.hjh.mall.category.bizapi.bizserver.car.vo.AddCarModel;
import com.hjh.mall.category.bizapi.bizserver.car.vo.AddCarParams;
import com.hjh.mall.category.bizapi.bizserver.car.vo.QueryCarModelLike;
import com.hjh.mall.category.bizapi.bizserver.car.vo.QueryCarModels;
import com.hjh.mall.category.bizapi.bizserver.car.vo.QueryCarModelsApp;
import com.hjh.mall.category.bizapi.bizserver.car.vo.QueryIdsByMetadataOrBrand;
import com.hjh.mall.category.bizapi.bizserver.car.vo.UpdataCarStatusBatch;
import com.hjh.mall.category.bizapi.bizserver.car.vo.UpdateCarModel;
import com.hjh.mall.category.entity.CarModels;
import com.hjh.mall.category.entity.CarParams;
import com.hjh.mall.category.entity.Metadata;
import com.hjh.mall.category.service.CarModelsService;
import com.hjh.mall.category.service.CarParamsService;
import com.hjh.mall.category.service.GoodsCarModelsService;
import com.hjh.mall.category.service.MetadataService;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.model.Pagination;
import com.hjh.mall.common.core.util.BeanUtil;
import com.hjh.mall.common.core.util.DateTimeUtil;
import com.hjh.mall.common.core.util.JSONUtil;
import com.hjh.mall.common.core.util.PinyinUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.field.constant.CacheKeys;
import com.hjh.mall.field.constant.MallFields;
import com.hjh.mall.field.error.MallErrorCode;
import com.hjh.mall.field.type.Status;

@Component
public class BizCarModelServiceImpl implements BizCarModelService {
	@Resource
	private CarModelsService carModelsService;
	@Resource
	private MetadataService metadataService;
	@Resource
	private KeyGenerate keyGenerate;
	@Resource
	private CacheHelper cacheHelper;
	@Resource
	private CarParamsService carParamsService;
	@Resource
	private GoodsCarModelsService goodsCarModelsService;
//	private static ExcelUtil eu = ExcelUtil.getInstance();

	@Override
	public Map<String, Object> addCarModel(AddCarModel addCarModel) {
		Map<String, Object> result = VOUtil.genSuccessResult();
		CarModels entity = new CarModels();
		try {
			BeanUtil.reflectionAttr(addCarModel, entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		CarModels oldCarModels = new CarModels();
		oldCarModels.setBrand_id(addCarModel.getBrand_id());
		oldCarModels.setMetadata_id(addCarModel.getMetadata_id());
		oldCarModels.setCar_models_name(addCarModel.getCar_models_name());
		if (checkCarModel(oldCarModels, null)) {
			result = VOUtil.genErrorResult(MallErrorCode.CarModelErrorCode.CAR_MODEL_EXIST);
		} else {
			setCarModelParams(addCarModel, entity);
			String car_params_ids = "";

			car_params_ids = saveCarParamsList(addCarModel, entity, car_params_ids);
			entity.setCar_params_ids(car_params_ids.toString());
			carModelsService.save(entity);
			if (Status.ENABLED.getVal().equals(addCarModel.getApp_show())) {
				// 记录车型信息
				saveModelInCache(addCarModel, entity);
				// 根据品牌寸类型名称（去重）
				cacheHelper.zdd(CacheKeys.BRAND_CAR_METADATA_NAMES_PREFIX + entity.getBrand_id(),
						Double.valueOf(entity.getMetadata_id()), addCarModel.getMetadata_name());
				// 根据品牌类型寸车型名称（去重的）
				saveCarModelsByBrandAndMetadataInCache(entity);

				// saveMetadatasByBrandInCache(entity);

			}

		}
		return result;
	}

	/**
	 * @param entity
	 */
	@SuppressWarnings("unused")
	private void saveMetadatasByBrandInCache(CarModels entity) {
		cacheHelper.lpush(CacheKeys.BRAND_CAR_METADATA_PREFIX + entity.getBrand_id(), entity.getMetadata_id());
	}

	/**
	 * 根据品牌类型为主键存入所有的车型
	 * 
	 * @param entity
	 */
	private void saveCarModelsByBrandAndMetadataInCache(CarModels entity) {
		String key = CacheKeys.getCarModelsByBrandAndMetadata(entity.getBrand_id(), entity.getMetadata_id());
		cacheHelper.zdd(key, Double.valueOf(entity.getCar_models_id()), entity.getCar_models_id());
	}

	/**
	 * @param addCarModel
	 */
	private void saveModelInCache(AddCarModel addCarModel, CarModels entity) {
		Map<String, String> member = new HashMap<String, String>();
		member.put(MallFields.CAR_BRAND_ID, addCarModel.getBrand_id());
		member.put(MallFields.CAR_BRAND_NAME, addCarModel.getBrand_name());
		member.put(MallFields.CAR_TYPE, addCarModel.getMetadata_name());
		member.put(MallFields.METADATA_ID, addCarModel.getMetadata_id());
		member.put(MallFields.CAR_MODELS_ID, entity.getCar_models_id());
		member.put(MallFields.CAR_MODELS_NAME, addCarModel.getCar_models_name());
		cacheHelper.hmset(CacheKeys.CAR_MODELS_ID_PREFIX + entity.getCar_models_id(), member);
	}

	@SuppressWarnings("unchecked")
	/**
	 * @param addCarModel
	 * @param entity
	 */
	private void setCarModelParams(AddCarModel addCarModel, CarModels entity) {
		entity.setCar_models_id(keyGenerate.getKeyGenerate(MallFields.CAR_MODELS));
		Date date = new Date();
		String dateString = DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK);
		String timeString = DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK);
		entity.setCreate_date(dateString);
		entity.setCreate_time(timeString);
		entity.setUpdate_date(dateString);
		entity.setUpdate_time(timeString);
		String operateInfo = cacheHelper.get("hjh.hjy.access_token." + addCarModel.getAccess_token());
		Map<String, String> map = JSONUtil.trans(operateInfo, Map.class);
		entity.setCreate_user(map.get("operatorName"));
		entity.setStatus(addCarModel.getStatus());
		entity.setUpdate_user_name(map.get("operatorName"));
		entity.setUpdate_user(map.get(BasicFields.CLIENTID));
		entity.setCar_model_alias(addCarModel.getBrand_name() + " " + addCarModel.getMetadata_name() + " "
				+ addCarModel.getCar_models_name());
	}

	/**
	 * 保存车型参数
	 * 
	 * @param addCarModel
	 * @param entity
	 */
	private String saveCarParamsList(AddCarModel addCarModel, CarModels entity, String car_params_ids) {
		List<CarParams> carList = new ArrayList<CarParams>();
		if (null != addCarModel.getCarParamsList() && addCarModel.getCarParamsList().size() > 0) {
			for (AddCarParams addCarParams : addCarModel.getCarParamsList()) {
				CarParams carParams = new CarParams();
				String car_params_id = keyGenerate.getKeyGenerate(MallFields.CAR_PARAMS);
				carParams.setCar_params_id(car_params_id);
				carParams.setCar_params_name(addCarParams.getCar_params_name());
				carParams.setCar_params_value(addCarParams.getCar_params_value());
				carList.add(carParams);
				car_params_ids = car_params_ids + car_params_id + ",";
			}
			carParamsService.batchSavaCarParams(carList);

		}
		return car_params_ids;

	}

	@Override
	public Map<String, Object> updateCarModel(UpdateCarModel updateCarModel) {
		Map<String, Object> result = VOUtil.genSuccessResult();
		CarModels entity = new CarModels();
		try {
			BeanUtil.reflectionAttr(updateCarModel, entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		entity.setStatus(updateCarModel.getStatus());
		// CarModels carModels =
		// carModelsService.get(entity.getCar_models_id());
		CarModels oldCarModels = new CarModels();
		oldCarModels.setBrand_id(updateCarModel.getBrand_id());
		oldCarModels.setMetadata_id(updateCarModel.getMetadata_id());
		oldCarModels.setCar_models_name(updateCarModel.getCar_models_name());
		if (checkCarModel(oldCarModels, entity.getCar_models_id())) {
			result = VOUtil.genErrorResult(MallErrorCode.CarModelErrorCode.CAR_MODEL_EXIST);
		} else {
			String car_params_ids = "";
			car_params_ids = updateCarParams(updateCarModel, car_params_ids);
			// String carNameString =
			// carModelsService.get(updateCarModel.getCar_models_id()).getCar_models_name();
			updateCarModel(updateCarModel, entity, car_params_ids);

			updateCarModelInCache(updateCarModel, entity);

			if (entity.getApp_show().equals("1")) {

				saveCarModelsByBrandAndMetadataInCache(entity);
			} else {
				String key = CacheKeys.getCarModelsByBrandAndMetadata(entity.getBrand_id(), entity.getMetadata_id());
				cacheHelper.zremrangeByScore(key, Double.valueOf(entity.getCar_models_id()),
						Double.valueOf(entity.getCar_models_id()));
			}
			// 暂时不做修改类型处理
			// updateMetadataNamesByBrandInCache(entity);

			// updateCarModelNamesByBrandAndMetadataInCache(entity);
			// 查询该车型下的商品ids

		}

		return result;
	}

	public boolean checkCarModel(CarModels carModels, String id) {
		boolean flag = false;
		List<CarModels> list = carModelsService.query(carModels);
		if (list != null && list.size() > 0) {// 名称重复
			if (!StringUtils.isBlank(id)) {
				for (CarModels m : list) {
					if (!m.getCar_models_id().equals(id)) {
						flag = true;
						continue;
					}
				}
			} else {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * @param updateCarModel
	 * @param entity
	 */
	private void updateCarModelInCache(UpdateCarModel updateCarModel, CarModels entity) {
		Map<String, String> member = new HashMap<String, String>();
		member.put(MallFields.CAR_BRAND_ID, updateCarModel.getBrand_id());
		member.put(MallFields.CAR_BRAND_NAME, updateCarModel.getBrand_name());
		member.put(MallFields.CAR_TYPE, updateCarModel.getMetadata_name());
		member.put(MallFields.METADATA_ID, updateCarModel.getMetadata_id());
		member.put(MallFields.CAR_MODELS_ID, entity.getCar_models_id());
		member.put(MallFields.CAR_MODELS_NAME, updateCarModel.getCar_models_name());
		cacheHelper.hmset(CacheKeys.CAR_MODELS_ID_PREFIX + entity.getCar_models_id(), member);
	}

	/**
	 * @param entity
	 */
	private void updateCarModelNamesByBrandAndMetadataInCache(CarModels entity) {
		String metedataIds = carModelsService.getMetedataIds(entity.getBrand_id());
		cacheHelper.destroy(CacheKeys.BRAND_CAR_METADATA_NAMES_PREFIX + entity.getBrand_id());
		if (StringUtils.isNotBlank(metedataIds)) {
			String[] value = metedataIds.split(",");
			for (String string : value) {
				String metadataName = cacheHelper.hmget(CacheKeys.METADATA_ID_PREFIX + string, "metadata_name");
				if (StringUtils.isBlank(metadataName)) {
					Metadata metadata = new Metadata();
					metadata.setMetadata_id(string);
					Map<String, Object> map = metadataService.getMetadataById(metadata);
					cacheHelper.zdd(CacheKeys.BRAND_CAR_METADATA_NAMES_PREFIX + entity.getBrand_id(),
							Double.valueOf(string), map.get("metadata_name").toString());
				} else {
					cacheHelper.zdd(CacheKeys.BRAND_CAR_METADATA_NAMES_PREFIX + entity.getBrand_id(),
							Double.valueOf(string), metadataName);
				}
			}
		}
	}

	/**
	 * @param entity
	 */
	@SuppressWarnings("unused")
	private void updateMetadataNamesByBrandInCache(CarModels entity) {
		String metedataIds = carModelsService.getMetedataIds(entity.getBrand_id());
		cacheHelper.zremrangeByScore(CacheKeys.BRAND_CAR_METADATA_NAMES_PREFIX + entity.getBrand_id(), 0, -1);
		if (StringUtils.isNotBlank(metedataIds)) {
			String[] value = metedataIds.split(",");
			for (String string : value) {
				String metadataName = cacheHelper.hmget(CacheKeys.METADATA_ID_PREFIX + string, "metadata_name");
				if (StringUtils.isBlank(metadataName)) {
					Metadata metadata = new Metadata();
					metadata.setMetadata_id(string);
					Map<String, Object> map = metadataService.getMetadataById(metadata);
					cacheHelper.zdd(CacheKeys.BRAND_CAR_METADATA_NAMES_PREFIX + entity.getBrand_id(),
							Double.valueOf(string), map.get("metadata_name").toString());
				} else {
					cacheHelper.zdd(CacheKeys.BRAND_CAR_METADATA_NAMES_PREFIX + entity.getBrand_id(),
							Double.valueOf(string), metadataName);
				}
			}
		}
	}

	/**
	 * 根据品牌ID更新对应的类型
	 * 
	 * @param entity
	 */
	@SuppressWarnings("unused")
	private void updateMetadatasByBrandInCache(CarModels entity) {
		cacheHelper.destroy(CacheKeys.BRAND_CAR_METADATA_PREFIX + entity.getBrand_id());
		String metedataIds = carModelsService.getMetedataIds(entity.getBrand_id());
		if (StringUtils.isNotBlank(metedataIds)) {
			String[] value = metedataIds.split(",");
			cacheHelper.lpush(CacheKeys.BRAND_CAR_METADATA_PREFIX + entity.getBrand_id(), value);
		}
	}

	/**
	 * 更新车型数据进缓存
	 * 
	 * @param updateCarModel
	 * @param entity
	 */
	@SuppressWarnings("unused")
	private void updateModelInCache(UpdateCarModel updateCarModel, CarModels entity) {
		Map<String, String> map = cacheHelper.hgetAll(CacheKeys.CAR_MODELS_ID_PREFIX + entity.getCar_models_id());
		if (StringUtils.isNotBlank(updateCarModel.getBrand_name())) {
			map.put(MallFields.CAR_BRAND_ID, updateCarModel.getBrand_id());
			map.put(MallFields.CAR_BRAND_NAME, updateCarModel.getBrand_name());
		}
		if (StringUtils.isNotBlank(updateCarModel.getMetadata_name())) {
			map.put(MallFields.METADATA_ID, updateCarModel.getMetadata_id());
			map.put(MallFields.CAR_TYPE, updateCarModel.getMetadata_name());
		}
		if (StringUtils.isNotBlank(updateCarModel.getCar_models_name())) {
			map.put(MallFields.CAR_MODELS_ID, entity.getCar_models_id());
			map.put(MallFields.CAR_MODELS_NAME, updateCarModel.getCar_models_name());
		}
		cacheHelper.hmset(CacheKeys.CAR_MODELS_ID_PREFIX + entity.getCar_models_id(), map);
	}

	/**
	 * 更新车型
	 * 
	 * @param updateCarModel
	 * @param entity
	 * @param car_params_ids
	 */
	private void updateCarModel(UpdateCarModel updateCarModel, CarModels entity, String car_params_ids) {
		Date date = new Date();
		String dateString = DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK);
		String timeString = DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK);
		entity.setUpdate_date(dateString);
		entity.setUpdate_time(timeString);
		String operateInfo = cacheHelper.get("hjh.hjy.access_token." + updateCarModel.getAccess_token());
		@SuppressWarnings("unchecked")
		Map<String, String> map = JSONUtil.trans(operateInfo, Map.class);
		entity.setUpdate_user_name(map.get("operatorName"));
		entity.setUpdate_user(map.get(BasicFields.CLIENT_ID));
		entity.setCar_params_ids(car_params_ids);
		entity.setCar_model_alias(updateCarModel.getBrand_name() + " " + updateCarModel.getMetadata_name() + " "
				+ updateCarModel.getCar_models_name());
		carModelsService.update(entity);
	}

	/**
	 * 更新车型参数
	 * 
	 * @param updateCarModel
	 * @param car_params_ids
	 * @return
	 */
	private String updateCarParams(UpdateCarModel updateCarModel, String car_params_ids) {
		if (null != updateCarModel.getCarParamsList() && updateCarModel.getCarParamsList().size() > 0) {
			List<CarParams> carList = new ArrayList<CarParams>();
			for (AddCarParams addCarParams : updateCarModel.getCarParamsList()) {
				CarParams carParams = JSONUtil.trans(addCarParams, CarParams.class);
				if (StringUtils.isBlank(carParams.getCar_params_id())) {
					String car_params_id = keyGenerate.getKeyGenerate(MallFields.CAR_PARAMS);
					carParams.setCar_params_id(car_params_id);
					car_params_ids = car_params_ids + car_params_id + ",";
				} else {
					car_params_ids = car_params_ids + carParams.getCar_params_id() + ",";
				}
				carList.add(carParams);

			}
			carParamsService.batchDelete(carList);

			carParamsService.batchSavaCarParams(carList);
		}
		return car_params_ids;
	}

	@Override
	public Map<String, Object> queryCarModelList(QueryCarModels queryCarModels) {
		Map<String, Object> result = VOUtil.genSuccessResult();
		Map<String, Object> map = new HashMap<String, Object>();

		CarModels carModels = new CarModels();

		map = addQueryParams(queryCarModels, map, carModels);

		int total_item_num = addPageParam(queryCarModels, map, carModels);

		List<Map<String, Object>> result_list = carModelsService.queryCarModelList(map);

		addCarModelParamsInResult(result_list);
		result.put(BasicFields.RESULT_LIST, result_list);
		result.put(BasicFields.TOTAL_NUM, total_item_num);
		return result;
	}



	/**
	 * @param result_list
	 */
	private void addCarModelParamsInResult(List<Map<String, Object>> result_list) {
		for (Map<String, Object> carModels2 : result_list) {
			String car_params_ids = (String) carModels2.get(MallFields.CAR_PARAMS_IDS);
			carModels2.put(MallFields.CARPARAMSLIST, new ArrayList<Map<String, String>>());
			if (StringUtils.isNotBlank(car_params_ids)) {
				String[] idArr = car_params_ids.split(",");
				List<String> ids = new ArrayList<String>();
				for (String i : idArr) {
					ids.add(i);
				}
				List<Map<String, String>> list = carParamsService.queryCarParamsByIds(ids);
				carModels2.put(MallFields.CARPARAMSLIST, list);
			}

		}
	}

	/**
	 * @param queryCarModels
	 * @param map
	 * @param carModels
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> addQueryParams(QueryCarModels queryCarModels, Map<String, Object> map,
			CarModels carModels) {
		try {
			BeanUtil.reflectionAttr(queryCarModels, carModels);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map = JSONUtil.trans(carModels, Map.class);
		map.put(MallFields.IS_NOPARAMS, queryCarModels.getIs_noparams());
		map.put(MallFields.END_DATE, queryCarModels.getEnd_date());
		map.put(MallFields.START_DATE, queryCarModels.getStart_date());
		map.put(MallFields.BRAND_NAME, queryCarModels.getBrand_name());
		map.put(MallFields.METADATA_NAME, queryCarModels.getMetadata_name());
		map.put(MallFields.STATUS, queryCarModels.getStatus());
		map.put(MallFields.APP_SHOW, queryCarModels.getApp_show());
		return map;
	}

	/**
	 * @param queryCarModels
	 * @param map
	 * @param carModels
	 * @return
	 */
	private int addPageParam(QueryCarModels queryCarModels, Map<String, Object> map, CarModels carModels) {
		Pagination page = new Pagination();
		page.setPage_no(queryCarModels.getPage());
		page.setPage_size(queryCarModels.getLimit());
		int total_item_num = carModelsService.countCarModelBylike(map);
		page.setTotal_item_num(total_item_num);
		page.calc();
		map.put("page", page);
		return total_item_num;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryCarModellike(QueryCarModelLike queryCarModelLike) {
		Map<String, Object> result = VOUtil.genSuccessResult();
		Map<String, Object> param = JSONUtil.trans(queryCarModelLike, Map.class);
		result.put(BasicFields.RESULT_LIST, carModelsService.queryModelsByLike(param));
		return result;
	}

	@Override
	public Map<String, Object> countCarModellike(QueryCarModels queryCarModels) {
		Map<String, Object> result = VOUtil.genSuccessResult();
		CarModels carModels = new CarModels();
		carModels.setBrand_id(queryCarModels.getBrand_id());
		int total_num = carModelsService.count(carModels);
		result.put(BasicFields.TOTAL_NUM, total_num);
		return result;
	}

	@Override
	public Map<String, Object> exportCarModelList(QueryCarModels queryCarModels) {
		Map<String, Object> map = new HashMap<String, Object>();

		CarModels carModels = new CarModels();
		map = addQueryParams(queryCarModels, map, carModels);
		List<Map<String, Object>> result_list = carModelsService.queryCarModelList(map);
		Map<String, Object> reslut = VOUtil.genSuccessResult();
		reslut.put(BasicFields.RESULT_LIST, result_list);
		return reslut;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryCarModelsListApp(QueryCarModelsApp queryCarModelsApp) {
		Map<String, Object> result = VOUtil.genSuccessResult();
		List<String> list = new ArrayList<String>();
		String key = CacheKeys.getCarModelsByBrandAndMetadata(queryCarModelsApp.getBrand_id(),
				queryCarModelsApp.getMetadata_id());
		Set<String> allCarModelList = cacheHelper.zrange(key, 0, -1);
		if (allCarModelList.size() > 0) {
			for (String string : allCarModelList) {
				String car_model_name = cacheHelper.hmget(CacheKeys.CAR_MODELS_ID_PREFIX + string,
						MallFields.CAR_MODELS_NAME);
				if (StringUtils.isBlank(car_model_name)) {
					CarModels example = carModelsService.get(string);
					Map<String, String> map = JSONUtil.trans(example, Map.class);
					map.remove("update_version");
					if (example != null) {
						list.add(example.getCar_models_name());
						cacheHelper.hmset(CacheKeys.CAR_MODELS_ID_PREFIX + string, map);
					}
				} else {
					list.add(car_model_name);
				}
			}
		} else {
			CarModels example = new CarModels();
			example.setBrand_id(queryCarModelsApp.getBrand_id());
			example.setMetadata_id(queryCarModelsApp.getMetadata_id());
			example.setApp_show("1");
			List<CarModels> clist = carModelsService.query(example);
			for (CarModels carModels : clist) {
				list.add(carModels.getCar_models_name());
				String metadata_name = cacheHelper.hmget(CacheKeys.METADATA_ID_PREFIX + carModels.getMetadata_id(),
						MallFields.METADATA_NAME);
				// 根据品牌寸类型名称（去重）
				cacheHelper.zdd(CacheKeys.BRAND_CAR_METADATA_NAMES_PREFIX + carModels.getBrand_id(),
						Double.valueOf(carModels.getMetadata_id()), metadata_name);
				// 根据品牌类型寸车型名称（去重的）
				saveCarModelsByBrandAndMetadataInCache(carModels);
			}
		}
		Collections.sort(list, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {

				return PinyinUtil.getPingYin(o1).compareTo(PinyinUtil.getPingYin(o2));
			}
		});
		result.put(BasicFields.RESULT_LIST, list);
		return result;
	}

	@Override
	public Map<String, Object> queryCarTypeByBrandId(QueryCarModelLike queryCarModelLike) {
		Map<String, Object> result = VOUtil.genSuccessResult();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		Set<String> set = cacheHelper.zrange(
				CacheKeys.BRAND_CAR_METADATA_NAMES_PREFIX + queryCarModelLike.getBrand_id(), 0, -1);
		if (set.size() > 0) {
			for (String string : set) {
				Double midString = cacheHelper.zscore(
						CacheKeys.BRAND_CAR_METADATA_NAMES_PREFIX + queryCarModelLike.getBrand_id(), string);
				BigDecimal bd = new BigDecimal(midString);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(MallFields.METADATA_ID, String.valueOf(bd));
				map.put(MallFields.CAR_TYPE, string);
				resultList.add(map);
			}
		} else {
			CarModels carModels = new CarModels();
			carModels.setBrand_id(queryCarModelLike.getBrand_id());
			String metedataIds = carModelsService.getMetedataIds(queryCarModelLike.getBrand_id());
			if (StringUtils.isNotBlank(metedataIds)) {
				String[] value = metedataIds.split(",");
				for (String string : value) {
					Metadata metadata = new Metadata();
					metadata.setMetadata_id(string);
					String metadataName = metadataService.getMetadataById(metadata).get("metadata_name").toString();
					Map<String, Object> map1 = new HashMap<String, Object>();
					map1.put(MallFields.METADATA_ID, string);
					map1.put(MallFields.CAR_TYPE, metadataName);
					resultList.add(map1);
					// 根据品牌寸类型名称（去重）
					cacheHelper.zdd(CacheKeys.BRAND_CAR_METADATA_NAMES_PREFIX + carModels.getBrand_id(),
							Double.valueOf(string), metadataName);
				}
			}
		}
		result.put(BasicFields.RESULT_LIST, resultList);
		return result;
	}

	@Override
	public Map<String, Object> updateCarTypeBatch(UpdataCarStatusBatch updataCarStatusBatch) {

		CarModels carModels = new CarModels();
		String[] idArr = updataCarStatusBatch.getCar_models_ids().split(",");
		List<String> ids = new ArrayList<String>();
		for (String i : idArr) {
			ids.add(i);
		}
		Date date = new Date();
		String dateString = DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK);
		String timeString = DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK);
		carModels.setUpdate_date(dateString);
		carModels.setUpdate_time(timeString);
		String operateInfo = cacheHelper.get("hjh.hjy.access_token." + updataCarStatusBatch.getAccess_token());
		@SuppressWarnings("unchecked")
		Map<String, String> map = JSONUtil.trans(operateInfo, Map.class);
		carModels.setUpdate_user_name(map.get("operatorName"));
		carModels.setUpdate_user(map.get(BasicFields.CLIENTID));
		carModels.setIds(ids);
		carModels.setStatus(updataCarStatusBatch.getStatus());
		carModelsService.updateCarStatusBatch(carModels);
		if (updataCarStatusBatch.getStatus().equals("1")) {
			for (String string : ids) {
				CarModels carModels2 = carModelsService.get(string);
				updateCarModelNamesByBrandAndMetadataInCache(carModels2);
			}
		}
		return VOUtil.genSuccessResult();
	}

	@Override
	public Map<String, Object> queryCarModelListWeb(QueryCarModelsApp queryCarModelsApp) {
		Map<String, Object> result = VOUtil.genSuccessResult();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		CarModels example = new CarModels();
		example.setBrand_id(queryCarModelsApp.getBrand_id());
		example.setMetadata_id(queryCarModelsApp.getMetadata_id());
		example.setStatus(BasicFields.ENABLE);
		List<CarModels> list2 = carModelsService.query(example);
		for (CarModels carModels : list2) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("car_model_name", carModels.getCar_models_name());
			map.put("car_model_is", carModels.getCar_models_id());
			// saveCarModelsByBrandAndMetadataInCache(carModels);
			list.add(map);
		}
		result.put(BasicFields.RESULT_LIST, list);
		return result;
	}

	@Override
	public List<String> getBrandIdsByIds(List<String> ids) {
		CarModels carModels = new CarModels();
		carModels.setIds(ids);
		return carModelsService.getBrandIdsByIds(carModels);
	}

	@Override
	public List<String> getIdsByMetadataOrBrand(QueryIdsByMetadataOrBrand queryIdsByMetadataOrBrand) {
		CarModels carModels = JSONUtil.trans(queryIdsByMetadataOrBrand, CarModels.class);

		return carModelsService.getIdsByMetadataOrBrand(carModels);
	}

}

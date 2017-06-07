package com.hjh.mall.category.bizapi.bizserver.car;

import java.util.List;
import java.util.Map;

import com.hjh.mall.category.bizapi.bizserver.car.vo.AddCarModel;
import com.hjh.mall.category.bizapi.bizserver.car.vo.QueryCarModelLike;
import com.hjh.mall.category.bizapi.bizserver.car.vo.QueryCarModels;
import com.hjh.mall.category.bizapi.bizserver.car.vo.QueryCarModelsApp;
import com.hjh.mall.category.bizapi.bizserver.car.vo.QueryIdsByMetadataOrBrand;
import com.hjh.mall.category.bizapi.bizserver.car.vo.UpdataCarStatusBatch;
import com.hjh.mall.category.bizapi.bizserver.car.vo.UpdateCarModel;
import com.hjh.mall.common.core.annotation.BizService;

/**
 * * @author csj:
 * 
 * @date 创建时间：2017年3月14日 下午2:13:20
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public interface BizCarModelService {
	@BizService(functionId = "901001", name = "创建车型", desc = "创建车型")
	public Map<String, Object> addCarModel(AddCarModel addCarModel);

	@BizService(functionId = "901002", name = "编辑车型", desc = "编辑车型")
	public Map<String, Object> updateCarModel(UpdateCarModel updateCarModel);

	@BizService(functionId = "901003", name = "模糊查询车型列表", desc = "模糊查询车型列表")
	public Map<String, Object> queryCarModelList(QueryCarModels queryCarModels);

	@BizService(functionId = "901004", name = "输入匹配", desc = "输入匹配")
	public Map<String, Object> queryCarModellike(QueryCarModelLike queryCarModelLike);

	@BizService(functionId = "901005", name = "根据条件查询数量", desc = "根据条件查询数量")
	public Map<String, Object> countCarModellike(QueryCarModels queryCarModels);

	@BizService(functionId = "901006", name = "根据条件查询数量", desc = "根据条件查询数量")
	public Map<String, Object> exportCarModelList(QueryCarModels queryCarModels);

	@BizService(functionId = "901007", name = "APP机型配车型列表", desc = "APP机型配车型列表")
	public Map<String, Object> queryCarModelsListApp(QueryCarModelsApp queryCarModelsApp);

	@BizService(functionId = "901009", name = "根据品牌获得类型", desc = "根据品牌获得类型")
	public Map<String, Object> queryCarTypeByBrandId(QueryCarModelLike queryCarModelLike);

	@BizService(functionId = "901010", name = "批量更新车型", desc = "批量更新车型")
	public Map<String, Object> updateCarTypeBatch(UpdataCarStatusBatch updataCarStatusBatch);

	@BizService(functionId = "901011", name = "web适用机型配车型列表", desc = "web适用机型配车型列表")
	public Map<String, Object> queryCarModelListWeb(QueryCarModelsApp queryCarModelsApp);

	@BizService(functionId = "901012", name = "根据元数据id或者车型品牌id获取机型Ids", desc = "根据元数据id或者车型品牌id获取机型Ids")
	public List<String> getIdsByMetadataOrBrand(QueryIdsByMetadataOrBrand queryIdsByMetadataOrBrand);

	@BizService(functionId = "901013", name = "根据机型Ids获取机型品牌ids", desc = "根据机型Ids获取机型品牌ids")
	public List<String> getBrandIdsByIds(List<String> ids);

}

/** * @author  csj
 * @date 创建时间：2017年3月15日 下午7:17:53 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  */
package com.hjh.mall.category.bizapi.bizserver.goodscar;

import java.util.List;
import java.util.Map;

import com.hjh.mall.category.bizapi.bizserver.goodscar.vo.AddGoodsCarModelsBatch;
import com.hjh.mall.common.core.annotation.BizService;

public interface BizGoodsCarModelService {
	@BizService(functionId = "901101", name = "批量创建商品车型关系", desc = "批量创建商品车型关系")
	public Map<String, Object> addCarModelsBatch(AddGoodsCarModelsBatch addGoodsCarModelsBatch);

	@BizService(functionId = "901003", name = "APP根据商品ID查询车型列表", desc = "APP根据商品ID查询车型列表")
	public Map<String, Object> queryGoodsCarModelList(AddGoodsCarModelsBatch addGoodsCarModelsBatch);

	@BizService(functionId = "901004", name = "WEB查询车型列表包含已选", desc = "WEB查询车型列表包含已选")
	public Map<String, Object> queryGoodsCarModelListIsChoosed(AddGoodsCarModelsBatch addGoodsCarModelsBatch);

	@BizService(functionId = "901005", name = "编辑机型", desc = "编辑机型")
	public Map<String, Object> modifyGoodsCarModel(AddGoodsCarModelsBatch addGoodsCarModelsBatch);

	@BizService(functionId = "901005", name = "通过goodsid查找品牌类型车型名称", desc = "通过goodsid查找品牌类型车型名称")
	public Map<String, Object> queryNamesByGoodsId(String goods_id);

	@BizService(functionId = "901006", name = "通过goodsid删除商品下所有机型", desc = "通过goodsid删除商品下所有机型")
	public Map<String, Object> deleteBatchNamesByGoodsId(AddGoodsCarModelsBatch addGoodsCarModelsBatch);

	@BizService(functionId = "901007", name = "根据车型获得商品列表", desc = "根据车型获得商品列表")
	public Map<String, Object> queryGoodsNamesByCarId(String car_models_id);

	@BizService(functionId = "901008", name = "通过carid查询所有商品ids", desc = "通过carid查询所有商品ids")
	public Map<String, Object> queryGoodsIdsByCarId(String car_models_id);

	@BizService(functionId = "901009", name = "通过carids查询所有商品ids", desc = "通过carids查询所有商品ids")
	public List<String> queryGoodsIdsByCarIds(List<String> ids);

}

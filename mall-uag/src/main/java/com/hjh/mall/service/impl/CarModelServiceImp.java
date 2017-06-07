/** * @author  csj
 * @date 创建时间：2017年4月7日 下午3:15:51 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  */
package com.hjh.mall.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hjh.mall.category.bizapi.bizserver.goodscar.BizGoodsCarModelService;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.goods.bizapi.bizserver.BizGoodsService;
import com.hjh.mall.goods.bizapi.bizserver.vo.updateTimer.GoodsTimerVo;

@Service
public class CarModelServiceImp {

	@Reference(version = "1.0.0")
	private BizGoodsService bizGoodsService;
	@Reference(version = "1.0.0")
	private BizGoodsCarModelService bizGoodsCarModelService;

	public void UpdateSolrVo(GoodsTimerVo goodsTimerVo) {

		// 找到需要更新的solr实体
		Map<String, Object> allIdMap = bizGoodsService.querySolrEntityByids(goodsTimerVo.getIds());
		List<Map<String, Object>> list = (List<Map<String, Object>>) allIdMap.get(BasicFields.RESULT_LIST);
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				if (map.get(com.hjh.mall.field.constant.MallFields.ADAPT_ALL_MODELS).toString()
						.equals(BasicFields.DISABLE)) {
					Map<String, Object> carTypeName = bizGoodsCarModelService.queryNamesByGoodsId(map.get("id")
							.toString());
					Map<String, Object> carModelMap = (Map<String, Object>) carTypeName.get("typeNamesInSolr");
					if (carModelMap != null) {
						map.put("car_brand_name", carModelMap.get("brand_names"));
						map.put("car_models_name", carModelMap.get("car_models_names"));
						map.put("car_type", carModelMap.get("car_types"));
					}
				}
			}
		}

		// 更新solr
		Map<String, Object> result = bizGoodsService.bathUpdateSolr(list);

	}
}

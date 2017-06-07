package com.hjh.mall.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hjh.mall.cache.cache.helper.CacheHelper;
import com.hjh.mall.category.bizapi.bizserver.car.BizCarModelService;
import com.hjh.mall.category.bizapi.bizserver.car.vo.QueryIdsByMetadataOrBrand;
import com.hjh.mall.category.bizapi.bizserver.goodscar.BizGoodsCarModelService;
import com.hjh.mall.category.bizapi.bizserver.metadata.BizMetadataService;
import com.hjh.mall.category.bizapi.bizserver.metadata.vo.AddMetadata;
import com.hjh.mall.category.bizapi.bizserver.metadata.vo.GetMetadataByBrand;
import com.hjh.mall.category.bizapi.bizserver.metadata.vo.GetMetadataById;
import com.hjh.mall.category.bizapi.bizserver.metadata.vo.GetMetadataByName;
import com.hjh.mall.category.bizapi.bizserver.metadata.vo.GetMetadataByType;
import com.hjh.mall.category.bizapi.bizserver.metadata.vo.GetMetadataList;
import com.hjh.mall.category.bizapi.bizserver.metadata.vo.UpdateMetadata;
import com.hjh.mall.category.bizapi.bizserver.metadata.vo.UpdateMetadataStatus;
import com.hjh.mall.common.core.annotation.NoLogin;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.context.HJYBizDataContext;
import com.hjh.mall.field.RestFulAPI;
import com.hjh.mall.field.constant.CacheKeys;
import com.hjh.mall.field.constant.MallFields;
import com.hjh.mall.field.type.MetadataType;
import com.hjh.mall.goods.bizapi.bizserver.BizGoodsService;
import com.hjh.mall.goods.bizapi.bizserver.vo.updateTimer.GoodsTimerVo;
import com.hjh.mall.service.impl.CarModelServiceImp;

/**
 * @Project: mall-web
 * @Description TODO
 * @author 曾繁林
 * @date 2017年3月14日
 * @version V1.0
 */
@Controller
public class MetadataController {
	@Reference(version = "1.0.0")
	private BizMetadataService bizMetadataService;

	@Reference(version = "1.0.0")
	private BizGoodsService bizGoodsService;

	@Reference(version = "1.0.0")
	private BizGoodsCarModelService bizGoodsCarModelService;

	@Reference(version = "1.0.0")
	private BizCarModelService bizCarModelService;

	@Resource
	private CarModelServiceImp carModelServiceImp;

	@Resource
	private CacheHelper cacheHelper;

	@RequestMapping(value="/addMetadata",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMetadata(@RequestBody AddMetadata addMetadata) {
		String operator_id = HJYBizDataContext.getSessionIdentity().getClientId();
		String operator_name = HJYBizDataContext.getSessionIdentity().getOperatorName();
		addMetadata.setCreate_user(operator_name);
		addMetadata.setCreate_user_id(operator_id);
		addMetadata.setUpdate_user(operator_name);
		addMetadata.setUpdate_user_id(operator_id);
		return bizMetadataService.addMetadata(addMetadata);
	}

	@RequestMapping(value="/updateMetadata",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMetadata(@RequestBody UpdateMetadata updateMetadata) {
		String operator_id = HJYBizDataContext.getSessionIdentity().getClientId();
		String operator_name = HJYBizDataContext.getSessionIdentity().getOperatorName();
		updateMetadata.setUpdate_user(operator_name);
		updateMetadata.setUpdate_user_id(operator_id);
		boolean flag = bizMetadataService.checkMetadataName(updateMetadata);// 判断是否更改元数据名称

		Map<String, Object> result = bizMetadataService.updateMetadata(updateMetadata);
		if (result.get(BasicFields.ERROR_NO).equals(BasicFields.SUCCESS) && flag) {

			if (MetadataType.UNIT.getVal() == updateMetadata.getType()) {// 修改的计量单位的话，只修改商品数据库
				// 更新商品表
				GoodsTimerVo goodsTimerVo = new GoodsTimerVo();
				goodsTimerVo.setUnit_id(updateMetadata.getMetadata_id());
				goodsTimerVo.setUnit_name(updateMetadata.getMetadata_name());
				bizGoodsService.updateGoodsTimer(goodsTimerVo);
			}

			if (MetadataType.MACHINE_TYPE.getVal() == updateMetadata.getType()) {// 修改的机械类型的话，只修改搜索引擎
				QueryIdsByMetadataOrBrand queryIdsByMetadataOrBrand = new QueryIdsByMetadataOrBrand();
				queryIdsByMetadataOrBrand.setMetadata_id(updateMetadata.getMetadata_id());
				List<String> carIds = bizCarModelService.getIdsByMetadataOrBrand(queryIdsByMetadataOrBrand);
				if (carIds != null && carIds.size() > 0) {

					// 更新机型相关缓存
					for (String id : carIds) {
						Map<String, String> map = cacheHelper.hgetAll(CacheKeys.CAR_MODELS_ID_PREFIX + id);
						map.put(MallFields.CAR_TYPE, updateMetadata.getMetadata_name());
						cacheHelper.hmset(CacheKeys.CAR_MODELS_ID_PREFIX + id, map);
					}
					List<String> brandIds = bizCarModelService.getBrandIdsByIds(carIds);
					if (brandIds != null && brandIds.size() > 0) {
						for (String id : brandIds) {
							cacheHelper.zremrangeByScore(CacheKeys.BRAND_CAR_METADATA_NAMES_PREFIX + id,
									Double.valueOf(updateMetadata.getMetadata_id()),
									Double.valueOf(updateMetadata.getMetadata_id()));
							cacheHelper.zdd(CacheKeys.BRAND_CAR_METADATA_NAMES_PREFIX + id,
									Double.valueOf(updateMetadata.getMetadata_id()), updateMetadata.getMetadata_name());
						}
					}
					List<String> goodIds = bizGoodsCarModelService.queryGoodsIdsByCarIds(carIds);
					if (goodIds != null && goodIds.size() > 0) {
						MyThread mythread = new MyThread();
						mythread.setIds(goodIds);
						mythread.run();
					}

				}
			}

		}
		return result;
	}

	public class MyThread implements Runnable {

		private List<String> ids;

		public List<String> getIds() {
			return ids;
		}

		public void setIds(List<String> ids) {
			this.ids = ids;
		}

		@Override
		public void run() {
			GoodsTimerVo goodsTimerVo = new GoodsTimerVo();
			goodsTimerVo.setIds(ids);
			carModelServiceImp.UpdateSolrVo(goodsTimerVo);

		}

	}

	@RequestMapping(value="/updateMetadataStatus",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMetadataStatus(@RequestBody UpdateMetadataStatus updateMetadataStatus) {
		String operator_id = HJYBizDataContext.getSessionIdentity().getClientId();
		String operator_name = HJYBizDataContext.getSessionIdentity().getOperatorName();
		updateMetadataStatus.setUpdate_user(operator_name);
		updateMetadataStatus.setUpdate_user_id(operator_id);
		return bizMetadataService.updateMetadataStatus(updateMetadataStatus);
	}

	@NoLogin
	@RequestMapping(value="/queryMetadata",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMetadata(@RequestBody GetMetadataList getMetadataList) {
		return bizMetadataService.getMetadataList(getMetadataList);
	}

	@NoLogin
	@RequestMapping(value="/queryMetadataByType",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMetadataByType(@RequestBody GetMetadataByType getMetadataByType) {
		return bizMetadataService.getMetadataListByType(getMetadataByType);
	}
	
	@NoLogin
	@RequestMapping(value="json/queryMetadataByType",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMetadataByTypeApp(@RequestBody GetMetadataByType getMetadataByType) {
		return bizMetadataService.getMetadataListByType(getMetadataByType);
	}

	@NoLogin
	@RequestMapping(value="/queryMetadataByName",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMetadataByName(@RequestBody GetMetadataByName getMetadataByName) {
		return bizMetadataService.getMetadataByName(getMetadataByName);
	}

	@NoLogin
	@RequestMapping(value="/queryMetadataById",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMetadataById(@RequestBody GetMetadataById getMetadataById) {
		return bizMetadataService.getMetadataById(getMetadataById);
	}

	@NoLogin
	@RequestMapping(value=RestFulAPI.MetadataCode.QUERY_METADATA_BY_BRAND,method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMetadataByBrand(@RequestBody GetMetadataByBrand getMetadataByBrand) {
		return bizMetadataService.getMetadataBybrand(getMetadataByBrand);
	}

	@RequestMapping(value="/reloadAllMetadata",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reloadAllMetadata(@RequestBody HJYVO hjyvo) {
		return bizMetadataService.reloadAllMetadata();
	}

}

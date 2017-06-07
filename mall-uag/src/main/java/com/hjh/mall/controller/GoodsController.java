package com.hjh.mall.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hjh.mall.category.bizapi.bizserver.goodsbrand.BizGoodsBrandService;
import com.hjh.mall.category.bizapi.bizserver.goodsbrand.vo.GoodsBrandQueryById;
import com.hjh.mall.category.bizapi.bizserver.goodscar.BizGoodsCarModelService;
import com.hjh.mall.category.bizapi.bizserver.goodscar.vo.AddGoodsCarModelsBatch;
import com.hjh.mall.common.core.annotation.NoLogin;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.filed.UploadType;
import com.hjh.mall.common.core.util.JSONUtil;
import com.hjh.mall.common.core.util.StringUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.context.HJYBizDataContext;
import com.hjh.mall.field.constant.MallFields;
import com.hjh.mall.field.restfulapi.RestFulAPI.GoodsCode;
import com.hjh.mall.goods.bizapi.bizserver.BizGoodsService;
import com.hjh.mall.goods.bizapi.bizserver.vo.AddGoodsVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.ChmodBathVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.GetJsonGoodsVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.ModifyGoodsVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.QueryGoodsVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.QueryModelByGoodsVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.QuerySolrVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.UndercarriageGoodsVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.UpdateSolrVo;
import com.hjh.mall.service.GoodsService;
import com.hjh.mall.service.UploadService;

import io.swagger.annotations.ApiOperation;

/**
 * @Project: hjh-mall
 * @Description
 * @author 杨益桦
 * @date 2017年2月18日
 * @version V1.0
 */
@Controller
public class GoodsController {

	@Reference(version = "1.0.0")
	private BizGoodsService bizGoodsService;

	@Reference(version = "1.0.0")
	private BizGoodsCarModelService bizGoodsCarModelService;

	@Resource
	private GoodsService goodsService;

	@Resource
	private UploadService uploadService;

	@Reference(version = "1.0.0")
	private BizGoodsBrandService bizGoodsBrandService;

	@ApiOperation(value = "更新上架商品的搜索引擎", notes = "")
	@RequestMapping(value = "/updateGroundGoods", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateGroundGoods(@RequestBody HJYVO hjyvo) {
		goodsService.updateGroundSolr();
		return VOUtil.genSuccessResult();
	}

	// @SuppressWarnings("unchecked")
	// @NoLogin
	// @ResponseBody
	// public Map<String, Object> updateGoodsTimer(@RequestBody
	// UpdateCarModelsVo updateModelsVo) {
	// GoodsTimerVo goodsTimerVo = JSONUtil.trans(updateModelsVo,
	// GoodsTimerVo.class);
	// bizGoodsService.updateGoodsTimer(goodsTimerVo);
	//
	// String startDate = DateTimeUtil.toString(DateTimeUtil.getStartOfDay(new
	// Date()),
	// DateTimeUtil.FORMAT_YYYYMMDDHHMMSSSSS_NO_BREAK);
	// String endDate = DateTimeUtil.toString(DateTimeUtil.getEndOfDay(new
	// Date()),
	// DateTimeUtil.FORMAT_YYYYMMDDHHMMSSSSS_NO_BREAK);
	//
	// Map<String, Object> allIdMap = bizGoodsService.queryAllId(startDate,
	// endDate);
	// List<Map<String, Object>> list = (List<Map<String, Object>>)
	// allIdMap.get(BasicFields.RESULT_LIST);
	// if (list != null && list.size() > 0) {
	// for (Map<String, Object> map : list) {
	// if
	// (map.get(MallFields.ADAPT_ALL_MODELS).toString().equals(BasicFields.DISABLE))
	// {
	// Map<String, Object> carTypeName =
	// bizGoodsCarModelService.queryNamesByGoodsId(map.get("id")
	// .toString());
	// Map<String, Object> carModelMap = (Map<String, Object>)
	// carTypeName.get("typeNamesInSolr");
	// if (carModelMap != null) {
	// map.put("car_brand_name", carModelMap.get("brand_names"));
	// map.put("car_models_name", carModelMap.get("car_models_names"));
	// map.put("car_type", carModelMap.get("car_types"));
	// }
	// }
	// }
	// }
	// Map<String, Object> result = bizGoodsService.bathUpdateSolr(list);
	// return result;
	// }

	/**
	 * @date 2017年2月20日
	 * @Description: 查询商品
	 * @author 杨益桦
	 * @param queryGoodsVo
	 * @return Map<String,Object>
	 */
	@ApiOperation(value = "查询商品列表", notes = "查询商品列表")
	@RequestMapping(value = "/queryGoods", method = RequestMethod.POST)
	@NoLogin
	@ResponseBody
	public Map<String, Object> queryGoods(@RequestBody QueryGoodsVo queryGoodsVo) {
		Map<String, Object> result = bizGoodsService.queryGoods(queryGoodsVo);
		List<Map<String, Object>> resultList = (List<Map<String, Object>>) result.get(BasicFields.RESULT_LIST);
		for (Map<String, Object> map : resultList) {
			AddGoodsCarModelsBatch addGoodsCarModelsBatch = new AddGoodsCarModelsBatch();
			addGoodsCarModelsBatch.setGoods_id(map.get(MallFields.GOODS_ID).toString());
			addGoodsCarModelsBatch.setPage(1);
			addGoodsCarModelsBatch.setLimit(1000000);
			Map<String, Object> modelResult = bizGoodsCarModelService.queryGoodsCarModelList(addGoodsCarModelsBatch);
			map.put(MallFields.CAR_MODEL_LIST, modelResult.get(BasicFields.RESULT_LIST));
		}
		return result;
	}

	// @SuppressWarnings("unchecked")
	// @ApiOperation(value = "", notes = "")
	// @RequestMapping(value="/bathUpdate",method = RequestMethod.POST)
	// @NoLogin
	// @ResponseBody
	// public Map<String, Object> bathUpdate(@RequestBody HJYVO hjyvo) {
	// Map<String, Object> result = null;
	// String startDate = DateTimeUtil.toString(DateTimeUtil.getStartOfDay(new
	// Date()),
	// DateTimeUtil.FORMAT_YYYYMMDDHHMMSSSSS_NO_BREAK);
	// String endDate = DateTimeUtil.toString(DateTimeUtil.getEndOfDay(new
	// Date()),
	// DateTimeUtil.FORMAT_YYYYMMDDHHMMSSSSS_NO_BREAK);
	//
	// Map<String, Object> allIdMap = bizGoodsService.queryAllId(startDate,
	// endDate);
	// List<Map<String, Object>> list = (List<Map<String, Object>>)
	// allIdMap.get(BasicFields.RESULT_LIST);
	// if (list != null && list.size() > 0) {
	// for (Map<String, Object> map : list) {
	// if
	// (map.get(MallFields.ADAPT_ALL_MODELS).toString().equals(BasicFields.DISABLE))
	// {
	// Map<String, Object> carTypeName =
	// bizGoodsCarModelService.queryNamesByGoodsId(map.get("id")
	// .toString());
	// Map<String, Object> carModelMap = (Map<String, Object>)
	// carTypeName.get("typeNamesInSolr");
	// if (carModelMap != null) {
	// map.put("car_brand_name", carModelMap.get("brand_names"));
	// map.put("car_models_name", carModelMap.get("car_models_names"));
	// map.put("car_type", carModelMap.get("car_types"));
	// }
	// }
	// }
	// }
	// result = bizGoodsService.bathUpdateSolr(list);
	// return result;
	// }

	/**
	 * @date 2017年2月20日
	 * @Description: 添加商品
	 * @author 杨益桦
	 * @param addGoodsVo
	 * @return Map<String,Object>
	 */
	@ApiOperation(value = "添加已上架商品", notes = "")
	@RequestMapping(value = "/addGoods", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addGoods(@RequestBody AddGoodsVo addGoodsVo) {
		addGoodsVo.setHjy_operator_id(HJYBizDataContext.getSessionIdentity().getClientId());
		addGoodsVo.setHjy_operator_name(HJYBizDataContext.getSessionIdentity().getOperatorName());
		addGoodsVo.setGoods_status(BasicFields.ENABLE);
		addShowUrl(addGoodsVo, UploadType.GOODS_AD);
		addShowUrl(addGoodsVo, UploadType.GOODS_BANNER);
		addShowUrl(addGoodsVo, UploadType.GOODS_DETAIL);
		addShowUrl(addGoodsVo, UploadType.GOODS_SHOW);
		Map<String, Object> result = bizGoodsService.addGoods(addGoodsVo);
		if (addGoodsVo.getCarModelList() != null) {// 适配全部机型
			AddGoodsCarModelsBatch addGoodsCarModelsBatch = new AddGoodsCarModelsBatch();
			// HJYBizDataContext.getSessionIdentity().getOperatorName();
			addGoodsCarModelsBatch.setAccess_token(addGoodsVo.getHjy_operator_name());
			addGoodsCarModelsBatch.setGoods_id(result.get(MallFields.GOODS_ID).toString());
			addGoodsCarModelsBatch.setCarModelList(addGoodsVo.getCarModelList());
			bizGoodsCarModelService.addCarModelsBatch(addGoodsCarModelsBatch);
		}

		Map<String, Object> carTypeName = bizGoodsCarModelService
				.queryNamesByGoodsId(result.get(MallFields.GOODS_ID).toString());
		UpdateSolrVo updateSolrVo = JSONUtil.trans(carTypeName.get(MallFields.TYPE_NAMES_IN_SOLR), UpdateSolrVo.class);
		if (updateSolrVo == null) {
			updateSolrVo = new UpdateSolrVo();
			updateSolrVo.setGoods_id(result.get(MallFields.GOODS_ID).toString());
		}
		updateSolrVo.setAdapt_all_models(addGoodsVo.getAdapt_all_models());
		bizGoodsService.updateSolr(updateSolrVo);

		return result;
	}

	/**
	 * @date 2017年3月21日
	 * @Description: 添加未上架商品
	 * @author 杨益桦
	 * @param addGoodsVo
	 * @return Map<String,Object>
	 */
	@ApiOperation(value = "添加待上架商品", notes = "")
	@RequestMapping(value = "/adUnderdGoods", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> adUnderdGoods(@RequestBody AddGoodsVo addGoodsVo) {
		// 图片处理
		addShowUrl(addGoodsVo, UploadType.GOODS_AD);
		addShowUrl(addGoodsVo, UploadType.GOODS_BANNER);
		addShowUrl(addGoodsVo, UploadType.GOODS_DETAIL);
		addShowUrl(addGoodsVo, UploadType.GOODS_SHOW);
		addGoodsVo.setHjy_operator_id(HJYBizDataContext.getSessionIdentity().getClientId());
		addGoodsVo.setHjy_operator_name(HJYBizDataContext.getSessionIdentity().getOperatorName());
		addGoodsVo.setGoods_status(BasicFields.DISABLE);
		Map<String, Object> result = bizGoodsService.addGoods(addGoodsVo);
		AddGoodsCarModelsBatch addGoodsCarModelsBatch = new AddGoodsCarModelsBatch();
		addGoodsCarModelsBatch.setGoods_id(result.get(MallFields.GOODS_ID).toString());
		addGoodsCarModelsBatch.setCarModelList(addGoodsVo.getCarModelList());
		bizGoodsCarModelService.addCarModelsBatch(addGoodsCarModelsBatch);

		return result;
	}

	/**
	 * @date 2017年2月20日
	 * @Description: 修改商品
	 * @author 杨益桦
	 * @param modifyGoodsVo
	 * @return Map<String,Object>
	 */
	@ApiOperation(value = "修改商品", notes = "")
	@RequestMapping(value = "/modifyGoods", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modifyGoods(@RequestBody ModifyGoodsVo modifyGoodsVo) {
		// 图片处理
		editShowUrl(modifyGoodsVo, UploadType.GOODS_AD);
		editShowUrl(modifyGoodsVo, UploadType.GOODS_BANNER);
		editShowUrl(modifyGoodsVo, UploadType.GOODS_DETAIL);
		editShowUrl(modifyGoodsVo, UploadType.GOODS_SHOW);

		modifyGoodsVo.setHjy_operator_id(HJYBizDataContext.getSessionIdentity().getClientId());
		modifyGoodsVo.setHjy_operator_name(HJYBizDataContext.getSessionIdentity().getOperatorName());
		Map<String, Object> result = bizGoodsService.modifyGoods(modifyGoodsVo);
		if (modifyGoodsVo.getCarModelList() != null) {// 适配全部机型
			AddGoodsCarModelsBatch addGoodsCarModelsBatch = new AddGoodsCarModelsBatch();
			addGoodsCarModelsBatch.setAccess_token(modifyGoodsVo.getHjy_operator_name());
			addGoodsCarModelsBatch.setGoods_id(modifyGoodsVo.getGoods_id());
			addGoodsCarModelsBatch.setCarModelList(modifyGoodsVo.getCarModelList());
			result = bizGoodsCarModelService.modifyGoodsCarModel(addGoodsCarModelsBatch);
		} else {
			AddGoodsCarModelsBatch addGoodsCarModelsBatch = new AddGoodsCarModelsBatch();
			addGoodsCarModelsBatch.setGoods_id(modifyGoodsVo.getGoods_id());
			bizGoodsCarModelService.deleteBatchNamesByGoodsId(addGoodsCarModelsBatch);
		}

		return result;
	}

	/**
	 * @date 2017年2月20日
	 * @Description: 下架商品
	 * @author 杨益桦
	 * @param modifyGoodsVo
	 * @return Map<String,Object>
	 */
	@ApiOperation(value = "下架商品", notes = "")
	@RequestMapping(value = "/undercarriageGoods", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> undercarriageGoods(@RequestBody UndercarriageGoodsVo undercarriageGoodsVo) {
		undercarriageGoodsVo.setHjy_operator_id(HJYBizDataContext.getSessionIdentity().getClientId());
		undercarriageGoodsVo.setHjy_operator_name(HJYBizDataContext.getSessionIdentity().getOperatorName());
		Map<String, Object> result = bizGoodsService.undercarriageGoods(undercarriageGoodsVo);
		return result;
	}

	/**
	 * @date 2017年3月16日
	 * @Description: 批量下架商品
	 * @author 杨益桦
	 * @param chmodBathVo
	 * @return Map<String,Object>
	 */
	@ApiOperation(value = "批量下架商品", notes = "")
	@RequestMapping(value = "/bathUndercarriageGoods", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> bathUndercarriageGoods(@RequestBody ChmodBathVo chmodBathVo) {
		chmodBathVo.setHjy_operator_id(HJYBizDataContext.getSessionIdentity().getClientId());
		chmodBathVo.setHjy_operator_name(HJYBizDataContext.getSessionIdentity().getOperatorName());
		chmodBathVo.setGoods_status(BasicFields.DISABLE);
		Map<String, Object> result = bizGoodsService.chmodBath(chmodBathVo);
		// 处理搜索引擎
		List<String> ids = chmodBathVo.getIds();
		for (String id : ids) {
			UpdateSolrVo updateSolrVo = new UpdateSolrVo();
			updateSolrVo.setGoods_id(id);
			try {
				bizGoodsService.delSolr(updateSolrVo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * @date 2017年3月16日
	 * @Description: 批量上架商品
	 * @author 杨益桦
	 * @param chmodBathVo
	 * @return Map<String,Object>
	 */
	@ApiOperation(value = "批量上架商品", notes = "")
	@RequestMapping(value = "/bathGroundingGoods", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> bathGroundingGoods(@RequestBody ChmodBathVo chmodBathVo) {
		chmodBathVo.setHjy_operator_id(HJYBizDataContext.getSessionIdentity().getClientId());
		chmodBathVo.setHjy_operator_name(HJYBizDataContext.getSessionIdentity().getOperatorName());
		chmodBathVo.setGoods_status(BasicFields.ENABLE);
		Map<String, Object> result = bizGoodsService.chmodBath(chmodBathVo);

		// 更新搜索引擎
		List<String> ids = chmodBathVo.getIds();
		for (String id : ids) {
			Map<String, Object> carTypeName = bizGoodsCarModelService.queryNamesByGoodsId(id);
			UpdateSolrVo updateSolrVo = JSONUtil.trans(carTypeName.get(MallFields.TYPE_NAMES_IN_SOLR),
					UpdateSolrVo.class);
			if (updateSolrVo == null) {
				updateSolrVo = new UpdateSolrVo();
				updateSolrVo.setGoods_id(id);
			}
			bizGoodsService.updateSolr(updateSolrVo);
		}
		return result;
	}

	/**
	 * @date 2017年2月23日
	 * @Description: 上架商品
	 * @author 杨益桦
	 * @param undercarriageGoodsVo
	 * @return Map<String,Object>
	 */
	@ApiOperation(value = "上架商品", notes = "")
	@RequestMapping(value = "/groundingGoods", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> groundingGoods(@RequestBody UndercarriageGoodsVo undercarriageGoodsVo) {
		undercarriageGoodsVo.setHjy_operator_id(HJYBizDataContext.getSessionIdentity().getClientId());
		undercarriageGoodsVo.setHjy_operator_name(HJYBizDataContext.getSessionIdentity().getOperatorName());
		Map<String, Object> result = bizGoodsService.groundingGoods(undercarriageGoodsVo);

		Map<String, Object> carTypeName = bizGoodsCarModelService
				.queryNamesByGoodsId(undercarriageGoodsVo.getGoods_id());
		UpdateSolrVo updateSolrVo = JSONUtil.trans(carTypeName.get(MallFields.TYPE_NAMES_IN_SOLR), UpdateSolrVo.class);
		if (updateSolrVo == null) {
			updateSolrVo = new UpdateSolrVo();
			updateSolrVo.setGoods_id(undercarriageGoodsVo.getGoods_id());
		}
		bizGoodsService.updateSolr(updateSolrVo);
		return result;
	}

	/**
	 * @date 2017年3月16日
	 * @Description: 删除商品，标志位删除
	 * @author 杨益桦
	 * @param undercarriageGoodsVo
	 * @return Map<String,Object>
	 */
	@ApiOperation(value = "删除商品", notes = "")
	@RequestMapping(value = "/delGoods", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delGoods(@RequestBody UndercarriageGoodsVo undercarriageGoodsVo) {
		undercarriageGoodsVo.setHjy_operator_id(HJYBizDataContext.getSessionIdentity().getClientId());
		undercarriageGoodsVo.setHjy_operator_name(HJYBizDataContext.getSessionIdentity().getOperatorName());
		Map<String, Object> result = bizGoodsService.delGoods(undercarriageGoodsVo);
		return result;
	}

	// @NoLogin
	// @RequestMapping(value="/test/updateSaleNum",method = RequestMethod.POST)
	// @ResponseBody
	// public Map<String, Object> updateSaleNum(@RequestBody UpdateSaleNumVo
	// updateSaleNumVo) {
	// // Map<String, Object> result =
	// // bizGoodsService.updateSaleNum(updateSaleNumVo);
	// Map<String, Object> result = VOUtil.genSuccessResult();
	// return result;
	// }

	// 要移动的
	/**
	 * @date 2017年3月19日
	 * @Description: /json/900305 查询搜索引擎
	 * @author 杨益桦
	 * @param querySolrVo
	 * @return Map<String,Object>
	 */

	@ApiOperation(value = "查询搜索引擎的商品列表", notes = "")
	@RequestMapping(value = GoodsCode.QUERY_SOLR, method = RequestMethod.POST)
	@NoLogin
	@ResponseBody
	public Map<String, Object> querySolrByAll(@RequestBody QuerySolrVo querySolrVo) {
		Map<String, Object> result = bizGoodsService.querySolrByAll(querySolrVo);
		return result;
	}

	/**
	 * @date 2017年3月19日
	 * @Description: app 查询单个商品 /json/900302
	 * @author 杨益桦
	 * @param getJsonGoodsVo
	 * @return Map<String,Object>
	 */
	@ApiOperation(value = "app获得商品详情", notes = "")
	@RequestMapping(value = GoodsCode.GET_GOODS, method = RequestMethod.POST)
	@NoLogin
	@ResponseBody
	public Map<String, Object> getJsonGoods(@RequestBody GetJsonGoodsVo getJsonGoodsVo) {
		Map<String, Object> result = bizGoodsService.getJsonGoods(getJsonGoodsVo);

		AddGoodsCarModelsBatch addGoodsCarModelsBatch = new AddGoodsCarModelsBatch();
		addGoodsCarModelsBatch.setGoods_id(getJsonGoodsVo.getGoods_id());
		addGoodsCarModelsBatch.setPage(getJsonGoodsVo.getPage());
		addGoodsCarModelsBatch.setLimit(getJsonGoodsVo.getLimit());
		Map<String, Object> modelResult = bizGoodsCarModelService.queryGoodsCarModelList(addGoodsCarModelsBatch);
		result.put(MallFields.CAR_MODEL_LIST, modelResult.get(BasicFields.RESULT_LIST));

		Map<String, Object> brandRel = bizGoodsBrandService
				.findGoodsBrandById(new GoodsBrandQueryById((String) result.get(MallFields.BRAND_ID)));
		@SuppressWarnings("unchecked")
		Map<String, Object> brandMap = JSONUtil.trans(brandRel.get(BasicFields.RESULT_LIST), Map.class);
		if (brandMap != null) {
			result.put(MallFields.BRAND_LOGO, brandMap.get(MallFields.BRAND_LOGO));
		} else {
			result.put(MallFields.BRAND_LOGO, "");
		}
		result.put(BasicFields.TOTAL_NUM, modelResult.get(BasicFields.TOTAL_NUM));
		return result;
	}

	/**
	 * @date 2017年3月19日
	 * @Description: app 机型分页 /json/900304
	 * @author 杨益桦
	 * @param queryModelByGoodsVo
	 * @return Map<String,Object>
	 */
	@ApiOperation(value = "app机型分页", notes = "")
	@RequestMapping(value = GoodsCode.QUERY_MODEL_BY_GOODS, method = RequestMethod.POST)
	@NoLogin
	@ResponseBody
	public Map<String, Object> queryModelByGoods(@RequestBody QueryModelByGoodsVo queryModelByGoodsVo) {
		Map<String, Object> result = VOUtil.genSuccessResult();
		AddGoodsCarModelsBatch addGoodsCarModelsBatch = new AddGoodsCarModelsBatch();
		addGoodsCarModelsBatch.setGoods_id(queryModelByGoodsVo.getGoods_id());
		addGoodsCarModelsBatch.setPage(queryModelByGoodsVo.getPage());
		addGoodsCarModelsBatch.setLimit(queryModelByGoodsVo.getLimit());
		Map<String, Object> modelResult = bizGoodsCarModelService.queryGoodsCarModelList(addGoodsCarModelsBatch);
		result.put(BasicFields.RESULT_LIST, modelResult.get(BasicFields.RESULT_LIST));
		result.put(BasicFields.TOTAL_NUM, modelResult.get(BasicFields.TOTAL_NUM));

		GetJsonGoodsVo getJsonGoodsVo = new GetJsonGoodsVo();
		getJsonGoodsVo.setGoods_id(queryModelByGoodsVo.getGoods_id());
		Map<String, Object> goodsResult = bizGoodsService.getJsonGoods(getJsonGoodsVo);
		result.put(MallFields.ADAPT_ALL_MODELS, goodsResult.get(MallFields.ADAPT_ALL_MODELS));
		return result;
	}

	// 要移动的

	/**
	 * @date 2017年3月15日
	 * @Description: 商品首页展示图片上传,添加的处理
	 * @author 李慧峰
	 * @param addGoodsVo
	 * @param goods
	 *            void
	 */
	private void addShowUrl(AddGoodsVo addGoodsVo, UploadType type) {
		String img_path = null;
		if (UploadType.GOODS_AD.equals(type)) {
			img_path = null;
			if (!StringUtil.isBlank(addGoodsVo.getAd_url())) {
				img_path = uploadService.uploadImg(type.getDescription(), addGoodsVo.getAd_url());
			}
			addGoodsVo.setAd_url(img_path);

		} else if (UploadType.GOODS_SHOW.equals(type)) {
			img_path = null;
			if (!StringUtil.isBlank(addGoodsVo.getShow_url())) {
				img_path = uploadService.uploadImg(type.getDescription(), addGoodsVo.getShow_url());
			}
			addGoodsVo.setShow_url(img_path);
		} else if (UploadType.GOODS_BANNER.equals(type)) {

			List<Map<String, Object>> Listmap = addGoodsVo.getBannerList();
			List<Map<String, Object>> Listresult = new LinkedList<Map<String, Object>>();
			if (Listmap.size() > 0) {
				img_path = null;
				for (Map<String, Object> map : Listmap) {
					Map<String, Object> mapimg = new HashMap();
					img_path = uploadService.uploadImg(type.getDescription(), (String) map.get("pic_url"));
					mapimg.put("pic_url", img_path);
					Listresult.add(mapimg);
				}
				addGoodsVo.setBannerList(Listresult);

			}
		} else if (UploadType.GOODS_DETAIL.equals(type)) {
			List<Map<String, Object>> Listmap = addGoodsVo.getDetailList();
			List<Map<String, Object>> Listresult = new LinkedList<Map<String, Object>>();
			if (Listmap.size() > 0) {
				img_path = null;
				for (Map<String, Object> map : Listmap) {
					Map<String, Object> mapimg = new HashMap();
					img_path = uploadService.uploadImg(type.getDescription(), (String) map.get("pic_url"));
					mapimg.put("pic_url", img_path);
					mapimg.put("pic_desc", map.get("pic_desc"));
					mapimg.put("pic_id", map.get("pic_id"));
					Listresult.add(mapimg);
				}
				addGoodsVo.setDetailList(Listresult);

			}
		}

	}

	/**
	 * @date 2017年3月15日
	 * @Description: 商品首页展示图片上传,添加的处理
	 * @author 李慧峰
	 * @param addGoodsVo
	 * @param goods
	 *            void
	 */
	private void editShowUrl(ModifyGoodsVo addGoodsVo, UploadType type) {
		String img_path = null;
		if (UploadType.GOODS_AD.equals(type)) {
			img_path = null;
			if (!StringUtil.isBlank(addGoodsVo.getAd_url()) && addGoodsVo.getAd_url().indexOf("data") > -1) {
				img_path = uploadService.uploadImg(type.getDescription(), addGoodsVo.getAd_url());
				addGoodsVo.setAd_url(img_path);
			}
		} else if (UploadType.GOODS_SHOW.equals(type)) {
			img_path = null;
			if (!StringUtil.isBlank(addGoodsVo.getShow_url()) && addGoodsVo.getShow_url().indexOf("data") > -1) {
				img_path = uploadService.uploadImg(type.getDescription(), addGoodsVo.getShow_url());
				addGoodsVo.setShow_url(img_path);
			}

		} else if (UploadType.GOODS_BANNER.equals(type)) {

			List<Map<String, Object>> Listmap = addGoodsVo.getBannerList();
			List<Map<String, Object>> Listresult = new LinkedList<Map<String, Object>>();
			if (Listmap.size() > 0) {
				img_path = null;
				for (Map<String, Object> map : Listmap) {
					Map<String, Object> mapimg = new HashMap();
					String imgdata = (String) map.get("pic_url");
					if (!StringUtil.isBlank(imgdata) && imgdata.indexOf("data") > -1) {
						img_path = uploadService.uploadImg(type.getDescription(), (String) map.get("pic_url"));
						mapimg.put("pic_url", img_path);
					} else {
						mapimg.put("pic_url", map.get("pic_url"));
					}
					mapimg.put("pic_id", map.get("pic_id"));
					Listresult.add(mapimg);
				}
				addGoodsVo.setBannerList(Listresult);

			}
		} else if (UploadType.GOODS_DETAIL.equals(type)) {
			List<Map<String, Object>> Listmap = addGoodsVo.getDetailList();
			List<Map<String, Object>> Listresult = new LinkedList<Map<String, Object>>();
			if (Listmap.size() > 0) {
				img_path = null;
				for (Map<String, Object> map : Listmap) {
					Map<String, Object> mapimg = new HashMap();
					String imgdata = (String) map.get("pic_url");
					if (!StringUtil.isBlank(imgdata) && imgdata.indexOf("data") > -1) {
						img_path = uploadService.uploadImg(type.getDescription(), (String) map.get("pic_url"));
						mapimg.put("pic_url", img_path);
					} else {
						mapimg.put("pic_url", map.get("pic_url"));
					}

					mapimg.put("pic_desc", map.get("pic_desc"));
					mapimg.put("pic_id", map.get("pic_id"));
					Listresult.add(mapimg);
				}
				addGoodsVo.setDetailList(Listresult);

			}
		}

	}

}

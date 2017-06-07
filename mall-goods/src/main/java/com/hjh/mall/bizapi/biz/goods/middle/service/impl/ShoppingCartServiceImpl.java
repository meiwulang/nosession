package com.hjh.mall.bizapi.biz.goods.middle.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.hjh.mall.bizapi.biz.goods.middle.dao.ShoppingCartDao;
import com.hjh.mall.bizapi.biz.goods.middle.dao.ShoppingCartDetailDao;
import com.hjh.mall.bizapi.biz.goods.middle.entity.ShoppingCart;
import com.hjh.mall.bizapi.biz.goods.middle.entity.ShoppingCartDetail;
import com.hjh.mall.bizapi.biz.goods.middle.service.GoodsService;
import com.hjh.mall.bizapi.biz.goods.middle.service.base.HJYServiceImplBase;
import com.hjh.mall.cache.cache.helper.CacheHelper;
import com.hjh.mall.cache.cache.sequence.KeyGenerate;
import com.hjh.mall.category.bizapi.bizserver.goodscar.vo.AddGoodsCarModelsBatch;
import com.hjh.mall.common.core.constants.BasicErrorCodes;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.common.core.model.Pagination;
import com.hjh.mall.common.core.model.SortMarker;
import com.hjh.mall.common.core.util.DateTimeUtil;
import com.hjh.mall.common.core.util.JSONUtil;
import com.hjh.mall.common.core.util.PinyinUtil;
import com.hjh.mall.common.core.util.StringUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.common.core.vo.PageQueryVO;
import com.hjh.mall.field.constant.CacheKeys;
import com.hjh.mall.field.constant.MallFields;
import com.hjh.mall.field.error.MallErrorCode;
import com.hjh.mall.field.type.Status;
import com.hjh.mall.goods.bizapi.bizserver.BizShoppingCartService;
import com.hjh.mall.goods.bizapi.bizserver.vo.AddPreOrderVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.DelPreOrderStandardsVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.DelPreOrderVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.DelPreOrdersVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.GetLastInfoParamsVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.GetLastInfoVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.QueryGoodsVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.UpdatePreOrderStandardsVo;

/**
 * @Project: mall-goods-bran
 * @Description 购物车业务层
 * @author 王斌
 * @date 2017年5月15日
 * @version V1.0
 */
@Service
public class ShoppingCartServiceImpl extends HJYServiceImplBase<ShoppingCart, String>
		implements BizShoppingCartService {
	@Resource
	private ShoppingCartDao shoppingCartDao;
	@Resource
	private ShoppingCartDetailDao shoppingCartDetailDao;
	@Resource
	private CacheHelper cacheHelper;
	@Resource
	private KeyGenerate idHelper;
	@Resource
	private GoodsService goodsService;

	@Override
	protected DAOBase<ShoppingCart, String> getDAO() {
		return shoppingCartDao;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public Map<String, Object> addNewPreOrder(AddPreOrderVo vo) {
		// 校验用户信息
		String clientStr = cacheHelper.get(CacheKeys.MALL_WEB_ACCESS_TOKEN_PREFIX + vo.getAccess_token());
		if (StringUtil.isBlank(clientStr)) {
			return VOUtil.genErrorResult(BasicErrorCodes.NOT_LOGGED);
		}
		// 校验规格列表大小
		if (vo.getAddPreOrderDetailVo().size() < 1) {
			return VOUtil.genErrorResult(MallErrorCode.OrderErrorCode.ERROR_SPEC);
		}
		// 生成处理业务需要的参数
		Map<String, Object> result = VOUtil.genSuccessResult();
		Set<Map<String, Object>> shopping_cart_details = new HashSet<>();
		String keyGenerate = idHelper.getKeyGenerate(MallFields.SHOPPING_CART_ID);
		List<ShoppingCartDetail> detailList = JSONUtil.transInSide(vo.getAddPreOrderDetailVo(),
				ShoppingCartDetail.class);// 传入的规格
		String clientId = JSON.parseObject(clientStr).getString(MallFields.CLIENTID);
		String standard_ids = "";
		String currentTimeStr = DateTimeUtil.getCurrentDateString(DateTimeUtil.FORMAT_YYYYMMDDHHMMSS_NO_BREAK);
		String currentDate = currentTimeStr.substring(0, 8);
		String currentTime = currentTimeStr.substring(8);
		// 对比购物车信息和商品信息
		QueryGoodsVo goodsVo = new QueryGoodsVo();
		String prdtId = vo.getGoods_id();
		goodsVo.setGoods_id(prdtId);
		Map<String, Object> goods = queryGoods(goodsVo);
		String errorNo = (String) goods.get(BasicFields.ERROR_NO);
		// 商品不存在就停止加入购物车
		if (!BasicFields.SUCCESS.equals(errorNo)) {
			return VOUtil.genErrorResult(MallErrorCode.OrderErrorCode.GOODS_NOT_EXIST);
		}
		List<Map<String, Object>> list = (List<Map<String, Object>>) goods.get(BasicFields.RESULT_LIST);

		if (list.size() > 0) {
			goods = list.get(0);
		} else {
			return VOUtil.genErrorResult(MallErrorCode.OrderErrorCode.GOODS_NOT_EXIST);
		}
		// 校验该商品在购物车是否存在
		ShoppingCart example = new ShoppingCart();
		example.setGoods_id(vo.getGoods_id());
		example.setClientId(clientId);
		List<ShoppingCart> insertedShopCarts = query(example);
		if (insertedShopCarts.size() > 0) {// 已有规格加入购物车
			ShoppingCart currentInsertedShopCart = insertedShopCarts.get(0);
			// 处理规格
			List<ShoppingCartDetail> newstandards = new ArrayList<>();
			// 查找规格是否存在
			for (ShoppingCartDetail shopDetail : detailList) {
				ShoppingCartDetail isExistParam = new ShoppingCartDetail();
				isExistParam.setShopping_cart_id(currentInsertedShopCart.getShoppingCartId());
				isExistParam.setStandard_id(shopDetail.getStandard_id());
				List<ShoppingCartDetail> existDetails = shoppingCartDetailDao.query(isExistParam);

				// 规格已插入，更新规格相关信息
				if (existDetails.size() > 0) {
					ShoppingCartDetail insertedShopDetail = existDetails.get(0);
					insertedShopDetail.setOptional_first(shopDetail.getOptional_first());
					insertedShopDetail.setOptional_second(shopDetail.getOptional_second());
					insertedShopDetail.setStandard_must(shopDetail.getStandard_must());
					insertedShopDetail.setMetadata_name(shopDetail.getMetadata_name());
					insertedShopDetail.setMax_sale_num(shopDetail.getMax_sale_num());
					insertedShopDetail.setPrice(shopDetail.getPrice());
					insertedShopDetail.setPrdt_num(insertedShopDetail.getPrdt_num() + shopDetail.getPrdt_num());
					Integer max_sale_num = insertedShopDetail.getMax_sale_num();
					if (max_sale_num < insertedShopDetail.getPrdt_num()) {
						insertedShopDetail.setPrdt_num(max_sale_num);
					}
					shoppingCartDetailDao.update(insertedShopDetail);
					Map<String, Object> shopping_cart_detail = new HashMap<>();
					shopping_cart_details.add(shopping_cart_detail);
					shopping_cart_detail.put(MallFields.SHOPPING_CART_DETAIL_ID,
							insertedShopDetail.getShoppingCartDetailId());
					shopping_cart_detail.put(MallFields.STANDARD_ID, insertedShopDetail.getStandard_id());
				} else {// 规格未插入，新增规格相关信息，并修改insertedShopCart的standardids
					String odDtlPk = idHelper.getKeyGenerate(MallFields.SHOPPING_CART_DETAIL_ID);
					shopDetail.setShoppingCartDetailId(odDtlPk);
					shopDetail.setGoods_name(vo.getGoods_name());
					shopDetail.setShopping_cart_id(currentInsertedShopCart.getShoppingCartId());
					standard_ids += shopDetail.getStandard_id() + ",";
					shopDetail.initForClearNull();
					newstandards.add(shopDetail);
					Map<String, Object> shopping_cart_detail = new HashMap<>();
					shopping_cart_details.add(shopping_cart_detail);
					shopping_cart_detail.put(MallFields.SHOPPING_CART_DETAIL_ID, odDtlPk);
					shopping_cart_detail.put(MallFields.STANDARD_ID, shopDetail.getStandard_id());
				}
			}
			// 处理子表主键
			if (standard_ids.length() > 1) {
				standard_ids = currentInsertedShopCart.getStandardIds() + ","
						+ standard_ids.substring(0, standard_ids.length() - 1);
			} else {
				standard_ids = currentInsertedShopCart.getStandardIds();
			}
			HashSet<String> set = new HashSet<>();
			set.addAll(Arrays.asList(standard_ids.split(",")));
			standard_ids = set.toString().substring(1, set.toString().length() - 1).replace(" ", "");
			// 处理购物车主要信息
			ShoppingCart entity = JSONUtil.trans(vo, ShoppingCart.class);
			ShoppingCart shoppingCart = currentInsertedShopCart;
			entity.setShoppingCartId(shoppingCart.getShoppingCartId());
			entity.setStandardIds(standard_ids);
			entity.setBrand_name(vo.getBrand_name());
			entity.setUpdateDate(currentDate);
			entity.setUpdateTime(currentTime);
			entity.setBrand_name((String) goods.get(MallFields.BRAND_NAME));
			entity.setGoods_name((String) goods.get(MallFields.GOODS_NAME));
			entity.setShow_url((String) goods.get(MallFields.SHOW_URL));
			entity.setBrand_id((String) goods.get(MallFields.BRAND_ID));
			String brand_spell = PinyinUtil.getPingYin(vo.getBrand_name());
			if (brand_spell.length() > 10) {
				brand_spell = brand_spell.substring(0, 10);
			}
			entity.setBrand_spell(brand_spell);
			// 商品已存在 该更新购物车商品信息
			update(entity);
			if (newstandards.size() > 0) {
				shoppingCartDetailDao.batchSave(newstandards);
				result.put(MallFields.SHOPPING_CART_ID, entity.getShoppingCartId());
			}
			result.put(MallFields.SHOPPING_CART_ID, entity.getShoppingCartId());
			result.put(MallFields.BRAND_SPELL, brand_spell);
		} else {// 没有规格加入，保存新规格，加入购物车
			for (ShoppingCartDetail detail : detailList) {
				Integer prdt_num = detail.getPrdt_num();
				if (prdt_num == null || prdt_num < 1) {// 数量不合法
					return VOUtil.genErrorResult(MallErrorCode.OrderErrorCode.ERROR_NUM);
				}
				BigDecimal prdt_price = detail.getPrice();
				if (prdt_price == null || prdt_price.compareTo(new BigDecimal(0)) < 0) {// 价格不合法
					return VOUtil.genErrorResult(MallErrorCode.OrderErrorCode.ERROR_PRICE);
				}
				String standard_must = detail.getStandard_must();
				if (StringUtil.isBlank(standard_must)) {// 规格不存在
					return VOUtil.genErrorResult(MallErrorCode.OrderErrorCode.ERROR_MUST_SPEC);
				}
				String voStandardId = detail.getStandard_id();
				if (StringUtil.isBlank(voStandardId)) {// 规格主键不存在
					return VOUtil.genErrorResult(MallErrorCode.OrderErrorCode.GOOGS_ERROR_STANDANDS_ID);
				}
				String odDtlPk = idHelper.getKeyGenerate(MallFields.SHOPPING_CART_DETAIL_ID);
				detail.setShoppingCartDetailId(odDtlPk);
				detail.setGoods_name(vo.getGoods_name());
				detail.setShopping_cart_id(keyGenerate);
				Map<String, Object> shopping_cart_detail = new HashMap<>();
				shopping_cart_details.add(shopping_cart_detail);
				shopping_cart_detail.put(MallFields.SHOPPING_CART_DETAIL_ID, odDtlPk);
				shopping_cart_detail.put(MallFields.STANDARD_ID, detail.getStandard_id());
				standard_ids += detail.getStandard_id() + ",";
				detail.initForClearNull();
			}
			// 处理购物车主要信息
			ShoppingCart entity = JSONUtil.trans(vo, ShoppingCart.class);
			entity.setClientId(clientId);
			entity.setBrand_name(vo.getBrand_name());
			entity.setShoppingCartId(keyGenerate);
			entity.setStandardIds(standard_ids.substring(0, standard_ids.length() - 1));
			entity.setUpdateDate(currentDate);
			entity.setUpdateTime(currentTime);
			entity.setInitDate(currentDate);
			entity.setInitTime(currentTime);
			entity.setBrand_id((String) goods.get(MallFields.BRAND_ID));
			String brand_spell = PinyinUtil.getPingYin(entity.getBrand_name());
			if (brand_spell.length() > 10) {
				brand_spell = brand_spell.substring(0, 10);
			}
			entity.setBrand_spell(brand_spell);
			save(entity);
			shoppingCartDetailDao.batchSave(detailList);
			result.put(MallFields.SHOPPING_CART_ID, keyGenerate);
			result.put(MallFields.BRAND_SPELL, brand_spell);
		}
		result.put(MallFields.SHOPPINGCARTDETAILS, shopping_cart_details);
		return result;

	}

	@Override
	@Transactional
	public Map<String, Object> delPreOrder(DelPreOrderVo vo) {
		String shopping_cart_id = vo.getShopping_cart_id();
		ShoppingCart currentEntity = get(shopping_cart_id);
		if (currentEntity != null && StringUtil.isNotBlank(currentEntity.getStandardIds())) {
			delete(shopping_cart_id);
			ShoppingCartDetail detail = new ShoppingCartDetail();
			detail.setIds(Arrays.asList(currentEntity.getStandardIds().split(",")));
			shoppingCartDetailDao.batchDel(detail);
		}
		return VOUtil.genSuccessResult();
	}

	/**
	 * @date 2017年5月11日
	 * @Description: 删除指定预订单的规格
	 * @author：王斌
	 * @param vo
	 * @return Map<String,Object>
	 */
	@Override
	@Transactional
	public Map<String, Object> delPreOrdersStandard(DelPreOrderStandardsVo vo) {
		ShoppingCartDetail detail = new ShoppingCartDetail();
		String shopping_cart_id = vo.getShopping_cart_id();
		detail.setShopping_cart_id(shopping_cart_id);
		List<String> shopping_cart_detail_ids = vo.getShopping_cart_detail_ids();
		detail.setIds(shopping_cart_detail_ids);
		ShoppingCart shoppingCart = shoppingCartDao.get(shopping_cart_id);
		// 删除多余规格
		if (shoppingCart != null) {
			String standIds = shoppingCart.getStandardIds();
			HashSet<String> standardSet = new HashSet<String>();
			standardSet.addAll(Arrays.asList(standIds.split(",")));
			standardSet.removeAll(shoppingCartDetailDao.getshoppingCartStandards(detail));
			standIds = standardSet.toString().substring(1, standardSet.toString().length() - 1).replaceAll(" ", "");
			if (!standardSet.isEmpty()) {
				shoppingCart.setStandardIds(standIds);
				shoppingCartDao.update(shoppingCart);
			} else {
				shoppingCartDao.delete(shopping_cart_id);
			}
			shoppingCartDetailDao.batchDel(detail);
		}
		return VOUtil.genSuccessResult();
	}

	@Override
	@Transactional
	public Map<String, Object> delPreOrders(DelPreOrdersVo vo) {
		ShoppingCart currentEntity = new ShoppingCart();
		ArrayList<String> shopping_cart_ids = vo.getShopping_cart_ids();
		if (shopping_cart_ids.size() > 0) {
			currentEntity.setIds(shopping_cart_ids);
			String standards = shoppingCartDao.queryStandardIds(currentEntity);
			shoppingCartDao.batchDel(currentEntity);
			if (StringUtil.isNotBlank(standards)) {
				ShoppingCartDetail detail = new ShoppingCartDetail();
				detail.setIds(Arrays.asList(standards.split(",")));
				shoppingCartDetailDao.batchDel(detail);
			}
		}
		return VOUtil.genSuccessResult();
	}

	@Override
	@Transactional
	public Map<String, Object> queryPreOrders(PageQueryVO vo) {
		String clientStr = cacheHelper.get(CacheKeys.MALL_WEB_ACCESS_TOKEN_PREFIX + vo.getAccess_token());
		if (StringUtil.isBlank(clientStr)) {
			return VOUtil.genErrorResult(BasicErrorCodes.NOT_LOGGED);
		}
		String clientId = JSON.parseObject(clientStr).getString(MallFields.CLIENTID);
		ShoppingCart currentEntity = new ShoppingCart();
		currentEntity.setClientId(clientId);
		int count = shoppingCartDao.count(currentEntity);
		Pagination page = new Pagination();
		page.setPage_size(vo.getLimit());
		page.setPage_no(vo.getPage());
		page.setTotal_item_num(count);
		page.calc();
		currentEntity.setPage(page);
		List<SortMarker> sortMarkers = new ArrayList<SortMarker>();
		sortMarkers.add(new SortMarker(MallFields.BRAND_SPELL, true));
		sortMarkers.add(new SortMarker(MallFields.UPDATE_DATE, false));
		sortMarkers.add(new SortMarker(MallFields.UPDATE_TIME, false));
		currentEntity.setSortMarkers(sortMarkers);
		Map<String, Object> result = VOUtil.genSuccessResult();
		List<Map<String, Object>> queryResult = shoppingCartDao.queryReturnMap(currentEntity);// 购物车列表
		// List<Map<String, Object>> needDel = new ArrayList<>();
		Iterator<Map<String, Object>> iterator = queryResult.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> cart = iterator.next();
			List<String> standards = Arrays.asList(((String) cart.get(MallFields.STANDARD_IDS)).split(","));// 规格数组
			ArrayList<Map<String, Object>> queryBydetails = new ArrayList<>();
			if (standards.size() > 0) {
				ShoppingCartDetail queryDetailEnt = new ShoppingCartDetail();
				queryDetailEnt.setShopping_cart_id((String) cart.get(MallFields.SHOPPING_CART_ID));
				queryDetailEnt.setIds(standards);
				queryBydetails = shoppingCartDetailDao.queryBydetails(queryDetailEnt);
			}
			if (queryBydetails.size() < 1) {
				delete((String) cart.get(MallFields.SHOPPING_CART_DETAIL_ID));
				// needDel.add(cart);
				iterator.remove();
				continue;
			}
			cart.put(MallFields.SHOPPINGCARTDETAILS, queryBydetails);
		}
		// for (Map<String, Object> cart : queryResult) {// 购物车详情封装
		// List<String> standards = Arrays.asList(((String)
		// cart.get(MallFields.STANDARD_IDS)).split(","));// 规格数组
		// ArrayList<Map<String, Object>> queryBydetails = new ArrayList<>();
		// if (standards.size() > 0) {
		// ShoppingCartDetail queryDetailEnt = new ShoppingCartDetail();
		// queryDetailEnt.setShopping_cart_id((String)
		// cart.get(MallFields.SHOPPING_CART_ID));
		// queryDetailEnt.setIds(standards);
		// queryBydetails =
		// shoppingCartDetailDao.queryBydetails(queryDetailEnt);
		// }
		// if (queryBydetails.size() < 1) {
		// delete((String) cart.get(MallFields.SHOPPING_CART_DETAIL_ID));
		// needDel.add(cart);
		// continue;
		// }
		// cart.put(MallFields.SHOPPINGCARTDETAILS, queryBydetails);
		// }
		// queryResult.removeAll(needDel);
		result.put(BasicFields.RESULT_LIST, queryResult);
		currentEntity.setPage(null);
		result.put(BasicFields.TOTAL_NUM, queryResult.size());
		return result;
	}

	/**
	 * @date 2017年4月12日
	 * @Description: 获取商品详情
	 * @author：王斌
	 * @param queryGoodsVo
	 * @return Map<String,Object>
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryGoods(QueryGoodsVo queryGoodsVo) {
		Map<String, Object> result = goodsService.queryGoods(queryGoodsVo);
		List<Map<String, Object>> resultList = (List<Map<String, Object>>) result.get(BasicFields.RESULT_LIST);
		for (Map<String, Object> map : resultList) {
			AddGoodsCarModelsBatch addGoodsCarModelsBatch = new AddGoodsCarModelsBatch();
			addGoodsCarModelsBatch.setGoods_id(map.get(MallFields.GOODS_ID).toString());
			addGoodsCarModelsBatch.setPage(1);
			addGoodsCarModelsBatch.setLimit(1000000);
		}
		return result;
	}

	@Override
	public Map<String, Object> updatePreOrdersStandard(UpdatePreOrderStandardsVo vo) {
		ShoppingCartDetail detail = JSONUtil.trans(vo, ShoppingCartDetail.class);
		if (null != detail.getPrice() && detail.getPrice().compareTo(new BigDecimal(0)) < 0) {
			return VOUtil.genErrorResult(MallErrorCode.OrderErrorCode.ERROR_PRICE);
		}
		ShoppingCartDetail shoppingCartDetail = shoppingCartDetailDao.get(vo.getShoppingCartDetailId());
		if (shoppingCartDetail != null) {

			if (null != detail.getPrdt_num()
					&& (detail.getPrdt_num() < 0 || detail.getPrdt_num() > shoppingCartDetail.getMax_sale_num())) {
				return VOUtil.genErrorResult(MallErrorCode.OrderErrorCode.ERROR_NUM);
			}
		}
		shoppingCartDetailDao.update(detail);
		return VOUtil.genSuccessResult();
	}

	@Override
	public Map<String, Object> getlastInfo(GetLastInfoVo vo) {
		// 校验用户信息
		String clientStr = cacheHelper.get(CacheKeys.MALL_WEB_ACCESS_TOKEN_PREFIX + vo.getAccess_token());
		if (StringUtil.isBlank(clientStr)) {
			return VOUtil.genErrorResult(BasicErrorCodes.NOT_LOGGED);
		}
		String clientId = JSON.parseObject(clientStr).getString(MallFields.CLIENTID);
		Map<String, Object> result = VOUtil.genSuccessResult();
		List<Map<String, Object>> resultList = new ArrayList<>();
		final ArrayList<Map<String, Object>> needUpdateInfo = new ArrayList<>();
		HashSet<String> errorShopcartIds = new HashSet<>();
		// final HashSet<String> allShopDetailIds = new HashSet<>();// 全部请求规格集合
		final HashSet<String> allExistShopDetailIds = new HashSet<>();// 全部失效规格集合
		for (GetLastInfoParamsVo shopCartvo : vo.getShopcartIds()) {
			// 查出shopCart信息
			ShoppingCart shoppingCart = new ShoppingCart();
			shoppingCart.setClientId(clientId);
			shoppingCart.setShoppingCartId(shopCartvo.getShopping_cart_id());
			Map<String, Object> lastshopCart = shoppingCartDao.getLastshopCart(shoppingCart);// 查出最新的shopCart信息
			if (lastshopCart != null
					&& ((String) lastshopCart.get(MallFields.GOODS_STATUS)).equals(Status.ENABLED.getVal())) {

				if (lastshopCart != null && lastshopCart.size() > 0) {// 查出最新的shopCartdetail信息
					ShoppingCartDetail shopdetail = new ShoppingCartDetail();
					List<String> shopping_cart_detail_ids = shopCartvo.getShopping_cart_detail_ids();
					// allShopDetailIds.addAll(shopping_cart_detail_ids);//
					shopdetail.setIds(shopping_cart_detail_ids);
					ArrayList<Map<String, Object>> lastshopcartDetailListByPks = shoppingCartDetailDao
							.getLastshopcartDetailListByPks(shopdetail);
					for (Map<String, Object> shopDetails : lastshopcartDetailListByPks) {
						allExistShopDetailIds.add((String) shopDetails.get(MallFields.SHOPPING_CART_DETAIL_ID));
					}
					needUpdateInfo.addAll(lastshopcartDetailListByPks);
					lastshopCart.put(MallFields.SHOPPINGCARTDETAILS, lastshopcartDetailListByPks);
					resultList.add(lastshopCart);
				}
			} else {
				// 错误记录
				errorShopcartIds.add(shoppingCart.getShoppingCartId());
			}
		}
		// if (allShopDetailIds.size() > allExistShopDetailIds.size() &&
		// allExistShopDetailIds.size() > 0) {
		// allShopDetailIds.retainAll(allExistShopDetailIds);
		// }
		result.put(MallFields.EXISTS_SHOP_CART_DETAIL_IDS, allExistShopDetailIds);
		// 同步数据库，更新最新数据
		new Thread() {
			@Override
			public void run() {
				// 对象信息转化
				List<ShoppingCartDetail> details = JSONUtil.transInSide(needUpdateInfo, ShoppingCartDetail.class);
				if (details.size() > 0) {
					shoppingCartDetailDao.batchUpdateDetail(details);
				}
			};
		}.start();
		result.put(BasicFields.RESULT_LIST, resultList);
		result.put(MallFields.ERROR_SHOP_CART_IDS, errorShopcartIds);
		return result;
	}
}

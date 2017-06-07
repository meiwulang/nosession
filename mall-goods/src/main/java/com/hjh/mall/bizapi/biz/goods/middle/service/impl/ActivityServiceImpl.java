package com.hjh.mall.bizapi.biz.goods.middle.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hjh.mall.bizapi.biz.goods.middle.dao.ActivityDao;
import com.hjh.mall.bizapi.biz.goods.middle.dao.ActivityGoodDao;
import com.hjh.mall.bizapi.biz.goods.middle.dao.GoodsDao;
import com.hjh.mall.bizapi.biz.goods.middle.dao.GoodsInfoDao;
import com.hjh.mall.bizapi.biz.goods.middle.dao.GoodsPicDao;
import com.hjh.mall.bizapi.biz.goods.middle.dao.GoodsStandardsDao;
import com.hjh.mall.bizapi.biz.goods.middle.entity.Activity;
import com.hjh.mall.bizapi.biz.goods.middle.entity.ActivityGood;
import com.hjh.mall.bizapi.biz.goods.middle.entity.Goods;
import com.hjh.mall.bizapi.biz.goods.middle.entity.GoodsInfo;
import com.hjh.mall.bizapi.biz.goods.middle.entity.GoodsPic;
import com.hjh.mall.bizapi.biz.goods.middle.entity.GoodsStandards;
import com.hjh.mall.bizapi.biz.goods.middle.service.GoodsService;
import com.hjh.mall.bizapi.biz.goods.middle.service.base.HJYServiceImplBase;
import com.hjh.mall.cache.cache.constants.CacheKeys;
import com.hjh.mall.cache.cache.helper.CacheHelper;
import com.hjh.mall.cache.cache.sequence.KeyGenerate;
import com.hjh.mall.common.core.constants.BasicErrorCodes;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.common.core.model.Pagination;
import com.hjh.mall.common.core.model.SortMarker;
import com.hjh.mall.common.core.util.BeanUtil;
import com.hjh.mall.common.core.util.DateTimeUtil;
import com.hjh.mall.common.core.util.JSONUtil;
import com.hjh.mall.common.core.util.StringUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.common.core.vo.PageQueryVO;
import com.hjh.mall.field.constant.MallFields;
import com.hjh.mall.field.error.MallErrorCode;
import com.hjh.mall.field.type.Status;
import com.hjh.mall.goods.bizapi.bizserver.BizActivityService;
import com.hjh.mall.goods.bizapi.bizserver.vo.AddActivityVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.AddGoodsForActivityVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.DelActivitysGoodsVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.QueryActivityDetailVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.QueryActivitysGoodsVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.QueryGoods4ActivityVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.UpdateActivityVo;

/**
 * @Project: mall-goods-jhd
 * @Description 活动业务层
 * @author 王斌
 * @date 2017年5月16日
 * @version V1.0
 */
@Service
public class ActivityServiceImpl extends HJYServiceImplBase<Activity, String> implements BizActivityService {
	@Resource
	private ActivityDao activityDao;
	@Resource
	private ActivityGoodDao activityGoodDao;
	@Resource
	private KeyGenerate idGenerate;
	@Resource
	private CacheHelper cacheHelper;
	@Resource
	private GoodsDao goodsDao;
	@Resource
	private GoodsInfoDao goodsInfoDao;
	@Resource
	private GoodsStandardsDao goodsStandardsDao;
	@Resource
	private GoodsService goodsService;
	@Resource
	private GoodsPicDao goodsPicDao;

	@Override
	protected DAOBase<Activity, String> getDAO() {
		return activityDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> addActivity(AddActivityVo vo) {
		Activity activity = new Activity();
		activity.setSort(vo.getSort());
		if (query(activity).size() > 0) {
			return VOUtil.genErrorResult(MallErrorCode.Activity.SORT_REPEAT);
		}
		if (vo.getSort() > 999) {
			return VOUtil.genErrorResult(MallErrorCode.Activity.SORT_TOO_LAGER);
		}
		activity = JSONUtil.trans(vo, Activity.class);
		activity.setActivityId(idGenerate.getKeyGenerate(MallFields.ACTIVITY_ID));
		String currentTimeStr = DateTimeUtil.getCurrentDateString(DateTimeUtil.FORMAT_YYYYMMDDHHMMSS_NO_BREAK);
		String currentDate = currentTimeStr.substring(0, 8);
		String currentTime = currentTimeStr.substring(8);
		activity.setInitDate(currentDate);
		activity.setInitTime(currentTime);
		activity.setUpdateDate(currentDate);
		activity.setUpdateTime(currentTime);
		Object operatorName = getOPerator(vo);
		if (operatorName instanceof String) {
			activity.setInitUser((String) operatorName);
			activity.setUpdateUser((String) operatorName);
		} else {
			return (Map<String, Object>) operatorName;
		}
		activity.setAppDisplay(Status.DISENABLED.getVal());
		activity.initForClearNull();
		activityDao.save(activity);
		return VOUtil.genSuccessResult();
	}

	/**
	 * @date 2017年5月8日
	 * @Description: 获取操作人
	 * @author：王斌
	 * @param vo
	 * @return Object
	 */
	private Object getOPerator(HJYVO vo) {
		String clientStr = cacheHelper.get(CacheKeys.MALL_WEB_ACCESS_TOKEN_PREFIX + vo.getAccess_token());
		if (StringUtil.isBlank(clientStr)) {
			return VOUtil.genErrorResult(BasicErrorCodes.NOT_LOGGED);
		}
		return JSON.parseObject(clientStr).getString(MallFields.OPERATORNAME_CAMEL_STR);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<String, Object> queryActivitysForWeb(PageQueryVO vo) {
		Activity activity = new Activity();
		Pagination page = new Pagination();
		page.setPage_no(vo.getPage());
		page.setPage_size(vo.getLimit());
		int count = count(activity);
		page.setTotal_item_num(count);
		page.calc();
		activity.setPage(page);
		List<SortMarker> markers = new ArrayList<>();
		markers.add(new SortMarker(MallFields.UPDATE_DATE, false));
		markers.add(new SortMarker(MallFields.UPDATE_TIME, false));
		activity.setSortMarkers(markers);
		Map<String, Object> result = VOUtil.genSuccessResult();
		List<Map> query = JSONUtil.transInSide(query(activity), Map.class);
		result.put(BasicFields.RESULT_LIST, query);
		for (Map m : query) {

			ActivityGood example = new ActivityGood();
			example.setActivityId((String) m.get(MallFields.ACTIVITYID));
			m.put(MallFields.GOODSNUM, activityGoodDao.count(example));
		}
		result.put(BasicFields.TOTAL_NUM, count);
		return result;
	}

	@Override
	public Map<String, Object> queryActivitysForApp(HJYVO vo) {
		Activity activity = new Activity();
		Pagination page = new Pagination();
		page.setPage_no(1);
		page.setPage_size(6);
		page.calc();
		activity.setPage(page);
		activity.setAppDisplay(Status.ENABLED.getVal());
		List<SortMarker> markers = new ArrayList<>();
		markers.add(new SortMarker(MallFields.SORT, true));
		activity.setSortMarkers(markers);
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, query(activity));
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> updateActivity(UpdateActivityVo vo) {
		Integer sort = vo.getSort();
		Activity activity = new Activity();
		if (sort != null) {
			if (sort > 999) {
				return VOUtil.genErrorResult(MallErrorCode.Activity.SORT_TOO_LAGER);
			}
			activity.setSort(sort);
			List<Activity> query = query(activity);
			if (query.size() > 1 || (query.size() == 1 && !vo.getActivityId().equals(query.get(0).getActivityId())
					&& query.get(0).getSort().equals(sort))) {
				return VOUtil.genErrorResult(MallErrorCode.Activity.SORT_REPEAT);
			}
		}
		activity = JSONUtil.trans(vo, Activity.class);
		String currentTimeStr = DateTimeUtil.getCurrentDateString(DateTimeUtil.FORMAT_YYYYMMDDHHMMSS_NO_BREAK);
		String currentDate = currentTimeStr.substring(0, 8);
		String currentTime = currentTimeStr.substring(8);
		activity.setUpdateDate(currentDate);
		activity.setUpdateTime(currentTime);
		Object operatorName = getOPerator(vo);
		if (operatorName instanceof String) {
			activity.setUpdateUser((String) operatorName);
		} else {
			return (Map<String, Object>) operatorName;
		}
		update(activity);
		return VOUtil.genSuccessResult();
	}

	@Override
	public Map<String, Object> addGoodsForActivity(AddGoodsForActivityVo vo) {
		String activtyId = vo.getActivityId();
		Activity activity = activityDao.get(activtyId);
		if (activity == null || !Status.DISENABLED.getVal().equals(activity.getAppDisplay())) {
			return VOUtil.genErrorResult(MallErrorCode.Activity.NOT_EXIST);
		}
		List<ActivityGood> activityGoods = new ArrayList<>();
		List<ActivityGood> currentGoods = activityGoodDao.query(new ActivityGood(null, null, activtyId));
		for (String goodId : vo.getGoodIds()) {
			ActivityGood e = new ActivityGood(null, goodId, activtyId);
			if (currentGoods.contains(e)) {
				continue;
			}
			e.setActGoodId(idGenerate.getKeyGenerate(MallFields.ACT_GOOD_ID));
			activityGoods.add(e);
		}
		if (activityGoods.size() > 0) {
			activityGoodDao.batchSave(activityGoods);
		}
		return VOUtil.genSuccessResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> delActivitysGoods(DelActivitysGoodsVo vo) {
		ActivityGood ag = new ActivityGood();
		ag.setActivityId(vo.getActivityId());
		ag.setIds(vo.getActGoodIds());
		activityGoodDao.batchDelByActivityIdAndGoodID(ag);
		Activity activity = JSONUtil.trans(vo, Activity.class);
		String currentTimeStr = DateTimeUtil.getCurrentDateString(DateTimeUtil.FORMAT_YYYYMMDDHHMMSS_NO_BREAK);
		String currentDate = currentTimeStr.substring(0, 8);
		String currentTime = currentTimeStr.substring(8);
		activity.setUpdateDate(currentDate);
		activity.setUpdateTime(currentTime);
		Object operatorName = getOPerator(vo);
		if (operatorName instanceof String) {
			activity.setUpdateUser((String) operatorName);
		} else {
			return (Map<String, Object>) operatorName;
		}
		update(activity);
		return VOUtil.genSuccessResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryActivitysGoods(QueryActivitysGoodsVo vo) {
		// 查询活动关联的所有商品编号
		List<String> activitysGoodsId = activityGoodDao.getActivitysGoodsId(vo.getActivityId());
		// 无商品直接返回
		if (activitysGoodsId.size() < 1) {
			Map<String, Object> result = VOUtil.genSuccessResult();
			result.put(BasicFields.RESULT_LIST, activitysGoodsId);// 设置商品列表
			result.put(BasicFields.TOTAL_NUM, 0);// 设置总条数
			return result;
		}
		// 组装参数
		Map<String, Object> params = JSONUtil.trans(vo, Map.class);
		params.remove(MallFields.LIMIT);
		params.remove(BasicFields.PAGE);
		if (StringUtil.isBlank(vo.getGoods_id())) {
			params.put(MallFields.IDS, activitysGoodsId);
		}
		List<SortMarker> sorts = new ArrayList<>();
		sorts.add(new SortMarker(MallFields.UPDATE_DATE, false));
		sorts.add(new SortMarker(MallFields.UPDATE_TIME, false));
		params.put(MallFields.SORTMARKERS, sorts);
		params.put(MallFields.SORT, 0);// 使用其他同学的底层 需要特殊处理
		Pagination page = new Pagination();
		page.setPage_no(vo.getPage());
		page.setPage_size(vo.getLimit());
		int countQueryGoods = goodsDao.countQueryGoods(params);
		page.setTotal_item_num(countQueryGoods);
		page.calc();
		params.put(BasicFields.PAGE, page);
		// 查询满足条件的所有商品
		List<Map<String, Object>> queryGoods = goodsDao.queryGoods(params);
		for (Map<String, Object> map : queryGoods) {
			String standard_ids = (String) map.get(MallFields.STANDARD_IDS);
			if (StringUtil.isNotBlank(standard_ids)) {
				List<String> standards = Arrays.asList(standard_ids.split(","));
				GoodsStandards goodsStandards = new GoodsStandards();
				goodsStandards.setIds(standards);
				List<Map<String, Object>> queryByIds = goodsStandardsDao.queryByIds(goodsStandards);
				for (Map<String, Object> t1 : queryByIds) {
					t1.put(MallFields.UNIT_NAME, map.get(MallFields.UNIT_NAME));
				}
				map.put(MallFields.STANDARDLIST, queryByIds);
			} else {
				map.put(MallFields.STANDARDLIST, new ArrayList<>());
			}
			Goods singleGoods = JSONUtil.trans(map, Goods.class);
			String[] bannerArray = singleGoods.getBanner_ids().split(",");
			List<Map<String, Object>> bannerList = new ArrayList<>();
			for (String id : bannerArray) {
				Map<String, Object> bannerMap = new HashMap<>();
				String picUrl = "";
				String picId = "";
				GoodsPic goodsPic = goodsPicDao.get(id);
				if (goodsPic != null && !StringUtils.isBlank(goodsPic.getPic_url())) {
					picUrl = goodsPic.getPic_url();
					picId = goodsPic.getPic_id();
				}
				bannerMap.put(MallFields.BANNER_URL, picUrl);
				bannerMap.put(MallFields.PIC_ID, picId);
				bannerList.add(bannerMap);
			}
			map.put("bannerList", bannerList);

			String[] detailArray = singleGoods.getDetail_ids().split(",");
			List<Map<String, Object>> detailList = new ArrayList<>();
			for (String id : detailArray) {
				Map<String, Object> detailMap = new HashMap<>();
				String picUrl = "";
				String picId = "";
				String picDesc = "";
				GoodsPic goodsPic = goodsPicDao.get(id);
				if (goodsPic != null && !StringUtils.isBlank(goodsPic.getPic_url())) {
					picUrl = goodsPic.getPic_url();
					picDesc = goodsPic.getPic_desc();
					picId = goodsPic.getPic_id();
				}
				detailMap.put(MallFields.DETAIL_URL, picUrl);
				detailMap.put(MallFields.PIC_ID, picId);
				detailMap.put(MallFields.PIC_DESC, picDesc);
				detailList.add(detailMap);
			}
			map.put("detailList", detailList);

			String[] infoArray = singleGoods.getInfo_ids().split(",");
			List<Map<String, Object>> infoList = new ArrayList<>();
			for (String id : infoArray) {
				Map<String, Object> infoMap = new HashMap<>();
				String infoContent = "";
				String infoTitle = "";
				String infoId = "";
				GoodsInfo goodsInfo = goodsInfoDao.get(id);
				if (goodsInfo != null && !StringUtils.isBlank(goodsInfo.getInfo_title())) {
					infoTitle = goodsInfo.getInfo_title();
					infoContent = goodsInfo.getInfo_content();
					infoId = goodsInfo.getInfo_id();
				}
				infoMap.put(MallFields.INFO_TITLE, infoTitle);
				infoMap.put(MallFields.INFO_ID, infoId);
				infoMap.put(MallFields.INFO_CONTENT, infoContent);
				infoList.add(infoMap);
			}
			map.put(MallFields.INFOLIST, infoList);
			map.put(MallFields.SORT, singleGoods.getSort());
		}
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, queryGoods);// 设置商品列表
		result.put(BasicFields.TOTAL_NUM, countQueryGoods);// 设置总条数
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryActivitysDetail(QueryActivityDetailVo vo) {
		String activityId = vo.getActivity_id();
		Map<String, Object> result = JSONUtil.trans(get(activityId), Map.class);
		result.put(MallFields.PRDT_LIST, activityGoodDao.getPrdtList(activityId));
		result.put(BasicFields.ERROR_NO, BasicFields.SUCCESS);
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryGoods(QueryGoods4ActivityVo queryGoodsVo) {
		Goods goods = new Goods();
		// 将queryGoodsVo转为goods
		transGoods(queryGoodsVo, goods);
		goods.setStatus(BasicFields.ENABLE);

		Map<String, Object> goodsMap = JSONUtil.trans(goods, Map.class);
		List<String> activitysGoodsId = activityGoodDao.getActivitysGoodsId(queryGoodsVo.getActivityId());
		if (activitysGoodsId.size() > 0) {
			goodsMap.put(MallFields.ADDEDGOODSIDS, activitysGoodsId);
		}
		goodsMap.put(MallFields.START_DATE, queryGoodsVo.getStart_date());
		goodsMap.put(MallFields.END_DATE, queryGoodsVo.getEnd_date());
		goodsMap.put(MallFields.UPDATE_START_DATE, queryGoodsVo.getUpdate_start_date());
		goodsMap.put(MallFields.UPDATE_END_DATE, queryGoodsVo.getUpdate_end_date());

		int total = activityGoodDao.countQueryGoods(goodsMap);
		// 添加分页和排序参数
		addSrotAndPageParam(queryGoodsVo, goods, total);
		goodsMap = JSONUtil.trans(goods, Map.class);
		goodsMap.put(MallFields.START_DATE, queryGoodsVo.getStart_date());
		goodsMap.put(MallFields.END_DATE, queryGoodsVo.getEnd_date());
		goodsMap.put(MallFields.UPDATE_START_DATE, queryGoodsVo.getUpdate_start_date());
		goodsMap.put(MallFields.UPDATE_END_DATE, queryGoodsVo.getUpdate_end_date());
		if (queryGoodsVo.getIds() != null && queryGoodsVo.getIds().size() > 0) {
			goodsMap.put("ids", queryGoodsVo.getIds());
		}
		if (activitysGoodsId.size() > 0) {
			goodsMap.put(MallFields.ADDEDGOODSIDS, activitysGoodsId);
		}
		List<Map<String, Object>> goodsList = activityGoodDao.queryGoods(goodsMap);

		List<Map<String, Object>> resultList = new ArrayList<>();
		// 给每个商品遍历添加类目名称
		ergodicGoods(goodsList, resultList);

		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, goodsList);
		result.put(BasicFields.TOTAL_NUM, total);
		return result;
	}

	private void transGoods(QueryGoods4ActivityVo queryGoodsVo, Goods goods) {
		try {
			BeanUtil.reflectionAttr(queryGoodsVo, goods);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addSrotAndPageParam(QueryGoods4ActivityVo queryGoodsVo, Goods goods, int total) {
		Pagination pagination = createPage(total, queryGoodsVo.getPage(), queryGoodsVo.getLimit());
		goods.setPage(pagination);

		SortMarker date = createMarker(false, MallFields.UPDATE_DATE);
		SortMarker time = createMarker(false, MallFields.UPDATE_TIME);
		List<SortMarker> list = new ArrayList<>();
		list.add(date);
		list.add(time);
		goods.setSortMarkers(list);
	}

	private void ergodicGoods(List<Map<String, Object>> goodsList, List<Map<String, Object>> resultList) {
		for (Map<String, Object> map : goodsList) {
			Goods singleGoods = JSONUtil.trans(map, Goods.class);
			// 获取商品规格
			GoodsStandards goodsStandards = new GoodsStandards();
			goodsStandards.setIds(Arrays.asList(singleGoods.getStandard_ids().split(",")));
			goodsStandards.setStatus(BasicFields.ENABLE);
			List<Map<String, Object>> standardList = goodsStandardsDao.queryByIds(goodsStandards);
			for (Map<String, Object> standard : standardList) {
				BigDecimal bd = (BigDecimal) standard.get(MallFields.PRICE);
				standard.put(MallFields.PRICE, bd.setScale(2).toString());
				standard.put(MallFields.MAX_SALE_NUM, standard.get(MallFields.MAX_SALE_NUM));
			}
			map.put("standardList", standardList);
			for (Map<String, Object> standMap : standardList) {
				standMap.put(MallFields.UNIT_NAME, singleGoods.getUnit_name());
			}

			String[] bannerArray = singleGoods.getBanner_ids().split(",");
			List<Map<String, Object>> bannerList = new ArrayList<>();
			for (String id : bannerArray) {
				Map<String, Object> bannerMap = new HashMap<>();
				String picUrl = "";
				String picId = "";
				GoodsPic goodsPic = goodsPicDao.get(id);
				if (goodsPic != null && !StringUtils.isBlank(goodsPic.getPic_url())) {
					picUrl = goodsPic.getPic_url();
					picId = goodsPic.getPic_id();
				}
				bannerMap.put(MallFields.BANNER_URL, picUrl);
				bannerMap.put(MallFields.PIC_ID, picId);
				bannerList.add(bannerMap);
			}
			map.put("bannerList", bannerList);

			String[] detailArray = singleGoods.getDetail_ids().split(",");
			List<Map<String, Object>> detailList = new ArrayList<>();
			for (String id : detailArray) {
				Map<String, Object> detailMap = new HashMap<>();
				String picUrl = "";
				String picId = "";
				String picDesc = "";
				GoodsPic goodsPic = goodsPicDao.get(id);
				if (goodsPic != null && !StringUtils.isBlank(goodsPic.getPic_url())) {
					picUrl = goodsPic.getPic_url();
					picDesc = goodsPic.getPic_desc();
					picId = goodsPic.getPic_id();
				}
				detailMap.put(MallFields.DETAIL_URL, picUrl);
				detailMap.put(MallFields.PIC_ID, picId);
				detailMap.put(MallFields.PIC_DESC, picDesc);
				detailList.add(detailMap);
			}
			map.put("detailList", detailList);

			String[] infoArray = singleGoods.getInfo_ids().split(",");
			List<Map<String, Object>> infoList = new ArrayList<>();
			for (String id : infoArray) {
				Map<String, Object> infoMap = new HashMap<>();
				String infoContent = "";
				String infoTitle = "";
				String infoId = "";
				GoodsInfo goodsInfo = goodsInfoDao.get(id);
				if (goodsInfo != null && !StringUtils.isBlank(goodsInfo.getInfo_title())) {
					infoTitle = goodsInfo.getInfo_title();
					infoContent = goodsInfo.getInfo_content();
					infoId = goodsInfo.getInfo_id();
				}
				infoMap.put(MallFields.INFO_TITLE, infoTitle);
				infoMap.put(MallFields.INFO_ID, infoId);
				infoMap.put(MallFields.INFO_CONTENT, infoContent);
				infoList.add(infoMap);
			}
			map.put(MallFields.INFOLIST, infoList);
			map.put(MallFields.SORT, singleGoods.getSort());

			resultList.add(map);
		}
	}

	private SortMarker createMarker(boolean asc, String field) {
		SortMarker sortMarker = new SortMarker();
		sortMarker.setAsc(asc);
		sortMarker.setField(field);
		return sortMarker;
	}

	private Pagination createPage(int total, int pageNum, int limit) {
		Pagination page = new Pagination();
		page.setPage_size(limit);
		page.setPage_no(pageNum);
		page.setTotal_item_num(total);
		page.calc();
		return page;
	}
}

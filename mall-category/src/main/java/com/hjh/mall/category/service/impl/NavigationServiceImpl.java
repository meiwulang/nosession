package com.hjh.mall.category.service.impl;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.json.JSON;
import com.hjh.mall.cache.cache.helper.CacheHelper;
import com.hjh.mall.cache.cache.sequence.KeyGenerate;
import com.hjh.mall.category.bizapi.bizserver.navigation.BizNavigationService;
import com.hjh.mall.category.bizapi.bizserver.navigation.vo.CreateNavigation;
import com.hjh.mall.category.bizapi.bizserver.navigation.vo.QueryFirstLevelNavigations;
import com.hjh.mall.category.bizapi.bizserver.navigation.vo.QueryNavigationsByLike;
import com.hjh.mall.category.bizapi.bizserver.navigation.vo.QueryNavigationsByparentId;
import com.hjh.mall.category.bizapi.bizserver.navigation.vo.QueryNavigationsForApp;
import com.hjh.mall.category.bizapi.bizserver.navigation.vo.QuerySecondLevelNavigations;
import com.hjh.mall.category.bizapi.bizserver.navigation.vo.QueryThirdLevelNavigations;
import com.hjh.mall.category.bizapi.bizserver.navigation.vo.UpdateNavigation;
import com.hjh.mall.category.bizapi.bizserver.navigation.vo.UpdateNavigationStatus;
import com.hjh.mall.category.bizapi.bizserver.navigation.vo.UpdatesingleNavigationStatus;
import com.hjh.mall.category.dao.NavigationDao;
import com.hjh.mall.category.entity.Navigation;
import com.hjh.mall.category.quartz.CategoryTask;
import com.hjh.mall.category.service.NavigationService;
import com.hjh.mall.category.service.base.HJYServiceImplBase;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.common.core.model.SortMarker;
import com.hjh.mall.common.core.util.DateTimeUtil;
import com.hjh.mall.common.core.util.JSONUtil;
import com.hjh.mall.common.core.util.PinyinUtil;
import com.hjh.mall.common.core.util.StringUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.field.constant.CacheKeys;
import com.hjh.mall.field.constant.MallFields;
import com.hjh.mall.field.error.MallErrorCode;
import com.hjh.mall.field.type.Status;
import com.hjh.mall.store.service.HjyStoreService;

/**
 * @Project: hjy-middle
 * @Description 元数据业务层
 * @author zfl
 * @date 2017年03月13日
 * @version V1.0
 */

@Service
public class NavigationServiceImpl extends HJYServiceImplBase<Navigation, String>
		implements NavigationService, BizNavigationService {
	private static final long serialVersionUID = 1L;
	@Resource
	private NavigationDao dao;
	@Resource
	private CategoryTask task;
	@Resource
	private KeyGenerate keyGenerate;
	@Resource
	private HjyStoreService hjyStoreService;
	@Resource
	private CacheHelper cache;
	private static String msgPpattern = "代码为{0}的类目存在启用的子类目";

	@Override
	protected DAOBase<Navigation, String> getDAO() {
		return dao;
	}

	@Override
	@Transactional
	public Map<String, Object> createNavigation(CreateNavigation vo) {
		Byte level = vo.getLevel();
		if (level == 3) {
			Boolean hotable = vo.isHotable();
			if (null != hotable && hotable) {

				if (!StringUtil.isNotBlank(vo.getHotIcon())) {
					return VOUtil.genErrorResult(MallErrorCode.NavigationErrorCode.HOT_IMG_CANNOT_NULL);
				}
				Integer hotSort = vo.getHotSort();
				if (hotSort == null || hotSort < 1) {
					return VOUtil.genErrorResult(MallErrorCode.NavigationErrorCode.HOT_SORT_CANNOT_NULL);
				}
				Navigation hotsortparm = new Navigation();
				hotsortparm.setHotSort(hotSort);
				hotsortparm.setLevel(new Byte("3"));
				if (query(hotsortparm).size() > 0) {
					return VOUtil.genErrorResult(MallErrorCode.NavigationErrorCode.HOT_SORT_UNIQUE);
				}
			}
			if (null == hotable || !hotable) {
				vo.setHotIcon("");
				vo.setHotSort(-1);
			}
		}
		boolean equals = vo.getFatherId().equals("0000000000000000");
		if (level > 1) {
			if (equals) {
				return VOUtil.genErrorResult(MallErrorCode.NavigationErrorCode.ERROR_FATHER);
			}
			Navigation father = new Navigation();
			father.setCategoryId(vo.getFatherId());
			father.setStatus(Status.ENABLED.getVal());
			if (dao.queryReturnMap(father).size() < 1) {
				return VOUtil.genErrorResult(MallErrorCode.NavigationErrorCode.ERROR_FATHER);
			}
		} else if (!equals) {
			return VOUtil.genErrorResult(MallErrorCode.NavigationErrorCode.ERROR_FATHER);
		}
		Navigation queryEntity = new Navigation(vo.getCategoryName());
		queryEntity.setFatherId(vo.getFatherId());
		if (dao.queryReturnMap(queryEntity).size() > 0)
			return VOUtil.genErrorResult(MallErrorCode.NavigationErrorCode.NAVIGATION_EXIST);
		queryEntity.setCategoryName(null);
		if (StringUtil.isNotBlank(vo.getNickName())) {
			queryEntity.setNickName(vo.getNickName());
			if (dao.queryReturnMap(queryEntity).size() > 0)
				return VOUtil.genErrorResult(MallErrorCode.NavigationErrorCode.NICK_EXIST);
		}
		queryEntity.setNickName(null);
		queryEntity.setFatherId(null);
		Integer sort = vo.getSort();
		if (sort != null && sort > 0) {
			queryEntity.setSort(sort);
			queryEntity.setLevel(level);
			queryEntity.setFatherId(vo.getFatherId());
			if (dao.queryReturnMap(queryEntity).size() > 0)
				return VOUtil.genErrorResult(MallErrorCode.NavigationErrorCode.SORT_EXIST);
		}
		Navigation entity = JSONUtil.trans(vo, Navigation.class);

		String currentFullTime = DateTimeUtil.getCurrentDateString(DateTimeUtil.FORMAT_YYYYMMDDHHMMSS_NO_BREAK);
		String currentDate = currentFullTime.substring(0, 8);
		String currentTime = currentFullTime.substring(8);
		String icon = vo.getIcon();

		entity.setCategoryId(keyGenerate.getKeyGenerate("navigation"));
		entity.setCreater(vo.getUpdater());
		entity.setInitDate(currentDate);
		entity.setInitTime(currentTime);
		entity.setUpdateDate(currentDate);
		entity.setUpdateTime(currentTime);
		entity.setStatus(Status.ENABLED.getVal());
		entity.setFirstChar(PinyinUtil.getPinYinHeadChar(vo.getCategoryName()));
		entity.initForClearNull();

		dao.save(entity);
		return VOUtil.genSuccessResult();
	}

	/**
	 *
	 */
	@Override
	public Map<String, Object> updateNavigation(UpdateNavigation vo) {
		Map<String, Object> currentNav = dao.getReturnMap(vo.getCategoryId());
		Boolean hotable = vo.isHotable();
		if (null != hotable && hotable) {
			if (!StringUtil.isNotBlank(vo.getHotIcon())) {
				return VOUtil.genErrorResult(MallErrorCode.NavigationErrorCode.HOT_IMG_CANNOT_NULL);
			}
			Integer hotSort = vo.getHotSort();
			if (hotSort == null || hotSort < 1) {
				return VOUtil.genErrorResult(MallErrorCode.NavigationErrorCode.HOT_SORT_CANNOT_NULL);
			}
			Navigation hotsortparm = new Navigation();
			hotsortparm.setHotSort(hotSort);
			hotsortparm.setLevel(new Byte("3"));
			List<Map<String, Object>> query = dao.queryReturnMap(hotsortparm);
			int size = query.size();
			if (size > 1 || (size == 1 && !query.get(0).get(MallFields.CATEGORY_ID).equals(vo.getCategoryId()))) {
				return VOUtil.genErrorResult(MallErrorCode.NavigationErrorCode.HOT_SORT_UNIQUE);
			}
		}
		if (null == hotable || !hotable) {
			vo.setHotIcon("");
			vo.setHotSort(-1);
		}
		Navigation queryEntity = new Navigation();
		String categoryName = vo.getCategoryName();
		queryEntity.setFatherId((String) currentNav.get(MallFields.FATHER_ID));
		if (StringUtil.isNotBlank(categoryName)) {
			queryEntity.setCategoryName(categoryName);
			List<Map<String, Object>> query = dao.queryReturnMap(queryEntity);
			if (query.size() > 1
					|| (query.size() == 1 && !query.get(0).get(MallFields.CATEGORY_ID).equals(vo.getCategoryId())))
				return VOUtil.genErrorResult(MallErrorCode.NavigationErrorCode.NAVIGATION_EXIST);
		}
		if (StringUtil.isNotBlank(vo.getNickName())) {
			queryEntity.setNickName(vo.getNickName());
			queryEntity.setCategoryName(null);
			List<Map<String, Object>> query = dao.queryReturnMap(queryEntity);
			if (query.size() > 1
					|| (query.size() == 1 && !query.get(0).get(MallFields.CATEGORY_ID).equals(vo.getCategoryId())))
				return VOUtil.genErrorResult(MallErrorCode.NavigationErrorCode.NICK_EXIST);
		}
		queryEntity.setFatherId(null);
		Navigation entity = JSONUtil.trans(vo, Navigation.class);

		Byte level = (byte) ((Integer) currentNav.get(MallFields.LEVEL)).intValue();

		Integer sort = vo.getSort();
		if (null != sort && sort > 0) {
			queryEntity.setNickName(null);
			queryEntity.setCategoryName(null);
			queryEntity.setSort(sort);
			queryEntity.setLevel(level);
			queryEntity.setFatherId(vo.getFatherId());
			List<Map<String, Object>> query = dao.queryReturnMap(queryEntity);
			if (query.size() > 1
					|| (query.size() == 1 && !query.get(0).get(MallFields.CATEGORY_ID).equals(vo.getCategoryId())))
				return VOUtil.genErrorResult(MallErrorCode.NavigationErrorCode.SORT_EXIST);
		}
		Integer hotSort = vo.getHotSort();
		if (null != hotSort && hotSort > 0) {
			queryEntity.setNickName(null);
			queryEntity.setCategoryName(null);
			queryEntity.setSort(null);
			queryEntity.setHotSort(hotSort);
			List<Map<String, Object>> query = dao.queryReturnMap(queryEntity);
			if (query.size() > 1
					|| (query.size() == 1 && !query.get(0).get(MallFields.CATEGORY_ID).equals(vo.getCategoryId())))
				return VOUtil.genErrorResult(MallErrorCode.NavigationErrorCode.HOT_SORT_EXIST);
		}
		updateOperatTime(entity);
		String fatherId = vo.getFatherId();
		if (level > 1) {
			if (null == fatherId || fatherId.equals("0000000000000000")) {
				return VOUtil.genErrorResult(MallErrorCode.NavigationErrorCode.ERROR_FATHER);
			}
			Navigation father = new Navigation();
			father.setCategoryId(fatherId);
			father.setStatus(Status.ENABLED.getVal());
			if (dao.queryReturnMap(father).size() < 1) {
				return VOUtil.genErrorResult(MallErrorCode.NavigationErrorCode.ERROR_FATHER);
			}
		} else if (null != fatherId && fatherId.equals("0000000000000000")) {
			return VOUtil.genErrorResult(MallErrorCode.NavigationErrorCode.ERROR_FATHER);
		}
		if (StringUtil.isNotBlank((String) currentNav.get(MallFields.HOT_ICON))
				&& StringUtil.isBlank(vo.getHotIcon())) {
			entity.setHotIcon("");
		}
		if ((int) currentNav.get(MallFields.SORT) > 0 && vo.getSort() < 0) {
			entity.setSort(-1);
		}
		if ((int) currentNav.get(MallFields.HOT_SORT) > 0 && vo.getHotSort() < 0) {
			entity.setHotSort(-1);
		}
		if (StringUtil.isNotBlank((String) currentNav.get(MallFields.HOT_ICON))
				&& StringUtil.isBlank(vo.getHotIcon())) {
			entity.setHotIcon("");
		}
		if (StringUtil.isBlank(vo.getNickName())) {
			entity.setNickName("");
		}
		if (!StringUtil.isBlank(categoryName)) {
			entity.setFirstChar(PinyinUtil.getPinYinHeadChar(categoryName));
		}
		update(entity);
		if (level.equals((byte) 3)) {
			task.doTask();
		}
		return VOUtil.genSuccessResult();
	}

	public Map<String, Object> updateNavigationStatus(UpdatesingleNavigationStatus vo) {
		Navigation father = new Navigation();
		String categoryId = vo.getCategoryId();
		father.setFatherId(categoryId);
		father.setStatus(Status.ENABLED.getVal());
		if (Status.DISENABLED.getVal().equals(vo.getStatus()) && dao.queryReturnMap(father).size() > 0) {
			return VOUtil.genErrorResult(MallErrorCode.NavigationErrorCode.HAS_ENABLE_CHILD,
					MessageFormat.format(msgPpattern, categoryId));
		}
		Navigation entity = JSONUtil.trans(vo, Navigation.class);
		updateOperatTime(entity);
		update(entity);
		return VOUtil.genSuccessResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> batchUpdateNavigationStatus(UpdateNavigationStatus vo) {
		String status = vo.getStatus();
		if (status.equals(Status.DISENABLED.getVal())) {
			for (String s : vo.getCategoryIds()) {
				if (dao.queryReturnMap(new Navigation(s, Status.ENABLED.getVal())).size() > 0) {
					return VOUtil.genErrorResult(MallErrorCode.NavigationErrorCode.HAS_ENABLE_CHILD,
							MessageFormat.format(msgPpattern, s));
				}
			}
		}
		Map<String, Object> entity = JSONUtil.trans(vo, Map.class);
		String currentFullTime = DateTimeUtil.getCurrentDateString(DateTimeUtil.FORMAT_YYYYMMDDHHMMSS_NO_BREAK);
		entity.put(MallFields.UPDATEDATE, currentFullTime.substring(0, 8));
		entity.put(MallFields.UPDATETIME, currentFullTime.substring(8));
		dao.batchUpdateStatus(entity);
		return VOUtil.genSuccessResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryFirstLevelNavigations(QueryFirstLevelNavigations vo) {
		vo.blankStringToNull();
		Map<String, Object> entity = JSONUtil.trans(vo, Map.class);
		entity.put(MallFields.LEVEL, 1);
		List<Map<String, Object>> resultList = dao.queryFirstLevelNavigations(entity);
		entity.remove("page");
		entity.remove("limit");
		int count = dao.countByLike(entity);
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.TOTAL_NUM, count);
		result.put(BasicFields.RESULT_LIST, resultList);

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> querySecondLevelNavigations(QuerySecondLevelNavigations vo) {
		vo.blankStringToNull();
		Map<String, Object> entity = JSONUtil.trans(vo, Map.class);
		List<Map<String, Object>> resultList = dao.querySecondLevelNavigations(entity);
		int count = dao.querySecondLevelCount(entity);
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.TOTAL_NUM, count);
		result.put(BasicFields.RESULT_LIST, resultList);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryThirdLevelNavigations(QueryThirdLevelNavigations vo) {
		vo.blankStringToNull();
		Map<String, Object> entity = JSONUtil.trans(vo, Map.class);
		List<Map<String, Object>> resultList = dao.queryThirdLevelNavigations(entity);
		int count = dao.queryThirdLevelCount(entity);
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.TOTAL_NUM, count);
		result.put(BasicFields.RESULT_LIST, resultList);
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> queryNavigationsForApp(QueryNavigationsForApp vo) {
		Map<String, Object> result = VOUtil.genSuccessResult();
		List<Map> resultList = new ArrayList<>();
		int level = vo.getLevel();
		String jsonString = cache.lget(CacheKeys.ALL_LEVEL_NAVIGATION, level);
		if (StringUtil.isNotBlank(jsonString)) {
			resultList = JSONUtil.trans2List(jsonString, Map.class);
		}
		int size = vo.getSize();
		boolean b = resultList == null;
		if (b || resultList.size() != size) {
			Map<String, Object> entity = JSONUtil.trans(vo, Map.class);
			if (level == 3) {
				entity.put("hotSort", 1);
			}
			resultList = dao.queryNavigationsForApp(entity);
			if (level == 2) {
				Map<String, Object> queryMap = VOUtil.genEmptyResult();
				queryMap.put(MallFields.LEVEL, 1);
				queryMap.put("size", Integer.MAX_VALUE);
				List<SortMarker> sortMarkers0 = new ArrayList<>();
				SortMarker sortMarker0 = new SortMarker();
				sortMarker0.setField(MallFields.SORT);
				sortMarkers0.add(sortMarker0);
				queryMap.put("sortMarkers", sortMarkers0);
				List<Map> firstNavs = dao.queryNavigationsForApp(queryMap);
				List<Map> secondNavs = new ArrayList<>();
				for (Map<String, Object> firstNav : firstNavs) {
					Navigation example = new Navigation((String) firstNav.get(MallFields.CATEGORY_ID), null);
					example.setLevel((byte) 2);
					List<SortMarker> sortMarkers = new ArrayList<>();
					SortMarker sortMarker = new SortMarker();
					sortMarker.setField(MallFields.SORT);
					SortMarker sortMarker1 = new SortMarker();
					sortMarker1.setField(BasicFields.INIT_DATE);
					SortMarker sortMarker2 = new SortMarker();
					sortMarker2.setField(BasicFields.INIT_TIME);
					sortMarkers.add(sortMarker);
					sortMarkers.add(sortMarker1);
					sortMarkers.add(sortMarker2);
					example.setSortMarkers(sortMarkers);
					Map<String, Object> secondEntity = (Map<String, Object>) JSONUtil.trans(example, Map.class);
					secondEntity.put("appDisplay", true);
					secondEntity.put("sortMarkers", sortMarkers);
					secondNavs.addAll(dao.queryByLike(secondEntity));
				}
				resultList = secondNavs;
			}
			JSONUtil.trans(resultList, String.class);
			lsetValues(resultList, level);
			result.put(BasicFields.RESULT_LIST, resultList);
			return result;
		}

		result.put(BasicFields.RESULT_LIST, resultList);
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Object> queryAllNavigationsForApp(HJYVO vo) {
		Map<String, Object> result = VOUtil.genSuccessResult();
		List<Map> resultList = new ArrayList<>();
		Map<String, Object> entity = JSONUtil.trans(vo, Map.class);
		resultList = dao.queryAllNavigationsForApp(entity);
		result.put(BasicFields.RESULT_LIST, resultList);
		return result;
	}

	/**
	 * @date 2017年3月16日
	 * @Description: 入缓存信息
	 * @author：王斌
	 * @param resultList
	 *            void
	 */
	private void lsetValues(@SuppressWarnings("rawtypes") List<Map> resultList, int key) {
		try {
			if (!cache.exists(CacheKeys.ALL_LEVEL_NAVIGATION)) {
				cache.lpush(CacheKeys.ALL_LEVEL_NAVIGATION, "", "", "", "");
			}
			cache.lset(CacheKeys.ALL_LEVEL_NAVIGATION, key, JSON.json(resultList));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @date 2017年3月14日
	 * @Description:修改操作时间
	 * @author：王斌
	 * @param entity
	 */
	private void updateOperatTime(Navigation entity) {
		String currentFullTime = DateTimeUtil.getCurrentDateString(DateTimeUtil.FORMAT_YYYYMMDDHHMMSS_NO_BREAK);
		String currentDate = currentFullTime.substring(0, 8);
		String currentTime = currentFullTime.substring(8);
		entity.setUpdateDate(currentDate);
		entity.setUpdateTime(currentTime);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryNavigationsByLike(QueryNavigationsByLike vo) {
		Map<String, Object> entity = JSONUtil.trans(vo, Map.class);
		entity.remove("limit");
		entity.remove("page");
		entity.put("appDisplay", true);
		SortMarker sortMarker = new SortMarker();
		sortMarker.setField(MallFields.LEVEL);
		sortMarker.setAsc(true);
		ArrayList<SortMarker> sortMarkers = new ArrayList<>();
		sortMarkers.add(sortMarker);
		SortMarker sortMarker1 = new SortMarker();
		sortMarker1.setField(MallFields.SORT);
		sortMarker1.setAsc(true);
		sortMarkers.add(sortMarker);
		entity.put("sortMarkers", sortMarkers);
		// entity.put(MallFields.STATUS, Status.ENABLED.getVal());
		List<Map<String, Object>> resultList = dao.queryByLike(entity);
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, resultList);
		return result;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> queryNavigationsByparentId(QueryNavigationsByparentId vo) {
		Map<String, Object> entity = JSONUtil.trans(vo, Map.class);
		entity.remove("limit");
		entity.remove("page");
		SortMarker sortMarker = new SortMarker();
		sortMarker.setField(MallFields.SORT);
		sortMarker.setAsc(true);
		ArrayList<SortMarker> sortMarkers = new ArrayList<>();
		sortMarkers.add(sortMarker);
		entity.put("sortMarkers", sortMarkers);
		String status = vo.getStatus();
		if (!StringUtil.isBlank(status)) {
			entity.put(MallFields.STATUS, status);
		}
		if (!vo.isWebUse()) {
			entity.put("appDisplay", true);
		}
		List<Map<String, Object>> resultList = dao.queryByLike(entity);
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, resultList);
		return result;
	}
}

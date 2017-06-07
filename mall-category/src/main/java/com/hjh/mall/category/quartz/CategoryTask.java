package com.hjh.mall.category.quartz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.json.JSON;
import com.hjh.mall.cache.cache.helper.CacheHelper;
import com.hjh.mall.category.bizapi.bizserver.metadata.BizMetadataService;
import com.hjh.mall.category.dao.NavigationDao;
import com.hjh.mall.category.entity.Navigation;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.model.SortMarker;
import com.hjh.mall.common.core.util.JSONUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.field.constant.CacheKeys;
import com.hjh.mall.field.constant.MallFields;

/**
 * @Project: mall-category
 * @Description TODO
 * @author 曾繁林
 * @date 2017年3月22日
 * @version V1.0
 */
@Component
public class CategoryTask extends SimpleLeaderTakeTask {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryTask.class);

	private boolean isTaskenable;

	@Resource
	private BizMetadataService bizMetadataService;
	@Resource
	private NavigationDao dao;
	@Resource
	private CacheHelper cache;

	public boolean isTaskenable() {
		return isTaskenable;
	}

	@Value("${task.category.execute.enable}")
	public void setTaskenable(boolean isTaskenable) {
		this.isTaskenable = isTaskenable;
	}

	@Override
	public void doTask() {
		LOGGER.info("基础数据定时更新开始！");
		// 元数据入缓存
		bizMetadataService.reloadAllMetadata();
		// 类目入缓存
		cache.destroy(CacheKeys.ALL_LEVEL_NAVIGATION);
		cache.lpush(CacheKeys.ALL_LEVEL_NAVIGATION, "", "", "", "");
		for (int i = 1; i < 4; i++) {
			lsetValues(i);
		}
		LOGGER.info("基础数据定时更新结束！");
	}

	@SuppressWarnings("rawtypes")
	private void lsetValues(int key) {
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put("level", key);
		entity.put("size", Integer.MAX_VALUE);
		if (key == 3) {
			entity.put("hotSort", 1);
		}
		List<Map> resultList = dao.queryNavigationsForApp(entity);
		if (key == 2) {
			Map<String, Object> queryMap = VOUtil.genEmptyResult();
			queryMap.put(MallFields.LEVEL, 1);
			queryMap.put("page", 0);
			queryMap.put("appDisplay", true);
			queryMap.put("limit", Integer.MAX_VALUE);
			List<SortMarker> sortMarkers0 = new ArrayList<>();
			SortMarker sortMarker0 = new SortMarker();
			sortMarker0.setField(MallFields.SORT);
			sortMarkers0.add(sortMarker0);
			queryMap.put("sortMarkers", sortMarkers0);
			List<Map<String, Object>> firstNavs = dao.queryFirstLevelNavigations(queryMap);
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

		try {
			cache.lset(CacheKeys.ALL_LEVEL_NAVIGATION, key, JSON.json(resultList));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

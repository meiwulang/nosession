package com.hjh.mall.category.dao;

import java.util.List;
import java.util.Map;

import com.hjh.mall.category.entity.Navigation;
import com.hjh.mall.common.core.dao.base.DAOBase;

public interface NavigationDao extends DAOBase<Navigation, String> {
	List<Map<String, Object>> queryByLike(Map<String, Object> example);

	int countByLike(Map<String, Object> example);

	Map<String, Object> getReturnMap(String key);

	List<Map<String, Object>> queryFirstLevelNavigations(Map<String, Object> example);

	List<Map<String, Object>> queryReturnMap(Navigation example);

	List<Map<String, Object>> querySecondLevelNavigations(Map<String, Object> example);

	int querySecondLevelCount(Map<String, Object> example);

	List<Map<String, Object>> queryThirdLevelNavigations(Map<String, Object> example);

	int queryThirdLevelCount(Map<String, Object> example);

	@SuppressWarnings("rawtypes")
	List<Map> queryNavigationsForApp(Map<String, Object> example);

	@SuppressWarnings("rawtypes")
	List<Map> queryAllNavigationsForApp(Map<String, Object> example);

	int batchUpdateStatus(Map<String, Object> example);
}
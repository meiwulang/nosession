package com.hjh.mall.bizapi.biz.goods.middle.dao;

import java.util.List;
import java.util.Map;

import com.hjh.mall.bizapi.biz.goods.middle.entity.Activity;
import com.hjh.mall.common.core.dao.base.DAOBase;

public interface ActivityDao extends DAOBase<Activity, String> {
	List<Map<String, Object>> queryByLike(Map<String, Object> example);

	int countByLike(Map<String, Object> example);
}
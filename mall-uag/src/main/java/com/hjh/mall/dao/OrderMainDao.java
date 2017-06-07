package com.hjh.mall.dao;

import java.util.List;
import java.util.Map;

import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.entity.OrderMain;

/**
 * @Project: hjh-mall
 * @Description 订单业务层
 * @author 王斌
 * @date 2017年02月20日
 * @version V1.0
 */
public interface OrderMainDao extends DAOBase<OrderMain, String> {
	/**
	 * @date 2017年2月20日
	 * @Description: 条件模糊查询订单列表
	 * @author：王斌
	 * @param queryParams
	 * @return List<OrderMain>
	 */
	List<Map<String, Object>> queryByLike(Map<String, Object> queryParams);

	/**
	 * @date 2017年2月20日
	 * @Description: 条件模糊查询订单总数
	 * @author：王斌
	 * @param queryParams
	 * @return int
	 */
	int countByLike(Map<?, ?> queryParams);

	/**
	 * @date 2017年2月26日
	 * @Description: app分页查询订单
	 * @author：曾繁林
	 * @param orderMain
	 * @return List
	 */
	List<OrderMain> queryMinePaged(OrderMain orderMain);
}
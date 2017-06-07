package com.hjh.mall.dao;

import java.util.List;
import java.util.Map;

import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.entity.OrderDetail;

/**
 * @Project: hjh-mall
 * @Description 订单业务层
 * @author 王斌
 * @date 2017年02月20日
 * @version V1.0
 */
public interface OrderDetailDao extends DAOBase<OrderDetail, String> {
	void batchSave(List<OrderDetail> list);

	List<Map<String, Object>> queryDetailsByIn(OrderDetail orderDetail);
}
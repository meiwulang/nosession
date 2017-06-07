package com.hjh.mall.service;

import java.io.OutputStream;
import java.util.Map;

import com.hjh.mall.common.core.service.base.ServiceBase;
import com.hjh.mall.common.core.vo.AppPagedQueryVO;
import com.hjh.mall.entity.OrderMain;
import com.hjh.mall.vo.CreateOrderVo;
import com.hjh.mall.vo.QueryOrdersVo;

/**
 * @Project: hjh-mall
 * @Description 订单业务层
 * @author 王斌
 * @date 2017年02月20日
 * @version V1.0
 */
public interface OrderMainService extends ServiceBase<OrderMain, String> {
	Map<String, Object> createOrder(CreateOrderVo vo);

	Map<String, Object> queryOrder(QueryOrdersVo vo);

	OutputStream exportOrder(QueryOrdersVo vo, OutputStream opts);

	Map<String, Object> getClientsOrders(AppPagedQueryVO appPagedQueryVO);
}

package com.hjh.mall.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.dao.OrderDetailDao;
import com.hjh.mall.entity.OrderDetail;
import com.hjh.mall.service.OrderDetailService;
import com.hjh.mall.service.base.HJYServiceImplBase;

/**
 * @Project: hjh-mall
 * @Description 订单业务层
 * @author 王斌
 * @date 2017年02月20日
 * @version V1.0
 */

@Service
public class OrderDetailServiceImpl extends HJYServiceImplBase<OrderDetail, String>
		implements
			OrderDetailService {
	@Resource
	private OrderDetailDao orderDetailDao;
	@Override
	protected DAOBase<OrderDetail, String> getDAO() {
		return orderDetailDao;
	}

}

package com.hjh.mall.order.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.evun.sweet.framework.core.mvc.BusinessException;

import com.hjh.mall.order.constants.Errors;
import com.hjh.mall.order.constants.OrderStatus;
//import com.alibaba.fastjson.JSON;
import com.hjh.mall.order.dao.LogisticsDao;
import com.hjh.mall.order.dao.OrderDao;
import com.hjh.mall.order.dao.OrderOperationLogDao;
import com.hjh.mall.order.dto.Logistics;
import com.hjh.mall.order.model.LogisticsDomain;
import com.hjh.mall.order.model.OrderDomain;
import com.hjh.mall.order.service.LogisticsService;
import com.hjh.mall.order.vo.AddLogisticsVo;
import com.hjh.mall.order.vo.GetLogisticsVo;

/**
 * Created by qiuxianxiang on 17/5/16.
 */
@Service("logisticsService")
public class LogisticsServiceImpl implements LogisticsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(LogisticsServiceImpl.class);
	@Autowired
	private LogisticsDao logisticsDao;

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private OrderOperationLogDao orderOperationLogDao;

	@Override
	public Logistics getLogistics(GetLogisticsVo getLogisticsVo) {
		LogisticsDomain logisticsDomain = logisticsDao.getLogisticsByOrderId(getLogisticsVo.getOrderId());
		// Logistics logistics =
		// JSON.parseObject(JSON.toJSONString(logisticsDomain),
		// Logistics.class);
		Logistics logistics = new Logistics();
		BeanUtils.copyProperties(logisticsDomain, logistics);

		return logistics;
	}

	@Override
	public void addLogistics(AddLogisticsVo addLogisticsVo) {
		OrderDomain order = orderDao.get(addLogisticsVo.getOrderId());
		OrderStatus orderStatus = OrderStatus.getEnum(order.getOrderStatus());
		if (OrderStatus.UN_DELIVERY != orderStatus) {
			LOGGER.error("order stauts is error orderId is "+addLogisticsVo.getOrderId());
			throw BusinessException.withErrorCode(Errors.Order.ORDER_STATUS_ERROR);
		}

		LogisticsDomain logisticsDomain = new LogisticsDomain();
		BeanUtils.copyProperties(addLogisticsVo, logisticsDomain);
		// LogisticsDomain logisticsDomain =
		// JSON.parseObject(JSON.toJSONString(addLogisticsVo),
		// LogisticsDomain.class);
		logisticsDomain.setCreateDate(new Date());

		logisticsDao.insert(logisticsDomain);

		// 记录操作日志
		// OrderOperationLogDomain orderOperationLogDomain = new
		// OrderOperationLogDomain();
		// orderOperationLogDomain.setOrderId(addLogisticsVo.getOrderId());
		// orderOperationLogDomain.setOrderPreStatus(OrderStatus.UN_DELIVERY.getCode());
		// orderOperationLogDomain.setOrderStatus(OrderStatus.DELIVERED.getCode());
		// orderOperationLogDomain.setOperationMsg("发货时间");
		// orderOperationLogDao.insert(orderOperationLogDomain);

		// 定单发货时间修改

	}

	@Override
	public void deleteLogistics(String logisticsId) {
		logisticsDao.delete(logisticsId);
	}

	@Override
	public void updateLogistics(String logisticsId, String logisticsNo, String logisticsNoteSnapshot,
			String senderName, String senderMobile, String logisticsCompany, Date deliveryDate, String createUserId,
			String createUserName) {
		LogisticsDomain logisticsDomain = new LogisticsDomain();
		logisticsDomain.setLogisticsId(logisticsId);
		logisticsDomain.setCreateUserId(createUserId);
		logisticsDomain.setLogisticsNo(logisticsNo);
		logisticsDomain.setLogisticsNoteSnapshot(logisticsNoteSnapshot);
		logisticsDomain.setSenderName(senderName);
		logisticsDomain.setSenderMobile(senderMobile);
		logisticsDomain.setLogisticsCompany(logisticsCompany);
		logisticsDomain.setDeliveryDate(deliveryDate);
		logisticsDomain.setCreateUserName(createUserName);

		logisticsDao.update(logisticsDomain);

	}

}

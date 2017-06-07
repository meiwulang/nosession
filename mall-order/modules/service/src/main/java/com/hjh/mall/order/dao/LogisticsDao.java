package com.hjh.mall.order.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hjh.mall.order.model.LogisticsDomain;

/**
 * @Project: mall-logistics-service
 * @Description 物流的dao
 * @author 杨益桦
 * @date 2017年5月24日
 * @version V1.0 
 */
@Mapper
public interface LogisticsDao {
    int delete(String logisticsId);

    int insert(LogisticsDomain logisticsDomain);

    LogisticsDomain get(String logisticsId);

    LogisticsDomain getLogisticsByOrderId(@Param("orderId")String orderId);

    int update(LogisticsDomain logisticsDomain);

    List<LogisticsDomain> query(LogisticsDomain logisticsDomain);
}
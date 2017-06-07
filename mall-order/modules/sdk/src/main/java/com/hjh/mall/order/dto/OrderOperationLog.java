package com.hjh.mall.order.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by qiuxianxiang on 17/5/23.
 */
public class OrderOperationLog implements java.io.Serializable {

    private static final long serialVersionUID = 12739127391L;

    /**
     * 主键
     */
    private Integer id;
    /**
     * 订单主键
     */
    private String orderId;
    /**
     * 订单操作前状态
     */
    private Integer orderPreStatus;
    /**
     * 订单当前状态
     */
    private Integer orderStatus;
    /**
     * 操作描述
     */
    private String operationMsg;
    /**
     * 创建时间
     */
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderPreStatus() {
        return orderPreStatus;
    }

    public void setOrderPreStatus(Integer orderPreStatus) {
        this.orderPreStatus = orderPreStatus;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOperationMsg() {
        return operationMsg;
    }

    public void setOperationMsg(String operationMsg) {
        this.operationMsg = operationMsg;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}

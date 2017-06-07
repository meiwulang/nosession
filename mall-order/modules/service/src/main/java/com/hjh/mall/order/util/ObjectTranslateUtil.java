package com.hjh.mall.order.util;

import com.hjh.mall.order.dto.*;
import com.hjh.mall.order.model.OrderDomain;
import com.hjh.mall.order.model.OrderItemDomain;
import com.hjh.mall.order.model.OrderOperationLogDomain;
import com.hjh.mall.order.model.OrderPaymentProofDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

/**
 * Created by qiuxianxiang on 17/5/12.
 */
public class ObjectTranslateUtil {

    private static final Logger logger = LoggerFactory.getLogger(ObjectTranslateUtil.class);

    private ObjectTranslateUtil() {
    }

    public static Order translateOrderDomainToDto(OrderDomain orderDomain, Order order) {
        if (orderDomain != null && order != null) {

            BeanUtils.copyProperties(orderDomain,order);

           /* order.setOrderId(orderDomain.getOrderId());
            order.setOrderNo(orderDomain.getOrderNo());
            order.setOrderStatus(orderDomain.getOrderStatus());
            order.setUserId(orderDomain.getUserId());
            order.setTransactionType(orderDomain.getTransactionType());
            order.setTransactionAmount(orderDomain.getTransactionAmount());
            order.setTransactionActualPayAmount(orderDomain.getTransactionActualPayAmount());
            order.setTransactionDeposit(orderDomain.getTransactionDeposit());
            order.setTransactionActualPayBalance(orderDomain.getTransactionActualPayBalance());
            order.setPostage(orderDomain.getPostage());
            order.setPayDate(orderDomain.getPayDate());
            order.setCreatedDate(orderDomain.getCreatedDate());

            order.setConsigneeName(orderDomain.getConsigneeName());
            order.setConsigneeMobile(orderDomain.getConsigneeMobile());
            order.setConsigneeTelephone(orderDomain.getConsigneeTelephone());
            order.setConsigneeAddress(orderDomain.getConsigneeAddress());
            order.setConsigneeCity(orderDomain.getConsigneeCity());
            order.setConsigneeDistrict(orderDomain.getConsigneeDistrict());
            order.setBuyerComments(orderDomain.getBuyerComments());
*/

        }

        return order;
    }


    public static OrderItem translateOrderItemDomainToDto(OrderItemDomain orderItemDomain, OrderItem orderItem) {
        if (orderItemDomain != null && orderItem != null) {
            BeanUtils.copyProperties(orderItemDomain,orderItem);
        }

        return orderItem;
    }



    public static OrderPaymentProof translateOrderPaymentProofDomainToDto(OrderPaymentProofDomain orderPaymentProofDomain, OrderPaymentProof orderPaymentProof) {
        if (orderPaymentProofDomain != null && orderPaymentProof != null) {
            BeanUtils.copyProperties(orderPaymentProofDomain,orderPaymentProof);
        }

        return orderPaymentProof;
    }

    public static OrderOperationLog translateOrderOperationLogDomainToDto(OrderOperationLogDomain orderOperationLogDomain, OrderOperationLog orderOperationLog) {
        if (orderOperationLogDomain != null && orderOperationLog != null) {
            BeanUtils.copyProperties(orderOperationLogDomain,orderOperationLog);
        }

        return orderOperationLog;
    }


    public static Product translateOrderItemToProduct(OrderItem orderItem, Product product) {
        if (orderItem != null && product != null) {
            BeanUtils.copyProperties(orderItem,product);
        }

        return product;
    }



}

package com.hjh.mall.order.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.evun.sweet.framework.core.mvc.BusinessException;

import com.hjh.mall.order.constants.Errors;
import com.hjh.mall.order.constants.OrderStatus;
import com.hjh.mall.order.constants.TransactionType;
import com.hjh.mall.order.dao.OrderDao;
import com.hjh.mall.order.dao.OrderOperationLogDao;
import com.hjh.mall.order.model.OrderDomain;
import com.hjh.mall.order.model.OrderOperationLogDomain;
import com.hjh.mall.order.service.OrderStatusService;
import com.hjh.mall.order.util.OrderUtil;
import org.springframework.util.StringUtils;

/**
 * Created by qiuxianxiang on 17/5/12.
 */
@Service("orderStatusService")
public class OrderStatusServiceImpl implements OrderStatusService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderOperationLogDao orderOperationLogDao;

    @Override
    public boolean isPostageCalculation(String orderId,String userId) {
        // 0.
        OrderUtil.checkIsUserOrder(orderDao,userId,orderId);

        BigDecimal orderPostage = orderDao.getOrderPostage(orderId);
//        orderDao.get(orderId).getPostage();

        //该业务逻辑认为,如果该 邮费没有设置,则认为 订单邮费没有被审核
        if (orderPostage == null) {
            return false;
        }

        return true;
    }

    @Override
    @Transactional
    public void payTransactionDeposit(String orderId, Double deposit,String userId) {
        // 0.
        OrderUtil.checkIsUserOrder(orderDao,userId,orderId);

        // 1. 更新订单状态
        updateOrderToPaidPart(orderId);

        // 2. 更新
        orderDao.updateOrderTransactionActualPayAmount(orderId,deposit);

        // 3. 记录日志

        OrderOperationLogDomain orderOperationLogDomain = new OrderOperationLogDomain();
        orderOperationLogDomain.setOrderId(orderId);
        orderOperationLogDomain.setOrderPreStatus(OrderStatus.NON_PAYMENT.getCode());
        orderOperationLogDomain.setOrderStatus(OrderStatus.PART_PAID.getCode());
        //orderOperationLogDomain.setOperationMsg("支付订单定金:"+ deposit);
        orderOperationLogDomain.setOperationMsg("定金支付时间");

        orderOperationLogDao.insert(orderOperationLogDomain);


    }

    @Override
    @Transactional
    public void payTransactionBalance(String orderId, Double balance,String userId) {
        // 0.
        OrderUtil.checkIsUserOrder(orderDao,userId,orderId);


        // 0.0   检查订单支付类型   检查订单是否为 [待付款] 或 [预付款]
        OrderDomain order = orderDao.get(orderId);
        Integer transactionType = order.getTransactionType();
        Integer orderStatus = order.getOrderStatus();
        OrderUtil.canPayTransactionBalance(TransactionType.getEnum(transactionType),OrderStatus.getEnum(orderStatus));

        // 0.0.0  检查余款延期时间[必须过了该时间后才能支付]
        Date balanceProofDate = order.getBalanceProofDate();  // 上传支付凭证时间
        int balanceDateCount = order.getBalanceDateCount();   // 余款延期时间
        OrderUtil.isTimeToPayTransactionBalance(balanceProofDate,balanceDateCount);


        // 1. 更新订单状态
        updateOrderToUnDelivery(orderId);

        // 2.  更新
        orderDao.updateOrderTransactionActualPayBalance(orderId,balance);


        // 3. 暂时不记录日志
    }

    @Override
    @Transactional
    public void payTransactionAmount(String orderId, Double amount,String userId) {
        // 0.
        OrderUtil.checkIsUserOrder(orderDao,userId,orderId);

        // 0.0   检查订单支付类型   检查订单是否为 [待付款] 或 [预付款]
        OrderDomain order = orderDao.get(orderId);
        Integer transactionType = order.getTransactionType();
        Integer orderStatus = order.getOrderStatus();
        OrderUtil.canPayTransactionAmount(TransactionType.getEnum(transactionType),OrderStatus.getEnum(orderStatus));

        //  0.1  TODO 检查支付金额是否匹配


        // 1. 更新订单状态
        updateOrderToUnDelivery(orderId);

        // 2.  更新
        orderDao.updateOrderTransactionActualPayAmount(orderId,amount);

        // 3. 记录日志
        OrderOperationLogDomain orderOperationLogDomain = new OrderOperationLogDomain();
        orderOperationLogDomain.setOrderId(orderId);
        orderOperationLogDomain.setOrderPreStatus(OrderStatus.NON_PAYMENT.getCode());
        orderOperationLogDomain.setOrderStatus(OrderStatus.UN_DELIVERY.getCode());
//        orderOperationLogDomain.setOperationMsg("支付订单额:"+ amount);
        orderOperationLogDomain.setOperationMsg("付款时间");

        orderOperationLogDao.insert(orderOperationLogDomain);
    }

    @Override
    @Transactional
    public void toShippedProduct(String orderId,String userId) {
        // 0.
        OrderUtil.checkIsUserOrder(orderDao,userId,orderId);

        // 1. 更新订单状态,修改发货时间
        updateOrderToDelivered(orderId);


        // 2. 记录日志
        OrderOperationLogDomain orderOperationLogDomain = new OrderOperationLogDomain();
        orderOperationLogDomain.setOrderId(orderId);
        orderOperationLogDomain.setOrderPreStatus(OrderStatus.UN_DELIVERY.getCode());
        orderOperationLogDomain.setOrderStatus(OrderStatus.DELIVERED.getCode());
//        orderOperationLogDomain.setOperationMsg("卖家已发货,买家未收到货物");
        orderOperationLogDomain.setOperationMsg("发货时间");

        orderOperationLogDao.insert(orderOperationLogDomain);

    }

    @Override
    @Transactional
    public void toUserReceived(String orderId,String userId) {
        // 0.
        OrderUtil.checkIsUserOrder(orderDao,userId,orderId);

        // 1. 更新订单状态
        updateOrderToReceived(orderId);

        // 2. 记录日志    记录: 目前商城并没有确认收货操作, 故而暂时不记录该操作
//        OrderOperationLogDomain orderOperationLogDomain = new OrderOperationLogDomain();
//        orderOperationLogDomain.setOrderId(orderId);
//        orderOperationLogDomain.setOrderPreStatus(OrderStatus.DELIVERED.getCode());
//        orderOperationLogDomain.setOrderStatus(OrderStatus.RECEIVED.getCode());
//        orderOperationLogDomain.setOperationMsg("已确认收货");
//        orderOperationLogDomain.setOperationMsg("确认收货时间");
//        orderOperationLogDao.insert(orderOperationLogDomain);

        // 记录: 目前商城并没有确认收货操作,故而后端 自动确认收获
        toOrderSuccess(orderId,userId);

    }

    @Override
    @Transactional
    public void toOrderSuccess(String orderId,String userId) {
        // 0.
        OrderUtil.checkIsUserOrder(orderDao,userId,orderId);

        // 1.更新订单状态
        updateOrderToSuccess(orderId);

        // 2. 记录日志
        OrderOperationLogDomain orderOperationLogDomain = new OrderOperationLogDomain();
        orderOperationLogDomain.setOrderId(orderId);
        orderOperationLogDomain.setOrderPreStatus(OrderStatus.RECEIVED.getCode());
        orderOperationLogDomain.setOrderStatus(OrderStatus.SUCCESS.getCode());
        orderOperationLogDomain.setOperationMsg("订单完成时间");

        orderOperationLogDao.insert(orderOperationLogDomain);
    }



    @Override
    @Transactional
    public void cancelOrder(String orderId, String userId, boolean isCustomer, String cancelReason) {
        // 0.
        OrderUtil.checkIsUserOrder(orderDao,userId,orderId);

        // 1.
        updateOrderToCancel(orderId,isCustomer);

        // 2. 记录订单取消原因
        if (! StringUtils.isEmpty(cancelReason)) {
            orderDao.updateOrderRemark(orderId,cancelReason);
        }


    }

    @Override
    @Transactional
    public void refundOrder(String orderId, String userId, boolean isCustomer, String cancelReason) {
        // 0.
        OrderUtil.checkIsUserOrder(orderDao,userId,orderId);

        updateOrderToRefunding(orderId);

        // 2. 记录订单取消原因
        if (! StringUtils.isEmpty(cancelReason)) {
            orderDao.updateOrderRemark(orderId,cancelReason);
        }

    }

    @Override
    @Transactional
    public void undoRefundOrder(String orderId, String userId) {
        // 0.
        OrderUtil.checkIsUserOrder(orderDao,userId,orderId);

        // 1.
        undoRefundOrderInner(orderId);

    }

    @Override
    @Transactional
    public void refundOrderComplete(String orderId) {

        // 1.检查订单状态是否为 [退款中],否则抛出异常
        OrderDomain order = orderDao.get(orderId);
        OrderStatus orderStatus = OrderStatus.getEnum(order.getOrderStatus());
        if (OrderStatus.REFUNDING != orderStatus) {
            throw BusinessException.withErrorCode(Errors.Order.ORDER_STATUS_ERROR);
        }

        int status = OrderStatus.CLOSED_REFUNDED.getCode();
        orderDao.updateOrderToTerminal(orderId,status,new Date());


        // 2. 记录日志
        OrderOperationLogDomain orderOperationLogDomain = new OrderOperationLogDomain();
        orderOperationLogDomain.setOrderId(orderId);
        orderOperationLogDomain.setOrderPreStatus(OrderStatus.REFUNDING.getCode());
        orderOperationLogDomain.setOrderStatus(OrderStatus.CLOSED_REFUNDED.getCode());
        orderOperationLogDomain.setOperationMsg("退款完成时间");

        orderOperationLogDao.insert(orderOperationLogDomain);
    }

    @Override
    @Transactional
    public void returnProducts(String orderId, String userId, String cancelReason) {
        // 0.
        OrderUtil.checkIsUserOrder(orderDao,userId,orderId);

        // 1.
        updateOrderToRefundingGoods(orderId);

        // 2. 记录订单取消原因
        if (! StringUtils.isEmpty(cancelReason)) {
            orderDao.updateOrderRemark(orderId,cancelReason);
        }

    }

    @Override
    @Transactional
    public void returnProductsComplete(String orderId) {

        // 1.检查订单状态是否为 [退货中],否则抛出异常
        OrderDomain order = orderDao.get(orderId);
        OrderStatus orderStatus = OrderStatus.getEnum(order.getOrderStatus());
        if (OrderStatus.RETURNING_GOODS != orderStatus) {
            throw BusinessException.withErrorCode(Errors.Order.ORDER_STATUS_ERROR);
        }

        int status = OrderStatus.CLOSED_RETURN_GOODS.getCode();
        orderDao.updateOrderStatus(orderId,status);

        // 2. 记录日志
        OrderOperationLogDomain orderOperationLogDomain = new OrderOperationLogDomain();
        orderOperationLogDomain.setOrderId(orderId);
        orderOperationLogDomain.setOrderPreStatus(OrderStatus.RETURNING_GOODS.getCode());
        orderOperationLogDomain.setOrderStatus(OrderStatus.CLOSED_RETURN_GOODS.getCode());
        orderOperationLogDomain.setOperationMsg("退货完成时间");

        orderOperationLogDao.insert(orderOperationLogDomain);
    }


    /**
     * 修改订单状态为  [预付款]
     * @param orderId
     */
    private void updateOrderToPaidPart(String orderId) {
        OrderDomain order = orderDao.get(orderId);
        //  1. 检查订单支付类型  否者抛出异常
        //  2. 检查订单状态 是否为[待付款], 否者抛出异常
        Integer transactionType = order.getTransactionType();
        Integer orderStatus = order.getOrderStatus();
        OrderUtil.isOrderCanPayDeposit(TransactionType.getEnum(transactionType),OrderStatus.getEnum(orderStatus));


        int status = OrderStatus.PART_PAID.getCode();
        orderDao.updateOrderStatus(orderId,status);
    }

    /**
     * 修改订单状态为   [等待卖家发货,买家已付款]
      * @param orderId
     */
    private void updateOrderToUnDelivery(String orderId) {

        int status = OrderStatus.UN_DELIVERY.getCode();
        orderDao.updateOrderToUnDelivery(orderId,status,new Date());
    }


    /**
     * 修改订单状态为   [卖家已发货,卖家已发货]
     * @param orderId
     */
    private void updateOrderToDelivered(String orderId) {
        // 1. 检查订单状态是否为 [等待卖家发货],否则抛出异常
        OrderDomain order = orderDao.get(orderId);
        OrderStatus orderStatus = OrderStatus.getEnum(order.getOrderStatus());
        if (OrderStatus.UN_DELIVERY != orderStatus) {
            throw BusinessException.withErrorCode(Errors.Order.ORDER_STATUS_ERROR);
        }


        int status = OrderStatus.DELIVERED.getCode();
        orderDao.updateDeliveryTime(orderId,status,new Date());
    }


    /**
     * 修改订单状态为   [已确认收货]
     * @param orderId
     */
    private void updateOrderToReceived(String orderId) {
        // 1. 检查订单状态是否为 [卖家已发货],否则抛出异常
        OrderDomain order = orderDao.get(orderId);
        OrderStatus orderStatus = OrderStatus.getEnum(order.getOrderStatus());
        if (OrderStatus.DELIVERED != orderStatus) {
            throw BusinessException.withErrorCode(Errors.Order.ORDER_STATUS_ERROR);
        }

        int status = OrderStatus.RECEIVED.getCode();
        orderDao.updateOrderStatus(orderId,status);
    }


    /**
     * 修改订单状态为   [交易成功]
     * @param orderId
     */
    private void updateOrderToSuccess(String orderId) {
        // 1.检查订单状态是否为 [已确认收货],否则抛出异常
        OrderDomain order = orderDao.get(orderId);
        OrderStatus orderStatus = OrderStatus.getEnum(order.getOrderStatus());
        if (OrderStatus.RECEIVED != orderStatus) {
            throw BusinessException.withErrorCode(Errors.Order.ORDER_STATUS_ERROR);
        }


        int status = OrderStatus.SUCCESS.getCode();
        orderDao.updateOrderStatus(orderId,status);

    }


    /**
     * 修改订单状态为   [取消订单]
     * @param orderId
     */
    private void updateOrderToCancel(String orderId,boolean isCustomer) {
        if (isCustomer) {

            updateOrderToCancelByCustomer(orderId);

        } else {

            updateOrderToCancelByManager(orderId);
        }


    }

    private void updateOrderToCancelByCustomer(String orderId) {

        // 1.检查订单状态是否为 [待付款],否则抛出异常
        OrderDomain order = orderDao.get(orderId);
        OrderStatus orderStatus = OrderStatus.getEnum(order.getOrderStatus());
        if (OrderStatus.NON_PAYMENT != orderStatus) {
            throw BusinessException.withErrorCode(Errors.Order.ORDER_STATUS_ERROR);
        }

        int status = OrderStatus.CLOSED_BUYER_CANCEL.getCode();
        orderDao.updateOrderToCancel(orderId,status,new Date());


        // 2. 记录日志
        OrderOperationLogDomain orderOperationLogDomain = new OrderOperationLogDomain();
        orderOperationLogDomain.setOrderId(orderId);
        orderOperationLogDomain.setOrderPreStatus(OrderStatus.NON_PAYMENT.getCode());
        orderOperationLogDomain.setOrderStatus(OrderStatus.CLOSED_BUYER_CANCEL.getCode());
        orderOperationLogDomain.setOperationMsg("订单取消时间");

        orderOperationLogDao.insert(orderOperationLogDomain);

    }

    private void updateOrderToCancelByManager(String orderId) {

        // 1.检查订单状态是否为 [已确认收货],否则抛出异常
        OrderDomain order = orderDao.get(orderId);
        OrderStatus orderStatus = OrderStatus.getEnum(order.getOrderStatus());
        if (OrderStatus.NON_PAYMENT != orderStatus) {
            throw BusinessException.withErrorCode(Errors.Order.ORDER_STATUS_ERROR);
        }

        int status = OrderStatus.CLOSED_SELLER_CANCEL.getCode();
        orderDao.updateOrderToCancel(orderId,status,new Date());



        // 2. 记录日志
        OrderOperationLogDomain orderOperationLogDomain = new OrderOperationLogDomain();
        orderOperationLogDomain.setOrderId(orderId);
        orderOperationLogDomain.setOrderPreStatus(OrderStatus.NON_PAYMENT.getCode());
        orderOperationLogDomain.setOrderStatus(OrderStatus.CLOSED_SELLER_CANCEL.getCode());
        orderOperationLogDomain.setOperationMsg("订单取消时间");

        orderOperationLogDao.insert(orderOperationLogDomain);
    }


    /**
     * 修改订单状态为   [申请退款中]
     * @param orderId
     */
    private void updateOrderToRefunding(String orderId) {
        // 1.检查订单状态是否为 [已付款、待发货],否则抛出异常
        OrderDomain order = orderDao.get(orderId);
        OrderStatus orderStatus = OrderStatus.getEnum(order.getOrderStatus());
        if (OrderStatus.UN_DELIVERY != orderStatus) {
            throw BusinessException.withErrorCode(Errors.Order.ORDER_STATUS_ERROR);
        }

        int status = OrderStatus.REFUNDING.getCode();
        orderDao.updateOrderToRefunding(orderId,status,new Date());


        // 2. 记录日志
        OrderOperationLogDomain orderOperationLogDomain = new OrderOperationLogDomain();
        orderOperationLogDomain.setOrderId(orderId);
        orderOperationLogDomain.setOrderPreStatus(OrderStatus.UN_DELIVERY.getCode());
        orderOperationLogDomain.setOrderStatus(OrderStatus.REFUNDING.getCode());
        orderOperationLogDomain.setOperationMsg("申请退款时间");

        orderOperationLogDao.insert(orderOperationLogDomain);

    }

    private void undoRefundOrderInner(String orderId) {

        // 1.检查订单状态是否为 [退款中],否则抛出异常
        OrderDomain order = orderDao.get(orderId);
        OrderStatus orderStatus = OrderStatus.getEnum(order.getOrderStatus());
        if (OrderStatus.REFUNDING != orderStatus) {
            throw BusinessException.withErrorCode(Errors.Order.ORDER_STATUS_ERROR);
        }

        //TODO 这里的逻辑应该是 回退订单上一版本状态,目前偷懒暂定这个状态
        int status = OrderStatus.UN_DELIVERY.getCode();  // 未发货
        orderDao.updateOrderStatus(orderId,status);


        // 2. 记录日志
        OrderOperationLogDomain orderOperationLogDomain = new OrderOperationLogDomain();
        orderOperationLogDomain.setOrderId(orderId);
        orderOperationLogDomain.setOrderPreStatus(OrderStatus.REFUNDING.getCode());
        orderOperationLogDomain.setOrderStatus(OrderStatus.UN_DELIVERY.getCode());
        orderOperationLogDomain.setOperationMsg("撤销申请退款时间");

        orderOperationLogDao.insert(orderOperationLogDomain);
    }

    /**
     * 修改订单状态为   [申请退货退款中]
     * @param orderId
     */
    private void updateOrderToRefundingGoods(String orderId) {
        // 1.检查订单状态是否为 [卖家已发货],否则抛出异常
        OrderDomain order = orderDao.get(orderId);
        OrderStatus orderStatus = OrderStatus.getEnum(order.getOrderStatus());
        if (OrderStatus.DELIVERED != orderStatus) {
            throw BusinessException.withErrorCode(Errors.Order.ORDER_STATUS_ERROR);
        }

        int status = OrderStatus.RETURNING_GOODS.getCode();
        orderDao.updateOrderStatus(orderId,status);


        // 2. 记录日志
        OrderOperationLogDomain orderOperationLogDomain = new OrderOperationLogDomain();
        orderOperationLogDomain.setOrderId(orderId);
        orderOperationLogDomain.setOrderPreStatus(OrderStatus.UN_DELIVERY.getCode());
        orderOperationLogDomain.setOrderStatus(OrderStatus.REFUNDING.getCode());
        orderOperationLogDomain.setOperationMsg("申请退款、退货时间");

        orderOperationLogDao.insert(orderOperationLogDomain);
    }



}

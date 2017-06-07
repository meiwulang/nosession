package com.hjh.mall.order.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.evun.sweet.framework.core.mvc.BusinessException;
import com.hjh.mall.order.constants.*;
import com.hjh.mall.order.service.OrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

//import cn.evun.sweet.framework.core.mvc.model.QueryResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hjh.mall.common.core.util.DateTimeUtil;
import com.hjh.mall.order.dao.OrderDao;
import com.hjh.mall.order.dao.OrderItemDao;
import com.hjh.mall.order.dao.OrderOperationLogDao;
import com.hjh.mall.order.dao.OrderPaymentProofDao;
import com.hjh.mall.order.dto.Order;
import com.hjh.mall.order.dto.OrderItem;
import com.hjh.mall.order.dto.OrderOperationLog;
import com.hjh.mall.order.dto.OrderPaymentProof;
import com.hjh.mall.order.dto.Product;
import com.hjh.mall.order.dto.QueryResult;
import com.hjh.mall.order.model.OrderDomain;
import com.hjh.mall.order.model.OrderItemDomain;
import com.hjh.mall.order.model.OrderOperationLogDomain;
import com.hjh.mall.order.model.OrderPaymentProofDomain;
import com.hjh.mall.order.service.OrderBaseService;
import com.hjh.mall.order.util.ObjectTranslateUtil;
import com.hjh.mall.order.util.OrderUtil;
import com.hjh.mall.order.vo.QueryOrderVo;

/**
 * Created by qiuxianxiang on 17/5/8.
 */

@Service("orderBaseService")
public class OrderBaseServiceImpl implements OrderBaseService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private OrderPaymentProofDao orderPaymentProofDao;

    @Autowired
    private OrderOperationLogDao orderOperationLogDao;

    @Autowired
    private OrderStatusService orderStatusService;



    @Override
    public QueryResult<Order> listOrder(QueryOrderVo queryOrderVo) {

		Date deliveryDateBegin = queryOrderVo.getDeliveryDateBegin();
		Date deliveryDateEnd = queryOrderVo.getDeliveryDateEnd();
		if (null != deliveryDateBegin) {
			queryOrderVo.setDeliveryDateBegin(deliveryDateBegin);
//			queryOrderVo.setDeliveryDateBegin(DateTimeUtil.getStartOfDay(deliveryDateBegin));
		}
		if (null != deliveryDateEnd) {
			queryOrderVo.setDeliveryDateEnd(deliveryDateEnd);
//			queryOrderVo.setDeliveryDateEnd(DateTimeUtil.getEndOfDay(deliveryDateEnd));
		}
		Date createDateBegin = queryOrderVo.getCreateDateBegin();
		Date createDateEnd = queryOrderVo.getCreateDateEnd();
		if(createDateBegin!=null){
			queryOrderVo.setCreateDateBegin(createDateBegin);
//			queryOrderVo.setCreateDateBegin(DateTimeUtil.getStartOfDay(createDateBegin));
		}
		if(createDateEnd!=null){
			queryOrderVo.setCreateDateEnd(createDateEnd);
//			queryOrderVo.setCreateDateEnd(DateTimeUtil.getEndOfDay(createDateEnd));
		}
		
    	
        PageHelper.startPage(queryOrderVo.getPageNum(),queryOrderVo.getPageSize());

        Page<OrderDomain> orderDomains = (Page<OrderDomain>) orderDao.query(queryOrderVo);


        QueryResult<OrderDomain> result = new QueryResult<>(orderDomains);

        QueryResult<Order> orderQueryResult = copyListProperties(result);

        // 2. 查询Item信息
        List<Order> orders = orderQueryResult.getItems();
        for (Order order : orders) {
            order.setProductList(getProductByOrderId(order.getOrderId()));
        }


        return orderQueryResult;

    }


    private List<Product> getProductByOrderId(String orderId) {

        List<OrderItemDomain> orderItemDomains = orderItemDao.queryOrderItems(orderId);
        List<OrderItem> orderItems = copyOrderItemListProperties(orderItemDomains);
        //order.setOrderItemList(orderItems);



        Map<String,Product> productMap = new HashMap<>();
        for (OrderItem orderItem : orderItems) {
            String key = orderItem.getProductId();
            Product product = productMap.get(key);

            if (product == null) {
                product = new Product();
                ObjectTranslateUtil.translateOrderItemToProduct(orderItem,product);
                productMap.put(key,product);

                if (product.getOrderItemList() == null) {
                    product.setOrderItemList(new ArrayList<OrderItem>());
                }
            }

            List<OrderItem> orderItemList = product.getOrderItemList();
            orderItemList.add(orderItem);

        }


        List<Product> products = new ArrayList<>();

        Iterator<String> iterator = productMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();

            products.add(productMap.get(key));
        }

        return products;
    }



    private QueryResult<Order> copyListProperties(QueryResult<OrderDomain> orderDomainQuery) {
        List<Order> orders = new ArrayList<>();

        for (OrderDomain userInfoDomain : orderDomainQuery.getItems()) {

            Order userInfo = new Order();
            ObjectTranslateUtil.translateOrderDomainToDto(userInfoDomain, userInfo);

            orders.add(userInfo);

        }

        QueryResult<Order> result = new QueryResult<Order>(orderDomainQuery.getTotal(), orders);
        return result;
    }



    @Override
    public Order getOrder(String orderId,String userId) {
        // 1. 确定订单是否为该用户的
        OrderUtil.checkIsUserOrder(orderDao,userId,orderId);



        OrderDomain orderDomain = orderDao.get(orderId);
        if (orderDomain == null) {
            return null;
        }

        Order order = new Order();
        ObjectTranslateUtil.translateOrderDomainToDto(orderDomain,order);

        //级联 T_ORDER_ITEM
        List<Product> products = getProductByOrderId(orderId);


        order.setProductList(products);

        return order;
    }

    private List<OrderItem> copyOrderItemListProperties(List<OrderItemDomain> orderItemDomains) {
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemDomain orderItemDomain : orderItemDomains) {
            orderItems.add(ObjectTranslateUtil.translateOrderItemDomainToDto(orderItemDomain,new OrderItem()));

        }

        return orderItems;
    }



    @Override
    @Transactional
    public void deleteOrder(String orderId, String userId) {
        // 0.
        OrderUtil.checkIsUserOrder(orderDao,userId,orderId);

        // 1. 确定改订单可删除,否则抛出异常
        OrderDomain orderDomain = orderDao.get(orderId);
        Integer orderStatus = orderDomain.getOrderStatus();
        OrderUtil.isOrderCanDelete(OrderStatus.getEnum(orderStatus));

        // 2. delete order
        orderDao.deleteOrder(orderId,OrderDelete.DELETE.getCode());

        // 3. 记录日志
        OrderOperationLogDomain orderOperationLogDomain = new OrderOperationLogDomain();
        orderOperationLogDomain.setOrderId(orderId);
        orderOperationLogDomain.setOrderPreStatus(orderStatus);
        orderOperationLogDomain.setOrderStatus(orderStatus);
        orderOperationLogDomain.setOperationMsg("删除订单");

        orderOperationLogDao.insert(orderOperationLogDomain);

    }

    @Override
    public Double getOrderTransactionAmount(String orderId,String userId) {
        // 0.
        OrderUtil.checkIsUserOrder(orderDao,userId,orderId);

        // 1.
        BigDecimal orderTransactionAmount = orderDao.getOrderTransactionAmount(orderId);
        if (orderTransactionAmount == null) {
            return null;
        }
        return orderTransactionAmount.doubleValue();

    }

    @Override
    public Double getOrderTransactionPayAmount(String orderId, String userId) {
        // 0.
        OrderUtil.checkIsUserOrder(orderDao,userId,orderId);

        // 1.
        BigDecimal orderTransactionAmount = orderDao.getOrderTransactionAmount(orderId);
        BigDecimal orderPostage = orderDao.getOrderPostage(orderId);
        if (orderPostage != null) {
            return orderTransactionAmount.add(orderPostage).doubleValue();
        }

        return orderTransactionAmount.doubleValue();
    }

    @Override
    public Double getOrderTransactionDeposit(String orderId,String userId) {
        // 0.
        OrderUtil.checkIsUserOrder(orderDao,userId,orderId);


        BigDecimal orderTransactionDeposit = orderDao.getOrderTransactionDeposit(orderId);
        if (orderTransactionDeposit == null) {
            return null;
        }

        return orderTransactionDeposit.doubleValue();
    }

    @Override
    public Double getOrderTransactionActualPayAmount(String orderId,String userId) {
        // 0.
        OrderUtil.checkIsUserOrder(orderDao,userId,orderId);

        BigDecimal orderTransactionActualPayAmount = orderDao.getOrderTransactionActualPayAmount(orderId);
        if (orderTransactionActualPayAmount == null) {
            return null;
        }


        return orderTransactionActualPayAmount.doubleValue();
    }

    @Override
    public Double getOrderPostage(String orderId,String userId) {
        // 0.
        OrderUtil.checkIsUserOrder(orderDao,userId,orderId);
        BigDecimal orderPostage = orderDao.getOrderPostage(orderId);
        if (orderPostage == null) {
            return null;
        }
        return orderPostage.doubleValue();
    }

    @Override
    @Transactional
    public void updateOrderPostage(String orderId, double postage,String userId) {
        // 0.
        OrderUtil.checkIsUserOrder(orderDao,userId,orderId);

        // 1.
        orderDao.updateOrderPostage(orderId,postage);

        // 2. 记录日志
        recordPostageUpdatedLog(orderId,postage);
    }

    @Override
    @Transactional
    public void updateOrderExceptDeliveryDesc(String orderId, String estimateDeliveryDesc,String userId) {
        // 0.
        OrderUtil.checkIsUserOrder(orderDao,userId,orderId);

        orderDao.updateOrderEstimateDeliveryDesc(orderId,estimateDeliveryDesc);

    }

    @Override
    @Transactional
    public void updateOrderBalanceDate(String orderId, Integer balanceDateCount,String userId) {
        // 0.
        OrderUtil.checkIsUserOrder(orderDao,userId,orderId);

        orderDao.updateOrderBalanceDateCount(orderId,balanceDateCount);
    }

    @Override
    @Transactional
    public void updateOrderPostage(String orderId, Double postage, String estimateDeliveryDesc, Integer balanceDateCount,String userId) {
        // 0.
        OrderUtil.checkIsUserOrder(orderDao,userId,orderId);

        // 1.
        orderDao.updateOrderPostageEtc(orderId,postage,estimateDeliveryDesc,balanceDateCount);

        // 2. 记录日志
        recordPostageUpdatedLog(orderId,postage);
    }

    @Override
    @Transactional
    public void updateOrderDeliveryDate(String orderId, Date deliveryDate) {
        orderDao.updateOrderDeliveryDate(orderId,deliveryDate);
    }


    private void recordPostageUpdatedLog(String orderId, double postage) {
        OrderOperationLogDomain orderOperationLogDomain = new OrderOperationLogDomain();
        orderOperationLogDomain.setOrderId(orderId);
        orderOperationLogDomain.setOrderPreStatus(OrderStatus.NON_PAYMENT.getCode());
        orderOperationLogDomain.setOrderStatus(OrderStatus.NON_PAYMENT.getCode());
        orderOperationLogDomain.setOperationMsg("核算时间");
//        orderOperationLogDomain.setOperationMsg("设置订单邮费:"+ postage);

        orderOperationLogDao.insert(orderOperationLogDomain);
    }

    @Override
    @Transactional
    public void updateOrderRemark(String orderId, String userId, String remark) {
        // 0.
        OrderUtil.checkIsUserOrder(orderDao,userId,orderId);

        orderDao.updateOrderRemark(orderId,remark);
    }

    @Override
    public String getOrderRemark(String orderId,String userId) {
        // 0.
        OrderUtil.checkIsUserOrder(orderDao,userId,orderId);

        return orderDao.getOrderRemark(orderId);
    }

    @Override
    @Transactional
    public void addOrderPayProof(String orderId, String userId, String paymentUserName, String bankAccount,
                                 String paymentAccount, Double amount, String paymentProofSnapshot) {
        // 0.
        OrderUtil.checkIsUserOrder(orderDao,userId,orderId);

        OrderDomain orderDomain = orderDao.get(orderId);
        OrderStatus orderStatus = OrderStatus.getEnum(orderDomain.getOrderStatus());
        TransactionType transactionType = TransactionType.getEnum(orderDomain.getTransactionType());

        // 1.
        OrderPaymentProofDomain orderPaymentProofDomain = new OrderPaymentProofDomain();
        orderPaymentProofDomain.setOrderId(orderId);
        orderPaymentProofDomain.setPaymentUserName(paymentUserName);
        orderPaymentProofDomain.setBankAccount(bankAccount);
        orderPaymentProofDomain.setPaymentAccount(paymentAccount);
        orderPaymentProofDomain.setPaymentProofSnapshot(paymentProofSnapshot);
        if (amount != null) {
            orderPaymentProofDomain.setAmount(new BigDecimal(amount));
        }

        orderPaymentProofDomain.setCreateDate(new Date());

        orderPaymentProofDao.insert(orderPaymentProofDomain);


        if (TransactionType.NORMAL == transactionType) {
            orderDao.updateBalanceProofDate(orderId,new Date());
            recordUploadPayNormalProofLog(orderId);
        }

        if (TransactionType.DEPOSIT == transactionType && OrderStatus.NON_PAYMENT == orderStatus) {
            orderDao.updateDepositProofDate(orderId,new Date());
            recordUploadPayDepositProofLog(orderId);
        }

        if (TransactionType.DEPOSIT == transactionType && OrderStatus.PART_PAID == orderStatus) {
            orderDao.updateBalanceProofDate(orderId,new Date());
            recordUploadPayBalanceProofLog(orderId);
        }



    }

    private void recordUploadPayDepositProofLog(String orderId) {

        OrderOperationLogDomain orderOperationLogDomain = new OrderOperationLogDomain();
        orderOperationLogDomain.setOrderId(orderId);
        orderOperationLogDomain.setOrderPreStatus(OrderStatus.NON_PAYMENT.getCode());
        orderOperationLogDomain.setOrderStatus(OrderStatus.NON_PAYMENT.getCode());
        orderOperationLogDomain.setOperationMsg("定金支付时间");

        orderOperationLogDao.insert(orderOperationLogDomain);

    }

    private void recordUploadPayBalanceProofLog(String orderId) {

        OrderOperationLogDomain orderOperationLogDomain = new OrderOperationLogDomain();
        orderOperationLogDomain.setOrderId(orderId);
        orderOperationLogDomain.setOrderPreStatus(OrderStatus.PART_PAID.getCode());
        orderOperationLogDomain.setOrderStatus(OrderStatus.PART_PAID.getCode());
        orderOperationLogDomain.setOperationMsg("余款支付时间");

        orderOperationLogDao.insert(orderOperationLogDomain);

    }


    private void recordUploadPayNormalProofLog(String orderId) {

        OrderOperationLogDomain orderOperationLogDomain = new OrderOperationLogDomain();
        orderOperationLogDomain.setOrderId(orderId);
        orderOperationLogDomain.setOrderPreStatus(OrderStatus.NON_PAYMENT.getCode());
        orderOperationLogDomain.setOrderStatus(OrderStatus.NON_PAYMENT.getCode());
        orderOperationLogDomain.setOperationMsg("付款时间");

        orderOperationLogDao.insert(orderOperationLogDomain);

    }








    @Override
    public List<OrderPaymentProof> getOrderPayProof(String orderId, String userId) {
        // 0.
        OrderUtil.checkIsUserOrder(orderDao,userId,orderId);

        List<OrderPaymentProofDomain> orderPayProof = orderPaymentProofDao.getOrderPayProof(orderId);

        return copyProofListProperties(orderPayProof);
    }


    private List<OrderPaymentProof> copyProofListProperties(List<OrderPaymentProofDomain> proofDomainList) {
        List<OrderPaymentProof> orders = new ArrayList<>();

        for (OrderPaymentProofDomain orderPaymentProofDomain : proofDomainList) {

            OrderPaymentProof orderPaymentProof = new OrderPaymentProof();
            ObjectTranslateUtil.translateOrderPaymentProofDomainToDto(orderPaymentProofDomain, orderPaymentProof);

            orders.add(orderPaymentProof);
        }

        return orders;
    }


    @Override
    @Transactional
    public void updateOrderPayProof(String payProofId,String orderId, String userId, String paymentUserName,
                                    String bankAccount, String paymentAccount, Double amount, String paymentProofSnapshot) {
        // 0.
        OrderUtil.checkIsUserOrder(orderDao,userId,orderId);

        // 1.
        OrderPaymentProofDomain orderPaymentProofDomain = new OrderPaymentProofDomain();
        orderPaymentProofDomain.setId(payProofId);
        orderPaymentProofDomain.setOrderId(orderId);
        orderPaymentProofDomain.setPaymentUserName(paymentUserName);
        orderPaymentProofDomain.setPaymentProofSnapshot(paymentProofSnapshot);
        orderPaymentProofDomain.setBankAccount(bankAccount);
        orderPaymentProofDomain.setPaymentAccount(paymentAccount);

        if (amount != null) {
            orderPaymentProofDomain.setAmount(new BigDecimal(amount));
        }


        orderPaymentProofDao.update(orderPaymentProofDomain);

        orderDao.updateDepositProofDate(orderId,new Date());
    }

    @Override
    public List<Map<String, Object>> getOrderCount(String userId) {
       List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
       
       //获取枚举中状态列表
       OrderStatusCategory[] list=OrderStatusCategory.values();
    	for (OrderStatusCategory e : list) {
    		Map<String, Object> map = new HashMap<String, Object>();
    		
    		Map<String,Object> queryParam = new HashMap<>();
    		if (! StringUtils.isEmpty(userId)) {
                queryParam.put("userId",userId);
            }
    		
    		queryParam.put("orderStatus",e.getStatutes().split(","));

            switch (e) {
                case CHECKING_REIGHT:  // 未核算运费
                    queryParam.put("isPostageCalculation",false);
                    break;
                case PENDING_PAYMENT:  // 待付款
                    queryParam.put("isPostageCalculation",true);
                    queryParam.put("haveFullPayProof",false);
                    break;

                case GOODS_NOT_RECEIVED:
                    queryParam.put("haveFullPayProof",true);
                    break;
            }
            map.put("code", e.getCode());
    		map.put("status", e.getStatutes());
    		map.put("description", e.getDescription());
    		map.put("count", orderDao.queryCount(queryParam));
    		result.add(map);
		}
    	return result;
    }

    @Override
    public Map<String, Object> getManagerOrderCount() {
        Map<String, Object> resultMap = new HashMap<>();

        //1. 待核算
        resultMap.put("POSTAGE_NO_CALCULATION",getPostageNotCalculationOrder());

        //2. 待付款
        resultMap.put("NON_PAYMENT",getNonPaymentOrder());

        //3. 已付定金
        resultMap.put("PART_PAID", getPartPaidOrder());

        //4. 已付款
        resultMap.put("PAID", getPaidOrder());

        //5. 退款中
        resultMap.put("REFUNDING",getRefundingOrder());
        //6. 待发货
        resultMap.put("UN_DELIVERY",getUnDeliveryOrder());

        //7. 已发货
        resultMap.put("DELIVERED",getDeliveryOrder());

        //8. 成功的订单
        resultMap.put("SUCCESS",getSuccessOrder());

        //9. 关闭的订单
        resultMap.put("CLOSED", getClosedOrder());


        return resultMap;
    }
    private Integer getPostageNotCalculationOrder() {
        // o = 1   运费未核算
        Map<String,Object> queryParam = new HashMap<>();
        Integer[] orderStatusArr = {OrderStatus.NON_PAYMENT.getCode()};
        queryParam.put("orderStatus",orderStatusArr);
        queryParam.put("isPostageCalculation",false);

        return orderDao.queryCount(queryParam);
    }

    private Integer getNonPaymentOrder() { //NON_PAYMENT
        //  0 = 1  运费已核算   定金支付凭证未上传   尾款凭证未上传

        Map<String,Object> queryParam = new HashMap<>();
        Integer[] orderStatusArr = {OrderStatus.NON_PAYMENT.getCode()};
        queryParam.put("orderStatus",orderStatusArr);
        queryParam.put("isPostageCalculation",true);

        queryParam.put("haveDepositPayProof",false);
        queryParam.put("haveFullPayProof",false);


        return orderDao.queryCount(queryParam);
    }


    private Integer getPartPaidOrder() { //PART_PAID
        // 已付定金  0=[1,2] 定金支付凭证已上传   尾款凭证未上传
        Map<String,Object> queryParam = new HashMap<>();

        Integer[] orderStatusArr = {OrderStatus.NON_PAYMENT.getCode(),OrderStatus.PART_PAID.getCode()};
        queryParam.put("orderStatus",orderStatusArr);

        queryParam.put("haveDepositPayProof",true);
        queryParam.put("haveFullPayProof",false);

        return orderDao.queryCount(queryParam);
    }


    private Integer getPaidOrder() { // paid
        //  o=[1,2] 尾款凭证已上传
        Map<String,Object> queryParam = new HashMap<>();

        Integer[] orderStatusArr = {OrderStatus.NON_PAYMENT.getCode(),OrderStatus.PART_PAID.getCode()};
        queryParam.put("orderStatus",orderStatusArr);

        queryParam.put("haveFullPayProof",true);



        return orderDao.queryCount(queryParam);
    }

    private Integer getRefundingOrder() { // REFUNDING
        // o=9
        Map<String,Object> queryParam = new HashMap<>();

        Integer[] orderStatusArr = {OrderStatus.REFUNDING.getCode()};
        queryParam.put("orderStatus",orderStatusArr);

        return orderDao.queryCount(queryParam);
    }

    private Integer getUnDeliveryOrder() { // UN_DELIVERY
        //  o=3
        Map<String,Object> queryParam = new HashMap<>();

        Integer[] orderStatusArr = {OrderStatus.UN_DELIVERY.getCode()};
        queryParam.put("orderStatus",orderStatusArr);


        return orderDao.queryCount(queryParam);
    }


    private Integer getDeliveryOrder() { // DELIVERED
        // o=4
        Map<String,Object> queryParam = new HashMap<>();

        Integer[] orderStatusArr = {OrderStatus.DELIVERED.getCode()};
        queryParam.put("orderStatus",orderStatusArr);

        return orderDao.queryCount(queryParam);
    }


    private Integer getSuccessOrder() {   //SUCCESS
        // o=[5,6]
        Map<String,Object> queryParam = new HashMap<>();

        Integer[] orderStatusArr = {OrderStatus.RECEIVED.getCode(),OrderStatus.SUCCESS.getCode()};
        queryParam.put("orderStatus",orderStatusArr);

        return orderDao.queryCount(queryParam);
    }

    private Integer getClosedOrder() {   // CLOSED
        // o=[...]
        Map<String,Object> queryParam = new HashMap<>();

        Integer[] orderStatusArr = {
                OrderStatus.CLOSED_BUYER_CANCEL.getCode(),
                OrderStatus.CLOSED_SELLER_CANCEL.getCode(),
                OrderStatus.CLOSED_REFUNDED.getCode(),
                OrderStatus.CLOSED_RETURN_GOODS.getCode(),
                OrderStatus.CLOSED_EXCEPTION.getCode(),
        };
        queryParam.put("orderStatus",orderStatusArr);


        return orderDao.queryCount(queryParam);
    }



    @Override
    public List<OrderOperationLog> getOrderOperations(String orderId, String userId) {

        // 0.
        OrderUtil.checkIsUserOrder(orderDao,userId,orderId);


        // 1.
        List<OrderOperationLogDomain> orderOperationLog = orderOperationLogDao.getOrderOperationLog(orderId);


        return copyOrderOperationListProperties(orderOperationLog);
    }

    @Override
    public List<OrderOperationLog> getOrderProgress(String orderId, String userId) {

        List<OrderOperationLog> orderOperations = getOrderOperations(orderId, userId);

        String preOperationMsg = "";
        for (int i = orderOperations.size()-1; i >=0; i--) {  // 去除重复操作数据
            OrderOperationLog orderOperationLog = orderOperations.get(i);
            String operationMsg = orderOperationLog.getOperationMsg();

            if (preOperationMsg.equals(operationMsg)) {
                orderOperations.remove(i);
            }

            preOperationMsg = operationMsg;
        }

        for (int i = orderOperations.size()-1; i >=0; i--) { // 去除撤销退款时间数据
            OrderOperationLog orderOperationLog = orderOperations.get(i);
            Integer orderPreStatus = orderOperationLog.getOrderPreStatus();
            Integer orderStatus = orderOperationLog.getOrderStatus();

            if (OrderStatus.REFUNDING.getCode() == orderPreStatus
                    && OrderStatus.UN_DELIVERY.getCode() == orderStatus
            ) {
                orderOperations.remove(i);
                orderOperations.remove(i-1);
                i--;
                //break;
            }

        }



        return orderOperations;
    }

    @Override
    @Transactional
    public void updateRefundingInfo(String orderId, double refundingAmount, String refundingExplain) {

        // 0. 修改订单状态
        orderStatusService.refundOrderComplete(orderId);

        // 1. 修改订单金额
        doUpdateRefundingInfo(orderId,refundingAmount,refundingExplain);


    }
    private void doUpdateRefundingInfo(String orderId, double refundingAmount, String refundingExplain) {
        // 1. 检查 退款金额是否超出用户支付金额
        OrderDomain orderDomain = orderDao.get(orderId);
        BigDecimal userPaidAmount = orderDomain.getTransactionActualPayAmount().add(orderDomain.getTransactionActualPayBalance());

        if (refundingAmount > userPaidAmount.doubleValue()) {
            throw BusinessException.withErrorCode(Errors.Order.ORDER_REFUND_AMOUNT_ERROR);
        }

        orderDao.updateRefundingInfo(orderId,refundingAmount,refundingExplain);
    }


    private List<OrderOperationLog> copyOrderOperationListProperties(List<OrderOperationLogDomain> orderOperationLogDomains) {
        List<OrderOperationLog> orders = new ArrayList<>();

        for (OrderOperationLogDomain orderPaymentProofDomain : orderOperationLogDomains) {

            OrderOperationLog orderOperationLog = new OrderOperationLog();
            ObjectTranslateUtil.translateOrderOperationLogDomainToDto(orderPaymentProofDomain, orderOperationLog);

            orders.add(orderOperationLog);
        }

        return orders;
    }


}

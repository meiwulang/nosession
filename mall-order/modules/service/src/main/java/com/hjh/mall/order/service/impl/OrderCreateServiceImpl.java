package com.hjh.mall.order.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hjh.mall.order.vo.CreateOrderAddressVo;
import com.hjh.mall.order.vo.CreateOrderUserVo;
import com.hjh.mall.order.vo.CreateOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.evun.sweet.framework.core.mvc.BusinessException;

import com.hjh.mall.bizapi.biz.goods.middle.entity.Product4Order;
import com.hjh.mall.order.constants.Errors;
import com.hjh.mall.order.constants.OrderDelete;
import com.hjh.mall.order.constants.OrderStatus;
import com.hjh.mall.order.constants.TransactionType;
import com.hjh.mall.order.dao.OrderDao;
import com.hjh.mall.order.dao.OrderItemDao;
import com.hjh.mall.order.dao.OrderOperationLogDao;
import com.hjh.mall.order.model.OrderDomain;
import com.hjh.mall.order.model.OrderItemDomain;
import com.hjh.mall.order.model.OrderOperationLogDomain;
import com.hjh.mall.order.service.OrderCreateService;

/**
 * Created by qiuxianxiang on 17/5/15.
 */
@Service("orderCreateService")
public class OrderCreateServiceImpl implements OrderCreateService {


    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private OrderOperationLogDao orderOperationLogDao;

    @Override
    @Transactional
    public String createSingleOrder(CreateOrderVo createOrderVo,
                                    CreateOrderAddressVo createOrderAddressVo,
                                    CreateOrderUserVo createOrderUserVo
    ) {
        List<Product4Order> product4OrderList = createOrderVo.getProduct4OrderList();


        // 0. 检查所有商品是否可购买(商品是否下架、最大采购量等),否则抛出异常
        assertProductCanPurchase(product4OrderList);

        // 1. 检查商品列表是否可分一个单, 否则抛出异常
        assertAnOrder(product4OrderList);


        // 2. 订单入库(T_ORDER & T_ORDER_ITEM )
        //          1) 查询商品信息
        //          2) 计算金额
        //          3) 其他
        OrderDomain orderDomain = new OrderDomain();
        insertOrder(orderDomain,createOrderVo,createOrderAddressVo,createOrderUserVo);


        // 3. 记录操作日志
        recordOperationLog(orderDomain.getOrderId(),
                createOrderUserVo.getUserId(),createOrderUserVo.getUserName());


        // 4. 返回订单ID
        return orderDomain.getOrderId();
    }

    private void insertOrder(OrderDomain orderDomain, CreateOrderVo createOrderVo,
                             CreateOrderAddressVo createOrderAddressVo,
                             CreateOrderUserVo createOrderUserVo) {

        List<Product4Order> product4OrderList = createOrderVo.getProduct4OrderList();

        orderDomain.setOrderNo(createOrderVo.getOrderNo());// orderNo 从外界传递过来
        orderDomain.setInviteCode(createOrderVo.getInviteCode());
        createBaseOrder(product4OrderList,orderDomain);

        //设置品牌信息
        orderDomain.setBrandId(product4OrderList.get(0).getBrandId());
        orderDomain.setBrandName(product4OrderList.get(0).getBrandName());
        //设置用户信息
        orderDomain.setUserId(createOrderUserVo.getUserId());
        orderDomain.setUserName(createOrderUserVo.getUserName());
        orderDomain.setUserMobile(createOrderUserVo.getMobile());
        // 设置收货信息
        orderDomain.setConsigneeName(createOrderAddressVo.getConsigneeName());
        orderDomain.setConsigneeMobile(createOrderAddressVo.getConsigneeMobile());
        orderDomain.setConsigneeAddress(createOrderAddressVo.getConsigneeAddress());
        orderDomain.setConsigneeProvince(createOrderAddressVo.getConsigneeProvince());
        orderDomain.setConsigneeCity(createOrderAddressVo.getConsigneeCity());
        orderDomain.setConsigneeDistrict(createOrderAddressVo.getConsigneeDistrict());
        //orderDomain.setConsigneeZip();

        // 设置 买家留言
        orderDomain.setBuyerComments(createOrderVo.getBuyerComments());
        orderDao.insert(orderDomain);

        List<OrderItemDomain> orderItemList = createOrderItem(product4OrderList,orderDomain.getOrderId(),orderDomain.getOrderNo());
        for(OrderItemDomain orderItemDomain : orderItemList ) {
            orderItemDao.insert(orderItemDomain);
        }
    }


    //断言商品可以购买
    private void assertProductCanPurchase(List<Product4Order> product4OrderList) {

        assertDateNotNull(product4OrderList);

        for (Product4Order product4Order : product4OrderList) {
            int maxSaleNum = product4Order.getMaxSaleNum();
            int prdtNum = product4Order.getPrdtNum();

            if ( prdtNum == 0 ) {
                throw BusinessException.withErrorCode(Errors.Order.ORDER_CREATE_PRODUCT_NULL);
            }

            if(prdtNum > maxSaleNum) {
                throw BusinessException.withErrorCode(Errors.Order.ORDER_CREATE_PRODUCT_LIMIT);
            }
        }
    }


    //断言一个订单
    // 是否为一个订单的逻辑: 订单支付类型+品牌是否一样
    private void assertAnOrder(List<Product4Order> product4OrderList) {
        assertDateNotNull(product4OrderList);

        // 0. 判断商品是否为同一个商家  [暂时不做todo]

        // 1. 判断订单支付类型
        assertIsOneTransactionType(product4OrderList);
        // 2. 判断商品品牌
        assertIsOneBrand(product4OrderList);

    }

    private void assertDateNotNull(List<Product4Order> product4OrderList) {
        if (product4OrderList == null && product4OrderList.size() == 0) {
            throw BusinessException.withErrorCode(Errors.Order.ORDER_CREATE_PRODUCT_NULL);
        }
    }

    private void assertIsOneTransactionType(List<Product4Order> product4OrderList) {
        assertDateNotNull(product4OrderList);

        int payType = product4OrderList.get(0).getPayType();

        for (Product4Order product4Order : product4OrderList) {

            if (payType != product4Order.getPayType()) {
                throw BusinessException.withErrorCode(Errors.Order.ORDER_CREATE_TRANSACTION_TYPE_ERROR);
            }
        }
    }

    private void assertIsOneBrand(List<Product4Order> product4OrderList) {
        assertDateNotNull(product4OrderList);

        String brandId = product4OrderList.get(0).getBrandId();

        for (Product4Order product4Order : product4OrderList) {

            if (! brandId.equals(product4Order.getBrandId())) {
                throw BusinessException.withErrorCode(Errors.Order.ORDER_CREATE_BRAND_ERROR);
            }

        }

    }


    private OrderDomain createBaseOrder(List<Product4Order> product4OrderList, OrderDomain orderDomain) {

        Product4Order product4Order = product4OrderList.get(0);

        //orderDomain.setOrderNo(OrderUtil.createOrderNo());
        orderDomain.setOrderStatus(OrderStatus.NON_PAYMENT.getCode());
        orderDomain.setShopId("0");   //TODO 暂时采取默认值,将来要从商品中获取
        orderDomain.setCreatedDate(new Date());  //TODO 时间要采用统一一台服务器时间,目前还没理清思路
        orderDomain.setDelFlag(OrderDelete.NORMAL.getCode());

        TransactionType transactionType = TransactionType.getEnum(product4Order.getPayType());
        orderDomain.setTransactionType(transactionType.getCode());

    //计算价格 BEGIN

        // 1. 设置 订单交易金额(除邮费)
        String transactionAmount = getTransactionAmount(product4OrderList)+"";
        orderDomain.setTransactionAmount(new BigDecimal(transactionAmount));


        if (TransactionType.DEPOSIT == transactionType) {  // 1.1 设置交易定金
            String transactionDeposit = getTransactionDeposit(product4OrderList) +"";
            orderDomain.setTransactionDeposit(new BigDecimal(transactionDeposit));
        } else {
            orderDomain.setTransactionDeposit(new BigDecimal("0"));
        }

        orderDomain.setTransactionActualPayBalance(new BigDecimal(0));
        //orderDomain.setPostage(new BigDecimal(99.01));

    //计算价格 END

        return orderDomain;
    }


    private double getTransactionAmount(List<Product4Order> product4OrderList) {
        BigDecimal transactionAmount = new BigDecimal("0");
        for (Product4Order product4Order : product4OrderList) {
            BigDecimal productPrices = product4Order.getPrice().multiply(new BigDecimal(product4Order.getPrdtNum()));

            transactionAmount = transactionAmount.add(productPrices);
        }

        return transactionAmount.doubleValue();
    }

    /*
    设置交易定金(不包含邮费)
     */
    private double getTransactionDeposit(List<Product4Order> product4OrderList) {
        BigDecimal transactionDeposit = new BigDecimal("0");
        for (Product4Order product4Order : product4OrderList) {
            BigDecimal productPrices = new BigDecimal(product4Order.getFontMoneyRate())
                    .divide(new BigDecimal(100))
                    .multiply(product4Order.getPrice())
                    .multiply(new BigDecimal(product4Order.getPrdtNum()));

            transactionDeposit = transactionDeposit.add(productPrices);
        }

        return transactionDeposit.doubleValue();

    }


    private List<OrderItemDomain> createOrderItem(List<Product4Order> product4OrderList,String orderId, String orderNo) {

        List<OrderItemDomain> orderItemDomains = new ArrayList<>();

        for (Product4Order product4Order : product4OrderList) {

            OrderItemDomain orderItemDomain2 = new OrderItemDomain();

            orderItemDomain2.setOrderId(orderId);
            orderItemDomain2.setOrderNo(orderNo);
            orderItemDomain2.setCategoryName(product4Order.getCategoryName());
            orderItemDomain2.setProductId(product4Order.getProductId());
            orderItemDomain2.setProductItemId(product4Order.getStandardId());
            orderItemDomain2.setCategoryId(product4Order.getCategoryId());
            orderItemDomain2.setBrandId(product4Order.getBrandId());

            BigDecimal productPrices = product4Order.getPrice().multiply(new BigDecimal(product4Order.getPrdtNum()));
            orderItemDomain2.setAmount(productPrices);
            orderItemDomain2.setPrice(product4Order.getPrice());
            orderItemDomain2.setDeposit(new BigDecimal(product4Order.getFontMoneyRate()));


            orderItemDomain2.setQuantity(product4Order.getPrdtNum());
            orderItemDomain2.setUnit(product4Order.getUnitName());
            orderItemDomain2.setProductName(product4Order.getProductName());
            orderItemDomain2.setProductDesc("no description now");
            orderItemDomain2.setPictureCode(product4Order.getShowUrl());
            orderItemDomain2.setBrandName(product4Order.getBrandName());
            orderItemDomain2.setProductStandardMust(product4Order.getStandard_must());
            orderItemDomain2.setProductOptionalFirst(product4Order.getOptional_first());
            orderItemDomain2.setProductOptionalSecond(product4Order.getOptional_second());

            orderItemDomains.add(orderItemDomain2);
        }


        return orderItemDomains;
    }



    private void recordOperationLog(String orderId, String userId, String userName) {
        OrderOperationLogDomain orderOperationLogDomain = new OrderOperationLogDomain();
        orderOperationLogDomain.setOrderId(orderId);
        orderOperationLogDomain.setOrderPreStatus(-1);
        orderOperationLogDomain.setOrderStatus(OrderStatus.NON_PAYMENT.getCode());
        orderOperationLogDomain.setOperatorId(userId);
        orderOperationLogDomain.setOperatorName(userName);
        orderOperationLogDomain.setOperationMsg("创建时间");
        orderOperationLogDao.insert(orderOperationLogDomain);
    }



}

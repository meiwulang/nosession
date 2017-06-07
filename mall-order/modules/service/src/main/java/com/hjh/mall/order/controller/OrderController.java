package com.hjh.mall.order.controller;

import com.hjh.mall.bizapi.biz.goods.middle.entity.Product4Order;
import com.hjh.mall.order.api.OrderCloudService;
import com.hjh.mall.order.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hjh.mall.order.constants.OrderStatus;
import com.hjh.mall.order.constants.TransactionType;
import com.hjh.mall.order.dto.Logistics;
import com.hjh.mall.order.dto.Order;
import com.hjh.mall.order.dto.OrderOperationLog;
import com.hjh.mall.order.dto.OrderPaymentProof;
import com.hjh.mall.order.dto.QueryResult;
import com.hjh.mall.order.dto.common.APIResponse;
import com.hjh.mall.order.service.LogisticsService;
import com.hjh.mall.order.service.OrderBaseService;
import com.hjh.mall.order.service.OrderCreateService;
import com.hjh.mall.order.service.OrderStatusService;

/**
 * Created by qiuxianxiang on 17/5/8.
 */
@Api("订单服务接口")
@RestController()
@RequestMapping("/order-service")
public class OrderController {

    @Autowired
    private OrderBaseService orderBaseService;

    @Autowired
    private OrderStatusService orderStatusService;

    @Autowired
    private OrderCreateService orderCreateService;

    @Autowired
    private OrderCloudService orderCloudService;


    @Autowired
    private LogisticsService logisticsService;

    @ApiOperation(value = "查询订单列表", notes = "条件之间是\"AND\"关系")
    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public APIResponse<QueryResult<Order>> listOrder(

            @ApiParam(name = "userId", value = "下单用户数据库主键,精确查询", required = false)
            @RequestParam(name = "userId", required = false)
                    String userId,

            @ApiParam(name = "orderNoLike", value = "模糊订单号", required = false)
            @RequestParam(name = "orderNoLike", required = false)
                    String orderNoLike,

            @ApiParam(name = "productNoLike", value = "模糊商品编号", required = false)
            @RequestParam(name = "productNoLike", required = false)
                    String productNoLike,

            @ApiParam(name = "productTitleLike", value = "模糊商品标题", required = false)
            @RequestParam(name = "productTitleLike", required = false)
                    String productTitleLike,

            @ApiParam(name = "brandLike", value = "模糊品牌名称", required = false)
            @RequestParam(name = "brandLike", required = false)
                    String brandLike,

            @ApiParam(name = "userNameLike", value = "模糊下单用户", required = false)
            @RequestParam(name = "userNameLike", required = false)
                    String userNameLike,

            @ApiParam(name = "inviteCodeBegin", value = "精确邀请码,范围开始", required = false)
            @RequestParam(name = "inviteCodeBegin", required = false)
                    String inviteCodeBegin,

            @ApiParam(name = "inviteCodeEnd", value = "精确邀请码,范围结束", required = false)
            @RequestParam(name = "inviteCodeEnd", required = false)
                    String inviteCodeEnd,

            @ApiParam(name = "transactionType", value = "交易类型", required = false)
            @RequestParam(name = "transactionType", required = false)
                    TransactionType transactionType,

            @ApiParam(name = "estimateDeliveryDescLike", value = "预计发货时间,范围开始", required = false)
            @RequestParam(name = "estimateDeliveryDescLike", required = false)
                    String estimateDeliveryDescLike,

            @ApiParam(name = "createDateBegin", value = "订单创建时间,范围开始", required = false)
            @RequestParam(name = "createDateBegin", required = false)
                    Date createDateBegin,

            @ApiParam(name = "createDateEnd", value = "订单创建时间,范围结束", required = false)
            @RequestParam(name = "createDateEnd", required = false)
                    Date createDateEnd,

            @ApiParam(name = "deliveryDateBegin", value = "发货时间,范围开始", required = false)
            @RequestParam(name = "deliveryDateBegin", required = false)
                    Date deliveryDateBegin,

            @ApiParam(name = "deliveryDateEnd", value = "发货时间,范围结束", required = false)
            @RequestParam(name = "deliveryDateEnd", required = false)
                    Date deliveryDateEnd,

            @ApiParam(name = "orderStatus", value = "订单状态", required = false)
            @RequestParam(name = "orderStatus", required = false)
                    OrderStatus[] orderStatus,

            @ApiParam(name = "isPostageCalculation", value = "邮费是否计算", required = false)
            @RequestParam(name = "isPostageCalculation", required = false)
            Boolean isPostageCalculation,

            @ApiParam(name = "haveFullPayProof", value = "是否具有全款支付的凭证，不传则不作为过滤条件", required = false)
            @RequestParam(name = "haveFullPayProof", required = false)
            Boolean haveFullPayProof,

            @ApiParam(name = "pageNum", value = "页号, 默认为1", required = false)
            @RequestParam(name = "pageNum", required = false, defaultValue = "1")
                    int pageNum,

            @ApiParam(name = "pageSize", value = "每页行数, 默认为10", required = false)
            @RequestParam(name = "pageSize", required = false, defaultValue = "10")
                    int pageSize
    ) {


        QueryOrderVo queryOrderVo = new QueryOrderVo();
        queryOrderVo.setUserId(userId);
        queryOrderVo.setOrderNoLike(orderNoLike);
        queryOrderVo.setBrandNameLike(brandLike);
        queryOrderVo.setUserNameLike(userNameLike);
        queryOrderVo.setInviteCodeBegin(inviteCodeBegin);
        queryOrderVo.setInviteCodeEnd(inviteCodeEnd);
        queryOrderVo.setTransactionType(transactionType);
        queryOrderVo.setEstimateDeliveryDescLike(estimateDeliveryDescLike);
        queryOrderVo.setCreateDateBegin(createDateBegin);
        queryOrderVo.setCreateDateEnd(createDateEnd);
        queryOrderVo.setDeliveryDateBegin(deliveryDateBegin);
        queryOrderVo.setDeliveryDateEnd(deliveryDateEnd);
        queryOrderVo.setOrderStatus(orderStatus);
        queryOrderVo.setPostageCalculation(isPostageCalculation);
        queryOrderVo.setHaveFullPayProof(haveFullPayProof);
        queryOrderVo.setPageNum(pageNum);
        queryOrderVo.setPageSize(pageSize);

        QueryResult<Order> orderQueryResult = orderBaseService.listOrder(queryOrderVo);


        return APIResponse.success(orderQueryResult);
    }



    @ApiOperation(value = "商品下单接口,返回创建订单ID",
            notes = "默认商品列表组成一个单,如果商品列表存在分单,则抛出异常")
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public APIResponse<String> createSingleOrder(

            @ApiParam(name = "productCompoundInfo", value = "商品下单的组合信息, 其构成为:  商品Id_规格Id_数量。 例如: 59_34_5 ", required = true)
            @RequestParam(name = "productCompoundInfo", required = true)
                    String[] productCompoundInfo,

            @ApiParam(name = "deliveryAddressId", value = "收获地址ID", required = true)
            @RequestParam(name = "deliveryAddressId", required = true)
                    String deliveryAddressId,

            @ApiParam(name = "deliveryAddress", value = "收获地址", required = true)
            @RequestParam(name = "deliveryAddress", required = true)
                    String deliveryAddress,


            @ApiParam(name = "buyerComments", value = "买家订单留言", required = false)
            @RequestParam(name = "buyerComments", required = false)
                    String buyerComments,

            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam(name = "userId", required = true)
                    String userId
    ) {

//        orderCreateService.createSingleOrder_Bak(productCompoundInfo,deliveryAddressId,deliveryAddress,buyerComments,userId);

        CreateOrderVo createOrderVo = new CreateOrderVo();
        initCreateOrderVo(createOrderVo);


        CreateOrderAddressVo createOrderAddressVo = new CreateOrderAddressVo();
        initCreateOrderAddressVo(createOrderAddressVo);

        CreateOrderUserVo createOrderUserVo = new CreateOrderUserVo();
        initCreateOrderUserVo(createOrderUserVo);


        String orderId = orderCreateService.createSingleOrder(createOrderVo,createOrderAddressVo,createOrderUserVo);

        return APIResponse.success(orderId);
    }

    private void initCreateOrderVo(CreateOrderVo createOrderVo) {
        createOrderVo.setOrderNo("2017052500029");
        createOrderVo.setBuyerComments("juset test");
        createOrderVo.setInviteCode("42033101");

        List<Product4Order> product4OrderList = new ArrayList<>();
        createOrderVo.setProduct4OrderList(product4OrderList);

        for(int i = 0;i < 4; i++) {

            Product4Order product4Order = new Product4Order();

            product4Order.setProductId("201703190000000"+i);
            product4Order.setProductName("商品测试11111111111111"+i);

            product4Order.setStandardId("201703200000007"+i);
            product4Order.setStandard_must("standard_must"+i);
            product4Order.setOptional_first("12"+i);
            product4Order.setOptional_second("Optional_second"+i);

            product4Order.setCategoryId("201702230000006"+i);
            product4Order.setCategoryName("破碎锤"+i);

            product4Order.setBrandId("brand_id123456");
            product4Order.setBrandName("阿泰斯特");

            product4Order.setPrice(new BigDecimal(99.2));
            //product4Order.setFontMoneyRate(20);
            product4Order.setShowUrl("GOODS_SHOW/1490431325979532.jpg");
            product4Order.setMaxSaleNum(20);
            product4Order.setStoreNum(99);
            product4Order.setPayType(0);
            product4Order.setPrdtNum(5);
            product4Order.setUnitName("个");

            product4OrderList.add(product4Order);
        }
    }

    private void initCreateOrderAddressVo(CreateOrderAddressVo createOrderAddressVo) {
        createOrderAddressVo.setDeliveryAddressId("131312312");
        createOrderAddressVo.setConsigneeAddress("浙江省杭州市滨江区海亮大厦190211111");
        createOrderAddressVo.setConsigneeName("xxxxxxxx");
        createOrderAddressVo.setConsigneeMobile("15166668888");
        createOrderAddressVo.setConsigneeProvince("浙江省");
        createOrderAddressVo.setConsigneeCity("杭州市");
        createOrderAddressVo.setConsigneeDistrict("滨江区");
    }

    private void initCreateOrderUserVo(CreateOrderUserVo createOrderUserVo) {
        createOrderUserVo.setUserId("0000001");
        createOrderUserVo.setUserName("邱献祥");
        createOrderUserVo.setMobile("13131313");
    }



    @ApiOperation(value = "获取订单详情,包括订单购买商品条目",
            notes = "")
    @RequestMapping(value = "/order/{orderId}", method = RequestMethod.GET)
    public APIResponse<Order> getOrder(
            @ApiParam(name = "orderId", value = "订单主键", required = true)
            @PathVariable("orderId")
                    String orderId,
            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam(name = "userId", required = true)
                    String userId
    ) {

        Order order = orderBaseService.getOrder(orderId, userId);

        return APIResponse.success(order);
    }




    @ApiOperation(value = "删除订单",
            notes = "必须确保该订单是这个用户的,否则抛出异常")
    @RequestMapping(value = "/order/{orderId}", method = RequestMethod.DELETE)
    public APIResponse deleteOrder(

            @ApiParam(name = "orderId", value = "订单主键", required = true)
            @PathVariable("orderId")
                    String orderId,

            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam(value = "userId",required = true)
                    String userId

    ) {
        orderBaseService.deleteOrder(orderId,userId);
        return APIResponse.success();
    }





    @ApiOperation(value = "获取订单总额(不包含物流费)",
            notes = "用户支付费用=订单总额+物流费")
    @RequestMapping(value = "/order/{orderId}/transaction/amount", method = RequestMethod.GET)
    public APIResponse<Double> getOrderTransactionAmount(
            @ApiParam(name = "orderId", value = "订单主键", required = true)
            @PathVariable("orderId")
                    String orderId,

            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam(value = "userId",required = true)
                    String userId

    ) {
        double orderTransactionAmount = orderBaseService.getOrderTransactionAmount(orderId, userId);

        return APIResponse.success(orderTransactionAmount);
    }



    @ApiOperation(value = "获取用户支付总额(订单总额+物流费)",
            notes = "用户支付费用=订单总额+物流费")
    @RequestMapping(value = "/order/{orderId}/transaction/pay/amount", method = RequestMethod.GET)
    public APIResponse<Double> getOrderTransactionPayAmount(
            @ApiParam(name = "orderId", value = "订单主键", required = true)
            @PathVariable("orderId")
                    String orderId,

            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam(value = "userId",required = true)
                    String userId
    ) {

        double orderTransactionPayAmount = orderBaseService.getOrderTransactionPayAmount(orderId, userId);
        return APIResponse.success(orderTransactionPayAmount);
    }





    @ApiOperation(value = "获取订单交易定金",
            notes = "")
    @RequestMapping(value = "/order/{orderId}/transaction/deposit", method = RequestMethod.GET)
    public APIResponse<Double> getOrderTransactionDeposit(
            @ApiParam(name = "orderId", value = "订单主键", required = true)
            @PathVariable("orderId")
                    String orderId,

            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam(value = "userId",required = true)
                    String userId
    ) {
        Double orderTransactionDeposit = orderBaseService.getOrderTransactionDeposit(orderId, userId);

        return APIResponse.success(orderTransactionDeposit);
    }





    @ApiOperation(value = "获取订单实际支付金额",
            notes = "")
    @RequestMapping(value = "/order/{orderId}/transaction/pay/actualAmount", method = RequestMethod.GET)
    public APIResponse<Double> getOrderTransactionActualPayAmount(
            @ApiParam(name = "orderId", value = "订单主键", required = true)
            @PathVariable("orderId")
                    String orderId,

            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam(value = "userId",required = true)
                    String userId
    ) {
        Double orderTransactionActualPayAmount = orderBaseService.getOrderTransactionActualPayAmount(orderId, userId);
        return APIResponse.success(orderTransactionActualPayAmount);
    }





    @ApiOperation(value = "获取订单邮费",
            notes = "")
    @RequestMapping(value = "/order/{orderId}/transaction/postage", method = RequestMethod.GET)
    public APIResponse<Double> getOrderPostage(
            @ApiParam(name = "orderId", value = "订单主键", required = true)
            @PathVariable("orderId")
                    String orderId,

            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam(value = "userId",required = true)
                    String userId
    ) {
        Double orderPostage = orderBaseService.getOrderPostage(orderId, userId);
        return APIResponse.success(orderPostage);
    }





    @ApiOperation(value = "更新订单邮费信息",
            notes = "")
    @RequestMapping(value = "/order/{orderId}/transaction/postage", method = RequestMethod.PUT)
    public APIResponse updateOrderPostage(
            @ApiParam(name = "orderId", value = "订单主键", required = true)
            @PathVariable("orderId")
                    String orderId,

            @ApiParam(name = "postage", value = "邮费", required = true)
            @RequestParam("postage")
                    double postage,

            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam(value = "userId",required = true)
                    String userId
    ) {

        orderBaseService.updateOrderPostage(orderId,postage,userId);
        return APIResponse.success();
    }





    @ApiOperation(value = "更新订单预计发货时间描述",
            notes = "虽然是描述,但推荐直接设置日期格式字符串")
    @RequestMapping(value = "/order/{orderId}/logistics/deliveryDesc", method = RequestMethod.PUT)
    public APIResponse updateOrderExceptDeliveryDesc(
            @ApiParam(name = "orderId", value = "订单主键", required = true)
            @PathVariable("orderId")
                    String orderId,
            @ApiParam(name = "exceptDeliveryDesc", value = "预计订单发送时间(推荐字符串日期格式)", required = true)
            @RequestParam("exceptDeliveryDesc")
                    String exceptDeliveryDesc,

            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam(value = "userId",required = true)
                    String userId
    ) {

        orderBaseService.updateOrderExceptDeliveryDesc(orderId,exceptDeliveryDesc,userId);

        return APIResponse.success();
    }





    @ApiOperation(value = "更新订单余款到期时间",
            notes = "")
    @RequestMapping(value = "/order/{orderId}/transaction/balance/date", method = RequestMethod.PUT)
    public APIResponse updateOrderBalanceDate(
            @ApiParam(name = "orderId", value = "订单主键", required = true)
            @PathVariable("orderId")
                    String orderId,
            @ApiParam(name = "balanceDate", value = "余款到期时间", required = true)
            @RequestParam(value = "balanceDate",required = true)
                    int balanceDateCount,

            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam(value = "userId",required = true)
                    String userId
    ) {
        orderBaseService.updateOrderBalanceDate(orderId,balanceDateCount,userId);

        return APIResponse.success();
    }





    @ApiOperation(value = "更新订单邮费等信息",
            notes = "")
    @RequestMapping(value = "/order/{orderId}/transaction/postageAndDeliveryDate", method = RequestMethod.PUT)
    public APIResponse updateOrderPostage(
            @ApiParam(name = "orderId", value = "订单主键", required = true)
            @PathVariable("orderId")
                    String orderId,

            @ApiParam(name = "postage", value = "邮费", required = true)
            @RequestParam(value = "postage",required = true)
                   double postage,

            @ApiParam(name = "estimateDeliveryDesc", value = "预计订单发送时间(推荐字符串日期格式)", required = true)
            @RequestParam(value = "estimateDeliveryDesc",required = true)
                   String estimateDeliveryDesc,

            @ApiParam(name = "balanceDateCount", value = "余款到期时间", required = true)
            @RequestParam(value = "balanceDateCount",required = true)
                   int balanceDateCount,

            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam(value = "userId",required = true)
                    String userId
    ) {

        orderBaseService.updateOrderPostage(orderId,postage,estimateDeliveryDesc,balanceDateCount,userId);

        return APIResponse.success();
    }





    @ApiOperation(value = "更新订单备注",
            notes = "")
    @RequestMapping(value = "/order/{orderId}/remark", method = RequestMethod.PUT)
    public APIResponse updateOrderRemark(
            @ApiParam(name = "orderId", value = "订单主键", required = true)
            @PathVariable("orderId")
                    String orderId,
            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam(value = "userId",required = true)
                    String userId,

            @ApiParam(name = "remark", value = "订单备注", required = true)
            @RequestParam(value = "remark",required = true)
                    String remark

    ) {

        orderBaseService.updateOrderRemark(orderId,userId,remark);
        return APIResponse.success();
    }





    @ApiOperation(value = "获取订单备注",
            notes = "")
    @RequestMapping(value = "/order/{orderId}/remark", method = RequestMethod.GET)
    public APIResponse<String> getOrderRemark(
            @ApiParam(name = "orderId", value = "订单主键", required = true)
            @PathVariable("orderId")
                    String orderId,

            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam(value = "userId",required = true)
                    String userId
    ) {

        orderBaseService.getOrderRemark(orderId,userId);
        return APIResponse.success();

    }




    @ApiOperation(value = "新增订单支付凭证",
            notes = "")
    @RequestMapping(value = "/order/{orderId}/pay/proof", method = RequestMethod.POST)
    public APIResponse addOrderPayProof(
            @ApiParam(name = "orderId", value = "订单主键", required = true)
            @PathVariable("orderId")
                    String orderId,

            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam(value = "userId",required = true)
                     String userId,

            @ApiParam(name = "paymentUserName", value = "付款人姓名", required = true)
            @RequestParam(value = "paymentUserName",required = false)
                         String paymentUserName,

            @ApiParam(name = "bankAccount", value = "付款银行", required = false)
            @RequestParam(value = "bankAccount",required = false)
                         String bankAccount,

            @ApiParam(name = "paymentAccount", value = "支付帐号", required = false)
            @RequestParam(value = "paymentAccount",required = false)
                         String paymentAccount,

            @ApiParam(name = "account", value = "金额", required = false)
            @RequestParam(value = "account",required = false)
                         Double amount,

            @ApiParam(name = "paymentProofSnapshot", value = "支付凭证快照", required = false)
            @RequestParam(value = "paymentProofSnapshot",required = false)
                         String paymentProofSnapshot
    ) {

        orderBaseService.addOrderPayProof(orderId,userId,paymentUserName,bankAccount,paymentAccount,amount,paymentProofSnapshot);
        return APIResponse.success();
    }





    @ApiOperation(value = "获取支付凭证列表",
            notes = "")
    @RequestMapping(value = "/order/{orderId}/pay/proofs", method = RequestMethod.GET)
    public APIResponse<List<OrderPaymentProof>> getOrderPayProof(
            @ApiParam(name = "orderId", value = "订单主键", required = true)
            @PathVariable("orderId")
                    String orderId,

            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam(value = "userId",required = true)
                    String userId

    ) {

        List<OrderPaymentProof> orderPayProof = orderBaseService.getOrderPayProof(orderId, userId);
        return APIResponse.success(orderPayProof);

    }




    @ApiOperation(value = "更新用户支付凭证",
            notes = "")
    @RequestMapping(value = "/order/{orderId}/pay/proof", method = RequestMethod.PUT)
    public APIResponse updateOrderPayProof(

            @ApiParam(name = "orderId", value = "订单主键", required = true)
            @PathVariable("orderId")
                    String orderId,

            @ApiParam(name = "payProofId", value = "支付凭证ID", required = true)
            @RequestParam(value = "payProofId",required = true)
                    String payProofId,

            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam(value = "userId",required = true)
                    String userId,

            @ApiParam(name = "paymentUserName", value = "付款人姓名", required = true)
            @RequestParam(value = "paymentUserName",required = true)
                    String paymentUserName,

            @ApiParam(name = "bankAccount", value = "银行帐号", required = false)
            @RequestParam(value = "bankAccount",required = false)
                    String bankAccount,

            @ApiParam(name = "paymentAccount", value = "支付帐号", required = false)
            @RequestParam(value = "paymentAccount",required = false)
                    String paymentAccount,

            @ApiParam(name = "account", value = "金额", required = false)
            @RequestParam(value = "account",required = false)
                    double amount,

            @ApiParam(name = "paymentProofSnapshot", value = "支付凭证快照", required = false)
            @RequestParam(value = "paymentProofSnapshot",required = false)
                    String paymentProofSnapshot
    ) {

        orderBaseService.updateOrderPayProof(payProofId,orderId,userId,paymentUserName,bankAccount,paymentAccount,amount,paymentProofSnapshot);
        return APIResponse.success();
    }





    @ApiOperation(value = "邮费是否核算",
            notes = "true[已经核算]  false[未核算]")
    @RequestMapping(value = "/order/{orderId}/transaction/postage/status", method = RequestMethod.GET)
    public APIResponse<Boolean> isPostageCalculation(
            @ApiParam(name = "orderId", value = "订单主键", required = true)
            @PathVariable("orderId")
                    String orderId,

            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam(value = "userId",required = true)
                    String userId
    ) {

        boolean postageCalculation = orderStatusService.isPostageCalculation(orderId, userId);
        return APIResponse.success(postageCalculation);
    }





    @ApiOperation(value = "支付预付款",
            notes = "")
    @RequestMapping(value = "/order/{orderId}/transaction/deposit", method = RequestMethod.PUT)
    public APIResponse payTransactionDeposit(

            @ApiParam(name = "orderId", value = "订单主键", required = true)
            @PathVariable("orderId")
                    String orderId,

            @ApiParam(name = "deposit", value = "定金", required = true)
            @RequestParam(value = "deposit",required = true)
                    double deposit,

            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam(value = "userId",required = true)
                    String userId

    ) {

        orderStatusService.payTransactionDeposit(orderId,deposit,userId);
        return APIResponse.success();
    }




    @ApiOperation(value = "支付余款",
            notes = "")
    @RequestMapping(value = "/order/{orderId}/transaction/balance", method = RequestMethod.PUT)
    public APIResponse payTransactionBalance(
            @ApiParam(name = "orderId", value = "订单主键", required = true)
            @PathVariable("orderId")
                    String orderId,

            @ApiParam(name = "balance", value = "余款", required = true)
            @RequestParam(value = "balance",required = true)
                    double balance,

            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam(value = "userId",required = true)
                    String userId
    ) {

        orderStatusService.payTransactionBalance(orderId,balance,userId);
        return APIResponse.success();
    }





    @ApiOperation(value = "全额付款",
            notes = "")
    @RequestMapping(value = "/order/{orderId}/transaction/actualAmount", method = RequestMethod.PUT)
    public APIResponse payTransactionAmount(
            @ApiParam(name = "orderId", value = "订单主键", required = true)
            @PathVariable("orderId")
                    String orderId,
            @ApiParam(name = "amount", value = "全额付款金额", required = true)
            @RequestParam(value = "amount",required = true)
                    double amount,

            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam(value = "userId",required = true)
                    String userId
    ) {

        orderStatusService.payTransactionAmount(orderId,amount,userId);

        return APIResponse.success();
    }




    @ApiOperation(value = "商家已发货",
            notes = "商家在 物流模块 记录订单的物流信息后, 更改订单状态为 [卖家已发货]")
    @RequestMapping(value = "/order/{orderId}/logistics/delivery", method = RequestMethod.PUT)
    public APIResponse toShippedProduct(
            @ApiParam(name = "orderId", value = "订单主键", required = true)
            @PathVariable("orderId")
                    String orderId,

            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam(value = "userId",required = true)
                    String userId
    ) {

        orderStatusService.toShippedProduct(orderId,userId);
        return APIResponse.success();

    }




    @ApiOperation(value = "用户已收货",
            notes = "买家在  记录订单收获后, 更改订单状态为 [用户已收货]")
    @RequestMapping(value = "/order/{orderId}/logistics/received", method = RequestMethod.PUT)
    public APIResponse toUserReceived(
            @ApiParam(name = "orderId", value = "订单主键", required = true)
            @PathVariable("orderId")
                    String orderId,

            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam(value = "userId",required = true)
                    String userId
    ) {

        orderStatusService.toUserReceived(orderId,userId);
        return APIResponse.success();
    }




    @ApiOperation(value = "用户交易成功",
            notes = "更改订单状态为 [交易成功]")
    @RequestMapping(value = "/order/{orderId}/status/success", method = RequestMethod.PUT)
    public APIResponse toOrderSuccess(
            @ApiParam(name = "orderId", value = "订单主键", required = true)
            @PathVariable("orderId")
                    String orderId,

            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam(value = "userId",required = true)
                    String userId
    ) {

        orderStatusService.toOrderSuccess(orderId,userId);
        return APIResponse.success();
    }


    @ApiOperation(value = "获取订单操作列表",
            notes = "")
    @RequestMapping(value = "/order/{orderId}/operations", method = RequestMethod.GET)
    public APIResponse<List<OrderOperationLog>> getOrderOperations(
            @ApiParam(name = "orderId", value = "订单主键", required = true)
            @PathVariable("orderId")
                    String orderId,

            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam(value = "userId",required = true)
                    String userId
    ) {


        List<OrderOperationLog> orderOperations = orderBaseService.getOrderOperations(orderId, userId);

        return APIResponse.success(orderOperations);
    }

    @ApiOperation(value = "获取订单进度条",
            notes = "")
    @RequestMapping(value = "/order/{orderId}/progress", method = RequestMethod.GET)
    public APIResponse<List<OrderOperationLog>> getOrderProgress(
            @ApiParam(name = "orderId", value = "订单主键", required = true)
            @PathVariable("orderId")
                    String orderId,

            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam(value = "userId",required = true)
                    String userId
    ) {


        List<OrderOperationLog> orderOperations = orderBaseService.getOrderProgress(orderId, userId);

        return APIResponse.success(orderOperations);
    }

    @ApiOperation(value = "获取订单操作列表",
            notes = "")
    @RequestMapping(value = "/addLogisticsVo", method = RequestMethod.PUT)
    public APIResponse test(@RequestBody AddLogisticsVo addLogisticsVo

    ) {

    	logisticsService.addLogistics(addLogisticsVo);

        return APIResponse.success();
    }

    @ApiOperation(value = "获取订单操作列表",
            notes = "")
    @RequestMapping(value = "/getLogisticsVo", method = RequestMethod.PUT)
    public APIResponse<Logistics> test2(@RequestBody GetLogisticsVo getLogisticsVo

    ) {


        return APIResponse.success(logisticsService.getLogistics(getLogisticsVo));
    }


    @ApiOperation(value = "取消订单")
    @RequestMapping(value = "/order/{orderId}/cancelOrder", method = RequestMethod.POST)
    public APIResponse cancelOrder(
            @ApiParam(name = "orderId", value = "订单主键", required = true)
            @PathVariable("orderId")
                    String orderId,

            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam(value = "userId",required = true)
                    String userId,

            @ApiParam(name = "cancelReason", value = "取消原因", required = true)
            @RequestParam(value = "cancelReason",required = true)
                String cancelReason,

            @ApiParam(name = "isCustomer", value = "是否用户取消", required = true)
            @RequestParam(value = "isCustomer", required = true, defaultValue = "true")
            boolean isCustomer
    ) {


        APIResponse apiResponse = orderCloudService.cancelOrder(orderId, userId, isCustomer,
                cancelReason);

        return apiResponse;
    }

    @ApiOperation(value = "申请退款")
    @RequestMapping(value = "/order/{orderId}/refundOrder", method = RequestMethod.POST)
    public APIResponse refundOrder(
            @ApiParam(name = "orderId", value = "订单主键", required = true)
            @PathVariable("orderId")
                    String orderId,

            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam(value = "userId",required = true)
                    String userId,

            @ApiParam(name = "cancelReason", value = "取消原因", required = true)
            @RequestParam(value = "cancelReason",required = true)
                    String cancelReason,

            @ApiParam(name = "isCustomer", value = "是否用户取消", required = true)
            @RequestParam(value = "isCustomer", required = true, defaultValue = "true")
                    boolean isCustomer

    ) {


        APIResponse apiResponse = orderCloudService.refundOrder(orderId, userId, isCustomer,
                cancelReason);

        return apiResponse;
    }


    @SuppressWarnings({ "unchecked", "rawtypes" })
    @ApiOperation(value = "申请退款 完成")
    @RequestMapping(value = "/order/{orderId}/refundOrderComplete", method = RequestMethod.POST)
    public APIResponse refundOrderComplete(

            @ApiParam(name = "orderId", value = "订单主键", required = true)
            @PathVariable("orderId")
                    String orderId,

            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam(value = "userId",required = true)
                    String userId

    ) {

        orderStatusService.refundOrderComplete(orderId);

        return APIResponse.success();
    }

    @ApiOperation(value = "获取订单状态的")
    @RequestMapping(value = "/order/count", method = RequestMethod.GET)
    public APIResponse getOrderCount(

            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam(value = "userId",required = true)
                String userId

    ) {

        APIResponse apiResponse = orderCloudService.getOrderCount(userId);

        return apiResponse;
    }


    @ApiOperation(value = "获取后台管理员订单状态的数量")
    @RequestMapping(value = "/order/manager/count", method = RequestMethod.GET)
    public APIResponse getManagerOrderCount() {

        APIResponse apiResponse = orderCloudService.getManagerOrderCount();

        return apiResponse;
    }

}

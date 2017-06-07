package com.hjh.mall.order.service.impl;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hjh.mall.order.api.OrderCloudService;
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
import com.hjh.mall.order.vo.AddLogisticsVo;
import com.hjh.mall.order.vo.CreateOrderAddressVo;
import com.hjh.mall.order.vo.CreateOrderUserVo;
import com.hjh.mall.order.vo.CreateOrderVo;
import com.hjh.mall.order.vo.GetLogisticsVo;
import com.hjh.mall.order.vo.QueryOrderVo;

/**
 * Created by qiuxianxiang on 17/5/19.
 */

@Service("orderCloudService")
public class OrderCloudServiceImpl implements OrderCloudService {

    @Autowired
    private OrderBaseService orderBaseService;

    @Autowired
    private OrderStatusService orderStatusService;

    @Autowired
    private OrderCreateService orderCreateService;
    
    @Autowired
    private LogisticsService logisticsService;


    @ApiOperation(value = "查询订单列表", notes = "条件之间是\"AND\"关系")
    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public APIResponse<QueryResult<Order>> listOrder(
            QueryOrderVo queryOrderVo
    ) {

        QueryResult<Order> orderQueryResult = orderBaseService.listOrder(queryOrderVo);

        return APIResponse.success(orderQueryResult);
    }

    @Override
    public APIResponse<String> createSingleOrder(
            CreateOrderVo createOrderVo,
            CreateOrderAddressVo createOrderAddressVo,
            CreateOrderUserVo createOrderUserVo
    ) {

        String orderId =orderCreateService.createSingleOrder(createOrderVo,createOrderAddressVo,createOrderUserVo);

        return APIResponse.success(orderId);
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
                    Double postage,

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

            @ApiParam(name = "balanceDateCount", value = "余款到期时间", required = true)
            @RequestParam(value = "balanceDateCount",required = true)
                    Integer balanceDateCount,

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
                    Double postage,

            @ApiParam(name = "estimateDeliveryDesc", value = "预计订单发送时间(推荐字符串日期格式)", required = true)
            @RequestParam(value = "estimateDeliveryDesc",required = true)
                    String estimateDeliveryDesc,

            @ApiParam(name = "balanceDateCount", value = "余款到期时间", required = true)
            @RequestParam(value = "balanceDateCount",required = true)
                    Integer balanceDateCount,



            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam(value = "userId",required = true)
                    String userId
    ) {

        orderBaseService.updateOrderPostage(orderId,postage,estimateDeliveryDesc,balanceDateCount,userId);

        return APIResponse.success();
    }

    @Override
    public void updateOrderDeliveryDate(String orderId, Date deliveryDate) {
        orderBaseService.updateOrderDeliveryDate(orderId,deliveryDate);
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
            @RequestParam(value = "paymentUserName",required = true)
                    String paymentUserName,

            @ApiParam(name = "bankAccount", value = "支付银行帐号", required = false)
            @RequestParam(value = "bankAccount",required = false)
                    String bankAccount,

            @ApiParam(name = "paymentAccount", value = "支付帐号", required = false)
            @RequestParam(value = "paymentAccount",required = false)
                    String paymentAccount,

            @ApiParam(name = "amount", value = "金额", required = false)
            @RequestParam(value = "amount",required = false)
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

            @ApiParam(name = "amount", value = "金额", required = false)
            @RequestParam(value = "amount",required = false)
                    Double amount,

            @ApiParam(name = "paymentProofSnapshot", value = "支付凭证快照", required = false)
            @RequestParam(value = "paymentProofSnapshot",required = false)
                    String paymentProofSnapshot
    ) {

        orderBaseService.updateOrderPayProof(payProofId, orderId,userId,paymentUserName,bankAccount,paymentAccount,amount,paymentProofSnapshot);
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
            notes = "true[已经核算]  false[未核算]")
    @RequestMapping(value = "/order/{orderId}/transaction/deposit", method = RequestMethod.PUT)
    public APIResponse payTransactionDeposit(

            @ApiParam(name = "orderId", value = "订单主键", required = true)
            @PathVariable("orderId")
                    String orderId,

            @ApiParam(name = "deposit", value = "定金", required = true)
            @RequestParam(value = "deposit",required = true)
                    Double deposit,

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
                    Double balance,

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
                    Double amount,

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


    @Override
    @ApiOperation(value = "获取订单操作列表",
            notes = "")
    @RequestMapping(value = "/order/{orderId}/operations", method = RequestMethod.PUT)
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

    @Override
    public APIResponse<List<OrderOperationLog>> getOrderProgress(String orderId, String userId) {
        List<OrderOperationLog> orderOperations = orderBaseService.getOrderProgress(orderId, userId);

        return APIResponse.success(orderOperations);
    }

    @Override
    public APIResponse cancelOrder(String orderId, String userId, boolean isCustomer, String cancelReason) {
        orderStatusService.cancelOrder(orderId,userId,isCustomer,cancelReason);
        return APIResponse.success();
    }


    @Override
    public APIResponse refundOrder(String orderId, String userId, boolean isCustomer, String cancelReason) {
        orderStatusService.refundOrder(orderId,userId,isCustomer,cancelReason);
        return APIResponse.success();
    }

//    @Override
//    public APIResponse refundOrderComplete(String orderId) {
//        orderStatusService.refundOrderComplete(orderId);
//        return APIResponse.success();
//    }

    @Override
    public APIResponse returnProducts(String orderId, String userId, String cancelReason) {
        orderStatusService.returnProducts(orderId,userId,cancelReason);
        return APIResponse.success();
    }

    @Override
    public APIResponse returnProductsComplete(String orderId) {
        orderStatusService.returnProductsComplete(orderId);
        return APIResponse.success();
    }

	//@Override
	public APIResponse getOrderCount(String userId) {
		return APIResponse.success(orderBaseService.getOrderCount(userId));

	}

    @Override
    public APIResponse<Map<String, Object>> getManagerOrderCount() {
        return APIResponse.success(orderBaseService.getManagerOrderCount());
    }


    @Override
	public  APIResponse<Logistics> getLogistics(GetLogisticsVo getLogisticsVo) {
		Logistics logistics = logisticsService.getLogistics(getLogisticsVo);
		return APIResponse.success(logistics);
	}

	@Override
	public APIResponse addLogistics(AddLogisticsVo addLogisticsVo) {
		logisticsService.addLogistics(addLogisticsVo);		
		return APIResponse.success();
	}


    @Override
    public APIResponse updateRefundingInfo(String orderId, double refundingAmount, String refundingExplain) {

        // 1. 设置退款信息 , 并 更新订单状态
        orderBaseService.updateRefundingInfo(orderId,refundingAmount,refundingExplain);


        return APIResponse.success();
    }

    @Override
    public APIResponse undoRefundOrder(String orderId, String userId) {
        orderStatusService.undoRefundOrder(orderId,userId);
        return APIResponse.success();
    }


}

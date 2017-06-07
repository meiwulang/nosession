package com.hjh.mall.order.api;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hjh.mall.order.dto.Logistics;
import com.hjh.mall.order.dto.Order;
import com.hjh.mall.order.dto.OrderOperationLog;
import com.hjh.mall.order.dto.OrderPaymentProof;
import com.hjh.mall.order.dto.QueryResult;
import com.hjh.mall.order.dto.common.APIResponse;
import com.hjh.mall.order.vo.AddLogisticsVo;
import com.hjh.mall.order.vo.CreateOrderAddressVo;
import com.hjh.mall.order.vo.CreateOrderUserVo;
import com.hjh.mall.order.vo.CreateOrderVo;
import com.hjh.mall.order.vo.GetLogisticsVo;
import com.hjh.mall.order.vo.QueryOrderVo;

/**
 * Created by qiuxianxiang on 17/5/18.
 */
public interface OrderCloudService {


    public APIResponse<QueryResult<Order>> listOrder(QueryOrderVo queryOrderVo) ;



    public APIResponse<String> createSingleOrder(
            CreateOrderVo createOrderVo,
            CreateOrderAddressVo createOrderAddressVo,
            CreateOrderUserVo createOrderUserVo
    );


    public APIResponse<Order> getOrder(
                    String orderId,
                    String userId
    ) ;



    public APIResponse deleteOrder(
                    String orderId,
                    String userId

    );





    public APIResponse<Double> getOrderTransactionAmount(
                    String orderId,
                    String userId
    ) ;



    public APIResponse<Double> getOrderTransactionPayAmount(
                    String orderId,
                    String userId
    ) ;





    public APIResponse<Double> getOrderTransactionDeposit(
                    String orderId,

                    String userId
    ) ;





    public APIResponse<Double> getOrderTransactionActualPayAmount(
                    String orderId,

                    String userId
    ) ;





    public APIResponse<Double> getOrderPostage(
                    String orderId,

                    String userId
    ) ;





    public APIResponse updateOrderPostage(
                    String orderId,

                    Double postage,

                    String userId
    ) ;





    public APIResponse updateOrderExceptDeliveryDesc(
                    String orderId,
                    String exceptDeliveryDesc,

                    String userId
    ) ;





    public APIResponse updateOrderBalanceDate(
                    String orderId,
                    Integer balanceDate,

                    String userId
    ) ;





    public APIResponse updateOrderPostage(
                    String orderId,

                    Double postage,

                    String estimateDeliveryDesc,

                    Integer balanceDate,

                    String userId
    ) ;



    /**
     * 设置发货时间
     * @param orderId
     * @param deliveryDate
     */
    public void updateOrderDeliveryDate(String orderId, Date deliveryDate);



    public APIResponse updateOrderRemark(
                    String orderId,
                    String userId,

                    String remark

    ) ;





    public APIResponse<String> getOrderRemark(
                    String orderId,

                    String userId
    ) ;




    public APIResponse addOrderPayProof(
                    String orderId,

                    String userId,

                    String paymentUserName,

                    String bankAccount,

                    String paymentAccount,

                    Double amount,

                    String paymentProofSnapshot
    ) ;





    public APIResponse<List<OrderPaymentProof>> getOrderPayProof(
                    String orderId,

                    String userId

    ) ;




    public APIResponse updateOrderPayProof(

                    String orderId,

                    String payProofId,

                    String userId,

                    String paymentUserName,

                    String bankAccount,

                    String paymentAccount,

                    Double amount,

                    String paymentProofSnapshot
    ) ;




    public APIResponse<Boolean> isPostageCalculation(
                    String orderId,

                    String userId
    ) ;




    public APIResponse payTransactionDeposit(

                    String orderId,

                    Double deposit,

                    String userId

    ) ;



    public APIResponse payTransactionBalance(
                    String orderId,

                    Double balance,

                    String userId
    ) ;





    public APIResponse payTransactionAmount(
                    String orderId,
                    Double amount,

                    String userId
    ) ;



    public APIResponse toShippedProduct(
                    String orderId,

                    String userId
    ) ;



    public APIResponse toUserReceived(
                    String orderId,

                    String userId
    ) ;




    public APIResponse toOrderSuccess(
                    String orderId,

                    String userId
    ) ;



    public APIResponse<List<OrderOperationLog>> getOrderOperations(
                    String orderId,
                    String userId
    );

    public APIResponse<List<OrderOperationLog>> getOrderProgress(
            String orderId,
            String userId
    );



    /**
     * 取消订单
     * 业务逻辑实现:
     *    1. 确定订单属于该用户,否则抛出异常
     *    2. 检查订单是否可取消,否则抛出异常
     *    3. 取消订单
     *    4. 记录日志
     *    5. 返回
     *
     * @param orderId           订单ID
     * @param userId            用户ID
     * @param isCustomer        是否为用户取消订单  true 是   false [商家取消]
     * @param cancelReason      取消原因[可选]
     */
    public APIResponse cancelOrder(String orderId, String userId, boolean isCustomer, String cancelReason);
    
    /**
     * 获取每种订单类型的数量
     *
             核算运费(没有核算的订单)数量
             待付款(核算运费但未付款、已付定金，等待开始付余款，等待付余款)
             待收货（订单已经付款，等待商家发货，商家已经发货）
             退款中
     *
     *
     * @param userId
     * @return
     */
    public APIResponse getOrderCount(String userId);

    public APIResponse<Map<String, Object>> getManagerOrderCount();

    //申请退款
    public APIResponse refundOrder(String orderId, String userId, boolean isCustomer, String cancelReason);

    // 申请退款 完成  请调用 updateRefundingInfo
    //public APIResponse refundOrderComplete(String orderId);

    //申请退货、退款
    public APIResponse returnProducts(String orderId, String userId, String cancelReason);

    //申请退货、退款 完成
    public APIResponse returnProductsComplete(String orderId);
    
    
    /** 
     * @date 2017年5月24日
     * @Description: 获得物流详情
     * @author 杨益桦
     * @param getLogisticsVo
     * @return
     * Logistics
     */
    APIResponse<Logistics> getLogistics(GetLogisticsVo getLogisticsVo);

    /** 
     * @date 2017年5月24日
     * @Description: 添加物流信息
     * @author 杨益桦
     * @param addLogisticsVo
     * void
     */
    APIResponse addLogistics(AddLogisticsVo addLogisticsVo);


    /**
     * 更新订单 退款信息
     * @param orderId
     * @param refundingAmount
     * @param refundingExplain
     */
    public APIResponse updateRefundingInfo(
            String orderId,
            double refundingAmount,
            String refundingExplain);


    public APIResponse undoRefundOrder(String orderId,String userId);
}

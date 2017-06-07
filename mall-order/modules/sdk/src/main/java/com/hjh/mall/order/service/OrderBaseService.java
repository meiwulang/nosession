package com.hjh.mall.order.service;

import com.hjh.mall.order.dto.Order;
import com.hjh.mall.order.dto.OrderOperationLog;
import com.hjh.mall.order.dto.OrderPaymentProof;
import com.hjh.mall.order.dto.QueryResult;
import com.hjh.mall.order.vo.QueryOrderVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by qiuxianxiang on 17/5/8.
 */
public interface OrderBaseService {


    public QueryResult<Order> listOrder(QueryOrderVo queryOrderVo);



    /**
     * 获取订单详情,包括订单购买商品条目 (级联 T_ORDER_ITEM)
     * @param orderId       订单主键
     * @return              订单详情
     */
    public Order getOrder(String orderId,String userId);


    /**
     * 逻辑删除订单
     * 业务逻辑:
     *      1. 确定改订单可删除,否则抛出异常[ 1)订单状态为 待付款、  ]
     *      2. 记录日志
     *      3. 返回
     *
     * @param orderId
     * @param userId
     */
    public void deleteOrder(String orderId, String userId);


    /**
     * 获取订单总额(用户支付费用=订单总额+物流费)
     * @param orderId       订单ID
     * @return              订单总金额
     */
    public Double getOrderTransactionAmount(String orderId,String userId);


    /**
     * 获取订单总额(用户支付费用=订单总额+物流费)
     * @param orderId       订单ID
     * @return              订单总金额
     */
    public Double getOrderTransactionPayAmount(String orderId,String userId);

    /**
     * 获取订单交易定金
     * @param orderId       订单ID
     * @return              交易金额
     */
    public Double getOrderTransactionDeposit(String orderId,String userId);


    /**
     * 获取订单实际支付金额
     * @param orderId   订单ID
     * @return          实际支付金额
     */
    public Double getOrderTransactionActualPayAmount(String orderId,String userId);


    /**
     * 获取订单邮费
     * @param orderId   订单ID
     * @return          邮费
     */
    public Double getOrderPostage(String orderId,String userId);


    /**
     * 更新订单邮费信息
     * @param orderId   订单ID
     * @param postage   邮费
     * @return
     */
    public void updateOrderPostage(String orderId,
                                          double postage,String userId
    );

    /**
     *  更新订单预计发货时间
     * @param orderId               订单ID
     * @param estimateDeliveryDesc  预计订单发送时间(推荐字符串日期格式)
     * @return
     */
    public void updateOrderExceptDeliveryDesc(String orderId,
                                          String estimateDeliveryDesc,String userId
    );

    /**
     * 更新订单余款到期时间
     * @param orderId           订单ID
     * @param balanceDateCount       余款到期时间
     * @return
     */
    public void updateOrderBalanceDate(String orderId,
                                       Integer balanceDateCount,String userId
    );

    /**
     * 更新订单邮费等信息
     * @param orderId                   订单ID
     * @param postage                   邮费
     * @param estimateDeliveryDesc      预计订单发送时间(推荐字符串日期格式)
     * @param balanceDateCount               余款到期时间
     * @return
     */
    public void updateOrderPostage(String orderId,
                                          Double postage,
                                          String estimateDeliveryDesc,
                                          Integer balanceDateCount,
                                          String userId
    );


    /**
     * 设置发货时间
     * @param orderId
     * @param deliveryDate
     */
    public void updateOrderDeliveryDate(String orderId, Date deliveryDate);


    /**
     * 更新订单备注
     * @param orderId   订单ID
     * @param remark    订单备注
     */
    public void updateOrderRemark(String orderId, String userId, String remark);

    /**
     * 获取订单备注
     * @param orderId   订单ID
     * @return          订单备注
     */
    public String getOrderRemark(String orderId,String userId);


    /**
     * 新增订单支付凭证
     * @param orderId               订单ID
     * @param userId                用户ID
     * @param paymentUserName       付款人姓名
     * @param bankAccount           银行帐号
     * @param paymentAccount        支付帐号
     * @param amount               帐号
     * @param paymentProofSnapshot  支付凭证快照
     * @return
     */
    public void addOrderPayProof(String orderId,
                                        String userId,
                                        String paymentUserName,
                                        String bankAccount,
                                        String paymentAccount,
                                        Double amount,
                                        String paymentProofSnapshot
    );


    /**
     * 获取支付凭证列表
     * @param orderId       订单ID
     * @param userId        用户ID
     * @return
     */
    public List<OrderPaymentProof> getOrderPayProof(String orderId, String userId);


    /**
     * 更新用户支付凭证
     * @param orderId               订单ID
     * @param userId                用户ID
     * @param paymentUserName       付款人姓名
     * @param bankAccount           银行帐号
     * @param paymentAccount        支付帐号
     * @param amount               帐号
     * @param paymentProofSnapshot  支付凭证快照
     */
    public void updateOrderPayProof(String payProofId,
                                    String orderId,
                                           String userId,
                                           String paymentUserName,
                                           String bankAccount,
                                           String paymentAccount,
                                            Double amount,
                                           String paymentProofSnapshot
    );


    /**
     * 获取每种订单类型的数量
     *
             核算运费数量:   未付款、且未核算运费                                status = 1  运费等待核算
             待付款:        未付款或已付定金、且已经核算运费 (核算运费但未付款、已付定金，等待开始付余款，等待付余款)
             待收货:        买家已付款、商家已经发货
             退款中:        退款中
     *
     *
     * @param userId
     * @return
     */
    public List<Map<String,Object>> getOrderCount(String userId);


    public Map<String,Object> getManagerOrderCount();


    public List<OrderOperationLog> getOrderOperations(String orderId, String userId);

    public List<OrderOperationLog> getOrderProgress(String orderId, String userId);




    public void updateRefundingInfo(
            String orderId,
            double refundingAmount,
            String refundingExplain);

}

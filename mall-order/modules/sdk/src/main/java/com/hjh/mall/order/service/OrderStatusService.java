package com.hjh.mall.order.service;




/**
 * Created by qiuxianxiang on 17/5/11.
 */
public interface OrderStatusService {


        /**
         * 邮费是否核算
         * @param orderId       订单ID
         * @return              true[已经核算]  false[未核算]
         */
        public boolean isPostageCalculation(String orderId,String userId);


        /**
         * 支付预付款
         *
         * 业务逻辑
         *  0. 检查订单支付类型  否者抛出异常
         *  1. 检查订单状态 是否为[待付款], 否者抛出异常
         *  2. 更新 TRANSACTION_ACTUAL_PAY_AMOUNT 字段
         *  2. 更新订单状态为 [定金已付]    调用updateToPaidPart()方法
         *  3. 记录日志
         *
         * @param orderId       订单ID
         * @param deposit       定金
         */
        public void payTransactionDeposit(String orderId, Double deposit,String userId);


        /**
         * 支付余款
         *
         * 业务逻辑
         *   0. 检查订单支付类型 否者抛出异常
         *   1. 检查订单状态 是否[定金已付], 否者抛出异常
         *   2. 更新 TRANSACTION_ACTUAL_PAY_BALANCE 字段
         *   3. 更新订单为 [等待卖家发货 or 买家已付款] 状态
         *   4. 记录日志
         *
         * @param orderId       订单ID
         * @param balance       余款
         */
        public void payTransactionBalance(String orderId, Double balance,String userId);

        /**
         * 全额付款
         * 业务逻辑
         *    0. 检查 订单类型[全额付款] 参考 TransactionType  否者抛出异常
         *    1. 检查订单状态 是否为[待付款], 否者抛出异常
         *    2. 更新 TRANSACTION_ACTUAL_PAY_AMOUNT 字段
         *    3. 记录日志
         *
         * @param orderId       订单ID
         * @Param amount        全额付款金额
         */
        public void payTransactionAmount(String orderId, Double amount,String userId);


        /**
         * 商家已发货
         * 商家在 物流模块 记录订单的物流信息后, 更改订单状态为 [卖家已发货]
         *
         * 业务逻辑
         *   1. 检查订单状态是否为 [等待卖家发货],否则抛出异常
         *   2. 更改订单状态为 [卖家已发货]
         *   3. 记录日志
         *
         * @param orderId
         */
        public void toShippedProduct(String orderId,String userId);

        /**
         * 用户已收货
         *
         * 业务逻辑:
         *   1.检查订单状态是否为 [卖家已发货],否则抛出异常
         *   2.更改订单状态为 [已确认收货]
         *   3. 记录日志
         *
         * @param orderId
         */
        public void toUserReceived(String orderId,String userId);


        /**
         * 用户交易成功
         *
         * 业务逻辑:
         *   1.检查订单状态是否为 [已确认收货],否则抛出异常
         *   2.更改订单状态为 [交易成功]
         *   3. 记录日志
         *
         * @param orderId
         */
        public void toOrderSuccess(String orderId,String userId);


//================以上为正常订单流程==================以下为异常订单流程=====================

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
        public void cancelOrder(String orderId, String userId, boolean isCustomer, String cancelReason);

        //申请退款
        public void refundOrder(String orderId, String userId, boolean isCustomer, String cancelReason);

        /**
         * 撤销退款
         * @param orderId
         * @param userId
         */
        public void undoRefundOrder(String orderId,String userId);

        // 申请退款 完成
        public void refundOrderComplete(String orderId);

        //申请退货、退款
        public void returnProducts(String orderId, String userId, String cancelReason);

        //申请退货、退款 完成
        public void returnProductsComplete(String orderId);



        

}

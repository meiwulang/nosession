package com.hjh.mall.order.constants;

/**
 * Created by qiuxianxiang on 17/5/15.
 */
public interface Errors {

    interface Common {
        String ORDER_NOT_MATCH_USER="order10001";  // 订单用户不匹配
    }

    interface Order {
        String ORDER_DELETE_ERROR = "order20001";   // 订单删除的时候,状态错误
        String ORDER_PAYMENT_TYPE_ERROR ="order20002";       // 支付类型错误
        String ORDER_PAYMENT_STATUS_ERROR="order20003";       // 支付状态错误
        String ORDER_REFUND_AMOUNT_ERROR="order20010";            //  退款金额超过用户实际支付金额

        String ORDER_PAYMENT_BALANCE_DATE_ERROR="order20009";  // 未超出余款延期时间,不能提前支付余款
        String ORDER_STATUS_ERROR="order20004";            //  订单状态错误


        String ORDER_CREATE_PRODUCT_NULL="order20005";   // 创建订单商品列表为空
        String ORDER_CREATE_PRODUCT_LIMIT="order20006";   // 创建订单商品超过最大采购量
        String ORDER_CREATE_BRAND_ERROR="order20007";   // 商品列表不能合单,因为不是同一种品牌
        String ORDER_CREATE_TRANSACTION_TYPE_ERROR="order20008";   // 商品列表不能合单,因为不是同一种支付类型

    }

}

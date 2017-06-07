package com.hjh.mall.order.constants;

/**
 * Created by qiuxianxiang on 17/5/11.
 */
public enum OrderStatus {

        //ALL(0, "全部", "全部"),
        NON_PAYMENT(1, "未付款", "等待买家付款"),
        PART_PAID(2, "定金已付", "定金已付"),
        UN_DELIVERY(3, "等待卖家发货", "买家已付款"),
        DELIVERED(4, "卖家已发货", "卖家已发货"),
        RECEIVED(5, "已确认收货", "已确认收货"),
        SUCCESS(6,"交易成功","交易成功"),

        CLOSED_BUYER_CANCEL(7,"交易关闭-买家取消订单","交易关闭-买家取消订单"),
        CLOSED_SELLER_CANCEL(8,"交易关闭-卖家取消订单","交易关闭-卖家取消订单"),
        REFUNDING(9,"退款中","退款中"),
        RETURNING_GOODS(10,"退货退款中","退货退款中"),
        CLOSED_REFUNDED(11,"交易关闭-退款","交易关闭-退款"),
        CLOSED_RETURN_GOODS(12,"交易关闭-退货退款","交易关闭-退货退款"),
        CLOSED_EXCEPTION(13,"交易关闭-订单异常","交易关闭-订单异常")
        ;

        private int code;
        private String customerDescription;
        private String shopDescription;

        OrderStatus(int code, String customerDescription, String shopDescription) {
            this.code = code;
            this.customerDescription = customerDescription;
            this.shopDescription = shopDescription;
        }

        public Integer getCode() {
            return this.code;
        }

        public String getDescription() {
            return this.customerDescription;
        }

        public String getCustomerDescription() {
            return this.customerDescription;
        }

        public String getShopDescription() {
            return this.shopDescription;
        }


        public static String getCustomerDescription(int code) {
            for (OrderStatus orderStatus: OrderStatus.values()) {
                if (orderStatus.code == code) {
                    return orderStatus.customerDescription;
                }
            }
            throw new IllegalArgumentException("Argument out of range");
        }

        public static String getShopDescription(int code) {
            for (OrderStatus orderStatus: OrderStatus.values()) {
                if (orderStatus.code == code) {
                    return orderStatus.shopDescription;
                }
            }
            throw new IllegalArgumentException("Argument out of range");
        }


        public static OrderStatus getEnum(int code) {
            for (OrderStatus orderStatus: OrderStatus.values()) {
                if (orderStatus.code == code) {
                    return orderStatus;
                }
            }
            throw new IllegalArgumentException("No matching type enum");
        }


}

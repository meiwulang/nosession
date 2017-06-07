package com.hjh.mall.order.constants;

/**
 * Created by qiuxianxiang on 17/5/11.
 * 	//ALL(0, "全部", "全部"),
    NON_PAYMENT(1, "运费", "等待买家付款"),
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
 */
public enum OrderStatusCategory {

	
		//ALL(0, "全部", "全部"),
		CHECKING_REIGHT("1","1", "核算运费", "未付款、且未核算运费"),
		PENDING_PAYMENT("2","1,2", "待付款", "未付款或已付定金、且已经核算运费 (核算运费但未付款、已付定金，等待开始付余款，等待付余款"),
		GOODS_NOT_RECEIVED("3","1,2,3,4", "待收货", "买家已付款、商家已经发货"),
		REFUND("4","9", "退款中", "退款中")
        ;


        private String code;
        private String statutes;
        private String description;
        private String remark;

        OrderStatusCategory(String code, String statutes,String description, String remark) {
            this.code = code;
            this.description = description;
            this.remark = remark;
            this.statutes = statutes;
        }

        public String getCode() {
            return this.code;
        }

        public String getDescription() {
            return this.description;
        }

        public String getRemark() {
            return this.remark;
        }

        public String getShopDescription() {
            return this.description;
        }


        public String getStatutes() {
			return statutes;
		}

		public static String getDescription(String code) {
            for (OrderStatusCategory orderStatus: OrderStatusCategory.values()) {
                if (orderStatus.code == code) {
                    return orderStatus.description;
                }
            }
            throw new IllegalArgumentException("Argument out of range");
        }

        public static String getRemark(String code) {
            for (OrderStatusCategory orderStatus: OrderStatusCategory.values()) {
                if (orderStatus.code.equals(code)) {
                    return orderStatus.remark;
                }
            }
            throw new IllegalArgumentException("Argument out of range");
        }


        public static OrderStatusCategory getEnum(String code) {
            for (OrderStatusCategory orderStatus: OrderStatusCategory.values()) {
                if (orderStatus.code.equals(code)) {
                    return orderStatus;
                }
            }
            throw new IllegalArgumentException("No matching type enum");
        }


}

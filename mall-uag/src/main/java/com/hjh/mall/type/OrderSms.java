package com.hjh.mall.type;

public enum OrderSms {
	
	/**
     * 您的订单${orderId}运费已核算您可以付款啦，打开 好机惠 进入“我的”-待付款，找到该订单付款。
     */
	ORDER_PER_PAYMENT(1,"SMS_68110197"),
	
	/**
	 * 订单${orderId}已付定金，请确认收款。
     * 提醒运营定金已付，对定金审核（运营固定手机号）
     */
    ORDER_TO_EXAMINE(2, "SMS_68090247"),
    
    /**
     * 订单${orderId}已付款完成，请确认收款。
     * 提醒用户余款或者全款付款完成,确认收款（运营固定手机号）
     */
    ORDER_RECEIVABLES(3, "SMS_68115202"),
    
    /**
     * 订单${orderId}已申请退款，请退款确认。
     * 提醒运营进行退款确认（运营固定手机号）
     */
    ORDER_APPLY_REFUND(4, "SMS_68085203"),
    
    /**
     * 您的订单${orderId}已发货，打开 好机惠 进入“我的”-已发货 查看发货信息。
     * 提醒用户商品已发货，进入我的订单当中查看
     */
    ORDER_DELIVER_GOODS(5, "SMS_68065393"),
    
    /**
     * 您的订单${orderId}已完成，打开好机惠进入“我的”-全部订单 查看订单信息。
     * 提醒用户订单交易已成功，进入我的订单当中查看
     */    
    ORDER_COMPLETE(6, "SMS_68035169"),
    
    /**
     * 您的订单${orderId}退款完成，打开好机惠进入“我的”-全部订单 查看订单信息。
     * 提醒用户订单退款成功，进入我的订单当中查看
     */
    ORDER_REFUND_COMPLETE(7, "SMS_68160239"),
    
    /**
     * 您的订单${orderId}需要付余款，打开好机惠进入“我的”-待付款  查看订单信息。
     * 提醒用户订单需要付余款，进入我的订单当中查看
     */
    
    ORDER_PAY_BALANCE(8, "SMS_68060214"),
    
    ;
	
    private final int val;
    
    private final String description;
    
    private String toString;
    
    private OrderSms(int val, String description) {
        this.val = val;
        this.description = description;
    }
    
    public int getVal() {   
        return val;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        if (null == toString) {
            toString = new StringBuilder().append("OrderSms[").append(val).append(':').append(description)
                    .append(']').toString();
        }
        return toString;
    }

}

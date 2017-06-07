package com.hjh.mall.order.constants;

/**
 * Created by qiuxianxiang on 17/5/11.
 */
public enum OrderDelete {

    NORMAL(0),    // 订单未删除
    //CUSTOMER_DELETE(1),    // 用户删除订单(商户对该订单仍可见)
    DELETE(2)              // 商户删除订单(该订单永久逻辑删除,用户也不可见)
    ;


    private int code;

    private OrderDelete(int code) {
        this.code = code;
    }


    public Integer getCode() {
        return this.code;
    }

    public static OrderDelete getEnum(int code) {
        for (OrderDelete orderDelete: OrderDelete.values()) {
            if (orderDelete.code == code) {
                return orderDelete;
            }
        }
        throw new IllegalArgumentException("No matching type enum");
    }

}

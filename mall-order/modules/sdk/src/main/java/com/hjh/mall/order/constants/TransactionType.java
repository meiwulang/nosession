package com.hjh.mall.order.constants;

/**
 * Created by qiuxianxiang on 17/5/11.
 */
public enum TransactionType {

    NORMAL(0),    // 普通交易,全额付款
    DEPOSIT(1)    // 预付款交易
    ;


    private int code;

    private TransactionType(int code) {
        this.code = code;
    }


    public Integer getCode() {
        return this.code;
    }

    public static TransactionType getEnum(int code) {
        for (TransactionType transactionType: TransactionType.values()) {
            if (transactionType.code == code) { 
                return transactionType;
            }
        }
        throw new IllegalArgumentException("No matching type enum");
    }


}

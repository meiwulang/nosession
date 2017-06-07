package com.hjh.mall.vo.order.manager;


import com.hjh.mall.common.core.vo.HJYVO;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by qiuxianxiang on 17/5/27.
 */
public class RefundOrderVo extends HJYVO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @NotBlank
    @ApiModelProperty(name = "orderId", value = "订单主键", required = true)
    private String orderId;

    @NotNull
    @ApiModelProperty(name = "refundingAmount", value = "退款金额", required = true)
    private Double refundingAmount;

    @NotNull
    @Length(min = 1, max = 20)
    @ApiModelProperty(name = "cancelReason", value = "退款说明", required = false)
    private String refundingExplain;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Double getRefundingAmount() {
        return refundingAmount;
    }

    public void setRefundingAmount(Double refundingAmount) {
        this.refundingAmount = refundingAmount;
    }

    public String getRefundingExplain() {
        return refundingExplain;
    }

    public void setRefundingExplain(String refundingExplain) {
        this.refundingExplain = refundingExplain;
    }
}

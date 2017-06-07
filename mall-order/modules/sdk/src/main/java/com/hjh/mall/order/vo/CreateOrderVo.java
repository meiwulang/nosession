package com.hjh.mall.order.vo;

import com.hjh.mall.bizapi.biz.goods.middle.entity.Product4Order;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qiuxianxiang on 17/5/25.
 */
public class CreateOrderVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String orderNo;
    private List<Product4Order> product4OrderList;
    private String buyerComments;
    private String inviteCode;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public List<Product4Order> getProduct4OrderList() {
        return product4OrderList;
    }

    public void setProduct4OrderList(List<Product4Order> product4OrderList) {
        this.product4OrderList = product4OrderList;
    }

    public String getBuyerComments() {
        return buyerComments;
    }

    public void setBuyerComments(String buyerComments) {
        this.buyerComments = buyerComments;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }
}

package com.hjh.mall.order.vo;

import java.io.Serializable;

/**
 * Created by qiuxianxiang on 17/5/25.
 */
public class CreateOrderUserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String userName;

    private String mobile;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}

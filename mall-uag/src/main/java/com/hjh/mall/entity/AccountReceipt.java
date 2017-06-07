package com.hjh.mall.entity;

import java.io.Serializable;

import com.hjh.mall.common.core.entity.Updatable;

public class AccountReceipt extends Updatable implements Serializable {
    // 账号id 
    private String account_id;

    // logo 
    private String account_logo;

    // 开户名 
    private String account_name;

    // 银行名称 
    private String bank_name;

    // 开户行 
    private String account_bank_name;

    // 银行账号 
    private String account_bank_no;

    // 手机号 
    private String mobile;

    // 是否显示 
    private String app_display;

    // 排序 
    private Integer sort;

    private String create_time;

    private String create_date;

    // 创建人id 
    private String create_user;

    // 添加人name 
    private String create_user_name;

    private String update_time;

    private String update_date;

    // 修改人名字 
    private String update_user_name;

    private static final long serialVersionUID = 1L;

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getAccount_logo() {
        return account_logo;
    }

    public void setAccount_logo(String account_logo) {
        this.account_logo = account_logo;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getAccount_bank_name() {
        return account_bank_name;
    }

    public void setAccount_bank_name(String account_bank_name) {
        this.account_bank_name = account_bank_name;
    }

    public String getAccount_bank_no() {
        return account_bank_no;
    }

    public void setAccount_bank_no(String account_bank_no) {
        this.account_bank_no = account_bank_no;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getApp_display() {
        return app_display;
    }

    public void setApp_display(String app_display) {
        this.app_display = app_display;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getCreate_user() {
        return create_user;
    }

    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }

    public String getCreate_user_name() {
        return create_user_name;
    }

    public void setCreate_user_name(String create_user_name) {
        this.create_user_name = create_user_name;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    public String getUpdate_user_name() {
        return update_user_name;
    }

    public void setUpdate_user_name(String update_user_name) {
        this.update_user_name = update_user_name;
    }
}
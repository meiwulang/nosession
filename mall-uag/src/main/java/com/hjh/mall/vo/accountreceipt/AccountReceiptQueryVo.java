package com.hjh.mall.vo.accountreceipt;

import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.common.core.vo.PageQueryVO;
import com.hjh.mall.field.type.Status;

public class AccountReceiptQueryVo extends PageQueryVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2490634392068951605L;

	// 账号id 
    private String account_id;

    // 开户名 
    private String account_name;

    // 银行名称 
    private String bank_name;

    // 开户行 
    private String account_bank_name;

    // 银行账号 
    private String account_bank_no;

    // 是否显示 
    @EnumValue(enumClass = Status.class,message="取值范围")
    private String app_display;
    
    private String create_user_name;
    
    private String create_user;
    
    private String update_user_name;
    
    private String update_user;

	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
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

	public String getApp_display() {
		return app_display;
	}

	public void setApp_display(String app_display) {
		this.app_display = app_display;
	}

	public String getCreate_user_name() {
		return create_user_name;
	}

	public void setCreate_user_name(String create_user_name) {
		this.create_user_name = create_user_name;
	}

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public String getUpdate_user_name() {
		return update_user_name;
	}

	public void setUpdate_user_name(String update_user_name) {
		this.update_user_name = update_user_name;
	}

	public String getUpdate_user() {
		return update_user;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}

}

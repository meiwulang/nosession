package com.hjh.mall.vo.accountreceipt;

import com.hjh.mall.common.core.vo.PageQueryVO;

public class AccountReceiptQueryAppVo extends PageQueryVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2490634392068951605L;


    // 开户名 
    private String account_name;

    // 银行名称 
    private String bank_name;

    // 开户行 
    private String account_bank_name;

    // 银行账号 
    private String account_bank_no;

 


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


}

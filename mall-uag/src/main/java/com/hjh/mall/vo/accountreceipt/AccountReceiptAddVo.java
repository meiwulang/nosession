package com.hjh.mall.vo.accountreceipt;
import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.common.core.annotation.Length;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.annotation.Range;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.field.type.Status;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel
public class AccountReceiptAddVo extends HJYVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2490634392068951605L;
    
    // 开户名 
	@NotBlank
	@Length(max=25,min=1)
	@ApiModelProperty(name = "account_name", value = "开户名",dataType="String",notes="开户名", required = true)
    private String account_name;

    // 银行名称 
	@NotBlank
	@Length(max=10,min=1)
	@ApiModelProperty(name = "bank_name", value = "银行名称",dataType="String",notes="银行名称", required = true)
    private String bank_name;

    // 开户行 
	@NotBlank
	@Length(max=30,min=2)
	@ApiModelProperty(name = "account_bank_name", value = "开户行 ",dataType="String",notes="开户行 ", required = true)
    private String account_bank_name;

    // 银行账号 
	@NotBlank
	@Length(max=25,min=2)
	@ApiModelProperty(name = "account_bank_no", value = "银行账号 ",dataType="String",notes="银行账号 ", required = true)
    private String account_bank_no;
    //排序
	@Range(max=9999,min=1)
	@ApiModelProperty(notes="取值1~9999", required = true)
    private Integer sort;
    // 是否显示 
    @NotBlank
    @EnumValue(enumClass = Status.class)
    @ApiModelProperty(dataType="String",notes="是否启用1启用 2禁用", required = true)
    private String app_display;
    
    // logo
    @NotBlank
    @ApiModelProperty(dataType="String",notes="logo base64字符串", required = true)
    private String account_logo;
    
    
    


	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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

	public String getApp_display() {
		return app_display;
	}

	public void setApp_display(String app_display) {
		this.app_display = app_display;
	}

}

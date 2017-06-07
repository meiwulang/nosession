package com.hjh.mall.vo.accountreceipt;

import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.field.type.Status;

import io.swagger.annotations.ApiModelProperty;

public class AccountReceiptAppDisplayVo extends HJYVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2490634392068951605L;

	// 账号id 
	@NotBlank
	@ApiModelProperty(notes="账号id")
    private String account_id;

    // 是否显示 
    @NotBlank
    @EnumValue(enumClass = Status.class,message="取值范围")
    @ApiModelProperty(notes="是否显示 ")
    private String app_display;
    
    

	
	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}

	public String getApp_display() {
		return app_display;
	}

	public void setApp_display(String app_display) {
		this.app_display = app_display;
	}

}

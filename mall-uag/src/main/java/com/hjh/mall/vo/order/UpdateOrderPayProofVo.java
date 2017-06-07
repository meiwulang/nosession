package com.hjh.mall.vo.order;

import com.hjh.mall.common.core.annotation.ChineseOrLetter;
import com.hjh.mall.common.core.annotation.Length;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.annotation.NotNull;
import com.hjh.mall.common.core.vo.HJYVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

@ApiModel("更新用户支付凭证")
public class UpdateOrderPayProofVo extends HJYVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotBlank
	private @ApiModelProperty(name = "payProofId", value = "支付凭证主键", required = true) String payProofId;

	@Length(min=1,max = 10)
	@ApiModelProperty(name = "paymentUserName", value = "付款人姓名", required = true)
	String paymentUserName;

	@Length(max = 30)
	@ChineseOrLetter
	@ApiModelProperty(name = "bankAccount", value = "支付银行帐号", required = false)
	String bankAccount;

	@Length(max = 20)
	@ApiModelProperty(name = "paymentAccount", value = "支付帐号", required = false)
	String paymentAccount;

	@Range(min=0,max=999999999L)
	@ApiModelProperty(name = "amount", value = "金额", required = false)
	Double amount;

	@Length(max = 200)
	@ApiModelProperty(name = "paymentProofSnapshot", value = "支付凭证快照", required = false)
	String paymentProofSnapshot;


	@NotBlank
	@ApiModelProperty(name = "orderId", value = "订单ID", required = true)
	private String orderId;


	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPayProofId() {
		return payProofId;
	}

	public void setPayProofId(String payProofId) {
		this.payProofId = payProofId;
	}

	public String getPaymentUserName() {
		return paymentUserName;
	}
	public void setPaymentUserName(String paymentUserName) {
		this.paymentUserName = paymentUserName;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getPaymentAccount() {
		return paymentAccount;
	}
	public void setPaymentAccount(String paymentAccount) {
		this.paymentAccount = paymentAccount;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getPaymentProofSnapshot() {
		return paymentProofSnapshot;
	}
	public void setPaymentProofSnapshot(String paymentProofSnapshot) {
		this.paymentProofSnapshot = paymentProofSnapshot;
	}
	


	
	
	
	

}

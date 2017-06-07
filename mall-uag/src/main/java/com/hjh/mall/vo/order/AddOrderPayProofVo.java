package com.hjh.mall.vo.order;

import com.hjh.mall.common.core.annotation.NotBlank;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import com.hjh.mall.common.core.annotation.ChineseOrLetter;
import com.hjh.mall.common.core.annotation.Length;
import com.hjh.mall.common.core.vo.HJYVO;
import org.hibernate.validator.constraints.Range;

@ApiModel("新增订单支付凭证")
public class AddOrderPayProofVo extends HJYVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Length(min=1,max = 10)
	private @ApiModelProperty(name = "paymentUserName", value = "付款人姓名", required = true) String paymentUserName;

	@Length(max = 30)
	@ChineseOrLetter
	private @ApiModelProperty(name = "bankAccount", value = "付款银行", required = false) String bankAccount;

	@Length(max = 20)
	private @ApiModelProperty(name = "paymentAccount", value = "支付帐号", required = false) String paymentAccount;

	@Range(min=0,max=999999999L)
	private @ApiModelProperty(name = "amount", value = "金额", required = false) Double amount;

	@Length(max = 200)
	private @ApiModelProperty(name = "paymentProofSnapshot", value = "支付凭证快照", required = false) String paymentProofSnapshot;

	@NotBlank
	private @ApiParam(name = "orderId", value = "订单主键", required = true) String orderId;

	private @ApiParam(name = "isDepositProof", value = "是否未定金支付,默认false", required = false) Boolean isDepositProof=false;

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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Boolean isDepositProof() {
		return isDepositProof;
	}

	public void setDepositProof(Boolean depositProof) {
		isDepositProof = depositProof;
	}
}

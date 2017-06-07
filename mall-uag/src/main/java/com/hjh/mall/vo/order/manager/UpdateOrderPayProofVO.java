package com.hjh.mall.vo.order.manager;

import com.hjh.mall.common.core.annotation.ChineseOrLetter;
import com.hjh.mall.common.core.annotation.Length;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@ApiModel("更新用户支付凭证")
public class UpdateOrderPayProofVO extends HJYVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 3719182122251292883L;
	@NotBlank
	private @ApiModelProperty(name = "orderId", value = "订单主键", required = true) String orderId;
	@NotBlank
	private @ApiModelProperty(name = "payProofId", value = "支付凭证主键", required = true) String payProofId;

	@NotBlank
	@Length(min=1,max = 10)
	private @ApiModelProperty(name = "paymentUserName", value = "付款人姓名", required = true) String paymentUserName;

	@Length(max = 30)
	@ChineseOrLetter
	private @ApiModelProperty(name = "bankAccount", value = "银行帐号", required = true) String bankAccount;

	@Length(max = 20)
	private @ApiModelProperty(name = "paymentAccount", value = "支付帐号", required = true) String paymentAccount;

	@Range(min=0,max=999999999L)
	private @ApiModelProperty(name = "amount", value = "帐号", required = true) Double amount;

	@Length(max = 200)
	private @ApiModelProperty(name = "paymentProofSnapshot", value = "支付凭证快照", required = true) String paymentProofSnapshot;

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UpdateOrderPayProofVO [orderId=").append(orderId).append(", paymentUserName=")
				.append(paymentUserName).append(", bankAccount=").append(bankAccount).append(", paymentAccount=")
				.append(paymentAccount).append(", account=").append(amount).append(", paymentProofSnapshot=")
				.append(paymentProofSnapshot).append("]");
		return builder.toString();
	}

}

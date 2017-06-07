package com.hjh.mall.order.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

import com.hjh.mall.common.core.annotation.Length;
import com.hjh.mall.common.core.annotation.MobileOrTel;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;

/**
 * @Project: mall-order-sdk
 * @Description 添加物流信息vo
 * @author 杨益桦
 * @date 2017年5月24日
 * @version V1.0
 */

public class AddLogisticsVo extends HJYVO{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(name = "orderId", value = "订单主键", required = true)
	@NotBlank
	private String orderId;
	
	@ApiModelProperty(name = "orderNo", value = "订单号", required = true)
	@NotBlank
	String orderNo;
	
	@ApiModelProperty(name = "logisticsNo", value = "物流单号", required = false)
	@Length(max=30)
	String logisticsNo;
	
	@ApiModelProperty(name = "logisticsNoteSnapshot", value = "快递单快照", required = false)
	String logisticsNoteSnapshot;
	
	@ApiModelProperty(name = "senderName", value = "送货人姓名", required = false)
	@Length(max=10,min=0)
	String senderName;
	
	@ApiModelProperty(name = "senderMobile", value = "送货人手机号", required = false)
	@MobileOrTel
	String senderMobile;
	
	@ApiModelProperty(name = "logisticsCompany", value = "物流公司", required = true)
	@NotBlank
	@Length(max=10,min=2)
	String logisticsCompany;
	
	@ApiModelProperty(name = "deliveryDate", value = "发货日期", required = true)
	Date deliveryDate;
	
	String createUserId;
	
	String createUserName;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getLogisticsNo() {
		return logisticsNo;
	}

	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}

	public String getLogisticsNoteSnapshot() {
		return logisticsNoteSnapshot;
	}

	public void setLogisticsNoteSnapshot(String logisticsNoteSnapshot) {
		this.logisticsNoteSnapshot = logisticsNoteSnapshot;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderMobile() {
		return senderMobile;
	}

	public void setSenderMobile(String senderMobile) {
		this.senderMobile = senderMobile;
	}

	public String getLogisticsCompany() {
		return logisticsCompany;
	}

	public void setLogisticsCompany(String logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

}

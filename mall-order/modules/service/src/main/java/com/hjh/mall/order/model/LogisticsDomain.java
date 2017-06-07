package com.hjh.mall.order.model;

import java.util.Date;

/**
 * Created by qiuxianxiang on 17/5/16.
 */
public class LogisticsDomain {

    private String logisticsId;   // 物流表主键
    private String orderId;       // 订单主键
    private String createUserId;  // 操作人id
    private String orderNo;       // 订单号
    private String logisticsNo;   // 物流单号
    private String logisticsNoteSnapshot;    // 快递单快照
    private String senderName;     //  送货人姓名
    private String senderMobile;   //  送货人手机号
    private String logisticsCompany;    // 物流公司
    private Date deliveryDate;     // 发货日期
    private Date createDate;       // 创建时间
    private String createUserName; //操作人姓名
    
    

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

	public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LogisticsDomain [logisticsId=");
		builder.append(logisticsId);
		builder.append(", orderId=");
		builder.append(orderId);
		builder.append(", createUserId=");
		builder.append(createUserId);
		builder.append(", orderNo=");
		builder.append(orderNo);
		builder.append(", logisticsNo=");
		builder.append(logisticsNo);
		builder.append(", logisticsNoteSnapshot=");
		builder.append(logisticsNoteSnapshot);
		builder.append(", senderName=");
		builder.append(senderName);
		builder.append(", senderMobile=");
		builder.append(senderMobile);
		builder.append(", logisticsCompany=");
		builder.append(logisticsCompany);
		builder.append(", deliveryDate=");
		builder.append(deliveryDate);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", createUserName=");
		builder.append(createUserName);
		builder.append(", getCreateUserId()=");
		builder.append(getCreateUserId());
		builder.append(", getCreateUserName()=");
		builder.append(getCreateUserName());
		builder.append(", getLogisticsId()=");
		builder.append(getLogisticsId());
		builder.append(", getOrderId()=");
		builder.append(getOrderId());
		builder.append(", getOrderNo()=");
		builder.append(getOrderNo());
		builder.append(", getLogisticsNo()=");
		builder.append(getLogisticsNo());
		builder.append(", getLogisticsNoteSnapshot()=");
		builder.append(getLogisticsNoteSnapshot());
		builder.append(", getSenderName()=");
		builder.append(getSenderName());
		builder.append(", getSenderMobile()=");
		builder.append(getSenderMobile());
		builder.append(", getLogisticsCompany()=");
		builder.append(getLogisticsCompany());
		builder.append(", getDeliveryDate()=");
		builder.append(getDeliveryDate());
		builder.append(", getCreateDate()=");
		builder.append(getCreateDate());
		builder.append("]");
		return builder.toString();
	}

    
}

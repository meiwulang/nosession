package com.hjh.mall.order.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by qiuxianxiang on 17/5/16.
 */
public class Logistics implements Serializable {

	private static final long serialVersionUID = 1L;
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
}

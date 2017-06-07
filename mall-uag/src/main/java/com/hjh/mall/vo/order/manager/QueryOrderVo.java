package com.hjh.mall.vo.order.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Arrays;
import java.util.Date;

import com.hjh.mall.common.core.vo.PageQueryVO;
import com.hjh.mall.order.constants.OrderStatus;
import com.hjh.mall.order.constants.TransactionType;

@ApiModel("订单入参查询实体类")
public class QueryOrderVo extends PageQueryVO {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(name = "client_id", value = "下单用户数据库主键,精确查询", required = false)
	private	String client_id;

	@ApiModelProperty(name = "postage", value = "邮费是否合算", required = false)
	private	Boolean postage;

	@ApiModelProperty(name = "userId", value = "订单凭证是否上传", required = false)
	private	Boolean ischeckupload;

	@ApiModelProperty(name = "orderNoLike", value = "模糊订单号", required = false)
	private	String orderNoLike;

	@ApiModelProperty(name = "productNoLike", value = "模糊商品编号", required = false)
	private	String productNoLike;

	@ApiModelProperty(name = "productTitleLike", value = "模糊商品标题", required = false)
	private	String productTitleLike;

	@ApiModelProperty(name = "brandLike", value = "模糊品牌名称", required = false)
	private	String brandLike;

	@ApiModelProperty(name = "userNameLike", value = "模糊用户名(下单用户)", required = false)
	private String userNameLike;

	@ApiModelProperty(name = "userMobileLike", value = "模糊手机号码(下单用户)", required = false)
	private	String userMobileLike;

	@ApiModelProperty(name = "inviteCodeBegin", value = "精确邀请码,范围开始", required = false)
	private	String inviteCodeBegin;

	@ApiModelProperty(name = "inviteCodeEnd", value = "精确邀请码,范围结束", required = false)
	private	String inviteCodeEnd;

	@ApiModelProperty(name = "transactionType", value = "交易类型", required = false)
	private	TransactionType transactionType;

	@ApiModelProperty(name = "estimateDeliveryDescLike", value = "预计发货时间描述 模糊查询", required = false)
	private	String estimateDeliveryDescLike;

	@ApiModelProperty(name = "createDateBegin", value = "订单创建时间,范围开始", required = false)
	private	Date createDateBegin;

	@ApiModelProperty(name = "createDateEnd", value = "订单创建时间,范围结束", required = false)
	private	Date createDateEnd;

	@ApiModelProperty(name = "deliveryDateBegin", value = "发货时间,范围开始", required = false)
	private	Date deliveryDateBegin;

	@ApiModelProperty(name = "deliveryDateEnd", value = "发货时间,范围结束", required = false)
	private	Date deliveryDateEnd;

	@ApiModelProperty(name = "orderStatus", value = "订单状态", required = false)
	private	OrderStatus[] orderStatus;
	
	@ApiModelProperty(name = "isPostageCalculation", value = "邮费是否核算过，不传则不作为过滤条件", required = false)
	private Boolean isPostageCalculation;
	
	@ApiModelProperty(name = "haveFullPayProof", value = "是否具有全款支付的凭证，不传则不作为过滤条件", required = false)
	private Boolean haveFullPayProof;

	@ApiModelProperty(name = "haveDepositPayProof", value = "是否具有定金支付的凭证，不传则不作为过滤条件", required = false)
	private Boolean haveDepositPayProof;

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public Boolean getPostage() {
		return postage;
	}

	public void setPostage(Boolean postage) {
		this.postage = postage;
	}

	public Boolean getIscheckupload() {
		return ischeckupload;
	}

	public void setIscheckupload(Boolean ischeckupload) {
		this.ischeckupload = ischeckupload;
	}

	public String getOrderNoLike() {
		return orderNoLike;
	}

	public void setOrderNoLike(String orderNoLike) {
		this.orderNoLike = orderNoLike;
	}

	public String getProductNoLike() {
		return productNoLike;
	}

	public void setProductNoLike(String productNoLike) {
		this.productNoLike = productNoLike;
	}

	public String getProductTitleLike() {
		return productTitleLike;
	}

	public void setProductTitleLike(String productTitleLike) {
		this.productTitleLike = productTitleLike;
	}

	public String getBrandLike() {
		return brandLike;
	}

	public void setBrandLike(String brandLike) {
		this.brandLike = brandLike;
	}

	public String getUserNameLike() {
		return userNameLike;
	}

	public void setUserNameLike(String userNameLike) {
		this.userNameLike = userNameLike;
	}

	public String getUserMobileLike() {
		return userMobileLike;
	}

	public void setUserMobileLike(String userMobileLike) {
		this.userMobileLike = userMobileLike;
	}

	public String getInviteCodeBegin() {
		return inviteCodeBegin;
	}

	public void setInviteCodeBegin(String inviteCodeBegin) {
		this.inviteCodeBegin = inviteCodeBegin;
	}

	public String getInviteCodeEnd() {
		return inviteCodeEnd;
	}

	public void setInviteCodeEnd(String inviteCodeEnd) {
		this.inviteCodeEnd = inviteCodeEnd;
	}

	public Boolean getPostageCalculation() {
		return isPostageCalculation;
	}

	public void setPostageCalculation(Boolean postageCalculation) {
		isPostageCalculation = postageCalculation;
	}

	public Boolean getHaveFullPayProof() {
		return haveFullPayProof;
	}

	public void setHaveFullPayProof(Boolean haveFullPayProof) {
		this.haveFullPayProof = haveFullPayProof;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}


	public String getEstimateDeliveryDescLike() {
		return estimateDeliveryDescLike;
	}

	public void setEstimateDeliveryDescLike(String estimateDeliveryDescLike) {
		this.estimateDeliveryDescLike = estimateDeliveryDescLike;
	}

	public Date getCreateDateBegin() {
		return createDateBegin;
	}

	public void setCreateDateBegin(Date createDateBegin) {
		this.createDateBegin = createDateBegin;
	}

	public Date getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(Date createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

	public Date getDeliveryDateBegin() {
		return deliveryDateBegin;
	}

	public void setDeliveryDateBegin(Date deliveryDateBegin) {
		this.deliveryDateBegin = deliveryDateBegin;
	}

	public Date getDeliveryDateEnd() {
		return deliveryDateEnd;
	}

	public void setDeliveryDateEnd(Date deliveryDateEnd) {
		this.deliveryDateEnd = deliveryDateEnd;
	}

	public OrderStatus[] getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus[] orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	

	public Boolean getIsPostageCalculation() {
		return isPostageCalculation;
	}

	public void setIsPostageCalculation(Boolean isPostageCalculation) {
		this.isPostageCalculation = isPostageCalculation;
	}

	public Boolean getHaveDepositPayProof() {
		return haveDepositPayProof;
	}

	public void setHaveDepositPayProof(Boolean haveDepositPayProof) {
		this.haveDepositPayProof = haveDepositPayProof;
	}
}

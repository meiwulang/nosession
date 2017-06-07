package com.hjh.mall.order.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.order.constants.OrderDelete;
import com.hjh.mall.order.constants.OrderStatus;
import com.hjh.mall.order.constants.TransactionType;

/**
 * Created by qiuxianxiang on 17/5/25.
 */
public class QueryOrderVo extends HJYVO {

    private static final long serialVersionUID = 1L;

    /**
     *[可选参数]    下单用户数据库主键
     */
    @ApiModelProperty(name = "userId", value = "下单用户数据库主键,精确查询", required = false)
    private String userId;

    /**
     * [可选参数]    模糊订单号
     */
    @ApiModelProperty(name = "orderNoLike", value = "模糊订单号", required = false)
    private String orderNoLike;

    /**
     *[可选参数]    模糊品牌名称
     */
    @ApiModelProperty(name = "brandNameLike", value = "模糊品牌名称", required = false)
    private String brandNameLike;

    /**
     *[可选参数]      模糊用户名(下单用户)
     */
    @ApiModelProperty(name = "userNameLike", value = "模糊用户名(下单用户)", required = false)
    private String userNameLike;

    /**
     *[可选参数]    邀请码开始范围
     */
    @ApiModelProperty(name = "inviteCodeBegin", value = "模糊用户名(下单用户)", required = false)
    private String inviteCodeBegin;

    /**
     * [可选参数]    邀请码结束范围
     */
    @ApiModelProperty(name = "inviteCodeEnd", value = "模糊用户名(下单用户)", required = false)
    private String inviteCodeEnd;


    /**
     *[可选参数]    预计发货时间描述 模糊查询
     */
    @ApiModelProperty(name = "estimateDeliveryDescLike", value = "预计发货时间描述 模糊查询", required = false)
    private String estimateDeliveryDescLike;

    /**
     *[可选参数]    订单创建时间,范围开始
     */
    @ApiModelProperty(name = "createDateBegin", value = "订单创建时间,范围开始", required = false)
    private Date createDateBegin;

    /**
     *[可选参数]    订单创建时间,范围结束
     */
	@ApiModelProperty(name = "createDateEnd", value = "订单创建时间,范围结束", required = false)
    private Date createDateEnd;

    /**
     *[可选参数]    发货时间,范围开始
     */
	@ApiModelProperty(name = "deliveryDateBegin", value = "发货时间,范围开始", required = false)
    private Date deliveryDateBegin;

    /**
     *[可选参数]    发货时间,范围结束
     */
	@ApiModelProperty(name = "deliveryDateEnd", value = "发货时间,范围结束", required = false)
    private Date deliveryDateEnd;

    /**
     *[可选参数]    交易类型
     */
	@ApiModelProperty(name = "transactionType", value = "交易类型", required = false)
    private TransactionType transactionType;

    /**
     *[可选参数]    订单状态列表
     */
	@ApiModelProperty(name = "orderStatus", value = "订单状态", required = false)
    private OrderStatus[] orderStatus;

    /**
     *[可选参数]    邮费是否核算
     */
    private Boolean isPostageCalculation;// 不用传

    //TODO
    private Boolean haveDepositPayProof;// 不用传

    private Boolean haveFullPayProof;// 不用传
    /**
     *
     */

    /**
     *[必填参数]    页号, 默认为1
     */
    private Integer pageNum=1;

    /**
     *[必填参数]    每页行数, 默认为10
     */
    private Integer pageSize=10;

    private int deleteFlag = OrderDelete.NORMAL.getCode();

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderNoLike() {
        return orderNoLike;
    }

    public void setOrderNoLike(String orderNoLike) {
        this.orderNoLike = orderNoLike;
    }

    public String getBrandNameLike() {
        return brandNameLike;
    }

    public void setBrandNameLike(String brandNameLike) {
        this.brandNameLike = brandNameLike;
    }

    public String getUserNameLike() {
        return userNameLike;
    }

    public void setUserNameLike(String userNameLike) {
        this.userNameLike = userNameLike;
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

    public Boolean getPostageCalculation() {
        return isPostageCalculation;
    }

    public void setPostageCalculation(Boolean postageCalculation) {
        isPostageCalculation = postageCalculation;
    }


    public Integer getPageNum() {
        if (pageNum <=0) {
            pageNum = 1;
        }
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        if (pageSize <=0) {
            return 10;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Boolean getHaveDepositPayProof() {
        return haveDepositPayProof;
    }

    public void setHaveDepositPayProof(Boolean haveDepositPayProof) {
        this.haveDepositPayProof = haveDepositPayProof;
    }

    public Boolean getHaveFullPayProof() {
        return haveFullPayProof;
    }

    public void setHaveFullPayProof(Boolean haveFullPayProof) {
        this.haveFullPayProof = haveFullPayProof;
    }
}
